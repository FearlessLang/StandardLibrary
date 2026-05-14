package base;
public interface NumRationalTokenizationWitness$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return _aimpl$.imm$test$1(
((base.Num$0)((base.Num$0)base.Int$0Instance.instance(3L).imm$$slash$1(
base.Nat$0Instance.instance(2L))).imm$$dash$1(
((base.Int$0)base.Int$0Instance.instance(3L).imm$div$1(
base.Nat$0Instance.instance(2L))).imm$num$0(
))).read$assertEq$1(
base.Int$0Instance.instance(1L).imm$$slash$1(
base.Nat$0Instance.instance(2L))));
  }
  NumRationalTokenizationWitness$0 instance= new NumRationalTokenizationWitness$0(){};}