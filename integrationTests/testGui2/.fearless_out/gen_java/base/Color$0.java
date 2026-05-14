package base;
public interface Color$0 extends base.DataType$2, base.Extensible$1, base.OrderHash$1, base.Order$1, base.ToImm$1, base.ToInfo$0, base.ToStr$0, base.WidenTo$1{
  default Object read$close$0(){
    var this$= this;
    return this$;
  }
  default Object mut$close$0(){
    var this$= this;
    return this$;
  }
  default Object read$close$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Color$0)p0;
    return _aimpl$;
  }
  default Object read$red$0(){
    var this$= this;
    return base.Red$0.instance;
  }
  default Object read$green$0(){
    var this$= this;
    return base.Green$0.instance;
  }
  default Object read$blue$0(){
    var this$= this;
    return base.Blue$0.instance;
  }
  default Object read$alpha$0(){
    var this$= this;
    return base.Alpha$0.instance.imm$opaque$0(
);
  }
  default Object imm$$hash$4(Object p0, Object p1, Object p2, Object p3){
    var this$= this;
    var red$= (base.Red$0)p0;
    var green$= (base.Green$0)p1;
    var blue$= (base.Blue$0)p2;
    var alpha$= (base.Alpha$0)p3;
    return new base.Color$0(){
    public Object read$red$0(){
    return red$;
  }

    public Object read$green$0(){
    return green$;
  }

    public Object read$blue$0(){
    return blue$;
  }

    public Object read$alpha$0(){
    return alpha$;
  }

};
  }
  default Object read$str$0(){
    var this$= this;
    return ((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Color[").imm$$plus$1(
this$.read$red$0(
))).imm$$plus$1(
base.Str$0Instance.instance(", "))).imm$$plus$1(
this$.read$green$0(
))).imm$$plus$1(
base.Str$0Instance.instance(", "))).imm$$plus$1(
this$.read$blue$0(
))).imm$$plus$1(
base.Str$0Instance.instance(", "))).imm$$plus$1(
this$.read$alpha$0(
))).imm$$plus$1(
base.Str$0Instance.instance("]"));
  }
  default Object read$hash$0(){
    var this$= this;
    return ((base.Nat$0)((base.Nat$0)((base.Nat$0)((base.Red$0)this$.read$red$0(
)).read$hash$0(
)).imm$hashWith$1(
((base.Green$0)this$.read$green$0(
)).read$hash$0(
))).imm$hashWith$1(
((base.Blue$0)this$.read$blue$0(
)).read$hash$0(
))).imm$hashWith$1(
((base.Alpha$0)this$.read$alpha$0(
)).read$hash$0(
));
  }
  default Object read$cmp$3(Object p0, Object p1, Object p2){
    var this$= this;
    var a$= (base.Color$0)p0;
    var b$= (base.Color$0)p1;
    var m$= (base.OrderMatch$1)p2;
    return ((base.Str$0)a$.read$str$0(
)).read$$lt_eq_gt$2(
b$.read$str$0(
),
m$);
  }
  default Object read$info$0(){
    var this$= this;
    return ((base.Str$0)this$.read$str$0(
)).read$info$0(
);
  }
  default Object read$imm$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
this$.read$red$0(
),
this$.read$green$0(
),
this$.read$blue$0(
),
this$.read$alpha$0(
));
  }
  default Object imm$transparent$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance,
base.Green$0.instance,
base.Blue$0.instance,
base.Alpha$0.instance.imm$transparent$0(
));
  }
  default Object imm$boringGray$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(160L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(160L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(160L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$voidBlack$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(0L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(0L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(0L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$ghostWhite$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(245L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(245L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(245L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$rubberDuck$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(255L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(220L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(40L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$bananaBruise$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(126L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(96L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(32L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$dangerTomato$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(230L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(64L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(48L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$lavaOrange$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(255L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(96L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(16L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$wizardPurple$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(116L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(64L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(196L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$sleepyBlue$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(72L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(120L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(210L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$deepNapBlue$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(24L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(48L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(112L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$goblinGreen$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(40L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(170L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(64L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$toxicSlime$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(120L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(255L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(32L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$alienMint$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(128L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(255L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(190L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$swampTea$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(82L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(112L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(54L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$rustyRobot$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(174L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(86L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(44L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$dustyRose$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(196L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(120L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(132L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$blueberrySoup$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(64L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(72L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(176L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$terminalGreen$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(32L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(220L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(80L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$panicPink$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(255L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(72L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(168L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$cardboard$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(178L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(136L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(86L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  default Object imm$moonCheese$0(){
    var this$= this;
    return base.Color$0.instance.imm$$hash$4(
base.Red$0.instance.read$$hash$1(
base.Nat$0Instance.instance(236L).imm$byte$0(
)),
base.Green$0.instance.read$$hash$1(
base.Nat$0Instance.instance(226L).imm$byte$0(
)),
base.Blue$0.instance.read$$hash$1(
base.Nat$0Instance.instance(148L).imm$byte$0(
)),
base.Alpha$0.instance.imm$opaque$0(
));
  }
  Color$0 instance= new Color$0(){};}