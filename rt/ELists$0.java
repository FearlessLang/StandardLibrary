package base;

import java.util.*;
import java.util.stream.IntStream;

import static base.Util.*;

public interface ELists$0 extends Sealed$0 {
    ELists$0 instance = new ELists$0() {
    };

    default Object imm$$hash$0() {
        return new EList$1Instance();
    }

    default Object imm$$hash$1(Object p0) {
        long capacity = natToLong(p0);
        check(capacity <= Integer.MAX_VALUE, "Internal EList capacity cannot be larger than Integer.MAX_VALUE");
        return new EList$1Instance(new ArrayList<>((int) capacity));
    }
}

class EList$1Instance implements EList$1 {
    protected ArrayList<Object> xs;

    EList$1Instance(ArrayList<Object> l) {
        xs = l;
    }

    EList$1Instance() {
        xs = new ArrayList<>();
    }

    static EList$1Instance empty() {
        return new EList$1Instance(new ArrayList<>());
    }

    static Object wrap(List<Object> l) {
        return new EList$1Instance(new ArrayList<>(l));
    }

    private ArrayList<Object> drain() {
        var r = xs;
        xs = new ArrayList<>();
        return r;
    }

    private int idx(Object p0) {
        long i = natToLong(p0);
        // Lists cannot get larger than an int
        check(0 <= i && i < xs.size(), "EList index out of range");
        return (int) i;
    }

    @Override
    public Object read$size$0() {
        return intToNat(xs.size());
    }

    @Override
    public Object read$getFirst$0() {
        check(this.xs.isEmpty(), ".first called on empty EList");
        return this.xs.getFirst();
    }

    @Override
    public Object read$getLast$0() {
        check(this.xs.isEmpty(), ".first called on empty EList");
        return this.xs.getLast();
    }

    @Override
    public Object mut$sort$1(Object p0) {
        var by = (OrderBy$2) p0;
        xs.sort((a, b) -> cmp(by, a, b));
        return new SortedEList$1Instance(this, by);
    }

    @Override
    public Object mut$distinct$1(Object p0) {
        distinctByHash(p0);
        return this;
    }

    @Override
    public Object mut$sortDistinct$1(Object p0) {
        sortDistinctInPlace(p0);
        return new SortedEList$1Instance(this, (OrderBy$2) p0);
    }

    @Override
    public Object mut$trimToSize$0() {
        xs.trimToSize();
        return this;
    }

    @Override
    public Object mut$fold$2(Object p0, Object p1) {
        Object acc = callMF$1(p0);
        for (int i = 0; i < xs.size(); i++) {
            acc = callMF$4(p1, acc, Nat$0Instance.instance(i), xs.get(i));
        }
        return acc;
    }

    @Override
    public Object mut$foldRight$2(Object p0, Object p1) {
        Object acc = callMF$1(p0);
        for (int i = xs.size() - 1; i >= 0; i--) {
            acc = callMF$4(p1, acc, Nat$0Instance.instance(i), xs.get(i));
        }
        return acc;
    }

    @Override
    public Object mut$foldUntil$3(Object p0, Object p1, Object p2) {
        Object acc = callMF$1(p0);
        for (int i = 0; i < xs.size(); i++) {
            acc = callMF$4(p1, acc, Nat$0Instance.instance(i), xs.get(i));
            if (isTrue(callMF$2(p2, acc))) {
                break;
            }
        }
        return acc;
    }

    @Override
    public Object mut$foldRightUntil$3(Object p0, Object p1, Object p2) {
        Object acc = callMF$1(p0);
        for (int i = xs.size() - 1; i >= 0; i--) {
            acc = callMF$4(p1, acc, Nat$0Instance.instance(i), xs.get(i));
            if (isTrue(callMF$2(p2, acc))) {
                break;
            }
        }
        return acc;
    }

    @Override
    public Object mut$accumulateInPlace$1(Object p0) {
        if (xs.size() <= 1) {
            return this;
        }

        Object acc = xs.getFirst();
        for (int i = 1; i < xs.size(); i++) {
            acc = callMF$3(p0, acc, xs.get(i));
            xs.set(i, acc);
        }

        return this;
    }

    @Override
    public Object mut$accumulateRightInPlace$1(Object p0) {
        if (xs.size() <= 1) {
            return this;
        }

        Object acc = xs.getLast();
        for (int i = xs.size() - 2; i >= 0; i--) {
            acc = callMF$3(p0, acc, xs.get(i));
            xs.set(i, acc);
        }

        return this;
    }

