package _base;

public final class _CapDownload$2gw$0 implements CapDownload$2gw$0{
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }

  @Override public Object mut$downloadBytes$2(Object url, Object maxBytes){
    var bs= DownloadFetch.bytes(AssetBytesRead.str(url), AssetBytesRead.nat(maxBytes));
    var l= new java.util.ArrayList<Object>(bs.length);
    for (var b : bs){ l.add(Byte$o$0Instance.instance(b)); }
    return List$o$1Instance.wrap(l);
  }

  @Override public Object mut$downloadStr$3(Object url, Object maxBytes, Object cleaner){
    return TextCleaning.str(cleaner, DownloadFetch.bytes(AssetBytesRead.str(url), AssetBytesRead.nat(maxBytes)));
  }
  @Override public Object mut$downloadUStr$3(Object url, Object maxBytes, Object cleaner){
    return TextCleaning.uStr(cleaner, DownloadFetch.bytes(AssetBytesRead.str(url), AssetBytesRead.nat(maxBytes)));
  }
  @Override public Object mut$downloadImage$3(Object url, Object maxBytes, Object maxPixels){
    var u= AssetBytesRead.str(url);
    var bs= DownloadFetch.bytes(u, AssetBytesRead.nat(maxBytes));
    return new Image$1c$0Instance(AssetImageRead.decode("Resource with url "+u, bs, AssetBytesRead.nat(maxPixels)));
  }
}
