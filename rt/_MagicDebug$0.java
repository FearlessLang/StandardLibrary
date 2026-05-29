package base;
import static base.Util.*;
public interface _MagicDebug$0 extends Sealed$0{
  default Object imm$$hash$1(Object p0){
    System.out.print(toS(p0)+"\n"); //Crucially the above does not use println since that makes \r\n on win
    return Void$0.instance;//Another version with different compile options could use .err
  }
  _MagicDebug$0 instance= new _MagicDebug$0(){};
}