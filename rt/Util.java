package base;

public class Util{
  public static Bool$0 bool(boolean b){ return b ? True$0.instance : False$0.instance; }
  public static boolean isTrue(Object b){ return b == True$0.instance; }
  public static Object ord(int i, Object mm){
    var m= (OrderMatch$1)mm;
    return i<0?m.mut$lt$0() : i==0? m.mut$eq$0() : m.mut$gt$0();
  }
  public static Opt$1 optEmpty(){ return Opt$1.instance; }
  public static Object optSome(Object x){ return Opts$0.instance.imm$$hash$1(x); }
  public static Error err(String msg){ return new Error(msg); }

  public static int natToInt(Object n){
    int res= ((Nat$0Instance)n).val();
    assert res >= 0;
    return res;
  }
  public static Object intToNat(int i){
    assert i >= 0;
    return new Nat$0Instance(i);
  }
  public static Object callMF$1(Object f){ return ((MF$1)f).mut$$hash$0(); }
  public static Object callMF$2(Object f, Object x){ return ((MF$2)f).mut$$hash$1(x); }
  public static Object callMF$3(Object f,Object x,Object y){ return ((MF$3)f).mut$$hash$2(x,y); }
  public static Object callF$1(Object f){ return ((F$1)f).read$$hash$0(); }
  public static Object callF$2(Object f, Object x){ return ((F$2)f).read$$hash$1(x); }
  public static Object callF$3(Object f,Object x,Object y){ return ((F$3)f).read$$hash$2(x,y); }


  public static void check(boolean ok, String msg){
    if (!ok){ throw err(msg); }
  }
  public static final class MapKey{
    public final OrderHash$1 ord; // OrderHash[K0] closed at this key's projection
    public final Object key;      // representative K (first inserted)
    public final Object close;    // K0
    public final int hc;
    public MapKey(Object oh,Object k){
      key= k;
      ord= (OrderHash$1)((OrderHashBy$2)oh).imm$$hash$1(k);
      close= ((Order$1)ord).read$close$0();
      hc= natToInt(ord.read$hash$1(Hasher$0.instance));
  }
  @Override public int hashCode(){ return hc; }
  @Override public boolean equals(Object o){
    if (!(o instanceof MapKey x)){ assert false; return false; }
    return isTrue(((Order$1)ord).read$$eq_eq$1(x.close));
  }
  }
  public static MapKey mapKey(Object oh,Object k){ return new MapKey(oh,k); }
}
