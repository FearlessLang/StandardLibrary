package base;
public interface Actions$0{
  default Object imm$ok$1(Object p0){
    var this$= this;
    var x$= p0;
    return new base.Action$1(){
    public Object mut$run$1(Object p0){
    var _aimpl$= (base.ActionMatch$2)p0;
    return _aimpl$.mut$ok$1(
x$);
  }

};
  }
  default Object imm$info$1(Object p0){
    var this$= this;
    var info$= (base.Info$0)p0;
    return new base.Action$1(){
    public Object mut$run$1(Object p0){
    var _bimpl$= (base.ActionMatch$2)p0;
    return _bimpl$.mut$info$1(
info$);
  }

};
  }
  Actions$0 instance= new Actions$0(){};}