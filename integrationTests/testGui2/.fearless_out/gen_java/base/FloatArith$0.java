package base;
public interface FloatArith$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Float$0)base.Float$0Instance.instance(1.0d).imm$$plus$1(
base.Float$0Instance.instance(2.0d))).read$assertEq$1(
base.Float$0Instance.instance(3.0d)))).imm$test$1(
((base.Float$0)base.Float$0Instance.instance(5.0d).imm$$dash$1(
base.Float$0Instance.instance(2.0d))).read$assertEq$1(
base.Float$0Instance.instance(3.0d)))).imm$test$1(
((base.Float$0)base.Float$0Instance.instance(3.0d).imm$$star$1(
base.Float$0Instance.instance(4.0d))).read$assertEq$1(
base.Float$0Instance.instance(12.0d)))).imm$test$1(
((base.Float$0)base.Float$0Instance.instance(7.0d).imm$$slash$1(
base.Float$0Instance.instance(2.0d))).read$assertEq$1(
base.Float$0Instance.instance(3.5d)))).imm$test$1(
((base.Float$0)base.Float$0Instance.instance(-3.0d).imm$abs$0(
)).read$assertEq$1(
base.Float$0Instance.instance(3.0d)))).imm$test$1(
((base.Float$0)base.Float$0Instance.instance(9.0d).imm$sqrt$0(
)).read$assertEq$1(
base.Float$0Instance.instance(3.0d)))).imm$test$1(
((base.Bool$0)((base.Float$0)base.Float$0Instance.instance(-1.0d).imm$sqrt$0(
)).imm$isNaN$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Float$0)base.Float$0Instance.instance(2.0d).imm$$star_star$1(
base.Float$0Instance.instance(3.0d))).read$assertEq$1(
base.Float$0Instance.instance(8.0d)))).imm$test$1(
base.Float$0Instance.instance(1.0d).imm$assertEqDelta$2(
base.Float$0Instance.instance(1.0d),
base.Float$0Instance.instance(0.0d)));
  }
  FloatArith$0 instance= new FloatArith$0(){};}