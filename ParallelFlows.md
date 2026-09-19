# Parallel flows

`read`/`imm` `List.flow`, `List.flow(mode)`, `EList.flow(mode)` and `ESet.flow` are
parallel. The promise is that a parallel flow is observationally the sequential flow:
same values in the same order, the same error when there is one, and it terminates
exactly when the sequential flow terminates. What parallelism may change is only how
much CPU is spent, when `Debug#` lines from lambdas appear, and how long the answer
takes. This file is the design that keeps the promise, the options it was chosen over,
and the mechanisms for the work that turns out to be wasted.

## Design

A parallel flow records its stateless stages (`map`, `filter`, `flatMap`) and does not
run anything until a terminal pulls from it. At the first pull, the source list is cut
into chunks whose sizes double from 1 up to `n/(4*parallelism)`, so the first results
exist early and full throughput follows; at most `4*parallelism` chunks are in flight.
Each chunk is a common-pool task that pushes its elements, in encounter order, through
the stages into its own output list, publishing every output as soon as it exists. A
chunk stops at its first error and records it after the values it did produce; it also
stops when its speculation is pointless (below).

The consumer is an ordinary sequential JDK stream over the chunks' outputs in encounter
order, so every terminal and every consumer-side operation (`fold`, `first`, `limit`,
`scan`, `min`, `max`, `join`, gatherers) is the plain JDK one. It reads the outputs the
chunk it is on has published, in batches: a snapshot of the published count and of the
output array, consumed without touching the chunk again. When it has consumed
everything published and the chunk is not finished, if the chunk has not started it
runs it itself, one source element per pull; otherwise it parks
(`ForkJoinPool.managedBlock`, so a worker consuming a nested flow is compensated)
until the chunk finishes or, at the latest, a millisecond later, and then reads the
next batch. Reaching the end of a chunk that recorded an error rethrows that error.

When the terminal completes, with a result or an error, the stream closes and the
speculation is cancelled: chunks not yet started never start
(`ForkJoinTask.cancel(false)`), running chunks stop at their next element or at their
next `Yield#`.

Why each guarantee holds:
- values and order: chunks are consumed in encounter order and each chunk is
  sequential inside;
- the error: the consumer reaches errors in encounter order, so it reports the first
  one the sequential flow would meet; errors in chunks it never reaches are never seen;
- termination: the consumer never waits for an element the sequential flow would not
  evaluate. A short-circuit terminal decides on an output the instant it is published,
  and the elements after it in the same chunk are then pointless; a diverging one costs
  a worker, never the answer. A not-yet-started chunk is run by the consumer itself, so
  a pool whose workers are all lost still makes progress.

Publication: the worker stores the output in its array, then release-stores the count;
the consumer acquire-loads the count and then reads the array reference and the slots
below the count, which that edge makes visible. A release store is a plain store on
x86, so publishing costs nothing per output; a volatile store would wait for the store
buffer to drain, tens of nanoseconds under the allocation traffic of a Fearless lambda.
The consumer touches a live chunk only once per batch, so producer and consumer do not
fight over cache lines. A finishing chunk stores `done` (volatile) and unparks the
consumer if its `parked` flag is set; the consumer sets the flag (volatile) and
re-checks before parking, so no wakeup is lost. The timed park is the latency of a
decision taken inside a chunk that is still being produced: the consumer notices the
deciding output within about a millisecond (a timer tick on Windows) or, sooner, when
the chunk finishes. Waiting with the pool's own `join` instead would let a worker
consuming a nested flow be handed other queued chunks to run whole, evaluating elements
past a decision; the park never runs anything.

## Options and trade-offs

JDK parallel streams as they come. Order is right for ordered terminals and the
collection of `toList` is parallel, but the exception reported is the first in time,
`toList`/`forEach` never stop at an error (an error at position 3 still evaluates all
n, and a diverging element after it hangs), and every terminal holds the calling thread
until every leaf task has completed, so a leaf inside a lambda that has not reached a
checkpoint blocks `first!` after element 0 is already known. No JDK terminal can return
while leaves still run; that is the reason for a scheduler of our own.

JDK parallel streams with boxed results. Boxing each result as value-or-error and
scanning in order gives the order and the sequential error, but not the two above.

Chunks consumed whole. Joining each chunk task and only then reading its outputs is
the simplest scheduler, and it violates termination: the consumer waits for the whole
chunk that holds its decision, up to `n/(4*parallelism)` more evaluations, and hangs if
one of them diverges where the sequential flow returns. Publishing per output is the
fix; its price is one release store per output and the batching on the consumer.

Boxes or a pair. A chunk stops at its first error, so an error box is always the last
entry of a chunk: one `(values, error)` pair per chunk says the same with no per-element
check, no `null` marker and no comparison of positions. Which error wins across chunks
is decided by the consumer's order, never by the time at which chunks fail.

Running the stages inside a chunk. A JDK stream's pull iterator buffers, and `flatMap`
pushes a whole inner stream through the later stages into that buffer before the first
element comes out, so when a later stage throws on the second inner element the first
is lost and the error lands one element early. A chain of `Consumer`s passes each
element through every stage one at a time, so the values before an error are exactly
the sequential ones, and it is faster (no per-element buffer or lambda allocation).

