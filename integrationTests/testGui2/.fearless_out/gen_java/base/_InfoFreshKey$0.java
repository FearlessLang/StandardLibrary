package base;
public interface _InfoFreshKey$0{
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    var this$= this;
    var m$= (base.Map$2)p0;
    var base$= (base.Str$0)p1;
    var i$= (base.Nat$0)p2;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Bool$0)i$.read$$eq_eq$1(
base.Nat$0Instance.instance(0L))).imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return base$;
  }

    public Object mut$else$0(){
    return ((base.Str$0)((base.Str$0)base$.imm$$plus$1(
base.Str$0Instance.instance("#"))).imm$$plus$1(
i$)).read$str$0(
);
  }

});
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var k$= (base.Str$0)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Bool$0)m$.read$containsKey$1(
k$)).imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return this$.imm$$hash$3(
m$,
base$,
i$.imm$$plus$1(
base.Nat$0Instance.instance(1L)));
  }

    public Object mut$else$0(){
    return k$;
  }

});
  }

});
  }

});
  }
  _InfoFreshKey$0 instance= new _InfoFreshKey$0(){};}