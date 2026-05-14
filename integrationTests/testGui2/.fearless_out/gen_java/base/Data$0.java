package base;
public interface Data$0{
  default Object imm$cA$0(){
    var this$= this;
    return base.Cats$0.instance.imm$$hash$2(
base.Str$0Instance.instance("Mimi"),
base.Nat$0Instance.instance(3L));
  }
  default Object imm$cB$0(){
    var this$= this;
    return base.Cats$0.instance.imm$$hash$2(
base.Str$0Instance.instance("Nori"),
base.Nat$0Instance.instance(5L));
  }
  default Object imm$cC$0(){
    var this$= this;
    return base.Cats$0.instance.imm$$hash$2(
base.Str$0Instance.instance("Puff"),
base.Nat$0Instance.instance(2L));
  }
  default Object imm$p1$0(){
    var this$= this;
    return base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(30L),
base.Str$0Instance.instance("Dr. Alice"),
base.Lists$0.instance.imm$$hash$2(
this$.imm$cA$0(
),
this$.imm$cB$0(
)));
  }
  default Object imm$p2$0(){
    var this$= this;
    return base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(25L),
base.Str$0Instance.instance("Bob"),
base.List$1.instance);
  }
  default Object imm$p3$0(){
    var this$= this;
    return base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(40L),
base.Str$0Instance.instance("Dr. Carol"),
base.Lists$0.instance.imm$$hash$1(
this$.imm$cC$0(
)));
  }
  default Object imm$ps$0(){
    var this$= this;
    return base.Lists$0.instance.imm$$hash$3(
this$.imm$p1$0(
),
this$.imm$p2$0(
),
this$.imm$p3$0(
));
  }
  default Object imm$psAllCats$0(){
    var this$= this;
    return base.Lists$0.instance.imm$$hash$2(
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(20L),
base.Str$0Instance.instance("Eve"),
base.Lists$0.instance.imm$$hash$1(
base.Cats$0.instance.imm$$hash$2(
base.Str$0Instance.instance("X"),
base.Nat$0Instance.instance(1L)))),
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(21L),
base.Str$0Instance.instance("Frank"),
base.Lists$0.instance.imm$$hash$2(
base.Cats$0.instance.imm$$hash$2(
base.Str$0Instance.instance("Y"),
base.Nat$0Instance.instance(1L)),
base.Cats$0.instance.imm$$hash$2(
base.Str$0Instance.instance("Z"),
base.Nat$0Instance.instance(1L)))));
  }
  default Object imm$psNoCats$0(){
    var this$= this;
    return base.Lists$0.instance.imm$$hash$2(
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(20L),
base.Str$0Instance.instance("Eve"),
base.List$1.instance),
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(21L),
base.Str$0Instance.instance("Frank"),
base.List$1.instance));
  }
  default Object imm$natsDup$0(){
    var this$= this;
    return base.Lists$0.instance.imm$$hash$8(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(2L),
base.Nat$0Instance.instance(3L),
base.Nat$0Instance.instance(3L),
base.Nat$0Instance.instance(2L),
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(0L));
  }
  Data$0 instance= new Data$0(){};}