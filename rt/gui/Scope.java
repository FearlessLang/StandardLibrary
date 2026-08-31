package base;

class Scopes{
  static final Painter$5c$0 idP = new Painter$5c$0(){ @Override public Object imm$run$1(Object p0){ return p0; } };

  static Nat$c$0 n(long n){ return new Nat$c$0Instance(n); }
  static Instant$5c$0 time(long n){ return (Instant$5c$0) Instant$5c$0.instance.read$$hash$1(new Nat$c$0Instance(n)); }
  static Instant$5c$0 timeNanos(long n){ return time(n / 1000); }
  static WidthNat$as$0 w(long n){ return (WidthNat$as$0) WidthNat$as$0.instance.read$$hash$1(new Nat$c$0Instance(n)); }
  static HeightNat$lg$0 h(long n){ return (HeightNat$lg$0) HeightNat$lg$0.instance.read$$hash$1(new Nat$c$0Instance(n)); }
  static XInt$s$0 x(long n){ return (XInt$s$0) XInt$s$0.instance.read$$hash$1(new Int$c$0Instance(n)); }
  static YInt$s$0 y(long n){ return (YInt$s$0) YInt$s$0.instance.read$$hash$1(new Int$c$0Instance(n)); }
  static int nat(Object n){ return Util.natToInt(n); }
  static int byt(Object b){ return Byte.toUnsignedInt(((Byte$o$0Instance) b).val()); }
  static int n(Nat$c$0 n){ return nat(n); }
  static int w(WidthNat$as$0 w){ return nat(w.read$get$0()); }
  static int h(HeightNat$lg$0 h){ return nat(h.read$get$0()); }
  static int x(XInt$s$0 x){ return Math.toIntExact(Util.intToLong(x.read$get$0())); }
  static int y(YInt$s$0 y){ return Math.toIntExact(Util.intToLong(y.read$get$0())); }

  static int red(Object r){ return byt(((Red$c$0) r).read$get$0()); }
  static int green(Object g){ return byt(((Green$1c$0) g).read$get$0()); }
  static int blue(Object b){ return byt(((Blue$o$0) b).read$get$0()); }
  static int alpha(Object a){ return byt(((Alpha$1c$0) a).read$get$0()); }
}