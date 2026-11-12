package _base;

import static _base.Scopes.*;
import static java.awt.event.KeyEvent.*;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.Rect;

final class CMenu implements Menu$o$0{
  final ArrayList<CMenuItem> items= new ArrayList<>();
  boolean built;
  static CMenu build(Object scope){
    var res= new CMenu();
    ((Scope$1c$1) scope).mut$run$1(res);
    res.built = true;
    return res;
  }
  boolean empty(){ return items.stream().allMatch(i -> i == null); }
  private void open(){
    if (built){ throw Util.detErr("This Menu is already built: a menu is fixed once the scope that builds it returns, so a change now would never show"); }
  }
  @Override public Object mut$item$1(Object s){
    open();
    var i= new CMenuItem();
    ((Scope$1c$1) s).mut$run$1(i);
    i.built = true;
    items.add(i);
    return this;
  }
  @Override public Object mut$separator$0(){
    open();
    items.add(null);
    return this;
  }
}

enum MenuMark{ none, check, radio }

final class CMenuItem implements MenuItem$aw$0{
  String text= "";
  boolean enabled= true;
  MenuMark mark= MenuMark.none;
  boolean ticked;
  MF$7$1 action;
  CMenu submenu;
  boolean built;
  Sk.Line line;
  private void open(){
    if (built){ throw Util.detErr("This MenuItem is already built: an item is fixed once the scope of its .item returns, so a change now would never show"); }
  }
  private String named(){ return text.isEmpty() ? "This MenuItem" : "The MenuItem \"" + text + "\""; }
  @Override public Object mut$uText$1(Object t){
    open();
    var s= ustr(t);
    var b= newline(s);
    if (b != null){ throw Util.detErr("The new text of this MenuItem has " + b + ": the text of a MenuItem is one line"); }
    text = s;
    return this;
  }
  @Override public Object mut$enabled$1(Object b){
    open();
    enabled = Util.isTrue(b);
    return this;
  }
  @Override public Object mut$check$1(Object b){ return mark(MenuMark.check, b); }
  @Override public Object mut$radio$1(Object b){ return mark(MenuMark.radio, b); }
  private Object mark(MenuMark m, Object b){
    open();
    if (mark != MenuMark.none && mark != m){ throw Util.detErr(named() + " already shows a " + (mark == MenuMark.check ? "check box" : "radio button") + ", so it cannot also show a " + (m == MenuMark.check ? "check box" : "radio button") + ": an item shows at most one mark"); }
    mark = m;
    ticked = Util.isTrue(b);
    return this;
  }
  @Override public Object mut$action$1(Object a){
    open();
    if (submenu != null){ throw Util.detErr(named() + " already has a submenu, so it cannot also have an action: choosing an item either opens its submenu or runs its action"); }
    action = (MF$7$1) a;
    return this;
  }
  @Override public Object mut$submenu$1(Object s){
    open();
    if (action != null){ throw Util.detErr(named() + " already has an action, so it cannot also have a submenu: choosing an item either opens its submenu or runs its action"); }
    submenu = CMenu.build(s);
    return this;
  }
}

final class SkMenus{
  record Open(CMenu menu, Rectangle box, int[] tops, int textAt, int from, int content, int[] scroll){
    boolean scrolls(){ return content > box.height; }
    int room(){ return scrolls() ? AWidget.bar + 4 : 0; }
    Rectangle track(){ return new Rectangle(box.x + box.width - 3 - AWidget.bar, box.y + 4, AWidget.bar, Math.max(0, box.height - 8)); }
    Rectangle thumb(){ return AWidget.thumb(track(), true, box.height, content, scroll[0]); }
    void scrollTo(int v){ scroll[0] = Math.clamp(v, 0, content - box.height); }
    int height(int i){ return (i + 1 < tops.length ? tops[i + 1] : content - 4) - tops[i]; }
  }
  record Hit(int menu, int item){}
  static final int row= 24;
  static final int gap= 9;
  static final int pad= 8;
  static final Paint line= new Paint().setAntiAlias(true).setMode(PaintMode.STROKE).setStrokeWidth(1.5f);
  final _Frame frame;
  final ArrayList<Open> open= new ArrayList<>();
  int highMenu= -1;
  int highItem= -1;
  int pressMenu= -1;
  int pressItem= -1;
  int dragMenu= -1;
  int grab;
  boolean swallow;
  boolean took;

  SkMenus(_Frame frame){ this.frame= frame; }

  boolean isOpen(){ return !open.isEmpty(); }

  void show(CMenu m, Rectangle anchor){
    if (m.empty()){ return; }
    close(0);
    frame.mouse.forget();
    took = true;
    add(m, anchor, false, -1);
  }

