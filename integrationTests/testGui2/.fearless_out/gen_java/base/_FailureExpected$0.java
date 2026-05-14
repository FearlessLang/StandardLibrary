package base;
public interface _FailureExpected$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var i$= (base.Info$0)p0;
    return ((base.Opt$1)((base.Map$2)i$.imm$map$0(
)).imm$opt$1(
base.Str$0Instance.instance("expected"))).mut$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var e$= (base.Info$0)p0;
    return base.Block$0.instance.imm$$hash$2(
base.Debug$0.instance.imm$$hash$1(
base.Str$0Instance.instance("Expected: ").imm$$plus$1(
e$.imm$msg$0(
))),
((base.Str$0)base.Str$0Instance.instance("<expected>").imm$$plus$1(
base._XmlEscape$0.instance.imm$$hash$1(
e$.imm$msg$0(
)))).imm$$plus$1(
base.Str$0Instance.instance("</expected>")));
  }

    public Object mut$empty$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  _FailureExpected$0 instance= new _FailureExpected$0(){};}