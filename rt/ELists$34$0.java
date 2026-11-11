package _base;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.RandomAccess;
import java.util.function.Predicate;

import static _base.Util.*;

public interface ELists$34$0 extends Sealed$2o$0{
  default Object imm$$hash$0(){ return new EList$1k$1Instance(new ArrayList<>()); }
  default Object imm$withCapacity$1(Object p0){
    long c= Nat$c$0Instance.unwrap(p0);
    if (Long.compareUnsigned(c, Integer.MAX_VALUE) >= 0){ throw detErr("ELists.withCapacity: capacity "+Long.toUnsignedString(c)+" is not less than "+Integer.MAX_VALUE); }
    return new EList$1k$1Instance(new ArrayList<>((int) c));
  }
  private static Object of(Object... es){ return new EList$1k$1Instance(new ArrayList<>(List.of(es))); }
  default Object imm$$hash$1(Object p0){ return of(p0); }
  default Object imm$$hash$2(Object p0, Object p1){ return of(p0, p1); }
  default Object imm$$hash$3(Object p0, Object p1, Object p2){ return of(p0, p1, p2); }
  default Object imm$$hash$4(Object p0, Object p1, Object p2, Object p3){ return of(p0, p1, p2, p3); }
  default Object imm$$hash$5(Object p0, Object p1, Object p2, Object p3, Object p4){ return of(p0, p1, p2, p3, p4); }
  default Object imm$$hash$6(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5){ return of(p0, p1, p2, p3, p4, p5); }
  default Object imm$$hash$7(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6){ return of(p0, p1, p2, p3, p4, p5, p6); }
  default Object imm$$hash$8(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7){ return of(p0, p1, p2, p3, p4, p5, p6, p7); }
  default Object imm$$hash$9(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8); }
  default Object imm$$hash$10(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9); }
  default Object imm$$hash$11(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10); }
  default Object imm$$hash$12(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11); }
  default Object imm$$hash$13(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12); }
  default Object imm$$hash$14(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13); }
  default Object imm$$hash$15(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14); }
  default Object imm$$hash$16(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15); }
  ELists$34$0 instance= new ELists$34$0(){};
}
final class EList$1k$1Instance implements EList$1k$1{
  EList$1k$1Instance(List<Object> l){ xs= l; }
  List<Object> xs;
  private ArrayList<Object> drain(){
    if (xs instanceof ArrayList<Object> a){ xs = new ArrayList<>(); return a; }
    var r= new ArrayList<>(xs);
    xs.clear();
    return r;
  }
  private String outOfRange(String m, String what){ return "EList."+m+": "+what+" is out of range for "+(xs.isEmpty() ? "an empty EList." : "an EList of size "+xs.size()+"."); }
  private int idx(String m, Object p0, int bound){
    long i= Nat$c$0Instance.unwrap(p0);
    if (Long.compareUnsigned(i, bound) >= 0){ throw detErr(outOfRange(m, "index "+Long.toUnsignedString(i))); }
    return (int) i;
  }
  private int idx(String m, Object p0){ return idx(m, p0, xs.size()); }
  private int range(String m, Object start, Object end){
    long rs= Nat$c$0Instance.unwrap(start);
    long re= Nat$c$0Instance.unwrap(end);
    var outside= Long.compareUnsigned(rs, xs.size()) > 0 || Long.compareUnsigned(re, xs.size()) > 0;
    if (outside){ throw detErr(outOfRange(m, "the range "+Long.toUnsignedString(rs)+" to "+Long.toUnsignedString(re))); }
    int s= (int) rs;
    int e= (int) re;
    if (s > e){ throw detErr("EList."+m+": start "+s+" is greater than end "+e+"."); }
    return s;
  }
  private boolean test(Object f, Object e){ return isTrue(callF$2(f, e)); }
  private int firstWhere(Object f){
    for (int i= 0; i < xs.size(); i += 1){ if (test(f, xs.get(i))){ return i; } }
    return -1;
  }
  private int lastWhere(Object f){
    for (int i= xs.size() - 1; i >= 0; i -= 1){ if (test(f, xs.get(i))){ return i; } }
    return -1;
  }
  private static Object optIndex(int i){ return i == -1 ? optEmpty() : optSome(Nat$c$0Instance.instance(i)); }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object read$close$0(){ return this; }
  @Override public Object read$size$0(){ return Nat$c$0Instance.instance(xs.size()); }
  @Override public Object mut$get$1(Object p0){ return xs.get(idx("get", p0)); }
  @Override public Object read$get$1(Object p0){ return xs.get(idx("get", p0)); }
  @Override public Object mut$tryGet$1(Object p0){
    long i= Nat$c$0Instance.unwrap(p0);
    if (Long.compareUnsigned(i, xs.size()) < 0){ return ok(xs.get((int) i)); }
    return fail(outOfRange("get", "index "+Long.toUnsignedString(i)));
  }
  @Override public Object read$tryGet$1(Object p0){ return mut$tryGet$1(p0); }
  @Override public Object mut$add$1(Object p0){ xs.add(p0); return this; }
  @Override public Object mut$addAll$1(Object p0){ xs.addAll(((EList$1k$1Instance) p0).xs); return this; }
  @Override public Object mut$insertBefore$2(Object p0, Object p1){ xs.add(idx("insertBefore", p0, xs.size() + 1), p1); return this; }
  @Override public Object mut$set$2(Object p0, Object p1){ xs.set(idx("set", p0), p1); return this; }
  @Override public Object mut$swap$2(Object p0, Object p1){ Collections.swap(xs, idx("swap", p0), idx("swap", p1)); return this; }
  @Override public Object mut$clear$0(){ xs.clear(); return this; }
  @Override public Object mut$remove$1(Object p0){ xs.remove(idx("remove", p0)); return this; }
  @Override public Object mut$removeIf$1(Object p0){ xs.removeIf(e -> test(p0, e)); return this; }
  @Override public Object mut$removeFirstWhere$1(Object p0){
    int i= firstWhere(p0);
    if (i != -1){ xs.remove(i); }
    return this;
  }
  @Override public Object mut$removeLastWhere$1(Object p0){
    int i= lastWhere(p0);
    if (i != -1){ xs.remove(i); }
    return this;
  }
  @Override public Object mut$removeAll$1(Object p0){
    var is= ((EList$1k$1Instance) p0).xs.stream().map(i -> idx("removeAll", i)).sorted(Collections.reverseOrder()).toList();
    for (int k= 1; k < is.size(); k += 1){
      if (is.get(k).equals(is.get(k - 1))){ throw detErr("EList.removeAll: index "+is.get(k)+" is present twice in the indices to remove."); }
    }
    for (int i : is){ xs.remove(i); }
    return this;
  }
  @Override public Object mut$trimTo$2(Object p0, Object p1){
    int s= range("trimTo", p0, p1);
    xs.subList(idx("trimTo", p1, xs.size() + 1), xs.size()).clear();
    xs.subList(0, s).clear();
    return this;
  }
  @Override public Object mut$eView$2(Object p0, Object p1){
    int s= range("eView", p0, p1);
    return new EList$1k$1Instance(new EListWindow(this, s, idx("eView", p1, xs.size() + 1)));
  }
  @Override public Object mut$reverse$0(){ Collections.reverse(xs); return this; }
  @Override public Object mut$mapInPlace$1(Object p0){ xs.replaceAll(e -> callF$2(p0, e)); return this; }
  @Override public Object mut$shallowClone$0(){ return new EList$1k$1Instance(new ArrayList<>(xs)); }
  @Override public Object mut$all$1(Object p0){ return bool(xs.stream().allMatch(e -> test(p0, e))); }
  @Override public Object mut$any$1(Object p0){ return bool(xs.stream().anyMatch(e -> test(p0, e))); }
  @Override public Object mut$none$1(Object p0){ return bool(xs.stream().noneMatch(e -> test(p0, e))); }
  @Override public Object mut$firstIndexWhere$1(Object p0){ return optIndex(firstWhere(p0)); }
  @Override public Object mut$lastIndexWhere$1(Object p0){ return optIndex(lastWhere(p0)); }
  @Override public Object mut$indicesWhere$1(Object p0){
    var res= new ArrayList<Object>();
    for (int i= 0; i < xs.size(); i += 1){ if (test(p0, xs.get(i))){ res.add(Nat$c$0Instance.instance(i)); } }
    return Flow$o$1Instance.of(res, FlowMode.ParImm);
  }
  @Override public Object mut$sort$1(Object p0){
    sortBy((OrderBy$5e$2)p0, "sort");
    return this;
  }
  @Override public Object mut$distinct$1(Object p0){
    var by= (OrderHashBy$2ea$2)p0;
    var seen= new HashSet<MapKey>();
    xs.removeIf(e -> !seen.add(mapKey(by, e)));
    return this;
  }
  @Override public Object mut$sortDistinct$1(Object p0){
    if (xs.size() < 2){ return this; }
    var by= (OrderBy$5e$2)p0;
    sortBy(by, "sortDistinct");
    int w= 1;
    for (int i= 1; i < xs.size(); i += 1){
      if (cmp(by, xs.get(w-1), xs.get(i)) != 0){ xs.set(w++, xs.get(i)); }
    }
    xs.subList(w, xs.size()).clear();
    return this;
  }
  private void sortBy(OrderBy$5e$2 by, String m){
    try{ xs.sort((a,b) -> cmp(by, a, b)); }
    catch (IllegalArgumentException e){ throw detErr("EList."+m+": the OrderBy does not give a consistent order to the elements"); }
  }
  private ArrayList<Object> storage(){ return xs instanceof EListWindow w ? w.parent.storage() : (ArrayList<Object>) xs; }
  @Override public Object mut$trimToSize$0(){ storage().trimToSize(); return this; }
  @Override public Object mut$expandCapacity$1(Object p0){
    long c= Nat$c$0Instance.unwrap(p0);
    var s= storage();
    int outside= s.size() - xs.size();
    if (Long.compareUnsigned(c, Integer.MAX_VALUE - outside) >= 0){ throw detErr("EList.expandCapacity: capacity "+Long.toUnsignedString(c)+(outside == 0 ? "" : " plus the "+outside+" elements outside the view")+" is not less than "+Integer.MAX_VALUE); }
    s.ensureCapacity((int) c + outside);
    return this;
  }
  @Override public Object mut$fold$2(Object p0, Object p1){
    var acc= callMF$1(p0);
    for (int i= 0; i < xs.size(); i += 1){ acc = callF$4(p1, acc, Nat$c$0Instance.instance(i), xs.get(i)); }
    return acc;
  }
  @Override public Object mut$foldRight$2(Object p0, Object p1){
    var acc= callMF$1(p0);
    for (int i= xs.size() - 1; i >= 0; i -= 1){ acc = callF$4(p1, acc, Nat$c$0Instance.instance(i), xs.get(i)); }
    return acc;
  }
  @Override public Object mut$foldUntil$3(Object p0, Object p1, Object p2){
    var acc= callMF$1(p0);
    for (int i= 0; i < xs.size() && !isTrue(callF$2(p2, acc)); i += 1){ acc = callF$4(p1, acc, Nat$c$0Instance.instance(i), xs.get(i)); }
    return acc;
  }
  @Override public Object mut$foldRightUntil$3(Object p0, Object p1, Object p2){
    var acc= callMF$1(p0);
    for (int i= xs.size() - 1; i >= 0 && !isTrue(callF$2(p2, acc)); i -= 1){ acc = callF$4(p1, acc, Nat$c$0Instance.instance(i), xs.get(i)); }
    return acc;
  }
  @Override public Object mut$accumulateInPlace$1(Object p0){
    for (int i= 1; i < xs.size(); i += 1){ xs.set(i, callF$3(p0, xs.get(i - 1), xs.get(i))); }
    return this;
  }
  @Override public Object mut$accumulateRightInPlace$1(Object p0){
    for (int i= xs.size() - 2; i >= 0; i -= 1){ xs.set(i, callF$3(p0, xs.get(i + 1), xs.get(i))); }
    return this;
  }
  @Override public Object mut$seqFlow$0(){ return Flow$o$1Instance.of(drain(), FlowMode.Seq); }
  @Override public Object mut$flow$1(Object p0){ return Flow$o$1Instance.of(drain(), FlowMode.of(p0)); }
  @Override public Object imm$flow$0(){ return Flow$o$1Instance.of(new ArrayList<>(xs), FlowMode.ParImm); }
  @Override public Object mut$list$0(){ return List$o$1Instance.wrap(drain()); }
}
final class EListWindow extends AbstractList<Object> implements RandomAccess{
  final EList$1k$1Instance parent;
  private final int start;
  private int end;
  EListWindow(EList$1k$1Instance parent, int start, int end){ this.parent= parent; this.start= start; this.end= end; }
  private List<Object> sub(){
    if (end > parent.xs.size()){ throw detErr("EList.eView: the view of the indices ["+start+", "+end+") is out of range for its "+(parent.xs.isEmpty() ? "empty EList." : "EList of size "+parent.xs.size()+".")); }
    return parent.xs.subList(start, end);
  }
  @Override public Object get(int i){ return sub().get(i); }
  @Override public Object set(int i, Object e){ return sub().set(i, e); }
  @Override public int size(){ return sub().size(); }
  @Override public void add(int i, Object e){ sub().add(i, e); end += 1; }
  @Override public Object remove(int i){ var r= sub().remove(i); end -= 1; return r; }
  @Override protected void removeRange(int from, int to){ sub().subList(from, to).clear(); end -= to - from; }
  @Override public boolean addAll(Collection<?> c){ sub().addAll(c); end += c.size(); return !c.isEmpty(); }
  @Override public boolean removeIf(Predicate<? super Object> f){
    var s= sub();
    int before= s.size();
    s.removeIf(f);
    end -= before - s.size();
    return before != end - start;
  }
}
