package gui_example;
public interface PaintTarget$0 extends base.Painter$0{
  default Object imm$size$0(){
    throw new AssertionError("Uncallable method: PaintTarget$0.imm$size$0"+this.getClass().getName());
  }
  default Object imm$run$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Graphics$0)p0;
    return ((base.Graphics$0)((base.Graphics$0)((base.Graphics$0)((base.Graphics$0)((base.Graphics$0)((base.Graphics$0)((base.Graphics$0)((base.Graphics$0)_aimpl$.mut$color$1(
base.Color$0.instance.imm$ghostWhite$0(
))).mut$position$2(
base.XNat$0.instance.read$$hash$1(
this$.imm$size$0(
)),
base.YNat$0.instance.read$$hash$1(
base.Nat$0Instance.instance(50L)))).mut$oval$2(
base.WidthNat$0.instance.read$$hash$1(
base.Nat$0Instance.instance(180L)),
base.HeightNat$0.instance.read$$hash$1(
base.Nat$0Instance.instance(180L)))).mut$color$1(
base.Color$0.instance.imm$rubberDuck$0(
))).mut$position$2(
base.XNat$0.instance.read$$hash$1(
base.Nat$0Instance.instance(205L)),
base.YNat$0.instance.read$$hash$1(
base.Nat$0Instance.instance(85L)))).mut$oval$2(
base.WidthNat$0.instance.read$$hash$1(
base.Nat$0Instance.instance(110L)),
base.HeightNat$0.instance.read$$hash$1(
base.Nat$0Instance.instance(110L)))).mut$color$1(
base.Color$0.instance.imm$dangerTomato$0(
))).mut$position$2(
base.XNat$0.instance.read$$hash$1(
base.Nat$0Instance.instance(240L)),
base.YNat$0.instance.read$$hash$1(
base.Nat$0Instance.instance(120L)))).mut$oval$2(
base.WidthNat$0.instance.read$$hash$1(
base.Nat$0Instance.instance(40L)),
base.HeightNat$0.instance.read$$hash$1(
base.Nat$0Instance.instance(40L)));
  }
}