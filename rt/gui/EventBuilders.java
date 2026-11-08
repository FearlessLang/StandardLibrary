package _base;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javax.swing.SwingUtilities;

import io.github.humbleui.skija.Canvas;

import static _base.MouseKind.*;

enum MouseKind{ Clicked, DoubleClicked, Pressed, Released, Moved, Dragged, Entered, Exited }

// Registers Fearless handlers on the widget; SkMouse does all dispatching.
final class CMouseBuilder implements Mouse$1c$0{
  final AWidget owner;
  final EnumMap<MouseKind,MouseHandler$4sg$0> handlers= new EnumMap<>(MouseKind.class);
  boolean changeable= true;// EDT confined
  CMouseBuilder(AWidget owner){ this.owner= owner; }
  private Mouse$1c$0 add(MouseKind k, Object a){
    owner.frame.changeOnEdt(() -> {
      if (owner.frame.frame.completed()){ throw Util.detErr("This Mouse belongs to a closed window: the CapGui.run that showed the window has returned, so a handler set now would never run"); }
      if (!changeable && (owner.handlers != handlers || !owner.canChange())){
        throw Util.detErr("This Mouse was replaced by a later .mouse, or its widget is not in the window any more, so a handler set now would never run");
      }
      handlers.put(k, (MouseHandler$4sg$0) a);
    });
    return this;
  }
  @Override public Object mut$clicked$1(Object a){ return add(Clicked, a); }
  @Override public Object mut$doubleClicked$1(Object a){ return add(DoubleClicked, a); }
  @Override public Object mut$pressed$1(Object a){ return add(Pressed, a); }
  @Override public Object mut$released$1(Object a){ return add(Released, a); }
  @Override public Object mut$moved$1(Object a){ return add(Moved, a); }
  @Override public Object mut$dragged$1(Object a){ return add(Dragged, a); }
  @Override public Object mut$entered$1(Object a){ return add(Entered, a); }
  @Override public Object mut$exited$1(Object a){ return add(Exited, a); }
}

// The single mouse dispatcher, fed by Stage in virtual pixels. All state is
// EDT confined.
// - Click = left press + release inside (Win32/Cocoa/Qt/DOM semantics); AWT's
//   MOUSE_CLICKED (which requires a perfectly still pointer) is never used.
// - Pressed/Released/Moved/Dragged bubble from the deepest widget under the
//   gesture to the nearest enclosing widget with a Fearless handler for that
//   kind; the first widget with handlers consumes the event.
// - A click bubbles from the press target to the nearest enclosing widget
//   that consumes clicks AND still contains the release point (the DOM
//   press/release common-ancestor rule). A button with no action does not
//   consume, so clicks on it bubble.
// - Entered/Exited fire on genuine containment transitions computed here by
//   hit-testing, so synthetic AWT enter/exit noise cannot reach them. They
//   fire on every widget entering/leaving the hover chain (mouseenter/
//   mouseleave semantics) and do not bubble.
// - Widget removal (slot replacement, Pane.clear, content swap) prunes the
//   references below without firing Exited: removal is not an exit (DOM
//   semantics), and firing events for widgets that no longer exist would be
//   misleading.
final class SkMouse extends MouseAdapter{
  sealed interface Gesture{ AWidget widget(); }
  record Press(AWidget widget, boolean inside) implements Gesture{}
  record Thumb(AWidget widget, boolean vert, int grab) implements Gesture{}
  record Divide(_Split widget, int divider, int[] from, int grab) implements Gesture{}
  record Swallowed() implements Gesture{ @Override public AWidget widget(){ return null; } }
  private static final Swallowed swallowed= new Swallowed();
  private final _Frame frame;
  private List<AWidget> hover= List.of();// deepest first
  private Point at= new Point();
  private Gesture gesture;
  private AWidget secondary;
  private AWidget tip;
  private long tipSince;
  private Point tipAt;
  private boolean tipOff;
  private Sk.Tip shown;

  SkMouse(_Frame frame){ this.frame= frame; }

  // A subtree was removed from the live tree: forget every reference into it.
  void detached(SkComponent root){
    assert SwingUtilities.isEventDispatchThread();
    hover = hover.stream().filter(t -> !SwingUtilities.isDescendingFrom(t.component, root)).toList();
    var w= gesture == null ? null : gesture.widget();
    if (w != null && SwingUtilities.isDescendingFrom(w.component, root)){ stop(); }
    if (frame.focus != null && SwingUtilities.isDescendingFrom(frame.focus.component, root)){ frame.focus(null); }
  }

