package base;
import static base.Util.*;
public interface Debug$0 extends Sealed$0{
  default Object imm$$hash$1(Object p0){
    System.out.print(toS(p0)+"\n"); //Crucially the above does not use println since that makes \r\n on win
    return Void$0.instance;//Another version with different compile options could use .err
  }
  Debug$0 instance= new Debug$0(){};
}