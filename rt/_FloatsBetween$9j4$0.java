package _base;

public interface _FloatsBetween$9j4$0 extends _base.Sealed$2o$0{
  _FloatsBetween$9j4$0 instance= new _FloatsBetween$9j4$0(){};

  default Object imm$$hash$2(Object p0, Object p1){
    double start= Float$1c$0Instance.unwrap(p0);
    double end= Float$1c$0Instance.unwrap(p1);
    assert start <= end;
    return Nat$c$0Instance.instance(ord(end) - ord(start) + 1);
  }
  static long ord(double d){
    long bits= Double.doubleToRawLongBits(d);
    return bits < 0 ? -(bits & Long.MAX_VALUE) : bits;
  }
}
