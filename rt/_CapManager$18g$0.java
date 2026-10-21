package _base;

import static _base.Util.*;
import static java.nio.charset.StandardCharsets.UTF_16LE;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.Charset;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HexFormat;
import java.util.stream.Stream;

public final class _CapManager$18g$0 implements CapManager$18g$0{
  private static final String launcher= System.getProperty("fearlessManager.launcher");
  private static final Charset pathCharset= System.getProperty("os.name").startsWith("Windows") ? UTF_16LE : Charset.forName(System.getProperty("sun.jnu.encoding"));
  private static Object send(String verb, Stream<String> args){
    if (launcher == null){ return fail("The message \""+verb+"\" is not sent: this program was not started by the Fearless manager."); }
    var cmd= Stream.concat(Stream.of(launcher, "FearlessCommand"+verb), args).toList();
    int exit;
    try{ exit= new ProcessBuilder(cmd).redirectOutput(Redirect.DISCARD).redirectError(Redirect.DISCARD).start().waitFor(); }
    catch (IOException|InterruptedException e){ throw nonDetErr("The message \""+verb+"\" could not reach the Fearless manager: "+e.getClass().getSimpleName()); }
    if (exit != 0){ throw nonDetErr("The message \""+verb+"\" could not reach the Fearless manager: the manager program ended with "+exit+"."); }
    return ok(Void$o$0.instance);
  }
  private static Object send(String verb, String... args){ return send(verb, Stream.of(args)); }
  private static Object sendPath(String verb, Object path, String... args){
    var bs= List$o$1Instance.asJava(path);
    var bytes= new byte[bs.size()];
    for (var i= 0; i < bytes.length; i += 1){ bytes[i] = Byte$o$0Instance.unwrap(bs.get(i)); }
    var text= new String(bytes, pathCharset);
    if (!absolute(text)){ return fail("The path \""+InputFile.portable(text)+"\" of the message \""+verb+"\" is not an absolute path of this system."); }
    return send(verb, Stream.concat(Stream.of("Base16:"+HexFormat.of().withUpperCase().formatHex(bytes)), Stream.of(args)));
  }
  private static boolean absolute(String path){
    try{ return Path.of(path).isAbsolute(); }
    catch (InvalidPathException _){ return false; }
  }
  private static Stream<String> strs(Object list){ return List$o$1Instance.asJava(list).stream().map(AssetBytesRead::str); }
  private static Object link(Object name, Object data, String how, Object types){
    return send("Link", Stream.concat(Stream.of(AssetBytesRead.str(name), AssetBytesRead.str(data), how), strs(types)));
  }
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
  @Override public Object mut$run$2(Object name, Object mains){ return send("Run", Stream.concat(Stream.of(AssetBytesRead.str(name)), strs(mains))); }
  @Override public Object mut$compile$1(Object name){ return send("Compile", AssetBytesRead.str(name)); }
  @Override public Object mut$check$1(Object name){ return send("Check", AssetBytesRead.str(name)); }
  @Override public Object mut$terminate$1(Object name){ return send("Terminate", AssetBytesRead.str(name)); }
  @Override public Object mut$deleteCache$1(Object name){ return send("DeleteCache", AssetBytesRead.str(name)); }
  @Override public Object mut$kindIdle$1(Object name){ return send("Kind", AssetBytesRead.str(name), "idle"); }
  @Override public Object mut$kindCode$1(Object name){ return send("Kind", AssetBytesRead.str(name), "code"); }
  @Override public Object mut$kindDataReadOnly$1(Object name){ return send("Kind", AssetBytesRead.str(name), "data:readOnly"); }
  @Override public Object mut$kindDataReadWrite$1(Object name){ return send("Kind", AssetBytesRead.str(name), "data:readWrite"); }
  @Override public Object mut$forget$1(Object name){ return send("Forget", AssetBytesRead.str(name)); }
  @Override public Object mut$linkRead$3(Object name, Object data, Object types){ return link(name, data, "read", types); }
  @Override public Object mut$linkWrite$3(Object name, Object data, Object types){ return link(name, data, "write", types); }
}
