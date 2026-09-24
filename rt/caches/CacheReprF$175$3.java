package _base;
public interface CacheReprF$175$3 extends _base.CacheHandler$4sg$0{
  default Object imm$$hash$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: CacheReprF$175$3.imm$$hash$2"+this.getClass().getName());
  }
  default Object imm$_get$2(Object p0, Object p1){
    var a$= (Repr$o$1)p0;
    var b$= (Norm$o$1)p1;
    return a$._reprCacheGet(new Cache2.Key(this,b$),()->this.imm$$hash$2(a$.read$look$1(new F$3$2(){public Object read$$hash$1(Object x){ return x;}}),b$),1);
  }
}