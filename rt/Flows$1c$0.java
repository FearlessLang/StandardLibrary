package base;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
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
  default Object imm$fromMutList$2(Object p0,Object p1){ return Flow$o$1Instance.par(List$o$1Instance.asJava(p0), true); }//parallel!
  default Object imm$fromReadList$1(Object p0){ return Flow$o$1Instance.par(List$o$1Instance.asJava(p0), false); }//parallel!
  default Object imm$fromImmList$1(Object p0){ return Flow$o$1Instance.par(List$o$1Instance.asJava(p0), false); }//parallel!

  Flows$1c$0 instance= new Flows$1c$0(){};
}

final class Flow$o$1Instance implements Flow$o$1{
  private Stream<Object> s;
  private final List<Object> src;
  private final UnaryOperator<Consumer<Object>> stages;
  private final boolean join;
  private boolean used;
  private Flow$o$1Instance(Stream<Object> s, List<Object> src, UnaryOperator<Consumer<Object>> stages, boolean join){ this.s= s; this.src= src; this.stages= stages; this.join= join; }
  private static Error consumed(){ return err("Flow consumed"); }
  static Flow$o$1Instance of(Object... args){ return Flow$o$1Instance.of(Stream.of(args)); }
  static Flow$o$1Instance of(Stream<Object> stream){ return new Flow$o$1Instance(stream, null, null, false); }
  static Flow$o$1Instance par(List<Object> src, boolean join){ return src.size() < 2 ? of(src.stream()) : new Flow$o$1Instance(null, src, down -> down, join); }
  Stream<Object> s(){
    if (used){ throw consumed(); }
    used= true;
    if (s == null){ s= Speculate.stream(src, stages, join); }
    return s;
  }
  private Flow$o$1Instance stage(UnaryOperator<Stream<Object>> seq, UnaryOperator<Consumer<Object>> par){
    if (src == null){ return of(seq.apply(s())); }
    if (used){ throw consumed(); }
    used= true;
    return new Flow$o$1Instance(null, src, down -> stages.apply(par.apply(down)), join);
  }
  private <R> R run(Function<Stream<Object>,R> body){
    var st= s();
    try{ return body.apply(st); }
    finally{ st.close(); }
  }
  private Flow$o$1Instance map(Object f){ return stage(st -> st.map(e -> callF$2(f, e)), down -> e -> down.accept(callF$2(f, e))); }
  @Override public Object mut$map$1(Object p0){ return map(p0); }
  @Override public Object mut$filter$1(Object p0){
    return stage(st -> st.filter(e -> isTrue(callF$2(p0, e))), down -> e -> { if (isTrue(callF$2(p0, e))){ down.accept(e); } });
  }
  @Override public Object mut$flatMap$1(Object p0){
    return stage(st -> st.flatMap(e -> ((Flow$o$1Instance)callF$2(p0, e)).s()), down -> e -> flatMap(p0, e, down));
  }
  private static void flatMap(Object f, Object e, Consumer<Object> down){
    try (var inner= ((Flow$o$1Instance)callF$2(f, e)).s()){ inner.forEach(down); }
  }
  @Override public Object mut$size$0(){ return run(st -> new Nat$c$0Instance(st.mapToLong(e -> 1).sum())); }
  @Override public Object mut$$plus_plus$1(Object o){ return of(Stream.concat(s(), ((Flow$o$1Instance)o).s())); }
  @Override public Object mut$forEach$1(Object p0){ return run(st -> forEach(st, p0)); }
  private static Object forEach(Stream<Object> st, Object f){ st.forEach(e -> callMF$2(f, e)); return Void$o$0.instance; }
  @Override public Object mut$list$0(){ return run(st -> List$o$1Instance.wrap(st.toList())); }
  @Override public Object mut$eList$0(){ return run(st -> EList$1k$1Instance.unsafeWrap(st.collect(Collectors.toCollection(ArrayList::new)))); }
  @Override public Object mut$set$2(Object p0, Object p1){
    AsImm$1g$2 toImm= (AsImm$1g$2)p0;
    return run(st -> Set$c$1Instance.fromUnsortedList(Set$c$1Instance.ordering(p1), st.map(toImm::mut$$hash$1).collect(Collectors.toCollection(ArrayList::new))));
  }
  @Override public Object mut$eSet$2(Object p0, Object p1){ return run(st -> eSet(st, (AsImm$1g$2)p0, new ESet$s$1Instance(Set$c$1Instance.ordering(p1)))); }
  private static Object eSet(Stream<Object> st, AsImm$1g$2 toImm, ESet$s$1Instance res){ st.forEach(e -> res.mut$add$1(toImm.mut$$hash$1(e))); return res; }
  @Override public Object mut$fold$2(Object p0,Object p1){ return run(st -> fold(st, p0, p1)); }
  private static Object fold(Stream<Object> st, Object acc, Object f){
    var it= st.iterator();
    Object r= callMF$1(acc);
    while(it.hasNext()){ r = callF$3(f,r,it.next()); }
    return r;
  }
  @Override public Object mut$mapping$2(Object p0,Object p1){ return run(st -> mapping(st, p0, (KeyElemMapper$9wg$3)p1)); }
  private static Object mapping(Stream<Object> st, Object keyOh, KeyElemMapper$9wg$3 kem){
    var m= new LinkedHashMap<Util.MapKey,Object>();
    var k= Maps$o$0.toKey(keyOh);
    st.forEach(e->m.put(mapKey(k,kem.imm$key$1(e)), kem.imm$elem$1(e)));
    return new Map$c$2Instance(k,m);
  }
  //---
  @Override public Object mut$any$1(Object p0){ return map(p0).run(st -> bool(st.anyMatch(Util::isTrue))); }
  @Override public Object mut$all$1(Object p0){ return map(p0).run(st -> bool(st.allMatch(Util::isTrue))); }
  @Override public Object mut$none$1(Object p0){ return map(p0).run(st -> bool(st.noneMatch(Util::isTrue))); }
  @Override public Object mut$get$0(){ return run(Flow$o$1Instance::get); }
  private static Object get(Stream<Object> st){
    var it= st.iterator();
    check(it.hasNext(), "Flow.get expected size==1, got 0");
    var e0= it.next();
    check(!it.hasNext(), "Flow.get expected size==1, got 2+");
    return e0;
  }
  @Override public Object mut$min$1(Object p0){ return of(s().gather(new MinGatherer((OrderBy$5e$2) p0))); }
  @Override public Object mut$max$1(Object p0){ return of(s().gather(new MaxGatherer((OrderBy$5e$2) p0))); }
  @Override public Object mut$getOpt$0(){ return run(Flow$o$1Instance::getOpt); }
  private static Object getOpt(Stream<Object> st){
    var it= st.iterator();
    if(!it.hasNext()){ return optEmpty(); }
    var e0= it.next();
    check(!it.hasNext(), "Flow.opt expected size in {0,1}, got 2+");
    return optSome(e0);
  }
  @Override public Object mut$first$0(){ return run(st -> Util.toOpt(st.findFirst())); }
  @Override public Object mut$last$0(){ return run(st -> Util.toOpt(st.reduce((_, b) -> b))); }
  @Override public Object mut$scan$2(Object p0, Object p1){ return of(s().gather(Gatherers.scan(() -> p0, (a,b) -> callF$3(p1,a,b)))); }
  @Override public Object mut$limit$1(Object p0){
    long limit = Nat$c$0Instance.unwrap(p0);
    if (limit < 0) { // check if overflows long
      // Potentially better to clamp here and hide it - the caller will likely die before they hit Long.MAX_VALUE.
      // It's dishonest but if each operation takes 1ns:  9223372036854775807 × (1 nanosecond) ≈ 106752 d ≈ 292 years
      throw err("Flow.limit: Cannot limit to more than "+Long.MAX_VALUE+" values, got "+Long.toUnsignedString(limit));
    }
    return of(s().limit(limit));
  }
  @Override public Object mut$map$2(Object p0, Object p1){
    var ctx= (ToIso$1g$1)p0;
    return of(s().map(e -> callF$3(p1, ((ToIso$1g$1)ctx.mut$iso$0()).mut$close$0(), e)));
  }
  @Override public Object mut$actor$2(Object p0, Object p1){
    var f= (ActorImpl$lk$3)p1;
    return of(s().gather(ActorStage.of(p0, (sink, st, e) -> f.read$$hash$4(sink, st, e, ActorStage.match))));
  }
  @Override public Object mut$actorMut$2(Object p0, Object p1){
    var f= (ActorImplMut$4sk$3)p1;
    return of(s().gather(ActorStage.of(p0, (sink, st, e) -> f.read$$hash$4(sink, st, e, ActorStage.match))));
  }
  @Override public Object mut$findMap$1(Object p0){
    return map(p0).run(st -> st.filter(o -> isTrue(((Opt$c$1)o).read$isSome$0())).findFirst().orElseGet(Util::optEmpty));
  }
  @Override public Object mut$findFirst$1(Object p0){ return ((Flow$o$1Instance)mut$filter$1(p0)).mut$first$0(); }
  @Override public Object mut$let$2(Object p0, Object p1){
    var dup= new MF$7$1(){
      List<Object> collected;
      @Override public Object mut$$hash$0(){
        if (collected == null){ collected= List$o$1Instance.asJava(mut$list$0()); }
        return src == null ? of(collected.stream()) : par(collected, join);
      }
    };
    var dr= callF$2(p0, dup);
    var self= dup.collected == null ? this : (Flow$o$1Instance)dup.mut$$hash$0();
    return ((FlowContinuation$25fk$3)p1).mut$$hash$2(dr, self);
  }
}

final class ActorStage implements _Sink$o$1{
  interface Call{ Object apply(Object sink, Object state, Object e); }
  static final Object CONTINUE= new Object();
  static final Object STOP= new Object();
  static final ActorMatch$174$1 match= new ActorMatch$174$1(){
    @Override public Object mut$continue$0(){ return CONTINUE; }
    @Override public Object mut$stop$0(){ return STOP; }
  };
  Gatherer.Downstream<? super Object> down;
  final Object actorSink= _ActorSinks$174$0.instance.imm$$hash$1(this);
  @Override public Object mut$$hash$1(Object p0){ down.push(p0); return Void$o$0.instance; }
  @Override public Object mut$pushError$1(Object p0){ throw Util.deterministic((Info$o$0)p0); }
  @Override public Object mut$stopDown$0(){ return Void$o$0.instance; }
  static Gatherer<Object, ActorStage, Object> of(Object state, Call call){
    return Gatherer.ofSequential(ActorStage::new, (st, e, down) -> {
      st.down= down;
      return call.apply(st.actorSink, state, e) != STOP;
    });
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
