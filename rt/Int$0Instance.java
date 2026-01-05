package base;

import java.math.BigInteger;
import static base.Util.*;
public record Int$0Instance(int val) implements Int$0{
  public static Int$0 instance(int val){ return new Int$0Instance(val); }

  private static int i(Object o){ return ((Int$0Instance)o).val; }
  private static int natBits(Object o){ return ((Nat$0Instance)o).val(); }
  private static long natU(Object o){ return Integer.toUnsignedLong(natBits(o)); }

  private static byte clampByte(int x){
    if (x < 0){ return 0; }
    if (x > 255){ return (byte)255; }
    return (byte)x;
  }
  @Override public Object imm$nat$0(){ return Nat$0Instance.instance(val < 0 ? 0 : val); }
  @Override public Object imm$byte$0(){ return Byte$0Instance.instance(clampByte(val)); }
  @Override public Object imm$float$0(){ return Float$0Instance.instance((double)val); }
  @Override public Object imm$num$0(){ return Num$0Instance.instance(BigInteger.valueOf(val),BigInteger.ONE); }
  @Override public Object imm$natExact$0(){ return val < 0 ? optEmpty() : optSome(Nat$0Instance.instance(val)); }
  @Override public Object imm$byteExact$0(){ return (val < 0 || val > 255) ? optEmpty() : optSome(Byte$0Instance.instance((byte)val)); }

  @Override public Object imm$$plus$1(Object p0){
    try{ return instance(Math.addExact(val,i(p0))); }
    catch(ArithmeticException e){ throw err("Int.+ overflow"); }
  }
  @Override public Object imm$$dash$1(Object p0){
    try{ return instance(Math.subtractExact(val,i(p0))); }
    catch(ArithmeticException e){ throw err("Int.- overflow"); }
  }
  @Override public Object imm$$star$1(Object p0){
    try{ return instance(Math.multiplyExact(val,i(p0))); }
    catch(ArithmeticException e){ throw err("Int.* overflow"); }
  }
  @Override public Object imm$abs$0(){
    if (val == Integer.MIN_VALUE){ throw err("Int.abs overflow"); }
    return instance(Math.abs(val));
  }
  @Override public Object imm$sqrt$0(){ return Float$0Instance.instance(Math.sqrt((double)val)); }
  @Override public Object read$str$0(){ return Str$0Instance.instance(Integer.toString(val)); }
  @Override public Object read$info$0(){ return Info$0.instance; }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object imm$clamp$2(Object p0, Object p1){
    int lo= i(p0), hi= i(p1);
    if (lo > hi){ throw err("Int.clamp: lo>hi"); }
    if (val < lo){ return instance(lo); }
    if (val > hi){ return instance(hi); }
    return this;
  }
  @Override public Object imm$div$1(Object p0){
    long d= natU(p0);
    if (d == 0L){ throw err("Int.div: d==0"); }
    return instance((int)(((long)val) / d));
  }
  @Override public Object imm$rem$1(Object p0){
    long d= natU(p0);
    if (d == 0L){ throw err("Int.rem: d==0"); }
    return instance((int)(((long)val) % d));
  }
  @Override public Object imm$divExact$1(Object p0){
    long d= natU(p0);
    if (d == 0L){ return optEmpty(); }
    if ((((long)val) % d) != 0L){ return optEmpty(); }
    return optSome(instance((int)(((long)val) / d)));
  }
  @Override public Object imm$wrapIndex$1(Object p0){
    long len= natU(p0);
    if (len == 0L){ throw err("Int.wrapIndex: len==0"); }
    long m= ((long)val) % len;
    if (m < 0L){ m += len; }
    return Nat$0Instance.instance((int)m);
  }
  @Override public Object imm$aluAddWrap$1(Object p0){ return instance(val + i(p0)); }
  @Override public Object imm$aluSubWrap$1(Object p0){ return instance(val - i(p0)); }
  @Override public Object imm$aluMulWrap$1(Object p0){ return instance(val * i(p0)); }
  @Override public Object imm$aluDiv$1(Object p0){
    int x= i(p0);
    if (x == 0){ throw err("Int.aluDiv: divByZero"); }
    return instance(val / x);
  }
  @Override public Object imm$aluRem$1(Object p0){
    int x= i(p0);
    if (x == 0){ throw err("Int.aluRem: divByZero"); }
    return instance(val % x);
  }
  @Override public Object imm$aluShiftLeft$1(Object p0){ return instance(val << (natBits(p0))); }
  @Override public Object imm$aluShiftRight$1(Object p0){ return instance(val >> (natBits(p0))); }

  @Override public Object imm$aluXor$1(Object p0){ return instance(val ^ i(p0)); }
  @Override public Object imm$aluAnd$1(Object p0){ return instance(val & i(p0)); }
  @Override public Object imm$aluOr$1(Object p0){ return instance(val | i(p0)); }
  @Override public Object imm$aluNat$0(){ return Nat$0Instance.instance(val); }
  @Override public Object imm$aluByte$0(){ return Byte$0Instance.instance((byte)val); }
  
  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(Integer.compare(i(p0),i(p1)),p2); }
}