  void hidden(SkComponent root){
    assert SwingUtilities.isEventDispatchThread();
    hoverTo(hover.stream().filter(t -> !SwingUtilities.isDescendingFrom(t.component, root)).toList());
    detached(root);
  }

  // The whole tree was replaced (content swap): forget everything.
  void reset(){
    assert SwingUtilities.isEventDispatchThread();
    hover = List.of();
    stop();
    frame.focus(null);
  }

  void stopDividing(_Split s){ if (gesture instanceof Divide d && d.widget() == s){ stop(); } }

  private void stop(){ gesture = gesture == null || gesture instanceof Press ? null : swallowed; }

  void forget(){ gesture = null; }

  private AWidget pressTarget(){ return gesture instanceof Press q ? q.widget() : null; }

  void rehover(){
    assert SwingUtilities.isEventDispatchThread();
    if (frame.menus.isOpen()){ return; }
    if (java.awt.Toolkit.getDefaultToolkit().getSystemEventQueue().peekEvent(MouseEvent.MOUSE_MOVED) != null){ return; }
    var s= frame.surface.getMousePosition();
    if (s == null){ hoverTo(List.of()); return; }
    var p= frame.stage.canvasPoint(s);
    at = p;
    updateHover(p);
  }

  boolean over(AWidget t){ return !frame.menus.isOpen() && hover.contains(t); }

  boolean down(AWidget t){ return gesture instanceof Press q && q.widget() == t && q.inside(); }

  void paintTip(Canvas cv){
    var owner= tipOwner();
    if (owner != tip){ tip = owner; tipSince = System.nanoTime(); tipAt = null; }
    if (tip == null || tipOff || System.nanoTime() - tipSince < 500_000_000L){ return; }
    if (tipAt == null){ tipAt = at; }
    if (shown == null || !shown.text().equals(tip.tooltip)){
      shown = Sk.tip(tip.tooltip);
    }
    Sk.tooltip(cv, shown, tipAt, top().getWidth(), top().getHeight());
  }
  private AWidget tipOwner(){
    if (frame.menus.isOpen()){ return null; }
    for (int i= 0; i < hover.size(); i += 1){ if (!hover.get(i).tooltip.isEmpty()){ return hover.get(i); } }
    return null;
  }

  @Override public void mousePressed(MouseEvent e){
    tipOff = true;
    var p= point(e);
    boolean left= SwingUtilities.isLeftMouseButton(e);
    if (frame.menus.pressed(p, e.getClickCount(), left)){ return; }
    var d= deepestAt(p);
    if (SwingUtilities.isRightMouseButton(e)){ secondary = d; }
    frame.pressed(d);
    if (left && d instanceof AEditText t){ frame.focus(t); }
    if (left && d != null && (onBar(d, p, true) || onBar(d, p, false))){ return; }
    if (left && onDivider(d, p)){
      var s= (_Split) d;
      var at= local(s, p);
      int i= s.dividerAt(at);
      var from= s.positions.clone();
      gesture = new Divide(s, i, from, spanAt(s, at) - from[i]);
      return;
    }
    if (left){
      gesture = new Press(d, true);
      for (var t : chainOf(d)){ if (t instanceof _Sections s){ s.pressed = s.part(local(s, p)); } }
      if (d instanceof AEditText t){ t.press(content(t, p), e.isShiftDown()); }
    }
    dispatch(d, Pressed, p);
  }

  @Override public void mouseReleased(MouseEvent e){
    var p= point(e);
    boolean left= SwingUtilities.isLeftMouseButton(e);
    int buttons= MouseEvent.BUTTON1_DOWN_MASK | MouseEvent.BUTTON2_DOWN_MASK | MouseEvent.BUTTON3_DOWN_MASK;
    if (frame.menus.released(p, left, (e.getModifiersEx() & buttons) == 0)){ return; }
    if (left && gesture != null && !(gesture instanceof Press)){ gesture = null; return; }
    // mousePressed only ever captures a left press into pressTarget, so only
    // a left release is interpreted against that press chain (AWT delivers
    // it to the pressed component even if the cursor left it); any other
    // button was dispatched at its own current location on press and is
    // released the same way, not against an unrelated left gesture's target.
    var t= pressTarget();
    dispatch(left && t != null ? t : deepestAt(p), Released, p);
    if (SwingUtilities.isRightMouseButton(e)){ context(p); }
    if (!left){ return; }
    click(p, e.getClickCount());
    gesture = null;
  }

