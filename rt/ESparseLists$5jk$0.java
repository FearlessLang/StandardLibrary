package _base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static _base.Util.*;

public interface ESparseLists$5jk$0{
  default Object imm$backedWithArray$1(Object p0){ return new ESparseList$2rs$1Instance(new SparseArrayStore(SparseArrayStore.checkedCap("ESparseLists.backedWithArray", Nat$c$0Instance.unwrap(p0)))); }
  default Object imm$backedWithMap$1(Object p0){ return new ESparseList$2rs$1Instance(new SparseMapStore(Nat$c$0Instance.unwrap(p0))); }
  default Object imm$backedWithSparseSegmentTree$1(Object p0){ return new ESparseList$2rs$1Instance(new SparseTreeStore(Nat$c$0Instance.unwrap(p0))); }
  ESparseLists$5jk$0 instance= new ESparseLists$5jk$0(){};
}
interface SparseStore{
  long cap();
  long count();
  Object at(long i);
  void put(long i, Object e);
  long[] present();
  SparseStore empty(long cap);
  SparseStore copy();
  void grow(long cap);
}
final class SparseArrayStore implements SparseStore{
  SparseArrayStore(int cap){ xs= new Object[cap]; }
  private Object[] xs;
  private long count= 0;
  static int checkedCap(String m, long c){
    if (Long.compareUnsigned(c, Integer.MAX_VALUE) >= 0){ throw detErr(m+": capacity "+Long.toUnsignedString(c)+" is not less than "+Integer.MAX_VALUE); }
    return (int) c;
  }
  @Override public long cap(){ return xs.length; }
  @Override public long count(){ return count; }
  @Override public Object at(long i){ return xs[(int) i]; }
  @Override public void put(long i, Object e){
    count+= (e == null ? 0 : 1) - (xs[(int) i] == null ? 0 : 1);
    xs[(int) i]= e;
  }
  @Override public long[] present(){ return IntStream.range(0, xs.length).filter(i->xs[i] != null).asLongStream().toArray(); }
  @Override public SparseStore empty(long cap){ return new SparseArrayStore((int) cap); }
  @Override public SparseStore copy(){
    var r= new SparseArrayStore(0);
    r.xs= xs.clone();
    r.count= count;
    return r;
  }
  @Override public void grow(long cap){ xs= Arrays.copyOf(xs, checkedCap("ESparseList.softIncreaseCapacity", cap)); }
}
final class SparseMapStore implements SparseStore{
  SparseMapStore(long cap){ this.cap= cap; }
  private long cap;
  private TreeMap<Long,Object> m= new TreeMap<>(Long::compareUnsigned);
  @Override public long cap(){ return cap; }
  @Override public long count(){ return m.size(); }
  @Override public Object at(long i){ return m.get(i); }
  @Override public void put(long i, Object e){
    if (e == null){ m.remove(i); return; }
    m.put(i, e);
  }
  @Override public long[] present(){ return m.keySet().stream().mapToLong(i->i).toArray(); }
  @Override public SparseStore empty(long cap){ return new SparseMapStore(cap); }
  @Override public SparseStore copy(){
    var r= new SparseMapStore(cap);
    r.m.putAll(m);
    return r;
  }
  @Override public void grow(long cap){ this.cap= cap; }
}
final class SparseTreeStore implements SparseStore{
  SparseTreeStore(long cap){ this.cap= cap; bits= bitsFor(cap); }
  private static final int leafBits= 9;
  private long cap;
  private long count= 0;
  private int bits;
  private Object[] root;
  private static int bitsFor(long cap){ return Long.compareUnsigned(cap, 1) <= 0 ? 0 : 64 - Long.numberOfLeadingZeros(cap - 1); }
  private static Object[] make(int b){ return new Object[b > leafBits ? 2 : 1 << b]; }
  private static int child(long i, int b){ return (int) ((i >>> (b - 1)) & 1); }
  @Override public long cap(){ return cap; }
  @Override public long count(){ return count; }
  @Override public Object at(long i){
    var n= root;
    int b= bits;
    for (; n != null && b > leafBits; b--){ n= (Object[]) n[child(i, b)]; }
    return n == null ? null : n[(int) (i & ((1L << b) - 1))];
  }
  @Override public void put(long i, Object e){
    if (root == null){
      if (e == null){ return; }
      root= make(bits);
    }
    var n= root;
    for (int b= bits; b > leafBits; b--){
      int c= child(i, b);
      if (n[c] == null){
        if (e == null){ return; }
        n[c]= make(b - 1);
      }
      n= (Object[]) n[c];
    }
    int k= (int) (i & ((1L << Math.min(bits, leafBits)) - 1));
    count+= (e == null ? 0 : 1) - (n[k] == null ? 0 : 1);
    n[k]= e;
  }
  private static void walk(Object[] n, int b, long base, LongStream.Builder out){
    if (b <= leafBits){
      for (int k= 0; k < n.length; k++){ if (n[k] != null){ out.add(base + k); } }
      return;
    }
    for (int c= 0; c < 2; c++){ if (n[c] != null){ walk((Object[]) n[c], b - 1, base | ((long) c << (b - 1)), out); } }
  }
  @Override public long[] present(){
    var out= LongStream.builder();
    if (root != null){ walk(root, bits, 0, out); }
    return out.build().toArray();
  }
  private static Object[] deep(Object[] n, int b){
    var r= n.clone();
    if (b > leafBits){
      for (int c= 0; c < 2; c++){ if (r[c] != null){ r[c]= deep((Object[]) r[c], b - 1); } }
    }
    return r;
  }
  @Override public SparseStore empty(long cap){ return new SparseTreeStore(cap); }
  @Override public SparseStore copy(){
    var r= new SparseTreeStore(cap);
    r.count= count;
    r.root= root == null ? null : deep(root, bits);
    return r;
  }
  @Override public void grow(long cap){
    this.cap= cap;
    int nb= bitsFor(cap);
    while (root != null && bits < nb){
      if (bits < leafBits){
        bits= Math.min(nb, leafBits);
        root= Arrays.copyOf(root, 1 << bits);
        continue;
      }
      root= new Object[]{root, null};
      bits++;
    }
    bits= Math.max(bits, nb);
  }
}
final class ESparseList$2rs$1Instance implements ESparseList$2rs$1{
  ESparseList$2rs$1Instance(SparseStore s){ this.s= s; }
  private SparseStore s;
  private static Object opt(Object e){ return e == null ? optEmpty() : optSome(e); }
  private static Object nat(long i){ return Nat$c$0Instance.instance(i); }
  private static LongStream upTo(long cap){ return LongStream.iterate(0, i->Long.compareUnsigned(i, cap) < 0, i->i + 1); }
  private long idx(String m, Object p0){
    long i= Nat$c$0Instance.unwrap(p0);
    if (Long.compareUnsigned(i, s.cap()) >= 0){ throw detErr("ESparseList."+m+": Index "+Long.toUnsignedString(i)+" out of bounds for ESparseList of capacity "+Long.toUnsignedString(s.cap())); }
    return i;
  }
  private boolean test(Object f, long i){ return isTrue(callMF$2(f, s.at(i))); }
  private LongStream where(Object f){ return Arrays.stream(s.present()).filter(i->test(f, i)); }
  private long lastWhere(Object f){
    var is= s.present();
    for (int k= is.length - 1; k >= 0; k--){ if (test(f, is[k])){ return is[k]; } }
    return -1;
  }
  private SparseStore drain(){
    var d= s;
    s= s.empty(s.cap());
    return d;
  }
  private long holes(){ return s.cap() - s.count(); }
  private long fill(Object p0){
    var l= (EList$1k$1)p0;
    long n= Nat$c$0Instance.unwrap(l.read$size$0());
    long next= 0;
    for (long i= 0; Long.compareUnsigned(i, s.cap()) < 0 && next < n; i++){
      if (s.at(i) == null){ s.put(i, l.mut$get$1(nat(next++))); }
    }
    return next;
  }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object read$close$0(){ return this; }
  @Override public Object read$capacity$0(){ return nat(s.cap()); }
  @Override public Object read$numHoles$0(){ return nat(holes()); }
  @Override public Object mut$get$1(Object p0){ return opt(s.at(idx("get", p0))); }
  @Override public Object read$get$1(Object p0){ return opt(s.at(idx("get", p0))); }
  @Override public Object mut$set$2(Object p0, Object p1){ s.put(idx("set", p0), p1); return this; }
  @Override public Object mut$remove$1(Object p0){ s.put(idx("remove", p0), null); return this; }
  @Override public Object mut$clear$0(){ s= s.empty(s.cap()); return this; }
  @Override public Object mut$swap$2(Object p0, Object p1){
    long i= idx("swap", p0);
    long j= idx("swap", p1);
    var e= s.at(i);
    s.put(i, s.at(j));
    s.put(j, e);
    return this;
  }
  @Override public Object mut$all$1(Object p0){ return bool(Arrays.stream(s.present()).allMatch(i->test(p0, i))); }
  @Override public Object mut$any$1(Object p0){ return bool(Arrays.stream(s.present()).anyMatch(i->test(p0, i))); }
  @Override public Object mut$none$1(Object p0){ return bool(Arrays.stream(s.present()).noneMatch(i->test(p0, i))); }
  @Override public Object mut$firstIndexWhere$1(Object p0){
    var r= where(p0).findFirst();
    return r.isEmpty() ? optEmpty() : optSome(nat(r.getAsLong()));
  }
  @Override public Object mut$lastIndexWhere$1(Object p0){
    long i= lastWhere(p0);
    return i == -1 ? optEmpty() : optSome(nat(i));
  }
  @Override public Object mut$indicesWhere$1(Object p0){ return Flow$o$1Instance.of(where(p0).mapToObj(ESparseList$2rs$1Instance::nat).toList(), FlowMode.ParImm); }
  @Override public Object mut$removeIf$1(Object p0){ where(p0).forEach(i->s.put(i, null)); return this; }
  @Override public Object mut$removeFirstWhere$1(Object p0){ where(p0).findFirst().ifPresent(i->s.put(i, null)); return this; }
  @Override public Object mut$removeLastWhere$1(Object p0){
    long i= lastWhere(p0);
    if (i != -1){ s.put(i, null); }
    return this;
  }
  @Override public Object mut$removeAll$1(Object p0){
    var l= (EList$1k$1)p0;
    long n= Nat$c$0Instance.unwrap(l.read$size$0());
    var seen= new HashSet<Long>();
    for (long k= 0; k < n; k++){
      long i= idx("removeAll", l.read$get$1(nat(k)));
      if (!seen.add(i)){ throw detErr("ESparseList.removeAll: Index "+Long.toUnsignedString(i)+" is present twice in the indices to remove"); }
    }
    seen.forEach(i->s.put(i, null));
    return this;
  }
  @Override public Object mut$trimTo$2(Object p0, Object p1){
    long start= Nat$c$0Instance.unwrap(p0);
    long end= Nat$c$0Instance.unwrap(p1);
    if (Long.compareUnsigned(end, s.cap()) > 0){ throw detErr("ESparseList.trimTo: Index "+Long.toUnsignedString(end)+" out of bounds for ESparseList of capacity "+Long.toUnsignedString(s.cap())); }
    if (Long.compareUnsigned(start, end) > 0){ throw detErr("ESparseList.trimTo: start "+Long.toUnsignedString(start)+" is greater than end "+Long.toUnsignedString(end)); }
    var r= s.empty(end - start);
    for (long i: s.present()){
      if (Long.compareUnsigned(i, start) >= 0 && Long.compareUnsigned(i, end) < 0){ r.put(i - start, s.at(i)); }
    }
    s= r;
    return this;
  }
  @Override public Object mut$reverse$0(){
    var r= s.empty(s.cap());
    for (long i: s.present()){ r.put(s.cap() - 1 - i, s.at(i)); }
    s= r;
    return this;
  }
  @Override public Object mut$mapInPlace$1(Object p0){
    for (long i: s.present()){ s.put(i, callMF$2(p0, s.at(i))); }
    return this;
  }
  @Override public Object mut$shallowClone$0(){ return new ESparseList$2rs$1Instance(s.copy()); }
  @Override public Object mut$softIncreaseCapacity$1(Object p0){
    long c= Nat$c$0Instance.unwrap(p0);
    if (Long.compareUnsigned(c, s.cap()) > 0){ s.grow(c); }
    return this;
  }
  @Override public Object mut$fillHoles$1(Object p0){
    for (long i= 0; Long.compareUnsigned(i, s.cap()) < 0 && holes() != 0; i++){
      if (s.at(i) == null){ s.put(i, callMF$2(p0, nat(i))); }
    }
    return this;
  }
  @Override public Object mut$mapOpts$1(Object p0){
    for (long i= 0; Long.compareUnsigned(i, s.cap()) < 0; i++){
      var o= (Opt$c$1)callMF$3(p0, nat(i), opt(s.at(i)));
      s.put(i, isTrue(o.read$isSome$0()) ? o.mut$$bang$0() : null);
    }
    return this;
  }
  @Override public Object mut$fillFrom$1(Object p0){ fill(p0); return this; }
  @Override public Object mut$fillAndExpand$1(Object p0){
    var l= (EList$1k$1)p0;
    long next= fill(p0);
    long n= Nat$c$0Instance.unwrap(l.read$size$0());
    if (next == n){ return this; }
    long cap= s.cap();
    if (Long.compareUnsigned(cap, -1L - (n - next)) > 0){ throw detErr("ESparseList.fillAndExpand: capacity "+Long.toUnsignedString(cap)+" plus "+(n - next)+" elements is greater than Math.maxNat"); }
    s.grow(cap + (n - next));
    for (long i= cap; next < n; i++){ s.put(i, l.mut$get$1(nat(next++))); }
    return this;
  }
  private static Flow$o$1Instance opts(SparseStore d, FlowMode mode){
    Iterable<Object> it= ()->upTo(d.cap()).mapToObj(i->opt(d.at(i))).iterator();
    return Flow$o$1Instance.of(it, mode);
  }
  private static Flow$o$1Instance flat(SparseStore d, FlowMode mode){ return Flow$o$1Instance.of(Arrays.stream(d.present()).mapToObj(d::at).toList(), mode); }
  @Override public Object mut$seqFlowOpts$0(){ return opts(drain(), FlowMode.Seq).mut$hintSequential$0(); }
  @Override public Object mut$flowOpts$1(Object p0){ return opts(drain(), FlowMode.of(p0)); }
  @Override public Object mut$flatSeqFlow$0(){ return flat(drain(), FlowMode.Seq).mut$hintSequential$0(); }
  @Override public Object mut$flatFlow$1(Object p0){ return flat(drain(), FlowMode.of(p0)); }
}
