package _base;

import static _base.Scopes.*;
import static java.awt.event.KeyEvent.*;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.TextHitInfo;
import java.awt.im.InputMethodRequests;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.Bidi;
import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.Locale;
import javax.swing.SwingUtilities;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.Rect;

final class _TextField extends AEditText implements TextField$ls$0{
  _TextField(_Frame frame){ super(frame, false); }
}

final class _UTextField extends AEditText implements UTextField$1e8$0{
  _UTextField(_Frame frame){ super(frame, true); }
}

final class _TextArea extends ATextArea implements TextArea$aw$0{
  _TextArea(_Frame frame){ super(frame, false); }
}

final class _UTextArea extends ATextArea implements UTextArea$p4$0{
  _UTextArea(_Frame frame){ super(frame, true); }
}

abstract class ATextArea extends AEditText{
  ATextArea(_Frame frame, boolean u){ super(frame, u); }
  @Override boolean oneLine(){ return false; }
  @Override boolean barRight(){ return true; }
  public Object mut$append$1(Object t){ return append(((Str$c$0Instance) t).val()); }
  public Object mut$uAppend$1(Object t){ return append(ustr(t)); }
  Object append(String s){
    frame.onEdtAndWait(() -> change());
    var ch= clean(buf.length(), buf.length(), s);
    return reStyle(() -> {
      boolean last= follow || scrollY >= maxScrollY();
      program(ch.start(), ch.stop(), ch.text(), caret, anchor);
      follow = last;
    });
  }
  public Object mut$wrap$1(Object b){ return reStyle(() -> wrap = Util.isTrue(b)); }
  public Object read$wrap$0(){ return Util.bool(wrap); }
}

abstract class AEditText extends AWidget implements _StrEditText$2h4$1, _UStrEditText$5mw$1{
  record Step(int at, String removed, String inserted, int caretBefore, int anchorBefore, int caretAfter, int anchorAfter){}
  record Seg(int from, int to, Sk.Line line, float[] edges){}
  record VLine(int start, int end, Sk.Line line, float[] edges, boolean wrapped, int y, int w, int h){
    float dx(){ return line == null ? 0 : (float) ((w - line.width()) / 2); }
  }
  record Fire(Consumer$ao$1 handler, CTextEvent event){}
  record Req(int a, int b, String s){}
  record Change(int start, int stop, String text, int caret){}
  static final Paint stroke= new Paint().setAntiAlias(true).setMode(PaintMode.STROKE);
  static final Paint fill= new Paint().setAntiAlias(false);
  final StringBuilder buf= new StringBuilder();
  int caret;
  int anchor;
  boolean up;
  String committed;
  boolean editable= true;
  boolean clearsUndo= true;
  boolean wrap;
  final ArrayList<Step> undo= new ArrayList<>();
  final ArrayList<Step> redo= new ArrayList<>();
  final boolean u;
  Object cleaner;
  Consumer$ao$1 onEdit;
  Consumer$ao$1 onCommit;
  CaretPainter$4sg$0 caretPainter;
  long movedAt;
  float goalX= -1;
  String composing= "";
  boolean follow;
  boolean reveal;
  boolean dirty= true;
  int laidWidth= -1;
  Sk.TextSettings laidSettings;
  HashMap<String,ArrayList<Seg>> shaped= new HashMap<>();
  ArrayList<VLine> lines= new ArrayList<>();
  int tallest;

  AEditText(_Frame frame, boolean u){
    super(frame);
    this.u= u;
    cleaner = TextCleaning.clean(u, oneLine());
    wrap = !oneLine();
  }
  boolean oneLine(){ return true; }
  @Override boolean barBottom(){ return !wrap; }

  @Override Dimension need(){
    int w= 0;
    int h= 0;
    for (var l : buf.toString().split("\n", -1)){
      var n= Sk.need(Sk.shape(l, textSettings));
      w = Math.max(w, n.width);
      h += Math.max(lineHeight(), n.height);
    }
    return new Dimension(w + 2 + edgeW(), h + edgeH());
  }
  @Override void grow(int w, int h){}
  @Override void fit(){ dirty = true; }

