package _base;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import io.github.humbleui.skija.Bitmap;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Path;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.types.Rect;

import static _base.Scopes.*;

// The single Swing component class behind every widget. It never paints its
// own pixels (only the surface of the frame blits the frame image) and never has
// AWT mouse listeners: all mouse input is received by the surface and
// routed by SkMouse. No listeners on children means AWT never synthesizes
// enter/exit/disarm noise for them.
@SuppressWarnings("serial")
final class SkComponent extends JComponent{
  final AWidget w;
  SkComponent(AWidget w){
    this.w= w;
    setOpaque(false);
    setFocusable(false);
  }
  @Override public Dimension getPreferredSize(){ return w.own(); }
  @Override public Dimension getMinimumSize(){ return getPreferredSize(); }
}

final class _Button extends AWidget implements Button$2o$0{
  MF$7$1 action;// EDT confined
  boolean down;// visual pressed state, maintained by SkMouse, read by Sk.button
  boolean over;// visual rollover state, maintained by SkMouse, read by Sk.button
  // Bevel path cache, used and maintained by Sk.button; EDT confined. The
  // paths depend only on (width, height, radius, bevel depth), so a stable
  // button costs zero path allocations per frame. Stale paths of discarded
  // buttons are reclaimed by Skija's cleaner.
  Path bevelTl, bevelBr;
  int bevelW= -1, bevelH= -1, bevelR= -1, bevelD= -1;

  _Button(_Frame frame){
    super(frame);
    background = (Color$1c$0) Color$1c$0.instance.imm$rgb$3(n(190), n(190), n(190));
    bg = Sk.color(background);
  }

  @Override public Object mut$action$1(Object r){
    return onEdt(() -> action = (MF$7$1) r);
  }
  @Override Dimension need(){ return Sk.textSizeWithInsets(this); }
  @Override void sk(Canvas cv){ Sk.button(cv, this); }
}

final class _Label extends AWidget implements Label$1c$0{
  _Label(_Frame frame){ super(frame); }

  @Override Dimension need(){ return Sk.textSizeWithInsets(this); }
  @Override void sk(Canvas cv){
    Sk.background(cv, this);
    Sk.text(cv, this, 0, 0);
  }
}

final class _Picture extends AWidget implements Picture$5c$0{
  Image image;
  boolean keepAspect= true;
  boolean byHeight;

  _Picture(_Frame frame){ super(frame); }

  @Override Dimension need(){
    if (image == null){ return new Dimension(edgeW(), edgeH()); }
    return new Dimension(image.getWidth() + edgeW(), image.getHeight() + edgeH());
  }
  @Override void fit(){
    if (!keepAspect || image == null){ return; }
    if (byHeight){ width = extent(edgeW() + Math.round((double) (height - edgeH()) * image.getWidth() / image.getHeight()), "keepAspect width"); return; }
    height = extent(edgeH() + Math.round((double) (width - edgeW()) * image.getHeight() / image.getWidth()), "keepAspect height");
  }
  @Override void sk(Canvas cv){
    Sk.background(cv, this);
    if (image == null){ return; }
    int cw= component.getWidth() - edgeW();
    int ch= component.getHeight() - edgeH();
    float s= Math.max((float) cw / image.getWidth(), (float) ch / image.getHeight());
    float iw= keepAspect ? image.getWidth() * s : cw;
    float ih= keepAspect ? image.getHeight() * s : ch;
    int save= cv.save();
    cv.clipRect(Rect.makeXYWH(left, top, cw, ch));
    cv.drawImageRect(image, Rect.makeWH(image.getWidth(), image.getHeight()), Rect.makeXYWH(left + (cw - iw) / 2, top + (ch - ih) / 2, iw, ih), SamplingMode.LINEAR, null, true);
    cv.restoreToCount(save);
  }
  @Override public Object mut$image$1(Object i){
    var img= ((Image$1c$0Instance) i).image;
    return reStyle(() -> { image = img; grow(img.getWidth() + edgeW(), img.getHeight() + edgeH()); });
  }
  @Override public Object mut$keepAspect$1(Object b){ return reStyle(() -> keepAspect = Util.isTrue(b)); }
  @Override public Object read$keepAspect$0(){ return Util.bool(keepAspect); }
  @Override public Object mut$width$p1$1(Object w){ byHeight = false; return super.mut$width$p1$1(w); }
  @Override public Object mut$height$p1$1(Object h){ byHeight = true; return super.mut$height$p1$1(h); }
}

final class _Pane extends AContainer implements Pane$o$0{
  boolean vertical= false;
  long minPerLine= 1;
  long maxPerLine= 0xFFFF_FFFF_FFFF_FFFFL;
  boolean lineScroll;
  boolean stackScroll;
  boolean centered= true;

