package _base;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static _base.Util.*;

public abstract class MultiFlow {
  protected interface Filler {
    Object fromIndex(Long unsignedIndex);
  }
  // list instead of array to allow for generic iterator
  final List<Iterator<Object>> channels;
  final Filler[] fillers; // can be each Filler can be null
  final int numChannels;

  long unsignedIndex = 0;

  // Set by noMoreThan(n): caps iteration at n elements regardless of channel contents.
  // -1 means "no cap". I hate sentinel values but this might be an okay use for them
  private long limit = -1;

  // Set by cutToSmallest(): once true, a channel running dry silently ends the whole
  // iteration instead of throwing on the resulting mismatch.
  private boolean cutToSmallestMode = false;

  abstract String className();

  protected MultiFlow(List<Iterator<Object>> channels) {
    this.channels = channels;
    this.numChannels = channels.size();
    this.fillers = new Filler[channels.size()];
  }

  protected MultiFlow(MultiFlow flow1, MultiFlow flow2) {
    this.numChannels = flow1.numChannels + flow2.numChannels;
    this.channels = Stream.concat(flow1.channels.stream(), flow2.channels.stream()).toList();

    this.fillers = Arrays.copyOf(flow1.fillers, numChannels);
    System.arraycopy(flow2.fillers, 0, this.fillers, flow1.numChannels, flow2.numChannels);
  }

  protected void fillChannel(int channelIndex, Filler channelFiller) {
    if (fillers[channelIndex] != null) {return;} // Already filled this channel - maybe should throw here
    fillers[channelIndex] = channelFiller;
  }

  protected MultiFlow noMoreThan(long n) {
    this.limit = (this.limit < 0) ? n : Math.min(this.limit, n);
    return this;
  }

  /**
   * `cutToSmallest()`: from now on, if any channel (without a filler) runs out while others
   * still have data, the flow just ends instead of throwing — i.e. it's implicitly limited to
   * the size of its smallest channel, discovered lazily as iteration proceeds rather than by
   * pre-scanning. Mutates `this` in place and returns `this` for chaining.
   */
  protected MultiFlow cutToSmallest() {
    this.cutToSmallestMode = true;
    return this;
  }

  private boolean underLimit() {
    return limit < 0 || unsignedIndex < limit;
  }
  boolean allReady() {
    return IntStream.range(0, numChannels)
      .allMatch(i -> Objects.nonNull(fillers[i]) || channels.get(i).hasNext());
  }
  /// Returns false if any of the channels have been exhausted and there isn't a filler present.
  /// Or if all of the channels have been exhausted/hit their limit
  /// true otherwise
  /// Throws if we discover the channels differ in length
  boolean hasNext(String methodName) {
    if (!underLimit()) { return false; }
    if (!anyHasNext()) { return false; }

    if (allReady()) return true;
    if (cutToSmallestMode) return false;

    throw new IllegalStateException(
      this.className()+methodName+": Channels differ in length: expected every channel to have a value at index " + unsignedIndex);
  }

  /** True iff at least one channel's underlying iterator still has data (ignoring fillers). */
  boolean anyHasNext() {
    if (!underLimit()) return false;
    return IntStream.range(0, numChannels)
      .anyMatch(i -> channels.get(i).hasNext());
  }

  Object[] next() {
    Object[] data = IntStream.range(0, numChannels)
      .mapToObj(i -> {
        if (channels.get(i).hasNext()) {
          return channels.get(i).next();
        }
        if (Objects.nonNull(fillers[i])) {
          return fillers[i].fromIndex(this.unsignedIndex);
        }
        throw new NoSuchElementException("Iterator exhausted");
      }).toArray();
    this.unsignedIndex += 1;
    return data;
  }

  Object[] nextOpts() {
    if (!anyHasNext()) {
      throw new NoSuchElementException("Iterator exhausted");
    }
    Object[] data = IntStream.range(0, numChannels)
      .mapToObj(i -> {
        if (channels.get(i).hasNext()) {
          return optSome(channels.get(i).next());
        }
        if (Objects.nonNull(fillers[i])) {
          return optSome(fillers[i].fromIndex(this.unsignedIndex));
        }
        return optEmpty();
      }).toArray();
    this.unsignedIndex += 1;
    return data;
  }


  protected Stream<Object> mergeExact(Function<Object[], Object> merger) {
    var self = this;

    Iterator<Object> iter = new Iterator<>() {
      @Override
      public boolean hasNext() {
        return self.hasNext(".getMerge");
      }

      @Override
      public Object next() {
        return merger.apply(self.next());
      }
    };

    return StreamSupport.stream(
      Spliterators.spliteratorUnknownSize(iter, Spliterator.ORDERED),
      false
    );
  }

