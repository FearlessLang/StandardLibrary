package base;
public interface Action$1{
  default Object mut$run$1(Object p0){
    throw new AssertionError("Uncallable method: Action$1.mut$run$1"+this.getClass().getName());
  }
  default Object mut$map$1(Object p0){
    var this$= this;
    var f$= (base.MF$2)p0;
    return new base.Action$1(){
    public Object mut$run$1(Object p0){
    var m$= (base.ActionMatch$2)p0;
    return this$.mut$run$1(
new base.ActionMatch$2(){
    public Object mut$ok$1(Object p0){
    var x$= p0;
    return m$.mut$ok$1(
f$.mut$$hash$1(
x$));
  }

    public Object mut$info$1(Object p0){
    var i$= (base.Info$0)p0;
    return m$.mut$info$1(
i$);
  }

});
  }

};
  }
  default Object mut$andThen$1(Object p0){
    var this$= this;
    var f$= (base.MF$2)p0;
    return new base.Action$1(){
    public Object mut$run$1(Object p0){
    var m$= (base.ActionMatch$2)p0;
    return this$.mut$run$1(
new base.ActionMatch$2(){
    public Object mut$ok$1(Object p0){
    var x$= p0;
    return ((base.Action$1)f$.mut$$hash$1(
x$)).mut$run$1(
m$);
  }

    public Object mut$info$1(Object p0){
    var i$= (base.Info$0)p0;
    return m$.mut$info$1(
i$);
  }

});
  }

};
  }
  default Object mut$mapInfo$1(Object p0){
    var this$= this;
    var f$= (base.MF$2)p0;
    return new base.Action$1(){
    public Object mut$run$1(Object p0){
    var m$= (base.ActionMatch$2)p0;
    return this$.mut$run$1(
new base.ActionMatch$2(){
    public Object mut$ok$1(Object p0){
    var x$= p0;
    return m$.mut$ok$1(
x$);
  }

    public Object mut$info$1(Object p0){
    var i$= (base.Info$0)p0;
    return m$.mut$info$1(
f$.mut$$hash$1(
i$));
  }

});
  }

};
  }
  default Object mut$$bang$0(){
    var this$= this;
    return this$.mut$run$1(
new base.ActionMatch$2(){
    public Object mut$ok$1(Object p0){
    var x$= p0;
    return x$;
  }

    public Object mut$info$1(Object p0){
    var i$= (base.Info$0)p0;
    return base.Error$0.instance.imm$$bang$1(
i$);
  }

});
  }
  default Object mut$ok$0(){
    var this$= this;
    return this$.mut$run$1(
new base.ActionMatch$2(){
    public Object mut$ok$1(Object p0){
    var x$= p0;
    return base.Opts$0.instance.imm$$hash$1(
x$);
  }

    public Object mut$info$1(Object p0){
    return base.Opt$1.instance;
  }

});
  }
  default Object mut$info$0(){
    var this$= this;
    return this$.mut$run$1(
new base.ActionMatch$2(){
    public Object mut$ok$1(Object p0){
    return base.Opt$1.instance;
  }

    public Object mut$info$1(Object p0){
    var i$= (base.Info$0)p0;
    return base.Opts$0.instance.imm$$hash$1(
i$);
  }

});
  }
  default Object mut$assertOk$0(){
    var this$= this;
    return this$.mut$run$1(
new base.ActionMatch$2(){
    public Object mut$ok$1(Object p0){
    return base.Void$0.instance;
  }

    public Object mut$info$1(Object p0){
    var i$= (base.Info$0)p0;
    return base.Assert$0.instance.imm$msg$1(
((base.Str$0)base.Str$0Instance.instance("Success expected but an Info was thrown:").imm$$or$1(
i$)).read$str$0(
));
  }

});
  }
  default Object mut$assertInfo$0(){
    var this$= this;
    return this$.mut$run$1(
new base.ActionMatch$2(){
    public Object mut$ok$1(Object p0){
    return base.Assert$0.instance.imm$msg$1(
base.Str$0Instance.instance("Failure expected but nothing was thrown"));
  }

    public Object mut$info$1(Object p0){
    return base.Void$0.instance;
  }

});
  }
}