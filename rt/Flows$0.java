package base;

import java.util.stream.Stream;

public interface Flows$0 extends Sealed$0{
  default Object imm$$hash$0(){ return new Flow$1Instance(Stream.empty()); }
  default Object imm$$hash$1(Object p0){ return new Flow$1Instance(Stream.of(p0)); }
  default Object imm$$hash$2(Object p0,Object p1){ return new Flow$1Instance(Stream.of(p0,p1)); }
  default Object imm$$hash$3(Object p0,Object p1,Object p2){ return new Flow$1Instance(Stream.of(p0,p1,p2)); }
  default Object imm$$hash$4(Object p0,Object p1,Object p2,Object p3){ return new Flow$1Instance(Stream.of(p0,p1,p2,p3)); }
  default Object imm$$hash$5(Object p0,Object p1,Object p2,Object p3, Object p4){ return new Flow$1Instance(Stream.of(p0,p1,p2,p3,p4)); }
  default Object imm$$hash$6(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5){ return new Flow$1Instance(Stream.of(p0,p1,p2,p3,p4,p5)); }
  default Object imm$$hash$7(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6){ return new Flow$1Instance(Stream.of(p0,p1,p2,p3,p4,p5,p6)); }
  default Object imm$$hash$8(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7){ return new Flow$1Instance(Stream.of(p0,p1,p2,p3,p4,p5,p6,p7)); }
  default Object imm$$hash$9(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8){ return new Flow$1Instance(Stream.of(p0,p1,p2,p3,p4,p5,p6,p7,p8)); }
  default Object imm$$hash$10(Object p0,Object p1,Object p2,Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9){ return new Flow$1Instance(Stream.of(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9)); }
  default Object imm$fromMutList$1(Object p0){ return new Flow$1Instance(List$1Instance.asJava(p0).stream()); }//sequential
  default Object imm$fromMutList$2(Object p0,Object p1){ return new Flow$1Instance(List$1Instance.asJava(p0).stream()); }//maybeparallel
  default Object imm$fromReadList$1(Object p0){ return new Flow$1Instance(List$1Instance.asJava(p0).stream()); }//maybeparallel
  default Object imm$fromImmList$1(Object p0){ return new Flow$1Instance(List$1Instance.asJava(p0).stream()); }//maybeparallel

  Flows$0 instance= new Flows$0(){};
}
