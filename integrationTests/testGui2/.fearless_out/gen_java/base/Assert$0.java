package base;
public interface Assert$0 extends base.Sealed$0{
  default Object imm$$bang$1(Object p0){
    var this$= this;
    var assertion$= (base.Bool$0)p0;
    return this$.imm$$bang$3(
assertion$,
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("");
  }

},
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.Void$0.instance;
  }

});
  }
  default Object imm$$bang$2(Object p0, Object p1){
    var this$= this;
    var assertion$= (base.Bool$0)p0;
    var msg$= (base.LazyInfo$0)p1;
    return this$.imm$$bang$3(
assertion$,
msg$,
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.Void$0.instance;
  }

});
  }
  default Object imm$$bang$3(Object p0, Object p1, Object p2){
    var this$= this;
    var assertion$= (base.Bool$0)p0;
    var msg$= (base.LazyInfo$0)p1;
    var cont$= (base.MF$1)p2;
    return assertion$.imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return cont$.mut$$hash$0(
);
  }

    public Object mut$else$0(){
    return this$.imm$_fail$1(
msg$.read$$hash$0(
));
  }

});
  }
  default Object imm$_fail$0(){
    var this$= this;
    return base.Error$0.instance.imm$nonDeterministic$1(
base.Str$0Instance.instance("Assertion failure."));
  }
  default Object imm$_fail$1(Object p0){
    var this$= this;
    var msg$= (base.ToInfo$0)p0;
    return base.Error$0.instance.imm$nonDeterministicInfo$1(
((base.Info$0)base.Str$0Instance.instance("Assertion failure.").read$info$0(
)).imm$$plus$1(
msg$.read$info$0(
)));
  }
  default Object imm$msg$1(Object p0){
    var this$= this;
    var msg$= (base.Str$0)p0;
    return this$.imm$_fail$1(
msg$);
  }
  default Object imm$expectedActual$4(Object p0, Object p1, Object p2, Object p3){
    var this$= this;
    var assertion$= (base.Bool$0)p0;
    var msg$= (base.LazyInfo$0)p1;
    var expected$= (base.ToStr$0)p2;
    var actual$= (base.ToStr$0)p3;
    return assertion$.imm$$q$1(
new base.ThenElse$1(){
    public Object mut$then$0(){
    return base.Void$0.instance;
  }

    public Object mut$else$0(){
    return this$.imm$_fail$1(
((base.Info$0)((base.Info$0)((base.ToInfo$0)msg$.read$$hash$0(
)).read$info$0(
)).imm$$plus$1(
((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Expected: ").imm$$plus$1(
expected$)).imm$$or$1(
base.Str$0Instance.instance("Actual: "))).imm$$plus$1(
actual$)).read$info$0(
))).imm$$plus$1(
base.Infos$0.instance.imm$map$4(
base.Str$0Instance.instance("expected"),
expected$.read$str$0(
),
base.Str$0Instance.instance("actual"),
actual$.read$str$0(
))));
  }

});
  }
  Assert$0 instance= new Assert$0(){};}