package base;

import static base.Nat$0Instance.MAX_UNSIGNED_VALUE_FLOAT;
import static base.Util.*;
import java.math.BigInteger;

public record Float$0Instance(double val) implements Float$0{
  public static Float$0 instance(double val){ return new Float$0Instance(val); }

  private static double f(Object o){ return ((Float$0Instance)o).val; }
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
  @Override public Object imm$signDefensive$0() {
    return Float$0Instance.instance(Math.signum(this.val));
  }
  @Override public Object imm$$star_star$1(Object p0){ return instance(Math.pow(val,f(p0))); }
  @Override public Object imm$intDefensive$0(){
    if (Double.isNaN(val)) {
      return Int$0Instance.instance(0);
    }
    if (val <= Long.MIN_VALUE) {
      return Int$0Instance.instance(Long.MIN_VALUE);
    }
    if (val >= Long.MAX_VALUE) {
      return Int$0Instance.instance(Long.MAX_VALUE);
    }
    return Int$0Instance.instance((long) val);
  }
  @Override public Object imm$intExact$0(){
    if (Double.isNaN(val)) {
      throw err("Float.intExact: NaN has no integer value");
    }
    if (val <= Long.MIN_VALUE) {
      throw err("Float.intExact: cannot convert to Int "+val+" is less than Math.minInt");
    }
    if (val >= Long.MAX_VALUE) {
      throw err("Float.intExact: cannot convert to Int "+val+" is greater than Math.maxInt");
    }
    if (val != (long) val) {
      throw err("Float.intExact: cannot convert to Int "+val+" is not an integer");
    }
    return Int$0Instance.instance((long) val);
  }
  @Override public Object imm$int$0(){
    if (Double.isNaN(val) || val <= Long.MIN_VALUE || val >= Long.MAX_VALUE || val != (long) val) {
      return optEmpty();
    }
    return optSome(Int$0Instance.instance((long) val));
  }

  @Override public Object imm$nat$0(){
    if (Double.isNaN(val) || val <= 0.0d || val > MAX_UNSIGNED_VALUE_FLOAT || val != (long) val) {
      return optEmpty();
    }
    return optSome(Nat$0Instance.instance((long) val));
  }

  /// clamp+trunc0; NaN->0; +Inf->maxNat; -Inf->0 (never throws)
  @Override public Object imm$natDefensive$0(){
    if (Double.isNaN(val) || val <= 0.0d) {
      return Nat$0Instance.instance(0);
    }
    if (val > MAX_UNSIGNED_VALUE_FLOAT) {
      return Nat$0Instance.instance(Nat$0Instance.MAX_UNSIGNED_VALUE);
    }
    return Nat$0Instance.instance((long) val);
  }

  @Override public Object imm$natExact$0(){
    if (Double.isNaN(val)) {
      throw err("Float.natExact: NaN has no Nat value");
    }
    if (val <= 0.0d) {
      throw err("Float.natExact: cannot convert to Nat "+val+" is less than 0");
    }
    if (val > MAX_UNSIGNED_VALUE_FLOAT) {
        throw err("Float.natExact: cannot convert to Nat "+val+" is greater than Math.maxNat");
    }
    if (val != (long) val) {
      throw err("Float.natExact: cannot convert to Nat "+val+" is not an integer");
    }
    return Nat$0Instance.instance((long) val);
  }


  @Override public Object imm$byte$0(){
    if (Double.isNaN(val) || val <= 0.0d || val > 255.0d || val != (long) val) {
      return optEmpty();
    }
    return optSome(Byte$0Instance.instance((byte) val));
  }

  /// clamp+trunc0; NaN->0; +Inf->maxNat; -Inf->0 (never throws)
  @Override public Object imm$byteDefensive$0(){
    if (Double.isNaN(val) || val <= 0.0d) {
      return Byte$0Instance.instance((byte) 0);
    }
    if (val > MAX_UNSIGNED_VALUE_FLOAT) {
      return Byte$0Instance.instance((byte) 255);
    }
    return Byte$0Instance.instance((byte) val);
  }

  @Override public Object imm$byteExact$0(){
    if (Double.isNaN(val)) {
      throw err("Float.natExact: NaN has no Byte value");
    }
    if (val <= 0.0d) {
      throw err("Float.natExact: cannot convert to Byte "+val+" is less than 0");
    }
    if (val > 255.0) {
      throw err("Float.natExact: cannot convert to Byte "+val+" is greater than Math.maxByte");
    }
    if (val != (long) val) {
      throw err("Float.natExact: cannot convert to Byte "+val+" is not an integer");
    }
    return Byte$0Instance.instance((byte) val);
  }

  @Override public Object imm$num$0() {
    if (Double.isFinite(val)) {
      return optSome(numExactFinite(val));
    }
    return optEmpty();
  }
  @Override public Object imm$numExact$0(){
    if (Double.isFinite(val)) {
      return numExactFinite(val);
    }
    throw err("Float.numDefensive: cannot convert non-finite value "+val+" to Num");
  }


