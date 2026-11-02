package _base;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

final class TextCleaning{
  static final Object[] reasons= {reason(0), reason(1), reason(2), reason(3)};
  static final String[] reasonNames= {"malformedEncoding", "truncatedEncoding", "notScalar", "notInStr"};
  static final int malformedEncoding= 0;
  static final int truncatedEncoding= 1;
  static final int notScalar= 2;
  static final int notInStr= 3;
  static final Object emptyUStr= UStr$s$0Instance.instance(new int[0]);
  static final Object emptyStr= Str$c$0Instance.instance("");

  static Object reason(int id){
    return new BadTextReason$9wg$0(){
      @Override public Object imm$match$1(Object p0){
        var m= (BadTextReasonMatch$8suo$1)p0;
        return switch (id){
          case 0 -> m.mut$malformedEncoding$0();
          case 1 -> m.mut$truncatedEncoding$0();
          case 2 -> m.mut$notScalar$0();
          case 3 -> m.mut$notInStr$0();
          default -> throw new AssertionError(id);
          };
      }
    };
  }

  static Object uStr(Object maker, byte[] bs){ return run(new Run(true, maker), bs); }
  static Object str(Object maker, byte[] bs){ return run(new Run(false, maker), bs); }
  static Object run(Run r, byte[] bs){
    for (var b : bs){ r.next(b & 0xFF); }
    r.finish();
    return r.result();
  }

  static final class Run{
    final boolean u;
    final F$3$2 maker;
    Object cur;
    boolean fresh;
    final StringBuilder chars= new StringBuilder();
    int[] scalars= new int[16];
    int size;
    Run(boolean u, Object maker){
      this.u= u;
      this.maker= (F$3$2) maker;
      start(empty(u));
    }
    void start(Object text){
      cur = maker.read$$hash$1(text);
      fresh = isEmpty(text);
    }
    void next(int b){
      fresh = false;
      var bb= Byte$o$0Instance.instance((byte) b);
      cut(u ? ((UStrCleaner$2tc$0) cur).mut$nextByte$1(bb) : ((StrCleaner$18g$0) cur).mut$nextByte$1(bb));
    }
    void finish(){
      while (!fresh && cut(u ? ((UStrCleaner$2tc$0) cur).mut$end$0() : ((StrCleaner$18g$0) cur).mut$end$0())){}
    }
    boolean cut(Object r){
      if (!u){
        var s= ((Str$c$0Instance) r).val();
        int i= s.indexOf('\n');
        if (i < 0){ chars.append(s); return false; }
        chars.append(s, 0, i + 1);
        start(Str$c$0Instance.instance(s.substring(i + 1)));
        return true;
      }
      var v= ((UStr$s$0Instance) r).val();
      int i= 0;
      while (i < v.length && v[i] != '\n'){ i += 1; }
      if (i == v.length){ add(v, v.length); return false; }
      add(v, i + 1);
      start(UStr$s$0Instance.instance(Arrays.copyOfRange(v, i + 1, v.length)));
      return true;
    }
    void add(int[] v, int len){
      if (size + len > scalars.length){ scalars = Arrays.copyOf(scalars, Math.max(Math.multiplyExact(scalars.length, 2), size + len)); }
      if (len == 1){ scalars[size++] = v[0]; return; }
      System.arraycopy(v, 0, scalars, size, len);
      size += len;
    }
    Object result(){ return u ? UStr$s$0Instance.instance(Arrays.copyOf(scalars, size)) : Str$c$0Instance.instance(chars.toString()); }
    String javaText(){ return u ? new String(scalars, 0, size) : chars.toString(); }
  }

  static void feed(Run r, CharSequence s, int from, int to){
    for (int i= from; i < to; i += 1){
      int c= s.charAt(i);
      boolean pair= Character.isHighSurrogate((char) c) && i + 1 < to && Character.isLowSurrogate(s.charAt(i + 1));
      if (pair){ c = Character.toCodePoint((char) c, s.charAt(i + 1)); i += 1; }
      utf8(r, c);
    }
  }
  static void utf8(Run r, int c){
    if (c < 0x80){ r.next(c); return; }
    if (c < 0x800){ r.next(0xC0 | c >> 6); r.next(0x80 | c & 0x3F); return; }
    if (c < 0x10000){ r.next(0xE0 | c >> 12); r.next(0x80 | c >> 6 & 0x3F); r.next(0x80 | c & 0x3F); return; }
    r.next(0xF0 | c >> 18);
    r.next(0x80 | c >> 12 & 0x3F);
    r.next(0x80 | c >> 6 & 0x3F);
    r.next(0x80 | c & 0x3F);
  }

