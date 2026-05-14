package base;
public interface StackFrames$0 extends base.F$5{
  default Object read$$hash$4(Object p0, Object p1, Object p2, Object p3){
    var this$= this;
    var fn$= (base.Str$0)p0;
    var tn$= (base.Str$0)p1;
    var mn$= (base.Str$0)p2;
    var ln$= (base.Nat$0)p3;
    return new base.StackFrame$0(){
    public Object imm$fileName$0(){
    var self$= this;
    return fn$;
  }

    public Object imm$typeName$0(){
    var self$= this;
    return tn$;
  }

    public Object imm$methodName$0(){
    var self$= this;
    return mn$;
  }

    public Object imm$lineNumber$0(){
    var self$= this;
    return ln$;
  }

    public Object read$imm$0(){
    var self$= this;
    return self$;
  }

};
  }
  StackFrames$0 instance= new StackFrames$0(){};}