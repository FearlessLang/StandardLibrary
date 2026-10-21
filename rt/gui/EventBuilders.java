package _base;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import javax.swing.SwingUtilities;

import static _base.MouseKind.*;

enum MouseKind{ Clicked, Pressed, Released, Moved, Dragged, Entered, Exited }

record CMouseCtx(
  Instant$5c$0 elapsed,
  XInt$s$0 mouseX,
  YInt$s$0 mouseY,
  _Frame frame,
  WidthNat$as$0 panelWidth,
  HeightNat$lg$0 panelHeight,
  Nat$c$0 clicks
) implements MouseEvent$174$0{
  @Override public Object read$elapsed$0(){ return elapsed; }
  @Override public Object imm$mouseX$0(){ return mouseX; }
  @Override public Object imm$mouseY$0(){ return mouseY; }
  @Override public Object read$panelWidth$0(){ return panelWidth; }
  @Override public Object read$panelHeight$0(){ return panelHeight; }
  @Override public Object imm$clicks$0(){ return clicks; }
}

// Registers Fearless handlers on the widget; SkMouse does all dispatching.
final class CMouseBuilder implements Mouse$1c$0{
  final AWidget owner;
  final EnumMap<MouseKind,Consumer$ao$1> handlers= new EnumMap<>(MouseKind.class);
  boolean changeable= true;// EDT confined
  CMouseBuilder(AWidget owner){ this.owner= owner; }
  private Mouse$1c$0 add(MouseKind k, Object a){
    owner.frame.onEdtAndWait(() -> {
      if (!changeable && (owner.handlers != handlers || !owner.canChange())){
        throw Util.detErr("This Mouse was replaced by a later .mouse, or its widget is not in the window any more, so a handler set now would never run");
      }
      handlers.put(k, (Consumer$ao$1) a);
    });
    return this;
  }
  @Override public Object mut$clicked$1(Object a){ return add(Clicked, a); }
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
  private final _Frame frame;
  private List<AWidget> hover= List.of();// deepest first
  private Point at= new Point();
  private AWidget pressTarget;
  private AWidget dragged;
  private boolean draggedVert;
  private int grab;

  SkMouse(_Frame frame){ this.frame= frame; }

  // A subtree was removed from the live tree: forget every reference into it.
  void detached(SkComponent root){
    assert SwingUtilities.isEventDispatchThread();
    hover = hover.stream().filter(t -> !SwingUtilities.isDescendingFrom(t.component, root)).toList();
    if (pressTarget != null && SwingUtilities.isDescendingFrom(pressTarget.component, root)){ pressTarget = null; }
    if (frame.focus != null && SwingUtilities.isDescendingFrom(frame.focus.component, root)){ frame.focus = null; }
  }

  // The whole tree was replaced (content swap): forget everything.
  void reset(){
    assert SwingUtilities.isEventDispatchThread();
    hover = List.of();
    pressTarget = null;
    frame.focus = null;
  }

  void rehover(){
    assert SwingUtilities.isEventDispatchThread();
    if (java.awt.Toolkit.getDefaultToolkit().getSystemEventQueue().peekEvent(MouseEvent.MOUSE_MOVED) != null){ return; }
    var s= frame.surface.getMousePosition();
    if (s == null){ hoverTo(List.of()); return; }
    var p= frame.stage.canvasPoint(s);
    at = p;
    updateHover(p);
  }

  @Override public void mousePressed(MouseEvent e){
    var p= point(e);
    var d= deepestAt(p);
    boolean left= SwingUtilities.isLeftMouseButton(e);
    frame.pressed(d);
    if (left && d instanceof AEditText t){ frame.focus = t; }
    if (left && d != null && (onBar(d, p, true) || onBar(d, p, false))){ return; }
    if (left){
      pressTarget = d;
      if (d instanceof _Button b){ b.down = true; }
      if (d instanceof AEditText t){ t.press(content(t, p), e.isShiftDown()); }
    }
    dispatch(d, Pressed, p, e.getClickCount());
  }

  @Override public void mouseReleased(MouseEvent e){
    var p= point(e);
    if (dragged != null && SwingUtilities.isLeftMouseButton(e)){ dragged = null; return; }
    boolean left= SwingUtilities.isLeftMouseButton(e);
    // mousePressed only ever captures a left press into pressTarget, so only
    // a left release is interpreted against that press chain (AWT delivers
    // it to the pressed component even if the cursor left it); any other
    // button was dispatched at its own current location on press and is
    // released the same way, not against an unrelated left gesture's target.
    dispatch(left && pressTarget != null ? pressTarget : deepestAt(p), Released, p, e.getClickCount());
    if (!left){ return; }
    if (pressTarget instanceof _Button b){ b.down = false; }
    click(p, e.getClickCount());
    pressTarget = null;
  }

  @Override public void mouseMoved(MouseEvent e){
    var p= point(e);
    updateHover(p);
    dispatch(deepestAt(p), Moved, p, 0);
  }

