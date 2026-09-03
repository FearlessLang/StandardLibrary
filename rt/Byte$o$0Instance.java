package base;

import java.math.BigInteger;
import java.util.stream.IntStream;

import static base.Util.*;

class ByteCache {
  final static int max = 255;
  final static Byte$o$0Instance[] byteArray = IntStream.rangeClosed(0, max)
          .mapToObj(b -> new Byte$o$0Instance((byte) b))
          .toArray(Byte$o$0Instance[]::new);

  static Byte$o$0Instance getByte(byte b) {
    // All bytes are cached :), we do have to say our bytes are unsigned though
    return byteArray[Byte.toUnsignedInt(b)];
  }
}

public record Byte$o$0Instance(byte val) implements Byte$o$0,Norm$o$1{
  private static final byte MAX_VALUE = (byte) 255;
  private static final long MAX_VALUE_LONG = 255;

  public static Byte$o$0 instance(byte val){ return ByteCache.getByte(val); }

  private static byte clampToByte(Object o) {
    return (byte) (((Byte$o$0Instance)o).val & 255);
  }
  public static byte unwrap(Object p0) {return ((Byte$o$0Instance) p0).val;}
  private static int u8(byte b){ return Byte.toUnsignedInt(b); }
  private static int u8(Object o){ return u8(((Byte$o$0Instance)o).val); }
  private static byte b(Object o){ return ((Byte$o$0Instance)o).val; }
  private static long natBits(Object o){ return ((Nat$c$0Instance)o).val(); }

  private static byte addChecked(byte a, byte b){
    int r= u8(a) + u8(b);
    if (r > 255){ throw err("Byte.+ overflow"); }
    return (byte)r;
  }
  private static byte subChecked(byte a, byte b){
    if (Byte.compareUnsigned(a, b) < 0) {
      throw err("Byte.- underflow");
    }
    return (byte) (a - b);
  }
  private static byte mulChecked(byte a, byte b){
    int r= u8(a) * u8(b);
    if (r > 255){ throw err("Byte.* overflow"); }
    return (byte)r;
  }

  @Override public Object imm$$plus$1(Object p0){ return instance(addChecked(val,b(p0))); }
  @Override public Object imm$$dash$1(Object p0){ return instance(subChecked(val,b(p0))); }
  @Override public Object imm$$star$1(Object p0){ return instance(mulChecked(val,b(p0))); }
  @Override public Object imm$$star_star$1(Object p0) {
    byte power = b(p0);
    if (power == 0) { return Byte$o$0Instance.instance((byte) 1); }
    if (power == 1 || this.val == 1 || this.val == 0) { return this; }
    byte result = 1;
    for (int i = 0; i < power; i++) {
      result = mulChecked(result, this.val);
    }
    return Byte$o$0Instance.instance(result);
  }
  @Override public Object imm$$slash$1(Object p0){
    return Num$c$0Instance.instance(
      BigInteger.valueOf(val),
      unsignedLongToBigInteger(Nat$c$0Instance.unwrap(p0))
    );
  }
  @Override public Object imm$rem$1(Object p0){
    long d= natBits(p0);
    if (d == 0){ return optEmpty(); }
    return optSome(instance((byte) Long.remainderUnsigned(Byte.toUnsignedLong(val), d)));
  }
  @Override public Object imm$getTruncDiv$1(Object p0){
    long d= natBits(p0);

    if (d == 0L){ throw err("Byte.getTruncDiv: d==0"); }
    return instance((byte)(u8(val) / d));
  }
  /// for a % b = c, c <= a.
  /// Therefore it is safe to cast this % nat to byte
  @Override public Object imm$getRem$1(Object p0){
    long d= Nat$c$0Instance.unwrap(p0);
    if (d == 0){ throw err("Byte.getRem: d==0"); }
    return instance((byte) Long.remainderUnsigned(Byte.toUnsignedLong(val), d));
  }
  @Override public Object imm$softSqrt$0(){ return Float$1c$0Instance.instance(Math.sqrt((double)u8(val))); }
  @Override public Object imm$nat$0(){ return Nat$c$0Instance.instance(u8(val)); }
  @Override public Object imm$int$0(){ return Int$c$0Instance.instance(u8(val)); }
  @Override public Object imm$float$0(){ return Float$1c$0Instance.instance((double)u8(val)); }
  @Override public Object imm$num$0(){ return Num$c$0Instance.instance(BigInteger.valueOf(u8(val)),BigInteger.ONE); }
  @Override public Object read$str$0(){ return Str$c$0Instance.instance(Integer.toString(u8(val))); }
  @Override public Object read$imm$0(){ return this; }
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
    long sh= natBits(p0) & 7; // Byte is u8, effective shift = bits & 7 (NOT Java's &31).
    return instance((byte)((u8(val) << sh)));
  }
  @Override public Object imm$aluShiftRight$1(Object p0){
    long sh= natBits(p0) & 7;
    return instance((byte)(u8(val) >>> sh));
  }
  @Override public Object imm$aluXor$1(Object p0){ return instance((byte)(val ^ b(p0))); }
  @Override public Object imm$aluAnd$1(Object p0){ return instance((byte)(val & b(p0))); }
  @Override public Object imm$aluOr$1(Object p0){ return instance((byte)(val | b(p0))); }
  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(Integer.compare(u8(p0),u8(p1)),p2); }
  @Override public Object imm$norm$0(){ return this; }
  @Override public Object imm$get$0(){ return this; }
}