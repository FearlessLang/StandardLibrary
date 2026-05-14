package base;
public interface NumArith$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Num$0)((base.Num$0)base.Int$0Instance.instance(1L).imm$num$0(
)).imm$$plus$1(
base.Int$0Instance.instance(2L).imm$num$0(
))).read$assertEq$1(
base.Int$0Instance.instance(3L).imm$num$0(
)))).imm$test$1(
((base.Num$0)((base.Num$0)base.Int$0Instance.instance(5L).imm$num$0(
)).imm$$dash$1(
base.Int$0Instance.instance(2L).imm$num$0(
))).read$assertEq$1(
base.Int$0Instance.instance(3L).imm$num$0(
)))).imm$test$1(
((base.Num$0)((base.Num$0)base.Int$0Instance.instance(3L).imm$num$0(
)).imm$$star$1(
base.Int$0Instance.instance(4L).imm$num$0(
))).read$assertEq$1(
base.Int$0Instance.instance(12L).imm$num$0(
)))).imm$test$1(
((base.Num$0)((base.Num$0)base.Int$0Instance.instance(6L).imm$num$0(
)).imm$$slash$1(
base.Int$0Instance.instance(3L).imm$num$0(
))).read$assertEq$1(
base.Int$0Instance.instance(2L).imm$num$0(
)))).imm$test$1(
((base.Num$0)((base.Num$0)base.Int$0Instance.instance(-5L).imm$num$0(
)).imm$abs$0(
)).read$assertEq$1(
base.Int$0Instance.instance(5L).imm$num$0(
)))).imm$test$1(
((base.Num$0)((base.Num$0)base.Int$0Instance.instance(5L).imm$num$0(
)).imm$abs$0(
)).read$assertEq$1(
base.Int$0Instance.instance(5L).imm$num$0(
)))).imm$test$1(
((base.Float$0)((base.Num$0)base.Int$0Instance.instance(9L).imm$num$0(
)).imm$sqrt$0(
)).read$assertEq$1(
base.Float$0Instance.instance(3.0d)))).imm$test$1(
((base.Bool$0)((base.Float$0)((base.Num$0)base.Int$0Instance.instance(-1L).imm$num$0(
)).imm$sqrt$0(
)).imm$isNaN$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Num$0)base.Int$0Instance.instance(1L).imm$num$0(
)).imm$assertEqDelta$2(
base.Int$0Instance.instance(1L).imm$num$0(
),
base.Int$0Instance.instance(0L).imm$num$0(
)))).imm$test$1(
((base.Num$0)((base.Num$0)base.Int$0Instance.instance(1L).imm$$slash$1(
base.Nat$0Instance.instance(2L))).imm$$star_star$1(
base.Int$0Instance.instance(-2L))).read$assertEq$1(
base.Int$0Instance.instance(4L).imm$num$0(
)))).imm$test$1(
((base.Num$0)((base.Num$0)base.Int$0Instance.instance(-4L).imm$$slash$1(
base.Nat$0Instance.instance(3L))).imm$$star_star$1(
base.Int$0Instance.instance(3L))).read$assertEq$1(
base.Int$0Instance.instance(-64L).imm$$slash$1(
base.Nat$0Instance.instance(27L))));
  }
  NumArith$0 instance= new NumArith$0(){};}