  _Pane(_Frame frame){
    super(frame);
    component.setLayout(new CenteredFlowLayout(this));
  }
  @Override Dimension need(){ return ((CenteredFlowLayout) component.getLayout()).natural(component); }
  @Override public Object mut$button$1(Object s){ return frame.addTo(this, null, s, _Button::new); }
  @Override public Object mut$label$1(Object s){ return frame.addTo(this, null, s, _Label::new); }
  @Override public Object mut$picture$1(Object s){ return frame.addTo(this, null, s, _Picture::new); }
  @Override public Object mut$pane$1(Object s){ return frame.addTo(this, null, s, _Pane::new); }
  @Override public Object mut$border$1(Object s){ return frame.addTo(this, null, s, _Border::new); }
  @Override public Object mut$split$0(){ return _RegionFiller.split(frame, (s, make) -> frame.addTo(this, null, s, make)); }
  @Override public Object mut$textField$1(Object s){ return frame.addTo(this, null, s, _TextField::new); }
  @Override public Object mut$textArea$1(Object s){ return frame.addTo(this, null, s, _TextArea::new); }
  @Override public Object mut$sections$1(Object s){ return frame.addTo(this, null, s, _Sections::new); }
  @Override public Object mut$clear$0(){
    return reStyle(() -> {
      // Removed widgets get no Exited events (DOM semantics: removal is not
      // an exit); SkMouse just forgets its references into the subtrees.
      // Handler tasks already queued for removed widgets are skipped.
      for (var c : component.getComponents()){ frame.mouse.detached((SkComponent) c); }
      component.removeAll();
    });
  }
  @Override public Object mut$lineScroll$1(Object b){ return reStyle(() -> lineScroll = Util.isTrue(b)); }
  @Override public Object mut$stackScroll$1(Object b){ return reStyle(() -> stackScroll = Util.isTrue(b)); }
  @Override public Object mut$centered$1(Object b){ return reStyle(() -> centered = Util.isTrue(b)); }
  @Override boolean barRight(){ return vertical ? lineScroll : stackScroll; }
  @Override boolean barBottom(){ return vertical ? stackScroll : lineScroll; }
  @Override void clipChildren(Canvas cv){
    if (!barRight() && !barBottom()){ return; }
    cv.clipRect(io.github.humbleui.types.Rect.makeXYWH(left, top, viewW(), viewH()));
  }
  @Override void skOver(Canvas cv){
    if (barRight()){ Sk.bar(cv, this, true); }
    if (barBottom()){ Sk.bar(cv, this, false); }
  }
  @Override public Object mut$horizontal$0(){ return reStyle(() -> vertical = false); }
  @Override public Object mut$vertical$0(){ return reStyle(() -> vertical = true); }
  @Override public Object mut$minPerLine$1(Object n){
    long v= perLine(n, "minPerLine");
    return reStyle(() -> {
      if (Long.compareUnsigned(v, maxPerLine) > 0){ throw Util.detErr("minPerLine " + Long.toUnsignedString(v) + " is more than maxPerLine " + Long.toUnsignedString(maxPerLine) + " of this Pane: no line can hold at least " + Long.toUnsignedString(v) + " children and at most " + Long.toUnsignedString(maxPerLine)); }
      minPerLine = v;
    });
  }
  @Override public Object mut$maxPerLine$1(Object n){
    long v= perLine(n, "maxPerLine");
    return reStyle(() -> {
      if (Long.compareUnsigned(v, minPerLine) < 0){ throw Util.detErr("maxPerLine " + Long.toUnsignedString(v) + " is less than minPerLine " + Long.toUnsignedString(minPerLine) + " of this Pane: no line can hold at least " + Long.toUnsignedString(minPerLine) + " children and at most " + Long.toUnsignedString(v)); }
      maxPerLine = v;
    });
  }
  private static long perLine(Object n, String what){
    long v= Util.natToLong(n);
    if (v == 0){ throw Util.detErr(what + " 0 is too small; it must be >= 1: every line holds at least one child"); }
    return v;
  }
}

final class _Border extends AContainer implements Border$2o$0{
  _Border(_Frame frame){
    super(frame);
    component.setLayout(new MutableBorderLayout(this));
  }
  @Override Dimension need(){ return ((MutableBorderLayout) component.getLayout()).natural(component); }
  @Override public Object mut$north$0(){ return frame.region(this, BorderLayout.NORTH); }
  @Override public Object mut$south$0(){ return frame.region(this, BorderLayout.SOUTH); }
  @Override public Object mut$east$0(){ return frame.region(this, BorderLayout.EAST); }
  @Override public Object mut$west$0(){ return frame.region(this, BorderLayout.WEST); }
  @Override public Object mut$center$0(){ return frame.region(this, BorderLayout.CENTER); }
}

@SuppressWarnings("serial")
final class _Split extends AContainer implements Split$1c$0{
  boolean vertical;
  boolean draggable= true;
  int position;

  _Split(_Frame frame){
    super(frame);
    component.setLayout(new BorderLayout(){ @Override public void layoutContainer(Container target){ layout(); } });
  }
  Component side(String where){ return ((BorderLayout) component.getLayout()).getLayoutComponent(where); }
  int span(){ return vertical ? viewH() : viewW(); }
  @Override Rectangle region(Component k){
    boolean first= k == side(BorderLayout.LINE_START);
    int from= first ? 0 : position + bar;
    int length= Math.max(0, first ? Math.min(position, span()) : span() - from);
    return vertical ? new Rectangle(left, top + from, viewW(), length) : new Rectangle(left + from, top, length, viewH());
  }
  Rectangle grip(){
    var g= vertical ? new Rectangle(left, top + position, viewW(), bar) : new Rectangle(left + position, top, bar, viewH());
    return g.intersection(view());
  }
  void drag(int want){
    position = Math.clamp(want, 0, Math.max(0, span() - bar));
    component.invalidate();
    frame.markLayoutDirty();
  }
  void layout(){
    for (var k : component.getComponents()){
      var r= region(k);
      MutableBorderLayout.place(k, r.x, r.y, r.width, r.height, true, true);
    }
  }
  @Override Dimension need(){
    var a= new Dimension(0, 0);
    for (var c : component.getComponents()){
      var o= ((SkComponent) c).w.own();
      a = vertical ? new Dimension(Math.max(a.width, o.width), a.height + o.height) : new Dimension(a.width + o.width, Math.max(a.height, o.height));
    }
    return vertical ? new Dimension(a.width + edgeW(), a.height + bar + edgeH()) : new Dimension(a.width + bar + edgeW(), a.height + edgeH());
  }
  @Override public Object mut$pack$0(){
    reStyle(() -> {
      var c= side(BorderLayout.LINE_START);
      var o= c == null ? new Dimension(0, 0) : ((SkComponent) c).w.own();
      position = vertical ? o.height : o.width;
    });
    return super.mut$pack$0();
  }
  @Override void skOver(Canvas cv){ Sk.divider(cv, this); }
  @Override public Object mut$horizontal$0(){ return reStyle(() -> vertical = false); }
  @Override public Object mut$vertical$0(){ return reStyle(() -> vertical = true); }
  @Override public Object mut$dividerPosition$1(Object n){ return reStyle(extent(n, "dividerPosition"), v -> position = v); }
  @Override public Object read$dividerPosition$0(){ return n(frame.onEdtAndWait(() -> position)); }
  @Override public Object mut$draggable$1(Object b){ return reStyle(() -> draggable = Util.isTrue(b)); }
  @Override public Object read$draggable$0(){ return Util.bool(draggable); }
  @Override public Object mut$first$0(){ return frame.region(this, BorderLayout.LINE_START); }
  @Override public Object mut$second$0(){ return frame.region(this, BorderLayout.LINE_END); }
}

