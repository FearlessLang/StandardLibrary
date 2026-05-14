package base;
public interface ExtensibleReenterFlow$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return _aimpl$.imm$test$1(
((base.Int$0)((base.List$1)((base.Flow$1)((base.Flow$1)((base.Int$0)((base.List$1)((base.Flow$1)((base.Flow$1)((base.List$1)base.Lists$0.instance.imm$$hash$2(
base.Int$0Instance.instance(1L),
base.Int$0Instance.instance(2L))).read$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var x$= (base.Int$0)p0;
    return x$.imm$$star$1(
base.Int$0Instance.instance(2L));
  }

})).mut$list$0(
)).mut$get$1(
base.Nat$0Instance.instance(0L))).imm$$tilde$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var n$= (base.Int$0)p0;
    return base.Flows$0.instance.imm$$hash$2(
n$,
n$.imm$$plus$1(
base.Int$0Instance.instance(1L)));
  }

})).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var x$= (base.Int$0)p0;
    return x$.imm$$plus$1(
base.Int$0Instance.instance(1L));
  }

})).mut$list$0(
)).mut$get$1(
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Int$0Instance.instance(4L)));
  }
  ExtensibleReenterFlow$0 instance= new ExtensibleReenterFlow$0(){};}