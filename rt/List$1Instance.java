package base;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static base.Util.*;

public record List$1Instance(List<Object> val) implements List$1 {
    static Object wrap(List<Object> l) {
        return l.isEmpty() ? List$1.instance : new List$1Instance(l);
    }

    static List<Object> asJava(Object xs) {
        if (xs == List$1.instance) {
            return List.of();
        }
        if (xs instanceof List$1Instance(List<Object> list)) {
            return list;
        }
        throw new AssertionError("Unexpected List impl: " + xs.getClass());
    }

    private int idx(Object p0) {
        long i = natToInt(p0);
        check(0 <= i && i < val.size(), "List index out of range");
        return (int) i;
    }

    @Override
    public Object read$size$0() {
        return Nat$0Instance.instance(val.size());
    }

    @Override
    public Object read$isEmpty$0() {
        return bool(val.isEmpty());
    }

    @Override
    public Object read$str$1(Object p0) {
        var by = (ToStrBy$1) p0;
        String res = val.stream().map(e -> toS(by.imm$$hash$1(e))).collect(Collectors.joining(", ", "[", "]"));
        return Str$0Instance.instance(res);
    }


    @Override
    public Object mut$get$1(Object p0) {
        return val.get(idx(p0));
    }

    @Override
    public Object read$get$1(Object p0) {
        return val.get(idx(p0));
    }

    @Override
    public Object mut$opt$1(Object p0) {
        long i = natToInt(p0);
        return (0 <= i && i < val.size()) ? optSome(val.get((int) i)) : optEmpty();
    }

    @Override
    public Object read$opt$1(Object p0) {
        return mut$opt$1(p0);
    }

    @Override
    public Object imm$opt$1(Object p0) {
        return mut$opt$1(p0);
    }

    @Override
    public Object mut$first$0() {
        //can not be empty check(!val.isEmpty(), "List was empty");
        return val.get(0);
    }

    @Override
    public Object read$first$0() {
        return mut$first$0();
    }

    @Override
    public Object mut$last$0() {
        //can not be empty check(!val.isEmpty(), "List was empty");
        return val.get(val.size() - 1);
    }

    @Override
    public Object read$last$0() {
        return mut$last$0();
    }

    @Override
    public Object mut$firstOpt$0() {
        return optSome(val.get(0));
    }

    @Override
    public Object read$firstOpt$0() {
        return mut$firstOpt$0();
    }

    @Override
    public Object imm$firstOpt$0() {
        return mut$firstOpt$0();
    }

    @Override
    public Object mut$lastOpt$0() {
        return optSome(val.getLast());
    }

    @Override
    public Object read$lastOpt$0() {
        return mut$lastOpt$0();
    }

    @Override
    public Object imm$lastOpt$0() {
        return mut$lastOpt$0();
    }

    @Override
    public Object mut$$plus_plus$1(Object p0) {
        var xs = asJava(p0);
        if (xs.isEmpty()) {
            return this;
        }
        var l = new ArrayList<Object>(val.size() + xs.size());
        l.addAll(val);
        l.addAll(xs);
        return wrap(l);
    }

    @Override
    public Object read$$plus_plus$1(Object p0) {
        return mut$$plus_plus$1(p0);
    }

    @Override
    public Object mut$$lt_plus$1(Object p0) {
        var l = new ArrayList<Object>(val.size() + 1);
        l.add(p0);
        l.addAll(val);
        return wrap(l);
    }

    @Override
    public Object read$$lt_plus$1(Object p0) {
        return mut$$lt_plus$1(p0);
    }

    @Override
    public Object mut$$plus_gt$1(Object p0) {
        var l = new ArrayList<Object>(val.size() + 1);
        l.addAll(val);
        l.add(p0);
        return wrap(l);
    }

    @Override
    public Object read$$plus_gt$1(Object p0) {
        return mut$$plus_gt$1(p0);
    }

    @Override
    public Object mut$subList$2(Object p0, Object p1) {
        long s = Util.natToInt(p0);
        long e = Util.natToInt(p1);
        check(0 <= s && s <= e && e <= val.size(), "List subList out of range");
        return wrap(val.subList((int) s, (int) e));
    }

    @Override
    public Object read$subList$2(Object p0, Object p1) {
        return mut$subList$2(p0, p1);
    }

    @Override
    public Object mut$subList$1(Object p0) {
        return mut$subList$2(p0, Nat$0Instance.instance(val.size()));
    }

    @Override
    public Object read$subList$1(Object p0) {
        return mut$subList$1(p0);
    }

    @Override
    public Object mut$with$2(Object p0, Object p1) {
        int i = idx(p0);
        var l = new ArrayList<Object>(val);
        l.set(i, p1);
        return wrap(l);
    }

    @Override
    public Object read$with$2(Object p0, Object p1) {
        return mut$with$2(p0, p1);
    }

    @Override
    public Object mut$withAlso$2(Object p0, Object p1) {
        long i = natToInt(p0);
        check(0 <= i && i <= val.size(), "List withAlso out of range");
        int index = (int) i;
        var l = new ArrayList<Object>(val.size() + 1);
        l.addAll(val.subList(0, index));
        l.add(p1);
        l.addAll(val.subList(index, val.size()));
        return wrap(l);
    }

    @Override
    public Object read$withAlso$2(Object p0, Object p1) {
        return mut$withAlso$2(p0, p1);
    }

    @Override
    public Object mut$without$1(Object p0) {
        int i = idx(p0);
        if (val.size() == 1 && i == 0) {
            return List$1.instance;
        }
        if (i == 0) {
            return wrap(val.subList(1, val.size()));
        }
        if (i == val.size() - 1) {
            return wrap(val.subList(0, val.size() - 1));
        }
        var l = new ArrayList<Object>(val.size() - 1);
        l.addAll(val.subList(0, i));
        l.addAll(val.subList(i + 1, val.size()));
        return wrap(l);
    }

    @Override
    public Object read$without$1(Object p0) {
        return mut$without$1(p0);
    }

    @Override
    public Object mut$reverse$0() {
        return wrap(val.reversed());
    }

    @Override
    public Object read$reverse$0() {
        return mut$reverse$0();
    }

    @Override
    public Object mut$as$1(Object p0) {
        var l = new ArrayList<Object>(val.size());
        for (var e : val) {
            l.add(callMF$2(p0, e));
        }
        return wrap(l);
    }

    @Override
    public Object read$as$1(Object p0) {
        return mut$as$1(p0);
    }

    @Override
    public Object imm$as$1(Object p0) {
        return mut$as$1(p0);
    }

    @Override
    public Object read$imm$1(Object p0) {
        var by = (ToImmBy$2) p0;
        return new List$1Instance(val.stream().map(e -> ((ToImm$1) by.imm$$hash$1(e)).read$imm$0()).toList());
    }
}
