package _base;

import static _base.Util.*;

import java.util.ArrayList;
import java.util.function.Supplier;

public final class _CapTry$2s$0 implements CapTry$2s$0{
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$$hash$1(Object p0){ return once(() -> outcome(() -> callF$1(p0))); }
  @Override public Object mut$$hash$2(Object p0, Object p1){ return once(() -> outcome(() -> callF$2(p1, p0))); }
  static Object outcome(Supplier<Object> s){
    try{ return ok(s.get()); }
    catch (Deterministic d){ return info(addStackInfo(d.i, d)); }
    catch (NonDeterministic d){ return info(addStackInfo(d.i, d)); }
    catch (EndedByManager e){ throw e; }
    catch (Throwable t){ return info(addStackInfo(new Str$c$0Instance(javaErrMsg(t)).read$info$0(), t)); }
  }
   static Object addStackInfo(Object info, Throwable t){
    return Infos$1c$0.instance.imm$map$4(new Str$c$0Instance("info"), info, new Str$c$0Instance("stack"), stackTraceOf(t.getStackTrace()).read$info$1(dtId));
  }
  private static final DataTypeBy$17m$3 dtId= new DataTypeBy$17m$3(){
    @Override public Object imm$$hash$1(Object p0){ return p0; }
  };
  @Override public Object mut$currentStackTrace$0(){ return stackTraceOf(new Throwable().getStackTrace()); }
  public static List$o$1Instance stackTraceOf(StackTraceElement[] st){
    return new List$o$1Instance(new ArrayList<>(_Throw$1c$0.frames(st)));
  }
}