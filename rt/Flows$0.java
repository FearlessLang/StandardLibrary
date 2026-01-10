package base;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

import static base.Util.*;

public interface Flows$0 extends Sealed$0{
  default Object imm$$hash$0(){ return new Flow$1Instance(Stream.empty()); }
  default Object imm$$hash$1(Object p0){ return new Flow$1Instance(Stream.of(p0)); }
  default Object imm$$hash$2(Object p0,Object p1){ return new Flow$1Instance(Stream.of(p0,p1)); }
  default Object imm$$hash$3(Object p0,Object p1,Object p2){ return new Flow$1Instance(Stream.of(p0,p1,p2)); }
  default Object imm$$hash$4(Object p0,Object p1,Object p2,Object p3){ return new Flow$1Instance(Stream.of(p0,p1,p2,p3)); }

  default Object imm$fromMutList$1(Object p0){ return new Flow$1Instance(List$1Instance.asJava(p0).stream()); }
  default Object imm$fromReadList$1(Object p0){ return new Flow$1Instance(List$1Instance.asJava(p0).stream()); }
  default Object imm$fromImmList$1(Object p0){ return new Flow$1Instance(List$1Instance.asJava(p0).stream()); }

  Flows$0 instance= new Flows$0(){};
}

record Flow$1Instance(Stream<Object> s) implements Flow$1{
  private static Error consumed(){ return err("Flow consumed"); }

  @Override public Object mut$map$1(Object p0){
    try{ return new Flow$1Instance(s.map(e->callF$2(p0,e))); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$list$0(){
    try{ return List$1Instance.wrap(s.toList()); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$fold$2(Object p0,Object p1){
    try{
      var it= s.iterator();
      Object r= callMF$1(p0);
      while(it.hasNext()){ r = callF$3(p1,r,it.next()); }
      return r;
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$mapping$2(Object p0,Object p1){
    try{
      var kem= (KeyElemMapper$3)p1;
      var m= new LinkedHashMap<Util.MapKey,Object>();
      var k= Maps$0.toKey(p0);
      s.forEach(e->m.put(mapKey(k,kem.imm$key$1(e)), kem.imm$elem$1(e)));
      return new Map$2Instance(k,m);
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
}