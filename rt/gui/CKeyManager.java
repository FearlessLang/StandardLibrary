package base;

import static base.Scopes.*;
import static base.Util.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

final class CKeyManager extends KeyAdapter implements Keys$0{
  final _Frame frame;
  final List<KeyAction$0> pressed=new ArrayList<>();
  final List<KeyAction$0> released=new ArrayList<>();

  CKeyManager(_Frame frame){ this.frame=frame; }

  @Override public void keyPressed(KeyEvent e){ dispatch(e,pressed); }
  @Override public void keyReleased(KeyEvent e){ dispatch(e,released); }

  private void dispatch(KeyEvent e,List<KeyAction$0> keyActions){
    var event=new CKeyEventData(
      frame.elapsed,
      frame.screenSizeW,
      frame.screenSizeH,
      w(frame.frame.getWidth()),
      h(frame.frame.getHeight()),
      keyText(e)
      );

    frame.frame.queue.submit(new MF$1(){
      @Override public Object mut$$hash$0(){
        var run=snapshot(keyActions,event.keyText());
        if (run.actions().isEmpty()){ return Void$0.instance; }

        var ctx=new CKeyCtx(
          event.elapsed(),
          event.screenWidth(),
          event.screenHeight(),
          event.windowWidth(),
          event.windowHeight(),
          run.key()
          );

        for (var a:run.actions()){ a.mut$accept$1(ctx); }
        return Void$0.instance;
      }
    });
  }

  private static CKeyRun snapshot(List<KeyAction$0> keyActions,String eventKey){
    var actions=new ArrayList<Consumer$1>();
    KeyStroke$0 key=null;

    for (var ka:keyActions){
      var k=matchingKey((EList$1)ka.mut$match$0(),eventKey);
      if (k == null){ continue; }
      if (key == null){ key = k; }
      copyActions((EList$1)ka.mut$actions$0(),actions);
    }

    return new CKeyRun(key,actions);
  }

  private static KeyStroke$0 matchingKey(EList$1 match,String eventKey){
    long size= natToLong(match.read$size$0());
    for (long i=0;i < size;i++){
      var k=(KeyStroke$0)match.mut$get$1(n(i));
      if (keyText(k).equals(eventKey)){ return k; }
    }
    return null;
  }

  private static void copyActions(EList$1 from,List<Consumer$1> to){
    long size= natToLong(from.read$size$0());
    for (long i=0;i < size;i++){
      to.add((Consumer$1)from.mut$get$1(n(i)));
    }
  }

  private static String keyText(KeyStroke$0 k){
    return ((Str$0Instance)k.read$get$0()).val();
  }
  private static String keyText(KeyEvent e){ return KeyNames.of(e.getKeyCode()); }

  @Override public Object mut$pressed$1(Object scope){
    var keyAction=(KeyAction$0)KeyActions$0.instance.imm$$hash$0();
    ((Scope$1)scope).mut$run$1(keyAction);
    pressed.add(keyAction);
    return this;
  }

  @Override public Object mut$released$1(Object scope){
    var keyAction=(KeyAction$0)KeyActions$0.instance.imm$$hash$0();
    ((Scope$1)scope).mut$run$1(keyAction);
    released.add(keyAction);
    return this;
  }
}

record CKeyEventData(
  Time$0 elapsed,
  WidthNat$0 screenWidth,
  HeightNat$0 screenHeight,
  WidthNat$0 windowWidth,
  HeightNat$0 windowHeight,
  String keyText
  ){}

record CKeyRun(KeyStroke$0 key,List<Consumer$1> actions){}

record CKeyCtx(
  Time$0 elapsed,
  WidthNat$0 screenWidth,
  HeightNat$0 screenHeight,
  WidthNat$0 windowWidth,
  HeightNat$0 windowHeight,
  KeyStroke$0 keyStroke
  ) implements KeyEvent$0{
  @Override public Object imm$elapsed$0(){ return elapsed; }
  @Override public Object imm$screenWidth$0(){ return screenWidth; }
  @Override public Object imm$screenHeight$0(){ return screenHeight; }
  @Override public Object imm$windowWidth$0(){ return windowWidth; }
  @Override public Object imm$windowHeight$0(){ return windowHeight; }
  @Override public Object imm$keyStroke$0(){ return keyStroke; }
}