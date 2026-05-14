package base;
public interface Error$0{
  default Object imm$$bang$1(Object p0){
    var this$= this;
    var info$= (base.Info$0)p0;
    return base._Throw$0.instance.imm$deterministic$1(
info$);
  }
  default Object imm$msg$1(Object p0){
    var this$= this;
    var msg$= (base.Str$0)p0;
    return base._Throw$0.instance.imm$deterministic$1(
base.Infos$0.instance.imm$msg$1(
msg$));
  }
  default Object imm$nonDeterministic$1(Object p0){
    var this$= this;
    var msg$= (base.Str$0)p0;
    return base._Throw$0.instance.imm$nonDeterministic$1(
base.Infos$0.instance.imm$msg$1(
msg$));
  }
  default Object imm$nonDeterministicInfo$1(Object p0){
    var this$= this;
    var i$= (base.Info$0)p0;
    return base._Throw$0.instance.imm$nonDeterministic$1(
i$);
  }
  Error$0 instance= new Error$0(){};}