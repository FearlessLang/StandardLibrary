package base;
public interface NatDivRem$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Nat$0)base.Nat$0Instance.instance(7L).imm$div$1(
base.Nat$0Instance.instance(3L))).read$assertEq$1(
base.Nat$0Instance.instance(2L)))).imm$test$1(
((base.Nat$0)base.Nat$0Instance.instance(7L).imm$rem$1(
base.Nat$0Instance.instance(3L))).read$assertEq$1(
base.Nat$0Instance.instance(1L)))).imm$test$1(
((base.Opt$1)base.Nat$0Instance.instance(6L).imm$divExact$1(
base.Nat$0Instance.instance(3L))).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.Nat$0)p0;
    return _bimpl$;
  }

},
base.Opts$0.instance.imm$$hash$1(
base.Nat$0Instance.instance(2L))))).imm$test$1(
((base.Opt$1)base.Nat$0Instance.instance(7L).imm$divExact$1(
base.Nat$0Instance.instance(3L))).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _cimpl$= (base.Nat$0)p0;
    return _cimpl$;
  }

},
base.Opt$1.instance))).imm$test$1(
((base.Opt$1)base.Nat$0Instance.instance(7L).imm$divExact$1(
base.Nat$0Instance.instance(0L))).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _dimpl$= (base.Nat$0)p0;
    return _dimpl$;
  }

},
base.Opt$1.instance))).imm$test$1(
((base.Float$0)base.Nat$0Instance.instance(9L).imm$sqrt$0(
)).read$assertEq$1(
base.Float$0Instance.instance(3.0d)));
  }
  NatDivRem$0 instance= new NatDivRem$0(){};}