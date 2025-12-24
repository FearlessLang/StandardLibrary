package base;

import java.math.BigInteger;

public record Num$0Instance(BigInteger n,BigInteger d) implements Num$0{
  public static Num$0Instance instance(BigInteger n,BigInteger d){ return canon(n,d); }

  static Num$0Instance canon(BigInteger n,BigInteger d){
    if (d.signum() == 0){ throw new Error("TODO: Num: /0 "); }
    if (d.signum() < 0){ n= n.negate(); d= d.negate(); }
    BigInteger g= n.gcd(d);
    if (!g.equals(BigInteger.ONE)){ n= n.divide(g); d= d.divide(g); }
    return new Num$0Instance(n,d);
  }
//---
  int cmp(Num$0Instance o){
    return n.multiply(o.d).compareTo(o.n.multiply(d));
  }
  boolean isInt(){ return n.remainder(d).signum() == 0; }

  BigInteger floorBI(){
    var qr= n.divideAndRemainder(d);
    var q= qr[0];
    var r= qr[1];
    if (r.signum() == 0){ return q; }
    if (n.signum() < 0){ return q.subtract(BigInteger.ONE); }
    return q;
  }
  BigInteger ceilBI(){
    var qr= n.divideAndRemainder(d);
    var q= qr[0];
    var r= qr[1];
    if (r.signum() == 0){ return q; }
    if (n.signum() > 0){ return q.add(BigInteger.ONE); }
    return q;
  }
  BigInteger roundBI(){
    var qr= n.divideAndRemainder(d);
    var q= qr[0];
    var r= qr[1];
    BigInteger twoAbsR= r.abs().shiftLeft(1);
    if (twoAbsR.compareTo(d) < 0){ return q; }
    if (n.signum() > 0){ return q.add(BigInteger.ONE); }
    if (n.signum() < 0){ return q.subtract(BigInteger.ONE); }
    return q;
  }
  int intExactBI(BigInteger x){//??
    try{ return x.intValueExact(); }
    catch(ArithmeticException e){ throw new Error("TODO: out of int range"); }
  }
  int intBI(BigInteger x){//??
    try{ return x.intValueExact(); }
    catch(ArithmeticException e){
      if (x.signum() < 0){ return Integer.MIN_VALUE; }
      return Integer.MAX_VALUE; 
    }
  }
  int intExact(){
    if (!isInt()){ throw new Error("TODO: Num.intExact: not integer"); }
    return intExactBI(n.divide(d));
  }
  double asDouble(){ return n.doubleValue()/d.doubleValue(); }

  @Override public Object read$int$0(){ return Int$0Instance.instance(intBI(n.divide(d))); }
  @Override public Object read$nat$0(){
    BigInteger q= n.divide(d);
    if (q.signum() < 0){ return Nat$0Instance.instance(0); }
    return Nat$0Instance.instance(intExactBI(q));//TODO: no, this kills the last bit?
  }
  @Override public Object read$byte$0(){
    BigInteger q= n.divide(d);
    int i= intExactBI(q);
    if (i < 0 || i > 255){ throw new Error("Num.byte: out of range"); }
    return Byte$0Instance.instance((byte)i);//TODO: is this ok for values over 128?
  }
  @Override public Object read$float$0(){ return Float$0Instance.instance(asDouble()); }
  @Override public Object read$num$0(){ return this; }
  @Override public Object read$numExact$0(){ return this; }
  @Override public Object read$intExact$0(){ return Int$0Instance.instance(intExact()); }
  @Override public Object read$natExact$0(){
    int i= intExact();
    if (i < 0){ throw new Error("Num.natExact: negative"); }
    return Nat$0Instance.instance(i);
  }
  @Override public Object read$byteExact$0(){//TODO: when ready it will return an Opt$1.instance or Opts$0hash...
    int i= intExact();
    if (i < 0 || i > 255){ throw new Error("Num.byteExact: out of range"); }
    return Byte$0Instance.instance((byte)i);
  }

