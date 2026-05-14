package base;
public interface StrConcat$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Str$0)base.Str$0Instance.instance("a").imm$$plus$1(
base.Str$0Instance.instance("b"))).read$assertEq$1(
base.Str$0Instance.instance("ab")))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("ab").imm$$plus$1(
base.Str$0Instance.instance("c"))).read$assertEq$1(
base.Str$0Instance.instance("abc")))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("a").imm$$plus$1(
base.Str$0Instance.instance(""))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("").imm$$plus$1(
base.Str$0Instance.instance("a"))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("").imm$$plus$1(
base.Str$0Instance.instance(""))).read$assertEq$1(
base.Str$0Instance.instance("")))).imm$test$1(
((base.Str$0)((base.Str$0)base.Str$0Instance.instance("a").imm$$plus$1(
base.Str$0Instance.instance(""))).imm$$plus$1(
base.Str$0Instance.instance("b"))).read$assertEq$1(
base.Str$0Instance.instance("ab")))).imm$test$1(
((base.Str$0)((base.Str$0)base.Str$0Instance.instance("").imm$$plus$1(
base.Str$0Instance.instance("a"))).imm$$plus$1(
base.Str$0Instance.instance(""))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Str$0)((base.Str$0)base.Str$0Instance.instance("a").imm$$plus$1(
base.Str$0Instance.instance("b"))).imm$$plus$1(
base.Str$0Instance.instance("c"))).read$assertEq$1(
base.Str$0Instance.instance("abc")))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("x=").imm$$plus$1(
base.True$0.instance)).read$assertEq$1(
base.Str$0Instance.instance("x=True")))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("x=").imm$$plus$1(
base.False$0.instance)).read$assertEq$1(
base.Str$0Instance.instance("x=False")))).imm$test$1(
((base.Str$0)((base.Str$0)base.Str$0Instance.instance("x=").imm$$plus$1(
base.True$0.instance)).read$str$0(
)).read$assertEq$1(
base.Str$0Instance.instance("x=True")))).imm$test$1(
((base.Str$0)((base.Str$0)base.Str$0Instance.instance("x=").imm$$plus$1(
base.True$0.instance)).imm$$plus$1(
base.False$0.instance)).read$assertEq$1(
base.Str$0Instance.instance("x=TrueFalse")))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("n=").imm$$plus$1(
base.Nat$0Instance.instance(5L))).read$assertEq$1(
base.Str$0Instance.instance("n=5")))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("n=").imm$$plus$1(
base.Nat$0Instance.instance(4294967295L))).read$assertEq$1(
base.Str$0Instance.instance("n=4294967295")))).imm$test$1(
((base.Nat$0)((base.Str$0)base.Str$0Instance.instance("a").imm$$plus$1(
base.Str$0Instance.instance("b"))).imm$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(2L)))).imm$test$1(
((base.Nat$0)((base.Str$0)base.Str$0Instance.instance("a").imm$$plus$1(
base.Str$0Instance.instance(""))).imm$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(1L)))).imm$test$1(
((base.Nat$0)((base.Str$0)base.Str$0Instance.instance("").imm$$plus$1(
base.Str$0Instance.instance("b"))).imm$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(1L)));
  }
  StrConcat$0 instance= new StrConcat$0(){};}