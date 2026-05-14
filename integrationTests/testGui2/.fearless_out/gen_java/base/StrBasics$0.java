package base;
public interface StrBasics$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Bool$0)base.Str$0Instance.instance("").imm$isEmpty$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Nat$0)base.Str$0Instance.instance("").imm$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(0L)))).imm$test$1(
((base.Bool$0)base.Str$0Instance.instance("a").imm$isEmpty$0(
)).read$assertEq$1(
base.False$0.instance))).imm$test$1(
((base.Nat$0)base.Str$0Instance.instance("a").imm$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(1L)))).imm$test$1(
((base.Nat$0)base.Str$0Instance.instance("ab").imm$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(2L)))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("").read$imm$0(
)).read$assertEq$1(
base.Str$0Instance.instance("")))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("a").read$imm$0(
)).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("a").read$str$0(
)).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("a").read$close$0(
)).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("ab").read$close$0(
)).read$assertEq$1(
base.Str$0Instance.instance("ab")));
  }
  StrBasics$0 instance= new StrBasics$0(){};}