package base;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
public class Util{
  private static final AtomicBoolean parentLifelineStarted= new AtomicBoolean();
  public static void installParentLifeline(){
    //This allows to 'disable' this check if there is no master process
    if (!"stdin".equals(System.getProperty("fearless.parentLifeline"))){ return; }
    //makes sure this truly happens only one time
    if (!parentLifelineStarted.compareAndSet(false,true)){ return; }
    var t= new Thread(Util::watchParentLifeline,"FearlessParentLifeline");
    t.setDaemon(true);
    t.start();
  }
  private static void watchParentLifeline(){
    try{
      while(System.in.read()!=-1){}
      parentGone();
    }
    catch(java.io.IOException _){ parentGone(); }
  }
  private static void parentGone(){
    int code= 121;
    long haltMs= 2000L;
    var halter= new Thread(() -> {
      sleepUninterruptibly(haltMs);
      Runtime.getRuntime().halt(code);
    },"FearlessParentLifelineHalt");
    halter.setDaemon(true);
    halter.start();
    Runtime.getRuntime().exit(code);
  }
  private static void sleepUninterruptibly(long millis){
    boolean interrupted= false;
    try{
      long end= System.nanoTime()+java.util.concurrent.TimeUnit.MILLISECONDS.toNanos(millis);
      for(;;){
        long left= end-System.nanoTime();
        if (left<=0){ return; }
        try{
          Thread.sleep(java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(left));
          return;
        }
        catch(InterruptedException e){ interrupted= true; }
      }
    }
    finally{ if (interrupted){ Thread.currentThread().interrupt(); } }
  }
  public static Bool$o$0 bool(boolean b){ return b ? True$o$0.instance : False$1c$0.instance; }
  public static boolean isTrue(Object b){ return b == True$o$0.instance; }
  public static Object ord(int i, Object mm){
    var m= (OrderMatch$174$1)mm;
    return i<0?m.mut$lt$0() : i==0? m.mut$eq$0() : m.mut$gt$0();
  }
  public static Opt$c$1 optEmpty(){ return Opt$c$1.instance; }
  public static Opt$c$1 optSome(Object x){ return (Opt$c$1) Opts$o$0.instance.imm$$hash$1(x); }
  public static Opt$c$1 optNullable(Object o) {
      if (o == null) {return optEmpty();}
      return optSome(o);
  }
  public static Opt$c$1 toOpt(Optional<?> opt) {
    return optNullable(opt.orElse(null));
  }
  public static Error nonDetErr(String msg){
    return (Error)Error$1c$0.instance.imm$nonDeterministic$1(new Str$c$0Instance(msg));
    }
  public static Error err(String msg){//TODO: eventually all calls to .err will need to take a decision det/non det.
    return detErr(msg);
    }
  public static Error detErr(String msg) {
    var info = base.Infos$1c$0.instance.imm$msg$1(new Str$c$0Instance(msg));
    return (Error) Error$1c$0.instance.imm$$bang$1(info);
  }
  public static BigInteger unsignedLongToBigInteger(long x){
    return x >= 0
      ? BigInteger.valueOf(x)
      : BigInteger.valueOf(x & Long.MAX_VALUE).setBit(63);
  }
  public static String toS(Object o){return ((Str$c$0Instance)((ToStr$1g$0)o).read$str$0()).val(); }
  public static String toStringBy(ToStrBy$5u$1 by, Object p0) {
    return toS(by.imm$$hash$1(p0));
  }
  public static long natToLong(Object n){
    return ((Nat$c$0Instance)n).val();
  }
  public static long intToLong(Object n){
    return ((Int$c$0Instance)n).val();
  }
  public static int natToInt(Object n){
    long nat = ((Nat$c$0Instance)n).val();
    if (Long.compareUnsigned(nat, Integer.MAX_VALUE) > 0) {
      throw err("Nat "+Long.toUnsignedString(nat)+"is too large to represented as an integer");
    }
    return (int) nat;
  }

  public static Object callMF$1(Object f){ return ((MF$7$1)f).mut$$hash$0(); }
  public static Object callMF$2(Object f, Object x){ return ((MF$7$2)f).mut$$hash$1(x); }
  public static Object callMF$3(Object f,Object x,Object y){ return ((MF$7$3)f).mut$$hash$2(x,y); }
  public static Object callF$1(Object f){ return ((F$3$1)f).read$$hash$0(); }
  public static Object callF$2(Object f, Object x){ return ((F$3$2)f).read$$hash$1(x); }
  public static Object callF$3(Object f,Object x,Object y){ return ((F$3$3)f).read$$hash$2(x,y); }

