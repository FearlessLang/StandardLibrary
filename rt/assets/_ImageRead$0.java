package base;

public interface _ImageRead$0{
  default Object imm$consumeImage$6(Object requestId,Object path,Object diskPath,Object zipSteps,Object zipEntry,Object maxPixels){
    AssetBytesRead.consumeOnce(
      AssetBytesRead.str(requestId),
      "Image",
      AssetBytesRead.str(path),
      AssetBytesRead.str(diskPath),
      AssetBytesRead.str(zipSteps),
      AssetBytesRead.str(zipEntry)
      );
    return AssetImageRead.readImage(path,diskPath,zipSteps,zipEntry,maxPixels);
  }
  _ImageRead$0 instance= new _ImageRead$0(){};
}