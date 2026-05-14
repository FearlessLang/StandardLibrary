package base;
public interface StackFrame$0 extends base.DataType$2, base.Extensible$1, base.OrderHash$1, base.Order$1, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object imm$fileName$0(){
    throw new AssertionError("Uncallable method: StackFrame$0.imm$fileName$0"+this.getClass().getName());
  }
  default Object imm$typeName$0(){
    throw new AssertionError("Uncallable method: StackFrame$0.imm$typeName$0"+this.getClass().getName());
  }
  default Object imm$methodName$0(){
    throw new AssertionError("Uncallable method: StackFrame$0.imm$methodName$0"+this.getClass().getName());
  }
  default Object imm$lineNumber$0(){
    throw new AssertionError("Uncallable method: StackFrame$0.imm$lineNumber$0"+this.getClass().getName());
  }
  default Object read$close$0(){
    var this$= this;
    return this$;
  }
  default Object mut$close$0(){
    var this$= this;
    return this$;
  }
  default Object read$close$1(Object p0){
    var this$= this;
    var _aimpl$= (base.StackFrame$0)p0;
    return _aimpl$;
  }
  default Object read$cmp$3(Object p0, Object p1, Object p2){
    var this$= this;
    var a$= (base.StackFrame$0)p0;
    var b$= (base.StackFrame$0)p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.Str$0)a$.read$str$0(
)).read$$lt_eq_gt$2(
b$.read$str$0(
),
m$);
  }
  default Object read$hash$0(){
    var this$= this;
    return ((base.Str$0)this$.read$str$0(
)).read$hash$0(
);
  }
  default Object read$str$0(){
    var this$= this;
    return ((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("StackFrame[").imm$$plus$1(
((base.StackFrame$0)this$.read$imm$0(
)).imm$fileName$0(
))).imm$$plus$1(
base.Str$0Instance.instance(", "))).imm$$plus$1(
((base.StackFrame$0)this$.read$imm$0(
)).imm$typeName$0(
))).imm$$plus$1(
base.Str$0Instance.instance(", "))).imm$$plus$1(
((base.StackFrame$0)this$.read$imm$0(
)).imm$methodName$0(
))).imm$$plus$1(
base.Str$0Instance.instance(", "))).imm$$plus$1(
((base.StackFrame$0)this$.read$imm$0(
)).imm$lineNumber$0(
))).imm$$plus$1(
base.Str$0Instance.instance("]"));
  }
  default Object read$info$0(){
    var this$= this;
    return base.Infos$0.instance.imm$map$8(
base.Str$0Instance.instance("fileName"),
((base.StackFrame$0)this$.read$imm$0(
)).imm$fileName$0(
),
base.Str$0Instance.instance("typeName"),
((base.StackFrame$0)this$.read$imm$0(
)).imm$typeName$0(
),
base.Str$0Instance.instance("methodName"),
((base.StackFrame$0)this$.read$imm$0(
)).imm$methodName$0(
),
base.Str$0Instance.instance("lineNumber"),
((base.StackFrame$0)this$.read$imm$0(
)).imm$lineNumber$0(
));
  }
}