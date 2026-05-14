package base;
public interface ExtensibleBasics$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Int$0)base.Int$0Instance.instance(3L).imm$$tilde$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var x$= (base.Int$0)p0;
    return x$.imm$$star$1(
base.Int$0Instance.instance(2L));
  }

})).read$assertEq$1(
base.Int$0Instance.instance(6L)))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("a").imm$$tilde$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var s$= (base.Str$0)p0;
    return s$.imm$$plus$1(
base.Str$0Instance.instance("b"));
  }

})).read$assertEq$1(
base.Str$0Instance.instance("ab")))).imm$test$1(
((base.Nat$0)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(2L))).mut$$tilde$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var xs$= (base.List$1)p0;
    return xs$.read$size$0(
);
  }

})).read$assertEq$1(
base.Nat$0Instance.instance(2L)))).imm$test$1(
((base.Str$0)((base.Map$2)base.Maps$0.instance.imm$$hash$3(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.Str$0)p0;
    return _bimpl$;
  }

},
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("x"))).mut$$tilde$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var _cimpl$= (base.Map$2)p0;
    return _cimpl$.mut$get$1(
base.Str$0Instance.instance("a"));
  }

})).read$assertEq$1(
base.Str$0Instance.instance("x")))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.Opts$0.instance.imm$$hash$1(
base.Int$0Instance.instance(1L))).mut$$tilde$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var o$= (base.Opt$1)p0;
    return o$.read$isSome$0(
);
  }

})).read$assertEq$1(
base.True$0.instance));
  }
  ExtensibleBasics$0 instance= new ExtensibleBasics$0(){};}