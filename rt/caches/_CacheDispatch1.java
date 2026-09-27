package _base;
interface _CacheDispatch1 extends _base.CacheHandler$4sg$0{
  default Object imm$$hash$1(Object p0){
    throw new AssertionError("Uncallable method: imm$$hash$1 on "+this.getClass().getName());
  }
  default Cache1 _cache1(){
    throw new AssertionError("Uncallable method: _cache1 on "+this.getClass().getName());
  }
  default Object imm$_get$1(Object p0){
    var a$= (_base.Norm$o$1)p0;
    return _cache1().get(a$);
  }
}
