package base;
public interface OrderBy$2{
  default Object imm$$hash$1(Object p0){
    throw new AssertionError("Uncallable method: OrderBy$2.imm$$hash$1"+this.getClass().getName());
  }
  default Object imm$then$1(Object p0){
    var this$= this;
    var next$= (base.OrderBy$2)p0;
    return new base.OrderBy$1(){
    public Object imm$cmp$3(Object p0, Object p1, Object p2){
    var t0$= p0;
    var t1$= p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.Order$1)this$.imm$$hash$1(
t0$)).read$$lt_eq_gt$2(
this$.imm$$hash$1(
t1$),
m$.mut$$and_and$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Order$1)next$.imm$$hash$1(
t0$)).read$$lt_eq_gt$2(
next$.imm$$hash$1(
t1$),
m$);
  }

}));
  }

};
  }
  default Object imm$view$1(Object p0){
    var this$= this;
    var f$= (base.F$2)p0;
    return new base.OrderBy$1(){
    public Object imm$cmp$3(Object p0, Object p1, Object p2){
    var a0$= p0;
    var a1$= p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.Order$1)this$.imm$$hash$1(
f$.read$$hash$1(
a0$))).read$$lt_eq_gt$2(
this$.imm$$hash$1(
f$.read$$hash$1(
a1$)),
m$);
  }

};
  }
}