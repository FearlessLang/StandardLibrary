package base;

import static base.Util.*;
public interface UnitTestLog$2f8$0{
  UnitTestLog$2f8$0 instance= new UnitTestLog$2f8$0(){};
  AppLog mainLog= AppLog.open(java.nio.file.Path.of(".out","logs","_base","unit_test_log.log"),true);
  default Object imm$log$1(Object str){ mainLog.append(toS(str)); return Void$o$0.instance; }
}