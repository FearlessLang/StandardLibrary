package _base;

import static _base.Util.*;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.ProcessBuilder.Redirect;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class _CapProcess$18g$0 implements CapProcess$18g$0{
  static final String namesKey= "fearlessAttenuators";
  static final String portKey= "fearlessProcess.port";
  static final String ok= "ok\n";
  static final String info= "info\n";
  public static Object system(){
    Object sys= new _System$2o$0();
    var names= System.getProperty(namesKey);
    if (names == null){ return sys; }
    var gate= (Var$c$1)Vars$o$0.instance.imm$$hash$1(Systems$5c$0.instance.imm$revoked$0());
    var u= Caretakers$16o$0.instance.imm$$hash$1(gate);
    for (var n : names.split(",")){ u= ((F$3$2)instance(n)).read$$hash$1(u); }
    gate.mut$set$1(sys);
    return u;
  }
  public static void report(Info$o$0 i){
    var port= System.getProperty(portKey);
    if (port == null){ return; }
    try (var s= new Socket(InetAddress.getLoopbackAddress(), Integer.parseInt(port))){
      s.getOutputStream().write((i == null ? ok : info+toS(i)).getBytes(UTF_8));
    }
    catch (IOException e){ throw new UncheckedIOException(e); }
  }
  private static Object[] table(String pkg, String field){
    try{ return (Object[])Class.forName("_"+pkg+".Main").getField(field).get(null); }
    catch (ReflectiveOperationException _){ throw new Error("Broken Environment"); }
  }
  private static Object instance(String name){
    var pkg= name.substring(0, name.indexOf('.'));
    var names= table(pkg, "attenuators");
    var instances= table(pkg, "attenuatorInstances");
    for (var i= 0; i < names.length; i += 1){ if (names[i].equals(name)){ return instances[i]; } }
    throw nonDetErr("No attenuator called "+name+" in package "+pkg+".");
  }
  private static String mainName(Object main){
    var pkg= main.getClass().getPackageName().substring(1);
    var names= table(pkg, "all");
    var instances= table(pkg, "mainInstances");
    for (var i= 0; i < names.length; i += 1){ if (instances[i] == main){ return (String)names[i]; } }
    return null;
  }
  private static Object run(Object main, Stream<String> extra){
    var name= mainName(main);
    if (name == null){ return fail("The main is not a main of this program: only a main that captures nothing can run in a new process."); }
    var inherited= System.getProperty(namesKey);
    var names= String.join(",", Stream.concat(inherited == null ? Stream.of() : Stream.of(inherited), extra).toList());
    return Try$c$0.actionLazy(() -> { runChild(name, names); return Void$o$0.instance; });
  }
  private static void runChild(String name, String names){
    try (var server= new ServerSocket(0, 1, InetAddress.getLoopbackAddress())){
      System.out.flush();
      System.err.flush();
      var p= new ProcessBuilder(command(name, names, server.getLocalPort())).redirectOutput(Redirect.INHERIT).redirectError(Redirect.INHERIT).start();
      var exit= p.waitFor();
      p.getOutputStream().close();
      server.setSoTimeout(1000);
      try (var s= server.accept()){ result(name, new String(s.getInputStream().readAllBytes(), UTF_8)); }
      catch (SocketTimeoutException _){ throw nonDetErr("The process running "+name+" ended with "+exit+" before its main ended."); }
    }
    catch (IOException|InterruptedException e){ throw nonDetErr("The process running "+name+" could not be started: "+e.getClass().getSimpleName()); }
  }
  private static List<String> command(String name, String names, int port){
    var cmd= new ArrayList<String>();
    cmd.add(Path.of(System.getProperty("java.home"), "bin", System.getProperty("os.name").startsWith("Windows") ? "java.exe" : "java").toString());
    ManagementFactory.getRuntimeMXBean().getInputArguments().stream().filter(_CapProcess$18g$0::inherited).forEach(cmd::add);
    if (!names.isEmpty()){ cmd.add("-D"+namesKey+"="+names); }
    cmd.add("-D"+portKey+"="+port);
    cmd.add("-cp");
    cmd.add(System.getProperty("java.class.path"));
    cmd.add("_"+name.substring(0, name.indexOf('.'))+".Main");
    cmd.add(name);
    return cmd;
  }
  private static boolean inherited(String arg){
    return !arg.startsWith("-D"+namesKey+"=") && !arg.startsWith("-D"+portKey+"=") && !arg.startsWith("-Dfearless.endMarker=");
  }
  private static void result(String name, String text){
    if (text.equals(ok)){ return; }
    if (!text.startsWith(info)){ throw nonDetErr("The process running "+name+" ended without saying how its main ended."); }
    throw deterministic((Info$o$0)Infos$1c$0.instance.imm$fromStr$1(new Str$c$0Instance(text.substring(info.length()))));
  }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$run$1(Object main){ return run(main, Stream.of()); }
  @Override public Object mut$run$2(Object main, Object attenuator){ return run(main, Stream.of(attenuatorName(attenuator))); }
  private static String attenuatorName(Object attenuator){
    var pkg= attenuator.getClass().getPackageName().substring(1);
    var names= table(pkg, "attenuators");
    var instances= table(pkg, "attenuatorInstances");
    for (var i= 0; i < names.length; i += 1){ if (instances[i] == attenuator){ return (String)names[i]; } }
    throw new Error("Broken Environment");
  }
}
