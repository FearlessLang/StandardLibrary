package base;

import static base.Util.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//TODO: still need to add the 3 different ways to turn it into a Flow.

final class GraphemeOps {
/*  public static boolean isPredictable(String grapheme){
    if (grapheme.length() != 1){ return false; }
    char c= grapheme.charAt(0);
    return UStr$0Instance.isSafeAscii(c) || c == '\n';
  }*/
/*  public static Function<String,String> simpleOr(Function<String,String> fallback){
    return g -> isPredictable(g) ? g : fallback.apply(g);
  }*/
/*  public static Comparator<String> simpleOr(Comparator<String> fallback){
    return (g1,g2) -> {
      boolean p1= isPredictable(g1);
      boolean p2= isPredictable(g2);
      if (p1 && p2){ return Character.compare(g1.charAt(0), g2.charAt(0)); }
      if (p1){ return -1; }//Safe ASCII comes first
      if (p2){ return 1; } //Unpredictable Unicode comes later
      return fallback.compare(g1, g2);
    };
  }*/
/*  public static ToIntFunction<String> simpleOr(ToIntFunction<String> fallback){
    return g -> isPredictable(g) ? g.charAt(0) : fallback.applyAsInt(g);
  }*/
/*  public static final Predicate<String> isComposite= g ->
    g.codePointCount(0, g.length()) > 1;*/
}
/*
final class Fallbacks{
  public static final Function<String,String> toCodePoints= g -> {
    var iter= g.codePoints().iterator();
    var sb= new StringBuilder("U+`");
    while (iter.hasNext()){
      sb.append(String.format("%04X", iter.nextInt()));
      if (iter.hasNext()){ sb.append(" "); }
    }
    return sb.append("`").toString();
  };
  public static final Comparator<String> bytewiseUft8= (g1,g2) ->
    Arrays.compareUnsigned(
      g1.getBytes(StandardCharsets.UTF_8),
      g2.getBytes(StandardCharsets.UTF_8)
    );
  public static final ToIntFunction<String> byteHash= g ->
    Arrays.hashCode(g.getBytes(StandardCharsets.UTF_8));
}*/

interface IntView{//Note: exists only for boxing performance
  PrimitiveIterator.OfInt iterator();

  default int compare(IntView o, IntBinaryOperator cmp){
    var i1= iterator();
    var i2= o.iterator();
    while (i1.hasNext() && i2.hasNext()){
      int c= cmp.applyAsInt(i1.nextInt(), i2.nextInt());
      if (c != 0){ return c; }
    }
    return Boolean.compare(i1.hasNext(), i2.hasNext());
  }
  default int hash(IntUnaryOperator hasher){
    var i= iterator();
    int h= 1;
    while (i.hasNext()){ h = 31 * h + hasher.applyAsInt(i.nextInt()); }
    return h;
  }
  default String asString(IntFunction<String> mapper){
    var i= iterator();
    var sb= new StringBuilder();
    while (i.hasNext()){ sb.append(mapper.apply(i.nextInt())); }
    return sb.toString();
  }
  default boolean startsWith(IntView prefix, IntBinaryOperator cmp){
    var iThis= iterator();
    var iOther= prefix.iterator();
    while (iOther.hasNext()){
      if (!iThis.hasNext()){ return false; }
      if (cmp.applyAsInt(iThis.nextInt(), iOther.nextInt()) != 0){ return false; }
    }
    return true;
  }
}

interface ObjView<T>{
  Iterator<T> iterator();

