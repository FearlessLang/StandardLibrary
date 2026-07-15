package base;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static base.Util.*;

class ESparseSegmentList$1Instance implements ESparseList$1 {
    // -1 is the maxUnsigned Long
    SparseSegmentTree base = SparseSegmentTree.of(0L, -1L);
    long capacaity;
    long numHoles;
    ESparseSegmentList$1Instance(long capacity) {
        this.capacaity = capacity;
        this.numHoles = capacity;
    }

    public ESparseSegmentList$1Instance(ESparseSegmentList$1Instance elist) {
        this.capacaity = elist.capacaity;
        this.numHoles = elist.numHoles;
        this.base = SparseSegmentTree.explore(new NodeExplorer<SparseSegmentTree>() {
            @Override
            public SparseSegmentTree leaf(SparseSegmentTreeLeaf leaf) {
                return new SparseSegmentTreeLeaf(
                        leaf.start,
                        leaf.end,
                        Arrays.copyOf(leaf.data, leaf.data.length)
                );
            }

            @Override
            public SparseSegmentTree node(SparseSegmentTreeNode node) {
                return new SparseSegmentTreeNode(
                        node.start, node.end,
                        SparseSegmentTree.explore(this, node.left, node.start, node.midPoint),
                        SparseSegmentTree.explore(this, node.right, node.midPoint, node.end)
                );
            }

            @Override
            public SparseSegmentTree unexplored(long lowerBound, long upperBound) {
                return null;
            }
        }, this.base, 0, this.capacaity);
    }

    @Override public Object read$capacity$0() {
        return Nat$0Instance.instance(capacaity);
    }

    @Override public Object read$numHoles$0() {
        return Nat$0Instance.instance(numHoles);
    }

    /// Note this does not expand the right tree each time,
    /// so calling this 10 times with size p0=1 will add 10 new nodes to the tree....
    @Override public Object mut$increaseCapacityDefensive$1(Object p0) {
        long proposedCapacity = natToLong(p0);
        if (proposedCapacity <= this.capacaity) { return this; }

        long addedCapacity = proposedCapacity - this.capacaity;

        SparseSegmentTreeNode newRoot = new SparseSegmentTreeNode(
                0, proposedCapacity,
                this.base,
                null
        );
        // Constructor sets numHoles = size, so we need to correct it:
        // new holes = old holes + added slots (which are all holes)
        newRoot.numHoles = this.numHoles + addedCapacity;
        // Also fix midPoint to be at oldCapacity so existing indices route left correctly
        newRoot.midPoint = this.capacaity;

        this.numHoles = newRoot.numHoles;
        this.capacaity = proposedCapacity;
        this.base = newRoot;
        return this;
    }

    @Override public Object mut$fillHoles$1(Object p0) {
        if (numHoles == 0) { return this; }
        SparseSegmentTree.explore(new NodeExplorers.RecursivelyFillHoles(p0), base, 0, this.capacaity);
        return this;
    }



    @Override public Object mut$fillFrom$1(Object p0) {
        var elist = (ESparseList$1Instance) p0;
        Object[] list = elist.list;
        SparseSegmentTree.explore(new NodeExplorers.RecursivelyFillFrom(list), base, 0, this.capacaity);
        return this;
    }


    @Override public Object mut$fillAndExpand$1(Object p0) {
        var elist = (ESparseList$1Instance) p0;
        Object[] list = elist.list;
        this.capacaity = Math.max(list.length + this.capacaity - this.numHoles, this.capacaity);
        this.mut$fillFrom$1(p0);
        return this;
    }

    Stream<Object> drainToNullStream() {
        var flow = SparseSegmentTree.explore(new NullStream(), this.base, 0, capacaity);
        this.mut$clear$0();
        return flow;
    }

    Stream<Object> drainToNonNullStream() {
        var flow = SparseSegmentTree.explore(new NonNullStream(), this.base, 0, capacaity);
        this.mut$clear$0();
        return flow;
    }

    @Override public Object mut$seqFlowOpts$0() {
        return Flows$0.of(
                drainToNullStream().map(Util::optNullable),
                this.capacaity
        );
    }

    @Override public Object mut$flowOpts$1(Object p0) {
        return Flows$0.of(
                drainToNullStream().map(Util::optNullable),
                this.capacaity
        );
    }

    @Override public Object mut$seqFlowDefensive$0() {
        return Flows$0.of(
                drainToNonNullStream(),
                this.capacaity - this.numHoles
        );
    }