final class _RegionFiller implements RegionFiller$4rk$1{
  interface Sink{ Object add(Object scope, Function<_Frame,? extends AWidget> make); }
  private final _Frame frame;
  private final Sink sink;
  _RegionFiller(_Frame frame, Sink sink){ this.frame= frame; this.sink= sink; }
  @Override public Object mut$pane$1(Object s){ return sink.add(s, _Pane::new); }
  @Override public Object mut$border$1(Object s){ return sink.add(s, _Border::new); }
  @Override public Object mut$split$0(){ return split(frame, sink); }
  @Override public Object mut$sections$1(Object s){ return sink.add(s, _Sections::new); }
  @Override public Object mut$button$1(Object s){ return sink.add(s, _Button::new); }
  @Override public Object mut$label$1(Object s){ return sink.add(s, _Label::new); }
  @Override public Object mut$picture$1(Object s){ return sink.add(s, _Picture::new); }
  @Override public Object mut$textField$1(Object s){ return sink.add(s, _TextField::new); }
  @Override public Object mut$textArea$1(Object s){ return sink.add(s, _TextArea::new); }
  static Object and(_Frame frame, Sink sink){
    return new And$c$1(){ @Override public Object mut$and$0(){ return new _RegionFiller(frame, sink); } };
  }
  static Object split(_Frame frame, Sink sink){
    return new _RegionFiller(frame, (first, m1) -> and(frame, (second, m2) -> with(frame, sink, first, m1, second, m2)));
  }
  private static Object with(_Frame frame, Sink sink, Object first, Function<_Frame,? extends AWidget> m1, Object second, Function<_Frame,? extends AWidget> m2){
    return new SplitWith$lk$1(){ @Override public Object mut$with$1(Object s){
      return sink.add(new Scope$1c$1(){ @Override public Object mut$run$1(Object w){
        frame.addTo((AWidget) w, BorderLayout.LINE_START, first, m1);
        frame.addTo((AWidget) w, BorderLayout.LINE_END, second, m2);
        return ((Scope$1c$1) s).mut$run$1(w);
      }}, _Split::new);
    }};
  }
}

abstract class AWidget implements Widget$2o$1{
  int left= 6;
  int top= 6;
  int right= 6;
  int bottom= 6;
  int radius= 12;
  int width= 24;
  int height= 24;
  boolean fill;
  Color$1c$0 foreground= (Color$1c$0) Color$1c$0.instance;
  Color$1c$0 background= (Color$1c$0) Color$1c$0.instance.imm$transparent$0();
  int fg= Sk.color(foreground);
  int bg= Sk.color(background);
  int textSize= 12;
  double above= 1;
  double below= 0.5;
  Sk.Line line;
  static final int bar= 5;
  int scrollX;
  int scrollY;
  int contentW;
  int contentH;
  // Read live by Sk.textSizeWithInsets/Sk.text; only _Button and _Label
  // expose Fearless methods to change it, but it lives here alongside the
  // other style fields.
  String text= "";
  String tooltip= "";
  Object contextMenu;

  final _Frame frame;
  final SkComponent component= new SkComponent(this);
  // Fearless mouse handlers per event kind; EDT confined, read by SkMouse.
  EnumMap<MouseKind,MouseHandler$4sg$0> handlers= new EnumMap<>(MouseKind.class);
  boolean changeable= true;// EDT confined
  String boundsWarning;
  String overlapWarning;
  String textWarning;
  String paintWarning;

  AWidget(_Frame frame){ this.frame= frame; }

  boolean canChange(){
    return changeable || this == frame.top || component.getParent() instanceof SkComponent p && p.w.canChange();
  }

  void change(){
    if (frame.frame.completed()){ throw Util.detErr("This " + kind() + " is in a closed window: the CapGui.run that showed the window has returned, so changing it would have no visible effect"); }
    if (canChange()){ return; }
    throw Util.detErr("This " + getClass().getSimpleName().substring(1)
      + " is not in the window any more: .clear, a replaced Border slot or a new .content removed it,"
      + " so changing it would have no visible effect. Change a widget that is not in the window inside .changeDetached{...}");
  }

  @Override public Object mut$changeDetached$1(Object s){
    boolean was= frame.onEdtAndWait(() -> { var c= changeable; changeable = true; return c; });
    ((Scope$1c$1) s).mut$run$1(mut$self$0());
    frame.onEdtAndWait(() -> changeable = was);
    return mut$self$0();
  }

