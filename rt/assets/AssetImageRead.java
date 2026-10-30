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
    var bs= AssetBytesRead.bytes(path, diskPath, zipSteps, zipEntry);
    return new Image$1c$0Instance(decode("Resource with path "+path.substring("fear:".length()), bs, maxPixels));
  }
  static final String standards= "PNG (ISO/IEC 15948:2004), JPEG (ISO/IEC 10918-1:1994), WebP (RFC 9649), GIF (GIF89a), BMP (Windows bitmap up to BITMAPV5HEADER), ICO (Windows icon) and WBMP (WAP-237, type 0)";

  //Skia codecs replace ImageIO: same decoder, so same pixels, on every
  //machine and JDK. Eager decode: errors surface here, at load; draws later
  //are pure blits.
  static Image decode(String subject, byte[] bs, long maxPixels){
    try (var data= Data.makeFromBytes(bs); var codec= codec(subject, data); var bm= new Bitmap()){
      if (!standard(codec)){ throw undecodable(subject); }
      var origin= codec.getEncodedOrigin();
      int w= codec.getWidth();
      int h= codec.getHeight();
      int uprightW= origin.swapsWidthHeight() ? h : w;
      int uprightH= origin.swapsWidthHeight() ? w : h;
      checkSize(subject, uprightW, uprightH, maxPixels);
      var info= new ImageInfo(w, h, ColorType.BGRA_8888, ColorAlphaType.PREMUL, ColorSpace.getSRGB());
      bm.allocPixels(info);
      try{ codec.readPixels(bm); }
      catch (IllegalArgumentException _){ throw undecodable(subject); }
      var image= Image.makeRasterFromBitmap(bm.setImmutable());//shares pixels, no copy
      if (origin == EncodedOrigin.TOP_LEFT){ return image; }
      try (image; var surf= Surface.makeRaster(info.withWidthHeight(uprightW, uprightH))){
        surf.getCanvas().concat(origin.toMatrix(uprightW, uprightH)).drawImage(image, 0, 0);
        return surf.makeImageSnapshot();
      }
    }
  }
  static Codec codec(String subject, Data data){
    try{ return Codec.makeFromData(data); }
    catch (IllegalArgumentException _){ throw undecodable(subject); }
  }
  @SuppressWarnings("deprecation")
  static boolean standard(Codec codec){
    return switch (codec.getEncodedImageFormat()){
      case PNG, JPEG, WEBP, GIF, BMP, ICO, WBMP -> true;
      default -> false;
    };
  }
  static Error undecodable(String subject){ return detErr(subject+" can not be decoded as an image according to standards "+standards); }

  static void checkSize(String subject, int w, int h, long maxPixels){
    assert w > 0 && h > 0;
    var pixels= Math.multiplyExact((long)w, h);
    if (w > Scopes.maxExtent || h > Scopes.maxExtent || pixels > Scopes.maxImagePixels){ throw detErr(subject+" is an image of "+w+" x "+h+", which is too large; each side must be <= "+Scopes.maxExtent+" and the pixels <= "+Scopes.maxImagePixels); }
    if (Long.compareUnsigned(maxPixels, pixels) >= 0){ return; }
    throw detErr(subject+" is an image of "+w+" x "+h+" = "+pixels+" pixels, more than maxPixels "+Long.toUnsignedString(maxPixels));
  }
}