  @Override public Object imm$$plus$1(Object p0){
    var o= (Num$0Instance)p0;
    return canon(n.multiply(o.d).add(o.n.multiply(d)), d.multiply(o.d));
  }
  @Override public Object imm$$dash$1(Object p0){
    var o= (Num$0Instance)p0;
    return canon(n.multiply(o.d).subtract(o.n.multiply(d)), d.multiply(o.d));
  }
  @Override public Object imm$$star$1(Object p0){
    var o= (Num$0Instance)p0;
    return canon(n.multiply(o.n), d.multiply(o.d));
  }
  @Override public Object imm$$slash$1(Object p0){
    var o= (Num$0Instance)p0;
    if (o.n.signum() == 0){ throw new Error("TODO: Num./: /0"); }
    return canon(n.multiply(o.d), d.multiply(o.n));
  }
  @Override public Object imm$$pct$1(Object p0){
    var o= (Num$0Instance)p0;
    if (!isInt() || !o.isInt()){ throw new Error("TODO: Num.%: not integer"); }//Interesting, is there no well known generalization? Should I simply remove this from Num?
    BigInteger a= n.divide(d);
    BigInteger b= o.n.divide(o.d);
    return canon(a.remainder(b), BigInteger.ONE);
  }

  @Override public Object imm$abs$0(){ return canon(n.abs(), d); }

  @Override public Object imm$sqrt$0(){
    if (n.signum() < 0){ return Float$0Instance.instance(Double.NaN); }
    BigInteger sn= n.sqrt();
    BigInteger sd= d.sqrt();
    if (sn.multiply(sn).equals(n) && sd.multiply(sd).equals(d)){ return canon(sn, sd); }
    return Float$0Instance.instance(Math.sqrt(asDouble()));
  }
  @Override public Object imm$cmpZero$0(){ return Util.ord(n.signum()); }

  @Override public Object read$str$0(){
    return Str$0Instance.instance(d.equals(BigInteger.ONE) ? n.toString() : (n+"/"+d));
  }
  @Override public Object read$info$0(){ throw new Error("InfoStillTODO"); }
  @Override public Object read$imm$0(){ return this; }

  @Override public Object imm$$lt_eq_gt$1(Object p0){ return Util.ord(cmp((Num$0Instance)p0)); }
  @Override public Object imm$inRange$2(Object p0, Object p1){
    var lo= (Num$0Instance)p0;
    var hi= (Num$0Instance)p1;
    return Util.bool(cmp(lo) >= 0 && cmp(hi) <= 0);
  }
  @Override public Object imm$inRangeOpen$2(Object p0, Object p1){
    var lo= (Num$0Instance)p0;
    var hi= (Num$0Instance)p1;
    return Util.bool(cmp(lo) > 0 && cmp(hi) < 0);
  }
  @Override public Object imm$inRangeLoOpen$2(Object p0, Object p1){
    var lo= (Num$0Instance)p0;
    var hi= (Num$0Instance)p1;
    return Util.bool(cmp(lo) > 0 && cmp(hi) <= 0);
  }
  @Override public Object imm$inRangeHiOpen$2(Object p0, Object p1){
    var lo= (Num$0Instance)p0;
    var hi= (Num$0Instance)p1;
    return Util.bool(cmp(lo) >= 0 && cmp(hi) < 0);
  }

  @Override public Object imm$clamp$2(Object p0, Object p1){
    var lo= (Num$0Instance)p0;
    var hi= (Num$0Instance)p1;
    if (cmp(lo) < 0){ return lo; }
    if (cmp(hi) > 0){ return hi; }
    return this;
  }
  //TODO: this suggests we should instead not have those at all 
  @Override public Object imm$clampOpen$2(Object p0, Object p1){ return imm$clamp$2(p0,p1); }
  @Override public Object imm$clampLoOpen$2(Object p0, Object p1){ return imm$clamp$2(p0,p1); }
  @Override public Object imm$clampHiOpen$2(Object p0, Object p1){ return imm$clamp$2(p0,p1); }

  @Override public Object imm$eqDelta$2(Object p0, Object p1){
    var o= (Num$0Instance)p0;
    var delta= (Num$0Instance)p1;
    var diff= (Num$0Instance)imm$$dash$1(o);
    var ad= (Num$0Instance)diff.imm$abs$0();
    return Util.bool(ad.cmp(delta) <= 0);
  }
  //TODO: I see. I'm not sure how to handle this, exception? optional return? stick to max/min int?
  @Override public Object imm$floor$0(){ return Int$0Instance.instance(intExactBI(floorBI())); }
  @Override public Object imm$ceil$0(){ return Int$0Instance.instance(intExactBI(ceilBI())); }
  @Override public Object imm$round$0(){ return Int$0Instance.instance(intExactBI(roundBI())); }

