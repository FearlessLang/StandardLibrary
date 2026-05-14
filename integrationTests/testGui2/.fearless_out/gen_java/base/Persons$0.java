package base;
public interface Persons$0{
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    var this$= this;
    var age$= (base.Nat$0)p0;
    var name$= (base.Str$0)p1;
    var cats$= (base.List$1)p2;
    return new Person$0(){
    public Object read$name$0(){
    var self$= this;
    return name$;
  }

    public Object read$age$0(){
    var self$= this;
    return age$;
  }

    public Object read$cats$0(){
    var self$= this;
    return cats$;
  }

    public Object read$cmp$3(Object p0, Object p1, Object p2){
    var self$= this;
    var p1$= (base.Person$0)p0;
    var p2$= (base.Person$0)p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.Nat$0)p1$.read$age$0(
)).read$$lt_eq_gt$2(
p2$.read$age$0(
),
m$.mut$$and_and$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Str$0)p1$.read$name$0(
)).read$$lt_eq_gt$2(
p2$.read$name$0(
),
m$);
  }

}));
  }

    public Object read$hash$0(){
    var self$= this;
    return ((base.Nat$0)age$.read$hash$0(
)).imm$hashWith$1(
name$.read$hash$0(
));
  }

    public Object read$str$0(){
    var self$= this;
    return ((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Person[age=").imm$$plus$1(
age$)).imm$$plus$1(
base.Str$0Instance.instance(", name="))).imm$$plus$1(
name$)).imm$$plus$1(
base.Str$0Instance.instance(", cats="))).imm$$plus$1(
cats$.read$size$0(
))).imm$$plus$1(
base.Str$0Instance.instance("]"));
  }

    public Object read$close$0(){
    var self$= this;
    return self$;
  }

    public Object read$close$1(Object p0){
    var self$= this;
    var _aimpl$= (base.Person$0)p0;
    return _aimpl$;
  }

};
  }
  Persons$0 instance= new Persons$0(){};}