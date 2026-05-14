package base;
public interface ThrowHarnessSuite$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var try$= (base.CapTry$0)p0;
    return ((base.Block$1)((base.Block$1)((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Action$1)try$.mut$$hash$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Assert$0.instance.imm$$bang$1(
base.False$0.instance);
  }

})).mut$assertInfo$0(
);
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Action$1)try$.mut$$hash$1(
new base.F$1(){
    public Object read$$hash$0(){
    return ((base.Action$1)base.Actions$0.instance.imm$ok$1(
base.Int$0Instance.instance(1L))).mut$assertInfo$0(
);
  }

})).mut$assertInfo$0(
);
  }

})).mut$done$0(
);
  }
  ThrowHarnessSuite$0 instance= new ThrowHarnessSuite$0(){};}