package base;

import static base.Nat$c$0Instance.MAX_UNSIGNED_VALUE_FLOAT;
import static base.Util.*;
import java.math.BigInteger;

public record Float$1c$0Instance(double val) implements Float$1c$0{
  public static Float$1c$0 instance(double val){ return new Float$1c$0Instance(val); }

  private static double f(Object o){ return ((Float$1c$0Instance)o).val; }
  private static long bits(double x){ return Double.doubleToRawLongBits(x); }
  private static boolean isNegZero(double x){ return bits(x) == bits(-0.0d); }
  private static boolean isPosZero(double x){ return bits(x) == bits(0.0d); }
  private static int cmpFearless(double a, double b){
    boolean an= Double.isNaN(a), bn= Double.isNaN(b);
    if (an){ return bn ? 0 : 1; }
    if (bn){ return -1; }
    if (a == 0.0d && b == 0.0d){ return 0; } // -0.0 == +0.0
    return Double.compare(a,b);
  }
  private static void checkRangeEnds(double lo, double hi, String where){
    if (Double.isNaN(lo) || Double.isNaN(hi)){ throw err(where+": lo/hi NaN"); }
    if (Double.compare(lo,hi) > 0){ throw err(where+": lo>hi"); }
  }
  private static long clampTrunc0ToInt(double x){ return (long)x; }
  private static byte clampTrunc0ToByteBits(double x){
    if (Double.isNaN(x) || x <= 0.0d){ return 0; }
    if (x >= 255.0d){ return (byte)255; }
    return (byte)((int)x);
  }
  private static boolean isIntegral(double x){
    if (!Double.isFinite(x)){ return false; }
    return x == Math.rint(x);
  }

  public static double unwrap(Object p0) {
     return ((Float$1c$0Instance) p0).val;
  }

  @Override public Object imm$getSucc$0() {
    if (Double.isNaN(val)) {
      throw err("Float.succ: NaN does not have a successor");
    }
    if (val == Double.POSITIVE_INFINITY) {
      throw err("Float.succ: Math.posInf does not have a successor");
    }
    return Float$1c$0Instance.instance(Math.nextUp(val));
  }
  @Override public Object imm$getPred$0() {
    if (Double.isNaN(val)) {
      throw err("Float.pred: NaN does not have a predecessor");
    }
    if (val == Double.NEGATIVE_INFINITY) {
      throw err("Float.pred: Math.negInf does not have a predecessor");
    }
    return Float$1c$0Instance.instance(Math.nextDown(val));
  }

  @Override public Object imm$signOrNaN$0() {
    return Float$1c$0Instance.instance(Math.signum(this.val));
  }
  @Override public Object imm$$star_star$1(Object p0){
    double power = Float$1c$0Instance.unwrap(p0);
    if (Double.isNaN(this.val) || Double.isNaN(power)) {
      return Float$1c$0Instance.instance(Double.NaN);
    }

    if (this.val == 0.0 && power == 0.0
            || (this.val < 0.0 && power == Double.POSITIVE_INFINITY)
            || (this.val == Double.POSITIVE_INFINITY && power == 0.0)
    ) {
      return Float$1c$0Instance.instance(Double.NaN);
    }
    return instance(Math.pow(val,power));
  }
  @Override public Object imm$softInt$0(){
    if (Double.isNaN(val)) {
      return Int$c$0Instance.instance(0);
    }
    if (val <= Long.MIN_VALUE) {
      return Int$c$0Instance.instance(Long.MIN_VALUE);
    }
    if (val >= Long.MAX_VALUE) {
      return Int$c$0Instance.instance(Long.MAX_VALUE);
    }
    return Int$c$0Instance.instance((long) val);
  }
  /// clamp+trunc0; NaN->0; +Inf->maxNat; -Inf->0 (never throws)
  @Override public Object imm$softNat$0(){
    if (Double.isNaN(val) || val <= 0.0d) {
      return Nat$c$0Instance.instance(0);
    }
    if (val > MAX_UNSIGNED_VALUE_FLOAT) {
      return Nat$c$0Instance.instance(Nat$c$0Instance.MAX_UNSIGNED_VALUE);
    }
    return Nat$c$0Instance.instance((long) val);
  }
  @Override public Object imm$softByte$0(){ return Byte$o$0Instance.instance(clampTrunc0ToByteBits(val)); }
  static Num$c$0 numExactFinite(double x){
    if (x == 0.0d){ return Num$c$0Instance.instance(BigInteger.ZERO,BigInteger.ONE); } // also -0.0
    long b= Double.doubleToRawLongBits(x);
    boolean neg= (b >>> 63) != 0;
    int exp= (int)((b >>> 52) & 0x7FFL);
    long frac= b & 0x000F_FFFF_FFFF_FFFFL;

    long mant= exp == 0 ? frac : (1L << 52) | frac;
    int e= exp == 0 ? -1074 : exp - 1023 - 52; // exact power-of-two exponent

    var n= BigInteger.valueOf(mant);
    var d= BigInteger.ONE;
    if (e >= 0){ n= n.shiftLeft(e); }
    else { d= d.shiftLeft(-e); }
    if (neg){ n= n.negate(); }
    return Num$c$0Instance.instance(n,d);
  }
  @Override public Object imm$num$0(){
    if (Double.isNaN(val) || Double.isInfinite(val)){ return optEmpty(); }
    return optSome(numExactFinite(val));
  }

