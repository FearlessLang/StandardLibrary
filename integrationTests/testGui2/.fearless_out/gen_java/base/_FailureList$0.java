package base;
public interface _FailureList$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var i$= (base.Info$0)p0;
    return ((base.Opt$1)((base.Map$2)i$.imm$map$0(
)).imm$opt$1(
base.Str$0Instance.instance("list"))).mut$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var e$= (base.Info$0)p0;
    return base.Str$0Instance.instance("").imm$$or$1(
((base.Flow$1)((base.Flow$1)((base.List$1)e$.imm$list$0(
)).imm$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _aimpl$= (base.Info$0)p0;
    return base._FailureTrace$0.instance.imm$$hash$1(
_aimpl$);
  }

})).mut$join$1(
base.Str$0Instance.instance("").imm$$or$0(
)));
  }

    public Object mut$empty$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  _FailureList$0 instance= new _FailureList$0(){};}