package base;

import static base.Util.*;

public final class _InputCursor$0 implements InputCursor$0{
  private static final InputCursorNode$0 node= new DemoNode();
  private static final InputCursor$0 tail= new Tail();
  @Override public Object mut$$hash$0(){ return optSome(node); }
  @Override public Object mut$next$0(){ return tail; }
  private static final class Tail implements InputCursor$0{
    @Override public Object mut$$hash$0(){ return optEmpty(); }
    @Override public Object mut$next$0(){ return this; }
  }
  private static final class DemoNode implements InputCursorNode$0{
    @Override public Object mut$label$0(){ return new Str$0Instance("demo.txt"); }
    @Override public Object mut$text$0(){ return optSome(new Str$0Instance("Hello world")); }
  }
}