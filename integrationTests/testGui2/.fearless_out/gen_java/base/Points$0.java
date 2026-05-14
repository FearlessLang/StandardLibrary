package base;
public interface Points$0{
  default Object imm$$hash$2(Object p0, Object p1){
    var this$= this;
    var x$= (base.Nat$0)p0;
    var y$= (base.Nat$0)p1;
    return new Point$0(){
    public Object imm$x$0(){
    var self$= this;
    return x$;
  }

    public Object imm$y$0(){
    var self$= this;
    return y$;
  }

    public Object imm$$plus$1(Object p0){
    var self$= this;
    var other$= (base.Point$0)p0;
    return base.Points$0.instance.imm$$hash$2(
((base.Nat$0)other$.imm$x$0(
)).imm$$plus$1(
x$),
((base.Nat$0)other$.imm$y$0(
)).imm$$plus$1(
y$));
  }

    public Object imm$move$1(Object p0){
    var self$= this;
    var d$= (base.Direction$0)p0;
    return d$.read$match$1(
new base.DirectionMatch$1(){
    public Object mut$north$0(){
    return base.Points$0.instance.imm$$hash$2(
x$.imm$$dash$1(
base.Nat$0Instance.instance(1L)),
y$);
  }

    public Object mut$east$0(){
    return base.Points$0.instance.imm$$hash$2(
x$,
y$.imm$$plus$1(
base.Nat$0Instance.instance(1L)));
  }

    public Object mut$south$0(){
    return base.Points$0.instance.imm$$hash$2(
x$.imm$$plus$1(
base.Nat$0Instance.instance(1L)),
y$);
  }

    public Object mut$west$0(){
    return base.Points$0.instance.imm$$hash$2(
x$,
y$.imm$$dash$1(
base.Nat$0Instance.instance(1L)));
  }

});
  }

    public Object imm$$eq_eq$1(Object p0){
    var self$= this;
    var other$= (base.Point$0)p0;
    return ((base.Bool$0)((base.Nat$0)other$.imm$x$0(
)).read$$eq_eq$1(
x$)).imm$and$1(
((base.Nat$0)other$.imm$y$0(
)).read$$eq_eq$1(
y$));
  }

    public Object read$str$0(){
    var self$= this;
    return ((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("[x=").imm$$plus$1(
x$)).imm$$plus$1(
base.Str$0Instance.instance(", y="))).imm$$plus$1(
y$)).imm$$plus$1(
base.Str$0Instance.instance("]"));
  }

};
  }
  Points$0 instance= new Points$0(){};}