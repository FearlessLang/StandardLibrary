package base;
public interface Order$2{
  default Object read$close$0(){
    throw new AssertionError("Uncallable method: Order$2.read$close$0"+this.getClass().getName());
  }
  default Object read$cmp$4(Object p0, Object p1, Object p2, Object p3){
    throw new AssertionError("Uncallable method: Order$2.read$cmp$4"+this.getClass().getName());
  }
  default Object read$order$1(Object p0){
    var this$= this;
    var by$= (base.OrderBy$2)p0;
    return new base.Order$1(){
    public Object read$close$0(){
    return this$.read$close$0(
);
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

};
  }
}