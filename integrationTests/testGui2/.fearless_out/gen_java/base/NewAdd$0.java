package base;
public interface NewAdd$0 extends base.MF$2{
  default Object mut$$hash$1(Object p0){
    var this$= this;
    var x$= (base.Int$0)p0;
    return new base.F$2(){
    public Object read$$hash$1(Object p0){
    var y$= (base.Int$0)p0;
    return x$.imm$$plus$1(
y$);
  }

};
  }
  NewAdd$0 instance= new NewAdd$0(){};}