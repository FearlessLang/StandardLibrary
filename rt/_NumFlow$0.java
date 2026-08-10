package base;

import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface _NumFlow$0 extends base.Sealed$0 {
_NumFlow$0 instance = new _NumFlow$0() {};

  default Object imm$bytes$2(Object p0, Object p1){
    byte start = Byte$0Instance.unwrap(p0);
    byte end = Byte$0Instance.unwrap(p1);
    assert start <= end;
    return Flow$1Instance.of(IntStream.rangeClosed(start, end)
      .mapToObj(i -> Byte$0Instance.instance((byte) i)));
  }

  default Object imm$bytes$3(Object p0, Object p1, Object p2){
    byte start = Byte$0Instance.unwrap(p0);
    byte end = Byte$0Instance.unwrap(p1);
    byte step = Byte$0Instance.unwrap(p2);
    assert start <= end;
    assert step > 0;
    return Flow$1Instance.of(
      Stream.iterate(start, d -> Byte.compareUnsigned(d, end) <= 0, d -> (byte) (d + step))
        .map(Byte$0Instance::instance)
      );
  }

  default Object imm$ints$2(Object p0, Object p1){
    long start = Int$0Instance.unwrap(p0);
    long end = Int$0Instance.unwrap(p1);
    assert start <= end;
    return Flow$1Instance.of(LongStream.rangeClosed(start, end)
      .mapToObj(Int$0Instance::instance));
  }

  default Object imm$ints$3(Object p0, Object p1, Object p2){
    long start = Byte$0Instance.unwrap(p0);
    long end = Byte$0Instance.unwrap(p1);
    long step = Byte$0Instance.unwrap(p2);
    assert start <= end;
    assert step > 0;
    return Flow$1Instance.of(
      Stream.iterate(start, d -> d <= end, d -> d + step)
        .map(Int$0Instance::instance)
    );
  }

  default Object imm$nats$2(Object p0, Object p1){
    long start = Nat$0Instance.unwrap(p0);
    long end = Nat$0Instance.unwrap(p1);
    assert Long.compareUnsigned(start, end) <= 0;
    if (Long.compareUnsigned(end, Long.MAX_VALUE) <= 0) {
      return Flow$1Instance.of(LongStream.rangeClosed(start, end)
        .mapToObj(Nat$0Instance::instance));
    }
    if (Long.compareUnsigned(start, Long.MAX_VALUE) > 0) {
      return Flow$1Instance.of(LongStream.rangeClosed(start, end)
        .mapToObj(Nat$0Instance::instance));
    }
    return Flow$1Instance.of(LongStream.concat(
      LongStream.rangeClosed(start, Long.MAX_VALUE),
      LongStream.rangeClosed(Long.MIN_VALUE, end)
    ).mapToObj(Nat$0Instance::instance));
  }

  default Object imm$nats$3(Object p0, Object p1, Object p2){
    long start = Byte$0Instance.unwrap(p0);
    long end = Byte$0Instance.unwrap(p1);
    long step = Byte$0Instance.unwrap(p2);
    assert Long.compareUnsigned(start, end) <= 0;
    assert Long.compareUnsigned(step, 0) > 0;
    return Flow$1Instance.of(
      Stream.iterate(start, d -> Long.compareUnsigned(d, end) <= 0, d -> d + step)
        .map(Int$0Instance::instance)
    );
  }

  default Object imm$floats$2(Object p0, Object p1) {
    double start = Float$0Instance.unwrap(p0);
    double end = Float$0Instance.unwrap(p1);
    assert start <= end;
    return Flow$1Instance.of(
      streamDoublesBetweenAsBits(start, end)
        .mapToObj(bits -> Float$0Instance.instance(Double.longBitsToDouble(bits)))
    );
  }

  default Object imm$floats$3(Object p0, Object p1, Object p2) {
    double start = Float$0Instance.unwrap(p0);
    double end = Float$0Instance.unwrap(p1);
    double step = Float$0Instance.unwrap(p2);
    assert start <= end;
    assert step > 0;
    return Flow$1Instance.of(
      Stream.iterate(start, d -> d <= end, d -> d+step)
        .map(Float$0Instance::instance)
    );
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

  default Object imm$float$3(Object p0, Object p1, Object p2) {
    double start = Float$0Instance.unwrap(p0);
    double end = Float$0Instance.unwrap(p1);
    double step = Float$0Instance.unwrap(p2);
    assert start <= end;
    assert step > 0;
    return Flow$1Instance.of(
      Stream.iterate(start, d -> d <= end, d -> d+step)
        .map(Float$0Instance::instance)
    );
  }
}
