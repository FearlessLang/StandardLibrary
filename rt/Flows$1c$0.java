package _base;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static _base.Util.*;

public interface Flows$1c$0 extends Sealed$2o$0{
  default Object imm$$hash$0(){ return Flow$o$1Instance.seq(); }
  default Object imm$$hash$1(Object p0){ return Flow$o$1Instance.seq(p0); }
  default Object imm$$hash$2(Object p0,Object p1){ return Flow$o$1Instance.seq(p0,p1); }
  default Object imm$$hash$3(Object p0,Object p1,Object p2){ return Flow$o$1Instance.seq(p0,p1,p2); }
  default Object imm$$hash$4(Object p0,Object p1,Object p2,Object p3){ return Flow$o$1Instance.seq(p0,p1,p2,p3); }
  default Object imm$$hash$5(Object p0,Object p1,Object p2,Object p3, Object p4){ return Flow$o$1Instance.seq(p0,p1,p2,p3,p4); }
  default Object imm$$hash$6(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5){ return Flow$o$1Instance.seq(p0,p1,p2,p3,p4,p5); }
  default Object imm$$hash$7(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6){ return Flow$o$1Instance.seq(p0,p1,p2,p3,p4,p5,p6); }
  default Object imm$$hash$8(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7){ return Flow$o$1Instance.seq(p0,p1,p2,p3,p4,p5,p6,p7); }
  default Object imm$$hash$9(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8){ return Flow$o$1Instance.seq(p0,p1,p2,p3,p4,p5,p6,p7,p8); }
  default Object imm$$hash$10(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9){ return Flow$o$1Instance.seq(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9); }
  default Object imm$$hash$11(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10){ return Flow$o$1Instance.seq(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10); }
  default Object imm$$hash$12(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11){ return Flow$o$1Instance.seq(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11); }
  default Object imm$$hash$13(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12){ return Flow$o$1Instance.seq(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12); }
  default Object imm$$hash$14(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13){ return Flow$o$1Instance.seq(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13); }
  default Object imm$$hash$15(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14){ return Flow$o$1Instance.seq(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14); }
  default Object imm$$hash$16(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15){ return Flow$o$1Instance.seq(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15); }

  default Object imm$fromMutList$1(Object p0){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0), FlowMode.Seq).mut$hintSequential$0(); }
  default Object imm$fromReadList$1(Object p0){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0), FlowMode.Seq).mut$hintSequential$0(); }
  default Object imm$fromImmList$1(Object p0){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0), FlowMode.ParImm); }
  default Object imm$fromMutList$2(Object p0,Object p1){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0), FlowMode.of(p1)); }
  default Object imm$fromReadList$2(Object p0,Object p1){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0), FlowMode.of(p1)); }

  Flows$1c$0 instance= new Flows$1c$0(){};
}

enum FlowMode{
  Seq, ParImm, ParRead, ParRepr, ParPod;
  static FlowMode of(Object mode){
    if (mode instanceof ParImm$2s$1){ return ParImm; }
    if (mode instanceof ParRead$5k$1){ return ParRead; }
    if (mode instanceof ParRepr$5k$1){ return ParRepr; }
    if (mode instanceof ParPod$2s$1){ return ParPod; }
    throw new AssertionError(mode.getClass().getName());
  }
}

record FlowStep(String name, List<Object> args){}

final class FlowChain{
  FlowChain(Iterable<Object> source, FlowMode mode){ this.source= source; this.mode= mode; }
  final Iterable<Object> source;
  final FlowMode mode;
  final ArrayList<FlowStep> shape= new ArrayList<>();
  final ArrayList<FlowCore> core= new ArrayList<>();
  boolean suppress= false;
  boolean hintSequential= false;
  boolean hintParallel= false;
}

interface FlowSink{
  void accept(Object e);
  boolean done();
  void finish();
}

interface FlowCore{ FlowSink sink(FlowSink down); }

