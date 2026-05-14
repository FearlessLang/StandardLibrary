package base;
public interface Map$2 extends base.Sealed$0, base.DataType$4, base._AssertEmpty$0, base._AssertSize$0, base.Extensible$1, base.OrderHash$2, base.Order$2, base.ToImm$3, base.ToInfo$1, base.ToStr$1, base.WidenTo$1{
  default Object read$size$0(){
    throw new AssertionError("Uncallable method: Map$2.read$size$0"+this.getClass().getName());
  }
  default Object read$isEmpty$0(){
    throw new AssertionError("Uncallable method: Map$2.read$isEmpty$0"+this.getClass().getName());
  }
  default Object read$keyOh$0(){
    throw new AssertionError("Uncallable method: Map$2.read$keyOh$0"+this.getClass().getName());
  }
  default Object mut$get$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.mut$get$1"+this.getClass().getName());
  }
  default Object read$get$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.read$get$1"+this.getClass().getName());
  }
  default Object mut$opt$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.mut$opt$1"+this.getClass().getName());
  }
  default Object read$opt$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.read$opt$1"+this.getClass().getName());
  }
  default Object imm$opt$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.imm$opt$1"+this.getClass().getName());
  }
  default Object read$containsKey$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.read$containsKey$1"+this.getClass().getName());
  }
  default Object mut$with$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: Map$2.mut$with$2"+this.getClass().getName());
  }
  default Object read$with$2(Object p0, Object p1){
    throw new AssertionError("Uncallable method: Map$2.read$with$2"+this.getClass().getName());
  }
  default Object mut$without$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.mut$without$1"+this.getClass().getName());
  }
  default Object read$without$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.read$without$1"+this.getClass().getName());
  }
  default Object mut$$plus_plus$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.mut$$plus_plus$1"+this.getClass().getName());
  }
  default Object read$$plus_plus$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.read$$plus_plus$1"+this.getClass().getName());
  }
  default Object mut$flow$0(){
    throw new AssertionError("Uncallable method: Map$2.mut$flow$0"+this.getClass().getName());
  }
  default Object read$flow$0(){
    throw new AssertionError("Uncallable method: Map$2.read$flow$0"+this.getClass().getName());
  }
  default Object imm$flow$0(){
    throw new AssertionError("Uncallable method: Map$2.imm$flow$0"+this.getClass().getName());
  }
  default Object mut$flow$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.mut$flow$1"+this.getClass().getName());
  }
  default Object read$flow$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.read$flow$1"+this.getClass().getName());
  }
  default Object imm$flow$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.imm$flow$1"+this.getClass().getName());
  }
  default Object read$keys$0(){
    throw new AssertionError("Uncallable method: Map$2.read$keys$0"+this.getClass().getName());
  }
  default Object read$elems$0(){
    throw new AssertionError("Uncallable method: Map$2.read$elems$0"+this.getClass().getName());
  }
  default Object mut$as$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.mut$as$1"+this.getClass().getName());
  }
  default Object read$as$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.read$as$1"+this.getClass().getName());
  }
  default Object imm$as$1(Object p0){
    throw new AssertionError("Uncallable method: Map$2.imm$as$1"+this.getClass().getName());
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
    return ((base.Flow$1)this$.read$flow$0(
)).mut$fold$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.Maps$0.instance.imm$$hash$1(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _aimpl$= (base.Str$0)p0;
    return _aimpl$;
  }

});
  }

},
new base.F$3(){
    public Object read$$hash$2(Object p0, Object p1){
    var acc$= (base.Map$2)p0;
    var ke$= (base.KeyElem$2)p1;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.OrderHash$1)((base.OrderHashBy$1)this$.read$keyOh$0(
)).imm$$hash$1(
ke$.read$key$0(
))).read$str$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var base$= (base.Str$0)p0;
    var _beqS$= (base.Block$1)p1;
    return _beqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base._InfoFreshKey$0.instance.imm$$hash$3(
acc$,
base$,
base.Nat$0Instance.instance(0L));
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var k$= (base.Str$0)p0;
    var _ceqS$= (base.Block$1)p1;
    return _ceqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return acc$.read$with$2(
k$,
((base.ToInfo$0)by$.imm$$hash$1(
ke$.imm$elem$0(
))).read$info$0(
));
  }

});
  }

});
  }

});
  }

});
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var map$= (base.Map$2)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return new base.Info$0(){
    public Object imm$match$1(Object p0){
    var i$= this;
    var _bimpl$= (base.InfoMatch$1)p0;
    return _bimpl$.imm$map$1(
map$);
  }

    public Object imm$map$0(){
    var i$= this;
    return map$;
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
}