  @Override public void mouseMoved(MouseEvent e){
    if (tipOff || tipAt == null){ tipOff = false; tipSince = System.nanoTime(); tipAt = null; }
    var p= point(e);
    if (frame.menus.moved(p)){ return; }
    updateHover(p);
    dispatch(deepestAt(p), Moved, p);
  }

  @Override public void mouseDragged(MouseEvent e){
    var p= point(e);
    if (frame.menus.moved(p)){ return; }
    if (gesture instanceof Swallowed){ return; }
    if (gesture instanceof Thumb g){ dragThumb(g, p); return; }
    if (gesture instanceof Divide g){
      g.widget().drag(g.divider(), g.from(), spanAt(g.widget(), local(g.widget(), p)) - g.grab());
      return;
    }
    var t= pressTarget();
    if (t instanceof _Button b){ gesture = new Press(b, inside(b, p)); }
    if (t instanceof AEditText a){ a.drag(content(a, p)); }
    updateHover(p);
    dispatch(t == null ? deepestAt(p) : t, Dragged, p);
  }

  @Override public void mouseEntered(MouseEvent e){ if (!frame.menus.isOpen()){ updateHover(point(e)); } }
  @Override public void mouseExited(MouseEvent e){ if (!frame.menus.isOpen()){ hoverTo(List.of()); } }

  int cursor(Point p){
    if (frame.menus.isOpen()){ return Cursor.DEFAULT_CURSOR; }
    var d= deepestAt(p);
    if (d instanceof AEditText t && t.view().contains(SwingUtilities.convertPoint(top(), p, t.component))){ return Cursor.TEXT_CURSOR; }
    if (onDivider(d, p)){ return ((_Split) d).vertical ? Cursor.N_RESIZE_CURSOR : Cursor.E_RESIZE_CURSOR; }
    return Cursor.DEFAULT_CURSOR;
  }

  private boolean onDivider(AWidget d, Point p){
    return d instanceof _Split s && s.draggable && s.dividerAt(local(s, p)) >= 0;
  }

  private static int spanAt(_Split s, Point at){ return s.vertical ? at.y - s.top : at.x - s.left; }

  private Point content(AEditText t, Point p){
    var q= SwingUtilities.convertPoint(top(), p, t.component);
    return new Point(q.x - t.left + t.scrollX, q.y - t.top + t.scrollY);
  }

  private boolean onBar(AWidget q, Point p, boolean vert){
    if (!(vert ? q.barRight() : q.barBottom())){ return false; }
    var at= SwingUtilities.convertPoint(top(), p, q.component);
    if (!q.track(vert).contains(at)){ return false; }
    var m= q.thumb(vert);
    if (m.contains(at)){
      gesture = new Thumb(q, vert, vert ? at.y - m.y : at.x - m.x);
      return true;
    }
    gesture = swallowed;
    int page= (vert ? at.y < m.y : at.x < m.x) ? -1 : 1;
    if (vert){ q.scrollTo(q.scrollX, q.scrollY + page * q.viewH()); }
    else{ q.scrollTo(q.scrollX + page * q.viewW(), q.scrollY); }
    return true;
  }

  private void dragThumb(Thumb g, Point p){
    var q= g.widget();
    boolean v= g.vert();
    var at= SwingUtilities.convertPoint(top(), p, q.component);
    var t= q.track(v);
    int s= AWidget.scrollFor(t, q.thumb(v), v, v ? at.y - g.grab() - t.y : at.x - g.grab() - t.x, v ? q.maxScrollY() : q.maxScrollX());
    if (v){ q.scrollTo(q.scrollX, s); }
    else{ q.scrollTo(s, q.scrollY); }
  }

  void wheel(Point p, double rotation, boolean horizontal){
    if (frame.menus.isOpen()){
      if (!horizontal){ frame.menus.wheel(p, rotation); }
      return;
    }
    int d= (int) Math.round(rotation * 40);
    for (var t : chainOf(deepestAt(p))){
      if (!(horizontal ? t.barBottom() : t.barRight())){ continue; }
      boolean moved= horizontal ? t.scrollTo(t.scrollX + d, t.scrollY) : t.scrollTo(t.scrollX, t.scrollY + d);
      if (moved){ return; }
    }
  }