  @Override public Object mut$text$1(Object t){ return text(((Str$c$0Instance) t).val()); }
  @Override public Object mut$uText$1(Object t){ return text(ustr(t)); }
  @Override public Object read$text$0(){ return Str$c$0Instance.instance(buf.toString()); }
  Object text(String s){
    frame.onEdtAndWait(() -> change());
    var ch= clean(0, buf.length(), s);
    return reStyle(() -> {
      program(ch.start(), ch.stop(), ch.text(), ch.text().length(), ch.text().length());
      reveal = true;
    });
  }
  @Override public Object read$uText$0(){ return UStr$s$0Instance.instance(buf.toString()); }
  @Override public Object mut$editable$1(Object b){ return reStyle(() -> editable = Util.isTrue(b)); }
  @Override public Object read$editable$0(){ return Util.bool(editable); }
  @Override public Object mut$textClearsUndo$1(Object b){ return onEdt(() -> clearsUndo = Util.isTrue(b)); }
  @Override public Object read$textClearsUndo$0(){ return Util.bool(clearsUndo); }
  @Override public Object mut$cleaner$1(Object f){ return onEdt(() -> cleaner = f); }
  @Override public Object mut$onEdit$1(Object c){ return onEdt(() -> onEdit = (Consumer$ao$1) c); }
  @Override public Object mut$onCommit$1(Object c){ return onEdt(() -> onCommit = (Consumer$ao$1) c); }
  @Override public Object mut$caret$1(Object p){ return onEdt(() -> caretPainter = (CaretPainter$4sg$0) p); }

  void program(int at, int end, String s, int c, int a){
    var removed= buf.substring(at, end);
    int caretBefore= caret;
    int anchorBefore= anchor;
    buf.replace(at, end, s);
    caret = snap(Math.min(c, buf.length()));
    anchor = snap(Math.min(a, buf.length()));
    var step= new Step(at, removed, s, caretBefore, anchorBefore, caret, anchor);
    moved();
    redo.clear();
    if (clearsUndo){ undo.clear(); }
    else{ undo.add(step); }
    committed = null;
    dirty = true;
  }

  int lineHeight(){ return (int) Math.max(1, textSettings.lineHeight()); }
  @Override boolean textClipped(){
    layout();
    return !buf.isEmpty() && (edgeW() + 1 > component.getWidth() || edgeH() + tallest > component.getHeight());
  }
  @Override String textClippedWarning(){
    return "the text of " + describe() + " needs at least " + (edgeW() + 1) + "x" + (edgeH() + tallest) + " insets and scroll bars included, but the widget is " + component.getWidth() + "x" + component.getHeight() + ": the text is clipped";
  }
  int selA(){ return Math.min(caret, anchor); }
  int selB(){ return Math.max(caret, anchor); }

