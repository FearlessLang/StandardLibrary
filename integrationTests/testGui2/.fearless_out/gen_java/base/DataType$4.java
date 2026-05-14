package base;
public interface DataType$4 extends base.ToInfo$1, base.ToImm$3, base.WidenTo$1, base.OrderHash$2, base.Extensible$1, base.Order$2, base.ToStr$1{
  default Object read$close$1(Object p0){
    throw new AssertionError("Uncallable method: DataType$4.read$close$1"+this.getClass().getName());
  }
  default Object read$dataType$1(Object p0){
    var this$= this;
    var by$= (base.DataTypeBy$3)p0;
    return new base.DataType$2(){
    public Object read$close$1(Object p0){
    var t$= p0;
    return ((base.DataType$4)this$.read$close$1(
t$)).read$dataType$1(
by$);
  }

    public Object read$close$0(){
    return this$.read$close$0(
);
  }

    public Object read$str$0(){
    return this$.read$str$1(
by$);
  }

    public Object read$info$0(){
    return this$.read$info$1(
by$);
  }

    public Object read$imm$0(){
    return this$.read$imm$1(
by$);
  }

    public Object read$hash$0(){
    return this$.read$hash$1(
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

};
  }
  default Object read$close$0(){
    throw new AssertionError("Uncallable method: DataType$4.read$close$0"+this.getClass().getName());
  }
}