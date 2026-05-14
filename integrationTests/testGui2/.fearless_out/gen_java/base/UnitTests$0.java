package base;
public interface UnitTests$0 extends base.Main$0{
  default Object imm$main$1(Object p0){
    var this$= this;
    var sys$= (base.System$0)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base._TestCaps$0.instance.imm$$hash$2(
sys$,
base.ELists$0.instance.imm$$hash$0(
));
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var ts$= (base.RTestCap$0)p0;
    var _aeqS$= (base.Block$1)p1;
    return ((base.Block$1)_aeqS$.mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.Block$0.instance.imm$$hash$2(
this$.imm$tests$1(
ts$),
base.Void$0.instance);
  }

})).mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ts$.mut$run$0(
);
  }

});
  }

});
  }
  default Object imm$tests$1(Object p0){
    throw new AssertionError("Uncallable method: UnitTests$0.imm$tests$1"+this.getClass().getName());
  }
}