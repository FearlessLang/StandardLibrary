package base;
public interface _MagicDebug$0 extends Sealed$0{
  default Object imm$$hash$1(Object p0){
    var x= (ToStr$0)p0;
    //return Magic$0.instance.imm$$bang$0();
    System.out.println(((Str$0Instance)x.read$str$0()).val());
    return Void$0.instance;
  }

  _MagicDebug$0 instance= new _MagicDebug$0(){};
}