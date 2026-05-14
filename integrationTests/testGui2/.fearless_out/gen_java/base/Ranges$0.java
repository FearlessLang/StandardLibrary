package base;
public interface Ranges$0 extends base.F$3{
  default Object read$$hash$2(Object p0, Object p1){
    var this$= this;
    var min$= (base.Nat$0)p0;
    var max$= (base.Nat$0)p1;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$repr$2(
new base.ReprWithInvariant$1(){
    public Object mut$invariant$0(){
    return new base.F$2(){
    public Object read$$hash$1(Object p0){
    var r$= (base.MinMax$0)p0;
    return ((base.Nat$0)r$.read$min$0(
)).read$$lt$1(
r$.read$max$0(
));
  }

};
  }

    public Object mut$elem$0(){
    return base.MinMaxs$0.instance.read$$hash$2(
min$,
max$);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var minMax$= (base.Repr$1)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return new Range$0(){
    public Object read$min$0(){
    return minMax$.read$look$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var r$= (base.MinMax$0)p0;
    return r$.read$min$0(
);
  }

});
  }

    public Object read$max$0(){
    return minMax$.read$look$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var r$= (base.MinMax$0)p0;
    return r$.read$max$0(
);
  }

});
  }

    public Object mut$min$1(Object p0){
    var n$= (base.Nat$0)p0;
    return minMax$.mut$mutate$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var r$= (base.MinMax$0)p0;
    return r$.mut$min$1(
n$);
  }

});
  }

    public Object mut$max$1(Object p0){
    var n$= (base.Nat$0)p0;
    return minMax$.mut$mutate$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var r$= (base.MinMax$0)p0;
    return r$.mut$max$1(
n$);
  }

});
  }

    public Object read$str$0(){
    return minMax$.read$cachableLook$1(
_GRang$0.instance);
  }

};
  }

});
  }

});
  }
  Ranges$0 instance= new Ranges$0(){};}