# Wrapper algebra

Status: design, not implemented. Nothing in `base` changes until this is agreed.

## The problem

`Wrapper[S,T]` gives a name, an ordering, a hash and a printed form. It gives no
arithmetic. So every computation unwraps, computes in the raw backing type, and
rewraps. In `testBasketball` that is 99 `.get` calls in 312 lines, plus 42
reconstructions. `MenuBounds` is the honest example:

    .let w = { Width#(width.get * 2.aluDiv 5) }
    .let x = { X#(width.get - (w.get) .aluDiv 2) }

Three unwraps and two rewraps to say "two fifths, centred".

The deeper cost is not the noise. The wrappers protect the boundaries, and then
every computation happens in undifferentiated `Nat` where nothing checks that the
operands belong to the same axis. `X#(width.get - (w.get) .aluDiv 2)` mixes a
width into an x computation and no type objects. The types guard the doorways and
leave the room unlocked.

## Three algebras, one refinement, one null

The split is by operation set, not by what the values happen to mean.

### Extent -- a magnitude along an axis

Closed under addition. Scalable by the backing number. The ratio of two of them is
dimensionless.

Members today: `WidthNat`, `HeightNat`, and the duration half of `Time`.

Not offered: `Self * Self`, which would need a type that does not exist. See
"No derived units".

### Coord -- a position on an axis

Not closed under anything. It is a set that the extents act on. The type parameter
is the extent type, which is what makes `X + Height` fail to compile rather than
merely being wrong.

Members today: `XNat`, `YNat`, and the instant half of `Time`.

Not offered: `Self + Self`, `Self * anything`.

### Level -- an extent with bounds

An extent whose type carries a floor and a ceiling. `Alpha` already has
`.transparent` and `.opaque`; `Hue`, `Saturation` and `Brightness` already assert
their bound in the constructor.

Members today: `Red`, `Green`, `Blue`, `Alpha` on `Byte`; `Saturation`,
`Brightness` on `Float`.

An earlier draft made `Level` a separate algebra whose `+` saturated instead of
throwing. That was wrong: it would have meant one symbol with two meanings
depending on which type you were looking at. `Level` propagates the backing throw
like everything else, which collapses it into a refinement of `Extent` rather than
a fourth algebra. Blending, which is what these are actually used for, gets a name
instead of a symbol.

### Cyclic -- a quantity that wraps

Wraps modulo a period, and equality itself is modulo that period. This is the one
group that cannot be a refinement of `Extent`, because it changes what `==` means.

Members today: `Hue` -- and `Degree` and `Radian`, which already implement this by
hand. `Angle[Self]` in `math.fear` is a cyclic mixin under another name, and
`Degree` and `Radian` separately repeat
`.cmp t0,t1,m -> t0.normalize# <=> (t1.normalize#, m)` and
`.hash -> this.normalize#.hash`.

### Wrapper alone -- no algebra

`KeyStroke`. A pure tag, and it should stay one.

## Rules

These are what stop this becoming a units-of-measure framework.

### 1. Inherit the failure mode, never reinterpret it

Every fixed width backing is partial. `Nat` throws below `0` and above `maxNat`;
`Int` throws outside `[minInt, maxInt]`; `Byte` outside `[0,255]`. `Float` never
throws but saturates to infinity, and only `Num` is genuinely total. There is no
choice of backing that makes `Coord - Coord` total, so the algebra cannot promise
totality.

What it can promise is transparency: an operation throws exactly when the backing
operation throws. A reader who knows `Nat` can predict `Width` without looking it
up. The moment a wrapper saturates where its backing throws, one symbol has two
contracts and the reader has to know which type they are holding.

Where a total variant is wanted it gets a name, not a symbol, following the
convention the library already uses: `getIndexOffset` throws and `indexOffset`
returns `Opt`, exactly as `getInt`/`int` and `getDiv`/`div`.

### 2. No derived units

`Width * Height -> Area` and `Width / Duration -> Speed` are out. An operation is
only offered when its result lands in a type that already exists.

This is the rule that matters most, and it comes from L42. Its `TraitUnit` -- the
single unit case -- is about twenty lines and carries `+`, `-`, `*` by scalar, `/`
by scalar, `/` by itself, comparisons and `zero`. Perfectly tractable. The
explosion is entirely in `CompositUnit`, and it has three causes:

- Composition. `Times`, `Per` and `Inverse` each need their own inverses:
  `(A/B) * B -> A`, `(A*B) / B -> A`, `(A*B) / A -> B`, and the same again for
  `Inverse`. That is where nine forms of division come from.
- The operators have to be installed on the operands, not on the result. `A / B`
  cannot be a method on `A/B`; it must be a method on `A` taking a `B`. So
  declaring a composite retroactively adds methods to both operand classes, which
  in L42 needed `Trait.delayedCode`, `Class.Relax` and a `ParRename` pass to stop
  the injected names colliding. Fearless has no such facility, so every one of
  those methods would be hand written.
- Non commutativity doubles it. The `*1` and `/1` variants exist only because the
  receiver can be on the wrong side.

Keeping `.get` as the escape hatch is what lets the closed set stay closed: if you
need an area, you unwrap and take one.

### 3. One operand order

Fearless dispatches on the left operand, and `Nat` is `Sealed`, so `Nat * Width`
cannot exist. `Width * Nat` is the canonical order and there is no flipped variant.
This is L42's `*1` problem, avoided by not having the other direction at all.

### 4. `/` is a ratio, not a division

`Nat./` returns `Num`, the exact rational. If `Width / 3` returned a `Width` it
would have to truncate, and `/` would mean exact division on `Nat` and truncating
division on a type wrapping `Nat` -- rule 1 violated in the most confusing possible
place. So `/` is only `Self / Self -> Num`, and scaling down is `.truncDiv`,
mirroring the backing's own vocabulary. The calling code already wants that
spelling: `MenuBounds` writes `.aluDiv 5` today.

### 5. `zero` and `origin` are different words for different things

An extent's `zero` is the additive identity, a structural fact. A coordinate's
`origin` is a chosen reference: screen origin is top left by convention, game time
zero is when the warmup ended. Sharing one name would hide that one of them is a
convention.

## Signatures

Method identity in Fearless is name plus arity (`MName(s, arity)`), with no
overloading on parameter type. That is a real constraint here: `Coord` wants both
`- (E) -> Self` and `- (Self) -> E`, and they are both `-/1`. They cannot coexist.

`-` therefore takes an extent, pairing with `+` on the same operand type, and the
difference of two coordinates gets a name.

    Extent[S,T]: Wrapper[S,T] {
      +(other: S): S;              /// throws exactly when the backing + throws
      -(other: S): S;              /// throws exactly when the backing - throws
      .gap(other: S): S;           /// absolute difference; total for Nat backing
      *(k: T): S;                  /// scale up
      .truncDiv(k: T): S;          /// scale down, truncating like the backing
      /(other: S): Num;            /// dimensionless ratio
      .zero: S;
      }

    Coord[S,E]: Wrapper[S,T] {
      +(d: E): S;
      -(d: E): S;
      .to(other: S): E;            /// directed difference; throws like the backing
      .gap(other: S): E;           /// absolute difference; total for Nat backing
      .origin: S;
      }

    Level[S,T]: Extent[S,T] {
      .min: S;
      .max: S;
      .mix(other: S, t: Num): S;
      }

    Cyclic[S,T]: Wrapper[S,T] {
      +(other: S): S;
      -(other: S): S;
      read .normalize: S;
      /// cmp and hash are defined on the normalized representative
      }

Then `WidthNat: Extent[WidthNat, Nat]`, `XNat: Coord[XNat, WidthNat]`,
`Red: Level[Red, Byte]`, `Hue: Cyclic[Hue, Float]`, `Angle: Cyclic` plus
trigonometry.

`.gap` is the operation worth arguing for. The directed difference throws whenever
the caller guessed the order wrong, and layout and collision code frequently does
not know the order. The absolute difference is total for `Nat` backing, and total
for `Int` coordinates landing in a `Nat` extent too, since `Int.abs` returns a
`Nat` precisely so the full span fits -- `Math.minInt.abs == Math.maxInt.getNat + 1`.

## Time splits in two

`Time` is a single `Nat` of microseconds doing both jobs. `Frame.elapsed` is
documented as time since game zero, which is a position, while everything physics
wants is a difference. Under this scheme that is exactly the `Coord`/`Extent` split:

    Duration: Extent[Duration, Nat]
    Instant:  Coord[Instant, Duration]

`Time.seconds(n)` and `Time.milliSeconds(n)` construct durations. `Frame.elapsed`,
`Graphics.elapsed`, `MouseEvent.elapsed` and `KeyEvent.elapsed` return instants,
and `now.to(then)` is a duration. This is a breaking change to those four
signatures and is the only breaking change the design requires.

## Out of scope, and why

- A `Number[S]` abstraction. The numeric tower already refutes it: `/` is
  `(Nat): Num` on `Nat`, `(Nat): Num` on `Byte`, `(Float): Float` on `Float`,
  `(Num): Num` on `Num`, and absent on `Int`. `**` has five different signatures
  too. The wrapper algebra is written for `Nat` and duplicated for `Byte` when
  colours want it.
- Unifying `Range` across the numeric types. `Nat`, `Int` and `Byte` enumerate via
  succ and pred; `Float` does so in ulp steps with NaN and infinity guards; `Num`
  produces a different type entirely, `NumRange`, with no `.flow`, no `.size`, and
  a `.clampTo` that only accepts `ClosedNumRange` because clamping to an open bound
  is ill defined. Unifying means giving `Num` a fake successor or a `.flow` that
  typechecks and throws.
- The 2-D aggregates. `Point`, `Size` and `Rect` are what actually delete
  `bounds.fear`, but they are unwritable until the scalars compose. They come next,
  and the names `Point` and `Size` are deliberately left free for them.

## Open questions

- `Coord` reads spatially; `Instant: Coord[Instant, Duration]` is defensible but
  `Mark` may be better for the time axis.
- `.to` versus `.delta` for the directed difference.
- `Wrapper` currently lives in `base/gui/types.fear`, but none of these four are
  GUI concepts. They probably belong under `base/datatypes/`, which would move
  `Wrapper` too.
- Whether `Extent` should offer `/` at all, given `*` takes the backing type and
  `/` takes `Self`. `.ratio(other): Num` avoids the asymmetry at the cost of the
  dimensional reading.
