package base;

final class AssetTextRead{
  private AssetTextRead(){ throw new AssertionError(); }

  static void consumeOnce(String requestId,String method,String path,String diskPath,String zipSteps,String zipEntry){
    AssetBytesRead.consumeOnce(requestId,method,path,diskPath,zipSteps,zipEntry);
  }
  static String str(Object o){ return AssetBytesRead.str(o); }

  static Object readStr(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover,TextEncoding.Decoder decoder){
    return TextEncoding.str(
      AssetBytesRead.bytes(str(path),str(diskPath),str(zipSteps),str(zipEntry)),
      recover,
      decoder
      );
  }
  static Object readUStr(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object recover,TextEncoding.Decoder decoder){
    return TextEncoding.uStr(
      AssetBytesRead.bytes(str(path),str(diskPath),str(zipSteps),str(zipEntry)),
      recover,
      decoder
      );
  }
}