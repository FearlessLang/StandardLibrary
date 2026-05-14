package base;

public interface _RadianTrig$0 extends base.Sealed$0 {
    static double rad(Object radian) {
        Float$0Instance f = (Float$0Instance) ((Radian$0) radian).read$$hash$0();
        return f.val();
    }
    default Object imm$sin$1(Object p0) {
        return Float$0Instance.instance(Math.sin(rad(p0)));
    }

    default Object imm$cos$1(Object p0) {
        return Float$0Instance.instance(Math.cos(rad(p0)));
    }

    default Object imm$tan$1(Object p0) {
        return Float$0Instance.instance(Math.tan(rad(p0)));
    }

    _RadianTrig$0 instance = new _RadianTrig$0() {
    };
}