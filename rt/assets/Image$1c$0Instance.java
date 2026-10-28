package _base;

import io.github.humbleui.skija.Bitmap;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Surface;

final class Image$1c$0Instance implements Image$1c$0{
  final Image image;
  Image$1c$0Instance(Image image){ this.image= image; }

  @Override public Object read$width$0(){ return width(image.getWidth()); }
  @Override public Object read$height$0(){ return height(image.getHeight()); }

  @Override public Object imm$scale$2(Object w, Object h){
    return scaled(widthVal(w), heightVal(h));
  }
  @Override public Object imm$scaleToWidth$p1$1(Object w){
    var newW= widthVal(w);
    return scaled(newW, side(proportional(image.getHeight(), newW, image.getWidth()), "image height"));
  }
  @Override public Object imm$scaleToHeight$p1$1(Object h){
    var newH= heightVal(h);
    return scaled(side(proportional(image.getWidth(), newH, image.getHeight()), "image width"), newH);
  }
  @Override public Object read$color$2(Object x, Object y){
    try (var b= new Bitmap()){
      b.allocN32Pixels(1, 1);
      image.readPixels(b, pixel(((XInt$s$0) x).read$get$0(), image.getWidth(), "x"), pixel(((YInt$s$0) y).read$get$0(), image.getHeight(), "y"));
      int c= b.getColor(0, 0);
      return Color$1c$0.instance.imm$$hash$4(Red$c$0.instance.read$$hash$1(byt(c >>> 16)), Green$1c$0.instance.read$$hash$1(byt(c >>> 8)), Blue$o$0.instance.read$$hash$1(byt(c)), Alpha$1c$0.instance.read$$hash$1(byt(c >>> 24)));
    }
  }
  private static Object byt(int c){ return Byte$o$0Instance.instance((byte) c); }
  private static int pixel(Object c, int side, String what){
    long v= Util.intToLong(c);
    if (v < 0 || v >= side){ throw Util.detErr("pixel " + what + " " + v + " is out of range; it must be between 0 and " + (side - 1)); }
    return (int) v;
  }
  @Override public Object imm$paint$1(Object p){
    try (var paint= new Paint().setAntiAlias(true); var surf= Surface.makeRaster(ImageInfo.makeN32Premul(image.getWidth(), image.getHeight()))){
      var cv= surf.getCanvas();
      cv.drawImage(image, 0, 0);
      painted(cv, (Painter$5c$0) p, paint);
      return new Image$1c$0Instance(surf.makeImageSnapshot());
    }
  }
  private void painted(Canvas cv, Painter$5c$0 p, Paint paint){
    var g= new CGraphicsCtx(cv, Scopes.time(0), width(image.getWidth()), height(image.getHeight()), paint, (Color$1c$0) Color$1c$0.instance);
    p.imm$run$1(g);
    var outside= g.outside("an image", "image", image.getWidth(), image.getHeight());
    if (outside != null){ System.err.print("Warning: "+outside+"\n"); }
  }
  static Image$1c$0Instance blank(Object w, Object h, Object background){
    var iw= widthVal(w);
    var ih= heightVal(h);
    checkPixels(iw, ih);
    try (var surf= Surface.makeRaster(ImageInfo.makeN32Premul(iw, ih))){
      surf.getCanvas().clear(Sk.color((Color$1c$0) background));
      return new Image$1c$0Instance(surf.makeImageSnapshot());
    }
  }

  private Image$1c$0Instance scaled(int w, int h){
    checkPixels(w, h);
    return new Image$1c$0Instance(Sk.scaled(image, w, h));
  }
  private static void checkPixels(int w, int h){
    long pixels= (long)w*h;
    if (pixels > Scopes.maxImagePixels){ throw Util.detErr("image "+w+" x "+h+" has "+pixels+" pixels; it must have <= "+Scopes.maxImagePixels); }
  }
  private static long proportional(int oldOther, int newMain, int oldMain){
    return Math.max(1, ((long)oldOther*newMain + oldMain/2L)/oldMain);
  }
  private static WidthNat$as$0 width(int n){
    return (WidthNat$as$0)WidthNat$as$0.instance.read$$hash$1(Nat$c$0Instance.instance(n));
  }
  private static HeightNat$lg$0 height(int n){
    return (HeightNat$lg$0)HeightNat$lg$0.instance.read$$hash$1(Nat$c$0Instance.instance(n));
  }
  private static int widthVal(Object o){
    return side(Util.natToLong(((WidthNat$as$0)o).read$get$0()), "image width");
  }
  private static int heightVal(Object o){
    return side(Util.natToLong(((HeightNat$lg$0)o).read$get$0()), "image height");
  }
  private static int side(long n, String what){
    if (n == 0){ throw Util.detErr(what+" 0 is too small; it must be >= 1"); }
    return Scopes.extent(n, what);
  }
}
