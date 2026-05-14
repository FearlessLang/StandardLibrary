package base;
public interface Vars$0 extends Sealed$0{
  default Object imm$$hash$1(Object p0){ return new _MagicVar(p0); }
  Vars$0 instance= new Vars$0(){};
  }
class _MagicVar implements Var$1{
  private Object o; _MagicVar(Object o){ this.o= o; }
  public Object read$get$0(){ return o; }
  public Object mut$get$0(){ return o; }
  public Object mut$swap$1(Object p0){ var old=o;  o= p0; return old; }
  public Object mut$set$1(Object p0){ o= p0; return Void$0.instance; }
}