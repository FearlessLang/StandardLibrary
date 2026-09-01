package base;

import java.io.IOException;
import java.io.UncheckedIOException;

// Used to be a near-full copy of Commons/tools/Fs.java (compiler-toolchain
// side); trimmed to what the shipped runtime actually calls today --
// of/ofV, from ReadZip.java. Commons/tools/Fs.java is the fuller version if
// this ever needs another method back.
public final class Fs{
  public interface RunVoid{void run() throws IOException;}
  public interface Run<T>{T run() throws IOException;}
  public static void ofV(RunVoid f) {
    try { f.run(); }
    catch(IOException io){ throw new UncheckedIOException(io); }
  }
  public static <T> T of(Run<T> f) {
    try { return f.run(); }
    catch(IOException io){ throw new UncheckedIOException(io); }
  }
}
