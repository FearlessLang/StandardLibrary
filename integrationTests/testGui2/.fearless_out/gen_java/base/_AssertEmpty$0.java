package base;
public interface _AssertEmpty$0{
  default Object read$isEmpty$0(){
    throw new AssertionError("Uncallable method: _AssertEmpty$0.read$isEmpty$0"+this.getClass().getName());
  }
  default Object read$assertEmpty$0(){
    var this$= this;
    return this$.read$assertEmpty$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  default Object read$assertEmpty$1(Object p0){
    var this$= this;
    var msg$= (base.F$1)p0;
    return base.Assert$0.instance.imm$$bang$2(
this$.read$isEmpty$0(
),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Str$0)msg$.read$$hash$0(
)).imm$$or$1(
base.Str$0Instance.instance("Expected empty"));
  }

});
  }
}