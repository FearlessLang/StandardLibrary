package base;

import static base.Scopes.*;
import static base.Util.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

final class CKeyManager extends KeyAdapter implements Keys$o$0, java.awt.event.WindowFocusListener{
  final _Frame frame;
  final ArrayList<KeyAction$m8$0> pressed=new ArrayList<>();
  final ArrayList<KeyAction$m8$0> released=new ArrayList<>();
  // Keys currently down, tracked so a lost window focus (alt-tab, OS focus
  // switch) can synthesize the releases AWT will otherwise never deliver:
  // without this, a key held during a focus switch leaves any state the
  // model set on .pressed (e.g. "moving left") stuck forever, since the
  // matching .released handler never runs.
  private final java.util.Set<String> held=new java.util.HashSet<>();

  CKeyManager(_Frame frame){ this.frame=frame; }

  @Override public void keyPressed(KeyEvent e){
    var k=keyText(e);
    held.add(k);
    dispatch(k,pressed);
  }
  @Override public void keyReleased(KeyEvent e){
    var k=keyText(e);
    if (held.remove(k)){ dispatch(k,released); }
  }
  @Override public void windowLostFocus(java.awt.event.WindowEvent e){
    if (held.isEmpty()){ return; }
    var keys=new ArrayList<>(held);
    held.clear();
    for (var k:keys){ dispatch(k,released); }
  }
  @Override public void windowGainedFocus(java.awt.event.WindowEvent e){}

  private void dispatch(String eventKey,List<KeyAction$m8$0> keyActions){
    var elapsed=frame.elapsed;
    var screenWidth=frame.screenWidth;
    var screenHeight=frame.screenHeight;
    var panelWidth=w(frame.top.component.getWidth());
    var panelHeight=h(frame.top.component.getHeight());

    frame.frame.queue.submit(new MF$7$1(){
      @Override public Object mut$$hash$0(){
        var actions=new ArrayList<Consumer$ao$1>();
        KeyStroke$m8$0 key=null;
        for (var ka:keyActions){
          var k=matchingKey((EList$1k$1)ka.mut$match$0(),eventKey);
          if (k == null){ continue; }
          if (key == null){ key = k; }
          copyActions((EList$1k$1)ka.mut$actions$0(),actions);
        }
        var ctx=new CKeyCtx(elapsed,screenWidth,screenHeight,panelWidth,panelHeight,key);
        for (var a:actions){ a.mut$accept$1(ctx); }
        return Void$o$0.instance;
      }
    });
  }

  private static KeyStroke$m8$0 matchingKey(EList$1k$1 match,String eventKey){
    int size=natToInt(match.read$size$0());
    for (long i=0;i < size;i++){
      var k=(KeyStroke$m8$0)match.mut$get$1(n(i));
      if (keyText(k).equals(eventKey)){ return k; }
    }
    return null;
  }

  private static void copyActions(EList$1k$1 from,List<Consumer$ao$1> to){
    long size=natToLong(from.read$size$0());
    for (long i=0;i < size;i++){
      to.add((Consumer$ao$1)from.mut$get$1(n(i)));
    }
  }

  private static String keyText(KeyStroke$m8$0 k){
    return ((Str$c$0Instance)k.read$get$0()).val();
  }
  private static String keyText(KeyEvent e){ return KeyNames.of(e.getKeyCode()); }

  @Override public Object mut$pressed$1(Object scope){ return addKeyAction(scope,pressed); }
  @Override public Object mut$released$1(Object scope){ return addKeyAction(scope,released); }

  private Object addKeyAction(Object scope,List<KeyAction$m8$0> list){
    var keyAction=(KeyAction$m8$0)KeyActions$18g$0.instance.imm$$hash$0();
    ((Scope$1c$1)scope).mut$run$1(keyAction);
    list.add(keyAction);
    return this;
  }
}

record CKeyCtx(
  Instant$5c$0 elapsed,
  WidthNat$as$0 screenWidth,
  HeightNat$lg$0 screenHeight,
  WidthNat$as$0 panelWidth,
  HeightNat$lg$0 panelHeight,
  KeyStroke$m8$0 keyStroke
  ) implements KeyEvent$b4$0{
  @Override public Object read$elapsed$0(){ return elapsed; }
  @Override public Object read$screenWidth$0(){ return screenWidth; }
  @Override public Object read$screenHeight$0(){ return screenHeight; }
  @Override public Object read$panelWidth$0(){ return panelWidth; }
  @Override public Object read$panelHeight$0(){ return panelHeight; }
  @Override public Object imm$keyStroke$0(){ return keyStroke; }
}
