package base;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import static base.Util.*;

public interface Maps$o$0 extends Sealed$2o$0{
  default Object imm$$hash$1(Object p0){ // (oh)
    var k= toKey(p0);
    return new Map$c$2Instance(k,new LinkedHashMap<>());
  }
  default Object imm$$hash$3(Object p0,Object p1,Object p2){ // (oh,k,e)
    var m= new LinkedHashMap<MapKey,Object>(2);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    return new Map$c$2Instance(k,m);
  }
  default Object imm$$hash$5(Object p0,Object p1,Object p2,Object p3,Object p4){ // (oh,k,e,k,e)
    var m= new LinkedHashMap<MapKey,Object>(2);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    return new Map$c$2Instance(k,m);
  }
  default Object imm$$hash$7(Object p0,Object p1,Object p2,Object p3,Object p4,Object p5,Object p6){ // 3 pairs
    var m= new LinkedHashMap<MapKey,Object>(3);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    m.put(mapKey(k,p5),p6);
    return new Map$c$2Instance(k,m);
  }
  default Object imm$$hash$9(Object p0,Object p1,Object p2,Object p3,Object p4,Object p5,Object p6,Object p7,Object p8){ // 4 pairs
    var m= new LinkedHashMap<MapKey,Object>(4);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    m.put(mapKey(k,p5),p6);
    m.put(mapKey(k,p7),p8);
    return new Map$c$2Instance(k,m);
  }
  default Object imm$$hash$11(Object p0,Object p1,Object p2,Object p3,Object p4,Object p5,Object p6,Object p7,Object p8,Object p9,Object p10){ // 5 pairs
    var m= new LinkedHashMap<MapKey,Object>(5);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    m.put(mapKey(k,p5),p6);
    m.put(mapKey(k,p7),p8);
    m.put(mapKey(k,p9),p10);
    return new Map$c$2Instance(k,m);
  }
  default Object imm$$hash$13(Object p0,Object p1,Object p2,Object p3,Object p4,Object p5,Object p6,Object p7,Object p8,Object p9,Object p10,Object p11,Object p12){ // 6 pairs
    var m= new LinkedHashMap<MapKey,Object>(6);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    m.put(mapKey(k,p5),p6);
    m.put(mapKey(k,p7),p8);
    m.put(mapKey(k,p9),p10);
    m.put(mapKey(k,p11),p12);
    return new Map$c$2Instance(k,m);
  }
  default Object imm$$hash$15(Object p0,Object p1,Object p2,Object p3,Object p4,Object p5,Object p6,Object p7,Object p8,Object p9,Object p10,Object p11,Object p12,Object p13,Object p14){ // 7 pairs
    var m= new LinkedHashMap<MapKey,Object>(7);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    m.put(mapKey(k,p5),p6);
    m.put(mapKey(k,p7),p8);
    m.put(mapKey(k,p9),p10);
    m.put(mapKey(k,p11),p12);
    m.put(mapKey(k,p13),p14);
    return new Map$c$2Instance(k,m);
  }
  default Object imm$$hash$17(Object p0,Object p1,Object p2,Object p3,Object p4,Object p5,Object p6,Object p7,Object p8,Object p9,Object p10,Object p11,Object p12,Object p13,Object p14,Object p15,Object p16){ // 8 pairs
    var m= new LinkedHashMap<MapKey,Object>(8);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    m.put(mapKey(k,p5),p6);
    m.put(mapKey(k,p7),p8);
    m.put(mapKey(k,p9),p10);
    m.put(mapKey(k,p11),p12);
    m.put(mapKey(k,p13),p14);
    m.put(mapKey(k,p15),p16);
    return new Map$c$2Instance(k,m);
  }
  default Object imm$$hash$19(Object p0,Object p1,Object p2,Object p3,Object p4,Object p5,Object p6,Object p7,Object p8,Object p9,Object p10,Object p11,Object p12,Object p13,Object p14,Object p15,Object p16,Object p17,Object p18){ // 9 pairs
    var m= new LinkedHashMap<MapKey,Object>(9);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    m.put(mapKey(k,p5),p6);
    m.put(mapKey(k,p7),p8);
    m.put(mapKey(k,p9),p10);
    m.put(mapKey(k,p11),p12);
    m.put(mapKey(k,p13),p14);
    m.put(mapKey(k,p15),p16);
    m.put(mapKey(k,p17),p18);
    return new Map$c$2Instance(k,m);
  }
  default Object imm$$hash$21(Object p0,Object p1,Object p2,Object p3,Object p4,Object p5,Object p6,Object p7,Object p8,Object p9,Object p10,Object p11,Object p12,Object p13,Object p14,Object p15,Object p16,Object p17,Object p18,Object p19,Object p20){ // 10 pairs
    var m= new LinkedHashMap<MapKey,Object>(10);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    m.put(mapKey(k,p5),p6);
    m.put(mapKey(k,p7),p8);
    m.put(mapKey(k,p9),p10);
    m.put(mapKey(k,p11),p12);
    m.put(mapKey(k,p13),p14);
    m.put(mapKey(k,p15),p16);
    m.put(mapKey(k,p17),p18);
    m.put(mapKey(k,p19),p20);
    return new Map$c$2Instance(k,m);
  }
  default Object imm$$hash$23(Object p0,Object p1,Object p2,Object p3,Object p4,Object p5,Object p6,Object p7,Object p8,Object p9,Object p10,Object p11,Object p12,Object p13,Object p14,Object p15,Object p16,Object p17,Object p18,Object p19,Object p20,Object p21,Object p22){ // 11 pairs
    var m= new LinkedHashMap<MapKey,Object>(11);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    m.put(mapKey(k,p5),p6);
    m.put(mapKey(k,p7),p8);
    m.put(mapKey(k,p9),p10);
    m.put(mapKey(k,p11),p12);
    m.put(mapKey(k,p13),p14);
    m.put(mapKey(k,p15),p16);
    m.put(mapKey(k,p17),p18);
    m.put(mapKey(k,p19),p20);
    m.put(mapKey(k,p21),p22);
    return new Map$c$2Instance(k,m);
  }
  default Object imm$$hash$25(Object p0,Object p1,Object p2,Object p3,Object p4,Object p5,Object p6,Object p7,Object p8,Object p9,Object p10,Object p11,Object p12,Object p13,Object p14,Object p15,Object p16,Object p17,Object p18,Object p19,Object p20,Object p21,Object p22,Object p23,Object p24){ // 12 pairs
    var m= new LinkedHashMap<MapKey,Object>(12);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    m.put(mapKey(k,p5),p6);
    m.put(mapKey(k,p7),p8);
    m.put(mapKey(k,p9),p10);
    m.put(mapKey(k,p11),p12);
    m.put(mapKey(k,p13),p14);
    m.put(mapKey(k,p15),p16);
    m.put(mapKey(k,p17),p18);
    m.put(mapKey(k,p19),p20);
    m.put(mapKey(k,p21),p22);
    m.put(mapKey(k,p23),p24);
    return new Map$c$2Instance(k,m);
  }
  static OrderHashBy$2ea$1 toKey(Object k){return (OrderHashBy$2ea$1) ((OrderHashBy$2ea$2)k).imm$hideKey$0(); }
  default Object read$singletonRead$3(Object p0,Object p1,Object p2){ return imm$$hash$3(p0,p1,p2); }

  Maps$o$0 instance= new Maps$o$0(){};
}
