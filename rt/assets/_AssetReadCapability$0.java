package base;

public final class _AssetReadCapability$0 implements AssetReadCapability$0{
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$readStrUtf8$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf8);
  }
  @Override public Object mut$readUStrUtf8$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf8);
  }
  @Override public Object mut$readStrUtf16Le$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf16le);
  }
  @Override public Object mut$readUStrUtf16Le$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf16le);
  }
  @Override public Object mut$readStrUtf16Be$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf16be);
  }
  @Override public Object mut$readUStrUtf16Be$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf16be);
  }
  @Override public Object mut$readStrUtf32Le$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf32le);
  }
  @Override public Object mut$readUStrUtf32Le$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf32le);
  }
  @Override public Object mut$readStrUtf32Be$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf32be);
  }
  @Override public Object mut$readUStrUtf32Be$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf32be);
  }
  @Override public Object mut$readStrAscii$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::ascii);
  }
  @Override public Object mut$readUStrAscii$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::ascii);
  }
  @Override public Object mut$readStrLatin1$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::latin1);
  }
  @Override public Object mut$readUStrLatin1$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::latin1);
  }
  @Override public Object mut$readImage$5(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object maxPixels){
    return AssetImageRead.readImage(path,diskPath,zipSteps,zipEntry,maxPixels);
  }
}
