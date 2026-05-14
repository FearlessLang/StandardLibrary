package base;
public interface Count$0{
  default Object imm$int$1(Object p0){
    var this$= this;
    var n$= (base.Int$0)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$var$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return n$;
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var c$= (base.Var$1)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return new base.Count$1(){
    public Object mut$get$0(){
    return c$.mut$get$0(
);
  }

    public Object read$get$0(){
    return c$.read$get$0(
);
  }

    public Object mut$swap$1(Object p0){
    var x$= (base.Int$0)p0;
    return c$.mut$swap$1(
x$);
  }

    public Object mut$$plus_plus$0(){
    return c$.mut$set$1(
((base.Int$0)c$.mut$get$0(
)).imm$$plus$1(
base.Int$0Instance.instance(1L)));
  }

    public Object mut$$dash_dash$0(){
    return c$.mut$set$1(
((base.Int$0)c$.mut$get$0(
)).imm$$dash$1(
base.Int$0Instance.instance(1L)));
  }

    public Object mut$$plus_eq$1(Object p0){
    var n2$= (base.Int$0)p0;
    return c$.mut$set$1(
((base.Int$0)c$.mut$get$0(
)).imm$$plus$1(
n2$));
  }

    public Object mut$$dash_eq$1(Object p0){
    var n2$= (base.Int$0)p0;
    return c$.mut$set$1(
((base.Int$0)c$.mut$get$0(
)).imm$$dash$1(
n2$));
  }

    public Object mut$$star_eq$1(Object p0){
    var n2$= (base.Int$0)p0;
    return c$.mut$set$1(
((base.Int$0)c$.mut$get$0(
)).imm$$star$1(
n2$));
  }

};
  }

});
  }

});
  }
  default Object imm$nat$1(Object p0){
    var this$= this;
    var n$= (base.Nat$0)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$var$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return n$;
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var c$= (base.Var$1)p0;
    var _beqS$= (base.Block$1)p1;
    return _beqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return new base.Count$1(){
    public Object mut$get$0(){
    return c$.mut$get$0(
);
  }

    public Object read$get$0(){
    return c$.read$get$0(
);
  }

    public Object mut$swap$1(Object p0){
    var x$= (base.Nat$0)p0;
    return c$.mut$swap$1(
x$);
  }

    public Object mut$$plus_plus$0(){
    return c$.mut$set$1(
((base.Nat$0)c$.mut$get$0(
)).imm$$plus$1(
base.Nat$0Instance.instance(1L)));
  }

    public Object mut$$dash_dash$0(){
    return c$.mut$set$1(
((base.Nat$0)c$.mut$get$0(
)).imm$$dash$1(
base.Nat$0Instance.instance(1L)));
  }

    public Object mut$$plus_eq$1(Object p0){
    var n2$= (base.Nat$0)p0;
    return c$.mut$set$1(
((base.Nat$0)c$.mut$get$0(
)).imm$$plus$1(
n2$));
  }

    public Object mut$$dash_eq$1(Object p0){
    var n2$= (base.Nat$0)p0;
    return c$.mut$set$1(
((base.Nat$0)c$.mut$get$0(
)).imm$$dash$1(
n2$));
  }

    public Object mut$$star_eq$1(Object p0){
    var n2$= (base.Nat$0)p0;
    return c$.mut$set$1(
((base.Nat$0)c$.mut$get$0(
)).imm$$star$1(
n2$));
  }

};
  }

});
  }

});
  }
  Count$0 instance= new Count$0(){};}