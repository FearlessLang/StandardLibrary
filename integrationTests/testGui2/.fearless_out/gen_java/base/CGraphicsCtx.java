package base;

import java.awt.Graphics2D;
import javax.swing.JComponent;

record CGraphicsCtx(
  Graphics2D g2d,
  _Frame frame,
  Time$0 elapsed,
  WidthNat$0 panelSizeW,
  HeightNat$0 panelSizeH,
  XNat$0 currentX,
  YNat$0 currentY
  ) implements Graphics$0 {
  static CGraphicsCtx of(java.awt.Graphics g,Frame$0 frame,JComponent c){
    return new CGraphicsCtx(
      (Graphics2D)g,
      (_Frame)frame,
      ((_Frame)frame).elapsed,
      Awt.fW(c),
      Awt.fH(c),
      XNat$0.instance,
      YNat$0.instance
      );
  }

  @Override public Object mut$color$1(Object color){
    g2d.setColor(Awt.color((Color$0)color));
    return this;
  }
//Drawing outside the panel silently clips, otherwise user can cause errors by resizing the gui by hand
  @Override public Object mut$position$2(Object x,Object y){
    frame.xPos((XNat$0)x,"graphics x position");
    frame.yPos((YNat$0)y,"graphics y position");
    return new CGraphicsCtx(g2d,frame,elapsed,panelSizeW,panelSizeH,(XNat$0)x,(YNat$0)y);
  }
//Correctly does not update the position.
  @Override public Object mut$line$2(Object x,Object y){
    g2d.drawLine(
      frame.xPos(currentX,"graphics current x"),
      frame.yPos(currentY,"graphics current y"),
      frame.xPos((XNat$0)x,"graphics line x"),
      frame.yPos((YNat$0)y,"graphics line y")
      );
    return this;
  }

  @Override public Object mut$rect$2(Object w,Object h){
    g2d.fillRect(
      frame.xPos(currentX,"graphics rect x"),
      frame.yPos(currentY,"graphics rect y"),
      frame.width((WidthNat$0)w,"graphics rect width"),
      frame.height((HeightNat$0)h,"graphics rect height")
      );
    return this;
  }

  @Override public Object mut$oval$2(Object w,Object h){
    g2d.fillOval(
      frame.xPos(currentX,"graphics oval x"),
      frame.yPos(currentY,"graphics oval y"),
      frame.width((WidthNat$0)w,"graphics oval width"),
      frame.height((HeightNat$0)h,"graphics oval height")
      );
    return this;
  }

  @Override public Object read$elapsed$0(){ return elapsed; }
  @Override public Object read$screenWidth$0(){ return frame.screenSizeW; }
  @Override public Object read$screenHeight$0(){ return frame.screenSizeH; }
  @Override public Object read$panelWidth$0(){ return panelSizeW; }
  @Override public Object read$panelHeight$0(){ return panelSizeH; }
}