package base;


public interface _FloatsBetween  extends base.Sealed$0 {
    _FloatsBetween instance = new _FloatsBetween() {};

    // In most cases:
    // Double.doubleToRawLongBits(d) + 1  == Double.doubleToRawLongBits(Math.nextUp(d))
    // and  Double.doubleToRawLongBits(d) - 1  == Double.doubleToRawLongBits(Math.nextDown(d))
    // This breaks at 0, where Double.doubleToRawLongBits(0.0) = 0
    // and Double.doubleToRawLongBits(-0.0) = Long.MIN_VALUE

    // Method from:
    // https://stackoverflow.com/questions/39258119/how-could-i-determine-the-number-of-unique-floating-point-numbers-in-between-two
    default Object imm$$hash$2(Object p0, Object p1) {
        double start = Float$0Instance.unwrap(p0);
        double end = Float$0Instance.unwrap(p1);
        assert !Double.isNaN(start) && !Double.isNaN(end);
        assert start <= end;
        // I love -0.0 and 0.0...
        boolean startNeg = Double.doubleToRawLongBits(start) < 0; // true for negatives AND -0.0
        boolean endNeg   = Double.doubleToRawLongBits(end) < 0;

        if (startNeg && !endNeg) {
            return Nat$0Instance.instance(
                    doublesBetweenSameSign(start, -0.0)
                    + doublesBetweenSameSign(Math.nextUp(0.0), end)
            );
        }
        return Nat$0Instance.instance(doublesBetweenSameSign(start, end));
    }

    static long doublesBetweenSameSign(double start, double end) {
        assert start <= end;
        long startBits = Double.doubleToRawLongBits(start);
        long endBits = Double.doubleToRawLongBits(end);
        long d = startBits - endBits;
        if (d < 0) d = -d;
        return d + 1; // inclusive of both endpoints
    }


}
