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
  public static Error err(String msg){
    return (Error)Error$0.instance.imm$nonDeterministic$1(new Str$0Instance(msg));
    }
  public static String toS(Object o){return ((Str$0Instance)((ToStr$0)o).read$str$0()).val(); }
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
  private static final Integer lt= -1, eq= 0, gt= 1;//avoid some access to the cached integer maps
  private static final OrderMatch$1 cmpM= new OrderMatch$1(){
    @Override public Object mut$lt$0(){ return lt; }
    @Override public Object mut$eq$0(){ return eq; }
    @Override public Object mut$gt$0(){ return gt; }
  };
  //OrderHashBy$1 implements OrderHashBy$2; $1 or $2 is the number of generics
  public static int cmp(OrderHashBy$2 by,Object a,Object b){//so this is the more general method
    var ohA= (Order$1)((OrderHash$1)by.imm$$hash$1(a));
    var ohB= (Order$1)((OrderHash$1)by.imm$$hash$1(b));
    return (Integer)ohA.read$cmp$3(ohA.read$close$0(),ohB.read$close$0(),cmpM);
  }
  public static final class MapKey{
    public final OrderHash$1 ord; // OrderHash[K0] closed at this key's projection
    public final Object key;      // representative K (first inserted)
    public final Object close;    // K0
    public final int hc;
    public MapKey(OrderHashBy$1 oh,Object k){
      key= k;
      ord= (OrderHash$1)oh.imm$$hash$1(k);
      close= ((Order$1)ord).read$close$0();
      hc= natToInt(ord.read$hash$0());
  }
  @Override public int hashCode(){ return hc; }
  @Override public boolean equals(Object o){
    if (!(o instanceof MapKey x)){ assert false; return false; }
    return isTrue(((Order$1)ord).read$$eq_eq$1(x.close));
    }
  }
  public static MapKey mapKey(OrderHashBy$1 oh,Object k){ return new MapKey(oh,k); }
  
  public static RuntimeException deterministic(Info$0 i){ return new Deterministic(i); }
  public static Error nonDeterministic(Info$0 i){ return new NonDeterministic(i); }
  public static void topLevel(Runnable r){
    try{r.run();}
    catch(Deterministic d){ printInfo(d.i); }
    catch(NonDeterministic d){ printInfo(d.i); }
    catch(Throwable t){ t.printStackTrace();}
  }
  public static void printInfo(Info$0 i){
    var map= ((Map$2Instance)i.imm$map$0()).elems();
    map.entrySet().stream()
      .filter(e->is(e.getKey(),"msg")).forEach(e->printInfoMsg("","",(Info$0)e.getValue()));
    map.entrySet().stream()
      .filter(e->is(e.getKey(),"list")).forEach(e->printInfoList((Info$0)e.getValue()));

  }
  public static boolean is(MapKey k,String label){
    return k.key instanceof Str$0Instance s && s.val().equals(label);
    }
  public static void printInfoMsg(String prefix, String indent, Info$0 i){
    var msg= ((Str$0Instance)i.imm$msg$0()).val();
    msg = msg.replace("\n","\n"+indent);
    System.err.println(prefix+msg);
  }
  public static void printInfoList(Info$0 i){ 
    var fList= (List$1)i.imm$list$0();
    if (isTrue(fList.read$isEmpty$0())){
      System.err.println("\nEmpty stack trace");  
      return;
    }
    System.err.println("\nStack trace:");
    var jList= ((List$1Instance)fList).val();
    for(var e : jList){ printInfoMsg("- ","  ",(Info$0)e); }
  }
}
@SuppressWarnings("serial")
class Deterministic extends RuntimeException{
  Info$0 i;Deterministic(Info$0 i){this.i= i;}
}
@SuppressWarnings("serial")
class NonDeterministic extends Error{
  Info$0 i;NonDeterministic(Info$0 i){this.i= i;}
}