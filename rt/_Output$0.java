package base;

import static base.Util.*;

import java.util.List;
public final class _Output$0 implements Output$0{
  public final Object mut$print$1(Object p0){ 
    print(toS(p0));
    return Void$0.instance;
  }
  public final Object mut$println$1(Object p0){ 
    print(toS(p0)+"\n");
    return Void$0.instance;
  }
  public final void print(String s){
    System.out.print(s);
  }
  public final Object mut$byte$1(Object o){
    byte b= ((Byte$0Instance)o).val();
    System.out.write(b);
    return Void$0.instance;
  }
  public final Object mut$bytes$1(Object o){
    if (o instanceof List$1Instance l){ l.val().forEach(this::mut$byte$1); }
    System.out.flush();
    return Void$0.instance;    
  }
}