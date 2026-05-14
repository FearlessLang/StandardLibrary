package base;
public interface Abort$0 extends base.Sealed$0{
  default Object imm$$bang$0(){
    var this$= this;
    return base._Throw$0.instance.imm$nonDeterministic$1(
base.Infos$0.instance.imm$msg$1(
base.Str$0Instance.instance("Abort reached")));
  }
  Abort$0 instance= new Abort$0(){};}