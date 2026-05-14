package base;
public interface Unreachable$0{
  default Object imm$$bang$0(){
    var this$= this;
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("This code was flagged as unreachable, but it has been reached."));
  }
  default Object imm$$bang$1(Object p0){
    var this$= this;
    var msg$= (base.Str$0)p0;
    return base.Error$0.instance.imm$msg$1(
((base.Str$0)base.Str$0Instance.instance("This code was flagged as unreachable as ").imm$$plus$1(
msg$)).imm$$plus$1(
base.Str$0Instance.instance(", but it has been reached.")));
  }
  Unreachable$0 instance= new Unreachable$0(){};}