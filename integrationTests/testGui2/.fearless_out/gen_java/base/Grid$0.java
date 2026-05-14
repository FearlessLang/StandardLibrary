package base;
public interface Grid$0{
  default Object imm$inner$0(){
    throw new AssertionError("Uncallable method: Grid$0.imm$inner$0"+this.getClass().getName());
  }
  default Object imm$get$2(Object p0, Object p1){
    var this$= this;
    var x$= (base.Nat$0)p0;
    var y$= (base.Nat$0)p1;
    return ((base.List$1)this$.imm$inner$0(
)).read$get$1(
((base.Nat$0)y$.imm$$star$1(
base.Nat$0Instance.instance(5L))).imm$$plus$1(
x$));
  }
  default Object imm$y$1(Object p0){
    var this$= this;
    var index$= (base.Nat$0)p0;
    return index$.imm$div$1(
base.Nat$0Instance.instance(5L));
  }
  default Object imm$x$1(Object p0){
    var this$= this;
    var index$= (base.Nat$0)p0;
    return index$.imm$rem$1(
base.Nat$0Instance.instance(5L));
  }
}