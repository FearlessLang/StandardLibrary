package base;

import java.util.Arrays;

final class TextEncoding{
  static final Object malformedEncoding=reason(0);
  static final Object truncatedEncoding=reason(1);
  static final Object notScalar=reason(2);
  static final Object notInStr=reason(3);

  private TextEncoding(){ throw new AssertionError(); }

  interface Decoder{ void decode(byte[] bs,Sink out); }

  interface Sink{
    void scalar(int byteOffset,int byteLength,int cp);
    void bad(Object reason,int byteOffset,int byteLength,long rejectedValue);
    Object result();
  }

  static Object str(byte[] bs,Object recover,Decoder decoder){
    var out=new StrSink((BadStrUnitRecover$0)recover);
    decoder.decode(bs,out);
    return out.result();
  }

  static Object uStr(byte[] bs,Object recover,Decoder decoder){
    var out=new UStrSink((BadUStrUnitRecover$0)recover);
    decoder.decode(bs,out);
    return out.result();
  }

  static Object reason(int id){
    return new BadTextReason$0(){
      @Override public Object imm$match$1(Object p0){
        var m=(BadTextReasonMatch$1)p0;
        return switch(id){
          case 0 -> m.mut$malformedEncoding$0();
          case 1 -> m.mut$truncatedEncoding$0();
          case 2 -> m.mut$notScalar$0();
          case 3 -> m.mut$notInStr$0();
          default -> throw new AssertionError(id);
          };
      }
    };
  }

  static Object nat(long n){ return Nat$0Instance.instance(n); }

  static final class StrSink implements Sink{
    final BadStrUnitRecover$0 recover;
    final StringBuilder out=new StringBuilder();

    StrSink(BadStrUnitRecover$0 recover){ this.recover=recover; }

    @Override public void scalar(int byteOffset,int byteLength,int cp){
      if (UStr$0Instance.isStrScalar(cp)){
        out.appendCodePoint(cp);
        return;
      }
      bad(notInStr,byteOffset,byteLength,cp);
    }

    @Override public void bad(Object reason,int byteOffset,int byteLength,long rejectedValue){
      out.append(((Str$0Instance)recover.mut$$hash$4(
        reason,nat(byteOffset),nat(byteLength),nat(rejectedValue)
        )).val());
    }

    @Override public Object result(){ return Str$0Instance.instance(out.toString()); }
  }

  static final class UStrSink implements Sink{
    final BadUStrUnitRecover$0 recover;
    int[] out=new int[16];
    int size;

    UStrSink(BadUStrUnitRecover$0 recover){ this.recover=recover; }

    @Override public void scalar(int byteOffset,int byteLength,int cp){ add(cp); }

    @Override public void bad(Object reason,int byteOffset,int byteLength,long rejectedValue){
      addAll(((UStr$0Instance)recover.mut$$hash$4(
        reason,nat(byteOffset),nat(byteLength),nat(rejectedValue)
        )).val());
    }

    void add(int cp){
      assert UStr$0Instance.isScalar(cp);
      if (size == out.length){ out=Arrays.copyOf(out,Math.multiplyExact(out.length,2)); }
      out[size++]=cp;
    }

    void addAll(int[] cps){
      int newSize=Math.addExact(size,cps.length);
      if (newSize > out.length){
        int cap=out.length;
        while (cap < newSize){ cap=Math.multiplyExact(cap,2); }
        out=Arrays.copyOf(out,cap);
      }
      System.arraycopy(cps,0,out,size,cps.length);
      size=newSize;
    }

    @Override public Object result(){ return UStr$0Instance.instance(Arrays.copyOf(out,size)); }
  }

  static void ascii(byte[] bs,Sink out){
    for (int i=0;i<bs.length;i++){
      int b=u8(bs[i]);
      if (b < 0x80){ out.scalar(i,1,b); }
      else{ out.bad(malformedEncoding,i,1,b); }
    }
  }

  static void latin1(byte[] bs,Sink out){
    for (int i=0;i<bs.length;i++){ out.scalar(i,1,u8(bs[i])); }
  }

  static void utf16le(byte[] bs,Sink out){ utf16(bs,out,true); }
  static void utf16be(byte[] bs,Sink out){ utf16(bs,out,false); }

  static void utf16(byte[] bs,Sink out,boolean le){
    for (int i=0;i<bs.length;){
      if (i+1 >= bs.length){
        out.bad(truncatedEncoding,i,bs.length-i,u8(bs[i]));
        break;
      }
      int w1=le?u16le(bs,i):u16be(bs,i);
      if (low(w1)){
        out.bad(notScalar,i,2,w1);
        i+=2;
        continue;
      }
      if (!high(w1)){
        out.scalar(i,2,w1);
        i+=2;
        continue;
      }
      if (i+3 >= bs.length){
        out.bad(notScalar,i,2,w1);
        i+=2;
        continue;
      }
      int w2=le?u16le(bs,i+2):u16be(bs,i+2);
      if (!low(w2)){
        out.bad(notScalar,i,2,w1);
        i+=2;
        continue;
      }
      out.scalar(i,4,0x10000+((w1-0xD800)<<10)+(w2-0xDC00));
      i+=4;
    }
  }

  static void utf32le(byte[] bs,Sink out){ utf32(bs,out,true); }
  static void utf32be(byte[] bs,Sink out){ utf32(bs,out,false); }

  static void utf32(byte[] bs,Sink out,boolean le){
    for (int i=0;i<bs.length;){
      if (i+3 >= bs.length){
        out.bad(truncatedEncoding,i,bs.length-i,le?partial32le(bs,i):partial32be(bs,i));
        break;
      }
      long cp=le?u32le(bs,i):u32be(bs,i);
      if (!isScalar(cp)){ out.bad(notScalar,i,4,cp); }
      else{ out.scalar(i,4,(int)cp); }
      i+=4;
    }
  }

