package base;
public interface Wrapper$2 extends base.DataType$2, base.Extensible$1, base.OrderHash$1, base.Order$1, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object read$get$0(){
    var this$= this;
    return ((base.DataType$2)this$.read$getDataType$0(
)).read$close$0(
);
  }
  default Object read$name$0(){
    throw new AssertionError("Uncallable method: Wrapper$2.read$name$0"+this.getClass().getName());
  }
  default Object read$getDataType$0(){
    throw new AssertionError("Uncallable method: Wrapper$2.read$getDataType$0"+this.getClass().getName());
  }
  default Object read$str$0(){
    var this$= this;
    return ((base.Str$0)((base.Str$0)((base.Str$0)this$.read$name$0(
)).imm$$plus$1(
base.Str$0Instance.instance("["))).imm$$plus$1(
((base.DataType$2)((base.DataType$2)this$.read$getDataType$0(
)).read$close$1(
this$.read$get$0(
))).read$str$0(
))).imm$$plus$1(
base.Str$0Instance.instance("]"));
  }
  default Object read$$hash$1(Object p0){
    throw new AssertionError("Uncallable method: Wrapper$2.read$$hash$1"+this.getClass().getName());
  }
  default Object read$hash$0(){
    var this$= this;
    return ((base.DataType$2)((base.DataType$2)this$.read$getDataType$0(
)).read$close$1(
this$.read$get$0(
))).read$hash$0(
);
  }
  default Object read$cmp$3(Object p0, Object p1, Object p2){
    var this$= this;
    var a$= p0;
    var b$= p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.DataType$2)this$.read$getDataType$0(
)).read$cmp$3(
((base.Wrapper$2)this$.read$close$1(
a$)).read$get$0(
),
((base.Wrapper$2)this$.read$close$1(
b$)).read$get$0(
),
m$);
  }
  default Object read$close$1(Object p0){
    throw new AssertionError("Uncallable method: Wrapper$2.read$close$1"+this.getClass().getName());
  }
  default Object read$info$0(){
    var this$= this;
    return ((base.DataType$2)((base.DataType$2)this$.read$getDataType$0(
)).read$close$1(
this$.read$get$0(
))).read$info$0(
);
  }
  default Object read$imm$0(){
    var this$= this;
    return this$.read$$hash$1(
this$.read$get$0(
));
  }
}