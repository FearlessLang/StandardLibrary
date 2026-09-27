package base;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static base.SparseSegmentTreeImpl.lessThan;
import static base.SparseSegmentTreeImpl.lessThanEq;
import static base.Util.err;

public final class SparseSegmentLeaf implements SparseSegmentTree {
  // Not final as reversal/growth may happen
  long start;
  long end;
  Object[] data;
  int numHoles;

  SparseSegmentLeaf(long start, long end) {
    this.start = start;
    this.end = end;
    this.data = new Object[(int) (end - start)];
    assert data.length == (end-start);
    this.numHoles = data.length;
  }

  SparseSegmentLeaf(long start, long end, Object[] data) {
    this.start = start;
    this.end = end;
    assert data.length == (end - start);
    this.data = data;
    // FIX: was left at 0.
    int holes = 0;
    for (Object t : data) {
      if (t == null) {
        holes++;
      }
    }
    this.numHoles = holes;
  }

  @Override public Object get(long index) { return data[(int) (index - start)]; }

  @Override
  public int set(long index, Object value) {
    Object oldValue = data[(int) (index - start)];
    data[(int) (index - start)] = value;
    if (oldValue == null && value != null) {
      this.numHoles -= 1;
      return 1;
    }
    if (oldValue != null && value == null) {
      this.numHoles += 1;
      return -1;
    }
    return 0;
  }

  @Override public long start() { return this.start; }
  @Override public long end() { return this.end; }
  @Override public long numHoles() { return numHoles;}
  @Override public long capacity() { return end - start; }

  @Override
  public <R> R explore(NodeExplorer<R> explorer) {
    return explorer.leaf(this);
  }

  @Override public SparseSegmentTree grow(long newEnd) {
    long added = newEnd - end;
    if (Long.compareUnsigned(newEnd - start, THRESHOLD) <= 0) {
      // Still small enough to be a single leaf.
      data = Arrays.copyOf(data, (int) (newEnd - start));
      numHoles += (int) added;
      end = newEnd;
      return this;
    }
    // Too big for a leaf: hang this leaf under a node whose midPoint is the old end,
    // so every existing index keeps routing to it.
    SparseSegmentNode root = new SparseSegmentNode(start, newEnd, this, null);
    root.midPoint = this.end;
    root.numHoles = this.numHoles + added;
    return root;
  }

  @Override
  public SparseSegmentLeaf shallowCopy() {
    return new SparseSegmentLeaf(
      start,
      end,
      Arrays.copyOf(data, data.length)
    );
  }

  @Override
  public Stream<Object> nullStream() {
    return Arrays.stream(data);
  }

  @Override
  public Stream<Object> nonNullStream() {
    return Arrays.stream(data).filter(Objects::nonNull);
  }

  @Override
  public Stream<IndexedElement<Object>> indexedStream() {
    return IntStream.range(0, data.length)
      .mapToObj(i -> new IndexedElement<>(start+i, data[i]));
  }

  @Override
  public Stream<IndexedElement<Object>> reversedIndexStream() {
    return IntStream.rangeClosed(1, data.length)
      .map(i -> data.length - i)
      .mapToObj(i -> new IndexedElement<>(start+i, data[i]));
  }

  @Override
  public void removeIf(Predicate<Object> shouldRemove) {
    for (int i=0;i<data.length;i++) {
      Object elem = data[i];
      if (elem == null) { continue; }
      if (shouldRemove.test(elem)) { data[i] = null; }
    }
  }

  @Override
  public void trimTo(long start, long end) {
    // bounds within this list
    assert lessThanEq(this.start, start);
    assert lessThanEq(end, this.end);
    int newLength = (int) (end - start);
    int arrStart = (int) (start - this.start);
    int arrEnd = arrStart + newLength;
    this.data = Arrays.copyOfRange(data, arrStart, arrEnd);
  }

  @Override
  public void reverse() {
    reverse(data);
  }

  public static <T> void reverse(T[] arr) {
    for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
      swap(arr, i, j);
    }
  }
  public static <T> void swap(T[] arr, int i, int j) {
    T temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }


  @Override
  public void mapElements(Function<Object, Object> f) {
    for (int i=0;i<data.length;i++) {
      Object elem = data[i];
      if (elem != null) { data[i] = f.apply(elem); }
    }
  }

  @Override
  public void swap(long i, long j) {
    swap(this.data, toLocalIndex(i), toLocalIndex(j));
  }

  int toLocalIndex(long i) {
    if (lessThan(end, i)) {
      throw err("Tried to use index into leaf with index "+i+" only supports up "+Long.toUnsignedString(end - 1));
    }
    return (int) (i - start);
  }
}
