package base;
public interface _Persons$0 extends base.F$3{
  default Object read$$hash$2(Object p0, Object p1){
    var this$= this;
    var name$= (base.Str$0)p0;
    var age$= (base.Nat$0)p1;
    return new base._Person$0(){
    public Object read$name$0(){
    return name$;
  }

    public Object read$age$0(){
    return age$;
  }

};
  }
  _Persons$0 instance= new _Persons$0(){};}