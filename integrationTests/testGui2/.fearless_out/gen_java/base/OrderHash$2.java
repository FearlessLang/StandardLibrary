package base;
public interface OrderHash$2 extends base.Order$2, base.ToStr$1{
  default Object read$hash$1(Object p0){
    throw new AssertionError("Uncallable method: OrderHash$2.read$hash$1"+this.getClass().getName());
  }
  default Object read$close$1(Object p0){
    throw new AssertionError("Uncallable method: OrderHash$2.read$close$1"+this.getClass().getName());
  }
  default Object read$orderHash$1(Object p0){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    return new base.OrderHash$1(){
    public Object read$close$0(){
    return this$.read$close$0(
);
  }

    public Object read$close$1(Object p0){
    var t$= p0;
    return ((base.OrderHash$2)this$.read$close$1(
t$)).read$orderHash$1(
by$);
  }

    public Object read$cmp$3(Object p0, Object p1, Object p2){
    var t0$= p0;
    var t1$= p1;
    var m$= (base.OrderMatch$1)p2;
    return this$.read$cmp$4(
by$,
t0$,
t1$,
m$);
  }

    public Object read$hash$0(){
    return this$.read$hash$1(
by$);
  }

    public Object read$str$0(){
    return this$.read$str$1(
by$);
  }

};
  }
  default Object read$assertEq$2(Object p0, Object p1){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var expected$= p1;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertEq$1(
expected$);
  }
  default Object read$assertEq$3(Object p0, Object p1, Object p2){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var expected$= p1;
    var msg$= (base.LazyInfo$0)p2;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertEq$2(
expected$,
msg$);
  }
  default Object read$assertNe$2(Object p0, Object p1){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var expected$= p1;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertNe$1(
expected$);
  }
  default Object read$assertNe$3(Object p0, Object p1, Object p2){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var expected$= p1;
    var msg$= (base.LazyInfo$0)p2;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertNe$2(
expected$,
msg$);
  }
  default Object read$assertLt$2(Object p0, Object p1){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var x$= p1;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertLt$1(
x$);
  }
  default Object read$assertLt$3(Object p0, Object p1, Object p2){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var x$= p1;
    var msg$= (base.LazyInfo$0)p2;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertLt$2(
x$,
msg$);
  }
  default Object read$assertLe$2(Object p0, Object p1){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var x$= p1;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertLe$1(
x$);
  }
  default Object read$assertLe$3(Object p0, Object p1, Object p2){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var x$= p1;
    var msg$= (base.LazyInfo$0)p2;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertLe$2(
x$,
msg$);
  }
  default Object read$assertGt$2(Object p0, Object p1){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var x$= p1;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertGt$1(
x$);
  }
  default Object read$assertGt$3(Object p0, Object p1, Object p2){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var x$= p1;
    var msg$= (base.LazyInfo$0)p2;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertGt$2(
x$,
msg$);
  }
  default Object read$assertGe$2(Object p0, Object p1){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var x$= p1;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertGe$1(
x$);
  }
  default Object read$assertGe$3(Object p0, Object p1, Object p2){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var x$= p1;
    var msg$= (base.LazyInfo$0)p2;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertGe$2(
x$,
msg$);
  }
  default Object read$assertInRange$3(Object p0, Object p1, Object p2){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var lo$= p1;
    var hi$= p2;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertInRange$2(
lo$,
hi$);
  }
  default Object read$assertInRange$4(Object p0, Object p1, Object p2, Object p3){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var lo$= p1;
    var hi$= p2;
    var msg$= (base.LazyInfo$0)p3;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertInRange$3(
lo$,
hi$,
msg$);
  }
  default Object read$assertInRangeOpen$3(Object p0, Object p1, Object p2){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var lo$= p1;
    var hi$= p2;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertInRangeOpen$2(
lo$,
hi$);
  }
  default Object read$assertInRangeOpen$4(Object p0, Object p1, Object p2, Object p3){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var lo$= p1;
    var hi$= p2;
    var msg$= (base.LazyInfo$0)p3;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertInRangeOpen$3(
lo$,
hi$,
msg$);
  }
  default Object read$assertInRangeLoOpen$3(Object p0, Object p1, Object p2){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var lo$= p1;
    var hi$= p2;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertInRangeLoOpen$2(
lo$,
hi$);
  }
  default Object read$assertInRangeLoOpen$4(Object p0, Object p1, Object p2, Object p3){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var lo$= p1;
    var hi$= p2;
    var msg$= (base.LazyInfo$0)p3;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertInRangeLoOpen$3(
lo$,
hi$,
msg$);
  }
  default Object read$assertInRangeHiOpen$3(Object p0, Object p1, Object p2){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var lo$= p1;
    var hi$= p2;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertInRangeHiOpen$2(
lo$,
hi$);
  }
  default Object read$assertInRangeHiOpen$4(Object p0, Object p1, Object p2, Object p3){
    var this$= this;
    var by$= (base.OrderHashBy$2)p0;
    var lo$= p1;
    var hi$= p2;
    var msg$= (base.LazyInfo$0)p3;
    return ((base.OrderHash$1)this$.read$orderHash$1(
by$)).read$assertInRangeHiOpen$3(
lo$,
hi$,
msg$);
  }
}