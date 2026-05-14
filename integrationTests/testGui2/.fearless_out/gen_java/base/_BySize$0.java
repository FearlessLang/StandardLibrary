package base;
public interface _BySize$0 extends base.OrderHashBy$2, base.OrderBy$2, base.ToStrBy$1{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var s$= (base.Str$0)p0;
    return ((base.Str$0)s$.read$imm$0(
)).imm$size$0(
);
  }
  _BySize$0 instance= new _BySize$0(){};}