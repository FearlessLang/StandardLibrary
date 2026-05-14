package base;
public interface _MapTrue$1 extends base.MF$3{
  default Object mut$$hash$2(Object p0, Object p1){
    var this$= this;
    var b$= (base.Bool$0)p0;
    var f$= (base.MF$1)p1;
    return b$.imm$if$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return base.Opts$0.instance.imm$$hash$1(
f$.mut$$hash$0(
));
  }

    public Object mut$else$0(){
    return base.Opt$1.instance;
  }

});
  }
  _MapTrue$1 instance= new _MapTrue$1(){};}