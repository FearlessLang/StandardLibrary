package _base;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_16BE;
import static java.nio.charset.StandardCharsets.UTF_16LE;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.nio.charset.Charset;

import io.github.humbleui.skija.EncoderPNG;

public final class _CapDataEdit$2h4$0 implements CapDataEdit$2h4$0{
  private static final Charset utf32le= Charset.forName("UTF-32LE");
  private static final Charset utf32be= Charset.forName("UTF-32BE");
  private final DataFiles.Root root;
  _CapDataEdit$2h4$0(Object data){ root= DataFiles.root(DataFiles.edits, "dataEdit project", data); }
  private Object str(Object path, Object content, Charset cs){ return DataFiles.writeBytes(root, path, AssetBytesRead.str(content).getBytes(cs)); }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$paths$1(Object path){ return DataFiles.paths(root, path); }
  @Override public Object mut$read$1(Object path){ return DataFiles.read(root, path); }
  @Override public Object mut$writeStrUtf8$2(Object path, Object content){ return str(path, content, UTF_8); }
  @Override public Object mut$writeStrAscii$2(Object path, Object content){ return str(path, content, US_ASCII); }
  @Override public Object mut$writeStrLatin1$2(Object path, Object content){ return str(path, content, ISO_8859_1); }
  @Override public Object mut$writeStrUtf16Le$2(Object path, Object content){ return str(path, content, UTF_16LE); }
  @Override public Object mut$writeStrUtf16Be$2(Object path, Object content){ return str(path, content, UTF_16BE); }
  @Override public Object mut$writeStrUtf32Le$2(Object path, Object content){ return str(path, content, utf32le); }
  @Override public Object mut$writeStrUtf32Be$2(Object path, Object content){ return str(path, content, utf32be); }
  @Override public Object mut$writeUStrUtf8$2(Object path, Object content){ return DataFiles.writeText(root, path, content, UTF_8); }
  @Override public Object mut$writeUStrUtf16Le$2(Object path, Object content){ return DataFiles.writeText(root, path, content, UTF_16LE); }
  @Override public Object mut$writeUStrUtf16Be$2(Object path, Object content){ return DataFiles.writeText(root, path, content, UTF_16BE); }
  @Override public Object mut$writeUStrUtf32Le$2(Object path, Object content){ return DataFiles.writeText(root, path, content, utf32le); }
  @Override public Object mut$writeUStrUtf32Be$2(Object path, Object content){ return DataFiles.writeText(root, path, content, utf32be); }
  @Override public Object mut$writeUStrAscii$3(Object path, Object content, Object recover){ return DataFiles.writeLossy(root, path, content, recover, 0x7F); }
  @Override public Object mut$writeUStrLatin1$3(Object path, Object content, Object recover){ return DataFiles.writeLossy(root, path, content, recover, 0xFF); }
  @Override public Object mut$writeBytes$2(Object path, Object content){
    var bs= List$o$1Instance.asJava(content);
    var res= new byte[bs.size()];
    for (var i= 0; i < res.length; i += 1){ res[i] = Byte$o$0Instance.unwrap(bs.get(i)); }
    return DataFiles.writeBytes(root, path, res);
  }
  @Override public Object mut$writeImagePng$2(Object path, Object content){
    try (var png= EncoderPNG.encode(((Image$1c$0Instance)content).image())){ return DataFiles.writeBytes(root, path, png.getBytes()); }
  }
  @Override public Object mut$delete$1(Object path){ return DataFiles.delete(root, path); }
}
