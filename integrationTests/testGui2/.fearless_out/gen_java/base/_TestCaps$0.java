package base;
public interface _TestCaps$0{
  default Object imm$$hash$2(Object p0, Object p1){
    var this$= this;
    var sys$= (base.System$0)p0;
    var ts$= (base.EList$1)p1;
    return new RTestCap$0(){
    public Object mut$check$1(Object p0){
    var self$= this;
    var c$= (base.Check$0)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return c$.imm$_fetchTrace$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.CapTry$0)sys$.mut$try$0(
)).mut$currentStackTrace$0(
);
  }

});
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var st$= (base.List$1)p0;
    var _aeqS$= (base.Block$1)p1;
    return ((base.Block$1)((base.Block$1)_aeqS$.mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.UnitTestLog$0.instance.imm$log$1(
base._PlanLine$0.instance.imm$$hash$3(
base.Str$0Instance.instance("RUN"),
st$,
base.Nat$0Instance.instance(2L)));
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ts$.mut$add$1(
base.CheckInfos$0.instance.read$$hash$2(
new base.CheckSys$0(){
    public Object imm$_fetchTrace$1(Object p0){
    var f$= (base.MF$1)p0;
    return c$.imm$_fetchTrace$1(
f$);
  }

    public Object imm$$hash$1(Object p0){
    return c$.imm$$hash$0(
);
  }

},
st$));
  }

})).mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return self$;
  }

});
  }

});
  }

    public Object mut$checkWithSys$1(Object p0){
    var self$= this;
    var c$= (base.CheckSys$0)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return c$.imm$_fetchTrace$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.CapTry$0)sys$.mut$try$0(
)).mut$currentStackTrace$0(
);
  }

});
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var st$= (base.List$1)p0;
    var _beqS$= (base.Block$1)p1;
    return ((base.Block$1)((base.Block$1)_beqS$.mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.UnitTestLog$0.instance.imm$log$1(
base._PlanLine$0.instance.imm$$hash$3(
base.Str$0Instance.instance("RUN"),
st$,
base.Nat$0Instance.instance(2L)));
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ts$.mut$add$1(
base.CheckInfos$0.instance.read$$hash$2(
c$,
st$));
  }

})).mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return self$;
  }

});
  }

});
  }

    public Object mut$disabledCheck$1(Object p0){
    var self$= this;
    var c$= (base.Check$0)p0;
    return base.Block$0.instance.imm$$hash$2(
base.UnitTestLog$0.instance.imm$log$1(
base._PlanLine$0.instance.imm$$hash$3(
base.Str$0Instance.instance("DISABLED"),
c$.imm$_fetchTrace$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.CapTry$0)sys$.mut$try$0(
)).mut$currentStackTrace$0(
);
  }

}),
base.Nat$0Instance.instance(1L))),
self$);
  }

    public Object mut$disabledCheckWithSys$1(Object p0){
    var self$= this;
    var c$= (base.CheckSys$0)p0;
    return base.Block$0.instance.imm$$hash$2(
base.UnitTestLog$0.instance.imm$log$1(
base._PlanLine$0.instance.imm$$hash$3(
base.Str$0Instance.instance("DISABLED"),
c$.imm$_fetchTrace$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.CapTry$0)sys$.mut$try$0(
)).mut$currentStackTrace$0(
);
  }

}),
base.Nat$0Instance.instance(1L))),
self$);
  }

    public Object mut$disabledTest$1(Object p0){
    var self$= this;
    return base.Block$0.instance.imm$$hash$2(
base.UnitTestLog$0.instance.imm$log$1(
base._PlanLine$0.instance.imm$$hash$3(
base.Str$0Instance.instance("DISABLED"),
((base.CapTry$0)sys$.mut$try$0(
)).mut$currentStackTrace$0(
),
base.Nat$0Instance.instance(1L))),
self$);
  }

    public Object mut$run$0(){
    var self$= this;
    return ((base.Flow$1)ts$.mut$seqFlow$0(
)).mut$forEach$1(
new base.MF$2(){
    public Object mut$$hash$1(Object p0){
    var ci$= (base.CheckInfo$0)p0;
    return base.UnitTestLog$0.instance.imm$log$1(
base._AddToLog$0.instance.imm$$hash$3(
ci$.imm$stackTrace$0(
),
((base.CapTry$0)sys$.mut$try$0(
)).mut$$hash$2(
((base.ToIso$1)sys$.mut$iso$0(
)).mut$close$0(
),
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var t$= (base.System$0)p0;
    return ((base.CheckSys$0)ci$.imm$check$0(
)).imm$$hash$1(
t$);
  }

}),
base.Nat$0Instance.instance(2L)));
  }

});
  }

};
  }
  _TestCaps$0 instance= new _TestCaps$0(){};}