    @Override public Object mut$flowDefensive$1(Object p0) {
        return Flows$0.of(
                drainToNonNullStream(),
                this.capacaity - this.numHoles
        );
    }


    @Override public Object mut$listExact$0() {
        if (numHoles > 0) {
            throw err(
                    "Cannot create a list of an ESparseList that is not full.\n"
                    +"Use a \"hole-safe\" flow method, or fill the holes first."
            );
        }
        return EList$1Instance.wrap(drainToNullStream().toList());
    }

    @Override public Object mut$all$1(Object p0) {
        return bool(SparseSegmentTree.explore(new NonNullStream(), this.base, 0, this.capacaity)
                .allMatch(o -> isTrue(callMF$2(p0, o))));
    }

    @Override public Object mut$any$1(Object p0) {
        return bool(SparseSegmentTree.explore(new NonNullStream(), this.base, 0, this.capacaity)
                .anyMatch(o -> isTrue(callMF$2(p0, o))));
    }

    @Override public Object mut$none$1(Object p0) {
        return bool(SparseSegmentTree.explore(new NonNullStream(), this.base, 0, this.capacaity)
                .noneMatch(o -> isTrue(callMF$2(p0, o))));
    }

    @Override public Object mut$firstIndexWhere$1(Object p0) {
        return SparseSegmentTree.explore(new NodeExplorer<Optional<Long>>() {
             @Override
             public Optional<Long> leaf(SparseSegmentTreeLeaf leaf) {
                 for (int i = 0; i<leaf.data.length; i++) {
                     if (leaf.data[i] == null) { continue; }
                     if (isTrue(callMF$2(p0, leaf.data[i]))) {
                         return Optional.of(i+leaf.start);
                     }
                 }
                 return Optional.empty();
             }

             @Override
             public Optional<Long> node(SparseSegmentTreeNode node) {
                 return SparseSegmentTree.explore(this, node.left, node.start, node.midPoint)
                         .or(() -> SparseSegmentTree.explore(this, node.right, node.midPoint, node.end));
             }

             @Override
             public Optional<Long> unexplored(long lowerBound, long upperBound) {
                 return Optional.empty();
             }
         }, this.base, 0, this.capacaity)
                .map(i -> optSome(Nat$0Instance.instance(i)))
                .orElse(optEmpty());
    }


    @Override public Object mut$lastIndexWhere$1(Object p0) {
        return SparseSegmentTree.explore(new NodeExplorer<Optional<Long>>() {
                    @Override
                    public Optional<Long> leaf(SparseSegmentTreeLeaf leaf) {
                        for (int i = leaf.data.length - 1; i>=0; i--) {
                            if (leaf.data[i] == null) { continue; }
                            if (isTrue(callMF$2(p0, leaf.data[i]))) {
                                return Optional.of(i+leaf.start);
                            }
                        }
                        return Optional.empty();
                    }

                    @Override
                    public Optional<Long> node(SparseSegmentTreeNode node) {
                        return SparseSegmentTree.explore(this, node.right, node.midPoint, node.end)
                                .or(() -> SparseSegmentTree.explore(this, node.left, node.start, node.midPoint));
                    }

                    @Override
                    public Optional<Long> unexplored(long lowerBound, long upperBound) {
                        return Optional.empty();
                    }
                }, this.base, 0, this.capacaity)
                .map(i -> optSome(Nat$0Instance.instance(i)))
                .orElse(optEmpty());
    }

    @Override public Object mut$indicesWhere$1(Object p0) {
         return Flows$0.of(SparseSegmentTree.explore(new NodeExplorer<Stream<Long>>() {
                    @Override
                    public Stream<Long> leaf(SparseSegmentTreeLeaf leaf) {
                        return IntStream.range(0, leaf.data.length)
                                .filter(i -> Objects.nonNull(leaf.data[i]))
                                .filter(i -> isTrue(callMF$2(p0, leaf.data[i])))
                                .mapToObj(i -> i + leaf.start);

                    }

                    @Override
                    public Stream<Long> node(SparseSegmentTreeNode node) {
                        return Stream.concat(
                                SparseSegmentTree.explore(this, node.left, node.start, node.midPoint),
                                SparseSegmentTree.explore(this, node.right, node.midPoint, node.end)
                        );
                    }

                    @Override
                    public Stream<Long> unexplored(long lowerBound, long upperBound) {
                        return Stream.empty();
                    }
                }, this.base, 0, this.capacaity).map(Nat$0Instance::instance));
    }

