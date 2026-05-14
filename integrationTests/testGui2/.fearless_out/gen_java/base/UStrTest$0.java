package base;
public interface UStrTest$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return _aimpl$.imm$test$1(
((base.Nat$0)base.Str$0Instance.instance("Hello").imm$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(5L)));
  }
  UStrTest$0 instance= new UStrTest$0(){};}