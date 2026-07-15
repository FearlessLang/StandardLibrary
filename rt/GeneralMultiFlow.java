package base;

import base.multiflowUtils.SizedLane;

import java.util.*;
import java.util.function.LongFunction;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static base.Util.*;

public abstract class GeneralMultiFlow implements _MultiFlow$0  {
    protected List<SizedLane<Object>> lanes;
    long minSize;
    protected long maxSize;
    long[] sizes;

    protected void toOpts() {
        for (SizedLane<Object> lane : lanes) {
            lane.source = lane.source.map(Util::optSome);
            this.fill(lane, _ -> Util.optEmpty());
        }
    }
    public static SizedFlow$1Instance sizedFlow(Object p0) {
        return (SizedFlow$1Instance) p0;
    }

    public static BiFlow$2Instance biFlow(Object p0) {
        return (BiFlow$2Instance) p0;
    }
    public static TriFlow$3Instance triFlow(Object p0) {
        return (TriFlow$3Instance) p0;
    }
    public static QuadFlow$4Instance quadFlow(Object p0) {
        return (QuadFlow$4Instance) p0;
    }
    @SafeVarargs
    protected GeneralMultiFlow(SizedLane<Object>... lanes) {
        this.lanes = Arrays.asList(lanes);
        recalculateSizes();
    }

    protected void recalculateSizes() {
        this.sizes = lanes.stream().mapToLong(SizedLane::size).toArray();
        this.minSize = Arrays.stream(sizes).min().getAsLong();
        this.maxSize = Arrays.stream(sizes).max().getAsLong();
    }

    protected void limit(long limit) {
        long lim = natToLong(limit);
        if (lim >= maxSize) { return; }
        int l = (int) lim;
        lanes.forEach(lane -> lane.size = Math.min(lane.size, l));
        recalculateSizes();
    }

    protected void lanesSameSizeForOp(String operation) {
        if (this.minSize != this.maxSize) {
            throw err("Cannot safely "+operation+" lanes of different sizes.\n"
                    + "Current lane sizes: " + Arrays.toString(sizes)
                    + "\n Try .limit, or the appropriate .fill");
        }
    }

    protected Object sizes() {
        return List$1Instance.wrap(Arrays.stream(this.sizes).<Object>mapToObj(
                Nat$0Instance::instance
        ).toList());
    }
    @Override public Object read$minSize$0() {
        return Nat$0Instance.instance(this.minSize);
    }

    @Override public Object read$maxSize$0() {
        return Nat$0Instance.instance(this.maxSize);
    }
    @Override public Object read$sizes$0() {
        return List$1Instance.wrap(Arrays.stream(this.sizes).<Object>mapToObj(
                Nat$0Instance::instance
        ).toList());
    }


    protected void fill(SizedLane<Object> lane, LongFunction<Object> filler) {
        if (lane.size == maxSize) { return; }
        var source = lane.source;
        var oldSize = lane.size;
        lane.source = Stream.concat(
                source.limit(oldSize),
                LongStream.range(oldSize, maxSize).mapToObj(filler)
        );
        lane.size = maxSize;
        recalculateSizes();
    }
}


