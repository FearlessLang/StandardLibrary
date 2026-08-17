package base;

public interface _FloatsBetween extends base.Sealed$2o$0 {
  _FloatsBetween instance = new _FloatsBetween() {};

  // Other than at -0.0 and 0.0:
  // Double.doubleToRawLongBits(d) + 1  == Double.doubleToRawLongBits(Math.nextUp(d))
  // and  Double.doubleToRawLongBits(d) - 1  == Double.doubleToRawLongBits(Math.nextDown(d))
  // This breaks at 0, where Double.doubleToRawLongBits(0.0) = 0
  // and Double.doubleToRawLongBits(-0.0) = Long.MIN_VALUE
  // Thus we need to handle this case specifically

  // Method from:
  // https://stackoverflow.com/questions/39258119/how-could-i-determine-the-number-of-unique-floating-point-numbers-in-between-two
  default Object imm$$hash$2(Object p0, Object p1) {
    double start = Float$1c$0Instance.unwrap(p0);
    double end = Float$1c$0Instance.unwrap(p1);
    assert !Double.isNaN(start) && !Double.isNaN(end);
    assert start <= end;
    boolean startNeg = Double.doubleToRawLongBits(start) < 0; // true for negatives AND -0.0
    boolean endNeg = Double.doubleToRawLongBits(end) < 0;
    if (startNeg && !endNeg) {
      return Nat$c$0Instance.instance(
        doublesBetweenSameSign(start, -0.0)
          + doublesBetweenSameSign(Math.nextUp(0.0), end) // We don't want to include both -0.0 and 0.0
      );
    }
    return Nat$c$0Instance.instance(doublesBetweenSameSign(start, end));
  }

  /**
   * Returns the number of doubles (as an unsigned long) between two doubles of the same sign, inclusive of both endpoints.
   */
  static long doublesBetweenSameSign(double start, double end) {
    assert start <= end;
    long startBits = Double.doubleToRawLongBits(start);
    long endBits = Double.doubleToRawLongBits(end);
    long d = startBits - endBits;
    if (d < 0) {d = -d;}
    return d + 1; // inclusive of both endpoints
  }
}
