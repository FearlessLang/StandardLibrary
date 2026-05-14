package base;
public interface IntAlu$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Int$0)((base.Int$0)base.Math$0.instance.imm$maxInt$0(
)).imm$aluAddWrap$1(
base.Int$0Instance.instance(1L))).read$assertEq$1(
base.Math$0.instance.imm$minInt$0(
)))).imm$test$1(
((base.Int$0)((base.Int$0)base.Math$0.instance.imm$minInt$0(
)).imm$aluSubWrap$1(
base.Int$0Instance.instance(1L))).read$assertEq$1(
base.Math$0.instance.imm$maxInt$0(
)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(4294967296L).imm$aluMulWrap$1(
base.Int$0Instance.instance(4294967296L))).read$assertEq$1(
base.Int$0Instance.instance(0L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(-7L).imm$aluDiv$1(
base.Int$0Instance.instance(3L))).read$assertEq$1(
base.Int$0Instance.instance(-2L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(-7L).imm$aluDiv$1(
base.Int$0Instance.instance(-3L))).read$assertEq$1(
base.Int$0Instance.instance(2L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(-7L).imm$aluRem$1(
base.Int$0Instance.instance(3L))).read$assertEq$1(
base.Int$0Instance.instance(-1L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(-7L).imm$aluRem$1(
base.Int$0Instance.instance(-3L))).read$assertEq$1(
base.Int$0Instance.instance(-1L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(1L).imm$aluShiftLeft$1(
base.Math$0.instance.imm$intBits$0(
))).read$assertEq$1(
base.Int$0Instance.instance(1L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(1L).imm$aluShiftLeft$1(
((base.Nat$0)base.Math$0.instance.imm$intBits$0(
)).imm$$plus$1(
base.Nat$0Instance.instance(1L)))).read$assertEq$1(
base.Int$0Instance.instance(2L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(-2L).imm$aluShiftRight$1(
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Int$0Instance.instance(-1L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(-2L).imm$aluShiftRight$1(
((base.Nat$0)base.Math$0.instance.imm$intBits$0(
)).imm$$plus$1(
base.Nat$0Instance.instance(1L)))).read$assertEq$1(
base.Int$0Instance.instance(-1L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(1L).imm$aluXor$1(
base.Int$0Instance.instance(3L))).read$assertEq$1(
base.Int$0Instance.instance(2L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(3L).imm$aluAnd$1(
base.Int$0Instance.instance(6L))).read$assertEq$1(
base.Int$0Instance.instance(2L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(3L).imm$aluOr$1(
base.Int$0Instance.instance(4L))).read$assertEq$1(
base.Int$0Instance.instance(7L)))).imm$test$1(
((base.Int$0)((base.Nat$0)base.Int$0Instance.instance(-1L).imm$aluNat$0(
)).imm$aluInt$0(
)).read$assertEq$1(
base.Int$0Instance.instance(-1L)))).imm$test$1(
((base.Byte$0)base.Int$0Instance.instance(300L).imm$aluByte$0(
)).read$assertEq$1(
base.Int$0Instance.instance(44L).imm$byte$0(
)));
  }
  IntAlu$0 instance= new IntAlu$0(){};}