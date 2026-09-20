package base;

public interface Yield$1c$0 extends Sealed$2o$0{
  default Object imm$$hash$0(){ DataParallel.poll(); Thread.yield(); return Void$o$0.instance; }
  Yield$1c$0 instance= new Yield$1c$0(){};
}
