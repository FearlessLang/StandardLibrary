package _base;

import static _base.Util.*;
import static java.nio.charset.StandardCharsets.UTF_16LE;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.charset.Charset;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HexFormat;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public final class _CapManager$18g$0 implements CapManager$18g$0{
  private static final String launcher= System.getProperty("fearlessManager.launcher");
  private static final String applied= "Applied\n";
  private static final String refused= "Refused\n";
  private static final Charset pathCharset= System.getProperty("os.name").startsWith("Windows") ? UTF_16LE : Charset.forName(System.getProperty("sun.jnu.encoding"));
  private static String message(String verb, List<String> args){
    try (var server= new ServerSocket(0, 1, InetAddress.getLoopbackAddress())){
      var cmd= Stream.of(List.of(launcher, "FearlessCommand"+verb), args, List.of("Port:"+server.getLocalPort())).flatMap(List::stream).toList();
      var exit= start(verb, cmd).waitFor();
      if (exit != 0){ throw nonDetErr("The message \""+verb+"\" could not reach the Fearless manager: the manager program ended with "+exit+"."); }
      try (var s= server.accept()){ return new String(s.getInputStream().readAllBytes(), UTF_8); }
    }
    catch (IOException|InterruptedException e){ throw nonDetErr("The message \""+verb+"\" could not reach the Fearless manager: "+e.getClass().getSimpleName()); }
  }
  private static Process start(String verb, List<String> cmd){
    try{ return new ProcessBuilder(cmd).redirectOutput(Redirect.DISCARD).redirectError(Redirect.DISCARD).start(); }
    catch (IOException e){ throw nonDetErr("The message \""+verb+"\" could not reach the Fearless manager: the manager program could not be started."); }
  }
  private static Object send(String verb, Stream<String> args, Function<String,Object> result){
    if (launcher == null){ throw nonDetErr("The message \""+verb+"\" is not sent: this program was not started by the Fearless manager."); }
    return result.apply(applied(verb, message(verb, args.toList())));
  }
  private static String applied(String verb, String text){
    if (text.startsWith(applied)){ return text.substring(applied.length()); }
    if (!text.startsWith(refused)){ throw nonDetErr("The Fearless manager ended the connection of the message \""+verb+"\" without saying whether it applied it."); }
    throw detErr(InputFile.portable(text.substring(refused.length())));
  }
  private static Object send(String verb, Stream<String> args){ return send(verb, args, _ -> Void$o$0.instance); }
  private static Object output(String verb, Stream<String> args){ return send(verb, args, o -> new UStr$s$0Instance(o.codePoints().toArray())); }
  private static Object send(String verb, String... args){ return send(verb, Stream.of(args)); }
  private static Object sendPath(String verb, Object path, String... args){
    var bs= List$o$1Instance.asJava(path);
    var bytes= new byte[bs.size()];
    for (var i= 0; i < bytes.length; i += 1){ bytes[i] = Byte$o$0Instance.unwrap(bs.get(i)); }
    var text= new String(bytes, pathCharset);
    if (!absolute(text)){ throw detErr("The path \""+InputFile.portable(text)+"\"\nof the message \""+verb+"\" is not an absolute path of this system."); }
    return send(verb, Stream.concat(Stream.of("Base16:"+HexFormat.of().withUpperCase().formatHex(bytes)), Stream.of(args)));
  }
  private static boolean absolute(String path){
    try{ return Path.of(path).isAbsolute(); }
    catch (InvalidPathException _){ return false; }
  }
  private static Stream<String> strs(Object list){ return List$o$1Instance.asJava(list).stream().map(AssetBytesRead::str); }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$show$0(){ return send("Show"); }
  @Override public Object mut$show$1(Object name){ return send("Show", AssetBytesRead.str(name)); }
  @Override public Object mut$register$1(Object path){ return sendPath("Register", path); }
  @Override public Object mut$quit$0(){ return send("Quit"); }
  @Override public Object mut$reset$0(){ return send("Reset"); }
  @Override public Object mut$connectEclipse$1(Object path){ return sendPath("ConnectEclipse", path); }
  @Override public Object mut$editMetadata$1(Object path){ return sendPath("EditMetadata", path); }
  @Override public Object mut$openWithOrShortcut$1(Object path){ return sendPath("OpenWithOrShortcut", path); }
  @Override public Object mut$openWithOrShortcut$3(Object path, Object name, Object main){ return sendPath("OpenWithOrShortcut", path, AssetBytesRead.str(name), AssetBytesRead.str(main)); }
  @Override public Object mut$run$2(Object name, Object mains){ return output("Run", Stream.concat(Stream.of(AssetBytesRead.str(name)), strs(mains))); }
  @Override public Object mut$compile$1(Object name){ return output("Compile", Stream.of(AssetBytesRead.str(name))); }
  @Override public Object mut$check$1(Object name){ return send("Check", AssetBytesRead.str(name)); }
  @Override public Object mut$terminate$1(Object name){ return send("Terminate", AssetBytesRead.str(name)); }
  @Override public Object mut$deleteCache$1(Object name){ return send("DeleteCache", AssetBytesRead.str(name)); }
  @Override public Object mut$kindIdle$1(Object name){ return send("Kind", AssetBytesRead.str(name), "idle"); }
  @Override public Object mut$kindCode$1(Object name){ return send("Kind", AssetBytesRead.str(name), "code"); }
  @Override public Object mut$kindDataRead$1(Object name){ return send("Kind", AssetBytesRead.str(name), "dataRead"); }
  @Override public Object mut$kindDataEdit$1(Object name){ return send("Kind", AssetBytesRead.str(name), "dataEdit"); }
  @Override public Object mut$forget$1(Object name){ return send("Forget", AssetBytesRead.str(name)); }
  @Override public Object mut$mount$3(Object name, Object type, Object data){ return send("Mount", AssetBytesRead.str(name), AssetBytesRead.str(type), AssetBytesRead.str(data)); }
  @Override public Object mut$unmount$2(Object name, Object type){ return send("Mount", AssetBytesRead.str(name), AssetBytesRead.str(type)); }
  @Override public Object mut$attenuate$3(Object name, Object main, Object attenuators){ return send("Attenuate", Stream.concat(Stream.of(AssetBytesRead.str(name), AssetBytesRead.str(main)), strs(attenuators))); }
}