  default int compare(ObjView<T> o, Comparator<T> cmp){
    var i1= iterator();
    var i2= o.iterator();
    while (i1.hasNext() && i2.hasNext()){
      int c= cmp.compare(i1.next(), i2.next());
      if (c != 0){ return c; }
    }
    return Boolean.compare(i1.hasNext(), i2.hasNext());
  }
  default int hash(ToIntFunction<T> hasher){
    var i= iterator();
    int h= 1;
    while (i.hasNext()){ h = 31 * h + hasher.applyAsInt(i.next()); }
    return h;
  }
  default String asString(Function<T,String> mapper){
    var i= iterator();
    var sb= new StringBuilder();
    while (i.hasNext()){ sb.append(mapper.apply(i.next())); }
    return sb.toString();
  }
  default boolean startsWith(ObjView<T> prefix, BiPredicate<T,T> eq){
    var iThis= iterator();
    var iOther= prefix.iterator();
    while (iOther.hasNext()){
      if (!iThis.hasNext()){ return false; }
      if (!eq.test(iThis.next(), iOther.next())){ return false; }
    }
    return true;
  }
}

public final class UStr$0Instance implements UStr$0{
  private static final Pattern graphemesRegex= Pattern.compile("\\X");
  private final String val;
  public String val(){ return val; }
  private int sizeBytes= -1;
  private int sizeCodepoints= -1;//TODO: remove? this is just val.lenght()?
  private int sizeGraphemes= -1;
  private UStr$0Instance(String val){ this.val= val; }

  //TODO: when used, need to also fill the cache; but it may be expesive to handle this? discuss.
  public IntView asCodepoints(){ return () -> val.codePoints().iterator(); }

  public IntView asBytes(){
    return () -> {
      var bs= utf8();
      return IntStream.range(0, bs.length).map(i -> Byte.toUnsignedInt(bs[i])).iterator();
    };
  }
  //TODO: when used, need to also fill the cache; but it may be expesive to handle this? discuss.
  public ObjView<String> asGraphemes(){
    return () -> graphemesRegex.matcher(val).results().map(MatchResult::group).iterator();
  }

  private byte[] utf8(){
    var res= val.getBytes(StandardCharsets.UTF_8);
    sizeBytes= res.length;
    return res;
  }
  @Override public Object imm$sizeBytes$0(){
    if (sizeBytes >= 0){ return Nat$0Instance.instance(sizeBytes); }
    return Nat$0Instance.instance(utf8().length);
  }
  @Override public Object imm$sizeCodePoints$0(){
    if (sizeCodepoints >= 0){ return Nat$0Instance.instance(sizeCodepoints); }
    sizeCodepoints= val.codePointCount(0, val.length());
    return Nat$0Instance.instance(sizeCodepoints);
  }
  private int sizeGraphemes(){
    if (sizeGraphemes >= 0){ return sizeGraphemes; }
    sizeGraphemes= Math.toIntExact(graphemesRegex.matcher(val).results().count());
    return sizeGraphemes;
  }

  public static UStr$0 instance(String val){ return new UStr$0Instance(val); }

  private static String s(Object o){ return ((UStr$0Instance)o).val; }

  private String toUExpr(){
    int baselineLen= val.length() + 2;
    int[] cps= val.codePoints().toArray();
    var sb= new StringBuilder(baselineLen + 16).append('"');
    for(int i= 0; i < cps.length; ){
      int cp= cps[i];
      if (cp == '"'){ sb.append("\" ^^ \""); i++; continue; }
      if (cp == '\n'){ sb.append("\" | \""); i++; continue; }
      if (isSafeAscii(cp)){ sb.append((char)cp); i++; continue; }
      sb.append("\" + (U+`");
      boolean first= true;
      while (i < cps.length && isUnsafeForU(cps[i])){
        if (!first){ sb.append(' '); }
        sb.append(String.format("%04X", cps[i]));
        first= false;
        i++;
      }
      sb.append("`) + \"");
    }
    sb.append('"');
    String expr= sb.toString();
    return expr.length() == baselineLen ? expr : "("+expr+")";
  }

  static boolean isSafeAscii(int cp){ return cp >= 0x20 && cp <= 0x7E; }

  private static boolean isUnsafeForU(int cp){
    if (cp == '"'){ return false; }
    if (cp == '\n'){ return false; }
    return !isSafeAscii(cp);
  }

