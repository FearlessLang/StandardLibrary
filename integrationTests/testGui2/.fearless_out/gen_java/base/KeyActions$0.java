package base;
public interface KeyActions$0{
  default Object imm$$hash$0(){
    var this$= this;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.ELists$0.instance.imm$$hash$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var match$= (base.EList$1)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.ELists$0.instance.imm$$hash$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var actions$= (base.EList$1)p0;
    var _beqS$= (base.Block$1)p1;
    return _beqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return new KeyAction$0(){
    public Object mut$match$0(){
    var self$= this;
    return match$;
  }

    public Object mut$actions$0(){
    var self$= this;
    return actions$;
  }

    public Object mut$matchOnly$1(Object p0){
    var self$= this;
    var k$= (base.KeyStroke$0)p0;
    return base.Block$0.instance.imm$$hash$3(
match$.mut$clear$0(
),
match$.mut$add$1(
k$),
self$);
  }

    public Object mut$matchAlso$1(Object p0){
    var self$= this;
    var k$= (base.KeyStroke$0)p0;
    return base.Block$0.instance.imm$$hash$2(
match$.mut$add$1(
k$),
self$);
  }

    public Object mut$doOnly$1(Object p0){
    var self$= this;
    var c$= (base.Consumer$1)p0;
    return base.Block$0.instance.imm$$hash$3(
actions$.mut$clear$0(
),
actions$.mut$add$1(
c$),
self$);
  }

    public Object mut$doAlso$1(Object p0){
    var self$= this;
    var c$= (base.Consumer$1)p0;
    return base.Block$0.instance.imm$$hash$2(
actions$.mut$add$1(
c$),
self$);
  }

    public Object mut$save$1(Object p0){
    var self$= this;
    var c$= (base.Consumer$1)p0;
    return base.Block$0.instance.imm$$hash$2(
c$.mut$accept$1(
self$),
self$);
  }

    public Object mut$style$1(Object p0){
    var self$= this;
    var c$= (base.Scope$1)p0;
    return base.Block$0.instance.imm$$hash$2(
c$.mut$run$1(
self$),
self$);
  }

};
  }

});
  }

});
  }

});
  }
  KeyActions$0 instance= new KeyActions$0(){};}