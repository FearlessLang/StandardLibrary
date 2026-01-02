package base;

import static base.Util.*;
import java.math.BigInteger;

public record Num$0Instance(BigInteger val1, BigInteger val2) implements Num$0{
  public static Num$0 instance(BigInteger val1, BigInteger val2){ return new Num$0Instance(val1,val2); }

  public Num$0Instance{
    if (val2.signum() == 0){ throw err("Num: denom==0"); }
    if (val1.signum() == 0){ val1= zero; val2= one; }
    else{
      var g= val1.gcd(val2);
      val1= val1.divide(g);
      val2= val2.divide(g);
      if (val2.signum() < 0){ val1= val1.negate(); val2= val2.negate(); }
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
    return a.val1.multiply(b.val2).compareTo(b.val1.multiply(a.val2));
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
    return instance(val1.multiply(o.val2).add(o.val1.multiply(val2)), val2.multiply(o.val2));
  }
  @Override public Object imm$$dash$1(Object p0){
    var o= num(p0);
    return instance(val1.multiply(o.val2).subtract(o.val1.multiply(val2)), val2.multiply(o.val2));
  }
  @Override public Object imm$$star$1(Object p0){
    var o= num(p0);
    return instance(val1.multiply(o.val1), val2.multiply(o.val2));
  }
  @Override public Object imm$$slash$1(Object p0){
    var o= num(p0);
    if (o.val1.signum() == 0){ throw err("Num./: x==0"); }
    return instance(val1.multiply(o.val2), val2.multiply(o.val1));
  }
  @Override public Object imm$abs$0(){ return val1.signum() < 0 ? instance(val1.negate(),val2) : this; }
  @Override public Object imm$sqrt$0(){ return Float$0Instance.instance(Math.sqrt(val1.doubleValue() / val2.doubleValue())); }
  @Override public Object imm$floor$0(){ return instance(floorZ(val1,val2), one); }
  @Override public Object imm$ceil$0(){ return instance(ceilZ(val1,val2), one); }
  @Override public Object imm$trunc0$0(){ return instance(trunc0Z(val1,val2), one); }
  @Override public Object imm$round$0(){
    if (val1.signum() == 0){ return this; }
    var n= val1.abs();
    var q= n.divide(val2);
    var r= n.remainder(val2);
    int c= r.shiftLeft(1).compareTo(val2); // compare 2*r with d
    if (c > 0 || (c == 0 && q.testBit(0))){ q= q.add(one); } // tie -> bump if odd
    if (val1.signum() < 0){ q= q.negate(); }
    return instance(q,one);
  }
  @Override public Object imm$isInteger$0(){ return bool(val2.equals(one)); }
  @Override public Object imm$int$0(){
    var z= trunc0Z(val1,val2);
    return Int$0Instance.instance(clampIntZ(z));
  }
  @Override public Object imm$nat$0(){
    var z= trunc0Z(val1,val2);
    return Nat$0Instance.instance(clampNatBitsZ(z));
  }
  @Override public Object imm$byte$0(){
    var z= trunc0Z(val1,val2);
    return Byte$0Instance.instance(clampByteBitsZ(z));
  }
  @Override public Object imm$float$0(){
    return Float$0Instance.instance(val1.doubleValue() / val2.doubleValue());
  }
  @Override public Object imm$intExact$0(){
    if (!val2.equals(one)){ return optEmpty(); }
    if (val1.compareTo(minInt) < 0 || val1.compareTo(maxInt) > 0){ return optEmpty(); }
    return optSome(Int$0Instance.instance(val1.intValue()));
  }
  @Override public Object imm$natExact$0(){
    if (!val2.equals(one)){ return optEmpty(); }
    if (val1.signum() < 0 || val1.compareTo(maxNat) > 0){ return optEmpty(); }
    return optSome(Nat$0Instance.instance((int)val1.longValue()));
  }
  @Override public Object imm$byteExact$0(){
    if (!val2.equals(one)){ return optEmpty(); }
    if (val1.signum() < 0 || val1.compareTo(maxByte) > 0){ return optEmpty(); }
    return optSome(Byte$0Instance.instance((byte)val1.intValue()));
  }
  @Override public Object read$str$0(){
    String s= val2.equals(one) ? val1.toString() : (val1.toString()+"/"+val2.toString());
    return Str$0Instance.instance(s);
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
    if (d.val1.signum() < 0){ throw err("Num.eqDelta: delta<0"); }
    var diff= (Num$0Instance)((Num$0Instance)this.imm$$dash$1(exp)).imm$abs$0();
    return bool(le(diff,d));
  }

  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(cmp(num(p0),num(p1)),p2); }
}