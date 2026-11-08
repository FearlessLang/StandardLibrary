package _base;

import static _base.Util.*;
import static java.nio.charset.StandardCharsets.UTF_8;

public final class _CapOutput$m8$0 implements CapOutput$m8$0{
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$print$1(Object p0){
    print(toS(p0));
    return Void$o$0.instance;
  }
  @Override public Object mut$println$1(Object p0){ 
    print(toS(p0)+"\n");
    return Void$o$0.instance;
  }
  @Override public Object mut$printU$1(Object p0){
    System.out.writeBytes(text(p0).getBytes(UTF_8));
    return Void$o$0.instance;
  }
  @Override public Object mut$printlnU$1(Object p0){
    System.out.writeBytes((text(p0)+"\n").getBytes(UTF_8));
    return Void$o$0.instance;
  }
  private static String text(Object u){
    var cps= ((UStr$s$0Instance)u).val();
    return new String(cps, 0, cps.length);
  }
  public void print(String s){
    System.out.print(s);
  }
  @Override public Object mut$byte$1(Object o){
    byte b= ((Byte$o$0Instance)o).val();
    System.out.write(b);
    return Void$o$0.instance;
  }
  @Override public Object mut$bytes$1(Object o){
    System.out.writeBytes(Byte$o$0Instance.unwrapAll(o));
    System.out.flush();
    return Void$o$0.instance;    
  }
}