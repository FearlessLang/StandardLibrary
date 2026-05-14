package base;
public interface _AddToLog$0{
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    var this$= this;
    var st$= (base.List$1)p0;
    var a$= (base.Action$1)p1;
    var dept$= (base.Nat$0)p2;
    return a$.mut$run$1(
new base.ActionMatch$2(){
    public Object mut$ok$1(Object p0){
    return ((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("<testcase classname=\"").imm$$plus$1(
base._XmlEscape$0.instance.imm$$hash$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$typeName$0(
)))).imm$$plus$1(
base.Str$0Instance.instance("\" name=\""))).imm$$plus$1(
base._XmlEscape$0.instance.imm$$hash$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$typeName$0(
)))).imm$$plus$1(
base.Str$0Instance.instance(" at line: "))).imm$$plus$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$lineNumber$0(
))).imm$$plus$1(
base.Str$0Instance.instance("\" file=\""))).imm$$plus$1(
base._XmlEscape$0.instance.imm$$hash$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$fileName$0(
)))).imm$$plus$1(
base.Str$0Instance.instance("\" line=\""))).imm$$plus$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$lineNumber$0(
))).imm$$plus$1(
base.Str$0Instance.instance("\"></testcase>"))).imm$$or$0(
);
  }

    public Object mut$info$1(Object p0){
    var i$= (base.Info$0)p0;
    return base.Block$0.instance.imm$$hash$2(
base.Debug$0.instance.imm$$hash$1(
((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Test failure").imm$$plus$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$typeName$0(
))).imm$$plus$1(
base.Str$0Instance.instance(" at line: "))).imm$$plus$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$lineNumber$0(
))).imm$$plus$1(
base.Str$0Instance.instance(" in file: "))).imm$$plus$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$fileName$0(
))),
((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("<testcase classname=\"").imm$$plus$1(
base._XmlEscape$0.instance.imm$$hash$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$typeName$0(
)))).imm$$plus$1(
base.Str$0Instance.instance("\" name=\""))).imm$$plus$1(
base._XmlEscape$0.instance.imm$$hash$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$typeName$0(
)))).imm$$plus$1(
base.Str$0Instance.instance(" at line: "))).imm$$plus$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$lineNumber$0(
))).imm$$plus$1(
base.Str$0Instance.instance("\" file=\""))).imm$$plus$1(
base._XmlEscape$0.instance.imm$$hash$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$fileName$0(
)))).imm$$plus$1(
base.Str$0Instance.instance("\" line=\""))).imm$$plus$1(
((base.StackFrame$0)st$.read$get$1(
dept$)).imm$lineNumber$0(
))).imm$$plus$1(
base.Str$0Instance.instance("\">"))).imm$$or$1(
base._FormatFailure$0.instance.imm$$hash$1(
i$))).imm$$or$1(
base.Str$0Instance.instance("</testcase>"))).imm$$or$0(
));
  }

});
  }
  _AddToLog$0 instance= new _AddToLog$0(){};}