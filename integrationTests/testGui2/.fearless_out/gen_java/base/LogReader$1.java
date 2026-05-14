package base;
public interface LogReader$1 extends base.ToIso$1, base.WidenTo$1{
  default Object mut$read$0(){
    throw new AssertionError("Uncallable method: LogReader$1.mut$read$0"+this.getClass().getName());
  }
  default Object mut$consume$0(){
    throw new AssertionError("Uncallable method: LogReader$1.mut$consume$0"+this.getClass().getName());
  }
  default Object read$logName$0(){
    throw new AssertionError("Uncallable method: LogReader$1.read$logName$0"+this.getClass().getName());
  }
  default Object mut$clear$0(){
    var this$= this;
    return base.Block$0.instance.imm$$hash$2(
this$.mut$consume$0(
),
base.Void$0.instance);
  }
}