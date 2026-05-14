package base;
public interface AllNames$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var ps$= (base.List$1)p0;
    return ((base.Flow$1)((base.Flow$1)ps$.imm$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _aimpl$= (base.Person$0)p0;
    return _aimpl$.read$name$0(
);
  }

})).mut$join$1(
base.Str$0Instance.instance(", "));
  }
  AllNames$0 instance= new AllNames$0(){};}