package base;
public interface AllCats$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var ps$= (base.List$1)p0;
    return ((base.Flow$1)((base.Flow$1)ps$.imm$flow$0(
)).mut$flatMap$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _aimpl$= (base.Person$0)p0;
    return ((base.List$1)_aimpl$.read$cats$0(
)).imm$flow$0(
);
  }

})).mut$list$0(
);
  }
  AllCats$0 instance= new AllCats$0(){};}