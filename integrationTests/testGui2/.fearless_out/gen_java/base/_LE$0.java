package base;
public interface _LE$0{
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    var this$= this;
    var o$= (base.Order$1)p0;
    var a$= p1;
    var b$= p2;
    return o$.read$cmp$3(
a$,
b$,
new base.OrderMatch$1(){
    public Object mut$lt$0(){
    return base.True$0.instance;
  }

    public Object mut$eq$0(){
    return base.True$0.instance;
  }

    public Object mut$gt$0(){
    return base.False$0.instance;
  }

});
  }
  _LE$0 instance= new _LE$0(){};}