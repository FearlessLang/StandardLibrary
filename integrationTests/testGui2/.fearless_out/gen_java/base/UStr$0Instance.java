package base;

import static base.Util.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public record UStr$0Instance(int[] val) implements UStr$0{
  private static final Pattern uCodeText= Pattern.compile("[0-9A-F]{1,6}(?: [0-9A-F]{1,6})*");
  private static final int[] empty= {};
  private static final int[] nl= {'\n'};
  private static final int[] quote= {'"'};
  private static final String strChars=
    "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+-*/=<>,.;:()[]{}`'\"!?@#$%^&_|~\\ \n";

  public static UStr$0 instance(int[] val){ return new UStr$0Instance(val); }
  public static UStr$0 instance(String val){ return new UStr$0Instance(val.codePoints().toArray()); }

  @Override public String toString(){ return "UStr["+strUCode(val)+"]"; }

  @Override public Object read$uStr$0(){ return this; }
  @Override public Object read$imm$0(){ return this; }

  @Override public Object imm$$plus$1(Object p0){
    return new UStr$0Instance(cat(val,((UStr$0Instance)p0).val));
  }
  @Override public Object imm$$or$1(Object p0){
    return new UStr$0Instance(cat(val,nl,((UStr$0Instance)p0).val));
  }
  @Override public Object imm$$xor$1(Object p0){
    return new UStr$0Instance(cat(val,quote,((UStr$0Instance)p0).val));
  }

  @Override public Object imm$$or$0(){ return new UStr$0Instance(cat(val,nl)); }
  @Override public Object imm$$xor$0(){ return new UStr$0Instance(cat(val,quote)); }

  @Override public Object imm$isEmpty$0(){ return bool(val.length == 0); }
  @Override public Object imm$size$0(){ return Nat$0Instance.instance(val.length); }

  @Override public Object imm$escape$0(){ return Str$0Instance.instance(escapeText(val)); }

  @Override public Object imm$scalarHex$0(){ return Str$0Instance.instance(strUCode(val)); }

  @Override public Object imm$isStr$0(){ return bool(isStr(val)); }

  @Override public Object imm$strExact$0(){
    return isStr(val)
      ? optSome(Str$0Instance.instance(new String(val,0,val.length)))
      : optEmpty();
  }
  @Override public Object imm$flow$0(){
    return new Flow$1Instance(Arrays.stream(val).mapToObj(e->Nat$0Instance.instance((long)e)));
  }
  @Override public Object imm$equalRepr$1(Object p0){
    return bool(Arrays.equals(val,((UStr$0Instance)p0).val));
  }
  @Override public Object imm$hashRepr$0(){
    return Nat$0Instance.instance(Arrays.hashCode(val));
  }
  @Override public Object imm$u$1(Object p0){
    return new UStr$0Instance(cat(val,parseUCode(((Str$0Instance)p0).val())));
  }
  @Override public Object imm$joinStr$1(Object p0){
    Stream<Object> s= ((Flow$1Instance)p0).s();
    @SuppressWarnings("unchecked")
    List<UStr$0Instance> parts= (List<UStr$0Instance>)(Object)s.toList();
    return new UStr$0Instance(join(val,parts));
  }
  @Override public Object imm$norm$0(){ return myCache.computeIfAbsent(val,_->new Norm(this)); }
  static java.util.concurrent.ConcurrentHashMap<Object,Object> myCache= new java.util.concurrent.ConcurrentHashMap<>(); 

  private static int[] cat(int[] a,int[] b){
    int size= Math.addExact(a.length,b.length);
    var res= new int[size];
    System.arraycopy(a,0,res,0,a.length);
    System.arraycopy(b,0,res,a.length,b.length);
    return res;
  }
  private static int[] cat(int[] a,int[] b,int[] c){
    int ab= Math.addExact(a.length,b.length);
    int size= Math.addExact(ab,c.length);
    var res= new int[size];
    System.arraycopy(a,0,res,0,a.length);
    System.arraycopy(b,0,res,a.length,b.length);
    System.arraycopy(c,0,res,ab,c.length);
    return res;
  }
  private static int[] join(int[] sep,List<UStr$0Instance> parts){
    if (parts.isEmpty()){ return empty; }
    int size= 0;
    for(int i= 0; i < parts.size(); i++){
      if (i != 0){ size = Math.addExact(size,sep.length); }
      size= Math.addExact(size,parts.get(i).val.length);
    }
    var res= new int[size];
    int off= 0;
    for(int i= 0; i<parts.size(); i++){
      if (i!=0){
        System.arraycopy(sep,0,res,off,sep.length);
        off += sep.length;
      }
      var cur= parts.get(i).val;
      System.arraycopy(cur,0,res,off,cur.length);
      off += cur.length;
    }
    return res;
  }
  private static boolean isStr(int[] cps){
    for(int cp: cps){ if (!isStr(cp)){ return false; } }
    return true;
  }

  private static boolean isStr(int cp){
    return cp < 128 && strChars.indexOf((char)cp) != -1;
  }
  private static String strUCode(int[] cps){
    if (cps.length==0){ return ""; }
    var res= new StringBuilder();
    for(int i= 0; i<cps.length; i++){
      if (i!=0){ res.append(' '); }
      appendHex(res,cps[i]);
    }
    return res.toString();
  }
  private static void appendHex(StringBuilder res,int cp){
    String s= Integer.toHexString(cp).toUpperCase();
    for(int i= s.length(); i < 4; i++){ res.append('0'); }
    res.append(s);
  }
  private static int[] parseUCode(String s){
    if (s.isEmpty()){ return empty; }
    if (!uCodeText.matcher(s).matches()){ throw err("Malformed UStr.u input: "+s); }
    String[] parts= s.split(" ");
    var res= new int[parts.length];
    for(int i= 0; i<parts.length; i++){
      int cp;
      try{ cp= Integer.parseInt(parts[i],16); }
      catch(NumberFormatException e){ throw err("Malformed UStr.u input: "+s); }
      if (cp > 0x10FFFF || (0xD800 <= cp && cp <= 0xDFFF)){
        throw err("Malformed UStr.u input: "+s);
      }
      res[i]= cp;
    }
    return res;
  }
  private static String escapeText(int[] cps){
    if (cps.length == 0){ return "\"\".u"; }
    var terms= new ArrayList<String>();
    for(int i= 0; i < cps.length; ){
      boolean safe= isStr(cps[i]);
      int j= i + 1;
      while (j < cps.length && isStr(cps[j]) == safe){ j++; }
      var part= Arrays.copyOfRange(cps,i,j);
      terms.add(safe ? strTerm(part) : uCodeTerm(part));
      i= j;
    }
    return joinTerms(terms);
  }
  private static String strTerm(int[] cps){
    return receiver(strExpr(new String(cps,0,cps.length)))+".u";
  }
  private static String uCodeTerm(int[] cps){
    return "\"\".u("+strExpr(strUCode(cps))+")";
  }
  private static String joinTerms(List<String> terms){
    assert !terms.isEmpty();
    if (terms.size() == 1){ return terms.getFirst(); }
    var res= new StringBuilder();
    for(int i= 0; i < terms.size(); i++){
      if (i != 0){ res.append(" + "); }
      res.append('(').append(terms.get(i)).append(')');
    }
    return res.toString();
  }
  private static String receiver(String e){
    return isOneLiteral(e) ? e : "("+e+")";
  }
  private static boolean isOneLiteral(String e){
    return e.length() >= 2
      && (e.charAt(0) == '"' || e.charAt(0) == '`')
      && e.charAt(e.length() - 1) == e.charAt(0);
  }
  private static String strExpr(String s){
    return ((Str$0Instance)Str$0Instance.instance(s).imm$escape$0()).val();
  }
  public static boolean isScalar(int cp){
    return 0 <= cp && cp <= 0x10FFFF && !(0xD800 <= cp && cp <= 0xDFFF);
  }
  public static boolean isStrScalar(int cp){
    return cp < 128 && strChars.indexOf((char)cp) != -1;
  }
}