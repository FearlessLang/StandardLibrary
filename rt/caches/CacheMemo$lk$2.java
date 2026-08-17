package base;
public interface CacheMemo$lk$2 extends base.CacheHandler$4sg$0{
  default Object imm$$hash$1(Object p0){
    throw new AssertionError("Uncallable method: CacheMemo$lk$2.imm$$hash$1"+this.getClass().getName());
  }
  default Object imm$_get$1(Object p0){
    var this$= this;
    var a$= (base.Norm$o$1)p0;
    return CacheMemo$lk$2.myCache.computeIfAbsent(this,v->new Cache1(1,v)).get(a$);
  }
  java.util.concurrent.ConcurrentHashMap<CacheMemo$lk$2, Cache1> myCache= new java.util.concurrent.ConcurrentHashMap<>();
}