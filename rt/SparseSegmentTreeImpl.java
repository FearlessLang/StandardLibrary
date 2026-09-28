package _base;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static _base.Util.*;

public class SparseSegmentTreeImpl implements ESparseList$2rs$1 {
  SparseSegmentTree inner;

  SparseSegmentTreeImpl(SparseSegmentTreeImpl tree) {
    this.inner = tree.inner.shallowCopy();
  }

  public SparseSegmentTreeImpl(long capacity) {
    this.inner = SparseSegmentTree.of(0, capacity);
  }

  public boolean isEmpty() { return this.inner.isEmpty(); }

  @Override
  public Object mut$all$1(Object p0) {
    if (isEmpty()) { return bool(true); }
    return bool(this.inner.nonNullStream().allMatch(e -> isTrue(callMF$2(p0, e))));
  }
  @Override
  public Object mut$any$1(Object p0) {
    if (isEmpty()) { return bool(false); }
    return bool(this.inner.nonNullStream().anyMatch(e -> isTrue(callMF$2(p0, e))));
  }

  @Override
  public Object mut$none$1(Object p0) {
    if (isEmpty()) { return bool(true); }
    return bool(this.inner.nonNullStream().noneMatch(e -> isTrue(callMF$2(p0, e))));
  }

  @Override
  public Object mut$firstIndexWhere$1(Object p0) {
    if (this.isEmpty()) { return optEmpty(); }
    return toOpt(this.inner.indexedStream()
      .filter(e -> isTrue(callMF$2(p0, e.element())))
      .findFirst()
      .map(IndexedElement::index));
  }

  @Override
  public Object mut$lastIndexWhere$1(Object p0) {
    if (this.isEmpty()) { return optEmpty(); }
    return toOpt(
      this.inner.reversedIndexStream()
        .filter(e -> isTrue(callMF$2(p0, e.element())))
        .findFirst()
        .map(IndexedElement::index));
  }

  @Override
  public Object mut$indicesWhere$1(Object p0) {
    if (this.isEmpty()) { return Flow$o$1Instance.of(); }
    return Flow$o$1Instance.of(
      this.inner.indexedStream()
        .filter(e -> isTrue(callMF$2(p0, e.element())))
        .map(IndexedElement::index));
  }


  @Override
  public Object mut$set$2(Object p0, Object p1) {
    inner.set(inner.idx(p0, ".set"), p1);
    return this;
  }

  @Override
  public Object mut$clear$0() {
    if (this.isEmpty()) {return this;}
    this.inner = SparseSegmentTree.of(inner.start(), inner.end());
    return this;
  }

  @Override
  public Object mut$removeIf$1(Object p0) {
    this.inner.removeIf(e -> isTrue(callMF$2(p0, e)));
    return this;
  }

  @Override
  public Object mut$remove$1(Object p0) {
    this.inner.set(inner.idx(p0, ".remove"), null);
    return this;
  }

  @Override
  public Object mut$removeAll$1(Object p0) {
    List<?> indices = EList$1k$1Instance.unwrap(p0);
    Set<Long> seen = new LinkedHashSet<>();
    for (Object o : indices) {
      long index = inner.idx(o, ".removeAll");
      if (!seen.add(index)) {
        throw err("ESparseList.removeAll: Index " + index + " is a duplicated index: " + indices);
      }
    }
    seen.forEach(i -> inner.set(i, null));
    return this;
  }

  @Override
  public Object mut$removeFirstWhere$1(Object p0) {
    this.inner.explore(
      (RemoveFirst) e -> isTrue(callMF$2(p0, e))
    );
    return this;
  }

  @Override
  public Object mut$removeLastWhere$1(Object p0) {
    this.inner.explore(
      (RemoveLast) e -> isTrue(callMF$2(p0, e))
    );
    return this;
  }

