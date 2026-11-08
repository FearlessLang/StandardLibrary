package _base;

import java.util.function.Supplier;

public interface Try$c$0{
  default Object imm$$hash$1(Object p0){ return Util.once(() -> outcome(() -> Util.callF$1(p0))); }
  default Object imm$$hash$2(Object p0, Object p1){ return Util.once(() -> outcome(() -> Util.callF$2(p1, p0))); }
  Try$c$0 instance= new Try$c$0(){};
  static Object outcome(Supplier<Object> s){
    try{ return Util.ok(s.get()); }
    catch (Deterministic d){ return Util.info(d.i); }
  }
}
