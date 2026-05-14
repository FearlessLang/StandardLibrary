package base;
public interface CacheF$2 extends base.CaptureFree$0{
  default Object imm$$hash$1(Object p0){
    throw new AssertionError("Uncallable method: CacheF$2.imm$$hash$1"+this.getClass().getName());
  }
  default Object imm$_get$1(Object p0){
    var this$= this;
    var a$= (base.Norm$1)p0;
    return CacheF$2.myCache.computeIfAbsent(this,v->new Cache1(1,v)).get(a$);
  }
  java.util.concurrent.ConcurrentHashMap<CacheF$2, Cache1> myCache= new java.util.concurrent.ConcurrentHashMap<>();
}