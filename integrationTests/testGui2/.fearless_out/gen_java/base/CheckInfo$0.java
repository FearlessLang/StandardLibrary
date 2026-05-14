package base;
public interface CheckInfo$0{
  default Object imm$check$0(){
    throw new AssertionError("Uncallable method: CheckInfo$0.imm$check$0"+this.getClass().getName());
  }
  default Object imm$stackTrace$0(){
    throw new AssertionError("Uncallable method: CheckInfo$0.imm$stackTrace$0"+this.getClass().getName());
  }
}