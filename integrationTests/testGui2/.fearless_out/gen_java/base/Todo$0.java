package base;
public interface Todo$0{
  default Object imm$$bang$0(){
    var this$= this;
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("TODO: Implement this code."));
  }
  default Object imm$$bang$1(Object p0){
    var this$= this;
    var msg$= (base.Str$0)p0;
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("TODO: ").imm$$plus$1(
msg$));
  }
  Todo$0 instance= new Todo$0(){};}