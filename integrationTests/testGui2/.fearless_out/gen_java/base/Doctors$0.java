package base;
public interface Doctors$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var ps$= (base.List$1)p0;
    return ((base.Flow$1)((base.Flow$1)ps$.imm$flow$0(
)).mut$filter$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _aimpl$= (base.Person$0)p0;
    return ((base.Str$0)_aimpl$.read$name$0(
)).imm$startsWith$1(
base.Str$0Instance.instance("Dr."));
  }

})).mut$list$0(
);
  }
  Doctors$0 instance= new Doctors$0(){};}