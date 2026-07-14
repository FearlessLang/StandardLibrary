package base;

import io.github.humbleui.skija.Bitmap;
import io.github.humbleui.skija.Codec;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.Data;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;

import static base.Util.*;

final class AssetImageRead{
  private AssetImageRead(){ throw new AssertionError(); }

  static Object readImage(Object path,Object diskPath,Object zipSteps,Object zipEntry,Object maxPixels){
    return readImage(
      AssetBytesRead.str(path),
      AssetBytesRead.str(diskPath),
      AssetBytesRead.str(zipSteps),
      AssetBytesRead.str(zipEntry),
      AssetBytesRead.nat(maxPixels)
      );
  }
  static Object readImage(String path,String diskPath,String zipSteps,String zipEntry,long maxPixels){
    return new Image$0Instance(
      decode(path,AssetBytesRead.bytes(path,diskPath,zipSteps,zipEntry),maxPixels));
  }

  //Skia codecs replace ImageIO: same decoder, so same pixels, on every
  //machine and JDK. Eager decode: errors surface here, at load; draws later
  //are pure blits.
  static Image decode(String path,byte[] bs,long maxPixels){
    try(var data=Data.makeFromBytes(bs); var codec=Codec.makeFromData(data); var bm=new Bitmap()){
      checkSize(path,codec.getWidth(),codec.getHeight(),maxPixels);
      bm.allocPixels(new ImageInfo(codec.getWidth(),codec.getHeight(),ColorType.BGRA_8888,ColorAlphaType.PREMUL));
      codec.readPixels(bm);
      return Image.makeRasterFromBitmap(bm.setImmutable());//shares pixels, no copy
    }
    catch(RuntimeException re){
      if (re.getClass().getName().startsWith("base.")){ throw re; }
      throw badImage(path,"image could not be decoded: "+re);
    }
  }

  static void checkSize(String path,int w,int h,long maxPixels){
    if (w <= 0 || h <= 0){ throw badImage(path,"image has invalid size: "+w+"x"+h); }
    var pixels=Math.multiplyExact((long)w,h);
    if (Long.compareUnsigned(maxPixels,pixels) >= 0){ return; }
    throw badImage(
      path,
      "image exceeds maxPixels.\n"
      +"width: "+w+"\nheight: "+h
      +"\npixels: "+pixels
      +"\nmaxPixels: "+Long.toUnsignedString(maxPixels)
      );
  }
  static RuntimeException badImage(String path,String msg){
    throw nonDetErr("Image asset could not be read.\npath: "+path+"\n"+msg);
  }
}