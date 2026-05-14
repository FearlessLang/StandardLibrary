package base;
public interface Infos$0 extends base.Sealed$0{
  default Object imm$msg$1(Object p0){
    var this$= this;
    var str$= (base.Str$0)p0;
    return ((base.Bool$0)str$.imm$isEmpty$0(
)).imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return base.Info$0.instance;
  }

    public Object mut$else$0(){
    return new base.Info$0(){
    public Object imm$match$1(Object p0){
    var i$= this;
    var _aimpl$= (base.InfoMatch$1)p0;
    return _aimpl$.imm$msg$1(
str$);
  }

    public Object imm$msg$0(){
    var i$= this;
    return str$;
  }

    public Object read$imm$0(){
    var i$= this;
    return i$;
  }

};
  }

});
  }
  default Object imm$list$1(Object p0){
    var this$= this;
    var i1$= (base.ToInfo$0)p0;
    return ((base.List$1)base.Lists$0.instance.imm$$hash$1(
i1$.read$info$0(
))).read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.Info$0)p0;
    return _bimpl$;
  }

});
  }
  default Object imm$list$2(Object p0, Object p1){
    var this$= this;
    var i1$= (base.ToInfo$0)p0;
    var i2$= (base.ToInfo$0)p1;
    return ((base.List$1)base.Lists$0.instance.imm$$hash$2(
i1$.read$info$0(
),
i2$.read$info$0(
))).read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _cimpl$= (base.Info$0)p0;
    return _cimpl$;
  }

});
  }
  default Object imm$map$2(Object p0, Object p1){
    var this$= this;
    var k1$= (base.Str$0)p0;
    var e1$= (base.ToInfo$0)p1;
    return ((base.Map$2)base.Maps$0.instance.imm$$hash$3(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _dimpl$= (base.Str$0)p0;
    return _dimpl$;
  }

},
k1$,
e1$.read$info$0(
))).read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _eimpl$= (base.Info$0)p0;
    return _eimpl$;
  }

});
  }
  default Object imm$map$4(Object p0, Object p1, Object p2, Object p3){
    var this$= this;
    var k1$= (base.Str$0)p0;
    var e1$= (base.ToInfo$0)p1;
    var k2$= (base.Str$0)p2;
    var e2$= (base.ToInfo$0)p3;
    return ((base.Map$2)base.Maps$0.instance.imm$$hash$5(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _fimpl$= (base.Str$0)p0;
    return _fimpl$;
  }

},
k1$,
e1$.read$info$0(
),
k2$,
e2$.read$info$0(
))).read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _gimpl$= (base.Info$0)p0;
    return _gimpl$;
  }

});
  }
  default Object imm$map$6(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5){
    var this$= this;
    var k1$= (base.Str$0)p0;
    var e1$= (base.ToInfo$0)p1;
    var k2$= (base.Str$0)p2;
    var e2$= (base.ToInfo$0)p3;
    var k3$= (base.Str$0)p4;
    var e3$= (base.ToInfo$0)p5;
    return ((base.Map$2)base.Maps$0.instance.imm$$hash$7(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _himpl$= (base.Str$0)p0;
    return _himpl$;
  }

},
k1$,
e1$.read$info$0(
),
k2$,
e2$.read$info$0(
),
k3$,
e3$.read$info$0(
))).read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _jimpl$= (base.Info$0)p0;
    return _jimpl$;
  }

});
  }
  default Object imm$map$8(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7){
    var this$= this;
    var k1$= (base.Str$0)p0;
    var e1$= (base.ToInfo$0)p1;
    var k2$= (base.Str$0)p2;
    var e2$= (base.ToInfo$0)p3;
    var k3$= (base.Str$0)p4;
    var e3$= (base.ToInfo$0)p5;
    var k4$= (base.Str$0)p6;
    var e4$= (base.ToInfo$0)p7;
    return ((base.Map$2)base.Maps$0.instance.imm$$hash$9(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _kimpl$= (base.Str$0)p0;
    return _kimpl$;
  }

},
k1$,
e1$.read$info$0(
),
k2$,
e2$.read$info$0(
),
k3$,
e3$.read$info$0(
),
k4$,
e4$.read$info$0(
))).read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _mimpl$= (base.Info$0)p0;
    return _mimpl$;
  }

});
  }
  default Object imm$map$10(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9){
    var this$= this;
    var k1$= (base.Str$0)p0;
    var e1$= (base.ToInfo$0)p1;
    var k2$= (base.Str$0)p2;
    var e2$= (base.ToInfo$0)p3;
    var k3$= (base.Str$0)p4;
    var e3$= (base.ToInfo$0)p5;
    var k4$= (base.Str$0)p6;
    var e4$= (base.ToInfo$0)p7;
    var k5$= (base.Str$0)p8;
    var e5$= (base.ToInfo$0)p9;
    return ((base.Map$2)base.Maps$0.instance.imm$$hash$11(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _nimpl$= (base.Str$0)p0;
    return _nimpl$;
  }

},
k1$,
e1$.read$info$0(
),
k2$,
e2$.read$info$0(
),
k3$,
e3$.read$info$0(
),
k4$,
e4$.read$info$0(
),
k5$,
e5$.read$info$0(
))).read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _pimpl$= (base.Info$0)p0;
    return _pimpl$;
  }

});
  }
  Infos$0 instance= new Infos$0(){};}