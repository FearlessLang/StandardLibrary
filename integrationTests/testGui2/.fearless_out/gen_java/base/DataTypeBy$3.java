package base;
public interface DataTypeBy$3 extends base.ToInfoBy$1, base.ToImmBy$2, base.OrderHashBy$2, base.OrderBy$2, base.ToStrBy$1{
  default Object imm$$hash$1(Object p0){
    throw new AssertionError("Uncallable method: DataTypeBy$3.imm$$hash$1"+this.getClass().getName());
  }
  default Object imm$viewData$1(Object p0){
    var this$= this;
    var f$= (base.F$2)p0;
    return new base.DataTypeBy$3(){
    public Object imm$$hash$1(Object p0){
    var a$= p0;
    return this$.imm$$hash$1(
f$.read$$hash$1(
a$));
  }

};
  }
  default Object imm$thenData$2(Object p0, Object p1){
    var this$= this;
    var next$= (base.DataTypeBy$3)p0;
    var mergeImm$= (base.F$3)p1;
    return new base.DataTypeBy$2(){
    public Object imm$cmp$3(Object p0, Object p1, Object p2){
    var e0$= p0;
    var e1$= p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.DataType$2)this$.imm$$hash$1(
e0$)).read$$lt_eq_gt$2(
this$.imm$$hash$1(
e1$),
new base.OrderMatch$1(){
    public Object mut$lt$0(){
    return m$.mut$lt$0(
);
  }

    public Object mut$gt$0(){
    return m$.mut$gt$0(
);
  }

    public Object mut$eq$0(){
    return ((base.DataType$2)next$.imm$$hash$1(
e0$)).read$$lt_eq_gt$2(
next$.imm$$hash$1(
e1$),
m$);
  }

});
  }

    public Object imm$hash$1(Object p0){
    var by$= p0;
    return ((base.Nat$0)((base.DataType$2)this$.imm$$hash$1(
by$)).read$hash$0(
)).imm$hashWith$1(
((base.DataType$2)next$.imm$$hash$1(
by$)).read$hash$0(
));
  }

    public Object imm$str$1(Object p0){
    var by$= p0;
    return ((base.Str$0)((base.Str$0)((base.DataType$2)this$.imm$$hash$1(
by$)).read$str$0(
)).imm$$plus$1(
base.Str$0Instance.instance(", "))).imm$$plus$1(
((base.DataType$2)next$.imm$$hash$1(
by$)).read$str$0(
));
  }

    public Object imm$info$1(Object p0){
    var by$= p0;
    return ((base.Info$0)((base.DataType$2)this$.imm$$hash$1(
by$)).read$info$0(
)).imm$$plus$1(
((base.DataType$2)next$.imm$$hash$1(
by$)).read$info$0(
));
  }

    public Object imm$imm$1(Object p0){
    var by$= p0;
    return mergeImm$.read$$hash$2(
((base.DataType$2)this$.imm$$hash$1(
by$)).read$imm$0(
),
((base.DataType$2)next$.imm$$hash$1(
by$)).read$imm$0(
));
  }

};
  }
  default Object imm$thenDataLeft$1(Object p0){
    var this$= this;
    var next$= (base.DataTypeBy$3)p0;
    return this$.imm$thenData$2(
next$,
new base.F$3(){
    public Object read$$hash$2(Object p0, Object p1){
    var k0$= p0;
    var k10$= p1;
    return k0$;
  }

});
  }
}