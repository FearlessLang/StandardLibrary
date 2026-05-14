package base;
public interface MatchTests$0 extends base.F$2{
  default Object read$matcher$0(){
    var this$= this;
    return new base.FloatMatch$1(){
    public Object mut$regular$1(Object p0){
    var x$= (base.Float$0)p0;
    return base.Nat$0Instance.instance(1L);
  }

    public Object mut$posZero$0(){
    return base.Nat$0Instance.instance(2L);
  }

    public Object mut$negZero$0(){
    return base.Nat$0Instance.instance(3L);
  }

    public Object mut$posInf$0(){
    return base.Nat$0Instance.instance(4L);
  }

    public Object mut$negInf$0(){
    return base.Nat$0Instance.instance(5L);
  }

    public Object mut$nan$0(){
    return base.Nat$0Instance.instance(6L);
  }

};
  }
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Nat$0)base.Math$0.instance.imm$match$2(
base.Float$0Instance.instance(5.0d),
this$.read$matcher$0(
))).read$assertEq$1(
base.Nat$0Instance.instance(1L)))).imm$test$1(
((base.Nat$0)base.Math$0.instance.imm$match$2(
base.Float$0Instance.instance(0.0d),
this$.read$matcher$0(
))).read$assertEq$1(
base.Nat$0Instance.instance(2L)))).imm$test$1(
((base.Nat$0)base.Math$0.instance.imm$match$2(
base.Float$0Instance.instance(-0.0d),
this$.read$matcher$0(
))).read$assertEq$1(
base.Nat$0Instance.instance(3L)))).imm$test$1(
((base.Nat$0)base.Math$0.instance.imm$match$2(
base.Math$0.instance.imm$inf$0(
),
this$.read$matcher$0(
))).read$assertEq$1(
base.Nat$0Instance.instance(4L)))).imm$test$1(
((base.Nat$0)base.Math$0.instance.imm$match$2(
base.Math$0.instance.imm$negInf$0(
),
this$.read$matcher$0(
))).read$assertEq$1(
base.Nat$0Instance.instance(5L)))).imm$test$1(
((base.Nat$0)base.Math$0.instance.imm$match$2(
base.Math$0.instance.imm$nan$0(
),
this$.read$matcher$0(
))).read$assertEq$1(
base.Nat$0Instance.instance(6L)));
  }
  MatchTests$0 instance= new MatchTests$0(){};}