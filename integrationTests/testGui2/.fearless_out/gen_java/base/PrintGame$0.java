package base;
public interface PrintGame$0{
  default Object mut$out$0(){
    throw new AssertionError("Uncallable method: PrintGame$0.mut$out$0"+this.getClass().getName());
  }
  default Object mut$singleLine$1(Object p0){
    var this$= this;
    var ts$= (base.List$1)p0;
    return ((base.Output$0)this$.mut$out$0(
)).mut$println$1(
base.TanksToS$0.instance.read$$hash$1(
ts$));
  }
  default Object mut$lines$2(Object p0, Object p1){
    var this$= this;
    var rounds$= (base.Nat$0)p0;
    var ts$= (base.List$1)p1;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$var$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ts$;
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var current$= (base.Var$1)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Flow$1)base.Nat$0Instance.instance(0L).imm$$tilde_tilde_eq$1(
rounds$)).mut$forEach$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var step$= (base.Nat$0)p0;
    return base.Block$0.instance.imm$$hash$5(
base.Void$0.instance,
base.Void$0.instance,
base.Void$0.instance,
base.Void$0.instance,
current$.mut$set$1(
base.NextState$0.instance.read$$hash$1(
current$.mut$get$0(
))));
  }

});
  }

});
  }

});
  }
}