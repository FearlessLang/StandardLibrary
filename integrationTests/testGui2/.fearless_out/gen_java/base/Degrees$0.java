package base;
public interface Degrees$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var angle$= (base.Float$0)p0;
    return ((base.Opt$1)angle$.imm$finite$0(
)).read$as$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var a$= (base.Float$0)p0;
    return new base.Degree$0(){
    public Object read$$hash$0(){
    return a$;
  }

};
  }

});
  }
  Degrees$0 instance= new Degrees$0(){};}