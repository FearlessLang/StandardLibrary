package base;

import static base.Util.*;

import java.util.ArrayList;

public final class _CapTry$2s$0 implements CapTry$2s$0{
  @Override public final Object mut$iso$0(){ return this; }
  @Override public final Object mut$close$0(){ return this; }
  public final Object mut$$hash$1(Object p0){ return actionLazy(()->callF$1(p0)); }
  public final Object mut$$hash$2(Object p0, Object p1){ return actionLazy(()->callF$2(p1,p0)); }
  public final Action$2o$1 actionLazy(java.util.function.Supplier<Object> s){
    return new Action$2o$1(){
      public Object mut$run$1(Object p0){
        var m= (ActionMatch$2ds$2)p0;
        Object res; try{ res= s.get(); }
        catch(Deterministic d){ return m.mut$info$1(addStackInfo(d.i,d)); }
        catch(NonDeterministic d){ return m.mut$info$1(addStackInfo(d.i,d)); }
        catch(Cancelled c){ throw c; }
        catch(Throwable t){ 
          var msg= t.getClass().getSimpleName()+"\n"+t.getMessage();
          return m.mut$info$1(addStackInfo(new Str$c$0Instance(msg).read$info$0(),t));
        }
        return m.mut$ok$1(res);
      }
    };
  }
   private static Object addStackInfo(Object info, Throwable t){
    return ((Info$o$0)info).imm$$plus$1(stackTraceOf(aboveHere(t.getStackTrace())).read$info$1(dtId));
  }
  private static StackTraceElement[] aboveHere(StackTraceElement[] st){
    var here= new Throwable().getStackTrace();
    int n= st.length, m= here.length;
    while (n > 0 && m > 0 && same(st[n-1], here[m-1])){ n-= 1; m-= 1; }
    return java.util.Arrays.copyOf(st, n);
  }
  private static boolean same(StackTraceElement a, StackTraceElement b){ return a.getClassName().equals(b.getClassName()) && a.getMethodName().equals(b.getMethodName()); }
  private static final DataTypeBy$17m$3 dtId= new DataTypeBy$17m$3(){
    @Override public Object imm$$hash$1(Object p0){ return p0; }
  };
  public final Object mut$currentStackTrace$0(){ return stackTraceOf(new Throwable().getStackTrace()); }
  public final static List$o$1Instance stackTraceOf(StackTraceElement[] st){
    var al= new ArrayList<>();
    for(var e : st){
      var s= base._Throw$1c$0.frameData(e);
      if(s == null){ continue; }
      al.add(s);
    }
    return new List$o$1Instance(al);
  }
}