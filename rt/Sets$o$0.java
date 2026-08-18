package base;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public interface Sets$o$0 extends Sealed$2o$0 {
  ArrayList<Object> emptyList = new ArrayList<>(0);
  default Object imm$$hash$1(Object p0){
    var ordering = Set$c$1Instance.ordering(p0);
    return new Set$c$1Instance(ordering, emptyList);
  }
  default Object imm$$hash$2(Object p0, Object p1){
    var ordering = Set$c$1Instance.ordering(p0);
    return Set$c$1Instance.fromSortedList(ordering, List.of(p1));
  }
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(2);
    s.add(p1); s.add(p2);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$4(Object p0, Object p1, Object p2, Object p3){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(3);
    s.add(p1); s.add(p2); s.add(p3);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$5(Object p0, Object p1, Object p2, Object p3, Object p4){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(4);
    s.add(p1); s.add(p2); s.add(p3); s.add(p4);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$6(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(5);
    s.add(p1); s.add(p2); s.add(p3); s.add(p4); s.add(p5);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$7(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(6);
    s.add(p1); s.add(p2); s.add(p3); s.add(p4); s.add(p5); s.add(p6);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$8(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(7);
    s.add(p1); s.add(p2); s.add(p3); s.add(p4); s.add(p5); s.add(p6); s.add(p7);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$9(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(8);
    s.add(p1); s.add(p2); s.add(p3); s.add(p4); s.add(p5); s.add(p6);s.add(p7); s.add(p8);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$10(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(9);
    s.add(p1); s.add(p2); s.add(p3); s.add(p4); s.add(p5); s.add(p6); s.add(p7); s.add(p8); s.add(p9);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$11(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(10);
    s.add(p1); s.add(p2); s.add(p3); s.add(p4); s.add(p5); s.add(p6); s.add(p7); s.add(p8); s.add(p9); s.add(p10);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$12(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(11);
    s.add(p1); s.add(p2); s.add(p3); s.add(p4); s.add(p5); s.add(p6); s.add(p7); s.add(p8); s.add(p9); s.add(p10); s.add(p11);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$13(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(12);
    s.add(p1); s.add(p2); s.add(p3); s.add(p4); s.add(p5); s.add(p6); s.add(p7); s.add(p8); s.add(p9); s.add(p10); s.add(p11); s.add(p12);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$14(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(13);
    s.add(p1); s.add(p2); s.add(p3); s.add(p4); s.add(p5); s.add(p6); s.add(p7); s.add(p8); s.add(p9); s.add(p10); s.add(p11); s.add(p12); s.add(p13);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$15(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(14);
    s.add(p1); s.add(p2); s.add(p3); s.add(p4); s.add(p5); s.add(p6); s.add(p7); s.add(p8); s.add(p9); s.add(p10); s.add(p11); s.add(p12); s.add(p13); s.add(p14);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$16(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(15);
    s.add(p1); s.add(p2); s.add(p3); s.add(p4); s.add(p5); s.add(p6); s.add(p7); s.add(p8); s.add(p9); s.add(p10); s.add(p11); s.add(p12); s.add(p13); s.add(p14); s.add(p15);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }
  default Object imm$$hash$17(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15, Object p16){
    var ordering = Set$c$1Instance.ordering(p0);
    var s = new ArrayList<>(15);
    s.add(p1); s.add(p2); s.add(p3); s.add(p4); s.add(p5); s.add(p6); s.add(p7); s.add(p8); s.add(p9); s.add(p10); s.add(p11); s.add(p12); s.add(p13); s.add(p14); s.add(p15); s.add(p16);
    return Set$c$1Instance.fromUnsortedList(ordering, s);
  }

  Sets$o$0 instance= new Sets$o$0(){};
}