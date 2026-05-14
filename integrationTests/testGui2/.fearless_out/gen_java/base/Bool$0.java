package base;
public interface Bool$0 extends base.Sealed$0, base.DataType$2, base.Extensible$1, base.OrderHash$1, base.Order$1, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object imm$and$1(Object p0){
    throw new AssertionError("Uncallable method: Bool$0.imm$and$1"+this.getClass().getName());
  }
  default Object imm$$and_and$1(Object p0){
    throw new AssertionError("Uncallable method: Bool$0.imm$$and_and$1"+this.getClass().getName());
  }
  default Object imm$$and$1(Object p0){
    var this$= this;
    var b$= (base.Bool$0)p0;
    return this$.imm$and$1(
b$);
  }
  default Object imm$or$1(Object p0){
    throw new AssertionError("Uncallable method: Bool$0.imm$or$1"+this.getClass().getName());
  }
  default Object imm$$or$1(Object p0){
    var this$= this;
    var b$= (base.Bool$0)p0;
    return this$.imm$or$1(
b$);
  }
  default Object imm$$or_or$1(Object p0){
    throw new AssertionError("Uncallable method: Bool$0.imm$$or_or$1"+this.getClass().getName());
  }
  default Object imm$not$0(){
    throw new AssertionError("Uncallable method: Bool$0.imm$not$0"+this.getClass().getName());
  }
  default Object imm$$eq_eq_gt$1(Object p0){
    var this$= this;
    var b$= (base.MF$1)p0;
    return ((base.Bool$0)this$.imm$not$0(
)).imm$$or_or$1(
b$);
  }
  default Object imm$if$1(Object p0){
    throw new AssertionError("Uncallable method: Bool$0.imm$if$1"+this.getClass().getName());
  }
  default Object imm$$q$1(Object p0){
    var this$= this;
    var f$= (base.ThenElse$1)p0;
    return this$.imm$if$1(
f$);
  }
  default Object imm$match$1(Object p0){
    var this$= this;
    var m$= (base.BoolMatch$1)p0;
    return this$.imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return m$.mut$true$0(
);
  }

    public Object mut$else$0(){
    return m$.mut$false$0(
);
  }

});
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
    var _aimpl$= (base.Bool$0)p0;
    return _aimpl$;
  }
  default Object read$cmp$3(Object p0, Object p1, Object p2){
    var this$= this;
    var a$= (base.Bool$0)p0;
    var b$= (base.Bool$0)p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.Bool$0)a$.read$imm$0(
)).imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return ((base.Bool$0)b$.read$imm$0(
)).imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return m$.mut$eq$0(
);
  }

    public Object mut$else$0(){
    return m$.mut$gt$0(
);
  }

});
  }

    public Object mut$else$0(){
    return ((base.Bool$0)b$.read$imm$0(
)).imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return m$.mut$lt$0(
);
  }

    public Object mut$else$0(){
    return m$.mut$eq$0(
);
  }

});
  }

});
  }
  default Object read$hash$0(){
    var this$= this;
    return ((base.Bool$0)this$.read$imm$0(
)).imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return base.Nat$0Instance.instance(1L);
  }

    public Object mut$else$0(){
    return base.Nat$0Instance.instance(0L);
  }

});
  }
  default Object read$str$0(){
    var this$= this;
    return ((base.Bool$0)this$.read$imm$0(
)).imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return base.Str$0Instance.instance("True");
  }

    public Object mut$else$0(){
    return base.Str$0Instance.instance("False");
  }

});
  }
  default Object read$info$0(){
    var this$= this;
    return base.Infos$0.instance.imm$msg$1(
this$.read$str$0(
));
  }
  default Object imm$assertTrue$0(){
    var this$= this;
    return this$.read$assertEq$1(
base.True$0.instance);
  }
  default Object imm$assertFalse$0(){
    var this$= this;
    return this$.read$assertEq$1(
base.False$0.instance);
  }
}