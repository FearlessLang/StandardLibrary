package _base;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public record Cache0(AtomicReference<Entry> entry, long time, CacheF$2p$1 f) implements Cache{
  static final ConcurrentLinkedQueue<Cache0> onces= new ConcurrentLinkedQueue<>();
  public Cache0(long time, CacheF$2p$1 f){ this(new AtomicReference<Entry>(), time, f);}
  public Cache0(long time, CacheMemo$lk$1 f){ this(new AtomicReference<Entry>(), time, new CacheF$2p$1(){@Override public Object imm$$hash$0(){ return new Norm(f.imm$$hash$0());}});}
  public static Cache0 once(Supplier<Object> f){
    var res= new Cache0(1, new CacheF$2p$1(){@Override public Object imm$$hash$0(){ return f.get();}});
    onces.add(res);
    return res;
  }
  @Override public Entry former(Object k, Entry candidate){ return entry.compareAndExchange(null, candidate); }
  public Object get(){ return get(null, f::imm$$hash$0, time); }
}
