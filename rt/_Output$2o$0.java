package base;

import static base.Util.*;

public final class _Output$2o$0 implements Output$2o$0{
  @Override public final Object mut$iso$0(){ return this; }
  @Override public final Object mut$close$0(){ return this; }
  public final Object mut$print$1(Object p0){
    print(toS(p0));
    return Void$o$0.instance;
  }
  public final Object mut$println$1(Object p0){ 
    print(toS(p0)+"\n");
    return Void$o$0.instance;
  }
  public final void print(String s){
    System.out.print(s);
  }
  public final Object mut$byte$1(Object o){
    byte b= ((Byte$o$0Instance)o).val();
    System.out.write(b);
    return Void$o$0.instance;
  }
  public final Object mut$bytes$1(Object o){
    if (o instanceof List$o$1Instance l){ l.val().forEach(this::mut$byte$1); }
    System.out.flush();
    return Void$o$0.instance;    
  }
}