package base;
public interface FloatConv$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Int$0)base.Float$0Instance.instance(1.5d).imm$int$0(
)).read$assertEq$1(
base.Int$0Instance.instance(1L)))).imm$test$1(
((base.Int$0)base.Float$0Instance.instance(-1.5d).imm$int$0(
)).read$assertEq$1(
base.Int$0Instance.instance(-1L)))).imm$test$1(
((base.Nat$0)base.Float$0Instance.instance(1.5d).imm$nat$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(1L)))).imm$test$1(
((base.Nat$0)base.Float$0Instance.instance(-1.5d).imm$nat$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(0L)))).imm$test$1(
((base.Byte$0)base.Float$0Instance.instance(300.0d).imm$byte$0(
)).read$assertEq$1(
base.Int$0Instance.instance(255L).imm$byte$0(
)))).imm$test$1(
((base.Byte$0)base.Float$0Instance.instance(-1.0d).imm$byte$0(
)).read$assertEq$1(
base.Int$0Instance.instance(0L).imm$byte$0(
)))).imm$test$1(
((base.Int$0)((base.Float$0)base.Math$0.instance.imm$nan$0(
)).imm$int$0(
)).read$assertEq$1(
base.Int$0Instance.instance(0L)))).imm$test$1(
((base.Int$0)((base.Float$0)base.Float$0Instance.instance(1.0d).imm$$slash$1(
base.Float$0Instance.instance(0.0d))).imm$int$0(
)).read$assertEq$1(
base.Math$0.instance.imm$maxInt$0(
)))).imm$test$1(
((base.Int$0)((base.Float$0)base.Float$0Instance.instance(-1.0d).imm$$slash$1(
base.Float$0Instance.instance(0.0d))).imm$int$0(
)).read$assertEq$1(
base.Math$0.instance.imm$minInt$0(
)))).imm$test$1(
((base.Nat$0)((base.Float$0)base.Float$0Instance.instance(1.0d).imm$$slash$1(
base.Float$0Instance.instance(0.0d))).imm$nat$0(
)).read$assertEq$1(
base.Math$0.instance.imm$maxNat$0(
)))).imm$test$1(
((base.Opt$1)base.Float$0Instance.instance(3.0d).imm$intExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.Int$0)p0;
    return _bimpl$;
  }

},
base.Opts$0.instance.imm$$hash$1(
base.Int$0Instance.instance(3L))))).imm$test$1(
((base.Opt$1)base.Float$0Instance.instance(3.0d).imm$natExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _cimpl$= (base.Nat$0)p0;
    return _cimpl$;
  }

},
base.Opts$0.instance.imm$$hash$1(
base.Nat$0Instance.instance(3L))))).imm$test$1(
((base.Opt$1)base.Float$0Instance.instance(3.0d).imm$byteExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _dimpl$= (base.Byte$0)p0;
    return _dimpl$;
  }

},
base.Opts$0.instance.imm$$hash$1(
base.Int$0Instance.instance(3L).imm$byte$0(
))))).imm$test$1(
((base.Opt$1)base.Float$0Instance.instance(255.0d).imm$byteExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _eimpl$= (base.Byte$0)p0;
    return _eimpl$;
  }

},
base.Opts$0.instance.imm$$hash$1(
base.Int$0Instance.instance(255L).imm$byte$0(
))))).imm$test$1(
((base.Opt$1)base.Float$0Instance.instance(1.5d).imm$intExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _fimpl$= (base.Int$0)p0;
    return _fimpl$;
  }

},
base.Opt$1.instance))).imm$test$1(
((base.Opt$1)base.Float$0Instance.instance(-1.0d).imm$natExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _gimpl$= (base.Nat$0)p0;
    return _gimpl$;
  }

},
base.Opt$1.instance))).imm$test$1(
((base.Opt$1)((base.Float$0)base.Math$0.instance.imm$nan$0(
)).imm$intExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _himpl$= (base.Int$0)p0;
    return _himpl$;
  }

},
base.Opt$1.instance))).imm$test$1(
((base.Opt$1)((base.Float$0)base.Float$0Instance.instance(1.0d).imm$$slash$1(
base.Float$0Instance.instance(0.0d))).imm$byteExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _jimpl$= (base.Byte$0)p0;
    return _jimpl$;
  }

},
base.Opt$1.instance))).imm$test$1(
((base.Opt$1)((base.Float$0)base.Math$0.instance.imm$nan$0(
)).imm$numExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _kimpl$= (base.Num$0)p0;
    return _kimpl$;
  }

},
base.Opt$1.instance))).imm$test$1(
((base.Opt$1)((base.Float$0)base.Float$0Instance.instance(1.0d).imm$$slash$1(
base.Float$0Instance.instance(0.0d))).imm$numExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _mimpl$= (base.Num$0)p0;
    return _mimpl$;
  }

},
base.Opt$1.instance))).imm$test$1(
((base.Opt$1)base.Float$0Instance.instance(3.0d).imm$numExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _nimpl$= (base.Num$0)p0;
    return _nimpl$;
  }

},
base.Opts$0.instance.imm$$hash$1(
base.Int$0Instance.instance(3L).imm$num$0(
))));
  }
  FloatConv$0 instance= new FloatConv$0(){};}