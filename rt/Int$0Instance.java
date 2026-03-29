package base;

import java.math.BigInteger;
import static base.Util.*;

public record Int$0Instance(long val) implements Int$0{
  public static Int$0 instance(long val){ return new Int$0Instance(val); }

  private static long i(Object o){ return ((Int$0Instance)o).val; }
  private static long unsignedLongFromNat(Object o){ return ((Nat$0Instance)o).val(); }

  private static byte clampByte(long x){
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
    // Long.MIN_VALUE correctly converted to Long.MAX_VALUE + 1
    return Nat$0Instance.instance(Math.abs(val));
  }
  @Override public Object imm$sqrt$0(){ return Float$0Instance.instance(Math.sqrt((double)val)); }

  /**
   * TODO: Why does this not use "-" for negative numbers?
   */
  @Override public Object read$str$0(){ return Str$0Instance.instance((val < 0 ? "" : "+")+val); }

  @Override public Object read$info$0(){ return Info$0.instance; }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object imm$clamp$2(Object p0, Object p1){
    long lo= i(p0), hi= i(p1);
    if (lo > hi){ throw err("Int.clamp: lo>hi"); }
    if (val < lo){ return instance(lo); }
    if (val > hi){ return instance(hi); }
    return this;
  }
  @Override public Object imm$div$1(Object p0){
    long d= unsignedLongFromNat(p0);
    if (d == 0L){ throw err("Int.div: d==0"); }

    // if a > b then a / b == 0.
    // We can't directly compare val and d since val is signed,
    // so Long.compareUnsigned would assume it to be a large negative number.
    // Since this is a signed num if d > Long.MAX_VALUE then it is > val and thus the result is 0.
    if (Long.compareUnsigned(d, Long.MAX_VALUE) > 0) {
      return instance(0L);
    }

    // In the range of unsigned longs, so we can just do the division.
    return instance(val / d);
  }
  @Override public Object imm$rem$1(Object p0){
    long d= unsignedLongFromNat(p0);
    if (d == 0L){ throw err("Int.rem: d==0"); }
    return instance(val % d);
  }
  @Override public Object imm$divExact$1(Object p0){
    long d= unsignedLongFromNat(p0);
    if (d == 0L){ return optEmpty(); }
    if ((val % d) != 0L){ return optEmpty(); }
    return optSome(imm$div$1(p0));
  }

  /**
   * Compute the modulo of this Int by the given Nat, returning a Nat.
   * This is the implementation used by python's % operator where negative numbers are converted into positive numbers.
   * e.g. -1 % 5 == 4, since -1 is congruent to 4 mod 5.
   *
   * This is tricky because p0 is an unsigned long so we can't directly compare it.
   *
   * We break this into four cases:
   * 1. len <= Long.MAX_VALUE: Safe to treat len as a signed long and just use floorMod.
   * 2. len > Long.MAX_VALUE , 0 <= val < Long.MIN_VALUE
   *  - val <= len, so val % len == val
   * 3. val < 0, |val| < len
   *  - val % len == len + val, since val is negative
   * 4. val < 0, |val| == len, only happens when val == Long.MIN_VALUE and len == Long.MAX_VALUE + 1
   *  - val % len == 0, since val is congruent to 0.
   */
  @Override public Object imm$wrapIndex$1(Object p0){
    long len= unsignedLongFromNat(p0);
    if (len == 0L){ throw err("Int.wrapIndex: len==0"); }
    // handle case where len cannot be represented as a signed long.
    if (Long.compareUnsigned(len, Long.MAX_VALUE) <= 0) {
      // safe to treat len as signed long
      return Nat$0Instance.instance(Math.floorMod(val, len));
    }

    // 0 <= val < len, so val % len == val
    if (val >= 0) {
      return Nat$0Instance.instance(val);
    }

    // Long.MIN_VALUE + (Long.MAX_VALUE + 1) == 0, so this handles 4.
    // Otherwise,val is negative, so we can
    // add len to it to get the correct result.
    return Nat$0Instance.instance(len + val);
  }

  @Override public Object imm$aluAddWrap$1(Object p0){ return instance(val + i(p0)); }
  @Override public Object imm$aluSubWrap$1(Object p0){ return instance(val - i(p0)); }
  @Override public Object imm$aluMulWrap$1(Object p0){ return instance(val * i(p0)); }
  @Override public Object imm$aluDiv$1(Object p0){
    long x= i(p0);
    if (x == 0){ throw err("Int.aluDiv: divByZero"); }
    return instance(val / x);
  }
  @Override public Object imm$aluRem$1(Object p0){
    long x= i(p0);
    if (x == 0){ throw err("Int.aluRem: divByZero"); }
    return instance(val % x);
  }
  @Override public Object imm$aluShiftLeft$1(Object p0){ return instance(val << (unsignedLongFromNat(p0))); }
  @Override public Object imm$aluShiftRight$1(Object p0){ return instance(val >> (unsignedLongFromNat(p0))); }

  @Override public Object imm$aluXor$1(Object p0){ return instance(val ^ i(p0)); }
  @Override public Object imm$aluAnd$1(Object p0){ return instance(val & i(p0)); }
  @Override public Object imm$aluOr$1(Object p0){ return instance(val | i(p0)); }
  @Override public Object imm$aluNat$0(){ return Nat$0Instance.instance(val); }
  @Override public Object imm$aluByte$0(){ return Byte$0Instance.instance((byte)val); }
  
  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(Long.compare(i(p0),i(p1)),p2); }
}