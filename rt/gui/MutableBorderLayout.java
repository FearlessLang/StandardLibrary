package base;

import static base.Scopes.*;

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
      return size(target, Component::getPreferredSize);
    }
  }

  @Override public Dimension minimumLayoutSize(Container target){
    synchronized (target.getTreeLock()){
      return size(target, Component::getMinimumSize);
    }
  }

  @Override public Dimension maximumLayoutSize(Container target){
    return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  @Override public float getLayoutAlignmentX(Container target){ return 0.5f; }
  @Override public float getLayoutAlignmentY(Container target){ return 0.5f; }
  @Override public void invalidateLayout(Container target){}

  @Override public void layoutContainer(Container target){
    synchronized (target.getTreeLock()){
      var a = area(target);
      int left = a.left();
      int right = a.right();
      int top = a.top();
      int bottom = a.bottom();
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

  private Area area(Container target){
    var in = target.getInsets();
    return new Area(
      in.left + w(gap.left),
      in.top + h(gap.top),
      target.getWidth() - in.right - w(gap.right),
      target.getHeight() - in.bottom - h(gap.bottom)
      );
  }

  private Dimension size(Container target, Dim dim){
    var middle = middleSize(dim);
    var total = new Dimension(middle.width, middle.height);

    if (north != null){
      var d = dim.of(north);
      total.width = Math.max(total.width, d.width);
      total.height = total.height == 0
        ? d.height
        : d.height + h(gap.heightGap) + total.height;
    }

    if (south != null){
      var d = dim.of(south);
      total.width = Math.max(total.width, d.width);
      total.height = total.height == 0
        ? d.height
        : total.height + h(gap.heightGap) + d.height;
    }

    var in = target.getInsets();
    total.width += in.left + in.right + w(gap.left) + w(gap.right);
    total.height += in.top + in.bottom + h(gap.top) + h(gap.bottom);
    return total;
  }

  private Dimension middleSize(Dim dim){
    var total = new Dimension();
    addMiddle(total, dim, west);
    addMiddle(total, dim, center);
    addMiddle(total, dim, east);
    return total;
  }

  private void addMiddle(Dimension total, Dim dim, Component c){
    if (c == null){ return; }
    var d = dim.of(c);
    if (total.width != 0){ total.width += w(gap.widthGap); }
    total.width += d.width;
    total.height = Math.max(total.height, d.height);
  }

  private record Area(int left, int top, int right, int bottom){}

  private interface Dim{
    Dimension of(Component c);
  }
}