package base;
public interface StrSizeOrder$0 extends base.OrderHashBy$1, base.OrderBy$2, base.OrderHashBy$2, base.ToStrBy$1{
  default Object imm$hash$1(Object p0){
    var this$= this;
    var s$= (base.Str$0)p0;
    return ((base.Nat$0)((base.Str$0)s$.read$imm$0(
)).imm$size$0(
)).read$hash$0(
);
  }
  default Object imm$str$1(Object p0){
    var this$= this;
    var s$= (base.Str$0)p0;
    return s$.read$imm$0(
);
  }
  default Object imm$cmp$3(Object p0, Object p1, Object p2){
    var this$= this;
    var t0$= (base.Str$0)p0;
    var t1$= (base.Str$0)p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.Nat$0)((base.Str$0)t0$.read$imm$0(
)).imm$size$0(
)).read$$lt_eq_gt$2(
((base.Str$0)t1$.read$imm$0(
)).imm$size$0(
),
m$);
  }
  StrSizeOrder$0 instance= new StrSizeOrder$0(){};}