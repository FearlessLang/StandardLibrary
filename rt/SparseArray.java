package base;

import java.util.*;
import java.util.stream.IntStream;

import static base.Util.*;

public class SparseArray implements ESparseList$2rs$1 {
  int capacity;
  int numHoles;
  Object[] inner;

  SparseArray(SparseArray arr) {
    this.capacity = arr.capacity;
    this.numHoles = arr.numHoles;
    this.inner = arr.inner.clone();
  }

  public SparseArray(int capacity) {
    this.capacity = capacity;
    this.numHoles = capacity;
    this.inner = new Object[capacity];
  }

  public boolean isEmpty() {
    return capacity == numHoles;
  }

  static <T> void swap(T[] array, int i, int j) {
    T temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  /**
   * Removes the element at i (if any), keeping numHoles in sync.
   */
  void remove(int i) {
    if (inner[i] == null) {
      return;
    }
    inner[i] = null;
    numHoles++;
  }

  /**
   * Stores e at i, keeping numHoles in sync.
   */
  void put(int i, Object e) {
    if (inner[i] == null) {
      numHoles--;
    }
    inner[i] = e;
  }

  private int idx(Object p0, String method) {
    long index = Nat$c$0Instance.unwrap(p0);
    check(
      Long.compareUnsigned(index, inner.length) < 0,
      // FIX: missing space before the capacity
      "ESparseList" + method + ": Index " + Long.toUnsignedString(index) + " is out of bounds for a ESparseList with capacity " + capacity
    );
    return (int) index;
  }

  private void checkCapacity(long proposed, String method) {
    check(
      Long.compareUnsigned(proposed, Integer.MAX_VALUE) < 0,
      "ESparseList" + method + ": Cannot create an array-based Sparse List with capacity >= " + Integer.MAX_VALUE
        + " (requested " + Long.toUnsignedString(proposed) + ")"
    );
  }

  /**
   * Hands the current contents to the caller and leaves this empty with the same capacity.
   */
  private Object[] drain() {
    Object[] old = inner;
    inner = new Object[capacity];
    numHoles = capacity;
    return old;
  }

  @Override
  public Object mut$all$1(Object p0) {
    for (Object e : inner) {
      if (e == null) {
        continue;
      }
      if (isFalse(callMF$2(p0, e))) {
        return bool(false);
      }
    }
    return bool(true);
  }

  @Override
  public Object mut$any$1(Object p0) {
    for (Object e : inner) {
      if (e == null) {
        continue;
      }
      if (isTrue(callMF$2(p0, e))) {
        return bool(true);
      }
    }
    return bool(false);
  }

  @Override
  public Object mut$none$1(Object p0) {
    for (Object e : inner) {
      if (e == null) {
        continue;
      }
      if (isTrue(callMF$2(p0, e))) {
        return bool(false);
      }
    }
    return bool(true);
  }

  @Override
  public Object mut$firstIndexWhere$1(Object p0) {
    if (this.isEmpty()) {
      return optEmpty();
    }
    for (int i = 0; i < inner.length; i++) {
      Object elem = inner[i];
      if (elem == null) {
        continue;
      }
      if (isTrue(callMF$2(p0, elem))) {
        return optSome(Nat$c$0Instance.instance(i));
      }
    }
    return optEmpty();
  }

  @Override
  public Object mut$lastIndexWhere$1(Object p0) {
    if (this.isEmpty()) {
      return optEmpty();
    }
    for (int i = inner.length - 1; i >= 0; i--) {
      Object e = inner[i];
      if (e == null) {
        continue;
      }
      if (isTrue(callMF$2(p0, e))) {
        return optSome(Nat$c$0Instance.instance(i));
      }
    }
    return optEmpty();
  }

  @Override
  public Object mut$indicesWhere$1(Object p0) {
    if (this.isEmpty()) {
      return Flow$o$1Instance.of();
    }
    return Flow$o$1Instance.of(IntStream.range(0, inner.length)
      .filter(i -> {
        Object e = inner[i];
        return e != null && isTrue(callMF$2(p0, e));
      })
      .mapToObj(Nat$c$0Instance::instance));
  }


  @Override
  public Object mut$set$2(Object p0, Object p1) {
    put(idx(p0, ".set"), p1);
    return this;
  }

  @Override
  public Object mut$clear$0() {
    if (this.isEmpty()) {
      return this;
    }
    Arrays.fill(inner, null);
    this.numHoles = capacity;
    return this;
  }

  @Override
  public Object mut$removeIf$1(Object p0) {
    if (this.isEmpty()) {
      return this;
    }
    for (int i = 0; i < inner.length; i++) {
      Object e = inner[i];
      if (e != null && isTrue(callMF$2(p0, e))) {
        remove(i);
      }
    }
    return this;
  }

  @Override
  public Object mut$remove$1(Object p0) {
    remove(idx(p0, ".remove"));
    return this;
  }

  @Override
  public Object mut$removeAll$1(Object p0) {
    List<?> indices = EList$1k$1Instance.unwrap(p0);
    Set<Integer> seen = new LinkedHashSet<>();
    for (Object o : indices) {
      int index = idx(o, ".removeAll");
      if (!seen.add(index)) {
        throw err("ESparseList.removeAll: Index " + index + " is a duplicated index: " + indices);
      }
    }
    for (Integer index : seen) {
      remove(index);
    }
    return this;
  }

  @Override
  public Object mut$removeFirstWhere$1(Object p0) {
    for (int i = 0; i < inner.length; i++) {
      Object e = inner[i];
      if (e != null && isTrue(callMF$2(p0, e))) {
        remove(i);
        break;
      }
    }
    return this;
  }

  @Override
  public Object mut$removeLastWhere$1(Object p0) {
    for (int i = inner.length - 1; i >= 0; i--) {
      Object e = inner[i];
      if (e != null && isTrue(callMF$2(p0, e))) {
        remove(i);
        break;
      }
    }
    return this;
  }

  @Override
  public Object mut$trimTo$2(Object p0, Object p1) {
    long start = Nat$c$0Instance.unwrap(p0);
    long end = Nat$c$0Instance.unwrap(p1);
    check(
      Long.compareUnsigned(start, end) <= 0 && Long.compareUnsigned(end, capacity) <= 0,
      "ESparseList.trimTo: Invalid range [" + Long.toUnsignedString(start) + ", " + Long.toUnsignedString(end)
        + ") for a ESparseList with capacity " + capacity
    );
    inner = Arrays.copyOfRange(inner, (int) start, (int) end);
    capacity = inner.length;
    int holes = 0;
    for (Object e : inner) {
      if (e == null) {
        holes++;
      }
    }
    numHoles = holes;
    return this;
  }

  @Override
  public Object mut$reverse$0() {
    if (this.isEmpty()) {
      return this;
    }
    reverseArray(this.inner);
    return this;
  }
  static <T> void reverseArray(T[] array) {
    for (int i = 0; i < array.length / 2; i++) {
      swap(array, i, array.length - 1 - i);
    }
  }

  @Override
  public Object mut$mapInPlace$1(Object p0) {
    if (this.isEmpty()) {
      return this;
    }
    for (int i = 0; i < inner.length; i++) {
      if (inner[i] == null) {
        continue;
      }
      inner[i] = callMF$2(p0, inner[i]);
    }
    return this;
  }

  @Override
  public Object mut$swap$2(Object p0, Object p1) {
    swap(this.inner, idx(p0, ".swap"), idx(p1, ".swap"));
    return this;
  }

  @Override
  public Object mut$shallowClone$0() {
    return new SparseArray(this);
  }

  @Override
  public Object read$capacity$0() {
    return Nat$c$0Instance.instance(capacity);
  }

  @Override
  public Object read$numHoles$0() {
    return Nat$c$0Instance.instance(numHoles);
  }

  @Override
  public Object mut$softIncreaseCapacity$1(Object p0) {
    long proposed = Nat$c$0Instance.unwrap(p0);
    if (Long.compareUnsigned(proposed, capacity) <= 0) {
      return this;
    }
    checkCapacity(proposed, ".softIncreaseCapacity");
    int newCapacity = (int) proposed;
    inner = Arrays.copyOf(inner, newCapacity);
    numHoles += newCapacity - capacity;
    capacity = newCapacity;
    return this;
  }

  private Object getOpt(int i) {
    Object e = inner[i];
    return e == null ? optEmpty() : optSome(e);
  }

  @Override
  public Object read$get$1(Object p0) {
    return mut$get$1(p0);
  }

  @Override
  public Object mut$get$1(Object p0) {
    return getOpt(idx(p0, ".get"));
  }

  @Override
  public Object mut$fillHoles$1(Object p0) {
    for (int i = 0; i < inner.length && numHoles > 0; i++) {
      if (inner[i] != null) {
        continue;
      }
      put(i, callMF$2(p0, Nat$c$0Instance.instance(i)));
    }
    return this;
  }

  @Override
  public Object mut$mapOpts$1(Object p0) {
    for (int i = 0; i < inner.length; i++) {
      Object result = callMF$3(p0, Nat$c$0Instance.instance(i), getOpt(i));
      if (optIsSome(result)) {
        put(i, optGet(result));
      } else {
        remove(i);
      }
    }
    return this;
  }

  @Override
  public Object mut$fillFrom$1(Object p0) {
    List<?> xs = EList$1k$1Instance.unwrap(p0);
    int next = 0;
    for (int i = 0; i < inner.length && numHoles > 0 && next < xs.size(); i++) {
      if (inner[i] == null) {
        put(i, xs.get(next++));
      }
    }
    return this;
  }

  @Override
  public Object mut$fillAndExpand$1(Object p0) {
    List<?> xs = EList$1k$1Instance.unwrap(p0);
    int next = 0;
    for (int i = 0; i < inner.length && numHoles > 0 && next < xs.size(); i++) {
      if (inner[i] == null) {
        put(i, xs.get(next++));
      }
    }
    int remaining = xs.size() - next;
    if (remaining > 0) {
      long newCapacity = (long) capacity + remaining;
      checkCapacity(newCapacity, ".fillAndExpand");
      int oldCapacity = capacity;
      inner = Arrays.copyOf(inner, (int) newCapacity);
      capacity = (int) newCapacity;
      numHoles += remaining;
      for (int i = oldCapacity; next < xs.size(); i++) {
        put(i, xs.get(next++));
      }
    }
    return this;
  }

  private Object flowOptsOf(Object[] snapshot) {
    return Flow$o$1Instance.of(Arrays.stream(snapshot).map(e -> e == null ? optEmpty() : optSome(e)));
  }

  private Object flatFlowOf(Object[] snapshot) {
    return Flow$o$1Instance.of(Arrays.stream(snapshot).filter(Objects::nonNull));
  }

  @Override
  public Object mut$seqFlowOpts$0() {
    return flowOptsOf(drain());
  }

  // NOTE: `mode` is not applied yet; wire it in once you know how FlowMode maps onto Flow$o$1Instance.
  @Override
  public Object mut$flowOpts$1(Object p0) {
    return flowOptsOf(drain());
  }

  @Override
  public Object mut$flatSeqFlow$0() {
    return flatFlowOf(drain());
  }

  // NOTE: `mode` is not applied yet (see flowOpts).
  @Override
  public Object mut$flatFlow$1(Object p0) {
    return flatFlowOf(drain());
  }

  @Override
  public Object mut$getList$0() {
    if (numHoles != 0) {
      throw err("ESparseList.getList: Cannot create an EList from an ESparseList that is not full ("
        + numHoles + " of " + capacity + " slots are holes). Fill the holes first.");
    }
    return EList$1k$1Instance.unsafeWrap(new ArrayList<>(Arrays.asList(drain())));
  }
}
