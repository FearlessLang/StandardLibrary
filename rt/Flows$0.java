package base;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

import static base.Util.*;

public interface Flows$0 extends Sealed$0{
  default Object imm$$hash$0(){ return Flow$1Instance.of(Stream.empty()); }
  default Object imm$$hash$1(Object p0){ return Flow$1Instance.of(p0); }
  default Object imm$$hash$2(Object p0,Object p1){ return Flow$1Instance.of(p0,p1); }
  default Object imm$$hash$3(Object p0,Object p1,Object p2){ return Flow$1Instance.of(p0,p1,p2); }
  default Object imm$$hash$4(Object p0,Object p1,Object p2,Object p3){ return Flow$1Instance.of(p0,p1,p2,p3); }
  default Object imm$$hash$5(Object p0,Object p1,Object p2,Object p3, Object p4){ return Flow$1Instance.of(p0,p1,p2,p3,p4); }
  default Object imm$$hash$6(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5){ return Flow$1Instance.of(p0,p1,p2,p3,p4,p5); }
  default Object imm$$hash$7(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6){ return Flow$1Instance.of(p0,p1,p2,p3,p4,p5,p6); }
  default Object imm$$hash$8(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7){ return Flow$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7); }
  default Object imm$$hash$9(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8){ return Flow$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8); }
  default Object imm$$hash$10(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9){ return Flow$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9); }
  default Object imm$fromMutList$1(Object p0){ return Flow$1Instance.of(List$1Instance.asJava(p0).stream()); }//sequential
  default Object imm$fromMutList$2(Object p0,Object p1){ return Flow$1Instance.of(List$1Instance.asJava(p0).stream()); }//maybeparallel
  default Object imm$fromReadList$1(Object p0){ return Flow$1Instance.of(List$1Instance.asJava(p0).stream()); }//maybeparallel
  default Object imm$fromImmList$1(Object p0){ return Flow$1Instance.of(List$1Instance.asJava(p0).stream()); }//maybeparallel

  Flows$0 instance= new Flows$0(){};
}

record Flow$1Instance(Stream<Object> s) implements Flow$1{
  private static Error consumed(){ return err("Flow consumed"); }
  static Flow$1Instance of(Object... args){ return Flow$1Instance.of(Stream.of(args)); }
  static Flow$1Instance of(Stream<Object> stream) {return new Flow$1Instance(stream);}

  @Override public Object mut$map$1(Object p0){
    try{ return new Flow$1Instance(s.map(e->callF$2(p0,e))); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$filter$1(Object p0){
    try{ return new Flow$1Instance(s.filter(e->isTrue(callF$2(p0,e)))); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$size$0(){
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
  @Override public Object mut$eList$0(){
    try{ return EList$1Instance.wrap(s.toList()); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$set$2(Object p0, Object p1){
    OrderHashBy$2 ordering = Set$1Instance.ordering(p1);
    AsImm$2 toImm = (AsImm$2) p0;
    try{ return Set$1Instance.ofMutableList(
      ordering,
      s.map(e -> toImm.mut$$hash$1(e))
        .collect(Collectors.toCollection(ArrayList::new))
    );}
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$eSet$2(Object p0, Object p1){
    OrderHashBy$2 ordering = Set$1Instance.ordering(p1);
    AsImm$2 toImm = (AsImm$2) p0;
    LinkedHashMap<Util.MapKey, Object> map = new LinkedHashMap<>();
    s.forEach(e -> map.put(mapKey(ordering, e), e));
    try{return new ESet$1Instance(map, ordering);}
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
  @Override public Object mut$min$1(Object p0){
    try {
      return Flow$1Instance.of(s.gather(new MinGatherer((OrderBy$2) p0)));
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$max$1(Object p0){
    try {
      return Flow$1Instance.of(s.gather(new MaxGatherer((OrderBy$2) p0)));
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$getOpt$0(){
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
}


final class MinGatherer implements Gatherer<Object, ArrayList<Object>, Object> {
  private final OrderBy$2 ordering;
  private Object min = null;

  MinGatherer(OrderBy$2 ordering) {
    this.ordering = ordering;
  }

  @Override
  public Supplier<ArrayList<Object>> initializer() {
    return ArrayList::new;
  }

  @Override
  public Integrator<ArrayList<Object>, Object, Object> integrator() {
    return (state, element, _) -> {
      if (this.min == null) {
        this.min = element;
        state.add(element);
        return true;
      }
      int cmp = cmp(ordering, element, min);
      if (cmp < 0) {
        this.min = element;
        state.clear();
        state.add(element);
        return true;
      }
      if (cmp == 0) {
        state.add(element);
      }
      return true;
    };
  }


  @Override
  public BiConsumer<ArrayList<Object>, Downstream<? super Object>> finisher() {
    return (state, downstream) -> {
      if (!downstream.isRejecting()) {
        state.forEach(downstream::push);
        state.clear();
      }
    };
  }
}


final class MaxGatherer implements Gatherer<Object, ArrayList<Object>, Object> {
  private final OrderBy$2 ordering;
  private Object max = null;

  MaxGatherer(OrderBy$2 ordering) {
    this.ordering = ordering;
  }

  @Override
  public Supplier<ArrayList<Object>> initializer() {
    return ArrayList::new;
  }

  @Override
  public Integrator<ArrayList<Object>, Object, Object> integrator() {
    return (state, element, _) -> {
      if (this.max == null) {
        this.max = element;
        state.add(element);
        return true;
      }
      int cmp = cmp(ordering, element, max);
      if (cmp > 0) {
        this.max = element;
        state.clear();
        state.add(element);
        return true;
      }
      if (cmp == 0) {
        state.add(element);
      }
      return true;
    };
  }


  @Override
  public BiConsumer<ArrayList<Object>, Downstream<? super Object>> finisher() {
    return (state, downstream) -> {
      if (!downstream.isRejecting()) {
        state.forEach(downstream::push);
        state.clear();
      }
    };
  }
}