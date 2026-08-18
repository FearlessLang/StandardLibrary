package base;
public interface Magic$1c$0 extends Sealed$2o$0{
  default Object imm$$bang$0(){
    throw new Error("Magic! invocation");
  }
  default Object imm$$bang$1(Object p0){
    var this$= this;
    return this$.imm$$bang$0();
  }
  Magic$1c$0 instance= new Magic$1c$0(){};}