package base;

import java.util.stream.Stream;

import static base.Util.*;

class InfiniteFlow$1Instance implements InfiniteFlow$1 {
  protected final Stream<Object> s;
  public Stream<Object> s() {
    return s;
  }

  public InfiniteFlow$1Instance(Stream<Object> s) {
    this.s = s;
  }


  protected static Error consumed(){ return err("Flow consumed"); }

  @Override public Object mut$map$1(Object p0){
    try{ return new InfiniteFlow$1Instance(s.map(e->callF$2(p0,e))); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$filter$1(Object p0){
    try{ return new InfiniteFlow$1Instance(s.filter(e->isTrue(callF$2(p0,e)))); }
    catch(IllegalStateException e){ throw consumed(); }
  }

  @Override public Object mut$forEach$1(Object p0){
    try{ s.forEach(e->callMF$2(p0,e)); return Void$0.instance; }
    catch(IllegalStateException e){ throw consumed(); }
  }

  @Override public Object mut$foldUntil$3(Object p0,Object p1,Object p2){
    try{
      var it= s.iterator();
      Object r= callMF$1(p0);
      while(it.hasNext() && !isTrue(callF$2(p2, r))) {
        r = callF$3(p1,r,it.next());
      }
      return r;
    }
    catch(IllegalStateException e){ throw consumed(); }
  }

  @Override public Object mut$flatMap$1(Object p0){
    try{ return new InfiniteFlow$1Instance(s.flatMap(e->((InfiniteFlow$1Instance)callF$2(p0,e)).s)); }
    catch(IllegalStateException e){ throw consumed(); }
  }

  @Override public Object mut$first$0(){
    try{
      var it= s.iterator();
      return it.hasNext() ? optSome(it.next()) : optEmpty();
    }
    catch(IllegalStateException e){ throw consumed(); }
  }

  @Override public Object mut$distinct$1(Object p0) {
    try{
      return new InfiniteFlow$1Instance(
          s.map(o -> mapKey((OrderHashBy$2) p0, o))
              .distinct()
              .map(k -> k.key)
      );
    }
    catch(IllegalStateException e){ throw consumed(); }
  }

  @Override public Object mut$limit$1(Object p0) {
    long limit = natToLong(p0);
    try{ return new SizedFlow$1Instance(this.s.limit(limit), limit); }
    catch(IllegalStateException e){ throw consumed(); }
  }
}
