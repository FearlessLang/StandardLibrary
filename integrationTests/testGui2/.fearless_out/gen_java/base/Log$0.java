package base;

import static base.Util.*;
public interface Log$0{
  Log$0 instance= new Log$0(){};
  AppLog mainLog= AppLog.open(java.nio.file.Path.of(".out","logs","_base","log.log"),false);
  default Object imm$log$1(Object str){ mainLog.append(toS(str)); return Void$0.instance; }
}