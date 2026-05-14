package base;
public interface CacheF$3 extends base.CaptureFree$0{
  default Object imm$$hash$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: CacheF$3.imm$$hash$2"+this.getClass().getName());
  }
  default Object imm$_get$2(Object p0, Object p1){
    var this$= this;
    var a$= (base.Norm$1)p0;
    var b$= (base.Norm$1)p1;
    return CacheF$3.myCache.computeIfAbsent(this,v->new Cache2(1,v)).get(a$,b$);
  }
  java.util.concurrent.ConcurrentHashMap<CacheF$3, Cache2> myCache= new java.util.concurrent.ConcurrentHashMap<>();
}