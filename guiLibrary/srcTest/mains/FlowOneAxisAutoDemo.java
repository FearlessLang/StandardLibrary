package mains;

import static mains.GuiTestSupport.*;

import base.*;

public class FlowOneAxisAutoDemo{
  public static void main(String[] args){
    new _FluentGUI().run(frame -> {
      frame.title("Flow one-axis auto demo").resizable().fps(30);
      frame.content(FlowOneAxisAutoDemo::root);
    });
  }

  static Pane root(Pane f){
    return f
      .width(w(900)).height(h(520))
      .background(rootC)
      .leftInset(w(18)).rightInset(w(18)).topInset(h(18)).bottomInset(h(18))
      .widthInset(w(12)).heightInset(h(12))
      .label(l -> l
        .text("FLOW: children keep preferred size; resizing only changes wrapping/centering")
        .textSize(h(22)).background(color(0,0,0,150)).foreground(white)
        .leftInset(w(18)).topInset(h(10)).rightInset(w(18)).bottomInset(h(10)))
      .pane(FlowOneAxisAutoDemo::widthOnlyFlow)
      .pane(FlowOneAxisAutoDemo::heightOnlyFlow)
      .pane(FlowOneAxisAutoDemo::autoFlow)
      .pane(FlowOneAxisAutoDemo::bothFixedFlow)
      .label(l -> l
        .text("Resize window: boxes do not stretch; they only move/wrap.")
        .textSize(h(20)).background(yellowA).foreground(black)
        .leftInset(w(18)).topInset(h(10)).rightInset(w(18)).bottomInset(h(10)));
  }

  static Pane widthOnlyFlow(Pane f){
    return f
      .width(w(330))       // width fixed, height auto
      .background(color(80,0,0,220))
      .leftInset(w(12)).rightInset(w(12)).topInset(h(12)).bottomInset(h(12))
      .widthInset(w(8)).heightInset(h(8))
      .label(l -> l
        .text("FLOW width=330, height auto")
        .textSize(h(20)).background(redA).foreground(white)
        .leftInset(w(14)).topInset(h(8)).rightInset(w(14)).bottomInset(h(8)))
      .label(l -> l
        .text("child width=230")
        .textSize(h(18)).background(greenA).foreground(black)
        .width(w(230))
        .leftInset(w(12)).topInset(h(8)).rightInset(w(12)).bottomInset(h(8)))
      .button(b -> b
        .text("button height auto")
        .textSize(h(18)).background(magentaA).foreground(white)
        .action(() -> System.out.println("width-only flow"))
        .leftInset(w(12)).topInset(h(8)).rightInset(w(12)).bottomInset(h(8)));
  }

  static Pane heightOnlyFlow(Pane f){
    return f
      .height(h(150))      // height fixed, width auto
      .background(color(0,0,90,220))
      .leftInset(w(12)).rightInset(w(12)).topInset(h(12)).bottomInset(h(12))
      .widthInset(w(8)).heightInset(h(8))
      .label(l -> l
        .text("FLOW height=150")
        .textSize(h(20)).background(blueA).foreground(white)
        .leftInset(w(14)).topInset(h(8)).rightInset(w(14)).bottomInset(h(8)))
      .label(l -> l
        .text("width auto")
        .textSize(h(18)).background(cyanA).foreground(black)
        .leftInset(w(12)).topInset(h(8)).rightInset(w(12)).bottomInset(h(8)))
      .button(b -> b
        .text("button width auto")
        .textSize(h(18)).background(yellowA).foreground(black)
        .action(() -> System.out.println("height-only flow"))
        .leftInset(w(12)).topInset(h(8)).rightInset(w(12)).bottomInset(h(8)));
  }

  static Pane autoFlow(Pane f){
    return f
      .background(color(0,80,0,210)) // width auto, height auto
      .leftInset(w(12)).rightInset(w(12)).topInset(h(12)).bottomInset(h(12))
      .widthInset(w(8)).heightInset(h(8))
      .label(l -> l
        .text("FLOW auto x auto")
        .textSize(h(20)).background(greenA).foreground(black)
        .leftInset(w(14)).topInset(h(8)).rightInset(w(14)).bottomInset(h(8)))
      .label(l -> l
        .text("label height=64")
        .textSize(h(18)).background(redA).foreground(white)
        .height(h(64))
        .leftInset(w(12)).topInset(h(8)).rightInset(w(12)).bottomInset(h(8)))
      .button(b -> b
        .text("button width=210")
        .textSize(h(18)).background(magentaA).foreground(white)
        .width(w(210))
        .action(() -> System.out.println("auto flow"))
        .leftInset(w(12)).topInset(h(8)).rightInset(w(12)).bottomInset(h(8)));
  }

  static Pane bothFixedFlow(Pane f){
    return f
      .width(w(330)).height(h(150))
      .background(color(80,40,0,220))
      .leftInset(w(12)).rightInset(w(12)).topInset(h(12)).bottomInset(h(12))
      .widthInset(w(8)).heightInset(h(8))
      .label(l -> l
        .text("FLOW 330 x 150")
        .textSize(h(20)).background(yellowA).foreground(black)
        .leftInset(w(14)).topInset(h(8)).rightInset(w(14)).bottomInset(h(8)))
      .label(l -> l
        .text("child 220 x 60")
        .textSize(h(18)).background(blueA).foreground(white)
        .width(w(220)).height(h(60))
        .leftInset(w(12)).topInset(h(8)).rightInset(w(12)).bottomInset(h(8)));
  }
}