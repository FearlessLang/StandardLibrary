package _base;
import java.util.ArrayList;
import java.util.List;

public interface InMemoryLog$2kk$1 extends CaptureFree$2dk$0{
  default Object imm$name$0(){
    throw new AssertionError("Uncallable method: InMemoryLog$2kk$1.imm$name$0"+this.getClass().getName());
  }
  ArrayList<Object> _log();
  default Object imm$log$1(Object p0){
    var log= _log();
    synchronized(log){ log.add(p0); }
    return Void$o$0.instance;
  }
  default Object imm$reader$1(Object p0){
    return ((System$2o$0)p0).mut$readLog$1(this);
  }
  default Object _readCopy(){
    var log= _log();
    if (log.isEmpty()){ return List$o$1.instance; }
    synchronized(log){ return new List$o$1Instance(List.copyOf(log)); }
  }
  default Object _consumeCopy(){
    var log= _log();
    if (log.isEmpty()){ return List$o$1.instance; }
    synchronized(log){
      var res= new List$o$1Instance(List.copyOf(log));
      log.clear();
      return res;
    }
  }
}
