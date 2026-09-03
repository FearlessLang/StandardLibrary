package base;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipInputStream;

import static base.Util.*;

final class AssetBytesRead{
  static final Set<Key> seen=ConcurrentHashMap.newKeySet();

  private AssetBytesRead(){ throw new AssertionError(); }

  record Key(
    String requestId,
    String method,
    String path,
    String diskPath,
    String zipSteps,
    String zipEntry
    ){}

  static void consumeOnce(
    String requestId,String method,String path,String diskPath,String zipSteps,String zipEntry
    ){
    if (seen.add(new Key(requestId,method,path,diskPath,zipSteps,zipEntry))){ return; }
    throw nonDetErr(
      "Asset read consumed more than once.\n"
      +"requestId: "+requestId+"\n"
      +"method: "+method+"\n"
      +"path: "+path
      );
  }

  static String str(Object o){ return ((Str$c$0Instance)o).val(); }

  static long nat(Object o){ return ((Nat$c$0Instance)o).val(); }

  static byte[] bytes(String path,String diskPath,String zipSteps,String zipEntry){
    checkAutoloaded(path,diskPath,zipSteps,zipEntry);
    var full= localAssetPath(diskPath);
    var steps= zipStepList(zipSteps);
    var entry= canonicalZipEntry(zipEntry);
    if (entry.isEmpty() && !steps.isEmpty()){ throw invalidAssetDescriptor("zipSteps without zipEntry: "+zipSteps); }
    try{
      if (entry.isEmpty()){ return Files.readAllBytes(full); }
      return new ReadZip(
        n->{ throw nonDetErr("Files under project root has been altered.\nBad zip entry name: "+n); },
        n->{ throw nonDetErr("Files under project root has been altered.\nDuplicate zip entry name: "+n); },
        n->{ throw nonDetErr("Files under project root has been altered.\nZip entry too large: "+n); }
        ).readEntry(
          ()->new ZipInputStream(Files.newInputStream(full),StandardCharsets.UTF_8),
          steps,
          entry,
          StandardCharsets.UTF_8
          );
    }
    catch(IOException ioe){
      throw nonDetErr("Files under project root has been altered.\nFile not found in "+path+" "+ioe);
    }
  }

  static final ConcurrentHashMap<String,List<List<String>>> autoloadCache= new ConcurrentHashMap<>();

  /** this.path/diskPath/zipSteps/zipEntry are plain Str fields any Fearless type can override
   * with an arbitrary literal, so they cannot be trusted on their own: this checks the exact
   * triple against the compiler's own compiled-in record of which files it auto-imported for
   * the package path claims to come from (base.<pkg>.Main.autoloadedAssets), rejecting anything
   * that is not literally one of those files. */
  static void checkAutoloaded(String path,String diskPath,String zipSteps,String zipEntry){
    var javaPkg= autoloadJavaPackage(path);
    var triple= List.of(diskPath,zipSteps,zipEntry);
    if (autoloadCache.computeIfAbsent(javaPkg,AssetBytesRead::loadAutoloaded).contains(triple)){ return; }
    throw invalidAssetDescriptor(
      "This file was not recognized by the compiler as auto-imported.\n"
      +"package: "+javaPkg+"\ndiskPath: "+diskPath
      );
  }

  static String autoloadJavaPackage(String path){
    var prefix= "fear:/";
    if (!path.startsWith(prefix)){ throw invalidAssetDescriptor("path does not start with "+prefix+": "+path); }
    var rest= path.substring(prefix.length());
    var slash= rest.indexOf('/');
    var folder= slash < 0 ? rest : rest.substring(0,slash);
    if (folder.length() < 2 || folder.charAt(0) != '_'){
      throw invalidAssetDescriptor("path has no valid package folder: "+path);
    }
    return folder.substring(1);
  }

  static List<List<String>> loadAutoloaded(String javaPkg){
    try{
      var cls= Class.forName(javaPkg+".Main",true,AssetBytesRead.class.getClassLoader());
      var field= cls.getField("autoloadedAssets");
      var rows= (String[][])field.get(null);
      var res= new ArrayList<List<String>>();
      for (var row: rows){ res.add(List.of(row)); }
      return List.copyOf(res);
    }
    catch(ReflectiveOperationException|ClassCastException e){
      throw invalidAssetDescriptor("Cannot resolve auto-imported assets for package \""+javaPkg+"\": "+e);
    }
  }

  static Path localAssetPath(String diskPath){
    var root= Path.of(System.getProperty("fearlessUser.dir")).toAbsolutePath().normalize();
    var segments= portableSegments("diskPath",diskPath);
    var full= root;
    for (var s: segments){ full = full.resolve(s); }
    full = full.normalize();
    if (!full.startsWith(root)){ throw invalidAssetDescriptor("diskPath escapes root: "+diskPath); }
    rejectSymlinks(full,root,segments,diskPath);
    return full;
  }

  static void rejectSymlinks(Path full,Path root,List<String> segments,String original){
    var p= root;
    for (var s: segments){
      p = p.resolve(s);
      if (Files.isSymbolicLink(p)){ throw invalidAssetDescriptor("asset path contains symbolic link: "+original); }
    }
    assert full.startsWith(root);
  }

  static List<String> zipStepList(String zipSteps){
    if (zipSteps.isEmpty()){ return List.of(); }
    var res= new ArrayList<String>();
    for (var step: zipSteps.split(";", -1)){ res.add(canonicalSlashPath("zipSteps",step)); }
    return res;
  }

  static String canonicalZipEntry(String zipEntry){
    if (zipEntry.isEmpty()){ return ""; }
    return canonicalSlashPath("zipEntry",zipEntry);
  }

  static String canonicalSlashPath(String field,String path){
    return String.join("/", portableSegments(field,path));
  }

  static List<String> portableSegments(String field,String path){
    if (path.isEmpty()){ throw invalidAssetDescriptor(field+" is empty"); }
    var res= new ArrayList<String>();
    for (var s: path.split("/", -1)){
      checkPortableSegment(field,s,path);
      res.add(s);
    }
    return res;
  }

  static void checkPortableSegment(String field,String s,String path){
    if (
      s.isEmpty()
      || s.startsWith(".")
      || s.indexOf('\\') >= 0
      || s.indexOf(';') >= 0
      ){ throw invalidAssetDescriptor("bad "+field+" segment in "+path+": "+s); }
  }
  static RuntimeException invalidAssetDescriptor(String msg){
    throw nonDetErr("Invalid asset descriptor.\n"+msg);
  }
}