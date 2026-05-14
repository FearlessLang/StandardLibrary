package base;
public interface _ActorSinks$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var sink$= (base._Sink$1)p0;
    return new ActorSink$1(){
    public Object mut$$hash$1(Object p0){
    var x$= p0;
    return sink$.mut$$hash$1(
x$);
  }

    public Object mut$pushError$1(Object p0){
    var info$= (base.Info$0)p0;
    return sink$.mut$pushError$1(
info$);
  }

};
  }
  _ActorSinks$0 instance= new _ActorSinks$0(){};}