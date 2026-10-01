package _base;

import java.util.ArrayList;
import java.util.List;

import static _base.Util.*;

public interface _BiFlows$5s$0{
  default Object imm$$hash$2(Object p0, Object p1){ return new BiFlow$2w$2Instance(List$o$1Instance.asJava(p0), List$o$1Instance.asJava(p1)); }
  _BiFlows$5s$0 instance= new _BiFlows$5s$0(){};
}
final class BiFlow$2w$2Instance implements BiFlow$2w$2{
  BiFlow$2w$2Instance(List<Object> a, List<Object> b){ this.a= new ArrayList<>(a); this.b= new ArrayList<>(b); }
  private ArrayList<Object> a;
  private ArrayList<Object> b;
  private boolean consumed= false;
  private void live(){ check(!consumed, "BiFlow: this BiFlow is already consumed: a BiFlow is consumed by the call that merges or folds it."); }
  private void take(){ live(); consumed= true; }
  private boolean same(){ return a.size() == b.size(); }
  private void sameOrErr(String m){
    if (!same()){ throw detErr("BiFlow."+m+": the channels have different sizes, "+a.size()+" and "+b.size()); }
  }
  private static Object opt(List<Object> xs, int i){ return i < xs.size() ? optSome(xs.get(i)) : optEmpty(); }
  private int longest(){ return Math.max(a.size(), b.size()); }
  private Object merged(Object f){
    var res= new ArrayList<Object>(a.size());
    for (int i= 0; i < a.size(); i++){ res.add(callF$3(f, a.get(i), b.get(i))); }
    return Flow$o$1Instance.of(res, FlowMode.Seq);
  }
  private Object folded(Object acc, Object f){
    var r= callMF$1(acc);
    for (int i= 0; i < a.size(); i++){ r= callF$4(f, r, a.get(i), b.get(i)); }
    return r;
  }
  @Override public Object mut$getMerge$1(Object p0){ take(); sameOrErr("getMerge"); return merged(p0); }
  @Override public Object mut$merge$1(Object p0){ take(); return same() ? optSome(merged(p0)) : optEmpty(); }
  @Override public Object mut$mergeOpts$1(Object p0){
    take();
    var res= new ArrayList<Object>(longest());
    for (int i= 0; i < longest(); i++){ res.add(callF$3(p0, opt(a, i), opt(b, i))); }
    return Flow$o$1Instance.of(res, FlowMode.Seq);
  }
  @Override public Object mut$getFold$2(Object p0, Object p1){ take(); sameOrErr("getFold"); return folded(p0, p1); }
  @Override public Object mut$fold$2(Object p0, Object p1){ take(); return same() ? optSome(folded(p0, p1)) : optEmpty(); }
  @Override public Object mut$foldOpts$2(Object p0, Object p1){
    take();
    var r= callMF$1(p0);
    for (int i= 0; i < longest(); i++){ r= callF$4(p1, r, opt(a, i), opt(b, i)); }
    return r;
  }
  private Object fill(ArrayList<Object> xs, int size, Object f){
    live();
    for (int i= xs.size(); i < size; i++){ xs.add(callF$2(f, Nat$c$0Instance.instance(i))); }
    return this;
  }
  @Override public Object mut$fillA$1(Object p0){ return fill(a, b.size(), p0); }
  @Override public Object mut$fillB$1(Object p0){ return fill(b, a.size(), p0); }
  @Override public Object mut$cutToSmallest$0(){
    live();
    int n= Math.min(a.size(), b.size());
    a.subList(n, a.size()).clear();
    b.subList(n, b.size()).clear();
    return this;
  }
}
