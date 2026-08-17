package base;
public interface CacheReprF$175$2 extends base.CacheHandler$4sg$0{
  default Object imm$$hash$1(Object p0){
    throw new AssertionError("Uncallable method: CacheReprF$175$2.imm$$hash$1"+this.getClass().getName());
  }
  default Object imm$_get$1(Object p0){
    var a$= (Repr$o$1)p0;
    return CacheReprF$175$2.myCache.computeIfAbsent(a$,v->new Cache1(1,v)).get(this);
  }
  java.util.concurrent.ConcurrentHashMap<Repr$o$1, Cache1> myCache= new java.util.concurrent.ConcurrentHashMap<>();
}