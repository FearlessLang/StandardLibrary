package _base;
public interface _Magic$1c$0 extends Sealed$2o$0{
  default Object imm$$bang$0(){
    throw new Error("Magic! invocation");
  }
  default Object imm$$bang$1(Object p0){
    var this$= this;
    return this$.imm$$bang$0();
  }
  _Magic$1c$0 instance= new _Magic$1c$0(){};}