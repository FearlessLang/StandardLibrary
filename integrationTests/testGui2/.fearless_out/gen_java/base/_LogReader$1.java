package base;

import static base.Util.*;

import java.util.ArrayList;
import java.util.List;

public record _LogReader$1(InMemoryLog$1 o,ArrayList<Object> log) implements LogReader$1{
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  public _LogReader$1{
    if (!o.getClass().getSimpleName().endsWith("$0")){ throw nonDetErr("Generic logs can not be read"); }
  }
  public Object mut$read$0(){
    if (log.isEmpty()){ return List$1.instance; }
    return new List$1Instance(List.copyOf(log)); 
  }
  public Object mut$consume$0(){
    if (log.isEmpty()){ return List$1.instance; }
    var res= new List$1Instance(List.copyOf(log));
    log.clear(); 
    return res;
  }
  public Object mut$logName$0(){
    return o.imm$name$0();
  }
}