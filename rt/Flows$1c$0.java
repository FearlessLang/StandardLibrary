package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import base.FlowOps.FlowOp;
import base.FlowOps.ListOp;
import base.FlowOps.Sink;

import static base.Util.*;

public interface Flows$1c$0 extends Sealed$2o$0{
  default Object imm$$hash$0(){ return Flow$o$1Instance.of(); }
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

  default Object imm$fromMutList$1(Object p0){ var l= List$o$1Instance.asJava(p0); return Flow$o$1Instance.seq(new ListOp(l, 0, l.size()), l.size()); }//sequential
  default Object imm$fromMutList$2(Object p0,Object p1){ return Flow$o$1Instance.par(List$o$1Instance.asJava(p0)); }//parallel!
  default Object imm$fromReadList$1(Object p0){ return Flow$o$1Instance.par(List$o$1Instance.asJava(p0)); }//parallel!
  default Object imm$fromImmList$1(Object p0){ return Flow$o$1Instance.par(List$o$1Instance.asJava(p0)); }//parallel!

  Flows$1c$0 instance= new Flows$1c$0(){};
}

final class Flow$o$1Instance implements Flow$o$1{
  enum Kind{ seq, dp, pp }
  private final FlowOp op;
  private final long size;
  private final Kind kind;
  private boolean used;
  private Flow$o$1Instance(FlowOp op, long size, Kind kind){ this.op= op; this.size= size; this.kind= kind; }
  private static Error consumed(){ return err("Flow consumed"); }
  static Flow$o$1Instance of(Object... args){ return seq(new ListOp(Arrays.asList(args), 0, args.length), args.length); }
  static Flow$o$1Instance of(Stream<Object> stream){ return seq(new FlowOps.IteratorOp(stream.iterator()), -1); }
  static Flow$o$1Instance seq(FlowOp op, long size){ return new Flow$o$1Instance(op, size, Kind.seq); }
  static Flow$o$1Instance pp(FlowOp op, long size){ return new Flow$o$1Instance(new PipelineParallel.Safe(op), size, Kind.pp); }
  static Flow$o$1Instance par(List<Object> src){
    int n= src.size();
    var op= new ListOp(src, 0, n);
    if (DataParallel.sequentialised.isBound() || n <= 1){ return seq(op, n); }
    if (n == 3){ return pp(op, n); }
    return new Flow$o$1Instance(op, n, Kind.dp);
  }
  FlowOp take(){
    if (used){ throw consumed(); }
    used= true;
    return op;
  }
  private UnaryOperator<Sink> deco(){ return kind == Kind.pp ? PipelineParallel.deco : FlowOps.plain; }
  private Flow$o$1Instance same(FlowOp op, long size){ return kind == Kind.pp ? pp(op, size) : new Flow$o$1Instance(op, size, kind); }
  private Flow$o$1Instance stateful(){ return pp(new DataParallel.Converted(new DataParallel.Source(take(), size)), size); }
  private Flow$o$1Instance view(){ return kind == Kind.dp ? seq(new DataParallel.Source(take(), size), size) : this; }
  private FlowOp viewOp(){ return kind == Kind.dp ? new DataParallel.Source(take(), size) : take(); }
  private ArrayList<Object> collect(){
    var v= view();
    var l= new ArrayList<Object>();
    FlowOps.run(v.take(), v.deco(), l::add);
    return l;
  }
  private Flow$o$1Instance filter(java.util.function.Predicate<Object> p){ return same(new FlowOps.Filter(deco(), take(), p), -1); }
  private boolean any(Object p){ return isTrue(((Opt$c$1)filter(e->isTrue(callF$2(p, e))).mut$first$0()).read$isSome$0()); }
  @Override public Object mut$map$1(Object p0){ return same(new FlowOps.Map(deco(), take(), p0), -1); }
  @Override public Object mut$map$2(Object p0, Object p1){
    if (kind == Kind.dp){ return stateful().mut$map$2(p0, p1); }
    return same(new FlowOps.MapCtx(deco(), take(), (ToIso$1g$1)p0, p1), -1);
  }
  @Override public Object mut$filter$1(Object p0){ return filter(e->isTrue(callF$2(p0, e))); }
  @Override public Object mut$flatMap$1(Object p0){ return same(new FlowOps.FlatMap(deco(), take(), p0), -1); }
  @Override public Object mut$actor$2(Object p0, Object p1){
    if (kind == Kind.dp){ return stateful().mut$actor$2(p0, p1); }
    var f= (ActorImpl$lk$3)p1;
    return same(new FlowOps.Actor(deco(), take(), p0, (s, st, e)->f.read$$hash$4(s, st, e, FlowOps.match)), -1);
  }
  @Override public Object mut$actorMut$2(Object p0, Object p1){
    if (kind == Kind.dp){ return stateful().mut$actorMut$2(p0, p1); }
    var f= (ActorImplMut$4sk$3)p1;
    return same(new FlowOps.Actor(deco(), take(), p0, (s, st, e)->f.read$$hash$4(s, st, e, FlowOps.match)), -1);
  }
  @Override public Object mut$scan$2(Object p0, Object p1){
    if (kind == Kind.dp){ return stateful().mut$scan$2(p0, p1); }
    return same(new FlowOps.Scan(deco(), take(), p0, p1), -1);
  }
  @Override public Object mut$limit$1(Object p0){
    long limit= Nat$c$0Instance.unwrap(p0);
    if (limit < 0){ throw err("Flow.limit: Cannot limit to more than "+Long.MAX_VALUE+" values, got "+Long.toUnsignedString(limit)); }
    if (kind == Kind.dp){ return stateful().mut$limit$1(p0); }
    return same(new FlowOps.Limit(deco(), take(), limit), size < 0 ? -1 : Math.min(size, limit));
  }
  @Override public Object mut$min$1(Object p0){
    if (kind == Kind.dp){ return stateful().mut$min$1(p0); }
    return same(new FlowOps.Best(deco(), take(), (OrderBy$5e$2)p0, -1), -1);
  }
  @Override public Object mut$max$1(Object p0){
    if (kind == Kind.dp){ return stateful().mut$max$1(p0); }
    return same(new FlowOps.Best(deco(), take(), (OrderBy$5e$2)p0, 1), -1);
  }
  @Override public Object mut$$plus_plus$1(Object o){
    var other= (Flow$o$1Instance)o;
    long n= size < 0 || other.size < 0 ? -1 : size + other.size;
    return seq(new FlowOps.Concat(viewOp(), other.viewOp()), n);
  }
  //---
  @Override public Object mut$size$0(){
    if (size >= 0){ take(); return Nat$c$0Instance.instance(size); }
    long[] n= {0};
    var v= view();
    FlowOps.run(v.take(), v.deco(), _->n[0]++);
    return Nat$c$0Instance.instance(n[0]);
  }
  @Override public Object mut$forEach$1(Object p0){
    for (var e: collect()){ callMF$2(p0, e); }
    return Void$o$0.instance;
  }
  @Override public Object mut$list$0(){ return List$o$1Instance.wrap(collect()); }
  @Override public Object mut$eList$0(){ return EList$1k$1Instance.unsafeWrap(collect()); }
  @Override public Object mut$set$2(Object p0, Object p1){
    var toImm= (AsImm$1g$2)p0;
    var l= new ArrayList<Object>();
    var v= view();
    FlowOps.run(v.take(), v.deco(), e->l.add(toImm.mut$$hash$1(e)));
    return Set$c$1Instance.fromUnsortedList(Set$c$1Instance.ordering(p1), l);
  }
  @Override public Object mut$eSet$2(Object p0, Object p1){
    var toImm= (AsImm$1g$2)p0;
    var res= new ESet$s$1Instance(Set$c$1Instance.ordering(p1));
    var v= view();
    FlowOps.run(v.take(), v.deco(), e->res.mut$add$1(toImm.mut$$hash$1(e)));
    return res;
  }
  @Override public Object mut$mapping$2(Object p0,Object p1){
    var kem= (KeyElemMapper$9wg$3)p1;
    var m= new LinkedHashMap<Util.MapKey,Object>();
    var k= Maps$o$0.toKey(p0);
    var v= view();
    FlowOps.run(v.take(), v.deco(), e->m.put(mapKey(k,kem.imm$key$1(e)), kem.imm$elem$1(e)));
    return new Map$c$2Instance(k,m);
  }
  @Override public Object mut$fold$2(Object p0,Object p1){
    Object[] r= {callMF$1(p0)};
    var v= view();
    FlowOps.run(v.take(), v.deco(), e->r[0]= callF$3(p1, r[0], e));
    return r[0];
  }
  @Override public Object mut$any$1(Object p0){ return bool(any(p0)); }
  @Override public Object mut$all$1(Object p0){ return bool(!isTrue(((Opt$c$1)filter(e->!isTrue(callF$2(p0, e))).mut$first$0()).read$isSome$0())); }
  @Override public Object mut$none$1(Object p0){ return bool(!any(p0)); }
  private ArrayList<Object> firstTwo(){ return ((Flow$o$1Instance)view().mut$limit$1(Nat$c$0Instance.instance(2))).collect(); }
  @Override public Object mut$get$0(){
    var l= firstTwo();
    check(!l.isEmpty(), "Flow.get expected size==1, got 0");
    check(l.size() == 1, "Flow.get expected size==1, got 2+");
    return l.getFirst();
  }
  @Override public Object mut$getOpt$0(){
    var l= firstTwo();
    if (l.isEmpty()){ return optEmpty(); }
    check(l.size() == 1, "Flow.opt expected size in {0,1}, got 2+");
    return optSome(l.getFirst());
  }
  @Override public Object mut$first$0(){
    var v= view();
    return FlowOps.first(v.take(), v.deco());
  }
  @Override public Object mut$last$0(){
    Object[] r= {null};
    var v= view();
    FlowOps.run(v.take(), v.deco(), e->r[0]= e);
    return optNullable(r[0]);
  }
  private static final F$3$2 isSome= new F$3$2(){ @Override public Object read$$hash$1(Object p0){ return ((Opt$c$1)p0).read$isSome$0(); } };
  @Override public Object mut$findMap$1(Object p0){
    var found= (Opt$c$1)((Flow$o$1Instance)((Flow$o$1Instance)mut$map$1(p0)).mut$filter$1(isSome)).mut$first$0();
    return isTrue(found.read$isSome$0()) ? found.mut$$bang$0() : optEmpty();
  }
  @Override public Object mut$findFirst$1(Object p0){ return ((Flow$o$1Instance)mut$filter$1(p0)).mut$first$0(); }
  @Override public Object mut$let$2(Object p0, Object p1){
    var collected= new ArrayList<Object>();
    var dup= new MF$7$1(){
      @Override public Object mut$$hash$0(){
        if (!used){ collected.addAll(collect()); }
        return kind == Kind.seq ? seq(new ListOp(collected, 0, collected.size()), collected.size()) : par(collected);
      }
    };
    var dr= callF$2(p0, dup);
    var self= used ? (Flow$o$1Instance)dup.mut$$hash$0() : this;
    return ((FlowContinuation$25fk$3)p1).mut$$hash$2(dr, self);
  }
}