  private void add(CMenu m, Rectangle anchor, boolean side, int from){
    boolean marks= m.items.stream().anyMatch(i -> i != null && i.mark != MenuMark.none);
    boolean subs= m.items.stream().anyMatch(i -> i != null && i.submenu != null);
    var tops= new int[m.items.size()];
    int y= 4;
    float text= 0;
    for (int i= 0; i < tops.length; i += 1){
      var it= m.items.get(i);
      tops[i] = y;
      if (it == null){ y += gap; continue; }
      it.line = Sk.shape(it.text, Sk.TextSettings.label);
      var need= Sk.need(it.line);
      y += need.height + row - 18;
      text = Math.max(text, need.width);
    }
    int textAt= 1 + pad + (marks ? 18 : 0);
    int content= y + 4;
    int vw= frame.top.component.getWidth();
    int vh= frame.top.component.getHeight();
    int h= Math.min(content, vh);
    int w= textAt + (int) Math.ceil(text) + (subs ? 20 : pad) + 1 + (content > vh ? AWidget.bar + 4 : 0);
    int x= side ? place(anchor.x + anchor.width, anchor.x, w, vw) : place(anchor.x, anchor.x + anchor.width, w, vw);
    int top= side ? clamp(anchor.y, h, vh) : place(anchor.y + anchor.height, anchor.y, h, vh);
    if (w > vw){ frame.guiRun.warn("a menu " + w + " virtual pixels wide does not fit in the virtual pixel space " + vw + " wide: the part outside it is clipped"); }
    open.add(new Open(m, new Rectangle(x, top, w, h), tops, textAt, from, content, new int[1]));
  }

  private static int place(int first, int second, int size, int room){
    if (first + size > room && second - size >= 0){ return second - size; }
    return clamp(first, size, room);
  }

  private static int clamp(int at, int size, int room){ return Math.max(0, Math.min(at, room - size)); }

  void close(int k){
    if (open.size() <= k){ return; }
    while (open.size() > k){ open.removeLast(); }
    if (highMenu >= k){ highMenu = -1; }
    if (pressMenu >= k){ pressMenu = -1; }
    if (dragMenu >= k){ dragMenu = -1; }
    if (k == 0){ frame.mouse.rehover(); }
  }

  private void choose(int k, int i){
    var it= open.get(k).menu().items.get(i);
    if (!it.enabled){ return; }
    if (it.submenu == null){
      close(0);
      if (it.action != null){ frame.guiRun.queue.submit(it.action); }
      return;
    }
    boolean was= open.size() > k + 1 && open.get(k + 1).from() == i;
    close(k + 1);
    if (was || it.submenu.empty()){ return; }
    var o= open.get(k);
    add(it.submenu, new Rectangle(o.box().x, o.box().y + o.tops()[i] - 4 - o.scroll()[0], o.box().width, o.height(i)), true, i);
  }

  private Hit hit(Point p){
    for (int k= open.size() - 1; k >= 0; k -= 1){
      var o= open.get(k);
      if (!o.box().contains(p)){ continue; }
      if (onBar(o, p)){ return new Hit(k, -1); }
      int y= p.y - o.box().y + o.scroll()[0];
      for (int i= 0; i < o.tops().length; i += 1){
        boolean on= o.menu().items.get(i) != null && y >= o.tops()[i] && y < o.tops()[i] + o.height(i);
        if (on){ return new Hit(k, i); }
      }
      return new Hit(k, -1);
    }
    return null;
  }

  boolean pressed(Point p, int clicks, boolean left){
    boolean second= took && clicks == 2;
    took = isOpen() || swallow || second;
    if (!took){ return false; }
    if (second || !isOpen()){ swallow = true; return true; }
    var h= hit(p);
    if (h == null){ close(0); swallow = true; return true; }
    if (left && onBar(open.get(h.menu()), p)){ pressBar(h.menu(), p); return true; }
    if (left){ pressMenu = h.menu(); pressItem = h.item(); }
    return true;
  }

  private static boolean onBar(Open o, Point p){ return o.scrolls() && p.x >= o.track().x - 2; }

  private void pressBar(int k, Point p){
    var o= open.get(k);
    var m= o.thumb();
    if (p.y >= m.y && p.y < m.y + m.height){ dragMenu = k; grab = p.y - m.y; return; }
    o.scrollTo(o.scroll()[0] + (p.y < m.y ? -1 : 1) * (o.box().height - 8));
  }

  void wheel(Point p, double rotation){
    var h= hit(p);
    if (h == null){ return; }
    var o= open.get(h.menu());
    o.scrollTo(o.scroll()[0] + (int) Math.round(rotation * 40));
    moved(p);
  }

  boolean released(Point p, boolean left, boolean allUp){
    if (swallow){ swallow = !allUp; return true; }
    if (!isOpen()){ return false; }
    if (!left){ return true; }
    if (dragMenu >= 0){ dragMenu = -1; return true; }
    var h= hit(p);
    boolean same= h != null && h.item() >= 0 && h.menu() == pressMenu && h.item() == pressItem;
    pressMenu = -1;
    if (same){ choose(h.menu(), h.item()); }
    return true;
  }

