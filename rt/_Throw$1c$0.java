package _base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public interface _Throw$1c$0{
  default Object imm$deterministic$1(Object p0){
    var this$= this;
    var info$= (Info$o$0)p0;
    throw Util.deterministic(info$);//.read$str$0().toString());
  }
  default Object imm$nonDeterministic$1(Object p0){
    var this$= this;
    var info$= (Info$o$0)p0;
    throw Util.nonDeterministic(info$);//throw new Error(info$.read$str$0().toString());
  }
  default Object imm$stackTrace$0(){
    var st= new Throwable().getStackTrace();
    var al= new ArrayList<>();
    for (var f : frames(Arrays.copyOfRange(st, 2, st.length))){ al.add(new Str$c$0Instance(fmtFrame(f))); }
    var list= new List$o$1Instance(al);
    var dtId= new DataTypeBy$17m$3(){ @Override public Object imm$$hash$1(Object p0){ return p0; } };
    return list.read$info$1(dtId);
  }
  static Set<String> machinery= Set.of("errors.fear", "action.fear", "attenuation.fear");
  static Set<String> rcs= Set.of("imm", "mut", "read", "iso", "readH", "mutH");
  static List<StackFrame$174$0> frames(StackTraceElement[] st){
    var res= new ArrayList<StackFrame$174$0>();
    var literal= false;
    for (var e : st){
      var f= frameData(e);
      if (f == null){ continue; }
      if (literal && fmtFrame(res.getLast()).equals(fmtFrame(f))){ res.removeLast(); }
      res.add(f);
      literal= load(e.getClassName()).isAnonymousClass();
    }
    return res;
  }
  static Class<?> load(String cn){
    try{ return Class.forName(cn, false, _Throw$1c$0.class.getClassLoader()); }
    catch (ClassNotFoundException ex){ throw new Error(ex); }
  }
  static StackFrame$174$0 frameData(StackTraceElement e){
    var cn= e.getClassName();
    int dot= cn.indexOf('.');
    if (dot < 0 || cn.indexOf('.', dot + 1) != -1 || e.getFileName() == null || e.getFileName().endsWith(".java") || machinery.contains(e.getFileName())){ return null; }
    var c= load(cn);
    var m= e.getMethodName();
    while (c.isAnonymousClass()){
      var em= c.getEnclosingMethod();
      if (em == null){ return null; }
      m = em.getName();
      c = c.getEnclosingClass();
    }
    if (InferredName.class.isAssignableFrom(c)){ return null; }
    var ty= c.getName().substring(dot + 1).split("\\$");
    var meth= fmtMethodName(m);
    var primed= ty.length == 4 && primes(ty[1]) >= 0;
    if (meth == null || ty.length != (primed ? 4 : 3) || !digits(ty[ty.length - 1])){ return null; }
    var type= ty[0] + "'".repeat(primed ? primes(ty[1]) : 0) + holes('[', ']', Integer.parseInt(ty[ty.length - 1]));
    return (StackFrame$174$0)StackFrames$2e8$0.instance.read$$hash$4(
      new Str$c$0Instance(e.getFileName()),
      new Str$c$0Instance(type),
      new Str$c$0Instance(meth),
      Nat$c$0Instance.instance(e.getLineNumber())
    );
  }
  static String fmtFrame(StackFrame$174$0 f){
    return fmtFrame(Util.toS(f.imm$fileName$0()), Util.toS(f.imm$typeName$0()), Util.toS(f.imm$methodName$0()), Util.toS(f.imm$lineNumber$0()));
  }
  static String fmtFrame(String fn, String tn, String mn, String ln){
    int sp= mn.indexOf(' ');
    assert sp > 0;
    var meth= mn.substring(0, sp+1)+tn+mn.substring(sp+1);
    return meth+" error line: "+ln+" in file "+fn;
  }
  static boolean digits(String s){ return !s.isEmpty() && s.chars().allMatch(c -> c >= '0' && c <= '9'); }
  static int primes(String s){ return s.startsWith("p") && digits(s.substring(1)) ? Integer.parseInt(s.substring(1)) : -1; }
  static String fmtMethodName(String m){
    int a= m.indexOf('$');
    int b= m.lastIndexOf('$');
    if (a <= 0 || b <= a || !rcs.contains(m.substring(0, a)) || !digits(m.substring(b + 1))){ return null; }
    int n= Integer.parseInt(m.substring(b + 1));
    var rc= m.substring(0, a);
    var mid= m.substring(a + 1, b);
    if (mid.charAt(0) == '$'){ return rc+" "+decodeOp(mid.substring(1))+holes('(', ')', n); }
    int p= mid.lastIndexOf('$');
    if (p > 0){ mid = mid.substring(0, p)+"'".repeat(primes(mid.substring(p + 1))); }
    return rc+" ."+mid+holes('(', ')', n);
  }
  static String holes(char open, char close, int n){
    if (n==0){ return ""; }
    var sb= new StringBuilder().append(open);
    for (int i= 0;i<n;i += 1){ if (i!=0){ sb.append(','); } sb.append('_'); }
    return sb.append(close).toString();
  }
  static String decodeOp(String enc){
    var ps= enc.split("_");
    var sb= new StringBuilder();
    for (var p : ps){ sb.append(opChar(p)); }
    return sb.toString();
  }
  static char opChar(String tok){ return switch (tok){
    case "plus" -> '+';
    case "dash" -> '-';
    case "star" -> '*';
    case "slash" -> '/';
    case "pct" -> '%';
    case "lt" -> '<';
    case "gt" -> '>';
    case "eq" -> '=';
    case "bang" -> '!';
    case "and" -> '&';
    case "or" -> '|';
    case "xor" -> '^';
    case "tilde" -> '~';
    case "q" -> '?';
    case "hash" -> '#';
    case "bslash" -> '\\';
    default -> throw new Error();
  };}
  
 
  _Throw$1c$0 instance= new _Throw$1c$0(){};}