  @Override public String toString(){ return toUExpr(); }

  @Override public Object read$imm$0(){ return this; }
  @Override public Object read$imm$1(Object p0){ return this; }

  private static UStr$0Instance catNoMerge(String a, String b){
    var sb= new StringBuilder(a.length()+b.length()+1).append(a);
    GraphemeBarrier.appendNoMerge(sb, b);
    return new UStr$0Instance(sb.toString());
  }
  private static UStr$0Instance catNoMerge(String a, String b, String c){
    var sb= new StringBuilder(a.length()+b.length()+c.length()+2).append(a);
    GraphemeBarrier.appendNoMerge(sb, b);
    GraphemeBarrier.appendNoMerge(sb, c);
    return new UStr$0Instance(sb.toString());
  }

  @Override public Object imm$merge$1(Object p0){ return instance(val+s(p0)); }

  @Override public Object imm$$plus$1(Object p0){ return catNoMerge(val, s(p0)); }

  @Override public Object imm$$or$1(Object p0){ return instance(val+"\n"+s(p0)); }

  @Override public Object imm$$xor$1(Object p0){ return catNoMerge(val, "`", s(p0)); }

  @Override public Object imm$$xor_xor$1(Object p0){ return catNoMerge(val, "\"", s(p0)); }

  @Override public Object imm$$or$0(){ return instance(val+"\n"); }

  @Override public Object imm$$xor$0(){ return catNoMerge(val, "`"); }

  @Override public Object imm$$xor_xor$0(){ return catNoMerge(val, "\""); }

  @Override public Object imm$isEmpty$0(){ return bool(val.isEmpty()); }

  @Override public Object imm$size$0(){ return Nat$0Instance.instance(sizeGraphemes()); }

  @Override public Object imm$escape$0(){ return new Str$0Instance(toUExpr()); }

