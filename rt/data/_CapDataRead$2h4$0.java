package _base;

public final class _CapDataRead$2h4$0 implements CapDataRead$2h4$0{
  private final DataFiles.Root root;
  _CapDataRead$2h4$0(Object data){ root= DataFiles.root(DataFiles.readable, "data project", data); }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$paths$1(Object path){ return DataFiles.paths(root, path); }
  @Override public Object mut$read$1(Object path){ return DataFiles.read(root, path); }
}