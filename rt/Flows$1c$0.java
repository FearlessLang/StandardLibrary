package base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

import static base.Util.*;

public interface Flows$1c$0 extends Sealed$2o$0{
  default Object imm$$hash$0(){ return Flow$o$1Instance.of(Stream.empty()); }
  default Object imm$$hash$1(Object p0){ return Flow$o$1Instance.of(p0); }
  default Object imm$$hash$2(Object p0,Object p1){ return Flow$o$1Instance.of(p0,p1); }
  default Object imm$$hash$3(Object p0,Object p1,Object p2){ return Flow$o$1Instance.of(p0,p1,p2); }
  default Object imm$$hash$4(Object p0,Object p1,Object p2,Object p3){ return Flow$o$1Instance.of(p0,p1,p2,p3); }
  default Object imm$$hash$5(Object p0,Object p1,Object p2,Object p3, Object p4){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4); }
  default Object imm$$hash$6(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5); }
  default Object imm$$hash$7(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6); }
  default Object imm$$hash$8(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7); }
  default Object imm$$hash$9(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8); }
  default Object imm$$hash$10(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9); }
  default Object imm$$hash$11(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10); }
  default Object imm$$hash$12(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11); }
  default Object imm$$hash$13(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12); }
  default Object imm$$hash$14(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13); }
  default Object imm$$hash$15(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14); }
  default Object imm$$hash$16(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15); }

  default Object imm$fromMutList$1(Object p0){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0).stream()); }//sequential
  default Object imm$fromMutList$2(Object p0,Object p1){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0).stream().parallel()); }//parallel!
  default Object imm$fromReadList$1(Object p0){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0).stream().parallel()); }//parallel!
  default Object imm$fromImmList$1(Object p0){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0).stream().parallel()); }//parallel!

  Flows$1c$0 instance= new Flows$1c$0(){};
}

record Flow$o$1Instance(Stream<Object> s) implements Flow$o$1{
  private static Error consumed(){ return err("Flow consumed"); }
  static Flow$o$1Instance of(Object... args){ return Flow$o$1Instance.of(Stream.of(args)); }
  static Flow$o$1Instance of(Stream<Object> stream) {return new Flow$o$1Instance(stream);}

