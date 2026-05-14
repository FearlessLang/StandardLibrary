package base;
public interface MaxAcc$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var by$= (base.OrderBy$2)p0;
    return new base.FoldAcc$2(){
    public Object mut$result$0(){
    return base.ELists$0.instance.imm$$hash$0(
);
  }

    public Object mut$acc$1(Object p0){
    var e$= p0;
    return base.MaxAcc$0.instance.imm$$hash$2(
by$,
((base.EList$1)base.ELists$0.instance.imm$$hash$0(
)).mut$addAnd$1(
e$));
  }

};
  }
  default Object imm$$hash$2(Object p0, Object p1){
    var this$= this;
    var by$= (base.OrderBy$2)p0;
    var bests$= (base.EList$1)p1;
    return new base.FoldAcc$2(){
    public Object mut$result$0(){
    var self$= this;
    return bests$;
  }

    public Object mut$acc$1(Object p0){
    var self$= this;
    var e$= p0;
    return ((base.Order$1)by$.imm$$hash$1(
bests$.mut$get$1(
base.Nat$0Instance.instance(0L)))).read$$lt_eq_gt$2(
by$.imm$$hash$1(
e$),
new base.OrderMatch$1(){
    public Object mut$lt$0(){
    return base.Block$0.instance.imm$$hash$2(
((base.EList$1)bests$.mut$clearAnd$0(
)).mut$add$1(
e$),
self$);
  }

    public Object mut$eq$0(){
    return base.Block$0.instance.imm$$hash$2(
bests$.mut$add$1(
e$),
self$);
  }

    public Object mut$gt$0(){
    return self$;
  }

});
  }

};
  }
  MaxAcc$0 instance= new MaxAcc$0(){};}