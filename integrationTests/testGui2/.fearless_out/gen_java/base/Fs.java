package base;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.spi.ToolProvider;
import java.util.stream.Stream;

public final class Fs{
  // ASCII whitelist
  public static final String allowed=
    "0123456789" +
    "abcdefghijklmnopqrstuvwxyz" +
    "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
    "+-*/=<>,.;:()[]{}" +
    "`'\"!?@#$%^&_|~\\" +
    " \n";
  public static void ensureDir(Path p){ of(()->Files.createDirectories(p)); }
  public static void cleanDirContents(Path p){
    var xs= walk(p, s-> s
      .filter(x->!x.equals(p))
      .sorted(Comparator.reverseOrder())
      .toList()
    );
    xs.forEach(x->ofV(()->forceDelete(x)));
  }
  private static void forceDelete(Path p) throws IOException{
    try{ makeWritableIfPossible(p); } catch(Throwable _){}
    Files.deleteIfExists(p);
  }
  private static void makeWritableIfPossible(Path p) throws IOException{
    var dos= Files.getFileAttributeView(p, DosFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
    if (dos != null){ dos.setReadOnly(false); return; }
    var posix= Files.getFileAttributeView(p, PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
    if (posix != null){
      var ps= posix.readAttributes().permissions();
      if (ps.add(PosixFilePermission.OWNER_WRITE)){ Files.setPosixFilePermissions(p, ps); }
      return;
    }
    p.toFile().setWritable(true);
  }  
  public static void writeUtf8(Path file, String content){
    ensureDir(file.getParent());
    ofV(()->Files.writeString(file, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
  }
  public static String readUtf8(Path file){
    ensureDir(file.getParent());
    return of(()->Files.readString(file,StandardCharsets.UTF_8));
  }
  public static void copyTree(Path from, Path to){
    walkV(from,s->s.forEach(src->ofV(()->copyOne(from, to, src))));
  }
  private static void copyOne(Path fromRoot, Path toRoot, Path src) throws IOException{
    var rel= fromRoot.relativize(src);
    var dst= toRoot.resolve(rel);
    if (Files.isDirectory(src)){ Files.createDirectories(dst); return; }
    Files.createDirectories(dst.getParent());
    Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
  }
  public static long lastModified(Path file){
    try{ return Files.getLastModifiedTime(file, LinkOption.NOFOLLOW_LINKS).toMillis(); }
    catch(NoSuchFileException _){ return -1; }
    catch(IOException ioe){ throw new UncheckedIOException(ioe); }
  }
  // Writes (overwriting if needed) and guarantees mtime > minExclusiveMillis. Returns the actual mtime.
  public static long writeUtf8(Path file, String content, long minExclusiveMillis){
    ensureDir(file.getParent());
    for(;;){
      ofV(()->Files.writeString(file, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
      var m= lastModified(file);
      if (m > minExclusiveMillis){ return m; }
      try{ Thread.sleep(10); }
      catch(InterruptedException ie){ Thread.currentThread().interrupt(); throw new RuntimeException(ie); }
    }
  }
  public static void reqDir(Path p, String what){  }
  public static void cleanDir(Path p){
    if (!Files.exists(p)){ ensureDir(p); return; }
    cleanDirContents(p);
  }
  public static void rmTree(Path p){
    if (!Files.exists(p)){ return; }
    if (!Files.isDirectory(p)){ ofV(()->Files.deleteIfExists(p)); return; }
    cleanDirContents(p);
    ofV(()->Files.deleteIfExists(p));
  }
  public static void copyFresh(Path from, Path to){
    rmTree(to);
    copyTree(from, to);
  }
  public static String runTool(String tool, List<String> args){
    var tp= ToolProvider.findFirst(tool).orElseThrow();
    var baos= new ByteArrayOutputStream();
    var ps= new PrintStream(baos, true, StandardCharsets.UTF_8);
    int rc= tp.run(ps, ps, args.toArray(String[]::new));
    var out= baos.toString(StandardCharsets.UTF_8);
    return out;
  }
  ///Returns the filename with extension (the substring after the last '/').
  public static String fileNameWithExtension(URI s){ return fileNameWithExtension(s.toString()); } 
  public static String fileNameWithExtension(String s){
    var res= s.substring(lastSlashIndex(s)+1);
    assert !res.isEmpty();
    return res;
  }
  /// Returns the filename without the extension. Example: "fear:/a/b/c.tar.gz" -> "c"
  public static String fileNameWithoutExtension(URI u){ return fileNameWithoutExtension(u.toString()); }
  public static String fileNameWithoutExtension(String s){
    int slash= lastSlashIndex(s);
    int dot= s.indexOf('.', slash + 1); // first dot after last slash
    assert dot >= 0 && dot + 1 < s.length();
    assert dot > slash + 1 && dot + 1 < s.length();
    return s.substring(slash + 1, dot);
  }
  /// Returns the extension including the leading '.' Example: "fear:/a/b/c.tar.gz" -> ".tar.gz"; but also /a.b/c.z -> .z
  public static String extensionWithDot(String s){
    int dot= s.indexOf('.', lastSlashIndex(s) + 1); // first dot after last slash
    assert dot >= 0 && dot + 1 < s.length();
    return s.substring(dot);
  }
  ///Returns the path without the filename
  public static String removeFileName(URI s){ return removeFileName(s.toString()); } 
  public static String removeFileName(String s){ return s.substring(0,lastSlashIndex(s)); }
  public static String removeFileNameAllowTop(URI s){ return removeFileNameAllowTop(s.toString()); } 
  public static String removeFileNameAllowTop(String s){
    int i= s.lastIndexOf('/');
    if (i == -1){ return ""; }
    int j= s.indexOf(":/");
    return i == j+1 ? s.substring(0,i+1) : s.substring(0,i);
  }  
  private static int lastSlashIndex(String s){
    int i= s.lastIndexOf('/');
    assert i >= 0 && i + 1 < s.length();
    return i;
  }
  public static boolean isWindows(){
    return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("win");
  }
  public static boolean isMac(){
    return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("mac");
  }
  public static boolean isLinux(){
    return System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("linux");
  }
    public interface RunVoid{void run() throws IOException;}
  public interface Run<T>{T run() throws IOException;}
  public interface WalkVoid{void walk(Stream<Path> p) throws IOException;}
  public interface Walk<T>{T walk(Stream<Path> p) throws IOException;}
  public static void ofV(RunVoid f) {
    try { f.run(); }
    catch(IOException io){ throw new UncheckedIOException(io); }
  }
  public static <T> T of(Run<T> f) {
    try { return f.run(); }
    catch(IOException io){ throw new UncheckedIOException(io); }
  }
  public static void walkV(Path p, WalkVoid f){
    try (Stream<Path> s= Files.walk(p)){ f.walk(s); }
    catch(IOException io){ throw new UncheckedIOException(io); }
  }
  public static <T> T walk(Path p, Walk<T> f){
    try (Stream<Path> s= Files.walk(p)){ return f.walk(s); }
    catch(IOException io){ throw new UncheckedIOException(io); }
  }
}