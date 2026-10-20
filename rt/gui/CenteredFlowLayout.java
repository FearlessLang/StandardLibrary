package _base;

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
//  - gap.minPerLine, gap.maxPerLine: line length. A line breaks after
//    maxPerLine children, and after at least minPerLine children when the
//    next child would overflow the available size along the flow axis.
// preferredLayoutSize is the natural layout, unwrapped by size: lines break
// only at maxPerLine (what pack() uses to compute the natural window size).
// When the container is later given less size than that, layoutContainer
// wraps into more lines; the container's cross-axis size must then come
// from sizeFor, which every layout queries with the exact size it assigns.
public final class CenteredFlowLayout implements LayoutManager, Serializable{
  private static final long serialVersionUID= 1L;

  private final _Pane gap;

  public CenteredFlowLayout(_Pane gap){ this.gap= gap; }

  @Override public void addLayoutComponent(String name, Component comp){}
  @Override public void removeLayoutComponent(Component comp){}

  // The size pack() uses for the natural window size: lines broken only at
  // maxPerLine, so a later resize below this can wrap them.
  @Override public Dimension preferredLayoutSize(Container target){ return target.getPreferredSize(); }

  // Below-preferred minimums are deliberately not supported: content wraps
  // or clips instead of shrinking.
  @Override public Dimension minimumLayoutSize(Container target){ return target.getPreferredSize(); }

  Dimension sizeFor(Container target, int width, int height){
    synchronized (target.getTreeLock()){
      boolean vert= gap.vertical;
      var ls= lines(target, vert ? height - insetsH() : width - insetsW());
      int cross= crossSum(ls, vert ? gap.widthGap : gap.heightGap);
      int primary= ls.stream().mapToInt(Line::primary).max().orElse(0);
      return vert
        ? new Dimension(cross + insetsW(), primary + insetsH())
        : new Dimension(primary + insetsW(), cross + insetsH());
    }
  }

  @Override public void layoutContainer(Container target){
    synchronized (target.getTreeLock()){
      boolean vert= gap.vertical;
      int availW= target.getWidth() - insetsW();
      int availH= target.getHeight() - insetsH();
      int lineGap= vert ? gap.widthGap : gap.heightGap;
      var ls= lines(target, vert ? availH : availW);

      int totalCross= crossSum(ls, lineGap);

      int crossStart= vert
        ? gap.left + Math.max(0, (availW - totalCross) / 2)
        : gap.top + Math.max(0, (availH - totalCross) / 2);
      int cross= crossStart;
      for (var ln : ls){
        int primaryStart= vert
          ? gap.top + Math.max(0, (availH - ln.primary()) / 2)
          : gap.left + Math.max(0, (availW - ln.primary()) / 2);
        int primary= primaryStart;
        for (var it : ln.items()){
          var c= it.c();
          var d= it.d();
          int pSize= vert ? d.height : d.width;
          int cSize= vert ? d.width : d.height;
          int off= cross + (ln.cross() - cSize) / 2;
          if (vert){ c.setBounds(off, primary, cSize, pSize); }
          else{ c.setBounds(primary, off, pSize, cSize); }
          primary += pSize + (vert ? gap.heightGap : gap.widthGap);
        }
        cross += ln.cross() + lineGap;
      }
    }
  }

  private record Item(Component c, Dimension d){}

  private record Line(List<Item> items, int primary, int cross){}

  // Groups the visible children into lines, greedily: a line breaks before
  // the next child when it holds maxPerLine children, or when it holds at
  // least minPerLine children and the next child would make it longer than
  // availPrimary along the flow axis. A line holding minPerLine children
  // longer than availPrimary clips.
  private List<Line> lines(Container target, int availPrimary){
    boolean vert= gap.vertical;
    int pg= vert ? gap.heightGap : gap.widthGap;
    var res= new ArrayList<Line>();
    var items= new ArrayList<Item>();
    int lp= 0;
    int lc= 0;
    for (var c : target.getComponents()){
      if (!c.isVisible()){ continue; }
      var d= vert ? Sk.sizeFor(c, Integer.MAX_VALUE, availPrimary) : Sk.sizeFor(c, availPrimary, Integer.MAX_VALUE);
      int cp= vert ? d.height : d.width;
      int cc= vert ? d.width : d.height;
      boolean full= Long.compareUnsigned(items.size(), gap.maxPerLine) >= 0;
      boolean noRoom= Long.compareUnsigned(items.size(), gap.minPerLine) >= 0 && (lp + pg + cp) > availPrimary;
      boolean breakBefore= !items.isEmpty() && (full || noRoom);
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

  private int insetsW(){ return gap.left + gap.right; }

  private int insetsH(){ return gap.top + gap.bottom; }
}
