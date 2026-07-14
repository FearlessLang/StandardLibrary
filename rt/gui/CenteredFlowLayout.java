package base;

import static base.Scopes.*;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Flow layout with rows centered horizontally and the block of rows centered
// vertically. Insets and gaps are read live from the owning widget, like
// MutableBorderLayout. preferredLayoutSize is the single-row size: it is what
// pack() uses to compute the natural window width. When the container is
// later given less width than that, layoutContainer wraps into multiple
// rows; the container's height must then come from heightFor(width), which
// MutableBorderLayout queries with the exact slot width it assigns —
// getPreferredSize().height alone would be the one-row height and the rows
// would overflow (clipped equally above and below by the vertical
// centering).
public final class CenteredFlowLayout implements LayoutManager, Serializable{
  private static final long serialVersionUID = 1L;

  private final AWidget gap;

  public CenteredFlowLayout(AWidget gap){ this.gap = gap; }

  @Override public void addLayoutComponent(String name, Component comp){}
  @Override public void removeLayoutComponent(Component comp){}

  @Override public Dimension preferredLayoutSize(Container target){
    synchronized (target.getTreeLock()){
      var d = oneRowSize(target);
      return new Dimension(d.width + insetsW(target), d.height + insetsH(target));
    }
  }

  // Below-preferred minimums are deliberately not supported: content wraps
  // or clips instead of shrinking.
  @Override public Dimension minimumLayoutSize(Container target){
    return preferredLayoutSize(target);
  }

  // The height this container needs when given the total width `width`:
  // wrap into rows and sum them. Deterministic within one layout pass.
  int heightFor(Container target, int width){
    synchronized (target.getTreeLock()){
      int hg = h(gap.heightGap);
      int total = 0;
      boolean first = true;
      for (var r : rows(target, width - insetsW(target))){
        total += (first ? 0 : hg) + r.height;
        first = false;
      }
      return total + insetsH(target);
    }
  }

  @Override public void layoutContainer(Container target){
    synchronized (target.getTreeLock()){
      var in = target.getInsets();
      int x0 = in.left + w(gap.left);
      int y0 = in.top + h(gap.top);
      int availW = target.getWidth() - insetsW(target);
      int availH = target.getHeight() - insetsH(target);
      int wg = w(gap.widthGap);
      int hg = h(gap.heightGap);
      var rs = rows(target, availW);
      int totalH = 0;
      boolean first = true;
      for (var r : rs){
        totalH += (first ? 0 : hg) + r.height;
        first = false;
      }
      int y = y0 + Math.max(0, (availH - totalH) / 2);
      for (var r : rs){
        int x = x0 + Math.max(0, (availW - r.width) / 2);
        for (var c : r.comps){
          var d = c.getPreferredSize();
          c.setBounds(x, y + (r.height - d.height) / 2, d.width, d.height);
          x += d.width + wg;
        }
        y += r.height + hg;
      }
    }
  }

  private record Row(List<Component> comps, int width, int height){}

  // Greedy wrap of the visible children into rows of at most availWidth;
  // a row always holds at least one component, so an oversized child gets a
  // row of its own (and clips) instead of looping.
  private List<Row> rows(Container target, int availWidth){
    var res = new ArrayList<Row>();
    int wg = w(gap.widthGap);
    var comps = new ArrayList<Component>();
    int rw = 0;
    int rh = 0;
    for (var c : target.getComponents()){
      if (!c.isVisible()){ continue; }
      var d = c.getPreferredSize();
      int cand = comps.isEmpty() ? d.width : rw + wg + d.width;
      if (!comps.isEmpty() && cand > availWidth){
        res.add(new Row(comps, rw, rh));
        comps = new ArrayList<>();
        comps.add(c);
        rw = d.width;
        rh = d.height;
      } else {
        comps.add(c);
        rw = cand;
        rh = Math.max(rh, d.height);
      }
    }
    if (!comps.isEmpty()){ res.add(new Row(comps, rw, rh)); }
    return res;
  }

  private Dimension oneRowSize(Container target){
    int wg = w(gap.widthGap);
    int wsum = 0;
    int hmax = 0;
    boolean first = true;
    for (var c : target.getComponents()){
      if (!c.isVisible()){ continue; }
      var d = c.getPreferredSize();
      wsum += (first ? 0 : wg) + d.width;
      hmax = Math.max(hmax, d.height);
      first = false;
    }
    return new Dimension(wsum, hmax);
  }

  private int insetsW(Container t){
    var in = t.getInsets();
    return in.left + in.right + w(gap.left) + w(gap.right);
  }

  private int insetsH(Container t){
    var in = t.getInsets();
    return in.top + in.bottom + h(gap.top) + h(gap.bottom);
  }
}