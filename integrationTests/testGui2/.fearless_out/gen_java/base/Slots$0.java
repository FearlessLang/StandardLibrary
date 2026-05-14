package base;
public interface Slots$0{
  default Object imm$$hash$0(){
    var this$= this;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$var$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.Opt$1.instance;
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var e$= (base.Var$1)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return new Slot$1(){
    public Object mut$accept$1(Object p0){
    var slot$= this;
    var x$= p0;
    return slot$.mut$fill$1(
x$);
  }

    public Object mut$$bang$0(){
    var slot$= this;
    return ((base.Opt$1)e$.mut$get$0(
)).mut$$bang$0(
);
  }

    public Object read$$bang$0(){
    var slot$= this;
    return ((base.Opt$1)e$.read$get$0(
)).read$$bang$0(
);
  }

    public Object mut$fill$1(Object p0){
    var slot$= this;
    var x$= p0;
    return e$.mut$set$1(
((base.Opt$1)e$.mut$get$0(
)).mut$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("Slot was already full"));
  }

    public Object mut$empty$0(){
    return base.Opts$0.instance.imm$$hash$1(
x$);
  }

}));
  }

    public Object read$isFull$0(){
    var slot$= this;
    return ((base.Opt$1)e$.read$get$0(
)).read$isSome$0(
);
  }

    public Object mut$opt$0(){
    var slot$= this;
    return e$.mut$get$0(
);
  }

};
  }

});
  }

});
  }
  Slots$0 instance= new Slots$0(){};}