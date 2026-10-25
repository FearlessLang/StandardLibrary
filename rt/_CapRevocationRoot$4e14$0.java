package _base;
import static _base.Util.*;

public final class _CapRevocationRoot$4e14$0 implements CapRevocationRoot$4e14$0{
  private volatile int state= 0;
  @Override public Object mut$$hash$0(){
    if (state == 1){ return Void$o$0.instance; }
    throw nonDetErr(state == 0 ? "Revocation.start was not called yet." : "Revocation.revoke was called.");
  }
  @Override public Object mut$start$0(){
    if (state != 0){ throw nonDetErr("Revocation.start was called twice."); }
    state= 1;
    return Void$o$0.instance;
  }
  @Override public Object mut$revoke$0(){
    if (state != 1){ throw nonDetErr(state == 0 ? "Revocation.revoke was called before .start." : "Revocation.revoke was called twice."); }
    state= 2;
    return Void$o$0.instance;
  }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
}
