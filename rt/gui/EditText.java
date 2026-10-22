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
import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.swing.SwingUtilities;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.TextLine;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;

final class _TextField extends AEditText implements TextField$ls$0{
  _TextField(_Frame frame){ super(frame); }
  @Override boolean oneLine(){ return true; }
  @Override boolean barBottom(){ return true; }
}

final class _TextArea extends AEditText implements TextArea$aw$0{
  _TextArea(_Frame frame){
    super(frame);
    wrap = true;
  }
  @Override boolean oneLine(){ return false; }
  @Override boolean barRight(){ return true; }
  @Override boolean barBottom(){ return !wrap; }
  @Override public Object mut$uAppend$1(Object t){
    var s= ustr(t);
    return reStyle(() -> {
      boolean last= follow || scrollY >= maxScrollY();
      int n= buf.length();
      program(n, n, s, caret, anchor);
      caret = snap(caret);
      anchor = snap(anchor);
      follow = last;
    });
  }
  @Override public Object mut$wrap$1(Object b){ return reStyle(() -> wrap = Util.isTrue(b)); }
  @Override public Object read$wrap$0(){ return Util.bool(wrap); }
}

abstract class AEditText extends AWidget implements _EditText$aw$1{
  record Step(int at, String removed, String inserted, int caretBefore, int anchorBefore, int caretAfter, int anchorAfter){}
  record Seg(int from, int to, TextLine line){}
  record VLine(int start, int end, TextLine line, boolean wrapped){}
  record Fire(Consumer$ao$1 handler, TextEvent$ls$0 event){}
  static final Paint stroke= new Paint().setAntiAlias(true).setMode(PaintMode.STROKE);
  final StringBuilder buf= new StringBuilder();
  int caret;
  int anchor;
  String committed= "";
  boolean committedLive= true;
  boolean editable= true;
  boolean clearsUndo= true;
  boolean wrap;
  final ArrayList<Step> undo= new ArrayList<>();
  final ArrayList<Step> redo= new ArrayList<>();
  MF$7$2 onInput;
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
  int laidSize= -1;
  HashMap<String,ArrayList<Seg>> shaped= new HashMap<>();
  ArrayList<VLine> lines= new ArrayList<>();

  AEditText(_Frame frame){ super(frame); }
  abstract boolean oneLine();

  @Override Dimension need(){
    int w= 0;
    var ls= buf.toString().split("\n", -1);
    for (var l : ls){
      if (l.isEmpty()){ continue; }
      try (var t= Sk.shape(l, textSize)){ w = Math.max(w, (int) Math.ceil(t.getWidth())); }
    }
    return new Dimension(w + 2 + edgeW(), ls.length * lineHeight() + edgeH());
  }
  @Override void grow(int w, int h){}
  @Override void fit(){ dirty = true; }
  @Override Object reText(Runnable r){ return reStyle(r); }

  @Override public Object mut$uText$1(Object t){
    var s= ustr(t);
    int nl= s.indexOf('\n');
    if (oneLine() && nl >= 0){ throw Util.detErr("The new text of this TextField has a newline at offset " + s.codePointCount(0, nl) + ": the text of a TextField is one line; a TextArea holds more lines"); }
    return reStyle(() -> {
      program(0, buf.length(), s, s.length(), s.length());
      reveal = true;
    });
  }
  @Override public Object read$uText$0(){ return UStr$s$0Instance.instance(buf.toString()); }
  @Override public Object mut$editable$1(Object b){ return reStyle(() -> editable = Util.isTrue(b)); }
  @Override public Object read$editable$0(){ return Util.bool(editable); }
  @Override public Object mut$textClearsUndo$1(Object b){ return onEdt(() -> clearsUndo = Util.isTrue(b)); }
  @Override public Object read$textClearsUndo$0(){ return Util.bool(clearsUndo); }
  @Override public Object mut$onInput$1(Object f){ return onEdt(() -> onInput = (MF$7$2) f); }
  @Override public Object mut$onEdit$1(Object c){ return onEdt(() -> onEdit = (Consumer$ao$1) c); }
  @Override public Object mut$onCommit$1(Object c){ return onEdt(() -> onCommit = (Consumer$ao$1) c); }
  @Override public Object mut$caret$1(Object p){ return onEdt(() -> caretPainter = (CaretPainter$4sg$0) p); }

  void program(int at, int end, String s, int c, int a){
    var step= new Step(at, buf.substring(at, end), s, caret, anchor, c, a);
    buf.replace(at, end, s);
    caret = c;
    anchor = a;
    redo.clear();
    if (clearsUndo){ undo.clear(); }
    else{ undo.add(step); }
    committedLive = true;
    dirty = true;
  }

