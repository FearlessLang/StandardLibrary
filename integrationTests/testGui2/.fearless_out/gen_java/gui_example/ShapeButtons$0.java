package gui_example;
public interface ShapeButtons$0 extends base.Scope$1{
  default Object mut$model$0(){
    throw new AssertionError("Uncallable method: ShapeButtons$0.mut$model$0"+this.getClass().getName());
  }
  default Object mut$run$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Pane$0)p0;
    return ((base.Pane$0)((base.Pane$0)((base.Pane$0)((base.Pane$0)((base.Pane$0)((base.Pane$0)_aimpl$.mut$background$1(
base.Color$0.instance.imm$cardboard$0(
))).mut$heightGap$1(
base.HeightNat$0.instance.read$$hash$1(
base.Nat$0Instance.instance(12L)))).mut$widthGap$1(
base.WidthNat$0.instance.read$$hash$1(
base.Nat$0Instance.instance(12L)))).mut$button$1(
new base.Scope$1(){
    public Object mut$run$1(Object p0){
    var _bimpl$= (base.Button$0)p0;
    return ((base.Button$0)((base.Button$0)_bimpl$.mut$text$1(
base.Str$0Instance.instance("Target"))).mut$background$1(
base.Color$0.instance.imm$rubberDuck$0(
))).mut$action$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((gui_example.Model$0)this$.mut$model$0(
)).mut$imgTarget$0(
);
  }

});
  }

})).mut$button$1(
new base.Scope$1(){
    public Object mut$run$1(Object p0){
    var _cimpl$= (base.Button$0)p0;
    return ((base.Button$0)((base.Button$0)_cimpl$.mut$text$1(
base.Str$0Instance.instance("Box"))).mut$background$1(
base.Color$0.instance.imm$alienMint$0(
))).mut$action$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((gui_example.Model$0)this$.mut$model$0(
)).mut$imgBox$0(
);
  }

});
  }

})).mut$button$1(
new base.Scope$1(){
    public Object mut$run$1(Object p0){
    var _dimpl$= (base.Button$0)p0;
    return ((base.Button$0)((base.Button$0)((base.Button$0)_dimpl$.mut$text$1(
base.Str$0Instance.instance("Portal"))).mut$background$1(
base.Color$0.instance.imm$sleepyBlue$0(
))).mut$foreground$1(
base.Color$0.instance.imm$ghostWhite$0(
))).mut$action$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((gui_example.Model$0)this$.mut$model$0(
)).mut$imgPortal$0(
);
  }

});
  }

})).mut$button$1(
new base.Scope$1(){
    public Object mut$run$1(Object p0){
    var _eimpl$= (base.Button$0)p0;
    return ((base.Button$0)((base.Button$0)((base.Button$0)_eimpl$.mut$text$1(
base.Str$0Instance.instance("Warning"))).mut$background$1(
base.Color$0.instance.imm$dangerTomato$0(
))).mut$foreground$1(
base.Color$0.instance.imm$ghostWhite$0(
))).mut$action$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((gui_example.Model$0)this$.mut$model$0(
)).mut$imgWarning$0(
);
  }

});
  }

});
  }
}