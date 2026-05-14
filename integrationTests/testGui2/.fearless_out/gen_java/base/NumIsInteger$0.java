package base;
public interface NumIsInteger$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Bool$0)((base.Num$0)base.Int$0Instance.instance(2L).imm$num$0(
)).imm$isInteger$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Num$0)((base.Num$0)base.Int$0Instance.instance(1L).imm$num$0(
)).imm$$slash$1(
base.Int$0Instance.instance(2L).imm$num$0(
))).imm$isInteger$0(
)).read$assertEq$1(
base.False$0.instance))).imm$test$1(
((base.Bool$0)((base.Num$0)base.Int$0Instance.instance(-2L).imm$num$0(
)).imm$isInteger$0(
)).read$assertEq$1(
base.True$0.instance));
  }
  NumIsInteger$0 instance= new NumIsInteger$0(){};}