  int lineHeight(){ return (int) Math.max(1, Sk.lineHeight(this)); }
  int selA(){ return Math.min(caret, anchor); }
  int selB(){ return Math.max(caret, anchor); }

  void layout(){
    int ww= wrap ? Math.max(1, viewW()) : Integer.MAX_VALUE;
    if (ww != laidWidth || textSize != laidSize){
      close(shaped);
      shaped = new HashMap<>();
      laidWidth = ww;
      laidSize = textSize;
      dirty = true;
    }
    if (!dirty){ return; }
    dirty = false;
    var old= shaped;
    shaped = new HashMap<>();
    lines = new ArrayList<>();
    float w= 0;
    for (int s= 0;;){
      int e= buf.indexOf("\n", s);
      int end= e < 0 ? buf.length() : e;
      var segs= segsOf(buf.substring(s, end), old, ww);
      for (int i= 0; i < segs.size(); i += 1){
        var g= segs.get(i);
        lines.add(new VLine(s + g.from(), s + g.to(), g.line(), i < segs.size() - 1));
        w = Math.max(w, width(g.line()));
      }
      if (e < 0){ break; }
      s = e + 1;
    }
    close(old);
    contentW = (int) Math.ceil(w) + 2;
    contentH = lines.size() * lineHeight();
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
  private static void close(HashMap<String,ArrayList<Seg>> m){
    for (var segs : m.values()){
      for (var g : segs){ if (g.line() != null){ g.line().close(); } }
    }
  }
  static float width(TextLine t){ return t == null ? 0 : t.getWidth(); }
  private ArrayList<Seg> segs(String s, int ww){
    var res= new ArrayList<Seg>();
    if (s.isEmpty()){ res.add(new Seg(0, 0, null)); return res; }
    var whole= Sk.shape(s, textSize);
    if (whole.getWidth() <= ww){ res.add(new Seg(0, s.length(), whole)); return res; }
    var words= BreakIterator.getLineInstance();
    words.setText(s);
    var clusters= BreakIterator.getCharacterInstance();
    clusters.setText(s);
    for (int from= 0; from < s.length();){
      int to= fit(s, whole, words, from, ww);
      if (to == from){ to = fit(s, whole, clusters, from, ww); }
      if (to == from){ to = clusters.following(from); }
      res.add(new Seg(from, to, Sk.shape(s.substring(from, to), textSize)));
      from = to;
    }
    whole.close();
    return res;
  }
  private static int fit(String s, TextLine whole, BreakIterator b, int from, int ww){
    float x0= whole.getCoordAtOffset(from);
    int res= from;
    for (int at= b.following(from); at != BreakIterator.DONE; at = b.next()){
      int ink= at;
      while (ink > from && s.charAt(ink - 1) == ' '){ ink -= 1; }
      if (whole.getCoordAtOffset(ink) - x0 > ww){ return res; }
      res = at;
    }
    return res;
  }

  int lineOf(int off){
    int lo= 0;
    int hi= lines.size() - 1;
    while (lo < hi){
      int mid= (lo + hi + 1) >>> 1;
      if (lines.get(mid).start() <= off){ lo = mid; }
      else{ hi = mid - 1; }
    }
    return lo;
  }
  static float xOf(VLine v, int off){ return v.line() == null ? 0 : v.line().getCoordAtOffset(off - v.start()); }
  int offsetAt(int x, int y){
    var v= lines.get(Math.clamp(Math.floorDiv(y, lineHeight()), 0, lines.size() - 1));
    int off= v.line() == null ? v.start() : Math.clamp(v.start() + v.line().getOffsetAtCoord(x), v.start(), v.end());
    if (v.wrapped() && off == v.end()){ off = prev(off); }
    return snap(off);
  }
  int lineStart(int i){ return buf.lastIndexOf("\n", i - 1) + 1; }
  int lineEnd(int i){
    int e= buf.indexOf("\n", i);
    return e < 0 ? buf.length() : e;
  }
  private BreakIterator clusters(int s, int e){
    var b= BreakIterator.getCharacterInstance();
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
    int lh= lineHeight();
    int k= lineOf(caret);
    int x= (int) xOf(lines.get(k), caret);
    int y= k * lh;
    int nx= Math.max(Math.min(scrollX, x), x + 2 - viewW());
    int ny= Math.max(Math.min(scrollY, y), y + lh - viewH());
    scrollTo(nx, ny);
  }
  void moved(){ movedAt = micros(frame.elapsed); }
  static long micros(Instant$5c$0 i){ return Util.natToLong(i.read$get$0()); }
  boolean moveTo(int c, boolean extend){
    caret = c;
    if (!extend){ anchor = c; }
    goalX = -1;
    moved();
    showCaret();
    return false;
  }
  boolean horizontal(boolean right, boolean extend){
    if (!extend && caret != anchor){ return moveTo(right ? selB() : selA(), false); }
    return moveTo(right ? next(caret) : prev(caret), extend);
  }
  boolean vertical(int by, boolean extend){
    int k= lineOf(caret);
    float g= goalX < 0 ? xOf(lines.get(k), caret) : goalX;
    int t= k + by;
    int c= t < 0 ? 0 : t >= lines.size() ? buf.length() : offsetAt((int) g, t * lineHeight());
    moveTo(c, extend);
    goalX = g;
    return false;
  }
  int page(){ return Math.max(1, viewH() / lineHeight()); }
  boolean home(boolean ctrl, boolean extend){ return moveTo(ctrl ? 0 : lines.get(lineOf(caret)).start(), extend); }
  boolean end(boolean ctrl, boolean extend){
    var v= lines.get(lineOf(caret));
    return moveTo(ctrl ? buf.length() : v.wrapped() ? prev(v.end()) : v.end(), extend);
  }
  boolean selectAll(){
    caret = buf.length();
    anchor = 0;
    goalX = -1;
    moved();
    showCaret();
    return false;
  }

  boolean edit(int a, int b, String s){
    if (!editable || a == b && s.isEmpty()){ return false; }
    int c= a + s.length();
    var step= new Step(a, buf.substring(a, b), s, caret, anchor, c, c);
    undo.add(step);
    redo.clear();
    apply(a, b, s, c, c);
    return true;
  }
  void apply(int a, int b, String s, int c, int an){
    if (committedLive){ committed = buf.toString(); committedLive = false; }
    buf.replace(a, b, s);
    caret = c;
    anchor = an;
    dirty = true;
    layout();
    moveTo(c, true);
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
  boolean backspace(){
    if (caret != anchor){ return edit(selA(), selB(), ""); }
    return edit(prev(caret), caret, "");
  }
  boolean delete(){
    if (caret != anchor){ return edit(selA(), selB(), ""); }
    return edit(caret, next(caret), "");
  }
  boolean copy(){
    if (caret == anchor){ return false; }
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(buf.substring(selA(), selB())), null);
    return false;
  }
  boolean cut(){
    if (!editable || caret == anchor){ return false; }
    copy();
    return edit(selA(), selB(), "");
  }

  TextEvent$ls$0 event(){
    return new CTextEvent(frame.elapsed, w(component.getWidth()), h(component.getHeight()),
      UStr$s$0Instance.instance(buf.toString()), n(buf.codePointCount(0, caret)), n(buf.codePointCount(0, anchor)));
  }
  Fire fired(boolean changed){ return changed && onEdit != null ? new Fire(onEdit, event()) : null; }

  private void submit(String incoming, Function<String,Fire> onEdt){
    frame.frame.queue.submit(new MF$7$1(){ @Override public Object mut$$hash$0(){
      frame.frame.queue.midTask().set(true);
      boolean live=frame.onEdtAndWait(() -> canChange() && (incoming == null || editable));
      if (!live){ return Void$o$0.instance; }
      var in= incoming == null ? null : filtered(incoming);
      var f= frame.onEdtAndWait(() -> { layout(); return onEdt.apply(in); });
      if (f != null){ f.handler().mut$accept$1(f.event()); }
      return Void$o$0.instance;
    }});
  }
  private void act(BooleanSupplier onEdt){ submit(null, _ -> fired(onEdt.getAsBoolean())); }
  void input(String s){ submit(s, in -> fired(!in.isEmpty() && edit(selA(), selB(), in))); }
  void press(Point p, boolean extend){ act(() -> moveTo(offsetAt(p.x, p.y), extend)); }
  void drag(Point p){ act(() -> moveTo(offsetAt(p.x, p.y), true)); }
  void commit(){
    submit(null, _ -> {
      if (committedLive || committed.contentEquals(buf)){ return null; }
      committedLive = true;
      return onCommit == null ? null : new Fire(onCommit, event());
    });
  }

  private String filtered(String in){
    var f= frame.onEdtAndWait(() -> onInput);
    var s= f == null ? standard(in, oneLine()) : ustr(f.mut$$hash$1(UStr$s$0Instance.instance(in)));
    int nl= s.indexOf('\n');
    if (oneLine() && nl >= 0){ throw Util.detErr("The .onInput of this TextField returned a text with a newline at offset " + s.codePointCount(0, nl) + ": the text of a TextField is one line; return the text without newlines, for example with each newline replaced by a space"); }
    return s;
  }
  static String standard(String in, boolean oneLine){
    var s= Normalizer.normalize(in.replace("\r\n", "\n").replace('\r', '\n').replace('\t', ' '), Normalizer.Form.NFKD);
    if (oneLine){ s = s.replace('\n', ' '); }
    var res= new StringBuilder();
    s.codePoints().filter(UStr$s$0Instance::isStrScalar).forEach(res::appendCodePoint);
    return res.toString();
  }

  void key(KeyEvent e){
    boolean shift= e.isShiftDown();
    boolean ctrl= e.isControlDown();
    switch (e.getKeyCode()){
      case VK_LEFT, VK_KP_LEFT -> act(() -> horizontal(false, shift));
      case VK_RIGHT, VK_KP_RIGHT -> act(() -> horizontal(true, shift));
      case VK_UP, VK_KP_UP -> act(() -> vertical(-1, shift));
      case VK_DOWN, VK_KP_DOWN -> act(() -> vertical(1, shift));
      case VK_PAGE_UP -> act(() -> vertical(-page(), shift));
      case VK_PAGE_DOWN -> act(() -> vertical(page(), shift));
      case VK_HOME -> act(() -> home(ctrl, shift));
      case VK_END -> act(() -> end(ctrl, shift));
      case VK_BACK_SPACE -> act(this::backspace);
      case VK_DELETE -> act(this::delete);
      case VK_TAB -> frame.tab(shift);
      case VK_ENTER -> enter(shift, ctrl);
      default -> { if (ctrl && !e.isAltDown()){ shortcut(e.getKeyCode(), shift); } }
    }
  }
  private void enter(boolean shift, boolean ctrl){
    if (ctrl || !shift){ commit(); return; }
    if (!oneLine()){ act(() -> edit(selA(), selB(), "\n")); }
  }
  private void shortcut(int key, boolean shift){
    switch (key){
      case VK_A -> act(this::selectAll);
      case VK_C -> act(this::copy);
      case VK_X -> act(this::cut);
      case VK_V -> paste();
      case VK_Z -> act(shift ? this::redo : this::undo);
      case VK_Y -> act(this::redo);
      default -> {}
    }
  }
  private void paste(){
    var c= Toolkit.getDefaultToolkit().getSystemClipboard();
    String s;
    try{ s= c.isDataFlavorAvailable(DataFlavor.stringFlavor) ? (String) c.getData(DataFlavor.stringFlavor) : null; }
    catch (IllegalStateException _){ s= null; }
    catch (UnsupportedFlavorException | IOException e){ throw new Error("Broken Environment", e); }
    if (s != null){ input(s); }
  }

  Rectangle caretOnScreen(){
    layout();
    int lh= lineHeight();
    int k= lineOf(caret);
    var p= SwingUtilities.convertPoint(component, (int) (left - scrollX + xOf(lines.get(k), caret)), top - scrollY + k * lh, frame.top.component);
    double s= frame.stage.scale();
    var o= frame.stage.offset(s);
    var on= frame.surface.getLocationOnScreen();
    return new Rectangle(on.x + o.x + (int) (p.x * s), on.y + o.y + (int) (p.y * s), 1, (int) Math.ceil(lh * s));
  }

  @Override void sk(Canvas cv){
    layout();
    int w= component.getWidth();
    int h= component.getHeight();
    boolean focused= frame.focus == this;
    float r= Math.min(radius, Math.min(w, h) / 2f);
    Sk.paint.setColor(bg >>> 24 == 0 ? 0xFFFFFFFF : bg);
    cv.drawRRect(RRect.makeXYWH(0, 0, w, h, r), Sk.paint);
    float sw= focused ? 2 : 1;
    stroke.setStrokeWidth(sw).setColor(focused ? 0xFF4878D2 : 0xFF8C8C8C);
    cv.drawRRect(RRect.makeXYWH(sw / 2, sw / 2, w - sw, h - sw, Math.max(0, r - sw / 2)), stroke);
    int save= cv.save();
    cv.clipRect(Rect.makeXYWH(left, top, viewW(), viewH()));
    cv.translate(left - scrollX, top - scrollY);
    int lh= lineHeight();
    int first= Math.max(0, scrollY / lh);
    int last= Math.min(lines.size() - 1, (scrollY + viewH()) / lh);
    for (int i= first; i <= last; i += 1){ line(cv, lines.get(i), i * lh, lh, focused); }
    if (focused && frame.keyboard){
      int k= lineOf(caret);
      cv.translate(xOf(lines.get(k), caret), k * lh);
      caret(cv, lh);
    }
    cv.restoreToCount(save);
    if (barRight()){ Sk.bar(cv, this, true); }
    if (barBottom()){ Sk.bar(cv, this, false); }
  }
  private void line(Canvas cv, VLine v, int y, int lh, boolean focused){
    int a= selA();
    int b= selB();
    int vEnd= v.wrapped() ? v.end() : v.end() + 1;
    if (a < b && a < vEnd && b > v.start()){
      float x1= a <= v.start() ? 0 : xOf(v, a);
      float x2= b > v.end() ? width(v.line()) + (v.wrapped() ? 0 : textSize / 3f) : xOf(v, b);
      Sk.paint.setColor(focused ? 0xFFB4CDF5 : 0xFFDCDCDC);
      cv.drawRect(Rect.makeXYWH(x1, y, x2 - x1, lh), Sk.paint);
    }
    if (v.line() == null){ return; }
    Sk.paint.setColor(fg);
    cv.drawTextLine(v.line(), 0, (float) (y + textSize * above), Sk.paint);
  }
  private void caret(Canvas cv, int lh){
    if (!composing.isEmpty()){
      try (var t= Sk.shape(composing, textSize)){
        Sk.paint.setColor(0xFFFFFFF0);
        cv.drawRect(Rect.makeXYWH(0, 0, t.getWidth(), lh), Sk.paint);
        Sk.paint.setColor(fg);
        float base= (float) (textSize * above);
        cv.drawTextLine(t, 0, base, Sk.paint);
        cv.drawRect(Rect.makeXYWH(0, base + 2, t.getWidth(), 1), Sk.paint);
        cv.translate(t.getWidth(), 0);
      }
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

record CTextEvent(Instant$5c$0 elapsed, WidthNat$as$0 panelWidth, HeightNat$lg$0 panelHeight, Object text, Object caret, Object anchor) implements TextEvent$ls$0{
  @Override public Object read$elapsed$0(){ return elapsed; }
  @Override public Object read$panelWidth$0(){ return panelWidth; }
  @Override public Object read$panelHeight$0(){ return panelHeight; }
  @Override public Object imm$text$0(){ return text; }
  @Override public Object imm$caret$0(){ return caret; }
  @Override public Object imm$anchor$0(){ return anchor; }
}

final class CTextKeys extends KeyAdapter implements FocusListener, InputMethodListener, InputMethodRequests{
  final _Frame frame;
  boolean reservedDown;
  char high;
  CTextKeys(_Frame frame){ this.frame= frame; }

  boolean uses(KeyEvent e){
    var t= frame.focus;
    int k= e.getKeyCode();
    if (t == null || frame.reserved(k)){ return false; }
    return switch (k){
      case VK_LEFT, VK_RIGHT, VK_KP_LEFT, VK_KP_RIGHT, VK_HOME, VK_END, VK_BACK_SPACE, VK_DELETE, VK_ENTER, VK_TAB -> true;
      case VK_UP, VK_DOWN, VK_KP_UP, VK_KP_DOWN, VK_PAGE_UP, VK_PAGE_DOWN -> !t.oneLine();
      case VK_A, VK_C, VK_X, VK_V, VK_Z, VK_Y -> e.isControlDown() && !e.isAltDown() || types(e);
      default -> k >= VK_DEAD_GRAVE && k <= VK_DEAD_SEMIVOICED_SOUND || types(e);
    };
  }
  static boolean typesWith(java.awt.event.InputEvent e){ return e.isAltDown() == e.isControlDown() || e.isAltGraphDown(); }
  static boolean types(KeyEvent e){
    char c= e.getKeyChar();
    return c != CHAR_UNDEFINED && !Character.isISOControl(c) && typesWith(e);
  }
  @Override public void keyPressed(KeyEvent e){
    reservedDown = frame.reserved(e.getKeyCode());
    if (!uses(e)){ return; }
    e.consume();
    frame.focus.key(e);
  }
  @Override public void keyTyped(KeyEvent e){
    var t= frame.focus;
    char c= e.getKeyChar();
    if (t == null || reservedDown || Character.isISOControl(c) || !typesWith(e)){ return; }
    e.consume();
    if (Character.isHighSurrogate(c)){ high = c; return; }
    if (Character.isLowSurrogate(c) && high == 0){ return; }
    var s= Character.isLowSurrogate(c) ? "" + high + c : "" + c;
    high = 0;
    t.input(s);
  }
  @Override public void focusGained(FocusEvent e){ frame.keyboard = true; }
  @Override public void focusLost(FocusEvent e){ frame.keyboard = false; }

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
    if (t == null){ return; }
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
