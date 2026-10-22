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
    final String name;
    final AWidget title;
    final AWidget content;
    boolean expanded;
    Rectangle head= new Rectangle();
    Section(String name, AWidget title, AWidget content){ this.name= name; this.title= title; this.content= content; }
  }
  record Part(Section s, boolean x){}
  final ArrayList<Section> sections= new ArrayList<>();// EDT confined
  boolean tabs;
  boolean autoCollapse= true;
  boolean scroll;
  SectionHandler$j0g$0 onToggle;
  SectionHandler$j0g$0 onClose;
  Rectangle area= new Rectangle();

  _Sections(_Frame frame){
    super(frame);
    component.setLayout(this);
  }
  static String str(Object s){ return ((Str$c$0Instance) s).val(); }
  Section named(String name){
    for (var s : sections){ if (s.name.equals(name)){ return s; } }
    var names= sections.stream().map(s -> "\"" + s.name + "\"").toList();
    throw Util.detErr("There is no section named \"" + name + "\" in this Sections: "
      + (names.isEmpty() ? "it has no sections" : "the names of its sections are " + String.join(", ", names)));
  }
  Object add(String name, AWidget title, Object scope, Function<_Frame,? extends AWidget> make){
    var c= frame.onEdtAndWait(() -> make.apply(frame));
    ((Scope$1c$1) scope).mut$run$1(c);
    frame.onEdtAndWait(() -> {
      change();
      if (sections.stream().anyMatch(s -> s.name.equals(name))){ throw Util.detErr("This Sections already has a section named \"" + name + "\": the name identifies the section, so every section of a Sections has its own name"); }
      var s= new Section(name, title, c);
      sections.add(s);
      component.add(title.component);
      component.add(c.component);
      var t= title.own();
      var k= c.own();
      grow(Math.max(lead() + t.width + trail(), k.width) + edgeW(), t.height + heightGap + k.height + edgeH());
      title.changeable = false;
      c.changeable = false;
    });
    frame.markLayoutDirty();
    return this;
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
    int h= ts.stream().mapToInt(d -> d.height).sum() + cs.stream().mapToInt(d -> d.height).sum() + heightGap * Math.max(0, 2 * n - 1);
    return new Dimension(Math.max(titleW, contentW) + edgeW(), h + edgeH());
  }
  @Override public void addLayoutComponent(String name, Component comp){}
  @Override public void removeLayoutComponent(Component comp){}
  @Override public Dimension preferredLayoutSize(Container target){ return target.getPreferredSize(); }
  @Override public Dimension minimumLayoutSize(Container target){ return target.getPreferredSize(); }
  @Override public void layoutContainer(Container target){
    if (tabs){ layoutTabs(); return; }
    int w= viewW();
    contentH = sections.stream().mapToInt(s -> s.title.own().height + (s.expanded ? heightGap + s.content.own().height : 0)).sum() + heightGap * Math.max(0, sections.size() - 1);
    scrollY = Math.clamp(scrollY, 0, maxScrollY());
    int y= top - scrollY;
    for (var s : sections){
      var t= s.title.own();
      s.head = new Rectangle(left, y, w, t.height);
      int tw= s.title.fill ? Math.max(t.width, w - lead() - trail()) : t.width;
      s.title.component.setBounds(left + lead(), y, tw, t.height);
      y += t.height + heightGap;
      if (!s.expanded){ continue; }
      var c= s.content.own();
      int cw= s.content.fill ? Math.max(c.width, w) : c.width;
      s.content.component.setBounds(left + (w - cw) / 2, y, cw, c.height);
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
    contentH = viewH();
    scrollY = 0;
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
  @Override boolean barRight(){ return scroll; }
  @Override void clipChildren(Canvas cv){
    if (!scroll){ return; }
    cv.clipRect(Rect.makeXYWH(left, top, viewW(), viewH()));
  }
  @Override void sk(Canvas cv){
    super.sk(cv);
    int save= cv.save();
    cv.clipRect(Rect.makeXYWH(left, top, viewW(), viewH()));
    for (var s : sections){ Sk.head(cv, this, s); }
    cv.restoreToCount(save);
  }
  @Override void skOver(Canvas cv){
    if (scroll){ Sk.bar(cv, this, true); }
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
    if (p.x()){
      handle(onClose, p.s());
      return true;
    }
    if (p.s().expanded){ show(p.s(), false); }
    else{ expand(p.s()); }
    component.invalidate();
    frame.markLayoutDirty();
    if (onToggle != null){ handle(onToggle, p.s()); }
    return true;
  }
  void handle(SectionHandler$j0g$0 h, Section s){
    var self= this;
    frame.frame.queue.submit(SkMouse.whileShown(s.title, new MF$7$1(){ @Override public Object mut$$hash$0(){
      return h.mut$$hash$2(self, Str$c$0Instance.instance(s.name));
    }}));
  }
  void expand(Section s){
    if (autoCollapse){ sections.stream().filter(o -> o != s).forEach(o -> show(o, false)); }
    show(s, true);
  }
  void show(Section s, boolean expanded){
    s.expanded = expanded;
    if (!expanded){ frame.mouse.detached(s.content.component); }
  }
  @Override public Object mut$section$2(Object n, Object scope){
    var name= str(n);
    var title= frame.onEdtAndWait(() -> new _Pane(frame));
    ((Scope$1c$1) scope).mut$run$1(title);
    return new CNewSection(this, name, title);
  }
  @Override public Object mut$column$0(){ return reStyle(() -> tabs = false); }
  @Override public Object mut$tabs$0(){
    return reStyle(() -> {
      if (!autoCollapse){ throw Util.detErr(".tabs is called on a Sections with .autoCollapse(False): a row of tabs shows the content of one section at a time, so tabs need .autoCollapse(True)"); }
      tabs = true;
    });
  }
  @Override public Object mut$autoCollapse$1(Object b){
    boolean v= Util.isTrue(b);
    return reStyle(() -> {
      if (!v && tabs){ throw Util.detErr(".autoCollapse(False) is called on a Sections shown as tabs: a row of tabs shows the content of one section at a time. Call .column first"); }
      autoCollapse = v;
      if (v){ sections.stream().filter(s -> s.expanded).findFirst().ifPresent(this::expand); }
    });
  }
  @Override public Object mut$scroll$1(Object b){ return reStyle(() -> scroll = Util.isTrue(b)); }
  @Override public Object mut$expand$1(Object n){
    var name= str(n);
    return reStyle(() -> expand(named(name)));
  }
  @Override public Object mut$collapse$1(Object n){
    var name= str(n);
    return reStyle(() -> show(named(name), false));
  }
  @Override public Object mut$index$2(Object n, Object i){
    var name= str(n);
    long to= Util.natToLong(i);
    return reStyle(() -> {
      var s= named(name);
      if (Long.compareUnsigned(to, sections.size()) >= 0){ throw Util.detErr(".index(\"" + name + "\", " + Long.toUnsignedString(to) + ") is out of range: this Sections has " + sections.size() + " sections, so the index is at most " + (sections.size() - 1)); }
      sections.remove(s);
      sections.add((int) to, s);
    });
  }
  @Override public Object mut$remove$1(Object n){
    var name= str(n);
    return reStyle(() -> {
      var s= named(name);
      sections.remove(s);
      for (var w : List.of(s.title, s.content)){
        frame.mouse.detached(w.component);
        component.remove(w.component);
      }
    });
  }
  @Override public Object mut$onToggle$1(Object h){ return onEdt(() -> onToggle = (SectionHandler$j0g$0) h); }
  @Override public Object mut$onClose$1(Object h){ return reStyle(() -> onClose = (SectionHandler$j0g$0) h); }
  @Override public Object read$names$0(){
    return frame.onEdtAndWait(() -> new List$o$1Instance(sections.stream().map(s -> (Object) Str$c$0Instance.instance(s.name)).toList()));
  }
  @Override public Object read$index$1(Object o){
    var name= str(o);
    return frame.onEdtAndWait(() -> n(sections.indexOf(named(name))));
  }
  @Override public Object read$isExpanded$1(Object n){
    var name= str(n);
    return frame.onEdtAndWait(() -> Util.bool(named(name).expanded));
  }
}

record CNewSection(_Sections owner, String name, AWidget title) implements NewSection$18g$0{
  @Override public Object mut$content$1(Object s){ return owner.add(name, title, s, _Pane::new); }
  @Override public Object mut$contentB$1(Object s){ return owner.add(name, title, s, _Border::new); }
  @Override public Object mut$contentS$1(Object s){ return owner.add(name, title, s, _Split::new); }
}
