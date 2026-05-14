package base;
public interface _InfoToStr$0 extends base.InfoMatch$1{
  default Object imm$empty$0(){
    var this$= this;
    return base.Str$0Instance.instance("EmptyInfo");
  }
  default Object imm$msg$1(Object p0){
    var this$= this;
    var msg$= (base.Str$0)p0;
    return msg$.imm$escape$0(
);
  }
  default Object imm$list$1(Object p0){
    var this$= this;
    var list$= (base.List$1)p0;
    return list$.read$str$1(
new base.ToStrBy$1(){
    public Object imm$$hash$1(Object p0){
    var _aimpl$= (base.Info$0)p0;
    return _aimpl$;
  }

});
  }
  default Object imm$map$1(Object p0){
    var this$= this;
    var map$= (base.Map$2)p0;
    return map$.read$str$1(
new base.ToStrBy$1(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.Info$0)p0;
    return _bimpl$;
  }

});
  }
  _InfoToStr$0 instance= new _InfoToStr$0(){};}