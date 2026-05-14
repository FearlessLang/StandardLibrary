package base;
public interface TestListsBasics$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Nat$0)base.List$1.instance.read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(0L)))).imm$test$1(
((base.Bool$0)base.List$1.instance.read$isEmpty$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Nat$0)((base.List$1)base.Lists$0.instance.imm$$hash$5(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"),
base.Str$0Instance.instance("d"),
base.Str$0Instance.instance("e"))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(5L)))).imm$test$1(
((base.Str$0)((base.List$1)base.Lists$0.instance.imm$$hash$5(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"),
base.Str$0Instance.instance("d"),
base.Str$0Instance.instance("e"))).mut$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Str$0)((base.List$1)base.Lists$0.instance.imm$$hash$5(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"),
base.Str$0Instance.instance("d"),
base.Str$0Instance.instance("e"))).mut$get$1(
base.Nat$0Instance.instance(4L))).read$assertEq$1(
base.Str$0Instance.instance("e")))).imm$test$1(
((base.Nat$0)((base.Grid$0)base.Grids$0.instance.imm$$hash$1(
base.Lists$0.instance.imm$$hash$15(
base.Nat$0Instance.instance(0L),
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(2L),
base.Nat$0Instance.instance(3L),
base.Nat$0Instance.instance(4L),
base.Nat$0Instance.instance(5L),
base.Nat$0Instance.instance(6L),
base.Nat$0Instance.instance(7L),
base.Nat$0Instance.instance(8L),
base.Nat$0Instance.instance(9L),
base.Nat$0Instance.instance(10L),
base.Nat$0Instance.instance(11L),
base.Nat$0Instance.instance(12L),
base.Nat$0Instance.instance(13L),
base.Nat$0Instance.instance(14L)))).imm$get$2(
base.Nat$0Instance.instance(3L),
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Nat$0Instance.instance(8L)))).imm$test$1(
((base.Nat$0)((base.Grid$0)base.Grids$0.instance.imm$$hash$1(
base.Lists$0.instance.imm$$hash$15(
base.Nat$0Instance.instance(0L),
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(2L),
base.Nat$0Instance.instance(3L),
base.Nat$0Instance.instance(4L),
base.Nat$0Instance.instance(5L),
base.Nat$0Instance.instance(6L),
base.Nat$0Instance.instance(7L),
base.Nat$0Instance.instance(8L),
base.Nat$0Instance.instance(9L),
base.Nat$0Instance.instance(10L),
base.Nat$0Instance.instance(11L),
base.Nat$0Instance.instance(12L),
base.Nat$0Instance.instance(13L),
base.Nat$0Instance.instance(14L)))).imm$y$1(
base.Nat$0Instance.instance(13L))).read$assertEq$1(
base.Nat$0Instance.instance(2L)))).imm$test$1(
((base.Nat$0)((base.Grid$0)base.Grids$0.instance.imm$$hash$1(
base.Lists$0.instance.imm$$hash$15(
base.Nat$0Instance.instance(0L),
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(2L),
base.Nat$0Instance.instance(3L),
base.Nat$0Instance.instance(4L),
base.Nat$0Instance.instance(5L),
base.Nat$0Instance.instance(6L),
base.Nat$0Instance.instance(7L),
base.Nat$0Instance.instance(8L),
base.Nat$0Instance.instance(9L),
base.Nat$0Instance.instance(10L),
base.Nat$0Instance.instance(11L),
base.Nat$0Instance.instance(12L),
base.Nat$0Instance.instance(13L),
base.Nat$0Instance.instance(14L)))).imm$x$1(
base.Nat$0Instance.instance(13L))).read$assertEq$1(
base.Nat$0Instance.instance(3L)))).imm$test$1(
((base.Bool$0)((base.OrderHash$1)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(2L),
base.Nat$0Instance.instance(3L))).mut$as$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var _bimpl$= (base.Nat$0)p0;
    return ((base.Nat$0)_bimpl$.imm$$plus$1(
base.Nat$0Instance.instance(2L))).read$str$0(
);
  }

})).read$orderHash$1(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _cimpl$= (base.Str$0)p0;
    return _cimpl$;
  }

})).read$$eq_eq$1(
((base.Flow$1)((base.Flow$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(2L),
base.Nat$0Instance.instance(3L))).read$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _dimpl$= (base.Nat$0)p0;
    return ((base.Nat$0)_dimpl$.imm$$plus$1(
base.Nat$0Instance.instance(2L))).read$str$0(
);
  }

})).mut$list$0(
))).imm$assertTrue$0(
))).imm$test$1(
((base.Bool$0)((base.OrderHash$1)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(2L),
base.Nat$0Instance.instance(3L))).mut$as$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var _eimpl$= (base.Nat$0)p0;
    return _eimpl$;
  }

})).read$orderHash$1(
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
));
  }
  TestListsBasics$0 instance= new TestListsBasics$0(){};}