package base;
public interface FloatSignNum$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Float$0)base.Float$0Instance.instance(-12.0d).imm$signnum$0(
)).read$assertEq$1(
base.Float$0Instance.instance(-1.0d)))).imm$test$1(
((base.Float$0)base.Float$0Instance.instance(7.0d).imm$signnum$0(
)).read$assertEq$1(
base.Float$0Instance.instance(1.0d)))).imm$test$1(
((base.Bool$0)((base.Float$0)base.Float$0Instance.instance(0.0d).imm$signnum$0(
)).imm$isPosZero$0(
)).imm$assertTrue$0(
))).imm$test$1(
((base.Bool$0)((base.Float$0)base.Float$0Instance.instance(-0.0d).imm$signnum$0(
)).imm$isNegZero$0(
)).imm$assertTrue$0(
))).imm$test$1(
((base.Float$0)((base.Float$0)base.Math$0.instance.imm$inf$0(
)).imm$signnum$0(
)).read$assertEq$1(
base.Float$0Instance.instance(1.0d)))).imm$test$1(
((base.Float$0)((base.Float$0)base.Math$0.instance.imm$negInf$0(
)).imm$signnum$0(
)).read$assertEq$1(
base.Float$0Instance.instance(-1.0d)))).imm$test$1(
((base.Float$0)((base.Float$0)base.Math$0.instance.imm$nan$0(
)).imm$signnum$0(
)).read$assertEq$1(
base.Math$0.instance.imm$nan$0(
)));
  }
  FloatSignNum$0 instance= new FloatSignNum$0(){};}