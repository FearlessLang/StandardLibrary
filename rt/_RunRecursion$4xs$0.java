package _base;

import java.util.stream.Stream;

public interface _RunRecursion$4xs$0{
  _RunRecursion$4xs$0 instance= new _RunRecursion$4xs$0(){};
  default Object imm$$hash$2(Object family, Object f){
    var fam= (RunRecursion$4xs$0)family;
    if (Thread.currentThread() instanceof RunRecursionThread t && t.nests(fam)){ return t.inline(f); }
    return new RunRecursionThread(fam, f).result();
  }
}
final class RunRecursionThread extends Thread{
  final Class<?> family;
  final Object f;
  long depth= 1;
  Object res;
  Throwable err;
  RunRecursionThread(RunRecursion$4xs$0 family, Object f){
    super(null, null, "RunRecursion", Util.natToLong(family.imm$stackSize$0()));
    this.family= family.getClass();
    this.f= f;
    setDaemon(true);
  }
  boolean nests(RunRecursion$4xs$0 fam){
    return family == fam.getClass() && Long.compareUnsigned(depth, Util.natToLong(fam.imm$depth$0())) < 0;
  }
  Object inline(Object g){
    depth++;
    try{ return Util.callMF$1(g); }
    finally{ depth--; }
  }
  @Override public void run(){
    try{ res= Util.callMF$1(f); }
    catch(Throwable t){ err= t; }
  }
  Object result(){
    start();
    var interrupted= false;
    while (isAlive()){
      try{ join(); }
      catch(InterruptedException _){ interrupted= true; }
    }
    if (interrupted){ Thread.currentThread().interrupt(); }
    if (err == null){ return res; }
    err.setStackTrace(Stream.concat(Stream.of(err.getStackTrace()), Stream.of(new Throwable().getStackTrace())).limit(1024).toArray(StackTraceElement[]::new));
    return sneakyThrow(err);
  }
  @SuppressWarnings("unchecked")
  private static <E extends Throwable> Object sneakyThrow(Throwable t) throws E{ throw (E)t; }
}
