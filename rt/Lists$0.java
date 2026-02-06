package base;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static base.Util.*;
public interface Lists$0 extends Sealed$0{
  default Object imm$singletonRead$1(Object p0){ return imm$$hash$1(p0); }
    
  default Object imm$$hash$1(Object p0){
    var l= new ArrayList<Object>(1);
    l.add(p0);
    return new List$1Instance(l);
  }
  default Object imm$$hash$2(Object p0, Object p1){
    var l= new ArrayList<Object>(2);
    l.add(p0); l.add(p1);
    return new List$1Instance(l);
  }
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    var l= new ArrayList<Object>(3);
    l.add(p0); l.add(p1); l.add(p2);
    return new List$1Instance(l);
  }
  default Object imm$$hash$4(Object p0, Object p1, Object p2, Object p3){
    var l= new ArrayList<Object>(4);
    l.add(p0); l.add(p1); l.add(p2); l.add(p3);
    return new List$1Instance(l);
  }
  default Object imm$$hash$5(Object p0, Object p1, Object p2, Object p3, Object p4){
    var l= new ArrayList<Object>(5);
    l.add(p0); l.add(p1); l.add(p2); l.add(p3); l.add(p4);
    return new List$1Instance(l);
  }
  default Object imm$$hash$6(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5){
    var l= new ArrayList<Object>(6);
    l.add(p0); l.add(p1); l.add(p2); l.add(p3); l.add(p4); l.add(p5);
    return new List$1Instance(l);
  }
  default Object imm$$hash$7(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6){
    var l= new ArrayList<Object>(7);
    l.add(p0); l.add(p1); l.add(p2); l.add(p3); l.add(p4); l.add(p5); l.add(p6);
    return new List$1Instance(l);
  }
  default Object imm$$hash$8(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7){
    var l= new ArrayList<Object>(8);
    l.add(p0); l.add(p1); l.add(p2); l.add(p3); l.add(p4); l.add(p5); l.add(p6); l.add(p7);
    return new List$1Instance(l);
  }
  default Object imm$$hash$9(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8){
    var l= new ArrayList<Object>(9);
    l.add(p0); l.add(p1); l.add(p2); l.add(p3); l.add(p4); l.add(p5); l.add(p6); l.add(p7); l.add(p8);
    return new List$1Instance(l);
  }
  default Object imm$$hash$10(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9){
    var l= new ArrayList<Object>(10);
    l.add(p0); l.add(p1); l.add(p2); l.add(p3); l.add(p4); l.add(p5); l.add(p6); l.add(p7); l.add(p8); l.add(p9);
    return new List$1Instance(l);
  }
  default Object imm$$hash$11(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10){
    var l= new ArrayList<Object>(11);
    l.add(p0); l.add(p1); l.add(p2); l.add(p3); l.add(p4); l.add(p5); l.add(p6); l.add(p7); l.add(p8); l.add(p9); l.add(p10);
    return new List$1Instance(l);
  }
  default Object imm$$hash$12(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11){
    var l= new ArrayList<Object>(12);
    l.add(p0); l.add(p1); l.add(p2); l.add(p3); l.add(p4); l.add(p5); l.add(p6); l.add(p7); l.add(p8); l.add(p9); l.add(p10); l.add(p11);
    return new List$1Instance(l);
  }
  default Object imm$$hash$13(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12){
    var l= new ArrayList<Object>(13);
    l.add(p0); l.add(p1); l.add(p2); l.add(p3); l.add(p4); l.add(p5); l.add(p6); l.add(p7); l.add(p8); l.add(p9); l.add(p10); l.add(p11); l.add(p12);
    return new List$1Instance(l);
  }
  default Object imm$$hash$14(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13){
    var l= new ArrayList<Object>(14);
    l.add(p0); l.add(p1); l.add(p2); l.add(p3); l.add(p4); l.add(p5); l.add(p6); l.add(p7); l.add(p8); l.add(p9); l.add(p10); l.add(p11); l.add(p12); l.add(p13);
    return new List$1Instance(l);
  }
  default Object imm$$hash$15(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14){
    var l= new ArrayList<Object>(15);
    l.add(p0); l.add(p1); l.add(p2); l.add(p3); l.add(p4); l.add(p5); l.add(p6); l.add(p7); l.add(p8); l.add(p9); l.add(p10); l.add(p11); l.add(p12); l.add(p13); l.add(p14);
    return new List$1Instance(l);
  }
  default Object imm$$hash$16(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15){
    var l= new ArrayList<Object>(16);
    l.add(p0); l.add(p1); l.add(p2); l.add(p3); l.add(p4); l.add(p5); l.add(p6); l.add(p7); l.add(p8); l.add(p9); l.add(p10); l.add(p11); l.add(p12); l.add(p13); l.add(p14); l.add(p15);
    return new List$1Instance(l);
  }
  Lists$0 instance= new Lists$0(){};
}

record List$1Instance(List<Object> val) implements List$1{
  static Object wrap(List<Object> l){ return l.isEmpty() ? List$1.instance : new List$1Instance(l); }

  static List<Object> asJava(Object xs){
    if (xs == List$1.instance){ return List.of(); }
    if (xs instanceof List$1Instance x){ return x.val; }
    throw new AssertionError("Unexpected List impl: "+xs.getClass());
  }
  private int idx(Object p0){
    int i= natToInt(p0);
    check(0 <= i && i < val.size(), "List index out of range");
    return i;
  }

