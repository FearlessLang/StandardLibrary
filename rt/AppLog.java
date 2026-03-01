package base;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.*;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public final class AppLog{
  public static final int keepLogs= 10;
  public static final int keepErrLogs= 100;
  public static final int maxStampRetries= 10;
  public static Path errPath= Path.of(".out","logs","err.log");
  public static final String stampPattern= "yyyyMMdd_HHmmss_SSS'Z'";
  private static final DateTimeFormatter stampFmt= DateTimeFormatter.ofPattern(stampPattern).withZone(ZoneOffset.UTC);
  private static final ConcurrentLinkedQueue<AppLog> all= new ConcurrentLinkedQueue<>();
  private static final AtomicBoolean hookInstalled= new AtomicBoolean();
  private static final AtomicBoolean closing= new AtomicBoolean();
  public static AppLog open(Path path){
    var p= path.toAbsolutePath().normalize();
    startupFor(errPath, keepErrLogs);
    startupFor(p, keepLogs);
    installHook();
    var log= new AppLog(p);
    all.add(log);
    return log;
  }
  private static void installHook(){
    if (!hookInstalled.compareAndSet(false,true)){ return; }
    Runtime.getRuntime().addShutdownHook(new Thread(AppLog::shutdown,"AppLog-shutdown"));
  }
  private static void shutdown(){
    closing.set(true);
    for (var log: all){
      try{ log.closeAndRotateQuiet(); }
      catch(Throwable t){ err("closing and log rotation crashed: "+log.path, t); }
    }
    try{ rotateOnCloseQuiet(errPath, keepErrLogs, true); }
    catch(Throwable t){
      System.err.print(stampNow()+" rotate err.log crashed: "+errPath+"\n");
      t.printStackTrace(System.err);
    }
  }
  private final Path path;
  private final Object lock= new Object();
  private final BufferedWriter out;
  private AppLog(Path path){
    this.path= path;
    this.out= openWriterTruncate(path);
  }
  public void append(String msg){
    var line= stampNow()+" "+msg+"\n";
    synchronized(lock){
      if (closing.get()){ err("append during shutdown for "+path+": "+msg, null); return; }
      try {
        out.write(line);
        out.flush();
      }
      catch(IOException e){ throw new UncheckedIOException(e); }
    }
  }
  private void closeAndRotateQuiet(){
    synchronized(lock){
      try { out.close(); }
      catch (IOException e){ err("close failed: "+path, e); return; }
    }
    rotateOnCloseQuiet(path, keepLogs, false);
  }
  private static void startupFor(Path p, int keep){
    createDirsOrThrow(p);
    if (Files.exists(p)){
      if (sizeOrThrow(p) == 0){ deleteOrThrow(p); }
      else{ moveToStampedOrThrow(p); }
    }
    enforceRetentionOrThrow(p, keep);
  }
  private static void rotateOnCloseQuiet(Path p, int keep, boolean isErrLog){
    try{
      if (!Files.exists(p)){ return; }
      if (Files.size(p) == 0){ Files.deleteIfExists(p); return; }
      moveToStamped(p,false);
      enforceRetention(p, keep);
    }
    catch(IOException e){
      if (!isErrLog){ err("rotate failed: "+p, e); return; }
      System.err.print(stampNow()+" rotate failed: "+p+"\n");
      e.printStackTrace(System.err);
    }
  }
  private static BufferedWriter openWriterTruncate(Path p){
    try{
      createDirsOrThrow(p);
      return Files.newBufferedWriter(p, UTF_8, CREATE, WRITE, TRUNCATE_EXISTING);
    }
    catch(IOException e){ throw new UncheckedIOException(e); }
  }
  private static String stampNow(){ return stampFmt.format(Instant.now()); }

  private static void createDirsOrThrow(Path p){
    try{ Files.createDirectories(p.getParent()); }
    catch(IOException e){ throw new UncheckedIOException(e); }
  }
  private static long sizeOrThrow(Path p){
    try{ return Files.size(p); }
    catch(IOException e){ throw new UncheckedIOException(e); }
  }
  private static void deleteOrThrow(Path p){
    try{ Files.deleteIfExists(p); }
    catch(IOException e){ throw new UncheckedIOException(e); }
  }
  private static Path rotatedPath(Path p,String stamp){
    var fName= p.getFileName().toString().replace(".log","$"+stamp+".log");//note: this is going to be the second $ of the file
    return p.getParent().resolve(fName);
  }
  private static String glob(Path p){ return p.getFileName().toString().replace(".log","$*.log"); }

  private static void appendOrThrow(Path p){
    try (var w= Files.newBufferedWriter(p, UTF_8, CREATE, WRITE, APPEND))
    { w.write("\n"+stampNow()+" interrupted here\n"); w.flush(); }
    catch(IOException e){ throw new UncheckedIOException(e); }
  }
  private static void moveToStampedOrThrow(Path src){
    try{ moveToStamped(src,true); }
    catch(IOException ioe){ throw new UncheckedIOException(ioe); }
  }
  private static void moveToStamped(Path src, boolean starting) throws IOException{
    for (int i= 0; i < maxStampRetries; i++){
      var dst= rotatedPath(src, stampNow());
      try { Files.move(src, dst); if (starting){ appendOrThrow(dst); } return; }
      catch(FileAlreadyExistsException e){ 
        try{ Thread.sleep(100); } catch(InterruptedException _){}//refuse to interrupt during log cleanup
      }
    }
    throw new IOException(" OS timestamps unresponsive when renaming "+src);
  }
  private static void enforceRetentionOrThrow(Path p, int keep){
    try { enforceRetention(p,keep); }
    catch(IOException e){ throw new UncheckedIOException(e); }
  }
  private static void enforceRetention(Path p, int keep) throws IOException{
    var files= new ArrayList<Path>();
    try (DirectoryStream<Path> ds= Files.newDirectoryStream(p.getParent(), glob(p))){
      for (var f: ds){ files.add(f); }
    }
    files.sort(Comparator.comparing(f->f.getFileName().toString()));
    for (int i= 0, del= files.size() - keep; i < del; i++){ Files.deleteIfExists(files.get(i)); }
  }
  private static String stack(Throwable t){
    var sw= new StringWriter();
    t.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }
  private static void err(String msg, Throwable t){
    var line= stampNow()+" "+msg+"\n";
    System.err.print(line);
    if (t != null){ System.err.print(stack(t)); }
    try{
      Files.createDirectories(errPath.getParent());
      try (var w= Files.newBufferedWriter(errPath, UTF_8,  CREATE, WRITE, APPEND)){
        w.write(line);
        if (t != null){ w.write(stack(t)); }
        w.flush();
      }
    }
    catch(Throwable _){}// best effort only
  }
}