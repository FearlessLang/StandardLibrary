package _base;

import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static _base.Util.*;

public class SparseMap implements ESparseList$2rs$1 {
  /// Please note this is unsigned
  long capacity;
  /// sorted because we need iteration order in index order
  /// There's a good argument for making this a sortedList
  /// Pros:
  ///   - Much more cache-friendly
  ///   - Able to use primitives and avoid boxing
  ///   - Iteration is so much faster
  ///   - Avoids Hashing
  /// Cons:
  ///   - Adding/removing elements is slower O(n) rather than O(log(n))
  SortedMap<Long, Object> inner;

  SparseMap(SparseMap other) {
    this.capacity = other.capacity;
    this.inner = new TreeMap<>(other.inner);
  }

  public SparseMap(long capacity) {
    this.capacity = capacity;
    this.inner = new TreeMap<>(Long::compareUnsigned);
  }

  private static LongStream allIndices(long cap) {
    if (cap >= 0) {
      return LongStream.range(0, cap);
    }
    // cap >= 2^63: [0, 2^63-1] followed by [2^63, cap), which is [MIN_VALUE, cap) when read as signed.
    return LongStream.concat(LongStream.rangeClosed(0, Long.MAX_VALUE), LongStream.range(Long.MIN_VALUE, cap));
  }

  public boolean isEmpty() {
    return inner.isEmpty();
  }

  private long holes() {
    return capacity - inner.size();
  }

  long idx(Object p0, String method) {
    long index = Nat$c$0Instance.unwrap(p0);
    check(
      Long.compareUnsigned(index, capacity) < 0,
      "ESparseList" + method + ": Index " + Long.toUnsignedString(index) + " is out of bounds for a ESparseList with capacity " + Long.toUnsignedString(capacity)
    );
    return index;
  }

  private void setOrRemove(Long i, Object e) {
    if (e == null) {
      inner.remove(i);
    } else {
      inner.put(i, e);
    }
  }

  private Object getOpt(long i) {
    Object e = inner.get(i);
    return e == null ? optEmpty() : optSome(e);
  }

  /**
   * Hands the current contents to the caller and leaves this empty with the same capacity.
   */
  private Map<Long, Object> drain() {
    Map<Long, Object> old = inner;
    inner = new TreeMap<>(Long::compareUnsigned);
    return old;
  }

  /**
   * Fills holes from index 0 with elements of xs; returns how many elements of xs were consumed.
   */
  private int fillFromList(List<?> xs) {
    int next = 0;
    for (long i = 0; next < xs.size() && Long.compareUnsigned(i, capacity) < 0 && holes() != 0; i++) {
      if (!inner.containsKey(i)) {
        inner.put(i, xs.get(next++));
      }
    }
    return next;
  }

  @Override
  public Object mut$all$1(Object p0) {
    for (Object e : inner.values()) {
      if (isFalse(callMF$2(p0, e))) {
        return bool(false);
      }
    }
    return bool(true);
  }

  @Override
  public Object mut$any$1(Object p0) {
    for (Object e : inner.values()) {
      if (isTrue(callMF$2(p0, e))) {
        return bool(true);
      }
    }
    return bool(false);
  }

  @Override
  public Object mut$none$1(Object p0) {
    for (Object e : inner.values()) {
      if (isTrue(callMF$2(p0, e))) {
        return bool(false);
      }
    }
    return bool(true);
  }

  @Override
  public Object mut$firstIndexWhere$1(Object p0) {
    for (Map.Entry<Long, Object> e : inner.entrySet()) {
      if (isTrue(callMF$2(p0, e.getValue()))) {
        return optSome(Nat$c$0Instance.instance(e.getKey()));
      }
    }
    return optEmpty();
  }

  @Override
  public Object mut$lastIndexWhere$1(Object p0) {
    for (Map.Entry<Long, Object> e : inner.reversed().entrySet()) {
      if (isTrue(callMF$2(p0, e.getValue()))) {
        return optSome(Nat$c$0Instance.instance(e.getKey()));
      }
    }
    return optEmpty();
  }

