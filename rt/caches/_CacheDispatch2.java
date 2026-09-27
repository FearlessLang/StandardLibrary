package _base;
interface _CacheDispatch2 extends _base.CacheHandler$4sg$0{
  default Object imm$$hash$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: imm$$hash$2 on "+this.getClass().getName());
  }
  default Cache2 _cache2(){
    throw new AssertionError("Uncallable method: _cache2 on "+this.getClass().getName());
  }
  default Object imm$_get$2(Object p0, Object p1){
    var a$= (_base.Norm$o$1)p0;
    var b$= (_base.Norm$o$1)p1;
    return _cache2().get(a$,b$);
  }
}
