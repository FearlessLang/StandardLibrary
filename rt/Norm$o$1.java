package _base;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public interface Norm$o$1 extends _base.Sealed$2o$0{
  default Object imm$get$0(){
    throw new AssertionError("Uncallable method: Norm$o$1.imm$get$0 on "+this.getClass().getName());
  }
  default Object read$as$1(Object p0){ return this; }
  default Object imm$as$1(Object p0){ return this; }
}
final class Norm implements Norm$o$1{
  final Object get;//fearless will guarantee that T is always deeply immutable
  Norm(Object get){ this.get= get; }
  @Override public Object imm$get$0(){return get; }
}
final class Entry{
  final CompletableFuture<Object> ready= new CompletableFuture<>();
  volatile boolean broken= false;
  public Object computeNow(Supplier<Object> s, Runnable forget){
    try{ var res= s.get(); ready.complete(res); return res; }
    catch (Deterministic d){ ready.completeExceptionally(d); throw d; }
    catch (Throwable t){ forget.run(); ready.completeExceptionally(t); throw t; }//Compiles in Java 24
  }
  public Object joinWait(long time, Supplier<Object> s){
    if (!ready.isDone() && broken){ return sequential(s); }
    try{ return ready.get(time, TimeUnit.SECONDS); }
    catch (TimeoutException _){
      broken = true;
      return sequential(s);
    }
    catch (ExecutionException ex){
      if (ex.getCause() instanceof Deterministic d){ throw d; }
      return s.get();
    }
    catch (InterruptedException ex){
      Thread.currentThread().interrupt();
      throw new RuntimeException(ex);
      }
    }
  private Object sequential(Supplier<Object> s){
    var res= s.get();
    if (ready.state() != Future.State.SUCCESS){ return res; }
    return ready.resultNow();//return the more normalized if possible
  }
}
interface Cache{
  Entry former(Object k, Entry candidate);
  void forget(Object k, Entry e);
  default Object get(Object k, Supplier<Object> f, long time){
    var fresh= new Entry();
    var e= former(k, fresh);
    if (e == null){ return fresh.computeNow(f, () -> forget(k, fresh)); }
    return e.joinWait(time, f);
  }
}