    static class RetainIf implements NodeExplorer<Integer> {
        final Object f;
        RetainIf(Object f) {
            this.f = f;
        }

        @Override
        public Integer leaf(SparseSegmentTreeLeaf leaf) {
            int removalCount = 0;
            for (int i=0; i < leaf.data.length; i++) {
                Object elem = leaf.data[i];
                if (elem == null) { continue; }
                if (isFalse(callMF$2(f, elem))) {
                    leaf.data[i] = null;
                    removalCount+=1;
                }
            }
            leaf.numHoles += removalCount;
            return removalCount;
        }

        @Override
        public Integer node(SparseSegmentTreeNode node) {
            int numRemoved = SparseSegmentTree.explore(this, node.left, node.start, node.midPoint)
                    + SparseSegmentTree.explore(this, node.right, node.midPoint, node.end);
            node.numHoles += numRemoved;
            return numRemoved;
        }

        @Override
        public Integer unexplored(long lowerBound, long upperBound) {
            return 0;
        }
    }

    static class RemoveIf extends RetainIf {
        RemoveIf(Object f) {
            super(f);
        }

        @Override
        public Integer leaf(SparseSegmentTreeLeaf leaf) {
            int removalCount = 0;
            for (int i=0; i < leaf.data.length; i++) {
                Object elem = leaf.data[i];
                if (elem == null) { continue; }
                if (isTrue(callMF$2(f, elem))) {
                    leaf.data[i] = null;
                    removalCount+=1;
                }
            }
            leaf.numHoles += removalCount;
            return removalCount;
        }
    }

    @Override public Object mut$removeIf$1(Object p0) {
        this.numHoles += SparseSegmentTree.explore(
                new RemoveIf(p0), this.base, 0, this.capacaity
        );
        return this;
    }
    @Override public Object mut$retainIf$1(Object p0) {
        this.numHoles += SparseSegmentTree.explore(
                new RetainIf(p0), this.base, 0, this.capacaity
        );
        return this;
    }

    @Override public Object mut$remove$1(Object p0) {
        this.numHoles -= this.base.set(natToLong(p0), null);
        return this;
    }

