package base;
public interface _IdOrErr$0{
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    var this$= this;
    var r$= p0;
    var b$= (base.Bool$0)p1;
    return base.Block$0.instance.imm$$hash$2(
b$.imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return base.Void$0.instance;
  }

    public Object mut$else$0(){
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("Invariant does not hold after mutation"));
  }

}),
r$);
  }
  _IdOrErr$0 instance= new _IdOrErr$0(){};}