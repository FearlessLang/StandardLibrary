package _base;

import static _base.Util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface _InfoFromStrMessage$8m68$0 extends Sealed$2o$0{
  _InfoFromStrMessage$8m68$0 instance= new _InfoFromStrMessage$8m68$0(){};
  default Object imm$$hash$4(Object text, Object names, Object spans, Object why){
    var ns= List$o$1Instance.asJava(names).stream().map(Util::toS).toList();
    var ps= List$o$1Instance.asJava(spans).stream().map(n -> (int) Nat$c$0Instance.unwrap(n)).toList();
    return Str$c$0Instance.instance(InfoFromStrMessage.of(toS(text), ns, ps, toS(why)));
  }
}
final class InfoFromStrMessage{
  private record Span(int sl, int sc, int el, int ec){ boolean single(){ return sl == el; } }
  static String of(String text, List<String> names, List<Integer> offsets, String why){
    var lines= text.split("\n", -1);
    var width= Math.max(3, String.valueOf(lines.length).length());
    var fs= new ArrayList<int[]>();
    for (int i= 0; i < names.size(); i += 1){ fs.add(new int[]{offsets.get(2 * i), offsets.get(2 * i + 1)}); }
    for (int i= 0; i + 1 < fs.size(); i += 1){
      var in= fs.get(i);
      var out= fs.get(i + 1);
      out[0]= Math.min(in[0], out[0]);
      out[1]= Math.max(in[1], out[1]);
    }
    var spans= fs.stream().map(f -> span(text, f[0], f[1])).toList();
    var leading= new ArrayList<Span>();
    for (var s : spans){ if (!s.single()){ break; } leading.add(s); }
    int target= leading.isEmpty() ? 0 : leading.getLast().sl();
    var chosen= leading.stream().filter(s -> s.sl() == target).distinct().limit(3).toList().reversed();
    var firstMulti= spans.stream().filter(s -> !s.single()).findFirst().orElse(null);
    int caret= !chosen.isEmpty() ? chosen.getLast().sl() : firstMulti != null ? firstMulti.sl() : spans.getLast().sl();
    Optional<String> caretLine= chosen.isEmpty() ? Optional.empty() : Optional.of(caretLine(lines, chosen, caret, width));
    var body= firstMulti != null
      ? multi(lines, firstMulti, caret, width, caretLine)
      : numbered(lines, caret, width)+"\n"+caretLine.orElse("");
    var named= names.stream().filter(n -> !n.isBlank()).toList();
    var framesLine= named.isEmpty() ? "While inspecting the text" : "While inspecting "+String.join(" > ", named);
    return "In the text given to Infos.fromStr:\n\n"+body+"\n\n"+framesLine+"\n"+why;
  }
  private static boolean visible(String text, int i){ return i < text.length() && text.charAt(i) != ' ' && text.charAt(i) != '\n'; }
  private static Span span(String text, int from, int to){
    int a= from;
    while (a < to && !visible(text, a)){ a += 1; }
    int b= to;
    while (b > a && !visible(text, b)){ b -= 1; }
    return new Span(line(text, a), col(text, a), line(text, b), col(text, b));
  }
  private static int line(String text, int o){ return 1 + (int) text.substring(0, Math.min(o, text.length())).chars().filter(c -> c == '\n').count(); }
  private static int col(String text, int o){ return o - text.lastIndexOf('\n', o - 1); }
  private static String get(String[] lines, int oneBased){ return oneBased < 1 || oneBased > lines.length ? "" : lines[oneBased - 1]; }
  private static String numbered(String[] lines, int line, int width){ return "0".repeat(width - String.valueOf(line).length())+line+"| "+get(lines, line); }
  private static String caretLine(String[] lines, List<Span> chosen, int caret, int width){
    var raw= get(lines, caret);
    var marks= chosen.size() == 1 ? "^" : chosen.size() == 2 ? "-^" : "-~^";
    var carr= new StringBuilder();
    for (int i= 0; i < chosen.size(); i += 1){
      var s= chosen.get(i);
      int a= s.sc();
      int last= Math.min(s.ec(), raw.length());
      int b= a + Math.max(1, last - a + 1) - 1;
      carr.append(" ".repeat(Math.max(0, b - carr.length())));
      for (int c= a; c <= b; c += 1){ carr.setCharAt(c - 1, marks.charAt(i)); }
    }
    carr.setLength(Math.min(carr.length(), raw.length()));
    return " ".repeat(width)+"| "+carr;
  }
  private static void elision(ArrayList<String> out, String[] lines, int width, int count, int oneLine){
    if (count == 1){ out.add(numbered(lines, oneLine, width)); return; }
    if (count > 1){ out.add(" ".repeat(width)+"| ... "+count+" lines ..."); }
  }
  private static String multi(String[] lines, Span group, int caret, int width, Optional<String> caretLine){
    var out= new ArrayList<String>();
    if (caret != group.sl()){
      out.add(numbered(lines, group.sl(), width));
      elision(out, lines, width, caret - group.sl() - 1, caret - 1);
    }
    out.add(numbered(lines, caret, width));
    caretLine.ifPresent(out::add);
    if (caret != group.el()){
      elision(out, lines, width, group.el() - caret - 1, caret + 1);
      out.add(numbered(lines, group.el(), width));
    }
    return String.join("\n", out);
  }
}