    @Override public Object mut$removeFirstWhere$1(Object p0) {
        SparseSegmentTree.explore(new NodeExplorer<Boolean>() {
            @Override
            public Boolean leaf(SparseSegmentTreeLeaf leaf) {
                for (int i = 0; i < leaf.data.length; i++) {
                    Object elem = leaf.data[i];
                    if (elem == null) {continue;}

                    if (isTrue(callMF$2(p0, elem))) {
                        leaf.data[i] = null;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Boolean node(SparseSegmentTreeNode node) {
                return SparseSegmentTree.explore(this, node.left, node.start, node.midPoint)
                        || SparseSegmentTree.explore(this, node.right, node.midPoint, node.end);
            }

            @Override
            public Boolean unexplored(long lowerBound, long upperBound) {
                return false;
            }
        }, this.base, 0, this.capacaity);
        return this;
    }


    @Override public Object mut$set$2(Object p0, Object p1) {
        long index = natToLong(p0);
        if (index > capacaity) {
            throw err("Index "+index+" doesn't exist in sparselist with capacity "+capacaity);
        }
        this.numHoles -= this.base.set(index, p1);
        return this;
    }

    @Override public Object mut$clear$0() {
        this.numHoles = this.capacaity;
        this.base = SparseSegmentTree.of(0, this.capacaity);
        return this;
    }


    @Override public Object mut$removeLastWhere$1(Object p0) {
        SparseSegmentTree.explore(new NodeExplorer<Boolean>() {
            @Override
            public Boolean leaf(SparseSegmentTreeLeaf leaf) {
                for (int i = leaf.data.length -1; i >= 0; i--) {
                    Object elem = leaf.data[i];
                    if (elem == null) { continue; }

                    if (isTrue(callMF$2(p0, elem))) {
                        leaf.data[i] = null;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Boolean node(SparseSegmentTreeNode node) {
                return SparseSegmentTree.explore(this, node.right, node.midPoint, node.end)
                        || SparseSegmentTree.explore(this, node.left, node.start, node.midPoint);
            }

            @Override
            public Boolean unexplored(long lowerBound, long upperBound) {
                return false;
            }
        }, this.base, 0, this.capacaity);
        return this;
    }

    @Override public Object mut$trimTo$2(Object p0, Object p1) {
        long start = natToLong(p0);
        long end = natToLong(p1);
        if (Long.compareUnsigned(this.capacaity, end) > 0) {
            throw err("End of trim is greater than the length of the list");
        }

        this.numHoles += SparseSegmentTree.explore(new NodeExplorer<Long>() {
            @Override
            public Long leaf(SparseSegmentTreeLeaf leaf) {
                // set all elements to null where the true index is < start or >= end
                // Compute the overlap between this leaf's range and [start, end)
                long overlapStart = Math.max(leaf.start, start);
                long overlapEnd = Math.min(leaf.end, end);

                int addedHoles = 0;

                // Null out everything before the overlap
                for (int i = 0; i < (int)(overlapStart - leaf.start); i++) {
                    if (leaf.data[i] != null) {
                        leaf.data[i] = null;
                        addedHoles++;
                    }
                }

                // Null out everything after the overlap
                for (int i = (int)(overlapEnd - leaf.start); i < leaf.data.length; i++) {
                    if (leaf.data[i] != null) {
                        leaf.data[i] = null;
                        addedHoles++;
                    }
                }

                leaf.numHoles += addedHoles;
                return (long) addedHoles;
            }

            @Override
            public Long node(SparseSegmentTreeNode node) {
                long addedHoles = 0;
                if (Long.compareUnsigned(node.midPoint, start) < 0 && node.left != null) {
                    addedHoles += node.left.capacity() - node.left.numHoles();
                    node.left = null;
                }
                if (Long.compareUnsigned(node.midPoint, end) > 0 && node.left != null) {
                    addedHoles += node.right.capacity() - node.right.numHoles();
                    node.right = null;
                }

                addedHoles += SparseSegmentTree.explore(this, node.left, node.start, node.midPoint);
                addedHoles += SparseSegmentTree.explore(this, node.right, node.midPoint, node.end);
                node.numHoles += addedHoles;

                return addedHoles;
            }

            @Override
            public Long unexplored(long lowerBound, long upperBound) {
                return 0L; // Do nothing
            }
        }, this.base, 0, this.capacaity);
        this.capacaity = end - start;
        return this;
    }

    /// This method is so painful as leaves aren't all the same size, consider capacity != 2^n
    @Override public Object mut$reverse$0() {
        SparseSegmentTree.explore(new NodeExplorer<Void>() {
            @Override
            public Void leaf(SparseSegmentTreeLeaf leaf) {
                for (int lo = 0, hi = leaf.data.length - 1; lo < hi; lo++, hi--) {
                    Object tmp = leaf.data[lo];
                    leaf.data[lo] = leaf.data[hi];
                    leaf.data[hi] = tmp;
                }
                return null;
            }

            @Override
            public Void node(SparseSegmentTreeNode node) {
                // Reverse children
                SparseSegmentTree.explore(this, node.left, node.start, node.midPoint);
                SparseSegmentTree.explore(this, node.right, node.midPoint, node.end);

                // swap children
                SparseSegmentTree tmp = node.left;
                node.left = node.right;
                node.right = tmp;

                // Re-key the children to their new positions within this node's range
                rekey(node.left,  node.start,   node.midPoint);
                rekey(node.right, node.midPoint, node.end);
                return null;
            }

            @Override
            public Void unexplored(long lowerBound, long upperBound) {
                return null;
            }
        }, this.base, 0, capacaity);
        return this;
    }

    private static void rekey(SparseSegmentTree tree, long newStart, long newEnd) {
        switch (tree) {
            case null -> {}
            case SparseSegmentTreeLeaf leaf -> {
                leaf.start = newStart;
                leaf.end   = newEnd;
            }
            case SparseSegmentTreeNode node -> {
                long newMid = newStart + Long.divideUnsigned(newEnd - newStart, 2);
                node.start    = newStart;
                node.end      = newEnd;
                node.midPoint = newMid;
                rekey(node.left,  newStart, newMid);
                rekey(node.right, newMid,   newEnd);
            }
        }
    }

    @Override public Object mut$mapInPlace$1(Object p0) {
        SparseSegmentTree.explore(new NodeExplorer<Void>() {
            @Override
            public Void leaf(SparseSegmentTreeLeaf leaf) {
                if (leaf.numHoles == leaf.capacity()) {
                    return null;
                }

                for (int i = 0; i<leaf.data.length; i++) {
                    Object elem = leaf.data[i];
                    if (elem == null) {
                        continue;
                    }
                    leaf.data[i] = callMF$2(p0, elem);
                }
                return null;
            }

            @Override
            public Void node(SparseSegmentTreeNode node) {
                SparseSegmentTree.explore(this, node.left, node.start, node.midPoint);
                SparseSegmentTree.explore(this, node.right, node.midPoint, node.end);

                return null;
            }

            @Override
            public Void unexplored(long lowerBound, long upperBound) {
                return null;
            }
        }, base, 0, capacaity);
        return this;
    }

    @Override public Object mut$swap$2(Object p0, Object p1) {
        // There is some optimization to be done here
        long index1 = natToLong(p0);
        long index2 = natToLong(p1);
        Object temp = this.base.get(index1);
        this.base.set(index1, this.base.get(index2));
        this.base.set(index2, temp);
        return this;
    }

    @Override public Object mut$shallow_clone$0() {
        return new ESparseSegmentList$1Instance(this);
    }
}

interface NodeExplorer<R> {
   R leaf(SparseSegmentTreeLeaf leaf);
   R node(SparseSegmentTreeNode node);
   R unexplored(long lowerBound, long upperBound);
}


class NullStream implements NodeExplorer<Stream<Object>> {
    @Override
    public Stream<Object> leaf(SparseSegmentTreeLeaf leaf) {
        return Arrays.stream(leaf.data);
    }

    @Override
    public Stream<Object> node(SparseSegmentTreeNode node) {
        return Stream.concat(
                SparseSegmentTree.explore(this, node.left, node.start, node.midPoint),
                SparseSegmentTree.explore(this, node.right, node.midPoint, node.end)
        );
    }

    @Override
    public Stream<Object> unexplored(long lowerBound, long upperBound) {
        return Stream.generate(() -> null)
                .limit(upperBound-lowerBound);
    }
}


class NonNullStream extends NullStream {
    @Override
    public Stream<Object> leaf(SparseSegmentTreeLeaf leaf) {
        if (leaf.numHoles == 0) {
            return Arrays.stream(leaf.data);
        }
        return Arrays.stream(leaf.data)
                .filter(Objects::nonNull);
    }

    @Override
    public Stream<Object> unexplored(long lowerBound, long upperBound) {
        return Stream.empty();
    }
}


sealed interface SparseSegmentTree permits SparseSegmentTreeLeaf, SparseSegmentTreeNode {
    long THRESHOLD = 512L;
    Object get(long index);
    /// Return the number of holes filled.
    int set(long index, Object value);
    static SparseSegmentTree of(long start, long end) {
        if (Long.compareUnsigned(end - start, THRESHOLD) <= 0) {
            return new SparseSegmentTreeLeaf(start, end);
        }
        return new SparseSegmentTreeNode(start, end);
    }
    long numHoles();
    static  <R> R explore(NodeExplorer<R> explorer, SparseSegmentTree tree, long start, long end) {
        return switch (tree) {
            case null -> explorer.unexplored(start, end);
            case SparseSegmentTreeNode node -> explorer.node(node);
            case SparseSegmentTreeLeaf leaf -> explorer.leaf(leaf);
        };
    };

    long capacity();
}

final class SparseSegmentTreeNode implements SparseSegmentTree {
    // Not final as reversal may happen
    long start;
    long end;
    long midPoint;
    final long size;
    long numHoles;
    SparseSegmentTree left, right;

    SparseSegmentTreeNode(long start, long end) {
        this(start, end, null, null);
    }
    SparseSegmentTreeNode(long start, long end, SparseSegmentTree left, SparseSegmentTree right) {
        this.start = start;
        this.end = end;
        this.size = end - start;
        this.midPoint = start + Long.divideUnsigned(size, 2);
        numHoles = this.size;
        this.left = left;
        this.right = right;
    }

    @Override
    public Object get(long index) {
        if (Long.compareUnsigned(index, midPoint) <= 0) {
            if (left == null) {
                return null;
            }
            return left.get(index);
        }
        if (right == null) {
            return null;
        }
        return right.get(index);
    }

    @Override
    public int set(long index, Object value) {
        int numAddedHoles = 0;
        if (Long.compareUnsigned(index, midPoint) <= 0) {
            if (left == null) {
                left = SparseSegmentTree.of(start, midPoint);
            }
            numAddedHoles = left.set(index, value);
        } else {
            if (right == null) {
                right = SparseSegmentTree.of(midPoint, end);
            }
            numAddedHoles = right.set(index, value);
        }
        this.numHoles += numAddedHoles;
        return numAddedHoles;
    }

    @Override
    public long numHoles() {
        return numHoles;
    }

    @Override
    public long capacity() {
        return end - start;
    }
}

final class SparseSegmentTreeLeaf implements SparseSegmentTree {
    // Not final as reversal may happen
    long start;
    long end;
    Object[] data;
    int numHoles;

    SparseSegmentTreeLeaf(long start, long end) {
        this.start = start;
        this.end = end;
        this.data = new Object[(int) (end - start)];
    }

    SparseSegmentTreeLeaf(long start, long end, Object[] data) {
        this.start = start;
        this.end = end;
        assert data.length == (end - start);
        this.data = data;
    }

    @Override
    public Object get(long index) {
        return data[(int) (index - start)];
    }

    @Override
    public int set(long index, Object value) {
        Object oldValue = data[(int) (index - start)];
        data[(int) (index - start)] = value;
        if (oldValue == null && value != null) {
            this.numHoles -= 1;
            return 1;
        }
        if (oldValue != null && value == null) {
            this.numHoles += 1;
            return -1;
        }
        return 0;
    }

    @Override
    public long numHoles() {
        return (long) numHoles;
    }

    @Override
    public long capacity() {
        return end - start;
    }
}



class NodeExplorers{
    static class RecursivelyFillHoles implements NodeExplorer<Integer> {
        final Object f;

        RecursivelyFillHoles(Object f) { this.f = f; }

        @Override
        public Integer leaf(SparseSegmentTreeLeaf leaf) {
            if (leaf.numHoles == 0) {
                return 0;
            }
            int holesFilled = 0;
            for (int i = 0; i < leaf.data.length; i++) {
                if (leaf.data[i] == null) {
                    leaf.data[i] = callMF$2(f, Nat$0Instance.instance(leaf.start + i));
                    holesFilled += 1;
                }
            }
            leaf.numHoles -= holesFilled;
            return holesFilled;
        }

        @Override
        public Integer node(SparseSegmentTreeNode node) {
            if (node.numHoles == 0) { return 0; }
            if (node.left == null) {
                node.left = SparseSegmentTree.of(node.start, node.midPoint);
            }
            int leftHolesFilled = SparseSegmentTree.explore(
                    this, node.left, node.start, node.midPoint
            );
            node.numHoles -= leftHolesFilled;

            if (node.numHoles == 0) { return leftHolesFilled; }
            if (node.right == null) {
                node.right = SparseSegmentTree.of(node.midPoint, node.end);
            }
            int rightFilledHoles = SparseSegmentTree.explore(this, node.right, node.midPoint, node.end);
            node.numHoles -= rightFilledHoles;

            return leftHolesFilled + rightFilledHoles;
        }

        @Override
        public Integer unexplored(long lowerBound, long upperBound) {
            throw new IllegalStateException("This method should never hit a null node");
        }
    }

    static class RecursivelyFillFrom implements NodeExplorer<Integer> {
        int currentIndex = 0;
        final Object[] array;

        RecursivelyFillFrom(Object[] array) {
            this.array = array;
        }

        @Override
        public Integer leaf(SparseSegmentTreeLeaf leaf) {
            if (leaf.numHoles == 0) {
                return 0;
            }
            int holesFilled = 0;
            int endPoint = Math.min(leaf.data.length, array.length - currentIndex);
            for (int i = 0; i < endPoint; i++) {
                if (leaf.data[i] == null) {
                    leaf.data[i] = array[currentIndex];
                    currentIndex += 1;
                    holesFilled += 1;
                }
            }
            leaf.numHoles -= holesFilled;
            return holesFilled;
        }

        @Override
        public Integer node(SparseSegmentTreeNode node) {
            if (this.currentIndex >= array.length) {
                return 0;
            }
            if (node.numHoles == 0) { return 0; }
            if (node.left == null) {
                node.left = SparseSegmentTree.of(node.start, node.midPoint);
            }
            int leftHolesFilled = SparseSegmentTree.explore(
                    this, node.left, node.start, node.midPoint
            );
            node.numHoles -= leftHolesFilled;

            if (this.currentIndex >= array.length) {
                return leftHolesFilled;
            }

            if (node.numHoles == 0) { return leftHolesFilled; }
            if (node.right == null) {
                node.right = SparseSegmentTree.of(node.midPoint, node.end);
            }
            int rightFilledHoles = SparseSegmentTree.explore(this, node.right, node.midPoint, node.end);
            node.numHoles -= rightFilledHoles;

            return leftHolesFilled + rightFilledHoles;
        }

        @Override
        public Integer unexplored(long lowerBound, long upperBound) {
            throw new IllegalStateException("This method should never hit a null node");
        }
    }
}