package _base;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;

public final class MutableBorderLayout{
  private final AContainer gap;
  final HashMap<String,Component> slots= new HashMap<>();

  public MutableBorderLayout(AContainer gap){ this.gap= gap; }

  // Whether a gap is owed before the next slot depends on whether a slot was
  // already placed, never on whether its measured size happens to be 0: a
  // widget can legitimately have width or height 0 (Nat includes 0), and
  // that must not be mistaken for "nothing here yet" the way it would be
  // with a plain `total == 0` check.
  void layoutContainer(Container target){
    var north= slots.get("north");
    var south= slots.get("south");
    var west= slots.get("west");
    var east= slots.get("east");
    var center= slots.get("center");
    int left= gap.left;
    int right= target.getWidth() - gap.right;
    int top= gap.top;
    int bottom= target.getHeight() - gap.bottom;
    boolean middle= west != null || center != null || east != null;
    if (north != null){
      int h= own(north).height;
      place(north, left, top, right - left, h, true, false);
      top += h;
      if (middle || south != null){ top += gap.heightGap; }
    }
    if (south != null){
      int h= own(south).height;
      bottom -= h;
      place(south, left, bottom, right - left, h, true, false);
      if (middle){ bottom -= gap.heightGap; }
    }
    if (west != null){
      int w= own(west).width;
      place(west, left, top, w, bottom - top, false, true);
      left += w;
      if (center != null || east != null){ left += gap.widthGap; }
    }
    if (east != null){
      int w= own(east).width;
      right -= w;
      place(east, right, top, w, bottom - top, false, true);
      if (center != null){ right -= gap.widthGap; }
    }
    if (center != null){ place(center, left, top, right - left, bottom - top, true, true); }
  }

  private static Dimension own(Component c){ return ((SkComponent) c).w.own(); }

  private Dimension own(String where){
    var c= slots.get(where);
    return c == null ? null : own(c);
  }

  Dimension natural(){
    var n= own("north");
    var s= own("south");
    var w= own("west");
    var e= own("east");
    var c= own("center");
    boolean middle= w != null || c != null || e != null;
    int width= 0;
    int height= 0;
    if (w != null){
      width += w.width + (c != null || e != null ? gap.widthGap : 0);
      height = w.height;
    }
    if (c != null){
      width += c.width + (e != null ? gap.widthGap : 0);
      height = Math.max(height, c.height);
    }
    if (e != null){
      width += e.width;
      height = Math.max(height, e.height);
    }
    if (n != null){
      height += n.height + (middle || s != null ? gap.heightGap : 0);
      width = Math.max(width, n.width);
    }
    if (s != null){
      height += s.height + (middle ? gap.heightGap : 0);
      width = Math.max(width, s.width);
    }
    return new Dimension(width + gap.left + gap.right, height + gap.top + gap.bottom);
  }

  static void place(Component c, int x, int y, int w, int h, boolean fillsWidth, boolean fillsHeight){
    var s= ((SkComponent) c).w;
    var o= s.own();
    int cw= s.fill && fillsWidth ? Math.max(o.width, w) : o.width;
    int ch= s.fill && fillsHeight ? Math.max(o.height, h) : o.height;
    c.setBounds(x + Math.max(0, w - cw) / 2, y + Math.max(0, h - ch) / 2, cw, ch);
  }
}
