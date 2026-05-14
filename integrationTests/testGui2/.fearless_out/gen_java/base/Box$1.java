package base;
public interface Box$1 extends base.OrderHash$2, base.Order$2, base.ToStr$1{
  default Object mut$get$0(){
    throw new AssertionError("Uncallable method: Box$1.mut$get$0"+this.getClass().getName());
  }
  default Object read$get$0(){
    throw new AssertionError("Uncallable method: Box$1.read$get$0"+this.getClass().getName());
  }
  default Object imm$get$0(){
    throw new AssertionError("Uncallable method: Box$1.imm$get$0"+this.getClass().getName());
  }
  default Object read$close$0(){
    var this$= this;
    return this$;
  }
  default Object read$close$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Box$1)p0;
    return _aimpl$;
  }
  default Object read$cmp$4(Object p0, Object p1, Object p2, Object p3){
    var this$= this;
    var by$= (base.OrderBy$2)p0;
    var t0$= (base.Box$1)p1;
    var t1$= (base.Box$1)p2;
    var m$= (base.OrderMatch$1)p3;
    return ((base.Order$1)by$.imm$$hash$1(
t0$.read$get$0(
))).read$$lt_eq_gt$2(
by$.imm$$hash$1(
t1$.read$get$0(
)),
m$);
  }
  default Object read$hash$1(Object p0){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    return ((base.OrderHash$1)by$.imm$$hash$1(
this$.read$get$0(
))).read$hash$0(
);
  }
  default Object read$str$1(Object p0){
    var this$= this;
    var by$= (base.ToStrBy$1)p0;
    return ((base.ToStr$0)by$.imm$$hash$1(
this$.read$get$0(
))).read$str$0(
);
  }
  default Object imm$proofConcrete$1(Object p0){
    var this$= this;
    var s$= (base.Str$0)p0;
    return new base.Box$1(){
    public Object read$get$0(){
    return s$;
  }

    public Object imm$get$0(){
    return s$;
  }

};
  }
}