    @Override
    public Object mut$clear$0() {
        xs.clear();
        return this;
    }

    @Override
    public Object mut$get$1(Object p0) {
        return xs.get(idx(p0));
    }

    @Override
    public Object mut$seqFlow$0() {
        long size = this.xs.size();
        return Flows$0.of(drain().stream(), size);
    }

    @Override
    public Object mut$flow$1(Object p0) {
        long size = this.xs.size();
        return Flows$0.of(drain().stream(), size);
    }

    @Override
    public Object mut$list$0() {
        return List$1Instance.wrap(drain());
    }

    @Override
    public Object mut$add$1(Object p0) {
        xs.add(p0);
        return this;
    }

    @Override
    public Object mut$addFirst$1(Object p0) {
        xs.addFirst(p0);
        return this;
    }

    private void distinctByHash(Object p0) {
        if (xs.size() < 2) {
            return;
        }
        var by = (OrderHashBy$2) p0;
        var seen = new LinkedHashSet<MapKey>(xs.size() * 2);
        var ys = new ArrayList<Object>(xs.size());//bad use of space here, we could avoid two copies
        for (var e : xs) {
            if (seen.add(mapKey(by, e))) {
                ys.add(e);
            }
        }
        xs = ys;
    }

    private void sortDistinctInPlace(Object p0) {
        if (xs.size() < 2) {
            return;
        }
        var by = (OrderBy$2) p0;
        xs.sort((a, b) -> cmp(by, a, b));
        int w = 1;
        for (int i = 1; i < xs.size(); i++) {//unsure if this is correct
            if (cmp(by, xs.get(w - 1), xs.get(i)) != 0) {
                xs.set(w++, xs.get(i));
            }
        }
        if (w < xs.size()) {
            xs.subList(w, xs.size()).clear();
        }
    }

    @Override
    public Object mut$all$1(Object p0) {
        for (var e : xs) {
            if (isFalse(callMF$2(p0, e))) {
                return bool(false);
            }
        }
        return bool(true);
    }

    @Override
    public Object mut$any$1(Object p0) {
        for (var e : xs) {
            if (isTrue(callMF$2(p0, e))) {
                return bool(true);
            }
        }
        return bool(false);
    }

    @Override
    public Object mut$none$1(Object p0) {
        for (var e : xs) {
            if (isTrue(callMF$2(p0, e))) {
                return bool(false);
            }
        }
        return bool(true);
    }

    @Override
    public Object mut$firstIndexWhere$1(Object p0) {
        for (int i = 0; i < xs.size(); i++) {
            if (isTrue(callMF$2(p0, xs.get(i)))) {
                return optSome(Nat$0Instance.instance(i));
            }
        }
        return optEmpty();
    }

    @Override
    public Object mut$lastIndexWhere$1(Object p0) {
        for (int i = xs.size() - 1; i >= 0; i--) {
            if (isTrue(callMF$2(p0, xs.get(i)))) {
                return optSome(Nat$0Instance.instance(i));
            }
        }
        return optEmpty();
    }

    @Override
    public Object mut$indicesWhere$1(Object p0) {
        return Flows$0.of(
                IntStream.range(0, xs.size())
                        .filter(i -> isTrue(callMF$2(p0, xs.get(i))))
                        .mapToObj(Nat$0Instance::instance)
        );
    }

    @Override
    public Object mut$insertBefore$2(Object p0, Object p1) {
        long index = natToLong(p0);
        check(0 <= index && index <= xs.size(), "EList index out of range");
        xs.add((int) index, p1);
        return this;
    }

    @Override
    public Object mut$set$2(Object p0, Object p1) {
        long index = natToLong(p0);
        check(0 <= index && index < xs.size(), "EList index out of range");
        xs.set((int) index, p1);
        return this;
    }


    @Override
    public Object mut$removeIf$1(Object p0) {
        this.xs.removeIf(o -> isTrue(callMF$2(p0, o)));
        return this;
    }

    @Override
    public Object mut$retainIf$1(Object p0) {
        this.xs.removeIf(o -> !isTrue(callMF$2(p0, o)));
        return this;
    }

    @Override
    public Object mut$remove$1(Object p0) {
        long index = natToLong(p0);
        check(0 <= index && index < xs.size(), "EList index out of range");
        xs.remove((int) index);
        return this;
    }