final class Flow$o$1Instance implements Flow$o$1{
  Flow$o$1Instance(FlowChain chain){ this.chain= chain; }
  private final FlowChain chain;
  private boolean consumed= false;
  static Flow$o$1Instance seq(Object... args){ return of(List.of(args), FlowMode.Seq); }
  static Flow$o$1Instance of(Iterable<Object> source, FlowMode mode){ return new Flow$o$1Instance(new FlowChain(source, mode)); }
  static Flow$o$1Instance of(Stream<Object> source, FlowMode mode){ Iterable<Object> it= source::iterator; return of(it, mode); }
  static Flow$o$1Instance range(Stream<Object> source){ return of(source, FlowMode.ParImm); }
  static List<Object> toJava(Object flow){ return List$o$1Instance.asJava(((Flow$o$1)flow).mut$list$0()); }
  private FlowChain take(){
    check(!consumed, "Flow: this flow is already consumed: a flow is consumed by the call that adds the next stage or the terminal.");
    consumed= true;
    return chain;
  }
  private Object stage(FlowCore c){
    var ch= take();
    ch.core.add(c);
    return new Flow$o$1Instance(ch);
  }
  @Override public Object mut$actor$2(Object state, Object f){
    return stage(d->new FlowActorSink(d, state, f));
  }
  @Override public Object mut$actorMut$2(Object state, Object f){
    return stage(d->new FlowActorSink(d, state, f));
  }
  @Override public Object mut$allToAll$1(Object f){
    return stage(d->new FlowAllToAllSink(d, f));
  }
  @Override public Object mut$distinct$1(Object by){
    return stage(d->new FlowDistinctSink(d, (OrderHashBy$2ea$2)by));
  }
  private Object record(Consumer<FlowChain> r){
    var ch= take();
    r.accept(ch);
    return new Flow$o$1Instance(ch);
  }
  @Override public Object mut$suppressMisuseExceptions$0(){ return record(c->c.suppress= true); }
  @Override public Object mut$hintSequential$0(){ return record(c->c.hintSequential= true); }
  @Override public Object mut$hintParallel$0(){ return record(c->c.hintParallel= true); }
  @Override public Object mut$fold$2(Object acc, Object f){
    var ch= take();
    FlowMisuse.checkHints(ch);
    //if (!ch.suppress){ FlowMisuse.check(ch); }
    var r= new FlowFoldSink(callMF$1(acc), f);
    run(ch, r);
    return r.r;
  }
  @Override public Object mut$forEach$2(Object a, Object f){
    var ch= take();
    FlowMisuse.checkHints(ch);
    //if (!ch.suppress){ FlowMisuse.check(ch); }
    run(ch, new FlowForEachSink(a, (ForEachBody$2h4$2)f));
    return Void$o$0.instance;
  }
  private static void run(FlowChain ch, FlowSink first){
    for (var c: ch.core.reversed()){ first= c.sink(first); }
    var it= ch.source.iterator();
    while (!first.done() && it.hasNext()){ first.accept(it.next()); }
    first.finish();
  }
}

final class FlowFoldSink implements FlowSink{
  FlowFoldSink(Object r, Object f){ this.r= r; this.f= f; }
  Object r;
  private final Object f;
  public void accept(Object e){ r= callF$3(f, r, e); }
  public boolean done(){ return false; }
  public void finish(){}
}

final class FlowForEachSink implements FlowSink{
  FlowForEachSink(Object a, ForEachBody$2h4$2 f){ this.a= a; this.f= f; }
  private final Object a;
  private final ForEachBody$2h4$2 f;
  public void accept(Object e){ f.mut$$hash$2(a, e); }
  public boolean done(){ return false; }
  public void finish(){}
}

