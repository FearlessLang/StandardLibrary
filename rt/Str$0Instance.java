package base;
import static base.Util.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

public record Str$0Instance(String val) implements Str$0{
  public static Str$0 instance(String val){ return new Str$0Instance(val); }
  String toS(Object o){return ((Str$0Instance)((ToStr$0)o).read$str$0()).val; }
  @Override public String toString(){ return toS(this); }
  private static String s(Object o){ return ((Str$0Instance)o).val; }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object read$info$0(){ return Infos$0.instance.imm$msg$1(this); }
  @Override public Object read$str$0(){ return this; }

  @Override public Object imm$$plus$1(Object p0){ return instance(val+toS(p0)); }
  @Override public Object imm$$or$1(Object p0){ return instance(val+"\n"+toS(p0)); }
  @Override public Object imm$$xor$1(Object p0){ return instance(val+"`"+toS(p0)); }
  @Override public Object imm$$xor_xor$1(Object p0){ return instance(val+"\""+toS(p0)); }

  @Override public Object imm$$or$0(){ return instance(val+"\n"); }
  @Override public Object imm$$xor$0(){ return instance(val+"`"); }
  @Override public Object imm$$xor_xor$0(){ return instance(val+"\""); }
  
  @Override public Object imm$isEmpty$0(){ return bool(val.isEmpty()); }
  @Override public Object imm$size$0(){ return Nat$0Instance.instance(val.length()); }
  
  @Override public Object imm$escape$0(){
    var res= "`"+val.replace("`", "` ^ `").replace("\n", "` | `")+"`";
    if (val.length()+2 != res.length()){ res= "("+res+")"; }
    return instance(res);
  }

  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(s(p0).compareTo(s(p1)),p2); }
  
  static final Pattern signedInt= Pattern.compile("[+-][0-9](?:[0-9_]*[0-9])?");
  static final Pattern unsignedInt= Pattern.compile("[0-9](?:[0-9_]*[0-9])?");
  static final Pattern signedFloat= Pattern.compile(
    "[+-](?:[0-9](?:[0-9_]*[0-9])?)\\.(?:[0-9](?:[0-9_]*[0-9])?)"
    + "(?:[eE][+-]?[0-9](?:[0-9_]*[0-9])?)?");
  static final Pattern signedRational= Pattern.compile(
    "[+-](?:[0-9](?:[0-9_]*[0-9])?(?:\\.[0-9](?:[0-9_]*[0-9])?)?)/(?:[0-9](?:[0-9_]*[0-9])?(?:\\.[0-9](?:[0-9_]*[0-9])?)?)");
  static final long maxNatU= 0xFFFF_FFFFL;
  static final long maxByteU= 255L;
  @Override public Object imm$intExact$0(){
    if (!signedInt.matcher(val).matches()){ return optEmpty(); }
    try{ return optSome(Int$0Instance.instance(Integer.parseInt(no_(val)))); }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  static final Pattern ieeeFloatText= Pattern.compile(
    "(?:NaN|[+-]?Infinity|[+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?)");
  @Override public Object imm$ieeeFloat$0(){
    if (!ieeeFloatText.matcher(val).matches()){ return optEmpty(); }
    try{ return optSome(Float$0Instance.instance(Double.parseDouble(val))); }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  @Override public Object imm$natExact$0(){
    if (!unsignedInt.matcher(val).matches()){ return optEmpty(); }
    try{
      long x= Long.parseLong(no_(val));
      if (x < 0L || x > maxNatU){ return optEmpty(); }
      return optSome(Nat$0Instance.instance((int)x)); // bit-cast semantics via int bits
    }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  @Override public Object imm$byteExact$0(){
    if (!unsignedInt.matcher(val).matches()){ return optEmpty(); }
    try{
      int x= Integer.parseInt(no_(val));
      if (x > 255){ return optEmpty(); }
      return optSome(Byte$0Instance.instance((byte)x));
    }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  @Override public Object imm$numExact$0(){
    if (!signedRational.matcher(val).matches()){ return optEmpty(); }
    try{
      String t= no_(val);
      boolean neg= t.charAt(0)=='-';
      t= t.substring(1); // drop leading +/-
      int slash= t.indexOf('/');
      Dec a= Dec.parse(t.substring(0,slash));
      Dec b= Dec.parse(t.substring(slash+1));
      if (b.u.signum() == 0){ return optEmpty(); } // denom == 0
      BigInteger num= a.u.multiply(BigInteger.TEN.pow(b.scale));
      BigInteger den= b.u.multiply(BigInteger.TEN.pow(a.scale));
      if (neg){ num= num.negate(); }
      return optSome(Num$0Instance.instance(num,den));
    }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  static record Dec(BigInteger u,int scale){
    static Dec parse(String s){
      int dot= s.indexOf('.');
      if (dot == -1){ return new Dec(new BigInteger(s),0); }
      return new Dec(new BigInteger(s.substring(0,dot)+s.substring(dot+1)), s.length()-dot-1);
    }
  }
  @Override public Object imm$floatExact$0(){
    if (!signedFloat.matcher(val).matches()){ return optEmpty(); }
    try{
      String x= no_(val);
      double d= Double.parseDouble(x);
      if (!Double.isFinite(d)){ return optEmpty(); }
      // reject "would-round" decimals: input must equal the exact value of the parsed double bits
      if (new BigDecimal(x).compareTo(new BigDecimal(d)) != 0){ return optEmpty(); }
      if (d == 0.0d){ d= 0.0d; } // canonicalize -0.0 -> +0.0 for normal Float
      return optSome(Float$0Instance.instance(d));
      }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  @Override public Object imm$cheapHash$0(){
    return Nat$0Instance.instance(val.hashCode());
  }
  static String no_(String s){ return s.indexOf('_')==-1 ? s : s.replace("_",""); }
}