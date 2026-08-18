package base;

import static base.Util.*;

public final class _InputCursor$2e8$0 implements InputCursor$2e8$0{
  @Override public final Object mut$iso$0(){ return this; }
  @Override public final Object mut$close$0(){ return this; }
  private static final InputCursorNode$12bs$0 node= new DemoNode();
  private static final InputCursor$2e8$0 tail= new Tail();
  @Override public Object mut$$hash$0(){ return optSome(node); }
  @Override public Object mut$next$0(){ return tail; }
  private static final class Tail implements InputCursor$2e8$0{
    @Override public final Object mut$iso$0(){ return this; }
    @Override public final Object mut$close$0(){ return this; }
    @Override public Object mut$$hash$0(){ return optEmpty(); }
    @Override public Object mut$next$0(){ return this; }
  }
  private static final class DemoNode implements InputCursorNode$12bs$0{
    @Override public final Object mut$iso$0(){ return this; }
    @Override public final Object mut$close$0(){ return this; }
    @Override public Object mut$label$0(){ return new Str$c$0Instance("demo.txt"); }
    @Override public Object mut$text$0(){ return optSome(new Str$c$0Instance("Hello world")); }
  }
}