package base;

import static base.CMouseBuilder.MouseKind.Clicked;
import static base.CMouseBuilder.MouseKind.Dragged;
import static base.CMouseBuilder.MouseKind.Entered;
import static base.CMouseBuilder.MouseKind.Exited;
import static base.CMouseBuilder.MouseKind.Moved;
import static base.CMouseBuilder.MouseKind.Pressed;
import static base.CMouseBuilder.MouseKind.Released;
import static base.Scopes.*;

import java.awt.event.MouseAdapter;

record CMouseCtx(
    Time$0 elapsed,
    XNat$0 mouseX,
    YNat$0 mouseY,
    WidthNat$0 screenWidth,
    HeightNat$0 screenHeight,
    WidthNat$0 panelWidth,
    HeightNat$0 panelHeight
) implements MouseEvent$0{
  public Object imm$elapsed$0(){ return elapsed; }
  public Object imm$mouseX$0(){ return mouseX; }
  public Object imm$mouseY$0(){ return mouseY; }
  public Object imm$screenWidth$0(){ return screenWidth; }
  public Object imm$screenHeight$0(){ return screenHeight; }
  public Object imm$panelWidth$0(){ return panelWidth; }
  public Object imm$panelHeight$0(){ return panelHeight; }
}

record CMouseBuilder(AWidget<?,?> panel) implements Mouse$0{
  private MouseEvent$0 ctx(java.awt.event.MouseEvent e){
    int pw=panel.component.getWidth();
    int ph=panel.component.getHeight();
    return new CMouseCtx(
      ((_Frame)panel.frame).elapsed,
      x(clamp(e.getX(),pw)),
      y(clamp(e.getY(),ph)),
      ((_Frame)panel.frame).screenSizeW,
      ((_Frame)panel.frame).screenSizeH,
      w(pw),
      h(ph)
      );
  }
  private static int clamp(int v,int size){
    if (size<=0){ return 0; }
    return Math.max(0,Math.min(v,size-1));
  }
  private Mouse$0 mouse(MouseAdapter a){
    panel.frame.onEdtAndWait(() -> panel.component.addMouseListener(a));
    return this;
  }

  private Mouse$0 motion(MouseAdapter a){
    panel.frame.onEdtAndWait(() -> panel.component.addMouseMotionListener(a));
    return this;
  }

  class Inner extends MouseAdapter{
    final MouseKind kind;
    final Consumer$1 action;
    Inner(MouseKind kind,Consumer$1 action){
      this.kind=kind;
      this.action=action;
    }
    void act(MouseKind actual,MouseKind target,java.awt.event.MouseEvent e){
      if (actual!=target){ return; }
      var c=ctx(e);
      panel.frame.frame.queue.submit(new MF$1(){
        @Override public Object mut$$hash$0(){
          action.mut$accept$1(c);
          return Void$0.instance;
        }
      });
    }
    @Override public void mouseClicked(java.awt.event.MouseEvent e){ act(kind,Clicked,e); }
    @Override public void mousePressed(java.awt.event.MouseEvent e){ act(kind,Pressed,e); }
    @Override public void mouseReleased(java.awt.event.MouseEvent e){ act(kind,Released,e); }
    @Override public void mouseMoved(java.awt.event.MouseEvent e){ act(kind,Moved,e); }
    @Override public void mouseDragged(java.awt.event.MouseEvent e){ act(kind,Dragged,e); }
    @Override public void mouseEntered(java.awt.event.MouseEvent e){ act(kind,Entered,e); }
    @Override public void mouseExited(java.awt.event.MouseEvent e){ act(kind,Exited,e); }
  }

  public enum MouseKind{ Clicked,Pressed,Released,Moved,Dragged,Entered,Exited; }

  @Override public Object mut$clicked$1(Object action){ return mouse(new Inner(Clicked,(Consumer$1)action)); }
  @Override public Object mut$pressed$1(Object action){ return mouse(new Inner(Pressed,(Consumer$1)action)); }
  @Override public Object mut$released$1(Object action){ return mouse(new Inner(Released,(Consumer$1)action)); }
  @Override public Object mut$moved$1(Object action){ return motion(new Inner(Moved,(Consumer$1)action)); }
  @Override public Object mut$dragged$1(Object action){ return motion(new Inner(Dragged,(Consumer$1)action)); }
  @Override public Object mut$entered$1(Object action){ return mouse(new Inner(Entered,(Consumer$1)action)); }
  @Override public Object mut$exited$1(Object action){ return mouse(new Inner(Exited,(Consumer$1)action)); }
}