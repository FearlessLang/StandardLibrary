package base;
/// Desired behaviour is a little strange:
/// NaN is > everything else so inf.succ -> NaN?
/// NaN.succ -> NaN
/// NaN.pred -> inf
/// -inf.pred -> -inf
public interface _FloatNext$0 extends base.Sealed$0 {
    _FloatNext$0 instance = new _FloatNext$0() {};
    default Object imm$succ$1(Object p0) {
        double f = Float$0Instance.unwrap(p0);
        //NaN > +inf
        if (f == Double.POSITIVE_INFINITY) {
            return Float$0Instance.instance(Double.NaN);
        }
        return Float$0Instance.instance(Math.nextUp(f));
    }

    default Object imm$pred$1(Object p0) {
        double f = Float$0Instance.unwrap(p0);
        if (Double.isNaN(f)) {
            return Float$0Instance.instance(Double.POSITIVE_INFINITY);
        }
        return Float$0Instance.instance(
                Math.nextDown(Float$0Instance.unwrap(p0))
        );
    }
}
