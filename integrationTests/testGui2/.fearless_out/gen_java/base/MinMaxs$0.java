package base;
public interface MinMaxs$0 extends base.F$3{
  default Object read$$hash$2(Object p0, Object p1){
    var this$= this;
    var min$= (base.Nat$0)p0;
    var max$= (base.Nat$0)p1;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$var$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return min$;
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var _min$= (base.Var$1)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$var$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return max$;
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var _max$= (base.Var$1)p0;
    var _beqS$= (base.Block$1)p1;
    return _beqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return new MinMax$0(){
    public Object read$min$0(){
    return _min$.read$get$0(
);
  }

    public Object read$max$0(){
    return _max$.read$get$0(
);
  }

    public Object mut$min$1(Object p0){
    var n$= (base.Nat$0)p0;
    return _min$.mut$set$1(
n$);
  }

    public Object mut$max$1(Object p0){
    var n$= (base.Nat$0)p0;
    return _max$.mut$set$1(
n$);
  }

};
  }

});
  }

});
  }

});
  }
  MinMaxs$0 instance= new MinMaxs$0(){};}