  abstract void sk(Canvas cv);
  String kind(){ return getClass().getSimpleName().substring(1); }
  String describe(){ return "a " + kind() + (text.isEmpty() ? "" : " \"" + text + "\""); }
  String warned(String was, String now){
    if (was == null && now != null){ frame.frame.warn(now); }
    return now;
  }
  abstract Dimension need();
  void fit(){}
  void clipChildren(Canvas cv){}
  boolean hides(Component k){ return false; }
  Rectangle region(Component k){ return null; }
  boolean barRight(){ return false; }
  boolean barBottom(){ return false; }
  int edgeW(){ return left + right + (barRight() ? bar : 0); }
  int edgeH(){ return top + bottom + (barBottom() ? bar : 0); }
  int viewW(){ return component.getWidth() - edgeW(); }
  int viewH(){ return component.getHeight() - edgeH(); }
  Rectangle view(){ return new Rectangle(left, top, viewW(), viewH()); }
  int maxScrollX(){ return barBottom() ? Math.max(0, contentW - viewW()) : 0; }
  int maxScrollY(){ return barRight() ? Math.max(0, contentH - viewH()) : 0; }
  boolean scrollTo(int x, int y){
    x = Math.clamp(x, 0, maxScrollX());
    y = Math.clamp(y, 0, maxScrollY());
    if (x == scrollX && y == scrollY){ return false; }
    scrollX = x;
    scrollY = y;
    component.invalidate();
    frame.markLayoutDirty();
    return true;
  }
  Rectangle track(boolean vert){
    return vert
      ? new Rectangle(component.getWidth() - right - bar, top, bar, viewH())
      : new Rectangle(left, component.getHeight() - bottom - bar, viewW(), bar);
  }
  Rectangle thumb(boolean vert){
    var t= track(vert);
    int length= vert ? t.height : t.width;
    int view= vert ? viewH() : viewW();
    int content= Math.max(view, vert ? contentH : contentW);
    int size= thumbLength(length, view, content);
    int at= (int) ((long) (length - size) * (vert ? scrollY : scrollX) / Math.max(1, content - view));
    return vert ? new Rectangle(t.x, t.y + at, bar, size) : new Rectangle(t.x + at, t.y, size, bar);
  }
  static int thumbLength(int length, int view, int content){ return Math.min(length, Math.max(Math.min(4 * bar, length / 2), (int) ((long) length * view / Math.max(1, content)))); }
  int scrollFor(boolean vert, int thumbAt){
    var t= track(vert);
    int size= vert ? thumb(vert).height : thumb(vert).width;
    int length= (vert ? t.height : t.width) - size;
    int max= vert ? maxScrollY() : maxScrollX();
    return (int) ((long) thumbAt * max / Math.max(1, length));
  }
  void skOver(Canvas cv){}
  Dimension own(){ return new Dimension(width, height); }
  void grow(int w, int h){
    width = Math.max(width, w);
    height = Math.max(height, h);
  }
  void insetsFit(String setter, int l, int r, int t, int b, int w, int h){
    if (l + r > w){ throw Util.detErr(setter + " would make the insets of this " + kind() + " larger than the " + kind() + ": leftInset " + l + " + rightInset " + r + " is more than width " + w); }
    if (t + b > h){ throw Util.detErr(setter + " would make the insets of this " + kind() + " larger than the " + kind() + ": topInset " + t + " + bottomInset " + b + " is more than height " + h); }
  }

  Object onEdt(Runnable r){
    frame.onEdtAndWait(() -> { change(); r.run(); });
    return mut$self$0();
  }

  Object reStyle(Runnable r){
    // Invalidate only this component: invalidation propagates upward on its
    // own, and _Frame.tick re-lays-out just the invalid path, so unchanged
    // widgets keep their exact bounds.
    frame.onEdtAndWait(() -> {
      change();
      r.run();
      fit();
      component.invalidate();
    });
    frame.markLayoutDirty();
    return mut$self$0();
  }

  Object reStyle(int n, IntConsumer set){ return reStyle(() -> set.accept(n)); }

  Object reText(Runnable r){
    return reStyle(() -> {
      r.run();
      line = null;
      var n= need();
      grow(n.width, n.height);
    });
  }

