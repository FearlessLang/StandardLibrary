package base;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

import static base.MouseKind.*;

enum MouseKind{ Clicked, Pressed, Released, Moved, Dragged, Entered, Exited }

record CMouseCtx(
  Time$0 elapsed,
  XNat$0 mouseX,
  YNat$0 mouseY,
  WidthNat$0 screenWidth,
  HeightNat$0 screenHeight,
  WidthNat$0 panelWidth,
  HeightNat$0 panelHeight
) implements MouseEvent$0{
  public Object imm$elapsed$0(){ return elapsed; }
  public Object imm$mouseX$0(){ return mouseX; }
  public Object imm$mouseY$0(){ return mouseY; }
  public Object imm$screenWidth$0(){ return screenWidth; }
  public Object imm$screenHeight$0(){ return screenHeight; }
  public Object imm$panelWidth$0(){ return panelWidth; }
  public Object imm$panelHeight$0(){ return panelHeight; }
}

// Registers Fearless handlers on the widget; SkMouse does all dispatching.
record CMouseBuilder(AWidget panel) implements Mouse$0{
  private Mouse$0 add(MouseKind k, Object a){
    panel.frame.onEdtAndWait(() ->
      panel.handlers.computeIfAbsent(k, _ -> new ArrayList<>()).add((Consumer$1) a));
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
//   press/release common-ancestor rule). A button with no actions does not
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
  boolean down;// any mouse button held; read by _Frame.tick to postpone relayout
  private List<AWidget> hover = List.of();// deepest first
  private Point at = new Point();
  private AWidget pressTarget;
  private _Button pressedButton;

  SkMouse(_Frame frame){ this.frame = frame; }

  // A subtree was removed from the live tree: forget every reference into it.
  // The gesture itself continues on the (unchanged) top component, so `down`
  // is untouched. pressedButton is always pressTarget or null, so both clear
  // together.
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

  // The whole tree was replaced (content swap): forget everything, including
  // `down`. The old top component holds the AWT mouse grab for any gesture in
  // progress but no longer has listeners, so its release would never arrive
  // here; leaving `down` true would postpone relayout forever.
  void reset(){
    assert SwingUtilities.isEventDispatchThread();
    down = false;
    hover = List.of();
    pressTarget = null;
    pressedButton = null;
  }

  @Override public void mousePressed(MouseEvent e){
    down = true;
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
    down = (e.getModifiersEx() & (InputEvent.BUTTON1_DOWN_MASK
      | InputEvent.BUTTON2_DOWN_MASK
      | InputEvent.BUTTON3_DOWN_MASK)) != 0;
    var p = point(e);
    // AWT delivers the release to the pressed component even if the cursor
    // left it, so the release is interpreted against the press chain.
    dispatch(pressTarget == null ? deepestAt(p) : pressTarget, Released, p);
    if (!SwingUtilities.isLeftMouseButton(e)){ return; }
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
        if (b.actions.isEmpty()){ continue; }// nothing reaches the programmer: bubble
        for (var a : b.actions){ frame.frame.queue.submit(a); }
        return;
      }
      if (fire(t, Clicked, p)){ return; }
    }
  }

  private void updateHover(Point p){ hoverTo(chainOf(deepestAt(p))); }

  private void hoverTo(List<AWidget> now){
    for (var t : hover){// exits, deepest first
      if (now.contains(t)){ continue; }
      if (t instanceof _Button b){ b.over = false; }
      fire(t, Exited, at);
    }
    for (var t : now.reversed()){// enters, outermost first
      if (hover.contains(t)){ continue; }
      if (t instanceof _Button b){ b.over = true; }
      fire(t, Entered, at);
    }
    hover = now;
  }

  private void dispatch(AWidget start, MouseKind kind, Point p){
    for (var t : chainOf(start)){
      if (fire(t, kind, p)){ return; }
    }
  }

  private boolean fire(AWidget t, MouseKind kind, Point p){
    var hs = t.handlers.get(kind);
    if (hs == null || hs.isEmpty()){ return false; }
    var ctx = ctx(t, p);
    for (var h : hs){
      frame.frame.queue.submit(new MF$1(){
        @Override public Object mut$$hash$0(){
          h.mut$accept$1(ctx);
          return Void$0.instance;
        }
      });
    }
    return true;
  }

  private MouseEvent$0 ctx(AWidget t, Point p){
    var q = SwingUtilities.convertPoint(top(), p, t.component);
    int pw = t.component.getWidth();
    int ph = t.component.getHeight();
    return new CMouseCtx(
      frame.elapsed,
      Scopes.x(clamp(q.x, pw)),
      Scopes.y(clamp(q.y, ph)),
      frame.screenSizeW,
      frame.screenSizeH,
      Scopes.w(pw),
      Scopes.h(ph));
  }

  private static int clamp(int v, int size){
    return size <= 0 ? 0 : Math.max(0, Math.min(v, size - 1));
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