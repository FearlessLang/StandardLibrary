package base;


import base.Util.MapKey;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static base.Util.*;
import static base.Set$1Instance.extractKey;

final class ESet$1Instance implements ESet$1 {
    /// A map to allow for O(1) get
    private LinkedHashMap<MapKey, Object> set;
    private final OrderHashBy$2 ordering;

    static Object wrap(LinkedHashSet<MapKey> s, OrderHashBy$2 ordering){
        return new ESet$1Instance(new LinkedHashSet<>(s), ordering);
    }

    ESet$1Instance(LinkedHashSet<MapKey> s, OrderHashBy$2 ordering){
        set= new LinkedHashMap<>(s.stream().collect(Collectors.toMap(
                k -> k,
                Set$1Instance::extractKey
        )));
        this.ordering= ordering;
    }
    ESet$1Instance(LinkedHashMap<MapKey, Object> s, OrderHashBy$2 ordering){
        set = s;
        this.ordering= ordering;
    }

    ESet$1Instance(OrderHashBy$2 ordering) {
        this.ordering=ordering;
        set= new LinkedHashMap<>();
    }

    private ArrayList<Object> drain(){
        var r= set;
        set= new LinkedHashMap<>();
        return r.keySet().stream().map(k -> k.key)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private Set<MapKey> set() {
        return this.set.keySet();
    }
    private Stream<Object> unhashedObjects() {
        return this.set.keySet().stream().map(k -> k.key);
    }

    @Override public Object read$size$0(){ return Nat$0Instance.instance(set.size()); }
    @Override public Object read$orderHash$0() {
        return this.ordering;
    }


    @Override public Object read$contains$1(Object p0){
        return bool(set.containsKey(mapKey(ordering, p0)));
    }

    @Override public Object read$containsAll$1(Object p0) {
        ESet$1Instance eset = (ESet$1Instance) p0;
        if (eset.set.size() > this.set.size()) { return bool(false); }
        if (eset.ordering.equals(this.ordering)) {
            return bool(this.set.keySet().containsAll(eset.set.keySet()));
        }

        return bool(eset.unhashedObjects().allMatch(e -> this.set.containsKey(mapKey(this.ordering, e))));
    }

    @Override public Object mut$add$1(Object p0){
        set.put(mapKey(ordering, p0), p0);
        return this;
    }

    @Override public Object mut$remove$1(Object p0){
        set.remove(mapKey(ordering, p0));
        return this;
    }

    @Override public Object mut$clear$0(){
        set.clear();
        return this;
    }


    @Override public Object mut$addAll$1(Object p0) {
        ESet$1Instance eset = (ESet$1Instance) p0;
        if (eset.ordering.equals(this.ordering)) {
            this.set.putAll(eset.set);
            return this;
        }

        for (MapKey m : eset.set.keySet()) {
            Object obj = extractKey(m);
            this.set.put(mapKey(this.ordering, obj), obj);
        }
        return this;
    }

    @Override public Object mut$removeIf$1(Object p0) {
        ESet$1Instance eset = (ESet$1Instance) p0;
        for (MapKey m : eset.set.keySet()) {
            if (isTrue(callMF$2(p0, extractKey(m)))) {
                this.set.remove(m);
            }
        }
        return this;
    }

    @Override public Object mut$opt$1(Object p0) {
        return optNullable(this.set.get(mapKey(this.ordering, p0)));
    }

    @Override public Object mut$get$1(Object p0) {
        Object key = this.set.get(mapKey(this.ordering, p0));
        if (key == null) {
            throw err("Tried to get object that is not in the set");
        }
        return key;
    }

    @Override public Object mut$seqFlow$0(){ return Flow$1Instance.of(drain().stream()); }
    @Override public Object mut$flow$0(){ return Flow$1Instance.of(drain().stream()); }//could be parallel
    @Override public Object mut$set$0(){ return Set$1Instance.of(ordering, drain()); }


    @Override public Object mut$distinctBy$1(Object p0){
        reOrderHash((OrderHashBy$2) p0);
        return this;
    }

    private void reOrderHash(OrderHashBy$2 ordering) {
        LinkedHashMap<MapKey, Object> newSet = new LinkedHashMap<>();
        set.forEach((k, v) -> newSet.put(mapKey(ordering, extractKey(k)), v));
        this.set = newSet;
    }
}
