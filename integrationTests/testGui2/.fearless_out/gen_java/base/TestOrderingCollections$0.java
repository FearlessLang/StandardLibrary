package base;
public interface TestOrderingCollections$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Bool$0)((base.OrderHash$1)((base.List$1)((base.Flow$1)((base.Flow$1)((base.List$1)base.Lists$0.instance.imm$$hash$4(
base.Lists$0.instance.imm$$hash$1(
base.Nat$0Instance.instance(2L)),
base.Lists$0.instance.imm$$hash$2(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(0L)),
base.Lists$0.instance.imm$$hash$1(
base.Nat$0Instance.instance(1L)),
base.List$1.instance)).read$flow$0(
)).mut$sort$1(
new base.OrderBy$2(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.List$1)p0;
    return _bimpl$.read$order$1(
new base.OrderBy$2(){
    public Object imm$$hash$1(Object p0){
    var _cimpl$= (base.Nat$0)p0;
    return _cimpl$;
  }

});
  }

})).mut$list$0(
)).read$orderHash$1(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _dimpl$= (base.List$1)p0;
    return _dimpl$.read$orderHash$1(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _eimpl$= (base.Nat$0)p0;
    return _eimpl$;
  }

});
  }

})).read$$eq_eq$1(
base.Lists$0.instance.imm$$hash$4(
base.List$1.instance,
base.Lists$0.instance.imm$$hash$1(
base.Nat$0Instance.instance(1L)),
base.Lists$0.instance.imm$$hash$2(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(0L)),
base.Lists$0.instance.imm$$hash$1(
base.Nat$0Instance.instance(2L))))).imm$assertTrue$0(
))).imm$test$1(
((base.Bool$0)((base.OrderHash$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(2L),
base.Nat$0Instance.instance(3L))).read$orderHash$1(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _fimpl$= (base.Nat$0)p0;
    return _fimpl$;
  }

})).read$$eq_eq$1(
base.Lists$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(2L),
base.Nat$0Instance.instance(3L)))).imm$assertTrue$0(
))).imm$test$1(
((base.Bool$0)((base.OrderHash$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(2L),
base.Nat$0Instance.instance(3L))).read$orderHash$1(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _gimpl$= (base.Nat$0)p0;
    return _gimpl$;
  }

})).read$$bang_eq$1(
base.Lists$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(2L),
base.Nat$0Instance.instance(4L)))).imm$assertTrue$0(
));
  }
  TestOrderingCollections$0 instance= new TestOrderingCollections$0(){};}