  private Point point(MouseEvent e){
    at = e.getPoint();
    return at;
  }

  private void click(Point p, int clicks){
    for (var t : chainOf(pressTarget())){
      if (!inside(t, p)){ continue; }
      if (t instanceof _Button b){
        if (b.action == null){ continue; }// nothing reaches the programmer: bubble
        frame.frame.queue.submit(whileShown(b, b.action));
        return;
      }
      if (t instanceof _Sections s && s.click(local(s, p))){ return; }
      if (clicks == 2 && fire(t, DoubleClicked, p)){ return; }
      if (fire(t, Clicked, p)){ return; }
      if (t.handlers.containsKey(DoubleClicked)){ return; }
    }
  }

  private void context(Point p){
    var from= secondary;
    secondary = null;
    for (var t : chainOf(from)){
      if (t.contextMenu == null || !inside(t, p)){ continue; }
      var s= t.contextMenu;
      frame.frame.queue.submit(whileShown(t, new MF$7$1(){ @Override public Object mut$$hash$0(){
        var m= CMenu.build(s);
        frame.changeOnEdt(() -> frame.menus.show(m, new Rectangle(p.x, p.y, 0, 0)));
        return Void$o$0.instance;
      }}));
      return;
    }
  }

  private void updateHover(Point p){ hoverTo(chainOf(deepestAt(p))); }

  private void hoverTo(List<AWidget> now){
    var tasks= new ArrayList<MF$7$1>();
    for (var t : hover){// exits, deepest first
      if (now.contains(t)){ continue; }
      handlers(t, Exited, at, tasks);
    }
    for (var t : now.reversed()){// enters, outermost first
      if (hover.contains(t)){ continue; }
      handlers(t, Entered, at, tasks);
    }
    hover = now;
    frame.frame.queue.submitAll(tasks);
  }

  private void dispatch(AWidget start, MouseKind kind, Point p){
    for (var t : chainOf(start)){
      if (fire(t, kind, p)){ return; }
    }
  }

  private boolean fire(AWidget t, MouseKind kind, Point p){
    var tasks= new ArrayList<MF$7$1>();
    handlers(t, kind, p, tasks);
    frame.frame.queue.submitAll(tasks);
    return !tasks.isEmpty();
  }

  private void handlers(AWidget t, MouseKind kind, Point p, ArrayList<MF$7$1> tasks){
    var h= t.handlers.get(kind);
    if (h == null){ return; }
    var q= SwingUtilities.convertPoint(top(), p, t.component);
    var x= Scopes.x(q.x);
    var y= Scopes.y(q.y);
    var elapsed= frame.elapsed;
    var w= Scopes.w(t.component.getWidth());
    var ht= Scopes.h(t.component.getHeight());
    tasks.add(whileShown(t, new MF$7$1(){
      @Override public Object mut$$hash$0(){
        h.mut$$hash$5(x, y, elapsed, w, ht);
        return Void$o$0.instance;
      }
    }));
  }

  static MF$7$1 whileShown(AWidget t, MF$7$1 r){
    return new MF$7$1(){
      @Override public Object mut$$hash$0(){
        if (!t.frame.onEdtAndWait(t::canChange)){ return Void$o$0.instance; }
        return r.mut$$hash$0();
      }
    };
  }

  private SkComponent top(){ return frame.top.component; }

  private AWidget deepestAt(Point p){ return deepest(top(), p.x, p.y); }

  private static AWidget deepest(SkComponent c, int x, int y){
    if (!c.contains(x, y)){ return null; }
    var w= c.w;
    for (int i= c.getComponentCount() - 1; i >= 0; i -= 1){
      var k= c.getComponent(i);
      var r= w.clip(k);
      if (w.hides(k) || r != null && !r.contains(x, y)){ continue; }
      var d= deepest((SkComponent) k, x - k.getX(), y - k.getY());
      if (d != null){ return d; }
    }
    return w;
  }

  private boolean inside(AWidget t, Point p){ return t.component.contains(local(t, p)); }

  private Point local(AWidget t, Point p){ return SwingUtilities.convertPoint(top(), p, t.component); }

  private static List<AWidget> chainOf(AWidget t){
    var l= new ArrayList<AWidget>();
    for (Component c= t == null ? null : t.component; c instanceof SkComponent s; c = c.getParent()){
      l.add(s.w);
    }
    return l;
  }
}