  @Override public Object read$size$0(){ return intToNat(val.size()); }
  @Override public Object read$isEmpty$0(){ return bool(val.isEmpty()); }

  @Override public Object read$str$1(Object p0){
    var by= (ToStrBy$1)p0;
    String res= val.stream().map(e->toS(by.imm$$hash$1(e))).collect(Collectors.joining(", ","[","]"));
    return Str$0Instance.instance(res);
  }


  @Override public Object mut$get$1(Object p0){ return val.get(idx(p0)); }
  @Override public Object read$get$1(Object p0){ return val.get(idx(p0)); }

  @Override public Object mut$opt$1(Object p0){
    int i= natToInt(p0);
    return (0 <= i && i < val.size()) ? optSome(val.get(i)) : optEmpty();
  }
  @Override public Object read$opt$1(Object p0){ return mut$opt$1(p0); }
  @Override public Object imm$opt$1(Object p0){ return mut$opt$1(p0); }

  @Override public Object mut$first$0(){
    //can not be empty check(!val.isEmpty(), "List was empty");
    return val.get(0);
  }
  @Override public Object read$first$0(){ return mut$first$0(); }

  @Override public Object mut$last$0(){
    //can not be empty check(!val.isEmpty(), "List was empty");
    return val.get(val.size()-1);
  }
  @Override public Object read$last$0(){ return mut$last$0(); }

  @Override public Object mut$firstOpt$0(){ return optSome(val.get(0)); }
  @Override public Object read$firstOpt$0(){ return mut$firstOpt$0(); }
  @Override public Object imm$firstOpt$0(){ return mut$firstOpt$0(); }

  @Override public Object mut$lastOpt$0(){ return optSome(val.getLast()); }
  @Override public Object read$lastOpt$0(){ return mut$lastOpt$0(); }
  @Override public Object imm$lastOpt$0(){ return mut$lastOpt$0(); }

  @Override public Object mut$$plus_plus$1(Object p0){
    var xs= asJava(p0);
    if (xs.isEmpty()){ return this; }
    var l= new ArrayList<Object>(val.size()+xs.size());
    l.addAll(val);
    l.addAll(xs);
    return wrap(l);
  }
  @Override public Object read$$plus_plus$1(Object p0){ return mut$$plus_plus$1(p0); }

  @Override public Object mut$$lt_plus$1(Object p0){
    var l= new ArrayList<Object>(val.size()+1);
    l.add(p0);
    l.addAll(val);
    return wrap(l);
  }
  @Override public Object read$$lt_plus$1(Object p0){ return mut$$lt_plus$1(p0); }

  @Override public Object mut$$plus_gt$1(Object p0){
    var l= new ArrayList<Object>(val.size()+1);
    l.addAll(val);
    l.add(p0);
    return wrap(l);
  }
  @Override public Object read$$plus_gt$1(Object p0){ return mut$$plus_gt$1(p0); }

  @Override public Object mut$subList$2(Object p0, Object p1){
    int s= Util.natToInt(p0); int e= Util.natToInt(p1);
    check(0 <= s && s <= e && e <= val.size(), "List subList out of range");
    return wrap(val.subList(s,e));
  }
  @Override public Object read$subList$2(Object p0, Object p1){ return mut$subList$2(p0,p1); }

  @Override public Object mut$subList$1(Object p0){ return mut$subList$2(p0, Util.intToNat(val.size())); }
  @Override public Object read$subList$1(Object p0){ return mut$subList$1(p0); }

  @Override public Object mut$with$2(Object p0, Object p1){
    int i= idx(p0);
    var l= new ArrayList<Object>(val);
    l.set(i,p1);
    return wrap(l);
  }
  @Override public Object read$with$2(Object p0, Object p1){ return mut$with$2(p0,p1); }

  @Override public Object mut$withAlso$2(Object p0, Object p1){
    int i= natToInt(p0);
    check(0 <= i && i <= val.size(), "List withAlso out of range");
    var l= new ArrayList<Object>(val.size()+1);
    l.addAll(val.subList(0,i));
    l.add(p1);
    l.addAll(val.subList(i,val.size()));
    return wrap(l);
  }
  @Override public Object read$withAlso$2(Object p0, Object p1){ return mut$withAlso$2(p0,p1); }

  @Override public Object mut$without$1(Object p0){
    int i= idx(p0);
    if (val.size() == 1 && i == 0){ return List$1.instance; }
    if (i == 0){ return wrap(val.subList(1,val.size())); }
    if (i == val.size()-1){ return wrap(val.subList(0,val.size()-1)); }
    var l= new ArrayList<Object>(val.size()-1);
    l.addAll(val.subList(0,i));
    l.addAll(val.subList(i+1,val.size()));
    return wrap(l);
  }
  @Override public Object read$without$1(Object p0){ return mut$without$1(p0); }

  @Override public Object mut$reverse$0(){ return wrap(val.reversed()); }
  @Override public Object read$reverse$0(){ return mut$reverse$0(); }

  @Override public Object mut$as$1(Object p0){
    var l= new ArrayList<Object>(val.size());
    for (var e: val){ l.add(callMF$2(p0,e)); }
    return wrap(l);
  }
  @Override public Object read$as$1(Object p0){ return mut$as$1(p0); }
  @Override public Object imm$as$1(Object p0){ return mut$as$1(p0); }
  @Override public Object read$imm$1(Object p0){
    var by= (ToImmBy$2)p0;
    return new List$1Instance(val.stream().map(e->((ToImm$1)by.imm$$hash$1(e)).read$imm$0()).toList());
  }  
}