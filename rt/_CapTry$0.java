package base;

import static base.Util.*;
public final class _CapTry$0 implements CapTry$0{
  public final Object mut$$hash$1(Object p0){ return actionLazy(()->callF$1(p0)); }
  public final Object mut$$hash$2(Object p0, Object p1){ return actionLazy(()->callF$2(p1,p0)); }
  public final Action$1 actionLazy(java.util.function.Supplier<Object> s){
    return new Action$1(){
      public Object mut$run$1(Object p0){
        var m= (ActionMatch$2)p0;
        Object res; try{ res=s.get(); }
        catch(Deterministic d){ return m.mut$info$1(d.i); }
        catch(NonDeterministic d){ return m.mut$info$1(d.i); }
        catch(Throwable t){ 
          var msg= t.getClass().getSimpleName()+"\n"+t.getMessage();
          return m.mut$info$1(new Str$0Instance(msg).read$info$0());
        }
        return m.mut$ok$1(res);
      }
    };
  }
}