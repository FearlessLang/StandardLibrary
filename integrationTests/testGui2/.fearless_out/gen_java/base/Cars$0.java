package base;
public interface Cars$0{
  default Object imm$$hash$2(Object p0, Object p1){
    var this$= this;
    var id$= (base.Nat$0)p0;
    var driver$= (base.Person$0)p1;
    return new Car$0(){
    public Object imm$id$0(){
    var self$= this;
    return id$;
  }

    public Object imm$driver$0(){
    var self$= this;
    return driver$;
  }

    public Object read$imm$0(){
    var self$= this;
    return self$;
  }

};
  }
  Cars$0 instance= new Cars$0(){};}