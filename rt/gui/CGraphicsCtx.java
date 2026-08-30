package base;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.types.Rect;

record CGraphicsCtx(
  Canvas cv,
  _Frame frame,
  Instant$5c$0 elapsed,
  WidthNat$as$0 panelSizeW,
  HeightNat$lg$0 panelSizeH,
  XNat$s$0 currentX,
  YNat$s$0 currentY,
  Paint paint//shared by all positions of one Painter run; owned by AContainer.sk
  ) implements Graphics$ao$0 {

  @Override public Object mut$color$1(Object color){
    paint.setColor(Sk.color((Color$1c$0)color));
    return this;
  }
//Drawing outside the panel silently clips (canvas is clipped to the panel),
//otherwise user can cause errors by resizing the gui by hand
  @Override public Object mut$position$2(Object x,Object y){
    frame.xPos((XNat$s$0)x,"graphics x position");
    frame.yPos((YNat$s$0)y,"graphics y position");
    return new CGraphicsCtx(cv,frame,elapsed,panelSizeW,panelSizeH,(XNat$s$0)x,(YNat$s$0)y,paint);
  }
//Correctly does not update the position.
  @Override public Object mut$line$2(Object x,Object y){
    paint.setMode(PaintMode.STROKE).setStrokeWidth(1);
    cv.drawLine(
      frame.xPos(currentX,"graphics current x"),
      frame.yPos(currentY,"graphics current y"),
      frame.xPos((XNat$s$0)x,"graphics line x"),
      frame.yPos((YNat$s$0)y,"graphics line y"),
      paint);
    return this;
  }
  @Override public Object mut$rect$2(Object w,Object h){
    paint.setMode(PaintMode.FILL);
    cv.drawRect(Rect.makeXYWH(
      frame.xPos(currentX,"graphics rect x"),
      frame.yPos(currentY,"graphics rect y"),
      frame.width((WidthNat$as$0)w,"graphics rect width"),
      frame.height((HeightNat$lg$0)h,"graphics rect height")),paint);
    return this;
  }
  @Override public Object mut$oval$2(Object w,Object h){
    paint.setMode(PaintMode.FILL);
    cv.drawOval(Rect.makeXYWH(
      frame.xPos(currentX,"graphics oval x"),
      frame.yPos(currentY,"graphics oval y"),
      frame.width((WidthNat$as$0)w,"graphics oval width"),
      frame.height((HeightNat$lg$0)h,"graphics oval height")),paint);
    return this;
  }
  @Override public Object mut$image$1(Object image){
    var img=((Image$1c$0Instance)image).image();
    var x=frame.xPos(currentX,"graphics image x");
    var y=frame.yPos(currentY,"graphics image y");
    cv.drawImageRect(
      img,
      Rect.makeWH(img.getWidth(),img.getHeight()),
      Rect.makeXYWH(x,y,img.getWidth(),img.getHeight()),
      SamplingMode.LINEAR,
      null,
      true);
    return this;
  }
  @Override public Object read$elapsed$0(){ return elapsed; }
  @Override public Object read$screenWidth$0(){ return frame.screenSizeW; }
  @Override public Object read$screenHeight$0(){ return frame.screenSizeH; }
  @Override public Object read$panelWidth$0(){ return panelSizeW; }
  @Override public Object read$panelHeight$0(){ return panelSizeH; }
}