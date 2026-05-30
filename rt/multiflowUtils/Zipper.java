package base.multiflowUtils;

import base.Flows$0;
import base.SizedFlow$1Instance;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Zipper {
    public static <A, B> SizedFlow$1Instance zip2(SizedLane<A> a, SizedLane<B> b, BiFunction<A, B, Object> combiner) {
        if (a.source.isParallel() && b.source.isParallel()) {
            return Flows$0.of(parZip(a, b, combiner), Math.min(a.size, b.size));
        }
        return Flows$0.of(seqZip2(a, b, combiner), Math.min(a.size, b.size));
    }

    static <A, B, C> Stream<C> seqZip2(SizedLane<A> a, SizedLane<B> b, BiFunction<A, B, C> combiner) {
        Iterator<A> itA = a.source.iterator();
        Iterator<B> itB = b.source.iterator();
        Iterator<C> combined = new Iterator<>() {
            public boolean hasNext() { return itA.hasNext() && itB.hasNext(); }
            public C next() { return combiner.apply(itA.next(), itB.next()); }
        };
        return StreamSupport.stream(
                Spliterators.spliterator(combined, Math.min(a.size, b.size), Spliterator.ORDERED),
                false
        );
    }

    static <A, B, C> Stream<C> parZip(SizedLane<A> a, SizedLane<B> b, BiFunction<A, B, C> combiner) {
        return seqZip2(a, b, Pair::new).parallel().map(p -> p.zip(combiner));
    }


    public static <A, B, C> SizedFlow$1Instance zip3(
            SizedLane<A> a,
            SizedLane<B> b,
            SizedLane<C> c,
            TriFunction<A, B, C, Object> combiner
    ) {
        long minSize = Math.min(a.size, Math.min(b.size, c.size));
        if (a.source.isParallel() && b.source.isParallel() && c.source.isParallel()) {
            return Flows$0.of(parZip3(a, b, c, combiner), minSize);
        }
        return  Flows$0.of(seqZip3(a, b, c, combiner), minSize);
    }

    static <A, B, C, D> Stream<D> seqZip3(
            SizedLane<A> a,
            SizedLane<B> b,
            SizedLane<C> c,
            TriFunction<A, B, C, D> combiner
    ) {
        Iterator<A> itA = a.source.iterator();
        Iterator<B> itB = b.source.iterator();
        Iterator<C> itC = c.source.iterator();
        long size = Math.min(a.size, Math.min(b.size, c.size));
        Iterator<D> combined = new Iterator<>() {
            public boolean hasNext() { return itA.hasNext() && itB.hasNext() && itC.hasNext(); }
            public D next() { return combiner.apply(itA.next(), itB.next(), itC.next()); }
        };
        return StreamSupport.stream(
                Spliterators.spliterator(combined, size, Spliterator.ORDERED),
                false
        );
    }

    static <A, B, C, D> Stream<D> parZip3(
            SizedLane<A> a,
            SizedLane<B> b,
            SizedLane<C> c,
            TriFunction<A, B, C, D> combiner
    ) {
        return seqZip3(a, b, c, Triple::new).parallel().map(t -> t.zip(combiner));
    }


    public static <A, B, C, D> SizedFlow$1Instance zip4(
            SizedLane<A> a,
            SizedLane<B> b,
            SizedLane<C> c,
            SizedLane<D> d,
            QuadFunction<A, B, C, D, Object> combiner) {

        long size = Math.min(a.size, Math.min(b.size, Math.min(c.size, d.size)));
        if (a.source.isParallel() && b.source.isParallel()
                && c.source.isParallel() && d.source.isParallel()) {
            return Flows$0.of(parZip4(a, b, c, d, combiner), size);
        }
        return  Flows$0.of(seqZip4(a, b, c, d, combiner), size);
    }

    static <A, B, C, D, E> Stream<E> seqZip4(
            SizedLane<A> a,
            SizedLane<B> b,
            SizedLane<C> c,
            SizedLane<D> d,
            QuadFunction<A, B, C, D, E> combiner) {
        Iterator<A> itA = a.source.iterator();
        Iterator<B> itB = b.source.iterator();
        Iterator<C> itC = c.source.iterator();
        Iterator<D> itD = d.source.iterator();
        long size = Math.min(a.size, Math.min(b.size, Math.min(c.size, d.size)));
        Iterator<E> combined = new Iterator<>() {
            public boolean hasNext() {
                return itA.hasNext() && itB.hasNext() && itC.hasNext() && itD.hasNext();
            }
            public E next() {
                return combiner.apply(itA.next(), itB.next(), itC.next(), itD.next());
            }
        };
        return StreamSupport.stream(
                Spliterators.spliterator(combined, size, Spliterator.ORDERED),
                false
        );
    }

    static <A, B, C, D, E> Stream<E> parZip4(
            SizedLane<A> a,
            SizedLane<B> b,
            SizedLane<C> c,
            SizedLane<D> d,
            QuadFunction<A, B, C, D, E> combiner) {
        return seqZip4(a, b, c, d, Quad::new).parallel().map(q -> q.zip(combiner));
    }
}

record Pair<A, B>(A a, B b) {
    <C> C zip(BiFunction<A, B, C> combiner) {
        return combiner.apply(a, b);
    }
}

record Triple<A, B, C>(A a, B b, C c) {
    <D> D zip(TriFunction<A, B, C, D> combiner) {
        return combiner.apply(a, b, c);
    }
}

record Quad<A, B, C, D>(A a, B b, C c, D d) {
    <E> E zip(QuadFunction<A, B, C, D, E> combiner) {
        return combiner.apply(a, b, c, d);
    }
}


