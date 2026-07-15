package base;

import base.multiflowUtils.SizedLane;

import java.util.stream.Stream;

import static base.Util.natToLong;

public class SizedFlow$1Instance extends Flow$1Instance implements SizedFlow$1 {
    public long size;
    public SizedFlow$1Instance(Stream<Object> stream, long currentSize) {
        super(stream);
        this.size = currentSize;
    }

    @Override public Object read$size$0() {
        return Nat$0Instance.instance(size);
    }

    @Override public Object mut$with$1(Object p0) {
        var other = (SizedFlow$1Instance) p0;
        return new BiFlow$2Instance(SizedLane.of(this.s, (int) size), SizedLane.of(other.s, (int) other.size));
    }

    @Override public Object mut$withBoth$1(Object p0) {
        var other = (BiFlow$2Instance) p0;
        return new TriFlow$3Instance(SizedLane.of(this.s, (int) size), other.laneA, other.laneB);
    }

    @Override public Object mut$withAll$1(Object p0) {
        var other = (TriFlow$3Instance) p0;
        return new QuadFlow$4Instance(SizedLane.of(this.s, (int) size), other.laneA, other.laneB, other.laneC);
    }

    @Override public Object mut$map$1(Object p0) {
        try{
            var r = (Flow$1Instance) super.mut$map$1(p0);
            return new SizedFlow$1Instance(r.s, size);
        } catch(IllegalStateException e){ throw consumed(); }
    }

    @Override public Object mut$sizedConcat$1(Object p0) {
        try{
            var other = (SizedFlow$1Instance)p0;
            return new SizedFlow$1Instance(Stream.concat(this.s, other.s), size + other.size);
        }
        catch(IllegalStateException e){ throw consumed(); }
    }

    @Override public Object mut$limitDefensive$1(Object p0) {
        long limit = natToLong(p0);
        if (limit >= size) { return this; }
        try{ return new SizedFlow$1Instance(this.s.limit(limit), limit); }
        catch(IllegalStateException e){ throw consumed(); }
    }
}
