package _base;

public final class _CapDownload$2gw$0 implements CapDownload$2gw$0{
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }

  @Override public Object mut$downloadBytes$3(Object url, Object maxBytes, Object follower){
    var bs= DownloadFetch.bytes(url, AssetBytesRead.nat(maxBytes), follower);
    var l= new java.util.ArrayList<Object>(bs.length);
    for (var b : bs){ l.add(Byte$o$0Instance.instance(b)); }
    return List$o$1Instance.wrap(l);
  }

  @Override public Object mut$downloadStr$4(Object url, Object maxBytes, Object cleaner, Object follower){
    return TextCleaning.run(false, cleaner, DownloadFetch.bytes(url, AssetBytesRead.nat(maxBytes), follower));
  }
  @Override public Object mut$downloadUStr$4(Object url, Object maxBytes, Object cleaner, Object follower){
    return TextCleaning.run(true, cleaner, DownloadFetch.bytes(url, AssetBytesRead.nat(maxBytes), follower));
  }
  @Override public Object mut$downloadImage$4(Object url, Object maxBytes, Object maxPixels, Object follower){
    var bs= DownloadFetch.bytes(url, AssetBytesRead.nat(maxBytes), follower);
    return new Image$1c$0Instance(AssetImageRead.decode("Resource with url "+DownloadFetch.textOf(url), bs, AssetBytesRead.nat(maxPixels)));
  }
}
