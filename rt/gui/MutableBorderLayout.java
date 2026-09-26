package _base;

import static _base.Scopes.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.io.Serializable;

public final class MutableBorderLayout implements LayoutManager2, Serializable{
  private static final long serialVersionUID = 1L;

  private final AWidget gap;
  private Component north;
  private Component south;
  private Component east;
  private Component west;
  private Component center;

  public MutableBorderLayout(AWidget gap){ this.gap = gap; }

  // Current occupant of a slot, or null. Used by _Frame.addTo to evict the
  // old occupant before adding a replacement, which is what keeps the
  // duplicate-slot check in put() unreachable in practice.
  Component at(String slot){
    return switch (slot){
      case BorderLayout.NORTH -> north;
      case BorderLayout.SOUTH -> south;
      case BorderLayout.EAST -> east;
      case BorderLayout.WEST -> west;
      case BorderLayout.CENTER -> center;
      default -> throw new IllegalArgumentException(slot);
    };
  }

  @Override public void addLayoutComponent(String name, Component comp){
    addLayoutComponent(comp, name);
  }

  @Override public void addLayoutComponent(Component comp, Object constraints){
    var name = constraints == null ? BorderLayout.CENTER : constraints;
    if (!(name instanceof String s)){ throw new IllegalArgumentException("" + name); }
    switch (s){
      case BorderLayout.NORTH -> north = put(north, comp, s);
      case BorderLayout.SOUTH -> south = put(south, comp, s);
      case BorderLayout.EAST -> east = put(east, comp, s);
      case BorderLayout.WEST -> west = put(west, comp, s);
      case BorderLayout.CENTER -> center = put(center, comp, s);
      default -> throw new IllegalArgumentException(s);
    }
  }

  private Component put(Component old, Component comp, String name){
    if (old != null && old != comp){ throw new IllegalStateException("duplicate border slot: " + name); }
    return comp;
  }

  @Override public void removeLayoutComponent(Component comp){
    if (comp == north){ north = null; }
    if (comp == south){ south = null; }
    if (comp == east){ east = null; }
    if (comp == west){ west = null; }
    if (comp == center){ center = null; }
  }

  @Override public Dimension preferredLayoutSize(Container target){ return target.getPreferredSize(); }

  @Override public Dimension minimumLayoutSize(Container target){ return target.getPreferredSize(); }

  @Override public Dimension maximumLayoutSize(Container target){
    return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  @Override public float getLayoutAlignmentX(Container target){ return 0.5f; }
  @Override public float getLayoutAlignmentY(Container target){ return 0.5f; }
  @Override public void invalidateLayout(Container target){}

  @Override public void layoutContainer(Container target){ lay(target, target.getWidth(), target.getHeight(), true); }

  Dimension sizeFor(Container target, int width, int height){ return lay(target, width, height, false); }

  // Whether a gap is owed before the next slot depends on whether a slot was
  // already placed, never on whether its measured size happens to be 0: a
  // widget can legitimately have width or height 0 (Nat includes 0), and
  // that must not be mistaken for "nothing here yet" the way it would be
  // with a plain `total == 0` check.
  private Dimension lay(Container target, int width, int height, boolean place){
    synchronized (target.getTreeLock()){
      int left = w(gap.left);
      int right = width - w(gap.right);
      int top = h(gap.top);
      int bottom = height - h(gap.bottom);
      boolean middle = west != null || center != null || east != null;
      int slotsW = 0;
      int middleH = 0;
      int centerW = 0;
      if (north != null){
        var d = Sk.sizeFor(north, span(right - left), Integer.MAX_VALUE);
        if (place){ north.setBounds(left, top, span(right - left), d.height); }
        slotsW = d.width;
        top += d.height;
        if (middle || south != null){ top += h(gap.heightGap); }
      }
      if (south != null){
        var d = Sk.sizeFor(south, span(right - left), Integer.MAX_VALUE);
        bottom -= d.height;
        if (place){ south.setBounds(left, bottom, span(right - left), d.height); }
        slotsW = Math.max(slotsW, d.width);
        if (middle){ bottom -= h(gap.heightGap); }
      }
      if (west != null){
        var d = Sk.sizeFor(west, Integer.MAX_VALUE, span(bottom - top));
        if (place){ west.setBounds(left, top, d.width, span(bottom - top)); }
        middleH = d.height;
        left += d.width;
        if (center != null || east != null){ left += w(gap.widthGap); }
      }
      if (east != null){
        var d = Sk.sizeFor(east, Integer.MAX_VALUE, span(bottom - top));
        right -= d.width;
        if (place){ east.setBounds(right, top, d.width, span(bottom - top)); }
        middleH = Math.max(middleH, d.height);
        if (center != null){ right -= w(gap.widthGap); }
      }
      if (center != null){
        var d = Sk.sizeFor(center, span(right - left), span(bottom - top));
        if (place){ center.setBounds(left, top, span(right - left), span(bottom - top)); }
        middleH = Math.max(middleH, d.height);
        centerW = d.width;
      }
      return new Dimension(
        Math.max(slotsW + w(gap.left) + w(gap.right), left + centerW + (width - right)),
        top + middleH + (height - bottom));
    }
  }

  private int span(int n){ return Math.max(0, n); }
}
