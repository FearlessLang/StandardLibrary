package base;
public interface OrderByCaseInsensitive$0 extends base.OrderBy$1, base.OrderBy$2{
  default Object imm$cmp$3(Object p0, Object p1, Object p2){
    var this$= this;
    var s0$= (base.Str$0)p0;
    var s1$= (base.Str$0)p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.Str$0)((base.Str$0)s0$.read$imm$0(
)).imm$lower$0(
)).read$$lt_eq_gt$2(
((base.Str$0)s1$.read$imm$0(
)).imm$lower$0(
),
m$);
  }
  OrderByCaseInsensitive$0 instance= new OrderByCaseInsensitive$0(){};}