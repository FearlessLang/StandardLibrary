package base;
public interface TestThrowingExample$0 extends base.Main$0{
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
    return ((base.Block$1)((base.Block$1)((base.Block$1)_aeqS$.mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Action$1)try$.mut$$hash$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Nat$0Instance.instance(12L).imm$div$1(
base.Nat$0Instance.instance(0L));
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
    return base.Math$0.instance.imm$log$1(
base.Float$0Instance.instance(-1.0d));
  }

})).mut$assertInfo$0(
);
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.TestSuite1$0.instance.read$$hash$1(
try$);
  }

})).mut$done$0(
);
  }

});
  }
  TestThrowingExample$0 instance= new TestThrowingExample$0(){};}