package base;
public interface _FrameField$0{
  default Object imm$$hash$2(Object p0, Object p1){
    var this$= this;
    var i$= (base.Info$0)p0;
    var k$= (base.Str$0)p1;
    return ((base.Info$0)((base.Opt$1)((base.Map$2)i$.imm$map$0(
)).imm$opt$1(
k$)).mut$orLazy$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("Malformed stack frame info, missing key: ").imm$$plus$1(
k$));
  }

})).imm$msg$0(
);
  }
  _FrameField$0 instance= new _FrameField$0(){};}