package base;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static base.Util.*;

public interface Flows$0 extends Sealed$0{
  default Object imm$$hash$0(){
    return new Flow$1Instance(Stream.empty());
  }
  default Object imm$$hash$1(Object p0){ return of(Stream.of(p0), 1); }
  default Object imm$$hash$2(Object p0,Object p1){ return of(Stream.of(p0,p1), 2); }
  default Object imm$$hash$3(Object p0,Object p1,Object p2){ return of(Stream.of(p0,p1,p2), 3); }
  default Object imm$$hash$4(Object p0,Object p1,Object p2,Object p3){ return of(Stream.of(p0,p1,p2,p3), 4); }

  default Object imm$fromMutList$1(Object p0){
    List<Object> list = List$1Instance.asJava(p0);
    return of(list.stream(), list.size());
  }

  default Object imm$fromMutList$2(Object p0,Object p1){
    List<Object> list = List$1Instance.asJava(p0);
    return of(list.stream(), list.size());
  }

  default Object imm$fromReadList$1(Object p0){
    List<Object> list = List$1Instance.asJava(p0);
    return of(list.stream(), list.size());
  }

  default Object imm$fromImmList$1(Object p0){
    List<Object> list = List$1Instance.asJava(p0);
    return of(list.stream(), list.size());
  }

  default Object imm$generate$1(Object p0) {
    return new InfiniteFlow$1Instance(Stream.generate(() -> callF$1(p0))); }

  default Object imm$generate$2(Object p0, Object p1) {
    return new InfiniteFlow$1Instance(Stream.iterate(callMF$1(p0), (acc) -> callMF$2(p0, acc)));
  }

  static Flow$1Instance of(Stream<Object> s) {
    return new Flow$1Instance(s);
  }


  static SizedFlow$1Instance of(Stream<Object> s, long size) {
    return new SizedFlow$1Instance(s, size);
  }


  Flows$0 instance= new Flows$0(){};
}



class Flow$1Instance implements Flow$1 {
  protected final Stream<Object> s;
  public Stream<Object> s() {
    return s;
  }

  public Flow$1Instance(Stream<Object> s) {
    this.s = s;
  }


  protected static Error consumed(){ return err("Flow consumed"); }

  @Override public Object mut$map$1(Object p0){
    try{ return new Flow$1Instance(s.map(e->callF$2(p0,e))); }
    catch(IllegalStateException e){ throw consumed(); }
  }

  @Override public Object mut$filter$1(Object p0){
    try{ return new Flow$1Instance(s.filter(e->isTrue(callF$2(p0,e)))); }
    catch(IllegalStateException e){ throw consumed(); }
  }

  @Override public Object mut$count$0(){
    try{ return new Nat$0Instance((int)s.count()); }
    catch(IllegalStateException e){ throw consumed(); }
  }

  @Override public Object mut$$plus_plus$1(Object o){
  //Note: all those try catches are relying on the JVM enforcing the stream consumptions,
  //but in the standard it is not guaranteed that it is checked. We need to add tests to all of the flow methods
  //to check that the current JVM does enforce it.
    var other= ((Flow$1Instance)o).s;
    try{ return new Flow$1Instance(Stream.concat(s, other)); }
    catch(IllegalStateException e){ throw consumed(); }    
  }
  @Override public Object mut$forEach$1(Object p0){
    try{ s.forEach(e->callMF$2(p0,e)); return Void$0.instance; }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$list$0(){
    try{ return List$1Instance.wrap(s.toList()); }
    catch(IllegalStateException e){ throw consumed(); }
  }

  @Override public Object mut$set$2(Object p0, Object p1){
    OrderHashBy$2 ordering = Set$2Instance.ordering(p1);
    AsImm$2 toImm = (AsImm$2) p0;

    try{ return new Set$2Instance(
            ordering,
            s.map(e -> toImm.mut$$hash$1(e))
            .collect(Collectors.toCollection(ArrayList::new))
    );}
    catch(IllegalStateException e){ throw consumed(); }
  }

  @Override public Object mut$eList$0(){
    try{ return EList$1Instance.wrap(s.toList()); }
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

  @Override public Object mut$mapping$2(Object p0,Object p1){
    try{
      var kem= (KeyElemMapper$3)p1;
      var m= new LinkedHashMap<Util.MapKey,Object>();
      var k= Maps$0.toKey(p0);
      s.forEach(e->m.put(mapKey(k,kem.imm$key$1(e)), kem.imm$elem$1(e)));
      return new Map$3Instance(k,m);
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$flatMap$1(Object p0){
    try{ return new Flow$1Instance(s.flatMap(e->((Flow$1Instance)callF$2(p0,e)).s)); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  //---
  @Override public Object mut$any$1(Object p0){
    try{ return bool(s.anyMatch(e->isTrue(callF$2(p0,e)))); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$all$1(Object p0){
    try{ return bool(s.allMatch(e->isTrue(callF$2(p0,e)))); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$none$1(Object p0){
    try{ return bool(s.noneMatch(e->isTrue(callF$2(p0,e)))); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$get$0(){
    try{
      var it= s.iterator();
      check(it.hasNext(), "Flow.get expected size==1, got 0");
      var e0= it.next();
      check(!it.hasNext(), "Flow.get expected size==1, got 2+");
      return e0;
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$opt$0(){
    try{
      var it= s.iterator();
      if(!it.hasNext()){ return optEmpty(); }
      var e0= it.next();
      check(!it.hasNext(), "Flow.opt expected size in {0,1}, got 2+");
      return optSome(e0);
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$first$0(){
    try{
      var it= s.iterator();
      return it.hasNext() ? optSome(it.next()) : optEmpty();
    }
    catch(IllegalStateException e){ throw consumed(); }
  }

  @Override public Object mut$limitDefensive$1(Object p0) {
    long limit = natToLong(p0);
    try{ return new Flow$1Instance(this.s.limit(limit)); }
    catch(IllegalStateException e){ throw consumed(); }
  }
}
