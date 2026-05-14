package base;
public interface OrderHash$1 extends base.Order$1, base.ToStr$0{
  default Object read$hash$0(){
    throw new AssertionError("Uncallable method: OrderHash$1.read$hash$0"+this.getClass().getName());
  }
  default Object read$close$1(Object p0){
    throw new AssertionError("Uncallable method: OrderHash$1.read$close$1"+this.getClass().getName());
  }
  default Object read$assertEq$1(Object p0){
    var this$= this;
    var expected$= p0;
    return this$.read$assertEq$2(
expected$,
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  default Object read$assertEq$2(Object p0, Object p1){
    var this$= this;
    var expected$= p0;
    var msg$= (base.LazyInfo$0)p1;
    return base.Assert$0.instance.imm$expectedActual$4(
this$.read$$eq_eq$1(
expected$),
msg$,
this$.read$close$1(
expected$),
this$);
  }
  default Object read$assertNe$1(Object p0){
    var this$= this;
    var expected$= p0;
    return this$.read$assertNe$2(
expected$,
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  default Object read$assertNe$2(Object p0, Object p1){
    var this$= this;
    var expected$= p0;
    var msg$= (base.LazyInfo$0)p1;
    return base.Assert$0.instance.imm$$bang$2(
this$.read$$bang_eq$1(
expected$),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Info$0)((base.ToInfo$0)msg$.read$$hash$0(
)).read$info$0(
)).imm$$plus$1(
((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Unexpected equality between").imm$$or$1(
base.Str$0Instance.instance("Expected: "))).imm$$plus$1(
this$.read$close$1(
expected$))).imm$$or$1(
base.Str$0Instance.instance("Actual: "))).imm$$plus$1(
this$)).read$info$0(
));
  }

});
  }
  default Object read$assertLt$1(Object p0){
    var this$= this;
    var x$= p0;
    return this$.read$assertLt$2(
x$,
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  default Object read$assertLt$2(Object p0, Object p1){
    var this$= this;
    var x$= p0;
    var msg$= (base.LazyInfo$0)p1;
    return base.Assert$0.instance.imm$$bang$2(
this$.read$$lt$1(
x$),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Info$0)((base.ToInfo$0)msg$.read$$hash$0(
)).read$info$0(
)).imm$$plus$1(
((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Expected: ").imm$$plus$1(
this$)).imm$$or$1(
base.Str$0Instance.instance(" < "))).imm$$plus$1(
this$.read$close$1(
x$))).read$info$0(
));
  }

});
  }
  default Object read$assertLe$1(Object p0){
    var this$= this;
    var x$= p0;
    return this$.read$assertLe$2(
x$,
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  default Object read$assertLe$2(Object p0, Object p1){
    var this$= this;
    var x$= p0;
    var msg$= (base.LazyInfo$0)p1;
    return base.Assert$0.instance.imm$$bang$2(
this$.read$$lt_eq$1(
x$),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Info$0)((base.ToInfo$0)msg$.read$$hash$0(
)).read$info$0(
)).imm$$plus$1(
((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Expected: ").imm$$plus$1(
this$)).imm$$or$1(
base.Str$0Instance.instance(" <= "))).imm$$plus$1(
this$.read$close$1(
x$))).read$info$0(
));
  }

});
  }
  default Object read$assertGt$1(Object p0){
    var this$= this;
    var x$= p0;
    return this$.read$assertGt$2(
x$,
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  default Object read$assertGt$2(Object p0, Object p1){
    var this$= this;
    var x$= p0;
    var msg$= (base.LazyInfo$0)p1;
    return base.Assert$0.instance.imm$$bang$2(
this$.read$$gt$1(
x$),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Info$0)((base.ToInfo$0)msg$.read$$hash$0(
)).read$info$0(
)).imm$$plus$1(
((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Expected: ").imm$$plus$1(
this$)).imm$$or$1(
base.Str$0Instance.instance(" > "))).imm$$plus$1(
this$.read$close$1(
x$))).read$info$0(
));
  }

});
  }
  default Object read$assertGe$1(Object p0){
    var this$= this;
    var x$= p0;
    return this$.read$assertGe$2(
x$,
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  default Object read$assertGe$2(Object p0, Object p1){
    var this$= this;
    var x$= p0;
    var msg$= (base.LazyInfo$0)p1;
    return base.Assert$0.instance.imm$$bang$2(
this$.read$$gt_eq$1(
x$),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Info$0)((base.ToInfo$0)msg$.read$$hash$0(
)).read$info$0(
)).imm$$plus$1(
((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Expected: ").imm$$plus$1(
this$)).imm$$or$1(
base.Str$0Instance.instance(" >= "))).imm$$plus$1(
this$.read$close$1(
x$))).read$info$0(
));
  }

});
  }
  default Object read$assertInRange$2(Object p0, Object p1){
    var this$= this;
    var lo$= p0;
    var hi$= p1;
    return this$.read$assertInRange$3(
lo$,
hi$,
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  default Object read$assertInRange$3(Object p0, Object p1, Object p2){
    var this$= this;
    var lo$= p0;
    var hi$= p1;
    var msg$= (base.LazyInfo$0)p2;
    return base.Assert$0.instance.imm$$bang$2(
this$.read$inRange$2(
lo$,
hi$),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Info$0)((base.ToInfo$0)msg$.read$$hash$0(
)).read$info$0(
)).imm$$plus$1(
((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Expected in [lo,hi]").imm$$or$1(
base.Str$0Instance.instance("lo: "))).imm$$plus$1(
this$.read$close$1(
lo$))).imm$$or$1(
base.Str$0Instance.instance("hi: "))).imm$$plus$1(
this$.read$close$1(
hi$))).imm$$or$1(
base.Str$0Instance.instance("actual: "))).imm$$plus$1(
this$)).read$info$0(
));
  }

});
  }
  default Object read$assertInRangeOpen$2(Object p0, Object p1){
    var this$= this;
    var lo$= p0;
    var hi$= p1;
    return this$.read$assertInRangeOpen$3(
lo$,
hi$,
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  default Object read$assertInRangeOpen$3(Object p0, Object p1, Object p2){
    var this$= this;
    var lo$= p0;
    var hi$= p1;
    var msg$= (base.LazyInfo$0)p2;
    return base.Assert$0.instance.imm$$bang$2(
this$.read$inRangeOpen$2(
lo$,
hi$),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Info$0)((base.ToInfo$0)msg$.read$$hash$0(
)).read$info$0(
)).imm$$plus$1(
((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Expected in (lo,hi)").imm$$or$1(
base.Str$0Instance.instance("lo: "))).imm$$plus$1(
this$.read$close$1(
lo$))).imm$$or$1(
base.Str$0Instance.instance("hi: "))).imm$$plus$1(
this$.read$close$1(
hi$))).imm$$or$1(
base.Str$0Instance.instance("actual: "))).imm$$plus$1(
this$)).read$info$0(
));
  }

});
  }
  default Object read$assertInRangeLoOpen$2(Object p0, Object p1){
    var this$= this;
    var lo$= p0;
    var hi$= p1;
    return this$.read$assertInRangeLoOpen$3(
lo$,
hi$,
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  default Object read$assertInRangeLoOpen$3(Object p0, Object p1, Object p2){
    var this$= this;
    var lo$= p0;
    var hi$= p1;
    var msg$= (base.LazyInfo$0)p2;
    return base.Assert$0.instance.imm$$bang$2(
this$.read$inRangeLoOpen$2(
lo$,
hi$),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Info$0)((base.ToInfo$0)msg$.read$$hash$0(
)).read$info$0(
)).imm$$plus$1(
((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Expected in (lo,hi]").imm$$or$1(
base.Str$0Instance.instance("lo: "))).imm$$plus$1(
this$.read$close$1(
lo$))).imm$$or$1(
base.Str$0Instance.instance("hi: "))).imm$$plus$1(
this$.read$close$1(
hi$))).imm$$or$1(
base.Str$0Instance.instance("actual: "))).imm$$plus$1(
this$)).read$info$0(
));
  }

});
  }
  default Object read$assertInRangeHiOpen$2(Object p0, Object p1){
    var this$= this;
    var lo$= p0;
    var hi$= p1;
    return this$.read$assertInRangeHiOpen$3(
lo$,
hi$,
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("");
  }

});
  }
  default Object read$assertInRangeHiOpen$3(Object p0, Object p1, Object p2){
    var this$= this;
    var lo$= p0;
    var hi$= p1;
    var msg$= (base.LazyInfo$0)p2;
    return base.Assert$0.instance.imm$$bang$2(
this$.read$inRangeHiOpen$2(
lo$,
hi$),
new base.LazyInfo$0(){
    public Object read$$hash$0(){
    return ((base.Info$0)((base.ToInfo$0)msg$.read$$hash$0(
)).read$info$0(
)).imm$$plus$1(
((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("Expected in [lo,hi)").imm$$or$1(
base.Str$0Instance.instance("lo: "))).imm$$plus$1(
this$.read$close$1(
lo$))).imm$$or$1(
base.Str$0Instance.instance("hi: "))).imm$$plus$1(
this$.read$close$1(
hi$))).imm$$or$1(
base.Str$0Instance.instance("actual: "))).imm$$plus$1(
this$)).read$info$0(
));
  }

});
  }
}