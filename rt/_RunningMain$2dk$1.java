package _base;

import static _base.Util.*;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public final class _RunningMain$2dk$1 implements RunningMain$2dk$1{
  private final String name;
  private final Process p;
  private final Thread out;
  private final Thread err;
  private final StringBuilder tail= new StringBuilder();
  private volatile boolean surelyTerminated;
  _RunningMain$2dk$1(String name, String names, Optional<String> arg){
    this.name= name;
    var marker= _CapProcess$18g$0.marker();
    System.out.flush();
    System.err.flush();
    try{
      p= new ProcessBuilder(_CapProcess$18g$0.command(name, names, marker, arg.isPresent())).start();
      if (arg.isPresent()){ p.getOutputStream().write((arg.get()+"\n").getBytes(UTF_8)); p.getOutputStream().flush(); }
    }
    catch (IOException e){ throw nonDetErr("The process running "+name+" could not be started: "+e.getClass().getSimpleName()); }
    out= Thread.startVirtualThread(() -> _CapProcess$18g$0.forward(p.getInputStream()));
    err= Thread.startVirtualThread(() -> _CapProcess$18g$0.forward(p.getErrorStream(), marker, tail));
  }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$await$0(){
    running("await");
    try{ p.waitFor(); }
    catch (InterruptedException e){ throw interrupted(); }
    return ended();
  }
  @Override public Object mut$awaitUpTo$1(Object time){
    running("awaitUpTo");
    boolean done;
    try{ done= p.waitFor(natToLong(((Duration$ao$0)time).read$get$0()), TimeUnit.MICROSECONDS); }
    catch (InterruptedException e){ throw interrupted(); }
    if (!done){ return optEmpty(); }
    return optSome(ended());
  }
  @Override public Object mut$terminate$0(){
    running("terminate");
    if (!p.isAlive()){ return ended(); }
    p.descendants().forEach(ProcessHandle::destroyForcibly);
    p.destroyForcibly();
    try{ p.waitFor(); }
    catch (InterruptedException e){ throw interrupted(); }
    joined();
    return Void$o$0.instance;
  }
  private void running(String m){
    if (surelyTerminated){ throw detErr("RunningMain."+m+" was called on a surely terminated RunningMain."); }
  }
  private void joined(){
    try{ out.join(); err.join(); }
    catch (InterruptedException e){ throw interrupted(); }
    finally{
      surelyTerminated = true;
      try{ p.getOutputStream().close(); }
      catch (IOException _){}
    }
  }
  private Error interrupted(){ return nonDetErr("The process running "+name+" was interrupted."); }
  private Object ended(){
    joined();
    var text= tail.toString();
    var nl= text.indexOf('\n');
    if (nl < 0){ throw nonDetErr("The process running "+name+" ended with "+p.exitValue()+" before its main ended."); }
    var kind= text.substring(0, nl);
    var rest= text.substring(nl+1).stripTrailing();
    if (kind.equals("ok") && rest.isEmpty()){ return Void$o$0.instance; }
    var info= (Info$o$0)Infos$1c$0.instance.imm$fromStr$1(new Str$c$0Instance(rest));
    if (kind.equals("ok")){ return info; }
    if (kind.equals("det")){ throw deterministic(info); }
    assert kind.equals("nondet");
    throw nonDeterministic(info);
  }
}
