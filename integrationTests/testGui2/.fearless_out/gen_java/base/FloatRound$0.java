package base;
public interface FloatRound$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Int$0)base.Float$0Instance.instance(0.5d).imm$round$0(
)).read$assertEq$1(
base.Int$0Instance.instance(0L)))).imm$test$1(
((base.Int$0)base.Float$0Instance.instance(1.5d).imm$round$0(
)).read$assertEq$1(
base.Int$0Instance.instance(2L)))).imm$test$1(
((base.Int$0)base.Float$0Instance.instance(2.5d).imm$round$0(
)).read$assertEq$1(
base.Int$0Instance.instance(2L)))).imm$test$1(
((base.Int$0)base.Float$0Instance.instance(-0.5d).imm$round$0(
)).read$assertEq$1(
base.Int$0Instance.instance(0L)))).imm$test$1(
((base.Int$0)base.Float$0Instance.instance(-1.5d).imm$round$0(
)).read$assertEq$1(
base.Int$0Instance.instance(-2L)))).imm$test$1(
((base.Int$0)base.Float$0Instance.instance(-2.5d).imm$round$0(
)).read$assertEq$1(
base.Int$0Instance.instance(-2L)))).imm$test$1(
((base.Int$0)((base.Float$0)base.Float$0Instance.instance(1.5d).imm$$dash$1(
base.Float$0Instance.instance(1.0d).imm$$slash$1(
base.Float$0Instance.instance(64.0d)))).imm$round$0(
)).read$assertEq$1(
base.Int$0Instance.instance(1L)))).imm$test$1(
((base.Int$0)((base.Float$0)base.Float$0Instance.instance(1.5d).imm$$plus$1(
base.Float$0Instance.instance(1.0d).imm$$slash$1(
base.Float$0Instance.instance(64.0d)))).imm$round$0(
)).read$assertEq$1(
base.Int$0Instance.instance(2L)))).imm$test$1(
((base.Int$0)((base.Float$0)base.Float$0Instance.instance(-1.5d).imm$$dash$1(
base.Float$0Instance.instance(1.0d).imm$$slash$1(
base.Float$0Instance.instance(64.0d)))).imm$round$0(
)).read$assertEq$1(
base.Int$0Instance.instance(-2L)))).imm$test$1(
((base.Int$0)base.Float$0Instance.instance(1.5d).imm$floor$0(
)).read$assertEq$1(
base.Int$0Instance.instance(1L)))).imm$test$1(
((base.Int$0)base.Float$0Instance.instance(1.5d).imm$ceil$0(
)).read$assertEq$1(
base.Int$0Instance.instance(2L)))).imm$test$1(
((base.Int$0)base.Float$0Instance.instance(1.5d).imm$trunc0$0(
)).read$assertEq$1(
base.Int$0Instance.instance(1L)))).imm$test$1(
((base.Int$0)base.Float$0Instance.instance(-1.5d).imm$floor$0(
)).read$assertEq$1(
base.Int$0Instance.instance(-2L)))).imm$test$1(
((base.Int$0)base.Float$0Instance.instance(-1.5d).imm$ceil$0(
)).read$assertEq$1(
base.Int$0Instance.instance(-1L)))).imm$test$1(
((base.Int$0)base.Float$0Instance.instance(-1.5d).imm$trunc0$0(
)).read$assertEq$1(
base.Int$0Instance.instance(-1L)))).imm$test$1(
((base.Int$0)((base.Float$0)base.Math$0.instance.imm$nan$0(
)).imm$round$0(
)).read$assertEq$1(
base.Int$0Instance.instance(0L)))).imm$test$1(
((base.Int$0)((base.Float$0)base.Float$0Instance.instance(1.0d).imm$$slash$1(
base.Float$0Instance.instance(0.0d))).imm$round$0(
)).read$assertEq$1(
base.Math$0.instance.imm$maxInt$0(
)))).imm$test$1(
((base.Int$0)((base.Float$0)base.Float$0Instance.instance(-1.0d).imm$$slash$1(
base.Float$0Instance.instance(0.0d))).imm$round$0(
)).read$assertEq$1(
base.Math$0.instance.imm$minInt$0(
)));
  }
  FloatRound$0 instance= new FloatRound$0(){};}