  private static Object numExactFinite(double x){
    if (x == 0.0d){ return Num$0Instance.instance(BigInteger.ZERO,BigInteger.ONE); } // also -0.0
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
    return Num$0Instance.instance(n,d);
  }
  @Override public Object imm$$plus$1(Object p0){ return instance(val + f(p0)); }
  @Override public Object imm$$dash$1(Object p0){ return instance(val - f(p0)); }
  @Override public Object imm$$star$1(Object p0){ return instance(val * f(p0)); }
  @Override public Object imm$$slash$1(Object p0){ return instance(val / f(p0)); }
  @Override public Object imm$absDefensive$0(){ return instance(Math.abs(val)); }
  @Override public Object imm$sqrtDefensive$0(){ return instance(Math.sqrt(val)); }
  @Override public Object read$str$0(){
    if (Double.isNaN(val)){ return Str$0Instance.instance("(+0.0 / +0.0)"); }
    if (val == Double.POSITIVE_INFINITY){ return Str$0Instance.instance("(+1.0 / +0.0)"); }
    if (val == Double.NEGATIVE_INFINITY){ return Str$0Instance.instance("(-1.0 / +0.0)"); }
    double x= (val == 0.0d) ? 0.0d : val; // merge -0.0
    String s= new java.math.BigDecimal(x).toPlainString(); // exact decimal of this binary64
    if (s.indexOf('.') == -1){ s += ".0"; }                // satisfy SignedFloat shape
    if (s.charAt(0) != '-'){ s= "+"+s; }                   // SignedFloat requires sign
    return Str$0Instance.instance(s);
  }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object imm$clampExact$2(Object p0,Object p1){
    double lo= f(p0);
    double hi= f(p1);
    if (cmpFearless(lo,hi) > 0){ throw err("Float.clamp: lo>hi"); }
    if (cmpFearless(val,lo) < 0){ return instance(lo); }
    if (cmpFearless(val,hi) > 0){ return instance(hi); }
    return this;
  }
  @Override public Object imm$eqDelta$2(Object p0, Object p1){
    double exp= f(p0), d= f(p1);
    if (Double.isNaN(exp) || Double.isNaN(d) || Double.isNaN(val)){ return bool(false); }
    if (d < 0.0d){ throw err("Float.eqDelta: delta<0"); }
    return bool(Math.abs(val - exp) <= d);
  }
  @Override public Object imm$roundDefensive$0(){
    if (Double.isNaN(val)){ return Int$0Instance.instance(0); }
    if (val == Double.POSITIVE_INFINITY){ return Int$0Instance.instance(Int$0Instance.MAX_VALUE); }
    if (val == Double.NEGATIVE_INFINITY){ return Int$0Instance.instance(Int$0Instance.MIN_VALUE); }
    double r= Math.rint(val); // ties-to-even
    if (r <= (double)Int$0Instance.MIN_VALUE){ return Int$0Instance.instance(Int$0Instance.MIN_VALUE); }
    if (r >= (double)Int$0Instance.MAX_VALUE){ return Int$0Instance.instance(Int$0Instance.MAX_VALUE); }
    return Int$0Instance.instance((int)r);
  }
  @Override public Object imm$ceilDefensive$0(){ return Int$0Instance.instance((int)Math.ceil(val)); }
  @Override public Object imm$floorDefensive$0(){ return Int$0Instance.instance((int)Math.floor(val)); }
  @Override public Object imm$trunc0Defensive$0(){ return Int$0Instance.instance(clampTrunc0ToInt(val)); }
  @Override public Object imm$isNaN$0(){ return bool(Double.isNaN(val)); }
  @Override public Object imm$isInfinite$0(){ return bool(Double.isInfinite(val)); }
  @Override public Object imm$isPosInfinity$0(){ return bool(val == Double.POSITIVE_INFINITY); }
  @Override public Object imm$isNegInfinity$0(){ return bool(val == Double.NEGATIVE_INFINITY); }
  @Override public Object imm$isNegZero$0(){ return bool(isNegZero(val)); }
  @Override public Object imm$isPosZero$0(){ return bool(isPosZero(val)); }
  @Override public Object imm$ieeeEq$1(Object p0){ return bool(val == f(p0)); }
  @Override public Object imm$ieeeSameBits$1(Object p0){ return bool(bits(val) == bits(f(p0))); }
  @Override public Object imm$ieeeStr$0(){ return Str$0Instance.instance(Double.toString(val)); }
  @Override public Object imm$ieeeRemainder$1(Object p0){ return instance(Math.IEEEremainder(val,f(p0))); }

  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(cmpFearless(f(p0),f(p1)),p2); }
}
