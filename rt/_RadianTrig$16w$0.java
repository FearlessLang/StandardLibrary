package base;

public interface _RadianTrig$16w$0 extends base.Sealed$2o$0 {
    static double rad(Object radian) {
        Float$1c$0Instance f = (Float$1c$0Instance) ((Radian$2o$0) radian).read$$hash$0();
        return f.val();
    }
    default Object imm$sin$1(Object p0) {
        return Float$1c$0Instance.instance(Math.sin(rad(p0)));
    }

    default Object imm$cos$1(Object p0) {
        return Float$1c$0Instance.instance(Math.cos(rad(p0)));
    }

    default Object imm$tan$1(Object p0) {
        return Float$1c$0Instance.instance(Math.tan(rad(p0)));
    }

    _RadianTrig$16w$0 instance = new _RadianTrig$16w$0() {
    };
}