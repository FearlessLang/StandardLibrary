package base;
public interface TestLog$0 extends base.Main$0{
  default Object imm$main$1(Object p0){
    var this$= this;
    return base.Log$0.instance.imm$log$1(
base.Str$0Instance.instance("Hello"));
  }
  TestLog$0 instance= new TestLog$0(){};}