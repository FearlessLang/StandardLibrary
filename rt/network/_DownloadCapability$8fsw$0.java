package base;

public final class _DownloadCapability$8fsw$0 implements DownloadCapability$8fsw$0{
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }

  @Override public Object mut$downloadBytes$2(Object url,Object maxBytes){
    var bs= DownloadFetch.bytes(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes));
    var l= new java.util.ArrayList<Object>(bs.length);
    for (var b: bs){ l.add(Byte$o$0Instance.instance(b)); }
    return List$o$1Instance.wrap(l);
  }

  @Override public Object mut$downloadStrUtf8$3(Object url,Object maxBytes,Object recover){
    return DownloadText.str(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::utf8);
  }
  @Override public Object mut$downloadUStrUtf8$3(Object url,Object maxBytes,Object recover){
    return DownloadText.uStr(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::utf8);
  }
  @Override public Object mut$downloadStrUtf16Le$3(Object url,Object maxBytes,Object recover){
    return DownloadText.str(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::utf16le);
  }
  @Override public Object mut$downloadUStrUtf16Le$3(Object url,Object maxBytes,Object recover){
    return DownloadText.uStr(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::utf16le);
  }
  @Override public Object mut$downloadStrUtf16Be$3(Object url,Object maxBytes,Object recover){
    return DownloadText.str(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::utf16be);
  }
  @Override public Object mut$downloadUStrUtf16Be$3(Object url,Object maxBytes,Object recover){
    return DownloadText.uStr(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::utf16be);
  }
  @Override public Object mut$downloadStrUtf32Le$3(Object url,Object maxBytes,Object recover){
    return DownloadText.str(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::utf32le);
  }
  @Override public Object mut$downloadUStrUtf32Le$3(Object url,Object maxBytes,Object recover){
    return DownloadText.uStr(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::utf32le);
  }
  @Override public Object mut$downloadStrUtf32Be$3(Object url,Object maxBytes,Object recover){
    return DownloadText.str(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::utf32be);
  }
  @Override public Object mut$downloadUStrUtf32Be$3(Object url,Object maxBytes,Object recover){
    return DownloadText.uStr(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::utf32be);
  }
  @Override public Object mut$downloadStrAscii$3(Object url,Object maxBytes,Object recover){
    return DownloadText.str(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::ascii);
  }
  @Override public Object mut$downloadUStrAscii$3(Object url,Object maxBytes,Object recover){
    return DownloadText.uStr(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::ascii);
  }
  @Override public Object mut$downloadStrLatin1$3(Object url,Object maxBytes,Object recover){
    return DownloadText.str(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::latin1);
  }
  @Override public Object mut$downloadUStrLatin1$3(Object url,Object maxBytes,Object recover){
    return DownloadText.uStr(AssetBytesRead.str(url),AssetBytesRead.nat(maxBytes),recover,TextEncoding::latin1);
  }

  @Override public Object mut$downloadImage$3(Object url,Object maxBytes,Object maxPixels){
    var u= AssetBytesRead.str(url);
    var bs= DownloadFetch.bytes(u,AssetBytesRead.nat(maxBytes));
    return new Image$1c$0Instance(AssetImageRead.decode(u,bs,AssetBytesRead.nat(maxPixels)));
  }
}
