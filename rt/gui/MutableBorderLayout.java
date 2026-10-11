package _base;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

public final class MutableBorderLayout extends BorderLayout{
  private static final long serialVersionUID = 1L;

  private final AContainer gap;

  public MutableBorderLayout(AContainer gap){ this.gap = gap; }

  @Override public void addLayoutComponent(Component comp, Object constraints){
    assert getLayoutComponent(constraints) == null;
    super.addLayoutComponent(comp, constraints);
  }

  @Override public Dimension preferredLayoutSize(Container target){ return target.getPreferredSize(); }

  @Override public Dimension minimumLayoutSize(Container target){ return target.getPreferredSize(); }

  @Override public void layoutContainer(Container target){ lay(target, target.getWidth(), target.getHeight(), true); }

  Dimension sizeFor(Container target, int width, int height){ return lay(target, width, height, false); }

  // Whether a gap is owed before the next slot depends on whether a slot was
  // already placed, never on whether its measured size happens to be 0: a
  // widget can legitimately have width or height 0 (Nat includes 0), and
  // that must not be mistaken for "nothing here yet" the way it would be
  // with a plain `total == 0` check.
  private Dimension lay(Container target, int width, int height, boolean place){
    synchronized (target.getTreeLock()){
      var north = getLayoutComponent(NORTH);
      var south = getLayoutComponent(SOUTH);
      var west = getLayoutComponent(WEST);
      var east = getLayoutComponent(EAST);
      var center = getLayoutComponent(CENTER);
      int left = gap.left;
      int right = width - gap.right;
      int top = gap.top;
      int bottom = height - gap.bottom;
      boolean middle = west != null || center != null || east != null;
      int slotsW = 0;
      int middleH = 0;
      int centerW = 0;
      if (north != null){
        var d = Sk.sizeFor(north, span(right - left), Integer.MAX_VALUE);
        if (place){ north.setBounds(left, top, span(right - left), d.height); }
        slotsW = d.width;
        top += d.height;
        if (middle || south != null){ top += gap.heightGap; }
      }
      if (south != null){
        var d = Sk.sizeFor(south, span(right - left), Integer.MAX_VALUE);
        bottom -= d.height;
        if (place){ south.setBounds(left, bottom, span(right - left), d.height); }
        slotsW = Math.max(slotsW, d.width);
        if (middle){ bottom -= gap.heightGap; }
      }
      if (west != null){
        var d = Sk.sizeFor(west, Integer.MAX_VALUE, span(bottom - top));
        if (place){ west.setBounds(left, top, d.width, span(bottom - top)); }
        middleH = d.height;
        left += d.width;
        if (center != null || east != null){ left += gap.widthGap; }
      }
      if (east != null){
        var d = Sk.sizeFor(east, Integer.MAX_VALUE, span(bottom - top));
        right -= d.width;
        if (place){ east.setBounds(right, top, d.width, span(bottom - top)); }
        middleH = Math.max(middleH, d.height);
        if (center != null){ right -= gap.widthGap; }
      }
      if (center != null){
        var d = Sk.sizeFor(center, span(right - left), span(bottom - top));
        if (place){ center.setBounds(left, top, span(right - left), span(bottom - top)); }
        middleH = Math.max(middleH, d.height);
        centerW = d.width;
      }
      return new Dimension(
        Math.max(slotsW + gap.left + gap.right, left + centerW + (width - right)),
        top + middleH + (height - bottom));
    }
  }

  private int span(int n){ return Math.max(0, n); }
}
