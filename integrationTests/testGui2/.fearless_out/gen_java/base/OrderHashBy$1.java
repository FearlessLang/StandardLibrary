package base;
public interface OrderHashBy$1 extends base.OrderHashBy$2, base.OrderBy$2, base.ToStrBy$1{
  default Object imm$cmp$3(Object p0, Object p1, Object p2){
    throw new AssertionError("Uncallable method: OrderHashBy$1.imm$cmp$3"+this.getClass().getName());
  }
  default Object imm$hash$1(Object p0){
    throw new AssertionError("Uncallable method: OrderHashBy$1.imm$hash$1"+this.getClass().getName());
  }
  default Object imm$str$1(Object p0){
    throw new AssertionError("Uncallable method: OrderHashBy$1.imm$str$1"+this.getClass().getName());
  }
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var t$= p0;
    return new base.OrderHash$1(){
    public Object read$close$0(){
    return t$;
  }

    public Object read$close$1(Object p0){
    var t0$= p0;
    return this$.imm$$hash$1(
t0$);
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

    public Object read$hash$0(){
    return this$.imm$hash$1(
t$);
  }

    public Object read$str$0(){
    return this$.imm$str$1(
t$);
  }

};
  }
}