  @Override
  public Object mut$trimTo$2(Object p0, Object p1) {
    long start = Nat$c$0Instance.unwrap(p0);
    long end = Nat$c$0Instance.unwrap(p1);
    check(
      lessThan(start, end),
      "ESparseList.trimTo: start ("+Long.toUnsignedString(start)+") must be less than end ("+Long.toUnsignedString(end)+")"
    );
    check(
      lessThan(end, inner.capacity()),
      "ESparseList.trimTo: Cannot trim a list with capacity "
        +inner.capacity()+" to an end of "+Long.toUnsignedString(end)
    );

    this.trimTo(start, end);
    return this;
  }
  void trimTo(long start, long end) {
    switch (inner) {
      case SparseSegmentLeaf leaf -> leaf.trimTo(start, end);
      case NullRegion nullRegion -> nullRegion.trimTo(start, end);
      case SparseSegmentNode node -> {
        boolean startInsideLeft = lessThanEq(node.left.start(), start);
        boolean endInsideLeft = lessThanEq(end, node.left.end());
        if (startInsideLeft && endInsideLeft) {
          this.inner = node.left;
          trimTo(start, end);
          return;
        }
        if (!startInsideLeft && !endInsideLeft) {
          this.inner = node.left;
          trimTo(start, end);
          return;
        }
        // in both left and right
        node.left.trimTo(start, node.midPoint);
        node.right.trimTo(node.midPoint, end);
        node.numHoles = node.left.numHoles() + node.right.numHoles();
      }
    }
  }
  static boolean lessThan(long a, long b) { return Long.compareUnsigned(a, b) < 0; }
  static boolean lessThanEq(long a, long b) { return Long.compareUnsigned(a, b) <= 0; }
  static long max(long a, long b) {
    if (Long.compareUnsigned(a, b) >= 0) {
      return a;
    }
    return b;
  }
  static long min(long a, long b) {
    if (Long.compareUnsigned(a, b) <= 0) {
      return a;
    }
    return b;
  }


  static int numHoles(Object[] arr, int start, int end) {
    int numHoles = 0;
    for (int i = start; i < end; i++) {
      if (arr[i] == null) { numHoles++; }
    }
    return numHoles;
  }

  @Override
  public Object mut$reverse$0() {
    if (this.isEmpty()) { return this; }
    this.inner.reverse();
    return this;
  }

  @Override
  public Object mut$mapInPlace$1(Object p0) {
    if (this.isEmpty()) { return this; }
    inner.mapElements(o -> callMF$2(p0, o));
    return this;
  }

  @Override
  public Object mut$swap$2(Object p0, Object p1) {
    final long i = inner.idx(p0, ".swap");
    final long j = inner.idx(p1, ".swap");
    if (i == j) { return this; }
    this.inner.swap(i, j);
    return this;
  }

  @Override
  public Object mut$shallowClone$0() {
    return new SparseSegmentTreeImpl(this);
  }

  @Override
  public Object read$capacity$0() {
    return Nat$c$0Instance.instance(inner.capacity());
  }

  @Override
  public Object read$numHoles$0() {
    return Nat$c$0Instance.instance(inner.numHoles());
  }

  @Override
  public Object mut$softIncreaseCapacity$1(Object p0) {
    long proposedCapacity = Nat$c$0Instance.unwrap(p0);
    this.inner = this.inner.grow(proposedCapacity);
    return this;
  }

  @Override
  public Object read$get$1(Object p0) {
    return mut$get$1(p0);
  }

  @Override
  public Object mut$get$1(Object p0) {
    return optNullable(inner.get(inner.idx(p0, ".get")));
  }

  @Override
  public Object mut$fillHoles$1(Object p0) {
    this.inner.explore(NodeExplorer.passThroughToLeaf(leaf ->  {
      if (leaf.numHoles == 0) { return; }
      for (int i = 0; i < leaf.data.length; i++) {
        if (leaf.data[i] != null) { continue; }
        leaf.data[i] = callMF$2(p0, Nat$c$0Instance.instance(i+leaf.start));
      }
      leaf.numHoles = 0;
    }));
    return this;
  }

  @Override
  public Object mut$mapOpts$1(Object p0) {
    this.inner.explore(NodeExplorer.passThroughToLeaf(leaf -> {
      for (int i = 0; i < leaf.data.length; i++) {
        Object elem = leaf.data[i];
        Object result = callMF$3(p0, Nat$c$0Instance.instance(i+leaf.start), optNullable(leaf.data[i]));
        if (optIsSome(result)) {
          leaf.data[i] = optGet(result);
          if (elem == null) { leaf.numHoles--; }
        } else {
          leaf.data[i] = null;
          if (elem != null) { leaf.numHoles++; }
        }
      }
    }));

    return this;
  }

