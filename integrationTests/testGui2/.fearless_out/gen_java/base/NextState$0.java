package base;
public interface NextState$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var tanks$= (base.List$1)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Flow$1)((base.Flow$1)tanks$.imm$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var t$= (base.Tank$0)p0;
    return ((base.Point$0)t$.imm$position$0(
)).imm$move$1(
t$.imm$aiming$0(
));
  }

})).mut$list$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var danger$= (base.List$1)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Flow$1)((base.Flow$1)tanks$.imm$flow$0(
)).mut$filter$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var t$= (base.Tank$0)p0;
    return ((base.Flow$1)((base.Flow$1)danger$.read$flow$0(
)).mut$filter$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _aimpl$= (base.Point$0)p0;
    return _aimpl$.imm$$eq_eq$1(
t$.imm$position$0(
));
  }

})).mut$isEmpty$0(
);
  }

})).mut$list$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var survivors$= (base.List$1)p0;
    var _beqS$= (base.Block$1)p1;
    return _beqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Flow$1)((base.Flow$1)((base.Flow$1)survivors$.read$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _bimpl$= (base.Tank$0)p0;
    return _bimpl$.imm$position$0(
);
  }

})).mut$$plus_plus$1(
((base.Flow$1)survivors$.read$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _cimpl$= (base.Tank$0)p0;
    return ((base.Tank$0)_cimpl$.imm$move$0(
)).imm$position$0(
);
  }

}))).mut$list$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var occupied$= (base.List$1)p0;
    var _ceqS$= (base.Block$1)p1;
    return _ceqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Flow$1)((base.Flow$1)survivors$.read$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var t$= (base.Tank$0)p0;
    return this$.read$moveIfFree$2(
t$,
occupied$);
  }

})).mut$list$0(
);
  }

});
  }

});
  }

});
  }

});
  }
  default Object read$moveIfFree$2(Object p0, Object p1){
    var this$= this;
    var t$= (base.Tank$0)p0;
    var occupied$= (base.List$1)p1;
    return ((base.Bool$0)((base.Nat$0)((base.Flow$1)((base.Flow$1)occupied$.imm$flow$0(
)).mut$filter$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _dimpl$= (base.Point$0)p0;
    return _dimpl$.imm$$eq_eq$1(
t$.imm$position$0(
));
  }

})).mut$size$0(
)).read$$eq_eq$1(
base.Nat$0Instance.instance(1L))).imm$if$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return t$.imm$move$0(
);
  }

    public Object mut$else$0(){
    return t$;
  }

});
  }
  NextState$0 instance= new NextState$0(){};}