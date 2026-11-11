package _base;

import static _base.Scopes.*;
import static _base.Util.*;

import java.util.ArrayList;
import java.util.List;

final class CKeyManager implements Keys$o$0{
  final _Frame frame;
  final ArrayList<CKeyAction> pressed= new ArrayList<>();
  final ArrayList<CKeyAction> released= new ArrayList<>();

  boolean installed;// EDT confined

  CKeyManager(_Frame frame){ this.frame= frame; }

  MF$7$1 task(int eventKey, List<CKeyAction> keyActions){
    var elapsed= frame.elapsed;
    var panelWidth= w(frame.top.component.getWidth());
    var panelHeight= h(frame.top.component.getHeight());

    return new MF$7$1(){
      @Override public Object mut$$hash$0(){
        var actions= new ArrayList<Consumer$ao$1>();
        KeyStroke$m8$0 key= null;
        for (var ka : keyActions){
          var k= ka.match.stream().filter(m -> _KeyNames$b4$0.codes.get(((Str$c$0Instance)m.read$get$0()).val()) == eventKey).findFirst().orElse(null);
          if (k == null){ continue; }
          if (key == null){ key = k; }
          actions.addAll(ka.actions);
        }
        var ctx= new CKeyCtx(elapsed, frame, panelWidth, panelHeight, key);
        for (var a : actions){ a.mut$accept$1(ctx); }
        return Void$o$0.instance;
      }
    };
  }

  @Override public Object mut$pressed$1(Object scope){ return addKeyAction(scope, pressed); }
  @Override public Object mut$released$1(Object scope){ return addKeyAction(scope, released); }

  void check(String closed, String replaced){
    frame.onEdtAndWait(() -> {
      if (frame.guiRun.completed()){ throw Util.detErr(closed); }
      if (installed && frame.keys != this){ throw Util.detErr(replaced); }
    });
  }
  private Object addKeyAction(Object scope, List<CKeyAction> list){
    check("This Keys belongs to a closed window: the CapGui.run that showed the window has returned, so a key action added now would never run", "This Keys was replaced by a later .onKey, so a key action added now would never run");
    var keyAction= new CKeyAction(this);
    ((Scope$1c$1)scope).mut$run$1(keyAction);
    list.add(keyAction);
    return this;
  }
}

final class CKeyAction implements KeyAction$m8$0{
  final CKeyManager keys;
  final ArrayList<KeyStroke$m8$0> match= new ArrayList<>();
  final ArrayList<Consumer$ao$1> actions= new ArrayList<>();
  CKeyAction(CKeyManager keys){ this.keys= keys; }
  private void check(){ keys.check("This KeyAction belongs to a closed window: the CapGui.run that showed the window has returned, so a key or an action added now would never run", "This KeyAction belongs to a Keys replaced by a later .onKey, so a key or an action added now would never run"); }
  @Override public Object mut$matchOnly$1(Object k){ check(); match.clear(); match.add((KeyStroke$m8$0)k); return this; }
  @Override public Object mut$matchAlso$1(Object k){ check(); match.add((KeyStroke$m8$0)k); return this; }
  @Override public Object mut$doOnly$1(Object c){ check(); actions.clear(); actions.add((Consumer$ao$1)c); return this; }
  @Override public Object mut$doAlso$1(Object c){ check(); actions.add((Consumer$ao$1)c); return this; }
}

record CKeyCtx(
  Instant$5c$0 elapsed,
  _Frame frame,
  WidthNat$as$0 panelWidth,
  HeightNat$lg$0 panelHeight,
  KeyStroke$m8$0 keyStroke
  ) implements KeyEvent$b4$0{
  @Override public Object read$elapsed$0(){ return elapsed; }
  @Override public Object read$panelWidth$0(){ return panelWidth; }
  @Override public Object read$panelHeight$0(){ return panelHeight; }
  @Override public Object read$keyStroke$0(){ return keyStroke; }
}
