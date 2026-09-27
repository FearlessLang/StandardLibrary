package _base;

import java.util.concurrent.ConcurrentHashMap;

public record Cache2(ConcurrentHashMap<Object,Entry> map, long time,CacheF$2p$3 f) implements Cache{
  public Cache2(long time,CacheF$2p$3 f){this(new ConcurrentHashMap<Object,Entry>(),time,f);}
  public Cache2(long time,CacheMemo$lk$3 f){ this(new ConcurrentHashMap<Object,Entry>(),time, new CacheF$2p$3(){public Object imm$$hash$2(Object p0,Object p1){ return new Norm(f.imm$$hash$2(p0,p1));}});}
  public record Key(Object a, Object b){}
  public Entry former(Object k, Entry candidate){ return map.putIfAbsent(k,candidate); }
  public Object get(Object a, Object b){ return get(new Key(a,b),()->f.imm$$hash$2(a,b),time); }
}
