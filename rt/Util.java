package base;

public class Util{
  public static Bool$0 bool(boolean b){ return b ? True$0.instance : False$0.instance; }
  public static Ordering$0 ord(int c){
    return c < 0 ? OrderingLt$0.instance
      : c == 0 ? OrderingEq$0.instance
      : OrderingGt$0.instance;
  }
  public static Opt$1 optEmpty(){ return Opt$1.instance; }
  public static Object optSome(Object x){ return Opts$0.instance.imm$$hash$1(x); }
  public static Error err(String msg){ return new Error(msg); }
}