  @Override public Object mut$topInset$p1$1(Object v){ return reStyle(extent((HeightNat$lg$0) v, "top inset"), n -> { insetsFit("topInset " + n, left, right, n, bottom, width, height); top = n; }); }
  @Override public Object mut$bottomInset$p1$1(Object v){ return reStyle(extent((HeightNat$lg$0) v, "bottom inset"), n -> { insetsFit("bottomInset " + n, left, right, top, n, width, height); bottom = n; }); }
  @Override public Object mut$leftInset$p1$1(Object v){ return reStyle(extent((WidthNat$as$0) v, "left inset"), n -> { insetsFit("leftInset " + n, n, right, top, bottom, width, height); left = n; }); }
  @Override public Object mut$rightInset$p1$1(Object v){ return reStyle(extent((WidthNat$as$0) v, "right inset"), n -> { insetsFit("rightInset " + n, left, n, top, bottom, width, height); right = n; }); }
  @Override public Object mut$width$p1$1(Object w){ return reStyle(extent((WidthNat$as$0) w, "width"), n -> { insetsFit("width " + n, left, right, top, bottom, n, height); width = n; }); }
  @Override public Object mut$height$p1$1(Object h){ return reStyle(extent((HeightNat$lg$0) h, "height"), n -> { insetsFit("height " + n, left, right, top, bottom, width, n); height = n; }); }
  @Override public Object mut$pack$0(){
    return reStyle(() -> {
      var n= need();
      width = n.width;
      height = n.height;
    });
  }
  @Override public Object mut$fill$0(){ return reStyle(() -> fill = true); }
  @Override public Object mut$noFill$0(){ return reStyle(() -> fill = false); }
  @Override public Object mut$radius$1(Object r){ return reStyle(extent(r, "radius"), n -> radius = n); }
  public Object mut$textHeight$p1$1(Object t){
    int n= extent((HeightNat$lg$0) t, "text height");
    return reText(() -> textSize = n);
  }
  public Object read$textHeight$0(){ return h(textSize); }
  public Object mut$aboveBaseline$p1$1(Object r){ return reText(() -> above = ratio(r)); }
  public Object mut$belowBaseline$p1$1(Object r){ return reText(() -> below = ratio(r)); }
  public Object read$aboveBaseline$0(){ return ratio(above); }
  public Object read$belowBaseline$0(){ return ratio(below); }
  public Object mut$uText$1(Object t){
    var s= ustr(t);
    var b= lineBreak(s);
    if (b != null){ throw Util.detErr("The new text of this " + kind() + " has " + b + ": the text of a " + kind() + " is one line"); }
    return reText(() -> text = s);
  }
  public Object read$uText$0(){ return UStr$s$0Instance.instance(text); }
  @Override public Object mut$foreground$1(Object c){ return onEdt(() -> fg = Sk.color(foreground = (Color$1c$0) c)); }
  @Override public Object mut$background$1(Object c){ return onEdt(() -> bg = Sk.color(background = (Color$1c$0) c)); }
  @Override public Object mut$uTooltip$1(Object t){
    var s= ustr(t);
    return onEdt(() -> tooltip = s);
  }
  @Override public Object read$uTooltip$0(){ return UStr$s$0Instance.instance(tooltip); }
  @Override public Object mut$contextMenu$1(Object s){ return onEdt(() -> contextMenu = s); }
  @Override public Object mut$openMenu$1(Object s){
    var m= CMenu.build(s);
    frame.onEdtAndWait(() -> {
      if (frame.frame.completed()){ throw Util.detErr("This " + kind() + " is in a closed window: the CapGui.run that showed the window has returned, so .openMenu has no window to open a menu in"); }
      if (!frame.started){ throw Util.detErr(".openMenu opens a menu in the open window, and this window is not open yet: the consumer given to CapGui.run has not returned"); }
      if (!SwingUtilities.isDescendingFrom(component, frame.top.component)){ throw Util.detErr("This " + kind() + " is not in the window, so .openMenu has no place below it to open a menu: .clear, a replaced Border slot or a new .content removed it, or the scope that adds it has not returned"); }
      var at= SwingUtilities.convertPoint(component, 0, 0, frame.top.component);
      frame.menus.show(m, new Rectangle(at.x, at.y, component.getWidth(), component.getHeight()));
    });
    return Void$o$0.instance;
  }
  @Override public Object read$topInset$0(){ return h(top); }
  @Override public Object read$bottomInset$0(){ return h(bottom); }
  @Override public Object read$leftInset$0(){ return w(left); }
  @Override public Object read$rightInset$0(){ return w(right); }
  @Override public Object read$width$0(){ return w(width); }
  @Override public Object read$height$0(){ return h(height); }
  @Override public Object read$fill$0(){ return Util.bool(fill); }
  @Override public Object read$radius$0(){ return n(radius); }
  @Override public Object read$foreground$0(){ return foreground; }
  @Override public Object read$background$0(){ return background; }
}

abstract class AContainer extends AWidget implements _Container$lc$1{
  int widthGap= 6;
  int heightGap= 6;
  Painter$5c$0 paint= Scopes.idP;

  AContainer(_Frame frame){ super(frame); }

  @Override void sk(Canvas cv){
    Sk.background(cv, this);
    if (paint == Scopes.idP){ return; }
    try (var p= new Paint().setAntiAlias(true)){
      var g= new CGraphicsCtx(
        cv,
        frame.elapsed,
        Scopes.w(component.getWidth()),
        Scopes.h(component.getHeight()),
        p,
        foreground
      );
      paint.imm$run$1(g);
      paintWarning = warned(paintWarning, g.outside(describe(), "widget", component.getWidth(), component.getHeight()));
    }
  }

  @Override public Object mut$heightGap$p1$1(Object v){ return reStyle(extent((HeightNat$lg$0) v, "height gap"), n -> heightGap = n); }
  @Override public Object mut$widthGap$p1$1(Object v){ return reStyle(extent((WidthNat$as$0) v, "width gap"), n -> widthGap = n); }
  @Override public Object read$heightGap$0(){ return h(heightGap); }
  @Override public Object read$widthGap$0(){ return w(widthGap); }
  @Override public Object mut$mouse$1(Object s){
    var b= new CMouseBuilder(this);
    ((Scope$1c$1) s).mut$run$1(b);
    frame.onEdtAndWait(() -> {
      change();
      handlers = b.handlers;// .mouse replaces earlier handlers
      b.changeable = false;
    });
    return mut$self$0();
  }

  @Override public Object mut$paint$1(Object p){ return onEdt(() -> paint = (Painter$5c$0) p); }
}

