package base;

import java.util.ArrayList;

public interface _Throw$0{
  default Object imm$deterministic$1(Object p0){
    var this$= this;
    var info$= (Info$0)p0;
    throw Util.deterministic(info$);//.read$str$0().toString());
  }
  default Object imm$nonDeterministic$1(Object p0){
    var this$= this;
    var info$= (Info$0)p0;
    throw Util.nonDeterministic(info$);//throw new Error(info$.read$str$0().toString());
  }
  default Object imm$stackTrace$0(){
    var st= new Throwable().getStackTrace();
    var al= new ArrayList<>();
    for (int i= 2; i < st.length; i++){
      var f= frameData(st[i]);
      if (f == null){ continue; }
      al.add(new Str$0Instance(fmtFrame(f)));
    }
    var list= new List$1Instance(al);
    var dtId= new DataTypeBy$3(){ @Override public Object imm$$hash$1(Object p0){ return p0; } };
    return list.read$info$1(dtId);
  }
  static StackFrame$0 frameData(StackTraceElement e){
    var cn= e.getClassName();
    int dot= cn.indexOf('.');
    if (dot < 0 || cn.indexOf('.',dot + 1) != -1){ return null; }
    var ty= cn.substring(dot + 1);
    int dol= ty.lastIndexOf('$');
    if (dol < 0 || dol == ty.length() - 1 || ty.indexOf('$') != dol){ return null; }
    var ds= ty.substring(dol + 1);
    for (int i= 0; i < ds.length(); i++){
      char c= ds.charAt(i);
      if (c < '0' || c > '9'){ return null; }
    }
    int targs= Integer.parseInt(ds);
    var type= ty.substring(0,dol) + holes('[',']',targs);
    var meth= fmtMethodName(e.getMethodName());
    return (StackFrame$0)StackFrames$0.instance.read$$hash$4(
      new Str$0Instance(e.getFileName()),
      new Str$0Instance(type),
      new Str$0Instance(meth),
      Nat$0Instance.instance(e.getLineNumber())
    );
  }
  static String fmtFrame(StackFrame$0 f){
    var fn= Util.toS(f.imm$fileName$0());
    var tn= Util.toS(f.imm$typeName$0());
    var mn= Util.toS(f.imm$methodName$0());
    var ln= Util.toS(f.imm$lineNumber$0());
    int sp= mn.indexOf(' ');
    assert sp > 0;
    var meth= mn.substring(0,sp+1)+tn+mn.substring(sp+1);
    return meth+" error line: "+ln+" in file "+fn;
  }
  static String fmtMethodName(String m){
    int a= m.indexOf('$');
    int b= m.lastIndexOf('$');
    assert a > 0 && b > a;
    int n= Integer.parseInt(m.substring(b + 1));
    var rc= m.substring(0,a);
    var mid= m.substring(a + 1,b);
    if(mid.charAt(0) == '$'){ return rc+" "+decodeOp(mid.substring(1))+holes('(',')',n); }
    return rc+" ."+mid+holes('(',')',n);
  }
  static String holes(char open,char close,int n){
    if(n==0){ return ""; }
    var sb=new StringBuilder().append(open);
    for(int i=0;i<n;i++){ if(i!=0){ sb.append(','); } sb.append('_'); }
    return sb.append(close).toString();
  }
  static String decodeOp(String enc){
    var ps=enc.split("_");
    var sb=new StringBuilder();
    for(var p:ps){ sb.append(opChar(p)); }
    return sb.toString();
  }
  static char opChar(String tok){ return switch(tok){
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
  
 
  _Throw$0 instance= new _Throw$0(){};}