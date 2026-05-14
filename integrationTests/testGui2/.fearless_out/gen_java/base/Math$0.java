package base;

import java.util.Optional;

import static base.Util.*;


public interface Math$0 extends base.Sealed$0 {
    default Object imm$minInt$0() {
        var this$ = this;
        return base.Int$0Instance.instance(-9223372036854775808L);
    }

    default Object imm$maxInt$0() {
        var this$ = this;
        return base.Int$0Instance.instance(9223372036854775807L);
    }

    default Object imm$intBits$0() {
        var this$ = this;
        return base.Nat$0Instance.instance(64L);
    }

    default Object imm$minNat$0() {
        var this$ = this;
        return base.Nat$0Instance.instance(0L);
    }

    default Object imm$maxNat$0() {
        var this$ = this;
        return base.Nat$0Instance.instance(-1L);
    }

    default Object imm$natBits$0() {
        var this$ = this;
        return base.Nat$0Instance.instance(64L);
    }

    default Object imm$minByte$0() {
        var this$ = this;
        return base.Nat$0Instance.instance(0L).imm$byte$0();
    }

    default Object imm$maxByte$0() {
        var this$ = this;
        return base.Nat$0Instance.instance(255L).imm$byte$0();
    }

    default Object imm$byteBits$0() {
        var this$ = this;
        return base.Nat$0Instance.instance(8L);
    }

    default Object imm$inf$0() {
        var this$ = this;
        return base.Float$0Instance.instance(1.0d).imm$$slash$1(base.Float$0Instance.instance(0.0d));
    }

    default Object imm$negInf$0() {
        var this$ = this;
        return base.Float$0Instance.instance(-1.0d).imm$$slash$1(base.Float$0Instance.instance(0.0d));
    }

    default Object imm$nan$0() {
        var this$ = this;
        return base.Float$0Instance.instance(0.0d).imm$$slash$1(base.Float$0Instance.instance(0.0d));
    }

    default Object imm$floatBits$0() {
        var this$ = this;
        return base.Nat$0Instance.instance(64L);
    }

    default Object imm$pi$0() {
        var this$ = this;
        return base.Float$0Instance.instance(3.141592653589793d);
    }

    default Object imm$tau$0() {
        var this$ = this;
        return base.Float$0Instance.instance(2.0d).imm$$star$1(this$.imm$pi$0());
    }

    default Object imm$e$0() {
        var this$ = this;
        return base.Float$0Instance.instance(2.718281828459045d);
    }

    default Object imm$ln$1(Object p0) {
        var this$ = this;
        var x$ = (base.Float$0) p0;
        return base.Magic$0.instance.imm$$bang$0();
    }

    default Object imm$log$1(Object p0) {
        var this$ = this;
        var x$ = (base.Float$0) p0;
        return base.Magic$0.instance.imm$$bang$0();
    }

    default Object imm$logb$2(Object p0, Object p1) {
        var this$ = this;
        var base$ = (base.Float$0) p0;
        var x$ = (base.Float$0) p1;
        return ((base.Bool$0) base$.read$$eq_eq$1(base.Float$0Instance.instance(0.0d))).imm$if$1(new base.ThenElse$1() {
            public Object mut$then$0() {
                return base.Error$0.instance.imm$msg$1(base.Str$0Instance.instance("base of log cannot be 0"));
            }

            public Object mut$else$0() {
                return ((base.Float$0) this$.imm$ln$1(x$)).imm$$slash$1(this$.imm$ln$1(base$));
            }

        });
    }

    default Object imm$log2$1(Object p0) {
        var this$ = this;
        var x$ = (base.Float$0) p0;
        return this$.imm$logb$2(base.Float$0Instance.instance(2.0d), x$);
    }

    default Object imm$asin$1(Object p0) {
        var this$ = this;
        var x$ = (base.Float$0) p0;
        return base.Magic$0.instance.imm$$bang$0();
    }

