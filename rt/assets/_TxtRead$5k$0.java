package _base;

public interface _TxtRead$5k$0{
  default Object imm$consumeStr$6(Object requestId, Object path, Object diskPath, Object zipSteps, Object zipEntry, Object cleaner){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId), "Str", AssetTextRead.str(path), AssetTextRead.str(diskPath), AssetTextRead.str(zipSteps), AssetTextRead.str(zipEntry));
    return TextCleaning.str(cleaner, AssetTextRead.bytes(path, diskPath, zipSteps, zipEntry));
  }
  default Object imm$consumeUStr$6(Object requestId, Object path, Object diskPath, Object zipSteps, Object zipEntry, Object cleaner){
    AssetTextRead.consumeOnce(AssetTextRead.str(requestId), "UStr", AssetTextRead.str(path), AssetTextRead.str(diskPath), AssetTextRead.str(zipSteps), AssetTextRead.str(zipEntry));
    return TextCleaning.uStr(cleaner, AssetTextRead.bytes(path, diskPath, zipSteps, zipEntry));
  }
  _TxtRead$5k$0 instance= new _TxtRead$5k$0(){};
}