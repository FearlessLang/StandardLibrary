package base;

import static base.Util.*;

import java.math.BigInteger;
import java.util.stream.LongStream;

// Cache the integers from -128 to 127, inclusive.
class NumCache {
  private static final int min = -128;
  private static final int max = 127;
  private static final BigInteger maxInt = BigInteger.valueOf(Integer.MAX_VALUE);
  private static final BigInteger minInt = BigInteger.valueOf(Integer.MIN_VALUE);
  private static final Num$0Instance[] intCache = LongStream.rangeClosed(min, max)
          .mapToObj(l -> new Num$0Instance(BigInteger.valueOf(l), BigInteger.ONE))
          .toArray(Num$0Instance[]::new);

  static Num$0Instance get(int i) {
    if (i < min || i > max) {
      return new Num$0Instance(BigInteger.valueOf(i), BigInteger.ONE);
    }
    // shift the index so that min is 0
    int index = (i - min);
    return intCache[index];
  }

  static boolean inCache(BigInteger numerator, BigInteger denominator) {
    if (!denominator.equals(BigInteger.ONE)) {
      return false;
    }
    return numerator.compareTo(maxInt) < 0 && numerator.compareTo(minInt) > 0;
  }
}

public record Num$0Instance(BigInteger numerator, BigInteger denominator) implements Num$0,Norm$1 {
  private static final BigInteger one= BigInteger.ONE;
  private static final BigInteger zero= BigInteger.ZERO;
  private static final BigInteger minInt= BigInteger.valueOf(Int$0Instance.MIN_VALUE);
  private static final BigInteger maxInt= BigInteger.valueOf(Int$0Instance.MAX_VALUE);
  private static final BigInteger maxNat= one.shiftLeft(64).subtract(one);
  private static final BigInteger maxByte= BigInteger.valueOf(255);

