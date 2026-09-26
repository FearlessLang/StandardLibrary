package _base;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.types.Rect;

record CGraphicsCtx(
  Canvas cv,
  _Frame frame,
  Instant$5c$0 elapsed,
  WidthNat$as$0 panelWidth,
  HeightNat$lg$0 panelHeight,
  XInt$s$0 currentX,
  YInt$s$0 currentY,
  Paint paint// shared by all positions of one Painter run; owned by AContainer.sk
  ) implements Graphics$ao$0{

  @Override public Object mut$color$1(Object color){
    paint.setColor(Sk.color((Color$1c$0) color));
    return this;
  }
  // Drawing outside the panel silently clips (canvas is clipped to the panel),
  // otherwise user can cause errors by resizing the gui by hand
  @Override public Object mut$position$2(Object x, Object y){
    return new CGraphicsCtx(cv, frame, elapsed, panelWidth, panelHeight, (XInt$s$0) x, (YInt$s$0) y, paint);
  }
  // Correctly does not update the position.
  @Override public Object mut$line$2(Object x, Object y){
    paint.setMode(PaintMode.STROKE).setStrokeWidth(1);
    cv.drawLine(at(currentX.read$get$0()), at(currentY.read$get$0()), at(((XInt$s$0) x).read$get$0()), at(((YInt$s$0) y).read$get$0()), paint);
    return this;
  }
  @Override public Object mut$rect$2(Object w, Object h){
    paint.setMode(PaintMode.FILL);
    cv.drawRect(shapeRect(w, h), paint);
    return this;
  }
  @Override public Object mut$oval$2(Object w, Object h){
    paint.setMode(PaintMode.FILL);
    cv.drawOval(shapeRect(w, h), paint);
    return this;
  }
  private Rect shapeRect(Object w, Object h){
    return Rect.makeXYWH(at(currentX.read$get$0()), at(currentY.read$get$0()), Scopes.w((WidthNat$as$0) w), Scopes.h((HeightNat$lg$0) h));
  }
  private static float at(Object coord){ return Util.intToLong(coord); }
  @Override public Object mut$image$1(Object image){
    var img = ((Image$1c$0Instance) image).image();
    var x = at(currentX.read$get$0());
    var y = at(currentY.read$get$0());
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
  @Override public Object read$screenWidth$0(){ return frame.screenWidth; }
  @Override public Object read$screenHeight$0(){ return frame.screenHeight; }
  @Override public Object read$panelWidth$0(){ return panelWidth; }
  @Override public Object read$panelHeight$0(){ return panelHeight; }
}