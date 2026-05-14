package mains;

import base.*;

public final class GuiTestSupport{
  private GuiTestSupport(){}

  public static final Color transparent=color(0,0,0,0);
  public static final Color rootC=color(90,90,90,255);
  public static final Color white=color(255,255,255,255);
  public static final Color black=color(0,0,0,255);
  public static final Color redA=color(255,0,0,120);
  public static final Color greenA=color(0,255,0,120);
  public static final Color blueA=color(0,0,255,120);
  public static final Color yellowA=color(255,255,0,120);
  public static final Color cyanA=color(0,255,255,120);
  public static final Color magentaA=color(255,0,255,120);
  public static final Color darkA=color(0,0,0,140);

  public static Color color(int r,int g,int b,int a){
    return new Color(new Red(r),new Green(g),new Blue(b),new Alpha(a));
  }
  public static Width w(int v){ return new Width(v); }
  public static Height h(int v){ return new Height(v); }
  public static X x(int v){ return new X(v); }
  public static Y y(int v){ return new Y(v); }

  public record Card(Color bg) implements Scope<Border>{
    public Border run(Border b){
      return b.width(w(520)).height(h(210)).background(bg)
        .widthInset(w(12)).heightInset(h(12));
    }
  }

  public record Header(String text) implements Scope<Label>{
    public Label run(Label l){
      return l.text(text).textSize(h(18)).background(transparent).foreground(white)
        .leftInset(w(10)).topInset(h(6)).rightInset(w(10)).bottomInset(h(6));
    }
  }

  public record Inner() implements Scope<Pane>{
    public Pane run(Pane f){
      return f.background(color(255,255,255,30)).widthInset(w(10)).heightInset(h(10));
    }
  }
}