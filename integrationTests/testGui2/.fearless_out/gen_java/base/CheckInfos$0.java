package base;
public interface CheckInfos$0 extends base.F$3{
  default Object read$$hash$2(Object p0, Object p1){
    var this$= this;
    var c$= (base.CheckSys$0)p0;
    var st$= (base.List$1)p1;
    return new CheckInfo$0(){
    public Object imm$check$0(){
    return c$;
  }

    public Object imm$stackTrace$0(){
    return st$;
  }

};
  }
  CheckInfos$0 instance= new CheckInfos$0(){};}