  protected Object mergeToList(Function<Object[], Object> merger) {
    try {
      List<Object> buffered = new ArrayList<>();
      mergeExact(merger).forEach(buffered::add);
      return optSome(List$o$1Instance.wrap(buffered));
    } catch (IllegalStateException e) {
      return optEmpty();
    }
  }

  /** `mergeOpts`: never throws; continues until every channel is exhausted, using `.empty`
   *  for any channel (without a filler) that has already run dry. */
  protected Stream<Object> mergeOpts(Function<Object[], Object> merger) {
    var self = this;

    Iterator<Object> iter = new Iterator<>() {
      @Override
      public boolean hasNext() {
        return self.anyHasNext();
      }

      @Override
      public Object next() {
        return merger.apply(self.nextOpts());
      }
    };

    return StreamSupport.stream(
      Spliterators.spliteratorUnknownSize(iter, Spliterator.ORDERED),
      false
    );
  }


  /** `getFold`: throws (via hasNext()) as soon as channels are found to differ in length. */
  protected Object foldExact(Object acc, BiFunction<Object, Object[], Object> folder) {
    Object current = acc;
    while (this.hasNext(".getFold")) {
      current = folder.apply(current, this.next());
    }
    return current;
  }

  /** `fold`: like {@link #foldExact}, but returns `.empty` instead of throwing on a length
   *  mismatch. May consume part of the flow before discovering the mismatch. */
  protected Object fold(Object acc, BiFunction<Object, Object[], Object> folder) {
    try {
      return optSome(foldExact(acc, folder));
    } catch (IllegalStateException e) {
      return optEmpty();
    }
  }

  /** `foldOpts`: never throws; folds using `.empty` for any channel (without a filler) that
   *  has already run dry, continuing until every channel is exhausted. */
  protected Object foldOptsExact(Object acc, BiFunction<Object, Object[], Object> folder) {
    Object current = acc;
    while (this.anyHasNext()) {
      current = folder.apply(current, this.nextOpts());
    }
    return current;
  }


  /** `getFoldUntil`: throws (via hasNext()) as soon as channels are found to differ in
   *  length, unless `pred` is satisfied first. */
  protected Object foldUntilExact(Object acc, BiFunction<Object, Object[], Object> folder, Predicate<Object> pred) {
    Object current = acc;
    while (this.hasNext(".getFoldUntil")) {
      current = folder.apply(current, this.next());
      if (pred.test(current)) return current;
    }
    return current;
  }

  /** `foldUntil`: like {@link #foldUntilExact}, but returns `.empty` instead of throwing on a
   *  length mismatch (unless `pred` is satisfied first). */
  protected Object foldUntil(Object acc, BiFunction<Object, Object[], Object> folder, Predicate<Object> pred) {
    try {
      return optSome(foldUntilExact(acc, folder, pred));
    } catch (IllegalStateException e) {
      return optEmpty();
    }
  }

  /** `foldOptsUntil`: never throws; folds using `.empty` for any channel (without a filler)
   *  that has already run dry, stopping early if `pred` is satisfied, otherwise continuing
   *  until every channel is exhausted. */
  protected Object foldOptsUntil(Object acc, BiFunction<Object, Object[], Object> folder, Predicate<Object> pred) {
    Object current = acc;
    while (this.anyHasNext()) {
      current = folder.apply(current, this.nextOpts());
      if (pred.test(current)) return current;
    }
    return current;
  }

  /// Since everything returns object we can just implement this here.
  public Object mut$noMoreThan$1(Object p0) {
    return this.noMoreThan(Nat$c$0Instance.unwrap(p0));
  }

  public Object mut$cutToSmallest$0() {
    return this.cutToSmallest();
  }
}

final class SingleChannelFlow extends MultiFlow {
  SingleChannelFlow(Stream<Object> stream) {
    this(stream.iterator());
  }
  SingleChannelFlow(Iterator<Object> iter) {
    super(List.of(iter));
  }

  @Override
  String className() {
    return "SingleChannelFlow";
  }
}

final class BiFlow extends MultiFlow implements _MultiFlow$lk$1, BiFlow$2w$2 {

  BiFlow(Stream<Object> channelA, Stream<Object> channelB) {
    super(List.of(channelA.iterator(), channelB.iterator()));
  }

  @Override
  String className() {
    return "BiFlow";
  }

  @Override public Object mut$with$1(Object p0) {
    Stream<Object> stream = _base.Flow$o$1Instance.unwrap(p0);
    return new TriFlow(this, stream);
  }

  @Override public Object mut$withBoth$1(Object p0) {
    BiFlow biFlow = (BiFlow) p0;
    return new QuadFlow(this, biFlow);
  }

  @Override public Object mut$enumerate$0() {
    var tri =  new TriFlow(new SingleChannelFlow(Stream.of()), this);
    tri.fillChannel(0, Nat$c$0Instance::instance);
    return tri;
  }