    @Override
    public Object mut$removeFirstWhere$1(Object p0) {
        for (int i = 0; i < xs.size(); i++) {
            if (isTrue(callMF$2(p0, xs.get(i)))) {
                xs.remove(i);
                return this;
            }
        }
        return this;
    }

    @Override
    public Object mut$removeLastWhere$1(Object p0) {
        for (int i = xs.size() - 1; i >= 0; i--) {
            if (isTrue(callMF$2(p0, xs.get(i)))) {
                xs.remove(i);
                return this;
            }
        }
        return this;
    }

    @Override
    public Object mut$trimTo$2(Object p0, Object p1) {
        long s = natToLong(p0);
        long e = natToLong(p1);
        check(
                0 <= s && e <= xs.size(),
                "EList trim indices out of range"
        );
        check(s <= e, "EList trim start index must be less than or equal to end index");
        int start = (int) s;
        int end = (int) e;
        for (int i = 0; i < start; i++) {
            xs.set(i, null);
        }
        for (int i = end; i < xs.size(); i++) {
            xs.set(i, null);
        }

        xs.removeIf(Objects::isNull);
        return this;
    }

    @Override
    public Object mut$reverse$0() {
        Collections.reverse(xs);
        return this;
    }

    @Override
    public Object mut$mapInPlace$1(Object p0) {
        xs.replaceAll(x -> callMF$2(p0, x));
        return this;
    }

    @Override
    public Object mut$swap$2(Object p0, Object p1) {
        long i = natToLong(p0);
        long j = natToLong(p1);
        check(0 <= i && i < xs.size(), "EList index out of range");
        check(0 <= j && j < xs.size(), "EList index out of range");
        Collections.swap(xs, (int) i, (int) j);
        return this;
    }

    @Override
    public Object mut$shallow_clone$0() {
        return new EList$1Instance(new ArrayList<>(this.xs));
    }
}


class SortedEList$1Instance implements SortedEList$2 {
    EList$1Instance elist;
    OrderBy$2 ordering;

    SortedEList$1Instance(EList$1Instance elist, OrderBy$2 o) {
        this.elist = elist;
        this.ordering = o;
    }


    @Override
    public Object read$order$0() {
        return ordering;
    }

