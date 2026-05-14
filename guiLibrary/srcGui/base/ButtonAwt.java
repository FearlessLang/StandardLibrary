package base;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.AbstractButton;
import static base.Scopes.*;

final class ButtonAwt{
  private ButtonAwt(){}

  static void paint(AbstractButton b,Graphics g,_Button s){
    int w=b.getWidth(),h=b.getHeight();
    if (w<=0 || h<=0){ return; }

    var m=b.getModel();
    boolean down=m.isPressed() && m.isArmed();
    boolean over=m.isRollover() && !down;
    int r=radius(w,h,n(s.radius));
    int d=bevel(w,h,r,s);

    var center=baseColor(s.background,b.isEnabled(),over,down);
    var light=mix(center,new java.awt.Color(255,255,255,255),45);
    var dark=mix(center,new java.awt.Color(0,0,0,255),45);
    var tl=down?dark:light;
    var br=down?light:dark;

    var g2=(Graphics2D)g.create();
    RoundAwt.fill(g2,w,h,r,center);
    if (d>0){
      if (r==0){ paintSquare(g2,w,h,d,tl,br); }
      else { paintRound(g2,w,h,r,d,tl,br); }
    }
    g2.dispose();

    int shift=down && d>0?Math.max(1,d/2):0;
    Awt.paintCenteredText(b,g,s.text,s,shift,shift);
  }

  private static java.awt.Color baseColor(Color$0 cc,boolean enabled,boolean over,boolean down){
    var c=Awt.color(cc);
    if (c.getAlpha()==0){ c=new java.awt.Color(190,190,190,255); }
    if (over){ c=mix(c,new java.awt.Color(255,255,255,255),12); }
    if (down){ c=mix(c,new java.awt.Color(0,0,0,255),16); }
    if (!enabled){ c=mix(c,new java.awt.Color(160,160,160,255),55); }
    return c;
  }
  private static void paintRound(Graphics2D g2,int w,int h,int r,int d,java.awt.Color tl,java.awt.Color br){
    fill(g2,r,0,w-r*2,d,tl);
    fill(g2,r,h-d,w-r*2,d,br);
    fill(g2,0,r,d,h-r*2,tl);
    fill(g2,w-d,r,d,h-r*2,br);

    paintTopLeftTop(g2,r,d,tl);
    paintTopLeftLeft(g2,r,d,tl);

    paintTopRightTop(g2,w,r,d,tl);
    paintTopRightRight(g2,w,r,d,br);

    paintBottomRightRight(g2,w,h,r,d,br);
    paintBottomRightBottom(g2,w,h,r,d,br);

    paintBottomLeftLeft(g2,h,r,d,tl);
    paintBottomLeftBottom(g2,h,r,d,br);
  }

  private static void paintTopLeftTop(Graphics2D g2,int r,int d,java.awt.Color c){
    for (int y=0;y<r;y++){
      int outer=RoundAwt.cornerInset(r,y);
      int inner=y<d?r:d+RoundAwt.cornerInset(r-d,y-d);
      span(g2,Math.max(outer,y),y,inner,c);
    }
  }

  private static void paintTopLeftLeft(Graphics2D g2,int r,int d,java.awt.Color c){
    for (int y=0;y<r;y++){
      int outer=RoundAwt.cornerInset(r,y);
      int inner=y<d?r:d+RoundAwt.cornerInset(r-d,y-d);
      span(g2,outer,y,Math.min(inner,y),c);
    }
  }

  private static void paintTopRightTop(Graphics2D g2,int w,int r,int d,java.awt.Color c){
    for (int y=0;y<r;y++){
      int outer=r-RoundAwt.cornerInset(r,y);
      int inner=y<d?0:r-d-RoundAwt.cornerInset(r-d,y-d);
      int split=r-y;
      span(g2,w-r+inner,y,w-r+Math.min(outer,split),c);
    }
  }

