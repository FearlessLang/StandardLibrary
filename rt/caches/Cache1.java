package base;

import java.util.concurrent.ConcurrentHashMap;

public record Cache1(ConcurrentHashMap<Object,Entry> map, long time, CacheF$2p$2 f) implements Cache{
  public Cache1(long time,CacheF$2p$2 f){this(new ConcurrentHashMap<Object,Entry>(),time,f);}
  public Cache1(long time,CacheMemo$lk$2 f){ this(new ConcurrentHashMap<Object,Entry>(),time, new CacheF$2p$2(){public Object imm$$hash$1(Object p0){ return new Norm(f.imm$$hash$1(p0));}});}
  public Entry former(Object k, Entry candidate){ return map.putIfAbsent(k,candidate); }
  public void evict(Object k, Entry e){ map.remove(k,e); }
  public Object get(Object a){ return get(a,()->f.imm$$hash$1(a),time); }
}