  void layout(){
    int ww= wrap ? Math.max(1, viewW()) : Integer.MAX_VALUE;
    if (ww != laidWidth || !textSettings.equals(laidSettings)){
      shaped = new HashMap<>();
      laidWidth = ww;
      laidSettings = textSettings;
      dirty = true;
    }
    if (!dirty){ return; }
    dirty = false;
    var old= shaped;
    shaped = new HashMap<>();
    lines = new ArrayList<>();
    int w= 0;
    int y= 0;
    tallest = 0;
    for (int s= 0;;){
      int e= buf.indexOf("\n", s);
      int end= e < 0 ? buf.length() : e;
      var segs= segsOf(buf.substring(s, end), old, ww);
      for (int i= 0; i < segs.size(); i += 1){
        var g= segs.get(i);
        var n= g.line() == null ? new Dimension() : Sk.need(g.line());
        int h= Math.max(lineHeight(), n.height);
        lines.add(new VLine(s + g.from(), s + g.to(), g.line(), g.edges(), i < segs.size() - 1, y, n.width, h));
        w = Math.max(w, n.width);
        tallest = Math.max(tallest, h);
        y += h;
      }
      if (e < 0){ break; }
      s = e + 1;
    }
    contentW = w + 2;
    contentH = y;
    scrollX = Math.clamp(scrollX, 0, maxScrollX());
    scrollY = Math.clamp(scrollY, 0, maxScrollY());
    if (follow){ follow = false; scrollY = maxScrollY(); }
    if (reveal){ reveal = false; showCaret(); }
  }
  private ArrayList<Seg> segsOf(String text, HashMap<String,ArrayList<Seg>> old, int ww){
    var res= shaped.get(text);
    if (res != null){ return res; }
    res = old.remove(text);
    if (res == null){ res = segs(text, ww); }
    shaped.put(text, res);
    return res;
  }
  private ArrayList<Seg> segs(String s, int ww){
    var res= new ArrayList<Seg>();
    if (s.isEmpty()){ res.add(new Seg(0, 0, null, null)); return res; }
    var bidi= new Bidi(s, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
    int from= 0;
    for (int to : ww == Integer.MAX_VALUE ? List.of(s.length()) : Sk.wrap(s, textSettings, ww)){
      var line= Sk.shape(s.substring(from, to), textSettings, bidi.createLineBidi(from, to));
      res.add(new Seg(from, to, line, line.edges()));
      from = to;
    }
    return res;
  }

  int find(ToIntFunction<VLine> key, int at){
    int lo= 0;
    int hi= lines.size() - 1;
    while (lo < hi){
      int mid= (lo + hi + 1) >>> 1;
      if (key.applyAsInt(lines.get(mid)) <= at){ lo = mid; }
      else{ hi = mid - 1; }
    }
    return lo;
  }
  int lineOf(int off){ return find(VLine::start, off); }
  int lineAt(int y){ return find(VLine::y, y); }
  int caretLine(){
    int i= lineOf(caret);
    return up && i > 0 && lines.get(i - 1).end() == caret ? i - 1 : i;
  }
  int endOf(VLine v){ return v.wrapped() ? Sk.trimmed(buf, v.start(), v.end()) : v.end(); }
  static float xOf(VLine v, int off){
    if (v.line() == null){ return 0; }
    int i= 2 * (off - v.start());
    float x= i == v.edges().length ? (float) (v.line().rtl() ? 0 : v.line().width()) : v.edges()[i];
    assert !Float.isNaN(x);
    return v.dx() + x;
  }
  static int offsetAt(VLine v, float x){
    var e= v.edges();
    int res= 0;
    float d= Float.POSITIVE_INFINITY;
    for (int p= 0, q; p < e.length / 2; p = q){
      q = p + 1;
      while (2 * q < e.length && Float.isNaN(e[2 * q])){ q += 1; }
      if (Math.abs(e[2 * p] - x) < d){ d = Math.abs(e[2 * p] - x); res = p; }
      if (Math.abs(e[2 * p + 1] - x) < d){ d = Math.abs(e[2 * p + 1] - x); res = q; }
    }
    return v.start() + res;
  }
  boolean pointTo(int x, int y, boolean extend){
    var v= lines.get(lineAt(y));
    int off= v.line() == null ? v.start() : Math.min(offsetAt(v, x - v.dx()), endOf(v));
    return moveTo(off, extend, off == v.end());
  }
  int lineStart(int i){ return buf.lastIndexOf("\n", i - 1) + 1; }
  int lineEnd(int i){
    int e= buf.indexOf("\n", i);
    return e < 0 ? buf.length() : e;
  }
  private BreakIterator clusters(int s, int e){
    var b= BreakIterator.getCharacterInstance(Locale.ROOT);
    b.setText(buf.substring(s, e));
    return b;
  }
  int next(int i){
    if (i == buf.length()){ return i; }
    if (buf.charAt(i) == '\n'){ return i + 1; }
    int s= lineStart(i);
    return s + clusters(s, lineEnd(i)).following(i - s);
  }
  int prev(int i){
    if (i == 0){ return 0; }
    if (buf.charAt(i - 1) == '\n'){ return i - 1; }
    int s= lineStart(i);
    return s + clusters(s, lineEnd(i)).preceding(i - s);
  }
  int snap(int i){
    int s= lineStart(i);
    int e= lineEnd(i);
    if (i == s || i == e){ return i; }
    var b= clusters(s, e);
    return b.isBoundary(i - s) ? i : s + b.preceding(i - s);
  }

  void showCaret(){
    var v= lines.get(caretLine());
    int x= (int) xOf(v, caret);
    int nx= Math.max(Math.min(scrollX, x), x + 2 - viewW());
    int ny= Math.max(Math.min(scrollY, v.y()), v.y() + v.h() - viewH());
    scrollTo(nx, ny);
  }
  void moved(){ movedAt = micros(frame.elapsed); }
  static long micros(Instant$5c$0 i){ return Util.natToLong(i.read$get$0()); }
  boolean moveTo(int c, boolean extend){ return moveTo(c, extend, false); }
  boolean moveTo(int c, boolean extend, boolean up){
    caret = c;
    this.up = up;
    if (!extend){ anchor = c; }
    goalX = -1;
    moved();
    showCaret();
    return false;
  }
  boolean horizontal(boolean right, boolean extend){
    int c= right ? selB() : selA();
    if (!extend && caret != anchor){ return moveTo(c, false, up && c == caret); }
    return moveTo(right ? next(caret) : prev(caret), extend);
  }
  boolean vertical(int dy, boolean extend){
    var v= lines.get(caretLine());
    float g= goalX < 0 ? xOf(v, caret) : goalX;
    int by= Math.max(v.h(), Math.abs(dy));
    int y= dy > 0 ? v.y() + by : v.y() + v.h() - 1 - by;
    if (y < 0 || y >= contentH){ moveTo(y < 0 ? 0 : buf.length(), extend); }
    else{ pointTo((int) g, y, extend); }
    goalX = g;
    return false;
  }
  int page(){ return Math.max(1, viewH()); }
  boolean home(boolean ctrl, boolean extend){ return moveTo(ctrl ? 0 : lines.get(caretLine()).start(), extend); }
  boolean end(boolean ctrl, boolean extend){ return ctrl ? moveTo(buf.length(), extend) : moveTo(endOf(lines.get(caretLine())), extend, true); }
  boolean selectAll(){
    anchor = 0;
    return moveTo(buf.length(), true);
  }

  Change clean(int a, int b, String s){
    int start= lineStart(a);
    var r= new TextCleaning.Run(u, cleaner, buf.chars().limit(start).filter(c -> c == '\n').count());
    TextCleaning.feed(r, buf.subSequence(start, a));
    TextCleaning.feed(r, s);
    int stop= b;
    do{
      int to= Math.min(lineEnd(stop) + 1, buf.length());
      TextCleaning.feed(r, buf.subSequence(stop, to));
      stop = to;
    } while (stop < buf.length() && !r.fresh());
    r.finish();
    var res= r.chars.toString();
    var nl= newline(res);
    if (oneLine() && nl != null){ throw Util.detErr("The run of the cleaner maker of this " + kind() + " gave a text with " + nl + ": the text of a " + kind() + " is one line; " + (u ? "UStrCleaners.line" : "StrCleaners.softLine") + " turns each newline into a space"); }
    int keep= Math.max(0, res.codePointCount(0, res.length()) - buf.codePointCount(b, stop));
    return new Change(start, stop, res, start + res.offsetByCodePoints(0, keep));
  }
  boolean edit(Change ch){
    var removed= buf.substring(ch.start(), ch.stop());
    if (removed.equals(ch.text())){ return moveTo(snap(ch.caret()), false); }
    int caretBefore= caret;
    int anchorBefore= anchor;
    apply(ch.start(), ch.stop(), ch.text(), ch.caret(), ch.caret());
    undo.add(new Step(ch.start(), removed, ch.text(), caretBefore, anchorBefore, caret, anchor));
    redo.clear();
    return true;
  }
  void apply(int a, int b, String s, int c, int an){
    if (committed == null){ committed = buf.toString(); }
    buf.replace(a, b, s);
    caret = snap(c);
    anchor = snap(an);
    dirty = true;
    layout();
    moveTo(caret, true);
  }
  boolean undo(){
    if (!editable || undo.isEmpty()){ return false; }
    var st= undo.removeLast();
    apply(st.at(), st.at() + st.inserted().length(), st.removed(), st.caretBefore(), st.anchorBefore());
    redo.add(st);
    return true;
  }
  boolean redo(){
    if (!editable || redo.isEmpty()){ return false; }
    var st= redo.removeLast();
    apply(st.at(), st.at() + st.removed().length(), st.inserted(), st.caretAfter(), st.anchorAfter());
    undo.add(st);
    return true;
  }
  Req backspace(){
    if (caret != anchor){ return new Req(selA(), selB(), ""); }
    return caret == 0 ? null : new Req(prev(caret), caret, "");
  }
  Req delete(){
    if (caret != anchor){ return new Req(selA(), selB(), ""); }
    return caret == buf.length() ? null : new Req(caret, next(caret), "");
  }
  boolean copy(){
    if (caret == anchor){ return false; }
    try{ Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(buf.substring(selA(), selB())), null); }
    catch (IllegalStateException _){}
    return false;
  }
  Req cut(){
    if (caret == anchor){ return null; }
    copy();
    return new Req(selA(), selB(), "");
  }

