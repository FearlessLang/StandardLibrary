package base;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static base.SparseSegmentTreeImpl.lessThanEq;
import static base._NumFlow$5k$0.unsignedClosedRange;

public final class NullRegion implements SparseSegmentTree {
  long start;
  long end;

  public NullRegion(long start, long end) {
    this.start = start;
    this.end = end;
  }

  @Override
  public Object get(long index) {
    return null;
  }

  @Override
  public int set(long index, Object value) {
    throw new UnsupportedOperationException("Cannot set an element in a null region");
  }

  @Override
  public long start() {
    return start;
  }

  @Override
  public long end() {
    return end;
  }

  @Override
  public long numHoles() {
    return end - start;
  }

  @Override
  public long capacity() {
    return end - start;
  }

  @Override
  public <R> R explore(NodeExplorer<R> explorer) {
    return explorer.nullRegion(this);
  }

  @Override
  public NullRegion grow(long newEnd) {
    if (newEnd < this.end) {
      throw new IllegalArgumentException("Cannot grow a null region to a smaller end: " + newEnd + " < " + this.end);
    }
    this.end = newEnd;
    return this;
  }

  @Override
  public Stream<Object> nullStream() {
    return unsignedClosedRange(start, end).mapToObj(_ -> null);
  }

  @Override
  public Stream<Object> nonNullStream() {
    return Stream.empty();
  }

  @Override
  public Stream<IndexedElement<Object>> indexedStream() {
    return Stream.empty();
  }

  @Override
  public Stream<IndexedElement<Object>> reversedIndexStream() {
    return Stream.empty();
  }

  @Override
  public void removeIf(Predicate<Object> o) {}

  @Override
  public void trimTo(long start, long end) {
    assert lessThanEq(this.start, start);
    assert lessThanEq(end, this.end);
    this.start = start;
    this.end = end;
  }

  @Override
  public void reverse() {}

  @Override
  public void mapElements(Function<Object, Object> f) {}

  @Override
  public void swap(long i, long j) {}

  @Override
  public NullRegion shallowCopy() {
    return new NullRegion(start, end);
  }
}
