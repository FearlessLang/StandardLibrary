package _base;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import static _base.Util.check;

public sealed interface SparseSegmentTree permits
  SparseSegmentLeaf,
  SparseSegmentNode,
  NullRegion
{
  long THRESHOLD = 512L;

  static SparseSegmentTree of(long start, long end) {
    if (Long.compareUnsigned(end - start, THRESHOLD) <= 0) {
      return new SparseSegmentLeaf(start, end);
    }
    return new SparseSegmentNode(start, end);
  }

  /// Return the change in the number of elements: 1 if a hole was filled, -1 if an element was removed, else 0.
  int set(long index, Object value);
  /// Return the starting index of this tree (inclusive) and the ending index (exclusive).
  long start();
  /// Return the ending index of this tree (exclusive).
  long end();
  default long midPoint() { return this.start() + Long.divideUnsigned(this.capacity(), 2); }
  long numHoles();
  long capacity();
  default boolean isEmpty() {
    return this.capacity() == this.numHoles();
  }
  <R> R explore(NodeExplorer<R> explorer);
  Object get(long index);

  /// Extends this tree (which spans [start, end)) so it spans [start, newEnd), where newEnd > end (unsigned).
  /// Returns the tree to use from now on (this, or a new root above it).
  SparseSegmentTree grow(long newEnd);

  SparseSegmentTree shallowCopy();

  Stream<Object> nullStream();
  Stream<Object> nonNullStream();
  Stream<IndexedElement<Object>> indexedStream();
  Stream<IndexedElement<Object>> reversedIndexStream();

  default long idx(Object p0, String method){
    long i= Nat$c$0Instance.unwrap(p0);
    // Lists cannot get larger than an int
    check(
      0 <= i && i < capacity(),
      "EList"+method+": Index "+Long.toUnsignedString(i)+" out of bounds, for SparseList with capacity: "+capacity()
    );
    return (int) i;
  }

  void removeIf(Predicate<Object> o);

  void trimTo(long start, long end);

  void reverse();

  void mapElements(Function<Object, Object> f);

  void swap(long i, long j);
}
