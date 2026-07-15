package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static base.Util.*;

public interface SparseLists$0  extends Sealed$0{
  default Object imm$backedWithArray$1(Object p0){
    long capacity = natToLong(p0);
    check(
        capacity <= Integer.MAX_VALUE,
        "Internal EList capacity cannot be larger than Integer.MAX_VALUE"
    );
    return new ESparseList$1Instance((int) capacity);
  }

  default Object imm$backedWithMap$1(Object p0){
    long capacity = natToLong(p0);
    return new ESparseListMap$1Instance((int) capacity);
  }

  default Object imm$backedWithSparse$1(Object p0){
    long capacity = natToLong(p0);
    return new ESparseSegmentList$1Instance(capacity);
  }

  ELists$0 instance= new ELists$0(){};
}

class ESparseList$1Instance implements ESparseList$1{
  Object[] list;
  int numHoles;
  public ESparseList$1Instance(int capacity) {
    this.list = new Object[capacity];
    this.numHoles = capacity;
  }

  public ESparseList$1Instance(ESparseList$1Instance elist) {
    this.list = Arrays.copyOf(elist.list, elist.list.length);
    this.numHoles = elist.numHoles;
  }

  @Override public Object mut$get$1(Object index) {
    long idx = natToLong(index);
    if (idx >= list.length) {
      throw err("Index "+index+" out of bounds for sparse list with capacity " +this.read$capacity$0());
    }
    return Util.optNullable(this.list[(int) idx]);
  }

  @Override public Object read$get$1(Object index) {
    return this.mut$get$1(index);
  }

  @Override public Object read$capacity$0() {
    return Nat$0Instance.instance(list.length);
  }

  @Override public Object read$numHoles$0() {
    return Nat$0Instance.instance(numHoles);
  }

  @Override public Object mut$increaseCapacity$1(Object p0) {
    long newCapacity = natToLong(p0);
    check(Long.compareUnsigned(newCapacity, Integer.MAX_VALUE) <= 0, "Internal EList capacity cannot be larger than Integer.MAX_VALUE");
    int addedCapacity = (int) newCapacity - this.list.length;
    if (addedCapacity < 0) {
      throw err("New capacity "+newCapacity+" cannot be smaller than current capacity "+list.length);
    }

    Object[] newList = new Object[(int) newCapacity];
    System.arraycopy(list, 0, newList, 0, list.length);
    this.numHoles = this.numHoles + addedCapacity;
    this.list = newList;
    return this;
  }

  @Override public Object mut$increaseCapacityDefensive$1(Object p0) {
    long newCapacity = natToLong(p0);
    check(newCapacity <= Integer.MAX_VALUE, "Internal EList capacity cannot be larger than Integer.MAX_VALUE");
    int cap = (int) newCapacity;
    int addedCapacity = (int) newCapacity - this.list.length;
    if (addedCapacity <= 0) {
       return this;
    }
    Object[] newList = new Object[(int) newCapacity];
    System.arraycopy(list, 0, newList, 0, list.length);
    this.numHoles = this.numHoles + addedCapacity;
    this.list = newList;
    return this;
  }

  @Override public Object mut$fillHoles$1(Object p0) {
    if (numHoles == 0) { return this; }

    for (int i = 0; i < list.length; i++) {
      if (list[i] == null) {
        list[i] = callMF$2(p0, Nat$0Instance.instance(i));
      }
    }
    numHoles = 0;
    return this;
  }


  @Override public Object mut$mapOpts$1(Object p0) {
    for (int i = 0; i < list.length; i++) {
      list[i] = optToNull((Opt$1) callMF$2(p0, optNullable(list[i])));
    }

    return this;
  }

  @Override public Object mut$fillFrom$1(Object p0) {
    if (numHoles == 0) { return this; }
    int idx = 0;
    ArrayList<Object> source = ((EList$1Instance) p0).xs;
    for (int i = 0; i < this.list.length; i++) {
        if (this.list[i] == null) {
            this.list[i] = source.get(idx);
            idx += 1;
        }
    }

    return this;
  }

