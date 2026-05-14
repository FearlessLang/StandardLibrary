package base;
public interface Byte$0 extends base.Sealed$0, base.DataType$2, base.SumNumber$1, base.Extensible$1, base.OrderHash$1, base.Order$1, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object imm$$plus$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$$plus$1"+this.getClass().getName());
  }
  default Object imm$$dash$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$$dash$1"+this.getClass().getName());
  }
  default Object imm$$star$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$$star$1"+this.getClass().getName());
  }
  default Object imm$$star_star$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$$star_star$1"+this.getClass().getName());
  }
  default Object imm$div$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$div$1"+this.getClass().getName());
  }
  default Object imm$rem$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$rem$1"+this.getClass().getName());
  }
  default Object imm$divExact$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$divExact$1"+this.getClass().getName());
  }
  default Object imm$sqrt$0(){
    throw new AssertionError("Uncallable method: Byte$0.imm$sqrt$0"+this.getClass().getName());
  }
  default Object imm$nat$0(){
    throw new AssertionError("Uncallable method: Byte$0.imm$nat$0"+this.getClass().getName());
  }
  default Object imm$int$0(){
    throw new AssertionError("Uncallable method: Byte$0.imm$int$0"+this.getClass().getName());
  }
  default Object imm$float$0(){
    throw new AssertionError("Uncallable method: Byte$0.imm$float$0"+this.getClass().getName());
  }
  default Object imm$num$0(){
    throw new AssertionError("Uncallable method: Byte$0.imm$num$0"+this.getClass().getName());
  }
  default Object imm$aluAddWrap$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$aluAddWrap$1"+this.getClass().getName());
  }
  default Object imm$aluSubWrap$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$aluSubWrap$1"+this.getClass().getName());
  }
  default Object imm$aluMulWrap$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$aluMulWrap$1"+this.getClass().getName());
  }
  default Object imm$aluDiv$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$aluDiv$1"+this.getClass().getName());
  }
  default Object imm$aluRem$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$aluRem$1"+this.getClass().getName());
  }
  default Object imm$aluShiftLeft$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$aluShiftLeft$1"+this.getClass().getName());
  }
  default Object imm$aluShiftRight$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$aluShiftRight$1"+this.getClass().getName());
  }
  default Object imm$aluXor$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$aluXor$1"+this.getClass().getName());
  }
  default Object imm$aluAnd$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$aluAnd$1"+this.getClass().getName());
  }
  default Object imm$aluOr$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$aluOr$1"+this.getClass().getName());
  }
  default Object imm$clamp$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: Byte$0.imm$clamp$2"+this.getClass().getName());
  }
  default Object imm$$tilde_tilde$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$$tilde_tilde$1"+this.getClass().getName());
  }
  default Object imm$$tilde_tilde_eq$1(Object p0){
    throw new AssertionError("Uncallable method: Byte$0.imm$$tilde_tilde_eq$1"+this.getClass().getName());
  }
  default Object read$hash$0(){
    var this$= this;
    return ((base.Str$0)this$.read$str$0(
)).read$hash$0(
);
  }
  default Object read$close$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Byte$0)p0;
    return _aimpl$;
  }
  default Object read$close$0(){
    var this$= this;
    return this$;
  }
  default Object mut$close$0(){
    var this$= this;
    return this$;
  }
  default Object imm$sumNumber$1(Object p0){
    var this$= this;
    var xs$= (base.Flow$1)p0;
    return xs$.mut$fold$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return this$;
  }

},
new base.F$3(){
    public Object read$$hash$2(Object p0, Object p1){
    var acc$= (base.Byte$0)p0;
    var e$= (base.Byte$0)p1;
    return acc$.imm$$plus$1(
e$);
  }

});
  }
  default Object imm$norm$0(){
    throw new AssertionError("Uncallable method: Byte$0.imm$norm$0"+this.getClass().getName());
  }
}