  CTextEvent event(){
    return new CTextEvent(frame.elapsed, w(component.getWidth()), h(component.getHeight()),
      buf.toString(), n(buf.codePointCount(0, caret)), n(buf.codePointCount(0, anchor)));
  }
  Fire fired(boolean changed){ return changed && onEdit != null ? new Fire(onEdit, event()) : null; }

  private void submit(Supplier<Fire> onEdt){
    frame.frame.queue.submit(new MF$7$1(){ @Override public Object mut$$hash$0(){
      if (!frame.onEdtAndWait(() -> canChange())){ return Void$o$0.instance; }
      fire(frame.changeOnEdt(() -> { layout(); return onEdt.get(); }));
      return Void$o$0.instance;
    }});
  }
  private void change(Supplier<Req> onEdt){
    frame.frame.queue.submit(new MF$7$1(){ @Override public Object mut$$hash$0(){
      var q= frame.onEdtAndWait(() -> canChange() && editable ? onEdt.get() : null);
      if (q == null){ return Void$o$0.instance; }
      var ch= clean(q.a(), q.b(), q.s());
      fire(frame.changeOnEdt(() -> { layout(); return fired(edit(ch)); }));
      return Void$o$0.instance;
    }});
  }
  static void fire(Fire f){ if (f != null){ f.handler().mut$accept$1(f.event()); } }
  private void act(BooleanSupplier onEdt){ submit(() -> fired(onEdt.getAsBoolean())); }
  void input(String s){ change(() -> new Req(selA(), selB(), s)); }
  void press(Point p, boolean extend){ act(() -> pointTo(p.x, p.y, extend)); }
  void drag(Point p){ act(() -> pointTo(p.x, p.y, true)); }
  void commit(){
    submit(() -> {
      if (committed == null || committed.contentEquals(buf)){ return null; }
      committed = null;
      return onCommit == null ? null : new Fire(onCommit, event());
    });
  }

