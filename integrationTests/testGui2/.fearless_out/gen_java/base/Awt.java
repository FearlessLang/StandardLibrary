package base;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JComponent;
import static base.Scopes.*;

interface Awt{
  String textWarningKey="base.Awt.textWarningKey";
  String explicitSizeWarningKey="base.Awt.explicitSizeWarningKey";

  static WidthNat$0 fW(JComponent c){ return Scopes.w(c.getWidth()); }
  static HeightNat$0 fH(JComponent c){ return Scopes.h(c.getHeight()); }

  static Dimension dimension(WidthNat$0 w,HeightNat$0 h){
    return new Dimension(w(w),h(h));
  }
  static Point point(XNat$0 x,YNat$0 y){
    return new Point(x(x),y(y));
  }

  static void revalidateKids(JComponent c){
    for (var ci: c.getComponents()){ ((JComponent)ci).revalidate(); }
  }

  static java.awt.Color color(Color$0 c){
    return new java.awt.Color(
      red(c.read$red$0()),
      green(c.read$green$0()),
      blue(c.read$blue$0()),
      alpha(c.read$alpha$0()));
  }

  static Font font(AWidget<?,?> s){
    return new Font("SansSerif",Font.PLAIN,h(s.textSize));
  }

  static void paintBackground(JComponent c,Graphics g,Widget$1 self){
    var s=(AWidget<?,?>)self;
    var bg=color(s.background);
    if (bg.getAlpha()==0){ return; }
    var g2=(Graphics2D)g.create();
    RoundAwt.fill(g2,c.getWidth(),c.getHeight(),n(s.radius),bg);
    g2.dispose();
  }

  static Dimension preferred(Dimension auto,AWidget<?,?> s){
    warnExplicitSize(s.component,s);
    return new Dimension(
      s.preferredWidth==null ? auto.width : w(s.preferredWidth),
      s.preferredHeight==null ? auto.height : h(s.preferredHeight));
  }

  static private Dimension textSize(JComponent c,String text,AWidget<?,?> s){
    FontMetrics fm=c.getFontMetrics(font(s));
    return new Dimension(fm.stringWidth(text),fm.getHeight());
  }

  static Dimension textSizeWithInsets(JComponent c,String text,AWidget<?,?> s){
    var d=textSize(c,text,s);
    return new Dimension(
      d.width+w(s.left)+w(s.right),
      d.height+h(s.top)+h(s.bottom));
  }

  static void paintCenteredText(JComponent c,Graphics g,String text,AWidget<?,?> s){
    paintCenteredText(c,g,text,s,0,0);
  }
//Correctly shifts the clipping rectangle, not only the text, so the button text is visible also when button is pressed
  static void paintCenteredText(JComponent c,Graphics g,String text,AWidget<?,?> s,int dx,int dy){
    var g2=(Graphics2D)g.create();
    g2.setFont(font(s));
    g2.setColor(color(s.foreground));

    FontMetrics fm=g2.getFontMetrics();
    int textW=fm.stringWidth(text);
    int textH=fm.getHeight();
    int x0=w(s.left);
    int y0=h(s.top);
    int w=c.getWidth()-w(s.left)-w(s.right);
    int h=c.getHeight()-h(s.top)-h(s.bottom);

    warnTextFit(c,text,s,textW,textH,w,h);
    if (w<=0 || h<=0){ g2.dispose(); return; }

    int x=x0+(w-textW)/2+dx;
    int y=y0+(h-textH)/2+fm.getAscent()+dy;

    var oldClip=g2.getClip();
    g2.clipRect(x0+dx,y0+dy,w,h);
    g2.drawString(text,x,y);
    g2.setClip(oldClip);
    g2.dispose();
  }

  static void background(JComponent c,Color$0 bg){
    c.setBackground(color(bg));
    c.setOpaque(false);
  }

  static Dimension textSize(JComponent c,String text){
    FontMetrics fm=c.getFontMetrics(c.getFont());
    return new Dimension(fm.stringWidth(text),fm.getHeight());
  }

  private static void warnTextFit(
    JComponent c,String text,AWidget<?,?> s,int textW,int textH,int contentW,int contentH){
    String msg=textWarning(c,text,s,textW,textH,contentW,contentH);
    if (msg==null){
      c.putClientProperty(textWarningKey,null);
      return;
    }
    if (msg.equals(c.getClientProperty(textWarningKey))){ return; }
    c.putClientProperty(textWarningKey,msg);
    System.err.println(msg);
  }

  private static String textWarning(
    JComponent c,String text,AWidget<?,?> s,int textW,int textH,int contentW,int contentH){
    String prefix="Fearless GUI warning:\n";
    if (contentW<=0 || contentH<=0){
      return prefix
        +"text not painted because style leaves no content area.\n"
        +"Component="+c.getWidth()+"x"+c.getHeight()
        +"\nContent="+contentW+"x"+contentH
        +"\nStyle=[left="+w(s.left)
        +", top="+h(s.top)
        +", right="+w(s.right)
        +", bottom="+h(s.bottom)
        +", radius="+n(s.radius)
        +"]\nText=\""+text+"\"";
    }
    if (textW>contentW || textH>contentH){
      return prefix
        +"text clipped by content area.\n"
        +"Component="+c.getWidth()+"x"+c.getHeight()
        +"\nContent="+contentW+"x"+contentH
        +"\nTextSize="+textW+"x"+textH
        +"\nStyle=[left="+w(s.left)
        +", top="+h(s.top)
        +", right="+w(s.right)
        +", bottom="+h(s.bottom)
        +", radius="+n(s.radius)
        +"]\nText=\""+text+"\"";
    }
    return null;
  }

  static void warnExplicitSize(JComponent c,AWidget<?,?> s){
    String msg=explicitSizeWarning(c,s);
    if (msg==null){
      c.putClientProperty(explicitSizeWarningKey,null);
      return;
    }
    if (msg.equals(c.getClientProperty(explicitSizeWarningKey))){ return; }
    c.putClientProperty(explicitSizeWarningKey,msg);
    System.err.println(msg);
  }

  private static String explicitSizeWarning(JComponent c,AWidget<?,?> s){
    String prefix="Fearless GUI warning:\n";
    var width=s.preferredWidth;
    var height=s.preferredHeight;
    boolean badW=width!=null && w(width)<=w(s.left)+w(s.right);
    boolean badH=height!=null && h(height)<=h(s.top)+h(s.bottom);
    if (!badW && !badH){ return null; }

    return prefix
      +"explicit size leaves no content area.\n"
      +"Explicit=[width="+(width==null?"auto":""+w(width))
      +", height="+(height==null?"auto":""+h(height))
      +"]\nContent=[width="+(width==null?"auto":""+(w(width)-w(s.left)-w(s.right)))
      +", height="+(height==null?"auto":""+(h(height)-h(s.top)-h(s.bottom)))
      +"]\nStyle=[left="+w(s.left)
      +", top="+h(s.top)
      +", right="+w(s.right)
      +", bottom="+h(s.bottom)
      +", widthGap="+w(s.widthGap)
      +", heightGap="+h(s.heightGap)
      +", radius="+n(s.radius)
      +"]";
  }
}