package _base;

final class Scopes{
  static final Painter$5c$0 idP= new Painter$5c$0(){ @Override public Object imm$run$1(Object p0){ return p0; } };

  static Nat$c$0 n(long n){ return new Nat$c$0Instance(n); }
  static Instant$5c$0 time(long n){ return (Instant$5c$0) Instant$5c$0.instance.read$$hash$1(new Nat$c$0Instance(n)); }
  static Instant$5c$0 timeNanos(long n){ return time(n / 1000); }
  static WidthNat$as$0 w(long n){ return (WidthNat$as$0) WidthNat$as$0.instance.read$$hash$1(new Nat$c$0Instance(n)); }
  static HeightNat$lg$0 h(long n){ return (HeightNat$lg$0) HeightNat$lg$0.instance.read$$hash$1(new Nat$c$0Instance(n)); }
  static XInt$s$0 x(long n){ return (XInt$s$0) XInt$s$0.instance.read$$hash$1(new Int$c$0Instance(n)); }
  static YInt$s$0 y(long n){ return (YInt$s$0) YInt$s$0.instance.read$$hash$1(new Int$c$0Instance(n)); }
  static TextRatio$ls$0 ratio(double d){ return (TextRatio$ls$0) TextRatio$ls$0.instance.read$$hash$1(new Float$1c$0Instance(d)); }
  static double ratio(Object r){ return ((Float$1c$0Instance) ((TextRatio$ls$0) r).read$get$0()).val(); }
  static int byt(Object b){ return Byte.toUnsignedInt(((Byte$o$0Instance) b).val()); }
  static final int maxExtent= 32767;
  static final long maxImagePixels= 1L << 28;
  static int extent(WidthNat$as$0 w, String what){ return extent(w.read$get$0(), what); }
  static int extent(HeightNat$lg$0 h, String what){ return extent(h.read$get$0(), what); }
  static int extent(Object n, String what){ return extent(Util.natToLong(n), what); }
  static int extent(long v, String what){
    if (Long.compareUnsigned(v, maxExtent) > 0){ throw Util.detErr(what + " " + Long.toUnsignedString(v) + " is too large; it must be <= " + maxExtent); }
    return (int) v;
  }
  static int coord(Object i, String what){
    long v= Util.intToLong(i);
    if (v < -32768 || v > maxExtent){ throw Util.detErr(what + " " + v + " is out of range; it must be between -32768 and " + maxExtent); }
    return (int) v;
  }
  static long fpsOf(Object n, String what){
    long v= Util.natToLong(n);
    if (v < 1 || v > 500){ throw Util.detErr(what + " " + Long.toUnsignedString(v) + " is out of range; it must be between 1 and 500"); }
    return v;
  }

  static int red(Object r){ return byt(((Red$c$0) r).read$get$0()); }
  static int green(Object g){ return byt(((Green$1c$0) g).read$get$0()); }
  static int blue(Object b){ return byt(((Blue$o$0) b).read$get$0()); }
  static int alpha(Object a){ return byt(((Alpha$1c$0) a).read$get$0()); }
  static String ustr(Object u){
    var cps= ((UStr$s$0Instance) u).val();
    return new String(cps, 0, cps.length);
  }
}