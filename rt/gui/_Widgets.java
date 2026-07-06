package base;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import io.github.humbleui.skija.Bitmap;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.Paint;

import static base.Scopes.*;

// The single Swing component class behind every widget. It never paints its
// own pixels (only the top component blits the frame image) and never has
// AWT mouse listeners: all mouse input is received by the top component and
// routed by SkMouse. No listeners on children means AWT never synthesizes
// enter/exit/disarm noise for them.
@SuppressWarnings("serial")
final class SkComponent extends JComponent{
  final AWidget w;
  SkComponent(AWidget w){
    this.w = w;
    setOpaque(false);
    setFocusable(false);
  }
  Dimension layoutSize(){ return super.getPreferredSize(); }// layout-manager size
  @Override public Dimension getPreferredSize(){ return Sk.preferred(w.autoSize(), w); }
  @Override public Dimension getMinimumSize(){ return getPreferredSize(); }
  @Override protected void paintComponent(java.awt.Graphics g){ w.frame.blit(g, this); }
}

class _Button extends AWidget implements Button$0{
  String text = "";
  final List<MF$1> actions = new ArrayList<>();// EDT confined
  boolean down;// visual pressed state, maintained by SkMouse, read by Sk.button
  boolean over;// visual rollover state, maintained by SkMouse, read by Sk.button

  _Button(_Frame frame){ super(frame); }

  @Override public Object mut$text$1(Object t){
    var s = ((Str$0Instance) t).val();
    return reStyle(() -> text = s);
  }
  @Override public Object read$text$0(){ return new Str$0Instance(text); }
  @Override public Object mut$action$1(Object r){
    frame.onEdtAndWait(() -> actions.add((MF$1) r));
    return this;
  }
  @Override Dimension autoSize(){ return Sk.textSizeWithInsets(text, this); }
  @Override void sk(Canvas cv){ Sk.button(cv, this); }
}

class _Label extends AWidget implements Label$0{
  String text = "";

  _Label(_Frame frame){ super(frame); }

  @Override public Object mut$text$1(Object t){
    var s = ((Str$0Instance) t).val();
    return reStyle(() -> text = s);
  }
  @Override public Object read$text$0(){ return new Str$0Instance(text); }
  @Override Dimension autoSize(){ return Sk.textSizeWithInsets(text, this); }
  @Override void sk(Canvas cv){
    Sk.background(cv, this);
    Sk.text(cv, text, this, 0, 0);
  }
}

class _Pane extends AContainer implements Pane$0{
  _Pane(_Frame frame){
    super(frame);
    component.setLayout(new CenteredFlowLayout(this));
  }
  @Override public Pane$0 mut$button$1(Object s){ frame.addTo(component, s, _Button::new); return this; }
  @Override public Pane$0 mut$label$1(Object s){ frame.addTo(component, s, _Label::new); return this; }
  @Override public Pane$0 mut$pane$1(Object s){ frame.addTo(component, s, _Pane::new); return this; }
  @Override public Pane$0 mut$border$1(Object s){ frame.addTo(component, s, _Border::new); return this; }
}

class _Border extends AContainer implements Border$0{
  _Border(_Frame frame){
    super(frame);
    component.setLayout(new MutableBorderLayout(this));
  }
  @Override public Border$0 mut$north$1(Object s){ frame.addTo(component, BorderLayout.NORTH, s, _Pane::new); return this; }
  @Override public Border$0 mut$south$1(Object s){ frame.addTo(component, BorderLayout.SOUTH, s, _Pane::new); return this; }
  @Override public Border$0 mut$east$1(Object s){ frame.addTo(component, BorderLayout.EAST, s, _Pane::new); return this; }
  @Override public Border$0 mut$west$1(Object s){ frame.addTo(component, BorderLayout.WEST, s, _Pane::new); return this; }
  @Override public Border$0 mut$center$1(Object s){ frame.addTo(component, BorderLayout.CENTER, s, _Pane::new); return this; }
  @Override public Border$0 mut$northB$1(Object s){ frame.addTo(component, BorderLayout.NORTH, s, _Border::new); return this; }
  @Override public Border$0 mut$southB$1(Object s){ frame.addTo(component, BorderLayout.SOUTH, s, _Border::new); return this; }
  @Override public Border$0 mut$eastB$1(Object s){ frame.addTo(component, BorderLayout.EAST, s, _Border::new); return this; }
  @Override public Border$0 mut$westB$1(Object s){ frame.addTo(component, BorderLayout.WEST, s, _Border::new); return this; }
  @Override public Border$0 mut$centerB$1(Object s){ frame.addTo(component, BorderLayout.CENTER, s, _Border::new); return this; }
}