  public static void check(boolean ok, String msg){
    if (!ok){ throw err(msg); }
  }
  private static final Integer lt= -1, eq= 0, gt= 1;//avoid some access to the cached integer maps
  private static final OrderMatch$174$1 cmpM= new OrderMatch$174$1(){
    @Override public Object mut$lt$0(){ return lt; }
    @Override public Object mut$eq$0(){ return eq; }
    @Override public Object mut$gt$0(){ return gt; }
  };
  public static int cmp(OrderBy$5e$2 by,Object a,Object b){//so this is the more general method
    var ohA= (Order$1c$1)by.imm$$hash$1(a);
    var ohB= (Order$1c$1)by.imm$$hash$1(b);
    return (Integer)ohA.read$cmp$3(ohA.read$close$0(),ohB.read$close$0(),cmpM);
  }
  public static Comparator<Object> toComparator(OrderBy$5e$2 ordering) {
    return (a, b) -> cmp(ordering, a, b);
  }
  public static final class MapKey{
    public final OrderHash$lk$1 ord; // OrderHash[K0] closed at this key's projection
    public final Object key;      // representative K (first inserted)
    public final Object close;    // K0
    public final long hc;
    public MapKey(OrderHashBy$2ea$2 oh,Object k){
      key= k;
      ord= (OrderHash$lk$1)oh.imm$$hash$1(k);
      close= ((Order$1c$1)ord).read$close$0();
      hc= natToLong(ord.read$hash$0());
  }
  @Override public int hashCode(){ return Long.hashCode(hc); }
  @Override public boolean equals(Object o){
    if (!(o instanceof MapKey x)){ assert false; return false; }
    return isTrue(((Order$1c$1)ord).read$$eq_eq$1(x.close));
    }
  }
  public static MapKey mapKey(OrderHashBy$2ea$2 oh,Object k){ return new MapKey(oh,k); }
  
  public static Deterministic deterministic(Info$o$0 i){ return new Deterministic(i); }
  public static NonDeterministic nonDeterministic(Info$o$0 i){ return new NonDeterministic(i); }
  public static void topLevel(Runnable r){
    try{r.run();}
    catch(Deterministic d){ printInfo(d.i,d); }
    catch(NonDeterministic d){ printInfo(d.i,d); }
    catch(Throwable t){ t.printStackTrace();}
  }
  public static void printInfo(Info$o$0 i, RuntimeException d){
    printInfoMsg("","",i);
    var map= ((Map$c$2Instance)i.imm$map$0()).elems();
    map.entrySet().stream()
      .filter(e->is(e.getKey(),"msg")).forEach(e->printInfoMsg("","",(Info$o$0)e.getValue()));
    //map.entrySet().stream()
    //  .filter(e->is(e.getKey(),"list")).forEach(e->printInfoList((Info$o$0)e.getValue()));
    var st= d.getStackTrace();
    for(int j= 3; j < st.length; j += 1){
      var stj= _Throw$1c$0.frameData(st[j]);
      if (stj != null){ System.err.print(_Throw$1c$0.fmtFrame(stj)+"\n"); }
    }
  }
  public static boolean is(MapKey k,String label){
    return k.key instanceof Str$c$0Instance s && s.val().equals(label);
    }
  public static void printInfoMsg(String prefix, String indent, Info$o$0 i){
    var msg= ((Str$c$0Instance)i.imm$msg$0()).val();
    msg = msg.replace("\n","\n"+indent);
    System.err.print(prefix+msg+"\n");
  }
  public static void printInfoList(Info$o$0 i){ 
    var fList= (List$o$1)i.imm$list$0();
    if (isTrue(fList.read$isEmpty$0())){
      System.err.print("\nEmpty stack trace\n");
      return;
    }
    System.err.print("\nStack trace:\n");
    var jList= ((List$o$1Instance)fList).val();
    for(var e : jList){ printInfoMsg("- ","  ",(Info$o$0)e); }
  }
  public static RuntimeException asFearlessError(Throwable t){
    for (var c= t; c != null; c = c.getCause()){
      if (c instanceof Deterministic d){ return d; }
      if (c instanceof NonDeterministic n){ return n; }
    }
    return nonDeterministic((Info$o$0)Infos$1c$0.instance.imm$msg$1(new Str$c$0Instance(javaErrMsg(t))));
}
  private static String javaErrMsg(Throwable t){
    var msg= t.getMessage();
    if (msg == null || msg.isBlank()){ return "Backend exception: "+t.getClass().getName(); }
    return "Backend exception: "+t.getClass().getName()+": "+msg;
  }
}
@SuppressWarnings("serial")
class Deterministic extends RuntimeException{
  Info$o$0 i;Deterministic(Info$o$0 i){this.i= i;}
}
@SuppressWarnings("serial")
class NonDeterministic extends RuntimeException{
  Info$o$0 i;NonDeterministic(Info$o$0 i){this.i= i;}
}