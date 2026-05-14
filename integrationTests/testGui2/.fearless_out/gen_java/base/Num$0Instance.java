package base;

import static base.Util.*;
import java.math.BigInteger;
import java.util.stream.LongStream;


class NumCache {
  private static final int min = -128;
  private static final int max = 127;
  private static final BigInteger maxInt = BigInteger.valueOf(Integer.MAX_VALUE);
  private static final BigInteger minInt = BigInteger.valueOf(Integer.MIN_VALUE);



  private final static Num$0Instance[] intCache = LongStream.rangeClosed(min, max)
          .mapToObj(l -> new Num$0Instance(BigInteger.valueOf(l), BigInteger.ONE))
          .toArray(Num$0Instance[]::new);

  static Num$0Instance getInt(int i) {
    if (i < min || i > max) {
      return new Num$0Instance(BigInteger.valueOf(i), BigInteger.ONE);
    }
    // shift the index so that min is 0
    int index = (int) (i - min);
    return intCache[index];
  }

  static boolean isInteger(BigInteger numerator, BigInteger denominator) {
    if (!denominator.equals(BigInteger.ONE)) {
      return false;
    }
    return numerator.compareTo(maxInt) < 0 && numerator.compareTo(minInt) > 0;
  }
}

public record Num$0Instance(BigInteger numerator, BigInteger denominator) implements Num$0 {
  public static Num$0 instance(BigInteger numerator, BigInteger denominator) {
    // if is a small enough integer
    if (NumCache.isInteger(numerator, denominator)) {
      return NumCache.getInt(numerator.intValue());
    }

    return new Num$0Instance(numerator, denominator);
  }

  public Num$0Instance{
    if (denominator.signum() == 0){ throw err("Num: denom==0"); }
    if (numerator.signum() == 0){ numerator = zero; denominator = one; }
    else{
      var g= numerator.gcd(denominator);
      numerator = numerator.divide(g);
      denominator = denominator.divide(g);
      if (denominator.signum() < 0){ numerator = numerator.negate(); denominator = denominator.negate(); }
    }
  }
  private static final BigInteger one= BigInteger.ONE;
  private static final BigInteger zero= BigInteger.ZERO;
  private static final BigInteger minInt= BigInteger.valueOf(Integer.MIN_VALUE);
  private static final BigInteger maxInt= BigInteger.valueOf(Integer.MAX_VALUE);
  private static final BigInteger maxNat= one.shiftLeft(32).subtract(one);
  private static final BigInteger maxByte= BigInteger.valueOf(255);

  private static Num$0Instance num(Object o){ return (Num$0Instance)o; }

  private static int cmp(Num$0Instance a, Num$0Instance b){
    return a.numerator.multiply(b.denominator).compareTo(b.numerator.multiply(a.denominator));
  }
  private static boolean lt(Num$0Instance a, Num$0Instance b){ return cmp(a,b) < 0; }
  private static boolean le(Num$0Instance a, Num$0Instance b){ return cmp(a,b) <= 0; }
  private static BigInteger trunc0Z(BigInteger n, BigInteger d){ return n.divide(d); }
  private static BigInteger floorZ(BigInteger n, BigInteger d){
    var qr= n.divideAndRemainder(d);
    var q= qr[0];
    if (qr[1].signum() == 0){ return q; }
    return n.signum() < 0 ? q.subtract(one) : q;
  }
  private static BigInteger ceilZ(BigInteger n, BigInteger d){
    var qr= n.divideAndRemainder(d);
    var q= qr[0];
    if (qr[1].signum() == 0){ return q; }
    return n.signum() > 0 ? q.add(one) : q;
  }
  private static int clampIntZ(BigInteger z){
    if (z.compareTo(minInt) < 0){ return Integer.MIN_VALUE; }
    if (z.compareTo(maxInt) > 0){ return Integer.MAX_VALUE; }
    return z.intValue();
  }
  private static int clampNatBitsZ(BigInteger z){
    if (z.signum() <= 0){ return 0; }
    if (z.compareTo(maxNat) >= 0){ return -1; } // 0xFFFF_FFFF
    return (int)z.longValue();
  }
  private static byte clampByteBitsZ(BigInteger z){
    if (z.signum() <= 0){ return 0; }
    if (z.compareTo(maxByte) >= 0){ return (byte)255; }
    return (byte)z.intValue();
  }
  @Override public Object imm$$plus$1(Object p0){
    var o= num(p0);
    return instance(numerator.multiply(o.denominator).add(o.numerator.multiply(denominator)), denominator.multiply(o.denominator));
  }
  @Override public Object imm$$dash$1(Object p0){
    var o= num(p0);
    return instance(numerator.multiply(o.denominator).subtract(o.numerator.multiply(denominator)), denominator.multiply(o.denominator));
  }
  @Override public Object imm$$star$1(Object p0){
    var o= num(p0);
    return instance(numerator.multiply(o.numerator), denominator.multiply(o.denominator));
  }

  /// exponent >= 0;
  public static BigInteger pow(BigInteger n, long exponent) {
    if (exponent <= Integer.MAX_VALUE) {
      // is small enough to safely cast
      return n.pow((int) exponent);
    }
    // Exponent is too large, use the rule:
    // a^(n+m) = a^n * a^m
    // we break it down into a^MAX_INT_1 * ... * a^MAX_INT_k * a^rem,
    // where MAX_INT_1 + ... + MAX_INT_K + rem = exponent;
    BigInteger result = BigInteger.ONE;
    long timesToDecompose = exponent / Integer.MAX_VALUE;
    int remainder = (int) (exponent % Integer.MAX_VALUE);
    for (int i = 0; i < timesToDecompose; i++) {
      result = result.multiply(n.pow(Integer.MAX_VALUE));
    }

    return result.multiply(n.pow(remainder));
  }

