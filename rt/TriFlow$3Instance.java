package base;


import base.multiflowUtils.Folder;
import base.multiflowUtils.SizedLane;
import base.multiflowUtils.Zipper;

import static base.Util.*;

public class TriFlow$3Instance extends GeneralMultiFlow implements TriFlow$3 {
    SizedLane<Object> laneA;
    SizedLane<Object> laneB;
    SizedLane<Object> laneC;

    TriFlow$3Instance(SizedLane<Object> listA, SizedLane<Object> listB, SizedLane<Object> listC) {
        super(listA, listB, listC);
        this.laneA = this.lanes.getFirst();
        this.laneB = this.lanes.get(1);
        this.laneC = this.lanes.get(2);
    }


    @Override public Object mut$with$1(Object p0) {
        return new QuadFlow$4Instance(laneA, laneB, laneC, SizedLane.of(sizedFlow(p0)));
    }

    @Override public Object mut$limitDefensive$1(Object p0) {
        this.limit(natToLong(p0));
        return this;
    }

    @Override public Object mut$zipExact$1(Object p0) {
        this.lanesSameSizeForOp("zip");
        return Zipper.zip3(
                laneA, laneB, laneC,
                (a, b, c) -> callF$4(p0, a, b, c)
        );
    }

    @Override public Object mut$zipOpts$1(Object p0) {
        this.toOpts();
        return this.mut$zipExact$1(p0);
    }

    @Override public Object mut$foldExact$2(Object p0, Object p1) {
        this.lanesSameSizeForOp("fold");
        return Folder.fold3(
                laneA,laneB,laneC, callF$1(p0),
                (acc, a, b, c) -> callMultiAcc$4(p1, acc, a,b,c)
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

    @Override public Object mut$fillC$1(Object p0) {
        this.fill(laneC, i -> callF$2(p0, Nat$0Instance.instance(i)));
        return this;
    }
}