abstract class AWidget implements Widget$1{
  static final Nat$0 zero = Nat$0Instance.instance(0);
  static final Nat$0 def = Nat$0Instance.instance(6);
  static final Nat$0 defText = Nat$0Instance.instance(12);

  WidthNat$0 left = (WidthNat$0) WidthNat$0.instance.read$$hash$1(def);
  HeightNat$0 top = (HeightNat$0) HeightNat$0.instance.read$$hash$1(def);
  WidthNat$0 right = (WidthNat$0) WidthNat$0.instance.read$$hash$1(def);
  HeightNat$0 bottom = (HeightNat$0) HeightNat$0.instance.read$$hash$1(def);
  WidthNat$0 widthGap = (WidthNat$0) WidthNat$0.instance.read$$hash$1(def);
  HeightNat$0 heightGap = (HeightNat$0) HeightNat$0.instance.read$$hash$1(def);
  Nat$0 radius = defText;
  WidthNat$0 preferredWidth;
  HeightNat$0 preferredHeight;
  Color$0 foreground = (Color$0) Color$0.instance;
  Color$0 background = (Color$0) Color$0.instance.imm$transparent$0();
  HeightNat$0 textSize = (HeightNat$0) HeightNat$0.instance.read$$hash$1(defText);

  final _Frame frame;
  final SkComponent component = new SkComponent(this);
  // Fearless mouse handlers per event kind; EDT confined, read by SkMouse.
  final EnumMap<MouseKind, List<Consumer$1>> handlers = new EnumMap<>(MouseKind.class);

  AWidget(_Frame frame){ this.frame = frame; }

  abstract void sk(Canvas cv);
  Dimension autoSize(){ return component.layoutSize(); }

  final Object onEdt(Runnable r){
    frame.onEdtAndWait(r);
    return mut$self$0();
  }

  final Object reStyle(Runnable r){
    // Invalidate only this component: invalidation propagates upward on its
    // own, and _Frame.tick re-lays-out just the invalid path, so unchanged
    // widgets keep their exact bounds.
    frame.onEdtAndWait(() -> {
      r.run();
      component.invalidate();
    });
    frame.markLayoutDirty();
    return mut$self$0();
  }

