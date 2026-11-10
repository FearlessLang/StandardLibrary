package _base;

import static _base.Util.*;
public interface UnitTestLog$2f8$0{
  UnitTestLog$2f8$0 instance= new UnitTestLog$2f8$0(){};
  AppLog mainLog= AppLog.open(java.nio.file.Path.of(".out", "logs", "_base", "unit_test_log.log"), true);
  default Object imm$log$1(Object str){ mainLog.append(toS(str)); return Void$o$0.instance; }
  default Object imm$plan$2(Object kind, Object frame){
    var f= (StackFrame$174$0)frame;
    var type= toS(f.imm$typeName$0());
    var line= toS(f.imm$lineNumber$0());
    mainLog.append(String.join("\t", "PLAN", toS(kind), toS(f.imm$fileName$0()), type, type+" at line: "+line, line)+"\n");
    return Void$o$0.instance;
  }
}