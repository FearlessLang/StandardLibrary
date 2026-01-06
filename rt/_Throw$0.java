package base;

import java.util.ArrayList;

public interface _Throw$0{
  default Object imm$deterministic$1(Object p0){
    var this$= this;
    var info$= (Info$0)p0;
    throw new RuntimeException(info$.read$str$0().toString());
  }
  default Object imm$nonDeterministic$1(Object p0){
    var this$= this;
    var info$= (Info$0)p0;
    throw new Error(info$.read$str$0().toString());
  }
  /*default Object imm$stackTrace$0(){
    var this$= this;
    var al=new ArrayList<>();
    al.add(new Str$0Instance("AAA"));
    al.add(new Str$0Instance("BBB"));
    var list= new List$1Instance(al);
    var dtId=new DataTypeBy$3(){ @Override public Object imm$$hash$1(Object p0){ return p0; } };
    return list.read$info$1(dtId);
  }*/
  default Object imm$stackTrace$0(){
    var st= new Throwable().getStackTrace();
    var al= new ArrayList<>();
    for(int i= 2; i < st.length; i++){
      var e= st[i];
      var cn= e.getClassName();
      if (cn.startsWith("java.")||cn.startsWith("jdk.")||cn.startsWith("sun.")){ continue; }
      al.add(new Str$0Instance(cleanType(cn)+" "+cleanMeth(e.getMethodName())));
    }
    var list= new List$1Instance(al);
    var dtId= new DataTypeBy$3(){ @Override public Object imm$$hash$1(Object p0){ return p0; } };
    return list.read$info$1(dtId);
  }
  private static String cleanType(String cn){
    var segs= cn.split("\\.");
    var sb= new StringBuilder();
    for(int i= 0; i < segs.length; i++){
      //if (i == 0 && segs[i].equals("base")){ continue; }
      if (sb.length() != 0){ sb.append('.'); }
      sb.append(cleanTypeSeg(segs[i]));
    }
    return sb.toString();
  }
  private static String cleanTypeSeg(String s){
    if (s.endsWith("Instance")){ s = s.substring(0,s.length()-8); }
    int first= s.indexOf('$');//TODO: this looks strange GPT to fix
    int last= s.lastIndexOf('$');
    if (first == last && last != -1){
      var tail= s.substring(last+1);
      if (isDigits(tail)){
        int n= Integer.parseInt(tail);
        var head=s.substring(0,last);
        if (n == 0){ return head; }
        return head+"["+holes(n)+"]";
      }
    }
    return s.replace('$','.');
  }
  private static String holes(int n){
    var sb=new StringBuilder();
    for(int i= 0; i < n; i++){ if( i != 0){ sb.append(','); } sb.append('_'); }
    return sb.toString();
  }
  private static boolean isDigits(String s){
    if (s.isEmpty()){ return false; }
    for(int i= 0; i < s.length(); i++){
      if (!Character.isDigit(s.charAt(i))){ return false; }
    }
    return true;
  }
  private static String cleanMeth(String m){
    int j= m.indexOf('$');
    if (j < 0){ return m; }
    var rc= m.substring(0,j);
    if (!rc.equals("imm")&&!rc.equals("read")&&!rc.equals("mut")){ return m; }
    int k= m.lastIndexOf('$');
    if (k <= j){ return m; }
    int n; try{ n= Integer.parseInt(m.substring(k + 1)); }
    catch(NumberFormatException ex){ return m; }
    var enc= m.substring(j + 1,k);
    var name= enc.startsWith("$") ? decodeOp(enc.substring(1)) : "."+enc;
    return rc+" "+name+args(n);
  }
  private static String args(int n){
    if (n == 0){ return ""; }
    if (n == 1){ return "(_)"; }
    var sb=new StringBuilder("(");//TODO: this is also insane shoult at least reuse the code above?
    for(int i= 0; i < n; i++){ if(i != 0){ sb.append(','); } sb.append('_'); }
    return sb.append(')').toString();
  }
  private static String decodeOp(String enc){
    var ps= enc.split("_");
    var sb= new StringBuilder();
    for (var p: ps){ sb.append(opChar(p)); }
    return sb.toString();
  }
  private static char opChar(String tok){ return switch(tok){
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