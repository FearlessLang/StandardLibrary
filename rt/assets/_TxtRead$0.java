package base;

public interface _TxtRead$0{
  default Object imm$consumeStrUtf8$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrUtf8",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf8);
  }
  default Object imm$consumeUStrUtf8$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrUtf8",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf8);
  }
  default Object imm$consumeStrUtf16Le$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrUtf16Le",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf16le);
  }
  default Object imm$consumeUStrUtf16Le$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrUtf16Le",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf16le);
  }
  default Object imm$consumeStrUtf16Be$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrUtf16Be",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf16be);
  }
  default Object imm$consumeUStrUtf16Be$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrUtf16Be",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf16be);
  }
  default Object imm$consumeStrUtf32Le$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrUtf32Le",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf32le);
  }
  default Object imm$consumeUStrUtf32Le$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrUtf32Le",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf32le);
  }
  default Object imm$consumeStrUtf32Be$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrUtf32Be",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf32be);
  }
  default Object imm$consumeUStrUtf32Be$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrUtf32Be",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf32be);
  }
  default Object imm$consumeStrAscii$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrAscii",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::ascii);
  }
  default Object imm$consumeUStrAscii$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrAscii",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::ascii);
  }
  default Object imm$consumeStrLatin1$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrLatin1",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::latin1);
  }
  default Object imm$consumeUStrLatin1$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrLatin1",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::latin1);
  }
  _TxtRead$0 instance= new _TxtRead$0(){};
}