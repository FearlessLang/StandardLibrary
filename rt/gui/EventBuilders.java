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
  HeightNat$lg$0 panelHeight
) implements MouseEvent$174$0{
  @Override public Object read$elapsed$0(){ return elapsed; }
  @Override public Object imm$mouseX$0(){ return mouseX; }
  @Override public Object imm$mouseY$0(){ return mouseY; }
  @Override public Object read$screenWidth$0(){ return Scopes.w(frame.screenW); }
  @Override public Object read$screenHeight$0(){ return Scopes.h(frame.screenH); }
  @Override public Object read$panelWidth$0(){ return panelWidth; }
  @Override public Object read$panelHeight$0(){ return panelHeight; }
}

// Registers Fearless handlers on the widget; SkMouse does all dispatching.
record CMouseBuilder(_Frame frame, EnumMap<MouseKind, Consumer$ao$1> handlers) implements Mouse$1c$0{
  private Mouse$1c$0 add(MouseKind k, Object a){
    frame.onEdtAndWait(() -> handlers.put(k, (Consumer$ao$1) a));
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

// The single mouse dispatcher, installed on the top component. All state is
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
  private List<AWidget> hover = List.of();// deepest first
  private Point at = new Point();
  private AWidget pressTarget;
  private _Button pressedButton;

  SkMouse(_Frame frame){ this.frame = frame; }

  // A subtree was removed from the live tree: forget every reference into it.
  // pressedButton is always pressTarget or null, so both clear together.
  void detached(SkComponent root){
    assert SwingUtilities.isEventDispatchThread();
    if (!hover.isEmpty()){
      var keep = new ArrayList<AWidget>();
      for (var t : hover){
        if (!SwingUtilities.isDescendingFrom(t.component, root)){ keep.add(t); }
      }
      if (keep.size() != hover.size()){ hover = List.copyOf(keep); }
    }
    if (pressTarget != null && SwingUtilities.isDescendingFrom(pressTarget.component, root)){
      pressTarget = null;
      pressedButton = null;
    }
  }

  // The whole tree was replaced (content swap): forget everything.
  void reset(){
    assert SwingUtilities.isEventDispatchThread();
    hover = List.of();
    pressTarget = null;
    pressedButton = null;
  }

  void rehover(){
    assert SwingUtilities.isEventDispatchThread();
    if (java.awt.Toolkit.getDefaultToolkit().getSystemEventQueue().peekEvent(MouseEvent.MOUSE_MOVED) != null){ return; }
    var p = top().getMousePosition();
    if (p == null){ hoverTo(List.of()); return; }
    at = p;
    updateHover(p);
  }

  @Override public void mousePressed(MouseEvent e){
    var p = point(e);
    var d = deepestAt(p);
    if (SwingUtilities.isLeftMouseButton(e)){
      pressTarget = d;
      pressedButton = d instanceof _Button b ? b : null;
      if (pressedButton != null){ pressedButton.down = true; }
    }
    dispatch(d, Pressed, p);
  }

  @Override public void mouseReleased(MouseEvent e){
    var p = point(e);
    boolean left = SwingUtilities.isLeftMouseButton(e);
    // mousePressed only ever captures a left press into pressTarget, so only
    // a left release is interpreted against that press chain (AWT delivers
    // it to the pressed component even if the cursor left it); any other
    // button was dispatched at its own current location on press and is
    // released the same way, not against an unrelated left gesture's target.
    dispatch(left && pressTarget != null ? pressTarget : deepestAt(p), Released, p);
    if (!left){ return; }
    if (pressedButton != null){ pressedButton.down = false; }
    click(p);
    pressTarget = null;
    pressedButton = null;
  }

  @Override public void mouseMoved(MouseEvent e){
    var p = point(e);
    updateHover(p);
    dispatch(deepestAt(p), Moved, p);
  }

  @Override public void mouseDragged(MouseEvent e){
    var p = point(e);
    if (pressedButton != null){ pressedButton.down = inside(pressedButton, p); }
    updateHover(p);
    dispatch(pressTarget == null ? deepestAt(p) : pressTarget, Dragged, p);
  }

  @Override public void mouseEntered(MouseEvent e){ updateHover(point(e)); }
  @Override public void mouseExited(MouseEvent e){ hoverTo(List.of()); }

  private Point point(MouseEvent e){
    at = e.getPoint();
    return at;
  }

  private void click(Point p){
    for (var t : chainOf(pressTarget)){
      if (!inside(t, p)){ continue; }
      if (t instanceof _Button b){
        if (b.action == null){ continue; }// nothing reaches the programmer: bubble
        frame.frame.queue.submit(b.action);
        return;
      }
      if (fire(t, Clicked, p)){ return; }
    }
  }

  private void updateHover(Point p){ hoverTo(chainOf(deepestAt(p))); }

  private void hoverTo(List<AWidget> now){
    var tasks = new ArrayList<MF$7$1>();
    for (var t : hover){// exits, deepest first
      if (now.contains(t)){ continue; }
      if (t instanceof _Button b){ b.over = false; }
      handlers(t, Exited, at, tasks);
    }
    for (var t : now.reversed()){// enters, outermost first
      if (hover.contains(t)){ continue; }
      if (t instanceof _Button b){ b.over = true; }
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
    var tasks = new ArrayList<MF$7$1>();
    handlers(t, kind, p, tasks);
    frame.frame.queue.submitAll(tasks);
    return !tasks.isEmpty();
  }

  private void handlers(AWidget t, MouseKind kind, Point p, ArrayList<MF$7$1> tasks){
    var h = t.handlers.get(kind);
    if (h == null){ return; }
    var ctx = ctx(t, p);
    tasks.add(new MF$7$1(){
      @Override public Object mut$$hash$0(){
        h.mut$accept$1(ctx);
        return Void$o$0.instance;
      }
    });
  }

  private MouseEvent$174$0 ctx(AWidget t, Point p){
    var q = SwingUtilities.convertPoint(top(), p, t.component);
    return new CMouseCtx(
      frame.elapsed,
      Scopes.x(q.x),
      Scopes.y(q.y),
      frame,
      Scopes.w(t.component.getWidth()),
      Scopes.h(t.component.getHeight()));
  }

  private SkComponent top(){ return frame.top.component; }

  private AWidget deepestAt(Point p){
    return SwingUtilities.getDeepestComponentAt(top(), p.x, p.y) instanceof SkComponent s
      ? s.w
      : null;
  }

  private boolean inside(AWidget t, Point p){
    return t.component.contains(SwingUtilities.convertPoint(top(), p, t.component));
  }

  private static List<AWidget> chainOf(AWidget t){
    var l = new ArrayList<AWidget>();
    for (Component c = t == null ? null : t.component; c instanceof SkComponent s; c = c.getParent()){
      l.add(s.w);
    }
    return l;
  }
}