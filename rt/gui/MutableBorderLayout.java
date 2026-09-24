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

  @Override public Dimension preferredLayoutSize(Container target){
    synchronized (target.getTreeLock()){
      return size();
    }
  }

  @Override public Dimension minimumLayoutSize(Container target){
    return preferredLayoutSize(target);
  }

  @Override public Dimension maximumLayoutSize(Container target){
    return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  @Override public float getLayoutAlignmentX(Container target){ return 0.5f; }
  @Override public float getLayoutAlignmentY(Container target){ return 0.5f; }
  @Override public void invalidateLayout(Container target){}

  @Override public void layoutContainer(Container target){
    synchronized (target.getTreeLock()){
      int left = w(gap.left);
      int right = target.getWidth() - w(gap.right);
      int top = h(gap.top);
      int bottom = target.getHeight() - h(gap.bottom);
      boolean middle = west != null || center != null || east != null;

      if (north != null){
        int hh = wrapHeight(north, span(right - left));
        north.setBounds(left, top, span(right - left), hh);
        top += hh;
        if (middle || south != null){ top += h(gap.heightGap); }
      }

      if (south != null){
        int hh = wrapHeight(south, span(right - left));
        bottom -= hh;
        south.setBounds(left, bottom, span(right - left), hh);
        if (middle){ bottom -= h(gap.heightGap); }
      }

      if (west != null){
        var d = west.getPreferredSize();
        west.setBounds(left, top, d.width, span(bottom - top));
        left += d.width;
        if (center != null || east != null){ left += w(gap.widthGap); }
      }

      if (east != null){
        var d = east.getPreferredSize();
        right -= d.width;
        east.setBounds(right, top, d.width, span(bottom - top));
        if (center != null){ right -= w(gap.widthGap); }
      }

      if (center != null){
        center.setBounds(left, top, span(right - left), span(bottom - top));
      }
    }
  }

  // Height for a north/south slot given the exact width it will receive. A
  // flow pane wraps into more rows when the window is narrower than its
  // one-row preferred width, so its height depends on that width; asking
  // getPreferredSize().height would return the one-row height and the
  // wrapped rows would be clipped. An explicit user .height wins over the
  // wrap-based height.
  private int wrapHeight(Component c, int width){
    if (c instanceof SkComponent s
      && s.w.preferredHeight == null
      && s.getLayout() instanceof CenteredFlowLayout f){
      return f.heightFor(s, width);
    }
    return c.getPreferredSize().height;
  }

  private int span(int n){ return Math.max(0, n); }

  // Whether a gap is owed before the next slot depends on whether a slot was
  // already placed, never on whether its measured size happens to be 0: a
  // widget can legitimately have width or height 0 (Nat includes 0), and
  // that must not be mistaken for "nothing here yet" the way it would be
  // with a plain `total == 0` check.
  private Dimension size(){
    var total = new Dimension();
    boolean hasContent = false;
    for (var c : new Component[]{ west, center, east }){
      if (c == null){ continue; }
      var d = c.getPreferredSize();
      total.width += (hasContent ? w(gap.widthGap) : 0) + d.width;
      total.height = Math.max(total.height, d.height);
      hasContent = true;
    }
    for (var c : new Component[]{ north, south }){
      if (c == null){ continue; }
      var d = c.getPreferredSize();
      total.width = Math.max(total.width, d.width);
      total.height = (hasContent ? total.height + h(gap.heightGap) : 0) + d.height;
      hasContent = true;
    }
    total.width += w(gap.left) + w(gap.right);
    total.height += h(gap.top) + h(gap.bottom);
    return total;
  }
}