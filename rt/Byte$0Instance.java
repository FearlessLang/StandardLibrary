package base;

import java.math.BigInteger;
import static base.Util.*;

public record Byte$0Instance(byte val) implements Byte$0{
  public static Byte$0 instance(byte val){ return new Byte$0Instance(val); }

  private static int u8(byte b){ return b & 255; }
  private static int u8(Object o){ return u8(((Byte$0Instance)o).val); }
  private static byte b(Object o){ return ((Byte$0Instance)o).val; }

  private static int natBits(Object o){ return ((Nat$0Instance)o).val(); }
  private static long natU(Object o){ return Integer.toUnsignedLong(natBits(o)); }

  private static byte addChecked(byte a, byte b){
    int r= u8(a) + u8(b);
    if (r > 255){ throw err("Byte.+ overflow"); }
    return (byte)r;
  }
  private static byte subChecked(byte a, byte b){
    int ua= u8(a), ub= u8(b);
    if (ua < ub){ throw err("Byte.- underflow"); }
    return (byte)(ua - ub);
  }
  private static byte mulChecked(byte a, byte b){
    int r= u8(a) * u8(b);
    if (r > 255){ throw err("Byte.* overflow"); }
    return (byte)r;
  }
  @Override public Object imm$$plus$1(Object p0){ return instance(addChecked(val,b(p0))); }
  @Override public Object imm$$dash$1(Object p0){ return instance(subChecked(val,b(p0))); }
  @Override public Object imm$$star$1(Object p0){ return instance(mulChecked(val,b(p0))); }
  @Override public Object imm$div$1(Object p0){
    long d= natU(p0);
    if (d == 0L){ throw err("Byte.div: d==0"); }
    return instance((byte)(u8(val) / d));
  }
  @Override public Object imm$rem$1(Object p0){
    long d= natU(p0);
    if (d == 0L){ throw err("Byte.rem: d==0"); }
    return instance((byte)(u8(val) % d));
  }
  @Override public Object imm$divExact$1(Object p0){
    long d= natU(p0);
    if (d == 0L){ return optEmpty(); }
    int x= u8(val);
    if ((x % d) != 0L){ return optEmpty(); }
    return optSome(instance((byte)(x / d)));
  }
  @Override public Object imm$sqrt$0(){ return Float$0Instance.instance(Math.sqrt((double)u8(val))); }
  @Override public Object read$nat$0(){ return Nat$0Instance.instance(u8(val)); }
  @Override public Object read$int$0(){ return Int$0Instance.instance(u8(val)); }
  @Override public Object read$float$0(){ return Float$0Instance.instance((double)u8(val)); }
  @Override public Object read$num$0(){ return Num$0Instance.instance(BigInteger.valueOf(u8(val)),BigInteger.ONE); }
  @Override public Object read$str$0(){ return Str$0Instance.instance(Integer.toString(u8(val))); }
  @Override public Object read$info$0(){ return Info$0.instance; }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object imm$$lt_eq_gt$1(Object p0){ return ord(Integer.compare(u8(val),u8(p0))); }
  @Override public Object imm$inRange$2(Object p0, Object p1){
    int lo= u8(p0), hi= u8(p1), x= u8(val);
    if (lo > hi){ throw err("Byte.inRange: lo>hi"); }
    return bool(lo <= x && x <= hi);
  }
  @Override public Object imm$inRangeOpen$2(Object p0, Object p1){
    int lo= u8(p0), hi= u8(p1), x= u8(val);
    if (lo > hi){ throw err("Byte.inRangeOpen: lo>hi"); }
    return bool(lo < x && x < hi);
  }
  @Override public Object imm$inRangeLoOpen$2(Object p0, Object p1){
    int lo= u8(p0), hi= u8(p1), x= u8(val);
    if (lo > hi){ throw err("Byte.inRangeLoOpen: lo>hi"); }
    return bool(lo < x && x <= hi);
  }
  @Override public Object imm$inRangeHiOpen$2(Object p0, Object p1){
    int lo= u8(p0), hi= u8(p1), x= u8(val);
    if (lo > hi){ throw err("Byte.inRangeHiOpen: lo>hi"); }
    return bool(lo <= x && x < hi);
  }
  @Override public Object imm$clamp$2(Object p0, Object p1){
    int lo= u8(p0), hi= u8(p1), x= u8(val);
    if (lo > hi){ throw err("Byte.clamp: lo>hi"); }
    if (x < lo){ return instance((byte)lo); }
    if (x > hi){ return instance((byte)hi); }
    return this;
  }
  @Override public Object imm$aluAddWrap$1(Object p0){ return instance((byte)(val + b(p0))); }
  @Override public Object imm$aluSubWrap$1(Object p0){ return instance((byte)(val - b(p0))); }
  @Override public Object imm$aluMulWrap$1(Object p0){ return instance((byte)(val * b(p0))); }
  @Override public Object imm$aluDiv$1(Object p0){
    int d= u8(p0);
    if (d == 0){ throw err("Byte.aluDiv: x==0"); }
    return instance((byte)(u8(val) / d));
  }
  @Override public Object imm$aluRem$1(Object p0){
    int d= u8(p0);
    if (d == 0){ throw err("Byte.aluRem: x==0"); }
    return instance((byte)(u8(val) % d));
  }
  @Override public Object imm$aluShiftLeft$1(Object p0){
    int sh= natBits(p0) & 7; // Byte is u8, effective shift = bits & 7 (NOT Java's &31).
    return instance((byte)((u8(val) << sh)));
  }
  @Override public Object imm$aluShiftRight$1(Object p0){
    int sh= natBits(p0) & 7;
    return instance((byte)(u8(val) >>> sh));
  }
  @Override public Object imm$aluXor$1(Object p0){ return instance((byte)(val ^ b(p0))); }
  @Override public Object imm$aluAnd$1(Object p0){ return instance((byte)(val & b(p0))); }
  @Override public Object imm$aluOr$1(Object p0){ return instance((byte)(val | b(p0))); }
}