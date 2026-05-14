package base;
public interface List$1 extends base._List$1, base._AssertEmpty$0, base._AssertSize$0, base.DataType$4, base.Extensible$1, base.OrderHash$2, base.Order$2, base.Sealed$0, base.ToImm$3, base.ToInfo$1, base.ToStr$1, base.WidenTo$1{
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
    var _aimpl$= (base.List$1)p0;
    return _aimpl$;
  }
  default Object read$size$0(){
    var this$= this;
    return base.Nat$0Instance.instance(0L);
  }
  default Object read$isEmpty$0(){
    var this$= this;
    return base.True$0.instance;
  }
  default Object mut$get$1(Object p0){
    var this$= this;
    var i$= (base.Nat$0)p0;
    return base.Assert$0.instance.imm$msg$1(
base.Str$0Instance.instance(".get called on empty list"));
  }
  default Object read$get$1(Object p0){
    var this$= this;
    var i$= (base.Nat$0)p0;
    return base.Assert$0.instance.imm$msg$1(
base.Str$0Instance.instance(".get called on empty list"));
  }
  default Object mut$opt$1(Object p0){
    var this$= this;
    var i$= (base.Nat$0)p0;
    return base.Opt$1.instance;
  }
  default Object read$opt$1(Object p0){
    var this$= this;
    var i$= (base.Nat$0)p0;
    return base.Opt$1.instance;
  }
  default Object imm$opt$1(Object p0){
    var this$= this;
    var i$= (base.Nat$0)p0;
    return base.Opt$1.instance;
  }
  default Object mut$first$0(){
    var this$= this;
    return base.Assert$0.instance.imm$msg$1(
base.Str$0Instance.instance(".first called on empty list"));
  }
  default Object read$first$0(){
    var this$= this;
    return base.Assert$0.instance.imm$msg$1(
base.Str$0Instance.instance(".first called on empty list"));
  }
  default Object mut$last$0(){
    var this$= this;
    return base.Assert$0.instance.imm$msg$1(
base.Str$0Instance.instance(".last called on empty list"));
  }
  default Object read$last$0(){
    var this$= this;
    return base.Assert$0.instance.imm$msg$1(
base.Str$0Instance.instance(".last called on empty list"));
  }
  default Object mut$firstOpt$0(){
    var this$= this;
    return base.Opt$1.instance;
  }
  default Object read$firstOpt$0(){
    var this$= this;
    return base.Opt$1.instance;
  }
  default Object imm$firstOpt$0(){
    var this$= this;
    return base.Opt$1.instance;
  }
  default Object mut$lastOpt$0(){
    var this$= this;
    return base.Opt$1.instance;
  }
  default Object read$lastOpt$0(){
    var this$= this;
    return base.Opt$1.instance;
  }
  default Object imm$lastOpt$0(){
    var this$= this;
    return base.Opt$1.instance;
  }
  default Object mut$$plus_plus$1(Object p0){
    var this$= this;
    var xs$= (base.List$1)p0;
    return xs$;
  }
  default Object read$$plus_plus$1(Object p0){
    var this$= this;
    var xs$= (base.List$1)p0;
    return xs$;
  }
  default Object mut$$lt_plus$1(Object p0){
    var this$= this;
    var e$= p0;
    return base.Lists$0.instance.imm$$hash$1(
e$);
  }
  default Object read$$lt_plus$1(Object p0){
    var this$= this;
    var e$= p0;
    return base.Lists$0.instance.imm$singletonRead$1(
e$);
  }
  default Object mut$$plus_gt$1(Object p0){
    var this$= this;
    var e$= p0;
    return base.Lists$0.instance.imm$$hash$1(
e$);
  }
  default Object read$$plus_gt$1(Object p0){
    var this$= this;
    var e$= p0;
    return base.Lists$0.instance.imm$singletonRead$1(
e$);
  }
  default Object mut$subList$2(Object p0, Object p1){
    var this$= this;
    var start$= (base.Nat$0)p0;
    var end$= (base.Nat$0)p1;
    return base.Block$0.instance.imm$$hash$2(
base.Assert$0.instance.imm$$bang$2(
((base.Bool$0)start$.read$$eq_eq$1(
base.Nat$0Instance.instance(0L))).imm$$and_and$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return end$.read$$eq_eq$1(
base.Nat$0Instance.instance(0L));
  }

}),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance(".subList(").imm$$plus$1(
start$)).imm$$plus$1(
base.Str$0Instance.instance(", "))).imm$$plus$1(
end$)).imm$$plus$1(
base.Str$0Instance.instance(") called on empty list"));
  }

}),
base.List$1.instance);
  }
  default Object read$subList$2(Object p0, Object p1){
    var this$= this;
    var start$= (base.Nat$0)p0;
    var end$= (base.Nat$0)p1;
    return base.Block$0.instance.imm$$hash$2(
base.Assert$0.instance.imm$$bang$2(
((base.Bool$0)start$.read$$eq_eq$1(
base.Nat$0Instance.instance(0L))).imm$$and_and$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return end$.read$$eq_eq$1(
base.Nat$0Instance.instance(0L));
  }

}),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance(".subList(").imm$$plus$1(
start$)).imm$$plus$1(
base.Str$0Instance.instance(", "))).imm$$plus$1(
end$)).imm$$plus$1(
base.Str$0Instance.instance(") called on empty list"));
  }

}),
base.List$1.instance);
  }
  default Object mut$subList$1(Object p0){
    var this$= this;
    var start$= (base.Nat$0)p0;
    return this$.mut$subList$2(
start$,
this$.read$size$0(
));
  }
  default Object read$subList$1(Object p0){
    var this$= this;
    var start$= (base.Nat$0)p0;
    return this$.read$subList$2(
start$,
this$.read$size$0(
));
  }
  default Object mut$with$2(Object p0, Object p1){
    var this$= this;
    var i$= (base.Nat$0)p0;
    var e$= p1;
    return base.Assert$0.instance.imm$msg$1(
base.Str$0Instance.instance(".with called on empty list"));
  }
  default Object read$with$2(Object p0, Object p1){
    var this$= this;
    var i$= (base.Nat$0)p0;
    var e$= p1;
    return base.Assert$0.instance.imm$msg$1(
base.Str$0Instance.instance(".with called on empty list"));
  }
  default Object mut$withAlso$2(Object p0, Object p1){
    var this$= this;
    var i$= (base.Nat$0)p0;
    var e$= p1;
    return base.Block$0.instance.imm$$hash$2(
base.Assert$0.instance.imm$$bang$2(
i$.read$$eq_eq$1(
base.Nat$0Instance.instance(0L)),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Str$0)base.Str$0Instance.instance(".withAlso(").imm$$plus$1(
i$)).imm$$plus$1(
base.Str$0Instance.instance(", _) called on empty list"));
  }

}),
base.Lists$0.instance.imm$$hash$1(
e$));
  }
  default Object read$withAlso$2(Object p0, Object p1){
    var this$= this;
    var i$= (base.Nat$0)p0;
    var e$= p1;
    return base.Block$0.instance.imm$$hash$2(
base.Assert$0.instance.imm$$bang$2(
i$.read$$eq_eq$1(
base.Nat$0Instance.instance(0L)),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Str$0)base.Str$0Instance.instance(".withAlso(").imm$$plus$1(
i$)).imm$$plus$1(
base.Str$0Instance.instance(", _) called on empty list"));
  }

}),
base.Lists$0.instance.imm$singletonRead$1(
e$));
  }
  default Object mut$without$1(Object p0){
    var this$= this;
    var i$= (base.Nat$0)p0;
    return base.Assert$0.instance.imm$msg$1(
base.Str$0Instance.instance(".without called on empty list"));
  }
  default Object read$without$1(Object p0){
    var this$= this;
    var i$= (base.Nat$0)p0;
    return base.Assert$0.instance.imm$msg$1(
base.Str$0Instance.instance(".without called on empty list"));
  }
  default Object mut$reverse$0(){
    var this$= this;
    return base.List$1.instance;
  }
  default Object read$reverse$0(){
    var this$= this;
    return base.List$1.instance;
  }
  default Object mut$seqFlow$0(){
    var this$= this;
    return base.Flows$0.instance.imm$fromMutList$1(
this$);
  }
  default Object read$flow$0(){
    var this$= this;
    return base.Flows$0.instance.imm$fromReadList$1(
this$);
  }
  default Object imm$flow$0(){
    var this$= this;
    return base.Flows$0.instance.imm$fromImmList$1(
this$);
  }
  default Object mut$flow$1(Object p0){
    var this$= this;
    var mode$= (base.FlowMode$1)p0;
    return base.Flows$0.instance.imm$fromMutList$2(
this$,
mode$);
  }
  default Object mut$as$1(Object p0){
    var this$= this;
    var f$= (base.MF$2)p0;
    return base.List$1.instance;
  }
  default Object read$as$1(Object p0){
    var this$= this;
    var f$= (base.MF$2)p0;
    return base.List$1.instance;
  }
  default Object imm$as$1(Object p0){
    var this$= this;
    var f$= (base.MF$2)p0;
    return base.List$1.instance;
  }
  default Object read$imm$1(Object p0){
    var this$= this;
    var by$= (base.ToImmBy$2)p0;
    return base.List$1.instance;
  }
  default Object read$str$1(Object p0){
    var this$= this;
    var by$= (base.ToStrBy$1)p0;
    return base.Str$0Instance.instance("[]");
  }
  default Object read$info$1(Object p0){
    var this$= this;
    var by$= (base.ToInfoBy$1)p0;
    return ((base.Block$1)((base.BlockIf$1)((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$if$1(
new base.F$1(){
    public Object read$$hash$0(){
    return this$.read$isEmpty$0(
);
  }

})).mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.Info$0.instance;
  }

})).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.List$1)((base.Flow$1)((base.Flow$1)this$.read$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var e$= p0;
    return ((base.ToInfo$0)by$.imm$$hash$1(
e$)).read$info$0(
);
  }

})).mut$list$0(
)).mut$as$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var _bimpl$= (base.Info$0)p0;
    return _bimpl$;
  }

});
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var list$= (base.List$1)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return new base.Info$0(){
    public Object imm$match$1(Object p0){
    var i$= this;
    var _cimpl$= (base.InfoMatch$1)p0;
    return _cimpl$.imm$list$1(
list$);
  }

    public Object imm$list$0(){
    var i$= this;
    return list$;
  }

    public Object read$imm$0(){
    var i$= this;
    return i$;
  }

};
  }

});
  }

});
  }
  default Object read$hash$1(Object p0){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return this$.read$size$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var size$= (base.Nat$0)p0;
    var _beqS$= (base.Block$1)p1;
    return _beqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Flow$1)this$.read$flow$0(
)).mut$fold$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return size$;
  }

},
new base.F$3(){
    public Object read$$hash$2(Object p0, Object p1){
    var acc$= (base.Nat$0)p0;
    var e$= p1;
    return acc$.imm$hashWith$1(
((base.OrderHash$1)by$.imm$$hash$1(
e$)).read$hash$0(
));
  }

});
  }

});
  }

});
  }
  default Object read$cmp$4(Object p0, Object p1, Object p2, Object p3){
    var this$= this;
    var by$= (base.OrderBy$2)p0;
    var a$= (base.List$1)p1;
    var b$= (base.List$1)p2;
    var m$= (base.OrderMatch$1)p3;
    return ((base.Opt$1)a$.read$opt$1(
base.Nat$0Instance.instance(0L))).mut$match$1(
new base.OptMatch$2(){
    public Object mut$empty$0(){
    return ((base.Opt$1)b$.read$opt$1(
base.Nat$0Instance.instance(0L))).mut$match$1(
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
    return ((base.Opt$1)b$.read$opt$1(
base.Nat$0Instance.instance(0L))).mut$match$1(
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
new base.OrderMatch$1(){
    public Object mut$lt$0(){
    return m$.mut$lt$0(
);
  }

    public Object mut$gt$0(){
    return m$.mut$gt$0(
);
  }

    public Object mut$eq$0(){
    return this$.read$cmp$4(
by$,
a$.read$subList$1(
base.Nat$0Instance.instance(1L)),
b$.read$subList$1(
base.Nat$0Instance.instance(1L)),
m$);
  }

});
  }

});
  }

});
  }
  default Object imm$norm$1(Object p0){
    var this$= this;
    var f$= (base.MF$2)p0;
    return base._ListNorm$0.instance.imm$$hash$2(
this$,
f$);
  }
  List$1 instance= new List$1(){};}