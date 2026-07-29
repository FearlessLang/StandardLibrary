package base;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import base.Util.MapKey;

import static base.Util.*;

public final class Set$1Instance implements Set$1 {
  private final OrderHashBy$2 ordering;
  private final HashSet<MapKey> set;
  private final ArrayList<Object> sortedList;

  static Set$1Instance of(OrderHashBy$2 ordering, List<Object> values) {
    return new Set$1Instance(ordering, new ArrayList<>(values));
  }

  static Set$1Instance fromSet(OrderHashBy$2 ordering, HashSet<MapKey> values) {
    return new Set$1Instance(ordering, values);
  }

  public static Set$1Instance ofMutableList(OrderHashBy$2 ordering, ArrayList<Object> values) {
    return new Set$1Instance(ordering, values);
  }

  Set$1Instance(OrderHashBy$2 ordering, ArrayList<Object> values) {
    this.ordering = ordering;
    values.sort((a, b) -> cmp(ordering,a,b));
    this.sortedList = values;
    this.set = sortedList.stream().map(k -> mapKey(ordering, k))
            .collect(Collectors.toCollection(HashSet::new));
  }
  Set$1Instance(OrderHashBy$2 ordering, ArrayList<Object> sortedList, HashSet<MapKey> set) {
    this.ordering = ordering;
    this.sortedList = sortedList;
    this.set = set;
  }
  Set$1Instance(OrderHashBy$2 ordering, HashSet<MapKey> values) {
    this.ordering = ordering;
    this.sortedList = values.stream().map(Set$1Instance::extractKey)
            .sorted((a, b) -> cmp(ordering, a, b))
            .collect(Collectors.toCollection(ArrayList::new));

    this.set = values;
  }

  static Object extractKey(MapKey mapKey) {
      return mapKey.key;
  }

  private static Set$1Instance toSet(Object p0) {
    return (Set$1Instance) p0;
  }

  public static OrderHashBy$2 ordering(Object ordering) {
    return (OrderHashBy$2) ordering;
  }


  @Override public Object imm$size$0(){ return Nat$0Instance.instance(set.size()); }

  @Override public Object read$imm$0() {return this;}


  @Override public Object imm$orderHash$0() { return ordering.imm$hideKey$0(); }

  @Override public Object imm$contains$1(Object p0) {
    return bool(set.contains(mapKey(ordering, p0)));
  }

  @Override public Object imm$$plus$1(Object p0){
    var key = mapKey(this.ordering, p0);
    if (this.set.contains(key)) {
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
    }
    else {
      // put it after the current element
      list.add(index + 1, t);
    }
  }

  @Override public Object imm$$dash$1(Object p0) {
    // Need to figure out if this is actually more efficient
    // Hashes twice 2*O(1) - but doesn't allocate the memory if the set O(n) if the element doesn't exist
    var setElement = mapKey(ordering, p0);
    if (!set.contains(setElement)) {
      return this;
    }
    var s = new HashSet<>(set);
    if (!s.remove(setElement)) {
        throw new IllegalArgumentException("Failed to remove from set");
    }

    return new Set$1Instance(ordering, s);
  }


  @Override public Object imm$union$2(Object p0, Object p1){
    var newOrdering = ordering(p0);
    Set$1Instance other = toSet(p1);

    // See if we can avoid rehashing some of the elements
    if (newOrdering.equals(ordering)) {
      return fastUnion(this, other);
    }
    if (newOrdering.equals(other.ordering)) {
      return fastUnion(other, this);
    }

    var set = Stream.concat(
            this.sortedList.stream(),
            other.sortedList.stream()
    ).map(e -> mapKey(newOrdering, e))
        .collect(Collectors.toCollection(HashSet::new));

    return new Set$1Instance(newOrdering, set);
  }

  private static Set$1Instance fastUnion(Set$1Instance setWithHasher, Set$1Instance other) {
    // This cannot allocate a set that is too small - but it can make one that is too big...
    ArrayList<Object> newSet = new ArrayList<>(setWithHasher.set.size() + other.set.size());
    newSet.addAll(setWithHasher.set);
    for (Object elem : other.sortedList) {
      MapKey newKey = new MapKey(setWithHasher.ordering, elem);
      newSet.add(newKey);
    }
    return new Set$1Instance(setWithHasher.ordering, newSet);
  }

  @Override public Object imm$intersection$2(Object p0, Object p1){
    var newOrdering = ordering(p0);
    Set$1Instance s1 = this;
    Set$1Instance s2 = toSet(p1);
    if (s1.set.isEmpty() || s2.set.isEmpty()) {
      return Sets$0.instance.imm$$hash$1(newOrdering);
    }

    if (newOrdering.equals(ordering)) {
      return fastIntersection(s1, s2);
    }
    if (newOrdering.equals(s2.ordering)) {
      return fastIntersection(s2, s1);
    }

    Set$1Instance smaller;
    Set$1Instance larger;
    if (s1.set.size() < s2.set.size()) {
      smaller = s1;
      larger = s2;
    } else {
      smaller = s2;
      larger = s1;
    }

    var result = rehash(smaller.set, newOrdering);
    result.retainAll(rehash(larger.set, newOrdering));
    return new Set$1Instance(newOrdering, result);
  }

  private static Set$1Instance fastIntersection(Set$1Instance setWithHasher, Set$1Instance other) {
    return fromSet(
        setWithHasher.ordering,
        other.set.stream()
            .map(Set$1Instance::extractKey)
            .map(k -> Util.mapKey(setWithHasher.ordering, k))
            .filter(setWithHasher.set::contains)
            .collect(Collectors.toCollection(HashSet::new))
    );
  }

  private static HashSet<MapKey> rehash(HashSet<MapKey> s, OrderHashBy$2 ordering) {
    return s.stream()
          .map(Set$1Instance::extractKey)
          .map(k -> mapKey(ordering, k))
          .collect(Collectors.toCollection(HashSet::new));
  }

  private static HashSet<MapKey> rehashSet(Set$1Instance set, OrderHashBy$2 ordering) {
    if (set.ordering.equals(ordering)) {
      return set.set;
    }

    return rehash(set.set, ordering);
  }

  @Override public Object imm$containsAll$2(Object p0, Object p1) {
      Set$1Instance set = toSet(p1);
      var newOrdering = ordering(p0);
      HashSet<MapKey> rehashedThis = null;
      HashSet<MapKey> rehashedOther = null;

      if (newOrdering.equals(this.ordering)) {
        rehashedThis = this.set;
        rehashedOther = rehash(set.set, newOrdering);

      } else if (newOrdering.equals(set.ordering)) {
        rehashedThis = rehash(this.set, newOrdering);
        rehashedOther = set.set;
      } else {
        rehashedThis = rehash(this.set, newOrdering);
        rehashedOther = rehash(set.set, newOrdering);
      }

      return bool(rehashedThis.containsAll(rehashedOther));
  }

  @Override public Object imm$distinctBy$1(Object p0) {
      var newOrdering = ordering(p0);
      if (this.ordering.equals(newOrdering)) { return this; }
      return new Set$1Instance(newOrdering, rehash(set, newOrdering));
  }


  @Override public Object imm$list$0() {
    return List$1Instance.wrap(sortedList);
  }
}

