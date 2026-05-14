package base;
public interface IntRangeChecks$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
base.Int$0Instance.instance(3L).read$assertInRange$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(5L)))).imm$test$1(
base.Int$0Instance.instance(3L).read$assertInRangeOpen$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(5L)))).imm$test$1(
base.Int$0Instance.instance(3L).read$assertInRangeLoOpen$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(3L)))).imm$test$1(
base.Int$0Instance.instance(3L).read$assertInRangeHiOpen$2(
base.Int$0Instance.instance(3L),
base.Int$0Instance.instance(5L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(0L).imm$clamp$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(5L))).read$assertEq$1(
base.Int$0Instance.instance(1L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(6L).imm$clamp$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(5L))).read$assertEq$1(
base.Int$0Instance.instance(5L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(3L).imm$clamp$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(5L))).read$assertEq$1(
base.Int$0Instance.instance(3L)))).imm$test$1(
((base.Int$0)base.Int$0Instance.instance(3L).imm$clamp$2(
base.Int$0Instance.instance(3L),
base.Int$0Instance.instance(3L))).read$assertEq$1(
base.Int$0Instance.instance(3L)));
  }
  IntRangeChecks$0 instance= new IntRangeChecks$0(){};}