  @Override
  public Object mut$indicesWhere$1(Object p0) {
    if (inner.isEmpty()) {
      return Flow$o$1Instance.of();
    }
    return Flow$o$1Instance.of(inner.entrySet().stream()
      .filter(e -> isTrue(callMF$2(p0, e.getValue())))
      .map(e -> Nat$c$0Instance.instance(e.getKey())));
  }

  @Override
  public Object mut$set$2(Object p0, Object p1) {
    inner.put(idx(p0, ".set"), p1);
    return this;
  }

  @Override
  public Object mut$clear$0() {
    inner.clear();
    return this;
  }

  @Override
  public Object mut$removeIf$1(Object p0) {
    inner.values().removeIf(e -> isTrue(callMF$2(p0, e)));
    return this;
  }

  @Override
  public Object mut$remove$1(Object p0) {
    inner.remove(idx(p0, ".remove"));
    return this;
  }

  @Override
  public Object mut$removeAll$1(Object p0) {
    List<?> indices = EList$1k$1Instance.unwrap(p0);
    Set<Long> seen = new HashSet<>();
    for (Object o : indices) {
      long index = idx(o, ".removeAll");
      if (!seen.add(index)) {
        throw err("ESparseList.removeAll: Index " + Long.toUnsignedString(index) + " is a duplicated index: " + indices);
      }
    }

    for (Object index : indices) {
      inner.remove(Nat$c$0Instance.unwrap(index));
    }
    return this;
  }

  @Override
  public Object mut$removeFirstWhere$1(Object p0) {
    for (Map.Entry<Long, Object> e : inner.entrySet()) {
      if (e != null && isTrue(callMF$2(p0, e.getValue()))) {
        inner.remove(e.getKey());
        break;
      }
    }
    return this;
  }

  @Override
  public Object mut$removeLastWhere$1(Object p0) {
    for (Map.Entry<Long, Object> e : inner.reversed().entrySet()) {
      if (e != null && isTrue(callMF$2(p0, e.getValue()))) {
        inner.remove(e.getKey());
        break;
      }
    }
    return this;
  }

  // Keeps [start, end) and re-bases it; capacity shrinks to end-start (same as SparseArray).
  @Override
  public Object mut$trimTo$2(Object p0, Object p1) {
    long start = Nat$c$0Instance.unwrap(p0);
    long end = Nat$c$0Instance.unwrap(p1);
    check(
      Long.compareUnsigned(start, end) <= 0 && Long.compareUnsigned(end, capacity) <= 0,
      "ESparseList.trimTo: Invalid range [" + Long.toUnsignedString(start) + ", " + Long.toUnsignedString(end)
        + ") for a ESparseList with capacity " + Long.toUnsignedString(capacity)
    );
    inner.keySet().removeIf(
      index -> Long.compareUnsigned(index, start) < 0 || Long.compareUnsigned(index, end) >= end
    );
    capacity = end - start;
    return this;
  }

  @Override
  public Object mut$reverse$0() {
    if (inner.isEmpty()) {
      return this;
    }
    SortedMap<Long, Object> reversed = new TreeMap<>(Long::compareUnsigned);
    inner.forEach((k, v) -> reversed.put(capacity - 1 - k, v));
    inner = reversed;
    return this;
  }

  @Override
  public Object mut$mapInPlace$1(Object p0) {
    for (Map.Entry<Long, Object> e : inner.entrySet()) {
      inner.put(e.getKey(), callMF$2(p0, e.getValue()));
    }
    return this;
  }

  @Override
  public Object mut$swap$2(Object p0, Object p1) {
    long i = idx(p0, ".swap");
    long j = idx(p1, ".swap");
    if (i == j) { return this; }
    Object vi = inner.get(i), vj = inner.get(j);
    setOrRemove(i, vj);
    setOrRemove(j, vi);
    return this;
  }

