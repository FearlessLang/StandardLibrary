package gui_example;
public interface Models$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var gui$= (gui_example.Gui$0)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$var$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.Nat$0Instance.instance(100L);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var size$= (base.Var$1)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return new Model$0(){
    public Object mut$imgPortal$0(){
    return gui$.mut$setImage$1(
gui_example.PaintPortal$0.instance);
  }

    public Object mut$imgWarning$0(){
    return gui$.mut$setImage$1(
gui_example.PaintWarning$0.instance);
  }

    public Object mut$imgBox$0(){
    return gui$.mut$setImage$1(
gui_example.PaintBox$0.instance);
  }

    public Object mut$imgTarget$0(){
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return size$.mut$get$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var s$= (base.Nat$0)p0;
    var _beqS$= (base.Block$1)p1;
    return ((base.Block$1)_beqS$.mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return size$.mut$set$1(
((base.Nat$0)size$.mut$get$0(
)).imm$$plus$1(
base.Nat$0Instance.instance(5L)));
  }

})).mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return gui$.mut$setImage$1(
new gui_example.PaintTarget$0(){
    public Object imm$size$0(){
    return s$;
  }

});
  }

});
  }

});
  }

};
  }

});
  }

});
  }
  Models$0 instance= new Models$0(){};}