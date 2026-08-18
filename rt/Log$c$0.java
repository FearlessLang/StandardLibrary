package base;

import static base.Util.*;
public interface Log$c$0{
  Log$c$0 instance= new Log$c$0(){};
  AppLog mainLog= AppLog.open(java.nio.file.Path.of(".out","logs","_base","log.log"),false);
  default Object imm$log$1(Object str){ mainLog.append(toS(str)); return Void$o$0.instance; }
}