  @Override
  public Object mut$shallowClone$0() {
    return new SparseMap(this);
  }

  @Override
  public Object read$capacity$0() {
    return Nat$c$0Instance.instance(capacity);
  }

  @Override
  public Object read$numHoles$0() {
    return Nat$c$0Instance.instance(holes());
  }

  @Override
  public Object mut$softIncreaseCapacity$1(Object p0) {
    long proposed = Nat$c$0Instance.unwrap(p0);
    if (Long.compareUnsigned(proposed, capacity) > 0) {
      capacity = proposed;
    }
    return this;
  }

  @Override
  public Object read$get$1(Object p0) {
    return getOpt(idx(p0, ".get"));
  }

  @Override
  public Object mut$get$1(Object p0) {
    return getOpt(idx(p0, ".get"));
  }

  @Override
  public Object mut$fillHoles$1(Object p0) {
    for (long i = 0; Long.compareUnsigned(i, capacity) < 0 && holes() != 0; i++) {
      if (inner.containsKey(i)) {
        continue;
      }
      inner.put(i, callMF$2(p0, Nat$c$0Instance.instance(i)));
    }
    return this;
  }

  @Override
  public Object mut$mapOpts$1(Object p0) {
    for (long i = 0; Long.compareUnsigned(i, capacity) < 0; i++) {
      Object result = callMF$3(p0, Nat$c$0Instance.instance(i), getOpt(i));
      if (optIsSome(result)) {
        inner.put(i, optGet(result));
      } else {
        inner.remove(i);
      }
    }
    return this;
  }

  @Override
  public Object mut$fillFrom$1(Object p0) {
    fillFromList(EList$1k$1Instance.unwrap(p0));
    return this;
  }

  @Override
  public Object mut$fillAndExpand$1(Object p0) {
    List<?> xs = EList$1k$1Instance.unwrap(p0);
    int next = fillFromList(xs);
    int remaining = xs.size() - next;
    if (remaining > 0) {
      long newCapacity = capacity + remaining;
      check(
        Long.compareUnsigned(newCapacity, capacity) > 0,
        "ESparseList.fillAndExpand: Cannot expand capacity " + Long.toUnsignedString(capacity) + " by " + remaining + " without overflowing"
      );
      long start = capacity;
      capacity = newCapacity;
      for (long i = start; next < xs.size(); i++) {
        inner.put(i, xs.get(next++));
      }
    }
    return this;
  }

  private Stream<Object> flowOptsOf(Map<Long, Object> snapshot) {
    return allIndices(capacity).mapToObj(i -> {
      Object e = snapshot.get(i);
      return e == null ? optEmpty() : optSome(e);
    });
  }

  private Stream<Object> flatFlowOf(Map<Long, Object> snapshot) {
    return snapshot.values().stream();
  }

  @Override
  public Object mut$seqFlowOpts$0() {
    return Flow$o$1Instance.of(flowOptsOf(drain()));
  }

  @Override
  public Object mut$flowOpts$1(Object p0) {
    return Flow$o$1Instance.of(flowOptsOf(drain()).parallel());
  }

  @Override
  public Object mut$flatSeqFlow$0() {
    return Flow$o$1Instance.of(flatFlowOf(drain()));
  }

  @Override
  public Object mut$flatFlow$1(Object p0) {
    return Flow$o$1Instance.of(flatFlowOf(drain()).parallel());
  }

  @Override
  public Object mut$getEList$0() {
    if (holes() != 0) {
      throw err(
        "ESparseList.getSeqFlow: Cannot create a list from an ESparseList that is not full."
          + "\n Use a \"hole-safe\" flow method, or fill the holes first."
      );
    }
    return EList$1k$1Instance.unsafeWrap(new ArrayList<>(drain().values()));
  }

  @Override
  public Object mut$eList$0() {
    if (holes() != 0) {
      return optEmpty();
    }
    return optSome(EList$1k$1Instance.unsafeWrap(new ArrayList<>(drain().values())));
  }
}
