package base;
public interface ListBasics$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Nat$0)base.List$1.instance.read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(0L)))).imm$test$1(
((base.Bool$0)base.List$1.instance.read$isEmpty$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.OrderHash$1)base.List$1.instance.read$orderHash$1(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.Int$0)p0;
    return _bimpl$;
  }

})).read$$eq_eq$1(
base.List$1.instance)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Str$0)base.List$1.instance.read$str$1(
new base.ToStrBy$1(){
    public Object imm$$hash$1(Object p0){
    var _cimpl$= (base.Str$0)p0;
    return _cimpl$.read$str$0(
);
  }

})).read$assertEq$1(
base.Str$0Instance.instance("[]")))).imm$test$1(
((base.Str$0)base.List$1.instance.read$str$1(
new base.ToStrBy$1(){
    public Object imm$$hash$1(Object p0){
    var _dimpl$= (base.List$1)p0;
    return _dimpl$.read$str$1(
new base.ToStrBy$1(){
    public Object imm$$hash$1(Object p0){
    var _eimpl$= (base.Str$0)p0;
    return _eimpl$.read$str$0(
);
  }

});
  }

})).read$assertEq$1(
base.Str$0Instance.instance("[]")))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.List$1.instance.imm$opt$1(
base.Nat$0Instance.instance(0L))).read$isEmpty$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.List$1.instance.imm$firstOpt$0(
)).read$isEmpty$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.List$1.instance.imm$lastOpt$0(
)).read$isEmpty$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Nat$0)((base.List$1)base.List$1.instance.read$reverse$0(
)).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(0L)))).imm$test$1(
((base.Nat$0)((base.List$1)base.List$1.instance.read$subList$1(
base.Nat$0Instance.instance(0L))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(0L)))).imm$test$1(
((base.Nat$0)((base.List$1)base.List$1.instance.read$$plus_plus$1(
base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("a")))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(1L)))).imm$test$1(
((base.Str$0)((base.List$1)base.List$1.instance.read$$plus_plus$1(
base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("a")))).read$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Int$0)((base.List$1)base.List$1.instance.read$$plus_plus$1(
base.Lists$0.instance.imm$$hash$1(
base.Int$0Instance.instance(1L)))).read$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Int$0Instance.instance(1L)))).imm$test$1(
((base.Nat$0)((base.List$1)base.List$1.instance.read$$lt_plus$1(
base.Str$0Instance.instance("a"))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(1L)))).imm$test$1(
((base.Str$0)((base.List$1)base.List$1.instance.read$$lt_plus$1(
base.Str$0Instance.instance("a"))).read$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Nat$0)((base.List$1)base.List$1.instance.read$$plus_gt$1(
base.Str$0Instance.instance("a"))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(1L)))).imm$test$1(
((base.Str$0)((base.List$1)base.List$1.instance.read$$plus_gt$1(
base.Str$0Instance.instance("a"))).read$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Nat$0)((base.List$1)base.List$1.instance.read$withAlso$2(
base.Nat$0Instance.instance(0L),
base.Str$0Instance.instance("a"))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(1L)))).imm$test$1(
((base.Str$0)((base.List$1)base.List$1.instance.read$withAlso$2(
base.Nat$0Instance.instance(0L),
base.Str$0Instance.instance("a"))).read$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Str$0)((base.List$1)base.List$1.instance.read$withAlso$2(
base.Nat$0Instance.instance(0L),
base.Str$0Instance.instance("a"))).read$str$1(
new base.ToStrBy$1(){
    public Object imm$$hash$1(Object p0){
    var _fimpl$= (base.Str$0)p0;
    return _fimpl$.read$str$0(
);
  }

})).read$assertEq$1(
((base.List$1)base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("a"))).read$str$1(
new base.ToStrBy$1(){
    public Object imm$$hash$1(Object p0){
    var _gimpl$= (base.Str$0)p0;
    return _gimpl$.read$str$0(
);
  }

})))).imm$test$1(
((base.Nat$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(2L))).mut$$plus_plus$1(
base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(3L),
base.Int$0Instance.instance(4L)))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(4L)))).imm$test$1(
((base.Int$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(2L))).mut$$plus_plus$1(
base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(3L),
base.Int$0Instance.instance(4L)))).mut$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Int$0Instance.instance(1L)))).imm$test$1(
((base.Int$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(2L))).mut$$plus_plus$1(
base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(3L),
base.Int$0Instance.instance(4L)))).mut$get$1(
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Int$0Instance.instance(2L)))).imm$test$1(
((base.Int$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(2L))).mut$$plus_plus$1(
base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(3L),
base.Int$0Instance.instance(4L)))).mut$get$1(
base.Nat$0Instance.instance(2L))).read$assertEq$1(
base.Int$0Instance.instance(3L)))).imm$test$1(
((base.Int$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(2L))).mut$$plus_plus$1(
base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(3L),
base.Int$0Instance.instance(4L)))).mut$get$1(
base.Nat$0Instance.instance(3L))).read$assertEq$1(
base.Int$0Instance.instance(4L)))).imm$test$1(
((base.Nat$0)((base.List$1)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$1(
base.Int$0Instance.instance(1L))).mut$$plus_plus$1(
base.Lists$0.instance.imm$$hash$1(
base.Int$0Instance.instance(2L)))).mut$$plus_plus$1(
base.Lists$0.instance.imm$$hash$1(
base.Int$0Instance.instance(3L)))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(3L)))).imm$test$1(
((base.Int$0)((base.List$1)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$1(
base.Int$0Instance.instance(1L))).mut$$plus_plus$1(
base.Lists$0.instance.imm$$hash$1(
base.Int$0Instance.instance(2L)))).mut$$plus_plus$1(
base.Lists$0.instance.imm$$hash$1(
base.Int$0Instance.instance(3L)))).mut$get$1(
base.Nat$0Instance.instance(2L))).read$assertEq$1(
base.Int$0Instance.instance(3L)))).imm$test$1(
((base.Bool$0)((base.Opt$1)((base.List$1)base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("a"))).mut$opt$1(
base.Nat$0Instance.instance(0L))).read$isEmpty$0(
)).read$assertEq$1(
base.False$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)((base.List$1)base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("a"))).mut$opt$1(
base.Nat$0Instance.instance(1L))).read$isEmpty$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"))).mut$firstOpt$0(
)).read$isEmpty$0(
)).read$assertEq$1(
base.False$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"))).mut$lastOpt$0(
)).read$isEmpty$0(
)).read$assertEq$1(
base.False$0.instance))).imm$test$1(
((base.Nat$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$subList$2(
base.Nat$0Instance.instance(0L),
base.Nat$0Instance.instance(0L))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(0L)))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$subList$2(
base.Nat$0Instance.instance(0L),
base.Nat$0Instance.instance(1L))).mut$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Nat$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$subList$2(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(3L))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(2L)))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$subList$2(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(3L))).mut$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("b")))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$subList$2(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(3L))).mut$get$1(
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Str$0Instance.instance("c")))).imm$test$1(
((base.Nat$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$subList$1(
base.Nat$0Instance.instance(3L))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(0L)))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$reverse$0(
)).mut$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("c")))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$reverse$0(
)).mut$get$1(
base.Nat$0Instance.instance(2L))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Int$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(2L),
base.Int$0Instance.instance(3L))).mut$reverse$0(
)).mut$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Int$0Instance.instance(3L)))).imm$test$1(
((base.Bool$0)((base.OrderHash$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).read$orderHash$1(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _himpl$= (base.Str$0)p0;
    return _himpl$;
  }

})).read$$eq_eq$1(
base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c")))).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.OrderHash$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).read$orderHash$1(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _jimpl$= (base.Str$0)p0;
    return _jimpl$;
  }

})).read$$eq_eq$1(
((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$reverse$0(
)).mut$reverse$0(
))).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"))).mut$with$2(
base.Nat$0Instance.instance(0L),
base.Str$0Instance.instance("x"))).mut$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("x")))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"))).mut$with$2(
base.Nat$0Instance.instance(1L),
base.Str$0Instance.instance("x"))).mut$get$1(
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Str$0Instance.instance("x")))).imm$test$1(
((base.Nat$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("c"))).mut$withAlso$2(
base.Nat$0Instance.instance(1L),
base.Str$0Instance.instance("b"))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(3L)))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("c"))).mut$withAlso$2(
base.Nat$0Instance.instance(1L),
base.Str$0Instance.instance("b"))).mut$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("c"))).mut$withAlso$2(
base.Nat$0Instance.instance(1L),
base.Str$0Instance.instance("b"))).mut$get$1(
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Str$0Instance.instance("b")))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("c"))).mut$withAlso$2(
base.Nat$0Instance.instance(1L),
base.Str$0Instance.instance("b"))).mut$get$1(
base.Nat$0Instance.instance(2L))).read$assertEq$1(
base.Str$0Instance.instance("c")))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$without$1(
base.Nat$0Instance.instance(0L))).mut$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("b")))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$without$1(
base.Nat$0Instance.instance(2L))).mut$get$1(
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Str$0Instance.instance("b")))).imm$test$1(
((base.Nat$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$without$1(
base.Nat$0Instance.instance(1L))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(2L)))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$without$1(
base.Nat$0Instance.instance(1L))).mut$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$3(
base.Str$0Instance.instance("a"),
base.Str$0Instance.instance("b"),
base.Str$0Instance.instance("c"))).mut$without$1(
base.Nat$0Instance.instance(1L))).mut$get$1(
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Str$0Instance.instance("c")))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)base.List$1.instance.read$withAlso$2(
base.Nat$0Instance.instance(0L),
base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("a")))).read$get$1(
base.Nat$0Instance.instance(0L))).read$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Nat$0)((base.List$1)((base.List$1)base.List$1.instance.read$withAlso$2(
base.Nat$0Instance.instance(0L),
base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(2L)))).read$get$1(
base.Nat$0Instance.instance(0L))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(2L)))).imm$test$1(
((base.Int$0)((base.List$1)((base.List$1)base.List$1.instance.read$withAlso$2(
base.Nat$0Instance.instance(0L),
base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(2L)))).read$get$1(
base.Nat$0Instance.instance(0L))).read$get$1(
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Int$0Instance.instance(2L)))).imm$test$1(
((base.Nat$0)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$1(
base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("a")))).mut$$plus_plus$1(
base.Lists$0.instance.imm$$hash$1(
base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("b"))))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(2L)))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$1(
base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("a")))).mut$$plus_plus$1(
base.Lists$0.instance.imm$$hash$1(
base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("b"))))).mut$get$1(
base.Nat$0Instance.instance(0L))).mut$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("a")))).imm$test$1(
((base.Str$0)((base.List$1)((base.List$1)((base.List$1)base.Lists$0.instance.imm$$hash$1(
base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("a")))).mut$$plus_plus$1(
base.Lists$0.instance.imm$$hash$1(
base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("b"))))).mut$get$1(
base.Nat$0Instance.instance(1L))).mut$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("b")))).imm$test$1(
((base.Str$0)((base.List$1)base.List$1.instance.read$withAlso$2(
base.Nat$0Instance.instance(0L),
base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("a")))).read$str$1(
new base.ToStrBy$1(){
    public Object imm$$hash$1(Object p0){
    var _kimpl$= (base.List$1)p0;
    return _kimpl$.read$str$1(
new base.ToStrBy$1(){
    public Object imm$$hash$1(Object p0){
    var _mimpl$= (base.Str$0)p0;
    return _mimpl$.read$str$0(
);
  }

});
  }

})).read$assertEq$1(
((base.List$1)base.Lists$0.instance.imm$$hash$1(
base.Lists$0.instance.imm$$hash$1(
base.Str$0Instance.instance("a")))).read$str$1(
new base.ToStrBy$1(){
    public Object imm$$hash$1(Object p0){
    var _nimpl$= (base.List$1)p0;
    return _nimpl$.read$str$1(
new base.ToStrBy$1(){
    public Object imm$$hash$1(Object p0){
    var _pimpl$= (base.Str$0)p0;
    return _pimpl$.read$str$0(
);
  }

});
  }

})));
  }
  ListBasics$0 instance= new ListBasics$0(){};}