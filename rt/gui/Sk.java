package _base;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Path;
import io.github.humbleui.skija.PathBuilder;
import io.github.humbleui.skija.PathOp;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.foreign.MemorySegment;
import java.text.Bidi;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import static _base.Scopes.*;

interface Sk{
  Paint paint= new Paint().setAntiAlias(true);
  Paint stroke= new Paint().setAntiAlias(true).setStrokeWidth(2);
  // Lookup order. The script fonts must precede Math: it has isolated Arabic
  // letters, and would win them from the Arabic font, which joins them.
  String[] fontFiles= {
    "NotoSans-Regular.ttf",
    "NotoSansArabic-Regular.ttf",
    "NotoSansHebrew-Regular.ttf",
    "NotoSansThaana-Regular.ttf",
    "NotoSansSyriac-Regular.ttf",
    "NotoSansArmenian-Regular.ttf",
    "NotoSansGeorgian-Regular.ttf",
    "NotoSansEthiopic-Regular.ttf",
    "NotoSansNKo-Regular.ttf",
    "NotoSansTifinagh-Regular.ttf",
    "NotoSansDevanagari-Regular.ttf",
    "NotoSansBengali-Regular.ttf",
    "NotoSansGurmukhi-Regular.ttf",
    "NotoSansGujarati-Regular.ttf",
    "NotoSansOriya-Regular.ttf",
    "NotoSansTamil-Regular.ttf",
    "NotoSansTelugu-Regular.ttf",
    "NotoSansKannada-Regular.ttf",
    "NotoSansMalayalam-Regular.ttf",
    "NotoSansSinhala-Regular.ttf",
    "NotoSansOlChiki-Regular.ttf",
    "NotoSansMeeteiMayek-Regular.ttf",
    "NotoSansThai-Regular.ttf",
    "NotoSansLao-Regular.ttf",
    "NotoSansKhmer-Regular.ttf",
    "NotoSansMyanmar-Regular.ttf",
    "NotoSerifTibetan-Regular.ttf",
    "NotoSansCherokee-Regular.ttf",
    "NotoSansCanadianAboriginal-Regular.ttf",
    "NotoSansSymbols2-Regular.ttf",
    "NotoSansSymbols-Regular.ttf",
    "NotoSansMath-Regular.ttf",
    "NotoSansCJKsc-Regular.otf",
    "Twemoji.Mozilla.ttf"};
  int emoji= fontFiles.length - 1;
  MemorySegment[] fonts= new MemorySegment[fontFiles.length];
  int[] upems= new int[fontFiles.length];
  HashMap<Integer,Integer> fontByCp= new HashMap<>();
  HashMap<Long,Glyph> glyphs= new HashMap<>();
  record Glyph(List<Hb.Layer> layers, Rect box){}
  record Run(int from, int to, int font, int level, int script){}
  record G(int font, int glyph, int cluster, boolean rtl, double x, double advance, double dx, double dy){}
  record Line(String text, int size, List<G> glyphs, double width){
    double coord(int off){
      G best= null;
      for (var g : glyphs){
        if (g.cluster() >= off && (best == null || g.cluster() < best.cluster() || g.cluster() == best.cluster() && (g.rtl() ? g.x() > best.x() : g.x() < best.x()))){ best = g; }
      }
      if (best == null){ return width; }
      return best.rtl() ? best.x() + best.advance() : best.x();
    }
    int offsetAt(double x){
      var cs= glyphs.stream().mapToInt(G::cluster).distinct().sorted().toArray();
      int res= 0;
      double d= Double.POSITIVE_INFINITY;
      for (var g : glyphs){
        int k= Arrays.binarySearch(cs, g.cluster());
        int after= k + 1 < cs.length ? cs[k + 1] : text.length();
        double l= Math.abs(g.x() - x);
        double r= Math.abs(g.x() + g.advance() - x);
        if (l < d){ d = l; res = g.rtl() ? after : g.cluster(); }
        if (r < d){ d = r; res = g.rtl() ? g.cluster() : after; }
      }
      return res;
    }
  }

  static MemorySegment font(int i){
    if (fonts[i] == null){ fonts[i] = Hb.font(fontBytes(fontFiles[i])); upems[i] = Hb.upem(fonts[i]); }
    return fonts[i];
  }

