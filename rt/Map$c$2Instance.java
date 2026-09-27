package _base;


import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import static _base.Util.*;

public record Map$c$2Instance(OrderHashBy$2ea$1 keyOh, LinkedHashMap<MapKey,Object> elems) implements Map$c$2{

  @Override public Object read$size$0(){ return Nat$c$0Instance.instance(elems.size()); }
  @Override public Object read$isEmpty$0(){ return bool(elems.isEmpty()); }
  @Override public Object read$keyOh$0(){ return keyOh; }

  @Override public Object read$str$1(Object p0){
    var byE= (ToStrBy$5u$1)p0;
    var byK= (ToStrBy$5u$1)keyOh;
    String res= elems.entrySet().stream().map(e->
      toS(byK.imm$$hash$1(e.getKey().key))
      +": "+toS(byE.imm$$hash$1(e.getValue()))
      ).collect(Collectors.joining(", ","{","}"));
    return Str$c$0Instance.instance(res);
  }
  @Override public Object mut$get$1(Object p0){
    var mk= mapKey(keyOh,p0);
    var e= elems.get(mk);
    if (e == null){ throw err(absent(p0)); }
    return e;
  }
  @Override public Object mut$tryGet$1(Object p0){
    var mk= mapKey(keyOh,p0);
    var e= elems.get(mk);
    if (e == null){ return fail(absent(p0)); }
    return ok(e);
  }
  private String absent(Object p0){ return "Map.get: Tried to get key "+toStringBy((ToStrBy$5u$1)keyOh, p0)+" that is not contained in this map.\n Consider using `Map.opt` to properly handle the failure case."; }
  @Override public Object read$get$1(Object p0){ return mut$get$1(p0); }
  @Override public Object read$tryGet$1(Object p0){ return mut$tryGet$1(p0); }

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
    return new Map$c$2Instance(keyOh,m);
  }
  @Override public Object read$with$2(Object p0,Object p1){ return mut$with$2(p0,p1); }
  @Override public Object read$hash$1(Object p0){
    var byE= (_base.OrderHashBy$2ea$2)p0;
    long h= 0;
    for(var e: elems.entrySet()){
      long kh= e.getKey().hashCode();
      long vh= natToLong(((_base.OrderHash$lk$1)byE.imm$$hash$1(e.getValue())).read$hash$0());
      h += kh ^ vh;
    }
    return new Nat$c$0Instance(h);
  }
  @Override public Object read$cmp$4(Object p0,Object p1,Object p2,Object m){
    var byE= (OrderHashBy$2ea$2)p0;
    var a= (Map$c$2Instance)p1;
    var b= (Map$c$2Instance)p2;
    int c= a.elems.size() - b.elems.size();
    if (c != 0){ return ord(c,m); }
    var ia= a.elems.entrySet().iterator();
    var ib= b.elems.entrySet().iterator();
    while(ia.hasNext()){
      var ea= ia.next();
      var eb= ib.next();
      c= cmp(a.keyOh, ea.getKey().key, eb.getKey().key);
      if (c != 0){ return ord(c,m); }
      c= cmp(byE, ea.getValue(), eb.getValue());
      if (c != 0){ return ord(c,m); }
    }
    return ((OrderMatch$174$1)m).mut$eq$0();
  }
  @Override public Object mut$without$1(Object p0){
    var mk= mapKey(keyOh,p0);
    var m= new LinkedHashMap<MapKey,Object>(elems);
    if (m.remove(mk) == null){ throw err("Map key absent"); }
    return new Map$c$2Instance(keyOh,m);
  }
  @Override public Object read$without$1(Object p0){ return mut$without$1(p0); }

  @Override public Object mut$$plus_plus$1(Object p0){
    var other= (Map$c$2Instance)p0;
    if (other.elems.isEmpty()){ return this; }
    var m= new LinkedHashMap<MapKey,Object>(elems);
    for (var e: other.elems.entrySet()){
      var k= e.getKey().key;
      var mk= mapKey(keyOh,k); // reinterpret under LEFT ordering
      m.putIfAbsent(mk, e.getValue());
    }
    return new Map$c$2Instance(keyOh,m);
  }
  @Override public Object read$$plus_plus$1(Object p0){ return mut$$plus_plus$1(p0); }

  @Override public Object read$as$1(Object p0){ return this; }

  @Override public Object mut$flow$0(){
    return Flow$o$1Instance.of(elems.entrySet().stream()
      .map(e->(Object)KeyElems$b4$0.instance.imm$$hash$2(e.getKey().key,e.getValue())));
  }
  @Override public Object read$flow$0(){ return mut$flow$0(); }
  @Override public Object imm$flow$0(){ return mut$flow$0(); }

  @Override public Object mut$flow$1(Object p0){
    return Flow$o$1Instance.of(elems.entrySet().stream()
      .map(e-> callF$3(p0,e.getKey().key,e.getValue())));
  }
  @Override public Object read$flow$1(Object p0){ return mut$flow$1(p0); }
  @Override public Object imm$flow$1(Object p0){ return mut$flow$1(p0); }


  @Override public Object read$keys$0(){ return Flow$o$1Instance.of(elems.keySet().stream().map(k->k.key)); }
  @Override public Object read$elems$0(){ return Flow$o$1Instance.of(elems.values().stream()); }

  @Override public Object read$close$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object read$close$1(Object p0){ return p0; }
  @Override public Object read$imm$1(Object p0){
    var by= (ToImmBy$5u$2)p0;
    var m=new LinkedHashMap<MapKey,Object>();
    elems.forEach((k, v) -> m.put(k, ((ToImm$1g$1)by.imm$$hash$1(v)).read$imm$0()));
    return new Map$c$2Instance(keyOh,m);
  }
}