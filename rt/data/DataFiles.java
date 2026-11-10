package _base;

import static _base.Util.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

final class DataFiles{
  static final Map<String,Path> mounts= linked("fearlessData.");
  static final Map<String,Path> locks= linked("fearlessDataLock.");
  private static final Map<Path,FileChannel> channels= new ConcurrentHashMap<>();
  private static final Object gate= new Object();
  private static Map<List<Object>,LinkedHashMap<String,Item>> zips= null;
  private static final ReentrantLock one= new ReentrantLock();
  static final String heldKey= "fearlessDataHeld";
  private static final String inherited= System.getProperty(heldKey);
  private static Map<Path,Boolean> current= inherited == null ? null : heldBy(inherited);
  private static String held= null;
  private static final List<Process> group= new ArrayList<>();
  static{ if (inherited != null){ Runtime.getRuntime().addShutdownHook(new Thread(DataFiles::awaitGroup)); } }
  private static final Pattern tmpName= Pattern.compile("\\.[0-9a-f-]{36}\\.tmp");
  private static final Set<String> reserved= Set.of("con", "prn", "aux", "nul", "com1", "com2", "com3", "com4", "com5", "com6", "com7", "com8", "com9", "lpt1", "lpt2", "lpt3", "lpt4", "lpt5", "lpt6", "lpt7", "lpt8", "lpt9");
  private static final Set<String> multiDot= Set.of("tar.gz", "tar.bz2", "tar.xz", "tar.zst", "tar.lz", "tar.lz4", "d.ts", "d.ts.map", "js.map", "css.map", "min.js", "min.css");
  sealed interface Node{}
  record Dir(Path path) implements Node{}
  record ZipDir(Path zip, List<String> steps, String prefix) implements Node{}
  record Resource(Path path, List<String> steps, String entry) implements Node{}
  record Item(long time, byte[] bytes){}
  record Root(String name, Path path, Path lock){}
  interface Op{ Object apply(Root root, List<String> path) throws IOException; }
  interface Locked<T>{ T run() throws IOException; }
  record Hold(Root root, boolean edit){}
  private static Map<String,Path> linked(String prefix){
    var res= new TreeMap<String,Path>();
    for (var k : System.getProperties().stringPropertyNames()){
      if (k.startsWith(prefix)){ res.put(k.substring(prefix.length()), pathProperty(k)); }
    }
    return Collections.unmodifiableMap(res);
  }
  static Root root(String kind, Object data){
    var name= AssetBytesRead.str(((Named$1c$0)data).read$name$0());
    var res= mounts.get(name);
    if (res == null){ throw nonDetErr("No "+kind+" is mounted on \""+name+"\"."); }
    return new Root(name, res, Objects.requireNonNull(locks.get(name)));
  }
  static Object paths(Root root, Object path){ return act(root, path, false, (r, p) -> list(r.name(), r.path(), p)); }
  static Object read(Root root, Object path){ return act(root, path, false, DataFiles::open); }
  static Object writeBytes(Root root, Object path, byte[] bytes){ return act(root, path, true, (r, p) -> write(r.name(), r.path(), p, bytes)); }
  static Object writeText(Root root, Object path, Object content, Charset cs){
    var cps= ((UStr$s$0Instance)content).val();
    return writeBytes(root, path, new String(cps, 0, cps.length).getBytes(cs));
  }
  static Object writeLossy(Root root, Object path, Object content, Object recover, int max){
    var out= new ByteArrayOutputStream();
    var cps= ((UStr$s$0Instance)content).val();
    for (var i= 0; i < cps.length; i += 1){
      if (cps[i] <= max){ out.write(cps[i]); continue; }
      out.writeBytes(Byte$o$0Instance.unwrapAll(((UnencodableRecover$8fgg$0)recover).mut$$hash$2(Nat$c$0Instance.instance(cps[i]), Nat$c$0Instance.instance(i))));
    }
    return writeBytes(root, path, out.toByteArray());
  }
  static Object delete(Root root, Object path){ return act(root, path, true, (r, p) -> delete(r.name(), r.path(), p)); }
  private static byte[] bytes(Root root, List<String> path) throws IOException{
    reachable(root);
    if (!(resolve(root.path(), path).orElse(null) instanceof Resource f)){ throw new NoSuchFileException(root.name()+"/"+String.join("/", path)); }
    if (f.entry().isEmpty()){ return Files.readAllBytes(f.path()); }
    return item(items(f.path(), f.steps(), f.entry()), f.entry()).bytes();
  }
  static void reachable(Root root){
    if (!Files.isDirectory(root.path())){ throw nonDetErr("The root folder of the data project mounted on \""+root.name()+"\" cannot be reached."); }
  }
  static Error lost(String msg, IOException e){ return e instanceof NoSuchFileException ? detErr(msg) : nonDetErr(msg); }
  private static Object act(Root root, Object path, boolean write, Op op){
    var p= List$o$1Instance.asJava(path).stream().map(AssetBytesRead::str).toList();
    reachable(root);
    try{ return held(root, write, () -> op.apply(root, p)); }
    catch (IOException e){ throw failed(root.name(), p, write, e); }
    catch (UncheckedIOException e){ throw failed(root.name(), p, write, e.getCause()); }
  }
  private static <T> T held(Root root, boolean edit, Locked<T> body) throws IOException{
    synchronized (gate){
      zips = new HashMap<>();
      try{
        if (current != null){ return inside(root, edit, body); }
        try (var _= take(root, edit)){ return body.run(); }
      }
      finally{ zips = null; }
    }
  }
  private static FileLock take(Root root, boolean edit) throws IOException{
    var res= channels.computeIfAbsent(root.lock(), DataFiles::channel).lock(0, Long.MAX_VALUE, !edit);
    if (edit){ dropTmps(root.path()); }
    return res;
  }
  private static <T> T inside(Root root, boolean edit, Locked<T> body) throws IOException{
    var locked= current.get(root.lock());
    if (locked == null){ throw detErr("The data project mounted on \""+root.name()+"\" is used while sys.locked runs, but it is not in the reads or the edits of that sys.locked."); }
    if (edit && !locked){ throw detErr("The data project mounted on \""+root.name()+"\" is changed while sys.locked runs, but that sys.locked holds only a read lock of it: it is in the reads, not in the edits."); }
    try (var _= channels.computeIfAbsent(inner(root.lock()), DataFiles::channel).lock(0, Long.MAX_VALUE, !edit)){ return body.run(); }
  }
  private static Path inner(Path lock){ return lock.resolveSibling(lock.getFileName().toString().replace(".lock", "_inner.lock")); }
  static Object locked(Object reads, Object edits, Supplier<Object> f){
    if (inherited != null){ throw detErr("sys.locked is called by a process started while the function of a sys.locked runs."); }
    if (one.isHeldByCurrentThread()){ throw detErr("sys.locked is called inside the function of another sys.locked."); }
    var want= new TreeMap<Path,Hold>();
    var types= new ArrayList<String>();
    List$o$1Instance.asJava(reads).forEach(d -> hold(want, types, root("data project", d), false));
    List$o$1Instance.asJava(edits).forEach(d -> hold(want, types, root("dataEdit project", d), true));
    one.lock();
    var taken= new ArrayList<FileLock>();
    try{
      synchronized (gate){
        for (var h : want.values()){ reachable(h.root()); taken.add(take(h.root(), h.edit())); }
        current = want.values().stream().collect(Collectors.toMap(h -> h.root().lock(), Hold::edit));
        held = String.join(",", types);
      }
      return f.get();
    }
    catch (IOException e){ throw nonDetErr("The locks of sys.locked can not be taken: "+e.getClass().getSimpleName()); }
    finally{
      awaitGroup();
      synchronized (gate){ current = null; held = null; taken.forEach(DataFiles::release); }
      one.unlock();
    }
  }
  private static void hold(TreeMap<Path,Hold> want, ArrayList<String> types, Root r, boolean edit){
    want.merge(r.lock(), new Hold(r, edit), (a, b) -> a.edit() ? a : b);
    types.add(r.name()+(edit ? ":edit" : ":read"));
  }
  private static Map<Path,Boolean> heldBy(String text){
    var res= new TreeMap<Path,Boolean>();
    for (var t : text.split(",")){
      var colon= t.lastIndexOf(':');
      res.merge(Objects.requireNonNull(locks.get(t.substring(0, colon))), t.substring(colon+1).equals("edit"), Boolean::logicalOr);
    }
    return Collections.unmodifiableMap(res);
  }
  static Optional<String> heldArg(){
    synchronized (gate){ return Optional.ofNullable(held).map(h -> "-D"+heldKey+"="+h); }
  }
  static void started(Process p){
    synchronized (gate){ if (current != null){ group.add(p); } }
  }
  private static void awaitGroup(){
    List<Process> ps;
    synchronized (gate){ ps= List.copyOf(group); group.clear(); }
    ps.forEach(p -> p.onExit().join());
  }
  private static void hold(TreeMap<Path,Hold> want, Root r, boolean edit){ want.merge(r.lock(), new Hold(r, edit), (a, b) -> a.edit() ? a : b); }
  private static void release(FileLock l){
    try{ l.release(); }
    catch (IOException e){ throw new UncheckedIOException(e); }
  }
  private static FileChannel channel(Path lock){
    try{ return FileChannel.open(lock, CREATE, READ, WRITE); }
    catch (IOException e){ throw new UncheckedIOException(e); }
  }
  private static void dropTmps(Path root) throws IOException{
    try (var s= Files.list(root)){
      for (var p : s.filter(p -> tmpName.matcher(p.getFileName().toString()).matches()).toList()){ deleteTree(p); }
    }
  }
  private static Path tmp(Path root){ return root.resolve("."+UUID.randomUUID()+".tmp"); }
  private static Error failed(String name, List<String> path, boolean write, IOException e){
    return lost("The "+at(name, path)+"\ncan not be "+(write ? "changed" : "read")+".", e);
  }
  private static String at(String name, List<String> path){ return "path "+name+"/"+String.join("/", path); }
  private static Error refused(String name, List<String> path, String why){ return detErr("The "+at(name, path)+"\n"+why); }
  private static Error invalid(String name, List<String> path, String why){ return detErr("The "+at(name, path)+"\nis not valid: "+why); }
  private static boolean atom(String s){ return s.matches("[a-z_][a-z0-9_]*") && !reserved.contains(s); }
  private static boolean fileName(String s){
    var dot= s.indexOf('.');
    if (dot < 0){ return false; }
    var ext= s.substring(dot+1);
    return atom(s.substring(0, dot)) && !ext.equals("zip") && !ext.equals("fearless") && (multiDot.contains(ext) || ext.matches("[a-z0-9]{1,16}"));
  }
  private static void folders(String name, List<String> path, List<String> folders){
    for (var s : folders){
      if (!atom(s)){ throw invalid(name, path, "\""+s+"\" is not a valid folder name. A folder name uses only lowercase letters, digits and underscores, starts with a letter or an underscore, and is not \"con\", \"prn\", \"aux\", \"nul\", \"com1\" to \"com9\" or \"lpt1\" to \"lpt9\"."); }
    }
  }
  private static void resource(String name, List<String> path){
    if (path.isEmpty()){ throw refused(name, path, "names the root folder, not a file."); }
    folders(name, path, path.subList(0, path.size()-1));
    if (!fileName(path.getLast())){ throw notFileName(name, path); }
  }
  private static Error notFileName(String name, List<String> path){
    return invalid(name, path, "\""+path.getLast()+"\" is not a valid file name.\n"
      +"A file name is a name, a dot and an extension.\n"
      +"The name uses only lowercase letters, digits and underscores, starts with a letter or an underscore, and is not \"con\", \"prn\", \"aux\", \"nul\", \"com1\" to \"com9\" or \"lpt1\" to \"lpt9\".\n"
      +"The extension is 1 to 16 lowercase letters or digits, other than \"zip\" and \"fearless\", or one of "+quoted(multiDot)+".");
  }
  private static String quoted(Set<String> names){ return String.join(", ", names.stream().sorted().map(n -> "\""+n+"\"").toList()); }
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
  private static Object open(Root r, List<String> path) throws IOException{
    var name= r.name();
    resource(name, path);
    var at= resolve(r.path(), path);
    if (!(at.orElse(null) instanceof Resource)){ throw refused(name, path, at.isEmpty() ? "names no resource." : "names a folder, not a file."); }
    var label= name+"/"+String.join("/", path);
    return new InputFile("Resource with path "+label, label, () -> held(r, false, () -> bytes(r, path)));
  }
  private static Object write(String name, Path root, List<String> path, byte[] bytes) throws IOException{
    writable(name, path);
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
    return Void$o$0.instance;
  }
  private static void clash(String name, List<String> path, Node at) throws IOException{
    if (child(at, path.getLast()).filter(n -> !(n instanceof Resource)).isPresent()){ throw refused(name, path, "names a folder, not a file."); }
  }
  private static void put(Node at, List<String> rest, byte[] bytes, Path root) throws IOException{
    switch (at){
      case Dir d -> {
        var tmp= tmp(root);
        var file= tmp;
        for (var s : rest.subList(1, rest.size())){ file = file.resolve(s); }
        Files.createDirectories(file.getParent());
        Files.write(file, bytes, CREATE_NEW);
        Files.move(tmp, d.path().resolve(rest.getFirst()), ATOMIC_MOVE);
      }
      case ZipDir z -> editZip(z.zip(), z.steps(), m -> m.put(z.prefix()+String.join("/", rest), new Item(System.currentTimeMillis(), bytes)), root);
      case Resource _ -> throw new AssertionError(at);
    }
  }
  private static Object delete(String name, Path root, List<String> path) throws IOException{
    if (path.isEmpty()){ throw refused(name, path, "names the root folder, which can not be deleted."); }
    if (atom(path.getLast())){ folders(name, path, path); }
    else{ writable(name, path); }
    var at= resolve(root, path);
    if (at.isEmpty()){ throw refused(name, path, "names no resource."); }
    switch (at.get()){
      case Resource f when f.entry().isEmpty() -> remove(f.path(), root);
      case Resource f -> editZip(f.path(), f.steps(), m -> m.remove(f.entry()), root);
      case Dir d -> remove(d.path(), root);
      case ZipDir z when !z.prefix().isEmpty() -> editZip(z.zip(), z.steps(), m -> m.keySet().removeIf(k -> k.startsWith(z.prefix())), root);
      case ZipDir z when z.steps().isEmpty() -> remove(z.zip(), root);
      case ZipDir z -> editZip(z.zip(), z.steps().subList(0, z.steps().size()-1), m -> m.remove(z.steps().getLast()), root);
    }
    return Void$o$0.instance;
  }
  private static void deleteTree(Path p) throws IOException{
    if (isDir(p)){ try (var s= Files.list(p)){ for (var c : s.toList()){ deleteTree(c); } } }
    Files.delete(p);
  }
  private static boolean isDir(Path p){ return Files.isDirectory(p, NOFOLLOW_LINKS) && !isLink(p); }
  // A junction or a drive mounted in a folder of Windows is a reparse point (isOther) that resolves elsewhere, or nowhere.
  // A OneDrive placeholder is a reparse point too, but it resolves to itself.
  static boolean isLink(Path p){
    BasicFileAttributes a;
    try{ a= Files.readAttributes(p, BasicFileAttributes.class, NOFOLLOW_LINKS); }
    catch (IOException _){ return false; }
    if (a.isSymbolicLink()){ return true; }
    if (!a.isOther()){ return false; }
    try{ return !p.toRealPath().equals(p.toAbsolutePath().getParent().toRealPath().resolve(p.getFileName())); }
    catch (IOException _){ return true; }
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
        if (isDir(p)){ return Optional.of(new Dir(p)); }
        if (Files.isRegularFile(p, NOFOLLOW_LINKS)){ return Optional.of(new Resource(p, List.of(), "")); }
        var zip= d.path().resolve(s+".zip");
        return Files.isRegularFile(zip, NOFOLLOW_LINKS) ? Optional.of(new ZipDir(zip, List.of(), "")) : Optional.empty();
      }
      case ZipDir z -> {
        var items= items(z.zip(), z.steps(), "");
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
        for (var k : items(z.zip(), z.steps(), "").keySet()){
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
  private static LinkedHashMap<String,Item> items(Path zip, List<String> steps, String wanted) throws IOException{
    var key= List.<Object>of(zip, steps, wanted);
    var known= zips.get(key);
    if (known != null){ return known; }
    var res= steps.isEmpty()
      ? items(Files.newInputStream(zip), wanted::equals)
      : items(new ByteArrayInputStream(item(items(zip, steps.subList(0, steps.size()-1), steps.getLast()), steps.getLast()).bytes()), wanted::equals);
    zips.put(key, res);
    return res;
  }
  private static LinkedHashMap<String,Item> items(InputStream zip, Predicate<String> keep) throws IOException{
    var res= new LinkedHashMap<String,Item>();
    try (var b= new BufferedInputStream(zip)){
      b.mark(4);
      var emptyZip= Arrays.equals(b.readNBytes(4), new byte[]{'P', 'K', 5, 6});
      b.reset();
      try (var in= new ZipInputStream(b, UTF_8)){
        var first= in.getNextEntry();
        if (first == null && !emptyZip){ throw new ZipException("not a zip"); }
        for (var e= first; e != null; e = in.getNextEntry()){
          if (!e.isDirectory()){ res.put(e.getName(), new Item(e.getTime(), keep.test(e.getName()) ? in.readAllBytes() : null)); }
        }
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
    if (bytes == null){ remove(zip, root); return; }
    var tmp= tmp(root);
    Files.write(tmp, bytes, CREATE_NEW);
    Files.move(tmp, zip, ATOMIC_MOVE);
  }
  private static byte[] edit(byte[] zip, List<String> steps, Consumer<LinkedHashMap<String,Item>> op) throws IOException{
    var items= items(new ByteArrayInputStream(zip), _ -> true);
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
  private static void remove(Path p, Path root) throws IOException{
    while (!p.getParent().equals(root) && only(p.getParent())){ p = p.getParent(); }
    var tmp= tmp(root);
    Files.move(p, tmp, ATOMIC_MOVE);
    deleteTree(tmp);
  }
  private static boolean only(Path dir) throws IOException{
    try (var s= Files.list(dir)){ return s.limit(2).count() == 1; }
  }
}
