package mains;

import static mains.GuiTestSupport.*;

import base.*;

record KeyInfo(String text) implements Scope<Label>{
  public Label run(Label l){
    return l.text(text).textSize(h(18)).background(color(0,0,0,130)).foreground(white)
      .leftInset(w(14)).topInset(h(8)).rightInset(w(14)).bottomInset(h(8));
  }
}

public class KeyboardDemo{
  static Slot<Label> status=Slot.of();
  static Slot<Label> last=Slot.of();
  static Slot<Label> held=Slot.of();
  static Slot<Pane> box=Slot.of();

  static int x=0;
  static int y=0;
  static boolean spaceDown=false;

  public static void main(String[] args){
    new _FluentGUI().run(frame -> {
      frame.title("Keyboard demo").resizable().fps(30);
      frame.onKey(k -> k
        .pressed(() -> new KeyStroke("pressed LEFT"), e -> move("LEFT", -1, 0, e))
        .pressed(() -> new KeyStroke("pressed RIGHT"), e -> move("RIGHT", 1, 0, e))
        .pressed(() -> new KeyStroke("pressed UP"), e -> move("UP", 0, -1, e))
        .pressed(() -> new KeyStroke("pressed DOWN"), e -> move("DOWN", 0, 1, e))
        .pressed(() -> new KeyStroke("pressed SPACE"), KeyboardDemo::spacePressed)
        .released(() -> new KeyStroke("released SPACE"), KeyboardDemo::spaceReleased)
        .pressed(() -> new KeyStroke("pressed R"), KeyboardDemo::reset)
        );
      frame.content(KeyboardDemo::root);
    });
  }

  static Pane root(Pane f){
    return f.width(w(880)).height(h(560)).background(rootC)
      .leftInset(w(18)).rightInset(w(18)).topInset(h(18)).bottomInset(h(18))
      .widthInset(w(14)).heightInset(h(14))
      .label(l -> l.style(new KeyInfo("Keyboard demo: arrow keys move the box; SPACE tests pressed/released; R resets.")))
      .label(l -> l.style(new KeyInfo("status: waiting")).save(status))
      .label(l -> l.style(new KeyInfo("last key: -")).save(last))
      .label(l -> l.style(new KeyInfo("held: SPACE is up")).save(held))
      .pane(KeyboardDemo::playArea);
  }

  static Pane playArea(Pane f){
    return f.width(w(760)).height(h(310)).background(color(255,255,255,35))
      .leftInset(w(20)).rightInset(w(20)).topInset(h(20)).bottomInset(h(20))
      .widthInset(w(10)).heightInset(h(10))
      .pane(b -> b.width(w(120)).height(h(90)).background(blueA)
        .leftInset(w(12)).rightInset(w(12)).topInset(h(12)).bottomInset(h(12))
        .save(box)
        .label(l -> l.text("BOX").textSize(h(26)).background(transparent).foreground(white)
          .leftInset(w(8)).topInset(h(8)).rightInset(w(8)).bottomInset(h(8))))
      .label(l -> l.text("The box changes size/insets as a visible state update.")
        .textSize(h(18)).background(yellowA).foreground(black)
        .leftInset(w(14)).topInset(h(8)).rightInset(w(14)).bottomInset(h(8)));
  }

  static void move(String key,int dx,int dy,KeyEvent e){
    x+=dx;
    y+=dy;
    var ww=120+x*12;
    var hh=90+y*10;
    if (ww<70){ ww=70; }
    if (hh<50){ hh=50; }

    box.get()
      .width(w(ww)).height(h(hh))
      .background(color(
        Math.min(255,80+Math.max(0,x)*20),
        Math.min(255,80+Math.max(0,y)*20),
        220,
        160));

    say("pressed "+key,e);
    last.get().text("last key: "+key+" -> box "+ww+"x"+hh);
  }

  static void spacePressed(KeyEvent e){
    spaceDown=true;
    box.get()
      .leftInset(w(28)).rightInset(w(28)).topInset(h(20)).bottomInset(h(20))
      .background(magentaA);
    say("pressed SPACE",e);
    held.get().text("held: SPACE is down");
  }

  static void spaceReleased(KeyEvent e){
    spaceDown=false;
    box.get()
      .leftInset(w(12)).rightInset(w(12)).topInset(h(12)).bottomInset(h(12))
      .background(blueA);
    say("released SPACE",e);
    held.get().text("held: SPACE is up");
  }

  static void reset(KeyEvent e){
    x=0;
    y=0;
    spaceDown=false;
    box.get()
      .width(w(120)).height(h(90))
      .leftInset(w(12)).rightInset(w(12)).topInset(h(12)).bottomInset(h(12))
      .background(blueA);
    say("pressed R: reset",e);
    last.get().text("last key: R -> reset");
    held.get().text("held: SPACE is up");
  }

  static void say(String what,KeyEvent e){
    status.get().text("status: "+what+" at "+millis(e)+" ms"
      +", panel="+e.panelSizeW().w()+"x"+e.panelSizeH().h());
  }

  static long millis(KeyEvent e){
    return e.elapsed().nanos()/1_000_000;
  }
}