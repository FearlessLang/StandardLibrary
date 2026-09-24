package _base;

import static _base.Util.*;

public record _LogReader$m8$1(InMemoryLog$2kk$1 o) implements LogReader$m8$1{
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  public _LogReader$m8$1{
    if (isGeneric(o)){ throw nonDetErr("Generic logs can not be read"); }
  }
  public Object mut$read$0(){ return o._readCopy(); }
  public Object mut$consume$0(){ return o._consumeCopy(); }
  public Object mut$logName$0(){ return o.imm$name$0(); }
}