    @Override
    public Object read$binarySearch$2(Object p0, Object p1) {
        ArrayList<Object> xs = elist.xs;
        if (xs.isEmpty()) {
            return ((FoundMatcher$1) p1).mut$notFound$1(
                    Nat$0Instance.instance(0)
            );
        }
        int low = 0, high = xs.size() - 1;
        while (low <= high) {
            int middle = low + ((high - low) / 2);
            int cmp = cmp(ordering, p0, xs.get(middle));
            if (cmp == 0) {
                return ((FoundMatcher$1) p1).mut$found$1(
                        Nat$0Instance.instance(middle)
                );
            }

            if (cmp > 0) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return ((FoundMatcher$1) p1).mut$notFound$1(
                Nat$0Instance.instance(low)
        );
    }

    @Override
    public Object read$max$0() {
        return this.elist.xs.getLast();
    }

    @Override
    public Object read$min$0() {
        return this.elist.xs.getFirst();
    }

    @Override
    public Object mut$max$0() {
        return this.elist.xs.getLast();
    }

    @Override
    public Object mut$min$0() {
        return this.elist.xs.getFirst();
    }

    @Override
    public Object read$size$0() {
        return elist.read$size$0();
    }

    @Override
    public Object read$getFirst$0() {
        return elist.read$getFirst$0();
    }

    @Override
    public Object read$getLast$0() {
        return elist.read$getLast$0();
    }

    @Override
    public Object mut$sort$1(Object p0) {
        if (this.ordering == p0) {return this;}
        this.ordering = (OrderBy$2) p0;
        this.elist.mut$sort$1(p0);
        return this;
    }

    @Override
    public Object mut$distinct$1(Object p0) {
        elist.mut$distinct$1(p0);
        return this;
    }

    @Override
    public Object mut$sortDistinct$1(Object p0) {
        if (this.ordering == p0) {return this;}
        this.ordering = (OrderBy$2) p0;
        this.elist.mut$sortDistinct$1(p0);
        return this;
    }

    @Override
    public Object mut$trimToSize$0() {
        this.elist.mut$trimToSize$0();
        return this;
    }

    @Override
    public Object mut$fold$2(Object p0, Object p1) {
        return elist.mut$fold$2(p0, p1);
    }

    @Override
    public Object mut$foldRight$2(Object p0, Object p1) {
        return elist.mut$foldRight$2(p0, p1);
    }

    @Override
    public Object mut$foldUntil$3(Object p0, Object p1, Object p2) {
        return elist.mut$foldUntil$3(p0, p1, p2);
    }

    @Override
    public Object mut$foldRightUntil$3(Object p0, Object p1, Object p2) {
        return elist.mut$foldRightUntil$3(p0, p1, p2);
    }

    @Override
    public Object mut$accumulateInPlace$1(Object p0) {
        return this.drain().mut$accumulateInPlace$1(p0);
    }

    EList$1Instance drain() {
        var elist = this.elist;
        this.elist = EList$1Instance.empty();
        return elist;
    }

    @Override
    public Object mut$accumulateRightInPlace$1(Object p0) {
        return this.drain().mut$accumulateInPlace$1(p0);
    }

    @Override
    public Object mut$clear$0() {
        this.elist.mut$clear$0();
        return this;
    }

    @Override
    public Object mut$get$1(Object p0) {
        return this.elist.mut$get$1(p0);
    }

    // Flow and list methods already drain
    @Override
    public Object mut$seqFlow$0() {
        return this.elist.mut$seqFlow$0();
    }

    @Override
    public Object mut$flow$1(Object p0) {
        return this.elist.mut$flow$1(p0);
    }

    @Override
    public Object mut$list$0() {
        return this.elist.mut$list$0();
    }

    @Override
    public Object mut$add$1(Object p0) {
        return this.drain().mut$add$1(p0);
    }

    @Override
    public Object mut$addFirst$1(Object p0) {
        return this.drain().mut$addFirst$1(p0);
    }


    @Override
    public Object mut$all$1(Object p0) {
        return this.elist.mut$all$1(p0);
    }

    @Override
    public Object mut$any$1(Object p0) {
        return this.elist.mut$any$1(p0);
    }

    @Override
    public Object mut$none$1(Object p0) {
        return this.elist.mut$none$1(p0);
    }

    @Override
    public Object mut$firstIndexWhere$1(Object p0) {
        return this.elist.mut$firstIndexWhere$1(p0);
    }

    @Override
    public Object mut$lastIndexWhere$1(Object p0) {
       return this.elist.mut$lastIndexWhere$1(p0);
    }

    @Override
    public Object mut$indicesWhere$1(Object p0) {
        return this.elist.mut$indicesWhere$1(p0);
    }

    @Override
    public Object mut$insertBefore$2(Object p0, Object p1) {
        return this.drain().mut$indicesWhere$1(p0);
    }

    @Override
    public Object mut$set$2(Object p0, Object p1) {
        return this.drain().mut$set$2(p0, p1);
    }


    @Override
    public Object mut$removeIf$1(Object p0) {
        this.elist.mut$removeIf$1(p0);
        return this;
    }

    @Override
    public Object mut$retainIf$1(Object p0) {
        this.elist.mut$retainIf$1(p0);
        return this;
    }

    @Override
    public Object mut$remove$1(Object p0) {
        this.elist.mut$remove$1(p0);
        return this;
    }

    @Override
    public Object mut$removeFirstWhere$1(Object p0) {
        this.elist.mut$removeFirstWhere$1(p0);
        return this;
    }

    @Override
    public Object mut$removeLastWhere$1(Object p0) {
        this.elist.mut$removeLastWhere$1(p0);
        return this;
    }

    @Override
    public Object mut$trimTo$2(Object p0, Object p1) {
        this.elist.mut$trimTo$2(p0, p1);
        return this;
    }

    @Override
    public Object mut$reverse$0() {
        this.ordering = (OrderBy$2) this.ordering.imm$flip$0();
        this.elist.mut$reverse$0();
        return this;
    }

    @Override
    public Object mut$mapInPlace$1(Object p0) {
        return this.drain().mut$mapInPlace$1(p0);
    }

    @Override
    public Object mut$swap$2(Object p0, Object p1) {
        return this.drain().mut$swap$2(p0, p1);
    }

    @Override
    public Object mut$shallow_clone$0() {
        return new SortedEList$1Instance(
                (EList$1Instance) this.elist.mut$shallow_clone$0(),
                this.ordering
        );
    }
}