  @Override public Object mut$getMerge$1(Object p0) {
    return _base.Flow$o$1Instance.of(
      this.mergeExact(tuple -> callF$3(p0, tuple[0], tuple[1]))
    );
  }

  @Override public Object mut$mergeToList$1(Object p0) {
    return this.mergeToList(tuple -> callF$3(p0, tuple[0], tuple[1]));
  }

  @Override public Object mut$mergeOpts$1(Object p0) {
    return _base.Flow$o$1Instance.of(
      this.mergeOpts(tuple -> callF$3(p0, tuple[0], tuple[1]))
    );
  }

  @Override public Object mut$getFold$2(Object p0, Object p1) {
    return this.foldExact(p0, (acc, tuple) -> callF$4(p1, acc, tuple[0], tuple[1]));
  }

  @Override public Object mut$fold$2(Object p0, Object p1) {
    return this.fold(p0, (acc, tuple) -> callF$4(p1, acc, tuple[0], tuple[1]));
  }

  @Override public Object mut$foldOpts$2(Object p0, Object p1) {
    return this.foldOptsExact(p0, (acc, tuple) -> callF$4(p1, acc, tuple[0], tuple[1]));
  }

  @Override public Object mut$getFoldUntil$3(Object p0, Object p1, Object p2) {
    return this.foldUntilExact(
      p0,
      (acc, tuple) -> callF$4(p1, acc, tuple[0], tuple[1]),
      acc -> isTrue(callF$2(p2, acc))
    );
  }

  @Override public Object mut$foldUntil$3(Object p0, Object p1, Object p2) {
    return this.foldUntil(
      p0,
      (acc, tuple) -> callF$4(p1, acc, tuple[0], tuple[1]),
      acc -> isTrue(callF$2(p2, acc))
    );
  }

  @Override public Object mut$foldOptsUntil$3(Object p0, Object p1, Object p2) {
    return this.foldOptsUntil(
      p0,
      (acc, tuple) -> callF$4(p1, acc, tuple[0], tuple[1]),
      acc -> isTrue(callF$2(p2, acc))
    );
  }

  @Override public Object mut$fillA$1(Object p0) {
    this.fillChannel(0, idx -> callF$2(p0, Nat$c$0Instance.instance(idx)));
    return this;
  }

  @Override public Object mut$fillB$1(Object p0) {
    this.fillChannel(1, idx -> callF$2(p0, Nat$c$0Instance.instance(idx)));
    return this;
  }
}

final class TriFlow extends MultiFlow implements _MultiFlow$lk$1, TriFlow$5k$3 {
  TriFlow(MultiFlow flow1, MultiFlow flow2) {
    super(flow1, flow2);
  }

  TriFlow(MultiFlow flow1, Stream<Object> extra) {
    this(flow1, new SingleChannelFlow(extra));
  }

  @Override
  String className() {
    return "TriFlow";
  }

  @Override public Object mut$with$1(Object p0) {
    Stream<Object> stream = _base.Flow$o$1Instance.unwrap(p0);
    return new QuadFlow(this, stream);
  }

  @Override public Object mut$enumerate$0() {
    var quad = new QuadFlow(new SingleChannelFlow(Stream.of()), this);
    quad.fillChannel(0, Nat$c$0Instance::instance);
    return quad;
  }

  @Override public Object mut$getMerge$1(Object p0) {
    return _base.Flow$o$1Instance.of(
      this.mergeExact(tuple -> callF$4(p0, tuple[0], tuple[1], tuple[2]))
    );
  }

  @Override public Object mut$mergeToList$1(Object p0) {
    return this.mergeToList(tuple -> callF$4(p0, tuple[0], tuple[1], tuple[2]));
  }

  @Override public Object mut$mergeOpts$1(Object p0) {
    return _base.Flow$o$1Instance.of(
      this.mergeOpts(tuple -> callF$4(p0, tuple[0], tuple[1], tuple[2]))
    );
  }

  @Override public Object mut$getFold$2(Object p0, Object p1) {
    return this.foldExact(p0, (acc, tuple) -> callF$5(p1, acc, tuple[0], tuple[1], tuple[2]));
  }

  @Override public Object mut$fold$2(Object p0, Object p1) {
    return this.fold(p0, (acc, tuple) -> callF$5(p1, acc, tuple[0], tuple[1], tuple[2]));
  }

  @Override public Object mut$foldOpts$2(Object p0, Object p1) {
    return this.foldOptsExact(p0, (acc, tuple) -> callF$5(p1, acc, tuple[0], tuple[1], tuple[2]));
  }

  @Override public Object mut$getFoldUntil$3(Object p0, Object p1, Object p2) {
    return this.foldUntilExact(
      p0,
      (acc, tuple) -> callF$5(p1, acc, tuple[0], tuple[1], tuple[2]),
      acc -> isTrue(callF$2(p2, acc))
    );
  }

