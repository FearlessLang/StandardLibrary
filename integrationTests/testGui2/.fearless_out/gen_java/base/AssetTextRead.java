package base;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipInputStream;
import static base.Util.*;

final class AssetTextRead{
  static final Set<Key> seen=ConcurrentHashMap.newKeySet();

  private AssetTextRead(){ throw new AssertionError(); }

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
    throw nonDetErr("Asset read consumed more than once.\nrequestId: "+requestId+"\nmethod: "+method+"\npath: "+path);
  }

  static byte[] bytes(String path,String diskPath,String zipSteps,String zipEntry){
    var root=System.getProperty("fearlessUser.dir");
    var full=Path.of(root).resolve(diskPath);
    try{
      if (zipEntry.isEmpty()){ return Files.readAllBytes(full); }
      var steps=zipSteps.isEmpty()?List.<String>of():List.of(zipSteps.split(";"));
      return new ReadZip(
        n->{ throw nonDetErr("Files under project root has been altered.\nBad zip entry name: "+n); },
        n->{ throw nonDetErr("Files under project root has been altered.\nDuplicate zip entry name: "+n); },
        n->{ throw nonDetErr("Files under project root has been altered.\nZip entry too large: "+n); }
        ).readEntry(
          ()->new ZipInputStream(Files.newInputStream(full),StandardCharsets.UTF_8),
          steps,
          zipEntry,
          StandardCharsets.UTF_8
          );
    }
    catch(IOException ioe){
      throw nonDetErr("Files under project root has been altered.\nFile not found in "+path+" "+ioe);
    }
  }

  static String str(Object o){ return ((Str$0Instance)o).val(); }

  static Object readStr(
    Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover,TextEncoding.Decoder decoder
    ){
    return TextEncoding.str(
      bytes(str(path),str(diskPath),str(zipSteps),str(zipEntry)),
      recover,
      decoder
      );
  }

  static Object readUStr(
    Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover,TextEncoding.Decoder decoder
    ){
    return TextEncoding.uStr(
      bytes(str(path),str(diskPath),str(zipSteps),str(zipEntry)),
      recover,
      decoder
      );
  }
}