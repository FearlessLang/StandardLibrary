package _base;
public interface Vars$o$0 extends Sealed$2o$0{
  default Object imm$$hash$1(Object p0){ return new Var$c$1Instance(p0); }
  Vars$o$0 instance= new Vars$o$0(){};
  }
class Var$c$1Instance implements Var$c$1{
  private Object o; Var$c$1Instance(Object o){ this.o= o; }
  public Object read$get$0(){ return o; }
  public Object mut$get$0(){ return o; }
  public Object mut$swap$1(Object p0){ var old=o;  o= p0; return old; }
}