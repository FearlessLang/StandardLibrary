package base;
public interface StrEscape$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Str$0)base.Str$0Instance.instance("").imm$escape$0(
)).read$assertEq$1(
base.Str$0Instance.instance("\"\"")))).imm$test$1(
((base.Nat$0)((base.Str$0)base.Str$0Instance.instance("").imm$escape$0(
)).imm$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(2L)))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("a").imm$escape$0(
)).read$assertEq$1(
((base.Str$0)base.Str$0Instance.instance("").imm$$xor$1(
base.Str$0Instance.instance("a"))).imm$$xor$0(
)))).imm$test$1(
((base.Nat$0)((base.Str$0)base.Str$0Instance.instance("a").imm$escape$0(
)).imm$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(3L)))).imm$test$1(
((base.Str$0)base.Str$0Instance.instance("abc").imm$escape$0(
)).read$assertEq$1(
((base.Str$0)base.Str$0Instance.instance("").imm$$xor$1(
base.Str$0Instance.instance("abc"))).imm$$xor$0(
)))).imm$test$1(
((base.Str$0)((base.Str$0)base.Str$0Instance.instance("a").imm$$or$1(
base.Str$0Instance.instance("b"))).imm$escape$0(
)).read$assertEq$1(
((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("").imm$$xor$1(
base.Str$0Instance.instance("a"))).imm$$xor$1(
base.Str$0Instance.instance(" | "))).imm$$xor$1(
base.Str$0Instance.instance("b"))).imm$$xor$0(
)))).imm$test$1(
((base.Str$0)((base.Str$0)base.Str$0Instance.instance("a").imm$$xor$1(
base.Str$0Instance.instance("b"))).imm$escape$0(
)).read$assertEq$1(
((base.Str$0)base.Str$0Instance.instance("`").imm$$plus$1(
base.Str$0Instance.instance("a\"b"))).imm$$plus$1(
base.Str$0Instance.instance("`"))));
  }
  StrEscape$0 instance= new StrEscape$0(){};}