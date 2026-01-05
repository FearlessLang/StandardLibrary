package base;

import java.math.BigInteger;
import static base.Util.*;

public record Nat$0Instance(int val) implements Nat$0{
  public static Nat$0 instance(int val){ return new Nat$0Instance(val); }

  private static int n(Object o){ return ((Nat$0Instance)o).val; }
  private static int i(Object o){ return ((Int$0Instance)o).val(); }
  private static long u32(int x){ return Integer.toUnsignedLong(x); }
  private static int addChecked(int a, int b){
    long r= u32(a) + u32(b);
    if (r > 0xFFFF_FFFFL){ throw err("Nat.+ overflow"); }
    return (int)r;
  }
  private static int subChecked(int a, int b){
    long ua= u32(a), ub= u32(b);
    if (ua < ub){ throw err("Nat.- underflow"); }
    return (int)(ua - ub);
  }
  private static int mulChecked(int a, int b){
    long ua= u32(a), ub= u32(b);
    if (ua != 0 && ub > (0xFFFF_FFFFL / ua)){ throw err("Nat.* overflow"); }
    return (int)(ua * ub);
  }
  @Override public Object imm$int$0(){ return Int$0Instance.instance(val < 0 ? Integer.MAX_VALUE : val); }
  @Override public Object imm$byte$0(){
    int x= Integer.compareUnsigned(val,255) > 0 ? 255 : val;
    return Byte$0Instance.instance((byte)x);
  }
  @Override public Object imm$float$0(){ return Float$0Instance.instance((double)u32(val)); }
  @Override public Object imm$num$0(){ return Num$0Instance.instance(BigInteger.valueOf(u32(val)),BigInteger.ONE); }
  @Override public Object imm$intExact$0(){ return val < 0 ? optEmpty() : optSome(Int$0Instance.instance(val)); }
  @Override public Object imm$byteExact$0(){
    return Integer.compareUnsigned(val,255) > 0 ? optEmpty() : optSome(Byte$0Instance.instance((byte)val));
  }
  @Override public Object imm$$plus$1(Object p0){ return instance(addChecked(val,n(p0))); }
  @Override public Object imm$$dash$1(Object p0){ return instance(subChecked(val,n(p0))); }
  @Override public Object imm$$star$1(Object p0){ return instance(mulChecked(val,n(p0))); }
  @Override public Object imm$sqrt$0(){ return Float$0Instance.instance(Math.sqrt((double)u32(val))); }
  @Override public Object read$str$0(){ return Str$0Instance.instance(Integer.toUnsignedString(val)); }
  @Override public Object read$info$0(){ return Info$0.instance; }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object imm$clamp$2(Object p0, Object p1){
    int lo= n(p0), hi= n(p1);
    if (Integer.compareUnsigned(lo,hi) > 0){ throw err("Nat.clamp: lo>hi"); }
    if (Integer.compareUnsigned(val,lo) < 0){ return instance(lo); }
    if (Integer.compareUnsigned(val,hi) > 0){ return instance(hi); }
    return this;
  }
  @Override public Object imm$div$1(Object p0){
    int d= n(p0);
    if (d == 0){ throw err("Nat.div: d==0"); }
    return instance(Integer.divideUnsigned(val,d));
  }
  @Override public Object imm$rem$1(Object p0){
    int d= n(p0);
    if (d == 0){ throw err("Nat.rem: d==0"); }
    return instance(Integer.remainderUnsigned(val,d));
  }
  @Override public Object imm$divExact$1(Object p0){
    int d= n(p0);
    if (d == 0){ return optEmpty(); }
    if (Integer.remainderUnsigned(val,d) != 0){ return optEmpty(); }
    return optSome(instance(Integer.divideUnsigned(val,d)));
  }
  @Override public Object imm$indexOffset$1(Object p0){
    long r= u32(val) + (long)i(p0);
    if (r < 0 || r > 0xFFFF_FFFFL){ throw err("Nat.indexOffset: under/overflow"); }
    return instance((int)r);
  }
  @Override public Object imm$aluAddWrap$1(Object p0){ return instance(val + n(p0)); }
  @Override public Object imm$aluSubWrap$1(Object p0){ return instance(val - n(p0)); }
  @Override public Object imm$aluMulWrap$1(Object p0){ return instance(val * n(p0)); }
  @Override public Object imm$aluDiv$1(Object p0){
    int d= n(p0);
    if (d == 0){ throw err("Nat.aluDiv: divByZero"); }
    return instance(Integer.divideUnsigned(val,d));
  }
  @Override public Object imm$aluRem$1(Object p0){
    int d= n(p0);
    if (d == 0){ throw err("Nat.aluRem: divByZero"); }
    return instance(Integer.remainderUnsigned(val,d));
  }
  @Override public Object imm$aluShiftLeft$1(Object p0){ return instance(val << n(p0)); }
  @Override public Object imm$aluShiftRight$1(Object p0){ return instance(val >>> n(p0)); }
  @Override public Object imm$aluXor$1(Object p0){ return instance(val ^ n(p0)); }
  @Override public Object imm$aluAnd$1(Object p0){ return instance(val & n(p0)); }
  @Override public Object imm$aluOr$1(Object p0){ return instance(val | n(p0)); }
  @Override public Object imm$aluInt$0(){ return Int$0Instance.instance(val); }
  @Override public Object imm$aluByte$0(){ return Byte$0Instance.instance((byte)val); }
  
  @Override public Object read$cmp$3(Object p0, Object p1, Object p2){ return ord(Integer.compareUnsigned(n(p0),n(p1)),p2); }
}