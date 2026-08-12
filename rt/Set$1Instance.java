package base;

import base.Util.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static base.Util.*;

public final class Set$1Instance implements Set$1 {
  private final OrderHashBy$2 ordering;
  private final Map<MapKey, Object> set;
  private final List<Object> sortedList;

  static Set$1Instance of(OrderHashBy$2 ordering, List<Object> values) { return new Set$1Instance(ordering, new ArrayList<>(values)); }
  static Set$1Instance ofMutableList(OrderHashBy$2 ordering, ArrayList<Object> values) { return new Set$1Instance(ordering, values); }

  Set$1Instance(OrderHashBy$2 ordering, ArrayList<Object> values) {
    this.ordering = ordering;
    values.sort((a, b) -> cmp(ordering, a, b));
    this.sortedList = values;
    this.set = sortedList.stream().collect(Collectors.toMap(
      o -> mapKey(ordering, o),
      o -> o
    ));
  }
  Set$1Instance(OrderHashBy$2 ordering, ArrayList<Object> sortedList, Map<MapKey, Object> set) {
    this.ordering = ordering;
    this.sortedList = sortedList;
    this.set = set;
  }
  Set$1Instance(OrderHashBy$2 ordering, Map<MapKey, Object> set) {
    this.ordering = ordering;
    this.sortedList = set.keySet().stream().map(Set$1Instance::extractKey)
      .sorted((a, b) -> cmp(ordering, a, b))
      .collect(Collectors.toCollection(ArrayList::new));

    this.set = set;
  }

  static Object extractKey(MapKey mapKey) { return mapKey.key; }
  static Set$1Instance toSet(Object p0) { return (Set$1Instance) p0; }
  static OrderHashBy$2 ordering(Object ordering) { return (OrderHashBy$2) ordering; }

  @Override
  public Object imm$size$0() { return Nat$0Instance.instance(set.size()); }
  @Override
  public Object read$imm$0() { return this; }
  @Override
  public Object imm$orderHash$0() { return ordering.imm$hideKey$0(); }
  @Override
  public Object imm$contains$1(Object p0) { return bool(set.containsKey(mapKey(ordering, p0))); }
  @Override
  public Object imm$get$1(Object p0) {
    Object key = set.get(mapKey(ordering, p0));
    if (key == null) { throw err(
      "Set.get: Inputted value "+toStringBy(ordering, p0)+" is not contained in this set"
    );}
    return key;
  }
  @Override
  public Object imm$opt$1(Object p0) { return optNullable(set.get(mapKey(ordering, p0))); }
  @Override
  public Object imm$$plus$1(Object p0) {
    var key = mapKey(this.ordering, p0);
    if (this.set.containsKey(key)) {
      return this;
    }
    var list = new ArrayList<>(set.size() + 1);
    list.addAll(sortedList);
    insertInSortedPosition(list, p0, Util.toComparator(ordering));
    return new Set$1Instance(ordering, list, this.set);
  }
  static <T> void insertInSortedPosition(ArrayList<T> list, T t, Comparator<T> comparator) {
    /*
    the index of the search key, if it is contained in the list; otherwise:
    (-(insertion point) - 1). The insertion point is defined as the point at which
    the key would be inserted into the list: the index of the first element greater
    than the key, or list.size()
    i =
     */
    int index = Collections.binarySearch(list, t, comparator);
    if (index < 0) {
      // not in the original index.
      //i = -(insertion point) - 1
      // => insertion point = -(i + 1)
      list.add(-(index + 1), t);
    } else {
      // put it after the current element
      list.add(index + 1, t);
    }
  }
  @Override
  public Object imm$$dash$1(Object p0) {
    // Need to figure out if this is actually more efficient
    // Hashes twice 2*O(1) - but doesn't allocate the memory if the set O(n) if the element doesn't exist
    var setElement = mapKey(ordering, p0);
    if (!set.containsKey(setElement)) {
      return this;
    }
    var s = new HashMap<>(set);
    s.remove(setElement);
    return new Set$1Instance(ordering, s);
  }
  @Override
  public Object imm$union$2(Object p0, Object p1) {
    var newOrdering = ordering(p0);
    Set$1Instance other = toSet(p1);
    // See if we can avoid rehashing some of the elements
    if (newOrdering.equals(this.ordering) && newOrdering.equals(other.ordering)) {
      Map<MapKey, Object> map = new HashMap<>(other.set);
      map.putAll(set);
      return new Set$1Instance(ordering, map);
    }
    if (newOrdering.equals(ordering)) {
      return new Set$1Instance(ordering, Stream.concat(
        this.set.keySet().stream(), // don't have to rehash this one
        other.sortedList.stream()
          .filter(k -> !this.set.containsKey(mapKey(newOrdering, k)))
      ).collect(Collectors.toMap(
        o -> mapKey(newOrdering, o),
        o -> o,
        (v1, _) -> v1 // keep old value
      )));
    }
    if (newOrdering.equals(other.ordering)) {
      Map<MapKey, Object> map = new HashMap<>(other.set);
      map.putAll(rehash(set, newOrdering));
      return new Set$1Instance(ordering, map);
    }
    var set = Stream.concat(
      this.sortedList.stream(),
      other.sortedList.stream()
    ).collect(Collectors.toMap(
      o -> mapKey(newOrdering, o),
      o -> o,
      (v1, _) -> v1 // keep old value
    ));
    return new Set$1Instance(newOrdering, set);
  }
  @Override
  public Object imm$intersection$2(Object p0, Object p1) {
    var newOrdering = ordering(p0);
    Set$1Instance other = toSet(p1);
    if (this.set.isEmpty() || other.set.isEmpty()) {
      return Sets$0.instance.imm$$hash$1(newOrdering);
    }
    Map<MapKey, Object> thisMap = rehashAndCloneSet(this, newOrdering);
    Map<MapKey, Object> otherMap = rehashSet(other, newOrdering);
    thisMap.keySet().removeIf(k -> !otherMap.containsKey(k));
    return new Set$1Instance(newOrdering, thisMap);
  }
  private static Map<MapKey, Object> rehash(Map<MapKey, Object> s, OrderHashBy$2 ordering) {
    return s.keySet().stream()
      .map(Set$1Instance::extractKey)
      .collect(Collectors.toMap(
        k -> mapKey(ordering, k),
        k -> mapKey(ordering, k),
        (v1, _) -> v1
      ));
  }
  private static Map<MapKey, Object> rehashSet(Set$1Instance set, OrderHashBy$2 ordering) {
    if (set.ordering.equals(ordering)) {return set.set;}
    return rehash(set.set, ordering);
  }
  private static Map<MapKey, Object> rehashAndCloneSet(Set$1Instance set, OrderHashBy$2 ordering) {
    if (set.ordering.equals(ordering)) {return new HashMap<>(set.set);}
    return rehash(set.set, ordering);
  }
  @Override
  public Object imm$containsAll$2(Object p0, Object p1) {
    Set$1Instance other = toSet(p1);
    var newOrdering = ordering(p0);
    Map<MapKey, Object> rehashedThis = rehashSet(this, newOrdering);
    Map<MapKey, Object> rehashedOther = rehashSet(other, newOrdering);
    return bool(rehashedThis.keySet().containsAll(rehashedOther.keySet()));
  }
  @Override
  public Object imm$distinctBy$1(Object p0) {
    var newOrdering = ordering(p0);
    if (this.ordering.equals(newOrdering)) {return this;}
    return new Set$1Instance(newOrdering, rehash(set, newOrdering));
  }
  @Override
  public Object imm$list$0() {
    return List$1Instance.wrap(sortedList);
  }
}
