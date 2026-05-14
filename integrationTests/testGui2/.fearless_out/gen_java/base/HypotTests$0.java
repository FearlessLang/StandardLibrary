package base;
public interface HypotTests$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Float$0)base.Math$0.instance.imm$hypot$2(
base.Float$0Instance.instance(3.0d),
base.Float$0Instance.instance(4.0d))).read$assertEq$1(
base.Float$0Instance.instance(5.0d)))).imm$test$1(
((base.Float$0)base.Math$0.instance.imm$hypot$2(
base.Float$0Instance.instance(0.0d),
base.Float$0Instance.instance(0.0d))).read$assertEq$1(
base.Float$0Instance.instance(0.0d)))).imm$test$1(
((base.Float$0)base.Math$0.instance.imm$hypot$2(
base.Math$0.instance.imm$inf$0(
),
base.Float$0Instance.instance(1.0d))).read$assertEq$1(
base.Math$0.instance.imm$inf$0(
)))).imm$test$1(
((base.Float$0)base.Math$0.instance.imm$hypot$2(
base.Float$0Instance.instance(1.0d),
base.Math$0.instance.imm$inf$0(
))).read$assertEq$1(
base.Math$0.instance.imm$inf$0(
)))).imm$testFails$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Math$0.instance.imm$hypot$2(
base.Math$0.instance.imm$nan$0(
),
base.Float$0Instance.instance(1.0d));
  }

})).imm$testFails$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Math$0.instance.imm$hypot$2(
base.Float$0Instance.instance(1.0d),
base.Math$0.instance.imm$nan$0(
));
  }

});
  }
  HypotTests$0 instance= new HypotTests$0(){};}