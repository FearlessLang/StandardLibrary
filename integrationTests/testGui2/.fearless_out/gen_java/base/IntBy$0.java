package base;
public interface IntBy$0 extends base.DataTypeBy$2, base.DataTypeBy$3, base.OrderBy$2, base.OrderHashBy$2, base.ToImmBy$2, base.ToInfoBy$1, base.ToStrBy$1{
  default Object imm$cmp$3(Object p0, Object p1, Object p2){
    var this$= this;
    var t0$= (base.Int$0)p0;
    var t1$= (base.Int$0)p1;
    var m$= (base.OrderMatch$1)p2;
    return t0$.read$$lt_eq_gt$2(
t1$,
m$);
  }
  default Object imm$hash$1(Object p0){
    var this$= this;
    var t$= (base.Int$0)p0;
    return t$.read$hash$0(
);
  }
  default Object imm$str$1(Object p0){
    var this$= this;
    var t$= (base.Int$0)p0;
    return t$.read$str$0(
);
  }
  default Object imm$info$1(Object p0){
    var this$= this;
    var t$= (base.Int$0)p0;
    return t$.read$info$0(
);
  }
  default Object imm$imm$1(Object p0){
    var this$= this;
    var t$= (base.Int$0)p0;
    return t$.read$imm$0(
);
  }
  IntBy$0 instance= new IntBy$0(){};}