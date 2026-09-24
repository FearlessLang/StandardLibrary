package _base;

import static _base.Util.*;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface _NumFlow$5k$0 extends _base.Sealed$2o$0 {
_NumFlow$5k$0 instance = new _NumFlow$5k$0() {};

  default Object imm$bytes$2(Object p0, Object p1){
    int start = Byte.toUnsignedInt(Byte$o$0Instance.unwrap(p0));
    int end = Byte.toUnsignedInt(Byte$o$0Instance.unwrap(p1));
    assert start <= end;
    return Flow$o$1Instance.of(IntStream.rangeClosed(start, end)
      .mapToObj(i -> Byte$o$0Instance.instance((byte) i)));
  }

  default Object imm$bytes$3(Object p0, Object p1, Object p2){
    int start = Byte.toUnsignedInt(Byte$o$0Instance.unwrap(p0));
    int end = Byte.toUnsignedInt(Byte$o$0Instance.unwrap(p1));
    int step = Byte.toUnsignedInt(Byte$o$0Instance.unwrap(p2));
    assert start <= end;
    if (step == 0){ throw badStep(p2); }
    return Flow$o$1Instance.of(IntStream.iterate(start, i -> i <= end, i -> i + step)
      .mapToObj(i -> Byte$o$0Instance.instance((byte) i)));
  }

  default Object imm$ints$2(Object p0, Object p1){
    long start = Int$c$0Instance.unwrap(p0);
    long end = Int$c$0Instance.unwrap(p1);
    assert start <= end;
    return Flow$o$1Instance.of(LongStream.rangeClosed(start, end)
      .mapToObj(Int$c$0Instance::instance));
  }

  default Object imm$ints$3(Object p0, Object p1, Object p2){
    long start = Int$c$0Instance.unwrap(p0);
    long end = Int$c$0Instance.unwrap(p1);
    long step = Int$c$0Instance.unwrap(p2);
    assert start <= end;
    if (step <= 0){ throw badStep(p2); }
    return Flow$o$1Instance.of(
      Stream.iterate(start, d -> d != null, d -> Long.compareUnsigned(end - d, step) < 0 ? null : d + step)
        .map(Int$c$0Instance::instance)
    );
  }

  default Object imm$nats$2(Object p0, Object p1){
    long start = Nat$c$0Instance.unwrap(p0);
    long end = Nat$c$0Instance.unwrap(p1);
    assert Long.compareUnsigned(start, end) <= 0;
    if (Long.compareUnsigned(end, Long.MAX_VALUE) <= 0) {
      return Flow$o$1Instance.of(LongStream.rangeClosed(start, end)
        .mapToObj(Nat$c$0Instance::instance));
    }
    if (Long.compareUnsigned(start, Long.MAX_VALUE) > 0) {
      return Flow$o$1Instance.of(LongStream.rangeClosed(start, end)
        .mapToObj(Nat$c$0Instance::instance));
    }
    return Flow$o$1Instance.of(LongStream.concat(
      LongStream.rangeClosed(start, Long.MAX_VALUE),
      LongStream.rangeClosed(Long.MIN_VALUE, end)
    ).mapToObj(Nat$c$0Instance::instance));
  }

  default Object imm$nats$3(Object p0, Object p1, Object p2){
    long start = Nat$c$0Instance.unwrap(p0);
    long end = Nat$c$0Instance.unwrap(p1);
    long step = Nat$c$0Instance.unwrap(p2);
    assert Long.compareUnsigned(start, end) <= 0;
    if (step == 0){ throw badStep(p2); }
    return Flow$o$1Instance.of(
      Stream.iterate(start, d -> d != null, d -> Long.compareUnsigned(end - d, step) < 0 ? null : d + step)
        .map(Nat$c$0Instance::instance)
    );
  }

  default Object imm$floats$2(Object p0, Object p1) {
    double start = Float$1c$0Instance.unwrap(p0);
    double end = Float$1c$0Instance.unwrap(p1);
    assert start <= end;
    return Flow$o$1Instance.of(
      streamDoublesBetweenAsBits(start, end)
        .mapToObj(bits -> Float$1c$0Instance.instance(Double.longBitsToDouble(bits)))
    );
  }

  default Object imm$floats$3(Object p0, Object p1, Object p2) {
    double start = Float$1c$0Instance.unwrap(p0);
    double end = Float$1c$0Instance.unwrap(p1);
    double step = Float$1c$0Instance.unwrap(p2);
    assert start <= end;
    if (!(step > 0)){ throw badStep(p2); }
    return Flow$o$1Instance.of(
      Stream.iterate(start, d -> d != null, d -> d == end || d + step > end ? null : grown(d, step))
        .map(Float$1c$0Instance::instance)
    );
  }

  private static double grown(double d, double step){
    if (d + step == d){ throw err("Range.flow(step): the step "+toS(Float$1c$0Instance.instance(step))+" is too small to move past "+toS(Float$1c$0Instance.instance(d))+": adding it gives the same Float back."); }
    return d + step;
  }

  long smallestNegBits = Double.doubleToRawLongBits(Math.nextDown(-0.0));

  static LongStream streamDoublesBetweenAsBits(double start, double end) {
    assert !Double.isNaN(start) && !Double.isNaN(end);
    assert start <= end;
    long startBits = Double.doubleToRawLongBits(start);
    long endBits = Double.doubleToRawLongBits(end);
    // I love -0.0 and 0.0...
    boolean startNeg = startBits < 0; // true for negatives AND -0.0
    boolean endNeg   = endBits < 0;

    if (startNeg && !endNeg) {
      return LongStream.concat(
        streamDoublesBetweenAsBits(Math.nextDown(-0.0), start)
          .map(doubleBits -> smallestNegBits - doubleBits + startBits), // reverse the stream
        streamDoublesBetweenAsBits(0.0, end)
        );
    }
    return LongStream.rangeClosed(startBits, endBits);
  }

  private static Error badStep(Object step){
    return err("Range.flow(step): the step is "+toS(step)+", but a step must be positive: the flow goes from the start of the range up to its end.");
  }
}
