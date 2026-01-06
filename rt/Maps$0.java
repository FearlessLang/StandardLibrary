package base;

import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import static base.Util.*;

public interface Maps$0 extends Sealed$0{
  default Object imm$$hash$1(Object p0){ // (oh)
    var k= toKey(p0);
    return new Map$2Instance(k,new LinkedHashMap<>());
  }
  default Object imm$$hash$3(Object p0,Object p1,Object p2){ // (oh,k,e)
    var m= new LinkedHashMap<MapKey,Object>(2);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    return new Map$2Instance(k,m);
  }
  default Object imm$$hash$5(Object p0,Object p1,Object p2,Object p3,Object p4){ // (oh,k,e,k,e)
    var m= new LinkedHashMap<MapKey,Object>(4);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    return new Map$2Instance(k,m);
  }
  default Object imm$$hash$7(Object p0,Object p1,Object p2,Object p3,Object p4,Object p5,Object p6){ // 3 pairs
    var m= new LinkedHashMap<MapKey,Object>(6);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    m.put(mapKey(k,p5),p6);
    return new Map$2Instance(k,m);
  }
  default Object imm$$hash$9(Object p0,Object p1,Object p2,Object p3,Object p4,Object p5,Object p6,Object p7,Object p8){ // 4 pairs
    var m= new LinkedHashMap<MapKey,Object>(8);
    var k= toKey(p0);
    m.put(mapKey(k,p1),p2);
    m.put(mapKey(k,p3),p4);
    m.put(mapKey(k,p5),p6);
    m.put(mapKey(k,p7),p8);
    return new Map$2Instance(k,m);
  }
  static OrderHashBy$1 toKey(Object k){return (OrderHashBy$1) ((OrderHashBy$2)k).imm$hideKey$0(); }
  default Object read$singletonRead$3(Object p0,Object p1,Object p2){ return imm$$hash$3(p0,p1,p2); }
  default Object imm$singletonImm$3(Object p0,Object p1,Object p2){ return imm$$hash$3(p0,p1,p2); }

  Maps$0 instance= new Maps$0(){};
}

record Map$2Instance(OrderHashBy$1 keyOh, LinkedHashMap<MapKey,Object> elems) implements Map$2{

  @Override public Object read$size$0(){ return intToNat(elems.size()); }
  @Override public Object read$isEmpty$0(){ return bool(elems.isEmpty()); }
  @Override public Object read$keyOh$0(){ return keyOh; }

  @Override public Object read$str$1(Object p0){
    var byE= (ToStrBy$1)p0;
    var byK= (ToStrBy$1)keyOh;
    String res= elems.entrySet().stream().map(e->
      ((Str$0Instance)((ToStr$0)byK.imm$$hash$1(e.getKey().key)).read$str$0()).val()
      +": "+((Str$0Instance)((ToStr$0)byE.imm$$hash$1(e.getValue())).read$str$0()).val()
      ).collect(Collectors.joining(", ","{","}"));
    return Str$0Instance.instance(res);
  }
  @Override public Object mut$get$1(Object p0){
    var mk= mapKey(keyOh,p0);
    var e= elems.get(mk);
    if (e == null){ throw err("Map key absent"); }
    return e;
  }
  @Override public Object read$get$1(Object p0){ return mut$get$1(p0); }
  @Override public Object imm$get$1(Object p0){ return mut$get$1(p0); }

  @Override public Object mut$opt$1(Object p0){
    var mk= mapKey(keyOh,p0);
    var e= elems.get(mk);
    return e == null ? optEmpty() : optSome(e);
  }
  @Override public Object read$opt$1(Object p0){ return mut$opt$1(p0); }
  @Override public Object imm$opt$1(Object p0){ return mut$opt$1(p0); }

  @Override public Object read$containsKey$1(Object p0){
    var mk= mapKey(keyOh,p0);
    return bool(elems.get(mk) != null);
  }

  @Override public Object mut$with$2(Object p0,Object p1){
    var m= new LinkedHashMap<MapKey,Object>(elems);
    m.put(mapKey(keyOh,p0),p1); // preserves existing representative MapKey if equal
    return new Map$2Instance(keyOh,m);
  }
  @Override public Object read$with$2(Object p0,Object p1){ return mut$with$2(p0,p1); }
  @Override public Object imm$with$2(Object p0,Object p1){ return mut$with$2(p0,p1); }

  @Override public Object mut$without$1(Object p0){
    var mk= mapKey(keyOh,p0);
    var m= new LinkedHashMap<MapKey,Object>(elems);
    if (m.remove(mk) == null){ throw err("Map key absent"); }
    return new Map$2Instance(keyOh,m);
  }
  @Override public Object read$without$1(Object p0){ return mut$without$1(p0); }
  @Override public Object imm$without$1(Object p0){ return mut$without$1(p0); }

  @Override public Object mut$$plus_plus$1(Object p0){
    var other= (Map$2Instance)p0;
    if (other.elems.isEmpty()){ return this; }
    var m= new LinkedHashMap<MapKey,Object>(elems);
    for (var e: other.elems.entrySet()){
      var k= e.getKey().key;
      var mk= mapKey(keyOh,k); // reinterpret under LEFT ordering
      m.putIfAbsent(mk, e.getValue());
    }
    return new Map$2Instance(keyOh,m);
  }
  @Override public Object read$$plus_plus$1(Object p0){ return mut$$plus_plus$1(p0); }
  @Override public Object imm$$plus_plus$1(Object p0){ return mut$$plus_plus$1(p0); }

  @Override public Object mut$as$1(Object p0){
    var m= new LinkedHashMap<MapKey,Object>(elems.size()*2);
    for (var e: elems.entrySet()){ m.put(e.getKey(),callMF$2(p0,e.getValue())); }
    return new Map$2Instance(keyOh,m);
  }
  @Override public Object read$as$1(Object p0){ return mut$as$1(p0); }
  @Override public Object imm$as$1(Object p0){ return mut$as$1(p0); }
  
  @Override public Object mut$flow$0(){
    return new Flow$1Instance(elems.entrySet().stream()
      .map(e->(Object)KeyElems$0.instance.imm$$hash$2(e.getKey().key,e.getValue())));
  }
  @Override public Object read$flow$0(){ return mut$flow$0(); }
  @Override public Object imm$flow$0(){ return mut$flow$0(); }

  @Override public Object read$keys$0(){ return new Flow$1Instance(elems.keySet().stream().map(k->k.key)); }
  @Override public Object read$elems$0(){ return new Flow$1Instance(elems.values().stream()); }

  @Override public Object read$close$0(){ return this; }
  @Override public Object read$close$1(Object p0){ return p0; }
  @Override public Object read$imm$1(Object p0){
    var by= (ToImmBy$2)p0;
    var m=new LinkedHashMap<MapKey,Object>();
    elems.entrySet().stream().forEach(e->m.put(e.getKey(), ((ToImm$1)by.imm$$hash$1(e.getValue())).read$imm$0()));
    return new Map$2Instance(keyOh,m);
  }
}
