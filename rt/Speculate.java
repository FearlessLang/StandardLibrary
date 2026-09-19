package base;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SuppressWarnings("serial")
final class Cancelled extends RuntimeException{ Cancelled(){ super(null, null, false, false); } }

final class Speculate{
  private static final ThreadLocal<Speculate> current= new ThreadLocal<>();
  private static final ThreadLocal<int[]> shield= ThreadLocal.withInitial(()->new int[1]);
  private final Speculate parent= shield.get()[0] > 0 ? null : current.get();
  private final Thread consumer= Thread.currentThread();
  private final ArrayDeque<Chunk> pending= new ArrayDeque<>();
  private final ConcurrentLinkedQueue<Chunk> running= new ConcurrentLinkedQueue<>();
  private final boolean join;
  private volatile boolean cancelled;
  private volatile boolean parked;
  private Speculate(boolean join){ this.join= join; }
  static void poll(){
    var sp= current.get();
    if (sp != null && shield.get()[0] == 0 && sp.cancelled()){ throw new Cancelled(); }
  }
  static Object shielded(Supplier<Object> s){
    var depth= shield.get();
    depth[0]+= 1;
    try{ return s.get(); }
    finally{ depth[0]-= 1; }
  }
  private boolean cancelled(){ return cancelled || (parent != null && parent.cancelled()); }
  private void cancel(){
    cancelled= true;
    pending.forEach(c -> c.task.cancel(false));
    if (join){ running.forEach(Chunk::finish); }
  }
  private void wake(){ if (parked){ LockSupport.unpark(consumer); } }
  static Stream<Object> stream(List<Object> src, UnaryOperator<Consumer<Object>> stages, boolean join){
    var sp= new Speculate(join);
    return StreamSupport.stream(sp.ordered(src, stages), false).onClose(sp::cancel);
  }
  private final class Chunk implements ForkJoinPool.ManagedBlocker{
    final List<Object> sub;
    final Consumer<Object> sink;
    final AtomicBoolean started= new AtomicBoolean();
    final AtomicInteger published= new AtomicInteger();
    final AtomicBoolean done= new AtomicBoolean();
    Object[] out;
    int count= 0;
    int next= 0;
    int seen= 0;
    Throwable err;
    ForkJoinTask<?> task;
    Chunk(List<Object> sub, UnaryOperator<Consumer<Object>> stages){
      this.sub= sub;
      out= new Object[sub.size()];
      sink= stages.apply(this::publish);
    }
    void publish(Object e){
      if (count == out.length){ out= Arrays.copyOf(out, 2 * count); }
      out[count++]= e;
      published.setRelease(count);
    }
    boolean step(){
      if (next == sub.size()){ return false; }
      try{ if (cancelled()){ throw new Cancelled(); } sink.accept(sub.get(next++)); return true; }
      catch(Throwable t){ err= t; return false; }
    }
    void finish(){ while (!done.get()){ LockSupport.parkNanos(this, 100_000L); } }
    void run(){
      if (!started.compareAndSet(false, true)){ return; }
      running.add(this);
      var prev= current.get();
      current.set(Speculate.this);
      try{ while (step()){} }
      finally{ current.set(prev); done.set(true); wake(); }
    }
    void await(int end){
      seen= end;
      try{ ForkJoinPool.managedBlock(this); }
      catch(InterruptedException e){ Thread.currentThread().interrupt(); throw new RuntimeException(e); }
    }
    @Override public boolean isReleasable(){ return done.get() || published.getAcquire() > seen; }
    @Override public boolean block(){
      parked= true;
      if (!isReleasable()){ LockSupport.parkNanos(this, 1_000_000L); }
      parked= false;
      return isReleasable();
    }
  }
  private Spliterator<Object> ordered(List<Object> src, UnaryOperator<Consumer<Object>> stages){
    int n= src.size();
    int parallelism= ForkJoinPool.getCommonPoolParallelism();
    int cap= Math.max(1, n / (4 * parallelism));
    return new Spliterators.AbstractSpliterator<Object>(Long.MAX_VALUE, Spliterator.ORDERED){
      int next= 0;
      int size= 1;
      Chunk buf;
      Object[] out;
      int pos= 0;
      int end= 0;
      boolean mine;
      boolean peeked;
      void fill(){
        while (pending.size() < 4 * parallelism && next < n){
          var c= new Chunk(src.subList(next, Math.min(n, next + size)), stages);
          c.task= ForkJoinTask.adapt(c::run).fork();
          pending.add(c);
          next+= c.sub.size();
          size= Math.min(cap, 2 * size);
        }
      }
      boolean advance(){
        if (buf != null && buf.err != null){ throw sneaky(buf.err); }
        if (buf == null){ fill(); }
        buf= pending.poll();
        pos= 0;
        end= 0;
        mine= false;
        peeked= false;
        fill();
        return buf != null;
      }
      void snapshot(int published){ end= published; out= buf.out; }
      boolean ready(){
        while (true){
          if (pos < end){ return true; }
          if (buf == null && !advance()){ return false; }
          if (mine){
            while (pos == buf.count && buf.step()){}
            snapshot(buf.count);
            if (pos < end){ return true; }
            if (!advance()){ return false; }
            continue;
          }
          boolean done= buf.done.get();
          if (!done && buf.started.compareAndSet(false, true)){ mine= true; continue; }
          if (!done && peeked){ buf.await(end); done= buf.done.get(); }
          peeked= true;
          snapshot(buf.published.getAcquire());
          if (pos < end){ return true; }
          if (done && !advance()){ return false; }
        }
      }
      @Override public boolean tryAdvance(Consumer<? super Object> action){
        if (!ready()){ return false; }
        action.accept(out[pos++]);
        return true;
      }
      @Override public void forEachRemaining(Consumer<? super Object> action){
        while (ready()){
          while (pos < end){ action.accept(out[pos++]); }
        }
      }
    };
  }
  @SuppressWarnings("unchecked") private static <E extends Throwable> RuntimeException sneaky(Throwable t) throws E{ throw (E)t; }
}