    default Object imm$acos$1(Object p0) {
        var this$ = this;
        var x$ = (base.Float$0) p0;
        return base.Magic$0.instance.imm$$bang$0();
    }

    default Object imm$atan$1(Object p0) {
        var this$ = this;
        var x$ = (base.Float$0) p0;
        return base.Magic$0.instance.imm$$bang$0();
    }

    default Object imm$atan2$2(Object p0, Object p1) {
        var this$ = this;
        var x$ = (base.Float$0) p0;
        var y$ = (base.Float$0) p1;
        return base.Magic$0.instance.imm$$bang$0();
    }

    default Object imm$euclideanMod$2(Object p0, Object p1) {
        var this$ = this;
        var base$ = (base.Float$0) p0;
        var divisor$ = (base.Float$0) p1;
        return ((base.Block$1) base.Block$0.instance.imm$$hash$0()).mut$let$2(new base.MF$1() {
            public Object mut$$hash$0() {
                return base$.imm$ieeeRemainder$1(divisor$);
            }

        }, new base.Continuation$2() {
            public Object mut$$hash$2(Object p0, Object p1) {
                var mod$ = (base.Float$0) p0;
                var _aeqS$ = (base.Block$1) p1;
                return _aeqS$.mut$return$1(new base.MF$1() {
                    public Object mut$$hash$0() {
                        return ((base.Bool$0) mod$.read$$lt$1(base.Float$0Instance.instance(0.0d)))
                                .imm$if$1(new base.ThenElse$1() {
                                    public Object mut$then$0() {
                                        return mod$.imm$$plus$1(divisor$);
                                    }

                                    public Object mut$else$0() {
                                        return mod$;
                                    }

                                });
                    }

                });
            }

        });
    }

    default Object imm$hypot$2(Object p0, Object p1) {
        var this$ = this;
        var x$ = (base.Float$0) p0;
        var y$ = (base.Float$0) p1;
        return base.Magic$0.instance.imm$$bang$0();
    }

    default Object imm$max$3(Object p0, Object p1, Object p2) {
        var this$ = this;
        var t0$ = p0;
        var t1$ = p1;
        var ordering$ = (base.OrderBy$2) p2;
        return ((base.Order$1) ordering$.imm$$hash$1(t0$)).read$$lt_eq_gt$2(ordering$.imm$$hash$1(t1$),
                new base.OrderMatch$1() {
                    public Object mut$lt$0() {
                        return t1$;
                    }

                    public Object mut$eq$0() {
                        return t0$;
                    }

                    public Object mut$gt$0() {
                        return t0$;
                    }

                });
    }

    default Object imm$min$3(Object p0, Object p1, Object p2) {
        var this$ = this;
        var t0$ = p0;
        var t1$ = p1;
        var ordering$ = (base.OrderBy$2) p2;
        return ((base.Order$1) ordering$.imm$$hash$1(t0$)).read$$lt_eq_gt$2(ordering$.imm$$hash$1(t1$),
                new base.OrderMatch$1() {
                    public Object mut$lt$0() {
                        return t0$;
                    }

                    public Object mut$eq$0() {
                        return t0$;
                    }

                    public Object mut$gt$0() {
                        return t1$;
                    }

                });
    }