  @Override public Object mut$foldUntil$3(Object p0, Object p1, Object p2) {
    return this.foldUntil(
      p0,
      (acc, tuple) -> callF$5(p1, acc, tuple[0], tuple[1], tuple[2]),
      acc -> isTrue(callF$2(p2, acc))
    );
  }

  @Override public Object mut$foldOptsUntil$3(Object p0, Object p1, Object p2) {
    return this.foldOptsUntil(
      p0,
      (acc, tuple) -> callF$5(p1, acc, tuple[0], tuple[1], tuple[2]),
      acc -> isTrue(callF$2(p2, acc))
    );
  }

  @Override public Object mut$fillA$1(Object p0) {
    this.fillChannel(0, idx -> callF$2(p0, Nat$c$0Instance.instance(idx)));
    return this;
  }

  @Override public Object mut$fillB$1(Object p0) {
    this.fillChannel(1, idx -> callF$2(p0, Nat$c$0Instance.instance(idx)));
    return this;
  }

  @Override public Object mut$fillC$1(Object p0) {
    this.fillChannel(2, idx -> callF$2(p0, Nat$c$0Instance.instance(idx)));
    return this;
  }
}

final class QuadFlow extends MultiFlow implements _MultiFlow$lk$1, QuadFlow$aw$4 {

  QuadFlow(MultiFlow flow1, MultiFlow flow2) {
    super(flow1, flow2);
  }

  QuadFlow(MultiFlow flow1, Stream<Object> extra) {
    this(flow1, new SingleChannelFlow(extra));
  }

  @Override
  String className() {
    return "QuadFlow";
  }


  @Override public Object mut$getMerge$1(Object p0) {
    return _base.Flow$o$1Instance.of(
      this.mergeExact(tuple -> callF$5(p0, tuple[0], tuple[1], tuple[2], tuple[3]))
    );
  }

  @Override public Object mut$mergeToList$1(Object p0) {
    return this.mergeToList(tuple -> callF$5(p0, tuple[0], tuple[1], tuple[2], tuple[3]));
  }

  @Override public Object mut$mergeOpts$1(Object p0) {
    return _base.Flow$o$1Instance.of(
      this.mergeOpts(tuple -> callF$5(p0, tuple[0], tuple[1], tuple[2], tuple[3]))
    );
  }

  @Override public Object mut$getFold$2(Object p0, Object p1) {
    return this.foldExact(p0, (acc, tuple) -> callF$6(p1, acc, tuple[0], tuple[1], tuple[2], tuple[3]));
  }

  @Override public Object mut$fold$2(Object p0, Object p1) {
    return this.fold(p0, (acc, tuple) -> callF$6(p1, acc, tuple[0], tuple[1], tuple[2], tuple[3]));
  }

  @Override public Object mut$foldOpts$2(Object p0, Object p1) {
    return this.foldOptsExact(p0, (acc, tuple) -> callF$6(p1, acc, tuple[0], tuple[1], tuple[2], tuple[3]));
  }

  @Override public Object mut$getFoldUntil$3(Object p0, Object p1, Object p2) {
    return this.foldUntilExact(
      p0,
      (acc, tuple) -> callF$6(p1, acc, tuple[0], tuple[1], tuple[2], tuple[3]),
      acc -> isTrue(callF$2(p2, acc))
    );
  }

  @Override public Object mut$foldUntil$3(Object p0, Object p1, Object p2) {
    return this.foldUntil(
      p0,
      (acc, tuple) -> callF$6(p1, acc, tuple[0], tuple[1], tuple[2], tuple[3]),
      acc -> isTrue(callF$2(p2, acc))
    );
  }

  @Override public Object mut$foldOptsUntil$3(Object p0, Object p1, Object p2) {
    return this.foldOptsUntil(
      p0,
      (acc, tuple) -> callF$6(p1, acc, tuple[0], tuple[1], tuple[2], tuple[3]),
      acc -> isTrue(callF$2(p2, acc))
    );
  }

  @Override public Object mut$fillA$1(Object p0) {
    this.fillChannel(0, idx -> callF$2(p0, Nat$c$0Instance.instance(idx)));
    return this;
  }

  @Override public Object mut$fillB$1(Object p0) {
    this.fillChannel(1, idx -> callF$2(p0, Nat$c$0Instance.instance(idx)));
    return this;
  }

  @Override public Object mut$fillC$1(Object p0) {
    this.fillChannel(2, idx -> callF$2(p0, Nat$c$0Instance.instance(idx)));
    return this;
  }

  @Override public Object mut$fillD$1(Object p0) {
    this.fillChannel(3, idx -> callF$2(p0, Nat$c$0Instance.instance(idx)));
    return this;
  }
}