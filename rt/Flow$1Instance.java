package base;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

import static base.Util.*;
import static base.Util.bool;
import static base.Util.callF$2;
import static base.Util.callF$3;
import static base.Util.callMF$1;
import static base.Util.callMF$2;
import static base.Util.check;
import static base.Util.cmp;
import static base.Util.isTrue;
import static base.Util.mapKey;
import static base.Util.optEmpty;
import static base.Util.optSome;


public record Flow$1Instance(Stream<Object> s) implements Flow$1 {
    private static Error consumed(){ return err("Flow consumed"); }
    public static Flow$1Instance of(Stream<Object> stream) {
        return new Flow$1Instance(stream);
    }

    public static Stream<Object> unwrap(Object p0) {
        return ((Flow$1Instance) p0).s;
    }

    @Override public Object mut$map$1(Object p0){
        try{ return new Flow$1Instance(s.map(e->callF$2(p0,e))); }
        catch(IllegalStateException e){ throw consumed(); }
    }

    @Override public Object mut$filter$1(Object p0){
        try{ return new Flow$1Instance(s.filter(e->isTrue(callF$2(p0,e)))); }
        catch(IllegalStateException e){ throw consumed(); }
    }

    @Override public Object mut$size$0(){
        try{ return new Nat$0Instance((int)s.count()); }
        catch(IllegalStateException e){ throw consumed(); }
    }

    @Override public Object mut$$plus_plus$1(Object o){
        //Note: all those try catches are relying on the JVM enforcing the stream consumptions,
        //but in the standard it is not guaranteed that it is checked. We need to add tests to all of the flow methods
        //to check that the current JVM does enforce it.
        var other= ((Flow$1Instance)o).s;
        try{ return new Flow$1Instance(Stream.concat(s, other)); }
        catch(IllegalStateException e){ throw consumed(); }
    }
    @Override public Object mut$forEach$1(Object p0){
        try{ s.forEach(e->callMF$2(p0,e)); return Void$0.instance; }
        catch(IllegalStateException e){ throw consumed(); }
    }
    @Override public Object mut$list$0(){
        try{ return List$1Instance.wrap(s.toList()); }
        catch(IllegalStateException e){ throw consumed(); }
    }
    @Override public Object mut$eList$0(){
        try{ return EList$1Instance.wrap(s.toList()); }
        catch(IllegalStateException e){ throw consumed(); }
    }
    @Override public Object mut$fold$2(Object p0,Object p1){
        try{
            var it= s.iterator();
            Object r= callMF$1(p0);
            while(it.hasNext()){ r = callF$3(p1,r,it.next()); }
            return r;
        }
        catch(IllegalStateException e){ throw consumed(); }
    }
    @Override public Object mut$min$1(Object p0){
        try {
            return new Flow$1Instance(
                    s.gather(new MinGatherer((OrderBy$2) p0))
            );
        } catch (IllegalStateException e) { throw consumed(); }
    }
    @Override public Object mut$max$1(Object p0){
        try {
            return new Flow$1Instance(
                    s.gather(new MaxGatherer((OrderBy$2) p0))
            );
        } catch (IllegalStateException e) { throw consumed(); }
    }
    @Override public Object mut$mapping$2(Object p0,Object p1){
        try{
            var kem= (KeyElemMapper$3)p1;
            var m= new LinkedHashMap<MapKey,Object>();
            var k= Maps$0.toKey(p0);
            s.forEach(e->m.put(mapKey(k,kem.imm$key$1(e)), kem.imm$elem$1(e)));
            return new Map$2Instance(k,m);
        }
        catch(IllegalStateException e){ throw consumed(); }
    }
    @Override public Object mut$flatMap$1(Object p0){
        try{ return new Flow$1Instance(s.flatMap(e->((Flow$1Instance)callF$2(p0,e)).s)); }
        catch(IllegalStateException e){ throw consumed(); }
    }
    //---
    @Override public Object mut$any$1(Object p0){
        try{ return bool(s.anyMatch(e->isTrue(callF$2(p0,e)))); }
        catch(IllegalStateException e){ throw consumed(); }
    }
    @Override public Object mut$all$1(Object p0){
        try{ return bool(s.allMatch(e->isTrue(callF$2(p0,e)))); }
        catch(IllegalStateException e){ throw consumed(); }
    }
    @Override public Object mut$none$1(Object p0){
        try{ return bool(s.noneMatch(e->isTrue(callF$2(p0,e)))); }
        catch(IllegalStateException e){ throw consumed(); }
    }
    @Override public Object mut$get$0(){
        try{
            var it= s.iterator();
            check(it.hasNext(), "Flow.get expected size==1, got 0");
            var e0= it.next();
            check(!it.hasNext(), "Flow.get expected size==1, got 2+");
            return e0;
        }
        catch(IllegalStateException e){ throw consumed(); }
    }
    @Override public Object mut$opt$0(){
        try{
            var it= s.iterator();
            if(!it.hasNext()){ return optEmpty(); }
            var e0= it.next();
            check(!it.hasNext(), "Flow.opt expected size in {0,1}, got 2+");
            return optSome(e0);
        }
        catch(IllegalStateException e){ throw consumed(); }
    }
    @Override public Object mut$first$0(){
        try{
            var it= s.iterator();
            return it.hasNext() ? optSome(it.next()) : optEmpty();
        }
        catch(IllegalStateException e){ throw consumed(); }
    }
}


final class MinGatherer implements Gatherer<Object, ArrayList<Object>, Object> {
    private final OrderBy$2 ordering;
    private Object min = null;

    MinGatherer(OrderBy$2 ordering) {
        this.ordering = ordering;
    }

    @Override
    public Supplier<ArrayList<Object>> initializer() {
        return ArrayList::new;
    }

    @Override
    public Integrator<ArrayList<Object>, Object, Object> integrator() {
        return (state, element, _) -> {
            if (this.min == null) {
                this.min = element;
                state.add(element);
                return true;
            }
            int cmp = cmp(ordering, element, min);
            if (cmp < 0) {
                this.min = element;
                state.clear();
                state.add(element);
                return true;
            }
            if (cmp == 0) {
                state.add(element);
            }
            return true;
        };
    }


    @Override
    public BiConsumer<ArrayList<Object>, Downstream<? super Object>> finisher() {
        return (state, downstream) -> {
            if (!downstream.isRejecting()) {
                state.forEach(downstream::push);
                state.clear();
            }
        };
    }
}


final class MaxGatherer implements Gatherer<Object, ArrayList<Object>, Object> {
    private final OrderBy$2 ordering;
    private Object max = null;

    MaxGatherer(OrderBy$2 ordering) {
        this.ordering = ordering;
    }

    @Override
    public Supplier<ArrayList<Object>> initializer() {
        return ArrayList::new;
    }

    @Override
    public Integrator<ArrayList<Object>, Object, Object> integrator() {
        return (state, element, _) -> {
            if (this.max == null) {
                this.max = element;
                state.add(element);
                return true;
            }
            int cmp = cmp(ordering, element, max);
            if (cmp > 0) {
                this.max = element;
                state.clear();
                state.add(element);
                return true;
            }
            if (cmp == 0) {
                state.add(element);
            }
            return true;
        };
    }


    @Override
    public BiConsumer<ArrayList<Object>, Downstream<? super Object>> finisher() {
        return (state, downstream) -> {
            if (!downstream.isRejecting()) {
                state.forEach(downstream::push);
                state.clear();
            }
        };
    }
}
