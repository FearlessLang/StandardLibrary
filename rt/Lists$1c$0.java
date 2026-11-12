package _base;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static _base.Util.*;
public interface Lists$1c$0 extends Sealed$2o$0{
  default Object imm$singletonRead$1(Object p0){ return imm$$hash$1(p0); }
    
  private static Object of(Object... es){ return new List$o$1Instance(List.of(es)); }
  default Object imm$$hash$1(Object p0){ return of(p0); }
  default Object imm$$hash$2(Object p0, Object p1){ return of(p0, p1); }
  default Object imm$$hash$3(Object p0, Object p1, Object p2){ return of(p0, p1, p2); }
  default Object imm$$hash$4(Object p0, Object p1, Object p2, Object p3){ return of(p0, p1, p2, p3); }
  default Object imm$$hash$5(Object p0, Object p1, Object p2, Object p3, Object p4){ return of(p0, p1, p2, p3, p4); }
  default Object imm$$hash$6(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5){ return of(p0, p1, p2, p3, p4, p5); }
  default Object imm$$hash$7(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6){ return of(p0, p1, p2, p3, p4, p5, p6); }
  default Object imm$$hash$8(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7){ return of(p0, p1, p2, p3, p4, p5, p6, p7); }
  default Object imm$$hash$9(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8); }
  default Object imm$$hash$10(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9); }
  default Object imm$$hash$11(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10); }
  default Object imm$$hash$12(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11); }
  default Object imm$$hash$13(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12); }
  default Object imm$$hash$14(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13); }
  default Object imm$$hash$15(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14); }
  default Object imm$$hash$16(Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9, Object p10, Object p11, Object p12, Object p13, Object p14, Object p15){ return of(p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15); }
  Lists$1c$0 instance= new Lists$1c$0(){};
}

record List$o$1Instance(List<Object> val) implements List$o$1{
  static Object wrap(List<Object> l){ return l.isEmpty() ? List$o$1.instance : new List$o$1Instance(l); }

  static List<Object> asJava(Object xs){
    if (xs == List$o$1.instance){ return List.of(); }
    if (xs instanceof List$o$1Instance x){ return x.val; }
    throw new AssertionError("Unexpected List impl: "+xs.getClass());
  }
  private int idx(String m, Object p0, Function<String,Error> err){
    long i= natToLong(p0);
    if (i < 0 || i >= val.size()){ throw err.apply(outOfRange(m, "index "+Long.toUnsignedString(i))); }
    return (int) i;
  }
  private String outOfRange(String m, String what){ return m+": "+what+" is out of range for a List of size "+val.size()+"."; }

