package base;
public interface A1$0 extends base.OrderHash$1, base.Order$1, base.ToStr$0{
  default Object read$close$0(){
    var this$= this;
    return this$;
  }
  default Object read$close$1(Object p0){
    var this$= this;
    var _aimpl$= (base.A1$0)p0;
    return _aimpl$;
  }
  default Object read$cmp$3(Object p0, Object p1, Object p2){
    var this$= this;
    var t0$= (base.A1$0)p0;
    var t1$= (base.A1$0)p1;
    var m$= (base.OrderMatch$1)p2;
    return m$.mut$eq$0(
);
  }
  default Object read$hash$0(){
    var this$= this;
    return base.Nat$0Instance.instance(0L);
  }
  default Object read$str$0(){
    var this$= this;
    return base.Str$0Instance.instance("");
  }
  default Object imm$proofConcrete$0(){
    var this$= this;
    return base.A1$0.instance;
  }
  A1$0 instance= new A1$0(){};}