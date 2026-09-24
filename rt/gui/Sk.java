package base;

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
import io.github.humbleui.skija.shaper.Shaper;
import io.github.humbleui.skija.shaper.ShapingOptions;
import io.github.humbleui.skija.shaper.TextLineRunHandler;
import io.github.humbleui.skija.shaper.TrivialLanguageRunIterator;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.awt.Dimension;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import static base.Scopes.*;

interface Sk{
  Paint paint = new Paint().setAntiAlias(true);
  // Lookup order. The script fonts must precede Math: it has isolated Arabic
  // letters, and would win them from the Arabic font, which joins them.
  String[] fontFiles = {
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
  int emoji = fontFiles.length - 1;
  Typeface[] typefaces = new Typeface[fontFiles.length];
  HashMap<Long, Font> fonts = new HashMap<>();
  HashMap<Integer, Integer> fontByCp = new HashMap<>();
  Shaper shaper = Shaper.makeShaperDrivenWrapper();

  static Typeface typeface(int i){
    if (typefaces[i] == null){ typefaces[i] = FontMgr.getDefault().makeFromData(Data.makeFromBytes(fontBytes(fontFiles[i]))); }
    return typefaces[i];
  }

  static byte[] fontBytes(String name){
    try (var in = Sk.class.getResourceAsStream(name)){ return in.readAllBytes(); }
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
    for (int i = 0; i < fontFiles.length; i++){
      if (typeface(i).getUTF32Glyph(cp) != 0){ return i; }
    }
    return 0;
  }

  static boolean sticky(int cp){
    int t = Character.getType(cp);
    return t == Character.NON_SPACING_MARK || t == Character.ENCLOSING_MARK
      || t == Character.COMBINING_SPACING_MARK || t == Character.FORMAT || Character.isEmojiModifier(cp);
  }

  static TextLine shape(String text, int size){
    var runs = new ArrayList<FontRun>();
    int font = -1;
    for (int i = 0, prev = -1; i < text.length();){
      int cp = text.codePointAt(i);
      int next = i + Character.charCount(cp);
      if (font < 0 || !(sticky(cp) || prev == 0x200D)){
        int nx = next < text.length() ? text.codePointAt(next) : -1;
        int f = fontOf(cp, nx == 0xFE0F || Character.isEmojiPresentation(cp));
        if (font >= 0 && f != font){ runs.add(new FontRun(i, font(font, size))); }
        font = f;
      }
      prev = cp;
      i = next;
    }
    if (font >= 0){ runs.add(new FontRun(text.length(), font(font, size))); }
    try (var handler = new TextLineRunHandler(text); var bidi = new IcuBidiRunIterator(text, 0xFE); var script = new HbIcuScriptRunIterator(text)){
      shaper.shape(text, runs.iterator(), bidi, script, new TrivialLanguageRunIterator(text, "en"), ShapingOptions.DEFAULT, Float.POSITIVE_INFINITY, handler);
      return handler.makeLine();
    }
  }

  static TextLine line(AWidget s){
    var key = h(s.textSize) + " " + s.text;
    if (!key.equals(s.lineKey)){
      if (s.line != null){ s.line.close(); }
      s.line = shape(s.text, h(s.textSize));
      s.lineKey = key;
    }
    return s.line;
  }

  static int color(Color$1c$0 c){
    return alpha(c.read$alpha$0()) << 24 | red(c.read$red$0()) << 16
      | green(c.read$green$0()) << 8 | blue(c.read$blue$0());
  }

  static void paintNode(SkComponent c, Canvas cv){
    c.w.sk(cv);
    for (var k : c.getComponents()){
      int save = cv.save();
      cv.translate(k.getX(), k.getY());
      cv.clipRect(Rect.makeWH(k.getWidth(), k.getHeight()));
      paintNode((SkComponent) k, cv);
      cv.restoreToCount(save);
    }
  }

  static void background(Canvas cv, AWidget s){
    int col = color(s.background);
    if (col >>> 24 == 0){ return; }
    float w = s.component.getWidth();
    float h = s.component.getHeight();
    paint.setColor(col);
    cv.drawRRect(RRect.makeXYWH(0, 0, w, h, Math.min(n(s.radius), Math.min(w, h) / 2)), paint);
  }

  static Dimension textSizeWithInsets(AWidget s){
    return new Dimension(
      (int) Math.ceil(line(s).getWidth()) + w(s.left) + w(s.right),
      (int) Math.ceil(font(0, h(s.textSize)).getMetrics().getHeight()) + h(s.top) + h(s.bottom));
  }

  static Dimension preferred(Dimension auto, AWidget s){
    return new Dimension(
      s.preferredWidth == null ? auto.width : w(s.preferredWidth),
      s.preferredHeight == null ? auto.height : h(s.preferredHeight));
  }

  static void text(Canvas cv, AWidget s, float dx, float dy){
    var c = s.component;
    var line = line(s);
    var fm = font(0, h(s.textSize)).getMetrics();
    float textH = fm.getHeight();
    int x0 = w(s.left);
    int y0 = h(s.top);
    int cw = c.getWidth() - w(s.left) - w(s.right);
    int ch = c.getHeight() - h(s.top) - h(s.bottom);
    if (cw <= 0 || ch <= 0){ return; }
    int save = cv.save();
    cv.clipRect(Rect.makeXYWH(x0 + dx, y0 + dy, cw, ch));
    paint.setColor(color(s.foreground));
    cv.drawTextLine(line, x0 + (cw - line.getWidth()) / 2 + dx, y0 + (ch - textH) / 2 - fm.getAscent() + dy, paint);
    cv.restoreToCount(save);
  }

  static void button(Canvas cv, _Button s){
    var b = s.component;
    int w = b.getWidth(), h = b.getHeight();
    if (w <= 0 || h <= 0){ return; }
    boolean down = s.down;
    boolean over = s.over && !down;
    float r = Math.min(n(s.radius), Math.min(w, h) / 2f);
    int d = bevel(w, h, (int) r, s);
    int center = baseColor(color(s.background), over, down);
    int light = mix(center, 0xFFFFFFFF, 45);
    int dark = mix(center, 0xFF000000, 45);
    var outer = RRect.makeXYWH(0, 0, w, h, r);
    paint.setColor(center);
    cv.drawRRect(outer, paint);
    if (d > 0){
      // The bevel paths depend only on (w, h, radius, d) and are cached on
      // the button: a stable button costs zero path allocations per frame.
      int rad = n(s.radius);
      if (s.bevelW != w || s.bevelH != h || s.bevelR != rad || s.bevelD != d){
        if (s.bevelTl != null){ s.bevelTl.close(); s.bevelBr.close(); }
        Path o = Path.makeRRect(outer);
        Path i = Path.makeRRect(RRect.makeXYWH(d, d, w - 2f * d, h - 2f * d, Math.max(0, r - d)));
        var pb = new PathBuilder();
        pb.moveTo(0, h);
        pb.lineTo(w, 0);
        pb.lineTo(0, 0);
        pb.closePath();
        Path diag = pb.build();
        Path ring = Path.makeCombining(o, i, PathOp.DIFFERENCE);
        s.bevelTl = Path.makeCombining(ring, diag, PathOp.INTERSECT);
        s.bevelBr = Path.makeCombining(ring, diag, PathOp.DIFFERENCE);
        assert ring != null && s.bevelTl != null && s.bevelBr != null;
        for (Path p : new Path[]{ o, i, diag, ring }){ p.close(); }
        s.bevelW = w;
        s.bevelH = h;
        s.bevelR = rad;
        s.bevelD = d;
      }
      paint.setColor(down ? dark : light);
      cv.drawPath(s.bevelTl, paint);
      paint.setColor(down ? light : dark);
      cv.drawPath(s.bevelBr, paint);
    }
    int shift = down && d > 0 ? Math.max(1, d / 2) : 0;
    text(cv, s, shift, shift);
  }

  private static int bevel(int w, int h, int r, AWidget s){
    int d = Math.max(3, Math.min(8, Math.min(w, h) / 9));
    d = Math.min(d, Math.min(Math.min(w(s.left), w(s.right)), Math.min(h(s.top), h(s.bottom))));
    d = Math.min(d, Math.min(w, h) / 2);
    return r > 0 ? Math.min(d, r) : d;
  }

  private static int baseColor(int c, boolean over, boolean down){
    if (c >>> 24 == 0){ c = 0xFFBEBEBE; }
    if (over){ c = mix(c, 0xFFFFFFFF, 12); }
    if (down){ c = mix(c, 0xFF000000, 16); }
    return c;
  }

  private static int mix(int a, int b, int p){
    int q = 100 - p;
    return c8(a >>> 24, b >>> 24, p, q) << 24 | c8(a >> 16 & 255, b >> 16 & 255, p, q) << 16
      | c8(a >> 8 & 255, b >> 8 & 255, p, q) << 8 | c8(a & 255, b & 255, p, q);
  }

  private static int c8(int a, int b, int p, int q){ return (a * q + b * p) / 100; }

  static Image scaled(Image src, int w, int h){
    var s = src;
    while (s.getWidth() / 2 >= w && s.getHeight() / 2 >= h){
      var t = pass(s, s.getWidth() / 2, s.getHeight() / 2, SamplingMode.LINEAR);
      if (s != src){ s.close(); }
      s = t;
    }
    var res = pass(s, w, h, SamplingMode.MITCHELL);
    if (s != src){ s.close(); }
    return res;
  }

  static Image pass(Image src, int w, int h, SamplingMode m){
    try (var surf = Surface.makeRaster(ImageInfo.makeN32Premul(w, h))){
      surf.getCanvas().drawImageRect(
        src, Rect.makeWH(src.getWidth(), src.getHeight()), Rect.makeWH(w, h), m, null, true);
      return surf.makeImageSnapshot();
    }
  }
}
