package base;

class Scopes{
  static final Painter$0 idP = new Painter$0(){ @Override public Object imm$run$1(Object p0){ return p0; } };

  static Nat$0 n(long n){ return new Nat$0Instance(n); }
  static Time$0 time(long n){ return (Time$0) Time$0.instance.read$$hash$1(new Nat$0Instance(n)); }
  static Time$0 timeNanos(long n){ return time(n / 1000); }
  static WidthNat$0 w(long n){ return (WidthNat$0) WidthNat$0.instance.read$$hash$1(new Nat$0Instance(n)); }
  static HeightNat$0 h(long n){ return (HeightNat$0) HeightNat$0.instance.read$$hash$1(new Nat$0Instance(n)); }
  static XNat$0 x(long n){ return (XNat$0) XNat$0.instance.read$$hash$1(new Nat$0Instance(n)); }
  static YNat$0 y(long n){ return (YNat$0) YNat$0.instance.read$$hash$1(new Nat$0Instance(n)); }
  static int nat(Object n){ return Math.toIntExact(Util.natToLong(n)); }
  static int byt(Object b){ return Byte.toUnsignedInt(((Byte$0Instance) b).val()); }
  static int n(Nat$0 n){ return nat(n); }
  static int w(WidthNat$0 w){ return nat(w.read$get$0()); }
  static int h(HeightNat$0 h){ return nat(h.read$get$0()); }
  static int x(XNat$0 x){ return nat(x.read$get$0()); }
  static int y(YNat$0 y){ return nat(y.read$get$0()); }

  static int red(Object r){ return byt(((Red$0) r).read$get$0()); }
  static int green(Object g){ return byt(((Green$0) g).read$get$0()); }
  static int blue(Object b){ return byt(((Blue$0) b).read$get$0()); }
  static int alpha(Object a){ return byt(((Alpha$0) a).read$get$0()); }
}