  boolean key(KeyEvent e){
    boolean shift= e.isShiftDown();
    boolean ctrl= e.isControlDown();
    int k= e.getKeyCode();
    switch (k){
      case VK_LEFT, VK_KP_LEFT -> act(() -> horizontal(false, shift));
      case VK_RIGHT, VK_KP_RIGHT -> act(() -> horizontal(true, shift));
      case VK_UP, VK_KP_UP -> { return acrossLines(() -> vertical(-1, shift)); }
      case VK_DOWN, VK_KP_DOWN -> { return acrossLines(() -> vertical(1, shift)); }
      case VK_PAGE_UP -> { return acrossLines(() -> vertical(-page(), shift)); }
      case VK_PAGE_DOWN -> { return acrossLines(() -> vertical(page(), shift)); }
      case VK_HOME -> act(() -> home(ctrl, shift));
      case VK_END -> act(() -> end(ctrl, shift));
      case VK_BACK_SPACE -> change(this::backspace);
      case VK_DELETE -> change(this::delete);
      case VK_TAB -> frame.tab(shift);
      case VK_ENTER -> enter(shift, ctrl);
      case VK_A -> { return command(e, () -> act(this::selectAll)); }
      case VK_C -> { return command(e, () -> act(this::copy)); }
      case VK_X -> { return command(e, () -> change(this::cut)); }
      case VK_V -> { return command(e, () -> change(this::paste)); }
      case VK_Z -> { return command(e, () -> act(shift ? this::redo : this::undo)); }
      case VK_Y -> { return command(e, () -> act(this::redo)); }
      default -> { return k >= VK_DEAD_GRAVE && k <= VK_DEAD_SEMIVOICED_SOUND || CTextKeys.types(e); }
    }
    return true;
  }
  private boolean acrossLines(BooleanSupplier onEdt){
    if (oneLine()){ return false; }
    act(onEdt);
    return true;
  }
  private static boolean command(KeyEvent e, Runnable r){
    if (!e.isControlDown() || e.isAltDown()){ return CTextKeys.types(e); }
    r.run();
    return true;
  }
  private void enter(boolean shift, boolean ctrl){
    if (ctrl || !shift){ commit(); return; }
    if (!oneLine()){ change(() -> new Req(selA(), selB(), "\n")); }
  }
  private Req paste(){
    var c= Toolkit.getDefaultToolkit().getSystemClipboard();
    try{ return c.isDataFlavorAvailable(DataFlavor.stringFlavor) ? new Req(selA(), selB(), (String) c.getData(DataFlavor.stringFlavor)) : null; }
    catch (IllegalStateException | UnsupportedFlavorException | IOException _){ return null; }
  }

