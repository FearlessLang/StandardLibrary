package base;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import base.Util.MapKey;

import static base.Util.mapKey;
import static base.Util.intToNat;
import static base.Util.bool;


public interface Sets$0 extends Sealed$0 {
  LinkedHashSet<Object> emptySet = new LinkedHashSet<>(0);
  default Object imm$$hash$1(Object p0){
    var ordering = Set$2Instance.ordering(p0);
    return new Set$2Instance(ordering, emptySet);
  }
  default Object imm$$hash$2(Object p0, Object p1){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(1);
    s.add(mapKey(ordering, p1));
    return new Set$2Instance(ordering, s);
  }
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(2);
    s.add(mapKey(ordering, p1));
    s.add(mapKey(ordering, p2));
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$4(Object p0, Object p1, Object p2, Object p3){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(3);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2)); s.add(mapKey(ordering, p3));
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$5(Object p0, Object p1, Object p2, Object p3, Object p4){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(4);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2));
    s.add(mapKey(ordering, p3)); s.add(mapKey(ordering, p4));
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$6(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(5);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2)); s.add(mapKey(ordering, p3));
    s.add(mapKey(ordering, p4)); s.add(mapKey(ordering, p5));
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$7(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(6);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2)); s.add(mapKey(ordering, p3));
    s.add(mapKey(ordering, p4)); s.add(mapKey(ordering, p5)); s.add(mapKey(ordering, p6));
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$8(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(7);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2)); s.add(mapKey(ordering, p3));
    s.add(mapKey(ordering, p4)); s.add(mapKey(ordering, p5)); s.add(mapKey(ordering, p6));
    s.add(mapKey(ordering, p7));
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$9(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(8);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2)); s.add(mapKey(ordering, p3));
    s.add(mapKey(ordering, p4)); s.add(mapKey(ordering, p5)); s.add(mapKey(ordering, p6));
    s.add(mapKey(ordering, p7)); s.add(mapKey(ordering, p8));
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$10(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(9);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2)); s.add(mapKey(ordering, p3));
    s.add(mapKey(ordering, p4)); s.add(mapKey(ordering, p5)); s.add(mapKey(ordering, p6));
    s.add(mapKey(ordering, p7)); s.add(mapKey(ordering, p8)); s.add(mapKey(ordering, p9));
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$11(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(10);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2)); s.add(mapKey(ordering, p3));
    s.add(mapKey(ordering, p4)); s.add(mapKey(ordering, p5)); s.add(mapKey(ordering, p6));
    s.add(mapKey(ordering, p7)); s.add(mapKey(ordering, p8)); s.add(mapKey(ordering, p9));
    s.add(mapKey(ordering, p10));
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$12(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(11);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2)); s.add(mapKey(ordering, p3));
    s.add(mapKey(ordering, p4)); s.add(mapKey(ordering, p5)); s.add(mapKey(ordering, p6));
    s.add(mapKey(ordering, p7)); s.add(mapKey(ordering, p8)); s.add(mapKey(ordering, p9));
    s.add(mapKey(ordering, p10)); s.add(mapKey(ordering, p11));
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$13(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(12);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2)); s.add(mapKey(ordering, p3));
    s.add(mapKey(ordering, p4)); s.add(mapKey(ordering, p5)); s.add(mapKey(ordering, p6));
    s.add(mapKey(ordering, p7)); s.add(mapKey(ordering, p8)); s.add(mapKey(ordering, p9));
    s.add(mapKey(ordering, p10)); s.add(mapKey(ordering, p11)); s.add(mapKey(ordering, p12));
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$14(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(13);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2)); s.add(mapKey(ordering, p3));
    s.add(mapKey(ordering, p4)); s.add(mapKey(ordering, p5)); s.add(mapKey(ordering, p6));
    s.add(mapKey(ordering, p7)); s.add(mapKey(ordering, p8)); s.add(mapKey(ordering, p9));
    s.add(mapKey(ordering, p10)); s.add(mapKey(ordering, p11)); s.add(mapKey(ordering, p12));
    s.add(mapKey(ordering, p13));
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$15(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(14);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2)); s.add(mapKey(ordering, p3));
    s.add(mapKey(ordering, p4)); s.add(mapKey(ordering, p5)); s.add(mapKey(ordering, p6));
    s.add(mapKey(ordering, p7)); s.add(mapKey(ordering, p8)); s.add(mapKey(ordering, p9));
    s.add(mapKey(ordering, p10)); s.add(mapKey(ordering, p11)); s.add(mapKey(ordering, p12));
    s.add(mapKey(ordering, p13)); s.add(mapKey(ordering, p14));
    return new Set$2Instance(ordering, s);
  }
  default Object imm$$hash$16(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(15);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2)); s.add(mapKey(ordering, p3));
    s.add(mapKey(ordering, p4)); s.add(mapKey(ordering, p5)); s.add(mapKey(ordering, p6));
    s.add(mapKey(ordering, p7)); s.add(mapKey(ordering, p8)); s.add(mapKey(ordering, p9));
    s.add(mapKey(ordering, p10)); s.add(mapKey(ordering, p11)); s.add(mapKey(ordering, p12));
    s.add(mapKey(ordering, p13)); s.add(mapKey(ordering, p14)); s.add(mapKey(ordering, p15));
    return new Set$2Instance(ordering, s);
  }

  default Object imm$$hash$17(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16){
    var ordering = Set$2Instance.ordering(p0);
    var s = new LinkedHashSet<>(15);
    s.add(mapKey(ordering, p1)); s.add(mapKey(ordering, p2)); s.add(mapKey(ordering, p3));
    s.add(mapKey(ordering, p4)); s.add(mapKey(ordering, p5)); s.add(mapKey(ordering, p6));
    s.add(mapKey(ordering, p7)); s.add(mapKey(ordering, p8)); s.add(mapKey(ordering, p9));
    s.add(mapKey(ordering, p10)); s.add(mapKey(ordering, p11)); s.add(mapKey(ordering, p12));
    s.add(mapKey(ordering, p13)); s.add(mapKey(ordering, p14)); s.add(mapKey(ordering, p15));
    s.add(mapKey(ordering, p16));
    return new Set$2Instance(ordering, s);
  }

  Sets$0 instance= new Sets$0(){};
}

