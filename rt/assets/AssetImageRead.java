package base;

import java.awt.AlphaComposite;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;

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
      decode(
        path,
        AssetBytesRead.bytes(path,diskPath,zipSteps,zipEntry),
        maxPixels
        )
      );
  }

  static BufferedImage decode(String path,byte[] bs,long maxPixels){
    try(var in=new MemoryCacheImageInputStream(new ByteArrayInputStream(bs))){
      var readers=ImageIO.getImageReaders(in);
      if (!readers.hasNext()){ throw badImage(path,"unsupported image format"); }
      var reader=readers.next();
      try{
        reader.setInput(in,true,true);
        var w=reader.getWidth(0);
        var h=reader.getHeight(0);
        checkSize(path,w,h,maxPixels);
        var raw=reader.read(0);
        if (raw == null){ throw badImage(path,"image reader returned no image"); }
        checkSize(path,raw.getWidth(),raw.getHeight(),maxPixels);
        return normalize(raw);
      }
      finally{ reader.dispose(); }
    }
    catch(IOException ioe){ throw badImage(path,"image could not be decoded: "+ioe); }
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
      +"width: "+w+"\n"
      +"height: "+h+"\n"
      +"pixels: "+pixels+"\n"
      +"maxPixels: "+Long.toUnsignedString(maxPixels)
      );
  }

  static BufferedImage normalize(BufferedImage raw){
    if (raw.getType() == BufferedImage.TYPE_INT_ARGB_PRE){ return raw; }
    var res=new BufferedImage(raw.getWidth(),raw.getHeight(),BufferedImage.TYPE_INT_ARGB_PRE);
    var g=res.createGraphics();
    try{
      g.setComposite(AlphaComposite.Src);
      g.drawImage(raw,0,0,null);
    }
    finally{ g.dispose(); }
    return res;
  }

  static RuntimeException badImage(String path,String msg){
    throw nonDetErr("Image asset could not be read.\npath: "+path+"\n"+msg);
  }
}