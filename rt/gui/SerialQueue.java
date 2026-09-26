package _base;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

record SerialQueue(BlockingQueue<MF$7$1> q, AtomicBoolean midTask){
  public SerialQueue(Consumer<Throwable> onError) {
    this(new LinkedBlockingQueue<>(), new AtomicBoolean());
    var t= new Thread(() ->runLoop(q, midTask),"GuiToMain");
    t.setUncaughtExceptionHandler((_, e) ->{
      if (e instanceof Poison){ return; }
      onError.accept(e);
      });
    t.start();
  }
  public void submit(MF$7$1 r){ synchronized (q){q.add(r);} } //synchronized needed to cooperate with the closeThen lock; q.add is synchronizing on another lock internally

  public void closeThen(Runnable then){//synchronized needed to make sure poison is on top
    synchronized (q){
      q.clear();
      q.add(new MF$7$1(){ public Object mut$$hash$0(){
        then.run();
        throw new Poison();
      }});//Correctly still accepting tasks, they will be ignored. Explicitly modelling 'ended' status not needed
    }
  }
  private static void runLoop(BlockingQueue<MF$7$1> q, AtomicBoolean midTask) {
    while(true){
      try { q.take().mut$$hash$0();  } 
      catch (InterruptedException e){ Thread.currentThread().interrupt(); throw new Error(e); }
      finally { midTask.set(false); }
    }
  }
  private static class Poison extends RuntimeException{ private static final long serialVersionUID = 1L; }
}