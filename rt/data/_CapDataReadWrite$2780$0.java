package _base;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_16BE;
import static java.nio.charset.StandardCharsets.UTF_16LE;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.nio.charset.Charset;

import io.github.humbleui.skija.EncoderPNG;

public final class _CapDataReadWrite$2780$0 implements CapDataReadWrite$2780$0{
  private static final Charset utf32le= Charset.forName("UTF-32LE");
  private static final Charset utf32be= Charset.forName("UTF-32BE");
  private static Object str(Object name, Object path, Object content, Charset cs){ return DataFiles.writeBytes(name, path, AssetBytesRead.str(content).getBytes(cs)); }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$paths$2(Object name, Object path){ return DataFiles.paths(name, path); }
  @Override public Object mut$read$2(Object name, Object path){ return DataFiles.read(name, path); }
  @Override public Object mut$writeStr$3(Object name, Object path, Object content){ return str(name, path, content, UTF_8); }
  @Override public Object mut$writeStrUtf16Le$3(Object name, Object path, Object content){ return str(name, path, content, UTF_16LE); }
  @Override public Object mut$writeStrUtf16Be$3(Object name, Object path, Object content){ return str(name, path, content, UTF_16BE); }
  @Override public Object mut$writeStrUtf32Le$3(Object name, Object path, Object content){ return str(name, path, content, utf32le); }
  @Override public Object mut$writeStrUtf32Be$3(Object name, Object path, Object content){ return str(name, path, content, utf32be); }
  @Override public Object mut$writeUStrUtf8$3(Object name, Object path, Object content){ return DataFiles.writeText(name, path, content, UTF_8); }
  @Override public Object mut$writeUStrUtf16Le$3(Object name, Object path, Object content){ return DataFiles.writeText(name, path, content, UTF_16LE); }
  @Override public Object mut$writeUStrUtf16Be$3(Object name, Object path, Object content){ return DataFiles.writeText(name, path, content, UTF_16BE); }
  @Override public Object mut$writeUStrUtf32Le$3(Object name, Object path, Object content){ return DataFiles.writeText(name, path, content, utf32le); }
  @Override public Object mut$writeUStrUtf32Be$3(Object name, Object path, Object content){ return DataFiles.writeText(name, path, content, utf32be); }
  @Override public Object mut$writeUStrAscii$3(Object name, Object path, Object content){ return DataFiles.writeText(name, path, content, US_ASCII); }
  @Override public Object mut$writeUStrLatin1$3(Object name, Object path, Object content){ return DataFiles.writeText(name, path, content, ISO_8859_1); }
  @Override public Object mut$writeBytes$3(Object name, Object path, Object content){
    var bs= List$o$1Instance.asJava(content);
    var res= new byte[bs.size()];
    for (var i= 0; i < res.length; i += 1){ res[i] = Byte$o$0Instance.unwrap(bs.get(i)); }
    return DataFiles.writeBytes(name, path, res);
  }
  @Override public Object mut$writeImagePng$3(Object name, Object path, Object content){
    try (var png= EncoderPNG.encode(((Image$1c$0Instance)content).image())){ return DataFiles.writeBytes(name, path, png.getBytes()); }
  }
  @Override public Object mut$delete$2(Object name, Object path){ return DataFiles.delete(name, path); }
}
