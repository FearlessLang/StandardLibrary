package _base;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Locale-independent key names. KeyEvent.getKeyText returns localized,
// platform-dependent strings, so KeyStroke matching would behave differently
// per machine. Names here come from the VK_* constant names, which are fixed
// by the Java API specification: VK_ENTER -> "ENTER", VK_BACK_SPACE ->
// "BACK_SPACE", VK_CONTROL -> "CONTROL", VK_A -> "A", VK_0 -> "0".
public interface _KeyNames$b4$0 extends Sealed$2o$0{
  Map<String,Integer> codes= load();
  default Object imm$isName$1(Object p0){ return Util.bool(codes.containsKey(((Str$c$0Instance) p0).val())); }
  default Object imm$isChord$1(Object p0){ return Util.bool(isChord(((Str$c$0Instance) p0).val())); }
  private static boolean isChord(String chord){
    var ks= List.of(chord.split("\\+", -1));
    var mods= ks.subList(0, ks.size() - 1);
    var key= ks.getLast();
    boolean once= Set.copyOf(mods).size() == mods.size();
    return once && Set.of("CONTROL", "ALT", "SHIFT").containsAll(mods) && codes.containsKey(key) && !Set.of("CONTROL", "ALT", "SHIFT", "META", "ALT_GRAPH", "WINDOWS").contains(key);
  }
  private static Map<String,Integer> load(){
    var res= new HashMap<String,Integer>();
    for (Field f : KeyEvent.class.getFields()){
      if (!f.getName().startsWith("VK_")){ continue; }
      try{ res.put(f.getName().substring(3), f.getInt(null)); }
      catch (IllegalAccessException e){ throw new Error(e); }
    }
    return Map.copyOf(res);
  }
  _KeyNames$b4$0 instance= new _KeyNames$b4$0(){};
}