  Rectangle caretOnScreen(){
    layout();
    var v= lines.get(caretLine());
    var p= SwingUtilities.convertPoint(component, (int) (left - scrollX + xOf(v, caret)), top - scrollY + v.y(), frame.top.component);
    double s= frame.stage.scale();
    var o= frame.stage.offset(s);
    var on= frame.surface.getLocationOnScreen();
    return new Rectangle(on.x + o.x + (int) (p.x * s), on.y + o.y + (int) (p.y * s), 1, (int) Math.ceil(v.h() * s));
  }

  @Override void sk(Canvas cv){
    layout();
    int w= component.getWidth();
    int h= component.getHeight();
    boolean focused= frame.focus == this;
    float r= Math.min(radius, Math.min(w, h) / 2f);
    Sk.paint.setColor(bg >>> 24 == 0 ? 0xFFFFFFFF : bg);
    Sk.rrect(cv, 0, 0, w, h, r, Sk.paint);
    float sw= focused ? 2 : 1;
    stroke.setStrokeWidth(sw).setColor(focused ? 0xFF4878D2 : 0xFF8C8C8C);
    Sk.rrect(cv, sw / 2, sw / 2, w - sw, h - sw, Math.max(0, r - sw / 2), stroke);
    int save= cv.save();
    Sk.clip(cv, left, top, viewW(), viewH());
    Sk.at(cv, left - scrollX, top - scrollY, 1);
    int last= lineAt(scrollY + viewH());
    for (int i= lineAt(scrollY); i <= last; i += 1){ line(cv, lines.get(i), focused); }
    if (focused && frame.keyboard){
      var v= lines.get(caretLine());
      Sk.at(cv, xOf(v, caret), v.y(), 1);
      caret(cv, v.h());
    }
    cv.restoreToCount(save);
  }
  private void line(Canvas cv, VLine v, boolean focused){
    fill.setColor(focused ? 0xFFB4CDF5 : 0xFFDCDCDC);
    int b= Math.min(selB(), v.end());
    float lo= 0;
    float hi= 0;
    for (int p= Math.max(selA(), v.start()); p < b; p += 1){
      int i= 2 * (p - v.start());
      if (Float.isNaN(v.edges()[i])){ continue; }
      float x1= v.dx() + Math.min(v.edges()[i], v.edges()[i + 1]);
      float x2= v.dx() + Math.max(v.edges()[i], v.edges()[i + 1]);
      if (x1 > hi || x2 < lo){ highlight(cv, v, lo, hi); lo = x1; hi = x2; continue; }
      lo = Math.min(lo, x1);
      hi = Math.max(hi, x2);
    }
    highlight(cv, v, lo, hi);
    boolean newline= !v.wrapped() && selA() <= v.end() && selB() > v.end();
    if (newline){
      float x= xOf(v, v.end());
      float d= v.line() != null && v.line().rtl() ? -textSettings.size() / 3f : textSettings.size() / 3f;
      highlight(cv, v, Math.min(x, x + d), Math.max(x, x + d));
    }
    if (v.line() == null){ return; }
    Sk.paint.setColor(fg);
    Sk.drawAt(cv, v.line(), (v.w() - v.line().width()) / 2, v.y() + v.line().settings().baseline(v.h()), Sk.paint, scrollX, scrollX + viewW());
  }
  private static void highlight(Canvas cv, VLine v, float lo, float hi){ if (lo < hi){ Sk.rect(cv, lo, v.y(), hi - lo, v.h(), fill); } }
  private void caret(Canvas cv, int lh){
    if (!composing.isEmpty()){
      var t= Sk.shape(composing, textSettings);
      float tw= (float) t.width();
      Sk.paint.setColor(0xFFFFFFF0);
      cv.drawRect(Rect.makeXYWH(0, 0, tw, lh), Sk.paint);
      Sk.paint.setColor(fg);
      float base= (float) textSettings.baseline(lh);
      Sk.drawAt(cv, t, 0, base, Sk.paint);
      cv.drawRect(Rect.makeXYWH(0, base + 2, tw, 1), Sk.paint);
      cv.translate(tw, 0);
    }
    long since= Math.max(0, micros(frame.elapsed) - movedAt);
    if (caretPainter == null){
      if (since / 500_000 % 2 != 0){ return; }
      Sk.paint.setColor(fg);
      cv.drawRect(Rect.makeXYWH(0, 0, 2, lh), Sk.paint);
      return;
    }
    try (var p= new Paint().setAntiAlias(true)){
      caretPainter.imm$run$1(new CCaretCtx(cv, frame.elapsed, w(component.getWidth()), h(component.getHeight()), p, foreground,
        (Duration$ao$0) Duration$ao$0.instance.read$$hash$1(n(since)), h(lh)));
    }
  }
}

