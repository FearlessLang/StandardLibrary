package base;
public interface Order$1{
  default Object read$close$0(){
    throw new AssertionError("Uncallable method: Order$1.read$close$0"+this.getClass().getName());
  }
  default Object read$cmp$3(Object p0, Object p1, Object p2){
    throw new AssertionError("Uncallable method: Order$1.read$cmp$3"+this.getClass().getName());
  }
  default Object read$$eq_eq$1(Object p0){
    var this$= this;
    var other$= p0;
    return this$.read$cmp$3(
this$.read$close$0(
),
other$,
new base.OrderMatch$1(){
    public Object mut$lt$0(){
    return base.False$0.instance;
  }

    public Object mut$eq$0(){
    return base.True$0.instance;
  }

    public Object mut$gt$0(){
    return base.False$0.instance;
  }

});
  }
  default Object read$$lt_eq$1(Object p0){
    var this$= this;
    var other$= p0;
    return this$.read$cmp$3(
this$.read$close$0(
),
other$,
new base.OrderMatch$1(){
    public Object mut$lt$0(){
    return base.True$0.instance;
  }

    public Object mut$eq$0(){
    return base.True$0.instance;
  }

    public Object mut$gt$0(){
    return base.False$0.instance;
  }

});
  }
  default Object read$$gt_eq$1(Object p0){
    var this$= this;
    var other$= p0;
    return this$.read$cmp$3(
this$.read$close$0(
),
other$,
new base.OrderMatch$1(){
    public Object mut$lt$0(){
    return base.False$0.instance;
  }

    public Object mut$eq$0(){
    return base.True$0.instance;
  }

    public Object mut$gt$0(){
    return base.True$0.instance;
  }

});
  }
  default Object read$$lt$1(Object p0){
    var this$= this;
    var other$= p0;
    return this$.read$cmp$3(
this$.read$close$0(
),
other$,
new base.OrderMatch$1(){
    public Object mut$lt$0(){
    return base.True$0.instance;
  }

    public Object mut$eq$0(){
    return base.False$0.instance;
  }

    public Object mut$gt$0(){
    return base.False$0.instance;
  }

});
  }
  default Object read$$gt$1(Object p0){
    var this$= this;
    var other$= p0;
    return this$.read$cmp$3(
this$.read$close$0(
),
other$,
new base.OrderMatch$1(){
    public Object mut$lt$0(){
    return base.False$0.instance;
  }

    public Object mut$eq$0(){
    return base.False$0.instance;
  }

    public Object mut$gt$0(){
    return base.True$0.instance;
  }

});
  }
  default Object read$$bang_eq$1(Object p0){
    var this$= this;
    var other$= p0;
    return this$.read$cmp$3(
this$.read$close$0(
),
other$,
new base.OrderMatch$1(){
    public Object mut$lt$0(){
    return base.True$0.instance;
  }

    public Object mut$eq$0(){
    return base.False$0.instance;
  }

    public Object mut$gt$0(){
    return base.True$0.instance;
  }

});
  }
  default Object read$inRange$2(Object p0, Object p1){
    var this$= this;
    var lo$= p0;
    var hi$= p1;
    return base.Block$0.instance.imm$$hash$2(
base.Assert$0.instance.imm$$bang$1(
base._LE$0.instance.imm$$hash$3(
this$,
lo$,
hi$)),
((base.Bool$0)this$.read$$gt_eq$1(
lo$)).imm$$and_and$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return this$.read$$lt_eq$1(
hi$);
  }

}));
  }
  default Object read$inRangeOpen$2(Object p0, Object p1){
    var this$= this;
    var lo$= p0;
    var hi$= p1;
    return base.Block$0.instance.imm$$hash$2(
base.Assert$0.instance.imm$$bang$1(
base._LE$0.instance.imm$$hash$3(
this$,
lo$,
hi$)),
((base.Bool$0)this$.read$$gt$1(
lo$)).imm$$and_and$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return this$.read$$lt$1(
hi$);
  }

}));
  }
  default Object read$inRangeLoOpen$2(Object p0, Object p1){
    var this$= this;
    var lo$= p0;
    var hi$= p1;
    return base.Block$0.instance.imm$$hash$2(
base.Assert$0.instance.imm$$bang$1(
base._LE$0.instance.imm$$hash$3(
this$,
lo$,
hi$)),
((base.Bool$0)this$.read$$gt$1(
lo$)).imm$$and_and$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return this$.read$$lt_eq$1(
hi$);
  }

}));
  }
  default Object read$inRangeHiOpen$2(Object p0, Object p1){
    var this$= this;
    var lo$= p0;
    var hi$= p1;
    return base.Block$0.instance.imm$$hash$2(
base.Assert$0.instance.imm$$bang$1(
base._LE$0.instance.imm$$hash$3(
this$,
lo$,
hi$)),
((base.Bool$0)this$.read$$gt_eq$1(
lo$)).imm$$and_and$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return this$.read$$lt$1(
hi$);
  }

}));
  }
  default Object read$$lt_eq_gt$2(Object p0, Object p1){
    var this$= this;
    var other$= (base.Order$1)p0;
    var m$= (base.OrderMatch$1)p1;
    return this$.read$cmp$3(
this$.read$close$0(
),
other$.read$close$0(
),
m$);
  }
}