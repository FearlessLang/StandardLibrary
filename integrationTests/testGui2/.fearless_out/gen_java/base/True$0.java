package base;
public interface True$0 extends base.Bool$0, base.DataType$2, base.Extensible$1, base.OrderHash$1, base.Order$1, base.Sealed$0, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object imm$and$1(Object p0){
    var this$= this;
    var b$= (base.Bool$0)p0;
    return b$;
  }
  default Object imm$$and_and$1(Object p0){
    var this$= this;
    var b$= (base.MF$1)p0;
    return b$.mut$$hash$0(
);
  }
  default Object imm$or$1(Object p0){
    var this$= this;
    var b$= (base.Bool$0)p0;
    return this$;
  }
  default Object imm$$or_or$1(Object p0){
    var this$= this;
    var b$= (base.MF$1)p0;
    return this$;
  }
  default Object imm$not$0(){
    var this$= this;
    return base.False$0.instance;
  }
  default Object imm$if$1(Object p0){
    var this$= this;
    var f$= (base.ThenElse$1)p0;
    return f$.mut$then$0(
);
  }
  default Object read$imm$0(){
    var this$= this;
    return base.True$0.instance;
  }
  True$0 instance= new True$0(){};}