package mains;

import static mains.GuiTestSupport.*;

import base.*;

public class InsetsStyleDemo{
  static Slot<Label> status=Slot.of();

  public static void main(String[] args){
    new _FluentGUI().run(frame -> frame
      .title("Insets style demo").resizable().fps(30)
      .content(root -> root
        .width(w(1150)).height(h(760))
        .background(rootC)
        .widthInset(w(18))
        .heightInset(h(18))
        .paint(g -> g
          .color(blueA).position(x(20),y(20)).rect(w(300),h(80))
          .color(yellowA).position(x(180),y(50)).oval(w(260),h(90)))
        .border(InsetsStyleDemo::autoLabels)
        .border(InsetsStyleDemo::fixedLabels)
        .border(InsetsStyleDemo::autoButtons)
        .border(InsetsStyleDemo::mixedBorder)
        .pane(InsetsStyleDemo::statusRow))
      );
  }
  static Pane statusRow(Pane f){ return f
    .width(w(1050)).height(h(90))
    .background(darkA)
    .widthInset(w(12))
    .heightInset(h(12))
    .label(l -> l
      .text("STATUS").textSize(h(20)).background(cyanA).foreground(black)
      .leftInset(w(30)).topInset(h(10)).rightInset(w(30)).bottomInset(h(10))
      .save(status))
    .button(b -> b
      .text("CHANGE").textSize(h(18)).background(magentaA).foreground(white)
      .action(InsetsStyleDemo::changeStatus)
      .leftInset(w(28)).topInset(h(10)).rightInset(w(28)).bottomInset(h(10)));
  }
  static void changeStatus(){ status.get()
    .text("CHANGED")
    .leftInset(w(70)).rightInset(w(70)).topInset(h(18)).bottomInset(h(18))
    .background(yellowA);
  }
  static Border autoLabels(Border b){ return b
      .style(new Card(color(30,30,30,210)))
      .north(f -> f.label(l -> l.style(new Header("AUTO LABELS"))))
      .center(f -> f.style(new Inner())
        .label(l -> l
          .text("TEXT").textSize(h(22)).background(redA).foreground(white)
          .leftInset(w(0)).topInset(h(0)).rightInset(w(0)).bottomInset(h(0)))
        .label(l -> l
          .text("TEXT").textSize(h(22)).background(greenA).foreground(black)
          .leftInset(w(16)).topInset(h(6)).rightInset(w(16)).bottomInset(h(6)))
        .label(l -> l
          .text("TEXT").textSize(h(22)).background(blueA).foreground(white)
          .leftInset(w(44)).topInset(h(16)).rightInset(w(44)).bottomInset(h(16))));
  }

  static Border fixedLabels(Border b){
    return b.style(new Card(color(0,40,100,180)))
      .north(f -> f.label(l -> l.style(new Header("FIXED LABELS"))))
      .center(f -> f.style(new Inner())
        .label(l -> l
          .text("TEXT").textSize(h(22)).background(redA).foreground(white)
          .width(w(150)).height(h(70))
          .leftInset(w(0)).topInset(h(0)).rightInset(w(0)).bottomInset(h(0)))
        .label(l -> l
          .text("TEXT").textSize(h(22)).background(greenA).foreground(black)
          .width(w(150)).height(h(70))
          .leftInset(w(30)).topInset(h(16)).rightInset(w(30)).bottomInset(h(16)))
        .label(l -> l
          .text("TEXT").textSize(h(22)).background(blueA).foreground(white)
          .width(w(150)).height(h(70))
          .leftInset(w(90)).topInset(h(24)).rightInset(w(28)).bottomInset(h(4))));
  }

  static Border autoButtons(Border b){
    return b.style(new Card(color(70,20,70,190)))
      .north(f -> f.label(l -> l.style(new Header("AUTO BUTTONS"))))
      .center(f -> f.style(new Inner())
        .button(b0 -> b0
          .text("GO").textSize(h(22)).background(redA).foreground(white)
          .action(() -> System.out.println("go 0"))
          .leftInset(w(0)).topInset(h(0)).rightInset(w(0)).bottomInset(h(0)))
        .button(b0 -> b0
          .text("GO").textSize(h(22)).background(greenA).foreground(black)
          .action(() -> System.out.println("go 1"))
          .leftInset(w(18)).topInset(h(8)).rightInset(w(18)).bottomInset(h(8)))
        .button(b0 -> b0
          .text("GO").textSize(h(22)).background(blueA).foreground(white)
          .action(() -> System.out.println("go 2"))
          .leftInset(w(72)).topInset(h(28)).rightInset(w(42)).bottomInset(h(28))));
  }

  static Border mixedBorder(Border b){
    return b.width(w(520)).height(h(210)).background(color(0,0,0,120))
      .leftInset(w(24)).rightInset(w(24)).topInset(h(24)).bottomInset(h(24))
      .widthInset(w(16)).heightInset(h(16))
      .paint(g -> g
        .color(yellowA).position(x(8),y(8)).rect(w(160),h(45))
        .color(cyanA).position(x(80),y(35)).oval(w(200),h(70)))
      .north(f -> f.label(l -> l
        .text("BORDER").textSize(h(18)).background(redA).foreground(white)
        .leftInset(w(20)).topInset(h(6)).rightInset(w(20)).bottomInset(h(6))))
      .west(f -> f.label(l -> l
        .text("W").textSize(h(18)).background(greenA).foreground(black)
        .width(w(80)).height(h(70))
        .leftInset(w(20)).topInset(h(10)).rightInset(w(20)).bottomInset(h(10))))
      .east(f -> f.button(b0 -> b0
        .text("E").textSize(h(18)).background(magentaA).foreground(white)
        .width(w(80)).height(h(70))
        .action(() -> System.out.println("east"))
        .leftInset(w(20)).topInset(h(10)).rightInset(w(20)).bottomInset(h(10))))
      .center(f -> f.background(color(255,255,255,40))
        .widthInset(w(10)).heightInset(h(10))
        .label(l -> l
          .text("TEXT").textSize(h(22)).background(blueA).foreground(white)
          .leftInset(w(40)).topInset(h(14)).rightInset(w(40)).bottomInset(h(14))));
  }
}