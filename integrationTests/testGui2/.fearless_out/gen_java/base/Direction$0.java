package base;
public interface Direction$0 extends base.ToStr$0, base.Sealed$0, base.WidenTo$1{
  default Object read$match$1(Object p0){
    throw new AssertionError("Uncallable method: Direction$0.read$match$1"+this.getClass().getName());
  }
  default Object imm$turn$0(){
    var this$= this;
    return this$.read$match$1(
new base.DirectionMatch$1(){
    public Object mut$north$0(){
    return base.East$0.instance;
  }

    public Object mut$east$0(){
    return base.South$0.instance;
  }

    public Object mut$south$0(){
    return base.West$0.instance;
  }

    public Object mut$west$0(){
    return base.North$0.instance;
  }

});
  }
  default Object read$str$0(){
    var this$= this;
    return this$.read$match$1(
new base.DirectionMatch$1(){
    public Object mut$north$0(){
    return base.Str$0Instance.instance("North");
  }

    public Object mut$east$0(){
    return base.Str$0Instance.instance("East");
  }

    public Object mut$south$0(){
    return base.Str$0Instance.instance("South");
  }

    public Object mut$west$0(){
    return base.Str$0Instance.instance("West");
  }

});
  }
}