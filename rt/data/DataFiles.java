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
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

final class DataFiles{
  static final Map<String,Path> reads= linked("fearlessDataRead.");
  static final Map<String,Path> edits= linked("fearlessDataEdit.");
  static final Map<String,Path> readable= readable();
  private static final Object lock= new Object();
  private static final Set<String> reserved= Set.of("con", "prn", "aux", "nul", "com1", "com2", "com3", "com4", "com5", "com6", "com7", "com8", "com9", "lpt1", "lpt2", "lpt3", "lpt4", "lpt5", "lpt6", "lpt7", "lpt8", "lpt9");
  private static final Set<String> noExt= Set.of("readme", "license", "copying", "notice", "authors", "contributors", "changelog", "changes", "news", "todo", "thanks", "security", "contributing", "code_of_conduct");
  private static final Set<String> multiDot= Set.of("tar.gz", "tar.bz2", "tar.xz", "tar.zst", "tar.lz", "tar.lz4", "d.ts", "d.ts.map", "js.map", "css.map", "min.js", "min.css");
  sealed interface Node{}
  record Dir(Path path) implements Node{}
  record ZipDir(Path zip, List<String> steps, String prefix) implements Node{}
  record Resource(Path path, List<String> steps, String entry) implements Node{}
  record Item(long time, byte[] bytes){}
  record Root(String name, Path path){}
  interface Op{ Object apply(String name, Path root, List<String> path) throws IOException; }
  private static Map<String,Path> linked(String prefix){
    var res= new TreeMap<String,Path>();
    for (var k : System.getProperties().stringPropertyNames()){
      if (k.startsWith(prefix)){ res.put(k.substring(prefix.length()), pathProperty(k)); }
    }
    return Collections.unmodifiableMap(res);
  }
  private static Map<String,Path> readable(){
    var res= new TreeMap<>(reads);
    res.putAll(edits);
    return Collections.unmodifiableMap(res);
  }
  static Root root(Map<String,Path> view, String kind, Object data){
    var name= AssetBytesRead.str(((Named$1c$0)data).read$name$0());
    var res= view.get(name);
    if (res == null){ throw nonDetErr("No "+kind+" is mounted on \""+name+"\"."); }
    return new Root(name, res);
  }
  static Object paths(Root root, Object path){ return act(root, path, false, DataFiles::list); }
  static Object read(Root root, Object path){ return act(root, path, false, DataFiles::open); }
  static Object writeBytes(Root root, Object path, byte[] bytes){ return act(root, path, true, (n, r, p) -> write(n, r, p, bytes)); }
  static Object writeText(Root root, Object path, Object content, Charset cs){
    var cps= ((UStr$s$0Instance)content).val();
    return writeBytes(root, path, new String(cps, 0, cps.length).getBytes(cs));
  }
  static Object writeLossy(Root root, Object path, Object content, Object recover, int max){
    var out= new ByteArrayOutputStream();
    var cps= ((UStr$s$0Instance)content).val();
    for (var i= 0; i < cps.length; i += 1){
      if (cps[i] <= max){ out.write(cps[i]); continue; }
      for (var b : List$o$1Instance.asJava(((UnencodableRecover$8fgg$0)recover).mut$$hash$2(Nat$c$0Instance.instance(cps[i]), Nat$c$0Instance.instance(i)))){ out.write(Byte$o$0Instance.unwrap(b)); }
    }
    return writeBytes(root, path, out.toByteArray());
  }
  static Object delete(Root root, Object path){ return act(root, path, true, DataFiles::delete); }
  static byte[] bytes(Path path, List<String> steps, String entry) throws IOException{
    if (entry.isEmpty()){ return Files.readAllBytes(path); }
    return item(items(path, steps), entry).bytes();
  }
  static void reachable(Root root){
    if (!Files.isDirectory(root.path())){ throw nonDetErr("The root folder of the data project mounted on \""+root.name()+"\" cannot be reached."); }
  }
  static Error lost(String msg, IOException e){ return e instanceof NoSuchFileException ? detErr(msg) : nonDetErr(msg); }
  private static Object act(Root root, Object path, boolean write, Op op){
    var p= List$o$1Instance.asJava(path).stream().map(AssetBytesRead::str).toList();
    reachable(root);
    try{ return op.apply(root.name(), root.path(), p); }
    catch (IOException e){ throw failed(root.name(), p, write, e); }
    catch (UncheckedIOException e){ throw failed(root.name(), p, write, e.getCause()); }
  }
  private static Error failed(String name, List<String> path, boolean write, IOException e){
    return lost("The "+at(name, path)+"\ncould not be "+(write ? "changed" : "read")+": "+e.getClass().getSimpleName(), e);
  }
  private static String at(String name, List<String> path){ return "path "+name+"/"+String.join("/", path); }
  private static Error refused(String name, List<String> path, String why){ return detErr("The "+at(name, path)+"\n"+why); }
  private static Error invalid(String name, List<String> path, String why){ return detErr("The "+at(name, path)+"\nis not valid: "+why); }
  private static boolean atom(String s){ return s.matches("[a-z_][a-z0-9_]*") && !reserved.contains(s); }
  private static boolean fileName(String s){
    var dot= s.indexOf('.');
    if (dot < 0){ return noExt.contains(s); }
    var ext= s.substring(dot+1);
    return atom(s.substring(0, dot)) && !ext.equals("zip") && (multiDot.contains(ext) || ext.matches("[a-z0-9]{1,16}"));
  }
  private static void folders(String name, List<String> path, List<String> folders){
    for (var s : folders){
      if (!atom(s)){ throw invalid(name, path, "\""+s+"\" is not a valid folder name. A folder name uses only lowercase letters, digits and underscores, starts with a letter or an underscore, and is not \"con\", \"prn\", \"aux\", \"nul\", \"com1\" to \"com9\" or \"lpt1\" to \"lpt9\"."); }
    }
  }
  private static void resource(String name, List<String> path){
    if (path.isEmpty()){ throw refused(name, path, "names the root folder, not a file."); }
    folders(name, path, path.subList(0, path.size()-1));
    if (path.size() == 1 && path.getFirst().endsWith(".fearless")){ throw invalid(name, path, "the top of a data project holds no file with the extension \"fearless\": that is the place of the marker of the project, which is not part of the data project."); }
    if (!fileName(path.getLast())){ throw invalid(name, path, "\""+path.getLast()+"\" is not a valid file name. A file name is a valid folder name, a dot and an extension of 1 to 16 lowercase letters or digits, other than \"zip\" (a zip is a folder); a few well known names, like \"readme\", have no extension, and a few well known extensions, like \"tar.gz\", have two parts."); }
  }
  private static void writable(String name, List<String> path){
    resource(name, path);
    if (String.join("/", path).length() > 200){ throw invalid(name, path, "a path is at most 200 characters long."); }
  }
  private static Object list(String name, Path root, List<String> path) throws IOException{
    folders(name, path, path);
    var at= resolve(root, path);
    if (at.isEmpty()){ throw refused(name, path, "names no resource."); }
    if (at.get() instanceof Resource){ throw refused(name, path, "names a file, not a folder."); }
    var names= names(at.get());
    if (path.isEmpty()){ names.removeIf(n -> n.endsWith(".fearless")); }
    names.stream().filter(n -> !atom(n) && !fileName(n)).findFirst().ifPresent(n -> { throw nonDetErr("The folder at the "+at(name, path)+"\nholds \""+n+"\", which is not a valid resource name: the data project changed while this program runs."); });
    return List$o$1Instance.wrap(names.stream().<Object>map(Str$c$0Instance::instance).toList());
  }
  private static Object open(String name, Path root, List<String> path) throws IOException{
    resource(name, path);
    var at= resolve(root, path);
    if (!(at.orElse(null) instanceof Resource f)){ throw refused(name, path, at.isEmpty() ? "names no resource." : "names a folder, not a file."); }
    return new InputFile(Optional.of(new Root(name, root)), name+"/"+String.join("/", path), f.path(), f.steps(), f.entry());
  }
  private static Object write(String name, Path root, List<String> path, byte[] bytes) throws IOException{
    writable(name, path);
    synchronized (lock){
      Node at= new Dir(root);
      var i= 0;
      for (; i < path.size()-1; i += 1){
        var next= child(at, path.get(i));
        if (next.isEmpty()){ break; }
        if (next.get() instanceof Resource){ throw refused(name, path, "is not valid: \""+path.get(i)+"\" is a file, not a folder."); }
        at = next.get();
      }
      if (i == path.size()-1){ clash(name, path, at); }
      put(at, path.subList(i, path.size()), bytes, root);
    }
    return Void$o$0.instance;
  }
  private static void clash(String name, List<String> path, Node at) throws IOException{
    var last= path.getLast();
    if (child(at, last).filter(n -> !(n instanceof Resource)).isPresent()){ throw refused(name, path, "names a folder, not a file."); }
    var dot= last.indexOf('.');
    var base= dot < 0 ? last : last.substring(0, dot);
    if (!noExt.contains(base)){ return; }
    var other= dot < 0
      ? names(at).stream().filter(n -> n.startsWith(last+".")).findFirst()
      : child(at, base).filter(Resource.class::isInstance).map(_ -> base);
    other.ifPresent(o -> { throw refused(name, path, "is not written: its folder holds \""+o+"\", and a file without extension cannot share its name with a file with an extension."); });
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
      case Resource _ -> throw new AssertionError(at);
    }
  }
  private static Object delete(String name, Path root, List<String> path) throws IOException{
    writable(name, path);
    synchronized (lock){
      var at= resolve(root, path);
      if (!(at.orElse(null) instanceof Resource f)){ throw refused(name, path, at.isEmpty() ? "names no resource." : "names a folder, not a file."); }
      if (!f.entry().isEmpty()){ editZip(f.path(), f.steps(), m -> m.remove(f.entry()), root); }
      else{ Files.delete(f.path()); prune(f.path().getParent(), root); }
    }
    return Void$o$0.instance;
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
        if (Files.isRegularFile(p, NOFOLLOW_LINKS)){ return Optional.of(new Resource(p, List.of(), "")); }
        var zip= d.path().resolve(s+".zip");
        return Files.isRegularFile(zip, NOFOLLOW_LINKS) ? Optional.of(new ZipDir(zip, List.of(), "")) : Optional.empty();
      }
      case ZipDir z -> {
        var items= items(z.zip(), z.steps());
        var name= z.prefix()+s;
        if (items.containsKey(name)){ return Optional.of(new Resource(z.zip(), z.steps(), name)); }
        if (items.containsKey(name+".zip")){ return Optional.of(new ZipDir(z.zip(), Stream.concat(z.steps().stream(), Stream.of(name+".zip")).toList(), "")); }
        return items.keySet().stream().anyMatch(k -> k.startsWith(name+"/")) ? Optional.of(new ZipDir(z.zip(), z.steps(), name+"/")) : Optional.empty();
      }
      case Resource _ -> { return Optional.empty(); }
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
      case Resource _ -> throw new AssertionError(folder);
    }
    res.removeIf(n -> n.startsWith("."));
    return res;
  }
  private static String unzipped(String name, boolean regular){ return regular && name.endsWith(".zip") ? name.substring(0, name.length()-4) : name; }
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
