package _base;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@SuppressWarnings("restricted")
public final class NativeLocale{
  private static final Linker linker= Linker.nativeLinker();
  public static void force(){
    Locale.setDefault(Locale.ROOT);
    var os= System.getProperty("os.name").toLowerCase(Locale.ROOT);
    var names= System.getProperty("sun.jnu.encoding");
    if (!os.contains("win") && !names.equals("UTF-8")){ throw new Error("Broken Environment: a Fearless program decodes file names as UTF-8, but this JVM decodes them as "+names+"; on Linux the launcher of the program sets LC_ALL=C.UTF-8"); }
    try{
      if (os.contains("win")){ windowsUiLanguage(); setCLocale(0, "C"); return; }
      if (os.contains("mac")){ setCLocale(0, "C"); return; }
      if (os.contains("linux")){ setCLocale(6, "C.UTF-8"); return; }
    }
    catch (Throwable t){ throw new Error("Broken Environment", t); }
    throw new Error("Broken Environment: unsupported operating system "+os);
  }
  private static void setCLocale(int lcAll, String name) throws Throwable{
    var setlocale= linker.downcallHandle(linker.defaultLookup().findOrThrow("setlocale"), FunctionDescriptor.of(ADDRESS, JAVA_INT, ADDRESS));
    try (var arena= Arena.ofConfined()){
      var res= (MemorySegment)setlocale.invokeExact(lcAll, arena.allocateFrom(name));
      if (MemorySegment.NULL.equals(res)){ throw new Error("Broken Environment: the C runtime rejected setlocale(LC_ALL, \""+name+"\")"); }
    }
  }
  //Platform primitives do what their specification says: once SetProcessPreferredUILanguages succeeds for one language, that language is the UI language of the process.
  private static void windowsUiLanguage() throws Throwable{
    var kernel32= SymbolLookup.libraryLookup("kernel32.dll", Arena.global());
    var set= linker.downcallHandle(kernel32.findOrThrow("SetProcessPreferredUILanguages"), FunctionDescriptor.of(JAVA_INT, JAVA_INT, ADDRESS, ADDRESS));
    try (var arena= Arena.ofConfined()){
      var count= arena.allocate(JAVA_INT);
      var ok= (int)set.invokeExact(8, arena.allocateFrom("en-US\0", StandardCharsets.UTF_16LE), count);
      if (ok == 0 || count.get(JAVA_INT, 0) != 1){ throw new Error("Broken Environment: Windows rejected the process UI language en-US"); }
    }
  }
}