  boolean moved(Point p){
    if (!isOpen()){ return swallow; }
    if (dragMenu >= 0){ dragBar(p); return true; }
    var h= hit(p);
    boolean on= h != null && h.item() >= 0 && open.get(h.menu()).menu().items.get(h.item()).enabled;
    highMenu = on ? h.menu() : -1;
    highItem = on ? h.item() : -1;
    return true;
  }

  private void dragBar(Point p){
    var o= open.get(dragMenu);
    var t= o.track();
    o.scrollTo(AWidget.scrollFor(t, o.thumb(), true, p.y - grab - t.y, o.content() - o.box().height));
  }

  void key(KeyEvent e){
    int k= open.size() - 1;
    switch (e.getKeyCode()){
      case VK_DOWN, VK_KP_DOWN -> step(k, 1);
      case VK_UP, VK_KP_UP -> step(k, -1);
      case VK_HOME -> fromEdge(k, 1);
      case VK_END -> fromEdge(k, -1);
      case VK_ENTER -> { if (highMenu == k){ choose(k, highItem); } }
      default -> {}
    }
  }

  private void fromEdge(int k, int by){
    highMenu = -1;
    step(k, by);
  }
  private void step(int k, int by){
    var items= open.get(k).menu().items;
    int n= items.size();
    int i= highMenu == k ? highItem : by > 0 ? -1 : n;
    for (int t= 0; t < n; t += 1){
      i = Math.floorMod(i + by, n);
      var it= items.get(i);
      if (it != null && it.enabled){ highMenu = k; highItem = i; reveal(open.get(k), i); return; }
    }
  }

  private static void reveal(Open o, int i){
    int top= o.tops()[i] - 4;
    int bottom= o.tops()[i] + o.height(i) + 4 - o.box().height;
    if (top < o.scroll()[0]){ o.scrollTo(top); }
    if (bottom > o.scroll()[0]){ o.scrollTo(bottom); }
  }

  void paint(Canvas cv){
    for (int k= 0; k < open.size(); k += 1){
      var o= open.get(k);
      var b= o.box();
      Sk.paint.setColor(0xFF767676);
      cv.drawRect(Rect.makeXYWH(b.x, b.y, b.width, b.height), Sk.paint);
      Sk.paint.setColor(0xFFFAFAFA);
      var inside= Rect.makeXYWH(b.x + 1, b.y + 1, b.width - 2, Math.max(0, b.height - 2));
      cv.drawRect(inside, Sk.paint);
      int save= cv.save();
      cv.clipRect(inside);
      for (int i= 0; i < o.tops().length; i += 1){ paint(cv, k, i); }
      cv.restoreToCount(save);
      if (o.scrolls()){ Sk.bar(cv, o.track(), o.thumb(), 0, false); }
    }
  }

  private void paint(Canvas cv, int k, int i){
    var o= open.get(k);
    var b= o.box();
    int y= b.y + o.tops()[i] - o.scroll()[0];
    int w= b.width - o.room();
    var it= o.menu().items.get(i);
    if (it == null){
      Sk.paint.setColor(0xFFC8C8C8);
      cv.drawRect(Rect.makeXYWH(b.x + 5, y + 4, w - 10, 1), Sk.paint);
      return;
    }
    int h= o.height(i);
    boolean high= highMenu == k && highItem == i;
    boolean path= open.size() > k + 1 && open.get(k + 1).from() == i;
    if (high || path){
      Sk.paint.setColor(high ? 0xFFB4CDF5 : 0xFFDCDCDC);
      cv.drawRect(Rect.makeXYWH(b.x + 1, y, w - 2, h), Sk.paint);
    }
    int ink= it.enabled ? 0xFF000000 : 0xFF9A9A9A;
    Sk.paint.setColor(ink);
    line.setColor(ink);
    Sk.draw(cv, it.line, b.x + o.textAt(), y, Sk.need(it.line).width, h, Sk.paint);
    float mx= b.x + 1 + pad + 5;
    float my= y + h / 2f;
    if (it.mark == MenuMark.check){ cv.drawRect(Rect.makeXYWH(mx - 5, my - 5, 10, 10), line); }
    if (it.mark == MenuMark.check && it.ticked){
      cv.drawLine(mx - 3, my, mx - 1, my + 3, line);
      cv.drawLine(mx - 1, my + 3, mx + 3.5f, my - 3.5f, line);
    }
    if (it.mark == MenuMark.radio){ cv.drawCircle(mx, my, 5, line); }
    if (it.mark == MenuMark.radio && it.ticked){ cv.drawCircle(mx, my, 2.5f, Sk.paint); }
    if (it.submenu == null){ return; }
    float ax= b.x + w - 14;
    cv.drawLine(ax, my - 4, ax + 4, my, line);
    cv.drawLine(ax + 4, my, ax, my + 4, line);
  }
}
