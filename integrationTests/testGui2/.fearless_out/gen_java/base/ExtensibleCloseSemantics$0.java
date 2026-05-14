package base;
public interface ExtensibleCloseSemantics$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)_aimpl$.imm$test$1(
((base.Int$0)base.Weird$0.instance.imm$$tilde$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var n$= (base.Int$0)p0;
    return n$.imm$$plus$1(
base.Int$0Instance.instance(1L));
  }

})).read$assertEq$1(
base.Int$0Instance.instance(8L)))).imm$test$1(
((base.Int$0)((base.List$1)((base.Flow$1)base.Weird$0.instance.imm$$tilde$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var n$= (base.Int$0)p0;
    return base.Flows$0.instance.imm$$hash$2(
n$,
n$.imm$$plus$1(
base.Int$0Instance.instance(1L)));
  }

})).mut$list$0(
)).mut$get$1(
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Int$0Instance.instance(8L)));
  }
  ExtensibleCloseSemantics$0 instance= new ExtensibleCloseSemantics$0(){};}