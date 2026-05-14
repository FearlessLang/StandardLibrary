package base;
public interface OrderMatch$1{
  default Object mut$lt$0(){
    throw new AssertionError("Uncallable method: OrderMatch$1.mut$lt$0"+this.getClass().getName());
  }
  default Object mut$eq$0(){
    throw new AssertionError("Uncallable method: OrderMatch$1.mut$eq$0"+this.getClass().getName());
  }
  default Object mut$gt$0(){
    throw new AssertionError("Uncallable method: OrderMatch$1.mut$gt$0"+this.getClass().getName());
  }
  default Object mut$$and_and$1(Object p0){
    var this$= this;
    var onEq$= (base.MF$1)p0;
    return new base.OrderMatch$1(){
    public Object mut$lt$0(){
    return this$.mut$lt$0(
);
  }

    public Object mut$eq$0(){
    return onEq$.mut$$hash$0(
);
  }

    public Object mut$gt$0(){
    return this$.mut$gt$0(
);
  }

};
  }
}