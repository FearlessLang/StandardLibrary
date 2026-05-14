package mains;

import static mains.GuiTestSupport.*;

import base.*;

public class OneAxisResizeDemo{
  public static void main(String[] args){
    new _FluentGUI().run(frame -> {
      frame.title("One-axis resize demo").resizable().fps(30);
      frame.contentB(OneAxisResizeDemo::root);
    });
  }

  static Border root(Border b){
    return b
      .width(w(820)).height(h(520)) // only initial preferred size for pack
      .background(rootC)
      .leftInset(w(18)).rightInset(w(18)).topInset(h(18)).bottomInset(h(18))
      .widthInset(w(12)).heightInset(h(12))
      .north(OneAxisResizeDemo::onlyWider)
      .west(OneAxisResizeDemo::onlyTaller)
      .center(OneAxisResizeDemo::explanation);
  }

  static Pane onlyWider(Pane f){
    return f
      .height(h(90)) // fixed height, auto/stretched width
      .background(color(80,0,0,220))
      .widthInset(w(10)).heightInset(h(10))
      .label(l -> l
        .text("ONLY WIDER")
        .textSize(h(26))
        .background(redA)
        .foreground(white)
        .leftInset(w(20)).topInset(h(10)).rightInset(w(20)).bottomInset(h(10)))
      .label(l -> l
        .text("height fixed to 90; resize window horizontally")
        .textSize(h(18))
        .background(yellowA)
        .foreground(black)
        .leftInset(w(18)).topInset(h(8)).rightInset(w(18)).bottomInset(h(8)));
  }

  static Pane onlyTaller(Pane f){
    return f
      .width(w(220)) // fixed width, auto/stretched height
      .background(color(0,0,100,220))
      .widthInset(w(10)).heightInset(h(10))
      .label(l -> l
        .text("ONLY")
        .textSize(h(24))
        .background(blueA)
        .foreground(white)
        .leftInset(w(20)).topInset(h(10)).rightInset(w(20)).bottomInset(h(10)))
      .label(l -> l
        .text("TALLER")
        .textSize(h(24))
        .background(blueA)
        .foreground(white)
        .leftInset(w(20)).topInset(h(10)).rightInset(w(20)).bottomInset(h(10)))
      .label(l -> l
        .text("width fixed")
        .textSize(h(18))
        .background(cyanA)
        .foreground(black)
        .leftInset(w(16)).topInset(h(8)).rightInset(w(16)).bottomInset(h(8)))
      .label(l -> l
        .text("height follows window")
        .textSize(h(18))
        .background(greenA)
        .foreground(black)
        .leftInset(w(16)).topInset(h(8)).rightInset(w(16)).bottomInset(h(8)));
  }

  static Pane explanation(Pane f){
    return f
      .background(color(255,255,255,40))
      .widthInset(w(10)).heightInset(h(10))
      .label(l -> l
        .text("Resize the window")
        .textSize(h(28))
        .background(color(0,0,0,130))
        .foreground(white)
        .leftInset(w(24)).topInset(h(12)).rightInset(w(24)).bottomInset(h(12)))
      .label(l -> l
        .text("Red top region: should change width only")
        .textSize(h(20))
        .background(redA)
        .foreground(white)
        .leftInset(w(20)).topInset(h(10)).rightInset(w(20)).bottomInset(h(10)))
      .label(l -> l
        .text("Blue left region: should change height only")
        .textSize(h(20))
        .background(blueA)
        .foreground(white)
        .leftInset(w(20)).topInset(h(10)).rightInset(w(20)).bottomInset(h(10)));
  }
}