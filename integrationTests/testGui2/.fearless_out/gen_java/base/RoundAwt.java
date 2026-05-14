package base;

import java.awt.Color;
import java.awt.Graphics2D;

final class RoundAwt{
  private RoundAwt(){}

  static void fill(Graphics2D g2,int w,int h,int r,Color c){
    fill(g2,0,0,w,h,r,c);
  }

  static void fill(Graphics2D g2,int x,int y,int w,int h,int r,Color c){
    if (w<=0 || h<=0 || c.getAlpha()==0){ return; }
    r=radius(w,h,r);
    g2.setColor(c);

    for (int row=0;row<h;row++){
      int in=inset(row,h,r);
      int ww=w-in*2;
      if (ww>0){ g2.fillRect(x+in,y+row,ww,1); }
    }
  }

  private static int radius(int w,int h,int r){
    return Math.max(0,Math.min(r,Math.min(w,h)/2));
  }

  private static int inset(int row,int h,int r){
    if (r==0){ return 0; }
    if (row<r){ return cornerInset(r,row); }
    if (row>=h-r){ return cornerInset(r,h-1-row); }
    return 0;
  }

  static int cornerInset(int r,int row){
    double y=r-row-0.5;
    double x=Math.sqrt(Math.max(0,r*r-y*y));
    return Math.max(0,(int)Math.ceil(r-x-0.5));
  }
}