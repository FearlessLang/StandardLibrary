package base;
public interface _BS$0 extends base.DataType$2, base.Extensible$1, base.OrderHash$1, base.Order$1, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object read$s$0(){
    throw new AssertionError("Uncallable method: _BS$0.read$s$0"+this.getClass().getName());
  }
  default Object read$str$0(){
    var this$= this;
    return base.Str$0Instance.instance("Nope");
  }
  default Object read$info$0(){
    var this$= this;
    return base.Todo$0.instance.imm$$bang$0(
);
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
    var _aimpl$= (base._BS$0)p0;
    return _aimpl$;
  }
  default Object read$imm$0(){
    var this$= this;
    return base.Todo$0.instance.imm$$bang$0(
);
  }
  default Object read$hash$0(){
    var this$= this;
    return ((base.Str$0)this$.read$s$0(
)).read$hash$0(
);
  }
  default Object read$cmp$3(Object p0, Object p1, Object p2){
    var this$= this;
    var a$= (base._BS$0)p0;
    var b$= (base._BS$0)p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.Str$0)a$.read$s$0(
)).read$$lt_eq_gt$2(
b$.read$s$0(
),
m$);
  }
}