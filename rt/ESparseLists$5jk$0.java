package _base;

import static _base.Util.*;


public interface ESparseLists$5jk$0 {
  ESparseLists$5jk$0 instance = new ESparseLists$5jk$0() {
  };

  default Object imm$backedWithArray$1(Object p0) {
    long capacity = Nat$c$0Instance.unwrap(p0);
    if (Long.compareUnsigned(capacity, Integer.MAX_VALUE) >= 0) {
      throw err("ESparseLists.backedWithArray: Cannot create a array-based Sparse List with capacity >= " + Integer.MAX_VALUE);
    }
    return new SparseArray((int) capacity);
  }

  // Capacity is a full unsigned long here; no Integer.MAX_VALUE limit.
  default Object imm$backedWithMap$1(Object p0) {
    return new SparseMap(Nat$c$0Instance.unwrap(p0));
  }

  // FIX: this used to `return null`, which turns into an NPE far away from the real problem.
  default Object imm$backedWithSparseSegmentTree$1(Object p0) {
    return new SparseSegmentTreeImpl(Nat$c$0Instance.unwrap(p0));
  }
}

