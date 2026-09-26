package _base;

import static _base.Scopes.*;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Flow layout with lines centered on the cross axis and the block of lines
// centered on the flow axis. Insets and gaps are read live from the owning
// widget, like MutableBorderLayout. Two independent knobs, both read live
// from `gap` (the _Pane):
//  - gap.vertical: flow axis. false (default) flows left-to-right and
//    stacks rows top-to-bottom; true flows top-to-bottom and stacks columns
//    left-to-right.
//  - gap.chunk: line length. 0 (default) wraps by available size along the
//    flow axis, as before. >0 wraps every `chunk` children regardless of
//    available size, so the grouping is stable across resizes.
// preferredLayoutSize is the natural, unwrapped-by-size layout: with chunk
// unset that is a single line (what pack() uses to compute the natural
// window size); with chunk set it is the chunked grouping, which does not
// depend on any given size, so it already is the final layout. When the
// container is later given less size than that (chunk unset only),
// layoutContainer wraps into more lines; the container's cross-axis size
// must then come from sizeFor, which every layout queries with the exact
// size it assigns.
public final class CenteredFlowLayout implements LayoutManager, Serializable{
  private static final long serialVersionUID = 1L;

  private final _Pane gap;

  public CenteredFlowLayout(_Pane gap){ this.gap = gap; }

  @Override public void addLayoutComponent(String name, Component comp){}
  @Override public void removeLayoutComponent(Component comp){}

  // The size pack() uses for the natural window size: one unwrapped line
  // when chunk is unset (so a later resize below this can wrap it), or the
  // full chunked grouping when chunk is set (already final, since it does
  // not depend on any given size).
  @Override public Dimension preferredLayoutSize(Container target){ return target.getPreferredSize(); }

  // Below-preferred minimums are deliberately not supported: content wraps
  // or clips instead of shrinking.
  @Override public Dimension minimumLayoutSize(Container target){ return target.getPreferredSize(); }

  Dimension sizeFor(Container target, int width, int height){
    synchronized (target.getTreeLock()){
      boolean vert = gap.vertical;
      var ls = lines(target, vert ? height - insetsH() : width - insetsW());
      int cross = crossSum(ls, vert ? w(gap.widthGap) : h(gap.heightGap));
      int primary = ls.stream().mapToInt(Line::primary).max().orElse(0);
      return vert
        ? new Dimension(cross + insetsW(), primary + insetsH())
        : new Dimension(primary + insetsW(), cross + insetsH());
    }
  }

  @Override public void layoutContainer(Container target){
    synchronized (target.getTreeLock()){
      boolean vert = gap.vertical;
      int x0 = w(gap.left);
      int y0 = h(gap.top);
      int availW = target.getWidth() - insetsW();
      int availH = target.getHeight() - insetsH();
      int wg = w(gap.widthGap);
      int hg = h(gap.heightGap);
      int lineGap = vert ? wg : hg;
      var ls = lines(target, vert ? availH : availW);

      int totalCross = crossSum(ls, lineGap);

      int crossStart = vert
        ? x0 + Math.max(0, (availW - totalCross) / 2)
        : y0 + Math.max(0, (availH - totalCross) / 2);
      int cross = crossStart;
      for (var ln : ls){
        int primaryStart = vert
          ? y0 + Math.max(0, (availH - ln.primary()) / 2)
          : x0 + Math.max(0, (availW - ln.primary()) / 2);
        int primary = primaryStart;
        for (var it : ln.items()){
          var c = it.c();
          var d = it.d();
          int pSize = vert ? d.height : d.width;
          int cSize = vert ? d.width : d.height;
          int off = cross + (ln.cross() - cSize) / 2;
          if (vert){ c.setBounds(off, primary, cSize, pSize); }
          else     { c.setBounds(primary, off, pSize, cSize); }
          primary += pSize + (vert ? hg : wg);
        }
        cross += ln.cross() + lineGap;
      }
    }
  }

  private record Item(Component c, Dimension d){}

  private record Line(List<Item> items, int primary, int cross){}

  // Groups the visible children into lines. If gap.chunk > 0, every line is
  // exactly that many children (the last one may be shorter), and
  // availPrimary is ignored entirely — the grouping is then a pure function
  // of the children, independent of any container size. Otherwise, greedily
  // wraps into lines of at most availPrimary along the flow axis; a line
  // always holds at least one component, so an oversized child gets a line
  // of its own (and clips) instead of looping.
  private List<Line> lines(Container target, int availPrimary){
    boolean vert = gap.vertical;
    int chunk = gap.chunk;
    int pg = vert ? h(gap.heightGap) : w(gap.widthGap);
    var res = new ArrayList<Line>();
    var items = new ArrayList<Item>();
    int lp = 0;
    int lc = 0;
    for (var c : target.getComponents()){
      if (!c.isVisible()){ continue; }
      var d = vert ? Sk.sizeFor(c, Integer.MAX_VALUE, availPrimary) : Sk.sizeFor(c, availPrimary, Integer.MAX_VALUE);
      int cp = vert ? d.height : d.width;
      int cc = vert ? d.width : d.height;
      boolean breakBefore = chunk > 0
        ? !items.isEmpty() && items.size() >= chunk
        : !items.isEmpty() && (lp + pg + cp) > availPrimary;
      if (breakBefore){
        res.add(new Line(items, lp, lc));
        items = new ArrayList<>();
        lp = 0;
        lc = 0;
      }
      lp = items.isEmpty() ? cp : lp + pg + cp;
      lc = Math.max(lc, cc);
      items.add(new Item(c, d));
    }
    if (!items.isEmpty()){ res.add(new Line(items, lp, lc)); }
    return res;
  }

  private static int crossSum(List<Line> ls, int lineGap){
    return ls.stream().mapToInt(Line::cross).sum() + lineGap * Math.max(0, ls.size() - 1);
  }

  private int insetsW(){ return w(gap.left) + w(gap.right); }

  private int insetsH(){ return h(gap.top) + h(gap.bottom); }
}
