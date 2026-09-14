package base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.LongStream;

import static base.Util.*;

class NatCache {
  private static final long max = 255L;
  private static final Nat$c$0Instance[] natCache = LongStream.rangeClosed(0, max)
          .mapToObj(Nat$c$0Instance::new)
          .toArray(Nat$c$0Instance[]::new);

  static Nat$c$0Instance getNat(long i) {
    if (Long.compareUnsigned(i, max) > 0) {
      return new Nat$c$0Instance(i);
    }
    return natCache[(int) i];
  }
}

/**
 * Natural numbers are stored in 64 bits
 * They represent the numbers >= 0;
 * @param val
 */
public record Nat$c$0Instance(long val) implements Nat$c$0,Norm$o$1 {

  /**
   * The maximum unsigned value that can be represented in a long (2^64 - 1)
   */
  public static final long MAX_UNSIGNED_VALUE = -1;
  public static final double MAX_UNSIGNED_VALUE_FLOAT = 18446744073709551615.0d;
  public static Nat$c$0 instance(long val){ return NatCache.getNat(val); }

  public static boolean overflowsLong(long unsignedLong){
    return Long.compareUnsigned(unsignedLong, Long.MAX_VALUE) > 0;
  }
  private static long n(Object o){ return ((Nat$c$0Instance)o).val; }
  private static long i(Object o){ return ((Int$c$0Instance)o).val(); }
  private static String overflowMsg(String op, long a, long b) {
    return "Nat " + op + ": overflow " + Long.toUnsignedString(a) + " " + op + " " + Long.toUnsignedString(b) + " is greater than " + Long.toUnsignedString(MAX_UNSIGNED_VALUE);
  }
  private static String underflowMsg(String op, long a, long b) {
    return "Nat " + op + ": underflow " + Long.toUnsignedString(a) + " " + op + " " + Long.toUnsignedString(b) + " is less than 0";
  }
  /**
   * For a long to overflow a + b has to be greater than Long.MAX_VALUE
   * We don't need to worry about underflow
   * if a + b > Long.MAX_VALUE
   * then a > Long.MAX_VALUE - b
   * (and b > Long.MAX_VALUE - a, but we only need to check one of them)
   */
  private static long addChecked(long a, long b){
    boolean overflow = Long.compareUnsigned(a, MAX_UNSIGNED_VALUE - b) > 0;
    if (overflow) { throw nonDetErr(overflowMsg("+", a, b)); }
    return a + b;
  }
  /**
   * Nat subtraction can only underflow if a < b,
   * since we are working with unsigned numbers
   */
  private static long subChecked(long a, long b){
    if (Long.compareUnsigned(a, b) < 0){ throw nonDetErr(underflowMsg("-", a, b)); }
    return a - b;
  }

  /**
   * Overflow if a * b > Long.MAX_VALUE
   * => a > Long.MAX_VALUE / b
   */
  private static long mulChecked(long a, long b){
    if (a == 0 || b == 0) {return 0;}
    boolean overflow = Long.compareUnsigned(a, Long.divideUnsigned(MAX_UNSIGNED_VALUE, b)) > 0;
    if (overflow){ throw nonDetErr(overflowMsg("*", a, b));
    }
    return a * b;
  }

  /**
   * Unwraps the given Nat$c$0Instance into an unsigned long
   */
  public static long unwrap(Object p0) {
    return ((Nat$c$0Instance) p0).val;
  }

  public static long getSignedLong(Object p0) {
    Nat$c$0Instance nat = (Nat$c$0Instance) p0;
    if (nat.val < 0) {
      throw err("Nat too large to be represented by an Int");
    }
    return nat.val;
  }

  @Override public Object imm$succ$0() {
    if (val != MAX_UNSIGNED_VALUE) {
      return Nat$c$0Instance.instance(val + 1);
    }
    throw nonDetErr("Nat.succ: cannot take the successor of "+Long.toUnsignedString(MAX_UNSIGNED_VALUE));
  }
  @Override public Object imm$pred$0() {
    if (val != 0) {
      return Nat$c$0Instance.instance(val - 1);
    }
    throw nonDetErr("Nat.pred: cannot take the predecessor of "+0);
  }
  @Override public Object imm$$slash$1(Object p0){
    long d=n(p0);
    if (d == 0L) {
      throw err("Nat /: Cannot create a Num with denominator 0.");
    }
    return Num$c$0Instance.instance(
      unsignedLongToBigInteger(val),
      unsignedLongToBigInteger(d)
    );
  }
  @Override public Object imm$int$0() {
    if (Long.compareUnsigned(val, Long.MAX_VALUE) > 0) {
      return optEmpty();
    }
    // We are in the long normal range, so we can safely interpret the unsigned long as a signed long
    return optSome(Int$c$0Instance.instance(val));
  }