  @Override public Object imm$$star_star$1(Object p0) {
    long exponent = ((Int$0Instance) p0).val();
    if (exponent == 0) {
      return NumCache.getInt(1);
    }
    if (exponent > 0){
      return Num$0Instance.instance(
              pow(numerator, exponent), pow(denominator, exponent)
      );
    }
    // Since negative flip fraction

    if (exponent == Long.MIN_VALUE) {
      // Since |Long.MIN_VALUE| is too large to fit in long
      return new Num$0Instance(
              pow(denominator, Long.MAX_VALUE).multiply(denominator),
              pow(numerator, Long.MAX_VALUE).multiply(numerator)
      );
    }

    return new Num$0Instance(
            pow(denominator, Math.abs(exponent)),
            pow(numerator, Math.abs(exponent))
    );
  }


  @Override public Object imm$$slash$1(Object p0){
    var o= num(p0);
    if (o.numerator.signum() == 0){ throw err("Num./: x==0"); }
    return instance(numerator.multiply(o.denominator), denominator.multiply(o.numerator));
  }
  @Override public Object imm$abs$0(){ return numerator.signum() < 0 ? instance(numerator.negate(), denominator) : this; }
  @Override public Object imm$sqrt$0(){ return Float$0Instance.instance(Math.sqrt(numerator.doubleValue() / denominator.doubleValue())); }
  @Override public Object imm$floor$0(){ return instance(floorZ(numerator, denominator), one); }
  @Override public Object imm$ceil$0(){ return instance(ceilZ(numerator, denominator), one); }
  @Override public Object imm$trunc0$0(){ return instance(trunc0Z(numerator, denominator), one); }

  @Override public Object imm$sign$0() {
    return NumCache.getInt(numerator.signum() * denominator.signum());
  }

  @Override public Object imm$round$0(){
    if (numerator.signum() == 0){ return this; }
    var n= numerator.abs();
    var q= n.divide(denominator);
    var r= n.remainder(denominator);
    int c= r.shiftLeft(1).compareTo(denominator); // compare 2*r with d
    if (c > 0 || (c == 0 && q.testBit(0))){ q= q.add(one); } // tie -> bump if odd
    if (numerator.signum() < 0){ q= q.negate(); }
    return instance(q,one);
  }
  @Override public Object imm$isInteger$0(){ return bool(denominator.equals(one)); }
  @Override public Object imm$int$0(){
    var z= trunc0Z(numerator, denominator);
    return Int$0Instance.instance(clampIntZ(z));
  }
  @Override public Object imm$nat$0(){
    var z= trunc0Z(numerator, denominator);
    return Nat$0Instance.instance(clampNatBitsZ(z));
  }
  @Override public Object imm$byte$0(){
    var z= trunc0Z(numerator, denominator);
    return Byte$0Instance.instance(clampByteBitsZ(z));
  }
  @Override public Object imm$float$0(){
    return Float$0Instance.instance(numerator.doubleValue() / denominator.doubleValue());
  }
  @Override public Object imm$intExact$0(){
    if (!denominator.equals(one)){ return optEmpty(); }
    if (numerator.compareTo(minInt) < 0 || numerator.compareTo(maxInt) > 0){ return optEmpty(); }
    return optSome(Int$0Instance.instance(numerator.intValue()));
  }
  @Override public Object imm$natExact$0(){
    if (!denominator.equals(one)){ return optEmpty(); }
    if (numerator.signum() < 0 || numerator.compareTo(maxNat) > 0){ return optEmpty(); }
    return optSome(Nat$0Instance.instance((int) numerator.longValue()));
  }
  @Override public Object imm$byteExact$0(){
    if (!denominator.equals(one)){ return optEmpty(); }
    if (numerator.signum() < 0 || numerator.compareTo(maxByte) > 0){ return optEmpty(); }
    return optSome(Byte$0Instance.instance((byte) numerator.intValue()));
  }
  @Override public Object read$str$0(){
    String sn= (numerator.signum() < 0 ? "" : "+")+ numerator;
    return Str$0Instance.instance(sn+"/"+ denominator);
  }
  @Override public Object read$info$0(){ return Info$0.instance; }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object imm$clamp$2(Object p0, Object p1){
    var lo= num(p0); var hi= num(p1);
    if (lt(hi,lo)){ throw err("Num.clamp: lo>hi"); }
    if (lt(this,lo)){ return lo; }
    if (lt(hi,this)){ return hi; }
    return this;
  }
  @Override public Object imm$eqDelta$2(Object p0, Object p1){
    var exp= num(p0);
    var d= num(p1);
    if (d.numerator.signum() < 0){ throw err("Num.eqDelta: delta<0"); }
    var diff= (Num$0Instance)((Num$0Instance)this.imm$$dash$1(exp)).imm$abs$0();
    return bool(le(diff,d));
  }

  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(cmp(num(p0),num(p1)),p2); }
  @Override public Object imm$norm$0(){ return myCache.computeIfAbsent(this,_->new Norm(this)); }
  static java.util.concurrent.ConcurrentHashMap<Object,Object> myCache= new java.util.concurrent.ConcurrentHashMap<>(); 
}