Cancellation points inside an element. Cancelling queued chunks and stopping running
ones between elements needs no cooperation. Inside one element there are these levels:
- none: a lambda that never returns keeps its worker for the life of the process. Java
  streams and Rust's rayon live here.
- a poll at each `Block.loop` iteration: about two nanoseconds per iteration, covers
  loops and nothing else (recursion never passes it).
- an explicit `Yield#`: no cost unless called, covers exactly what the programmer marks,
  loops and recursion alike. Kotlin coroutines, .NET and Go work this way
  (`ensureActive`, `ThrowIfCancellationRequested`, `ctx.Done()`). This is what the
  runtime has.
- a poll at the entry of every generated method, behind one global flag: covers
  everything without cooperation, as BEAM's reduction count does; a tax on every call
  and a Coordinator change.
- killing from outside (below): covers everything without cooperation, at the price of
  a native agent.

Chunk size. `n/(4*parallelism)` is the JDK's own leaf size. Smaller chunks bound the
speculative work done after a decision (the running chunks finish their current
element, then stop) and cost one task each, a few microseconds; the doubling ramp
already keeps the early chunks tiny.

`size`. The JDK's `count()` returns the source size of a pipeline whose size is known
without running its stages, so a sequential `map{f}.size` would never run `f` while a
parallel one, whose size is unknown until the end, would; `size` therefore counts by
reduction in both modes and an error in a stage is the same error in both. `isEmpty`
is `first.isEmpty` and evaluates one element in both modes.

What parallelism buys. Every `Nat` above 255 is a heap object, so a lambda dominated by
arithmetic is dominated by allocation, and allocation scales with memory bandwidth, not
with cores; on a machine where one thread already saturates it, a parallel flow cannot
beat the sequential one whatever the design. Lambdas that compute more than they
allocate are the ones that gain.

## Cooperative cancellation

A checkpoint is a call that throws `Cancelled` when the current chunk is pointless and
does nothing otherwise; `Yield#` is the explicit one, and the chunk loop checks the
same condition between elements. `Cancelled` is a Java exception, not a Fearless
error: no Fearless code can name it, and the outputs of a pointless chunk are never
read, so a checkpoint is semantically a no-op wherever it sits. It unwinds the worker to
the chunk boundary and frees the core. A speculation nested in a pointless chunk is
pointless too, so the check follows the parent chain.

Two places in the runtime catch Java exceptions on behalf of Fearless code and must let
`Cancelled` through, whatever triggers it:
- `_CapTry` (`System.try`) turns any `Throwable` into an `Action.info`. It rethrows
  `Cancelled` first; otherwise a `try` inside a speculative lambda swallows the
  cancellation and continues, and a memoized continuation publishes a value the
  sequential program never computes.
- the memo `Entry` stores whatever a cached body throws, for its waiters and for later
  callers. A body cancelled halfway must leave no entry: waiters compute themselves and
  the entry is evicted. Otherwise the main program's next call of that memo rethrows
  `Cancelled`.

## Killing from outside

Goal: eventual termination, seconds of delay are fine, of every lost cause, including
code that never reaches a checkpoint. Long-lasting programs need it: a leaked worker is
a leaked core for the life of the process.

No Java API does this. `Thread.stop` throws `UnsupportedOperationException` since
JDK 20, `interrupt` only wakes blocking calls, and virtual threads are neither preempted
while computing nor killable; a spinning virtual thread pins its carrier. JVMTI does: a
native agent (C, one binary per platform, loaded by the launcher with `-agentpath`)
has `SuspendThread`, `GetStackTrace`, `StopThread` (inject an exception into a thread,
raised when it runs again) and `ResumeThread`; JEP 444 extends them to virtual threads,
where `StopThread` wants the target suspended, which the protocol below does anyway.

Each running chunk records its worker thread. A janitor thread visits the chunks of
cancelled speculations that are still running after a grace period:
1. `SuspendThread`: the stack is frozen, so the inspection is exact.
2. `GetStackTrace`: JIT-inlined methods are reported as their own frames.
3. Safe iff every frame above the chunk boundary belongs to generated Fearless code or
   to an `rt` class that is exception safe at every instruction: no `<clinit>`, no
   `java.*` or `jdk.*` frame, no `Entry`/`Cache*`, no native frame.
4. Safe: `StopThread` with `Cancelled`, then `ResumeThread`; the exception unwinds
   through `finally` blocks and monitor exits like any other, and through the two catch
   points above. Unsafe: `ResumeThread`, visit again after the grace period.

Why the criterion is sound for Fearless and for nothing else on the JVM: a speculative
lambda is `read` or `imm`, so it cannot mutate state the rest of the program can see.
The only shared mutable state it can be in the middle of updating is the runtime's own,
and that is exactly the set of frames the criterion rejects. A Java thread stopped at an
arbitrary point can leave a half-updated map behind; a Fearless worker cannot, unless
it is inside one of those frames, and then it is left alone until it is not.

Costs: the agent per platform inside the portable image, the janitor, and the two
`Cancelled`-aware catch points. Checkpoints stay the fast path; killing is the backstop
for code that never checks.