record Set$2Instance(OrderHashBy$2 ordering, LinkedHashSet<Object> set) implements Set$2{
  Set$2Instance{
    // Ideally we would check that they all have the same .ord as would be produced by this ordering
    // but that's not possible :(
    assert set.stream()
            .allMatch(k -> k instanceof MapKey) : "Set has not mapkey elements -  this will break hashing :(";
  }
  
  static Object extractKey(Object mapKey) {
      return ((MapKey) mapKey).key;
  }
  static LinkedHashSet<Object> unwrap(Object xs){
    if (xs instanceof Set$2Instance set) {
      return set.set;
    }
    throw new AssertionError("Unexpected List impl: "+xs.getClass());
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

  @Override public Object read$orderHash$0() { return ordering; }
  @Override public Object read$size$0(){ return intToNat(set.size()); }
  @Override public Object read$isEmpty$0(){ return bool(set.isEmpty()); }

  @Override public Object read$contains$1(Object p0) {
    return bool(set.contains(mapKey(ordering, p0)));
  }

  @Override public Object mut$$plus$1(Object p0){
    var s = new LinkedHashSet<>(set.size()+1);
    s.addAll(set);
    s.add(mapKey(ordering, p0));
    return new Set$2Instance(ordering, s);
  }

  @Override public Object read$$plus$1(Object p0){ return mut$$plus$1(p0); }


  @Override public Object mut$$dash$1(Object p0) {
    // Need to figure out if this is actually more efficient
    // Hashes twice 2*O(1) - but doesn't allocate the memory if the set O(n) if the element doesn't exist
    var setElement = mapKey(ordering, p0);
    if (!set.contains(setElement)) {
      return this;
    }
    var s = new LinkedHashSet<>(set.size()-1);
    s.addAll(this.set);
    if (!s.remove(setElement)) {
        throw new IllegalArgumentException("Failed to remove from set");
    }

    return new Set$2Instance(ordering, s);
  }

  @Override public Object read$$dash$1(Object p0) { return mut$$dash$1(p0); }


  @Override public Object mut$union$2(Object p0, Object p1){
    var newOrdering = ordering(p1);
    Set$2Instance other = setInstance(p0);

    // See if we can avoid rehashing some of the elements
    if (newOrdering.equals(ordering)) {
      return fastUnion(this, other);
    }
    if (newOrdering.equals(other.ordering)) {
      return fastUnion(other, this);
    }

    LinkedHashSet<Object> newSet = Stream.concat(
            this.set.stream(),
            other.set.stream()
    ).map(Set$2Instance::extractKey)
      .map(k -> mapKey(newOrdering, k))
      .collect(Collectors.toCollection(LinkedHashSet::new));

    return new Set$2Instance(newOrdering, newSet);
  }

  @Override public Object read$union$2(Object p0, Object p1){ return this.mut$union$2(p0, p1); }

  private static Set$2Instance fastUnion(Set$2Instance setWithHasher, Set$2Instance other) {
    // This cannot allocate a set that is too small - but it can make one that is too big...
    LinkedHashSet<Object> newSet = new LinkedHashSet<>(setWithHasher.set.size() + other.set.size());
    newSet.addAll(setWithHasher.set);
    for (Object elem : other.set) {
      MapKey newKey = new MapKey(setWithHasher.ordering, extractKey(elem));
      newSet.add(newKey);
    }
    return new Set$2Instance(setWithHasher.ordering, newSet);
  }

  @Override public Object mut$intersection$2(Object p0, Object p1){
    var newOrdering = ordering(p1);
    Set$2Instance s1 = this;
    Set$2Instance s2 = setInstance(p0);
    if (s1.set.isEmpty() || s2.set.isEmpty()) {
      return Sets$0.instance.imm$$hash$1(p1);
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

  @Override public Object read$intersection$2(Object p0, Object p1){ return mut$intersection$2(p0, p1);
  }
  private static Set$2Instance fastIntersection(Set$2Instance setWithHasher, Set$2Instance other) {
    return new Set$2Instance(
        setWithHasher.ordering,
        other.set.stream()
            .map(Set$2Instance::extractKey)
            .map(k -> Util.mapKey(setWithHasher.ordering, k))
            .filter(setWithHasher.set::contains)
            .collect(Collectors.toCollection(LinkedHashSet::new)));
  }

  @Override public Object mut$$dash_dash$2(Object p0, Object p1) {
    if (this.set.isEmpty()) {return Sets$0.instance.imm$$hash$1(p1);}
    var newOrdering = ordering(p1);
    Set$2Instance other = setInstance(p0);


    if (this.ordering.equals(newOrdering)) {
        // Don't have to rehash the elements from this
        return new Set$2Instance(
          newOrdering,
          this.set.stream()
            .filter(k -> !other.set.contains(mapKey(
                        other.ordering,
                        extractKey(k)
            ))).collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
    if (other.ordering.equals(newOrdering)) {
       return new Set$2Instance(
          newOrdering,
          this.set.stream()
             // rehash the current elements
            .map(k -> mapKey(newOrdering, extractKey(k)))
            // Don't need to rehash to match other.
            .filter(k -> !other.set.contains(k))
            .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

    // should investigate whether keep in this if not in other, or
    // remove from this if in other is better
    return new Set$2Instance(
          newOrdering,
          this.set.stream()
            .filter(k -> !other.set.contains(
                    mapKey(other.ordering, extractKey(k))
            ))
            .map(k -> mapKey(newOrdering, extractKey(k)))
            .collect(Collectors.toCollection(LinkedHashSet::new))
        );
  }
  
  private static LinkedHashSet<Object> rehash(LinkedHashSet<Object> s, OrderHashBy$2 ordering) {
    return s.stream()
          .<Object>map(k -> extractKey(k))
          .<Object>map(k -> mapKey(ordering, k))
          .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  @Override public Object read$containsAll$2(Object p0, Object p1) {
      Set$2Instance set = setInstance(p0);
      var newOrdering = ordering(p1);
      LinkedHashSet<Object> rehashedThis = null;
      LinkedHashSet<Object> rehashedOther = null;

      if (newOrdering.equals(this.ordering())) {
        rehashedThis = this.set;
        rehashedOther = rehash(set.set, newOrdering);

      } else if (newOrdering.equals(set.ordering())) {
        rehashedThis = rehash(this.set, newOrdering);
        rehashedOther = set.set;
      } else {
        rehashedThis = rehash(this.set, newOrdering);
        rehashedOther = rehash(set.set, newOrdering);
      }

      return bool(rehashedThis.containsAll(rehashedOther));
  }

  @Override public Object mut$distinctBy$1(Object p0) {
      var newOrdering = ordering(p0);
      if (this.ordering.equals(newOrdering)) { return this; }
      return new Set$2Instance(newOrdering, rehash(set, newOrdering));
  }

  @Override public Object read$distinctBy$1(Object p0) { return mut$distinctBy$1(p0); }
}
