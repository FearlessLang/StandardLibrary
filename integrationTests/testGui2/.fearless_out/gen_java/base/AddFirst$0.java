package base;
public interface AddFirst$0 extends base.MF$2{
  default Object mut$$hash$1(Object p0){
    var this$= this;
    var xs$= (base.List$1)p0;
    return new base.F$2(){
    public Object read$$hash$1(Object p0){
    var k$= (base.Int$0)p0;
    return ((base.Int$0)xs$.read$get$1(
base.Nat$0Instance.instance(0L))).imm$$plus$1(
k$);
  }

};
  }
  AddFirst$0 instance= new AddFirst$0(){};}