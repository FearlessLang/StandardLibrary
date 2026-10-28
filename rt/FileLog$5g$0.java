package _base;

import static _base.Util.*;

public interface FileLog$5g$0 extends CaptureFree$2dk$0, Named$1c$0{
  AppLog _log();
  default Object imm$log$1(Object p0){
    _log().append(toS(p0));
    return Void$o$0.instance;
  }
}
