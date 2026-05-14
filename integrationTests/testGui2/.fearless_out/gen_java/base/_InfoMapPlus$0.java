package base;
public interface _InfoMapPlus$0{
  default Object imm$$hash$2(Object p0, Object p1){
    var this$= this;
    var m1$= (base.Map$2)p0;
    var m2$= (base.Map$2)p1;
    return ((base.Flow$1)m2$.imm$flow$0(
)).mut$fold$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return m1$;
  }

},
new base.F$3(){
    public Object read$$hash$2(Object p0, Object p1){
    var acc$= (base.Map$2)p0;
    var ke$= (base.KeyElem$2)p1;
    return ((base.Opt$1)acc$.imm$opt$1(
ke$.read$key$0(
))).mut$match$1(
new base.OptMatch$2(){
    public Object mut$empty$0(){
    return acc$.read$with$2(
ke$.read$key$0(
),
ke$.imm$elem$0(
));
  }

    public Object mut$some$1(Object p0){
    var v$= (base.Info$0)p0;
    return acc$.read$with$2(
ke$.read$key$0(
),
v$.imm$$plus$1(
ke$.imm$elem$0(
)));
  }

});
  }

});
  }
  _InfoMapPlus$0 instance= new _InfoMapPlus$0(){};}