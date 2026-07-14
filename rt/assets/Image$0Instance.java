package base;

import io.github.humbleui.skija.Image;

final class Image$0Instance implements Image$0{
  private final Image image;
  Image$0Instance(Image image){ this.image=image; }
  Image image(){ return image; }

  @Override public Object read$width$0(){ return width(image.getWidth()); }
  @Override public Object read$height$0(){ return height(image.getHeight()); }

  @Override public Object imm$scale$2(Object w,Object h){
    return new Image$0Instance(Sk.scaled(image,widthVal(w),heightVal(h)));
  }
  @Override public Object imm$scaleToWidth$1(Object w){
    var newW=widthVal(w);
    return new Image$0Instance(Sk.scaled(image,newW,proportional(image.getHeight(),newW,image.getWidth())));
  }
  @Override public Object imm$scaleToHeight$1(Object h){
    var newH=heightVal(h);
    return new Image$0Instance(Sk.scaled(image,proportional(image.getWidth(),newH,image.getHeight()),newH));
  }

  private static int proportional(int oldOther,int newMain,int oldMain){
    var res=((long)oldOther*newMain + oldMain/2L)/oldMain;
    if (res < 1){ return 1; }
    if (res > Integer.MAX_VALUE){ throw new AssertionError(res); }
    return (int)res;
  }
  private static WidthNat$0 width(int n){
    return (WidthNat$0)WidthNat$0.instance.read$$hash$1(Nat$0Instance.instance(n));
  }
  private static HeightNat$0 height(int n){
    return (HeightNat$0)HeightNat$0.instance.read$$hash$1(Nat$0Instance.instance(n));
  }
  private static int widthVal(Object o){
    return positiveInt(((WidthNat$0)o).read$get$0(),"image width");
  }
  private static int heightVal(Object o){
    return positiveInt(((HeightNat$0)o).read$get$0(),"image height");
  }
  private static int positiveInt(Object nat,String name){
    var n=((Nat$0Instance)nat).val();
    if (Long.compareUnsigned(n,1L) < 0){ throw new AssertionError(name+": "+Long.toUnsignedString(n)); }
    if (Long.compareUnsigned(n,Integer.MAX_VALUE) > 0){ throw new AssertionError(name+": "+Long.toUnsignedString(n)); }
    return (int)n;
  }
}