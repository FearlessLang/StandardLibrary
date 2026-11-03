package _base;

public interface _TxtRead$5k$0{
  default Object imm$consumeStr$6(Object requestId, Object path, Object diskPath, Object zipSteps, Object zipEntry, Object cleaner){
    AssetBytesRead.consumeOnce(AssetBytesRead.str(requestId), "Str", AssetBytesRead.str(path), AssetBytesRead.str(diskPath), AssetBytesRead.str(zipSteps), AssetBytesRead.str(zipEntry));
    return TextCleaning.run(false, cleaner, AssetBytesRead.bytes(AssetBytesRead.str(path), AssetBytesRead.str(diskPath), AssetBytesRead.str(zipSteps), AssetBytesRead.str(zipEntry)));
  }
  default Object imm$consumeUStr$6(Object requestId, Object path, Object diskPath, Object zipSteps, Object zipEntry, Object cleaner){
    AssetBytesRead.consumeOnce(AssetBytesRead.str(requestId), "UStr", AssetBytesRead.str(path), AssetBytesRead.str(diskPath), AssetBytesRead.str(zipSteps), AssetBytesRead.str(zipEntry));
    return TextCleaning.run(true, cleaner, AssetBytesRead.bytes(AssetBytesRead.str(path), AssetBytesRead.str(diskPath), AssetBytesRead.str(zipSteps), AssetBytesRead.str(zipEntry)));
  }
  _TxtRead$5k$0 instance= new _TxtRead$5k$0(){};
}