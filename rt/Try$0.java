package base;
public interface Try$0{
  default Object imm$$hash$1(Object p0){ return actionLazy(()->Util.callF$1(p0)); }
  default Object imm$$hash$2(Object p0, Object p1){ return actionLazy(()->Util.callF$2(p1,p0)); }
  Try$0 instance= new Try$0(){};
  static Action$1 actionLazy(java.util.function.Supplier<Object> s){
    return new Action$1(){
      public Object mut$run$1(Object p0){
        var m= (ActionMatch$2)p0;
        Object res; try{ res=s.get(); }
        catch(Deterministic d){ return m.mut$info$1(d.i); }
        return m.mut$ok$1(res);
      }
    };
  }
}