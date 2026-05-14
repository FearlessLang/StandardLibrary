package base;

public interface _TxtRead$0{
  default Object imm$readStrUtf8$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf8);
  }
  default Object imm$readUStrUtf8$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf8);
  }

  default Object imm$readStrUtf16Le$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf16le);
  }
  default Object imm$readUStrUtf16Le$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf16le);
  }

  default Object imm$readStrUtf16Be$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf16be);
  }
  default Object imm$readUStrUtf16Be$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf16be);
  }

  default Object imm$readStrUtf32Le$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf32le);
  }
  default Object imm$readUStrUtf32Le$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf32le);
  }

  default Object imm$readStrUtf32Be$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf32be);
  }
  default Object imm$readUStrUtf32Be$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::utf32be);
  }

  default Object imm$readStrAscii$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::ascii);
  }
  default Object imm$readUStrAscii$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::ascii);
  }

  default Object imm$readStrLatin1$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::latin1);
  }
  default Object imm$readUStrLatin1$6(Object cap,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    return AssetTextRead.readUStr(path,diskPath,zipSteps,zipEntry,recover,TextEncoding::latin1);
  }

  default Object imm$consumeStrUtf8$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrUtf8",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readStrUtf8$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }
  default Object imm$consumeUStrUtf8$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrUtf8",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readUStrUtf8$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }

  default Object imm$consumeStrUtf16Le$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrUtf16Le",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readStrUtf16Le$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }
  default Object imm$consumeUStrUtf16Le$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrUtf16Le",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readUStrUtf16Le$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }

  default Object imm$consumeStrUtf16Be$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrUtf16Be",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readStrUtf16Be$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }
  default Object imm$consumeUStrUtf16Be$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrUtf16Be",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readUStrUtf16Be$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }

  default Object imm$consumeStrUtf32Le$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrUtf32Le",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readStrUtf32Le$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }
  default Object imm$consumeUStrUtf32Le$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrUtf32Le",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readUStrUtf32Le$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }

  default Object imm$consumeStrUtf32Be$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrUtf32Be",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readStrUtf32Be$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }
  default Object imm$consumeUStrUtf32Be$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrUtf32Be",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readUStrUtf32Be$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }

  default Object imm$consumeStrAscii$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrAscii",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readStrAscii$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }
  default Object imm$consumeUStrAscii$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrAscii",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readUStrAscii$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }

  default Object imm$consumeStrLatin1$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"StrLatin1",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readStrLatin1$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }
  default Object imm$consumeUStrLatin1$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId),"UStrLatin1",AssetTextRead.str(path),AssetTextRead.str(diskPath),AssetTextRead.str(zipSteps),AssetTextRead.str(zipEntry));
    return imm$readUStrLatin1$6(null,path,diskPath,zipSteps,zipEntry,recover);
  }

  _TxtRead$0 instance=new _TxtRead$0(){};
}

/*
public interface _TxtRead$0{
  static final HashMap<String,String> cache= new HashMap<>();

  default String read(String path, String diskPath, String zipSteps, String zipEntry){
    var root= System.getProperty("fearlessUser.dir");
    var full= Path.of(root).resolve(diskPath);
    try{
      if (zipEntry.isEmpty()){ return Files.readString(full, StandardCharsets.UTF_8); }
      var steps= zipSteps.isEmpty() ? List.<String>of() : List.of(zipSteps.split(";"));
      var bytes= new ReadZip(
        n->{throw nonDetErr("Files under project root has been altered.\nBad zip entry name: "+n);},
        n->{throw nonDetErr("Files under project root has been altered.\nDuplicate zip entry name: "+n);},
        n->{throw nonDetErr("Files under project root has been altered.\nZip entry too large: "+n);}
        ).readEntry(
        ()->new ZipInputStream(Files.newInputStream(full), StandardCharsets.UTF_8),
        steps,
        zipEntry,
        StandardCharsets.UTF_8
        );
      return new String(bytes, StandardCharsets.UTF_8);
    }
    catch(IOException ioe){
      throw nonDetErr("Files under project root has been altered.\nFile not found in "+path+" "+ioe);
    }
  }
  default Object imm$cacheStrUtf8$4(Object p0, Object p1, Object p2, Object p3){
    String path= ((Str$0Instance)p0).val();
    String diskPath= ((Str$0Instance)p1).val();
    String zipSteps= ((Str$0Instance)p2).val();
    String zipEntry= ((Str$0Instance)p3).val();
    String key= path+"\n"+diskPath+"\n"+zipSteps+"\n"+zipEntry;
    String content= cache.computeIfAbsent(key, _->read(path, diskPath, zipSteps, zipEntry));
    return new Str$0Instance(content);
  }
  _TxtRead$0 instance= new _TxtRead$0(){};
}*/