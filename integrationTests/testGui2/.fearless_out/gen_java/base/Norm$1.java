package base;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public interface Norm$1 extends base.Sealed$0 {
  default Object imm$get$0(){
    throw new AssertionError("Uncallable method: Norm$1.imm$get$0"+this.getClass().getName());
  }
}
class Norm implements Norm$1{
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
    catch(ExecutionException ex){ return sneakyThrow(ex.getCause()); }
    catch(InterruptedException ex){
      Thread.currentThread().interrupt();
      throw new RuntimeException(ex);
      }
    }
  private Object sequential(Supplier<Object> s){
    var res= s.get();
    if (!ready.isDone()){ return res; }
    try{ return ready.get(); }
    catch(ExecutionException ex){ return sneakyThrow(ex.getCause()); }
    catch(InterruptedException ex){
      Thread.currentThread().interrupt();
      throw new RuntimeException(ex);
    }//return the more normalized if possible
  }
  @SuppressWarnings("unchecked")
  private static <E extends Throwable,T> T sneakyThrow(Throwable t) throws E{ throw (E)t; }
}
interface Cache{
  Entry former(Object k, Entry candidate);
  default Object get(Object k, Supplier<Object> f, long time){
    var fresh= new Entry();
    var e= former(k,fresh);
    if (e == null){ return fresh.computeNow(f); }
    return e.joinWait(time,f);
  }
}
record Cache0(AtomicReference<Entry> entry, long time, CacheF$1 f) implements Cache{
  public Cache0(long time,CacheF$1 f){ this(new AtomicReference<Entry>(),time,f);}
  public Cache0(long time,CacheMemo$1 f){ this(new AtomicReference<Entry>(),time, new CacheF$1(){public Object imm$$hash$0(){ return new Norm(f.imm$$hash$0());}});}
  public Entry former(Object k, Entry candidate){ return entry.compareAndExchange(null,candidate); }
  public Object get(){ return get(null,f::imm$$hash$0,time); }
}
record Cache1(ConcurrentHashMap<Object,Entry> map, long time, CacheF$2 f) implements Cache{
  public Cache1(long time,CacheF$2 f){this(new ConcurrentHashMap<Object,Entry>(),time,f);}
  public Cache1(long time,CacheMemo$2 f){ this(new ConcurrentHashMap<Object,Entry>(),time, new CacheF$2(){public Object imm$$hash$1(Object p0){ return new Norm(f.imm$$hash$1(p0));}});}
  public Cache1(long time,Repr$1 repr){ this(new ConcurrentHashMap<Object,Entry>(),time, new CacheF$2(){public Object imm$$hash$1(Object f){ return ((CacheReprF$2)f).imm$$hash$1(repr.read$look$1(new F$2(){public Object read$$hash$1(Object x){ return x;}}));}});}
  public Entry former(Object k, Entry candidate){ return map.putIfAbsent(k,candidate); }
  public Object get(Object a){ return get(a,()->f.imm$$hash$1(a),time); }
}
record Cache2(ConcurrentHashMap<Object,Entry> map, long time,CacheF$3 f) implements Cache{
  public Cache2(long time,CacheF$3 f){this(new ConcurrentHashMap<Object,Entry>(),time,f);}
  public Cache2(long time,CacheMemo$3 f){ this(new ConcurrentHashMap<Object,Entry>(),time, new CacheF$3(){public Object imm$$hash$2(Object p0,Object p1){ return new Norm(f.imm$$hash$2(p0,p1));}});}
  public Cache2(long time,Repr$1 repr){ this(new ConcurrentHashMap<Object,Entry>(),time, new CacheF$3(){public Object imm$$hash$2(Object f,Object n){ return ((CacheReprF$3)f).imm$$hash$2(repr.read$look$1(new F$2(){public Object read$$hash$1(Object x){ return x;}}),n);}});}
  public record Key(Object a, Object b){}
  public Entry former(Object k, Entry candidate){ return map.putIfAbsent(k,candidate); }
  public Object get(Object a, Object b){ return get(new Key(a,b),()->f.imm$$hash$2(a,b),time); }
}