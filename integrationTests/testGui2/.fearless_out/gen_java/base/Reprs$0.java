package base;
public interface Reprs$0{
  default Object imm$$hash$2(Object p0, Object p1){
    var this$= this;
    var x$= p0;
    var inv$= (base.F$2)p1;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$openIso$2(
x$,
new base.OpenIsoContinuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var _x$= p0;
    var _aeqS$= (base.Block$1)p1;
    return ((base.Block$1)((base.BlockIf$1)_aeqS$.mut$if$1(
new base.F$1(){
    public Object read$$hash$0(){
    return ((base.Bool$0)inv$.read$$hash$1(
_x$)).imm$not$0(
);
  }

})).mut$error$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.Infos$0.instance.imm$msg$1(
base.Str$0Instance.instance("Invariant does not initially hold"));
  }

})).mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return new Repr$1(){
    public Object mut$mutate$1(Object p0){
    var self$= this;
    var f$= (base.MF$2)p0;
    return base._IdOrErr$0.instance.imm$$hash$3(
f$.mut$$hash$1(
_x$),
inv$.read$$hash$1(
_x$),
base._ReprCacheFlush$0.instance.imm$$hash$1(
self$));
  }

    public Object read$look$1(Object p0){
    var self$= this;
    var f$= (base.F$2)p0;
    return f$.read$$hash$1(
_x$);
  }

    public Object read$cachableLook$1(Object p0){
    var self$= this;
    var f$= (base.CacheReprF$2)p0;
    return f$.imm$_get$1(
self$);
  }

    public Object read$cachableLook$2(Object p0, Object p1){
    var self$= this;
    var a$= (base.Norm$1)p0;
    var f$= (base.CacheReprF$3)p1;
    return f$.imm$_get$2(
self$,
a$);
  }

};
  }

});
  }

});
  }
  Reprs$0 instance= new Reprs$0(){};}