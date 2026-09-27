package _base;

import java.util.function.Consumer;

public interface NodeExplorer<R> {
  static NodeExplorer<Void> passThroughToLeaf(Consumer<SparseSegmentLeaf> leafConsumer) {
    return new NodeExplorer<>() {
      @Override
      public Void leaf(SparseSegmentLeaf leaf) {
        leafConsumer.accept(leaf);
        return null;
      }

      @Override
      public Void node(SparseSegmentNode node) {
        node.instantiateLeft();
        node.left.explore(this);
        node.instantiateRight();
        node.right.explore(this);
        node.numHoles = node.left.numHoles() + node.right.numHoles();
        return null;
      }

      @Override
      public Void nullRegion(NullRegion nullRegion) {
        throw new IllegalStateException("Should never be able to reach here, as parents should instatiate first");
      }
    };
  }
  R leaf(SparseSegmentLeaf leaf);

  R node(SparseSegmentNode node);

  R nullRegion(NullRegion nullRegion);
}
