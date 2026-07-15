package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static base.Util.*;


// TODO: decide if we want a sorted list of pairs, or a TreeMap as well.

class ESparseListMap$1Instance implements ESparseList$1 {
  Map<Integer, Object> map;
  int capacity;

  public int numHoles() {
    return capacity - map.size();
  }

  public ESparseListMap$1Instance(int capacity) {
    this(capacity, HashMap::new);
  }

  public ESparseListMap$1Instance(int capacity, Supplier<Map<Integer, Object>> mapSupplier) {
    this.map = mapSupplier.get();
    this.capacity = capacity;
  }

  @Override public Object read$capacity$0() {
    return Nat$0Instance.instance(capacity);
  }

  @Override public Object read$numHoles$0() {
    return Nat$0Instance.instance(numHoles());
  }

  @Override public Object mut$increaseCapacity$1(Object p0) {
    long newCapacity = natToLong(p0);
    check(newCapacity <= Integer.MAX_VALUE, "Internal ESparseList capacity cannot be larger than Integer.MAX_VALUE");
    if (newCapacity < this.capacity) {
      throw err("New capacity " + newCapacity + " must be larger than current capacity " + capacity);
    }
    this.capacity = (int) newCapacity;
    return this;
  }

  @Override public Object mut$increaseCapacityDefensive$1(Object p0) {
    long newCapacity = natToLong(p0);
    check(newCapacity <= Integer.MAX_VALUE, "Internal ESparseList capacity cannot be larger than Integer.MAX_VALUE");
    if (newCapacity <= this.capacity) { return this; }
    this.capacity = (int) newCapacity;
    return this;
  }

  @Override public Object mut$set$2(Object p0, Object p1) {
    long index = natToLong(p0);
    check(0 <= index && index < capacity, "ESparseList index out of range");
    int idx = (int) index;
    map.put(idx, p1);
    return this;
  }

  @Override public Object mut$remove$1(Object p0) {
    long index = natToLong(p0);
    check(0 <= index && index < capacity, "ESparseList index out of range");
    int idx = (int) index;
    map.remove(idx);
    return this;
  }

  @Override public Object mut$clear$0() {
    map.clear();
    return this;
  }

  @Override public Object mut$fillHoles$1(Object p0) {
    if (this.numHoles() == 0) { return this; }
    for (int i = 0; i < capacity; i++) {
      map.computeIfAbsent(i, idx -> callMF$2(p0, Nat$0Instance.instance(idx)));
    }
    return this;
  }

  @Override public Object mut$fillFrom$1(Object p0) {
    if (numHoles() == 0) { return this; }
    ArrayList<Object> elist = ((EList$1Instance) p0).xs;
    int currentIndex = 0;
    for (int i = 0; i<=capacity;i++) {
      if (this.map.containsKey(i)) { continue; }
      this.map.put(i, elist.get(i));
      currentIndex+=1;
    }

    return this;
  }

  @Override public Object mut$fillAndExpand$1(Object p0) {
    ArrayList<Object> elist = ((EList$1Instance) p0).xs;
    long additionalSize = this.numHoles() - elist.size();
    this.mut$increaseCapacity$1(Nat$0Instance.instance(capacity + additionalSize));
    this.mut$fillFrom$1(p0);
    return this;
  }

  private void fillAvailableHoles(Stream<Object> s) {
    int[] idx = {0};
    s.sequential().takeWhile(_ -> map.size() < this.capacity).forEach(e -> {
      while (idx[0] < capacity) {
        int i = idx[0];
        if (!map.containsKey(i)) {
          map.put(i, e);
          break;
        }
        idx[0]+=1;
      }
    });
  }

  @Override public Object mut$removeIf$1(Object p0) {
    map.entrySet().removeIf(
        entry -> isTrue(callMF$2(p0, entry.getValue()))
    );
    return this;
  }

  @Override public Object mut$retainIf$1(Object p0) {
    map.entrySet().removeIf(
        entry -> isFalse(callMF$2(p0, entry.getValue()))
    );
    return this;
  }

  @Override public Object mut$removeFirstWhere$1(Object p0) {
    if (this.map.isEmpty()) { return this; }

    for (int i = 0; i < capacity; i++) {
      Object v = map.get(i);
      if (v != null && isTrue(callMF$2(p0, v))) {
        map.remove(i);
        return this;
      }
    }
    return this;
  }

  @Override public Object mut$removeLastWhere$1(Object p0) {
    if (this.map.isEmpty()) { return this; }
    for (int i = capacity - 1; i >= 0; i--) {
      Object v = map.get(i);
      if (v != null && isTrue(callMF$2(p0, v))) {
        map.remove(i);
        return this;
      }
    }
    return this;
  }

  @Override public Object mut$mapInPlace$1(Object p0) {
    if (this.map.isEmpty()) { return this; }
    map.replaceAll((_, v) -> callMF$2(p0, v));
    return this;
  }

