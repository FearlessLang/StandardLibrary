package _base;

public record _CapLogReader$4yo$1(InMemoryLog$2kk$1 o) implements CapLogReader$4yo$1{
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$read$0(){ return o._readCopy(); }
  @Override public Object mut$consume$0(){ return o._consumeCopy(); }
  @Override public Object read$logName$0(){ return o.read$name$0(); }
}