  @Override public Object mut$topInset$1(Object v){ return mut$topInset$p1$1(HeightNat$0.instance.read$$hash$1((Nat$0) v)); }
  @Override public Object mut$topInset$p1$1(Object v){
    frame.height((HeightNat$0) v, "top inset");
    return reStyle(() -> top = (HeightNat$0) v);
  }
  @Override public Object mut$bottomInset$1(Object v){ return mut$bottomInset$p1$1(HeightNat$0.instance.read$$hash$1((Nat$0) v)); }
  @Override public Object mut$bottomInset$p1$1(Object v){
    frame.height((HeightNat$0) v, "bottom inset");
    return reStyle(() -> bottom = (HeightNat$0) v);
  }
  @Override public Object mut$leftInset$1(Object v){ return mut$leftInset$p1$1(WidthNat$0.instance.read$$hash$1((Nat$0) v)); }
  @Override public Object mut$leftInset$p1$1(Object v){
    frame.width((WidthNat$0) v, "left inset");
    return reStyle(() -> left = (WidthNat$0) v);
  }
  @Override public Object mut$rightInset$1(Object v){ return mut$rightInset$p1$1(WidthNat$0.instance.read$$hash$1((Nat$0) v)); }
  @Override public Object mut$rightInset$p1$1(Object v){
    frame.width((WidthNat$0) v, "right inset");
    return reStyle(() -> right = (WidthNat$0) v);
  }
  @Override public Object mut$heightGap$1(Object v){ return mut$heightGap$p1$1(HeightNat$0.instance.read$$hash$1((Nat$0) v)); }
  @Override public Object mut$heightGap$p1$1(Object v){
    frame.height((HeightNat$0) v, "height gap");
    return reStyle(() -> heightGap = (HeightNat$0) v);
  }
  @Override public Object mut$widthGap$1(Object v){ return mut$widthGap$p1$1(WidthNat$0.instance.read$$hash$1((Nat$0) v)); }
  @Override public Object mut$widthGap$p1$1(Object v){
    frame.width((WidthNat$0) v, "width gap");
    return reStyle(() -> widthGap = (WidthNat$0) v);
  }
  @Override public Object mut$width$1(Object w){ return mut$width$p1$1(WidthNat$0.instance.read$$hash$1((Nat$0) w)); }
  @Override public Object mut$width$p1$1(Object w){
    frame.width((WidthNat$0) w, "widget width");
    return reStyle(() -> preferredWidth = (WidthNat$0) w);
  }
  @Override public Object mut$height$1(Object h){ return mut$height$p1$1(HeightNat$0.instance.read$$hash$1((Nat$0) h)); }
  @Override public Object mut$height$p1$1(Object h){
    frame.height((HeightNat$0) h, "widget height");
    return reStyle(() -> preferredHeight = (HeightNat$0) h);
  }
  @Override public Object mut$radius$1(Object r){
    frame.size((Nat$0) r, "radius");
    return reStyle(() -> radius = (Nat$0) r);
  }
  public Object mut$textSize$1(Object t){
    frame.height((HeightNat$0) t, "text size");
    return reStyle(() -> textSize = (HeightNat$0) t);
  }
  public Object read$textSize$0(){ return textSize; }
  @Override public Object mut$autoWidth$0(){ return reStyle(() -> preferredWidth = null); }
  @Override public Object mut$autoHeight$0(){ return reStyle(() -> preferredHeight = null); }
  @Override public Object mut$autoSize$0(){
    return reStyle(() -> {
      preferredWidth = null;
      preferredHeight = null;
    });
  }
  @Override public Object mut$foreground$1(Object c){ return onEdt(() -> foreground = (Color$0) c); }
  @Override public Object mut$background$1(Object c){ return onEdt(() -> background = (Color$0) c); }
  @Override public Object read$topInset$0(){ return top; }
  @Override public Object read$bottomInset$0(){ return bottom; }
  @Override public Object read$leftInset$0(){ return left; }
  @Override public Object read$rightInset$0(){ return right; }
  @Override public Object read$heightGap$0(){ return heightGap; }
  @Override public Object read$widthGap$0(){ return widthGap; }
  @Override public Object read$width$0(){ return preferredWidth == null ? Util.optEmpty() : Util.optSome(preferredWidth); }
  @Override public Object read$height$0(){ return preferredHeight == null ? Util.optEmpty() : Util.optSome(preferredHeight); }
  @Override public Object read$radius$0(){ return radius; }
  @Override public Object read$foreground$0(){ return foreground; }
  @Override public Object read$background$0(){ return background; }
  @Override public Object mut$save$1(Object s){
    ((Consumer$1) s).mut$accept$1(mut$self$0());
    return mut$self$0();
  }
}

abstract class AContainer extends AWidget{
  Painter$0 paint = Scopes.idP;

  AContainer(_Frame frame){ super(frame); }

  @Override void sk(Canvas cv){
    Sk.background(cv, this);
    try (var p = new Paint().setAntiAlias(true)){
      paint.imm$run$1(new CGraphicsCtx(
        cv,
        frame,
        frame.elapsed,
        Scopes.w(component.getWidth()),
        Scopes.h(component.getHeight()),
        XNat$0.instance,
        YNat$0.instance,
        p
      ));
    }
  }

  public Object mut$mouse$1(Object s){
    frame.onEdtAndWait(handlers::clear);// .mouse replaces earlier handlers
    ((Scope$1) s).mut$run$1(new CMouseBuilder(this));
    return mut$self$0();
  }

