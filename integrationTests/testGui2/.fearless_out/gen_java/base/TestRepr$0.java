package base;
public interface TestRepr$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return _aimpl$.imm$test$1(
((base.Str$0)((base.Range$0)base.Ranges$0.instance.read$$hash$2(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(2L))).read$str$0(
)).read$assertEq$1(
base.Str$0Instance.instance("[1-2]")));
  }
  TestRepr$0 instance= new TestRepr$0(){};}