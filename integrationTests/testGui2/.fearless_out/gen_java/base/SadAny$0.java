package base;
public interface SadAny$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var ps$= (base.List$1)p0;
    return ((base.Flow$1)ps$.imm$flow$0(
)).mut$any$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _aimpl$= (base.Person$0)p0;
    return ((base.List$1)_aimpl$.read$cats$0(
)).read$isEmpty$0(
);
  }

});
  }
  SadAny$0 instance= new SadAny$0(){};}