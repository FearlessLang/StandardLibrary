package base;
public interface _FloatTest$0{
  default Object imm$within$3(Object p0, Object p1, Object p2){
    var this$= this;
    var actual$= (base.Float$0)p0;
    var expected$= (base.Float$0)p1;
    var delta$= (base.Float$0)p2;
    return actual$.read$assertInRange$2(
expected$.imm$$dash$1(
delta$),
expected$.imm$$plus$1(
delta$));
  }
  default Object imm$closeTo$2(Object p0, Object p1){
    var this$= this;
    var actual$= (base.Float$0)p0;
    var expected$= (base.Float$0)p1;
    return this$.imm$within$3(
actual$,
expected$,
base.Float$0Instance.instance(1.0E-4d));
  }
  _FloatTest$0 instance= new _FloatTest$0(){};}