final class CCaretCtx extends CGraphicsCtx implements CaretGraphics$9kw$0{
  final Duration$ao$0 sinceMove;
  final HeightNat$lg$0 lineHeight;
  CCaretCtx(Canvas cv, Instant$5c$0 elapsed, WidthNat$as$0 panelWidth, HeightNat$lg$0 panelHeight, Paint paint, Color$1c$0 color, Duration$ao$0 sinceMove, HeightNat$lg$0 lineHeight){
    super(cv, elapsed, panelWidth, panelHeight, paint, color);
    this.sinceMove= sinceMove;
    this.lineHeight= lineHeight;
  }
  @Override public Object read$sinceMove$0(){ return sinceMove; }
  @Override public Object read$lineHeight$0(){ return lineHeight; }
}

record CTextEvent(Instant$5c$0 elapsed, WidthNat$as$0 panelWidth, HeightNat$lg$0 panelHeight, String text, Object caret, Object anchor) implements TextEvent$ls$0, UTextEvent$1e8$0{
  @Override public Object read$elapsed$0(){ return elapsed; }
  @Override public Object read$panelWidth$0(){ return panelWidth; }
  @Override public Object read$panelHeight$0(){ return panelHeight; }
  @Override public Object read$text$0(){ return Str$c$0Instance.instance(text); }
  @Override public Object read$uText$0(){ return UStr$s$0Instance.instance(text); }
  @Override public Object read$caret$0(){ return caret; }
  @Override public Object read$anchor$0(){ return anchor; }
}

final class CTextKeys extends KeyAdapter implements FocusListener, InputMethodListener, InputMethodRequests{
  enum To{ menus, widget, frame, desk, nobody }
  final _Frame frame;
  final HashMap<List<Integer>,To> routes= new HashMap<>();
  To typing= To.nobody;
  char high;
  CTextKeys(_Frame frame){ this.frame= frame; }

