package mains;

import static mains.GuiTestSupport.*;

import base.*;

record RegionTitle(String text) implements Scope<Label>{
  public Label run(Label l){
    return l.text(text).textSize(h(18)).background(transparent).foreground(white)
      .leftInset(w(10)).topInset(h(6)).rightInset(w(10)).bottomInset(h(6));
  }
}

record Pill(String text,Color bg,Color fg) implements Scope<Label>{
  public Label run(Label l){
    return l.text(text).textSize(h(18)).background(bg).foreground(fg)
      .leftInset(w(14)).topInset(h(8)).rightInset(w(14)).bottomInset(h(8));
  }
}

record Push(String text,Color bg,Color fg) implements Scope<Button>{
  public Button run(Button b){
    return b.text(text).textSize(h(18)).background(bg).foreground(fg)
      .leftInset(w(16)).topInset(h(8)).rightInset(w(16)).bottomInset(h(8))
      .action(() -> System.out.println(text));
  }
}

public class AutoDirectionDemo{
  public static void main(String[] args){
    new _FluentGUI().run(frame -> {
      frame.title("Auto direction demo").resizable().fps(30);
      frame.contentB(AutoDirectionDemo::root);
    });
  }

  static Border root(Border b){
    return b.background(rootC)
      .leftInset(w(16)).rightInset(w(16)).topInset(h(16)).bottomInset(h(16))
      .widthInset(w(12)).heightInset(h(12))
      .north(AutoDirectionDemo::rootNorth)
      .south(AutoDirectionDemo::rootSouth)
      .west(AutoDirectionDemo::rootWest)
      .east(AutoDirectionDemo::rootEast)
      .centerB(AutoDirectionDemo::centerBorder);
  }

  static Pane rootNorth(Pane f){
    return f.height(h(62)).background(color(30,30,30,220))
      .widthInset(w(8)).heightInset(h(8))
      .label(l -> l.style(new RegionTitle("NORTH: height fixed to 62, width follows frame")))
      .label(l -> l.style(new Pill("auto label",redA,white)))
      .label(l -> l.style(new Pill("width=230, height auto",greenA,black)).width(w(230)))
      .label(l -> l.style(new Pill("height=42, width auto",blueA,white)).height(h(42)));
  }

  static Pane rootSouth(Pane f){
    return f.background(color(30,30,30,220))
      .widthInset(w(8)).heightInset(h(8))
      .label(l -> l.style(new RegionTitle("SOUTH: height is auto, width follows frame")))
      .label(l -> l.style(new Pill("auto",yellowA,black)))
      .label(l -> l.style(new Pill("width only",cyanA,black)).width(w(180)))
      .button(b -> b.style(new Push("height only",magentaA,white)).height(h(48)))
      .button(b -> b.style(new Push("both 170x48",blueA,white)).width(w(170)).height(h(48)));
  }

  static Pane rootWest(Pane f){
    return f.width(w(210)).background(color(0,30,90,210))
      .widthInset(w(8)).heightInset(h(8))
      .label(l -> l.style(new RegionTitle("WEST")))
      .label(l -> l.style(new Pill("width fixed",redA,white)).width(w(150)))
      .label(l -> l.style(new Pill("height auto",greenA,black)))
      .label(l -> l.style(new Pill("height=70",blueA,white)).height(h(70)));
  }

  static Pane rootEast(Pane f){
    return f.background(color(80,0,90,210))
      .widthInset(w(8)).heightInset(h(8))
      .label(l -> l.style(new RegionTitle("EAST auto width")))
      .label(l -> l.style(new Pill("auto",cyanA,black)))
      .label(l -> l.style(new Pill("width=180",magentaA,white)).width(w(180)))
      .button(b -> b.style(new Push("height=60",yellowA,black)).height(h(60)));
  }

  static Border centerBorder(Border b){
    return b.background(color(255,255,255,35))
      .leftInset(w(12)).rightInset(w(12)).topInset(h(12)).bottomInset(h(12))
      .widthInset(w(10)).heightInset(h(10))
      .north(AutoDirectionDemo::innerNorth)
      .south(AutoDirectionDemo::innerSouth)
      .westB(AutoDirectionDemo::innerWestBorder)
      .eastB(AutoDirectionDemo::innerEastBorder)
      .center(AutoDirectionDemo::innerCenterFlow);
  }

  static Pane innerNorth(Pane f){
    return f.height(h(54)).background(color(255,255,255,45))
      .widthInset(w(8)).heightInset(h(8))
      .label(l -> l.style(new RegionTitle("INNER NORTH: height fixed, width stretches")))
      .label(l -> l.style(new Pill("auto",redA,white)))
      .label(l -> l.style(new Pill("width=260",greenA,black)).width(w(260)));
  }

  static Pane innerSouth(Pane f){
    return f.background(color(255,255,255,45))
      .widthInset(w(8)).heightInset(h(8))
      .label(l -> l.style(new RegionTitle("INNER SOUTH: height auto")))
      .label(l -> l.style(new Pill("height=50",blueA,white)).height(h(50)))
      .label(l -> l.style(new Pill("auto height",yellowA,black)));
  }

  static Border innerWestBorder(Border b){
    return b.width(w(235)).background(color(0,0,0,90))
      .leftInset(w(8)).rightInset(w(8)).topInset(h(8)).bottomInset(h(8))
      .widthInset(w(8)).heightInset(h(8))
      .north(f -> f.label(l -> l.style(new RegionTitle("NESTED WESTB"))))
      .center(f -> f.background(color(255,255,255,35))
        .widthInset(w(6)).heightInset(h(6))
        .label(l -> l.style(new Pill("auto nested",redA,white)))
        .label(l -> l.style(new Pill("width=170",greenA,black)).width(w(170)))
        .label(l -> l.style(new Pill("height=64",blueA,white)).height(h(64))));
  }

  static Border innerEastBorder(Border b){
    return b.background(color(0,0,0,90))
      .leftInset(w(8)).rightInset(w(8)).topInset(h(8)).bottomInset(h(8))
      .widthInset(w(8)).heightInset(h(8))
      .north(f -> f.label(l -> l.style(new RegionTitle("NESTED EASTB auto width"))))
      .center(f -> f.background(color(255,255,255,35))
        .widthInset(w(6)).heightInset(h(6))
        .label(l -> l.style(new Pill("auto",cyanA,black)))
        .label(l -> l.style(new Pill("width=190",magentaA,white)).width(w(190)))
        .button(bt -> bt.style(new Push("height=56",yellowA,black)).height(h(56))));
  }

  static Pane innerCenterFlow(Pane f){
    return f.background(color(255,255,255,55))
      .widthInset(w(8)).heightInset(h(8))
      .label(l -> l.style(new RegionTitle("CENTER FLOW: resize window to force wrapping")))
      .label(l -> l.style(new Pill("auto label",redA,white)))
      .label(l -> l.style(new Pill("width=240, height auto",greenA,black)).width(w(240)))
      .label(l -> l.style(new Pill("height=58, width auto",blueA,white)).height(h(58)))
      .label(l -> l.style(new Pill("240x58",yellowA,black)).width(w(240)).height(h(58)))
      .button(b -> b.style(new Push("auto button",magentaA,white)))
      .button(b -> b.style(new Push("width=220",cyanA,black)).width(w(220)))
      .button(b -> b.style(new Push("height=60",darkA,white)).height(h(60)))
      .button(b -> b.style(new Push("220x60",color(255,120,0,150),black)).width(w(220)).height(h(60)));
  }
}