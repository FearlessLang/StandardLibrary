package base;
public interface ExtensibleViaDataTypeBy$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)_aimpl$.imm$test$1(
((base.Int$0)((base.Int$0)((base.DataType$2)base.IntBy$0.instance.imm$$hash$1(
base.Int$0Instance.instance(3L))).read$imm$0(
)).imm$$tilde$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var n$= (base.Int$0)p0;
    return n$.imm$$plus$1(
base.Int$0Instance.instance(1L));
  }

})).read$assertEq$1(
base.Int$0Instance.instance(4L)))).imm$test$1(
((base.Str$0)((base.Str$0)((base.DataType$2)base.StrBy$0.instance.imm$$hash$1(
base.Str$0Instance.instance("a"))).read$imm$0(
)).imm$$tilde$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var s$= (base.Str$0)p0;
    return s$.imm$$plus$1(
base.Str$0Instance.instance("b"));
  }

})).read$assertEq$1(
base.Str$0Instance.instance("ab")));
  }
  ExtensibleViaDataTypeBy$0 instance= new ExtensibleViaDataTypeBy$0(){};}