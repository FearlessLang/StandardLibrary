package base;
public interface Block$1 extends base.Sealed$0{
  default Object mut$done$0(){
    var this$= this;
    return base.Void$0.instance;
  }
  default Object mut$return$1(Object p0){
    var this$= this;
    var a$= (base.MF$1)p0;
    return a$.mut$$hash$0(
);
  }
  default Object mut$do$1(Object p0){
    var this$= this;
    var r$= (base.MF$1)p0;
    return base.Block$0.instance.imm$$hash$2(
r$.mut$$hash$0(
),
this$);
  }
  default Object mut$assert$1(Object p0){
    var this$= this;
    var p$= (base.F$1)p0;
    return base.Block$0.instance.imm$$hash$2(
base.Assert$0.instance.imm$$bang$1(
p$.read$$hash$0(
)),
this$);
  }
  default Object mut$assert$2(Object p0, Object p1){
    var this$= this;
    var p$= (base.F$1)p0;
    var failMsg$= (base.LazyInfo$0)p1;
    return base.Block$0.instance.imm$$hash$2(
base.Assert$0.instance.imm$$bang$2(
p$.read$$hash$0(
),
failMsg$),
this$);
  }
  default Object mut$let$2(Object p0, Object p1){
    var this$= this;
    var x$= (base.MF$1)p0;
    var cont$= (base.Continuation$2)p1;
    return cont$.mut$$hash$2(
x$.mut$$hash$0(
),
this$);
  }
  default Object mut$openIso$2(Object p0, Object p1){
    var this$= this;
    var x$= p0;
    var cont$= (base.OpenIsoContinuation$2)p1;
    return cont$.mut$$hash$2(
x$,
this$);
  }
  default Object mut$var$2(Object p0, Object p1){
    var this$= this;
    var x$= (base.MF$1)p0;
    var cont$= (base.Continuation$2)p1;
    return cont$.mut$$hash$2(
base.Vars$0.instance.imm$$hash$1(
x$.mut$$hash$0(
)),
this$);
  }
  default Object mut$repr$2(Object p0, Object p1){
    var this$= this;
    var x$= (base.ReprWithInvariant$1)p0;
    var cont$= (base.Continuation$2)p1;
    return cont$.mut$$hash$2(
base.Reprs$0.instance.imm$$hash$2(
x$.mut$elem$0(
),
x$.mut$invariant$0(
)),
this$);
  }
  default Object mut$if$1(Object p0){
    var this$= this;
    var p$= (base.F$1)p0;
    return ((base.Bool$0)p$.read$$hash$0(
)).imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return new base.BlockIf$1(){
    public Object mut$return$1(Object p0){
    var a$= (base.MF$1)p0;
    return base._DecidedBlock$0.instance.imm$$hash$1(
a$.mut$$hash$0(
));
  }

    public Object mut$error$1(Object p0){
    var i$= (base.MF$1)p0;
    return base.Error$0.instance.imm$$bang$1(
i$.mut$$hash$0(
));
  }

    public Object mut$do$1(Object p0){
    var r$= (base.MF$1)p0;
    return base.Block$0.instance.imm$$hash$2(
r$.mut$$hash$0(
),
this$);
  }

    public Object mut$done$0(){
    return base._DecidedBlock$0.instance.imm$$hash$1(
base.Void$0.instance);
  }

};
  }

    public Object mut$else$0(){
    return new base.BlockIf$1(){
    public Object mut$return$1(Object p0){
    return this$;
  }

    public Object mut$error$1(Object p0){
    return this$;
  }

    public Object mut$do$1(Object p0){
    return this$;
  }

    public Object mut$done$0(){
    return base.Block$0.instance.imm$$hash$0(
);
  }

};
  }

});
  }
  default Object mut$loop$1(Object p0){
    var this$= this;
    var body$= (base.MF$1)p0;
    return ((base.ControlFlow$1)body$.mut$$hash$0(
)).mut$match$1(
new ControlFlowMatch$1(){
    public Object mut$continue$0(){
    return this$.mut$loop$1(
body$);
  }

    public Object mut$break$0(){
    return this$;
  }

    public Object mut$return$1(Object p0){
    var v$= p0;
    return base._DecidedBlock$0.instance.imm$$hash$1(
v$);
  }

});
  }
  Block$1 instance= new Block$1(){};}