package base;
import java.util.ArrayList;
import java.util.IdentityHashMap;

public interface InMemoryLog$2kk$1 extends CaptureFree$2dk$0{
  default Object imm$name$0(){
    throw new AssertionError("Uncallable method: InMemoryLog$2kk$1.imm$name$0"+this.getClass().getName());
  }
  default Object imm$log$1(Object p0){
    var log= _InMemoryLogs.of(this);
    synchronized(log){ log.add(p0); }
    return Void$o$0.instance;
  }
  default Object imm$reader$1(Object p0){
    return ((System$2o$0)p0).mut$readLog$1(this);
  }
}
class _InMemoryLogs{
  private static final IdentityHashMap<Object,ArrayList<Object>> all= new IdentityHashMap<>();
  static synchronized ArrayList<Object> of(Object log){ return all.computeIfAbsent(log, _->new ArrayList<>()); }
}
