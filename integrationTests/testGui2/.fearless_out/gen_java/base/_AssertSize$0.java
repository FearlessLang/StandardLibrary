package base;
public interface _AssertSize$0{
  default Object read$size$0(){
    throw new AssertionError("Uncallable method: _AssertSize$0.read$size$0"+this.getClass().getName());
  }
  default Object read$assertSize$1(Object p0){
    var this$= this;
    var expected$= (base.Nat$0)p0;
    return this$.read$assertSize$2(
expected$,
new base.F$1(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  default Object read$assertSize$2(Object p0, Object p1){
    var this$= this;
    var expected$= (base.Nat$0)p0;
    var msg$= (base.F$1)p1;
    return base.Assert$0.instance.imm$$bang$2(
((base.Nat$0)this$.read$size$0(
)).read$$eq_eq$1(
expected$),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)msg$.read$$hash$0(
)).imm$$or$1(
base.Str$0Instance.instance("Expected size: "))).imm$$plus$1(
expected$)).imm$$or$1(
base.Str$0Instance.instance("Actual size: "))).imm$$plus$1(
this$.read$size$0(
));
  }

});
  }
}