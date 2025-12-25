package base;

import static base.Util.*;

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
  private static int clampTrunc0ToInt(double x){ return (int)x; }
  private static int clampTrunc0ToNatBits(double x){
    if (Double.isNaN(x) || x <= 0.0d){ return 0; }
    if (x >= 4294967295.0d){ return -1; } // 2^32-1
    long t= (long)x;
    return t > 0xFFFF_FFFFL ? -1 : (int)t;
  }
  private static byte clampTrunc0ToByteBits(double x){
    if (Double.isNaN(x) || x <= 0.0d){ return 0; }
    if (x >= 255.0d){ return (byte)255; }
    return (byte)((int)x);
  }
  private static boolean isIntegral(double x){
    if (!Double.isFinite(x)){ return false; }
    return x == Math.rint(x);
  }
  @Override public Object imm$$star_star$1(Object p0){ return instance(Math.pow(val,f(p0))); }
  @Override public Object read$int$0(){ return Int$0Instance.instance(clampTrunc0ToInt(val)); }
  @Override public Object read$nat$0(){ return Nat$0Instance.instance(clampTrunc0ToNatBits(val)); }
  @Override public Object read$byte$0(){ return Byte$0Instance.instance(clampTrunc0ToByteBits(val)); }
  @Override public Object read$numExact$0(){
    throw err("Float.numExact: need the chosen policy for converting a finite double to Num (or when to return empty)");
  }
  @Override public Object read$intExact$0(){
    if (!isIntegral(val)){ return optEmpty(); }
    if (val < (double)Integer.MIN_VALUE || val > (double)Integer.MAX_VALUE){ return optEmpty(); }
    return optSome(Int$0Instance.instance((int)val));
  }
  @Override public Object read$natExact$0(){
    if (!isIntegral(val)){ return optEmpty(); }
    if (val < 0.0d || val > 4294967295.0d){ return optEmpty(); }
    return optSome(Nat$0Instance.instance((int)((long)val)));
  }
  @Override public Object read$byteExact$0(){
    if (!isIntegral(val)){ return optEmpty(); }
    if (val < 0.0d || val > 255.0d){ return optEmpty(); }
    return optSome(Byte$0Instance.instance((byte)((int)val)));
  }
  @Override public Object imm$$plus$1(Object p0){ return instance(val + f(p0)); }
  @Override public Object imm$$dash$1(Object p0){ return instance(val - f(p0)); }
  @Override public Object imm$$star$1(Object p0){ return instance(val * f(p0)); }
  @Override public Object imm$$slash$1(Object p0){ return instance(val / f(p0)); }
  @Override public Object imm$abs$0(){ return instance(Math.abs(val)); }
  @Override public Object imm$sqrt$0(){ return instance(Math.sqrt(val)); }
  @Override public Object read$str$0(){ return Str$0Instance.instance(Double.toString(val)); }
  @Override public Object read$info$0(){ return Info$0.instance; }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object imm$$lt_eq_gt$1(Object p0){ return ord(cmpFearless(val,f(p0))); }
  @Override public Object imm$inRange$2(Object p0, Object p1){
    double lo= f(p0), hi= f(p1);
    checkRangeEnds(lo,hi,"Float.inRange");
    return bool(lo <= val && val <= hi);
  }
  @Override public Object imm$inRangeOpen$2(Object p0, Object p1){
    double lo= f(p0), hi= f(p1);
    checkRangeEnds(lo,hi,"Float.inRangeOpen");
    return bool(lo < val && val < hi);
  }
  @Override public Object imm$inRangeLoOpen$2(Object p0, Object p1){
    double lo= f(p0), hi= f(p1);
    checkRangeEnds(lo,hi,"Float.inRangeLoOpen");
    return bool(lo < val && val <= hi);
  }
  @Override public Object imm$inRangeHiOpen$2(Object p0, Object p1){
    double lo= f(p0), hi= f(p1);
    checkRangeEnds(lo,hi,"Float.inRangeHiOpen");
    return bool(lo <= val && val < hi);
  }
  @Override public Object imm$clamp$2(Object p0, Object p1){
    double lo= f(p0), hi= f(p1);
    if (Double.compare(lo,hi) > 0){ throw err("Float.clamp: lo>hi"); }
    if (val < lo){ return instance(lo); }
    if (val > hi){ return instance(hi); }
    return this;
  }
  @Override public Object imm$eqDelta$2(Object p0, Object p1){
    double exp= f(p0), d= f(p1);
    if (Double.isNaN(exp) || Double.isNaN(d) || Double.isNaN(val)){ return bool(false); }
    if (d < 0.0d){ throw err("Float.eqDelta: delta<0"); }
    return bool(Math.abs(val - exp) <= d);
  }
  @Override public Object imm$round$0(){
    if (Double.isNaN(val)){ return Int$0Instance.instance(0); }
    if (val == Double.POSITIVE_INFINITY){ return Int$0Instance.instance(Integer.MAX_VALUE); }
    if (val == Double.NEGATIVE_INFINITY){ return Int$0Instance.instance(Integer.MIN_VALUE); }
    double r= Math.rint(val); // ties-to-even
    if (r <= (double)Integer.MIN_VALUE){ return Int$0Instance.instance(Integer.MIN_VALUE); }
    if (r >= (double)Integer.MAX_VALUE){ return Int$0Instance.instance(Integer.MAX_VALUE); }
    return Int$0Instance.instance((int)r);
  }
  @Override public Object imm$ceil$0(){ return Int$0Instance.instance((int)Math.ceil(val)); }
  @Override public Object imm$floor$0(){ return Int$0Instance.instance((int)Math.floor(val)); }
  @Override public Object imm$trunc0$0(){ return Int$0Instance.instance(clampTrunc0ToInt(val)); }
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
}