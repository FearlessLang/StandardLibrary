package base;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public interface Norm$o$1 extends base.Sealed$2o$0 {
  default Object imm$get$0(){
    throw new AssertionError("Uncallable method: Norm$o$1.imm$get$0"+this.getClass().getName());
  }
  default Object read$as$1(Object p0){ return this; }
}
class Norm implements Norm$o$1{
  final Object get;//fearless will guarantee that T is always deeply immutable
  Norm(Object get){ this.get= get; }
  public Object imm$get$0(){return get; }
}
class Entry{
  final CompletableFuture<Object> ready= new CompletableFuture<>();
  volatile boolean broken=false;
  public Object computeNow(Supplier<Object> s){
    try{ var res= s.get(); ready.complete(res); return res; }
    catch(Throwable t){ ready.completeExceptionally(t); throw t; }//Compiles in Java 24
  }
  public Object joinWait(long time, Supplier<Object> s){
    if (!ready.isDone() && broken){ return sequential(s); }
    try{ return ready.get(time,TimeUnit.SECONDS); }
    catch(TimeoutException _){
      broken=true;
      return sequential(s);
    }
    catch(ExecutionException ex){ return cancelledOr(ex, () -> sequential(s)); }
    catch(InterruptedException ex){
      Thread.currentThread().interrupt();
      throw new RuntimeException(ex);
      }
    }
  private Object sequential(Supplier<Object> s){
    var res= s.get();
    if (!ready.isDone()){ return res; }
    try{ return ready.get(); }
    catch(ExecutionException ex){ return cancelledOr(ex, () -> res); }
    catch(InterruptedException ex){
      Thread.currentThread().interrupt();
      throw new RuntimeException(ex);
    }//return the more normalized if possible
  }
  private static Object cancelledOr(ExecutionException ex, Supplier<Object> alt){
    if (ex.getCause() instanceof Cancelled){ return alt.get(); }
    return sneakyThrow(ex.getCause());
  }
  @SuppressWarnings("unchecked")
  private static <E extends Throwable,T> T sneakyThrow(Throwable t) throws E{ throw (E)t; }
}
interface Cache{
  Entry former(Object k, Entry candidate);
  void evict(Object k, Entry e);
  default Object get(Object k, Supplier<Object> f, long time){
    var fresh= new Entry();
    var e= former(k,fresh);
    if (e != null){ return e.joinWait(time,f); }
    try{ return fresh.computeNow(f); }
    catch(Cancelled c){ evict(k,fresh); throw c; }
  }
}
