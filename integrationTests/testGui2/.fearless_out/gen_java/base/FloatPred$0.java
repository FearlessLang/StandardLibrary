package base;
public interface FloatPred$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Bool$0)((base.Float$0)base.Math$0.instance.imm$nan$0(
)).imm$isNaN$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Float$0)base.Math$0.instance.imm$inf$0(
)).imm$isInfinite$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Float$0)base.Math$0.instance.imm$inf$0(
)).imm$isPosInfinity$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Float$0)base.Math$0.instance.imm$inf$0(
)).imm$isNegInfinity$0(
)).read$assertEq$1(
base.False$0.instance))).imm$test$1(
((base.Bool$0)((base.Float$0)base.Math$0.instance.imm$negInf$0(
)).imm$isNegInfinity$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)base.Float$0Instance.instance(-0.0d).imm$isNegZero$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)base.Float$0Instance.instance(-0.0d).imm$isPosZero$0(
)).read$assertEq$1(
base.False$0.instance))).imm$test$1(
((base.Bool$0)base.Float$0Instance.instance(0.0d).imm$isPosZero$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)base.Float$0Instance.instance(0.0d).imm$isNegZero$0(
)).read$assertEq$1(
base.False$0.instance))).imm$test$1(
base.Float$0Instance.instance(-0.0d).read$assertEq$1(
base.Float$0Instance.instance(0.0d)))).imm$test$1(
((base.Float$0)base.Math$0.instance.imm$nan$0(
)).read$assertEq$1(
base.Math$0.instance.imm$nan$0(
)))).imm$test$1(
((base.Float$0)base.Math$0.instance.imm$nan$0(
)).read$assertGt$1(
base.Float$0Instance.instance(1.0d)));
  }
  FloatPred$0 instance= new FloatPred$0(){};}