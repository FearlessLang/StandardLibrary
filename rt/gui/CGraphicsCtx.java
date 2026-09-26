package _base;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.types.Rect;

final class CGraphicsCtx implements Graphics$ao$0{
  final Canvas cv;
  final _Frame frame;
  final Instant$5c$0 elapsed;
  final WidthNat$as$0 panelWidth;
  final HeightNat$lg$0 panelHeight;
  final Paint paint;// owned by AContainer.sk
  float x;
  float y;
  CGraphicsCtx(Canvas cv, _Frame frame, Instant$5c$0 elapsed, WidthNat$as$0 panelWidth, HeightNat$lg$0 panelHeight, Paint paint){
    this.cv = cv;
    this.frame = frame;
    this.elapsed = elapsed;
    this.panelWidth = panelWidth;
    this.panelHeight = panelHeight;
    this.paint = paint;
  }

  @Override public Object mut$color$1(Object color){
    paint.setColor(Sk.color((Color$1c$0) color));
    return this;
  }
  // Drawing outside the panel silently clips (canvas is clipped to the panel),
  // otherwise user can cause errors by resizing the gui by hand
  @Override public Object mut$position$p1$2(Object x, Object y){
    this.x = at(((XInt$s$0) x).read$get$0());
    this.y = at(((YInt$s$0) y).read$get$0());
    return this;
  }
  // Correctly does not update the position.
  @Override public Object mut$line$p1$2(Object x, Object y){
    paint.setMode(PaintMode.STROKE).setStrokeWidth(1);
    cv.drawLine(this.x, this.y, at(((XInt$s$0) x).read$get$0()), at(((YInt$s$0) y).read$get$0()), paint);
    return this;
  }
  @Override public Object mut$rect$p1$2(Object w, Object h){
    paint.setMode(PaintMode.FILL);
    cv.drawRect(shapeRect(w, h), paint);
    return this;
  }
  @Override public Object mut$oval$p1$2(Object w, Object h){
    paint.setMode(PaintMode.FILL);
    cv.drawOval(shapeRect(w, h), paint);
    return this;
  }
  private Rect shapeRect(Object w, Object h){
    return Rect.makeXYWH(x, y, Scopes.w((WidthNat$as$0) w), Scopes.h((HeightNat$lg$0) h));
  }
  private static float at(Object coord){ return Util.intToLong(coord); }
  @Override public Object mut$image$1(Object image){
    var img = ((Image$1c$0Instance) image).image();
    cv.drawImageRect(
      img,
      Rect.makeWH(img.getWidth(), img.getHeight()),
      Rect.makeXYWH(x, y, img.getWidth(), img.getHeight()),
      SamplingMode.LINEAR,
      null,
      true);
    return this;
  }
  @Override public Object read$elapsed$0(){ return elapsed; }
  @Override public Object read$screenWidth$0(){ return Scopes.w(frame.screenW); }
  @Override public Object read$screenHeight$0(){ return Scopes.h(frame.screenH); }
  @Override public Object read$panelWidth$0(){ return panelWidth; }
  @Override public Object read$panelHeight$0(){ return panelHeight; }
}