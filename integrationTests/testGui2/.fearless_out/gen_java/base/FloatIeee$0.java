package base;
public interface FloatIeee$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Bool$0)base.Float$0Instance.instance(0.0d).imm$ieeeEq$1(
base.Float$0Instance.instance(-0.0d))).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)base.Float$0Instance.instance(0.0d).imm$ieeeSameBits$1(
base.Float$0Instance.instance(-0.0d))).read$assertEq$1(
base.False$0.instance))).imm$test$1(
((base.Bool$0)((base.Float$0)base.Math$0.instance.imm$nan$0(
)).imm$ieeeEq$1(
base.Math$0.instance.imm$nan$0(
))).read$assertEq$1(
base.False$0.instance))).imm$test$1(
((base.Float$0)base.Float$0Instance.instance(5.0d).imm$ieeeRemainder$1(
base.Float$0Instance.instance(2.0d))).read$assertEq$1(
base.Float$0Instance.instance(1.0d)))).imm$test$1(
((base.Float$0)base.Float$0Instance.instance(7.0d).imm$ieeeRemainder$1(
base.Float$0Instance.instance(2.0d))).read$assertEq$1(
base.Float$0Instance.instance(-1.0d)))).imm$test$1(
((base.Float$0)base.Float$0Instance.instance(6.0d).imm$ieeeRemainder$1(
base.Float$0Instance.instance(4.0d))).read$assertEq$1(
base.Float$0Instance.instance(-2.0d)))).imm$test$1(
((base.Float$0)base.Float$0Instance.instance(10.0d).imm$ieeeRemainder$1(
base.Float$0Instance.instance(4.0d))).read$assertEq$1(
base.Float$0Instance.instance(2.0d)))).imm$test$1(
((base.Str$0)base.Float$0Instance.instance(0.0d).imm$ieeeStr$0(
)).read$assertEq$1(
base.Str$0Instance.instance("0.0")))).imm$test$1(
((base.Str$0)base.Float$0Instance.instance(-0.0d).imm$ieeeStr$0(
)).read$assertEq$1(
base.Str$0Instance.instance("-0.0")))).imm$test$1(
((base.Str$0)((base.Float$0)base.Math$0.instance.imm$nan$0(
)).imm$ieeeStr$0(
)).read$assertEq$1(
base.Str$0Instance.instance("NaN")))).imm$test$1(
((base.Str$0)((base.Float$0)base.Math$0.instance.imm$inf$0(
)).imm$ieeeStr$0(
)).read$assertEq$1(
base.Str$0Instance.instance("Infinity")))).imm$test$1(
((base.Str$0)((base.Float$0)base.Math$0.instance.imm$negInf$0(
)).imm$ieeeStr$0(
)).read$assertEq$1(
base.Str$0Instance.instance("-Infinity")));
  }
  FloatIeee$0 instance= new FloatIeee$0(){};}