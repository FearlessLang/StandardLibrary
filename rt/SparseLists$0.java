package base;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static base.Util.*;

public interface SparseLists$0  extends Sealed$0{
  default Object imm$$ackedWithArray$1(Object p0){
    long capacity = natToInt(p0);
    check(
        capacity <= Integer.MAX_VALUE,
        "Internal EList capacity cannot be larger than Integer.MAX_VALUE"
    );
    return new ESparseList$1Instance((int) capacity);
  }

  default Object imm$$backedWithMap$1(Object p0){
    long capacity = natToInt(p0);
    check(
        capacity <= Integer.MAX_VALUE,
        "Internal EList capacity cannot be larger than Integer.MAX_VALUE"
    );
    return new ESparseListMap$1Instance((int) capacity);
  }

  ELists$0 instance= new ELists$0(){};
}

class ESparseList$1Instance implements ESparseList$1{
  Object[] list;
  int numHoles;
  public ESparseList$1Instance(int capacity) {
    this.list = new Object[capacity];
    this.numHoles = 0;
  }

  @Override public Object read$capacity$0() {
    return Nat$0Instance.instance(list.length);
  }

  @Override public Object read$numHoles$0() {
    return Nat$0Instance.instance(numHoles);
  }

  @Override public Object mut$increaseCapacity$1(Object p0) {
    long newCapacity = natToInt(p0);
    check(newCapacity <= Integer.MAX_VALUE, "Internal EList capacity cannot be larger than Integer.MAX_VALUE");
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
    long newCapacity = natToInt(p0);
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

  @Override public Object mut$fillFrom$1(Object p0) {
    if (numHoles == 0) { return this; }

    Stream<Object> s = ((Flow$1Instance) p0).s();
    fillAvailableHoles(s);

    return this;
  }

  @Override public Object mut$fillAndExpand$1(Object p0) {
    SizedFlow$1Instance source = (SizedFlow$1Instance) p0;
    long additionalSize = natToInt(source.read$size$0());
    check(
        additionalSize <= (Integer.MAX_VALUE - list.length),
        "This would cause the internal EList capacity to exceed Math.maxInt"
    );
    Stream<Object> s = source.s();
    this.mut$increaseCapacity$1(
        Nat$0Instance.instance(list.length + additionalSize)
    );
    fillAvailableHoles(s);

    return this;
  }

  private void fillAvailableHoles(Stream<Object> s) {
    int[] holeIndex = {0};
    s.sequential().takeWhile(_ -> numHoles > 0).forEach(e -> {
       while (holeIndex[0] < list.length){
        int i = holeIndex[0];
        if (list[i] == null) {
          list[i] = e;
          numHoles--;
          break;
        }
        holeIndex[0]++;
      }
    });
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

  @Override public Object mut$flatSeqFlow$0() {
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

  @Override public Object mut$flatFlow$1(Object p0) {
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
    long index = natToInt(p0);
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
    long index = natToInt(p0);
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
    long start = natToInt(p0);
    long end   = natToInt(p1);
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
    if (this.numHoles == this.list.length) { return this; }

    for (int i = 0; i < list.length; i++) {
      if (list[i] == null) { continue; }
      list[i] = callMF$2(p0, list[i]);
    }
    return this;
  }

  @Override public Object mut$swap$2(Object p0, Object p1) {
    long i = natToInt(p0);
    long j = natToInt(p1);
    check(0 <= i && i < list.length, "ESparseList swap index i out of range");
    check(0 <= j && j < list.length, "ESparseList swap index j out of range");
    Object tmp    = list[(int) i];
    list[(int) i] = list[(int) j];
    list[(int) j] = tmp;
    // null/non-null counts don't change
    return this;
  }

  @Override public Object mut$shallow_clone$0() {
    ESparseList$1Instance clone = new ESparseList$1Instance(list.length);
    System.arraycopy(list, 0, clone.list, 0, list.length);
    clone.numHoles = this.numHoles;
    return clone;
  }
}