    default Object imm$match$2(Object p0, Object p1) {
        var this$ = this;
        var f$ = (base.Float$0) p0;
        var m$ = (base.FloatMatch$1) p1;
        return ((base.Opt$1) ((base.Opt$1) ((base.Opt$1) ((base.Opt$1) ((base.Opt$1) base.Opt$1.instance
                .mut$$or$1(base._MapTrue$1.instance.mut$$hash$2(f$.imm$isPosZero$0(), new base.MF$1() {
                    public Object mut$$hash$0() {
                        return m$.mut$posZero$0();
                    }

                }))).mut$$or$1(base._MapTrue$1.instance.mut$$hash$2(f$.imm$isNegZero$0(), new base.MF$1() {
            public Object mut$$hash$0() {
                return m$.mut$negZero$0();
            }

        }))).mut$$or$1(base._MapTrue$1.instance.mut$$hash$2(f$.imm$isPosInfinity$0(), new base.MF$1() {
            public Object mut$$hash$0() {
                return m$.mut$posInf$0();
            }

        }))).mut$$or$1(base._MapTrue$1.instance.mut$$hash$2(f$.imm$isNegInfinity$0(), new base.MF$1() {
            public Object mut$$hash$0() {
                return m$.mut$negInf$0();
            }

        }))).mut$$or$1(base._MapTrue$1.instance.mut$$hash$2(f$.imm$isNaN$0(), new base.MF$1() {
            public Object mut$$hash$0() {
                return m$.mut$nan$0();
            }

        }))).mut$orLazy$1(new base.MF$1() {
            public Object mut$$hash$0() {
                return m$.mut$regular$1(f$);
            }

        });
    }

    Math$0 instance = new Math$0Instance();
}

class Math$0Instance implements Math$0 {
    private static double doubleFromFloat(Object p0) {
        return ((base.Float$0Instance)p0).val();
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

    @Override
    public Object imm$ln$1(Object p0){
        double x = doubleFromFloat(p0);
        logContract(x).ifPresent(msg -> {throw detErr(msg);});
        return base.Float$0Instance.instance(Math.log(x));
    }

    @Override
    public Object imm$log$1(Object p0){
        double x = doubleFromFloat(p0);
        logContract(x).ifPresent(msg -> {throw detErr(msg);});
        return base.Float$0Instance.instance(Math.log10(x));
    }

    @Override
    public Object imm$logb$2(Object p0, Object p1){
        double base = doubleFromFloat(p0);
        if (base == 0.0d) {
            throw detErr("base of log cannot be 0");
        }
        return ((Float$0Instance) this.imm$ln$1(p1))
                .imm$$slash$1(this.imm$ln$1(p0));
    }

    @Override
    public Object imm$log2$1(Object p0){
        var this$= this;
        var x$= (base.Float$0)p0;
        return this$.imm$logb$2(
                base.Float$0Instance.instance(2.0d),
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

    @Override
    public Object imm$asin$1(Object p0){
        double x = ((base.Float$0Instance)p0).val();
        inverseTrigContract(x).ifPresent(msg -> {throw detErr(msg);});
        var theta = Float$0Instance.instance(Math.asin(x));
        return new base.Radian$0() {
            public Object read$$hash$0() {
                return theta;
            }
        };
    }
    @Override
    public Object imm$acos$1(Object p0){
        double x = doubleFromFloat(p0);
        inverseTrigContract(x).ifPresent(msg -> {throw detErr(msg);});
        var theta = Float$0Instance.instance(Math.acos(x));
        return new base.Radian$0() {
            public Object read$$hash$0() {
                return theta;
            }
        };
    }
    @Override
    public Object imm$atan$1(Object p0){
        double x = doubleFromFloat(p0);
        if (Double.isNaN(x)) {
            throw detErr("x cannot be NaN");
        }
        var theta = Float$0Instance.instance(Math.atan(x));
        return new base.Radian$0() {
            public Object read$$hash$0() {
                return theta;
            }
        };
    }

    @Override
    public Object imm$atan2$2(Object p0, Object p1){
        double x = doubleFromFloat(p0);
        double y = doubleFromFloat(p1);
        if (Double.isNaN(x)) {
            throw detErr("x cannot be NaN");
        }
        if (Double.isNaN(y)) {
            throw detErr("y cannot be NaN");
        }
        var theta = Float$0Instance.instance(Math.atan2(x, y));
        return new base.Radian$0() {
            public Object read$$hash$0() {
                return theta;
            }
        };
    }

    @Override
    public Object imm$hypot$2(Object p0, Object p1){
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