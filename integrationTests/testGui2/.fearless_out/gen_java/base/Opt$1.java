package base;
public interface Opt$1 extends base._Opt$1, base.DataType$4, base.Extensible$1, base.OrderHash$2, base.Order$2, base.ToImm$3, base.ToInfo$1, base.ToStr$1, base.WidenTo$1, base._AssertEmpty$0{
  default Object read$close$0(){
    var this$= this;
    return this$;
  }
  default Object mut$close$0(){
    var this$= this;
    return this$;
  }
  default Object read$close$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Opt$1)p0;
    return _aimpl$;
  }
  default Object mut$match$1(Object p0){
    var this$= this;
    var m$= (base.OptMatch$2)p0;
    return m$.mut$empty$0(
);
  }
  default Object read$match$1(Object p0){
    var this$= this;
    var m$= (base.OptMatch$2)p0;
    return m$.mut$empty$0(
);
  }
  default Object imm$match$1(Object p0){
    var this$= this;
    var m$= (base.OptMatch$2)p0;
    return m$.mut$empty$0(
);
  }
  default Object read$imm$1(Object p0){
    var this$= this;
    var by$= (base.ToImmBy$2)p0;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return base.Opts$0.instance.imm$$hash$1(
((base.ToImm$1)by$.imm$$hash$1(
x$)).read$imm$0(
));
  }

    public Object mut$empty$0(){
    return base.Opt$1.instance;
  }

});
  }
  default Object read$isEmpty$0(){
    var this$= this;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    return base.False$0.instance;
  }

    public Object mut$empty$0(){
    return base.True$0.instance;
  }

});
  }
  default Object read$isSome$0(){
    var this$= this;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    return base.True$0.instance;
  }

    public Object mut$empty$0(){
    return base.False$0.instance;
  }

});
  }
  default Object mut$$bang$0(){
    var this$= this;
    return this$.mut$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return x$;
  }

    public Object mut$empty$0(){
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("Opt was empty"));
  }

});
  }
  default Object read$$bang$0(){
    var this$= this;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return x$;
  }

    public Object mut$empty$0(){
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("Opt was empty"));
  }

});
  }
  default Object mut$$bang$1(Object p0){
    var this$= this;
    var msg$= (base.Str$0)p0;
    return this$.mut$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return x$;
  }

    public Object mut$empty$0(){
    return base.Error$0.instance.imm$msg$1(
msg$);
  }

});
  }
  default Object read$$bang$1(Object p0){
    var this$= this;
    var msg$= (base.Str$0)p0;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return x$;
  }

    public Object mut$empty$0(){
    return base.Error$0.instance.imm$msg$1(
msg$);
  }

});
  }
  default Object mut$or$1(Object p0){
    var this$= this;
    var other$= (base.Opt$1)p0;
    return this$.mut$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    return this$;
  }

    public Object mut$empty$0(){
    return other$;
  }

});
  }
  default Object read$or$1(Object p0){
    var this$= this;
    var other$= (base.Opt$1)p0;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    return this$;
  }

    public Object mut$empty$0(){
    return other$;
  }

});
  }
  default Object imm$or$1(Object p0){
    var this$= this;
    var other$= (base.Opt$1)p0;
    return this$.imm$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    return this$;
  }

    public Object mut$empty$0(){
    return other$;
  }

});
  }
  default Object mut$$or$1(Object p0){
    var this$= this;
    var other$= (base.Opt$1)p0;
    return this$.mut$or$1(
other$);
  }
  default Object read$$or$1(Object p0){
    var this$= this;
    var other$= (base.Opt$1)p0;
    return this$.read$or$1(
other$);
  }
  default Object imm$$or$1(Object p0){
    var this$= this;
    var other$= (base.Opt$1)p0;
    return this$.imm$or$1(
other$);
  }
  default Object mut$$or_or$1(Object p0){
    var this$= this;
    var other$= (base.MF$1)p0;
    return this$.mut$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    return this$;
  }

    public Object mut$empty$0(){
    return other$.mut$$hash$0(
);
  }

});
  }
  default Object read$$or_or$1(Object p0){
    var this$= this;
    var other$= (base.MF$1)p0;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    return this$;
  }

    public Object mut$empty$0(){
    return other$.mut$$hash$0(
);
  }

});
  }
  default Object imm$$or_or$1(Object p0){
    var this$= this;
    var other$= (base.MF$1)p0;
    return this$.imm$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    return this$;
  }

    public Object mut$empty$0(){
    return other$.mut$$hash$0(
);
  }

});
  }
  default Object mut$orValue$1(Object p0){
    var this$= this;
    var default$= p0;
    return this$.mut$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return x$;
  }

    public Object mut$empty$0(){
    return default$;
  }

});
  }
  default Object read$orValue$1(Object p0){
    var this$= this;
    var default$= p0;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return x$;
  }

    public Object mut$empty$0(){
    return default$;
  }

});
  }
  default Object mut$orLazy$1(Object p0){
    var this$= this;
    var default$= (base.MF$1)p0;
    return this$.mut$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return x$;
  }

    public Object mut$empty$0(){
    return default$.mut$$hash$0(
);
  }

});
  }
  default Object read$orLazy$1(Object p0){
    var this$= this;
    var default$= (base.MF$1)p0;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return x$;
  }

    public Object mut$empty$0(){
    return default$.mut$$hash$0(
);
  }

});
  }
  default Object imm$orLazy$1(Object p0){
    var this$= this;
    var default$= (base.MF$1)p0;
    return this$.imm$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return x$;
  }

    public Object mut$empty$0(){
    return default$.mut$$hash$0(
);
  }

});
  }
  default Object mut$flow$0(){
    var this$= this;
    return this$.mut$match$1(
new base.OptMatch$2(){
    public Object mut$empty$0(){
    return base.Flows$0.instance.imm$$hash$0(
);
  }

    public Object mut$some$1(Object p0){
    var x$= p0;
    return base.Flows$0.instance.imm$$hash$1(
x$);
  }

});
  }
  default Object read$flow$0(){
    var this$= this;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$empty$0(){
    return base.Flows$0.instance.imm$$hash$0(
);
  }

    public Object mut$some$1(Object p0){
    var x$= p0;
    return base.Flows$0.instance.imm$$hash$1(
x$);
  }

});
  }
  default Object imm$flow$0(){
    var this$= this;
    return this$.imm$match$1(
new base.OptMatch$2(){
    public Object mut$empty$0(){
    return base.Flows$0.instance.imm$$hash$0(
);
  }

    public Object mut$some$1(Object p0){
    var x$= p0;
    return base.Flows$0.instance.imm$$hash$1(
x$);
  }

});
  }
  default Object read$as$1(Object p0){
    var this$= this;
    var f$= (base.MF$2)p0;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return base.Opts$0.instance.imm$$hash$1(
f$.mut$$hash$1(
x$));
  }

    public Object mut$empty$0(){
    return base.Opt$1.instance;
  }

});
  }
  default Object mut$ifSome$1(Object p0){
    var this$= this;
    var f$= (base.MF$2)p0;
    return this$.mut$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return f$.mut$$hash$1(
x$);
  }

    public Object mut$empty$0(){
    return base.Void$0.instance;
  }

});
  }
  default Object read$ifSome$1(Object p0){
    var this$= this;
    var f$= (base.MF$2)p0;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return f$.mut$$hash$1(
x$);
  }

    public Object mut$empty$0(){
    return base.Void$0.instance;
  }

});
  }
  default Object imm$ifSome$1(Object p0){
    var this$= this;
    var f$= (base.MF$2)p0;
    return this$.imm$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return f$.mut$$hash$1(
x$);
  }

    public Object mut$empty$0(){
    return base.Void$0.instance;
  }

});
  }
  default Object read$ifEmpty$1(Object p0){
    var this$= this;
    var f$= (base.MF$1)p0;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    return base.Void$0.instance;
  }

    public Object mut$empty$0(){
    return f$.mut$$hash$0(
);
  }

});
  }
  default Object read$str$1(Object p0){
    var this$= this;
    var f$= (base.ToStrBy$1)p0;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return ((base.Str$0)base.Str$0Instance.instance("Opt[").imm$$plus$1(
f$.imm$$hash$1(
x$))).imm$$plus$1(
base.Str$0Instance.instance("]"));
  }

    public Object mut$empty$0(){
    return base.Str$0Instance.instance("Opt[]");
  }

});
  }
  default Object read$info$1(Object p0){
    var this$= this;
    var f$= (base.ToInfoBy$1)p0;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var x$= p0;
    return base.Infos$0.instance.imm$list$1(
f$.imm$$hash$1(
x$));
  }

    public Object mut$empty$0(){
    return base.Info$0.instance;
  }

});
  }
  default Object read$cmp$4(Object p0, Object p1, Object p2, Object p3){
    var this$= this;
    var by$= (base.OrderBy$2)p0;
    var a$= (base.Opt$1)p1;
    var b$= (base.Opt$1)p2;
    var m$= (base.OrderMatch$1)p3;
    return a$.read$match$1(
new base.OptMatch$2(){
    public Object mut$empty$0(){
    return b$.read$match$1(
new base.OptMatch$2(){
    public Object mut$empty$0(){
    return m$.mut$eq$0(
);
  }

    public Object mut$some$1(Object p0){
    return m$.mut$lt$0(
);
  }

});
  }

    public Object mut$some$1(Object p0){
    var ea$= p0;
    return b$.read$match$1(
new base.OptMatch$2(){
    public Object mut$empty$0(){
    return m$.mut$gt$0(
);
  }

    public Object mut$some$1(Object p0){
    var eb$= p0;
    return ((base.Order$1)by$.imm$$hash$1(
ea$)).read$$lt_eq_gt$2(
by$.imm$$hash$1(
eb$),
m$);
  }

});
  }

});
  }
  default Object read$hash$1(Object p0){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    return this$.read$match$1(
new base.OptMatch$2(){
    public Object mut$empty$0(){
    return base.Nat$0Instance.instance(0L);
  }

    public Object mut$some$1(Object p0){
    var e$= p0;
    return base.Nat$0Instance.instance(31L).imm$aluAddWrap$1(
((base.OrderHash$1)by$.imm$$hash$1(
e$)).read$hash$0(
));
  }

});
  }
  Opt$1 instance= new Opt$1(){};}