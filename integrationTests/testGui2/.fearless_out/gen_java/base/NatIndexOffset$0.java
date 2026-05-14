package base;
public interface NatIndexOffset$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)_aimpl$.imm$test$1(
((base.Nat$0)base.Nat$0Instance.instance(5L).imm$indexOffset$1(
base.Int$0Instance.instance(-2L))).read$assertEq$1(
base.Nat$0Instance.instance(3L)))).imm$test$1(
((base.Nat$0)base.Nat$0Instance.instance(5L).imm$indexOffset$1(
base.Int$0Instance.instance(2L))).read$assertEq$1(
base.Nat$0Instance.instance(7L)));
  }
  NatIndexOffset$0 instance= new NatIndexOffset$0(){};}