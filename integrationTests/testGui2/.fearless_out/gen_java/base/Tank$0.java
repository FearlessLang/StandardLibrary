package base;
public interface Tank$0 extends base.ToStr$0{
  default Object imm$heading$0(){
    throw new AssertionError("Uncallable method: Tank$0.imm$heading$0"+this.getClass().getName());
  }
  default Object imm$aiming$0(){
    throw new AssertionError("Uncallable method: Tank$0.imm$aiming$0"+this.getClass().getName());
  }
  default Object imm$position$0(){
    throw new AssertionError("Uncallable method: Tank$0.imm$position$0"+this.getClass().getName());
  }
  default Object imm$move$0(){
    var this$= this;
    return base.Tanks$0.instance.imm$$hash$3(
this$.imm$heading$0(
),
this$.imm$aiming$0(
),
((base.Point$0)this$.imm$position$0(
)).imm$move$1(
this$.imm$heading$0(
)));
  }
  default Object imm$repr1$0(){
    var this$= this;
    return ((base.Direction$0)this$.imm$aiming$0(
)).read$match$1(
base.AimingRepr1$0.instance);
  }
  default Object imm$repr2$0(){
    var this$= this;
    return ((base.Direction$0)this$.imm$aiming$0(
)).read$match$1(
new base.AimingRepr2$0(){
    public Object mut$centre$0(){
    return ((base.Direction$0)this$.imm$heading$0(
)).read$match$1(
base.HeadingChar$0.instance);
  }

});
  }
  default Object imm$repr3$0(){
    var this$= this;
    return ((base.Direction$0)this$.imm$aiming$0(
)).read$match$1(
base.AimingRepr3$0.instance);
  }
}