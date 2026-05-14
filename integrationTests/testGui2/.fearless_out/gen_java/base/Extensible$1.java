package base;
public interface Extensible$1{
  default Object mut$close$0(){
    throw new AssertionError("Uncallable method: Extensible$1.mut$close$0"+this.getClass().getName());
  }
  default Object read$close$0(){
    throw new AssertionError("Uncallable method: Extensible$1.read$close$0"+this.getClass().getName());
  }
  default Object mut$$tilde$1(Object p0){
    var this$= this;
    var ext$= (base.MF$2)p0;
    return ext$.mut$$hash$1(
this$.mut$close$0(
));
  }
  default Object read$$tilde$1(Object p0){
    var this$= this;
    var ext$= (base.MF$2)p0;
    return ext$.mut$$hash$1(
this$.read$close$0(
));
  }
  default Object imm$$tilde$1(Object p0){
    var this$= this;
    var ext$= (base.MF$2)p0;
    return ext$.mut$$hash$1(
this$.read$close$0(
));
  }
  default Object mut$$or_gt$1(Object p0){
    var this$= this;
    var fn$= (base.F$2)p0;
    return fn$.read$$hash$1(
this$.mut$close$0(
));
  }
  default Object read$$or_gt$1(Object p0){
    var this$= this;
    var fn$= (base.F$2)p0;
    return fn$.read$$hash$1(
this$.read$close$0(
));
  }
  default Object imm$$or_gt$1(Object p0){
    var this$= this;
    var fn$= (base.F$2)p0;
    return fn$.read$$hash$1(
this$.read$close$0(
));
  }
}