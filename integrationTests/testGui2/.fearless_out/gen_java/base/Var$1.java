package base;
public interface Var$1{
  default Object read$get$0(){
    throw new AssertionError("Uncallable method: Var$1.read$get$0"+this.getClass().getName());
  }
  default Object mut$get$0(){
    throw new AssertionError("Uncallable method: Var$1.mut$get$0"+this.getClass().getName());
  }
  default Object mut$swap$1(Object p0){
    throw new AssertionError("Uncallable method: Var$1.mut$swap$1"+this.getClass().getName());
  }
  default Object mut$set$1(Object p0){
    var this$= this;
    var v$= p0;
    return base.Block$0.instance.imm$$hash$2(
this$.mut$swap$1(
v$),
base.Void$0.instance);
  }
}