  @Override public Object read$size$0(){ return Nat$c$0Instance.instance(val.size()); }
  @Override public Object read$isEmpty$0(){ return bool(val.isEmpty()); }
  @Override public Object read$str$1(Object p0){
    var by= (ToStrBy$5u$1)p0;
    String res= val.stream().map(e -> toS(by.imm$$hash$1(e))).collect(Collectors.joining(", ", "[", "]"));
    return Str$c$0Instance.instance(res);
  }
  @Override public Object mut$get$1(Object p0){ return val.get(idx("List.get", p0, Util::detErr)); }
  @Override public Object mut$tryGet$1(Object p0){
    long i= natToLong(p0);
    if (0 <= i && i < val.size()){ return ok(val.get((int) i)); }
    return fail(outOfRange("List.get", "index "+Long.toUnsignedString(i)));
  }
  @Override public Object read$get$1(Object p0){ return val.get(idx("List.get", p0, Util::detErr)); }
  @Override public Object read$tryGet$1(Object p0){ return mut$tryGet$1(p0); }
  @Override public Object mut$opt$1(Object p0){
    long i= natToLong(p0);
    return (0 <= i && i < val.size()) ? optSome(val.get((int) i)) : optEmpty();
  }
  @Override public Object read$opt$1(Object p0){ return mut$opt$1(p0); }
  @Override public Object imm$opt$1(Object p0){ return mut$opt$1(p0); }
  @Override public Object mut$getFirst$0(){
    //cannot be empty check(!val.isEmpty(), "List was empty");
    return val.getFirst();
  }
  @Override public Object mut$tryGetFirst$0(){
    return ok(val.getFirst());
  }
  @Override public Object read$getFirst$0(){ return mut$getFirst$0(); }
  @Override public Object read$tryGetFirst$0(){ return mut$tryGetFirst$0(); }
  @Override public Object mut$getLast$0(){
    //cannot be empty check(!val.isEmpty(), "List was empty");
    return val.getLast();
  }
  @Override public Object mut$tryGetLast$0(){
    return ok(val.getLast());
  }
  @Override public Object read$getLast$0(){ return mut$getLast$0(); }
  @Override public Object read$tryGetLast$0(){ return mut$tryGetLast$0(); }
  @Override public Object mut$first$0(){ return optSome(val.getFirst()); }
  @Override public Object read$first$0(){ return mut$first$0(); }
  @Override public Object imm$first$0(){ return mut$first$0(); }
  @Override public Object mut$last$0(){ return optSome(val.getLast()); }
  @Override public Object read$last$0(){ return mut$last$0(); }
  @Override public Object imm$last$0(){ return mut$last$0(); }
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
    long s= Util.natToLong(p0); long e= Util.natToLong(p1);
    var outside= Long.compareUnsigned(s, val.size()) > 0 || Long.compareUnsigned(e, val.size()) > 0;
    if (outside){ throw nonDetErr(outOfRange("List.subList", "the range "+Long.toUnsignedString(s)+" to "+Long.toUnsignedString(e))); }
    if (s > e){ throw nonDetErr("List.subList: start "+s+" is greater than end "+e+"."); }
    return wrap(val.subList((int) s, (int) e));
  }
  @Override public Object read$subList$2(Object p0, Object p1){ return mut$subList$2(p0, p1); }
  @Override public Object mut$subList$1(Object p0){ return mut$subList$2(p0, Nat$c$0Instance.instance(val.size())); }
  @Override public Object read$subList$1(Object p0){ return mut$subList$1(p0); }
  @Override public Object mut$with$2(Object p0, Object p1){
    int i= idx("List.with", p0, Util::nonDetErr);
    var l= new ArrayList<Object>(val);
    l.set(i, p1);
    return wrap(l);
  }
  @Override public Object read$with$2(Object p0, Object p1){ return mut$with$2(p0, p1); }
  @Override public Object mut$withAlso$2(Object p0, Object p1){
    long i= natToLong(p0);
    if (i < 0 || i > val.size()){ throw nonDetErr(outOfRange("List.withAlso", "index "+Long.toUnsignedString(i))); }
    int index= (int) i;
    var l= new ArrayList<Object>(val.size()+1);
    l.addAll(val.subList(0, index));
    l.add(p1);
    l.addAll(val.subList(index, val.size()));
    return wrap(l);
  }
  @Override public Object read$withAlso$2(Object p0, Object p1){ return mut$withAlso$2(p0, p1); }
  @Override public Object mut$without$1(Object p0){
    int i= idx("List.without", p0, Util::nonDetErr);
    if (i == 0){ return wrap(val.subList(1, val.size())); }
    if (i == val.size()-1){ return wrap(val.subList(0, val.size()-1)); }
    var l= new ArrayList<Object>(val.size()-1);
    l.addAll(val.subList(0, i));
    l.addAll(val.subList(i+1, val.size()));
    return wrap(l);
  }
  @Override public Object read$without$1(Object p0){ return mut$without$1(p0); }

  @Override public Object mut$reverse$0(){ return wrap(val.reversed()); }
  @Override public Object read$reverse$0(){ return mut$reverse$0(); }

  @Override public Object read$as$1(Object p0){ return this; }
  @Override public Object imm$as$1(Object p0){ return this; }
  @Override public Object read$imm$1(Object p0){
    var by= (ToImmBy$5u$2)p0;
    return new List$o$1Instance(val.stream().map(e -> ((ToImm$1g$1)by.imm$$hash$1(e)).read$imm$0()).toList());
  }  
}
