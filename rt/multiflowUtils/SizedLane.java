package base.multiflowUtils;

import base.SizedFlow$1Instance;

import java.util.Collection;
import java.util.stream.Stream;

public class SizedLane<E> {
    public Stream<E> source;
    public long size;

    public long size() {
        return this.size;
    }


    public SizedLane(Stream<E> source, long size) {
        this.source = source;
        this.size = size;
    }

    public static SizedLane<Object> of(SizedFlow$1Instance flow) {
        return new SizedLane<>(flow.s(), flow.size);
    }

    public static <E> SizedLane<E> of(Stream<E> stream, long size) {
        return new SizedLane<>(stream, size);
    }
}
