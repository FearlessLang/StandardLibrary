package base;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.zip.ZipInputStream;
import java.nio.charset.Charset;

public record ReadZip(
    Function<String,RuntimeException> badNameErr,
    Function<String,RuntimeException> dupNameErr,
    Function<String,RuntimeException> tooLargeName){
  public interface IoSupplier{ ZipInputStream get() throws IOException; }
  public Map<String,byte[]> readAll(IoSupplier szin){
    return cleanUp(Fs.of(()->{try(var zin=szin.get()){ return _readAll(zin); }}));
  }
  private LinkedHashMap<String,byte[]> _readAll(ZipInputStream zin){
    var out= new LinkedHashMap<String,byte[]>();
    while (true){
      var e= Fs.of(zin::getNextEntry);
      if (e == null){ return out; }
      try {
        var n= e.getName();
        reqZipNameOk(n);
        var key= keyOf(n);
        if(out.containsKey(key)){ throw dupNameErr.apply(key); }
        if(n.endsWith("/")){ out.put(key, null); }
        else{ out.put(key, readEntryBytes(key,zin)); }
      }
      finally{ Fs.ofV(zin::closeEntry); }
    }
  }
  private static String keyOf(String n){ return n.endsWith("/") ? n.substring(0, n.length()-1) : n; }
  private void reqZipNameOk(String n){
    var bad= n.isEmpty() || n.startsWith("/") || n.indexOf('\0') >= 0;
    if(bad){ throw badNameErr.apply(n); }
    var sub= keyOf(n);
    for(var seg: sub.split("/", -1)){
      if(seg.isEmpty() || seg.equals(".") || seg.equals("..")){ throw badNameErr.apply(n); }
    }
  }
  private byte[] readEntryBytes(String name, ZipInputStream zin){
    try{ return Fs.of(zin::readAllBytes); }
    catch(OutOfMemoryError oom){ throw tooLargeName.apply(name); }
  }
  private Map<String,byte[]> cleanUp(LinkedHashMap<String,byte[]> map){
    map.values().removeIf(v -> v == null);
    return Collections.unmodifiableMap(map);//to keep the order instead of Map.copyOf undocumented behaviour exactly here
  }
  public byte[] readEntry(IoSupplier szin, List<String> steps, String entryName, Charset encoding){
    byte[] bytes= null;
    for (var step: steps){
      bytes= readAll(supplier(szin, bytes, encoding)).get(step);
      if (bytes == null){ return null; }
    }
    return readAll(supplier(szin, bytes, encoding)).get(entryName);
  }
  private static IoSupplier supplier(IoSupplier szin, byte[] bytes, Charset encoding){
    return bytes == null
      ? szin
      : ()->new ZipInputStream(new ByteArrayInputStream(bytes), encoding);
  }
}