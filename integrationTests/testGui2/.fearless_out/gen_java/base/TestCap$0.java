package base;
public interface TestCap$0 extends base.Sealed$0{
  default Object mut$let$2(Object p0, Object p1){
    var this$= this;
    var f$= (base.F$1)p0;
    var c$= (base.TestCont$1)p1;
    return c$.imm$$hash$2(
f$.read$$hash$0(
),
this$);
  }
  default Object mut$test$1(Object p0){
    var this$= this;
    var t$= (base.Test$0)p0;
    return t$.imm$tests$1(
this$);
  }
  default Object mut$disabledTest$1(Object p0){
    throw new AssertionError("Uncallable method: TestCap$0.mut$disabledTest$1"+this.getClass().getName());
  }
  default Object mut$check$1(Object p0){
    throw new AssertionError("Uncallable method: TestCap$0.mut$check$1"+this.getClass().getName());
  }
  default Object mut$disabledCheck$1(Object p0){
    throw new AssertionError("Uncallable method: TestCap$0.mut$disabledCheck$1"+this.getClass().getName());
  }
  default Object mut$checkWithSys$1(Object p0){
    throw new AssertionError("Uncallable method: TestCap$0.mut$checkWithSys$1"+this.getClass().getName());
  }
  default Object mut$disabledCheckWithSys$1(Object p0){
    throw new AssertionError("Uncallable method: TestCap$0.mut$disabledCheckWithSys$1"+this.getClass().getName());
  }
}