package base;
public interface YNat$0 extends base.Wrapper$2, base.DataType$2, base.Extensible$1, base.OrderHash$1, base.Order$1, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object read$name$0(){
    var this$= this;
    return base.Str$0Instance.instance("Y");
  }
  default Object read$getDataType$0(){
    var this$= this;
    return base.Nat$0Instance.instance(0L);
  }
  default Object read$$hash$1(Object p0){
    var this$= this;
    var t$= (base.Nat$0)p0;
    return new base.YNat$0(){
    public Object read$getDataType$0(){
    return t$;
  }

};
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
    var _aimpl$= (base.YNat$0)p0;
    return _aimpl$;
  }
  YNat$0 instance= new YNat$0(){};}