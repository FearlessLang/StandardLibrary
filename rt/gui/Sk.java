package _base;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Data;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontEdging;
import io.github.humbleui.skija.FontHinting;
import io.github.humbleui.skija.FontMgr;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Path;
import io.github.humbleui.skija.PathBuilder;
import io.github.humbleui.skija.PathOp;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.TextLine;
import io.github.humbleui.skija.Typeface;
import io.github.humbleui.skija.shaper.FontRun;
import io.github.humbleui.skija.shaper.HbIcuScriptRunIterator;
import io.github.humbleui.skija.shaper.IcuBidiRunIterator;
import io.github.humbleui.skija.shaper.RunHandler;
import io.github.humbleui.skija.shaper.RunInfo;
import io.github.humbleui.skija.shaper.Shaper;
import io.github.humbleui.skija.shaper.ShapingOptions;
import io.github.humbleui.skija.shaper.TextLineRunHandler;
import io.github.humbleui.skija.shaper.TrivialLanguageRunIterator;
import io.github.humbleui.types.Point;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.IOException;
import java.io.UncheckedIOException;
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
  Typeface[] typefaces= new Typeface[fontFiles.length];
  HashMap<Long,Font> fonts= new HashMap<>();
  HashMap<Integer,Integer> fontByCp= new HashMap<>();
  Shaper shaper= Shaper.makeShaperDrivenWrapper();

  static Typeface typeface(int i){
    if (typefaces[i] == null){ typefaces[i] = FontMgr.getDefault().makeFromData(Data.makeFromBytes(fontBytes(fontFiles[i]))); }
    return typefaces[i];
  }

  static byte[] fontBytes(String name){
    try (var in= Sk.class.getResourceAsStream(name)){ return in.readAllBytes(); }
    catch (IOException e){ throw new UncheckedIOException(e); }
  }

  static Font font(int i, int size){
    return fonts.computeIfAbsent((long) i << 32 | size, _ -> new Font(typeface(i), size)
      .setSubpixel(true)
      .setEdging(FontEdging.ANTI_ALIAS)
      .setHinting(FontHinting.NONE));
  }

  static int fontOf(int cp, boolean emojiFirst){
    return fontByCp.computeIfAbsent(cp << 1 | (emojiFirst ? 1 : 0), _ -> search(cp, emojiFirst));
  }

  static int search(int cp, boolean emojiFirst){
    if (emojiFirst && typeface(emoji).getUTF32Glyph(cp) != 0){ return emoji; }
    for (int i= 0; i < fontFiles.length; i += 1){
      if (typeface(i).getUTF32Glyph(cp) != 0){ return i; }
    }
    return 0;
  }

  static boolean sticky(int cp){
    int t= Character.getType(cp);
    return t == Character.NON_SPACING_MARK || t == Character.ENCLOSING_MARK
      || t == Character.COMBINING_SPACING_MARK || t == Character.FORMAT || Character.isEmojiModifier(cp);
  }

  static TextLine shape(String text, int size){
    try (var handler= new TextLineRunHandler(text)){ shape(text, size, handler); return handler.makeLine(); }
  }

  static Rect ink(String text, int size){
    var handler= new RunHandler(){
      float x;
      Rect ink= Rect.makeWH(0, 0);
      @Override public void beginLine(){}
      @Override public void runInfo(RunInfo info){}
      @Override public void commitRunInfo(){}
      @Override public Point runOffset(RunInfo info){ return new Point(x, 0); }
      @Override public void commitRun(RunInfo info, short[] glyphs, Point[] at, int[] clusters){
        var bounds= info.getFont().getBounds(glyphs);
        for (int i= 0; i < glyphs.length; i += 1){
          if (bounds[i].isEmpty()){ continue; }
          var b= bounds[i].offset(at[i]);
          ink = Rect.makeLTRB(Math.min(ink.getLeft(), b.getLeft()), Math.min(ink.getTop(), b.getTop()), Math.max(ink.getRight(), b.getRight()), Math.max(ink.getBottom(), b.getBottom()));
        }
        x += info.getAdvanceX();
      }
      @Override public void commitLine(){}
    };
    shape(text, size, handler);
    return handler.ink;
  }

  static void shape(String text, int size, RunHandler handler){
    synchronized (shaper){
      var runs= new ArrayList<FontRun>();
      int font= -1;
      for (int i= 0, prev= -1; i < text.length();){
        int cp= text.codePointAt(i);
        int next= i + Character.charCount(cp);
        if (font < 0 || !(sticky(cp) || prev == 0x200D)){
          int nx= next < text.length() ? text.codePointAt(next) : -1;
          int f= fontOf(cp, nx == 0xFE0F || Character.isEmojiPresentation(cp));
          if (font >= 0 && f != font){ runs.add(new FontRun(i, font(font, size))); }
          font = f;
        }
        prev = cp;
        i = next;
      }
      if (font >= 0){ runs.add(new FontRun(text.length(), font(font, size))); }
      try (var bidi= new IcuBidiRunIterator(text, 0xFE); var script= new HbIcuScriptRunIterator(text)){
        shaper.shape(text, runs.iterator(), bidi, script, new TrivialLanguageRunIterator(text, "en"), ShapingOptions.DEFAULT, Float.POSITIVE_INFINITY, handler);
      }
    }
  }

  static TextLine line(AWidget s){
    if (s.line != null){ return s.line; }
    s.line = shape(s.text, s.textSize);
    s.ink = ink(s.text, s.textSize);
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

  record Tip(String text, List<TextLine> lines, List<Dimension> needs, int width, int height) implements AutoCloseable{
    @Override public void close(){ lines.forEach(TextLine::close); }
  }

  static Tip tip(String text){
    var texts= text.split("\n", -1);
    var lines= Arrays.stream(texts).map(l -> shape(l, 12)).toList();
    var needs= new ArrayList<Dimension>();
    for (int i= 0; i < texts.length; i += 1){ needs.add(need(lines.get(i), ink(texts[i], 12), 12, 1, 0.5)); }
    int bw= needs.stream().mapToInt(d -> d.width).max().orElseThrow() + 10;
    int bh= needs.stream().mapToInt(d -> d.height).sum() + 6;
    return new Tip(text, lines, List.copyOf(needs), bw, bh);
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
      draw(cv, lines.get(i), x + 5, ly, n.width, n.height, 12, 1, 0.5, paint);
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

  static Dimension need(TextLine line, Rect ink, int size, double above, double below){
    float w= line.getWidth();
    long h= lineHeight(size, above, below);
    double a= size * above;
    double overX= Math.max(Math.max(0, -ink.getLeft()), ink.getRight() - w);
    double overY= Math.max(Math.max(0, -ink.getTop() - a), ink.getBottom() + a - h);
    return new Dimension((int) Math.ceil(w + 2 * overX), (int) Math.ceil(h + 2 * overY));
  }

  static double baseline(float h, int size, double above, double below){ return (h - lineHeight(size, above, below)) / 2d + size * above; }

  static void draw(Canvas cv, TextLine line, float x, float y, float w, float h, int size, double above, double below, Paint p){
    cv.drawTextLine(line, x + (w - line.getWidth()) / 2, (float) (y + baseline(h, size, above, below)), p);
  }

  static int roomWidth(String text, int size, double above, double below){
    try (var line= shape(text, size)){ return need(line, ink(text, size), size, above, below).width; }
  }

  static ArrayList<Integer> wrap(String s, int size, double above, double below, int ww){
    var res= new ArrayList<Integer>();
    var words= BreakIterator.getLineInstance(Locale.ROOT);
    words.setText(s);
    var clusters= BreakIterator.getCharacterInstance(Locale.ROOT);
    clusters.setText(s);
    try (var whole= shape(s, size)){
      for (int from= 0; from < s.length();){
        int to= fit(s, whole, words, from, size, above, below, ww);
        if (to == from){ to = fit(s, whole, clusters, from, size, above, below, ww); }
        if (to == from){ to = clusters.following(from); }
        res.add(to);
        from = to;
      }
    }
    if (res.isEmpty()){ res.add(0); }
    return res;
  }

  private static int fit(String s, TextLine whole, BreakIterator b, int from, int size, double above, double below, int ww){
    float x0= whole.getCoordAtOffset(from);
    var fits= new ArrayList<Integer>();
    for (int at= b.following(from); at != BreakIterator.DONE; at = b.next()){
      if (whole.getCoordAtOffset(trimmed(s, from, at)) - x0 > ww){ break; }
      fits.add(at);
    }
    for (int i= fits.size() - 1; i >= 0; i -= 1){
      if (roomWidth(s.substring(from, trimmed(s, from, fits.get(i))), size, above, below) <= ww){ return fits.get(i); }
    }
    return from;
  }

  static int trimmed(String s, int from, int to){
    while (to > from && s.charAt(to - 1) == ' '){ to -= 1; }
    return to;
  }

  static Dimension textSizeWithInsets(AWidget s){
    var need= need(line(s), s.ink, s.textSize, s.above, s.below);
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
    draw(cv, line, x0 + dx, y0 + dy, cw, ch, s.textSize, s.above, s.below, paint);
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
