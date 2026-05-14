package base;
public interface CPoints$0{
  default Object imm$$hash$2(Object p0, Object p1){
    var this$= this;
    var x$= (base.Nat$0)p0;
    var y$= (base.Nat$0)p1;
    return new CPoint$0(){
    public Object read$x$0(){
    var self$= this;
    return x$;
  }

    public Object read$y$0(){
    var self$= this;
    return y$;
  }

    public Object read$cmp$3(Object p0, Object p1, Object p2){
    var self$= this;
    var p0$= (base.CPoint$0)p0;
    var p1$= (base.CPoint$0)p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.Nat$0)p0$.read$x$0(
)).read$$lt_eq_gt$2(
p1$.read$x$0(
),
m$.mut$$and_and$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Nat$0)p0$.read$y$0(
)).read$$lt_eq_gt$2(
p1$.read$y$0(
),
m$);
  }

}));
  }

    public Object read$close$0(){
    var self$= this;
    return self$;
  }

};
  }
  CPoints$0 instance= new CPoints$0(){};}