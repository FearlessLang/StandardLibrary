package base;

import static base.Scopes.*;

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
// from `gap` (an AWidget):
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
// must then come from heightFor(width), which MutableBorderLayout queries
// with the exact slot width it assigns.
public final class CenteredFlowLayout implements LayoutManager, Serializable{
  private static final long serialVersionUID = 1L;

  private final AWidget gap;

  public CenteredFlowLayout(AWidget gap){ this.gap = gap; }

  @Override public void addLayoutComponent(String name, Component comp){}
  @Override public void removeLayoutComponent(Component comp){}

  @Override public Dimension preferredLayoutSize(Container target){
    synchronized (target.getTreeLock()){
      var d = naturalSize(target);
      return new Dimension(d.width + insetsW(target), d.height + insetsH(target));
    }
  }

  // Below-preferred minimums are deliberately not supported: content wraps
  // or clips instead of shrinking.
  @Override public Dimension minimumLayoutSize(Container target){
    return preferredLayoutSize(target);
  }

  // The height this container needs when given the total width `width`:
  // wrap into lines and sum them. Deterministic within one layout pass. Only
  // meaningful for a horizontal, unchunked flow (see MutableBorderLayout's
  // only caller, wrapHeight): a vertical or chunked flow's natural height
  // does not depend on the width it is offered, so those fall back to the
  // same value preferredLayoutSize already reports.
  int heightFor(Container target, int width){
    synchronized (target.getTreeLock()){
      if (gap.vertical || gap.chunk > 0){ return preferredLayoutSize(target).height; }
      int hg = h(gap.heightGap);
      int total = 0;
      boolean first = true;
      for (var ln : lines(target, width - insetsW(target))){
        total += (first ? 0 : hg) + ln.cross();
        first = false;
      }
      return total + insetsH(target);
    }
  }

  @Override public void layoutContainer(Container target){
    synchronized (target.getTreeLock()){
      boolean vert = gap.vertical;
      var in = target.getInsets();
      int x0 = in.left + w(gap.left);
      int y0 = in.top + h(gap.top);
      int availW = target.getWidth() - insetsW(target);
      int availH = target.getHeight() - insetsH(target);
      int wg = w(gap.widthGap);
      int hg = h(gap.heightGap);
      int lineGap = vert ? wg : hg;
      var ls = lines(target, vert ? availH : availW);

      int totalCross = 0;
      boolean first = true;
      for (var ln : ls){ totalCross += (first ? 0 : lineGap) + ln.cross(); first = false; }

      int crossStart = vert
        ? x0 + Math.max(0, (availW - totalCross) / 2)
        : y0 + Math.max(0, (availH - totalCross) / 2);
      int cross = crossStart;
      for (var ln : ls){
        int primaryStart = vert
          ? y0 + Math.max(0, (availH - ln.primary()) / 2)
          : x0 + Math.max(0, (availW - ln.primary()) / 2);
        int primary = primaryStart;
        for (var c : ln.comps()){
          var d = c.getPreferredSize();
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

  private record Line(List<Component> comps, int primary, int cross){}

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
    var comps = new ArrayList<Component>();
    int lp = 0;
    int lc = 0;
    for (var c : target.getComponents()){
      if (!c.isVisible()){ continue; }
      var d = c.getPreferredSize();
      int cp = vert ? d.height : d.width;
      int cc = vert ? d.width : d.height;
      boolean breakBefore = chunk > 0
        ? !comps.isEmpty() && comps.size() >= chunk
        : !comps.isEmpty() && (lp + pg + cp) > availPrimary;
      if (breakBefore){
        res.add(new Line(comps, lp, lc));
        comps = new ArrayList<>();
        lp = 0;
        lc = 0;
      }
      lp = comps.isEmpty() ? cp : lp + pg + cp;
      lc = Math.max(lc, cc);
      comps.add(c);
    }
    if (!comps.isEmpty()){ res.add(new Line(comps, lp, lc)); }
    return res;
  }

  // The size pack() uses for the natural window size: one unwrapped line
  // when chunk is unset (so a later resize below this can wrap it), or the
  // full chunked grouping when chunk is set (already final, since it does
  // not depend on any given size).
  private Dimension naturalSize(Container target){
    boolean vert = gap.vertical;
    if (gap.chunk <= 0){
      int pg = vert ? h(gap.heightGap) : w(gap.widthGap);
      int p = 0;
      int c = 0;
      boolean first = true;
      for (var comp : target.getComponents()){
        if (!comp.isVisible()){ continue; }
        var d = comp.getPreferredSize();
        int cp = vert ? d.height : d.width;
        int cc = vert ? d.width : d.height;
        p += (first ? 0 : pg) + cp;
        c = Math.max(c, cc);
        first = false;
      }
      return vert ? new Dimension(c, p) : new Dimension(p, c);
    }
    var ls = lines(target, Integer.MAX_VALUE);
    int lineGap = vert ? w(gap.widthGap) : h(gap.heightGap);
    int totalCross = 0;
    int maxPrimary = 0;
    boolean first = true;
    for (var ln : ls){
      totalCross += (first ? 0 : lineGap) + ln.cross();
      maxPrimary = Math.max(maxPrimary, ln.primary());
      first = false;
    }
    return vert ? new Dimension(totalCross, maxPrimary) : new Dimension(maxPrimary, totalCross);
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
