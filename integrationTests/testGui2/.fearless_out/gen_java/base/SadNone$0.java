package base;
public interface SadNone$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var ps$= (base.List$1)p0;
    return ((base.Flow$1)ps$.imm$flow$0(
)).mut$none$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _aimpl$= (base.Person$0)p0;
    return ((base.List$1)_aimpl$.read$cats$0(
)).read$isEmpty$0(
);
  }

});
  }
  SadNone$0 instance= new SadNone$0(){};}