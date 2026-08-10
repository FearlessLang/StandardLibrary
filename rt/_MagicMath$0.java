package base;

import static base.Util.err;

public interface _MagicMath$0 extends base.Sealed$0 {
  static void logContract(String methodName, double x) {
    if (x < 0.0) {
      throw err("Math."+methodName+": Cannot take the "+methodName+" of a negative number, given " + x);
    }
    if (Double.isNaN(x)) {
      throw err(methodName+": Input was NaN, cannot take the log of NaN");
    }
  }
  static void inverseTrigContract(String methodName, double x) {
    if (Math.abs(x) > 1) {
      throw err("Math."+methodName+": Expected a number in the range [-1, 1], got: " + x);
    }
    if (Double.isNaN(x)) {
      throw err("Math."+methodName+": Input was NaN, cannot take the "+methodName+" of NaN");
    }
  }

  default Object imm$ln$1(Object p0) {
    double x = Float$0Instance.unwrap(p0);
    logContract("ln:", x);
    return Float$0Instance.instance(Math.log(x));
  }
  default Object imm$log$1(Object p0) {
    double x = Float$0Instance.unwrap(p0);
    logContract("log:", x);
    return Float$0Instance.instance(Math.log10(x));
  }
  default Object imm$asin$1(Object p0) {
    double x = ((Float$0Instance) p0).val();
    inverseTrigContract("asin", x);
    var theta = Float$0Instance.instance(Math.asin(x));
    return new Radian$0() {
      public Object read$$hash$0() {
        return theta;
      }
    };
  }
  default Object imm$acos$1(Object p0) {
    double x = Float$0Instance.unwrap(p0);
    inverseTrigContract("acos", x);
    var theta = Float$0Instance.instance(Math.acos(x));
    return new Radian$0() {
      public Object read$$hash$0() {
        return theta;
      }
    };
  }
  default Object imm$atan$1(Object p0) {
    double x = Float$0Instance.unwrap(p0);
    if (Double.isNaN(x)) {
      throw err("Math.atan: Input was NaN, cannot take the atan of NaN");
    }
    var theta = Float$0Instance.instance(Math.atan(x));
    return new Radian$0() {
      public Object read$$hash$0() {
        return theta;
      }
    };
  }

  default Object imm$atan2$2(Object p0, Object p1) {
    double x = Float$0Instance.unwrap(p0);
    double y = Float$0Instance.unwrap(p1);
    if (Double.isNaN(x)) {
      throw err("Math.atan2: First argument was NaN, cannot take atan2 of NaN");
    }
    if (Double.isNaN(y)) {
      throw err("Math.atan2: Second argument was NaN, cannot take atan2 of NaN");
    }
    var theta = Float$0Instance.instance(Math.atan2(x, y));
    return new Radian$0() {
      public Object read$$hash$0() {
        return theta;
      }
    };
  }

  default Object imm$hypot$2(Object p0, Object p1) {
    double x = Float$0Instance.unwrap(p0);
    double y = Float$0Instance.unwrap(p1);
    if (Double.isNaN(x)) {
      throw err("Math.hypot: First argument was NaN, cannot take hypot of NaN");
    }
    if (Double.isNaN(y)) {
      throw err("Math.hypot: Second argument was NaN, cannot take hypot of NaN");
    }
    return Float$0Instance.instance(Math.hypot(x, y));
  }
}