final class _Frame implements Frame$1c$0{
  final FearlessFrame frame;
  final _FluentGUI gui;
  final SkMouse mouse= new SkMouse(this);
  final CTextKeys textKeys= new CTextKeys(this);
  final SkMenus menus= new SkMenus(this);
  @SuppressWarnings("serial")
  final JComponent surface= new JComponent(){
    @Override protected void paintComponent(java.awt.Graphics g){ blit(g); }
    @Override public java.awt.im.InputMethodRequests getInputMethodRequests(){ return textKeys; }
  };
  AEditText focus;// EDT confined
  boolean keyboard;// EDT confined
  // Written by the repaint tick on the EDT, read by painters on the EDT and
  // by the model (read .elapsed) on the queue thread with no ordering between
  // the two: volatile makes the reference handoff safe. The Time object
  // itself is immutable.
  volatile Instant$5c$0 elapsed= time(0);
  AWidget top;
  Stage stage;
  private long startNanos= System.nanoTime();// re-based in start(): game time zero = warmup end
  private int fps= 30;
  private long modelPeriodNs;
  private ArrayList<MF$7$1> modelTickActions= new ArrayList<>();// live, EDT confined
  private float opacity= 1f;
  private String title= "";
  private int canvasW= 1000;
  private int canvasH= 1000;
  private int bands= 0xFF000000;
  private Consumer$ao$1 onResize;
  private MF$7$1 onClose;
  private final AtomicReference<ResizeRequest$9j4$0> pendingRequest= new AtomicReference<>();
  // True once start() completed. Written on the EDT at the end of start().
  // Pre-start readers run on the launcher thread strictly before start() is
  // scheduled; post-start readers are queue tasks whose submission (from the
  // EDT) happens after the write, so the queue handoff gives the
  // happens-before. Single mutator at every point in time: no volatile.
  boolean started;
  private final AtomicBoolean layoutDirty= new AtomicBoolean();
  private Bitmap bitmap;
  private Surface gpu;
  private Canvas canvas;
  private java.awt.image.BufferedImage bimg;
  private java.nio.IntBuffer pixels;
  private int renderLogicalW;
  private int renderLogicalH;

  _Frame(CompletableFuture<Void> done, _FluentGUI gui){
    frame= new FearlessFrame(done, this);
    this.gui= gui;
    surface.setLayout(null);
    surface.setOpaque(true);
    surface.setFocusable(true);
    surface.setFocusTraversalKeysEnabled(false);
    surface.addKeyListener(textKeys);
    surface.addFocusListener(textKeys);
    surface.addInputMethodListener(textKeys);
    surface.enableInputMethods(true);
  }

  void pressed(AWidget d){
    if (focus == null || focus == d){ return; }
    focus.commit();
    focus = null;
  }

  void tab(boolean back){
    var all= new ArrayList<AEditText>();
    edits(top.component, all);
    int i= all.indexOf(focus);
    assert i >= 0;
    var next= all.get(Math.floorMod(i + (back ? -1 : 1), all.size()));
    if (next == focus){ return; }
    focus.commit();
    focus = next;
  }

  private static void edits(java.awt.Container c, ArrayList<AEditText> all){
    var w= ((SkComponent) c).w;
    if (w instanceof AEditText t){ all.add(t); }
    for (var k : c.getComponents()){
      if (!w.hides(k)){ edits((java.awt.Container) k, all); }
    }
  }

  Object region(AWidget into, String where){ return new _RegionFiller(this, (s, make) -> addTo(into, where, s, make)); }
  Object addTo(AWidget into, String where, Object scope, Function<_Frame,? extends AWidget> make){
    var parent= into.component;
    var b= onEdtAndWait(() -> make.apply(this));
    ((Scope$1c$1) scope).mut$run$1(b);
    onEdtAndWait(() -> {
      into.change();
      // Border slots are replaceable: a second .north evicts the first. The
      // model is the single mutator, so this removal cannot race any gesture
      // dispatch; SkMouse just forgets its references into the old subtree
      // (no Exited events for removed widgets: removal is not an exit).
      var old= where == null ? null : ((BorderLayout) parent.getLayout()).getLayoutComponent(where);
      if (old != null){
        mouse.detached((SkComponent) old);
        parent.remove(old);
      }
      parent.add(b.component, where);
      into.grow(b.width + into.edgeW(), b.height + into.edgeH());
      b.changeable = false;
    });
    markLayoutDirty();
    return into;
  }

  <T> T onEdtAndWait(Supplier<T> s){
    if (SwingUtilities.isEventDispatchThread()){ return s.get(); }
    var task= new FutureTask<>(s::get);
    SwingUtilities.invokeLater(task);
    try{ return task.get(); }
    catch (InterruptedException e){
      Thread.currentThread().interrupt();
      throw new Error(e);
    }
    catch (ExecutionException e){
      var c= e.getCause();
      if (c instanceof Deterministic d){ Error$1c$0.instance.imm$$bang$1(d.i); }
      if (c instanceof RuntimeException re){ throw re; }
      if (c instanceof Error er){ throw er; }
      throw new RuntimeException(c);
    }
  }

  void onEdtAndWait(Runnable r){
    if (started){ frame.queue.midTask().set(true); }
    onEdtAndWait(() -> {
      r.run();
      return null;
    });
  }

  void markLayoutDirty(){ layoutDirty.set(true); }

  void tick(Instant$5c$0 elapsed){
    this.elapsed = elapsed;
    stage.tick();
    if (frame.queue.midTask().get()){ return; }
    // Never relayout while a press/release is already queued behind this
    // tick: the user made it on the pixels on screen, so it is interpreted
    // against the bounds those pixels show. The dirty flag stays set, so the
    // pending layout runs on a later tick.
    if (layoutDirty.get() && !mouseEventPending()){
      layoutDirty.set(false);
      // Only the invalid path (marked by reStyle/add) is re-laid-out.
      top.component.validate();
      mouse.rehover();
    }
    if (surface.getWidth() != renderLogicalW || surface.getHeight() != renderLogicalH){ mouse.rehover(); }
    render();
    // Paint synchronously: after this tick the pixels on screen and the
    // bounds used for hit testing agree for every subsequent mouse event.
    if (surface.getWidth() > 0 && surface.getHeight() > 0){
      surface.paintImmediately(0, 0, surface.getWidth(), surface.getHeight());
    }
  }

  private boolean mouseEventPending(){
    var q= java.awt.Toolkit.getDefaultToolkit().getSystemEventQueue();
    return q.peekEvent(java.awt.event.MouseEvent.MOUSE_PRESSED) != null
      || q.peekEvent(java.awt.event.MouseEvent.MOUSE_RELEASED) != null;
  }