  @Override public Object mut$map$1(Object p0){
    try{ return new Flow$o$1Instance(s.map(e->callF$2(p0,e))); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$filter$1(Object p0){
    try{ return new Flow$o$1Instance(s.filter(e->isTrue(callF$2(p0,e)))); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$$plus_plus$1(Object o){
    //Note: all those try catches are relying on the JVM enforcing the stream consumptions,
    //but in the standard it is not guaranteed that it is checked. We need to add tests to all of the flow methods
    //to check that the current JVM does enforce it.
    var other= ((Flow$o$1Instance)o).s;
    try{ return new Flow$o$1Instance(Stream.concat(s, other)); }
    catch(IllegalStateException e){ throw consumed(); }    
  }
  @Override public Object mut$forEach$1(Object p0){
    try{ s.toList().forEach(e->callMF$2(p0,e)); return Void$o$0.instance; }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$list$0(){
    try{ return List$o$1Instance.wrap(s.toList()); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$eList$0(){
    try{ return EList$1k$1Instance.unsafeWrap(s.collect(Collectors.toCollection(ArrayList::new))); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$set$2(Object p0, Object p1){
    OrderHashBy$2ea$2 ordering = Set$c$1Instance.ordering(p1);
    AsImm$1g$2 toImm = (AsImm$1g$2) p0;
    try{
      return Set$c$1Instance.fromSortedList(
        ordering,
        s.map(toImm::read$$hash$1).sorted(Util.toComparator(ordering)).toList()
      );
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$eSet$2(Object p0, Object p1){
    OrderHashBy$2ea$2 ordering = Set$c$1Instance.ordering(p1);
    AsImm$1g$2 toImm = (AsImm$1g$2) p0;
    LinkedHashMap<Util.MapKey, Object> map = new LinkedHashMap<>();
    try{
      s.map(toImm::read$$hash$1).toList().forEach(e -> map.put(mapKey(ordering, e), e));
      return new ESet$s$1Instance(map, ordering);
    }
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
      var kem= (KeyElemMapper$9wg$3)p1;
      var m= new LinkedHashMap<Util.MapKey,Object>();
      var k= Maps$o$0.toKey(p0);
      s.toList().forEach(e->putOnce(m,k,kem,e));
      return new Map$c$2Instance(k,m);
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
  private static void putOnce(LinkedHashMap<Util.MapKey,Object> m,OrderHashBy$2ea$2 by,KeyElemMapper$9wg$3 kem,Object e){
    var key= kem.imm$key$1(e);
    var mk= mapKey(by,key);
    check(!m.containsKey(mk),
      "Flow.mapping: the key "+toStringBy(by,key)+" is produced by more than one element;"
      +" a Map holds one element per key.");
    m.put(mk, kem.imm$elem$1(e));
  }
  @Override public Object mut$flatMap$1(Object p0){
    try{ return new Flow$o$1Instance(s.flatMap(e->((Flow$o$1Instance)callF$2(p0,e)).s)); }
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
  @Override public Object mut$min$1(Object p0){
    try {
      return Flow$o$1Instance.of(s.gather(new MinGatherer((OrderBy$5e$2) p0)));
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$max$1(Object p0){
    try {
      return Flow$o$1Instance.of(s.gather(new MaxGatherer((OrderBy$5e$2) p0)));
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$sort$1(Object p0){
    try {
      return Flow$o$1Instance.of(s.sorted(toComparator((OrderBy$5e$2) p0)));
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$distinct$1(Object p0){
    try {
      return Flow$o$1Instance.of(s.gather(new DistinctGatherer((OrderHashBy$2ea$2) p0)));
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$sortDistinct$1(Object p0){
    var by= (OrderBy$5e$2) p0;
    try {
      return Flow$o$1Instance.of(s.sorted(toComparator(by)).gather(new SortedDistinctGatherer(by)));
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
  @Override public Object mut$last$0(){
    try{ return Util.toOpt(s.reduce((_, b) -> b)); }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$scan$2(Object p0, Object p1){
    try{
      return Flow$o$1Instance.of(
        s.gather(Gatherers.scan(() -> p0, (a,b) -> callF$3(p1,a,b)))
      );
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
  @Override public Object mut$limit$1(Object p0){
    try{
      long limit = Nat$c$0Instance.unwrap(p0);
      check(limit != 0, "Flow.limit: .limit 0 asks a whole chain of stages for no elements. When the count can be 0, test it with .if before building the flow.");
      if (limit < 0) { // check if overflows long
        // Potentially better to clamp here and hide it - the caller will likely die before they hit Long.MAX_VALUE.
        // It's dishonest but if each operation takes 1ns:  9223372036854775807 × (1 nanosecond) ≈ 106752 d ≈ 292 years
        throw err("Flow.limit: Cannot limit to more than "+Long.MAX_VALUE+" values, got "+Long.toUnsignedString(limit));
      }
      return Flow$o$1Instance.of(
        s.limit(limit)
      );
    }
    catch(IllegalStateException e){ throw consumed(); }
  }
}

sealed abstract class BestGatherer implements Gatherer<Object, ArrayList<Object>, Object> {
  private final OrderBy$5e$2 ordering;
  private Object best = null;

  BestGatherer(OrderBy$5e$2 ordering) { this.ordering = ordering; }

  abstract boolean better(int cmpResult);

  @Override
  public Supplier<ArrayList<Object>> initializer() { return ArrayList::new; }
  @Override
  public Integrator<ArrayList<Object>, Object, Object> integrator() {
    return (state, element, _) -> {
      if (this.best == null) {
        this.best = element;
        state.add(element);
        return true;
      }
      int cmp = cmp(ordering, element, best);
      if (better(cmp)) {
        this.best = element;
        state.clear();
        state.add(element);
        return true;
      }
      if (cmp == 0) { state.add(element); }
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

final class MinGatherer extends BestGatherer {
  MinGatherer(OrderBy$5e$2 ordering) { super(ordering); }

  @Override
  boolean better(int cmpResult) { return cmpResult < 0; }
}

final class MaxGatherer extends BestGatherer {
  MaxGatherer(OrderBy$5e$2 ordering) { super(ordering); }

  @Override
  boolean better(int cmpResult) { return cmpResult > 0; }
}

final class DistinctGatherer implements Gatherer<Object, HashSet<Util.MapKey>, Object> {
  private final OrderHashBy$2ea$2 ordering;

  DistinctGatherer(OrderHashBy$2ea$2 ordering) { this.ordering = ordering; }

  @Override
  public Supplier<HashSet<Util.MapKey>> initializer() { return HashSet::new; }
  @Override
  public Integrator<HashSet<Util.MapKey>, Object, Object> integrator() { return this::integrate; }
  private boolean integrate(HashSet<Util.MapKey> seen, Object element, Downstream<? super Object> downstream) {
    return !seen.add(mapKey(ordering, element)) || downstream.push(element);
  }
}

final class SortedDistinctGatherer implements Gatherer<Object, Object[], Object> {
  private final OrderBy$5e$2 ordering;

  SortedDistinctGatherer(OrderBy$5e$2 ordering) { this.ordering = ordering; }

  @Override
  public Supplier<Object[]> initializer() { return () -> new Object[1]; }
  @Override
  public Integrator<Object[], Object, Object> integrator() { return this::integrate; }
  private boolean integrate(Object[] kept, Object element, Downstream<? super Object> downstream) {
    if (kept[0] != null && cmp(ordering, kept[0], element) == 0) { return true; }
    kept[0] = element;
    return downstream.push(element);
  }
}
