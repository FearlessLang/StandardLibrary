package base;
public interface Angle$1 extends base.F$1, base.Sealed$0, base.DataType$2, base.Extensible$1, base.OrderHash$1, base.Order$1, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object imm$$plus$1(Object p0){
    throw new AssertionError("Uncallable method: Angle$1.imm$$plus$1"+this.getClass().getName());
  }
  default Object imm$$dash$1(Object p0){
    throw new AssertionError("Uncallable method: Angle$1.imm$$dash$1"+this.getClass().getName());
  }
  default Object read$normalize$0(){
    throw new AssertionError("Uncallable method: Angle$1.read$normalize$0"+this.getClass().getName());
  }
  default Object imm$sin$0(){
    throw new AssertionError("Uncallable method: Angle$1.imm$sin$0"+this.getClass().getName());
  }
  default Object imm$cos$0(){
    throw new AssertionError("Uncallable method: Angle$1.imm$cos$0"+this.getClass().getName());
  }
  default Object imm$tan$0(){
    throw new AssertionError("Uncallable method: Angle$1.imm$tan$0"+this.getClass().getName());
  }
  default Object imm$csc$0(){
    var this$= this;
    return base.Float$0Instance.instance(1.0d).imm$$slash$1(
this$.imm$sin$0(
));
  }
  default Object imm$sec$0(){
    var this$= this;
    return base.Float$0Instance.instance(1.0d).imm$$slash$1(
this$.imm$cos$0(
));
  }
  default Object imm$cot$0(){
    var this$= this;
    return base.Float$0Instance.instance(1.0d).imm$$slash$1(
this$.imm$tan$0(
));
  }
  default Object read$info$0(){
    var this$= this;
    return base.Infos$0.instance.imm$msg$1(
this$.read$str$0(
));
  }
}