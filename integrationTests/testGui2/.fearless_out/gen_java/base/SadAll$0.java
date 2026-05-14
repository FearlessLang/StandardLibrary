package base;
public interface SadAll$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var ps$= (base.List$1)p0;
    return ((base.Flow$1)ps$.imm$flow$0(
)).mut$all$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _aimpl$= (base.Person$0)p0;
    return ((base.List$1)_aimpl$.read$cats$0(
)).read$isEmpty$0(
);
  }

});
  }
  SadAll$0 instance= new SadAll$0(){};}