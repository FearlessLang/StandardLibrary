package _base;

import static _base.Util.*;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HexFormat;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class _CapManager$18g$0 implements CapManager$18g$0{
  private static final String launcher= System.getProperty("fearlessManager.launcher");
  private static final String applied= "Applied\n";
  private static final String refused= "Refused\n";
  private static final Pattern projectName= Pattern.compile("(?!(?:con|prn|aux|nul|com[1-9]|lpt[1-9])$)[a-z_][a-z0-9_]{0,190}");
  private static final Pattern typeName= Pattern.compile("(?!(?:con|prn|aux|nul|com[1-9]|lpt[1-9])[.])[a-z][a-z0-9_]*[.]_*[A-Z][A-Za-z0-9_]*'*");
  private static String message(String verb, List<String> args){
    try (var server= new ServerSocket(0, 1, InetAddress.getLoopbackAddress())){
      var call= Stream.of(List.of("FearlessCommand"+verb), args, List.of("Port:"+server.getLocalPort())).flatMap(List::stream).collect(Collectors.joining(" "));
      var cmd= List.of(launcher, call);
      var exit= start(verb, cmd).waitFor();
      if (exit != 0){ throw nonDetErr("The message \""+verb+"\" could not reach the Fearless manager: the manager program ended with "+exit+"."); }
      try (var s= server.accept()){ return new String(s.getInputStream().readAllBytes(), UTF_8); }
    }
    catch (IOException e){ throw nonDetErr("The message \""+verb+"\" could not reach the Fearless manager: "+InputFile.portable(e.getMessage())); }
    catch (InterruptedException e){ throw nonDetErr("The message \""+verb+"\" could not reach the Fearless manager: this program was interrupted while waiting for the manager."); }
  }
  private static Process start(String verb, List<String> cmd){
    try{ return new ProcessBuilder(cmd).redirectOutput(Redirect.DISCARD).redirectError(Redirect.DISCARD).start(); }
    catch (IOException e){ throw nonDetErr("The message \""+verb+"\" could not reach the Fearless manager: the manager program could not be started."); }
  }
  private static Object send(String verb, List<String> args, Function<String,Object> result){
    if (launcher == null){ throw nonDetErr("The message \""+verb+"\" is not sent: this program was not started by the Fearless manager."); }
    return result.apply(applied(verb, message(verb, args)));
  }
  private static String applied(String verb, String text){
    if (text.startsWith(applied)){ return text.substring(applied.length()); }
    if (!text.startsWith(refused)){ throw nonDetErr("The Fearless manager ended the connection of the message \""+verb+"\" without saying whether it applied it."); }
    throw detErr(InputFile.portable(text.substring(refused.length())));
  }
  private static Object send(String verb, List<String> args){ return send(verb, args, _ -> Void$o$0.instance); }
  private static Object output(String verb, String name){ return send(verb, List.of(name), o -> new UStr$s$0Instance(o.codePoints().toArray())); }
  private static Object send(String verb, String... args){ return send(verb, List.of(args)); }
  private static Object sendPath(String verb, Object path, String... args){
    var bytes= Byte$o$0Instance.unwrapAll(path);
    var text= pathText(verb, bytes);
    if (!absolute(text)){ throw detErr("The message \""+verb+"\" is not sent: the path \""+InputFile.portable(text)+"\"\nis not an absolute path of this system other than the root of a drive."); }
    return send(verb, Stream.concat(Stream.of("Base16:"+HexFormat.of().withUpperCase().formatHex(bytes)), Stream.of(args)).toList());
  }
  private static String pathText(String verb, byte[] bytes){
    if (!System.getProperty("os.name").startsWith("Windows")){ return new String(bytes, Charset.forName(System.getProperty("sun.jnu.encoding"))); }
    if (bytes.length % 2 != 0){ throw detErr("The message \""+verb+"\" is not sent: a path of Windows is 16 bit units, so its bytes are an even number."); }
    return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asCharBuffer().toString();
  }
  private static boolean absolute(String path){
    try{ var p= Path.of(path); return p.isAbsolute() && p.normalize().getFileName() != null; }
    catch (InvalidPathException _){ return false; }
  }
  private static String name(String verb, Object name){ return word(verb, AssetBytesRead.str(name), projectName, "a project name"); }
  private static String type(String verb, Object type){ return word(verb, AssetBytesRead.str(type), typeName, "a qualified type name, like \"hello.Main\""); }
  private static String word(String verb, String word, Pattern p, String what){
    if (p.matcher(word).matches()){ return word; }
    throw detErr("The message \""+verb+"\" is not sent: \""+InputFile.portable(word)+"\" is not "+what+".");
  }
  private static List<String> types(String verb, Object list){ return List$o$1Instance.asJava(list).stream().map(t -> type(verb, t)).toList(); }
  private static List<String> mains(Object list){
    var res= types("SelectMains", list);
    res.stream().filter(m -> res.indexOf(m) != res.lastIndexOf(m)).findFirst().ifPresent(m -> { throw detErr("The message \"SelectMains\" is not sent: the mains are all different, and \""+m+"\" is named twice."); });
    return res;
  }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$show$0(){ return send("Show"); }
  @Override public Object mut$show$1(Object name){ return send("Show", name("Show", name)); }
  @Override public Object mut$register$1(Object path){ return sendPath("Register", path); }
  @Override public Object mut$quit$0(){ send("Quit"); throw new EndedByManager(); }
  @Override public Object mut$reset$0(){ send("Reset"); throw new EndedByManager(); }
  @Override public Object mut$connectEclipse$1(Object path){ return sendPath("ConnectEclipse", path); }
  @Override public Object mut$editMetadata$1(Object path){ return sendPath("EditMetadata", path); }
  @Override public Object mut$openWithOrShortcut$1(Object path){ return sendPath("OpenWithOrShortcut", path); }
  @Override public Object mut$openWithOrShortcut$3(Object path, Object name, Object main){ return sendPath("OpenWithOrShortcut", path, name("OpenWithOrShortcut", name), type("OpenWithOrShortcut", main)); }
  @Override public Object mut$run$1(Object name){ return output("Run", name("Run", name)); }
  @Override public Object mut$compile$1(Object name){ return output("Compile", name("Compile", name)); }
  @Override public Object mut$check$1(Object name){ return output("Check", name("Check", name)); }
  @Override public Object mut$fix$1(Object name){ return output("Fix", name("Fix", name)); }
  @Override public Object mut$terminate$1(Object name){ return send("Terminate", name("Terminate", name)); }
  @Override public Object mut$deleteCache$1(Object name){ return send("DeleteCache", name("DeleteCache", name)); }
  @Override public Object mut$kindIdle$1(Object name){ return send("Kind", name("Kind", name), "idle"); }
  @Override public Object mut$kindCode$1(Object name){ return send("Kind", name("Kind", name), "code"); }
  @Override public Object mut$kindDataRead$1(Object name){ return send("Kind", name("Kind", name), "dataRead"); }
  @Override public Object mut$kindDataEdit$1(Object name){ return send("Kind", name("Kind", name), "dataEdit"); }
  @Override public Object mut$forget$1(Object name){ return send("Forget", name("Forget", name)); }
  @Override public Object mut$mount$3(Object name, Object type, Object data){ return send("Mount", name("Mount", name), type("Mount", type), name("Mount", data)); }
  @Override public Object mut$unmount$2(Object name, Object type){ return send("Unmount", name("Unmount", name), type("Unmount", type)); }
  @Override public Object mut$attenuate$3(Object name, Object main, Object attenuators){ return send("Attenuate", Stream.concat(Stream.of(name("Attenuate", name), type("Attenuate", main)), types("Attenuate", attenuators).stream()).toList()); }
  @Override public Object mut$selectMains$2(Object name, Object mains){ return send("SelectMains", Stream.concat(Stream.of(name("SelectMains", name)), mains(mains).stream()).toList()); }
}