  @Override public void mouseDragged(MouseEvent e){
    var p= point(e);
    if (dragged != null){ dragThumb(p); return; }
    if (pressTarget instanceof _Button b){ b.down = inside(b, p); }
    if (pressTarget instanceof AEditText t){ t.drag(content(t, p)); }
    updateHover(p);
    dispatch(pressTarget == null ? deepestAt(p) : pressTarget, Dragged, p, 0);
  }

  @Override public void mouseEntered(MouseEvent e){ updateHover(point(e)); }
  @Override public void mouseExited(MouseEvent e){ hoverTo(List.of()); }

  boolean overText(Point p){
    return deepestAt(p) instanceof AEditText t && t.view().contains(SwingUtilities.convertPoint(top(), p, t.component));
  }

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
      dragged = q;
      draggedVert = vert;
      grab = vert ? at.y - m.y : at.x - m.x;
      return true;
    }
    int page= (vert ? at.y < m.y : at.x < m.x) ? -1 : 1;
    if (vert){ q.scrollTo(q.scrollX, q.scrollY + page * q.viewH()); }
    else{ q.scrollTo(q.scrollX + page * q.viewW(), q.scrollY); }
    return true;
  }

  private void dragThumb(Point p){
    var at= SwingUtilities.convertPoint(top(), p, dragged.component);
    var t= dragged.track(draggedVert);
    int s= dragged.scrollFor(draggedVert, draggedVert ? at.y - grab - t.y : at.x - grab - t.x);
    if (draggedVert){ dragged.scrollTo(dragged.scrollX, s); }
    else{ dragged.scrollTo(s, dragged.scrollY); }
  }

  void wheel(Point p, double rotation, boolean horizontal){
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
    for (var t : chainOf(pressTarget)){
      if (!inside(t, p)){ continue; }
      if (t instanceof _Button b){
        if (b.action == null){ continue; }// nothing reaches the programmer: bubble
        frame.frame.queue.submit(whileShown(b, b.action));
        return;
      }
      if (fire(t, Clicked, p, clicks)){ return; }
    }
  }

  private void updateHover(Point p){ hoverTo(chainOf(deepestAt(p))); }

  private void hoverTo(List<AWidget> now){
    var tasks= new ArrayList<MF$7$1>();
    for (var t : hover){// exits, deepest first
      if (now.contains(t)){ continue; }
      if (t instanceof _Button b){ b.over = false; }
      handlers(t, Exited, at, 0, tasks);
    }
    for (var t : now.reversed()){// enters, outermost first
      if (hover.contains(t)){ continue; }
      if (t instanceof _Button b){ b.over = true; }
      handlers(t, Entered, at, 0, tasks);
    }
    hover = now;
    frame.frame.queue.submitAll(tasks);
  }

  private void dispatch(AWidget start, MouseKind kind, Point p, int clicks){
    for (var t : chainOf(start)){
      if (fire(t, kind, p, clicks)){ return; }
    }
  }

  private boolean fire(AWidget t, MouseKind kind, Point p, int clicks){
    var tasks= new ArrayList<MF$7$1>();
    handlers(t, kind, p, clicks, tasks);
    frame.frame.queue.submitAll(tasks);
    return !tasks.isEmpty();
  }

  private void handlers(AWidget t, MouseKind kind, Point p, int clicks, ArrayList<MF$7$1> tasks){
    var h= t.handlers.get(kind);
    if (h == null){ return; }
    var ctx= ctx(t, p, clicks);
    tasks.add(whileShown(t, new MF$7$1(){
      @Override public Object mut$$hash$0(){
        h.mut$accept$1(ctx);
        return Void$o$0.instance;
      }
    }));
  }

  private static MF$7$1 whileShown(AWidget t, MF$7$1 r){
    return new MF$7$1(){
      @Override public Object mut$$hash$0(){
        if (!t.frame.onEdtAndWait(t::canChange)){ return Void$o$0.instance; }
        return r.mut$$hash$0();
      }
    };
  }

  private MouseEvent$174$0 ctx(AWidget t, Point p, int clicks){
    var q= SwingUtilities.convertPoint(top(), p, t.component);
    return new CMouseCtx(
      frame.elapsed,
      Scopes.x(q.x),
      Scopes.y(q.y),
      frame,
      Scopes.w(t.component.getWidth()),
      Scopes.h(t.component.getHeight()),
      Scopes.n(clicks));
  }

  private SkComponent top(){ return frame.top.component; }

  private AWidget deepestAt(Point p){
    AWidget res= SwingUtilities.getDeepestComponentAt(top(), p.x, p.y) instanceof SkComponent s
      ? s.w
      : null;
    for (var t : chainOf(res)){
      boolean hides= (t.barRight() || t.barBottom()) && !t.view().contains(SwingUtilities.convertPoint(top(), p, t.component));
      if (hides){ res = t; }
    }
    return res;
  }

  private boolean inside(AWidget t, Point p){
    return t.component.contains(SwingUtilities.convertPoint(top(), p, t.component));
  }

  private static List<AWidget> chainOf(AWidget t){
    var l= new ArrayList<AWidget>();
    for (Component c= t == null ? null : t.component; c instanceof SkComponent s; c = c.getParent()){
      l.add(s.w);
    }
    return l;
  }
}