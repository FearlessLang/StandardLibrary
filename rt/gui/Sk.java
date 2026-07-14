package base;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontEdging;
import io.github.humbleui.skija.FontHinting;
import io.github.humbleui.skija.FontMgr;
import io.github.humbleui.skija.FontStyle;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.Path;
import io.github.humbleui.skija.PathBuilder;
import io.github.humbleui.skija.PathOp;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.Typeface;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.awt.Dimension;
import java.util.HashMap;
import static base.Scopes.*;

interface Sk{
  Paint paint = makePaint();
  Typeface typeface = typeface();
  HashMap<Integer, Font> fonts = new HashMap<>();

  static Paint makePaint(){
    var p = new Paint();
    p.setAntiAlias(true);
    p.setMode(PaintMode.FILL);
    return p;
  }
  // TODO(fonts): typeface resolution is platform-dependent: we take whatever
  // the OS resolves for Segoe UI / Arial / Consolas, so text pixels differ
  // across machines even though rendering is otherwise deterministic. The plan
  // is to bundle our own font(s) so text is identical everywhere. There is
  // deliberately NO glyph fallback: characters missing from the selected
  // typeface render as tofu boxes, identically on every machine, instead of
  // being silently substituted from whichever random font the host has
  // installed. Keep fallback disabled after bundling, so output never depends
  // on glyphs outside the bundled set.
  static Typeface typeface(){
    var fm = FontMgr.getDefault();
    var t = fm.matchFamilyStyle("Segoe UI", FontStyle.NORMAL);
    if (t != null){ return t; }
    t = fm.matchFamilyStyle("Arial", FontStyle.NORMAL);
    if (t != null){ return t; }
    t = fm.matchFamilyStyle("Consolas", FontStyle.NORMAL);
    if (t != null){ return t; }
    throw new Error("Could not find a usable Skija font");
  }

  static Font font(AWidget s){
    int size = h(s.textSize);
    Font f = fonts.get(size);
    if (f != null){ return f; }
    f = new Font(typeface, size);
    f.setSubpixel(true);
    f.setEdging(FontEdging.ANTI_ALIAS);
    f.setHinting(FontHinting.NONE);
    fonts.put(size, f);
    return f;
  }

  static int color(Color$0 c){
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

  static void fillRRect(Canvas cv, float x, float y, float w, float h, float r, int col){
    if (col >>> 24 == 0){ return; }
    r = Math.min(r, Math.min(w, h) / 2);
    paint.setMode(PaintMode.FILL);
    paint.setColor(col);
    cv.drawRRect(RRect.makeXYWH(x, y, w, h, r), paint);
  }

  static void background(Canvas cv, AWidget s){
    fillRRect(cv, 0, 0, s.component.getWidth(), s.component.getHeight(), n(s.radius), color(s.background));
  }

  static Dimension textSize(String text, AWidget s){
    var f = font(s);
    return new Dimension(
      (int) Math.ceil(f.measureTextWidth(text)),
      (int) Math.ceil(f.getMetrics().getHeight()));
  }

  static Dimension textSizeWithInsets(String text, AWidget s){
    var d = textSize(text, s);
    return new Dimension(
      d.width + w(s.left) + w(s.right),
      d.height + h(s.top) + h(s.bottom));
  }

  static Dimension preferred(Dimension auto, AWidget s){
    return new Dimension(
      s.preferredWidth == null ? auto.width : w(s.preferredWidth),
      s.preferredHeight == null ? auto.height : h(s.preferredHeight));
  }

  static void text(Canvas cv, String text, AWidget s, float dx, float dy){
    var c = s.component;
    var f = font(s);
    var fm = f.getMetrics();
    float textW = f.measureTextWidth(text);
    float textH = fm.getHeight();
    int x0 = w(s.left);
    int y0 = h(s.top);
    int cw = c.getWidth() - w(s.left) - w(s.right);
    int ch = c.getHeight() - h(s.top) - h(s.bottom);
    if (cw <= 0 || ch <= 0){ return; }
    int save = cv.save();
    cv.clipRect(Rect.makeXYWH(x0 + dx, y0 + dy, cw, ch));
    paint.setMode(PaintMode.FILL);
    paint.setColor(color(s.foreground));
    cv.drawString(text, x0 + (cw - textW) / 2 + dx, y0 + (ch - textH) / 2 - fm.getAscent() + dy, f, paint);
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
    paint.setMode(PaintMode.FILL);
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
      paint.setMode(PaintMode.FILL);
      paint.setColor(down ? dark : light);
      cv.drawPath(s.bevelTl, paint);
      paint.setColor(down ? light : dark);
      cv.drawPath(s.bevelBr, paint);
    }
    int shift = down && d > 0 ? Math.max(1, d / 2) : 0;
    text(cv, s.text, s, shift, shift);
  }

  private static int bevel(int w, int h, int r, AWidget s){
    int d = Math.max(3, Math.min(8, Math.min(w, h) / 9));
    d = Math.min(d, Math.min(Math.min(w(s.left), w(s.right)), Math.min(h(s.top), h(s.bottom))));
    d = Math.min(d, Math.min(w, h) / 2);
    if (r > 0){ d = Math.min(d, r); }
    return Math.max(0, d);
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