  public Object mut$paint$1(Object p){ return onEdt(() -> paint = (Painter$0) p); }
}

class _Frame implements Frame$0{
  final FearlessFrame frame;
  final SkMouse mouse = new SkMouse(this);
  Time$0 elapsed = time(0);
  AWidget top;
  final int screenW;
  final int screenH;
  final WidthNat$0 screenSizeW;
  final HeightNat$0 screenSizeH;
  private final long startNanos = System.nanoTime();
  private Nat$0 fps = n(30);
  private Nat$0 modelFpsVal;
  private final List<MF$1> modelTickActions = new ArrayList<>();
  private Alpha$0 alpha = (Alpha$0) Alpha$0.instance.imm$opaque$0();
  private XNat$0 locationX;
  private YNat$0 locationY;
  private Str$0 title = new Str$0Instance("");
  private boolean maximized;
  private boolean resizable;
  private boolean undecorated;
  private final AtomicBoolean layoutDirty = new AtomicBoolean();
  private Bitmap bitmap;
  private Canvas canvas;
  private java.awt.image.BufferedImage bimg;
  private int renderLogicalW = -1;
  private int renderLogicalH = -1;
  private double renderScaleX = Double.NaN;
  private double renderScaleY = Double.NaN;

  _Frame(CompletableFuture<Void> done){
    frame = new FearlessFrame(done);
    var b = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
      .getDefaultScreenDevice()
      .getDefaultConfiguration()
      .getBounds();
    screenW = b.width;
    screenH = b.height;
    screenSizeW = w(screenW);
    screenSizeH = h(screenH);
  }

  void addTo(JComponent parent, Object scope, Function<_Frame, ? extends AWidget> make){
    var b = onEdtAndWait(() -> {
      var bb = make.apply(this);
      parent.add(bb.component);
      return bb;
    });
    ((Scope$1) scope).mut$run$1(b);
    markLayoutDirty();
  }

  void addTo(JComponent parent, String where, Object scope, Function<_Frame, ? extends AWidget> make){
    var b = onEdtAndWait(() -> {
      var bb = make.apply(this);
      parent.add(bb.component, where);
      return bb;
    });
    ((Scope$1) scope).mut$run$1(b);
    markLayoutDirty();
  }

  <T> T onEdtAndWait(Supplier<T> s){
    if (SwingUtilities.isEventDispatchThread()){ return s.get(); }
    var task = new FutureTask<>(s::get);
    SwingUtilities.invokeLater(task);
    try { return task.get(); }
    catch (InterruptedException e){
      Thread.currentThread().interrupt();
      throw new Error(e);
    }
    catch (ExecutionException e){
      var c = e.getCause();
      if (c instanceof RuntimeException re){ throw re; }
      if (c instanceof Error er){ throw er; }
      throw new RuntimeException(c);
    }
  }

  void onEdtAndWait(Runnable r){
    onEdtAndWait(() -> {
      r.run();
      return null;
    });
  }

  void markLayoutDirty(){ layoutDirty.set(true); }

  void tick(Time$0 elapsed){
    this.elapsed = elapsed;
    // Never relayout while a mouse button is held or a press/release is
    // already queued behind this tick: moving components mid-gesture would
    // change the bounds used to interpret that gesture. The dirty flag stays
    // set, so the pending layout runs on a later tick.
    if (layoutDirty.get() && !mouse.down && !mouseEventPending()){
      layoutDirty.set(false);
      // Only the invalid path (marked by reStyle/add) is re-laid-out.
      top.component.validate();
    }
    render();
    // Paint synchronously: after this tick the pixels on screen and the
    // bounds used for hit testing agree for every subsequent mouse event.
    var c = top.component;
    if (c.getWidth() > 0 && c.getHeight() > 0){
      c.paintImmediately(0, 0, c.getWidth(), c.getHeight());
    }
  }

