package base;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static base.SparseSegmentTreeImpl.lessThan;
import static base.SparseSegmentTreeImpl.lessThanEq;

public final class SparseSegmentNode implements SparseSegmentTree {
  // Not final as reversal/growth may happen
  long start;
  long end;
  long midPoint;
  long numHoles;
  SparseSegmentTree left, right;

  SparseSegmentNode(long start, long end) {
    this(start, end, null, null);
  }

  SparseSegmentNode(long start, long end, SparseSegmentTree left, SparseSegmentTree right) {
    this.start = start;
    this.end = end;
    long size = end - start;
    this.midPoint = start + Long.divideUnsigned(size, 2);
    this.numHoles = size;
    this.left = left;
    this.right = right;
  }

  @Override
  public Object get(long index) {
    if (Long.compareUnsigned(index, midPoint) < 0) { return left.get(index); }
    return right.get(index);
  }

  @Override
  public int set(long index, Object value) {
    int filled;
    if (Long.compareUnsigned(index, midPoint) < 0) {
      if (left instanceof NullRegion) {
        if (value == null) { return 0; }
        left = SparseSegmentTree.of(start, midPoint);
      }
      filled = left.set(index, value);
    } else {
      if (right instanceof NullRegion) {
        if (value == null) { return 0; }
        right = SparseSegmentTree.of(midPoint, end);
      }
      filled = right.set(index, value);
    }
    this.numHoles -= filled;
    return filled;
  }

  @Override public long start() { return this.start; }
  @Override public long end() { return this.end; }
  @Override public long numHoles() { return numHoles; }
  @Override public long capacity() { return end - start; }
  @Override public <R> R explore(NodeExplorer<R> explorer) {
    return explorer.node(this);
  }

  // Only the right child's range depends on `end`, and a null right child means "all holes",
  // so growing is just extending the right spine.
  @Override public SparseSegmentTree grow(long newEnd) {
    long added = newEnd - end;
    if (right != null) { right = right.grow(newEnd); }
    end = newEnd;
    numHoles += added;
    return this;
  }


  @Override public SparseSegmentNode shallowCopy() {
    return new SparseSegmentNode(
      start,
      end,
      left.shallowCopy(),
      right.shallowCopy()
    );
  }

  @Override
  public Stream<Object> nullStream() {
    return Stream.concat(left.nullStream(), right.nullStream());
  }

  @Override
  public Stream<Object> nonNullStream() {
    return Stream.concat(left.nonNullStream(), right.nonNullStream());
  }

  @Override
  public Stream<IndexedElement<Object>> indexedStream() {
    return Stream.concat(left.indexedStream(), right.indexedStream());
  }

  @Override
  public Stream<IndexedElement<Object>> reversedIndexStream() {
    return Stream.concat(right.indexedStream(), left.indexedStream());
  }

  @Override
  public void removeIf(Predicate<Object> o) {
    left.removeIf(o);
    right.removeIf(o);
  }

  @Override
  public void trimTo(long start, long end) {
    boolean startInsideLeft = lessThanEq(this.left.start(), start);
    boolean endInsideLeft = lessThanEq(end, this.left.midPoint());
    if (startInsideLeft != endInsideLeft) {
      throw new IllegalStateException(
        "Should not call trimTo on the a node where the indices don't split both "
        + " as we would want to make this = this.left/right which has to be done at parent level"
      );
    }
    // in both left and right
    this.left.trimTo(start, this.midPoint);
    this.right.trimTo(this.midPoint, end);
    this.numHoles = this.left.numHoles() + this.right.numHoles();
  }

  @Override
  public void reverse() {
    var temp = this.left;
    this.left = this.right;
    this.right = temp;
    this.midPoint = this.left.end();
  }

  @Override
  public void mapElements(Function<Object, Object> f) {
    this.left.mapElements(f);
    this.right.mapElements(f);
  }

  @Override
  public void swap(long i, long j) {
    boolean iInsideLeft = lessThanEq(start, i) && lessThan(i, end);
    boolean jInsideLeft = lessThanEq(start, i) && lessThan(i, end);
    if (iInsideLeft && jInsideLeft) {
      left.swap(i, j);
      return;
    }
    if (!iInsideLeft && !jInsideLeft) {
      right.swap(i, j);
      return;
    }
    // one is in left & one is in right
    var temp = left.get(i);
    left.set(i, right.get(j));
    right.set(j, temp);
  }

  public void instantiateLeft() {
    if (this.left instanceof NullRegion) {
      this.left = SparseSegmentTree.of(start, midPoint);
    }
  }
  public void instantiateRight() {
    if (this.right instanceof NullRegion) {
      this.right = SparseSegmentTree.of(midPoint, end);
    }
  }
}
