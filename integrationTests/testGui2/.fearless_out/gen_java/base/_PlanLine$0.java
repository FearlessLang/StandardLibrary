package base;
public interface _PlanLine$0{
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    var this$= this;
    var kind$= (base.Str$0)p0;
    var st$= (base.List$1)p1;
    var dept$= (base.Nat$0)p2;
    return ((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("PLAN|").imm$$plus$1(
kind$)).imm$$plus$1(
base.Str$0Instance.instance("|"))).imm$$plus$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$fileName$0(
))).imm$$plus$1(
base.Str$0Instance.instance("|"))).imm$$plus$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$typeName$0(
))).imm$$plus$1(
base.Str$0Instance.instance("|"))).imm$$plus$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$typeName$0(
))).imm$$plus$1(
base.Str$0Instance.instance(" at line: "))).imm$$plus$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$lineNumber$0(
))).imm$$plus$1(
base.Str$0Instance.instance("|"))).imm$$plus$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$lineNumber$0(
))).imm$$or$0(
);
  }
  _PlanLine$0 instance= new _PlanLine$0(){};}