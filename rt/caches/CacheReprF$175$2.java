package _base;
public interface CacheReprF$175$2 extends _base.CacheHandler$4sg$0{
  default Object imm$$hash$1(Object p0){
    throw new AssertionError("Uncallable method: CacheReprF$175$2.imm$$hash$1"+this.getClass().getName());
  }
  default Object imm$_get$1(Object p0){
    var a$= (Repr$o$1)p0;
    return a$._reprCacheGet(this,()->this.imm$$hash$1(a$.read$look$1(new F$3$2(){public Object read$$hash$1(Object x){ return x;}})),1);
  }
}