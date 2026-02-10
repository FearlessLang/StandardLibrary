package base;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import static base.Util.*;

public interface ELists$0 extends Sealed$0{
  default Object imm$$hash$0(){ return new EList$1Instance(); }
  ELists$0 instance= new ELists$0(){};
}
final class EList$1Instance implements EList$1{
  private ArrayList<Object> xs= new ArrayList<>();
  private ArrayList<Object> drain(){
    var r= xs;
    xs= new ArrayList<>();
    return r;
  }
  private int idx(Object p0){
    int i= natToInt(p0);
    check(0 <= i && i < xs.size(), "EList index out of range");
    return i;
  }
  @Override public Object mut$add$1(Object p0){ xs.add(p0); return Void$0.instance; }
  @Override public Object mut$addAnd$1(Object p0){ xs.add(p0); return this; }
  @Override public Object mut$clear$0(){ xs.clear(); return Void$0.instance; }
  @Override public Object mut$clearAnd$0(){ xs.clear(); return this; }
  @Override public Object mut$get$1(Object p0){ return xs.get(idx(p0)); }
  @Override public Object read$size$0(){ return intToNat(xs.size()); }
  @Override public Object mut$flow$0(){ return new Flow$1Instance(drain().stream()); }
  @Override public Object mut$list$0(){ return List$1Instance.wrap(drain()); }
  @Override public Object mut$sort$1(Object p0){
    var by= (OrderBy$2)p0;
    xs.sort((a,b)->cmp(by,a,b));
    return Void$0.instance;
  }
  @Override public Object mut$sortAnd$1(Object p0){
    mut$sort$1(p0);
    return this;
  }
  private void distinctByHash(Object p0){
    if(xs.size() < 2){ return; }
    var by= (OrderHashBy$2)p0;
    var seen= new LinkedHashSet<MapKey>(xs.size()*2);
    var ys= new ArrayList<Object>(xs.size());//bad use of space here, we could avoid two copies
    for(var e: xs){ if(seen.add(mapKey(by,e))){ ys.add(e); } }
    xs= ys;
  }
  @Override public Object mut$distinct$1(Object p0){
    distinctByHash(p0);
    return Void$0.instance;
  }
  @Override public Object mut$distinctAnd$1(Object p0){
    distinctByHash(p0);
    return this;
  }
  private void sortDistinctInPlace(Object p0){
    if(xs.size() < 2){ return; }
    var by= (OrderBy$2)p0;
    xs.sort((a,b)->cmp(by,a,b));
    int w= 1;
    for(int i= 1; i < xs.size(); i++){//unsure if this is correct
      if(cmp(by,xs.get(w-1),xs.get(i)) != 0){ xs.set(w++, xs.get(i)); }
    }
    if(w < xs.size()){ xs.subList(w,xs.size()).clear(); }
  }
  @Override public Object mut$sortDistinct$1(Object p0){
    sortDistinctInPlace(p0);
    return Void$0.instance;
  }
  @Override public Object mut$sortDistinctAnd$1(Object p0){
    sortDistinctInPlace(p0);
    return this;
  }
}