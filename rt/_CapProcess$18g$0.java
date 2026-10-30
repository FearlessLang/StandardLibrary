package _base;

import static _base.Util.*;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.lang.management.ManagementFactory;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class _CapProcess$18g$0 implements CapProcess$18g$0{
  static final String namesKey= "fearlessAttenuators";
  static final String markerKey= "fearlessProcess.resultMarker";
  private static final AtomicInteger children= new AtomicInteger();
  public static void main(String[] args) throws IOException{
    NativeLocale.force();
    var line= new ByteArrayOutputStream();
    for (var b= System.in.read(); b != '\n'; b= System.in.read()){ assert b >= 0; line.write(b); }
    installParentLifeline();
    var arg= Infos$1c$0.instance.imm$fromStr$1(new Str$c$0Instance(line.toString(UTF_8)));
    if (!topLevel(() -> ((InfoMain$aw$0)instance(args[0])).imm$main$2(system(), arg))){ System.exit(1); }
  }
  public static Object system(){
    var sys= new _System$2o$0();
    var names= System.getProperty(namesKey);
    if (names == null){ return sys; }
    var r= (CapRevocationRoot$4e14$0)sys.mut$revocation$0();
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
    catch (ReflectiveOperationException _){ throw detErr("No type called "+name+"."); }
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
  @Override public Object mut$run$2(Object main, Object attenuators){ return ((_CapRunningMain$jrc$1)mut$start$2(main, attenuators)).mut$await$0(); }
  @Override public Object mut$run$3(Object main, Object arg, Object attenuators){ return ((_CapRunningMain$jrc$1)mut$start$3(main, arg, attenuators)).mut$await$0(); }
  @Override public Object mut$start$2(Object main, Object attenuators){ return new _CapRunningMain$jrc$1(name(main), names(attenuators), Optional.empty()); }
  @Override public Object mut$start$3(Object main, Object arg, Object attenuators){ return new _CapRunningMain$jrc$1(name(main), names(attenuators), Optional.of(toS(arg))); }
  private static String names(Object attenuators){
    var list= (List$o$1)attenuators;
    var size= ((Nat$c$0Instance)list.read$size$0()).val();
    var extra= LongStream.range(0, size).mapToObj(i -> name(list.read$get$1(Nat$c$0Instance.instance(i))));
    var inherited= System.getProperty(namesKey);
    return Stream.concat(inherited == null ? Stream.of() : Stream.of(inherited), extra).collect(Collectors.joining(","));
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
  static List<String> command(String name, String names, String marker, boolean info){
    var cmd= new ArrayList<String>();
    cmd.add(Path.of(System.getProperty("java.home"), "bin", System.getProperty("os.name").startsWith("Windows") ? "java.exe" : "java").toString());
    ManagementFactory.getRuntimeMXBean().getInputArguments().stream().filter(_CapProcess$18g$0::inherited).forEach(cmd::add);
    if (!names.isEmpty()){ cmd.add("-D"+namesKey+"="+names); }
    cmd.add("-D"+markerKey+"="+marker);
    cmd.add("-D"+AppLog.childKey+"=true");
    cmd.add("-cp");
    cmd.add(System.getProperty("java.class.path"));
    cmd.add(_CapProcess$18g$0.class.getClassLoader().getClass().getName());
    cmd.add(info ? _CapProcess$18g$0.class.getName() : "_"+name.substring(0, name.indexOf('.'))+".Main");
    cmd.add(name);
    return cmd;
  }
  private static boolean inherited(String arg){
    return !arg.startsWith("-D"+namesKey+"=") && !arg.startsWith("-D"+markerKey+"=") && !arg.startsWith("-Dfearless.endMarker=") && !arg.startsWith("-D"+AppLog.childKey+"=");
  }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
}
