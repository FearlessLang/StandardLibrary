package base;
public interface NatConv$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Int$0)base.Nat$0Instance.instance(0L).imm$int$0(
)).read$assertEq$1(
base.Int$0Instance.instance(0L)))).imm$test$1(
((base.Int$0)base.Nat$0Instance.instance(5L).imm$int$0(
)).read$assertEq$1(
base.Int$0Instance.instance(5L)))).imm$test$1(
((base.Byte$0)base.Nat$0Instance.instance(255L).imm$byte$0(
)).read$assertEq$1(
base.Int$0Instance.instance(255L).imm$byte$0(
)))).imm$test$1(
((base.Byte$0)base.Nat$0Instance.instance(300L).imm$byte$0(
)).read$assertEq$1(
base.Int$0Instance.instance(255L).imm$byte$0(
)))).imm$test$1(
((base.Float$0)base.Nat$0Instance.instance(5L).imm$float$0(
)).read$assertEq$1(
base.Float$0Instance.instance(5.0d)))).imm$test$1(
((base.Num$0)base.Nat$0Instance.instance(5L).imm$num$0(
)).read$assertEq$1(
base.Int$0Instance.instance(5L).imm$num$0(
)))).imm$test$1(
((base.Opt$1)base.Nat$0Instance.instance(5L).imm$intExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.Int$0)p0;
    return _bimpl$;
  }

},
base.Opts$0.instance.imm$$hash$1(
base.Int$0Instance.instance(5L))))).imm$test$1(
((base.Opt$1)((base.Nat$0)base.Int$0Instance.instance(-1L).imm$aluNat$0(
)).imm$intExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _cimpl$= (base.Int$0)p0;
    return _cimpl$;
  }

},
base.Opt$1.instance))).imm$test$1(
((base.Opt$1)base.Nat$0Instance.instance(5L).imm$byteExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _dimpl$= (base.Byte$0)p0;
    return _dimpl$;
  }

},
base.Opts$0.instance.imm$$hash$1(
base.Int$0Instance.instance(5L).imm$byte$0(
))))).imm$test$1(
((base.Opt$1)base.Nat$0Instance.instance(300L).imm$byteExact$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _eimpl$= (base.Byte$0)p0;
    return _eimpl$;
  }

},
base.Opt$1.instance))).imm$test$1(
((base.Int$0)((base.Nat$0)base.Int$0Instance.instance(-1L).imm$aluNat$0(
)).imm$int$0(
)).read$assertEq$1(
base.Math$0.instance.imm$maxInt$0(
)));
  }
  NatConv$0 instance= new NatConv$0(){};}