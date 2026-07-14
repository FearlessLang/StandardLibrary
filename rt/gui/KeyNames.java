package base;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// Locale-independent key names. KeyEvent.getKeyText returns localized,
// platform-dependent strings, so KeyStroke matching would behave differently
// per machine. Names here come from the VK_* constant names, which are fixed
// by the Java API specification: VK_ENTER -> "ENTER", VK_BACK_SPACE ->
// "BACK_SPACE", VK_CONTROL -> "CONTROL", VK_A -> "A", VK_0 -> "0".
final class KeyNames{
  private static final Map<Integer, String> names = load();

  private KeyNames(){}

  static String of(int keyCode){
    var s = names.get(keyCode);
    return s != null ? s : "UNKNOWN_KEY_" + keyCode;
  }

  private static Map<Integer, String> load(){
    var byName = new TreeMap<String, Integer>();// sorted: deterministic if two VK_ share a code
    for (Field f : KeyEvent.class.getFields()){
      if (!f.getName().startsWith("VK_")){ continue; }
      if (f.getType() != int.class || !Modifier.isStatic(f.getModifiers())){ continue; }
      try { byName.put(f.getName().substring(3), f.getInt(null)); }
      catch (IllegalAccessException e){ throw new Error(e); }
    }
    var res = new HashMap<Integer, String>();
    byName.forEach((n, c) -> res.putIfAbsent(c, n));
    return Map.copyOf(res);
  }
}