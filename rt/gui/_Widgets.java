package _base;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Path;
import io.github.humbleui.skija.TextLine;

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

  _Button(_Frame frame){ super(frame); }

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

final class _Pane extends AContainer implements Pane$o$0{
  boolean vertical= false;
  long minPerLine= 1;
  long maxPerLine= 0xFFFF_FFFF_FFFF_FFFFL;

  _Pane(_Frame frame){
    super(frame);
    component.setLayout(new CenteredFlowLayout(this));
  }
  @Override Dimension need(){ return ((CenteredFlowLayout) component.getLayout()).natural(component); }
  @Override public Pane$o$0 mut$button$1(Object s){ frame.addTo(this, null, s, _Button::new); return this; }
  @Override public Pane$o$0 mut$label$1(Object s){ frame.addTo(this, null, s, _Label::new); return this; }
  @Override public Pane$o$0 mut$pane$1(Object s){ frame.addTo(this, null, s, _Pane::new); return this; }
  @Override public Pane$o$0 mut$border$1(Object s){ frame.addTo(this, null, s, _Border::new); return this; }
  @Override public Object mut$clear$0(){
    return reStyle(() -> {
      // Removed widgets get no Exited events (DOM semantics: removal is not
      // an exit); SkMouse just forgets its references into the subtrees.
      // Handler tasks already queued for removed widgets are skipped.
      for (var c : component.getComponents()){ frame.mouse.detached((SkComponent) c); }
      component.removeAll();
    });
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
  @Override public Border$2o$0 mut$north$1(Object s){ frame.addTo(this, BorderLayout.NORTH, s, _Pane::new); return this; }
  @Override public Border$2o$0 mut$south$1(Object s){ frame.addTo(this, BorderLayout.SOUTH, s, _Pane::new); return this; }
  @Override public Border$2o$0 mut$east$1(Object s){ frame.addTo(this, BorderLayout.EAST, s, _Pane::new); return this; }
  @Override public Border$2o$0 mut$west$1(Object s){ frame.addTo(this, BorderLayout.WEST, s, _Pane::new); return this; }
  @Override public Border$2o$0 mut$center$1(Object s){ frame.addTo(this, BorderLayout.CENTER, s, _Pane::new); return this; }
  @Override public Border$2o$0 mut$northB$1(Object s){ frame.addTo(this, BorderLayout.NORTH, s, _Border::new); return this; }
  @Override public Border$2o$0 mut$southB$1(Object s){ frame.addTo(this, BorderLayout.SOUTH, s, _Border::new); return this; }
  @Override public Border$2o$0 mut$eastB$1(Object s){ frame.addTo(this, BorderLayout.EAST, s, _Border::new); return this; }
  @Override public Border$2o$0 mut$westB$1(Object s){ frame.addTo(this, BorderLayout.WEST, s, _Border::new); return this; }
  @Override public Border$2o$0 mut$centerB$1(Object s){ frame.addTo(this, BorderLayout.CENTER, s, _Border::new); return this; }
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
  TextLine line;
  // Read live by Sk.textSizeWithInsets/Sk.text; only _Button and _Label
  // expose Fearless methods to change it, but it lives here alongside the
  // other style fields.
  String text= "";

  final _Frame frame;
  final SkComponent component= new SkComponent(this);
  // Fearless mouse handlers per event kind; EDT confined, read by SkMouse.
  EnumMap<MouseKind,Consumer$ao$1> handlers= new EnumMap<>(MouseKind.class);
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
      component.invalidate();
    });
    frame.markLayoutDirty();
    return mut$self$0();
  }

  Object reStyle(int n, IntConsumer set){ return reStyle(() -> set.accept(n)); }

  Object reText(Runnable r){
    return reStyle(() -> {
      r.run();
      if (line != null){ line.close(); }
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
    return reText(() -> text = s);
  }
  public Object read$uText$0(){ return UStr$s$0Instance.instance(text); }
  @Override public Object mut$foreground$1(Object c){ return onEdt(() -> fg = Sk.color(foreground = (Color$1c$0) c)); }
  @Override public Object mut$background$1(Object c){ return onEdt(() -> bg = Sk.color(background = (Color$1c$0) c)); }
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
    try (var p= new Paint().setAntiAlias(true).setColor(fg)){
      var g= new CGraphicsCtx(
        cv,
        frame,
        frame.elapsed,
        Scopes.w(component.getWidth()),
        Scopes.h(component.getHeight()),
        p
      );
      paint.imm$run$1(g);
      paintWarning = warned(paintWarning, g.outside(this));
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
  @SuppressWarnings("serial")
  final JComponent surface= new JComponent(){ @Override protected void paintComponent(java.awt.Graphics g){ blit(g); } };
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
  private boolean started;
  private final AtomicBoolean layoutDirty= new AtomicBoolean();
  private Bitmap bitmap;
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
  }

  void addTo(AWidget into, String where, Object scope, Function<_Frame,? extends AWidget> make){
    var parent= into.component;
    var b= onEdtAndWait(() -> make.apply(this));
    ((Scope$1c$1) scope).mut$run$1(b);
    onEdtAndWait(() -> {
      // Border slots are replaceable: a second .north evicts the first. The
      // model is the single mutator, so this removal cannot race any gesture
      // dispatch; SkMouse just forgets its references into the old subtree
      // (no Exited events for removed widgets: removal is not an exit).
      var old= where == null ? null : ((MutableBorderLayout) parent.getLayout()).getLayoutComponent(where);
      if (old != null){
        mouse.detached((SkComponent) old);
        parent.remove(old);
      }
      into.change();
      parent.add(b.component, where);
      into.grow(b.width + into.left + into.right, b.height + into.top + into.bottom);
      b.changeable = false;
    });
    markLayoutDirty();
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
      if (bimg != null){ canvas.close(); bitmap.close(); }
      bitmap = new Bitmap();
      bitmap.allocPixels(new ImageInfo(pw, ph, ColorType.BGRA_8888, ColorAlphaType.PREMUL));
      canvas = new Canvas(bitmap);
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
    canvas.restoreToCount(picture);
    stage.paintCorner(canvas);
    canvas.restoreToCount(save);

    pixels.get(0, ((java.awt.image.DataBufferInt) bimg.getRaster().getDataBuffer()).getData());
  }

  void blit(java.awt.Graphics g){
    if (bimg == null){ return; }
    g.drawImage(bimg, 0, 0, renderLogicalW, renderLogicalH, null);
  }

  void start(){
    assert SwingUtilities.isEventDispatchThread();
    if (top == null){ throw Util.detErr("This Frame has no content, so there is nothing to show: call .content{...} or .contentB{...} on it before the consumer given to Gui.run returns"); }
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
    int b= Sk.color((Color$1c$0) c) | 0xFF000000;
    onEdtAndWait(() -> bands = b);
    return this;
  }
  @Override public Object mut$opacity$1(Object a){
    int v= alpha(a);
    if (v < gui.opacityFloor){ throw Util.detErr("Frame.opacity(Alpha[" + v + "]) is below the opacity floor Alpha[" + gui.opacityFloor + "] of this Gui: the window can not be more transparent than Gui.opacityFloor allows"); }
    opacity = v / 255f;
    if (started){ onEdtAndWait(() -> stage.opacity(opacity)); }
    return this;
  }
  @Override public Object mut$onResize$1(Object c){ onResize = (Consumer$ao$1) c; return this; }
  @Override public Object mut$onClose$1(Object a){ onClose = (MF$7$1) a; return this; }
  @Override public Object mut$close$0(){
    if (!started){ throw Util.detErr("Frame.close closes the open window, and this window is not open yet: the consumer given to Gui.run has not returned"); }
    onEdtAndWait(frame::closed);
    return Void$o$0.instance;
  }
  @Override public Object mut$onKey$1(Object scope){
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
    title = ustr(t);
    if (started){ onEdtAndWait(() -> stage.f.setTitle(windowTitle())); }
    return this;
  }
  @Override public Object mut$fps$1(Object f){
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
    long nn= fpsOf(f, "modelFps");
    var actions= new ArrayList<MF$7$1>();
    var b= new ModelFps$as$0(){
      boolean changeable= true;
      @Override public Object mut$action$1(Object r){
        onEdtAndWait(() -> {
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
  @Override public Object mut$content$1(Object s){ return newContent(s, _Pane::new); }
  @Override public Object mut$contentB$1(Object s){ return newContent(s, _Border::new); }

  private Object newContent(Object scope, Function<_Frame,? extends AWidget> make){
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
  @Override public Object imm$aspectRatio$0(){ return aspectRatio; }
  @Override public Object read$panelWidth$0(){ return panelWidth; }
  @Override public Object read$panelHeight$0(){ return panelHeight; }
}
