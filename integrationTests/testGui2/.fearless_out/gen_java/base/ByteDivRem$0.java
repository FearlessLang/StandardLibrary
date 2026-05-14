package base;
public interface ByteDivRem$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Byte$0)((base.Byte$0)base.Int$0Instance.instance(7L).imm$byte$0(
)).imm$div$1(
base.Nat$0Instance.instance(3L))).read$assertEq$1(
base.Int$0Instance.instance(2L).imm$byte$0(
)))).imm$test$1(
((base.Byte$0)((base.Byte$0)base.Int$0Instance.instance(7L).imm$byte$0(
)).imm$rem$1(
base.Nat$0Instance.instance(3L))).read$assertEq$1(
base.Int$0Instance.instance(1L).imm$byte$0(
)))).imm$test$1(
((base.Opt$1)((base.Byte$0)base.Int$0Instance.instance(6L).imm$byte$0(
)).imm$divExact$1(
base.Nat$0Instance.instance(3L))).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.Byte$0)p0;
    return _bimpl$;
  }

},
base.Opts$0.instance.imm$$hash$1(
base.Int$0Instance.instance(2L).imm$byte$0(
))))).imm$test$1(
((base.Opt$1)((base.Byte$0)base.Int$0Instance.instance(7L).imm$byte$0(
)).imm$divExact$1(
base.Nat$0Instance.instance(3L))).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _cimpl$= (base.Byte$0)p0;
    return _cimpl$;
  }

},
base.Opt$1.instance))).imm$test$1(
((base.Opt$1)((base.Byte$0)base.Int$0Instance.instance(7L).imm$byte$0(
)).imm$divExact$1(
base.Nat$0Instance.instance(0L))).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _dimpl$= (base.Byte$0)p0;
    return _dimpl$;
  }

},
base.Opt$1.instance));
  }
  ByteDivRem$0 instance= new ByteDivRem$0(){};}