  private boolean mouseEventPending(){
    var q = java.awt.Toolkit.getDefaultToolkit().getSystemEventQueue();
    return q.peekEvent(java.awt.event.MouseEvent.MOUSE_PRESSED) != null
      || q.peekEvent(java.awt.event.MouseEvent.MOUSE_RELEASED) != null;
  }

  private void render(){
    var c = top.component;
    int w = c.getWidth();
    int h = c.getHeight();
    if (w <= 0 || h <= 0){ return; }

    var tx = frame.getGraphicsConfiguration().getDefaultTransform();
    double sx = tx.getScaleX();
    double sy = tx.getScaleY();
    int pw = Math.max(1, (int) Math.ceil(w * sx));
    int ph = Math.max(1, (int) Math.ceil(h * sy));

    if (
      bimg == null
        || renderLogicalW != w
        || renderLogicalH != h
        || renderScaleX != sx
        || renderScaleY != sy
        || bimg.getWidth() != pw
        || bimg.getHeight() != ph
    ){
      if (canvas != null){ canvas.close(); canvas = null; }
      if (bitmap != null){ bitmap.close(); bitmap = null; }
      bitmap = new Bitmap();
      bitmap.allocPixels(new ImageInfo(pw, ph, ColorType.BGRA_8888, ColorAlphaType.PREMUL));
      canvas = new Canvas(bitmap);
      bimg = new java.awt.image.BufferedImage(pw, ph, java.awt.image.BufferedImage.TYPE_INT_ARGB_PRE);
      renderLogicalW = w;
      renderLogicalH = h;
      renderScaleX = sx;
      renderScaleY = sy;
    }

    canvas.clear(0);
    int save = canvas.save();
    canvas.scale((float) sx, (float) sy);
    Sk.paintNode(c, canvas);
    canvas.restoreToCount(save);

    var bb = bitmap.peekPixels();
    assert bb != null;
    bb.getBuffer().order(java.nio.ByteOrder.LITTLE_ENDIAN).asIntBuffer()
      .get(((java.awt.image.DataBufferInt) bimg.getRaster().getDataBuffer()).getData());
  }

  void blit(java.awt.Graphics g, SkComponent c){
    if (top == null || top.component != c || bimg == null){ return; }
    g.drawImage(bimg, 0, 0, renderLogicalW, renderLogicalH, null);
  }

  int width(WidthNat$0 v, String what){ return bounded(nat(v.read$get$0()), screenW, what, "screen width"); }
  int height(HeightNat$0 v, String what){ return bounded(nat(v.read$get$0()), screenH, what, "screen height"); }
  int xPos(XNat$0 v, String what){ return bounded(nat(v.read$get$0()), screenW - 1, what, "screen x"); }
  int yPos(YNat$0 v, String what){ return bounded(nat(v.read$get$0()), screenH - 1, what, "screen y"); }
  int size(Nat$0 v, String what){ return bounded(nat(v), Math.min(screenW, screenH), what, "screen size"); }

  private long nat(Object n){ return Util.natToInt(n); }

  private int bounded(long v, int max, String what, String bound){
    if (v > max){ throw Util.detErr(what + " must be <= " + max + " (" + bound + ")"); }
    return Math.toIntExact(v);
  }

  private void checkWindowFits(){
    if (frame.getWidth() > screenW || frame.getHeight() > screenH){
      throw Util.detErr("Window must fit on screen: window="
        + frame.getWidth() + "x" + frame.getHeight()
        + ", screen=" + screenW + "x" + screenH);
    }
  }

  private void checkWindowLocationFits(int x, int y){
    if (x + frame.getWidth() > screenW || y + frame.getHeight() > screenH){
      throw Util.detErr("Window location puts window outside screen: location="
        + x + "," + y
        + ", window=" + frame.getWidth() + "x" + frame.getHeight()
        + ", screen=" + screenW + "x" + screenH);
    }
  }

