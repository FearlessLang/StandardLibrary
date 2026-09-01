package base;

import java.io.IOException;
import java.io.UncheckedIOException;

// Trimmed from a near-full copy of Commons/tools/Fs.java down to what's actually called (ReadZip.java).
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
