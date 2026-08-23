package base;

import java.math.BigInteger;
import java.util.stream.LongStream;

import static base.Util.*;

class IntCache {
  private static final long min = -128;
  private static final long max = 127;

  private final static Int$0Instance[] intCache = LongStream.rangeClosed(min, max)
          .mapToObj(Int$0Instance::new)
          .toArray(Int$0Instance[]::new);

  static Int$0Instance getInt(long i) {
    if (i < min || i > max) {
      return new Int$0Instance(i);
    }
    // shift the index so that min is 0
    int index = (int) (i - min);
    return intCache[index];
  }
}

public record Int$0Instance(long val) implements Int$0,Norm$1{
  public static final long MAX_VALUE = Long.MAX_VALUE;
  public static final long MIN_VALUE = Long.MIN_VALUE;
  public static Int$0 instance(long val){ return IntCache.getInt(val); }

  static long unwrap(Object o){ return ((Int$0Instance)o).val; }
  private static long unsignedLongFromNat(Object o){ return ((Nat$0Instance)o).val(); }

  private static byte clampByte(long x){
    if (x < 0){ return 0; }
    if (x > 255){ return (byte)255; }
    return (byte)x;
  }

  private static long mulChecked(long a, long b) {
    try{ return Math.multiplyExact(a, b); }
    catch(ArithmeticException e){ throw err("Int.* overflow"); }
  }
  static boolean canSafelyConvertToDouble(long val) {
    // https://en.wikipedia.org/wiki/Double-precision_floating-point_format
    // The largest integer that can be exactly represented in a double is 2^53.
    if (-9007199254740993L <= val && val <= 9007199254740993L) {
      return true;
    }

    // Further optimisations exist here, I just can't be bothered right now...
    return val == (long) ((double) val);
  }


  @Override public Object imm$softNat$0(){ return Nat$0Instance.instance(val < 0 ? 0 : val); }
  @Override public Object imm$softByte$0(){ return Byte$0Instance.instance(clampByte(val)); }
  @Override public Object imm$softFloat$0(){ return Float$0Instance.instance((double)val); }
  @Override public Object imm$num$0(){ return Num$0Instance.instance(BigInteger.valueOf(val),BigInteger.ONE); }
  @Override public Object imm$nat$0(){ return val < 0 ? optEmpty() : optSome(Nat$0Instance.instance(val)); }
  @Override public Object imm$byte$0(){ return (val < 0 || val > 255) ? optEmpty() : optSome(Byte$0Instance.instance((byte)val)); }
  @Override public Object imm$float$0(){
    if (canSafelyConvertToDouble(val)) {
      return optSome(Float$0Instance.instance((float) val));
    }
    return optEmpty();
  }
  @Override public Object imm$getNat$0(){
    if (val < 0) {
      throw err("Int.getNat: cannot convert negative Int "+val+" to Nat");
    }
    return Nat$0Instance.instance(val);
  }
  @Override public Object imm$getByte$0(){
    if (val < 0) {
      throw err("Int.byteExact: cannot convert to Byte "+val+" is less than 0");
    }
    if (val > 255) {
      throw err("Int.byteExact: cannot convert to Byte "+val+" is greater than 255");
    }
    return Byte$0Instance.instance((byte)val);
  }
  @Override public Object imm$getFloat$0(){
    if (canSafelyConvertToDouble(val)) {
      return Float$0Instance.instance((float) val);
    }
    throw err(
  "Int.floatExact: cannot convert to Float "
        + val
        + " is too large to be represented as a Float without loss of precision"
    );
  }
  @Override public Object imm$$plus$1(Object p0){
    try{ return instance(Math.addExact(val, unwrap(p0))); }
    catch(ArithmeticException e){ throw err("Int.+ overflow"); }
  }
  @Override public Object imm$$dash$1(Object p0){
    try{ return instance(Math.subtractExact(val, unwrap(p0))); }
    catch(ArithmeticException e){ throw err("Int.- overflow"); }
  }
  @Override public Object imm$$slash$1(Object p0){
    long d=((Nat$0Instance)p0).val();
    return Num$0Instance.instance(
      BigInteger.valueOf(val),
      unsignedLongToBigInteger(d)
    );
  }
  @Override public Object imm$$star$1(Object p0){ return instance(mulChecked(val, unwrap(p0))); }
  @Override public Object imm$$star_star$1(Object p0) {
    long power = unsignedLongFromNat(p0);
    if (power == 0) { return Nat$0Instance.instance(1); }
    if (power == 1 || this.val == 1 || this.val == 0) { return this; }

    long result = 1;
    while (power > 0) {
      result = mulChecked(result, this.val);
      power -= 1;
    }

    return Int$0Instance.instance(result);
  }