  @Override public Object mut$fillAndExpand$1(Object p0) {
    SizedFlow$1Instance source = (SizedFlow$1Instance) p0;
    long additionalSize = this.numHoles - natToLong(source.read$size$0());
    check(
        additionalSize <= (Integer.MAX_VALUE - list.length),
        "This would cause the internal EList capacity to exceed Math.maxInt"
    );
    this.mut$increaseCapacity$1(
        Nat$0Instance.instance(list.length + additionalSize)
    );
    this.mut$fillFrom$1(p0);

    return this;
  }


  @Override public Object mut$seqFlowOpts$0() {
    return Flows$0.of(
        Arrays.stream(this.drain())
          .map(Util::optNullable),
        list.length
    );
  }

  @Override public Object mut$flowOpts$1(Object p0) {
    return Flows$0.of(
        Arrays.stream(this.drain())
            .map(Util::optNullable),
        list.length
    );
  }

  @Override public Object mut$seqFlowDefensive$0() {
    if (this.numHoles == 0) {
      return Flows$0.of(
          Arrays.stream(this.drain()),
          list.length
      );
    }

    return Flows$0.of(
        Arrays.stream(this.drain())
            .filter(Objects::nonNull),
        list.length
    );
  }

  @Override public Object mut$flowDefensive$1(Object p0) {
    if (this.numHoles == 0) {
      return Flows$0.of(
          Arrays.stream(this.drain()),
          list.length
      );
    }

    return Flows$0.of(
        Arrays.stream(this.drain())
            .filter(Objects::nonNull),
        list.length
    );
  }

  Object[] drain() {
    Object[] result = this.list;
    this.list = new Object[list.length];
    this.numHoles = this.list.length;
    return result;
  }

  @Override public Object mut$listExact$0() {
    if (this.numHoles != 0) {
      return err( "Cannot create a list from a ESparseList that is not full."
          + "\nUse a \"hole-safe\" flow method, or fill the holes first.");
    }

    return List$1Instance.wrap(Arrays.asList(drain()));
  }

  @Override public Object mut$all$1(Object p0) {
    if (this.numHoles == this.list.length) {
      return bool(true);
    }
    for (Object o : list) {
      if (o == null) {
        continue;
      }
      if (isFalse(o)) {
        return bool(false);
      }
    }
    return bool(true);
  }

  @Override public Object mut$any$1(Object p0) {
    if (this.numHoles == this.list.length) {
      return bool(false);
    }
    for (Object o : list) {
      if (o == null) {
        continue;
      }
      if (isTrue(o)) {
        return bool(true);
      }
    }
    return bool(false);
  }

  @Override public Object mut$none$1(Object p0) {
    if (this.numHoles == this.list.length) {
      return bool(true);
    }
    for (Object o : list) {
      if (o == null) {
        continue;
      }
      if (isTrue(o)) {
        return bool(false);
      }
    }
    return bool(true);
  }

  @Override public Object mut$firstIndexWhere$1(Object p0) {
    if (this.numHoles == this.list.length) {
      return optEmpty();
    }

    for (int i = 0; i < list.length; i++) {
      if (list[i] == null) { continue; }
      if (isTrue(callMF$2(p0, list[i]))) {
        return optSome(Nat$0Instance.instance(i));
      }
    }
    return optEmpty();
  }
  @Override public Object mut$lastIndexWhere$1(Object p0) {
    if (this.numHoles == this.list.length) {
      return optEmpty();
    }

    for (int i = list.length -1; i >= 0; i--) {
      if (list[i] == null) { continue; }
      if (isTrue(callMF$2(p0, list[i]))) {
        return optSome(Nat$0Instance.instance(i));
      }
    }
    return optEmpty();
  }

  @Override public Object mut$indicesWhere$1(Object p0) {
    if (this.numHoles == this.list.length) {
      return Flows$0.of(Stream.of());
    }
    return Flows$0.of(
        IntStream.range(0, list.length)
            .filter(i -> list[i] != null && isTrue(callMF$2(p0, list[i])))
            .mapToObj(Nat$0Instance::instance)
    );
  }


  @Override public Object mut$removeIf$1(Object p0) {
    if (this.numHoles == this.list.length) {
      return this;
    }

    for (int i = 0; i < list.length; i++) {
      if (list[i] == null) {continue;}
      if (isTrue(callMF$2(p0, list[i]))) {
        list[i] = null;
        numHoles++;
      }
    }
    return this;
  }

