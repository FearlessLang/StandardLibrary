package base;
public interface Vars$o$0 extends Sealed$2o$0{
  default Object imm$$hash$1(Object p0){ return new _MagicVar(p0); }
  Vars$o$0 instance= new Vars$o$0(){};
  }
class _MagicVar implements Var$c$1{
  private Object o; _MagicVar(Object o){ this.o= o; }
  public Object read$get$0(){ return o; }
  public Object mut$get$0(){ return o; }
  public Object mut$swap$1(Object p0){ var old=o;  o= p0; return old; }
  public Object mut$set$1(Object p0){ o= p0; return Void$o$0.instance; }
}