  void start(){
    assert SwingUtilities.isEventDispatchThread();
    assert top != null;

    frame.setTitle(((Str$0Instance) title).val());
    top.mut$radius$1(n(0));

    var col = (Color$0) top.read$background$0();
    if (Scopes.alpha(col.read$alpha$0()) == 0){
      top.mut$background$1(Color$0.instance.imm$boringGray$0());
    } else {
      top.mut$background$1(Color$0.instance.imm$$hash$4(
        col.read$red$0(),
        col.read$green$0(),
        col.read$blue$0(),
        Alpha$0.instance.imm$opaque$0()
      ));
    }

    frame.setContentPane(top.component);
    frame.setUndecorated(undecorated);
    frame.setResizable(resizable);
    frame.pack();

    if (maximized){
      if (locationX != null || locationY != null){
        throw Util.detErr("A maximized window cannot also have an explicit location");
      }
      frame.setSize(new Dimension(screenW, screenH));
      frame.setLocation(0, 0);
    } else {
      checkWindowFits();
      if (locationX != null && locationY != null){
        int xx = xPos(locationX, "window x location");
        int yy = yPos(locationY, "window y location");
        checkWindowLocationFits(xx, yy);
        frame.setLocation(xx, yy);
      } else {
        frame.setLocationRelativeTo(null);
      }
    }

    if (undecorated){ frame.setOpacity(Scopes.alpha(alpha) / 255f); }

    frame.validate();
    layoutDirty.set(false);
    render();
    frame.setFocusable(true);
    frame.setVisible(true);
    frame.startRuntime(
      Math.round(1000.0f / Scopes.nat(fps)),
      () -> tick(timeNanos(System.nanoTime() - startNanos))
    );

    if (modelFpsVal != null && !modelTickActions.isEmpty()){
      frame.startModelTimer(
        Math.round(1000.0f / Scopes.nat(modelFpsVal)),
        List.copyOf(modelTickActions)
      );
    }
  }

  @Override public Object mut$maximized$0(){ maximized = true; return this; }
  @Override public Object mut$resizable$0(){ resizable = true; return this; }
  @Override public Object mut$undecorated$1(Object a){
    undecorated = true;
    alpha = (Alpha$0) a;
    return this;
  }
  @Override public Object mut$location$2(Object x, Object y){
    xPos((XNat$0) x, "window x location");
    yPos((YNat$0) y, "window y location");
    locationX = (XNat$0) x;
    locationY = (YNat$0) y;
    return this;
  }
  @Override public Object mut$onKey$1(Object scope){
    var keys = new CKeyManager(this);
    ((Scope$1) scope).mut$run$1(keys);
    onEdtAndWait(() -> {
      for (var l : frame.getKeyListeners()){ frame.removeKeyListener(l); }
      frame.addKeyListener(keys);
    });
    return this;
  }
  @Override public Object mut$title$1(Object t){ title = (Str$0) t; return this; }
  @Override public Object mut$fps$1(Object f){
    long nn = Util.natToInt((Nat$0) f);
    if (nn < 1 || nn > 500){ throw Util.detErr("FPS must be between 1 and 500"); }
    fps = (Nat$0) f;
    return this;
  }
  @Override public Object mut$modelFps$2(Object f, Object scope){
    long nn = Util.natToInt((Nat$0) f);
    if (nn < 1 || nn > 500){ throw Util.detErr("modelFps must be between 1 and 500"); }
    modelFpsVal = (Nat$0) f;
    ((Scope$1) scope).mut$run$1(new ModelFps$0(){
      @Override public Object mut$action$1(Object r){
        modelTickActions.add((MF$1) r);
        return this;
      }
    });
    return this;
  }
  @Override public Object mut$content$1(Object s){
    var b = onEdtAndWait(() -> install(new _Pane(this)));
    ((Scope$1) s).mut$run$1(b);
    markLayoutDirty();
    return Void$0.instance;
  }
  @Override public Object mut$contentB$1(Object s){
    var b = onEdtAndWait(() -> install(new _Border(this)));
    ((Scope$1) s).mut$run$1(b);
    markLayoutDirty();
    return Void$0.instance;
  }

  // Only the top component listens for mouse events: children have no AWT
  // listeners, so AWT routes everything here and SkMouse dispatches.
  private <T extends AWidget> T install(T t){
    top = t;
    t.component.addMouseListener(mouse);
    t.component.addMouseMotionListener(mouse);
    return t;
  }
}