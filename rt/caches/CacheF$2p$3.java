package base;
public interface CacheF$2p$3 extends base.CacheHandler$4sg$0{
  default Object imm$$hash$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: CacheF$2p$3.imm$$hash$2"+this.getClass().getName());
  }
  default Object imm$_get$2(Object p0, Object p1){
    var this$= this;
    var a$= (base.Norm$o$1)p0;
    var b$= (base.Norm$o$1)p1;
    return CacheF$2p$3.myCache.computeIfAbsent(this,v->new Cache2(1,v)).get(a$,b$);
  }
  java.util.concurrent.ConcurrentHashMap<CacheF$2p$3, Cache2> myCache= new java.util.concurrent.ConcurrentHashMap<>();
}