package base;
public interface Tanks$0{
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    var this$= this;
    var heading$= (base.Direction$0)p0;
    var aiming$= (base.Direction$0)p1;
    var position$= (base.Point$0)p2;
    return new base.Tank$0(){
    public Object imm$heading$0(){
    var self$= this;
    return heading$;
  }

    public Object imm$aiming$0(){
    var self$= this;
    return aiming$;
  }

    public Object imm$position$0(){
    var self$= this;
    return position$;
  }

    public Object read$str$0(){
    var self$= this;
    return ((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("").imm$$or$1(
self$.imm$repr1$0(
))).imm$$or$1(
self$.imm$repr2$0(
))).imm$$or$1(
self$.imm$repr3$0(
))).imm$$or$0(
);
  }

};
  }
  Tanks$0 instance= new Tanks$0(){};}