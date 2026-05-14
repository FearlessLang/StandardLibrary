package base;
public interface Tests$0{
  default Object imm$test$1(Object p0){
    var this$= this;
    var v$= (base.Void$0)p0;
    return this$;
  }
  default Object imm$testFails$1(Object p0){
    var this$= this;
    var f$= (base.F$1)p0;
    return ((base.Block$1)((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Action$1)base.Try$0.instance.imm$$hash$1(
f$)).mut$assertInfo$0(
);
  }

})).mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return this$;
  }

});
  }
  default Object imm$done$0(){
    var this$= this;
    return base.Void$0.instance;
  }
  default Object imm$testSuite$1(Object p0){
    var this$= this;
    var f$= (base.F$2)p0;
    return f$.read$$hash$1(
this$);
  }
  Tests$0 instance= new Tests$0(){};}