  static boolean typesWith(java.awt.event.InputEvent e){ return e.isAltDown() == e.isControlDown() || e.isAltGraphDown(); }
  static boolean types(KeyEvent e){
    char c= e.getKeyChar();
    return c != CHAR_UNDEFINED && !Character.isISOControl(c) && typesWith(e);
  }
  @Override public void keyPressed(KeyEvent e){
    var id= List.of(e.getKeyCode(), e.getKeyLocation());
    var to= routes.get(id);
    boolean first= to == null;
    if (first){
      var f= frame.focus;
      to = first(e);
      routes.put(id, f == frame.focus ? to : To.nobody);
    }
    else if (to == To.menus && frame.menus.isOpen() && e.getKeyCode() != _FluentGUI.code(frame.gui.closeKey)){ frame.menus.key(e); }
    else if (to == To.widget){ frame.focus.key(e); }
    typing = routes.get(id);
    if (to != To.frame && !(first && to == To.desk)){ e.consume(); }
  }
  private To first(KeyEvent e){
    if (frame.menus.isOpen() && e.getKeyCode() != _FluentGUI.code(frame.gui.fullScreenKey)){
      frame.menus.key(e);
      return To.menus;
    }
    if (frame.reserved(e.getKeyCode())){ return To.desk; }
    if (frame.focus != null && frame.focus.key(e)){ return To.widget; }
    return To.frame;
  }
  void focusMoved(){
    typing = To.nobody;
    routes.replaceAll((_, _) -> To.nobody);
  }
  @Override public void keyTyped(KeyEvent e){
    var t= frame.focus;
    char c= e.getKeyChar();
    boolean typesHere= typing == To.widget || typing == To.frame;
    if (t == null || !typesHere || Character.isISOControl(c) || !typesWith(e)){ return; }
    e.consume();
    if (Character.isHighSurrogate(c)){ high = c; return; }
    var s= high == 0 ? "" + c : "" + high + c;
    high = 0;
    t.input(s);
  }
  @Override public void focusGained(FocusEvent e){ frame.keyboard = true; }
  @Override public void keyReleased(KeyEvent e){
    var to= routes.remove(List.of(e.getKeyCode(), e.getKeyLocation()));
    if (to != null && to != To.frame){ e.consume(); }
  }
  @Override public void focusLost(FocusEvent e){
    frame.keyboard = false;
    typing = To.nobody;
    routes.clear();
  }

  @Override public void inputMethodTextChanged(InputMethodEvent e){
    var done= new StringBuilder();
    var composing= new StringBuilder();
    var it= e.getText();
    if (it != null){
      int i= 0;
      for (char c= it.first(); c != CharacterIterator.DONE; c = it.next(), i += 1){ (i < e.getCommittedCharacterCount() ? done : composing).append(c); }
    }
    e.consume();
    var t= frame.focus;
    if (t == null || frame.menus.isOpen()){ return; }
    t.composing = composing.toString();
    if (!done.isEmpty()){ t.input(done.toString()); }
  }
  @Override public void caretPositionChanged(InputMethodEvent e){ e.consume(); }

  @Override public Rectangle getTextLocation(TextHitInfo offset){
    if (frame.focus == null){ return new Rectangle(frame.surface.getLocationOnScreen(), new Dimension()); }
    return frame.focus.caretOnScreen();
  }
  @Override public TextHitInfo getLocationOffset(int x, int y){ return null; }
  @Override public int getInsertPositionOffset(){ return 0; }
  @Override public AttributedCharacterIterator getCommittedText(int beginIndex, int endIndex, AttributedCharacterIterator.Attribute[] attributes){ return new AttributedString("").getIterator(); }
  @Override public int getCommittedTextLength(){ return 0; }
  @Override public AttributedCharacterIterator cancelLatestCommittedText(AttributedCharacterIterator.Attribute[] attributes){ return null; }
  @Override public AttributedCharacterIterator getSelectedText(AttributedCharacterIterator.Attribute[] attributes){ return new AttributedString("").getIterator(); }
}