  @Override public Object imm$isInteger$0(){ return Util.bool(isInt()); }

  @Override public Object imm$$star_star$1(Object p0){
    int e= ((Int$0Instance)p0).val();//Is pow on Num*Num hard to do?
    if (e == 0){ return canon(BigInteger.ONE, BigInteger.ONE); }
    if (e > 0){ return canon(n.pow(e), d.pow(e)); }
    int pe= -e;
    if (n.signum() == 0){ throw new Error("Num.**: 0^neg"); }
    return canon(d.pow(pe), n.pow(pe));
  }
}

/*
public record Num$0Instance(BigInteger val1,BigInteger val2) implements Num$0{
  public static Num$0 instance(BigInteger val1,BigInteger val2){ return new Num$0Instance(val1,val2); }

  //@Override public Object imm$shiftLeft$1(Object p0){ throw new Error(); }
  //@Override public Object imm$shiftRight$1(Object p0){ throw new Error(); }
  //@Override public Object imm$xor$1(Object p0){ throw new Error(); }
  //@Override public Object imm$bitwiseAnd$1(Object p0){ throw new Error(); }
  //@Override public Object imm$bitwiseOr$1(Object p0){ throw new Error(); }
  //@Override public Object imm$$star_star$1(Object p0){ throw new Error(); }
  @Override public Object read$int$0(){ throw new Error(); }
  @Override public Object read$nat$0(){ throw new Error(); }
  @Override public Object read$byte$0(){ throw new Error(); }
  @Override public Object read$float$0(){ throw new Error(); }
  @Override public Object read$num$0(){ throw new Error(); }
  @Override public Object read$numExact$0(){ throw new Error(); }
  @Override public Object read$intExact$0(){ throw new Error(); }
  @Override public Object read$natExact$0(){ throw new Error(); }
  @Override public Object read$byteExact$0(){ throw new Error(); }
  @Override public Object imm$$plus$1(Object p0){ throw new Error(); }
  @Override public Object imm$$dash$1(Object p0){ throw new Error(); }
  @Override public Object imm$$star$1(Object p0){ throw new Error(); }
  @Override public Object imm$$slash$1(Object p0){ throw new Error(); }
  @Override public Object imm$$pct$1(Object p0){ throw new Error(); }
  @Override public Object imm$abs$0(){ throw new Error(); }
  @Override public Object imm$sqrt$0(){ throw new Error(); }
  @Override public Object imm$cmpZero$0(){ throw new Error(); }
  //@Override public Object imm$offset$1(Object p0){ throw new Error(); }
  @Override public Object read$str$0(){ throw new Error(); }
  @Override public Object read$info$0(){ throw new Error(); }
  @Override public Object read$imm$0(){ throw new Error(); }
  @Override public Object imm$$lt_eq_gt$1(Object p0){ throw new Error(); }
  @Override public Object imm$inRange$2(Object p0, Object p1){ throw new Error(); }
  @Override public Object imm$inRangeOpen$2(Object p0, Object p1){ throw new Error(); }
  @Override public Object imm$inRangeLoOpen$2(Object p0, Object p1){ throw new Error(); }
  @Override public Object imm$inRangeHiOpen$2(Object p0, Object p1){ throw new Error(); }
  @Override public Object imm$clamp$2(Object p0, Object p1){ throw new Error(); }
  @Override public Object imm$clampOpen$2(Object p0, Object p1){ throw new Error(); }
  @Override public Object imm$clampLoOpen$2(Object p0, Object p1){ throw new Error(); }
  @Override public Object imm$clampHiOpen$2(Object p0, Object p1){ throw new Error(); }
  @Override public Object imm$eqDelta$2(Object p0, Object p1){ throw new Error(); }
  @Override public Object imm$floor$0(){ throw new Error(); }
  @Override public Object imm$ceil$0(){ throw new Error(); }
  @Override public Object imm$round$0(){ throw new Error(); }
  @Override public Object imm$isInteger$0(){ throw new Error(); }
  @Override public Object imm$$star_star$1(Object p0){ throw new Error(); }
}*/