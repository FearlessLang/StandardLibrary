package base;
public interface Nicer$0{
  default Object imm$$hash$2(Object p0, Object p1){
    var this$= this;
    var owner$= (base.Person$0)p0;
    var ps$= (base.List$1)p1;
    return ((base.Flow$1)ps$.imm$flow$0(
)).mut$fold$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return owner$;
  }

},
new base.F$3(){
    public Object read$$hash$2(Object p0, Object p1){
    var acc$= (base.Person$0)p0;
    var p$= (base.Person$0)p1;
    return ((base.Bool$0)((base.Nat$0)((base.List$1)p$.read$cats$0(
)).read$size$0(
)).read$$gt_eq$1(
((base.List$1)acc$.read$cats$0(
)).read$size$0(
))).imm$if$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return p$;
  }

    public Object mut$else$0(){
    return acc$;
  }

});
  }

});
  }
  Nicer$0 instance= new Nicer$0(){};}