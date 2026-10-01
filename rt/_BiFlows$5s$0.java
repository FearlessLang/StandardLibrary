package _base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static _base.Util.*;

public interface _BiFlows$5s$0{
  default Object imm$$hash$2(Object p0, Object p1){ return MultiFlow.of(List.of(List$o$1Instance.asJava(p0), List$o$1Instance.asJava(p1)), FlowMode.Seq); }
  default Object imm$tri$2(Object p0, Object p1){ return ((MultiFlow) p1).after(List$o$1Instance.asJava(p0)); }
  default Object imm$quad$2(Object p0, Object p1){ return ((MultiFlow) p1).after(List$o$1Instance.asJava(p0)); }
  _BiFlows$5s$0 instance= new _BiFlows$5s$0(){};
}
abstract class MultiFlow{
  static MultiFlow of(List<? extends List<Object>> cs, FlowMode mode){
    return switch (cs.size()){
      case 2 -> new BiFlow$2w$2Instance(cs, mode);
      case 3 -> new TriFlow$5k$3Instance(cs, mode);
      default -> new QuadFlow$aw$4Instance(cs, mode);
    };
  }
  MultiFlow(List<? extends List<Object>> cs, FlowMode mode){ this.cs= cs.stream().map(c->new ArrayList<>(c)).toList(); this.mode= mode; }
  private final FlowMode mode;
  private final List<ArrayList<Object>> cs;
  private boolean consumed= false;
  private String name(){ return List.of("BiFlow", "TriFlow", "QuadFlow").get(cs.size() - 2); }
  private void live(){ check(!consumed, name()+": this "+name()+" is already consumed: it is consumed by the call that merges, folds or extends it."); }
  private void take(){ live(); consumed= true; }
  private int longest(){ return cs.stream().mapToInt(List::size).max().getAsInt(); }
  private boolean same(){ return cs.stream().mapToInt(List::size).distinct().count() == 1; }
  private String sizesErr(String m){
    var sizes= cs.stream().map(c->""+c.size()).toList();
    return name()+"."+m+": the channels have different sizes, "+String.join(", ", sizes.subList(0, sizes.size() - 1))+" and "+sizes.getLast();
  }
  private static Object call(Object f, Object[] xs){
    return switch (xs.length){
      case 2 -> callF$3(f, xs[0], xs[1]);
      case 3 -> callF$4(f, xs[0], xs[1], xs[2]);
      case 4 -> callF$5(f, xs[0], xs[1], xs[2], xs[3]);
      default -> callF$6(f, xs[0], xs[1], xs[2], xs[3], xs[4]);
    };
  }
  private Object[] row(int i){ return cs.stream().map(c->c.get(i)).toArray(); }
  private Object[] optRow(int i){ return cs.stream().map(c->i < c.size() ? optSome(c.get(i)) : optEmpty()).toArray(); }
  private static Object[] prepend(Object x, Object[] xs){ return Stream.concat(Stream.of(x), Stream.of(xs)).toArray(); }
  private Object lazy(IntFunction<Object> e){
    Iterable<Object> it= ()->IntStream.range(0, longest()).mapToObj(e).iterator();
    return Flow$o$1Instance.of(it, mode);
  }
  private Object merged(Object f){ return lazy(i->call(f, row(i))); }
  private Object folded(Object acc, Object f, IntFunction<Object[]> row, Predicate<Object> stop){
    var r= callMF$1(acc);
    for (int i= 0; i < longest() && !stop.test(r); i++){ r= call(f, prepend(r, row.apply(i))); }
    return r;
  }
  private static Predicate<Object> until(Object pred){ return r->isTrue(callF$2(pred, r)); }
  private static Object channels(List<? extends List<Object>> first, List<? extends List<Object>> rest, FlowMode mode){
    var res= new ArrayList<List<Object>>(first);
    res.addAll(rest);
    return MultiFlow.of(res, mode);
  }
  Object after(List<Object> first){ take(); return channels(List.of(first), cs, FlowMode.Seq); }
  public Object mut$with$1(Object p0){ take(); return channels(cs, List.of(Flow$o$1Instance.toJava(p0)), FlowMode.Seq); }
  public Object mut$withBoth$1(Object p0){ take(); var o= (MultiFlow) p0; o.take(); return channels(cs, o.cs, mode == o.mode ? mode : FlowMode.Seq); }
  public Object mut$enumerate$0(){ take(); return channels(List.of(IntStream.range(0, longest()).<Object>mapToObj(Nat$c$0Instance::instance).toList()), cs, mode); }
  public Object mut$tryGetMerge$1(Object p0){ take(); return same() ? ok(merged(p0)) : fail(sizesErr("getMerge")); }
  public Object mut$mergeOpts$1(Object p0){ take(); return lazy(i->call(p0, optRow(i))); }
  public Object mut$tryGetFold$2(Object p0, Object p1){ take(); return same() ? ok(folded(p0, p1, this::row, r->false)) : fail(sizesErr("getFold")); }
  public Object mut$foldOpts$2(Object p0, Object p1){ take(); return folded(p0, p1, this::optRow, r->false); }
  public Object mut$tryGetFoldUntil$3(Object p0, Object p1, Object p2){ take(); return same() ? ok(folded(p0, p1, this::row, until(p2))) : fail(sizesErr("getFoldUntil")); }
  public Object mut$foldOptsUntil$3(Object p0, Object p1, Object p2){ take(); return folded(p0, p1, this::optRow, until(p2)); }
  private Object fill(int k, Object f){
    live();
    var c= cs.get(k);
    for (int i= c.size(), n= longest(); i < n; i++){ c.add(callF$2(f, Nat$c$0Instance.instance(i))); }
    return this;
  }
  public Object mut$fillA$1(Object p0){ return fill(0, p0); }
  public Object mut$fillB$1(Object p0){ return fill(1, p0); }
  public Object mut$fillC$1(Object p0){ return fill(2, p0); }
  public Object mut$fillD$1(Object p0){ return fill(3, p0); }
  public Object mut$cutToSmallest$0(){
    live();
    int n= cs.stream().mapToInt(List::size).min().getAsInt();
    cs.forEach(c->c.subList(n, c.size()).clear());
    return this;
  }
  public Object mut$limit$1(Object p0){
    live();
    long n= Nat$c$0Instance.unwrap(p0);
    if (n == 0){ throw detErr(name()+".limit: .limit 0 asks for no elements of every channel. When the count can be 0, test it with .if before building the "+name()+"."); }
    cs.forEach(c->c.subList((int) Math.min(n, c.size()), c.size()).clear());
    return this;
  }
}
final class BiFlow$2w$2Instance extends MultiFlow implements BiFlow$2w$2{ BiFlow$2w$2Instance(List<? extends List<Object>> cs, FlowMode mode){ super(cs, mode); } }
final class TriFlow$5k$3Instance extends MultiFlow implements TriFlow$5k$3{ TriFlow$5k$3Instance(List<? extends List<Object>> cs, FlowMode mode){ super(cs, mode); } }
final class QuadFlow$aw$4Instance extends MultiFlow implements QuadFlow$aw$4{ QuadFlow$aw$4Instance(List<? extends List<Object>> cs, FlowMode mode){ super(cs, mode); } }