  static Object empty(boolean u){ return u ? emptyUStr : emptyStr; }
  static boolean isEmpty(Object t){ return t instanceof Str$c$0Instance s ? s.val().isEmpty() : ((UStr$s$0Instance) t).val().length == 0; }
  static int[] scalars(Object t){ return t instanceof Str$c$0Instance s ? s.val().codePoints().toArray() : ((UStr$s$0Instance) t).val(); }
  static void add(StringBuilder sb, Object t){
    if (t instanceof Str$c$0Instance s){ sb.append(s.val()); return; }
    for (int c : ((UStr$s$0Instance) t).val()){ sb.appendCodePoint(c); }
  }
  static Object text(StringBuilder sb, boolean u){
    if (sb.isEmpty()){ return empty(u); }
    char c= sb.charAt(0);
    var res= sb.length() != 1 || c > 0xFF ? (u ? UStr$s$0Instance.instance(sb.toString()) : Str$c$0Instance.instance(sb.toString()))
      : u ? UStr$s$0Instance.size1(c) : Str$c$0Instance.size1(c);
    sb.setLength(0);
    return res;
  }
  static int u8(Object b){ return Byte.toUnsignedInt(Byte$o$0Instance.unwrap(b)); }
  static Object nat(long n){ return Nat$c$0Instance.instance(n); }
  static F$3$2 maker(Function<Object,Object> make){
    return new F$3$2(){ @Override public Object read$$hash$1(Object start){ return make.apply(start); } };
  }

  interface Sink{
    void scalar(int cp, long at, int len);
    void bad(int reason, long at, int len, long rejected);
  }

  abstract static class Decoder{
    final int[] p= new int[4];
    int n;
    long at;
    Sink out;
    boolean partial(){ return n != 0; }
    abstract void feed(int b);
    abstract void end();
    void push(int b){ p[n++] = b; }
    void scalar(int cp, int len){ out.scalar(cp, at, len); drop(len); }
    void bad(int reason, int len, long rejected){ out.bad(reason, at, len, rejected); drop(len); }
    void drop(int len){
      System.arraycopy(p, len, p, 0, n - len);
      n -= len;
      at += len;
    }
  }

  static final class Utf8 extends Decoder{
    int need;
    boolean overlong;
    @Override void feed(int b){
      if (n == 0){ start(b); return; }
      if (b < 0x80 || b > 0xBF){ bad(malformedEncoding, n, p[0]); feed(b); return; }
      push(b);
      if (n == 2 && (p[0] == 0xE0 && b < 0xA0 || p[0] == 0xF0 && b < 0x90)){ overlong = true; }
      if (n < need){ return; }
      if (overlong){ bad(malformedEncoding, n, p[0]); return; }
      int cp= decode();
      if (UStr$s$0Instance.isScalar(cp)){ scalar(cp, n); return; }
      bad(notScalar, n, cp);
    }
    void start(int b){
      push(b);
      if (b < 0x80){ scalar(b, 1); return; }
      if (b < 0xC0 || b > 0xF7){ bad(malformedEncoding, 1, b); return; }
      overlong = b < 0xC2;
      need = b < 0xE0 ? 2 : b < 0xF0 ? 3 : 4;
    }
    int decode(){
      return switch (need){
        case 2 -> (p[0] & 0x1F) << 6 | p[1] & 0x3F;
        case 3 -> (p[0] & 0x0F) << 12 | (p[1] & 0x3F) << 6 | p[2] & 0x3F;
        default -> (p[0] & 0x07) << 18 | (p[1] & 0x3F) << 12 | (p[2] & 0x3F) << 6 | p[3] & 0x3F;
        };
    }
    @Override void end(){
      if (n == 0){ return; }
      bad(overlong ? malformedEncoding : truncatedEncoding, n, p[0]);
    }
  }

