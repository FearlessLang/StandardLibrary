package base;
public interface CacheMemo$lk$1 extends base.CacheHandler$4sg$0{
  default Object imm$$hash$0(){
    throw new AssertionError("Uncallable method: CacheMemo$lk$1.imm$$hash$0"+this.getClass().getName());
  }
  default Object imm$_get$0(){
    return CacheMemo$lk$1.myCache.computeIfAbsent(this,v->new Cache0(1,v)).get();
  }
  java.util.concurrent.ConcurrentHashMap<CacheMemo$lk$1, Cache0> myCache= new java.util.concurrent.ConcurrentHashMap<>();
}