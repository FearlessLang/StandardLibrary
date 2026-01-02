package base;
public interface _Throw$0{
  default Object imm$deterministic$1(Object p0){
    var this$= this;
    var info$= (Info$0)p0;
    throw new RuntimeException(info$.read$str$0().toString());
  }
  default Object imm$nonDeterministic$1(Object p0){
    var this$= this;
    var info$= (Info$0)p0;
    throw new Error(info$.read$str$0().toString());
  }
  default Object imm$stackTrace$0(){
    var this$= this;
    return Infos$0.instance.imm$msg$1(new Str$0Instance("TODO: as a list"));
  }
  _Throw$0 instance= new _Throw$0(){};}