  static final class Utf16 extends Decoder{
    final boolean le;
    Utf16(boolean le){ this.le= le; }
    int unit(int i){ return le ? p[i] | p[i + 1] << 8 : p[i] << 8 | p[i + 1]; }
    @Override void feed(int b){
      push(b);
      if (n == 2){ first(); return; }
      if (n < 4){ return; }
      int w2= unit(2);
      if (w2 < 0xDC00 || w2 > 0xDFFF){ bad(notScalar, 2, unit(0)); first(); return; }
      scalar(0x10000 + (unit(0) - 0xD800 << 10) + (w2 - 0xDC00), 4);
    }
    void first(){
      int w1= unit(0);
      if (w1 >= 0xDC00 && w1 <= 0xDFFF){ bad(notScalar, 2, w1); return; }
      if (w1 < 0xD800 || w1 > 0xDBFF){ scalar(w1, 2); }
    }
    @Override void end(){
      if (n == 0){ return; }
      bad(truncatedEncoding, n, n == 1 ? p[0] : unit(0));
    }
  }

  static final class Utf32 extends Decoder{
    final boolean le;
    Utf32(boolean le){ this.le= le; }
    long value(){
      long res= 0;
      for (int j= 0; j < n; j += 1){ res = le ? res | (long) p[j] << 8 * j : res << 8 | p[j]; }
      return res;
    }
    @Override void feed(int b){
      push(b);
      if (n < 4){ return; }
      long cp= value();
      if (cp <= 0x10FFFF && UStr$s$0Instance.isScalar((int) cp)){ scalar((int) cp, 4); return; }
      bad(notScalar, 4, cp);
    }
    @Override void end(){
      if (n == 0){ return; }
      bad(truncatedEncoding, n, value());
    }
  }

  static final class Ascii extends Decoder{
    @Override void feed(int b){
      push(b);
      if (b < 0x80){ scalar(b, 1); return; }
      bad(malformedEncoding, 1, b);
    }
    @Override void end(){}
  }

  static final class Latin1 extends Decoder{
    @Override void feed(int b){ push(b); scalar(b, 1); }
    @Override void end(){}
  }

  static final class Encoding implements UStrCleaner$2tc$0, StrCleaner$18g$0, Sink{
    final boolean u;
    final Decoder d;
    final Object fixer;
    final String strict;
    final StringBuilder out= new StringBuilder();
    Encoding(boolean u, Decoder d, Object fixerMaker, String strict, Object start){
      this.u= u;
      this.d= d;
      this.strict= strict;
      fixer = fixerMaker == null ? null : ((F$3$1) fixerMaker).read$$hash$0();
      d.out = this;
      add(out, start);
    }
    @Override public Object mut$nextByte$1(Object b){
      d.feed(u8(b));
      return d.partial() ? empty(u) : text(out, u);
    }
    @Override public Object mut$end$0(){
      d.end();
      return text(out, u);
    }
    @Override public void scalar(int cp, long at, int len){
      if (u || UStr$s$0Instance.isStrScalar(cp)){ out.appendCodePoint(cp); return; }
      bad(notInStr, at, len, cp);
    }
    @Override public void bad(int reason, long at, int len, long rejected){
      if (fixer == null){ throw Util.detErr(strict + ": " + reasonNames[reason] + ": " + badUnit(reason, at, len, rejected)); }
      var args= new Object[]{reasons[reason], nat(at), nat(len), nat(rejected)};
      add(out, u ? ((UStrFixer$pc$0) fixer).mut$$hash$4(args[0], args[1], args[2], args[3]) : ((StrFixer$b4$0) fixer).mut$$hash$4(args[0], args[1], args[2], args[3]));
    }
    String badUnit(int reason, long at, int len, long rejected){
      var bytes= new StringBuilder();
      for (int i= 0; i < len; i += 1){ bytes.append(i == 0 ? "" : " ").append(String.format(Locale.ROOT, "%02X", d.p[i])); }
      var where= "the bytes " + bytes + " at byte offset " + at + " of the line";
      var value= String.format(Locale.ROOT, "%04X", rejected);
      return switch (reason){
        case malformedEncoding -> where + " are not UTF-8";
        case truncatedEncoding -> "the line ends in the middle of the UTF-8 sequence " + bytes + " at byte offset " + at;
        case notScalar -> where + " encode " + value + ", which is not a unicode scalar value";
        default -> where + " encode " + value + ", which is not a character of Str; the characters of Str have the scalar values 10 and 32 to 126";
        };
    }
  }

