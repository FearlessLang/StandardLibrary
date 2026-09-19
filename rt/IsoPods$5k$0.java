package base;
import static base.Util.*;
public interface IsoPods$5k$0{
  default Object imm$$hash$1(Object p0){ return new IsoPod$2s$1Instance(p0); }
  IsoPods$5k$0 instance= new IsoPods$5k$0(){};
  }
class IsoPod$2s$1Instance implements IsoPod$2s$1{
  private Object v; private boolean closed;
  IsoPod$2s$1Instance(Object v){ this.v= v; closed= true; }
  public Object read$isClosed$0(){ return bool(closed); }
  public Object read$peek$1(Object p0){ var f=(IsoViewer$m8$2)p0; return closed ? f.mut$some$1(v) : f.mut$empty$0(); }
  public Object mut$open$0(){
    if (!closed){ throw err("The IsoPod was open"); }
    var old=v; v= null; closed= false; return old;
    }
  public Object mut$mutate$1(Object p0){ var f=(IsoMutator$18g$2)p0; return closed ? Speculate.shielded(()->f.mut$some$1(v)) : f.mut$empty$0(); }
  }
