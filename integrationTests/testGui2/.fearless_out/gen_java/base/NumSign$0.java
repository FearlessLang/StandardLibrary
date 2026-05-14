package base;
public interface NumSign$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Num$0)((base.Num$0)base.Int$0Instance.instance(-12L).imm$num$0(
)).imm$sign$0(
)).read$assertEq$1(
base.Int$0Instance.instance(-1L).imm$num$0(
)))).imm$test$1(
((base.Num$0)((base.Num$0)base.Int$0Instance.instance(7L).imm$num$0(
)).imm$sign$0(
)).read$assertEq$1(
base.Int$0Instance.instance(1L).imm$num$0(
)))).imm$test$1(
((base.Num$0)((base.Num$0)base.Nat$0Instance.instance(0L).imm$num$0(
)).imm$sign$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(0L).imm$num$0(
)));
  }
  NumSign$0 instance= new NumSign$0(){};}