  static final class Clean implements UStrCleaner$2tc$0, StrCleaner$18g$0, Sink{
    final boolean u;
    final boolean oneLine;
    final Decoder d= new Utf8();
    final StringBuilder line= new StringBuilder();
    final StringBuilder ready= new StringBuilder();
    boolean cr;
    Clean(boolean u, boolean oneLine, Object start){
      this.u= u;
      this.oneLine= oneLine;
      d.out = this;
      for (int c : scalars(start)){ put(c); }
    }
    @Override public Object mut$nextByte$1(Object b){
      d.feed(u8(b));
      if (ready.isEmpty() || d.partial() || cr){ return empty(u); }
      ready.append(norm(line));
      line.setLength(0);
      return text(ready, u);
    }
    @Override public Object mut$end$0(){
      d.end();
      if (cr){ cr = false; newline(); }
      ready.append(norm(line));
      line.setLength(0);
      return text(ready, u);
    }
    @Override public void scalar(int cp, long at, int len){ put(cp); }
    @Override public void bad(int reason, long at, int len, long rejected){ put(u ? 0xFFFD : '?'); }
    void put(int cp){
      if (cr){
        cr = false;
        newline();
        if (cp == '\n'){ return; }
      }
      if (cp == '\r'){ cr = true; return; }
      boolean breaks= cp == '\n' || cp == 0x0B || cp == 0x0C || cp == 0x85 || cp == 0x2028 || cp == 0x2029;
      if (breaks){ newline(); return; }
      if (cp != 0xFEFF){ line.appendCodePoint(cp); }
    }
    void newline(){
      if (oneLine){ line.append(' '); return; }
      ready.append(norm(line)).append('\n');
      line.setLength(0);
    }
    String norm(CharSequence s){
      if (u){ return Normalizer.normalize(s, Normalizer.Form.NFC); }
      var res= new StringBuilder();
      Normalizer.normalize(s.toString().replace('\t', ' '), Normalizer.Form.NFKD).codePoints().filter(UStr$s$0Instance::isStrScalar).forEach(res::appendCodePoint);
      return res.toString();
    }
  }

  static final class Then implements UStrCleaner$2tc$0, StrCleaner$18g$0, Sink{
    final boolean u;
    final Decoder d= new Utf8();
    final Object scalars;
    final StringBuilder out= new StringBuilder();
    Then(boolean u, Object maker, Object start){
      this.u= u;
      scalars = ((F$3$1) maker).read$$hash$0();
      d.out = this;
      for (int c : TextCleaning.scalars(start)){ put(c); }
    }
    @Override public Object mut$nextByte$1(Object b){
      d.feed(u8(b));
      return d.partial() ? empty(u) : text(out, u);
    }
    @Override public Object mut$end$0(){
      d.end();
      add(out, u ? ((UStrScalarCleaner$5074$0) scalars).mut$end$0() : ((StrScalarCleaner$2728$0) scalars).mut$end$0());
      return text(out, u);
    }
    @Override public void scalar(int cp, long at, int len){ put(cp); }
    @Override public void bad(int reason, long at, int len, long rejected){ put(0xFFFD); }
    void put(int cp){
      var n= nat(cp);
      add(out, u ? ((UStrScalarCleaner$5074$0) scalars).mut$nextScalar$1(n) : ((StrScalarCleaner$2728$0) scalars).mut$nextScalar$1(n));
    }
  }

  static Object encoding(boolean u, Supplier<Decoder> decoder, Object fixer){ return maker(s -> new Encoding(u, decoder.get(), fixer, null, s)); }
  static Object strictUtf8(boolean u){ return maker(s -> new Encoding(u, new Utf8(), null, u ? "UStrCleaners.utf8" : "StrCleaners.utf8", s)); }
  static Object clean(boolean u, boolean oneLine){ return maker(s -> new Clean(u, oneLine, s)); }
  static Object then(boolean u, Object scalarMaker){ return maker(s -> new Then(u, scalarMaker, s)); }
}
