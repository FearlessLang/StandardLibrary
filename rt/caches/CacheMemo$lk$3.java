package base;
public interface CacheMemo$lk$3 extends base.CacheHandler$4sg$0{
  default Object imm$$hash$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: CacheMemo$lk$3.imm$$hash$2"+this.getClass().getName());
  }
  default Object imm$_get$2(Object p0, Object p1){
    var this$= this;
    var a$= (base.Norm$o$1)p0;
    var b$= (base.Norm$o$1)p1;
    return CacheMemo$lk$3.myCache.computeIfAbsent(this,v->new Cache2(1,v)).get(a$,b$);
  }
  java.util.concurrent.ConcurrentHashMap<CacheMemo$lk$3, Cache2> myCache= new java.util.concurrent.ConcurrentHashMap<>();
}