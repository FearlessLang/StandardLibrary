package base;
public interface Saturation$0 extends base.Wrapper$2, base.DataType$2, base.Extensible$1, base.OrderHash$1, base.Order$1, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object read$name$0(){
    var this$= this;
    return base.Str$0Instance.instance("Saturation");
  }
  default Object read$getDataType$0(){
    var this$= this;
    return base.Float$0Instance.instance(0.0d);
  }
  default Object read$$hash$1(Object p0){
    var this$= this;
    var t$= (base.Float$0)p0;
    return base.Block$0.instance.imm$$hash$2(
t$.read$assertInRange$3(
base.Float$0Instance.instance(0.0d),
base.Float$0Instance.instance(1.0d),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("Saturation must be between 0 and 1");
  }

}),
new base.Saturation$0(){
    public Object read$getDataType$0(){
    return t$;
  }

});
  }
  default Object read$close$0(){
    var this$= this;
    return this$;
  }
  default Object mut$close$0(){
    var this$= this;
    return this$;
  }
  default Object read$close$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Saturation$0)p0;
    return _aimpl$;
  }
  Saturation$0 instance= new Saturation$0(){};}