final class FlowActorSink implements FlowSink, ActorSink$lk$1, ControlFlowMatch$2428$1{
  FlowActorSink(FlowSink down, Object state, Object f){ this.down= down; this.state= state; this.f= f; }
  private final FlowSink down;
  private final Object state;
  private final Object f;
  private boolean broke= false;
  public void accept(Object e){
    var r= f instanceof ActorImplMut$4sk$3 m ? m.read$$hash$3(this, state, e) : ((ActorImpl$lk$3)f).read$$hash$3(this, state, e);
    ((ControlFlow$2dk$1)r).mut$match$1(this);
  }
  public boolean done(){ return broke || down.done(); }
  public void finish(){ down.finish(); }
  @Override public Object mut$$hash$1(Object e){
    if (!down.done()){ down.accept(e); }
    return Void$o$0.instance;
  }
  @Override public Object mut$continue$0(){ return null; }
  @Override public Object mut$break$0(){ broke= true; return null; }
  @Override public Object mut$return$1(Object v){ throw new AssertionError("An actor answers Continue or Break"); }
}

final class FlowAllToAllSink implements FlowSink{
  FlowAllToAllSink(FlowSink down, Object f){ this.down= down; this.f= f; }
  private final FlowSink down;
  private final Object f;
  private final ArrayList<Object> xs= new ArrayList<>();
  public void accept(Object e){ xs.add(e); }
  public boolean done(){ return down.done(); }
  public void finish(){
    for (var r: List$o$1Instance.asJava(callF$2(f, List$o$1Instance.wrap(xs)))){
      if (down.done()){ break; }
      down.accept(r);
    }
    down.finish();
  }
}

final class FlowDistinctSink implements FlowSink{
  FlowDistinctSink(FlowSink down, OrderHashBy$2ea$2 by){ this.down= down; this.by= by; }
  private final FlowSink down;
  private final OrderHashBy$2ea$2 by;
  private final Object keys= Vars$o$0.instance.imm$$hash$1(List$o$1.instance);
  public void accept(Object e){
    if (isTrue(_KeepNew$5g$0.instance.imm$$hash$2(keys, by.imm$$hash$1(e)))){ down.accept(e); }
  }
  public boolean done(){ return down.done(); }
  public void finish(){ down.finish(); }
}

