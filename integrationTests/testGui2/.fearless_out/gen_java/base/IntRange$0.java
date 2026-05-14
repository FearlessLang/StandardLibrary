package base;
public interface IntRange$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Nat$0)((base.Flow$1)base.Int$0Instance.instance(0L).imm$$tilde_tilde$1(
base.Int$0Instance.instance(0L))).mut$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(0L)))).imm$test$1(
((base.List$1)((base.Flow$1)base.Int$0Instance.instance(0L).imm$$tilde_tilde$1(
base.Int$0Instance.instance(1L))).mut$list$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.Int$0)p0;
    return _bimpl$;
  }

},
base.Lists$0.instance.imm$$hash$1(
base.Int$0Instance.instance(0L))))).imm$test$1(
((base.List$1)((base.Flow$1)base.Int$0Instance.instance(0L).imm$$tilde_tilde_eq$1(
base.Int$0Instance.instance(1L))).mut$list$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _cimpl$= (base.Int$0)p0;
    return _cimpl$;
  }

},
base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(0L),
base.Int$0Instance.instance(1L))))).imm$test$1(
((base.List$1)((base.Flow$1)base.Int$0Instance.instance(0L).imm$$tilde_tilde$1(
base.Int$0Instance.instance(2L))).mut$list$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _dimpl$= (base.Int$0)p0;
    return _dimpl$;
  }

},
base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(0L),
base.Int$0Instance.instance(1L))))).imm$test$1(
((base.List$1)((base.Flow$1)base.Int$0Instance.instance(-2L).imm$$tilde_tilde$1(
base.Int$0Instance.instance(2L))).mut$list$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _eimpl$= (base.Int$0)p0;
    return _eimpl$;
  }

},
base.Lists$0.instance.imm$$hash$4(
base.Int$0Instance.instance(-2L),
base.Int$0Instance.instance(-1L),
base.Int$0Instance.instance(0L),
base.Int$0Instance.instance(1L))))).imm$test$1(
((base.List$1)((base.Flow$1)base.Int$0Instance.instance(-2L).imm$$tilde_tilde_eq$1(
base.Int$0Instance.instance(2L))).mut$list$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _fimpl$= (base.Int$0)p0;
    return _fimpl$;
  }

},
base.Lists$0.instance.imm$$hash$5(
base.Int$0Instance.instance(-2L),
base.Int$0Instance.instance(-1L),
base.Int$0Instance.instance(0L),
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(2L))))).imm$testFails$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Int$0Instance.instance(0L).imm$$tilde_tilde$1(
base.Int$0Instance.instance(-1L));
  }

})).imm$testFails$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Int$0Instance.instance(0L).imm$$tilde_tilde_eq$1(
base.Int$0Instance.instance(-1L));
  }

});
  }
  IntRange$0 instance= new IntRange$0(){};}