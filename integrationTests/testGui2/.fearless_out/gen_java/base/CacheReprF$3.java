package base;
public interface CacheReprF$3 extends base.CaptureFree$0{
  default Object imm$$hash$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: CacheReprF$3.imm$$hash$2"+this.getClass().getName());
  }
  default Object imm$_get$2(Object p0, Object p1){
    var a$= (Repr$1)p0;
    var b$= (Norm$1)p1;
    return CacheReprF$3.myCache.computeIfAbsent(a$,v->new Cache2(1,v)).get(this,b$);
  }
  java.util.concurrent.ConcurrentHashMap<Repr$1, Cache2> myCache= new java.util.concurrent.ConcurrentHashMap<>();
}