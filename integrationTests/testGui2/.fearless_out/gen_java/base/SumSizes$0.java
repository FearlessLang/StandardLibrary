package base;
public interface SumSizes$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var ps$= (base.List$1)p0;
    return ((base.Flow$1)((base.Flow$1)ps$.imm$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _aimpl$= (base.Person$0)p0;
    return ((base.Str$0)_aimpl$.read$name$0(
)).imm$size$0(
);
  }

})).mut$sum$1(
base.Nat$0Instance.instance(0L));
  }
  SumSizes$0 instance= new SumSizes$0(){};}