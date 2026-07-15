package base;

import base.multiflowUtils.Folder;
import base.multiflowUtils.SizedLane;
import base.multiflowUtils.Zipper;

import static base.Util.*;

public class QuadFlow$4Instance extends GeneralMultiFlow implements QuadFlow$4 {
    SizedLane<Object> laneA;
    SizedLane<Object> laneB;
    SizedLane<Object> laneC;
    SizedLane<Object> laneD;

    QuadFlow$4Instance(SizedLane<Object> listA, SizedLane<Object> listB, SizedLane<Object> listC, SizedLane<Object> listD) {
        super(listA, listB, listC, listD);
        this.laneA = this.lanes.getFirst();
        this.laneB = this.lanes.get(1);
        this.laneC = this.lanes.get(2);
        this.laneD = this.lanes.get(3);
    }

    @Override public Object mut$limitDefensive$1(Object p0) {
        this.limit(natToLong(p0));
        return this;
    }

    @Override public Object mut$zipExact$1(Object p0) {
        this.lanesSameSizeForOp("zip");
        return Zipper.zip4(
                laneA, laneB, laneC, laneD,
                (a, b, c,d) -> callF$5(p0, a, b, c, d)
        );
    }

    @Override public Object mut$zipOpts$1(Object p0) {
        this.toOpts();
        return this.mut$zipExact$1(p0);
    }

    @Override public Object mut$foldExact$2(Object p0, Object p1) {
        this.lanesSameSizeForOp("fold");
        return Folder.fold4(
                laneA,laneB,laneC,laneD, callF$1(p0),
                (acc, a, b, c, d) -> callMultiAcc$5(p1, acc, a,b,c,d)
        );
    }

    @Override public Object mut$foldOpts$2(Object p0, Object p1) {
        this.toOpts();
        return this.mut$foldExact$2(p0, p1);
    }


    @Override public Object mut$fillA$1(Object p0) {
        fill(laneA, i -> callF$2(p0, Nat$0Instance.instance(i)));
        return this;
    }

    @Override public Object mut$fillB$1(Object p0) {
        fill(laneB, i -> callF$2(p0, Nat$0Instance.instance(i)));
        return this;
    }

    @Override public Object mut$fillC$1(Object p0) {
        fill(laneC, i -> callF$2(p0, Nat$0Instance.instance(i)));
        return this;
    }

    @Override public Object mut$fillD$1(Object p0) {
        fill(laneD, i -> callF$2(p0, Nat$0Instance.instance(i)));
        return this;
    }
}
