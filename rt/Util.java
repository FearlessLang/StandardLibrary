package base;

public class Util{
  public static Bool$0 bool(boolean b){ return b ? True$0.instance : False$0.instance; }
  public static Object ord(int i, Object mm){
    var m= (OrderMatch$1)mm;
    return i<0?m.mut$lt$0() : i==0? m.mut$eq$0() : m.mut$gt$0();
  }
  public static Opt$1 optEmpty(){ return Opt$1.instance; }
  public static Object optSome(Object x){ return Opts$0.instance.imm$$hash$1(x); }
  public static Error err(String msg){ return new Error(msg); }
}