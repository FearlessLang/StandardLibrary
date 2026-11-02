package _base;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.FilterMipmap;
import io.github.humbleui.skija.FilterMode;
import io.github.humbleui.skija.MipmapMode;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.Rect;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

class CGraphicsCtx implements Graphics$ao$0{
  final Canvas cv;
  final Instant$5c$0 elapsed;
  final WidthNat$as$0 panelWidth;
  final HeightNat$lg$0 panelHeight;
  final Paint paint;// owned by AContainer.sk
  Color$1c$0 color;
  float x;
  float y;
  int textSize= 12;
  double above= 1;
  double below= 0.5;
  float left= Float.MAX_VALUE;
  float top= Float.MAX_VALUE;
  float right= -Float.MAX_VALUE;
  float bottom= -Float.MAX_VALUE;
  CGraphicsCtx(Canvas cv, Instant$5c$0 elapsed, WidthNat$as$0 panelWidth, HeightNat$lg$0 panelHeight, Paint paint, Color$1c$0 color){
    this.cv= cv;
    this.elapsed= elapsed;
    this.panelWidth= panelWidth;
    this.panelHeight= panelHeight;
    this.paint= paint;
    mut$color$1(color);
  }

  @Override public Object mut$color$1(Object color){
    this.color = (Color$1c$0) color;
    paint.setColor(Sk.color(this.color));
    return this;
  }
  @Override public Object read$color$0(){ return color; }
  @Override public Object read$positionX$0(){ return Scopes.x((long) x); }
  @Override public Object read$positionY$0(){ return Scopes.y((long) y); }
  @Override public Object read$textHeight$0(){ return Scopes.h(textSize); }
  @Override public Object read$aboveBaseline$0(){ return Scopes.ratio(above); }
  @Override public Object read$belowBaseline$0(){ return Scopes.ratio(below); }
  @Override public Object read$uTextRoom$1(Object text){
    var s= oneLine(text, "Graphics.textRoom", "measures");
    var room= Sk.need(Sk.shape(s, textSize), above, below);
    return new CTextRoom(Scopes.w(room.width), Scopes.h(room.height), Scopes.h(Math.round(Sk.baseline(room.height, textSize, above, below))));
  }
  @Override public Object read$uTextLines$2(Object text, Object width){
    int ww= Scopes.extent((WidthNat$as$0) width, "textLines width");
    var res= new ArrayList<Object>();
    for (var p : Scopes.ustr(text).split("\n", -1)){
      int from= 0;
      for (int to : Sk.wrap(p, textSize, above, below, ww)){
        res.add(UStr$s$0Instance.instance(p.substring(from, Sk.trimmed(p, from, to))));
        from = to;
      }
    }
    return new List$o$1Instance(List.copyOf(res));
  }
  private static String oneLine(Object text, String method, String does){
    var s= Scopes.ustr(text);
    var b= Scopes.newline(s);
    if (b != null){ throw Util.detErr("The text given to " + method + " has " + b + ": " + method + " " + does + " one line"); }
    return s;
  }
  // Drawing outside the panel clips with a warning (canvas is clipped to the panel),
  // otherwise user can cause errors by resizing the gui by hand
  @Override public Object mut$position$2(Object x, Object y){
    this.x = Scopes.coord(((XInt$s$0) x).read$get$0(), "position x");
    this.y = Scopes.coord(((YInt$s$0) y).read$get$0(), "position y");
    return this;
  }
  // Correctly does not update the position.
  @Override public Object mut$line$2(Object x, Object y){
    paint.setMode(PaintMode.STROKE).setStrokeWidth(1);
    int x1= Scopes.coord(((XInt$s$0) x).read$get$0(), "line x");
    int y1= Scopes.coord(((YInt$s$0) y).read$get$0(), "line y");
    cv.drawLine(this.x, this.y, x1, y1, paint);
    reach(Rect.makeLTRB(Math.min(this.x, x1), Math.min(this.y, y1), Math.max(this.x, x1), Math.max(this.y, y1)));
    return this;
  }
  @Override public Object mut$rect$2(Object w, Object h){
    paint.setMode(PaintMode.FILL);
    cv.drawRect(reach(shapeRect(w, h, "rect")), paint);
    return this;
  }
  @Override public Object mut$oval$2(Object w, Object h){
    paint.setMode(PaintMode.FILL);
    cv.drawOval(reach(shapeRect(w, h, "oval")), paint);
    return this;
  }
  private Rect shapeRect(Object w, Object h, String what){
    return Rect.makeXYWH(x, y, Scopes.extent((WidthNat$as$0) w, what + " width"), Scopes.extent((HeightNat$lg$0) h, what + " height"));
  }
  @Override public Object mut$image$3(Object image, Object w, Object h){
    var img= ((Image$1c$0Instance) image).image;
    cv.drawImageRect(
      img,
      Rect.makeWH(img.getWidth(), img.getHeight()),
      reach(shapeRect(w, h, "image")),
      new FilterMipmap(FilterMode.LINEAR, MipmapMode.LINEAR),
      null,
      true);
    return this;
  }
  @Override public Object mut$textHeight$p1$1(Object h){
    textSize = Scopes.extent((HeightNat$lg$0) h, "text height");
    return this;
  }
  @Override public Object mut$aboveBaseline$p1$1(Object r){
    above = Scopes.ratio(r);
    return this;
  }
  @Override public Object mut$belowBaseline$p1$1(Object r){
    below = Scopes.ratio(r);
    return this;
  }
  @Override public Object mut$uText$1(Object text){
    var s= oneLine(text, "Graphics.text", "draws");
    var line= Sk.shape(s, textSize);
    var room= Sk.need(line, above, below);
    return text(line, room, room);
  }
  @Override public Object mut$uText$3(Object text, Object w, Object h){
    var s= oneLine(text, "Graphics.text", "draws");
    var box= new Dimension(Scopes.extent((WidthNat$as$0) w, "text box width"), Scopes.extent((HeightNat$lg$0) h, "text box height"));
    var line= Sk.shape(s, textSize);
    return text(line, Sk.need(line, above, below), box);
  }
  private Object text(Sk.Line line, Dimension room, Dimension box){
    paint.setMode(PaintMode.FILL);
    Sk.draw(cv, line, x, y, box.width, box.height, above, below, paint);
    reach(Rect.makeXYWH(x + (box.width - room.width) / 2f, y + (box.height - room.height) / 2f, room.width, room.height));
    return this;
  }
  private Rect reach(Rect r){
    left = Math.min(left, r.getLeft());
    top = Math.min(top, r.getTop());
    right = Math.max(right, r.getRight());
    bottom = Math.max(bottom, r.getBottom());
    return r;
  }
  String outside(String what, String noun, int w, int h){
    var edges= new java.util.ArrayList<String>();
    if (left < 0){ edges.add("left"); }
    if (top < 0){ edges.add("top"); }
    if (right > w){ edges.add("right"); }
    if (bottom > h){ edges.add("bottom"); }
    if (edges.isEmpty()){ return null; }
    return "the painter of " + what + " of " + w + "x" + h + " draws past its " + String.join(", ", edges) + (edges.size() == 1 ? " edge" : " edges") + ": the part outside the " + noun + " is clipped";
  }
  @Override public Object read$elapsed$0(){ return elapsed; }
  @Override public Object read$panelWidth$0(){ return panelWidth; }
  @Override public Object read$panelHeight$0(){ return panelHeight; }
}

record CTextRoom(Object width, Object height, Object baseline) implements TextRoom$aw$0{
  @Override public Object read$width$0(){ return width; }
  @Override public Object read$height$0(){ return height; }
  @Override public Object read$baseline$0(){ return baseline; }
}