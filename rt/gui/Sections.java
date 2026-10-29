package _base;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.Rect;

import static _base.Scopes.*;

final class _Sections extends AContainer implements Sections$ao$0, LayoutManager{
  static final int mark= 16;
  static final class Section{
    AWidget title;
    AWidget content;
    boolean expanded;
    Rectangle head= new Rectangle();
    Section(AWidget title, AWidget content){ this.title= title; this.content= content; }
  }
  record Part(Section s, boolean x){}
  final ArrayList<Section> sections= new ArrayList<>();
  static final SectionHandler$j0g$0 expandClicked= new SectionHandler$j0g$0(){ @Override public Object mut$$hash$2(Object s, Object i){
    ((_Sections) s).mut$expand$1(i);
    return Void$o$0.instance;
  }};
  boolean tabs;
  SectionHandler$j0g$0 onTitleClick= expandClicked;
  SectionHandler$j0g$0 onClose;
  Rectangle area= new Rectangle();

  _Sections(_Frame frame){
    super(frame);
    component.setLayout(this);
  }
  Section at(long i){
    if (Long.compareUnsigned(i, sections.size()) < 0){ return sections.get((int) i); }
    throw Util.detErr("There is no section at index " + Long.toUnsignedString(i) + " in this Sections: "
      + (sections.isEmpty() ? "it has no sections" : "it has " + sections.size() + (sections.size() == 1 ? " section" : " sections") + ", so the index is at most " + (sections.size() - 1)));
  }
  AWidget build(Object scope, Function<_Frame,? extends AWidget> make){
    var c= frame.onEdtAndWait(() -> make.apply(frame));
    ((Scope$1c$1) scope).mut$run$1(c);
    return c;
  }
  Object add(AWidget title, AWidget c){
    frame.onEdtAndWait(() -> {
      change();
      var s= new Section(title, c);
      sections.add(s);
      component.add(title.component);
      component.add(c.component);
      grow(s);
      title.changeable = false;
      c.changeable = false;
    });
    frame.markLayoutDirty();
    return this;
  }
  void grow(Section s){
    var t= s.title.own();
    var k= s.content.own();
    grow(Math.max(lead() + t.width + trail(), k.width) + edgeW(), t.height + heightGap + k.height + edgeH());
  }
  Object replacer(long i, boolean title){ return new _RegionFiller(frame, (s, make) -> replace(i, title, build(s, make))); }
  Object replace(long i, boolean title, AWidget w){
    return reStyle(() -> {
      var s= at(i);
      var old= title ? s.title : s.content;
      frame.mouse.detached(old.component);
      component.remove(old.component);
      component.add(w.component);
      if (title){ s.title = w; } else { s.content = w; }
      grow(s);
      w.changeable = false;
    });
  }
  int lead(){ return tabs ? 0 : mark + widthGap; }
  int trail(){ return onClose == null ? 0 : widthGap + mark; }
  @Override Dimension need(){
    int n= sections.size();
    var ts= sections.stream().map(s -> s.title.own()).toList();
    var cs= sections.stream().map(s -> s.content.own()).toList();
    int contentW= cs.stream().mapToInt(d -> d.width).max().orElse(0);
    if (tabs){
      int row= ts.stream().mapToInt(d -> d.width + trail()).sum() + widthGap * Math.max(0, n - 1);
      int rowH= ts.stream().mapToInt(d -> d.height).max().orElse(0);
      int contentH= cs.stream().mapToInt(d -> d.height).max().orElse(0);
      return new Dimension(Math.max(row, contentW) + edgeW(), rowH + (n == 0 ? 0 : heightGap) + contentH + edgeH());
    }
    int titleW= ts.stream().mapToInt(d -> lead() + d.width + trail()).max().orElse(0);
    int h= ts.stream().mapToInt(d -> d.height).sum() + cs.stream().mapToInt(d -> d.height).max().orElse(0) + heightGap * n;
    return new Dimension(Math.max(titleW, contentW) + edgeW(), h + edgeH());
  }
  @Override public void addLayoutComponent(String name, Component comp){}
  @Override public void removeLayoutComponent(Component comp){}
  @Override public Dimension preferredLayoutSize(Container target){ return target.getPreferredSize(); }
  @Override public Dimension minimumLayoutSize(Container target){ return target.getPreferredSize(); }
  @Override public void layoutContainer(Container target){
    if (tabs){ layoutTabs(); return; }
    int w= viewW();
    int y= top;
    for (var s : sections){
      var t= s.title.own();
      s.head = new Rectangle(left, y, w, t.height);
      int tw= s.title.fill ? Math.max(t.width, w - lead() - trail()) : t.width;
      s.title.component.setBounds(left + lead(), y, tw, t.height);
      y += t.height + heightGap;
      if (!s.expanded){ continue; }
      var c= s.content.own();
      MutableBorderLayout.place(s.content.component, left, y, w, c.height, true, false);
      y += c.height + heightGap;
    }
  }
  void layoutTabs(){
    int rowH= sections.stream().mapToInt(s -> s.title.own().height).max().orElse(0);
    int x= left;
    for (var s : sections){
      var t= s.title.own();
      s.head = new Rectangle(x, top, t.width + trail(), rowH);
      s.title.component.setBounds(x, top + (rowH - t.height) / 2, t.width, t.height);
      x += t.width + trail() + widthGap;
    }
    int gap= sections.isEmpty() ? 0 : rowH + heightGap;
    area = new Rectangle(left, top + gap, viewW(), Math.max(0, viewH() - gap));
    for (var s : sections){
      if (s.expanded){ MutableBorderLayout.place(s.content.component, area.x, area.y, area.width, area.height, true, true); }
    }
  }
  @Override boolean hides(Component k){
    for (var s : sections){ if (s.content.component == k){ return !s.expanded; } }
    return false;
  }
  @Override Rectangle region(Component k){
    if (!tabs){ return null; }
    for (var s : sections){ if (s.content.component == k){ return area; } }
    return null;
  }
  @Override void sk(Canvas cv){
    super.sk(cv);
    int save= cv.save();
    cv.clipRect(Rect.makeXYWH(left, top, viewW(), viewH()));
    for (var s : sections){ Sk.head(cv, this, s); }
    cv.restoreToCount(save);
  }
  Part part(Point p){
    if (!view().contains(p)){ return null; }
    for (var s : sections){
      if (s.head.contains(p)){ return new Part(s, onClose != null && p.x >= s.head.x + s.head.width - mark); }
    }
    return null;
  }
  boolean click(Point press, Point release){
    var p= part(press);
    if (p == null || !p.equals(part(release))){ return false; }
    handle(p.x() ? onClose : onTitleClick, p.s());
    return true;
  }
  void handle(SectionHandler$j0g$0 h, Section s){
    var self= this;
    frame.frame.queue.submit(SkMouse.whileShown(s.title, new MF$7$1(){ @Override public Object mut$$hash$0(){
      return h.mut$$hash$2(self, frame.onEdtAndWait(() -> n(sections.indexOf(s))));
    }}));
  }
  void expand(Section s){
    sections.stream().filter(o -> o != s).forEach(o -> show(o, false));
    show(s, true);
  }
  void show(Section s, boolean expanded){
    s.expanded = expanded;
    if (!expanded){ frame.mouse.detached(s.content.component); }
  }
  @Override public Object mut$section$0(){ return new _RegionFiller(frame, (s, make) -> titled(build(s, make))); }
  Object titled(AWidget title){ return _RegionFiller.and(frame, (s, make) -> add(title, build(s, make))); }
  @Override public Object mut$tabs$1(Object b){ return reStyle(() -> tabs = Util.isTrue(b)); }
  @Override public Object mut$expand$1(Object i){
    long k= Util.natToLong(i);
    return reStyle(() -> expand(at(k)));
  }
  @Override public Object mut$collapse$1(Object i){
    long k= Util.natToLong(i);
    return reStyle(() -> show(at(k), false));
  }
  @Override public Object mut$remove$1(Object i){
    long k= Util.natToLong(i);
    return reStyle(() -> {
      var s= at(k);
      sections.remove(s);
      for (var w : List.of(s.title, s.content)){
        frame.mouse.detached(w.component);
        component.remove(w.component);
      }
    });
  }
  @Override public Object mut$title$1(Object i){ return replacer(Util.natToLong(i), true); }
  @Override public Object mut$content$1(Object i){ return replacer(Util.natToLong(i), false); }
  @Override public Object mut$onTitleClick$1(Object h){ return onEdt(() -> onTitleClick = (SectionHandler$j0g$0) h); }
  @Override public Object mut$onClose$1(Object h){ return reStyle(() -> onClose = (SectionHandler$j0g$0) h); }
  @Override public Object read$size$0(){ return frame.onEdtAndWait(() -> n(sections.size())); }
  @Override public Object read$isExpanded$1(Object i){
    long k= Util.natToLong(i);
    return frame.onEdtAndWait(() -> Util.bool(at(k).expanded));
  }
}