  private static int hashNat(OrderHashBy$2 by, int x){
    return natToInt(((OrderHash$1)by.imm$$hash$1(Nat$0Instance.instance(x))).read$hash$0());
  }
  @Override public Object read$cmpCodePoints$4(Object p0,Object p1,Object p2,Object m){
    var a= (UStr$0Instance)p1;
    var b= (UStr$0Instance)p2;
    int c= a.asCodepoints().compare(b.asCodepoints(),
      (x,y) -> cmp((OrderBy$2)p0, Nat$0Instance.instance(x), Nat$0Instance.instance(y))
    );
    return ord(c, m);
  }
  @Override public Object read$hashCodePoints$1(Object p0){
    var by= (OrderHashBy$2)p0;
    int h= asCodepoints().hash(x -> hashNat(by, x));
    return Nat$0Instance.instance(h);
  }
  @Override public Object read$cmpBytes$4(Object p0,Object p1,Object p2,Object m){
    var a= (UStr$0Instance)p1;
    var b= (UStr$0Instance)p2;
    int c= a.asBytes().compare(b.asBytes(),
      (x,y) -> cmp((OrderBy$2)p0, Nat$0Instance.instance(x), Nat$0Instance.instance(y))
    );
    return ord(c, m);
  }
  @Override public Object read$hashBytes$1(Object p0){
    var by= (OrderHashBy$2)p0;
    int h= asBytes().hash(x -> hashNat(by, x));
    return Nat$0Instance.instance(h);
  }
  @Override public Object read$cmp$4(Object p0,Object p1,Object p2,Object m){
    var a= (UStr$0Instance)p1;
    var b= (UStr$0Instance)p2;
    int c= a.asGraphemes().compare(b.asGraphemes(), (g1,g2) -> cmp((OrderBy$2)p0, instance(g1), instance(g2)));
    return ord(c, m);
  }
  @Override public Object read$hash$1(Object p0){
    var by= (OrderHashBy$2)p0;
    int h= asGraphemes().hash(g -> natToInt(((OrderHash$1)by.imm$$hash$1(instance(g))).read$hash$0()));
    return Nat$0Instance.instance(h);
  }
  @Override public Object imm$joinStr$1(Object p0){
    Stream<Object> stream= ((Flow$1Instance)p0).s();
    var it= stream.iterator();
    if (!it.hasNext()){ return new UStr$0Instance(""); }
    var sb= new StringBuilder().append(s(it.next()));
    if (val.isEmpty()){
      while (it.hasNext()){ GraphemeBarrier.appendNoMerge(sb, s(it.next())); }
      return new UStr$0Instance(sb.toString());
    }
    while (it.hasNext()){
      GraphemeBarrier.appendNoMerge(sb, val);
      GraphemeBarrier.appendNoMerge(sb, s(it.next()));
    }
    return new UStr$0Instance(sb.toString());
  }
  @Override public Object imm$startsWithStr$1(Object p0){
    var other= ((Str$0Instance)p0).val();
    return bool(val.startsWith(other));
  }
  @Override public Object imm$startsWithCodePoints$2(Object p0,Object p1){
    var other= (UStr$0Instance)p1;
    return bool(asCodepoints().startsWith(other.asCodepoints(),
      (x,y) -> cmp((OrderBy$2)p0, Nat$0Instance.instance(x), Nat$0Instance.instance(y))
    ));
  }
  @Override public Object imm$startsWithBytes$2(Object p0,Object p1){
    var other= (UStr$0Instance)p1;
    return bool(asBytes().startsWith(other.asBytes(),
      (x,y) -> cmp((OrderBy$2)p0, Nat$0Instance.instance(x), Nat$0Instance.instance(y))
    ));
  }
  @Override public Object imm$startsWith$2(Object p0,Object p1){
    var other= (UStr$0Instance)p1;
    return bool(asGraphemes().startsWith(other.asGraphemes(),
      (g1,g2) -> cmp((OrderBy$2)p0, instance(g1), instance(g2)) == 0
    ));
  }
  @Override public Object imm$endsWithStr$1(Object p0){
    var other= ((Str$0Instance)p0).val();
    return bool(val.endsWith(other));
  }
  @Override public Object imm$endsWithCodePoints$2(Object p0,Object p1){
    var other= (UStr$0Instance)p1;
    int[] a= val.codePoints().toArray();
    int[] b= other.val().codePoints().toArray();
    if (b.length > a.length){ return bool(false); }
    for(int i= 1; i <= b.length; i++){
      int c= cmp((OrderBy$2)p0,
        Nat$0Instance.instance(a[a.length-i]),
        Nat$0Instance.instance(b[b.length-i])
      );
      if (c != 0){ return bool(false); }
    }
    return bool(true);
  }
  @Override public Object imm$endsWithBytes$2(Object p0,Object p1){
    var other= (UStr$0Instance)p1;
    byte[] a= utf8();
    byte[] b= other.utf8();
    if (b.length > a.length){ return bool(false); }
    for(int i= 1; i <= b.length; i++){
      int c= cmp((OrderBy$2)p0,
        Nat$0Instance.instance(Byte.toUnsignedInt(a[a.length-i])),
        Nat$0Instance.instance(Byte.toUnsignedInt(b[b.length-i]))
      );
      if (c != 0){ return bool(false); }
    }
    return bool(true);
  }
  @Override public Object imm$endsWith$2(Object p0,Object p1){
    var other= (UStr$0Instance)p1;
    String[] a= graphemesRegex.matcher(val).results().map(MatchResult::group).toArray(String[]::new);
    String[] b= graphemesRegex.matcher(other.val).results().map(MatchResult::group).toArray(String[]::new);
    if (b.length > a.length){ return bool(false); }
    for(int i= 1; i <= b.length; i++){
      int c= cmp((OrderBy$2)p0, instance(a[a.length-i]), instance(b[b.length-i]));
      if (c != 0){ return bool(false); }
    }
    return bool(true);
  }
  private static final ToInfoBy$1 idInfoBy= new ToInfoBy$1(){//ID function to call .info on list!
    @Override public Object imm$$hash$1(Object p0){ return (Info$0)p0; }
  };
  private static String strPart(ToStrBy$1 by, Object x){
    return ((Str$0Instance)((ToStr$0)by.imm$$hash$1(x)).read$str$0()).val();
  }
  private static Info$0 infoPart(ToInfoBy$1 by, Object x){
    return (Info$0)((ToInfo$0)by.imm$$hash$1(x)).read$info$0();
  }
  @Override public Object read$str$1(Object p0){
    var by= (ToStrBy$1)p0;
    var it= asGraphemes().iterator();
    var sb= new StringBuilder();
    while (it.hasNext()){ sb.append(strPart(by, instance(it.next()))); }
    return Str$0Instance.instance(sb.toString());
  }
  @Override public Object read$strCodePoints$1(Object p0){
    var by= (ToStrBy$1)p0;
    var it= asCodepoints().iterator();
    var sb= new StringBuilder();
    while (it.hasNext()){ sb.append(strPart(by, Nat$0Instance.instance(it.nextInt()))); }
    return Str$0Instance.instance(sb.toString());
  }
  @Override public Object read$strBytes$1(Object p0){
    var by= (ToStrBy$1)p0;
    var it= asBytes().iterator();
    var sb= new StringBuilder();
    while (it.hasNext()){ sb.append(strPart(by, Nat$0Instance.instance(it.nextInt()))); }
    return Str$0Instance.instance(sb.toString());
  }
  @Override public Object read$info$1(Object p0){
    var by= (ToInfoBy$1)p0;
    var it= asGraphemes().iterator();
    var infos= new ArrayList<Object>();
    while (it.hasNext()){ infos.add(infoPart(by, instance(it.next()))); }
    return new List$1Instance(infos).read$info$1(idInfoBy);
  }
  @Override public Object read$infoCodePoints$1(Object p0){
    var by= (ToInfoBy$1)p0;
    var it= asCodepoints().iterator();
    var infos= new ArrayList<Object>();
    while (it.hasNext()){ infos.add(infoPart(by, Nat$0Instance.instance(it.nextInt()))); }
    return new List$1Instance(infos).read$info$1(idInfoBy);
  }
  @Override public Object read$infoBytes$1(Object p0){
    var by= (ToInfoBy$1)p0;
    var it= asBytes().iterator();
    var infos= new ArrayList<Object>();
    while (it.hasNext()){ infos.add(infoPart(by, Nat$0Instance.instance(it.nextInt()))); }
    return new List$1Instance(infos).read$info$1(idInfoBy);
  }
}
final class GraphemeBarrier{
  private static final Pattern gb= Pattern.compile("\\b{g}");
  static final char zwsp= '\u200B';

  static boolean needsZwsp(CharSequence a, CharSequence b){
    if (a.length() == 0 || b.length() == 0){ return false; }
    int k= a.length();
    var m= gb.matcher(new Concat(a, b));
    m.region(k, k + b.length());
    return !m.lookingAt();
  }
  static void appendNoMerge(StringBuilder sb, String s){
    if (sb.length() != 0 && s.length() != 0 && needsZwsp(sb, s)){ sb.append(zwsp); }
    sb.append(s);
  }
  record Concat(CharSequence a, CharSequence b) implements CharSequence{
    @Override public int length(){ return a.length() + b.length(); }
    @Override public char charAt(int i){
      int al= a.length();
      return i < al ? a.charAt(i) : b.charAt(i - al);
    }
    @Override public CharSequence subSequence(int s, int e){
      int al= a.length();
      if (e <= al){ return a.subSequence(s, e); }
      if (s >= al){ return b.subSequence(s - al, e - al); }
      return new Concat(a.subSequence(s, al), b.subSequence(0, e - al));
    }
  }
}