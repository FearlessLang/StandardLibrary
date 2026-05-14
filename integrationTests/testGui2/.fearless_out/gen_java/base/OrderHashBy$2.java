package base;
public interface OrderHashBy$2 extends base.OrderBy$2, base.ToStrBy$1{
  default Object imm$$hash$1(Object p0){
    throw new AssertionError("Uncallable method: OrderHashBy$2.imm$$hash$1"+this.getClass().getName());
  }
  default Object imm$thenHash$1(Object p0){
    var this$= this;
    var next$= (base.OrderHashBy$2)p0;
    return new base.OrderHashBy$1(){
    public Object imm$cmp$3(Object p0, Object p1, Object p2){
    var t0$= p0;
    var t1$= p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.OrderHash$1)this$.imm$$hash$1(
t0$)).read$$lt_eq_gt$2(
this$.imm$$hash$1(
t1$),
new base.OrderMatch$1(){
    public Object mut$lt$0(){
    return m$.mut$lt$0(
);
  }

    public Object mut$eq$0(){
    return ((base.OrderHash$1)next$.imm$$hash$1(
t0$)).read$$lt_eq_gt$2(
next$.imm$$hash$1(
t1$),
m$);
  }

    public Object mut$gt$0(){
    return m$.mut$gt$0(
);
  }

});
  }

    public Object imm$hash$1(Object p0){
    var t$= p0;
    return ((base.Nat$0)((base.OrderHash$1)this$.imm$$hash$1(
t$)).read$hash$0(
)).imm$hashWith$1(
((base.OrderHash$1)next$.imm$$hash$1(
t$)).read$hash$0(
));
  }

    public Object imm$str$1(Object p0){
    var t$= p0;
    return ((base.OrderHash$1)this$.imm$$hash$1(
t$)).read$str$0(
);
  }

};
  }
  default Object imm$viewHash$1(Object p0){
    var this$= this;
    var f$= (base.F$2)p0;
    return new base.OrderHashBy$1(){
    public Object imm$cmp$3(Object p0, Object p1, Object p2){
    var a0$= p0;
    var a1$= p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.OrderHash$1)this$.imm$$hash$1(
f$.read$$hash$1(
a0$))).read$$lt_eq_gt$2(
this$.imm$$hash$1(
f$.read$$hash$1(
a1$)),
m$);
  }

    public Object imm$hash$1(Object p0){
    var a$= p0;
    return ((base.OrderHash$1)this$.imm$$hash$1(
f$.read$$hash$1(
a$))).read$hash$0(
);
  }

    public Object imm$str$1(Object p0){
    var t$= p0;
    return ((base.OrderHash$1)this$.imm$$hash$1(
f$.read$$hash$1(
t$))).read$str$0(
);
  }

};
  }
  default Object imm$hideKey$0(){
    var this$= this;
    return new base.OrderHashBy$1(){
    public Object imm$cmp$3(Object p0, Object p1, Object p2){
    var t0$= p0;
    var t1$= p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.OrderHash$1)this$.imm$$hash$1(
t0$)).read$$lt_eq_gt$2(
this$.imm$$hash$1(
t1$),
m$);
  }

    public Object imm$hash$1(Object p0){
    var t$= p0;
    return ((base.OrderHash$1)this$.imm$$hash$1(
t$)).read$hash$0(
);
  }

    public Object imm$str$1(Object p0){
    var t$= p0;
    return ((base.OrderHash$1)this$.imm$$hash$1(
t$)).read$str$0(
);
  }

};
  }
}