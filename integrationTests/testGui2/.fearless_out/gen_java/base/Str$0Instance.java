package base;
import static base.Util.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public record Str$0Instance(String val) implements Str$0{
  public static Str$0 instance(String val){ return new Str$0Instance(val); }
  @Override public String toString(){ return toS(this); }
  private static String s(Object o){ return ((Str$0Instance)o).val; }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object read$info$0(){ return Infos$0.instance.imm$msg$1(this); }
  @Override public Object read$str$0(){ return this; }

  @Override public Object imm$$plus$1(Object p0){ return instance(val+toS(p0)); }
  @Override public Object imm$$or$1(Object p0){ return instance(val+"\n"+toS(p0)); }
  @Override public Object imm$$or$0(){ return instance(val+"\n"); }
  @Override public Object imm$$xor$1(Object p0){ return instance(val+"\""+toS(p0)); }
  @Override public Object imm$$xor$0(){ return instance(val+"\""); }

  @Override public Object imm$isEmpty$0(){ return bool(val.isEmpty()); }
  @Override public Object imm$size$0(){ return Nat$0Instance.instance(val.length()); }
  
  @Override public Object imm$escape$0(){
    return instance(strExpr(val));
  }

  private static String strExpr(String s){
    var parts= s.split("\n",-1);
    var res= new StringBuilder(lineExpr(parts[0]));
    for (var i= 1; i < parts.length; i++){
      if (parts[i].isEmpty()){
        if (res.charAt(res.length()-1) == '|'){ res.append(' '); }
        res.append('|');
        continue;
      }
      res.append(" | ").append(lineExpr(parts[i]));
    }
    return res.toString();
  }

  private static String lineExpr(String s){
    if (s.indexOf('"') < 0){ return "\""+s+"\""; }
    if (s.indexOf('`') < 0){ return "`"+s+"`"; }
    return splitOnDelimiterChanges(s).stream()
      .map(Str$0Instance::lineExpr)
      .collect(Collectors.joining("+"));
  }

  private static List<String> splitOnDelimiterChanges(String s){
    var res= new ArrayList<String>();
    var start= 0;
    char seen= 0;
    for (var i= 0; i < s.length(); i++){
      var c= s.charAt(i);
      if (c != '"' && c != '`'){ continue; }
      if (seen == 0){ seen = c; continue; }
      if (seen == c){ continue; }
      res.add(s.substring(start,i));
      start = i;
      seen = c;
    }
    if (start < s.length()){ res.add(s.substring(start)); }
    return res;
  }

  @Override public Object imm$u$1(Object p0){
    return UStr$0Instance.instance(val).imm$u$1(p0);
  }
  @Override public Object imm$u$0(){
    return UStr$0Instance.instance(val);
  }
  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(s(p0).compareTo(s(p1)),p2); }
  
  static final Pattern signedInt= Pattern.compile("[+-][0-9](?:[0-9_]*[0-9])?");
  static final Pattern unsignedInt= Pattern.compile("[0-9](?:[0-9_]*[0-9])?");
  static final Pattern signedFloat= Pattern.compile(
    "[+-](?:[0-9](?:[0-9_]*[0-9])?)\\.(?:[0-9](?:[0-9_]*[0-9])?)"
    + "(?:[eE][+-]?[0-9](?:[0-9_]*[0-9])?)?");
  static final Pattern signedRational= Pattern.compile(
    "[+-]?(?:[0-9](?:[0-9_]*[0-9])?(?:\\.[0-9](?:[0-9_]*[0-9])?)?)/(?:[0-9](?:[0-9_]*[0-9])?(?:\\.[0-9](?:[0-9_]*[0-9])?)?)");
  static final long maxNatU= -1;
  static final long maxByteU= 255L;
  @Override public Object imm$intExact$0(){
    if (!signedInt.matcher(val).matches()){ return optEmpty(); }
    try{ return optSome(Int$0Instance.instance(Long.parseLong(no_(val)))); }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  @Override public Object imm$indexOf$1(Object p1){
    String text= ((Str$0Instance)p1).val();
    var res= val.indexOf(text);
    return res==-1? optEmpty(): optSome(Nat$0Instance.instance(res));
  }
  @Override public Object imm$sub$2(Object p1, Object p2){
    long from= ((Nat$0Instance)p1).val();
    long to= ((Nat$0Instance)p2).val();
    int size= val.length();
    if (Long.compareUnsigned(from,to) > 0){
      throw Util.err("Str.sub invalid range: from > to; from="+Long.toUnsignedString(from)+" to="+Long.toUnsignedString(to));
    }
    if (Long.compareUnsigned(from,size) > 0){
      throw Util.err("Str.sub invalid range: from out of bounds; from="+Long.toUnsignedString(from)+" size="+size);
    }
    if (Long.compareUnsigned(to,size) > 0){
      throw Util.err("Str.sub invalid range: to out of bounds; to="+Long.toUnsignedString(to)+" size="+size);
    }
    return new Str$0Instance(val.substring((int)from,(int)to));
  }
  @Override public Object imm$replaceSimultaneousOrdered$1(Object p1){
    if (!(p1 instanceof List$1Instance repl)){ return this; }
    @SuppressWarnings("unchecked")
    String res= Replacements.replaceSimultaneousOrdered((List<Str$0Instance>)(Object)repl.val(),val);
    return new Str$0Instance(res);
  }
  @Override public Object imm$replaceSimultaneous$1(Object p1){
    if (!(p1 instanceof List$1Instance repl)){ return this; }
    @SuppressWarnings("unchecked")
    String res= Replacements.replaceSimultaneous((List<Str$0Instance>)(Object)repl.val(),val);
    return new Str$0Instance(res);
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
      return optSome(Nat$0Instance.instance(Long.parseUnsignedLong(no_(val))));
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
      boolean neg= t.charAt(0) == '-';
      if (t.charAt(0)=='+' || t.charAt(0)=='-'){ t= t.substring(1); }
      int slash= t.indexOf('/');
      Dec a= Dec.parse(t.substring(0,slash));
      Dec b= Dec.parse(t.substring(slash+1));
      if (b.u.signum() == 0){ return optEmpty(); }
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
      if (new BigDecimal(x).compareTo(new BigDecimal(d)) != 0){ return optEmpty(); }
      if (d == 0.0d){ d= 0.0d; }
      return optSome(Float$0Instance.instance(d));
    }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  @Override public Object read$hash$0(){
    return Nat$0Instance.instance(val.hashCode());
  }
  @Override public Object imm$joinStr$1(Object p0){
    Stream<Object> stream= ((Flow$1Instance)p0).s();
    String res= stream.map(o->((Str$0Instance)o).val).collect(java.util.stream.Collectors.joining(val));
    return new Str$0Instance(res);
  }
  @Override public Object imm$startsWith$1(Object p0){
    var other= ((Str$0Instance)p0).val;
    return bool(val.startsWith(other));  
  }
  @Override public Object imm$lower$0(){ return new Str$0Instance(val.toLowerCase()); }
  @Override public Object imm$upper$0(){ return new Str$0Instance(val.toUpperCase()); }
  @Override public Object imm$norm$0(){ return myCache.computeIfAbsent(val,_->new Norm(this)); }
  static java.util.concurrent.ConcurrentHashMap<Object,Object> myCache= new java.util.concurrent.ConcurrentHashMap<>(); 
  static String no_(String s){ return s.indexOf('_')==-1 ? s : s.replace("_",""); }
}