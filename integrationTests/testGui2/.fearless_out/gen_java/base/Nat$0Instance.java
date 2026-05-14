package base;

import java.math.BigInteger;
import java.util.stream.LongStream;

import static base.Util.*;

class NatCache {
  private static final long max = 255L;
  private static final Nat$0Instance[] natCache = LongStream.rangeClosed(0, max)
          .mapToObj(Nat$0Instance::new)
          .toArray(Nat$0Instance[]::new);

  static Nat$0Instance getNat(long i) {
    if (Long.compareUnsigned(i, max) > 0) {
      return new Nat$0Instance(i);
    }
    return natCache[(int) i];
  }
}

/**
 * Natural numbers are stored in 64 bits
 * They represent the numbers >= 0;
 * @param val
 */
public record Nat$0Instance(long val) implements Nat$0 {

  /**
   * The maximum unsigned value that can be represented in a long (2^64 - 1)
   */
  @SuppressWarnings("NumericOverflow")
  public static final long MAX_UNSIGNED_VALUE = -1;
  public static final double MAX_UNSIGNED_VALUE_FLOAT = 18446744073709551615.0d;
  public static Nat$0 instance(long val){ return NatCache.getNat(val); }

  public static boolean overflowsLong(long unsignedLong){
    return Long.compareUnsigned(unsignedLong, Long.MAX_VALUE) > 0;
  }

  private static long n(Object o){ return ((Nat$0Instance)o).val; }
  private static long i(Object o){ return ((Int$0Instance)o).val(); }

  /**
   * For a long to overflow a + b has to be greater than Long.MAX_VALUE
   * We don't need to worry about underflow
   * if a + b > Long.MAX_VALUE
   * then a > Long.MAX_VALUE - b
   * (and b > Long.MAX_VALUE - a, but we only need to check one of them)
   */
  private static long addChecked(long a, long b){

    boolean overflow = Long.compareUnsigned(a, MAX_UNSIGNED_VALUE - b) > 0;
    if (overflow) { throw err("Nat.+ overflow"); }

    return a + b;
  }

  /**
   * Nat subtraction can only underflow if a < b,
   * since we are working with unsigned numbers
   */
  private static long subChecked(long a, long b){
    if (Long.compareUnsigned(a, b) < 0){ throw err("Nat.- underflow"); }

    return a - b;
  }

  /**
   * Overflow if a * b > Long.MAX_VALUE
   * => a > Long.MAX_VALUE / b
   */
  private static long mulChecked(long a, long b){
    if (a == 0) {
      return 0;
    }

    boolean overflow = Long.compareUnsigned(a, Long.divideUnsigned(MAX_UNSIGNED_VALUE, b)) > 0;

    if (overflow){ throw err("Nat.* overflow"); }
    return a * b;
  }
  @Override public Object imm$$slash$1(Object p0){
    long d=n(p0);
    return Num$0Instance.instance(
      unsignedLongToBigInteger(val),
      unsignedLongToBigInteger(d)
    );
  }

  /**
   * Clamp the natural number to the range of int,
   * since that's the largest type we can convert it to without losing information
   */
  @Override public Object imm$int$0(){

    if (Long.compareUnsigned(val, Long.MAX_VALUE) > 0) {
      return Int$0Instance.instance(Long.MAX_VALUE);
    }
    // We are in the long normal range, so we can safely interpret the unsigned long as a signed long
    return Int$0Instance.instance(val);
  }

  @Override public Object imm$byte$0(){
    long x= Long.compareUnsigned(val,255) > 0 ? 255 : val;
    return Byte$0Instance.instance((byte)x);
  }

