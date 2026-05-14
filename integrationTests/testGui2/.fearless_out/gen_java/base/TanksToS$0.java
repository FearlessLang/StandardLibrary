package base;
public interface TanksToS$0 extends base.F$2{
  default Object read$newLine$0(){
    var this$= this;
    return ((base.Flow$1)((base.Flow$1)base.Nat$0Instance.instance(0L).imm$$tilde_tilde_eq$1(
base.Nat$0Instance.instance(10L))).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    return base.Vars$0.instance.imm$$hash$1(
base.Str$0Instance.instance("      "));
  }

})).mut$list$0(
);
  }
  default Object read$$hash$1(Object p0){
    var this$= this;
    var ts$= (base.List$1)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Flow$1)((base.Flow$1)base.Nat$0Instance.instance(0L).imm$$tilde_tilde_eq$1(
base.Nat$0Instance.instance(30L))).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    return this$.read$newLine$0(
);
  }

})).mut$list$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var res$= (base.List$1)p0;
    var _aeqS$= (base.Block$1)p1;
    return ((base.Block$1)_aeqS$.mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Flow$1)ts$.imm$flow$0(
)).mut$forEach$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var t$= (base.Tank$0)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Point$0)t$.imm$position$0(
)).imm$x$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var x$= (base.Nat$0)p0;
    var _beqS$= (base.Block$1)p1;
    return _beqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Point$0)t$.imm$position$0(
)).imm$y$0(
);
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var y$= (base.Nat$0)p0;
    var _ceqS$= (base.Block$1)p1;
    return ((base.Block$1)((base.Block$1)((base.Block$1)((base.Block$1)((base.BlockIf$1)((base.Block$1)((base.BlockIf$1)_ceqS$.mut$if$1(
new base.F$1(){
    public Object read$$hash$0(){
    return ((base.Bool$0)x$.read$inRange$2(
base.Nat$0Instance.instance(0L),
base.Nat$0Instance.instance(10L))).imm$not$0(
);
  }

})).mut$done$0(
)).mut$if$1(
new base.F$1(){
    public Object read$$hash$0(){
    return ((base.Bool$0)y$.read$inRange$2(
base.Nat$0Instance.instance(0L),
base.Nat$0Instance.instance(10L))).imm$not$0(
);
  }

})).mut$done$0(
)).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Var$1)((base.List$1)res$.mut$get$1(
y$.imm$$star$1(
base.Nat$0Instance.instance(3L)))).mut$get$1(
x$)).mut$set$1(
t$.imm$repr1$0(
));
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Var$1)((base.List$1)res$.mut$get$1(
((base.Nat$0)y$.imm$$star$1(
base.Nat$0Instance.instance(3L))).imm$$plus$1(
base.Nat$0Instance.instance(1L)))).mut$get$1(
x$)).mut$set$1(
t$.imm$repr2$0(
));
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Var$1)((base.List$1)res$.mut$get$1(
((base.Nat$0)y$.imm$$star$1(
base.Nat$0Instance.instance(3L))).imm$$plus$1(
base.Nat$0Instance.instance(2L)))).mut$get$1(
x$)).mut$set$1(
t$.imm$repr3$0(
));
  }

})).mut$done$0(
);
  }

});
  }

});
  }

});
  }

})).mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Flow$1)((base.Flow$1)res$.read$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _aimpl$= (base.List$1)p0;
    return ((base.Flow$1)((base.Flow$1)_aimpl$.read$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _bimpl$= (base.Var$1)p0;
    return _bimpl$.read$get$0(
);
  }

})).mut$join$1(
base.Str$0Instance.instance(""));
  }

})).mut$join$1(
base.Str$0Instance.instance("").imm$$or$0(
));
  }

});
  }

});
  }
  TanksToS$0 instance= new TanksToS$0(){};}