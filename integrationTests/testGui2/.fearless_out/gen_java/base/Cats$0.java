package base;
public interface Cats$0{
  default Object imm$$hash$2(Object p0, Object p1){
    var this$= this;
    var name$= (base.Str$0)p0;
    var weight$= (base.Nat$0)p1;
    return new Cat$0(){
    public Object imm$name$0(){
    return name$;
  }

    public Object imm$weight$0(){
    return weight$;
  }

};
  }
  Cats$0 instance= new Cats$0(){};}