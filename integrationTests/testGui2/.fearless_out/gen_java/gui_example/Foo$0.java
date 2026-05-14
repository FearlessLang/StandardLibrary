package gui_example;
public interface Foo$0 extends base.Main$0{
  default Object imm$main$1(Object p0){
    var this$= this;
    var s$= (base.System$0)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return s$.mut$gui$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var g$= (base.Gui$0)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.Slots$0.instance.imm$$hash$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var model$= (base.Slot$1)p0;
    var _beqS$= (base.Block$1)p1;
    return _beqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.Slots$0.instance.imm$$hash$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var canvas$= (base.Slot$1)p0;
    var _ceqS$= (base.Block$1)p1;
    return _ceqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return new gui_example.Gui$0(){
    public Object mut$canvas$0(){
    return canvas$;
  }

    public Object mut$model$0(){
    return model$.mut$$bang$0(
);
  }

};
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var gui$= (gui_example.Gui$0)p0;
    var _deqS$= (base.Block$1)p1;
    return ((base.Block$1)((base.Block$1)_deqS$.mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return model$.mut$fill$1(
gui_example.Models$0.instance.imm$$hash$1(
gui$));
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return g$.mut$run$1(
gui$);
  }

})).mut$done$0(
);
  }

});
  }

});
  }

});
  }

});
  }
  Foo$0 instance= new Foo$0(){};}