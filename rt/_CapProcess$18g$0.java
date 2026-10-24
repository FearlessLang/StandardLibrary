package _base;

import static _base.Util.*;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.lang.management.ManagementFactory;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class _CapProcess$18g$0 implements CapProcess$18g$0{
  static final String namesKey= "fearlessAttenuators";
  static final String markerKey= "fearlessProcess.resultMarker";
  private static final AtomicInteger children= new AtomicInteger();
  public static Object system(){
    var sys= new _System$2o$0();
    var names= System.getProperty(namesKey);
    if (names == null){ return sys; }
    var r= (RevocationRoot$iyw$0)sys.mut$revocation$0();
    var u= RevokableSystem$11y8$0.instance.imm$$hash$2(r, sys);
    for (var n : names.split(",")){ u= ((F$3$2)instance(n)).read$$hash$1(u); }
    r.mut$start$0();
    return u;
  }
  static boolean result(String kind, Info$o$0 i){
    var marker= System.getProperty(markerKey);
    if (marker == null){ return false; }
    System.err.print(marker+"\n"+kind+"\n"+(i == null ? "" : toS(i)+"\n"));
    System.err.flush();
    return true;
  }
  private static Object instance(String name){
    var simple= name.substring(name.indexOf('.')+1);
    try{ return Class.forName("_"+primes(name)+"$"+caseTag(simple)+"$0").getField("instance").get(null); }
    catch (ReflectiveOperationException _){ throw nonDetErr("No attenuator called "+name+"."); }
  }
  private static String primes(String s){
    var k= 0;
    while (k < s.length() && s.charAt(s.length()-1-k) == '\''){ k += 1; }
    return k == 0 ? s : s.substring(0, s.length()-k)+"$p"+k;
  }
  private static String caseTag(String s){
    var bits= s.chars().filter(c -> ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z')).mapToObj(c -> c < 'a' ? "1" : "0").collect(Collectors.joining());
    return new BigInteger("1"+bits, 2).toString(36);
  }
  private static String name(Object instance){
    var parts= instance.getClass().getInterfaces()[0].getName().substring(1).split("\\$");
    return parts.length == 3 ? parts[0] : parts[0]+"'".repeat(Integer.parseInt(parts[1].substring(1)));
  }
  @Override public Object mut$run$2(Object main, Object attenuators){ return ((_RunningMain$2dk$0)mut$start$2(main, attenuators)).mut$await$0(); }
  @Override public Object mut$start$2(Object main, Object attenuators){
    var list= (List$o$1)attenuators;
    var size= ((Nat$c$0Instance)list.read$size$0()).val();
    var extra= LongStream.range(0, size).mapToObj(i -> name(list.read$get$1(Nat$c$0Instance.instance(i))));
    var inherited= System.getProperty(namesKey);
    var names= Stream.concat(inherited == null ? Stream.of() : Stream.of(inherited), extra).collect(Collectors.joining(","));
    return new _RunningMain$2dk$0(name(main), names);
  }
  static String marker(){ return "fearless result "+ProcessHandle.current().pid()+" "+children.incrementAndGet(); }
  static void forward(InputStream in){
    try (in){ in.transferTo(System.out); }
    catch (IOException e){ throw new UncheckedIOException(e); }
    finally{ System.out.flush(); }
  }
  static void forward(InputStream in, String marker, StringBuilder tail){
    try (var r= new BufferedReader(new InputStreamReader(in, UTF_8))){
      for (var line= r.readLine(); line != null; line = r.readLine()){
        if (line.equals(marker)){ r.lines().forEach(l -> tail.append(l).append('\n')); return; }
        System.err.print(line+"\n");
      }
    }
    catch (IOException e){ throw new UncheckedIOException(e); }
    finally{ System.err.flush(); }
  }
  static List<String> command(String name, String names, String marker){
    var cmd= new ArrayList<String>();
    cmd.add(Path.of(System.getProperty("java.home"), "bin", System.getProperty("os.name").startsWith("Windows") ? "java.exe" : "java").toString());
    ManagementFactory.getRuntimeMXBean().getInputArguments().stream().filter(_CapProcess$18g$0::inherited).forEach(cmd::add);
    if (!names.isEmpty()){ cmd.add("-D"+namesKey+"="+names); }
    cmd.add("-D"+markerKey+"="+marker);
    cmd.add("-cp");
    cmd.add(System.getProperty("java.class.path"));
    cmd.add("_"+name.substring(0, name.indexOf('.'))+".Main");
    cmd.add(name);
    return cmd;
  }
  private static boolean inherited(String arg){
    return !arg.startsWith("-D"+namesKey+"=") && !arg.startsWith("-D"+markerKey+"=") && !arg.startsWith("-Dfearless.endMarker=");
  }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
}
