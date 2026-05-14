package base;
public interface FloatMatches$1{
  default Object imm$$hash$2(Object p0, Object p1){
    var this$= this;
    var number$= (base.MF$2)p0;
    var nan$= (base.MF$1)p1;
    return new base.FloatMatch$1(){
    public Object mut$regular$1(Object p0){
    var x$= (base.Float$0)p0;
    return number$.mut$$hash$1(
x$);
  }

    public Object mut$posZero$0(){
    return number$.mut$$hash$1(
base.Float$0Instance.instance(0.0d));
  }

    public Object mut$negZero$0(){
    return number$.mut$$hash$1(
base.Float$0Instance.instance(-0.0d));
  }

    public Object mut$posInf$0(){
    return number$.mut$$hash$1(
base.Math$0.instance.imm$inf$0(
));
  }

    public Object mut$negInf$0(){
    return number$.mut$$hash$1(
base.Math$0.instance.imm$negInf$0(
));
  }

    public Object mut$nan$0(){
    return nan$.mut$$hash$0(
);
  }

};
  }
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    var this$= this;
    var finite$= (base.MF$2)p0;
    var nan$= (base.MF$1)p1;
    var infs$= (base.MF$2)p2;
    return new base.FloatMatch$1(){
    public Object mut$regular$1(Object p0){
    var x$= (base.Float$0)p0;
    return finite$.mut$$hash$1(
x$);
  }

    public Object mut$posZero$0(){
    return finite$.mut$$hash$1(
base.Float$0Instance.instance(0.0d));
  }

    public Object mut$negZero$0(){
    return finite$.mut$$hash$1(
base.Float$0Instance.instance(-0.0d));
  }

    public Object mut$posInf$0(){
    return infs$.mut$$hash$1(
base.Math$0.instance.imm$inf$0(
));
  }

    public Object mut$negInf$0(){
    return infs$.mut$$hash$1(
base.Math$0.instance.imm$negInf$0(
));
  }

    public Object mut$nan$0(){
    return nan$.mut$$hash$0(
);
  }

};
  }
  default Object imm$$hash$4(Object p0, Object p1, Object p2, Object p3){
    var this$= this;
    var regular$= (base.MF$2)p0;
    var nan$= (base.MF$1)p1;
    var infs$= (base.MF$2)p2;
    var zeros$= (base.MF$2)p3;
    return new base.FloatMatch$1(){
    public Object mut$regular$1(Object p0){
    var x$= (base.Float$0)p0;
    return regular$.mut$$hash$1(
x$);
  }

    public Object mut$posZero$0(){
    return zeros$.mut$$hash$1(
base.Float$0Instance.instance(0.0d));
  }

    public Object mut$negZero$0(){
    return zeros$.mut$$hash$1(
base.Float$0Instance.instance(-0.0d));
  }

    public Object mut$posInf$0(){
    return infs$.mut$$hash$1(
base.Math$0.instance.imm$inf$0(
));
  }

    public Object mut$negInf$0(){
    return infs$.mut$$hash$1(
base.Math$0.instance.imm$negInf$0(
));
  }

    public Object mut$nan$0(){
    return nan$.mut$$hash$0(
);
  }

};
  }
  FloatMatches$1 instance= new FloatMatches$1(){};}