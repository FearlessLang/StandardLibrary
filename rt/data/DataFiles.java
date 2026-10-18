package _base;

import static _base.Util.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

final class DataFiles{
  private static final Map<String,Path> reads= linked("fearlessDataRead.");
  private static final Map<String,Path> edits= linked("fearlessDataWrite.");
  private static final Object lock= new Object();
  private static final Set<String> reserved= Set.of("con", "prn", "aux", "nul", "com1", "com2", "com3", "com4", "com5", "com6", "com7", "com8", "com9", "lpt1", "lpt2", "lpt3", "lpt4", "lpt5", "lpt6", "lpt7", "lpt8", "lpt9");
  private static final Set<String> noExt= Set.of("readme", "license", "copying", "notice", "authors", "contributors", "changelog", "changes", "news", "todo", "thanks", "security", "contributing", "code_of_conduct");
  private static final Set<String> multiDot= Set.of("tar.gz", "tar.bz2", "tar.xz", "tar.zst", "tar.lz", "tar.lz4", "d.ts", "d.ts.map", "js.map", "css.map", "min.js", "min.css");
  sealed interface Node{}
  record Dir(Path path) implements Node{}
  record ZipDir(Path zip, List<String> steps, String prefix) implements Node{}
  record File(Path path, List<String> steps, String entry) implements Node{}
  record Item(long time, byte[] bytes){}
  interface Op{ Object apply(String name, Path root, List<String> path) throws IOException; }
  @SuppressWarnings("serial") static final class Refused extends RuntimeException{ Refused(String msg){ super(msg, null, false, false); } }
  private static Map<String,Path> linked(String prefix){
    var res= new TreeMap<String,Path>();
    for (var k : System.getProperties().stringPropertyNames()){
      if (k.startsWith(prefix)){ res.put(k.substring(prefix.length()), Path.of(System.getProperty(k))); }
    }
    return Collections.unmodifiableMap(res);
  }
  static Object paths(Object name, Object path){ return act(name, path, false, DataFiles::list); }
  static Object read(Object name, Object path){ return act(name, path, false, DataFiles::open); }
  static Object writeBytes(Object name, Object path, byte[] bytes){ return act(name, path, true, (n, root, p) -> write(n, root, p, bytes)); }
  static Object writeText(Object name, Object path, Object content, Charset cs){
    return act(name, path, true, (n, root, p) -> write(n, root, p, encode(n, p, ((UStr$s$0Instance)content).val(), cs)));
  }
  static Object delete(Object name, Object path){ return act(name, path, true, DataFiles::delete); }
  static byte[] bytes(Path path, List<String> steps, String entry) throws IOException{
    if (entry.isEmpty()){ return Files.readAllBytes(path); }
    return item(items(path, steps), entry).bytes();
  }
  private static Object act(Object name, Object path, boolean write, Op op){
    var n= AssetBytesRead.str(name);
    var p= List$o$1Instance.asJava(path).stream().map(AssetBytesRead::str).toList();
    try{ return ok(op.apply(n, root(n, write), p)); }
    catch (Refused r){ return fail(r.getMessage()); }
    catch (IOException e){ throw failed(n, p, write, e); }
    catch (UncheckedIOException e){ throw failed(n, p, write, e.getCause()); }
  }
  private static Error failed(String name, List<String> path, boolean write, IOException e){
    return nonDetErr("The "+at(name, path)+" could not be "+(write ? "changed" : "read")+": "+e.getClass().getSimpleName());
  }
  private static Path root(String name, boolean write){
    var res= edits.containsKey(name) || write ? edits.get(name) : reads.get(name);
    if (res != null){ return res; }
    if (write && reads.containsKey(name)){ throw new Refused("\""+name+"\" names a data project this program reads, but does not write."+named(" The data projects it writes are named ", edits.keySet(), " This program writes no data project.")); }
    var all= new TreeSet<>(reads.keySet());
    all.addAll(edits.keySet());
    throw new Refused("\""+name+"\" names no data project of this program."+named(" Its data projects are named ", all, " This program has no data project."));
  }
  private static String named(String start, Set<String> names, String none){
    if (names.isEmpty()){ return none; }
    return start+names.stream().map(n -> "\""+n+"\"").collect(Collectors.joining(", "))+".";
  }
  private static String at(String name, List<String> path){ return "path \""+String.join("/", path)+"\" of \""+name+"\""; }
  private static Refused refused(String name, List<String> path, String why){ return new Refused("The "+at(name, path)+" "+why); }
  private static boolean atom(String s){ return s.matches("[a-z_][a-z0-9_]*") && !reserved.contains(s); }
  private static boolean fileName(String s){
    var dot= s.indexOf('.');
    if (dot < 0){ return noExt.contains(s); }
    var ext= s.substring(dot+1);
    return atom(s.substring(0, dot)) && !ext.equals("zip") && (multiDot.contains(ext) || ext.matches("[a-z0-9]{1,16}"));
  }
  private static void folders(String name, List<String> path, List<String> folders){
    for (var s : folders){
      if (!atom(s)){ throw refused(name, path, "is not valid: \""+s+"\" is not a valid folder name. A folder name uses only lowercase letters, digits and underscores, starts with a letter or an underscore, and is not \"con\", \"prn\", \"aux\", \"nul\", \"com1\" to \"com9\" or \"lpt1\" to \"lpt9\"."); }
    }
  }
  private static void file(String name, List<String> path){
    if (path.isEmpty()){ throw refused(name, path, "names the root folder, not a file."); }
    folders(name, path, path.subList(0, path.size()-1));
    if (!fileName(path.getLast())){ throw refused(name, path, "is not valid: \""+path.getLast()+"\" is not a valid file name. A file name is a valid folder name, a dot and an extension of 1 to 16 lowercase letters or digits, other than \"zip\" (a zip file is a folder); a few well known names, like \"readme\", have no extension, and a few well known extensions, like \"tar.gz\", have two parts."); }
  }
  private static void writable(String name, List<String> path){
    file(name, path);
    if (String.join("/", path).length() > 200){ throw refused(name, path, "is not valid: a path is at most 200 characters long."); }
    if (path.size() == 1 && path.getFirst().endsWith(".fearless")){ throw refused(name, path, "is not written or deleted: a file with the extension \"fearless\" at the top of a data project is its marker file."); }
  }
  private static Object list(String name, Path root, List<String> path) throws IOException{
    folders(name, path, path);
    var at= resolve(root, path);
    if (at.isEmpty()){ throw refused(name, path, "names no folder."); }
    if (at.get() instanceof File){ throw refused(name, path, "names a file, not a folder."); }
    var names= names(at.get());
    names.stream().filter(n -> !atom(n) && !fileName(n)).findFirst().ifPresent(n -> { throw nonDetErr("The folder at the "+at(name, path)+" holds \""+n+"\", which is not a valid file or folder name: the data project changed while this program runs."); });
    return List$o$1Instance.wrap(names.stream().<Object>map(Str$c$0Instance::instance).toList());
  }
  private static Object open(String name, Path root, List<String> path) throws IOException{
    file(name, path);
    var at= resolve(root, path);
    if (!(at.orElse(null) instanceof File f)){ throw refused(name, path, at.isEmpty() ? "names no file." : "names a folder, not a file."); }
    return new InputFile(name+"/"+String.join("/", path), f.path(), f.steps(), f.entry());
  }
  private static Object write(String name, Path root, List<String> path, byte[] bytes) throws IOException{
    writable(name, path);
    synchronized (lock){
      Node at= new Dir(root);
      var i= 0;
      for (; i < path.size()-1; i += 1){
        var next= child(at, path.get(i));
        if (next.isEmpty()){ break; }
        if (next.get() instanceof File){ throw refused(name, path, "is not valid: \""+path.get(i)+"\" is a file, not a folder."); }
        at = next.get();
      }
      if (i == path.size()-1){ clash(name, path, at); }
      put(at, path.subList(i, path.size()), bytes, root);
    }
    return Void$o$0.instance;
  }
  private static void clash(String name, List<String> path, Node at) throws IOException{
    var file= path.getLast();
    if (child(at, file).filter(n -> !(n instanceof File)).isPresent()){ throw refused(name, path, "names a folder, not a file."); }
    var dot= file.indexOf('.');
    var base= dot < 0 ? file : file.substring(0, dot);
    if (!noExt.contains(base)){ return; }
    var other= dot < 0
      ? names(at).stream().filter(n -> n.startsWith(file+".")).findFirst()
      : child(at, base).filter(File.class::isInstance).map(_ -> base);
    other.ifPresent(o -> { throw refused(name, path, "is not written: its folder holds \""+o+"\", and a file without extension can not share its name with a file with an extension."); });
  }
  private static void put(Node at, List<String> rest, byte[] bytes, Path root) throws IOException{
    switch (at){
      case Dir d -> {
        var target= d.path();
        for (var s : rest){ target = target.resolve(s); }
        Files.createDirectories(target.getParent());
        save(target, bytes);
      }
      case ZipDir z -> editZip(z.zip(), z.steps(), m -> m.put(z.prefix()+String.join("/", rest), new Item(System.currentTimeMillis(), bytes)), root);
      case File _ -> throw new AssertionError(at);
    }
  }
  private static Object delete(String name, Path root, List<String> path) throws IOException{
    writable(name, path);
    synchronized (lock){
      var at= resolve(root, path);
      if (!(at.orElse(null) instanceof File f)){ throw refused(name, path, at.isEmpty() ? "names no file." : "names a folder, not a file."); }
      if (!f.entry().isEmpty()){ editZip(f.path(), f.steps(), m -> m.remove(f.entry()), root); }
      else{ Files.delete(f.path()); prune(f.path().getParent(), root); }
    }
    return Void$o$0.instance;
  }
  private static byte[] encode(String name, List<String> path, int[] cps, Charset cs){
    var encoder= cs.newEncoder();
    for (var i= 0; i < cps.length; i += 1){
      if (!encoder.canEncode(Character.toString(cps[i]))){ throw refused(name, path, "is not written: "+cs.name()+" does not encode the character U+%04X at index %d of the text.".formatted(cps[i], i)); }
    }
    return new String(cps, 0, cps.length).getBytes(cs);
  }
  private static Optional<Node> resolve(Path root, List<String> path) throws IOException{
    Optional<Node> at= Optional.of(new Dir(root));
    for (var s : path){
      if (at.isEmpty()){ return at; }
      at = child(at.get(), s);
    }
    return at;
  }
  private static Optional<Node> child(Node at, String s) throws IOException{
    switch (at){
      case Dir d -> {
        var p= d.path().resolve(s);
        if (Files.isDirectory(p, NOFOLLOW_LINKS)){ return Optional.of(new Dir(p)); }
        if (Files.isRegularFile(p, NOFOLLOW_LINKS)){ return Optional.of(new File(p, List.of(), "")); }
        var zip= d.path().resolve(s+".zip");
        return Files.isRegularFile(zip, NOFOLLOW_LINKS) ? Optional.of(new ZipDir(zip, List.of(), "")) : Optional.empty();
      }
      case ZipDir z -> {
        var items= items(z.zip(), z.steps());
        var name= z.prefix()+s;
        if (items.containsKey(name)){ return Optional.of(new File(z.zip(), z.steps(), name)); }
        if (items.containsKey(name+".zip")){ return Optional.of(new ZipDir(z.zip(), Stream.concat(z.steps().stream(), Stream.of(name+".zip")).toList(), "")); }
        return items.keySet().stream().anyMatch(k -> k.startsWith(name+"/")) ? Optional.of(new ZipDir(z.zip(), z.steps(), name+"/")) : Optional.empty();
      }
      case File _ -> { return Optional.empty(); }
    }
  }
  private static TreeSet<String> names(Node folder) throws IOException{
    var res= new TreeSet<String>();
    switch (folder){
      case Dir d -> {
        try (var s= Files.list(d.path())){
          for (var p : s.toList()){ res.add(unzipped(p.getFileName().toString(), Files.isRegularFile(p, NOFOLLOW_LINKS))); }
        }
      }
      case ZipDir z -> {
        for (var k : items(z.zip(), z.steps()).keySet()){
          if (!k.startsWith(z.prefix())){ continue; }
          var rest= k.substring(z.prefix().length());
          var slash= rest.indexOf('/');
          res.add(slash < 0 ? unzipped(rest, true) : rest.substring(0, slash));
        }
      }
      case File _ -> throw new AssertionError(folder);
    }
    res.removeIf(n -> n.startsWith("."));
    return res;
  }
  private static String unzipped(String name, boolean file){ return file && name.endsWith(".zip") ? name.substring(0, name.length()-4) : name; }
  private static LinkedHashMap<String,Item> items(Path zip, List<String> steps) throws IOException{
    var res= items(Files.readAllBytes(zip));
    for (var s : steps){ res = items(item(res, s).bytes()); }
    return res;
  }
  private static LinkedHashMap<String,Item> items(byte[] zip) throws IOException{
    var res= new LinkedHashMap<String,Item>();
    try (var in= new ZipInputStream(new ByteArrayInputStream(zip), UTF_8)){
      for (var e= in.getNextEntry(); e != null; e = in.getNextEntry()){
        if (!e.isDirectory()){ res.put(e.getName(), new Item(e.getTime(), in.readAllBytes())); }
      }
    }
    return res;
  }
  private static Item item(Map<String,Item> items, String name) throws NoSuchFileException{
    var res= items.get(name);
    if (res == null){ throw new NoSuchFileException(name); }
    return res;
  }
  private static void editZip(Path zip, List<String> steps, Consumer<LinkedHashMap<String,Item>> op, Path root) throws IOException{
    var bytes= edit(Files.readAllBytes(zip), steps, op);
    if (bytes != null){ save(zip, bytes); return; }
    Files.delete(zip);
    prune(zip.getParent(), root);
  }
  private static byte[] edit(byte[] zip, List<String> steps, Consumer<LinkedHashMap<String,Item>> op) throws IOException{
    var items= items(zip);
    if (steps.isEmpty()){ op.accept(items); }
    else{
      var inner= edit(item(items, steps.getFirst()).bytes(), steps.subList(1, steps.size()), op);
      if (inner == null){ items.remove(steps.getFirst()); }
      else{ items.put(steps.getFirst(), new Item(System.currentTimeMillis(), inner)); }
    }
    if (items.isEmpty()){ return null; }
    var out= new ByteArrayOutputStream();
    try (var z= new ZipOutputStream(out, UTF_8)){
      for (var e : items.entrySet()){
        var entry= new ZipEntry(e.getKey());
        entry.setTime(e.getValue().time());
        z.putNextEntry(entry);
        z.write(e.getValue().bytes());
        z.closeEntry();
      }
    }
    return out.toByteArray();
  }
  private static void save(Path target, byte[] bytes) throws IOException{
    var tmp= target.resolveSibling("."+UUID.randomUUID()+".tmp");
    Files.write(tmp, bytes, CREATE_NEW);
    Files.move(tmp, target, ATOMIC_MOVE);
  }
  private static void prune(Path dir, Path root) throws IOException{
    for (var d= dir; !d.equals(root) && empty(d); d = d.getParent()){ Files.delete(d); }
  }
  private static boolean empty(Path dir) throws IOException{
    try (var s= Files.list(dir)){ return s.findAny().isEmpty(); }
  }
}
