package base;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static base.Util.mapKey;
import static base.Util.MapKey;
import static base.Util.bool;
import static base.Util.intToNat;

public interface ESets$0 extends Sealed$0 {
  default Object imm$$hash$1(Object p0){
    return new ESet$2Instance((OrderHashBy$2) p0);
  }

  ESets$0 instance= new ESets$0(){};
}


final class ESet$2Instance implements ESet$2 {
  private LinkedHashSet<MapKey> set;
  private OrderHashBy$2 ordering;

  static Object wrap(LinkedHashSet<MapKey> s, OrderHashBy$2 ordering){
    return new ESet$2Instance(new LinkedHashSet<>(s), ordering);
  }

  ESet$2Instance(LinkedHashSet<MapKey> s, OrderHashBy$2 ordering){
    set= s;
    this.ordering= ordering;
  }

  ESet$2Instance(OrderHashBy$2 ordering) {
    this.ordering=ordering;
    set= new LinkedHashSet<>();
  }

  private ArrayList<Object> drain(){
    var r= set;
    set= new LinkedHashSet<>();
    return new ArrayList<>(r.stream().map(k -> k.key).toList());
  }

  @Override public Object mut$add$1(Object p0){
    set.add(mapKey(ordering, p0));
    return this;
  }

  @Override public Object mut$clear$0(){ 
    set.clear();
    return this;
  }

  @Override public Object read$contains$1(Object p0){
    return bool(set.contains(mapKey(ordering, p0)));
  }
  @Override public Object read$size$0(){ return intToNat(set.size()); }
  @Override public Object mut$seqFlow$0(){ return new Flow$1Instance(drain().stream()); }
  @Override public Object mut$flow$0(){ return new Flow$1Instance(drain().stream()); }//could be parallel
  @Override public Object mut$set$0(){ return Set$2Instance.of(ordering, drain()); }


  @Override public Object mut$distinctBy$1(Object p0){
    reOrderHash((OrderHashBy$2) p0);
    return this;
  }

  private void reOrderHash(OrderHashBy$2 ordering) {
    set = set.stream()
      .map(e -> ((MapKey) e).key)
      .map(e -> mapKey(ordering, e))
      .collect(Collectors.toCollection(LinkedHashSet::new));
  }
}