  static byte[] fontBytes(String name){
    try (var in= Sk.class.getResourceAsStream(name)){ return in.readAllBytes(); }
    catch (IOException e){ throw new UncheckedIOException(e); }
  }

  static int fontOf(int cp, boolean emojiFirst){
    return fontByCp.computeIfAbsent(cp << 1 | (emojiFirst ? 1 : 0), _ -> search(cp, emojiFirst));
  }

  static int search(int cp, boolean emojiFirst){
    if (emojiFirst && Hb.has(font(emoji), cp)){ return emoji; }
    for (int i= 0; i < fontFiles.length; i += 1){
      if (Hb.has(font(i), cp)){ return i; }
    }
    return 0;
  }

  static boolean sticky(int cp){
    int t= Character.getType(cp);
    return t == Character.NON_SPACING_MARK || t == Character.ENCLOSING_MARK
      || t == Character.COMBINING_SPACING_MARK || t == Character.FORMAT || Character.isEmojiModifier(cp);
  }

  static Line shape(String text, int size){
    if (text.isEmpty()){ return new Line(text, size, List.of(), 0); }
    synchronized (fonts){
      var bidi= new Bidi(text, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
      var runs= new ArrayList<Run>();
      int from= 0, font= -1, level= 0, script= Hb.common;
      for (int i= 0, prev= -1; i < text.length();){
        int cp= text.codePointAt(i);
        int next= i + Character.charCount(cp);
        int f= font;
        if (font < 0 || !(sticky(cp) || prev == 0x200D)){
          int nx= next < text.length() ? text.codePointAt(next) : -1;
          f = fontOf(cp, nx == 0xFE0F || Character.isEmojiPresentation(cp));
        }
        int s= Hb.script(cp);
        boolean weak= s == Hb.common || s == Hb.inherited || s == Hb.unknown;
        int l= bidi.getLevelAt(i);
        if (font >= 0 && (f != font || l != level || !weak && script != Hb.common && s != script)){ runs.add(new Run(from, i, font, level, script)); from = i; }
        font = f;
        level = l;
        if (!weak){ script = s; }
        prev = cp;
        i = next;
      }
      runs.add(new Run(from, text.length(), font, level, script));
      var levels= new byte[runs.size()];
      for (int i= 0; i < levels.length; i += 1){ levels[i] = (byte) runs.get(i).level(); }
      var order= runs.toArray(Run[]::new);
      Bidi.reorderVisually(levels, 0, order, 0, order.length);
      var res= new ArrayList<G>();
      double x= 0;
      for (var r : order){
        double k= (double) size / upems[r.font()];
        boolean rtl= (r.level() & 1) != 0;
        for (var g : Hb.shape(fonts[r.font()], text, r.from(), r.to(), rtl, r.script())){
          res.add(new G(r.font(), g.glyph(), g.cluster(), rtl, x, g.advance() * k, g.dx() * k, -g.dy() * k));
          x += g.advance() * k;
        }
      }
      return new Line(text, size, List.copyOf(res), x);
    }
  }

  static Glyph glyph(int font, int glyph){
    synchronized (fonts){
      return glyphs.computeIfAbsent((long) font << 32 | glyph, _ -> {
        var layers= Hb.layers(fonts[font], glyph);
        return new Glyph(layers, layers.stream().map(l -> l.path().getBounds()).filter(b -> !b.isEmpty()).reduce(Sk::union).orElse(null));
      });
    }
  }

  static Rect union(Rect a, Rect b){
    return Rect.makeLTRB(Math.min(a.getLeft(), b.getLeft()), Math.min(a.getTop(), b.getTop()), Math.max(a.getRight(), b.getRight()), Math.max(a.getBottom(), b.getBottom()));
  }

  static Rect ink(Line line){
    var res= Rect.makeWH(0, 0);
    for (var g : line.glyphs()){
      var b= glyph(g.font(), g.glyph()).box();
      if (b == null){ continue; }
      double k= (double) line.size() / upems[g.font()];
      double x= g.x() + g.dx();
      res = union(res, Rect.makeLTRB((float) (x + b.getLeft() * k), (float) (g.dy() + b.getTop() * k), (float) (x + b.getRight() * k), (float) (g.dy() + b.getBottom() * k)));
    }
    return res;
  }

  static Line line(AWidget s){
    if (s.line == null){ s.line = shape(s.text, s.textSize); }
    return s.line;
  }


  static long lineHeight(int size, double above, double below){ return Math.round(size * (above + below)); }

  static long lineHeight(AWidget s){ return lineHeight(s.textSize, s.above, s.below); }

  static int color(Color$1c$0 c){
    return alpha(c.read$alpha$0()) << 24 | red(c.read$red$0()) << 16
      | green(c.read$green$0()) << 8 | blue(c.read$blue$0());
  }

  static void paintNode(SkComponent c, Canvas cv){
    c.w.textWarning = c.w.warned(c.w.textWarning, textClip(c.w));
    c.w.sk(cv);
    int outer= cv.save();
    c.w.clipChildren(cv);
    for (int i= 0; i < c.getComponentCount(); i += 1){
      var k= c.getComponent(i);
      if (c.w.hides(k)){ continue; }
      var kw= ((SkComponent) k).w;
      kw.boundsWarning = kw.warned(kw.boundsWarning, boundsClip(c, k));
      kw.overlapWarning = kw.warned(kw.overlapWarning, overlap(c, i));
      int save= cv.save();
      var r= c.w.region(k);
      if (r != null){ cv.clipRect(Rect.makeXYWH(r.x, r.y, r.width, r.height)); }
      cv.translate(k.getX(), k.getY());
      cv.clipRect(Rect.makeWH(k.getWidth(), k.getHeight()));
      paintNode((SkComponent) k, cv);
      cv.restoreToCount(save);
    }
    cv.restoreToCount(outer);
    c.w.skOver(cv);
  }

  static void head(Canvas cv, _Sections p, _Sections.Section s){
    var h= s.head;
    paint.setColor(p.fg & 0xFFFFFF | (s.expanded ? 0x40000000 : 0x18000000));
    cv.drawRRect(RRect.makeXYWH(h.x, h.y, h.width, h.height, 4), paint);
    stroke.setColor(p.fg & 0xFFFFFF | 0xA0000000);
    float y= h.y + h.height / 2f;
    if (!p.tabs){
      float x= h.x + _Sections.mark / 2f;
      if (s.expanded){ cv.drawLine(x - 4, y - 2, x, y + 2, stroke); cv.drawLine(x, y + 2, x + 4, y - 2, stroke); }
      else{ cv.drawLine(x - 2, y - 4, x + 2, y, stroke); cv.drawLine(x + 2, y, x - 2, y + 4, stroke); }
    }
    if (p.onClose == null){ return; }
    float x= h.x + h.width - _Sections.mark / 2f;
    cv.drawLine(x - 3, y - 3, x + 3, y + 3, stroke);
    cv.drawLine(x - 3, y + 3, x + 3, y - 3, stroke);
  }

  static void bar(Canvas cv, AWidget p, boolean vert){
    var t= p.track(vert);
    var m= p.thumb(vert);
    paint.setColor(p.fg & 0xFFFFFF | 0x30000000);
    cv.drawRRect(RRect.makeXYWH(t.x, t.y, t.width, t.height, AWidget.bar / 2f), paint);
    boolean fits= vert ? m.height == t.height : m.width == t.width;
    paint.setColor(p.fg & 0xFFFFFF | (fits ? 0x30000000 : 0xA0000000));
    cv.drawRRect(RRect.makeXYWH(m.x, m.y, m.width, m.height, AWidget.bar / 2f), paint);
  }

  static void divider(Canvas cv, _Split s){
    var d= s.grip();
    if (d.isEmpty()){ return; }
    paint.setColor(s.fg & 0xFFFFFF | 0x30000000);
    cv.drawRect(Rect.makeXYWH(d.x, d.y, d.width, d.height), paint);
    if (!s.draggable){ return; }
    int t= Math.max(1, (s.vertical ? d.height : d.width) / 3);
    float l= Math.min(s.vertical ? d.width : d.height, 30);
    paint.setColor(s.fg & 0xFFFFFF | 0xA0000000);
    float cx= d.x + d.width / 2f;
    float cy= d.y + d.height / 2f;
    float gw= s.vertical ? l : t;
    float gh= s.vertical ? t : l;
    cv.drawRRect(RRect.makeXYWH(cx - gw / 2, cy - gh / 2, gw, gh, t / 2f), paint);
  }

  record Tip(String text, List<Line> lines, List<Dimension> needs, int width, int height){}

  static Tip tip(String text){
    var lines= Arrays.stream(text.split("\n", -1)).map(l -> shape(l, 12)).toList();
    var needs= lines.stream().map(l -> need(l, 1, 0.5)).toList();
    int bw= needs.stream().mapToInt(d -> d.width).max().orElseThrow() + 10;
    int bh= needs.stream().mapToInt(d -> d.height).sum() + 6;
    return new Tip(text, lines, needs, bw, bh);
  }

  static void tooltip(Canvas cv, Tip tip, java.awt.Point at, int w, int h){
    var lines= tip.lines();
    var needs= tip.needs();
    float bw= tip.width();
    float bh= tip.height();
    float x= Math.clamp(at.x, 0, Math.max(0, w - bw));
    float y= at.y + 20 + bh <= h ? at.y + 20 : Math.max(0, at.y - 4 - bh);
    paint.setColor(0xFF767676);
    cv.drawRect(Rect.makeXYWH(x, y, bw, bh), paint);
    paint.setColor(0xFFFAFAFA);
    cv.drawRect(Rect.makeXYWH(x + 1, y + 1, bw - 2, bh - 2), paint);
    paint.setColor(0xFF000000);
    float ly= y + 3;
    for (int i= 0; i < lines.size(); i += 1){
      var n= needs.get(i);
      draw(cv, lines.get(i), x + 5, ly, n.width, n.height, 1, 0.5, paint);
      ly += n.height;
    }
  }

  static void background(Canvas cv, AWidget s){
    if (s.bg >>> 24 == 0){ return; }
    float w= s.component.getWidth();
    float h= s.component.getHeight();
    paint.setColor(s.bg);
    cv.drawRRect(RRect.makeXYWH(0, 0, w, h, Math.min(s.radius, Math.min(w, h) / 2)), paint);
  }

  static String textClip(AWidget s){
    if (s.text.isEmpty()){ return null; }
    var need= textSizeWithInsets(s);
    var c= s.component;
    if (need.width <= c.getWidth() && need.height <= c.getHeight()){ return null; }
    return "the text of " + s.describe() + " needs " + need.width + "x" + need.height + " insets included, but the widget is " + c.getWidth() + "x" + c.getHeight() + ": the text is clipped";
  }

  static String boundsClip(SkComponent c, Component k){
    var r= c.w.region(k);
    if (r != null){ return regionClip(c.w, k, r); }
    boolean scrollsX= c.w.barBottom();
    boolean scrollsY= c.w.barRight();
    boolean insideX= scrollsX || k.getX() >= 0 && k.getX() + k.getWidth() <= c.getWidth();
    boolean insideY= scrollsY || k.getY() >= 0 && k.getY() + k.getHeight() <= c.getHeight();
    boolean inside= insideX && insideY;
    if (inside){ return null; }
    return ((SkComponent) k).w.describe() + " of " + k.getWidth() + "x" + k.getHeight() + " at (" + k.getX() + ", " + k.getY() + ") does not fit in its parent, " + c.w.describe() + " of " + c.getWidth() + "x" + c.getHeight() + ": the part outside the parent is clipped";
  }

  static String regionClip(AWidget s, Component k, java.awt.Rectangle r){
    boolean inside= k.getX() >= r.x && k.getY() >= r.y && k.getX() + k.getWidth() <= r.x + r.width && k.getY() + k.getHeight() <= r.y + r.height;
    if (inside){ return null; }
    var which= !(s instanceof _Split p) ? "content area" : k == p.side(BorderLayout.LINE_START) ? "first region" : "second region";
    return ((SkComponent) k).w.describe() + " of " + k.getWidth() + "x" + k.getHeight() + " at (" + k.getX() + ", " + k.getY() + ") does not fit in the " + which + " of its parent, " + s.describe() + ": the " + which + " is " + r.width + "x" + r.height + " at (" + r.x + ", " + r.y + "), and the part outside it is clipped";
  }

  static String overlap(SkComponent c, int i){
    if (!(c.w instanceof _Border)){ return null; }
    var k= c.getComponent(i);
    for (int j= 0; j < i; j += 1){
      var o= c.getComponent(j);
      if (!k.getBounds().intersects(o.getBounds())){ continue; }
      return ((SkComponent) k).w.describe() + " of " + k.getWidth() + "x" + k.getHeight() + " at (" + k.getX() + ", " + k.getY() + ") overlaps " + ((SkComponent) o).w.describe() + " of " + o.getWidth() + "x" + o.getHeight() + " at (" + o.getX() + ", " + o.getY() + ") in their parent, " + c.w.describe() + " of " + c.getWidth() + "x" + c.getHeight() + ": the first one covers the second";
    }
    return null;
  }

  static Dimension need(Line line, double above, double below){
    var ink= ink(line);
    double w= line.width();
    long h= lineHeight(line.size(), above, below);
    double a= line.size() * above;
    double overX= Math.max(Math.max(0, -ink.getLeft()), ink.getRight() - w);
    double overY= Math.max(Math.max(0, -ink.getTop() - a), ink.getBottom() + a - h);
    return new Dimension((int) Math.ceil(w + 2 * overX), (int) Math.ceil(h + 2 * overY));
  }

  static double baseline(float h, int size, double above, double below){ return (h - lineHeight(size, above, below)) / 2d + size * above; }

  static void draw(Canvas cv, Line line, float x, float y, float w, float h, double above, double below, Paint p){
    drawAt(cv, line, x + (w - line.width()) / 2, y + baseline(h, line.size(), above, below), p);
  }

  static void drawAt(Canvas cv, Line line, double x, double y, Paint p){
    int fg= p.getColor();
    for (var g : line.glyphs()){
      float k= (float) ((double) line.size() / upems[g.font()]);
      cv.save();
      cv.translate((float) (x + g.x() + g.dx()), (float) (y + g.dy()));
      cv.scale(k, k);
      for (var l : glyph(g.font(), g.glyph()).layers()){
        p.setColor(l.foreground() ? fg : l.color());
        cv.drawPath(l.path(), p);
      }
      cv.restore();
    }
    p.setColor(fg);
  }

  static int roomWidth(String text, int size, double above, double below){ return need(shape(text, size), above, below).width; }

  static ArrayList<Integer> wrap(String s, int size, double above, double below, int ww){
    var res= new ArrayList<Integer>();
    var words= BreakIterator.getLineInstance(Locale.ROOT);
    words.setText(s);
    var clusters= BreakIterator.getCharacterInstance(Locale.ROOT);
    clusters.setText(s);
    var whole= shape(s, size);
    for (int from= 0; from < s.length();){
      int to= fit(s, whole, words, from, size, above, below, ww);
      if (to == from){ to = fit(s, whole, clusters, from, size, above, below, ww); }
      if (to == from){ to = clusters.following(from); }
      while (to < s.length() && Character.isWhitespace(s.charAt(to))){ to += 1; }
      res.add(to);
      from = to;
    }
    if (res.isEmpty()){ res.add(0); }
    return res;
  }

  private static int fit(String s, Line whole, BreakIterator b, int from, int size, double above, double below, int ww){
    double x0= whole.coord(from);
    var fits= new ArrayList<Integer>();
    for (int at= b.following(from); at != BreakIterator.DONE; at = b.next()){
      if (whole.coord(trimmed(s, from, at)) - x0 > ww){ break; }
      fits.add(at);
    }
    for (int i= fits.size() - 1; i >= 0; i -= 1){
      if (roomWidth(s.substring(from, trimmed(s, from, fits.get(i))), size, above, below) <= ww){ return fits.get(i); }
    }
    return from;
  }

  static int trimmed(String s, int from, int to){
    while (to > from && Character.isWhitespace(s.charAt(to - 1))){ to -= 1; }
    return to;
  }

  static Dimension textSizeWithInsets(AWidget s){
    var need= need(line(s), s.above, s.below);
    return new Dimension(need.width + s.left + s.right, need.height + s.top + s.bottom);
  }

  static void text(Canvas cv, AWidget s, float dx, float dy){
    var c= s.component;
    var line= line(s);
    int x0= s.left;
    int y0= s.top;
    int cw= c.getWidth() - s.left - s.right;
    int ch= c.getHeight() - s.top - s.bottom;
    if (cw <= 0 || ch <= 0){ return; }
    int save= cv.save();
    cv.clipRect(Rect.makeXYWH(x0 + dx, y0 + dy, cw, ch));
    paint.setColor(s.fg);
    draw(cv, line, x0 + dx, y0 + dy, cw, ch, s.above, s.below, paint);
    cv.restoreToCount(save);
  }

  static void button(Canvas cv, _Button s){
    var b= s.component;
    int w= b.getWidth(), h= b.getHeight();
    if (w <= 0 || h <= 0){ return; }
    boolean down= s.down;
    boolean over= s.over && !down;
    float r= Math.min(s.radius, Math.min(w, h) / 2f);
    int d= bevel(w, h, (int) r, s);
    int center= baseColor(s.bg, over, down);
    int light= mix(center, 0xFFFFFFFF, 45);
    int dark= mix(center, 0xFF000000, 45);
    var outer= RRect.makeXYWH(0, 0, w, h, r);
    paint.setColor(center);
    cv.drawRRect(outer, paint);
    if (d > 0){
      // The bevel paths depend only on (w, h, radius, d) and are cached on
      // the button: a stable button costs zero path allocations per frame.
      if (s.bevelW != w || s.bevelH != h || s.bevelR != s.radius || s.bevelD != d){
        if (s.bevelTl != null){ s.bevelTl.close(); s.bevelBr.close(); }
        Path o= Path.makeRRect(outer);
        Path i= Path.makeRRect(RRect.makeXYWH(d, d, w - 2f * d, h - 2f * d, Math.max(0, r - d)));
        var pb= new PathBuilder();
        pb.moveTo(0, h);
        pb.lineTo(w, 0);
        pb.lineTo(0, 0);
        pb.closePath();
        Path diag= pb.build();
        Path ring= Path.makeCombining(o, i, PathOp.DIFFERENCE);
        s.bevelTl = Path.makeCombining(ring, diag, PathOp.INTERSECT);
        s.bevelBr = Path.makeCombining(ring, diag, PathOp.DIFFERENCE);
        assert ring != null && s.bevelTl != null && s.bevelBr != null;
        for (Path p : new Path[]{ o, i, diag, ring }){ p.close(); }
        s.bevelW = w;
        s.bevelH = h;
        s.bevelR = s.radius;
        s.bevelD = d;
      }
      paint.setColor(down ? dark : light);
      cv.drawPath(s.bevelTl, paint);
      paint.setColor(down ? light : dark);
      cv.drawPath(s.bevelBr, paint);
    }
    int shift= down && d > 0 ? Math.max(1, d / 2) : 0;
    text(cv, s, shift, shift);
  }

  private static int bevel(int w, int h, int r, AWidget s){
    int d= Math.max(3, Math.min(8, Math.min(w, h) / 9));
    d = Math.min(d, Math.min(Math.min(s.left, s.right), Math.min(s.top, s.bottom)));
    d = Math.min(d, Math.min(w, h) / 2);
    return r > 0 ? Math.min(d, r) : d;
  }

  private static int baseColor(int c, boolean over, boolean down){
    if (over){ c = mix(c, 0xFFFFFFFF, 12); }
    if (down){ c = mix(c, 0xFF000000, 16); }
    return c;
  }

  private static int mix(int a, int b, int p){
    int res= 0;
    for (int s= 0; s < 32; s += 8){ res |= ((a >>> s & 255) * (100 - p) + (b >>> s & 255) * p) / 100 << s; }
    return res;
  }

  static Image scaled(Image src, int w, int h){
    var s= src;
    while (s.getWidth() / 2 >= w && s.getHeight() / 2 >= h){
      var t= pass(s, s.getWidth() / 2, s.getHeight() / 2, SamplingMode.LINEAR);
      if (s != src){ s.close(); }
      s = t;
    }
    var res= pass(s, w, h, SamplingMode.MITCHELL);
    if (s != src){ s.close(); }
    return res;
  }

  static Image pass(Image src, int w, int h, SamplingMode m){
    try (var surf= Surface.makeRaster(ImageInfo.makeN32Premul(w, h))){
      surf.getCanvas().drawImageRect(
        src, Rect.makeWH(src.getWidth(), src.getHeight()), Rect.makeWH(w, h), m, null, true);
      return surf.makeImageSnapshot();
    }
  }
}
