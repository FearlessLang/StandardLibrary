package base;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.UnaryOperator;

import base.FlowOps.Err;
import base.FlowOps.FlowOp;
import base.FlowOps.Sink;

final class PipelineParallel{
  private PipelineParallel(){}
  static final Object stop= new Object();
  static final UnaryOperator<Sink> deco= Wrapped::new;
  static final class Wrapped implements Sink{
    final Sink original;
    final Subject subject;
    Wrapped(Sink original){ this.original= original; subject= new Subject(original); }
    @Override public void accept(Object e){ subject.submit(e); }
    @Override public void pushError(Object info){ subject.submit(new Err(info)); }
    @Override public void stopDown(){ subject.submit(stop); subject.join(); }
    void softClose(){ subject.softClosed= true; }
  }
  static final class Subject implements Runnable{
    final Sink downstream;
    final ArrayBlockingQueue<Object> buffer= new ArrayBlockingQueue<>(512);
    final Thread worker;
    volatile boolean softClosed;
    volatile Throwable exception;
    volatile CompletableFuture<Void> onEmpty;
    Subject(Sink downstream){
      this.downstream= downstream;
      worker= Thread.ofVirtual().uncaughtExceptionHandler((_, e)->exception= e).start(this);
    }
    void submit(Object msg){
      while (true){
        if (softClosed && msg != stop){ return; }
        if (buffer.offer(msg)){ return; }
        onEmpty= new CompletableFuture<>();
        try{ onEmpty.orTimeout(50, TimeUnit.MILLISECONDS).join(); }
        catch(CompletionException e){ if (!(e.getCause() instanceof TimeoutException)){ throw e; } }
      }
    }
    @Override public void run(){
      while (true){
        Object msg;
        var oe= onEmpty;
        if (oe != null && !oe.isDone()){
          msg= buffer.poll();
          if (msg == null){ oe.complete(null); continue; }
        }
        else{
          try{ msg= buffer.take(); }
          catch(InterruptedException e){ Thread.currentThread().interrupt(); throw new RuntimeException(e); }
        }
        if (msg == stop){ downstream.stopDown(); return; }
        if (msg instanceof Err err){ processError(err); continue; }
        processData(msg);
      }
    }
    void join(){
      DataParallel.join(worker);
      downstream.stopDown();
      if (exception != null){ throw FlowOps.sneaky(exception); }
    }
    private void processError(Err err){
      if (softClosed){ return; }
      softClosed= true;
      try{ downstream.pushError(err.info()); }
      catch(Deterministic d){ throw new FlowError(d.i); }
    }
    private void processData(Object data){
      if (softClosed){ return; }
      try{ downstream.accept(data); }
      catch(Deterministic d){
        softClosed= true;
        try{ downstream.pushError(d.i); }
        catch(Deterministic d1){ throw new FlowError(d1.i); }
      }
    }
  }
  static final class Safe implements FlowOp{
    final FlowOp original;
    Wrapped sink;
    Safe(FlowOp original){ this.original= original; }
    @Override public void step(Sink s){
      if (s instanceof Wrapped w){ sink= w; }
      try{ original.step(s); }
      catch(FlowError e){ throw e; }
      catch(Deterministic d){ s.pushError(d.i); }
    }
    @Override public void forAll(Sink s){
      if (s instanceof Wrapped w){ sink= w; }
      try{ original.forAll(s); }
      catch(FlowError e){ throw e; }
      catch(Deterministic d){ s.pushError(d.i); }
    }
    @Override public void stopUp(){
      if (sink != null){ sink.softClose(); }
      original.stopUp();
    }
    @Override public boolean isRunning(){ return original.isRunning(); }
    @Override public FlowOp split(){ return original.split(); }
    @Override public boolean canSplit(){ return original.canSplit(); }
  }
}
