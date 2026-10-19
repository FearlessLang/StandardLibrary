package _base;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static _base.Util.*;

record InputFile(String what, String label, Path path, List<String> zipSteps, String zipEntry) implements InputCursorNode$12bs$0{
  InputFile(Path path){ this("input file", portable(path.getFileName().toString()), path, List.of(), ""); }
  private static String portable(String name){
    return name.codePoints()
      .map(c -> UStr$s$0Instance.isStrScalar(c) ? c : '?')
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
  }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$label$0(){ return Str$c$0Instance.instance(label); }
  byte[] bytes(){
    try{ return DataFiles.bytes(path, zipSteps, zipEntry); }
    catch (IOException e){ throw nonDetErr("The "+what+" \""+label+"\" could not be read: "+e.getClass().getSimpleName()); }
  }
  Object str(Object recover, TextEncoding.Decoder decoder){ return TextEncoding.str(bytes(), recover, decoder); }
  Object uStr(Object recover, TextEncoding.Decoder decoder){ return TextEncoding.uStr(bytes(), recover, decoder); }
  @Override public Object mut$readStrUtf8$1(Object recover){ return str(recover, TextEncoding::utf8); }
  @Override public Object mut$readUStrUtf8$1(Object recover){ return uStr(recover, TextEncoding::utf8); }
  @Override public Object mut$readStrUtf16Le$1(Object recover){ return str(recover, TextEncoding::utf16le); }
  @Override public Object mut$readUStrUtf16Le$1(Object recover){ return uStr(recover, TextEncoding::utf16le); }
  @Override public Object mut$readStrUtf32Le$1(Object recover){ return str(recover, TextEncoding::utf32le); }
  @Override public Object mut$readUStrUtf32Le$1(Object recover){ return uStr(recover, TextEncoding::utf32le); }
  @Override public Object mut$readStrUtf16Be$1(Object recover){ return str(recover, TextEncoding::utf16be); }
  @Override public Object mut$readUStrUtf16Be$1(Object recover){ return uStr(recover, TextEncoding::utf16be); }
  @Override public Object mut$readStrUtf32Be$1(Object recover){ return str(recover, TextEncoding::utf32be); }
  @Override public Object mut$readUStrUtf32Be$1(Object recover){ return uStr(recover, TextEncoding::utf32be); }
  @Override public Object mut$readStrAscii$1(Object recover){ return str(recover, TextEncoding::ascii); }
  @Override public Object mut$readUStrAscii$1(Object recover){ return uStr(recover, TextEncoding::ascii); }
  @Override public Object mut$readStrLatin1$1(Object recover){ return str(recover, TextEncoding::latin1); }
  @Override public Object mut$readUStrLatin1$1(Object recover){ return uStr(recover, TextEncoding::latin1); }
  @Override public Object mut$readBytes$0(){
    var bs= bytes();
    var l= new java.util.ArrayList<Object>(bs.length);
    for (var b : bs){ l.add(Byte$o$0Instance.instance(b)); }
    return List$o$1Instance.wrap(l);
  }
  @Override public Object mut$readImage$1(Object maxPixels){
    return new Image$1c$0Instance(AssetImageRead.decode(label, bytes(), AssetBytesRead.nat(maxPixels)));
  }
}
