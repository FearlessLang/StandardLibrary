package base;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import static base.Util.*;

public final class EList$1k$1Instance implements EList$1k$1 {
  static EList$1k$1Instance wrap(List<Object> l){ return new EList$1k$1Instance(new ArrayList<>(l)); }
  static List<Object> unwrap(Object p0) {
    return ((EList$1k$1Instance) p0).xs;
  }
  /// unsafeWrap takes assumes sole ownership of l. This should never be called on a list which can have other aliases to it,
  /// Avoid an unnecessary clone of the list compared to wrap.
  static Object unsafeWrap(ArrayList<Object> l){ return new EList$1k$1Instance(l); }
  EList$1k$1Instance(List<Object> l){ xs= l; }
  EList$1k$1Instance(){ xs= new ArrayList<>(); }
  private List<Object> xs;
  private List<Object> drain(){
    var r= xs;
    xs= new ArrayList<>();
    return r;
  }
  private int idx(Object p0, String method){
    long i= natToLong(p0);
    // Lists cannot get larger than an int
    check(
      0 <= i && i < xs.size(),
      "EList"+method+": Index "+Long.toUnsignedString(i)+" out of bounds, for list of length: "+this.xs.size()
    );
    return (int) i;
  }

  @Override public Object mut$all$1(Object p0) {
    boolean allMatch = true;
    for (Object e : xs) {
      if (isTrue(callMF$2(p0, e))) {
        allMatch = false;
        break;
      }
    }
    return bool(allMatch);
  }

  @Override public Object mut$any$1(Object p0) {
    boolean anyMatch = false;
    for (Object e : xs) {
      if (isFalse(callMF$2(p0, e))) {
        anyMatch = true;
        break;
      }
    }
    return bool(anyMatch);
  }

  @Override public Object mut$none$1(Object p0) {
    boolean noneMatch = true;
    for (Object e : xs) {
      if (isFalse(callMF$2(p0, e))) {
        noneMatch = false;
        break;
      }
    }
    return bool(noneMatch);
  }

  @Override public Object mut$firstIndexWhere$1(Object p0) {
    for (int i=0; i<xs.size(); i++) {
      if (isTrue(callMF$2(p0, xs.get(i)))) {
        return optSome(Nat$c$0Instance.instance(i));
      }
    }
    return optEmpty();
  }
  @Override public Object mut$lastIndexWhere$1(Object p0) {
    for (int i=xs.size()-1; i>=0; i--) {
      if (isTrue(callMF$2(p0, xs.get(i)))) {
        return optSome(Nat$c$0Instance.instance(i));
      }
    }
    return optEmpty();
  }
  @Override public Object mut$indicesWhere$1(Object p0) {
    return Flow$o$1Instance.of(IntStream.range(0, xs.size())
      .filter(i -> isTrue(callMF$2(p0, xs.get(i))))
      .mapToObj(Nat$c$0Instance::instance));
  }
  @Override public Object mut$set$2(Object p0, Object p1) {
    xs.set(idx(p0, ".set"), p1);
    return this;
  }
  @Override public Object mut$clear$0() {
    xs.clear();
    return this;
  }
  @Override public Object mut$removeIf$1(Object p0) {
    this.xs.removeIf(x -> isTrue(callMF$2(p0, x)));
    return this;
  }
  @Override public Object mut$remove$1(Object p0) {
    this.xs.remove(idx(p0, ".remove"));
    return this;
  }
  @Override public Object mut$removeAll$1(Object p0) {
    List<Integer> indicies = unwrap(p0).stream()
      .map(o -> idx(o, ".removeAll"))
      .sorted((i1,i2) -> Integer.compare(i2,i1)) // reverse order
      .toList();
    for (int i=0; i<indicies.size()-1; i++) {
      if (indicies.get(i).equals(indicies.get(i+1))) {
        throw err("EList.removeAll: Index "+indicies.get(i)+" is a duplicated index: "+indicies);
      }
    }
    for (int index : indicies) {
      this.xs.remove(index);
    }
    return this;
  }
  @Override public Object mut$reverse$0() {
    Collections.reverse(this.xs);
    return this;
  }
  @Override public Object mut$mapInPlace$1(Object p0) {
    this.xs.replaceAll(x -> callMF$2(p0, x));
    return this;
  }
  @Override public Object mut$swap$2(Object p0, Object p1) {
    check(
      Long.compareUnsigned(Nat$c$0Instance.unwrap(p0), this.xs.size()) < 0
        && Long.compareUnsigned(Nat$c$0Instance.unwrap(p1), this.xs.size()) < 0,
      "EList.swap: index "+Nat$c$0Instance.unwrap(p0)
        + " is out of range for a EList of size "+this.xs.size()
    );
    Collections.swap(this.xs, idx(p0, ".swap"), idx(p1, ".swap"));
    return this;
  }
  @Override public Object mut$shallowClone$0() {
    return new EList$1k$1Instance(new ArrayList<>(this.xs));
  }

