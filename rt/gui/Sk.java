package _base;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.FilterTileMode;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.Matrix33;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.types.Rect;
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
  ThreadLocal<float[]> matrix= ThreadLocal.withInitial(() -> new float[]{ 1, 0, 0, 0, 1, 0, 0, 0, 1 });
  ThreadLocal<float[]> radius= ThreadLocal.withInitial(() -> new float[1]);
  record Glyph(List<Hb.Layer> layers, Rect box){}
  record Run(int from, int to, int font, int level, int script){}
  record G(int font, Glyph glyph, int cluster, boolean rtl, double x, double advance, double dx, double dy){}
  record TextSettings(int size, double above, double below){
    static final TextSettings label= new TextSettings(12, 1, 0.5);
    long lineHeight(){ return Math.round(size * (above + below)); }
    double baseline(float h){ return (h - lineHeight()) / 2d + size * above; }
  }
  record Line(String text, TextSettings settings, List<G> glyphs, double width, boolean rtl, Rect ink){
    float[] edges(){
      int n= text.length();
      var l= new double[n + 1];
      var r= new double[n + 1];
      var back= new boolean[n + 1];
      Arrays.fill(l, Double.POSITIVE_INFINITY);
      Arrays.fill(r, Double.NEGATIVE_INFINITY);
      for (var g : glyphs){
        int c= g.cluster();
        l[c] = Math.min(l[c], g.x());
        r[c] = Math.max(r[c], g.x() + g.advance());
        back[c] = g.rtl();
      }
      var b= BreakIterator.getCharacterInstance(Locale.ROOT);
      b.setText(text);
      var res= new float[2 * n];
      Arrays.fill(res, Float.NaN);
      for (int u= 0, q; u < n; u = q){
        int m= 1;
        q = b.following(u);
        while (q < n && l[q] == Double.POSITIVE_INFINITY){ q = b.following(q); m += 1; }
        double lo= Double.POSITIVE_INFINITY;
        double hi= Double.NEGATIVE_INFINITY;
        for (int i= u; i < q; i += 1){ lo = Math.min(lo, l[i]); hi = Math.max(hi, r[i]); }
        double w= (back[u] ? lo - hi : hi - lo) / m;
        double x= back[u] ? hi : lo;
        for (int p= u, k= 0; p < q; p = b.following(p), k += 1){
          res[2 * p] = (float) (x + k * w);
          res[2 * p + 1] = (float) (x + (k + 1) * w);
        }
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

  static Line shape(String text, TextSettings settings){ return shape(text, settings, new Bidi(text, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT)); }

  static Line shape(String text, TextSettings settings, Bidi bidi){
    if (text.isEmpty()){ return new Line(text, settings, List.of(), 0, false, Rect.makeWH(0, 0)); }
    var shaped= text.replace('\t', ' ');
    synchronized (fonts){
      var runs= new ArrayList<Run>();
      int from= 0, font= -1, level= 0, script= Hb.common;
      for (int i= 0, prev= -1; i < text.length();){
        int cp= shaped.codePointAt(i);
        int next= i + Character.charCount(cp);
        int f= font;
        if (font < 0 || !(sticky(cp) || prev == 0x200D)){
          int nx= next < text.length() ? shaped.codePointAt(next) : -1;
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
        double k= (double) settings.size() / upems[r.font()];
        boolean rtl= (r.level() & 1) != 0;
        for (var g : Hb.shape(fonts[r.font()], shaped, r.from(), r.to(), rtl, r.script())){
          res.add(new G(r.font(), glyph(r.font(), g.glyph()), g.cluster(), rtl, x, g.advance() * k, g.dx() * k, -g.dy() * k));
          x += g.advance() * k;
        }
      }
      return new Line(text, settings, List.copyOf(res), x, !bidi.baseIsLeftToRight(), ink(res, settings));
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

  static Rect ink(List<G> glyphs, TextSettings settings){
    var res= Rect.makeWH(0, 0);
    for (var g : glyphs){
      var b= g.glyph().box();
      if (b == null){ continue; }
      double k= (double) settings.size() / upems[g.font()];
      double x= g.x() + g.dx();
      res = union(res, Rect.makeLTRB((float) (x + b.getLeft() * k), (float) (g.dy() + b.getTop() * k), (float) (x + b.getRight() * k), (float) (g.dy() + b.getBottom() * k)));
    }
    return res;
  }

  static Line line(AWidget s){
    if (s.line == null){ s.line = shape(s.text, s.textSettings); }
    return s.line;
  }


  static int color(Color$1c$0 c){
    return alpha(c.read$alpha$0()) << 24 | red(c.read$red$0()) << 16
      | green(c.read$green$0()) << 8 | blue(c.read$blue$0());
  }

  static void paintNode(SkComponent c, Canvas cv){
    c.w.textWarning = c.w.warned(c.w.textWarning, c.w.textClip(c.w.textWarning));
    c.w.sk(cv);
    for (int i= 0; i < c.getComponentCount(); i += 1){
      var k= c.getComponent(i);
      if (c.w.hides(k)){ continue; }
      var kw= ((SkComponent) k).w;
      kw.boundsWarning = kw.warned(kw.boundsWarning, boundsClip(c, k, kw.boundsWarning));
      kw.overlapWarning = kw.warned(kw.overlapWarning, overlap(c, i, kw.overlapWarning));
      int save= cv.save();
      var r= c.w.clip(k);
      if (r != null){ clip(cv, r.x, r.y, r.width, r.height); }
      at(cv, k.getX(), k.getY(), 1);
      clip(cv, 0, 0, k.getWidth(), k.getHeight());
      paintNode((SkComponent) k, cv);
      cv.restoreToCount(save);
    }
    c.w.skOver(cv);
    bars(cv, c.w);
  }

  static void bars(Canvas cv, AWidget s){
    if (s.barRight()){ bar(cv, s.track(true), s.thumb(true), s.fg, s.maxScrollY() == 0); }
    if (s.barBottom()){ bar(cv, s.track(false), s.thumb(false), s.fg, s.maxScrollX() == 0); }
  }

  static void bar(Canvas cv, java.awt.Rectangle t, java.awt.Rectangle m, int rgb, boolean fits){
    paint.setColor(rgb & 0xFFFFFF | 0x30000000);
    rrect(cv, t.x, t.y, t.width, t.height, AWidget.bar / 2f, paint);
    paint.setColor(rgb & 0xFFFFFF | (fits ? 0x30000000 : 0xA0000000));
    rrect(cv, m.x, m.y, m.width, m.height, AWidget.bar / 2f, paint);
  }

  record Tip(String text, List<Line> lines, List<Dimension> needs, int width, int height){}

  static Tip tip(String text){
    var lines= Arrays.stream(text.split("\n", -1)).map(l -> shape(l, TextSettings.label)).toList();
    var needs= lines.stream().map(Sk::need).toList();
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
      draw(cv, lines.get(i), x + 5, ly, n.width, n.height, paint);
      ly += n.height;
    }
  }

  static void background(Canvas cv, AWidget s){
    if (s.bg >>> 24 == 0){ return; }
    float w= s.component.getWidth();
    float h= s.component.getHeight();
    paint.setColor(s.bg);
    rrect(cv, 0, 0, w, h, Math.min(s.radius, Math.min(w, h) / 2), paint);
  }

  static String boundsClip(SkComponent c, Component k, String warning){
    var r= c.w.region(k);
    if (r != null){ return regionClip(c.w, k, r, warning); }
    boolean scrollsX= c.w.barBottom();
    boolean scrollsY= c.w.barRight();
    boolean insideX= scrollsX || k.getX() >= 0 && k.getX() + k.getWidth() <= c.getWidth();
    boolean insideY= scrollsY || k.getY() >= 0 && k.getY() + k.getHeight() <= c.getHeight();
    boolean inside= insideX && insideY;
    if (inside){ return null; }
    if (warning != null){ return warning; }
    return ((SkComponent) k).w.describe() + " of " + k.getWidth() + "x" + k.getHeight() + " at (" + k.getX() + ", " + k.getY() + ") does not fit in its parent, " + c.w.describe() + " of " + c.getWidth() + "x" + c.getHeight() + ": the part outside the parent is clipped";
  }

  static String regionClip(AWidget s, Component k, java.awt.Rectangle r, String warning){
    boolean inside= k.getX() >= r.x && k.getY() >= r.y && k.getX() + k.getWidth() <= r.x + r.width && k.getY() + k.getHeight() <= r.y + r.height;
    if (inside){ return null; }
    if (warning != null){ return warning; }
    var which= s instanceof _Split ? "region at index " + s.component.getComponentZOrder(k) : "content area";
    return ((SkComponent) k).w.describe() + " of " + k.getWidth() + "x" + k.getHeight() + " at (" + k.getX() + ", " + k.getY() + ") does not fit in the " + which + " of its parent, " + s.describe() + ": the " + which + " is " + r.width + "x" + r.height + " at (" + r.x + ", " + r.y + "), and the part outside it is clipped";
  }

  static String overlap(SkComponent c, int i, String warning){
    if (!(c.w instanceof _Border)){ return null; }
    var k= c.getComponent(i);
    for (int j= 0; j < i; j += 1){
      var o= c.getComponent(j);
      if (!k.getBounds().intersects(o.getBounds())){ continue; }
      if (warning != null){ return warning; }
      return ((SkComponent) k).w.describe() + " of " + k.getWidth() + "x" + k.getHeight() + " at (" + k.getX() + ", " + k.getY() + ") overlaps " + ((SkComponent) o).w.describe() + " of " + o.getWidth() + "x" + o.getHeight() + " at (" + o.getX() + ", " + o.getY() + ") in their parent, " + c.w.describe() + " of " + c.getWidth() + "x" + c.getHeight() + ": the first one covers the second";
    }
    return null;
  }

  static Dimension need(Line line){
    var t= line.settings();
    var ink= line.ink();
    double w= line.width();
    long h= t.lineHeight();
    double a= t.size() * t.above();
    double overX= Math.max(Math.max(0, -ink.getLeft()), ink.getRight() - w);
    double overY= Math.max(Math.max(0, -ink.getTop() - a), ink.getBottom() + a - h);
    return new Dimension((int) Math.ceil(w + 2 * overX), (int) Math.ceil(h + 2 * overY));
  }

  static void draw(Canvas cv, Line line, float x, float y, float w, float h, Paint p){
    drawAt(cv, line, x + (w - line.width()) / 2, y + line.settings().baseline(h), p);
  }

  static void drawAt(Canvas cv, Line line, double x, double y, Paint p){ drawAt(cv, line, x, y, p, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY); }

  static void drawAt(Canvas cv, Line line, double x, double y, Paint p, double left, double right){
    int fg= p.getColor();
    var gs= line.glyphs();
    for (int i= 0; i < gs.size(); i += 1){
      var g= gs.get(i);
      var b= g.glyph().box();
      float k= (float) ((double) line.settings().size() / upems[g.font()]);
      double gx= x + g.x() + g.dx();
      if (b == null || gx + b.getRight() * k < left || gx + b.getLeft() * k > right){ continue; }
      cv.save();
      at(cv, gx, y + g.dy(), k);
      var ls= g.glyph().layers();
      for (int j= 0; j < ls.size(); j += 1){
        var l= ls.get(j);
        p.setColor(l.foreground() ? fg : l.color());
        cv.drawPath(l.path(), p);
      }
      cv.restore();
    }
    p.setColor(fg);
  }

  static void at(Canvas cv, double x, double y, float k){
    var m= matrix.get();
    m[0] = k;
    m[2] = (float) x;
    m[4] = k;
    m[5] = (float) y;
    Canvas._nConcat(cv._ptr, m);
  }

  static void clip(Canvas cv, float x, float y, float w, float h){ Canvas._nClipRect(cv._ptr, x, y, x + w, y + h, ClipMode.INTERSECT.ordinal(), false); }

  static void clip(Canvas cv, float x, float y, float w, float h, float r){
    var a= radius.get();
    a[0] = r;
    Canvas._nClipRRect(cv._ptr, x, y, x + w, y + h, a, ClipMode.INTERSECT.ordinal(), true);
  }

  static void rect(Canvas cv, float x, float y, float w, float h, Paint p){ Canvas._nDrawRect(cv._ptr, x, y, x + w, y + h, p._ptr); }

  static void rrect(Canvas cv, float x, float y, float w, float h, float r, Paint p){
    var a= radius.get();
    a[0] = r;
    Canvas._nDrawRRect(cv._ptr, x, y, x + w, y + h, a, p._ptr);
  }

  static ArrayList<Integer> wrap(String s, TextSettings settings, int ww){
    var res= new ArrayList<Integer>();
    var words= BreakIterator.getLineInstance(Locale.ROOT);
    words.setText(s);
    var clusters= BreakIterator.getCharacterInstance(Locale.ROOT);
    clusters.setText(s);
    var advances= new double[s.length() + 1];
    for (var g : shape(s, settings).glyphs()){ advances[g.cluster() + 1] += g.advance(); }
    for (int i= 1; i < advances.length; i += 1){ advances[i] += advances[i - 1]; }
    for (int from= 0; from < s.length();){
      int to= fit(s, settings, advances, words, from, ww);
      if (to == from){ to = fit(s, settings, advances, clusters, from, ww); }
      if (to == from){ to = clusters.following(from); }
      while (to < s.length() && space(s.charAt(to))){ to += 1; }
      res.add(to);
      from = to;
    }
    if (res.isEmpty()){ res.add(0); }
    return res;
  }

  private static int fit(String s, TextSettings settings, double[] advances, BreakIterator b, int from, int ww){
    var fits= new ArrayList<Integer>();
    for (int at= b.following(from); at != BreakIterator.DONE; at = b.next()){
      if (advances[trimmed(s, from, at)] - advances[from] > ww){ break; }
      fits.add(at);
    }
    for (int i= fits.size() - 1; i >= 0; i -= 1){
      if (need(shape(s.substring(from, trimmed(s, from, fits.get(i))), settings)).width <= ww){ return fits.get(i); }
    }
    return from;
  }

  static int trimmed(CharSequence s, int from, int to){
    while (to > from && space(s.charAt(to - 1))){ to -= 1; }
    return to;
  }
  static boolean space(char c){ return c == '\t' || Character.getType(c) == Character.SPACE_SEPARATOR && Character.isWhitespace(c); }

  static Dimension textSizeWithInsets(AWidget s){
    var need= need(line(s));
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
    clip(cv, x0 + dx, y0 + dy, cw, ch);
    paint.setColor(s.fg);
    draw(cv, line, x0 + dx, y0 + dy, cw, ch, paint);
    cv.restoreToCount(save);
  }

  static Image scaled(Image src, int w, int h){
    var s= halved(src, w, h);
    var res= pass(s, w, h, SamplingMode.CATMULL_ROM);
    if (s != src){ s.close(); }
    return res;
  }

  static Image halved(Image src, int w, int h){
    var s= src;
    while (s.getWidth() / 2 >= w || s.getHeight() / 2 >= h){
      var t= pass(s, half(s.getWidth(), w), half(s.getHeight(), h), SamplingMode.LINEAR);
      if (s != src){ s.close(); }
      s = t;
    }
    return s;
  }

  static int halvedSide(int side, int goal){ return side / 2 >= goal ? halvedSide(side / 2, goal) : side; }

  private static int half(int side, int goal){ return side / 2 >= goal ? side / 2 : side; }

  final class Kept{
    Image image;
    Rect to;
    final Paint paint= new Paint().setAntiAlias(false);
  }

  static void image(Canvas cv, Image src, Kept k, Rect to, Rect room){
    var m= Canvas._nGetLocalToDevice(cv._ptr);
    var w= Math.max(1, Math.round(to.getWidth() * m[0]));
    var h= Math.max(1, Math.round(to.getHeight() * m[5]));
    var fits= k.image != null && k.image.getWidth() == halvedSide(src.getWidth(), w) && k.image.getHeight() == halvedSide(src.getHeight(), h);
    if (!fits){
      if (k.image != null && k.image != src){ k.image.close(); }
      k.image = halved(src, w, h);
      k.to = null;
    }
    if (!to.equals(k.to)){
      k.to = to;
      var fit= new Matrix33(to.getWidth() / k.image.getWidth(), 0, to.getLeft(), 0, to.getHeight() / k.image.getHeight(), to.getTop(), 0, 0, 1);
      try (var sh= k.image.makeShader(FilterTileMode.CLAMP, FilterTileMode.CLAMP, SamplingMode.LINEAR, fit)){ k.paint.setShader(sh); }
    }
    cv.drawRect(room, k.paint);
  }

  static Image pass(Image src, int w, int h, SamplingMode m){
    try (var surf= Surface.makeRaster(ImageInfo.makeN32Premul(w, h))){
      surf.getCanvas().drawImageRect(
        src, Rect.makeWH(src.getWidth(), src.getHeight()), Rect.makeWH(w, h), m, null, true);
      return surf.makeImageSnapshot();
    }
  }
}
