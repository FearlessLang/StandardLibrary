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
// widget, like MutableBorderLayout. Three independent knobs, all read live
// from `gap` (the _Pane):
//  - gap.vertical: flow axis. false (default) flows left-to-right and
//    stacks rows top-to-bottom; true flows top-to-bottom and stacks columns
//    left-to-right.
//  - gap.minPerLine, gap.maxPerLine: line length. A line breaks after
//    maxPerLine children, and after at least minPerLine children when the
//    next child would overflow the size of the container along the flow axis.
//  - gap.centered: false places the lines, the block of lines and each child
//    in its line at the start (left, top) instead of centering them.
// Every child keeps its own size; a child with fill takes the whole flow
// axis of the container. Laying out never changes the size of the container;
// natural is the size .pack gives it: lines broken only at maxPerLine.
public final class CenteredFlowLayout implements LayoutManager, Serializable{
  private static final long serialVersionUID= 1L;

  private final _Pane gap;

  public CenteredFlowLayout(_Pane gap){ this.gap= gap; }

  @Override public void addLayoutComponent(String name, Component comp){}
  @Override public void removeLayoutComponent(Component comp){}

  @Override public Dimension preferredLayoutSize(Container target){ return target.getPreferredSize(); }

  // Below-preferred minimums are deliberately not supported: content wraps
  // or clips instead of shrinking.
  @Override public Dimension minimumLayoutSize(Container target){ return target.getPreferredSize(); }

  @Override public void layoutContainer(Container target){
    synchronized (target.getTreeLock()){
      boolean vert= gap.vertical;
      int availW= gap.viewW();
      int availH= gap.viewH();
      int lineGap= vert ? gap.widthGap : gap.heightGap;
      var ls= lines(target, vert ? availH : availW, true);

      int totalCross= crossSum(ls, lineGap);
      int longest= ls.stream().mapToInt(Line::primary).max().orElse(0);
      gap.contentW = vert ? totalCross : longest;
      gap.contentH = vert ? longest : totalCross;
      gap.scrollX = Math.clamp(gap.scrollX, 0, gap.maxScrollX());
      gap.scrollY = Math.clamp(gap.scrollY, 0, gap.maxScrollY());

      int crossStart= vert
        ? gap.left + half(availW - totalCross)
        : gap.top + half(availH - totalCross);
      int cross= crossStart;
      for (var ln : ls){
        int primaryStart= vert
          ? gap.top + half(availH - ln.primary())
          : gap.left + half(availW - ln.primary());
        int primary= primaryStart;
        for (var it : ln.items()){
          var c= it.c();
          var d= it.d();
          int pSize= vert ? d.height : d.width;
          int cSize= vert ? d.width : d.height;
          int off= cross + half(ln.cross() - cSize);
          if (vert){ c.setBounds(off - gap.scrollX, primary - gap.scrollY, cSize, pSize); }
          else{ c.setBounds(primary - gap.scrollX, off - gap.scrollY, pSize, cSize); }
          primary += pSize + (vert ? gap.heightGap : gap.widthGap);
        }
        cross += ln.cross() + lineGap;
      }
    }
  }

  private int half(int space){ return gap.centered ? Math.max(0, space) / 2 : 0; }

  private record Item(Component c, Dimension d){}

  private record Line(List<Item> items, int primary, int cross){}

  // Groups the visible children into lines, greedily: a line breaks before
  // the next child when it holds maxPerLine children, or when it holds at
  // least minPerLine children and the next child would make it longer than
  // availPrimary along the flow axis. A line holding minPerLine children
  // longer than availPrimary clips.
  Dimension natural(Container target){
    synchronized (target.getTreeLock()){
      boolean vert= gap.vertical;
      var ls= lines(target, Integer.MAX_VALUE, false);
      int cross= crossSum(ls, vert ? gap.widthGap : gap.heightGap);
      int primary= ls.stream().mapToInt(Line::primary).max().orElse(0);
      return vert
        ? new Dimension(cross + gap.edgeW(), primary + gap.edgeH())
        : new Dimension(primary + gap.edgeW(), cross + gap.edgeH());
    }
  }

  private List<Line> lines(Container target, int availPrimary, boolean fills){
    boolean vert= gap.vertical;
    int pg= vert ? gap.heightGap : gap.widthGap;
    var res= new ArrayList<Line>();
    var items= new ArrayList<Item>();
    int lp= 0;
    int lc= 0;
    for (var c : target.getComponents()){
      if (!c.isVisible()){ continue; }
      var d= size(((SkComponent) c).w, vert, availPrimary, fills);
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

  private static Dimension size(AWidget s, boolean vert, int availPrimary, boolean fills){
    var o= s.own();
    if (!s.fill || !fills){ return o; }
    return vert ? new Dimension(o.width, Math.max(o.height, availPrimary)) : new Dimension(Math.max(o.width, availPrimary), o.height);
  }

  private static int crossSum(List<Line> ls, int lineGap){
    return ls.stream().mapToInt(Line::cross).sum() + lineGap * Math.max(0, ls.size() - 1);
  }
}