  @Override public Object mut$swap$2(Object p0, Object p1) {
    long iLong = natToLong(p0);
    long jLong = natToLong(p1);
    check(0 <= iLong && iLong < capacity, "ESparseList swap index i out of range");
    check(0 <= jLong && jLong < capacity, "ESparseList swap index j out of range");
    int j = (int) jLong;
    int i = (int) iLong;

    Object vi = map.remove(i);
    Object vj = map.remove(j);

    if (vj != null) { map.put(i, vj); }
    if (vi != null) { map.put(j, vi); }
    return this;
  }

  @Override public Object mut$reverse$0() {
    if (this.map.isEmpty()) { return this; }
    HashMap<Integer, Object> reversed = new HashMap<>(map.size());
    map.forEach((k, v) -> reversed.put(capacity - 1 - k, v));
    this.map = reversed;
    return this;
  }

  @Override public Object mut$trimTo$2(Object p0, Object p1) {
    long start = natToLong(p0);
    long end   = natToLong(p1);
    check(0 <= start && start <= end && end <= capacity,
        "ESparseList trimTo range out of bounds");
    int s = (int) start;
    int e = (int) end;
    int newLen = e - s;

    Map<Integer, Object> newMap = new HashMap<>(newLen);
    this.map.entrySet().stream()
        .filter(entry -> entry.getKey() < s || entry.getKey() >= e)
        .forEach(entry -> newMap.put(entry.getKey() - s, entry.getValue()));
    this.map = newMap;
    this.capacity = newLen;
    return this;
  }

  @Override public Object mut$all$1(Object p0) {
    if (this.map.isEmpty()) { return bool(true); }
    for (Object v : map.values()) { if (isFalse(v)) { return bool(false); } }
    return bool(true);
  }

  @Override public Object mut$any$1(Object p0) {
    if (this.map.isEmpty()) { return bool(false); }
    for (Object v : map.values()) { if (isTrue(v)) { return bool(true); } }
    return bool(false);
  }

  @Override public Object mut$none$1(Object p0) {
    if (this.map.isEmpty()) { return bool(true); }
    for (Object v : map.values()) { if (isTrue(v)) { return bool(false); } }
    return bool(true);
  }

  @Override public Object mut$firstIndexWhere$1(Object p0) {
    if (this.map.isEmpty()) { return optEmpty(); }
    for (int i = 0; i < capacity; i++) {
      Object v = map.get(i);
      if (v != null && isTrue(callMF$2(p0, v))) {
        return optSome(Nat$0Instance.instance(i));
      }
    }
    return optEmpty();
  }

  @Override public Object mut$lastIndexWhere$1(Object p0) {
    if (this.map.isEmpty()) { return optEmpty(); }
    for (int i = capacity - 1; i >= 0; i--) {
      Object v = map.get(i);
      if (v != null && isTrue(callMF$2(p0, v))) {
        return optSome(Nat$0Instance.instance(i));
      }
    }
    return optEmpty();
  }

  @Override public Object mut$indicesWhere$1(Object p0) {
    if (this.map.isEmpty()) { return Flows$0.of(Stream.of()); }
    return Flows$0.of(
        map.entrySet().stream()
            .filter(e -> isTrue(callMF$2(p0, e.getValue())))
            .sorted(Map.Entry.comparingByKey())
            .map(e -> Nat$0Instance.instance(e.getKey()))
    );
  }

  @Override public Object mut$seqFlowOpts$0() {
    Map<Integer, Object> drained = drain();
    return Flows$0.of(
        IntStream.range(0, capacity)
            .mapToObj(i -> optNullable(drained.get(i))),
        capacity
    );
  }

  // In future, make this parallel
  @Override public Object mut$flowOpts$1(Object p0) { return mut$seqFlowOpts$0(); }

  @Override public Object mut$seqFlowDefensive$0() {
    Map<Integer, Object> drained = drain();
    Stream<Object> s = drained.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .map(Map.Entry::getValue);
    return Flows$0.of(s, drained.size());
  }

  // In future, make this parallel
  @Override public Object mut$flowDefensive$1(Object p0) { return mut$seqFlowDefensive$0(); }

  @Override public Object mut$listExact$0() {
    if (this.capacity != this.map.size()) {
      return err("Cannot create a list from an ESparseList that is not full."
          + "\nUse a \"hole-safe\" flow method, or fill the holes first.");
    }
    Map<Integer, Object> drained = drain();

    return List$1Instance.wrap(drained.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .map(Map.Entry::getValue).toList());
  }

  @Override public Object mut$shallow_clone$0() {
    ESparseListMap$1Instance clone = new ESparseListMap$1Instance(capacity);
    clone.map = new HashMap<>(this.map);
    return clone;
  }
  @Override public Object mut$mapOpts$1(Object p0) {
    IntStream.range(0, this.capacity)
      .forEach(i -> {
        Object optValue = optToNull((Opt$1) callMF$2(p0, map.get(i)));
        if (optValue == null) {
          map.remove(i);
        }
        map.put(i, optValue);
      });

    return this;
  }
  Map<Integer, Object> drain() {
    Map<Integer, Object> result = this.map;
    this.map = new HashMap<>();
    return result;
  }
}