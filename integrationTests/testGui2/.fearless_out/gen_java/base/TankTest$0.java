package base;
public interface TankTest$0 extends base.Main$0{
  default Object imm$main$1(Object p0){
    var this$= this;
    var sys$= (base.System$0)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return sys$.mut$out$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var out$= (base.Output$0)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Opt$1)((base.InputCursor$0)sys$.mut$inputCursor$0(
)).mut$$hash$0(
)).mut$$bang$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var in$= (base.InputCursorNode$0)p0;
    var _beqS$= (base.Block$1)p1;
    return _beqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return new base.ReadGame$0(){
    public Object mut$in$0(){
    return in$;
  }

}.mut$read$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var game$= (base.List$1)p0;
    var _ceqS$= (base.Block$1)p1;
    return _ceqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return new base.PrintGame$0(){
    public Object mut$out$0(){
    return out$;
  }

}.mut$lines$2(
base.Nat$0Instance.instance(50L),
game$);
  }

});
  }

});
  }

});
  }

});
  }
  TankTest$0 instance= new TankTest$0(){};}