package base;
public interface _AssertApprox$1 extends base.DataType$2, base.Extensible$1, base.OrderHash$1, base.Order$1, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object imm$eqDelta$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: _AssertApprox$1.imm$eqDelta$2"+this.getClass().getName());
  }
  default Object imm$assertEqDelta$2(Object p0, Object p1){
    var this$= this;
    var expected$= p0;
    var delta$= p1;
    return base.Assert$0.instance.imm$$bang$2(
this$.imm$eqDelta$2(
expected$,
delta$),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Expected within delta").imm$$or$1(
base.Str$0Instance.instance("expected: "))).imm$$plus$1(
this$.read$close$1(
expected$))).imm$$or$1(
base.Str$0Instance.instance("delta: "))).imm$$plus$1(
this$.read$close$1(
delta$))).imm$$or$1(
base.Str$0Instance.instance("actual: "))).imm$$plus$1(
this$);
  }

});
  }
  default Object imm$assertEqDelta$3(Object p0, Object p1, Object p2){
    var this$= this;
    var expected$= p0;
    var delta$= p1;
    var msg$= (base.F$1)p2;
    return base.Assert$0.instance.imm$$bang$2(
this$.imm$eqDelta$2(
expected$,
delta$),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)msg$.read$$hash$0(
)).imm$$or$1(
base.Str$0Instance.instance("expected: "))).imm$$plus$1(
this$.read$close$1(
expected$))).imm$$or$1(
base.Str$0Instance.instance("delta: "))).imm$$plus$1(
this$.read$close$1(
delta$))).imm$$or$1(
base.Str$0Instance.instance("actual: "))).imm$$plus$1(
this$);
  }

});
  }
}