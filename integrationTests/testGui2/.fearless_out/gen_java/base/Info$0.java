package base;
public interface Info$0 extends base.Sealed$0, base.DataType$2, base._AssertImmEmpty$0, base.Extensible$1, base.OrderHash$1, base.Order$1, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object read$close$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Info$0)p0;
    return _aimpl$;
  }
  default Object read$close$0(){
    var this$= this;
    return this$;
  }
  default Object mut$close$0(){
    var this$= this;
    return this$;
  }
  default Object read$info$0(){
    var this$= this;
    return this$.read$imm$0(
);
  }
  default Object read$imm$0(){
    var this$= this;
    return base.Info$0.instance;
  }
  default Object read$hash$0(){
    var this$= this;
    return ((base.Str$0)this$.read$str$0(
)).read$hash$0(
);
  }
  default Object read$cmp$3(Object p0, Object p1, Object p2){
    var this$= this;
    var a$= (base.Info$0)p0;
    var b$= (base.Info$0)p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.Str$0)a$.read$str$0(
)).read$$lt_eq_gt$2(
b$.read$str$0(
),
m$);
  }
  default Object imm$msg$0(){
    var this$= this;
    return base.Str$0Instance.instance("");
  }
  default Object imm$list$0(){
    var this$= this;
    return base.List$1.instance;
  }
  default Object imm$map$0(){
    var this$= this;
    return base.Maps$0.instance.imm$$hash$1(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.Str$0)p0;
    return _bimpl$;
  }

});
  }
  default Object imm$isEmpty$0(){
    var this$= this;
    return ((base.Bool$0)((base.Bool$0)((base.Str$0)this$.imm$msg$0(
)).imm$isEmpty$0(
)).imm$and$1(
((base.List$1)this$.imm$list$0(
)).read$isEmpty$0(
))).imm$and$1(
((base.Map$2)this$.imm$map$0(
)).read$isEmpty$0(
));
  }
  default Object imm$match$1(Object p0){
    var this$= this;
    var m$= (base.InfoMatch$1)p0;
    return m$.imm$empty$0(
);
  }
  default Object read$str$0(){
    var this$= this;
    return ((base.Info$0)this$.read$imm$0(
)).imm$match$1(
base._InfoToStr$0.instance);
  }
  default Object imm$$plus$1(Object p0){
    var this$= this;
    var other$= (base.Info$0)p0;
    return this$.imm$match$1(
new base.InfoMatch$1(){
    public Object imm$empty$0(){
    return other$;
  }

    public Object imm$msg$1(Object p0){
    var s1$= (base.Str$0)p0;
    return other$.imm$match$1(
new base.InfoMatch$1(){
    public Object imm$empty$0(){
    return this$;
  }

    public Object imm$msg$1(Object p0){
    var s2$= (base.Str$0)p0;
    return base.Infos$0.instance.imm$msg$1(
s1$.imm$$or$1(
s2$));
  }

    public Object imm$list$1(Object p0){
    var l2$= (base.List$1)p0;
    return base.Infos$0.instance.imm$map$4(
base.Str$0Instance.instance("msg"),
s1$,
base.Str$0Instance.instance("list"),
l2$.read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _cimpl$= (base.Info$0)p0;
    return _cimpl$;
  }

}));
  }

    public Object imm$map$1(Object p0){
    return ((base.Info$0)base.Infos$0.instance.imm$map$2(
base.Str$0Instance.instance("msg"),
s1$)).imm$$plus$1(
other$);
  }

});
  }

    public Object imm$list$1(Object p0){
    var l1$= (base.List$1)p0;
    return other$.imm$match$1(
new base.InfoMatch$1(){
    public Object imm$empty$0(){
    return this$;
  }

    public Object imm$msg$1(Object p0){
    var s2$= (base.Str$0)p0;
    return base.Infos$0.instance.imm$map$4(
base.Str$0Instance.instance("msg"),
s2$,
base.Str$0Instance.instance("list"),
l1$.read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _dimpl$= (base.Info$0)p0;
    return _dimpl$;
  }

}));
  }

    public Object imm$list$1(Object p0){
    var l2$= (base.List$1)p0;
    return ((base.List$1)l1$.read$$plus_plus$1(
l2$)).read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _eimpl$= (base.Info$0)p0;
    return _eimpl$;
  }

});
  }

    public Object imm$map$1(Object p0){
    return ((base.Info$0)base.Infos$0.instance.imm$map$2(
base.Str$0Instance.instance("list"),
l1$.read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _fimpl$= (base.Info$0)p0;
    return _fimpl$;
  }

}))).imm$$plus$1(
other$);
  }

});
  }

    public Object imm$map$1(Object p0){
    var m1$= (base.Map$2)p0;
    return other$.imm$match$1(
new base.InfoMatch$1(){
    public Object imm$empty$0(){
    return this$;
  }

    public Object imm$msg$1(Object p0){
    var s2$= (base.Str$0)p0;
    return ((base.Map$2)base._InfoMapPlus$0.instance.imm$$hash$2(
m1$,
((base.Info$0)base.Infos$0.instance.imm$map$2(
base.Str$0Instance.instance("msg"),
s2$)).imm$map$0(
))).read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _gimpl$= (base.Info$0)p0;
    return _gimpl$;
  }

});
  }

    public Object imm$list$1(Object p0){
    var l2$= (base.List$1)p0;
    return ((base.Map$2)base._InfoMapPlus$0.instance.imm$$hash$2(
m1$,
((base.Info$0)base.Infos$0.instance.imm$map$2(
base.Str$0Instance.instance("list"),
l2$.read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _himpl$= (base.Info$0)p0;
    return _himpl$;
  }

}))).imm$map$0(
))).read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _jimpl$= (base.Info$0)p0;
    return _jimpl$;
  }

});
  }

    public Object imm$map$1(Object p0){
    var m2$= (base.Map$2)p0;
    return ((base.Map$2)base._InfoMapPlus$0.instance.imm$$hash$2(
m1$,
m2$)).read$info$1(
new base.ToInfoBy$1(){
    public Object imm$$hash$1(Object p0){
    var _kimpl$= (base.Info$0)p0;
    return _kimpl$;
  }

});
  }

});
  }

});
  }
  Info$0 instance= new Info$0(){};}