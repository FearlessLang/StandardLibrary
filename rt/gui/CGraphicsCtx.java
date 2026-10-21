package _base;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.types.Rect;

class CGraphicsCtx implements Graphics$ao$0{
  final Canvas cv;
  final _Frame frame;
  final Instant$5c$0 elapsed;
  final WidthNat$as$0 panelWidth;
  final HeightNat$lg$0 panelHeight;
  final Paint paint;// owned by AContainer.sk
  float x;
  float y;
  float left= Float.MAX_VALUE;
  float top= Float.MAX_VALUE;
  float right= -Float.MAX_VALUE;
  float bottom= -Float.MAX_VALUE;
  CGraphicsCtx(Canvas cv, _Frame frame, Instant$5c$0 elapsed, WidthNat$as$0 panelWidth, HeightNat$lg$0 panelHeight, Paint paint){
    this.cv= cv;
    this.frame= frame;
    this.elapsed= elapsed;
    this.panelWidth= panelWidth;
    this.panelHeight= panelHeight;
    this.paint= paint;
  }

  @Override public Object mut$color$1(Object color){
    paint.setColor(Sk.color((Color$1c$0) color));
    return this;
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
  @Override public Object mut$image$1(Object image){
    var img= ((Image$1c$0Instance) image).image();
    cv.drawImageRect(
      img,
      Rect.makeWH(img.getWidth(), img.getHeight()),
      reach(Rect.makeXYWH(x, y, img.getWidth(), img.getHeight())),
      SamplingMode.LINEAR,
      null,
      true);
    return this;
  }
  private Rect reach(Rect r){
    left = Math.min(left, r.getLeft());
    top = Math.min(top, r.getTop());
    right = Math.max(right, r.getRight());
    bottom = Math.max(bottom, r.getBottom());
    return r;
  }
  String outside(AWidget s){
    int w= s.component.getWidth();
    int h= s.component.getHeight();
    var edges= new java.util.ArrayList<String>();
    if (left < 0){ edges.add("left"); }
    if (top < 0){ edges.add("top"); }
    if (right > w){ edges.add("right"); }
    if (bottom > h){ edges.add("bottom"); }
    if (edges.isEmpty()){ return null; }
    return "the painter of " + s.describe() + " of " + w + "x" + h + " draws past its " + String.join(", ", edges) + (edges.size() == 1 ? " edge" : " edges") + ": the part outside the widget is clipped";
  }
  @Override public Object read$elapsed$0(){ return elapsed; }
  @Override public Object read$panelWidth$0(){ return panelWidth; }
  @Override public Object read$panelHeight$0(){ return panelHeight; }
}