package _base;

import java.io.IOException;
import java.io.UncheckedIOException;

// The part of Commons/tools/Fs.java that ReadZip.java calls.
public final class Fs{
  public interface RunVoid{void run() throws IOException;}
  public interface Run<T>{T run() throws IOException;}
  public static void ofV(RunVoid f){
    try{ f.run(); }
    catch (IOException io){ throw new UncheckedIOException(io); }
  }
  public static <T> T of(Run<T> f){
    try{ return f.run(); }
    catch (IOException io){ throw new UncheckedIOException(io); }
  }
}
