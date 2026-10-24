package _base;

public record _LogReader$m8$1(InMemoryLog$2kk$1 o) implements LogReader$m8$1{
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$read$0(){ return o._readCopy(); }
  @Override public Object mut$consume$0(){ return o._consumeCopy(); }
  public Object mut$logName$0(){ return o.imm$name$0(); }
}
