package base;
public interface InMemoryLog$1 extends base.CaptureFree$0{
  default Object imm$name$0(){
    throw new AssertionError("Uncallable method: InMemoryLog$1.imm$name$0"+this.getClass().getName());
  }
  default Object imm$log$1(Object p0){
    var this$= this;
    var s$= p0;
    return base.Magic$0.instance.imm$$bang$0(
);
  }
  default Object imm$reader$1(Object p0){
    var this$= this;
    var sys$= (base.System$0)p0;
    return sys$.mut$readLog$1(
this$);
  }
}