  @Override public Object imm$byte$0() {
    if (Long.compareUnsigned(val, 255L) > 0) {
      return optEmpty();
    }
    // We are in the long normal range, so we can safely interpret the unsigned long as a signed long
    return optSome(Byte$o$0Instance.instance((byte) val));
  }
  @Override public Object imm$float$0() {
    if (!canConvertToFloat(val)) {
      return optEmpty();
    }
    return optSome(Float$1c$0Instance.instance(unsignedLongToDouble(val)));
  }
  /**
   * Clamp the natural number to the range of int,
   * since that's the largest type we can convert it to without losing information
   */
  @Override public Object imm$softInt$0(){
    if (Long.compareUnsigned(val, Long.MAX_VALUE) > 0) {
      return Int$c$0Instance.instance(Long.MAX_VALUE);
    }
    // We are in the long normal range, so we can safely interpret the unsigned long as a signed long
    return Int$c$0Instance.instance(val);
  }
  @Override public Object imm$softByte$0(){
    long x= Long.compareUnsigned(val,255) > 0 ? 255 : val;
    return Byte$o$0Instance.instance((byte)x);
  }
  @Override public Object imm$softFloat$0(){
    return Float$1c$0Instance.instance(unsignedLongToDouble(val));
  }
  static double unsignedLongToDouble(long num) {
    if (Long.compareUnsigned(num, Long.MAX_VALUE) > 0) {
      // Hopefully correct...
      // a + b - b is an identity function, up to floating point precision
      // We add Long.MIN_VALUE rather than subtracting Long.MAX_VALUE,
      // MIN_VALUE = -2^63 rather than MAX_VALUE = 2^63 - 1
      return ((double) (num + Long.MIN_VALUE)) - Long.MIN_VALUE;
    }
    // Since the number is small enough that the sign bit is not set,
    // we can safely interpret the unsigned long as a signed long
    // and thus use the regular conversion to double.
    return (double) num;
  }
  static boolean canConvertToFloat(long val) {
    // https://en.wikipedia.org/wiki/Double-precision_floating-point_format
    // The largest integer that can be exactly represented in a double is 2^53.
    if (Long.compareUnsigned(val, 9007199254740993L) <= 0) {
      return true;
    }
    BigInteger bigInteger = toUnsignedBigInteger(val);
    return BigDecimal.valueOf(bigInteger.doubleValue()).toBigInteger().equals(bigInteger);
  }
  /**
   * Code is from a private method in the open jdk <a href="https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/lang/Long.java">Long</a> class.
   * Seen as of <a href="https://github.com/openjdk/jdk/commit/a7507ffa1dda403110a61c4b61143b76e8a7911e">this commit</a>
   * Return a BigInteger equal to the unsigned value of the
   * argument.
   */
  private static BigInteger toUnsignedBigInteger(long i) {
    if (i >= 0L)
      return BigInteger.valueOf(i);
    else {
      int upper = (int) (i >>> 32);
      int lower = (int) i;

      // return (upper << 32) + lower
      return (BigInteger.valueOf(Integer.toUnsignedLong(upper))).shiftLeft(32).
              add(BigInteger.valueOf(Integer.toUnsignedLong(lower)));
    }
  }
  @Override public Object imm$getInt$0() {
    if (Long.compareUnsigned(val, Long.MAX_VALUE) > 0) {
      throw err("Nat.getInt: cannot convert to Int, "+Long.toUnsignedString(val)+" is greater than Math.maxInt ("+Long.toUnsignedString(-1)+")");
    }
    return Int$c$0Instance.instance(val);
  }
  @Override public Object imm$getByte$0() {
    if (Long.compareUnsigned(val, 255L) > 0) {
      throw err("Nat.getByte: cannot convert to Byte, "+Long.toUnsignedString(val)+" is greater than Math.maxByte (255)");
    }
    return Byte$o$0Instance.instance((byte) val);
  }
  @Override public Object imm$getFloat$0() {
    if (!canConvertToFloat(val)) {
      throw err("Nat.getFloat: cannot convert to Float "+Long.toUnsignedString(val)+" without losing precision");
    }
    return Float$1c$0Instance.instance(unsignedLongToDouble(val));
  }
  @Override public Object imm$$plus$1(Object p0){ return instance(addChecked(val,n(p0))); }
  @Override public Object imm$$dash$1(Object p0){ return instance(subChecked(val,n(p0))); }
  @Override public Object imm$$star$1(Object p0){ return instance(mulChecked(val,n(p0))); }
  @Override public Object imm$$star_star$1(Object p0) {
    long power = n(p0);
    if (power == 0) { return Nat$c$0Instance.instance(1); }
    if (power == 1 || this.val == 1 || this.val == 0) { return this; }
    long result = 1;
    try {
      while (power > 0) {
        result = mulChecked(result, this.val);
        power -= 1;
      }
    }
    catch (Error _) {
      // re-wrap the error for a better message
      throw nonDetErr(overflowMsg("**", this.val, n(p0)));
    }
    return Nat$c$0Instance.instance(result);
  }
  @Override public Object imm$softSqrt$0(){
    return Float$1c$0Instance.instance(Math.sqrt(unsignedLongToDouble(val)));
  }
  @Override public Object read$str$0(){ return Str$c$0Instance.instance(Long.toUnsignedString(val)); }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object imm$getTruncDiv$1(Object p0){
    long d= n(p0);
    if (d == 0){ throw err("Nat.getTruncDiv: d==0"); }
    return instance(Long.divideUnsigned(val,d));
  }
  @Override public Object imm$getRem$1(Object p0){
    long d= n(p0);
    if (d == 0){ throw err("Nat.getRem: d==0"); }
    return instance(Long.remainderUnsigned(val,d));
  }
  @Override public Object imm$getIndexOffset$1(Object p0){
    long offset = i(p0);

    if (offset <= 0) {
      if (Long.compareUnsigned(val, -offset) < 0) {
        throw detErr(
          "Nat.getIndexOffset: Underflow occurred when offsetting "
          + Long.toUnsignedString(val)+" by "+offset+", this must be >= delta");
      }
      // works for Long.MIN_VALUE as well, as Long.MIN_VALUE when read unsigned is LONG.MAX_VALUE + 1
      return instance(subChecked(val, -offset));
    }
    if (Long.compareUnsigned(val, MAX_UNSIGNED_VALUE - offset) > 0) {
      throw detErr(
        "Nat.getIndexOffset: Overflow occurred when offsetting "
          + Long.toUnsignedString(val)+" by "+offset+"this+delta must be <= "
          + Long.toUnsignedString(MAX_UNSIGNED_VALUE));
    }
    return instance(addChecked(val, offset));
  }
  @Override public Object imm$aluAddWrap$1(Object p0){ return instance(val + n(p0)); }
  @Override public Object imm$aluSubWrap$1(Object p0){ return instance(val - n(p0)); }
  @Override public Object imm$aluMulWrap$1(Object p0){ return instance(val * n(p0)); }
  @Override public Object imm$aluDiv$1(Object p0){
    long d= n(p0);
    if (d == 0){ throw err("Nat.aluDiv: divByZero"); }
    return instance(Long.divideUnsigned(val,d));
  }
  @Override public Object imm$aluRem$1(Object p0){
    long d= n(p0);
    if (d == 0){ throw err("Nat.aluRem: divByZero"); }
    return instance(Long.remainderUnsigned(val,d));
  }
  @Override public Object imm$aluShiftLeft$1(Object p0){ return instance(val << n(p0)); }
  @Override public Object imm$aluShiftRight$1(Object p0){ return instance(val >>> n(p0)); }
  @Override public Object imm$aluXor$1(Object p0){ return instance(val ^ n(p0)); }
  @Override public Object imm$aluAnd$1(Object p0){ return instance(val & n(p0)); }
  @Override public Object imm$aluOr$1(Object p0){ return instance(val | n(p0)); }
  @Override public Object imm$aluInt$0(){ return Int$c$0Instance.instance(val); }
  @Override public Object imm$aluByte$0(){ return Byte$o$0Instance.instance((byte)val); }

  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(Long.compareUnsigned(n(p0),n(p1)),p2); }

  @Override public Object imm$norm$0(){ return this; }
  @Override public Object imm$get$0(){ return this; }
  @Override public Object read$info$0(){ return Infos$1c$0.instance.imm$msg$1(this.read$str$0());}
}