  @Override
  public Object mut$fillFrom$1(Object p0) {
    if (inner.numHoles() == 0) { return this; }
    List<Object> elist = EList$1k$1Instance.unwrap(p0);
    int amountUsed = inner.explore(new NodeExplorer<>() {
      int consumedIndex = 0;
      @Override
      public Integer leaf(SparseSegmentLeaf leaf) {
        for (int i=0; i<leaf.data.length && consumedIndex < elist.size(); i++) {
          Object elem = leaf.data[i];
          if (elem == null) {
            leaf.data[i] = elist.get(consumedIndex);
            consumedIndex++;
            leaf.numHoles--;
          }
        }

        return this.consumedIndex;
      }

      @Override
      public Integer node(SparseSegmentNode node) {
        if (consumedIndex == elist.size()) {return consumedIndex;}
        node.instantiateLeft();
        long numHoles = node.left.numHoles();
        node.left.explore(this);
        node.numHoles -= numHoles - node.left.numHoles();
        if (consumedIndex == elist.size()) {return consumedIndex;}
        node.instantiateRight();
        numHoles = node.right.numHoles();
        node.right.explore(this);
        node.numHoles -= numHoles - node.right.numHoles();
        return consumedIndex;
      }

      @Override
      public Integer nullRegion(NullRegion nullRegion) {
        throw new UnsupportedOperationException("Tried to set into nullRegion, should replace this at the parent first");
      }
    });
    return this;
  }

  @Override
  public Object mut$fillAndExpand$1(Object p0) {
    // There exists a more efficient implementation, but this works for now
    List<Object> source = EList$1k$1Instance.unwrap(p0);
    if (lessThan(this.inner.numHoles(), source.size())) {
      this.mut$softIncreaseCapacity$1(this.inner.capacity() + Nat$c$0Instance.unwrap(source.size()));
    }
    this.mut$fillFrom$1(p0);
    return this;
  }
  private Stream<Object> drain() {
    Stream<Object> stream = this.inner.nullStream();
    this.inner = SparseSegmentTree.of(this.inner.start(), this.inner.end());
    return stream;
  }
  private Stream<Object> drainExcludeNull() {
    Stream<Object> stream = this.inner.nonNullStream();
    this.inner = SparseSegmentTree.of(this.inner.start(), this.inner.end());
    return stream;
  }

  @Override
  public Object mut$seqFlowOpts$0() { return Flow$o$1Instance.of(drain().map(Util::optNullable)); }
  @Override
  public Object mut$flowOpts$1(Object p0) { return Flow$o$1Instance.of(drain().map(Util::optNullable).parallel()); }

  @Override
  public Object mut$flatSeqFlow$0() {
    return Flow$o$1Instance.of(drainExcludeNull());
  }

  @Override
  public Object mut$flatFlow$1(Object p0) { return Flow$o$1Instance.of(drainExcludeNull().parallel()); }

  @Override
  public Object mut$getEList$0() {
    if (inner.numHoles() != 0) {
      throw err("ESparseList.getList: Cannot create an EList from an ESparseList that is not full ("
        + inner.numHoles() + " of " + inner.capacity() + " slots are holes). Fill the holes first.");
    }
    check(
      lessThan(inner.capacity(), Integer.MAX_VALUE),
      "ESparseList.getList: Capacity of ESparseList ("+ inner.capacity() + ") is too large to be converted into a List, must be less than 2147483647"
    );
    ArrayList<Object> eList = new ArrayList<>((int) inner.capacity());
    this.inner.nullStream().forEach(eList::add);
    return EList$1k$1Instance.unsafeWrap(eList);
  }

  @Override public Object mut$eList$0() {
    if (inner.numHoles() != 0) { return optEmpty(); }
    // should we return optEmpty for capacity >= Integer.MAX_VALUE, or just wrap this into an OOM error?
    ArrayList<Object> eList = new ArrayList<>((int) inner.capacity());
    this.inner.nullStream().forEach(eList::add);
    return optSome(EList$1k$1Instance.unsafeWrap(eList));
  }

}

interface RemoveFirst extends NodeExplorer<Boolean> {
  boolean shouldRemove(Object elem);
  default Boolean leaf(SparseSegmentLeaf leaf) {
    for (int i=0; i<leaf.data.length; i++) {
      Object elem = leaf.data[i];
      if (elem != null && shouldRemove(elem)) {
        leaf.data[i] = null;
        return true;
      }
    }
    return false;
  }

  default Boolean node(SparseSegmentNode node) {
    return node.left.explore(this) && node.right.explore(this);
  }

  default Boolean nullRegion(NullRegion nullRegion) { return false; }
}

interface RemoveLast extends NodeExplorer<Boolean> {
  boolean shouldRemove(Object elem);
  default Boolean leaf(SparseSegmentLeaf leaf) {
    for (int i=leaf.data.length-1; i>=0; i--) {
      Object elem = leaf.data[i];
      if (elem != null && shouldRemove(elem)) {
        leaf.data[i] = null;
        return true;
      }
    }
    return false;
  }

  default Boolean node(SparseSegmentNode node) {
    return node.right.explore(this) && node.left.explore(this);
  }

  default Boolean nullRegion(NullRegion nullRegion) { return false; }
}