  private static double unsignedLongToDouble(long num) {
    // Guaranteed to be correct but seems slow..
    // return Double.parseDouble(Long.toUnsignedString(num))

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
  @Override public Object imm$float$0(){
    return Float$0Instance.instance(unsignedLongToDouble(val));
  }

  @Override public Object imm$num$0(){
    return Num$0Instance.instance(
            toUnsignedBigInteger(val),
            BigInteger.ONE
    );
  }

  /**
   * Code is from using a private method in the open jdk <a href="https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/lang/Long.java">Long</a> class.
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

  /**
   * Is this method named the right thing?
   */
  @Override public Object imm$intExact$0() {
    if (Long.compareUnsigned(val, Long.MAX_VALUE) > 0) {
      return optEmpty();
    }

    return optSome(Int$0Instance.instance(val));
  }

  @Override public Object imm$byteExact$0(){
    return Long.compareUnsigned(val,255) > 0 ? optEmpty() : optSome(Byte$0Instance.instance((byte)val));
  }
  @Override public Object imm$$plus$1(Object p0){ return instance(addChecked(val,n(p0))); }
  @Override public Object imm$$dash$1(Object p0){ return instance(subChecked(val,n(p0))); }
  @Override public Object imm$$star$1(Object p0){ return instance(mulChecked(val,n(p0))); }

  @Override public Object imm$$star_star$1(Object p0) {
    long power = n(p0);
    if (power == 0) { return Nat$0Instance.instance(1); }
    if (power == 1 || this.val == 1 || this.val == 0) { return this; }

    long result = 1;
    while (power > 0) {
      result = mulChecked(result, this.val);
      power -= 1;
    }

    return Nat$0Instance.instance(result);
  }

  @Override public Object imm$sqrt$0(){
    return Float$0Instance.instance(
            Math.sqrt(unsignedLongToDouble(val))
    );
  }
  @Override public Object read$str$0(){ return Str$0Instance.instance(Long.toUnsignedString(val)); }
  @Override public Object read$info$0(){ return Info$0.instance; }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object imm$clamp$2(Object p0, Object p1){
    long lo= n(p0), hi= n(p1);
    if (Long.compareUnsigned(lo,hi) > 0){ throw err("Nat.clamp: lo>hi"); }
    if (Long.compareUnsigned(val,lo) < 0){ return instance(lo); }
    if (Long.compareUnsigned(val,hi) > 0){ return instance(hi); }
    return this;
  }
  @Override public Object imm$div$1(Object p0){
    long d= n(p0);
    if (d == 0){ throw err("Nat.div: d==0"); }
    return instance(Long.divideUnsigned(val,d));
  }
  @Override public Object imm$rem$1(Object p0){
    long d= n(p0);
    if (d == 0){ throw err("Nat.rem: d==0"); }
    return instance(Long.remainderUnsigned(val,d));
  }
  @Override public Object imm$divExact$1(Object p0){
    long d= n(p0);
    if (d == 0){ return optEmpty(); }
    if (Long.remainderUnsigned(val,d) != 0){ return optEmpty(); }
    return optSome(instance(Long.divideUnsigned(val,d)));
  }
  @Override public Object imm$indexOffset$1(Object p0){
    long offset = i(p0);

    if (offset <= 0) {
      // works for Long.MIN_VALUE as well,
      // Since -Long.MIN_VALUE == Long.MIN_VALUE but when treated as unsigned it actually
      // is correctly treated as the positive number.
      return instance(subChecked(val, -offset));
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
  @Override public Object imm$aluInt$0(){ return Int$0Instance.instance(val); }
  @Override public Object imm$aluByte$0(){ return Byte$0Instance.instance((byte)val); }

  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(Long.compareUnsigned(n(p0),n(p1)),p2); }
  @Override public Object imm$$tilde_tilde$1(Object p0){
    long start= this.val;
    long until= ((Nat$0Instance)p0).val;

    if (Long.compareUnsigned(until, start) < 0) {
      throw detErr("Valid ranges require end ("+until+") >= start ("+start+")");
    }
    // Don't need to worry about overflow :)
    if (
            Long.compareUnsigned(until, Long.MAX_VALUE) < 0
                    || Long.compareUnsigned(start, Long.MAX_VALUE) > 0
    ) {
      return new Flow$1Instance(
              LongStream.range(start, until)
                      .<Object>mapToObj(Nat$0Instance::new)
      );
    }

    return new Flow$1Instance(LongStream.concat(
            LongStream.rangeClosed(start,Long.MAX_VALUE),
            LongStream.range(Long.MIN_VALUE, until)
    ).mapToObj(Nat$0Instance::new));
  }
  @Override public Object imm$$tilde_tilde_eq$1(Object p0){
   long start= this.val;
    long until= ((Nat$0Instance)p0).val;

    if (Long.compareUnsigned(until, start) < 0) {
      throw detErr("Valid ranges require end ("+until+") >= start ("+start+")");
    }
    // Don't need to worry about overflow :)
    if (
            Long.compareUnsigned(until, Long.MAX_VALUE) < 0
                    || Long.compareUnsigned(start, Long.MAX_VALUE) > 0
    ) {
      return new Flow$1Instance(
              LongStream.rangeClosed(start, until)
                      .<Object>mapToObj(Nat$0Instance::new)
      );
    }

    return new Flow$1Instance(LongStream.concat(
            LongStream.rangeClosed(start,Long.MAX_VALUE),
            LongStream.rangeClosed(Long.MIN_VALUE, until)
    ).mapToObj(Nat$0Instance::new));
  }
  @Override public Object imm$norm$0(){ return myCache.computeIfAbsent(val,_->new Norm(this)); }
  static java.util.concurrent.ConcurrentHashMap<Object,Object> myCache= new java.util.concurrent.ConcurrentHashMap<>();
}