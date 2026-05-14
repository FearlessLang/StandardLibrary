package base;
public interface OrderBy$1 extends base.OrderBy$2{
  default Object imm$cmp$3(Object p0, Object p1, Object p2){
    throw new AssertionError("Uncallable method: OrderBy$1.imm$cmp$3"+this.getClass().getName());
  }
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var t$= p0;
    return new base.Order$1(){
    public Object read$close$0(){
    return t$;
  }

    public Object read$cmp$3(Object p0, Object p1, Object p2){
    var t0$= p0;
    var t1$= p1;
    var m$= (base.OrderMatch$1)p2;
    return this$.imm$cmp$3(
t0$,
t1$,
m$);
  }

};
  }
}