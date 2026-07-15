package base;
import base.multiflowUtils.Folder;
import base.multiflowUtils.SizedLane;
import base.multiflowUtils.Zipper;

import static base.Util.*;

public class BiFlow$2Instance extends GeneralMultiFlow implements BiFlow$2 {
    SizedLane<Object> laneA;
    SizedLane<Object> laneB;


    /// Copies the lanes
    BiFlow$2Instance(SizedLane<Object> laneA, SizedLane<Object> laneB) {
        super(laneA, laneB);
        this.laneA = this.lanes.getFirst();
        this.laneB = this.lanes.get(1);
    }

    @Override public Object mut$with$1(Object p0){
        var s = sizedFlow(p0);
        return new TriFlow$3Instance(laneA, laneB, SizedLane.<Object>of(s.s(), s.size));
    }

    @Override public Object mut$withBoth$1(Object p0){
        BiFlow$2Instance mf2 = biFlow(p0);
        return new QuadFlow$4Instance(laneA, laneB, mf2.laneA, mf2.laneB);
    }

    @Override public Object mut$limitDefensive$1(Object p0) {
        this.limit(natToLong(p0));
        return this;
    }

    @Override public Object mut$zipExact$1(Object p0) {
        this.lanesSameSizeForOp("zip");
        return Zipper.zip2(
                laneA, laneB,
                (a, b) -> callF$3(p0, a, b)
        );
    }

    @Override public Object mut$zipOpts$1(Object p0) {
        this.toOpts();
        return this.mut$zipExact$1(p0);
    }

    @Override public Object mut$foldExact$2(Object p0, Object p1) {
        this.lanesSameSizeForOp("fold");
        return Folder.fold2(
                laneA,laneB, callMF$1(p0),
                (acc, a, b) -> callMultiAcc$3(p1, acc, a,b)
        );
    }

    @Override public Object mut$foldOpts$2(Object p0, Object p1) {
        this.toOpts();
        return this.mut$foldExact$2(p0, p1);
    }


    @Override public Object mut$fillA$1(Object p0) {
        this.fill(laneA, i -> callF$2(p0, Nat$0Instance.instance(i)));
        return this;
    }

    @Override public Object mut$fillB$1(Object p0) {
        this.fill(laneB, i -> callF$2(p0, Nat$0Instance.instance(i)));
        return this;
    }
}
