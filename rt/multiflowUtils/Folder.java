package base.multiflowUtils;

import java.util.Iterator;

public class Folder {
    public static <A, B, R> R fold2(
            SizedLane<A> a,
            SizedLane<B> b,
            R identity,
            TriFunction<R, A, B, R> accumulator) {
        Iterator<A> itA = a.source.iterator();
        Iterator<B> itB = b.source.iterator();
        R acc = identity;
        while (itA.hasNext() && itB.hasNext()) {
            acc = accumulator.apply(acc, itA.next(), itB.next());
        }
        return acc;
    }


    public static <A, B, C, R> R fold3(
            SizedLane<A> a,
            SizedLane<B> b,
            SizedLane<C> c,
            R identity,
            QuadFunction<R, A, B, C, R> accumulator) {
        Iterator<A> itA = a.source.iterator();
        Iterator<B> itB = b.source.iterator();
        Iterator<C> itC = c.source.iterator();
        R acc = identity;
        while (itA.hasNext() && itB.hasNext() && itC.hasNext()) {
            acc = accumulator.apply(acc, itA.next(), itB.next(), itC.next());
        }
        return acc;
    }

    public static <A, B, C, D, R> R fold4(
            SizedLane<A> a,
            SizedLane<B> b,
            SizedLane<C> c,
            SizedLane<D> d,
            R identity,
            PentaFunction<R, A, B, C, D, R> accumulator) {
        Iterator<A> itA = a.source.iterator();
        Iterator<B> itB = b.source.iterator();
        Iterator<C> itC = c.source.iterator();
        Iterator<D> itD = d.source.iterator();
        R acc = identity;
        while (itA.hasNext() && itB.hasNext() && itC.hasNext() && itD.hasNext()) {
            acc = accumulator.apply(acc, itA.next(), itB.next(), itC.next(), itD.next());
        }
        return acc;
    }

}