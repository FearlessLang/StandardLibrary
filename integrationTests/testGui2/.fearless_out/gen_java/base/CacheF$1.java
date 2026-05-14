package base;
public interface CacheF$1 extends base.CaptureFree$0{
  default Object imm$$hash$0(){
    throw new AssertionError("Uncallable method: CacheF$1.imm$$hash$0"+this.getClass().getName());
  }
  default Object imm$_get$0(){
    return CacheF$1.myCache.computeIfAbsent(this,v->new Cache0(1,v)).get();
  }
  java.util.concurrent.ConcurrentHashMap<CacheF$1, Cache0> myCache= new java.util.concurrent.ConcurrentHashMap<>();
}