final class FlowMisuse{
  static final Set<String> fence= Set.of("allToAll", "sort", "sortDistinct", "max", "min", "distinctMut", "++");
  static final Set<String> oneToOne= Set.of("map", "peek", "scan", "mapCtx", "peekCtx");
  static final Set<String> elemLambda= Set.of("map", "peek", "scan", "mapCtx", "peekCtx", "filter", "flatMap", "flatMapMut", "mapFilter", "actor", "actorMut");
  static final Set<String> toRead= Set.of("++", "flatMap", "distinct", "actor", "scan");
  static final Set<String> decision= Set.of("limit", "actor", "actorMut");
  static final Set<String> shortCircuit= Set.of("first", "findFirst", "findMap", "any", "all", "none");
  static final Set<String> elementwise= Set.of("filter", "flatMap", "flatMapMut", "mapFilter");
  static final String suppressHint= " To keep this shape, call `.suppressMisuseExceptions` on the flow.";
  private final FlowChain c;
  private final List<FlowStep> stages;
  private final FlowStep terminal;
  private FlowMisuse(FlowChain c){
    this.c= c;
    stages= c.shape.subList(0, c.shape.size() - 1);
    terminal= c.shape.getLast();
  }
  static void checkHints(FlowChain c){
    if (c.suppress || !(c.hintSequential && c.hintParallel)){ return; }
    throw misuse("`.hintParallel` asks for a parallel strategy, but the flow also asks for the sequential strategy, with `.hintSequential` or by starting with `.seqFlow`. Remove `.hintParallel`, or the request for the sequential strategy.");
  }
  static void check(FlowChain c){
    var m= new FlowMisuse(c);
    if (c.mode == FlowMode.ParRepr || c.mode == FlowMode.ParPod){ m.checkPar(); }
    m.checkForEach();
    m.checkShape();
  }
  private static Error misuse(String msg){ return nonDetErr(msg + suppressHint); }
  private FlowStep step(int i){ return i == stages.size() ? terminal : stages.get(i); }
  private String name(int i){ return step(i).name(); }
  private boolean mutReach(int i){ return stages.subList(0, i).stream().noneMatch(s->toRead.contains(s.name())); }
  static final int shownStrSize= 20;
  private static String show(FlowStep s){
    var n= switch(s.name()){
      case "++" -> "++";
      case "mapCtx" -> ".map";
      case "peekCtx" -> ".peek";
      default -> "."+s.name();
    };
    var args= s.args().stream().map(FlowMisuse::showArg).toList();
    if (args.isEmpty()){ return n; }
    if (args.equals(List.of("{..}"))){ return n+"{..}"; }
    if (args.size() == 1){ return n+" "+args.getFirst(); }
    return n+"("+String.join(", ", args)+")";
  }
  private static String showArg(Object a){
    if (a instanceof Nat$c$0Instance || a instanceof Byte$o$0Instance || a instanceof Int$c$0Instance || a instanceof Num$c$0Instance || a instanceof Float$1c$0Instance){ return toS(a); }
    if (!(a instanceof Str$c$0Instance str)){ return "{..}"; }
    var v= str.val();
    return "\""+(v.length() <= shownStrSize ? v : v.substring(0, shownStrSize)+"..")+"\"";
  }
  private String show(int i){ return show(step(i)); }
  private String show(int from, int to){
    return java.util.stream.IntStream.range(from, to).mapToObj(this::show).collect(Collectors.joining(" "));
  }
  private static boolean isLambda(Object a){
    return !(a instanceof Nat$c$0Instance) && a != Void$o$0.instance;
  }
  private static boolean onlyImmCapture(Object a){
    if (a instanceof OnlyImmCapture){ return true; }
    for (var i: a.getClass().getInterfaces()){
      try{ if (i.getField("instance").get(null) == a){ return true; } }
      catch(ReflectiveOperationException _){}
    }
    return false;
  }
  private void checkPar(){
    var seen= new IdentityHashMap<Object,Integer>();
    int j= 0;
    for (var e: c.source){
      var i= seen.putIfAbsent(e, j);
      if (i != null){ throw misuse("The elements at index "+i+" and "+j+" of the source are identity equal: each element of a `"+c.mode+"` flow must be a distinct object."); }
      j++;
    }
    for (var s: c.shape){
      if (Set.of("for", "forEach", "let").contains(s.name())){ continue; }
      for (var a: s.args()){
        if (isLambda(a) && !onlyImmCapture(a)){
          throw misuse("The lambda of `"+show(s)+"` captures a value that is not `imm`: over `ParRepr` and `ParPod` only the lambdas of `.for`, `.forEach` and `.let` may capture `read` or `mut` values.");
        }
      }
    }
    for (int i= 0; i < stages.size(); i++){
      if (elemLambda.contains(name(i)) && mutReach(i)){ checkDecisionAfter(i); }
    }
    var t= terminal.name();
    if (shortCircuit.contains(t) && !t.equals("first") && mutReach(stages.size())){ throw decisionMisuse(stages.size(), stages.size()); }
  }
  private void checkDecisionAfter(int i){
    boolean allOneToOne= true;
    for (int j= i + 1; j <= stages.size(); j++){
      allOneToOne &= oneToOne.contains(name(j - 1));
      var d= name(j);
      var isDecision= j < stages.size() ? decision.contains(d) : shortCircuit.contains(d);
      var exempt= (d.equals("limit") || d.equals("first")) && allOneToOne;
      if (isDecision && !exempt){ throw decisionMisuse(i, j); }
      if (j < stages.size() && fence.contains(d)){ return; }
    }
  }
  private Error decisionMisuse(int s, int d){
    return misuse("`"+show(s)+"` receives the elements of `"+c.mode+"` as `mut` and runs before the decision of `"+show(d)
      +"`, with no fence between them: it can not run in parallel without mutating elements the flow may never process. End the flow expression with `.list` before `"
      +show(d)+"`, and continue with `.seqFlow`.");
  }
  private void checkForEach(){
    if (!terminal.name().equals("forEach")){ return; }
    int f= stages.size() - 1;
    while (f >= 0 && !fence.contains(name(f))){ f--; }
    if (f == stages.size() - 1){ return; }
    var segment= stages.subList(f + 1, stages.size());
    var seqOk= c.mode == FlowMode.Seq && segment.stream().noneMatch(s->toRead.contains(s.name()));
    var immOk= c.mode == FlowMode.ParImm
      && stages.stream().allMatch(s->s.args().stream().allMatch(a->!isLambda(a) || onlyImmCapture(a)))
      && stages.stream().noneMatch(s->Set.of("flatMapMut", "actorMut", "allToAll", "++").contains(s.name()));
    if (seqOk || immOk){ return; }
    throw misuse("`.forEach` runs its lambda while `"+show(f + 1, stages.size())+"` may still run on the elements after, preventing the parallelism `"
      +c.mode+"` asks for: write `.for`, which runs its lambda once every stage has finished.");
  }
  static final Set<String> emptyStages= Set.of("map", "++", "scan", "sort", "distinct", "distinctMut", "sortDistinct", "max", "min", "limit");
  static final Set<String> countStages= Set.of("map", "++", "scan", "sort", "limit");
  static final Set<String> elemsStages= Set.of("map", "++", "sort", "max", "min", "filter", "flatMap", "flatMapMut", "mapFilter");
  static final Set<String> elemsToCount= Set.of("filter", "flatMap", "flatMapMut", "mapFilter", "distinct", "distinctMut", "sortDistinct", "max", "min");
  static final Set<String> firstStages= Set.of("map", "++", "scan", "distinct", "distinctMut", "limit");
  private boolean allIn(int from, int to, Set<String> set){ return java.util.stream.IntStream.range(from, to).allMatch(i->set.contains(name(i))); }
  private boolean readsEmpty(int from){ return allIn(from, stages.size(), emptyStages) && terminal.name().equals("isEmpty"); }
  private boolean readsCount(int from){ return allIn(from, stages.size(), countStages) && terminal.name().equals("size"); }
  private boolean readsFirst(int from){ return allIn(from, stages.size(), firstStages) && terminal.name().equals("first"); }
  private boolean orderBlindTerminal(){
    var t= terminal.name();
    if (t.equals("get") || t.equals("getOpt")){ return true; }
    if (!t.equals("sum")){ return false; }
    var sn= terminal.args().getFirst();
    return sn instanceof Nat$c$0Instance || sn instanceof Byte$o$0Instance || sn instanceof Num$c$0Instance;
  }
  private boolean readsElems(int from){
    for (int k= from; k <= stages.size(); k++){
      if (k == stages.size()){ return orderBlindTerminal(); }
      var n= name(k);
      if (elemsToCount.contains(n) && readsCount(k + 1)){ return true; }
      if (elementwise.contains(n) && readsEmpty(k + 1)){ return true; }
      if (!elemsStages.contains(n)){ return false; }
    }
    throw new AssertionError();
  }
  private String reads(int from){
    if (readsEmpty(from)){ return "whether there are elements"; }
    if (readsCount(from)){ return "how many elements there are"; }
    if (readsElems(from)){ return "which elements there are, in any order"; }
    return "the first element";
  }
  private boolean pointless(int i){
    var n= name(i);
    var r= i + 1;
    return switch(n){
      case "sort" -> readsEmpty(r) || readsCount(r) || readsElems(r);
      case "map", "scan" -> readsEmpty(r) || readsCount(r);
      case "distinct", "distinctMut" -> readsEmpty(r) || readsFirst(r);
      case "sortDistinct", "max", "min" -> readsEmpty(r);
      default -> false;
    };
  }
  private void checkShape(){
    for (int i= 0; i < stages.size(); i++){
      if (!pointless(i)){ continue; }
      throw misuse("`"+show(i)+"` is pointless: `"+show(i + 1, stages.size() + 1)+"` reads only "+reads(i + 1)
        +", and `"+show(i)+"` does not change that. Remove `"+show(i)+"`.");
    }
    for (int i= 0; i < stages.size(); i++){ checkShapeAt(i); }
    checkTerminalShape();
  }
  private void checkShapeAt(int i){
    var n= name(i);
    var next= name(i + 1);
    var nextIsStage= i + 1 < stages.size();
    if (n.equals("sort") && next.equals("sort") && nextIsStage){
      throw misuse("Two sorting stages in a row: `.sort by1 .sort by2` is one sort by `by2` with ties ordered by `by1`. Write `.sort(by2.then by1)`, or `.sort(by1.then by2)` if `by1` is the main order.");
    }
    if (n.equals("limit") && next.equals("limit") && nextIsStage){
      var k= Long.min(natToLong(stages.get(i).args().getFirst()), natToLong(stages.get(i + 1).args().getFirst()));
      throw misuse("Two limits in a row: `"+show(i, i + 2)+"` keeps the first "+k+" elements. Write `.limit "+k+"`.");
    }
    if (n.equals("limit") && i > 0 && oneToOne.contains(name(i - 1))){
      int from= i - 1;
      while (from > 0 && oneToOne.contains(name(from - 1))){ from--; }
      var xs= show(from, i);
      throw misuse("`"+xs+" "+show(i)+"` runs `"+xs+"` on the same elements as `"+show(i)+" "+xs+"`. Write `"+show(i)+"` before `"+xs+"`.");
    }
    if (n.equals("flatMapMut") || n.equals("distinctMut")){ checkDiscardedMut(i); }
    if (n.equals("sort") && nextIsStage && (next.equals("max") || next.equals("min"))){
      throw misuse("Wasted sorting: `"+show(i, i + 2)+"` sorts the elements `"+show(i + 1)+"` drops. Write `"+show(i + 1)+" "+show(i)+"`.");
    }
    if (n.equals("sort") && nextIsStage && next.equals("filter")){ checkSortFilter(i + 1); }
  }
  private void checkDiscardedMut(int i){
    var pass= Set.of("sort", "max", "min", "sortDistinct", "distinctMut", "limit");
    for (int j= i + 1; j <= stages.size(); j++){
      var t= name(j);
      if (Set.of("distinct", "++", "size", "isEmpty").contains(t)){
        var fix= name(i).equals("flatMapMut") ? "`.flatMap`" : "`.distinct`";
        throw misuse("`"+show(i)+"` sends `mut` elements downstream, but `"+show(j)+"` discards their mutability. Write "+fix+".");
      }
      if (j == stages.size() || !pass.contains(t)){ return; }
    }
  }
  private void checkSortFilter(int filter){
    for (int j= filter + 1; j <= stages.size(); j++){
      var t= name(j);
      if (j == stages.size() && shortCircuit.contains(t)){ return; }
      if (j < stages.size() && decision.contains(t)){ return; }
      if (j == stages.size() || fence.contains(t)){
        throw misuse("Wasted sorting: `.sort .filter` also sorts the elements `.filter` drops. Write `.filter .sort`.");
      }
    }
  }
  private void checkTerminalShape(){
    var t= terminal.name();
    var last= stages.isEmpty() ? "" : stages.getLast().name();
    if (t.equals("forEach") && (stages.isEmpty() || fence.contains(last))){
      var after= stages.isEmpty() ? "the source" : "`"+show(stages.size() - 1)+"`";
      throw misuse("`.forEach` directly after "+after+" has no stage to run while it receives the elements. Write `.for`.");
    }
    if (!(t.equals("first") || t.equals("last")) || !(last.equals("sort") || last.equals("sortDistinct"))){ return; }
    var fix= t.equals("first") ? "`.min .first`" : last.equals("sort") ? "`.max .last`" : "`.max .first`";
    throw misuse("Wasted sorting: `"+show(stages.size() - 1, stages.size() + 1)+"` sorts every element to return one. Write "+fix+".");
  }
}
