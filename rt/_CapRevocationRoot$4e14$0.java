package _base;
import static _base.Util.*;

import java.util.concurrent.atomic.AtomicInteger;

public final class _CapRevocationRoot$4e14$0 implements CapRevocationRoot$4e14$0{
  private final AtomicInteger state= new AtomicInteger();
  @Override public Object mut$$hash$0(){
    var s= state.get();
    if (s == 1){ return Void$o$0.instance; }
    throw nonDetErr(s == 0 ? "Revocation.start was not called yet." : "Revocation.revoke was called.");
  }
  @Override public Object mut$start$0(){
    if (state.compareAndExchange(0, 1) == 2){ throw nonDetErr("Revocation.revoke was called."); }
    return Void$o$0.instance;
  }
  @Override public Object mut$revoke$0(){
    state.set(2);
    return Void$o$0.instance;
  }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
}
