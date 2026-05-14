package base;
public interface NatBoundaryAndBoundedVsAlu$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Nat$0)((base.Nat$0)base.Math$0.instance.imm$maxNat$0(
)).imm$aluAddWrap$1(
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Nat$0Instance.instance(0L)))).imm$test$1(
((base.Nat$0)base.Nat$0Instance.instance(0L).imm$aluSubWrap$1(
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Math$0.instance.imm$maxNat$0(
)))).imm$test$1(
((base.Int$0)((base.Nat$0)base.Math$0.instance.imm$maxNat$0(
)).imm$aluInt$0(
)).read$assertEq$1(
base.Int$0Instance.instance(-1L)));
  }
  NatBoundaryAndBoundedVsAlu$0 instance= new NatBoundaryAndBoundedVsAlu$0(){};}