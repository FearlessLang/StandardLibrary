package _base;

public final class _CapAssetRead$4y0$0 implements CapAssetRead$4y0$0{
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$readStr$5(Object path, Object diskPath, Object zipSteps, Object zipEntry, Object cleaner){
    return TextCleaning.str(cleaner, AssetTextRead.bytes(path, diskPath, zipSteps, zipEntry));
  }
  @Override public Object mut$readUStr$5(Object path, Object diskPath, Object zipSteps, Object zipEntry, Object cleaner){
    return TextCleaning.uStr(cleaner, AssetTextRead.bytes(path, diskPath, zipSteps, zipEntry));
  }
  @Override public Object mut$readImage$5(Object path, Object diskPath, Object zipSteps, Object zipEntry, Object maxPixels){
    return AssetImageRead.readImage(path, diskPath, zipSteps, zipEntry, maxPixels);
  }
}