  public Num$0Instance {
    if (denominator.signum() == 0){ throw err("Num: denom==0"); }
    if (numerator.signum() == 0){ numerator = zero; denominator = one; }
    else{
      var g= numerator.gcd(denominator);
      numerator = numerator.divide(g);
      denominator = denominator.divide(g);
      if (denominator.signum() < 0){ numerator = numerator.negate(); denominator = denominator.negate(); }
    }
  }
  public static Num$0 instance(BigInteger numerator, BigInteger denominator) {
    if (NumCache.inCache(numerator, denominator)) { return NumCache.get(numerator.intValue()); }
    return new Num$0Instance(numerator, denominator);
  }
  private static Num$0Instance num(Object o){ return (Num$0Instance)o; }
  static int cmp(Num$0Instance a, Num$0Instance b){
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
  public static BigInteger pow(BigInteger n, long exponent) {
    assert exponent >= 0;
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
    if (exponent == 0) {return NumCache.get(1);}
    if (exponent == 1) {return this;}
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
  @Override public Object imm$floor$0(){ return instance(floorZ(numerator, denominator), one); }
  @Override public Object imm$ceil$0(){ return instance(ceilZ(numerator, denominator), one); }
  @Override public Object imm$trunc0$0(){ return instance(trunc0Z(numerator, denominator), one); }

  @Override public Object imm$sign$0() {
    return NumCache.get(numerator.signum() * denominator.signum());
  }

  @Override public Object imm$round$0(){
    if (numerator.signum() == 0){ return this; }
    var n= numerator.abs();
    var q= n.divide(denominator);
    var r= n.remainder(denominator);
    int c= r.shiftLeft(1).compareTo(denominator); // compare 2*r with d
    if (c >= 0){ q= q.add(one); } // always round up
    if (numerator.signum() < 0){ q= q.negate(); }
    return instance(q,one);
  }

  @Override public Object imm$roundTiesEven$0(){
    if (numerator.signum() == 0){ return this; }
    var n= numerator.abs();
    var q= n.divide(denominator); //

    var r= n.remainder(denominator);
    int c= r.shiftLeft(1).compareTo(denominator); // compare 2*r with d
    if (c > 0 || (c == 0 && q.testBit(0))){ q= q.add(one); } // tie -> bump if odd
    if (numerator.signum() < 0){ q= q.negate(); }
    return instance(q,one);
  }

  public boolean isRepresentableAsDouble() {
    // Works as all Num's that are numerically equal are the same fraction
    // i.e. Num(1, 2).equals(Num(2, 4));
    return Float$0Instance.numExactFinite(
            numerator.doubleValue() / denominator.doubleValue()
    ).equals(this);
  }
  public boolean isInteger() { return this.denominator.equals(one); }
  public boolean isIntegerInRange(BigInteger min, BigInteger max) {
    return this.isInteger() && this.numerator.compareTo(min) >= 0 && this.numerator.compareTo(max) <= 0;
  }
  @Override public Object imm$isInteger$0(){ return bool(this.isInteger()); }
  @Override public Object imm$int$0() {
    // A num can be converted into an int if it an integer in [minInt, maxInt];
    if (this.isIntegerInRange(minInt, maxInt)) {return optSome(Int$0Instance.instance(this.numerator.longValue()));}
    return optEmpty();
  }
  @Override public Object imm$nat$0() {
    if (this.isIntegerInRange(zero, maxNat)) {
      // Numbers larger than Long.MAX_VALUE overflow and thus end up as the correct unsigned value.
      return optSome(Nat$0Instance.instance(this.numerator.longValue()));
    }
    return optEmpty();
  }
  @Override public Object imm$byte$0() {
    if (this.isIntegerInRange(zero, maxByte)) {return optSome(Byte$0Instance.instance(this.numerator.byteValue()));}
    return optEmpty();
  }
  @Override public Object imm$float$0(){
    if (!isRepresentableAsDouble()) { return optEmpty(); }
    return optSome(Float$0Instance.instance(numerator.doubleValue() / denominator.doubleValue()));
  }
  public void assertInteger(String message) {
    if (!this.isInteger()) {throw err(message);}
  }
  public void assertInRange(String message, BigInteger min, BigInteger max) {
    if (this.numerator.compareTo(min) < 0 || this.numerator.compareTo(max) > 0) {
      throw err(message);
    }
  }
  @Override public Object imm$getInt$0() {
    assertInteger("Num.getInt: cannot convert Num " + this.asString() + " to Int as the denominator is not 1.");
    // We know the denominator must be one.
    assertInRange(
      "Num.getInt: cannot convert Num " + this.asString() + " to Int as the numerator is not in the range ["+minInt+", "+maxInt+"]",
      minInt, maxInt);
    return Int$0Instance.instance(this.numerator.longValue());
  }

  @Override public Object imm$getNat$0() {
    assertInteger("Num.getNat: cannot convert Num " + this.asString() + " to Nat as the denominator is not 1.");
    assertInRange("Num.getNat: cannot convert Num " + this.asString() + " to Nat as the numerator is not in the range [0, "+maxNat+"]", zero, maxNat);
    return Nat$0Instance.instance(this.numerator.longValue());
  }


  @Override public Object imm$getByte$0() {
    assertInteger("Num.getInt: cannot convert Num " + this.asString() + " to Byte as the denominator is not 1.");
    assertInRange("Nat.getInt: cannot convert Num " + this.asString() + " to Byte as the numerator is not in the range [0, "+maxByte+"]",
      zero, maxByte);
    return Byte$0Instance.instance(this.numerator.byteValue());
  }


  @Override public Object imm$getFloat$0(){
    if (!isRepresentableAsDouble()) {
      throw err(read$str$0()+" is not exactly representable as a Float");
    }
    return Float$0Instance.instance(numerator.doubleValue() / denominator.doubleValue());
  }

  @Override public Object imm$softInt$0(){
    var z= trunc0Z(numerator, denominator);
    return Int$0Instance.instance(clampIntZ(z));
  }
  @Override public Object imm$softNat$0(){
    var z= trunc0Z(numerator, denominator);
    return Nat$0Instance.instance(clampNatBitsZ(z));
  }
  @Override public Object imm$softByte$0(){
    var z= trunc0Z(numerator, denominator);
    return Byte$0Instance.instance(clampByteBitsZ(z));
  }
  @Override public Object imm$softFloat$0(){
    return Float$0Instance.instance(numerator.doubleValue() / denominator.doubleValue());
  }
  String asString() {
    String sn= (numerator.signum() < 0 ? "" : "+")+ numerator;
    return sn+"/"+ denominator;
  }

  @Override public Object read$str$0(){
    return Str$0Instance.instance(this.asString());
  }
  @Override public Object read$info$0(){ return Info$0.instance; }
  @Override public Object read$imm$0(){ return this; }

  @Override public Object imm$eqDelta$2(Object p0, Object p1){
    var exp= num(p0);
    var d= num(p1);
    if (d.numerator.signum() < 0){ throw err("Num.eqDelta: delta<0"); }
    var diff= (Num$0Instance)((Num$0Instance)this.imm$$dash$1(exp)).imm$abs$0();
    return bool(le(diff,d));
  }
  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(cmp(num(p0),num(p1)),p2); }
  @Override public Object imm$norm$0(){
    if (numerator.abs().bitLength() + denominator.bitLength() <= 512){ return this; }
    return myCache.computeIfAbsent(this,_->new Norm(this));
  }
  @Override public Object imm$get$0(){ return this; }
  static java.util.concurrent.ConcurrentHashMap<Object,Object> myCache= new java.util.concurrent.ConcurrentHashMap<>(); 
}