  @Override public Object mut$retainIf$1(Object p0) {
    if (this.numHoles == this.list.length) { return this; }

    for (int i = 0; i < list.length; i++) {
      if (list[i] == null) {continue;}
      if (isFalse(callMF$2(p0, list[i]))) {
        list[i] = null;
        numHoles++;
      }
    }
    return this;
  }

  @Override public Object mut$remove$1(Object p0) {
    long index = natToLong(p0);
    check(0 <= index && index <= list.length, "EList index out of range");
    int idx = (int) index;
    if (list[idx] != null) {
      list[idx] = null;
      numHoles++;
    }
    return this;
  }

  @Override public Object mut$removeFirstWhere$1(Object p0) {
    if (this.numHoles == this.list.length) { return this; }

    for (int i = 0; i < list.length; i++) {
      if (list[i] == null) {continue;}
      if (isTrue(callMF$2(p0, this.list[i]))) {
        list[i] = null;
        numHoles ++;
        return this;
      }
    }
    return this;
  }


  @Override public Object mut$set$2(Object p0, Object p1) {
    long index = natToLong(p0);
    check(0 <= index && index < list.length, "ESparseList index out of range");
    int idx = (int) index;
    boolean wasHole = list[idx] == null;
    list[idx] = p1;
    if (wasHole) { numHoles--; }
    return this;
  }

  @Override public Object mut$clear$0() {
    Arrays.fill(list, null);
    numHoles = list.length;
    return this;
  }



  @Override public Object mut$removeLastWhere$1(Object p0) {
    if (this.numHoles == this.list.length) { return this; }

    for (int i = list.length - 1; i >= 0; i--) {
      if (list[i] == null) { continue; }
      if (isTrue(callMF$2(p0, list[i]))) {
        list[i] = null;
        numHoles++;
        return this;
      }
    }
    return this;
  }

  @Override public Object mut$trimTo$2(Object p0, Object p1) {
    long start = natToLong(p0);
    long end = natToLong(p1);
    check(0 <= start && start <= end && end <= list.length,
        "ESparseList trimTo range out of bounds");
    int s = (int) start;
    int e = (int) end;

    Object[] newArray = Arrays.copyOfRange(list, s, e);
    int newLen = e - s;
    int removedLen = list.length - newLen;
    if (removedLen < newLen) {
      int removedHoles = 0;
      for (int i = 0; i < s; i++) { if (list[i] == null) { removedHoles++; } }
      for (int i = e; i < list.length; i++) { if (list[i] == null) { removedHoles++; } }
      this.numHoles = this.numHoles - removedHoles;
    } else {
      int remainingHoles = 0;
      for (Object o : newArray) { if (o == null) { remainingHoles++; } }
      this.numHoles = remainingHoles;
    }

    this.list = newArray;
    return this;
  }

  @Override public Object mut$reverse$0() {
    for (int lo = 0, hi = list.length - 1; lo < hi; lo++, hi--) {
      Object tmp = list[lo];
      list[lo]   = list[hi];
      list[hi]   = tmp;
    }
    return this;
  }

  @Override public Object mut$mapInPlace$1(Object p0) {
    for (int i=0; i<list.length; i++) {
      if (this.list[i] == null) {continue;}
      this.list[i] = callMF$2(p0, this.list[i]);
    }
    return this;
  }

  @Override public Object mut$swap$2(Object p0, Object p1) {
    final long index1 = natToLong(p0);
    final long index2 = natToLong(p1);
    if (index1 >= this.list.length) {
      throw err("First Index "+index1+" must be smaller than the capacity "+this.list.length);
    }
    if (index2 >= this.list.length) {
      throw err("Second Index "+index2+" must be smaller than the capacity "+this.list.length);
    }
    int i = (int) index1;
    int j = (int) index2;
    Object tmp = list[i];
    list[i] = list[j];
    list[j] = tmp;
    return this;
  }

  @Override public Object mut$shallow_clone$0() {
    return new ESparseList$1Instance(this);
  }
}