  @Override public Object imm$int$0(){
    if (!isIntegral(val)){ return optEmpty(); }
    if (val < (double)Integer.MIN_VALUE || val > (double)Integer.MAX_VALUE){ return optEmpty(); }
    return optSome(Int$c$0Instance.instance((int)val));
  }
  @Override public Object imm$nat$0(){
    if (!isIntegral(val)){ return optEmpty(); }
    if (val < 0.0d || val > MAX_UNSIGNED_VALUE_FLOAT){ return optEmpty(); }
    return optSome(Nat$c$0Instance.instance((int)((long)val)));
  }
  @Override public Object imm$byte$0(){
    if (!isIntegral(val)){ return optEmpty(); }
    if (val < 0.0d || val > 255.0d){ return optEmpty(); }
    return optSome(Byte$o$0Instance.instance((byte)((int)val)));
  }

  @Override public Object imm$getNum$0(){
    if (Double.isNaN(val) || Double.isInfinite(val)){
      throw err("Float.getNum: cannot convert non-finite Float " + val + " to Num");
    }
    return numExactFinite(val);
  }

  @Override public Object imm$getInt$0(){
    if (!isIntegral(val)){
      throw err("Float.getInt: cannot convert non-integral Float " + val + " to Int");
    }
    if (val < (double)Integer.MIN_VALUE || val > (double)Integer.MAX_VALUE){
      throw err("Float.getInt: cannot convert Float " + val + " to Int: out of Int range");
    }
    return Int$c$0Instance.instance((int)val);
  }

  @Override public Object imm$getNat$0(){
    if (!isIntegral(val)){
      throw err("Float.getNat: cannot convert non-integral Float " + val + " to Nat");
    }
    if (val < 0.0d){
      throw err("Float.getNat: cannot convert negative Float " + val + " to Nat");
    }
    if (val > MAX_UNSIGNED_VALUE_FLOAT){
      throw err("Float.getNat: cannot convert Float " + val + " to Nat: out of Nat range");
    }
    return Nat$c$0Instance.instance((int)((long)val));
  }

  @Override public Object imm$getByte$0(){
    if (!isIntegral(val)){
      throw err("Float.getByte: cannot convert non-integral Float " + val + " to Byte");
    }
    if (val < 0.0d){
      throw err("Float.getByte: cannot convert to Byte " + val + " is less than 0");
    }
    if (val > 255.0d){
      throw err("Float.getByte: cannot convert to Byte " + val + " is greater than 255");
    }
    return Byte$o$0Instance.instance((byte)((int)val));
  }

  @Override public Object imm$$plus$1(Object p0){ return instance(val + f(p0)); }
  @Override public Object imm$$dash$1(Object p0){ return instance(val - f(p0)); }
  @Override public Object imm$$star$1(Object p0){ return instance(val * f(p0)); }
  @Override public Object imm$$slash$1(Object p0){ return instance(val / f(p0)); }
  @Override public Object imm$absOrNaN$0(){ return instance(Math.abs(val)); }
  @Override public Object imm$sqrtOrNaN$0(){ return instance(Math.sqrt(val)); }
  @Override public Object read$str$0(){
    if (Double.isNaN(val)){ return Str$c$0Instance.instance("(+0.0 / +0.0)"); }
    if (val == Double.POSITIVE_INFINITY){ return Str$c$0Instance.instance("(+1.0 / +0.0)"); }
    if (val == Double.NEGATIVE_INFINITY){ return Str$c$0Instance.instance("(-1.0 / +0.0)"); }
    double x= (val == 0.0d) ? 0.0d : val; // merge -0.0
    String s= new java.math.BigDecimal(x).toPlainString(); // exact decimal of this binary64
    if (s.indexOf('.') == -1){ s += ".0"; }                // satisfy SignedFloat shape
    if (s.charAt(0) != '-'){ s= "+"+s; }                   // SignedFloat requires sign
    return Str$c$0Instance.instance(s);
  }
  @Override public Object read$info$0(){ return Info$o$0.instance; }
  @Override public Object read$imm$0(){ return this; }

