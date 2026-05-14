package base;
public interface NewSum3$0 extends base.MF$2{
  default Object mut$$hash$1(Object p0){
    var this$= this;
    var x$= (base.Int$0)p0;
    return new base.F$4(){
    public Object read$$hash$3(Object p0, Object p1, Object p2){
    var a$= (base.Int$0)p0;
    var b$= (base.Int$0)p1;
    var c$= (base.Int$0)p2;
    return ((base.Int$0)((base.Int$0)x$.imm$$plus$1(
a$)).imm$$plus$1(
b$)).imm$$plus$1(
c$);
  }

};
  }
  NewSum3$0 instance= new NewSum3$0(){};}