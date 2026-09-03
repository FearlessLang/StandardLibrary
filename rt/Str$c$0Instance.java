package base;

import static base.Util.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public record Str$c$0Instance(String val) implements Str$c$0,Norm$o$1{
  public static Str$c$0 instance(String val){ return new Str$c$0Instance(val); }
  @Override public String toString(){ return toS(this); }
  private static String s(Object o){ return ((Str$c$0Instance)o).val; }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object read$info$0(){ return Infos$1c$0.instance.imm$msg$1(this); }
  @Override public Object read$str$0(){ return this; }

  @Override public Object imm$$plus$1(Object p0){ return instance(val+toS(p0)); }
  @Override public Object imm$$or$1(Object p0){ return instance(val+"\n"+toS(p0)); }
  @Override public Object imm$$or$0(){ return instance(val+"\n"); }
  @Override public Object imm$$xor$1(Object p0){ return instance(val+"\""+toS(p0)); }
  @Override public Object imm$$xor$0(){ return instance(val+"\""); }

  @Override public Object imm$isEmpty$0(){ return bool(val.isEmpty()); }
  @Override public Object imm$size$0(){ return Nat$c$0Instance.instance(val.length()); }
  
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
      .map(Str$c$0Instance::lineExpr)
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
    return UStr$s$0Instance.instance(val).imm$u$1(p0);
  }
  @Override public Object imm$u$0(){
    return UStr$s$0Instance.instance(val);
  }
  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(s(p0).compareTo(s(p1)),p2); }
  
  static final Pattern signedInt= Pattern.compile("[+-][0-9](?:[0-9_]*[0-9])?");
  static final Pattern unsignedInt= Pattern.compile("[0-9](?:[0-9_]*[0-9])?");
  static final Pattern optionallySignedFloat = Pattern.compile(
    "[+-]?(?:[0-9](?:[0-9_]*[0-9])?)\\.(?:[0-9](?:[0-9_]*[0-9])?)"
    + "(?:[eE][+-]?[0-9](?:[0-9_]*[0-9])?)?");
  static final Pattern optionallySignedRational = Pattern.compile(
    "[+-]?(?:[0-9](?:[0-9_]*[0-9])?(?:\\.[0-9](?:[0-9_]*[0-9])?)?)/(?:[0-9](?:[0-9_]*[0-9])?(?:\\.[0-9](?:[0-9_]*[0-9])?)?)");
  static final long maxNatU= -1;
  static final long maxByteU= 255L;
  @Override public Object imm$int$0(){
    if (!signedInt.matcher(val).matches()){ return optEmpty(); }
    try{ return optSome(Int$c$0Instance.instance(Long.parseLong(no_(val)))); }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  @Override public Object imm$getInt$0(){
    if (!signedInt.matcher(val).matches()){
      throw err("Str.getInt: cannot convert String \""+val+"\" to Int. \n"
              + "A Valid Int is any number of digits,, separated by optional \"_\", and preceded by either a + or a - sign."
              + "That is in the range ["+Int$c$0Instance.MIN_VALUE+", "+Int$c$0Instance.MAX_VALUE+"]."
              + "\n For example -4, +0, -3021\"");
    }
    try{ return Int$c$0Instance.instance(Long.parseLong(no_(val))); }
    catch(NumberFormatException e){
      throw err("Str.getInt: Str \""+val+"\" has a magnitude too great to be stored in an int, "
              + " must be in ["+Int$c$0Instance.MIN_VALUE+", "+Int$c$0Instance.MAX_VALUE+"]");
    }
  }
  @Override public Object imm$indexOf$1(Object p1){
    String text= ((Str$c$0Instance)p1).val();
    var res= val.indexOf(text);
    return res==-1? optEmpty(): optSome(Nat$c$0Instance.instance(res));
  }
  @Override public Object imm$sub$2(Object p1, Object p2){
    long from= ((Nat$c$0Instance)p1).val();
    long to= ((Nat$c$0Instance)p2).val();
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
    return new Str$c$0Instance(val.substring((int)from,(int)to));
  }
  @Override public Object imm$replaceSimultaneousOrdered$1(Object p1){
    if (!(p1 instanceof List$o$1Instance repl)){ return this; }
    @SuppressWarnings("unchecked")
    String res= Replacements.replaceSimultaneousOrdered((List<Str$c$0Instance>)(Object)repl.val(),val);
    return new Str$c$0Instance(res);
  }
  @Override public Object imm$replaceSimultaneous$1(Object p1){
    if (!(p1 instanceof List$o$1Instance repl)){ return this; }
    @SuppressWarnings("unchecked")
    String res= Replacements.replaceSimultaneous((List<Str$c$0Instance>)(Object)repl.val(),val);
    return new Str$c$0Instance(res);
  }
  static final Pattern ieeeFloatText= Pattern.compile(
    "(?:NaN|[+-]?Infinity|[+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?)");
  @Override public Object imm$ieeeFloat$0(){
    if (!ieeeFloatText.matcher(val).matches()){ return optEmpty(); }
    try{ return optSome(Float$1c$0Instance.instance(Double.parseDouble(val))); }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  @Override public Object imm$getIeeeFloat$0(){
    if (!ieeeFloatText.matcher(val).matches()){
      throw err("Str.getIeeeFloat: cannot convert String \""+val+"\" to Float. \n"
        + "A Valid IEEE-754 Float is \"NaN\", \"Infinity\", \"-Infinity\", or a decimal number "
        + "optionally preceded by a \"+\" or \"-\" sign, with an optional \".\" followed by digits, "
        + "and an optional exponent (\"e\"/\"E\" followed by an optionally-signed integer)."
        + "\n For example: NaN, Infinity, -Infinity, -0.0, +0.0, 1e0, 3000.2");
    }
    try{ return Float$1c$0Instance.instance(Double.parseDouble(val)); }
    catch(NumberFormatException e){
      throw err("Str.getIeeeFloat: cannot convert String \""+val+"\" to Float. \n"
          + "A Valid IEEE-754 Float is \"NaN\", \"Infinity\", \"-Infinity\", or a decimal number "
          + "optionally preceded by a \"+\" or \"-\" sign, with an optional \".\" followed by digits, "
          + "and an optional exponent (\"e\"/\"E\" followed by an optionally-signed integer)."
          + "\n For example: NaN, Infinity, -Infinity, -0.0, +0.0, 1e0, 3000.2");
    }
  }
  @Override public Object imm$nat$0(){
    if (!unsignedInt.matcher(val).matches()){ return optEmpty(); }
    try{
      return optSome(Nat$c$0Instance.instance(Long.parseUnsignedLong(no_(val))));
    }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  @Override public Object imm$getNat$0(){
    if (!unsignedInt.matcher(val).matches()){
      throw err("Str.getNat: cannot convert String \""+val+"\" to Nat. \n"
              + "A Valid Nat is any number of digits, separated by optional \"_\", that is in the range ["+0+", "+Long.toUnsignedString(Nat$c$0Instance.MAX_UNSIGNED_VALUE)+"]."
              + "\n For example 0, 3_021\"");
    }
    try{ return Nat$c$0Instance.instance(Long.parseUnsignedLong(no_(val))); }
    catch(NumberFormatException e){
      throw err("Str.getNat: Str \""+val+"\" has a magnitude too great to be stored in an Nat, "
              + " must be in ["+0+", "+Long.toUnsignedString(Nat$c$0Instance.MAX_UNSIGNED_VALUE)+"].");
    }
  }
  @Override public Object imm$byte$0(){
    if (!unsignedInt.matcher(val).matches()){ return optEmpty(); }
    try{
      int x= Integer.parseInt(no_(val));
      if (x > 255){ return optEmpty(); }
      return optSome(Byte$o$0Instance.instance((byte)x));
    }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  @Override public Object imm$getByte$0(){
    if (!unsignedInt.matcher(val).matches()){
      throw err("Str.getByte: cannot convert String \""+val+"\" to Byte. \n"
              + "A Valid Byte is any number of digits, separated by optional \"_\"s, that is in the range ["+0+", "+255+"]."
              + "\n For example 0, 255, 42");
    }
    try{
      int x= Integer.parseInt(no_(val));
      if (x > 255){ throw new NumberFormatException(); }
      return Byte$o$0Instance.instance((byte)x);
    }
    catch(NumberFormatException e){
      throw err("Str.getByte: Str \""+val+"\" has a magnitude too great to be stored in an Nat, must be in ["+0+", "+255+"].");
    }
  }
  @Override public Object imm$num$0(){
    if (!optionallySignedRational.matcher(val).matches()){ return optEmpty(); }
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
      return optSome(Num$c$0Instance.instance(num,den));
    }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  @Override public Object imm$getNum$0(){
    if (!optionallySignedRational.matcher(val).matches()){
      throw err("Str.getNum: cannot convert String \""+val+"\" to Num. \n"
              + "A valid Num is in the form [-]a / b:\n"
              + "Where the numerator is any number of digits, optionally separated by \"_\"s and proceeded by a \"-\".\n"
              + "And the denominator is any number of digits optionally separated by \"_\"s but cannot be 0 or proceeded by a \"-\".\n"
              + "For example: -40 / 3, 40_330 / 3_003, 12/ 2");
    }
    try{
      String t= no_(val);
      boolean neg= t.charAt(0) == '-';
      if (t.charAt(0)=='+' || t.charAt(0)=='-'){ t= t.substring(1); }
      int slash= t.indexOf('/');
      Dec a= Dec.parse(t.substring(0,slash));
      Dec b= Dec.parse(t.substring(slash+1));
      if (b.u.signum() == 0){ throw err("Str.getNum: Cannot convert String "+val+" to Num, as the denominator is 0"); }
      BigInteger num= a.u.multiply(BigInteger.TEN.pow(b.scale));
      BigInteger den= b.u.multiply(BigInteger.TEN.pow(a.scale));
      if (neg){ num= num.negate(); }
      return Num$c$0Instance.instance(num,den);
    }
    catch(NumberFormatException e){
      throw err("Str.getNum: cannot convert String \""+val+"\" to Num. \n"
              + "A valid Num is in the form [-]a / b:\n"
              + "Where the numerator is any number of digits, optionally separated by \"_\"s and proceeded by a \"-\".\n"
              + "And the denominator is any number of digits optionally separated by \"_\"s but cannot be 0 or proceeded by a \"-\".\n"
              + "For example: -40 / 3, 40_330 / 3_003, 12/ 2");
    }
  }
  record Dec(BigInteger u, int scale){
    static Dec parse(String s){
      int dot= s.indexOf('.');
      if (dot == -1){ return new Dec(new BigInteger(s),0); }
      return new Dec(new BigInteger(s.substring(0,dot)+s.substring(dot+1)), s.length()-dot-1);
    }
  }
  @Override public Object imm$float$0(){
    if (!optionallySignedFloat.matcher(val).matches()){ return optEmpty(); }
    try{
      String x= no_(val);
      double d= Double.parseDouble(x);
      if (!Double.isFinite(d)){ return optEmpty(); }
      if (new BigDecimal(x).compareTo(new BigDecimal(d)) != 0){ return optEmpty(); }
      if (d == 0.0d){ d= 0.0d; }
      return optSome(Float$1c$0Instance.instance(d));
    }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  @Override public Object imm$getFloat$0(){
    if (!optionallySignedFloat.matcher(val).matches()){
      throw err("Str.getFloat: cannot convert String \""+val+"\" to float.\n" +
              "A valid Float is at least one digits and must be exactly representable as an IEEE-754 64bit number, " +
              "separated by a \".\" and then at least one more digit optionally proceeded by a sign \"+/-\"" +
              "\n For example: -2.4, 3000.2, +24.0");
    }
    try{
      String x= no_(val);
      double d= Double.parseDouble(x);
      if (!Double.isFinite(d)){ throw err("Str.getFloat: cannot create non finite float "+d+" if this is needed use Str.getIeeeFloat instead"); }
      if (new BigDecimal(x).compareTo(new BigDecimal(d)) != 0){ throw err("Str.getFloat: Float "+val+" cannot be represented exactly.\n"
              + "If rounded, the nearest representable value is "+floatExactFearlessLit(d)+"."
              + "If this rounding is desired use Str.getSoftFloat");}
      if (d == 0.0d){ d= 0.0d; }
      return Float$1c$0Instance.instance(d);
    }
    catch(NumberFormatException e){
      throw err("Str.getFloat: cannot convert String \""+val+"\" to float.\n" +
              "A valid Float is at least one digits and must be exactly representable as an IEEE-746 64bit number, " +
              "separated by a \".\" and then at least one more digit optionally proceeded by a sign \"+/-\"" +
              "\n For example: -2.4, 3000.2, +24.0");
    }
  }

  @Override public Object imm$softFloat$0(){
    if (!optionallySignedFloat.matcher(val).matches()){ return optEmpty(); }
    try{
      double d= Double.parseDouble(no_(val));
      if (!Double.isFinite(d)){ return optEmpty(); }
      if (d == 0.0d){ d= 0.0d; }
      return optSome(Float$1c$0Instance.instance(d));
    }
    catch(NumberFormatException e){ return optEmpty(); }
  }
  @Override public Object imm$getSoftFloat$0(){
    if (!optionallySignedFloat.matcher(val).matches()){
      throw err("Str.getSoftFloat: cannot convert String \""+val+"\" to float.\n" +
              "A valid Float is at least one digit then a \".\" and then at least one more digits optionally proceeded by a sign \"+/-\"" +
              "\n For example: -2.4, 3000.2, +24.0");
    }
    try{
      String x= no_(val);
      double d= Double.parseDouble(x);
      if (!Double.isFinite(d)){ throw err("Str.getSoftFloat: cannot create non finite float "+d+" if this is needed use Str.getIeeeFloat instead"); }
      if (d == 0.0d){ d= 0.0d; }
      return Float$1c$0Instance.instance(d);
    }
    catch(NumberFormatException e){
      throw err("Str.getSoftFloat: cannot convert String \""+val+"\" to float.\n" +
               "A valid Float is at least one digit separated by a \".\" and then at least one more digit optionally proceeded by a sign \"+/-\"" +
               "\n For example: -2.4, 3000.2, +24.0");
    }
  }

  // Stolen from WellFormedErrors.java
  public static String floatExactFearlessLit(double d){
    assert Double.isFinite(d);
    boolean neg= (Double.doubleToRawLongBits(d) & (1L<<63)) != 0;
    String mag= new BigDecimal(d).abs().toString(); // exact decimal for this double, may use E
    int e= mag.indexOf('E');
    if (e != -1){
      String base= mag.substring(0,e);
      String exp= mag.substring(e+1);
      if (base.indexOf('.') == -1){ base = base+".0"; }
      mag = base+"e"+exp;
    }
    else if (mag.indexOf('.') == -1){ mag = mag + ".0"; }
    return (neg ? "-" : "+") + mag;
  }

  @Override public Object read$hash$0(){
    return Nat$c$0Instance.instance(val.hashCode());
  }
  @Override public Object imm$joinStr$1(Object p0){
    Stream<Object> stream= ((Flow$o$1Instance)p0).s();
    String res= stream.map(o->((Str$c$0Instance)o).val).collect(java.util.stream.Collectors.joining(val));
    return new Str$c$0Instance(res);
  }
  @Override public Object imm$startsWith$1(Object p0){
    var other= ((Str$c$0Instance)p0).val;
    return bool(val.startsWith(other));  
  }
  @Override public Object imm$contains$1(Object p0){
    var other= ((Str$c$0Instance)p0).val;
    return bool(val.contains(other));
  }
  @Override public Object imm$lower$0(){ return new Str$c$0Instance(val.toLowerCase()); }
  @Override public Object imm$upper$0(){ return new Str$c$0Instance(val.toUpperCase()); }
  @Override public Object imm$norm$0(){
    if (val.length() < 64){ return this; }
    return myCache.computeIfAbsent(val,_->new Norm(this));
  }
  @Override public Object imm$get$0(){ return this; }
  static java.util.concurrent.ConcurrentHashMap<Object,Object> myCache= new java.util.concurrent.ConcurrentHashMap<>(); 
  static String no_(String s){ return s.indexOf('_')==-1 ? s : s.replace("_",""); }
}