  @Override public Object imm$abs$0(){
    // Long.MIN_VALUE correctly converted to Long.MAX_VALUE + 1
    return Nat$0Instance.instance(Math.abs(val));
  }

  @Override public Object imm$negAbs$0(){
    // Long.MIN_VALUE correctly converted to Long.MAX_VALUE + 1
    if (val < 0) {
      return this;
    }
    return Int$0Instance.instance(-val);
  }

  @Override public Object imm$sign$0() {
    if (val == 0L) {
      return Int$0Instance.instance(0L);
    }
    if (val < 0L) {
      return Int$0Instance.instance(-1L);
    }
    return Int$0Instance.instance(1L);
  }

  @Override public Object read$str$0(){ return Str$0Instance.instance((val < 0 ? "" : "+")+val); }

  @Override public Object read$imm$0(){ return this; }

  @Override public Object imm$getDiv$1(Object p0){
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
  public static long remainderWithUnsignedLong(long signed, long unsignedRemainder) {
    // if a % b = a if b> a,
    if (Long.compareUnsigned(unsignedRemainder, Long.MAX_VALUE) > 0) {
      return signed;
    }
    return signed % unsignedRemainder;
  }
  @Override public Object imm$getRem$1(Object p0){
    long d= unsignedLongFromNat(p0);
    if (d == 0L){ throw err("Int.rem: d==0"); }
    return instance(remainderWithUnsignedLong(val, d));
  }

  /**
   * Compute the modulo of this Int by the given Nat, returning a Nat.
   * This is the implementation used by python's % operator where negative numbers are converted into positive numbers.
   * e.g. -1 % 5 == 4, since -1 is congruent to 4 mod 5.

   * This is tricky because p0 is an unsigned long so we can't directly compare it.

   * We break this into four cases:
   * 1. len <= Long.MAX_VALUE: Safe to treat len as a signed long and just use floorMod.
   * 2. len > Long.MAX_VALUE , 0 <= val < Long.MIN_VALUE
   *  - val <= len, so val % len == val
   * 3. val < 0, |val| < len
   *  - val % len == len + val, since val is negative
   * 4. val < 0, |val| == len, only happens when val == Long.MIN_VALUE and len == Long.MAX_VALUE + 1
   *  - val % len == 0, since val is congruent to 0.
   */
  @Override public Object imm$getWrapIndex$1(Object p0){
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
    // Otherwise, val is negative, so we can
    // add len to it to get the correct result.
    return Nat$0Instance.instance(len + val);
  }
  @Override public Object imm$wrapIndex$1(Object p0){
    long len= unsignedLongFromNat(p0);
    if (len == 0L){ return optEmpty(); }
    if (Long.compareUnsigned(len, Long.MAX_VALUE) <= 0) {return optSome(Nat$0Instance.instance(Math.floorMod(val, len))); }
    if (val >= 0) { return optSome(Nat$0Instance.instance(val)); }
    return optSome(Nat$0Instance.instance(len + val));
  }
  @Override public Object imm$aluAddWrap$1(Object p0){ return instance(val + unwrap(p0)); }
  @Override public Object imm$aluSubWrap$1(Object p0){ return instance(val - unwrap(p0)); }
  @Override public Object imm$aluMulWrap$1(Object p0){ return instance(val * unwrap(p0)); }
  @Override public Object imm$aluDiv$1(Object p0){
    long x= unwrap(p0);
    if (x == 0){ throw err("Int.aluDiv: divByZero"); }
    return instance(val / x);
  }
  @Override public Object imm$aluRem$1(Object p0){
    long x= unwrap(p0);
    if (x == 0){ throw err("Int.aluRem: divByZero"); }
    return instance(val % x);
  }
  @Override public Object imm$aluShiftLeft$1(Object p0){ return instance(val << (unsignedLongFromNat(p0))); }
  @Override public Object imm$aluShiftRight$1(Object p0){ return instance(val >> (unsignedLongFromNat(p0))); }

  @Override public Object imm$aluXor$1(Object p0){ return instance(val ^ unwrap(p0)); }
  @Override public Object imm$aluAnd$1(Object p0){ return instance(val & unwrap(p0)); }
  @Override public Object imm$aluOr$1(Object p0){ return instance(val | unwrap(p0)); }
  @Override public Object imm$aluNat$0(){ return Nat$0Instance.instance(val); }
  @Override public Object imm$aluByte$0(){ return Byte$0Instance.instance((byte)val); }

  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(Long.compare(unwrap(p0), unwrap(p1)),p2); }

  @Override public Object imm$get$0(){ return this; }
  @Override public Object imm$norm$0(){ return this; }
}