  private void render(){
    int w= surface.getWidth();
    int h= surface.getHeight();
    if (w <= 0 || h <= 0){ return; }

    var tx= stage.f.getGraphicsConfiguration().getDefaultTransform();
    double sx= tx.getScaleX();
    double sy= tx.getScaleY();
    int pw= Math.max(1, (int) Math.ceil(w * sx));
    int ph= Math.max(1, (int) Math.ceil(h * sy));

    boolean resized= bimg == null || bimg.getWidth() != pw || bimg.getHeight() != ph;
    if (resized){
      if (bimg != null){ gpu.close(); bitmap.close(); }
      var info= new ImageInfo(pw, ph, ColorType.BGRA_8888, ColorAlphaType.PREMUL);
      bitmap = new Bitmap();
      bitmap.allocPixels(info);
      gpu = Vk.surface(info);
      canvas = gpu.getCanvas();
      bimg = new java.awt.image.BufferedImage(pw, ph, java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE);
      pixels = bitmap.peekPixels().getBuffer().order(java.nio.ByteOrder.LITTLE_ENDIAN).asIntBuffer();
    }
    renderLogicalW = w;
    renderLogicalH = h;

    canvas.clear(bands);
    int save= canvas.save();
    canvas.scale((float) sx, (float) sy);
    int picture= canvas.save();
    double s= stage.scale();
    var o= stage.offset(s);
    canvas.translate(o.x, o.y);
    canvas.scale((float) s, (float) s);
    var c= top.component;
    canvas.clipRect(io.github.humbleui.types.Rect.makeWH(c.getWidth(), c.getHeight()));
    canvas.clear(top.bg >>> 24 == 0 ? 0xFFA0A0A0 : top.bg | 0xFF000000);
    Sk.paintNode(c, canvas);
    menus.paint(canvas);
    mouse.paintTip(canvas);
    canvas.restoreToCount(picture);
    stage.paintCorner(canvas);
    canvas.restoreToCount(save);
    Vk.read(gpu, bitmap);
    pixels.get(0, ((java.awt.image.DataBufferInt) bimg.getRaster().getDataBuffer()).getData());
  }

  void blit(java.awt.Graphics g){
    if (bimg == null){ return; }
    g.drawImage(bimg, 0, 0, renderLogicalW, renderLogicalH, null);
  }

  void start(){
    assert SwingUtilities.isEventDispatchThread();
    if (top == null){ install(new _Pane(this)); }
    top.component.setBounds(0, 0, canvasW, canvasH);
    top.component.validate();
    layoutDirty.set(false);
    stage= new Stage(this, canvasW, canvasH, opacity);
    stage.f.validate();
    render();
    // Nothing after startRuntime may throw: abortBeforeStart asserts the
    // runtime timer never started. Both timers begin firing WarmupMillis
    // from now (Timer.setInitialDelay), skipping the worst-case first EDT
    // second (JIT, first rasterization, window-manager events); the window
    // is already visible showing the frame rendered above. Game time zero
    // is warmup end, so elapsed, the model tick deadlines and warning
    // timestamps agree, and the first tick behaves like every later one.
    startNanos = System.nanoTime() + FearlessFrame.WarmupMillis * 1_000_000L;
    frame.startRuntime(
      Math.round(1000.0f / fps),
      () -> tick(timeNanos(System.nanoTime() - startNanos))
    );
    if (modelPeriodNs != 0){
      frame.restartModelTimer(modelPeriodNs, FearlessFrame.WarmupMillis, modelTickActions);
    }
    started = true;
  }

