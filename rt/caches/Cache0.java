package _base;

import java.util.concurrent.atomic.AtomicReference;

public record Cache0(AtomicReference<Entry> entry, long time, CacheF$2p$1 f) implements Cache{
  public Cache0(long time, CacheF$2p$1 f){ this(new AtomicReference<Entry>(), time, f);}
  public Cache0(long time, CacheMemo$lk$1 f){ this(new AtomicReference<Entry>(), time, new CacheF$2p$1(){@Override public Object imm$$hash$0(){ return new Norm(f.imm$$hash$0());}});}
  @Override public Entry former(Object k, Entry candidate){ return entry.compareAndExchange(null, candidate); }
  @Override public void forget(Object k, Entry e){ entry.compareAndSet(e, null); }
  public Object get(){ return get(null, f::imm$$hash$0, time); }
}
