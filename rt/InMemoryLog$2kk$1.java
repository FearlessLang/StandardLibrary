package _base;
import java.util.ArrayList;
import java.util.List;

public interface InMemoryLog$2kk$1 extends CaptureFree$2dk$0{
  default Object imm$name$0(){
    throw new AssertionError("Uncallable method: InMemoryLog$2kk$1.imm$name$0 on "+this.getClass().getName());
  }
  ArrayList<Object> _log();
  default Object imm$log$1(Object p0){
    var log= _log();
    synchronized (log){ log.add(p0); }
    return Void$o$0.instance;
  }
  default Object _readCopy(){
    var log= _log();
    synchronized (log){ return log.isEmpty() ? List$o$1.instance : new List$o$1Instance(List.copyOf(log)); }
  }
  default Object _consumeCopy(){
    var log= _log();
    synchronized (log){
      if (log.isEmpty()){ return List$o$1.instance; }
      var res= new List$o$1Instance(List.copyOf(log));
      log.clear();
      return res;
    }
  }
}
