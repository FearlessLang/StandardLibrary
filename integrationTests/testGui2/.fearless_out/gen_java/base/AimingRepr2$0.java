package base;
public interface AimingRepr2$0 extends base.DirectionMatch$1{
  default Object mut$centre$0(){
    throw new AssertionError("Uncallable method: AimingRepr2$0.mut$centre$0"+this.getClass().getName());
  }
  default Object mut$north$0(){
    var this$= this;
    return ((base.Str$0)base.Str$0Instance.instance(" | ").imm$$plus$1(
this$.mut$centre$0(
))).imm$$plus$1(
base.Str$0Instance.instance(" | "));
  }
  default Object mut$east$0(){
    var this$= this;
    return ((base.Str$0)base.Str$0Instance.instance(" - ").imm$$plus$1(
this$.mut$centre$0(
))).imm$$plus$1(
base.Str$0Instance.instance(" | "));
  }
  default Object mut$south$0(){
    var this$= this;
    return ((base.Str$0)base.Str$0Instance.instance(" | ").imm$$plus$1(
this$.mut$centre$0(
))).imm$$plus$1(
base.Str$0Instance.instance(" | "));
  }
  default Object mut$west$0(){
    var this$= this;
    return ((base.Str$0)base.Str$0Instance.instance(" | ").imm$$plus$1(
this$.mut$centre$0(
))).imm$$plus$1(
base.Str$0Instance.instance(" - "));
  }
}