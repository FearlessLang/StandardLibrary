package base;
public interface Debug$0 extends Sealed$0{
  default Object imm$$hash$2(Object p0, Object p1){
    var x= p0;
    var f= (F$2)p1;
    return Block$0.instance.imm$$hash$2(this.imm$$hash$1(f.read$$hash$1(x)), x);
  }
  default Object imm$$hash$1(Object p0){
    var x= (ToStr$0)p0;
    //return Magic$0.instance.imm$$bang$0();
    System.out.print(((Str$0Instance)x.read$str$0()).val()+"\n");
    //Crucially the above does not use println since that makes \r\n on win
    return Void$0.instance;
  }
  default Object imm$identify$1(Object p0){
    var x= p0;
    return Magic$0.instance.imm$$bang$0();
  }
  Debug$0 instance= new Debug$0(){};}