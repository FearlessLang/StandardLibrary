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

    default Object imm$ints$2(Object p0, Object p1){
        long start = Int$0Instance.unwrap(p0);
        long end = Int$0Instance.unwrap(p1);
        assert start <= end;
        return Flow$1Instance.of(LongStream.rangeClosed(start, end)
                .mapToObj(Int$0Instance::instance));
    }

    default Object imm$nats$2(Object p0, Object p1){
        long start = Nat$0Instance.unwrap(p0);
        long end = Nat$0Instance.unwrap(p1);
        assert start <= end;
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

    default Object imm$floats$2(Object p0, Object p1) {
        double start = Float$0Instance.unwrap(p0);
        double end = Float$0Instance.unwrap(p1);
        assert start <= end;
        return Flow$1Instance.of(
                Stream.iterate(start, d -> d <= end, Math::nextUp)
                        .map(Float$0Instance::instance)
        );
    }

    default Object imm$floats$3(Object p0, Object p1, Object p2) {
        double start = Float$0Instance.unwrap(p0);
        double end = Float$0Instance.unwrap(p1);
        double step = Float$0Instance.unwrap(p2);
        assert start <= end;
        return Flow$1Instance.of(
                Stream.iterate(start, d -> d <= end, d -> d+step)
                        .map(Float$0Instance::instance)
        );
    }

    default Object imm$numsOpen$3(Object p0, Object p1, Object p2) {
        double start = Float$0Instance.unwrap(p0);
        double end = Float$0Instance.unwrap(p1);
        double step = Float$0Instance.unwrap(p2);
        assert start <= end;
        assert step > 0.0;
        return Flow$1Instance.of(
                Stream.iterate(start, d -> d < end, d -> d+step)
                        .map(Float$0Instance::instance)
        );
    }

    default Object imm$numsClosed$3(Object p0, Object p1, Object p2) {
        Num$0Instance start = (Num$0Instance) p0;
        Num$0Instance end = (Num$0Instance) p1;
        Num$0Instance step = (Num$0Instance) p2;

        return Flow$1Instance.of(Stream.iterate(
                start,
                n -> Num$0Instance.cmp(n, end) <= 0,
                n -> (Num$0Instance) n.imm$$plus$1(step)
        ).map(i -> i));
    }


}
