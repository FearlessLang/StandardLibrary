package _base;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.Path;
import io.github.humbleui.skija.PathBuilder;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.types.Rect;

final class Stage extends MouseAdapter{
  enum Mode{ decorated, fearless, fullScreen }
  static final Image cornerIcon= AssetImageRead.decode("icon", _FluentGUI.iconBytes(IconsFearless$9kw$0.instance), Scopes.maxImagePixels);
  final _Frame owner;
  final _FluentGUI gui;
  final GraphicsDevice device= GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
  final GraphicsConfiguration gc= device.getDefaultConfiguration();
  final Rectangle screen= gc.getBounds();
  final boolean translucent= device.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT);
  Win f;
  int canvasW;
  int canvasH;
  float opacity;
  Mode mode;
  Dimension lastSize= new Dimension();
  Dimension chosen= new Dimension();
  boolean deskResized;
  boolean unmaximized;
  Rectangle restore;
  boolean reshaping;
  Point pressAt;
  Rectangle pressBounds;
  boolean resizing;
  boolean dragged;
  int clicks;
  long clickWhen;
  Point clickAt= new Point();
  int clickButton;
  boolean opensFullScreen;
  boolean activating;
  boolean handed;
  Rectangle pending;
  Rectangle maxed= new Rectangle();
  Rectangle seen= new Rectangle();
  boolean deskMaximized;

  Stage(_Frame owner, int canvasW, int canvasH, float opacity){
    this.owner= owner;
    this.gui= owner.gui;
    this.canvasW = canvasW;
    this.canvasH = canvasH;
    this.opacity = opacity;
    owner.surface.addMouseListener(this);
    owner.surface.addMouseMotionListener(this);
    owner.surface.addMouseWheelListener(this);
    owner.surface.addKeyListener(new KeyAdapter(){ @Override public void keyPressed(KeyEvent e){ if (!e.isConsumed()){ reservedKey(e.getKeyCode()); } } });
    if (Desk.managed){ open(); return; }
    realize(switch (gui.start){ case fullScreen -> Mode.fullScreen; case undecorated -> Mode.fearless; case window, maximised -> Mode.decorated; });
    if (gui.start == _FluentGUI.Start.maximised){ f.setExtendedState(Frame.MAXIMIZED_BOTH); }
  }
  @SuppressWarnings("serial")
  final class Win extends JFrame{
    Win(){ super(gc); }
    Stage stage(){ return Stage.this; }
    @Override public Dimension getMinimumSize(){
      var d= super.getMinimumSize();
      double k= reshaping || !Desk.windows ? 1 : gc.getDefaultTransform().getScaleX();
      return new Dimension((int)Math.ceil(d.width*k), (int)Math.ceil(d.height*k));
    }
    @SuppressWarnings("deprecation") @Override public void reshape(int x, int y, int width, int height){
      reshaping = true;
      super.reshape(x, y, width, height);
      reshaping = false;
    }
  }
  private Win makeFrame(boolean undecorated){
    var frame= new Win();
    frame.setUndecorated(undecorated);
    frame.setBackground(java.awt.Color.black);
    frame.setContentPane(owner.surface);
    frame.setIconImage(gui.awtIcon);
    frame.setTitle(owner.windowTitle());
    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    var l= new WindowAdapter(){
      @Override public void windowClosing(WindowEvent e){ owner.closeRequest(); }
      @Override public void windowGainedFocus(WindowEvent e){ owner.surface.requestFocusInWindow(); }
    };
    frame.addWindowListener(l);
    frame.addWindowFocusListener(l);
    frame.addWindowStateListener(this::stateChanged);
    new DropTarget(frame, DnDConstants.ACTION_COPY, new DropTargetAdapter(){
      @Override public void drop(DropTargetDropEvent e){ FearlessFrame.dropped(e); }
    });
    Desk.identify(frame, gui.iconName, gui.name);
    return frame;
  }
  private void reservedKey(int code){
    if (code == _FluentGUI.code(gui.closeKey)){ owner.closeRequest(); return; }
    if (code != _FluentGUI.code(gui.fullScreenKey)){ return; }
    if (Desk.managed){ Desk.fullScreen(f, mode != Mode.fullScreen); return; }
    realize(mode == Mode.fullScreen ? Mode.fearless : Mode.fullScreen);
  }
  private void open(){
    f = makeFrame(false);
    mode = Mode.decorated;
    f.pack();
    place(opening());
    minimum();
    opensFullScreen = gui.start == _FluentGUI.Start.fullScreen;
    f.setVisible(true);
    f.toFront();
  }
  void realize(Mode m){
    var old= f;
    var r= old != null && mode != Mode.fullScreen ? old.getBounds() : fit(workArea(), old == null ? openingWidth() : leavingWidth());
    boolean undecorated= m != Mode.decorated;
    if (old == null || old.isUndecorated() != undecorated){ f = makeFrame(undecorated); }
    boolean replaced= old != null && old != f;
    if (replaced && !Desk.windows){ old.dispose(); }
    mode = m;
    deskResized = false;
    unmaximized = false;
    deskMaximized = false;
    switch (m){
      case fullScreen -> enterFullScreen();
      case fearless -> {
        if (device.getFullScreenWindow() == f){ device.setFullScreenWindow(null); }
        place(r);
        if (translucent){ f.setOpacity(opacity); }
      }
      case decorated -> {
        f.pack();
        place(old == null ? opening() : compacted(inset(r, f.getInsets())));
      }
    }
    minimum();
    f.setVisible(true);
    f.toFront();
    owner.surface.requestFocusInWindow();
    activating = replaced && !Desk.windows;
    if (replaced && Desk.windows){ old.dispose(); }
  }
  private void enterFullScreen(){
    if (device.getFullScreenWindow() instanceof Win w && w != f){ w.stage().realize(Mode.fearless); }
    if (!Desk.windows && !f.isShowing()){ place(fit(workArea(), leavingWidth())); f.setVisible(true); }
    device.setFullScreenWindow(f);
  }
  void dispose(){
    if (device.getFullScreenWindow() == f){ device.setFullScreenWindow(null); }
    f.dispose();
  }
  private Rectangle opening(){ return fit(inset(workArea(), f.getInsets()), openingWidth()); }
  private Rectangle workArea(){ return Desk.cosmic ? Desk.cosmicArea : inset(screen, Toolkit.getDefaultToolkit().getScreenInsets(gc)); }
  private static Rectangle inset(Rectangle r, Insets in){ return new Rectangle(r.x+in.left, r.y+in.top, r.width-in.left-in.right, r.height-in.top-in.bottom); }
  private static int within(int v, int low, int high){ return Math.max(low, Math.min(v, high)); }
  private Rectangle outset(Rectangle content){
    var in= f.getInsets();
    return new Rectangle(content.x-in.left, content.y-in.top, content.width+in.left+in.right, content.height+in.top+in.bottom);
  }
  private void place(Rectangle content){
    chosen = content.getSize();
    f.setBounds(outset(content));
    restore = f.getBounds();
    if (Desk.cosmic){ pending = restore; }
  }
  private void minimum(){
    var in= f.getInsets();
    var d= size(areaWidth(gui.minArea));
    var m= new Dimension(d.width+in.left+in.right, d.height+in.top+in.bottom);
    f.setMinimumSize(m);
    if (!maximized() || !Desk.windows){ return; }
    restore.setSize(Math.max(restore.width, m.width), Math.max(restore.height, m.height));
    Desk.restoreSize(f, restore, true);
  }
  private Rectangle compacted(Rectangle r){
    var d= size(fitWidth(r.width, r.height));
    return new Rectangle(r.x+(r.width-d.width)/2, r.y, d.width, d.height);
  }
  private Rectangle rescaled(Rectangle r){
    var room= inset(workArea(), f.getInsets());
    if (Desk.cosmic){
      var d= clamped(Math.sqrt(r.width*(double)r.height*canvasW/canvasH), room.x+room.width-r.x, room.y+room.height-r.y);
      return new Rectangle(r.x, r.y, d.width, d.height);
    }
    var d= clamped(Math.sqrt(r.width*(double)r.height*canvasW/canvasH), room.width, room.height);
    int x= within(r.x+(r.width-d.width)/2, room.x, room.x+room.width-d.width);
    return new Rectangle(x, within(r.y+(r.height-d.height)/2, room.y, room.y+room.height-d.height), d.width, d.height);
  }
  private Dimension size(double w){
    int iw= (int)w;
    return new Dimension(iw, (int)Math.round(iw*(double)canvasH/canvasW));
  }
  private double fitWidth(double w, double h){ return Math.min(w, h*canvasW/canvasH); }
  private Rectangle fit(Rectangle r, double w){
    var d= size(Math.min(w, fitWidth(r.width, r.height)));
    return new Rectangle(r.x+(r.width-d.width)/2, r.y+(r.height-d.height)/2, d.width, d.height);
  }
  private double openingWidth(){ return gui.start == _FluentGUI.Start.maximised ? leavingWidth() : areaWidth(gui.initialArea); }
  private double leavingWidth(){
    var w= workArea();
    return 0.77*fitWidth(w.width, w.height);
  }
  private double areaWidth(double area){
    var w= workArea();
    return Math.min(Math.sqrt(area*screen.width*screen.height*canvasW/canvasH), fitWidth(w.width, w.height));
  }
  private Dimension clamped(double w, int roomW, int roomH){ return size(Math.max(Math.min(w, fitWidth(roomW, roomH)), areaWidth(gui.minArea))); }
  private boolean maximized(){ return mode == Mode.decorated && !Desk.managed && (f.getExtendedState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH; }

  private void request(Dimension d){
    if (mode == Mode.fearless || d.width == 0 || d.height == 0){ return; }
    owner.resizeRequest(d.getWidth()/d.getHeight());
  }
  void canvas(int w, int h){
    if (w == canvasW && h == canvasH){ return; }
    var old= inset(bounds(), f.getInsets());
    canvasW = w;
    canvasH = h;
    minimum();
    if (mode == Mode.fullScreen || maximized() || deskResized || Desk.managed){ return; }
    place(rescaled(old));
  }
  void opacity(float a){
    opacity = a;
    if (mode == Mode.fearless && translucent){ f.setOpacity(a); }
  }

  void tick(){
    if (Desk.managed){ managedState(); }
    if (activating && Desk.managing(f)){ activating = false; Desk.activate(f); }
    if (x11Unmaximize() && mode == Mode.decorated){ unmaximizeX11(); }
    var now= owner.surface.getSize();
    boolean deskWindow= mode == Mode.decorated && !maximized() && !Desk.managed;
    deskResized &= deskWindow;
    boolean fitted= Desk.cosmic && cosmicTick(deskWindow);
    if (!Desk.cosmic && deskWindow && !unmaximized && (Desk.resizeUnseen || sameSize(f.getBounds(), restore))){ restore = f.getBounds(); }
    boolean deskChose= !near(now.width, chosen.width) || !near(now.height, chosen.height);
    if (!now.equals(lastSize) && deskChose && !fitted){ deskResized |= deskWindow && !Desk.resizeUnseen; request(now); }
    if (Desk.managed && deskChose && now.width > 0){ chosen = new Dimension(); }
    lastSize = now;
    if (unmaximized && (Desk.cosmic ? sameSize(f.getBounds(), restore) : !Desk.down())){ unmaximized(); return; }
    if (deskResized && !Desk.down()){ compact(); }
  }
  private boolean cosmicTick(boolean deskWindow){
    if (maximized()){
      var m= Desk.bounds(f);
      int top= Desk.cosmicArea.y;
      Desk.cosmicArea = new Rectangle(m.x, top, m.width, m.y+m.height-top);
    }
    if (deskWindow && !unmaximized && pending == null){ restore = f.getBounds(); }
    if (pending == null || !Desk.managing(f) || Desk.fullScreen(f)){ return false; }
    var real= Desk.bounds(f);
    if (real.equals(screen)){ return false; }
    var in= f.getInsets();
    var room= inset(workArea(), in);
    var d= clamped(pending.width-in.left-in.right, room.x+room.width-real.x, room.y+room.height-real.y);
    pending = null;
    if (real.getSize().equals(d)){ return false; }
    chosen = d;
    Desk.resize(f, d);
    return true;
  }
  private Rectangle bounds(){ return Desk.cosmic ? outset(Desk.bounds(f)) : f.getBounds(); }
  private static boolean sameSize(Rectangle a, Rectangle b){ return near(a.width, b.width) && near(a.height, b.height); }
  private void managedState(){
    if (opensFullScreen && Desk.managing(f)){ opensFullScreen = false; Desk.fullScreen(f, true); }
    mode = Desk.fullScreen(f) ? Mode.fullScreen : Mode.decorated;
  }
  private void stateChanged(WindowEvent e){
    if (e.getComponent() != f || mode != Mode.decorated || Desk.managed){ return; }
    boolean was= (e.getOldState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH;
    boolean is= (e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH;
    if (!was && is && Desk.windows){ restore = outset(compacted(inset(fit(workArea(), leavingWidth()), f.getInsets()))); minimum(); return; }
    unmaximized = !x11Unmaximize() && was && (e.getNewState() & (Frame.MAXIMIZED_BOTH|Frame.ICONIFIED)) == 0;
  }
  private static boolean near(int a, int b){ return Math.abs(a-b) <= 1; }
  private boolean x11Unmaximize(){ return !Desk.windows && !Desk.mac && !Desk.cosmic && !Desk.managed; }
  private void unmaximizeX11(){
    var now= Desk.bounds(f);
    if (Desk.maximized(f)){ maxed = now; deskMaximized = true; return; }
    if (!deskMaximized){ seen = now; return; }
    if (now.equals(maxed) || Desk.down()){ return; }
    deskMaximized = false;
    if (now.x == seen.x && now.y == seen.y){ realize(Mode.fullScreen); return; }
    compact();
  }
  private void unmaximized(){
    unmaximized = false;
    var b= f.getBounds();
    boolean restored= near(b.x, restore.x) && near(b.y, restore.y) && near(b.width, restore.width) && near(b.height, restore.height);
    if (restored){ realize(Mode.fullScreen); return; }
    compact();
  }
  private void compact(){
    deskResized = false;
    place(compacted(inset(f.getBounds(), f.getInsets())));
    if (Desk.windows){ Desk.restoreSize(f, f.getBounds(), false); }
  }

  double scale(){ return fitWidth(owner.surface.getWidth(), owner.surface.getHeight())/canvasW; }
  Point offset(double s){ return new Point((int)((owner.surface.getWidth()-canvasW*s)/2), (int)((owner.surface.getHeight()-canvasH*s)/2)); }
  Point canvasPoint(Point p){
    if (inCorner(p)){ return new Point(-1, -1); }
    double s= scale();
    var o= offset(s);
    return new Point((int)Math.floor((p.x-o.x)/s), (int)Math.floor((p.y-o.y)/s));
  }
  private int legs(){ return Math.min(screen.height/16, owner.surface.getHeight()/4); }
  private int tip(){ return legs()/5; }
  private boolean inCorner(Point p){ return (mode != Mode.decorated || Desk.managed) && p.x+p.y < legs(); }
  private boolean inTip(Point p){ return mode == Mode.fearless && p.x < tip() && p.y < tip(); }
  private void forward(MouseEvent e){
    var p= canvasPoint(e.getPoint());
    var v= new MouseEvent(owner.top.component, e.getID(), e.getWhen(), e.getModifiersEx(), p.x, p.y, clicks, false, e.getButton());
    switch (e.getID()){
      case MouseEvent.MOUSE_PRESSED -> owner.mouse.mousePressed(v);
      case MouseEvent.MOUSE_RELEASED -> owner.mouse.mouseReleased(v);
      case MouseEvent.MOUSE_MOVED -> owner.mouse.mouseMoved(v);
      case MouseEvent.MOUSE_DRAGGED -> owner.mouse.mouseDragged(v);
      case MouseEvent.MOUSE_ENTERED -> owner.mouse.mouseEntered(v);
      default -> owner.mouse.mouseExited(v);
    }
  }

  @Override public void mouseWheelMoved(MouseWheelEvent e){
    if (inCorner(e.getPoint())){ return; }
    owner.mouse.wheel(canvasPoint(e.getPoint()), e.getPreciseWheelRotation(), e.isShiftDown());
  }
  @Override public void mouseEntered(MouseEvent e){ forward(e); }
  @Override public void mouseExited(MouseEvent e){ forward(e); }
  @Override public void mouseMoved(MouseEvent e){
    var p= e.getPoint();
    int cursor= !inCorner(p) ? owner.mouse.cursor(canvasPoint(p)) : inTip(p) ? Cursor.NW_RESIZE_CURSOR : Cursor.MOVE_CURSOR;
    owner.surface.setCursor(Cursor.getPredefinedCursor(cursor));
    forward(e);
  }
  private void count(MouseEvent e){
    var again= e.getButton() == clickButton && e.getWhen() - clickWhen <= 500 && e.getLocationOnScreen().distance(clickAt) <= 4;
    clicks = again && clicks == 1 ? 2 : 1;
    clickWhen = e.getWhen();
    clickAt = e.getLocationOnScreen();
    clickButton = e.getButton();
  }
  @Override public void mousePressed(MouseEvent e){
    count(e);
    if (e.getButton() != MouseEvent.BUTTON1 || !inCorner(e.getPoint())){ forward(e); return; }
    owner.pressed(null);
    if (clicks == 2 || Desk.managed){ return; }
    pressAt = e.getLocationOnScreen();
    pressBounds = f.getBounds();
    resizing = inTip(e.getPoint());
    dragged = false;
  }
  @Override public void mouseDragged(MouseEvent e){
    if (pressAt == null){ forward(e); return; }
    var p= e.getLocationOnScreen();
    if (!dragged && p.distance(pressAt) <= DragSource.getDragThreshold()){ return; }
    dragged = true;
    if (Desk.cosmic){ handOver(p); return; }
    if (mode == Mode.fullScreen){ realize(Mode.fearless); }
    var at= new Point(pressBounds.x+p.x-pressAt.x, pressBounds.y+p.y-pressAt.y);
    place(resizing ? resized(at) : moved(at));
  }
  @Override public void mouseReleased(MouseEvent e){
    boolean corner= e.getButton() == MouseEvent.BUTTON1 && clicks == 2 && inCorner(e.getPoint());
    if (corner && Desk.managed){ Desk.fullScreen(f, mode != Mode.fullScreen); return; }
    if (corner){ realize(Mode.decorated); return; }
    if (e.getButton() != MouseEvent.BUTTON1 || pressAt == null){ forward(e); return; }
    pressAt = null;
    handed = false;
    if (Desk.cosmic && dragged && mode == Mode.fearless){ pending = resizing ? compacted(bounds()) : bounds(); }
    if (!dragged || mode != Mode.fearless){ return; }
    var b= bounds();
    if (new Point(b.x+e.getX(), b.y+e.getY()).distance(workArea().getLocation()) <= screen.height/16){ realize(Mode.fullScreen); }
  }
  private void handOver(Point p){
    if (handed){ return; }
    handed = true;
    if (mode == Mode.fullScreen){ realize(Mode.fearless); return; }
    Desk.moveResize(f, p, resizing ? 0 : 8);
  }
  private Rectangle moved(Point at){
    var w= workArea();
    int right= w.x+w.width;
    int bottom= w.y+w.height;
    int x= Math.max(w.x, at.x);
    int y= Math.max(w.y, at.y);
    var d= clamped(restore.width, right-x, bottom-y);
    return new Rectangle(Math.min(x, right-d.width), Math.min(y, bottom-d.height), d.width, d.height);
  }
  private Rectangle resized(Point at){
    int right= pressBounds.x+pressBounds.width;
    int bottom= pressBounds.y+pressBounds.height;
    var w= workArea();
    var d= clamped(Math.max(right-at.x, (bottom-at.y)*(double)canvasW/canvasH), right-w.x, bottom-w.y);
    return new Rectangle(right-d.width, bottom-d.height, d.width, d.height);
  }

  void paintCorner(Canvas cv){
    if (mode == Mode.decorated && !Desk.managed){ return; }
    int l= legs();
    var pb= new PathBuilder();
    pb.moveTo(0, 0);
    pb.lineTo(l, 0);
    pb.lineTo(0, l);
    pb.closePath();
    try (Path tri= pb.build()){
      Sk.paint.setColor(0xFF1C1C20);
      cv.drawPath(tri, Sk.paint);
      int save= cv.save();
      cv.clipPath(tri, ClipMode.INTERSECT, true);
      int side= l/2;
      int at= (int)Math.round(l*(2-Math.sqrt(2))/2)-side/2;
      var icon= cornerIcon;
      cv.drawImageRect(icon, Rect.makeWH(icon.getWidth(), icon.getHeight()), Rect.makeXYWH(at, at, side, side), SamplingMode.LINEAR, null, true);
      cv.restoreToCount(save);
    }
    if (mode != Mode.fearless){ return; }
    Sk.paint.setColor(0xFF404046);
    cv.drawRect(Rect.makeWH(tip(), tip()), Sk.paint);
  }
}
