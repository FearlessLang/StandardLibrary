package base;
public interface Degree$0 extends base.Angle$1, base.DataType$2, base.Extensible$1, base.F$1, base.OrderHash$1, base.Order$1, base.Sealed$0, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object imm$$plus$1(Object p0){
    var this$= this;
    var other$= (base.Degree$0)p0;
    return ((base.Opt$1)((base.Float$0)((base.Float$0)this$.read$$hash$0(
)).imm$$plus$1(
other$.read$$hash$0(
))).imm$$or_gt$1(
base.Degrees$0.instance)).read$$bang$0(
);
  }
  default Object imm$$dash$1(Object p0){
    var this$= this;
    var other$= (base.Degree$0)p0;
    return ((base.Opt$1)((base.Float$0)((base.Float$0)this$.read$$hash$0(
)).imm$$plus$1(
other$.read$$hash$0(
))).imm$$or_gt$1(
base.Degrees$0.instance)).read$$bang$0(
);
  }
  default Object imm$sin$0(){
    var this$= this;
    return ((base.Radian$0)this$.imm$toRad$0(
)).imm$sin$0(
);
  }
  default Object imm$cos$0(){
    var this$= this;
    return ((base.Radian$0)this$.imm$toRad$0(
)).imm$cos$0(
);
  }
  default Object imm$tan$0(){
    var this$= this;
    return ((base.Radian$0)this$.imm$toRad$0(
)).imm$tan$0(
);
  }
  default Object imm$toRad$0(){
    var this$= this;
    return ((base.Opt$1)((base.Float$0)((base.Float$0)((base.Float$0)this$.read$$hash$0(
)).imm$$star$1(
base.Math$0.instance.imm$pi$0(
))).imm$$slash$1(
base.Float$0Instance.instance(180.0d))).imm$$or_gt$1(
base.Radians$0.instance)).read$$bang$0(
);
  }
  default Object read$normalize$0(){
    var this$= this;
    return ((base.Opt$1)((base.Float$0)((base.Float$0)this$.read$$hash$0(
)).imm$$or_gt$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _aimpl$= (base.Float$0)p0;
    return base.Math$0.instance.imm$euclideanMod$2(
_aimpl$,
base.Float$0Instance.instance(360.0d));
  }

})).imm$$or_gt$1(
base.Degrees$0.instance)).read$$bang$0(
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
  default Object read$imm$0(){
    var this$= this;
    return ((base.Opt$1)((base.Float$0)this$.read$$hash$0(
)).imm$$or_gt$1(
base.Degrees$0.instance)).read$$bang$0(
);
  }
  default Object read$str$0(){
    var this$= this;
    return ((base.Str$0)base.Str$0Instance.instance("Degree[").imm$$plus$1(
((base.Float$0)this$.read$$hash$0(
)).read$str$0(
))).imm$$plus$1(
base.Str$0Instance.instance("]"));
  }
  default Object read$cmp$3(Object p0, Object p1, Object p2){
    var this$= this;
    var t0$= (base.Degree$0)p0;
    var t1$= (base.Degree$0)p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.Float$0)((base.Degree$0)t0$.read$normalize$0(
)).read$$hash$0(
)).read$$lt_eq_gt$2(
((base.Degree$0)t1$.read$normalize$0(
)).read$$hash$0(
),
m$);
  }
  default Object read$hash$0(){
    var this$= this;
    return ((base.Float$0)((base.Degree$0)this$.read$normalize$0(
)).read$$hash$0(
)).read$hash$0(
);
  }
  default Object read$close$1(Object p0){
    var this$= this;
    var _bimpl$= (base.Degree$0)p0;
    return _bimpl$;
  }
}