  static void utf8(byte[] bs,Sink out){
    for (int i=0;i<bs.length;){
      int b0=u8(bs[i]);
      if (b0 < 0x80){
        out.scalar(i,1,b0);
        i+=1;
        continue;
      }
      if (b0 < 0xC0){
        out.bad(malformedEncoding,i,1,b0);
        i+=1;
        continue;
      }
      if (b0 < 0xC2){
        int len=utf8MalformedLength(bs,i,2);
        out.bad(malformedEncoding,i,len,b0);
        i+=len;
        continue;
      }
      if (b0 < 0xE0){
        if (i+1 >= bs.length){
          out.bad(truncatedEncoding,i,bs.length-i,b0);
          break;
        }
        int b1=u8(bs[i+1]);
        if (!cont(b1)){
          out.bad(malformedEncoding,i,1,b0);
          i+=1;
          continue;
        }
        out.scalar(i,2,((b0&0x1F)<<6)|(b1&0x3F));
        i+=2;
        continue;
      }
      if (b0 < 0xF0){
        if (i+1 >= bs.length){
          out.bad(truncatedEncoding,i,bs.length-i,b0);
          break;
        }
        int b1=u8(bs[i+1]);
        if (!cont(b1)){
          out.bad(malformedEncoding,i,1,b0);
          i+=1;
          continue;
        }
        if (b0 == 0xE0 && b1 < 0xA0){
          int len=utf8MalformedLength(bs,i,3);
          out.bad(malformedEncoding,i,len,b0);
          i+=len;
          continue;
        }
        if (i+2 >= bs.length){
          out.bad(truncatedEncoding,i,bs.length-i,b0);
          break;
        }
        int b2=u8(bs[i+2]);
        if (!cont(b2)){
          out.bad(malformedEncoding,i,2,b0);
          i+=2;
          continue;
        }
        int cp=((b0&0x0F)<<12)|((b1&0x3F)<<6)|(b2&0x3F);
        if (!UStr$0Instance.isScalar(cp)){ out.bad(notScalar,i,3,cp); }
        else{ out.scalar(i,3,cp); }
        i+=3;
        continue;
      }
      if (b0 < 0xF8){
        if (i+1 >= bs.length){
          out.bad(truncatedEncoding,i,bs.length-i,b0);
          break;
        }
        int b1=u8(bs[i+1]);
        if (!cont(b1)){
          out.bad(malformedEncoding,i,1,b0);
          i+=1;
          continue;
        }
        if (b0 == 0xF0 && b1 < 0x90){
          int len=utf8MalformedLength(bs,i,4);
          out.bad(malformedEncoding,i,len,b0);
          i+=len;
          continue;
        }
        if (i+2 >= bs.length){
          out.bad(truncatedEncoding,i,bs.length-i,b0);
          break;
        }
        int b2=u8(bs[i+2]);
        if (!cont(b2)){
          out.bad(malformedEncoding,i,2,b0);
          i+=2;
          continue;
        }
        if (i+3 >= bs.length){
          out.bad(truncatedEncoding,i,bs.length-i,b0);
          break;
        }
        int b3=u8(bs[i+3]);
        if (!cont(b3)){
          out.bad(malformedEncoding,i,3,b0);
          i+=3;
          continue;
        }
        int cp=((b0&0x07)<<18)|((b1&0x3F)<<12)|((b2&0x3F)<<6)|(b3&0x3F);
        if (!UStr$0Instance.isScalar(cp)){ out.bad(notScalar,i,4,cp); }
        else{ out.scalar(i,4,cp); }
        i+=4;
        continue;
      }
      out.bad(malformedEncoding,i,1,b0);
      i+=1;
    }
  }

  static int utf8MalformedLength(byte[] bs,int i,int expectedLength){
    int len=1;
    while (len < expectedLength && i+len < bs.length && cont(u8(bs[i+len]))){ len++; }
    return len;
  }

  static boolean isScalar(long cp){
    return 0 <= cp && cp <= 0x10FFFF && !(0xD800 <= cp && cp <= 0xDFFF);
  }

  static boolean high(int w){ return 0xD800 <= w && w <= 0xDBFF; }
  static boolean low(int w){ return 0xDC00 <= w && w <= 0xDFFF; }
  static boolean cont(int b){ return 0x80 <= b && b <= 0xBF; }
  static int u8(byte b){ return b&0xFF; }

  static int u16le(byte[] bs,int i){ return u8(bs[i]) | (u8(bs[i+1])<<8); }
  static int u16be(byte[] bs,int i){ return (u8(bs[i])<<8) | u8(bs[i+1]); }

  static long u32le(byte[] bs,int i){
    return ((long)u8(bs[i]))
      | ((long)u8(bs[i+1])<<8)
      | ((long)u8(bs[i+2])<<16)
      | ((long)u8(bs[i+3])<<24);
  }

  static long u32be(byte[] bs,int i){
    return ((long)u8(bs[i])<<24)
      | ((long)u8(bs[i+1])<<16)
      | ((long)u8(bs[i+2])<<8)
      | ((long)u8(bs[i+3]));
  }

  static long partial32le(byte[] bs,int i){
    long res=0;
    for (int j=0;i+j<bs.length;j++){ res|=(long)u8(bs[i+j])<<(8*j); }
    return res;
  }

  static long partial32be(byte[] bs,int i){
    long res=0;
    for (int j=0;i+j<bs.length;j++){ res=(res<<8)|u8(bs[i+j]); }
    return res;
  }
}