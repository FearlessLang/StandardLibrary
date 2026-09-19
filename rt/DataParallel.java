package base;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import base.FlowOps.Err;
import base.FlowOps.FlowOp;
import base.FlowOps.Sink;

final class DataParallel{
  private DataParallel(){}
  static final ScopedValue<Boolean> sequentialised= ScopedValue.newInstance();
  static final ThreadLocal<Source> current= new ThreadLocal<>();
  static final int cpus= Runtime.getRuntime().availableProcessors();
  static final int parallelismPotential= 4 * cpus;
  static final Semaphore available= new Semaphore(parallelismPotential);
  static final Object stopToken= new Object();
  record Failure(Throwable t){}
  static final ThreadLocal<int[]> shield= ThreadLocal.withInitial(()->new int[1]);
  static void poll(){
    var s= current.get();
    if (s != null && shield.get()[0] == 0 && s.cancelled()){ throw new Cancelled(); }
  }
  static Object shielded(Supplier<Object> s){
    var depth= shield.get();
    depth[0]+= 1;
    try{ return s.get(); }
    finally{ depth[0]-= 1; }
  }
  static Object take(LinkedBlockingQueue<Object> q){
    try{ return q.take(); }
    catch(InterruptedException e){ Thread.currentThread().interrupt(); throw new RuntimeException(e); }
  }
  static void put(LinkedBlockingQueue<Object> q, Object e){
    try{ q.put(e); }
    catch(InterruptedException ex){ Thread.currentThread().interrupt(); throw new RuntimeException(ex); }
  }
  static void join(Thread t){
    try{ t.join(); }
    catch(InterruptedException e){ Thread.currentThread().interrupt(); throw new RuntimeException(e); }
  }
  static final class Source implements FlowOp{
    final FlowOp source;
    final long size;
    final Source parent= shield.get()[0] > 0 ? null : current.get();
    volatile boolean running= true;
    Source(FlowOp source, long size){ this.source= source; this.size= size; }
    boolean cancelled(){ return !running || (parent != null && parent.cancelled()); }
    @Override public void step(Sink sink){ source.step(sink); }
    @Override public void stopUp(){ running= false; source.stopUp(); }
    @Override public boolean isRunning(){ return source.isRunning(); }
    @Override public FlowOp split(){ return source.split(); }
    @Override public boolean canSplit(){ return source.canSplit(); }
    @Override public void forAll(Sink down){
      var split= FlowOps.splitTasks(source, Math.max(parallelismPotential / 2, 2));
      int nTasks= split.size();
      if (nTasks != 2 && nTasks <= 3){ seqOnly(split, down); return; }
      manyPar(split, down);
    }
    private static void seqOnly(List<FlowOp> split, Sink down){
      var s= new DelayedStop(down);
      for (var d: split){ d.forAll(s); }
      s.stop();
    }
    private void manyPar(List<FlowOp> split, Sink down){
      int n= split.size();
      var exception= new AtomicReference<Throwable>();
      Thread.UncaughtExceptionHandler handler= (_, err)->exception.compareAndSet(null, err);
      var flusher= Flusher.start(handler);
      var sync= new CountDownLatch(n);
      var spawned= new Thread[n];
      int knownFinished= 0;
      for (int i= 0; i < n; i++){
        var worker= new Worker(split.get(i), new Buffer(down, flusher, this), sync, this);
        if (i == n - 1){ worker.run(); break; }
        if (available.tryAcquire()){
          worker.releaseOnDone= true;
          spawned[i]= Thread.ofVirtual().uncaughtExceptionHandler(handler).start(worker);
          continue;
        }
        for (; knownFinished < i; knownFinished++){
          var t= spawned[knownFinished];
          if (t == null){ continue; }
          join(t);
          spawned[knownFinished]= null;
        }
        ScopedValue.where(sequentialised, Boolean.TRUE).run(worker);
      }
      try{ sync.await(); }
      catch(InterruptedException e){ Thread.currentThread().interrupt(); throw new RuntimeException(e); }
      flusher.stop(down);
      var t= exception.get();
      if (t != null){ throw FlowOps.sneaky(t); }
    }
  }
  static final class Worker implements Runnable{
    final FlowOp source;
    final Buffer down;
    final CountDownLatch sync;
    final Source dp;
    boolean releaseOnDone;
    Worker(FlowOp source, Buffer down, CountDownLatch sync, Source dp){ this.source= source; this.down= down; this.sync= sync; this.dp= dp; }
    @Override public void run(){
      var prev= current.get();
      current.set(dp);
      try{ if (!dp.cancelled()){ source.forAll(down); } }
      catch(Deterministic d){ down.pushError(d.i); }
      catch(Cancelled c){}
      finally{
        current.set(prev);
        down.flush();
        sync.countDown();
        if (releaseOnDone){ available.release(); }
      }
    }
  }
  static final class Buffer implements Sink{
    final Sink original;
    final Source dp;
    final LinkedBlockingQueue<Object> buffer= new LinkedBlockingQueue<>();
    Buffer(Sink original, Flusher flusher, Source dp){ this.original= original; this.dp= dp; flusher.toFlush.add(this); }
    @Override public void accept(Object e){
      if (dp.cancelled()){ throw new Cancelled(); }
      put(buffer, e);
    }
    @Override public void pushError(Object info){ put(buffer, new Err(info)); }
    @Override public void stopDown(){}
    void flush(){ put(buffer, stopToken); }
  }
  static final class Flusher implements Runnable{
    final LinkedBlockingQueue<Object> toFlush= new LinkedBlockingQueue<>();
    Thread thread;
    static Flusher start(Thread.UncaughtExceptionHandler handler){
      var f= new Flusher();
      f.thread= Thread.ofPlatform().daemon(true).uncaughtExceptionHandler(handler).start(f);
      return f;
    }
    void stop(Sink original){
      toFlush.add(stopToken);
      join(thread);
      original.stopDown();
    }
    @Override public void run(){
      while (true){
        var e= take(toFlush);
        if (e == stopToken){ assert toFlush.isEmpty(); return; }
        flush((Buffer)e);
      }
    }
    private static void flush(Buffer b){
      while (true){
        var e= take(b.buffer);
        if (e == stopToken){ return; }
        if (e instanceof Err err){ b.original.pushError(err.info()); continue; }
        try{ b.original.accept(e); }
        catch(Deterministic d){ b.original.pushError(d.i); }
      }
    }
  }
  static final class DelayedStop implements Sink{
    final Sink original;
    DelayedStop(Sink original){ this.original= original; }
    @Override public void accept(Object e){ original.accept(e); }
    @Override public void pushError(Object info){ original.pushError(info); }
    @Override public void stopDown(){}
    void stop(){ original.stopDown(); }
  }
  static final class Converted implements FlowOp{
    final LinkedBlockingDeque<Object> buffer= new LinkedBlockingDeque<>();
    final Source source;
    boolean running= true;
    boolean started;
    Converted(Source source){ this.source= source; }
    private void start(){
      started= true;
      Thread.ofVirtual().start(this::feed);
    }
    private void feed(){
      try{
        source.forAll(new Sink(){
          @Override public void accept(Object e){ buffer.offer(e); }
          @Override public void pushError(Object info){ buffer.offer(new Err(info)); }
          @Override public void stopDown(){ buffer.offer(PipelineParallel.stop); }
        });
      }
      catch(Throwable t){ buffer.offer(new Failure(t)); }
    }
    @Override public void step(Sink sink){
      if (!running){ return; }
      if (!started){ start(); }
      Object msg;
      try{ msg= buffer.take(); }
      catch(InterruptedException e){ Thread.currentThread().interrupt(); throw new RuntimeException(e); }
      if (msg instanceof Failure f){ throw FlowOps.sneaky(f.t()); }
      ((PipelineParallel.Wrapped)sink).subject.submit(msg);
      if (msg == PipelineParallel.stop){ running= false; }
      if (!running){ sink.stopDown(); stopUp(); }
    }
    @Override public void stopUp(){ running= false; source.stopUp(); }
    @Override public boolean isRunning(){ return running; }
  }
}
