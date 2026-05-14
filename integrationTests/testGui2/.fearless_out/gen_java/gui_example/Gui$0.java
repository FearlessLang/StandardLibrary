package gui_example;
public interface Gui$0 extends base.Consumer$1{
  default Object mut$canvas$0(){
    throw new AssertionError("Uncallable method: Gui$0.mut$canvas$0"+this.getClass().getName());
  }
  default Object mut$model$0(){
    throw new AssertionError("Uncallable method: Gui$0.mut$model$0"+this.getClass().getName());
  }
  default Object mut$setImage$1(Object p0){
    var this$= this;
    var p$= (base.Painter$0)p0;
    return base.Block$0.instance.imm$$hash$2(
((base.Pane$0)((base.Slot$1)this$.mut$canvas$0(
)).mut$$bang$0(
)).mut$paint$1(
p$),
base.Void$0.instance);
  }
  default Object mut$accept$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Frame$0)p0;
    return ((base.Frame$0)((base.Frame$0)_aimpl$.mut$title$1(
base.Str$0Instance.instance("Gui buttons replace canvas painter"))).mut$fps$1(
base.Nat$0Instance.instance(30L))).mut$contentB$1(
new base.Scope$1(){
    public Object mut$run$1(Object p0){
    var _bimpl$= (base.Border$0)p0;
    return ((base.Border$0)((base.Border$0)((base.Border$0)_bimpl$.mut$background$1(
base.Color$0.instance.imm$ghostWhite$0(
))).mut$north$1(
gui_example.ShapeHeader$0.instance)).mut$center$1(
new base.Scope$1(){
    public Object mut$run$1(Object p0){
    var _cimpl$= (base.Pane$0)p0;
    return ((base.Pane$0)_cimpl$.mut$style$1(
gui_example.ShapeCanvas$0.instance)).mut$save$1(
this$.mut$canvas$0(
));
  }

})).mut$south$1(
new gui_example.ShapeButtons$0(){
    public Object mut$model$0(){
    return this$.mut$model$0(
);
  }

});
  }

});
  }
}