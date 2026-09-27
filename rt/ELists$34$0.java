package _base;

import java.util.*;
import _base.EList$1k$1Instance;

import static _base.SparseSegmentTreeImpl.lessThan;
import static _base.Util.check;

public interface ELists$34$0 extends Sealed$2o$0 {
  default Object imm$withCapacity$1(Object p0) {
    long capacity = _base.Nat$c$0Instance.unwrap(p0);
    check(
      lessThan(capacity, Integer.MAX_VALUE),
      "ELists.withCapacity: Cannot create a list with a capacity > "+Integer.MAX_VALUE
    );
    return EList$1k$1Instance.unsafeWrap(new ArrayList<>((int) capacity));
  }
  default Object imm$$hash$0(){ return new EList$1k$1Instance(); }
  default Object imm$$hash$1(Object p0){
    ArrayList<Object> list = new ArrayList<>(1);
    list.add(p0);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$2(Object p0, Object p1){
    ArrayList<Object> list = new ArrayList<>(2);
    list.add(p0); list.add(p1);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    ArrayList<Object> list = new ArrayList<>(3);
    list.add(p0); list.add(p1); list.add(p2);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$4(Object p0, Object p1, Object p2, Object p3){
    ArrayList<Object> list = new ArrayList<>(4);
    list.add(p0); list.add(p1); list.add(p2); list.add(p3);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$5(Object p0, Object p1, Object p2, Object p3, Object p4){
    ArrayList<Object> list = new ArrayList<>(5);
    list.add(p0); list.add(p1); list.add(p2); list.add(p3); list.add(p4);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$6(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5){
    ArrayList<Object> list = new ArrayList<>(6);
    list.add(p0); list.add(p1); list.add(p2); list.add(p3); list.add(p4); list.add(p5);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$7(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6){
    ArrayList<Object> list = new ArrayList<>(7);
    list.add(p0); list.add(p1); list.add(p2); list.add(p3); list.add(p4); list.add(p5); list.add(p6);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$8(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7){
    ArrayList<Object> list = new ArrayList<>(8);
    list.add(p0); list.add(p1); list.add(p2); list.add(p3); list.add(p4); list.add(p5); list.add(p6); list.add(p7);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$9(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8){
    ArrayList<Object> list = new ArrayList<>(9);
    list.add(p0); list.add(p1); list.add(p2); list.add(p3); list.add(p4); list.add(p5); list.add(p6); list.add(p7); list.add(p8);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$10(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9){
    ArrayList<Object> list = new ArrayList<>(10);
    list.add(p0); list.add(p1); list.add(p2); list.add(p3); list.add(p4); list.add(p5); list.add(p6); list.add(p7); list.add(p8); list.add(p9);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$11(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10){
    ArrayList<Object> list = new ArrayList<>(11);
    list.add(p0); list.add(p1); list.add(p2); list.add(p3); list.add(p4); list.add(p5); list.add(p6); list.add(p7); list.add(p8); list.add(p9); list.add(p10);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$12(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11){
    ArrayList<Object> list = new ArrayList<>(12);
    list.add(p0); list.add(p1); list.add(p2); list.add(p3); list.add(p4); list.add(p5); list.add(p6); list.add(p7); list.add(p8); list.add(p9); list.add(p10); list.add(p11);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$13(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12){
    ArrayList<Object> list = new ArrayList<>(13);
    list.add(p0); list.add(p1); list.add(p2); list.add(p3); list.add(p4); list.add(p5); list.add(p6); list.add(p7); list.add(p8); list.add(p9); list.add(p10); list.add(p11); list.add(p12);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$14(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13){
    ArrayList<Object> list = new ArrayList<>(14);
    list.add(p0); list.add(p1); list.add(p2); list.add(p3); list.add(p4); list.add(p5); list.add(p6); list.add(p7); list.add(p8); list.add(p9); list.add(p10); list.add(p11); list.add(p12); list.add(p13);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$15(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14){
    ArrayList<Object> list = new ArrayList<>(15);
    list.add(p0); list.add(p1); list.add(p2); list.add(p3); list.add(p4); list.add(p5); list.add(p6); list.add(p7); list.add(p8); list.add(p9); list.add(p10); list.add(p11); list.add(p12); list.add(p13); list.add(p14);
    return EList$1k$1Instance.unsafeWrap(list);
  }
  default Object imm$$hash$16(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15){
    ArrayList<Object> list = new ArrayList<>(16);
    list.add(p0); list.add(p1); list.add(p2); list.add(p3); list.add(p4); list.add(p5); list.add(p6); list.add(p7); list.add(p8); list.add(p9); list.add(p10); list.add(p11); list.add(p12); list.add(p13); list.add(p14); list.add(p15);
    return EList$1k$1Instance.unsafeWrap(list);
  }

  ELists$34$0 instance= new ELists$34$0(){};
}
