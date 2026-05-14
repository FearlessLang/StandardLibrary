package base;
public interface _FailureMsg$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var i$= (base.Info$0)p0;
    return ((base.Bool$0)((base.Str$0)i$.imm$msg$0(
)).imm$isEmpty$0(
)).imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return ((base.Opt$1)((base.Map$2)i$.imm$map$0(
)).imm$opt$1(
base.Str$0Instance.instance("msg"))).mut$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var e$= (base.Info$0)p0;
    return e$.imm$msg$0(
);
  }

    public Object mut$empty$0(){
    return i$.read$str$0(
);
  }

});
  }

    public Object mut$else$0(){
    return i$.imm$msg$0(
);
  }

});
  }
  _FailureMsg$0 instance= new _FailureMsg$0(){};}