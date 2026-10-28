package _base;

import static _base.Util.*;

public interface FileLog$5g$0 extends CaptureFree$2dk$0{
  default Object imm$name$0(){
    throw new AssertionError("Uncallable method: FileLog$5g$0.imm$name$0 on "+this.getClass().getName());
  }
  AppLog _log();
  default Object imm$log$1(Object p0){
    _log().append(toS(p0));
    return Void$o$0.instance;
  }
}
