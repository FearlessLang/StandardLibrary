package base;
public interface LogbTests$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Float$0)base.Math$0.instance.imm$logb$2(
base.Float$0Instance.instance(2.0d),
base.Float$0Instance.instance(8.0d))).read$assertEq$1(
base.Float$0Instance.instance(3.0d)))).imm$test$1(
((base.Float$0)base.Math$0.instance.imm$logb$2(
base.Float$0Instance.instance(10.0d),
base.Float$0Instance.instance(100.0d))).read$assertEq$1(
base.Float$0Instance.instance(2.0d)))).imm$test$1(
base._FloatTest$0.instance.imm$closeTo$2(
base.Math$0.instance.imm$logb$2(
base.Float$0Instance.instance(3.0d),
((base.Nat$0)base.Nat$0Instance.instance(3L).imm$$star_star$1(
base.Nat$0Instance.instance(5L))).imm$float$0(
)),
base.Float$0Instance.instance(5.0d)))).imm$test$1(
((base.Float$0)base.Math$0.instance.imm$logb$2(
base.Float$0Instance.instance(2.0d),
base.Float$0Instance.instance(1.0d))).read$assertEq$1(
base.Float$0Instance.instance(0.0d)))).imm$test$1(
((base.Float$0)base.Math$0.instance.imm$logb$2(
base.Float$0Instance.instance(2.0d),
base.Float$0Instance.instance(0.0d))).read$assertEq$1(
base.Math$0.instance.imm$negInf$0(
)))).imm$test$1(
((base.Float$0)base.Math$0.instance.imm$logb$2(
base.Float$0Instance.instance(2.0d),
base.Math$0.instance.imm$inf$0(
))).read$assertEq$1(
base.Math$0.instance.imm$inf$0(
)))).imm$testFails$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Math$0.instance.imm$logb$2(
base.Float$0Instance.instance(2.0d),
base.Float$0Instance.instance(-1.0d));
  }

})).imm$testFails$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Math$0.instance.imm$logb$2(
base.Float$0Instance.instance(-2.0d),
base.Float$0Instance.instance(4.0d));
  }

})).imm$testFails$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Math$0.instance.imm$logb$2(
base.Float$0Instance.instance(0.0d),
base.Float$0Instance.instance(4.0d));
  }

})).imm$testFails$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Math$0.instance.imm$logb$2(
base.Math$0.instance.imm$nan$0(
),
base.Float$0Instance.instance(1.0d));
  }

})).imm$testFails$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Math$0.instance.imm$logb$2(
base.Float$0Instance.instance(3.0d),
base.Math$0.instance.imm$nan$0(
));
  }

});
  }
  LogbTests$0 instance= new LogbTests$0(){};}