  String windowTitle(){ return title.isEmpty() ? gui.name : gui.name + "    " + title; }
  boolean reserved(int key){ return key == _FluentGUI.code(gui.closeKey) || key == _FluentGUI.code(gui.fullScreenKey); }
  void closeRequest(){
    frame.queue.submit(new MF$7$1(){ @Override public Object mut$$hash$0(){
      if (onClose == null){ return mut$close$0(); }
      return onClose.mut$$hash$0();
    }});
  }
  void resizeRequest(double aspect){
    var r= new CResizeRequest(elapsed, Float$1c$0Instance.instance(aspect), w(canvasW), h(canvasH));
    if (pendingRequest.getAndSet(r) != null){ return; }
    frame.queue.submit(new MF$7$1(){ @Override public Object mut$$hash$0(){
      var q= pendingRequest.getAndSet(null);
      if (onResize != null){ onResize.mut$accept$1(q); }
      return Void$o$0.instance;
    }});
  }
  @Override public Object mut$virtualPixelSpace$2(Object w, Object h){
    requireOpen();
    int ww= extent((WidthNat$as$0) w, "virtual pixel space width");
    int hh= extent((HeightNat$lg$0) h, "virtual pixel space height");
    if (ww == 0 || hh == 0){ throw Util.detErr("The virtual pixel space " + ww + "x" + hh + " is empty: a virtual pixel space has at least one virtual pixel in each direction"); }
    canvasW = ww;
    canvasH = hh;
    if (!started){ return this; }
    onEdtAndWait(() -> {
      stage.canvas(ww, hh);
      top.component.setBounds(0, 0, ww, hh);
    });
    markLayoutDirty();
    return this;
  }
  @Override public Object read$virtualWidth$0(){ return w(canvasW); }
  @Override public Object read$virtualHeight$0(){ return h(canvasH); }
  @Override public Object mut$bands$1(Object c){
    requireOpen();
    int b= Sk.color((Color$1c$0) c) | 0xFF000000;
    onEdtAndWait(() -> bands = b);
    return this;
  }
  @Override public Object mut$opacity$1(Object a){
    requireOpen();
    int v= alpha(a);
    if (v < gui.opacityFloor){ throw Util.detErr("Frame.opacity(Alpha[" + v + "]) is below the opacity floor Alpha[" + gui.opacityFloor + "] of this CapGui: the window cannot be more transparent than CapGui.opacityFloor allows"); }
    opacity = v / 255f;
    if (started){ onEdtAndWait(() -> stage.opacity(opacity)); }
    return this;
  }
  private void requireOpen(){ if (frame.completed()){ throw Util.detErr("This Frame is closed: the CapGui.run that showed it has returned, so changing it would have no effect"); } }
  @Override public Object mut$onResize$1(Object c){ requireOpen(); onResize = (Consumer$ao$1) c; return this; }
  @Override public Object mut$onClose$1(Object a){ requireOpen(); onClose = (MF$7$1) a; return this; }
  @Override public Object mut$close$0(){
    requireOpen();
    if (!started){ throw Util.detErr("Frame.close closes the open window, and this window is not open yet: the consumer given to CapGui.run has not returned"); }
    onEdtAndWait(frame::closed);
    return Void$o$0.instance;
  }
  @Override public Object mut$onKey$1(Object scope){
    requireOpen();
    var keys= new CKeyManager(this);
    ((Scope$1c$1) scope).mut$run$1(keys);
    onEdtAndWait(() -> {
      for (var l : surface.getKeyListeners()){
        if (l instanceof CKeyManager k){ k.focusLost(null); surface.removeKeyListener(k); surface.removeFocusListener(k); }
      }
      surface.addKeyListener(keys);
      surface.addFocusListener(keys);
      keys.changeable = false;
    });
    return this;
  }
  @Override public Object mut$uTitle$1(Object t){
    requireOpen();
    var s= ustr(t);
    var b= lineBreak(s);
    if (b != null){ throw Util.detErr("The new title of this Frame has " + b + ": the title bar shows the title on one line"); }
    title = s;
    if (started){ onEdtAndWait(() -> stage.f.setTitle(windowTitle())); }
    return this;
  }
  @Override public Object mut$fps$1(Object f){
    requireOpen();
    fps = (int) fpsOf(f, "fps");
    if (started){
      int delay= Math.round(1000.0f / fps);
      onEdtAndWait(() -> frame.setTickDelay(delay));
    }
    return this;
  }
  // Getters. All safe from the queue thread: title/fps are read under the
  // single-mutator model, elapsed is volatile.
  @Override public Object read$uTitle$0(){ return UStr$s$0Instance.instance(title); }
  @Override public Object read$fps$0(){ return n(fps); }
  @Override public Object read$elapsed$0(){ return elapsed; }
  @Override public Object mut$modelFps$2(Object f, Object scope){
    requireOpen();
    long nn= fpsOf(f, "modelFps");
    var actions= new ArrayList<MF$7$1>();
    var b= new ModelFps$as$0(){
      boolean changeable= true;
      @Override public Object mut$action$1(Object r){
        onEdtAndWait(() -> {
          if (frame.completed()){ throw Util.detErr("This ModelFps belongs to a closed window: the CapGui.run that showed the window has returned, so an action set now would never run"); }
          if (!changeable && modelTickActions != actions){
            throw Util.detErr("This ModelFps was replaced by a later .modelFps, so an action set now would never run");
          }
          actions.clear();
          actions.add((MF$7$1) r);
        });
        return this;
      }
    };
    ((Scope$1c$1) scope).mut$run$1(b);
    onEdtAndWait(() -> {
      modelPeriodNs = Math.round(1e9 / nn);
      modelTickActions = actions;// replace semantics, like .mouse and .onKey
      b.changeable = false;
      // Live change: fixed-rate deadlines restart from now, no warmup.
      if (started){ frame.restartModelTimer(modelPeriodNs, 0, actions); }
    });
    return this;
  }
  @Override public Object mut$content$0(){ return new _RegionFiller(this, this::newContent); }

  private Object newContent(Object scope, Function<_Frame,? extends AWidget> make){
    requireOpen();
    var t= onEdtAndWait(() -> make.apply(this));
    if (!started){
      onEdtAndWait(() -> {
        if (top != null){ uninstall(top); }// a later .content replaces an earlier one
        install(t);
      });
      ((Scope$1c$1) scope).mut$run$1(t);
      markLayoutDirty();
      return Void$o$0.instance;
    }
    // Post start: configure the new tree while detached (ticks keep painting
    // the old tree), then swap in one EDT block so no tick or mouse event
    // ever sees a half-built tree. The new tree takes the current virtual pixel space.
    ((Scope$1c$1) scope).mut$run$1(t);
    onEdtAndWait(() -> {
      uninstall(top);
      install(t);
      t.component.setBounds(0, 0, stage.canvasW, stage.canvasH);
      // The old tree is gone: no Exited events for it, and a gesture in
      // progress is forgotten (an in-flight release finds no press target,
      // like a release over empty space).
      mouse.reset();
    });
    markLayoutDirty();
    return Void$o$0.instance;
  }

  // Only the surface listens for mouse events: the widgets have no AWT
  // listeners, so AWT routes everything to the surface, and Stage hands the
  // events in virtual pixels to SkMouse.
  private void install(AWidget t){
    top = t;
    t.changeable = false;
    surface.add(t.component);
  }

  private void uninstall(AWidget t){ surface.remove(t.component); }
}

record CResizeRequest(Instant$5c$0 elapsed, Float$1c$0 aspectRatio, WidthNat$as$0 panelWidth, HeightNat$lg$0 panelHeight) implements ResizeRequest$9j4$0{
  @Override public Object read$elapsed$0(){ return elapsed; }
  @Override public Object read$aspectRatio$0(){ return aspectRatio; }
  @Override public Object read$panelWidth$0(){ return panelWidth; }
  @Override public Object read$panelHeight$0(){ return panelHeight; }
}
