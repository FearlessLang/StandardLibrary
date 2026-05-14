package base;
public interface TestThrowing$0 extends base.Main$0{
  default Object imm$main$1(Object p0){
    var this$= this;
    var s$= (base.System$0)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return s$.mut$try$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var try$= (base.CapTry$0)p0;
    var _aeqS$= (base.Block$1)p1;
    return ((base.Block$1)((base.Block$1)((base.Block$1)((base.Block$1)((base.Block$1)_aeqS$.mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.ThrowHarnessSuite$0.instance.read$$hash$1(
try$);
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.ThrowArithSuite$0.instance.read$$hash$1(
try$);
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.ThrowRangeSuite$0.instance.read$$hash$1(
try$);
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.ThrowListSuite$0.instance.read$$hash$1(
try$);
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.ThrowMapSuite$0.instance.read$$hash$1(
try$);
  }

})).mut$done$0(
);
  }

});
  }
  TestThrowing$0 instance= new TestThrowing$0(){};}