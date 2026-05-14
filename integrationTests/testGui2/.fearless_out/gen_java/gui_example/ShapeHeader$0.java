package gui_example;
public interface ShapeHeader$0 extends base.Scope$1{
  default Object mut$run$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Pane$0)p0;
    return ((base.Pane$0)((base.Pane$0)_aimpl$.mut$background$1(
base.Color$0.instance.imm$ghostWhite$0(
))).mut$label$1(
new base.Scope$1(){
    public Object mut$run$1(Object p0){
    var _bimpl$= (base.Label$0)p0;
    return _bimpl$.mut$text$1(
base.Str$0Instance.instance("Press buttons to change the image"));
  }

})).mut$label$1(
new base.Scope$1(){
    public Object mut$run$1(Object p0){
    var _cimpl$= (base.Label$0)p0;
    return _cimpl$.mut$text$1(
base.Str$0Instance.instance("Buttons below replace the whole Painter of the canvas"));
  }

});
  }
  ShapeHeader$0 instance= new ShapeHeader$0(){};}