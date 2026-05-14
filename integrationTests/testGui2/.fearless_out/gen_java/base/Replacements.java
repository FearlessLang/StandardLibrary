package base;

import java.util.List;

public final class Replacements{
  public static String replaceSimultaneousOrdered(List<Str$0Instance> rules, String text){
    validateRules(rules,false);
    return applyOrdered(rules,text);
  }
  public static String replaceSimultaneous(List<Str$0Instance> rules, String text){
    validateRules(rules,true);
    return applyOrdered(rules,text);
  }
  private static String applyOrdered(List<Str$0Instance> rules, String text){
    var out= new StringBuilder(text.length());
    for (int i= 0; i < text.length();){
      var next= tryRule(rules,text,i,out);
      if (next != -1){ i = next; continue; }
      out.append(text.charAt(i));
      i++;
    }
    return out.toString();
  }
  private static int tryRule(List<Str$0Instance> rules, String text, int i, StringBuilder out){
    for (int r= 0; r < rules.size(); r += 2){
      String from= rules.get(r).val();
      if (!text.startsWith(from,i)){ continue; }
      out.append(rules.get(r + 1).val());
      return i + from.length();
    }
    return -1;
  }
  private static void validateRules(List<Str$0Instance> rules, boolean checkPrefixes){
    if ((rules.size() & 1) != 0){ throw Util.err("Odd number of strings"); }
    for (int i= 0; i < rules.size(); i += 2){
      var ki= rules.get(i).val();
      if (ki.isEmpty()){ throw Util.err("Empty key in position "+i); }
      for (int j= i + 2; j < rules.size(); j += 2){
        var kj= rules.get(j).val();
        if(ki.equals(kj)){ throw Util.err("Duplicate key in positions "+i+" and "+j); }
        var prefix= checkPrefixes && (ki.startsWith(kj) || kj.startsWith(ki));
        if(prefix){ throw Util.err("Prefix clash in positions "+i+" and "+j); }
      }
    }
  }
}