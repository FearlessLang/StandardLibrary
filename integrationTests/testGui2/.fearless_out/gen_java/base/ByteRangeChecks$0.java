package base;
public interface ByteRangeChecks$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Byte$0)base.Int$0Instance.instance(3L).imm$byte$0(
)).read$assertInRange$2(
base.Int$0Instance.instance(1L).imm$byte$0(
),
base.Int$0Instance.instance(5L).imm$byte$0(
)))).imm$test$1(
((base.Byte$0)base.Int$0Instance.instance(3L).imm$byte$0(
)).read$assertInRangeOpen$2(
base.Int$0Instance.instance(1L).imm$byte$0(
),
base.Int$0Instance.instance(5L).imm$byte$0(
)))).imm$test$1(
((base.Byte$0)base.Int$0Instance.instance(3L).imm$byte$0(
)).read$assertInRangeLoOpen$2(
base.Int$0Instance.instance(1L).imm$byte$0(
),
base.Int$0Instance.instance(3L).imm$byte$0(
)))).imm$test$1(
((base.Byte$0)base.Int$0Instance.instance(3L).imm$byte$0(
)).read$assertInRangeHiOpen$2(
base.Int$0Instance.instance(3L).imm$byte$0(
),
base.Int$0Instance.instance(5L).imm$byte$0(
)))).imm$test$1(
((base.Byte$0)((base.Byte$0)base.Int$0Instance.instance(0L).imm$byte$0(
)).imm$clamp$2(
base.Int$0Instance.instance(1L).imm$byte$0(
),
base.Int$0Instance.instance(5L).imm$byte$0(
))).read$assertEq$1(
base.Int$0Instance.instance(1L).imm$byte$0(
)))).imm$test$1(
((base.Byte$0)((base.Byte$0)base.Int$0Instance.instance(6L).imm$byte$0(
)).imm$clamp$2(
base.Int$0Instance.instance(1L).imm$byte$0(
),
base.Int$0Instance.instance(5L).imm$byte$0(
))).read$assertEq$1(
base.Int$0Instance.instance(5L).imm$byte$0(
)))).imm$test$1(
((base.Byte$0)((base.Byte$0)base.Int$0Instance.instance(3L).imm$byte$0(
)).imm$clamp$2(
base.Int$0Instance.instance(1L).imm$byte$0(
),
base.Int$0Instance.instance(5L).imm$byte$0(
))).read$assertEq$1(
base.Int$0Instance.instance(3L).imm$byte$0(
)))).imm$test$1(
((base.Byte$0)((base.Byte$0)base.Int$0Instance.instance(3L).imm$byte$0(
)).imm$clamp$2(
base.Int$0Instance.instance(3L).imm$byte$0(
),
base.Int$0Instance.instance(3L).imm$byte$0(
))).read$assertEq$1(
base.Int$0Instance.instance(3L).imm$byte$0(
)));
  }
  ByteRangeChecks$0 instance= new ByteRangeChecks$0(){};}