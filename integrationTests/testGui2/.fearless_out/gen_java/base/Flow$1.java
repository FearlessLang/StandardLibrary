package base;
public interface Flow$1{
  default Object mut$forEach$1(Object p0){
    throw new AssertionError("Uncallable method: Flow$1.mut$forEach$1"+this.getClass().getName());
  }
  default Object mut$map$1(Object p0){
    throw new AssertionError("Uncallable method: Flow$1.mut$map$1"+this.getClass().getName());
  }
  default Object mut$flatMap$1(Object p0){
    throw new AssertionError("Uncallable method: Flow$1.mut$flatMap$1"+this.getClass().getName());
  }
  default Object mut$filter$1(Object p0){
    throw new AssertionError("Uncallable method: Flow$1.mut$filter$1"+this.getClass().getName());
  }
  default Object mut$size$0(){
    throw new AssertionError("Uncallable method: Flow$1.mut$size$0"+this.getClass().getName());
  }
  default Object mut$isEmpty$0(){
    var this$= this;
    return ((base.Nat$0)this$.mut$size$0(
)).read$$eq_eq$1(
base.Nat$0Instance.instance(0L));
  }
  default Object mut$$plus_plus$1(Object p0){
    throw new AssertionError("Uncallable method: Flow$1.mut$$plus_plus$1"+this.getClass().getName());
  }
  default Object mut$list$0(){
    throw new AssertionError("Uncallable method: Flow$1.mut$list$0"+this.getClass().getName());
  }
  default Object mut$eList$0(){
    throw new AssertionError("Uncallable method: Flow$1.mut$eList$0"+this.getClass().getName());
  }
  default Object mut$mapping$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: Flow$1.mut$mapping$2"+this.getClass().getName());
  }
  default Object mut$join$1(Object p0){
    var this$= this;
    var js$= (base.JoinStr$1)p0;
    return js$.imm$joinStr$1(
this$);
  }
  default Object mut$sum$1(Object p0){
    var this$= this;
    var sn$= (base.SumNumber$1)p0;
    return sn$.imm$sumNumber$1(
this$);
  }
  default Object mut$fold$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: Flow$1.mut$fold$2"+this.getClass().getName());
  }
  default Object mut$foldAcc$1(Object p0){
    var this$= this;
    var acc$= (base.MF$1)p0;
    return ((base.FoldAcc$2)this$.mut$fold$2(
acc$,
new base.F$3(){
    public Object read$$hash$2(Object p0, Object p1){
    var a$= (base.FoldAcc$2)p0;
    var e$= p1;
    return a$.mut$acc$1(
e$);
  }

})).mut$result$0(
);
  }
  default Object mut$max$1(Object p0){
    var this$= this;
    var by$= (base.OrderBy$2)p0;
    return ((base.EList$1)this$.mut$foldAcc$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.MaxAcc$0.instance.imm$$hash$1(
by$);
  }

})).mut$seqFlow$0(
);
  }
  default Object mut$min$1(Object p0){
    var this$= this;
    var by$= (base.OrderBy$2)p0;
    return ((base.EList$1)this$.mut$foldAcc$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.MinAcc$0.instance.imm$$hash$1(
by$);
  }

})).mut$seqFlow$0(
);
  }
  default Object mut$sort$1(Object p0){
    var this$= this;
    var by$= (base.OrderBy$2)p0;
    return ((base.EList$1)((base.EList$1)this$.mut$eList$0(
)).mut$sortAnd$1(
by$)).mut$seqFlow$0(
);
  }
  default Object mut$distinct$1(Object p0){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    return ((base.EList$1)((base.EList$1)this$.mut$eList$0(
)).mut$distinctAnd$1(
by$)).mut$seqFlow$0(
);
  }
  default Object mut$sortDistinct$1(Object p0){
    var this$= this;
    var by$= (base.OrderBy$2)p0;
    return ((base.EList$1)((base.EList$1)this$.mut$eList$0(
)).mut$sortDistinctAnd$1(
by$)).mut$seqFlow$0(
);
  }
  default Object mut$any$1(Object p0){
    throw new AssertionError("Uncallable method: Flow$1.mut$any$1"+this.getClass().getName());
  }
  default Object mut$all$1(Object p0){
    throw new AssertionError("Uncallable method: Flow$1.mut$all$1"+this.getClass().getName());
  }
  default Object mut$none$1(Object p0){
    throw new AssertionError("Uncallable method: Flow$1.mut$none$1"+this.getClass().getName());
  }
  default Object mut$get$0(){
    throw new AssertionError("Uncallable method: Flow$1.mut$get$0"+this.getClass().getName());
  }
  default Object mut$opt$0(){
    throw new AssertionError("Uncallable method: Flow$1.mut$opt$0"+this.getClass().getName());
  }
  default Object mut$first$0(){
    throw new AssertionError("Uncallable method: Flow$1.mut$first$0"+this.getClass().getName());
  }
  default Object mut$mapFilter$1(Object p0){
    var this$= this;
    var f$= (base.F$2)p0;
    return ((base.Flow$1)((base.Flow$1)this$.mut$map$1(
f$)).mut$filter$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var r$= (base.Opt$1)p0;
    return r$.read$isSome$0(
);
  }

})).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var r$= (base.Opt$1)p0;
    return r$.mut$$bang$0(
);
  }

});
  }
  default Object mut$peek$1(Object p0){
    var this$= this;
    var f$= (base.F$2)p0;
    return this$.mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var e$= p0;
    return base.Block$0.instance.imm$$hash$2(
f$.read$$hash$1(
e$),
e$);
  }

});
  }
  default Object mut$peek$2(Object p0, Object p1){
    var this$= this;
    var ctx$= (base.ToIso$1)p0;
    var f$= (base.F$3)p1;
    return this$.mut$map$2(
ctx$,
new base.F$3(){
    public Object read$$hash$2(Object p0, Object p1){
    var c$= p0;
    var e$= p1;
    return base.Block$0.instance.imm$$hash$2(
f$.read$$hash$2(
c$,
e$),
e$);
  }

});
  }
  default Object mut$let$2(Object p0, Object p1){
    var this$= this;
    var x$= (base.F$2)p0;
    var cont$= (base.FlowContinuation$2)p1;
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("TODO"));
  }
  default Object mut$map$2(Object p0, Object p1){
    var this$= this;
    var ctx$= (base.ToIso$1)p0;
    var f$= (base.F$3)p1;
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("TODO"));
  }
  default Object mut$actor$2(Object p0, Object p1){
    var this$= this;
    var state$= p0;
    var f$= (base.ActorImpl$3)p1;
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("TODO"));
  }
  default Object mut$actorMut$2(Object p0, Object p1){
    var this$= this;
    var state$= p0;
    var f$= (base.ActorImplMut$3)p1;
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("TODO"));
  }
  default Object mut$limit$1(Object p0){
    var this$= this;
    var n$= (base.Nat$0)p0;
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("TODO"));
  }
  default Object mut$scan$2(Object p0, Object p1){
    var this$= this;
    var acc$= p0;
    var f$= (base.F$3)p1;
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("TODO"));
  }
  default Object mut$last$0(){
    var this$= this;
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("TODO"));
  }
  default Object mut$findMap$1(Object p0){
    var this$= this;
    var f$= (base.F$2)p0;
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("TODO"));
  }
  default Object mut$findFirst$1(Object p0){
    var this$= this;
    var f$= (base.F$2)p0;
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("TODO"));
  }
}