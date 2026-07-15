package base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import base.Util.MapKey;

import static base.Util.*;


public interface Sets$0 extends Sealed$0 {
  ArrayList<Object> emptyList = new ArrayList<>(0);
  default Object imm$$hash$1(Object p0){
    var ordering = Set$2Instance.ordering(p0);
    return new Set$2Instance(ordering, emptyList);
  }
  default Object imm$$hash$2(Object p0, Object p1){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(1);
    s.add(p1);
    return new Set$2Instance(ordering, s);
  }
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(2);
    s.add(p1);
    s.add(p2);
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$4(Object p0, Object p1, Object p2, Object p3){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(3);
    s.add(p1); s.add(p2); s.add(p3);
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$5(Object p0, Object p1, Object p2, Object p3, Object p4){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(4);
    s.add(p1); s.add(p2);
    s.add(p3); s.add(p4);
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$6(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(5);
    s.add(p1); s.add(p2); s.add(p3);
    s.add(p4); s.add(p5);
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$7(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(6);
    s.add(p1); s.add(p2); s.add(p3);
    s.add(p4); s.add(p5); s.add(p6);
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$8(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(7);
    s.add(p1); s.add(p2); s.add(p3);
    s.add(p4); s.add(p5); s.add(p6);
    s.add(p7);
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$9(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(8);
    s.add(p1); s.add(p2); s.add(p3);
    s.add(p4); s.add(p5); s.add(p6);
    s.add(p7); s.add(p8);
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$10(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(9);
    s.add(p1); s.add(p2); s.add(p3);
    s.add(p4); s.add(p5); s.add(p6);
    s.add(p7); s.add(p8); s.add(p9);
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$11(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(10);
    s.add(p1); s.add(p2); s.add(p3);
    s.add(p4); s.add(p5); s.add(p6);
    s.add(p7); s.add(p8); s.add(p9);
    s.add(p10);
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$12(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(11);
    s.add(p1); s.add(p2); s.add(p3);
    s.add(p4); s.add(p5); s.add(p6);
    s.add(p7); s.add(p8); s.add(p9);
    s.add(p10); s.add(p11);
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$13(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(12);
    s.add(p1); s.add(p2); s.add(p3);
    s.add(p4); s.add(p5); s.add(p6);
    s.add(p7); s.add(p8); s.add(p9);
    s.add(p10); s.add(p11); s.add(p12);
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$14(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(13);
    s.add(p1); s.add(p2); s.add(p3);
    s.add(p4); s.add(p5); s.add(p6);
    s.add(p7); s.add(p8); s.add(p9);
    s.add(p10); s.add(p11); s.add(p12);
    s.add(p13);
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$15(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(14);
    s.add(p1); s.add(p2); s.add(p3);
    s.add(p4); s.add(p5); s.add(p6);
    s.add(p7); s.add(p8); s.add(p9);
    s.add(p10); s.add(p11); s.add(p12);
    s.add(p13); s.add(p14);
    return new Set$2Instance(ordering, s);
  }
  default Object imm$$hash$16(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(15);
    s.add(p1); s.add(p2); s.add(p3);
    s.add(p4); s.add(p5); s.add(p6);
    s.add(p7); s.add(p8); s.add(p9);
    s.add(p10); s.add(p11); s.add(p12);
    s.add(p13); s.add(p14); s.add(p15);
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$17(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16){
    var ordering = Set$2Instance.ordering(p0);
    var s = new ArrayList<>(15);
    s.add(p1); s.add(p2); s.add(p3);
    s.add(p4); s.add(p5); s.add(p6);
    s.add(p7); s.add(p8); s.add(p9);
    s.add(p10); s.add(p11); s.add(p12);
    s.add(p13); s.add(p14); s.add(p15);
    s.add(p16);
    return new Set$2Instance(ordering, s);
  }

  Sets$0 instance= new Sets$0(){};
}

final class Set$2Instance implements Set$2 {
  private final OrderHashBy$2 ordering;
  private final HashSet<MapKey> set;
  private final ArrayList<Object> sortedList;
  static Set$2Instance of(OrderHashBy$2 ordering, List<Object> values) {
    return new Set$2Instance(ordering, new ArrayList<>(values));
  }
  static Set$2Instance fromSet(OrderHashBy$2 ordering, HashSet<MapKey> values) {
    return new Set$2Instance(ordering, values);
  }


  Set$2Instance(OrderHashBy$2 ordering, ArrayList<Object> values) {
    this.ordering = ordering;
    values.sort((a, b) -> cmp(ordering,a,b));
    this.sortedList = values;
    this.set = sortedList.stream().map(k -> mapKey(ordering, k))
            .collect(Collectors.toCollection(HashSet::new));
  }

  Set$2Instance(OrderHashBy$2 ordering, HashSet<MapKey> values) {
    this.ordering = ordering;
    this.sortedList = values.stream().map(Set$2Instance::extractKey)
      .collect(Collectors.toCollection(ArrayList::new));

    this.sortedList.sort((a, b) -> cmp(ordering,a,b));
    this.set = values;
  }

  static Object extractKey(MapKey mapKey) {
      return mapKey.key;
  }

  private static Set$2Instance setInstance(Object p0) {
    if (p0 instanceof Set$2Instance s1) {
      return s1;
    }

    throw new IllegalArgumentException("Expected Set$2Instance, got: " + p0.getClass());
  }
  static OrderHashBy$2 ordering(Object ordering) {
    return (OrderHashBy$2) ordering;
  }

  @Override public Object read$imm$0() {return this;}

  @Override public Object imm$orderHash$0() { return ordering; }
  @Override public Object read$size$0(){ return intToNat(set.size()); }
  @Override public Object read$isEmpty$0(){ return bool(set.isEmpty()); }

  @Override public Object imm$contains$1(Object p0) {
    return bool(set.contains(mapKey(ordering, p0)));
  }

  @Override public Object imm$$plus$1(Object p0){
    // TODO: make this o(1)
    var list = new ArrayList<>(set.size() + 1);
    list.addAll(sortedList);
    list.add(p0);
    return new Set$2Instance(ordering, list);
  }

  @Override public Object imm$$dash$1(Object p0) {
    // Need to figure out if this is actually more efficient
    // Hashes twice 2*O(1) - but doesn't allocate the memory if the set O(n) if the element doesn't exist
    var setElement = mapKey(ordering, p0);
    if (!set.contains(setElement)) {
      return this;
    }
    var s = new HashSet<MapKey>(set);
    if (!s.remove(setElement)) {
        throw new IllegalArgumentException("Failed to remove from set");
    }

    return new Set$2Instance(ordering, s);
  }


  @Override public Object imm$union$2(Object p0, Object p1){
    var newOrdering = ordering(p0);
    Set$2Instance other = setInstance(p1);

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

    return new Set$2Instance(newOrdering, set);
  }

  private static Set$2Instance fastUnion(Set$2Instance setWithHasher, Set$2Instance other) {
    // This cannot allocate a set that is too small - but it can make one that is too big...
    ArrayList<Object> newSet = new ArrayList<>(setWithHasher.set.size() + other.set.size());
    newSet.addAll(setWithHasher.set);
    for (Object elem : other.sortedList) {
      MapKey newKey = new MapKey(setWithHasher.ordering, elem);
      newSet.add(newKey);
    }
    return new Set$2Instance(setWithHasher.ordering, newSet);
  }

  @Override public Object imm$intersection$2(Object p0, Object p1){
    var newOrdering = ordering(p0);
    Set$2Instance s1 = this;
    Set$2Instance s2 = setInstance(p1);
    if (s1.set.isEmpty() || s2.set.isEmpty()) {
      return Sets$0.instance.imm$$hash$1(newOrdering);
    }

    if (newOrdering.equals(ordering)) {
      return fastIntersection(s1, s2);
    }
    if (newOrdering.equals(s2.ordering)) {
      return fastIntersection(s2, s1);
    }

    Set$2Instance smaller;
    Set$2Instance larger;
    if (s1.set.size() < s2.set.size()) {
      smaller = s1;
      larger = s2;
    } else {
      smaller = s2;
      larger = s1;
    }

    var result = rehash(smaller.set, newOrdering);
    result.retainAll(rehash(larger.set, newOrdering));
    return new Set$2Instance(newOrdering, result);
  }

  private static Set$2Instance fastIntersection(Set$2Instance setWithHasher, Set$2Instance other) {
    return fromSet(
        setWithHasher.ordering,
        other.set.stream()
            .map(Set$2Instance::extractKey)
            .map(k -> Util.mapKey(setWithHasher.ordering, k))
            .filter(setWithHasher.set::contains)
            .collect(Collectors.toCollection(HashSet::new))
    );
  }

  @Override public Object imm$$dash_dash$2(Object p0, Object p1) {
    if (this.set.isEmpty()) {return Sets$0.instance.imm$$hash$1(p1);}
    var newOrdering = ordering(p0);
    Set$2Instance other = setInstance(p1);


    if (this.ordering.equals(newOrdering)) {
        // Don't have to rehash the elements from this
        return new Set$2Instance(
          newOrdering,
          (HashSet<MapKey>) this.set.stream()
            .filter(k -> !other.set.contains(mapKey(
                        other.ordering,
                        extractKey(k)
            ))).collect(Collectors.toCollection(HashSet::new))
        );
    }
    if (other.ordering.equals(newOrdering)) {
       return fromSet(
          newOrdering,
          this.set.stream()
             // rehash the current elements
            .map(k -> mapKey(newOrdering, extractKey(k)))
            // Don't need to rehash to match other.
            .filter(k -> !other.set.contains(k))
            .collect(Collectors.toCollection(HashSet::new))
        );
    }

    // should investigate whether keep in this if not in other, or
    // remove from this if in other is better
    return fromSet(
        newOrdering,
          this.set.stream()
            .filter(k -> !other.set.contains(
                    mapKey(other.ordering, extractKey(k))
            ))
            .map(k -> mapKey(newOrdering, extractKey(k)))
            .collect(Collectors.toCollection(HashSet::new))
        );
  }

  private static HashSet<MapKey> rehash(HashSet<MapKey> s, OrderHashBy$2 ordering) {
    return s.stream()
          .map(Set$2Instance::extractKey)
          .map(k -> mapKey(ordering, k))
          .collect(Collectors.toCollection(HashSet::new));
  }

  @Override public Object imm$containsAll$2(Object p0, Object p1) {
      Set$2Instance set = setInstance(p1);
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
      return new Set$2Instance(newOrdering, rehash(set, newOrdering));
  }


  @Override public Object imm$list$0() {
    return List$1Instance.wrap(sortedList);
  }
}


