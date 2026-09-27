package _base;

import java.util.stream.Stream;


public interface Flows$1c$0 extends Sealed$2o$0{
  default Object imm$$hash$0(){ return Flow$o$1Instance.of(Stream.empty()); }
  default Object imm$$hash$1(Object p0){ return Flow$o$1Instance.of(p0); }
  default Object imm$$hash$2(Object p0,Object p1){ return Flow$o$1Instance.of(p0,p1); }
  default Object imm$$hash$3(Object p0,Object p1,Object p2){ return Flow$o$1Instance.of(p0,p1,p2); }
  default Object imm$$hash$4(Object p0,Object p1,Object p2,Object p3){ return Flow$o$1Instance.of(p0,p1,p2,p3); }
  default Object imm$$hash$5(Object p0,Object p1,Object p2,Object p3, Object p4){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4); }
  default Object imm$$hash$6(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5); }
  default Object imm$$hash$7(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6); }
  default Object imm$$hash$8(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7); }
  default Object imm$$hash$9(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8); }
  default Object imm$$hash$10(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9); }
  default Object imm$$hash$11(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10); }
  default Object imm$$hash$12(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11); }
  default Object imm$$hash$13(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12); }
  default Object imm$$hash$14(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13); }
  default Object imm$$hash$15(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14); }
  default Object imm$$hash$16(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15){ return Flow$o$1Instance.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15); }

  default Object imm$fromMutList$1(Object p0){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0).stream()); }//sequential
  default Object imm$fromMutList$2(Object p0,Object p1){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0).stream()); }
  default Object imm$fromReadList$1(Object p0){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0).stream()); }
  default Object imm$fromImmList$1(Object p0){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0).stream()); }


  default Object imm$seqFromMutList$1(Object p0){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0).stream()); }//sequential
  default Object imm$seqFromReadList$1(Object p0){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0).stream()); }//parallel!
  default Object imm$seqFromImmList$1(Object p0){ return Flow$o$1Instance.of(List$o$1Instance.asJava(p0).stream()); }//parallel!


  Flows$1c$0 instance= new Flows$1c$0(){};
}
