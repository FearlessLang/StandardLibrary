package _base;

import io.github.humbleui.skija.Bitmap;
import io.github.humbleui.skija.Codec;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.Data;
import io.github.humbleui.skija.EncodedOrigin;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.Surface;

import java.util.function.Function;

import static _base.Util.*;

final class AssetImageRead{
  static Object readImage(Object path, Object diskPath, Object zipSteps, Object zipEntry, Object maxPixels){
    return readImage(
      AssetBytesRead.str(path),
      AssetBytesRead.str(diskPath),
      AssetBytesRead.str(zipSteps),
      AssetBytesRead.str(zipEntry),
      AssetBytesRead.nat(maxPixels)
      );
  }
  static Object readImage(String path, String diskPath, String zipSteps, String zipEntry, long maxPixels){
    return new Image$1c$0Instance(
      decode(path, AssetBytesRead.bytes(path, diskPath, zipSteps, zipEntry), maxPixels, Util::nonDetErr));
  }

  //Skia codecs replace ImageIO: same decoder, so same pixels, on every
  //machine and JDK. Eager decode: errors surface here, at load; draws later
  //are pure blits.
  static Image decode(String path, byte[] bs, long maxPixels, Function<String,Error> err){
    try (var data= Data.makeFromBytes(bs); var codec= codec(path, data, err); var bm= new Bitmap()){
      var origin= codec.getEncodedOrigin();
      int w= codec.getWidth();
      int h= codec.getHeight();
      int uprightW= origin.swapsWidthHeight() ? h : w;
      int uprightH= origin.swapsWidthHeight() ? w : h;
      checkSize(path, uprightW, uprightH, maxPixels, err);
      var info= new ImageInfo(w, h, ColorType.BGRA_8888, ColorAlphaType.PREMUL, ColorSpace.getSRGB());
      bm.allocPixels(info);
      try{ codec.readPixels(bm); }
      catch (IllegalArgumentException _){ throw badImage(path, "the image data is damaged, or ends before the image does", err); }
      var image= Image.makeRasterFromBitmap(bm.setImmutable());//shares pixels, no copy
      if (origin == EncodedOrigin.TOP_LEFT){ return image; }
      try (image; var surf= Surface.makeRaster(info.withWidthHeight(uprightW, uprightH))){
        surf.getCanvas().concat(origin.toMatrix(uprightW, uprightH)).drawImage(image, 0, 0);
        return surf.makeImageSnapshot();
      }
    }
  }
  static Codec codec(String path, Data data, Function<String,Error> err){
    try{ return Codec.makeFromData(data); }
    catch (IllegalArgumentException _){ throw badImage(path, "the data is not an image in a format that can be read", err); }
  }

  static void checkSize(String path, int w, int h, long maxPixels, Function<String,Error> err){
    if (w <= 0 || h <= 0){ throw badImage(path, "image has invalid size: "+w+"x"+h, err); }
    var pixels= Math.multiplyExact((long)w, h);
    if (w > Scopes.maxExtent || h > Scopes.maxExtent || pixels > Scopes.maxImagePixels){ throw badImage(path, "image "+w+" x "+h+" is too large; each side must be <= "+Scopes.maxExtent+" and the pixels <= "+Scopes.maxImagePixels, err); }
    if (Long.compareUnsigned(maxPixels, pixels) >= 0){ return; }
    throw badImage(
      path,
      "image exceeds maxPixels.\n"
      +"width: "+w+"\nheight: "+h
      +"\npixels: "+pixels
      +"\nmaxPixels: "+Long.toUnsignedString(maxPixels),
      err
      );
  }
  static RuntimeException badImage(String path, String msg, Function<String,Error> err){
    throw err.apply("Image asset could not be read.\npath: "+path+"\n"+msg);
  }
}