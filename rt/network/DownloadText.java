package _base;

final class DownloadText{
  static Object str(String url,long maxBytes,Object recover,TextEncoding.Decoder decoder){
    return TextEncoding.str(DownloadFetch.bytes(url,maxBytes),recover,decoder);
  }
  static Object uStr(String url,long maxBytes,Object recover,TextEncoding.Decoder decoder){
    return TextEncoding.uStr(DownloadFetch.bytes(url,maxBytes),recover,decoder);
  }
}