  private static void paintTopRightRight(Graphics2D g2,int w,int r,int d,java.awt.Color c){
    for (int y=0;y<r;y++){
      int outer=r-RoundAwt.cornerInset(r,y);
      int inner=y<d?0:r-d-RoundAwt.cornerInset(r-d,y-d);
      int split=r-y;
      span(g2,w-r+Math.max(inner,split),y,w-r+outer,c);
    }
  }

  private static void paintBottomRightRight(Graphics2D g2,int w,int h,int r,int d,java.awt.Color c){
    for (int y=0;y<r;y++){
      int outer=r-RoundAwt.cornerInset(r,r-1-y);
      int inner=y>=r-d?0:r-d-RoundAwt.cornerInset(r-d,r-d-1-y);
      span(g2,w-r+Math.max(inner,y),h-r+y,w-r+outer,c);
    }
  }

  private static void paintBottomRightBottom(Graphics2D g2,int w,int h,int r,int d,java.awt.Color c){
    for (int y=0;y<r;y++){
      int outer=r-RoundAwt.cornerInset(r,r-1-y);
      int inner=y>=r-d?0:r-d-RoundAwt.cornerInset(r-d,r-d-1-y);
      span(g2,w-r+inner,h-r+y,w-r+Math.min(outer,y),c);
    }
  }

  private static void paintBottomLeftLeft(Graphics2D g2,int h,int r,int d,java.awt.Color c){
    for (int y=0;y<r;y++){
      int outer=RoundAwt.cornerInset(r,r-1-y);
      int inner=y>=r-d?r:d+RoundAwt.cornerInset(r-d,r-d-1-y);
      int split=r-y;
      span(g2,outer,h-r+y,Math.min(inner,split),c);
    }
  }

  private static void paintBottomLeftBottom(Graphics2D g2,int h,int r,int d,java.awt.Color c){
    for (int y=0;y<r;y++){
      int outer=RoundAwt.cornerInset(r,r-1-y);
      int inner=y>=r-d?r:d+RoundAwt.cornerInset(r-d,r-d-1-y);
      int split=r-y;
      span(g2,Math.max(outer,split),h-r+y,inner,c);
    }
  }

  private static void paintSquare(Graphics2D g2,int w,int h,int d,java.awt.Color tl,java.awt.Color br){
    fill(g2,0,0,w,d,tl);
    fill(g2,0,d,d,h-d*2,tl);
    fill(g2,0,h-d,w,d,br);
    fill(g2,w-d,d,d,h-d*2,br);
  }

  private static void span(Graphics2D g2,int x,int y,int end,java.awt.Color c){
    fill(g2,x,y,end-x,1,c);
  }

  private static void fill(Graphics2D g2,int x,int y,int w,int h,java.awt.Color c){
    if (w<=0 || h<=0 || c.getAlpha()==0){ return; }
    g2.setColor(c);
    g2.fillRect(x,y,w,h);
  }
  private static java.awt.Color mix(java.awt.Color a,java.awt.Color b,int p){
    int q=100-p;
    return new java.awt.Color(
      (a.getRed()*q+b.getRed()*p)/100,
      (a.getGreen()*q+b.getGreen()*p)/100,
      (a.getBlue()*q+b.getBlue()*p)/100,
      (a.getAlpha()*q+b.getAlpha()*p)/100
    );
  }

  private static int bevel(int w,int h,int r,AWidget<?,?> s){
    int d=Math.max(3,Math.min(8,Math.min(w,h)/9));
    d=Math.min(d,Math.min(Math.min(w(s.left),w(s.right)),Math.min(h(s.top),h(s.bottom))));
    d=Math.min(d,Math.min(w,h)/2);
    if (r>0){ d=Math.min(d,r); }
    return Math.max(0,d);
  }

  private static int radius(int w,int h,int r){
    return Math.max(0,Math.min(r,Math.min(w,h)/2));
  }
}