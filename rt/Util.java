package base;

public class Util{
  public static Bool$0 bool(boolean b){ return b ? True$0.instance : False$0.instance; }
  public static Ordering$0 ord(int c){
    return c < 0 ? OrderingLt$0.instance
      : c == 0 ? OrderingEq$0.instance
      : OrderingGt$0.instance;
  }  
}