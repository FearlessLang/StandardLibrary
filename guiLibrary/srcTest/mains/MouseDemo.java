package mains;

import static mains.GuiTestSupport.*;

import base.*;

record MouseInfo(String text) implements Scope<Label>{
  public Label run(Label l){
    return l.text(text).textSize(h(18)).background(color(0,0,0,130)).foreground(white)
      .leftInset(w(14)).topInset(h(8)).rightInset(w(14)).bottomInset(h(8));
  }
}

record MouseBox(Color bg) implements Scope<Pane>{
  public Pane run(Pane f){
    return f.width(w(260)).height(h(120)).background(bg)
      .leftInset(w(8)).rightInset(w(8)).topInset(h(8)).bottomInset(h(8));
  }
}

public class MouseDemo{
  static Slot<Label> status=Slot.of();
  static Slot<Label> coords=Slot.of();
  static Slot<Pane> clickBox=Slot.of();
  static Slot<Pane> pressBox=Slot.of();
  static Slot<Pane> moveBox=Slot.of();
  static Slot<Pane> dragBox=Slot.of();

  public static void main(String[] args){
    new _FluentGUI().run(frame -> {
      frame.title("Mouse demo").resizable().fps(30);
      frame.content(MouseDemo::root);
    });
  }

  static Pane root(Pane f){
    return f.width(w(960)).height(h(620)).background(rootC)
      .leftInset(w(18)).rightInset(w(18)).topInset(h(18)).bottomInset(h(18))
      .widthInset(w(14)).heightInset(h(14))
      .label(l -> l.style(new MouseInfo("Move, click, press, release, enter, exit, and drag inside the colored empty boxes.")))
      .label(l -> l.style(new MouseInfo("status: waiting")).save(status))
      .label(l -> l.style(new MouseInfo("coords: -")).save(coords))
      .pane(MouseDemo::clickTarget)
      .pane(MouseDemo::pressTarget)
      .pane(MouseDemo::moveTarget)
      .pane(MouseDemo::dragTarget);
  }

  static Pane clickTarget(Pane f){
    return f.widthInset(w(10)).heightInset(h(10))
      .label(l -> l.style(new MouseInfo("CLICK target: clicked / entered / exited")))
      .pane(box -> box.style(new MouseBox(redA)).save(clickBox)
        .mouse(m -> m
          .entered(e -> {
            clickBox.get().background(color(255,80,80,170));
            say("entered CLICK",e);
          })
          .exited(e -> {
            clickBox.get().background(redA);
            say("exited CLICK",e);
          })
          .clicked(e -> {
            clickBox.get().background(yellowA);
            say("clicked CLICK",e);
          })));
  }

  static Pane pressTarget(Pane f){
    return f.widthInset(w(10)).heightInset(h(10))
      .label(l -> l.style(new MouseInfo("PRESS target: pressed changes green, released changes blue")))
      .pane(box -> box.style(new MouseBox(greenA)).save(pressBox)
        .mouse(m -> m
          .pressed(e -> {
            pressBox.get().background(greenA);
            say("pressed PRESS",e);
          })
          .released(e -> {
            pressBox.get().background(blueA);
            say("released PRESS",e);
          })
          .clicked(e -> say("clicked PRESS",e))));
  }

  static Pane moveTarget(Pane f){
    return f.widthInset(w(10)).heightInset(h(10))
      .label(l -> l.style(new MouseInfo("MOVE target: movement updates coordinates continuously")))
      .pane(box -> box.style(new MouseBox(cyanA)).save(moveBox)
        .mouse(m -> m
          .entered(e -> {
            moveBox.get().background(color(0,255,255,170));
            say("entered MOVE",e);
          })
          .moved(e -> {
            coords.get().text("coords: MOVE "+pos(e));
          })
          .exited(e -> {
            moveBox.get().background(cyanA);
            say("exited MOVE",e);
          })));
  }

  static Pane dragTarget(Pane f){
    return f.widthInset(w(10)).heightInset(h(10))
      .label(l -> l.style(new MouseInfo("DRAG target: press inside, then drag")))
      .pane(box -> box.style(new MouseBox(magentaA)).save(dragBox)
        .mouse(m -> m
          .pressed(e -> {
            dragBox.get().background(color(255,0,255,190));
            say("pressed DRAG",e);
          })
          .dragged(e -> {
            dragBox.get().background(color(255,255,0,160));
            coords.get().text("coords: DRAG "+pos(e));
          })
          .released(e -> {
            dragBox.get().background(magentaA);
            say("released DRAG",e);
          })));
  }

  static void say(String what,MouseEvent e){
    status.get().text("status: "+what+" at "+millis(e)+" ms");
    coords.get().text("coords: "+pos(e));
  }

  static String pos(MouseEvent e){
    return "x="+e.mouseX().x()+", y="+e.mouseY().y()
      +", panel="+e.panelSizeW().w()+"x"+e.panelSizeH().h();
  }

  static long millis(MouseEvent e){
    return e.elapsed().nanos()/1_000_000;
  }
}