package base;
public interface Num$0 extends base.Sealed$0, base.DataType$2, base.SumNumber$1, base._AssertApprox$1, base.Extensible$1, base.OrderHash$1, base.Order$1, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object imm$$plus$1(Object p0){
    throw new AssertionError("Uncallable method: Num$0.imm$$plus$1"+this.getClass().getName());
  }
  default Object imm$$dash$1(Object p0){
    throw new AssertionError("Uncallable method: Num$0.imm$$dash$1"+this.getClass().getName());
  }
  default Object imm$$star$1(Object p0){
    throw new AssertionError("Uncallable method: Num$0.imm$$star$1"+this.getClass().getName());
  }
  default Object imm$$slash$1(Object p0){
    throw new AssertionError("Uncallable method: Num$0.imm$$slash$1"+this.getClass().getName());
  }
  default Object imm$$star_star$1(Object p0){
    throw new AssertionError("Uncallable method: Num$0.imm$$star_star$1"+this.getClass().getName());
  }
  default Object imm$abs$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$abs$0"+this.getClass().getName());
  }
  default Object imm$sqrt$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$sqrt$0"+this.getClass().getName());
  }
  default Object imm$sign$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$sign$0"+this.getClass().getName());
  }
  default Object imm$floor$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$floor$0"+this.getClass().getName());
  }
  default Object imm$ceil$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$ceil$0"+this.getClass().getName());
  }
  default Object imm$trunc0$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$trunc0$0"+this.getClass().getName());
  }
  default Object imm$round$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$round$0"+this.getClass().getName());
  }
  default Object imm$isInteger$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$isInteger$0"+this.getClass().getName());
  }
  default Object imm$int$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$int$0"+this.getClass().getName());
  }
  default Object imm$nat$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$nat$0"+this.getClass().getName());
  }
  default Object imm$byte$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$byte$0"+this.getClass().getName());
  }
  default Object imm$float$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$float$0"+this.getClass().getName());
  }
  default Object imm$intExact$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$intExact$0"+this.getClass().getName());
  }
  default Object imm$natExact$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$natExact$0"+this.getClass().getName());
  }
  default Object imm$byteExact$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$byteExact$0"+this.getClass().getName());
  }
  default Object imm$clamp$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: Num$0.imm$clamp$2"+this.getClass().getName());
  }
  default Object read$hash$0(){
    var this$= this;
    return ((base.Str$0)this$.read$str$0(
)).read$hash$0(
);
  }
  default Object read$close$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Num$0)p0;
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
    var acc$= (base.Num$0)p0;
    var e$= (base.Num$0)p1;
    return acc$.imm$$plus$1(
e$);
  }

});
  }
  default Object imm$norm$0(){
    throw new AssertionError("Uncallable method: Num$0.imm$norm$0"+this.getClass().getName());
  }
  default Object read$info$0(){
    throw new AssertionError("Uncallable method: Num$0.read$info$0"+this.getClass().getName());
  }
  default Object read$imm$0(){
    throw new AssertionError("Uncallable method: Num$0.read$imm$0"+this.getClass().getName());
  }
  default Object read$cmp$3(Object p0, Object p1, Object p2){
    throw new AssertionError("Uncallable method: Num$0.read$cmp$3"+this.getClass().getName());
  }
  default Object read$str$0(){
    throw new AssertionError("Uncallable method: Num$0.read$str$0"+this.getClass().getName());
  }
}