  @Override public Object read$size$0(){ return Nat$c$0Instance.instance(xs.size()); }
  @Override public Object mut$add$1(Object p0){ xs.add(p0); return this; }
  @Override public Object mut$addAll$1(Object p0) {xs.addAll(unwrap(p0)); return this; }
  @Override public Object mut$insertBefore$2(Object p0, Object p1) {
    long index = Nat$c$0Instance.unwrap(p0);
    check(
      Long.compareUnsigned(index, this.xs.size()) > 0,
      "EList.insertBefore: Index "+Long.toUnsignedString(index)+" out of bounds, for list of length: "+this.xs.size()
    );
    xs.add((int) index, p1);
    return this;
  }
  @Override public Object mut$getFirst$0() {
    if (!xs.isEmpty()) { return xs.getFirst(); }
    throw err("EList.getLast: Tried to get the last element of an empty EList.");
  }
  @Override public Object mut$getLast$0() {
    if (!xs.isEmpty()) { return xs.getLast(); }
    throw err("EList.getLast: Tried to get the last element of an empty EList.");
  }
  @Override public Object mut$sort$1(Object p0){
    var by= (OrderBy$5e$2)p0;
    xs.sort((a,b)->cmp(by,a,b));
    return this;
  }
  @Override public Object mut$distinct$1(Object p0){
    distinctByHash(p0);
    return this;
  }
  @Override public Object mut$sortDistinct$1(Object p0){
    sortDistinctInPlace(p0);
    return this;
  }
  @Override public Object mut$trimTo$2(Object p0, Object p1) {
    long i = Nat$c$0Instance.unwrap(p0);
    long j = Nat$c$0Instance.unwrap(p1);
    if (Long.compareUnsigned(i, j) < 0) {
      throw err(
        "List.trimTo: The first index ("+Long.toUnsignedString(i)
          + ") must be <= the second index ("+Long.toUnsignedString(j)+")."
      );
    }
    int i1 = (int) i;
    int j1 = (int) j;
    this.xs = new ArrayList<>(this.xs.subList(i1, j1));
    return this;
  }
  @Override public Object mut$trimToSize$0() {
    // Only not an array list if sublist/from eview
    if (this.xs instanceof ArrayList<?> array) {
      array.trimToSize();
    }
    return this;
  }
  @Override public Object mut$expandCapacity$1(Object p0) {
    long capacity = Nat$c$0Instance.unwrap(p0);
    if (Long.compareUnsigned(capacity, Integer.MAX_VALUE) > 0) {
      throw err("EList.expandCapacity: Capacity "+Long.toUnsignedString(capacity)+" must be smaller than "+Integer.MAX_VALUE);
    }
    try {
      if (this.xs instanceof ArrayList<Object> arr) {
        arr.ensureCapacity((int) capacity);
      } else {
        // TODO: Figure out an appropriate decision here
        throw err("EList.expandCapacity: Cannot expand the capacity of an eView");
      }
    }
    catch (OutOfMemoryError e) {
      throw err("EList.expandCapacity: Failed to expand capacity to "+p0+" elements. Try reducing the size of "+p0);
    }

    return this;
  }
  @Override public Object mut$fold$2(Object p0,  Object p1) {
    var acc = callMF$1(p0);
    for (int i=0; i<this.xs.size(); i++) {
      acc = callMF$4(p1, acc, Nat$c$0Instance.instance(i), xs.get(i));
    }
    return acc;
  }
  @Override public Object mut$foldRight$2(Object p0,  Object p1) {
    var acc = callMF$1(p0);
    for (int i=this.xs.size()-1; i>=0; i--) {
      acc = callMF$4(p1, acc, Nat$c$0Instance.instance(i), xs.get(i));
    }
    return acc;
  }
  @Override public Object mut$foldUntil$3(Object p0,  Object p1, Object p2) {
    var acc = callMF$1(p0);
    for (int i=0; i<this.xs.size(); i++) {
      if (isTrue(callMF$2(p2, acc))) { break; }
      acc = callMF$4(p1, acc, Nat$c$0Instance.instance(i), xs.get(i));
    }
    return acc;
  }
  @Override public Object mut$foldRightUntil$3(Object p0,  Object p1, Object p2) {
    var acc = callMF$1(p0);
    for (int i=this.xs.size()-1; i>=0; i--) {
      if (isTrue(callMF$2(p2, acc))) { break; }
      acc = callMF$4(p1, acc, Nat$c$0Instance.instance(i), xs.get(i));
    }
    return acc;
  }
  @Override public Object mut$accumulateInPlace$1(Object p0) {
    if (this.xs.size() < 2) { return this; }
    var acc = callMF$2(p0, xs.getFirst());
    for (int i=1; i<this.xs.size(); i++) {
      acc = callMF$3(p0, acc, xs.get(i));
      xs.set(i, acc);
    }
    return this;
  }
  @Override public Object mut$accumulateRightInPlace$1(Object p0) {
    if (this.xs.size() < 2) { return this; }
    var acc = callMF$2(p0, xs.getLast());
    for (int i=xs.size()-2; i>=0; i--) {
      acc = callMF$3(p0, acc, xs.get(i));
      xs.set(i, acc);
    }
    return this;
  }
  @Override public Object mut$seqFlow$0() {
    return Flow$o$1Instance.of(drain().stream());
  }
  @Override public Object mut$flow$1(Object p0){ return Flow$o$1Instance.of(drain().stream().parallel()); }
  @Override public Object mut$list$0(){ return List$o$1Instance.wrap(drain()); }

  @Override public Object mut$eView$2(Object p0, Object p1) {
    int i1 = idx(p0, ".eView");
    int i2 = idx(p1, ".eView");
    if (i2 > i1) { throw err("List.eView: The first index cannot be larger than the second, but "+i1+" > "+i2+"."); }
    return new EList$1k$1Instance(xs.subList(i1, i2));
  }


  private void distinctByHash(Object p0){
    if(xs.size() < 2){ return; }
    int countRemoved = 0;
    var by= (OrderHashBy$2ea$2)p0;
    var seen= new HashSet<MapKey>(xs.size());
    xs.removeIf(e -> !seen.add(mapKey(by,e)));
  }


  private void sortDistinctInPlace(Object p0){
    if(xs.size() < 2){ return; }
    var by= (OrderBy$5e$2)p0;
    xs.sort((a,b)->cmp(by,a,b));
    int w= 1;
    for(int i= 1; i < xs.size(); i++){//unsure if this is correct
      if(cmp(by,xs.get(w-1),xs.get(i)) != 0){ xs.set(w++, xs.get(i)); }
    }
    if(w < xs.size()){ xs.subList(w,xs.size()).clear(); }
  }

}