  @Override public Object imm$eqDelta$2(Object p0, Object p1){
    double exp= f(p0), d= f(p1);
    if (Double.isNaN(exp) || Double.isNaN(d) || Double.isNaN(val)){ return bool(false); }
    if (d < 0.0d){ throw err("Float.eqDelta: delta<0"); }
    return bool(Math.abs(val - exp) <= d);
  }
  @Override public Object imm$softRoundTiesEven$0(){
    return instance(Math.rint(val));
  }
  @Override public Object imm$softRound$0(){
    if (val >= 0.0d) { return instance(Math.floor(val + 0.5)); }
    return instance(Math.ceil(val - 0.5));
  }
  @Override public Object imm$softCeil$0(){ return instance(Math.ceil(val)); }
  @Override public Object imm$softFloor$0(){ return instance(Math.floor(val)); }
  @Override public Object imm$softTrunc0$0(){
    if (val < 0) {
      return instance(Math.ceil(val));
    }
    return instance(Math.floor(val));
  }
  @Override public Object imm$isNaN$0(){ return bool(Double.isNaN(val)); }
  @Override public Object imm$isInfinite$0(){ return bool(Double.isInfinite(val)); }
  @Override public Object imm$isPosInfinity$0(){ return bool(val == Double.POSITIVE_INFINITY); }
  @Override public Object imm$isNegInfinity$0(){ return bool(val == Double.NEGATIVE_INFINITY); }
  @Override public Object imm$isNegZero$0(){ return bool(isNegZero(val)); }
  @Override public Object imm$isPosZero$0(){ return bool(isPosZero(val)); }
  @Override public Object imm$ieeeSameBits$1(Object p0){ return bool(bits(val) == bits(f(p0))); }
  @Override public Object imm$ieeeStr$0(){ return Str$c$0Instance.instance(Double.toString(val)); }
  @Override public Object imm$ieeeRemainder$1(Object p0){ return instance(Math.IEEEremainder(val,f(p0))); }
  /* IEEE standard - note we don't have signalling NaN (I believe all NaN's are quiet NaN's in Java)
  pow (x, ±0) is 1 if x is not a signaling NaN
  pow (±0, y) is ±∞ and signals the divideByZero exception for y an odd integer < 0
  pow (±0, −∞) is +∞ with no exception
  pow (±0, +∞) is +0 with no exception
  pow (±0, y) is ±0 for finite y > 0 an odd integer
  pow (−1, ±∞) is 1 with no exception
  pow (+1, y) is 1 for any y (even a quiet NaN)
  pow (x, +∞) is +0 for −1 < x < 1
  pow (x, +∞) is +∞ for x < −1 or for 1 < x (including ±∞)
  pow (x, −∞) is +∞ for −1 < x < 1
  pow (x, −∞) is +0 for x < −1 or for 1 < x (including ±∞)
  pow (+∞, y) is +0 for a number y < 0
  pow (+∞, y) is +∞ for a number y > 0
  pow (−∞, y) is −0 for finite y < 0 an odd integer
  pow (−∞, y) is −∞ for finite y > 0 an odd integer
  pow (−∞, y) is +0 for finite y < 0 and not an odd integer
  pow (−∞, y) is +∞ for finite y > 0 and not an odd integer
  pow (±0, y) is +∞ and signals the divideByZero exception for finite y < 0 and not an odd integer
  pow(±0, y) is +0 for finite y > 0 and not an odd integer
  pow(x, y) signals the invalid operation exception for finite x < 0 and finite non-integer y.
   */
  @Override public Object imm$ieeePow$1(Object p0){
    double f = unwrap(p0);
    if (f == 0.0) { return Float$1c$0Instance.instance(1.0); }
    // I think these are the only differences between ieee and Math
    if (val == 1.0 || val == -1.0 && Double.isInfinite(f)) {
      return Float$1c$0Instance.instance(1.0);
    }

    return Float$1c$0Instance.instance(Math.pow(val, f));
  }
  @Override public Object imm$ieeeEq$1(Object p0){
    return bool(val == unwrap(p0));
  }


  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(cmpFearless(f(p0),f(p1)),p2); }
}