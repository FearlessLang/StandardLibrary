package _base;

final class AssetTextRead{
  static void consumeOnce(String requestId, String method, String path, String diskPath, String zipSteps, String zipEntry){
    AssetBytesRead.consumeOnce(requestId, method, path, diskPath, zipSteps, zipEntry);
  }
  static String str(Object o){ return AssetBytesRead.str(o); }
  static byte[] bytes(Object path, Object diskPath, Object zipSteps, Object zipEntry){
    return AssetBytesRead.bytes(str(path), str(diskPath), str(zipSteps), str(zipEntry));
  }
}