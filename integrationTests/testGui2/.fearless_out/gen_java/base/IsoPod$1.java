package base;
public interface IsoPod$1{
  default Object read$isClosed$0(){
    throw new AssertionError("Uncallable method: IsoPod$1.read$isClosed$0"+this.getClass().getName());
  }
  default Object read$peek$1(Object p0){
    throw new AssertionError("Uncallable method: IsoPod$1.read$peek$1"+this.getClass().getName());
  }
  default Object read$look$1(Object p0){
    var this$= this;
    var f$= (base.MF$2)p0;
    return this$.read$peek$1(
new base.IsoViewer$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return f$.mut$$hash$1(
x$);
  }

    public Object mut$empty$0(){
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("The IsoPod was open"));
  }

});
  }
  default Object mut$open$0(){
    throw new AssertionError("Uncallable method: IsoPod$1.mut$open$0"+this.getClass().getName());
  }
  default Object read$isOpen$0(){
    var this$= this;
    return ((base.Bool$0)this$.read$isClosed$0(
)).imm$not$0(
);
  }
  default Object mut$consume$1(Object p0){
    var this$= this;
    var f$= (base.IsoConsumer$2)p0;
    return ((base.Bool$0)this$.read$isClosed$0(
)).imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return f$.mut$some$1(
this$.mut$open$0(
));
  }

    public Object mut$else$0(){
    return f$.mut$empty$0(
);
  }

});
  }
  default Object mut$mutate$1(Object p0){
    throw new AssertionError("Uncallable method: IsoPod$1.mut$mutate$1"+this.getClass().getName());
  }
  default Object mut$blindMutate$1(Object p0){
    var this$= this;
    var f$= (base.F$2)p0;
    return this$.mut$mutate$1(
new base.IsoMutator$2(){
    public Object mut$some$1(Object p0){
    var mhT$= p0;
    return f$.read$$hash$1(
mhT$);
  }

    public Object mut$empty$0(){
    return base.Void$0.instance;
  }

});
  }
}