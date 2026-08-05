package base;

import java.util.Optional;

import static base.Util.detErr;

public interface _MagicMath$0 extends base.Sealed$0 {
    static double doubleFromFloat(Object p0) {
        return ((Float$0Instance)p0).val();
    }
    static Optional<String> logContract(double x) {
        if (x < 0.0) {
            return Optional.of("x cannot be negative");
        }
        if (Double.isNaN(x)) {
            return Optional.of("x cannot be NaN");
        }
        return Optional.empty();
    }

    default Object imm$ln$1(Object p0){
        double x = doubleFromFloat(p0);
        logContract(x).ifPresent(msg -> {throw detErr(msg);});
        return Float$0Instance.instance(Math.log(x));
    }

    default Object imm$log$1(Object p0){
        double x = doubleFromFloat(p0);
        logContract(x).ifPresent(msg -> {throw detErr(msg);});
        return Float$0Instance.instance(Math.log10(x));
    }

    default Object imm$logb$2(Object p0, Object p1){
        double base = doubleFromFloat(p0);
        if (base == 0.0d) {
            throw detErr("base of log cannot be 0");
        }
        return ((Float$0Instance) this.imm$ln$1(p1))
                .imm$$slash$1(this.imm$ln$1(p0));
    }

    default Object imm$log2$1(Object p0){
        var this$= this;
        var x$= (base.Float$0)p0;
        return this$.imm$logb$2(
                Float$0Instance.instance(2.0d),
                x$);
    }

    static Optional<String> inverseTrigContract(double x) {
        if (Math.abs(x) > 1) {
            return Optional.of("Expected a number in the range [-1, 1], got: "+x);
        }
        if (Double.isNaN(x)) {
            return Optional.of("x cannot be NaN");
        }
        return Optional.empty();
    }

    default Object imm$asin$1(Object p0){
        double x = ((Float$0Instance)p0).val();
        inverseTrigContract(x).ifPresent(msg -> {throw detErr(msg);});
        var theta = Float$0Instance.instance(Math.asin(x));
        return new Radian$0() {
            public Object read$$hash$0() {
                return theta;
            }
        };
    }

    default Object imm$acos$1(Object p0){
        double x = doubleFromFloat(p0);
        inverseTrigContract(x).ifPresent(msg -> {throw detErr(msg);});
        var theta = Float$0Instance.instance(Math.acos(x));
        return new Radian$0() {
            public Object read$$hash$0() {
                return theta;
            }
        };
    }

    default Object imm$atan$1(Object p0){
        double x = doubleFromFloat(p0);
        if (Double.isNaN(x)) {
            throw detErr("x cannot be NaN");
        }
        var theta = Float$0Instance.instance(Math.atan(x));
        return new Radian$0() {
            public Object read$$hash$0() {
                return theta;
            }
        };
    }

    default Object imm$atan2$2(Object p0, Object p1){
        double x = doubleFromFloat(p0);
        double y = doubleFromFloat(p1);
        if (Double.isNaN(x)) {
            throw detErr("x cannot be NaN");
        }
        if (Double.isNaN(y)) {
            throw detErr("y cannot be NaN");
        }
        var theta = Float$0Instance.instance(Math.atan2(x, y));
        return new Radian$0() {
            public Object read$$hash$0() {
                return theta;
            }
        };
    }

    default Object imm$hypot$2(Object p0, Object p1){
        double x = doubleFromFloat(p0);
        double y = doubleFromFloat(p1);
        if (Double.isNaN(x)) {
            throw detErr("x cannot be NaN");
        }
        if (Double.isNaN(y)) {
            throw detErr("y cannot be NaN");
        }
        return Float$0Instance.instance(Math.hypot(x, y));
    }
}