package _base;

public interface UStrCleaners$5mo$0{
  default Object imm$text$0(){ return TextCleaning.clean(true, false); }
  default Object imm$line$0(){ return TextCleaning.clean(true, true); }
  default Object imm$utf8$0(){ return TextCleaning.strictUtf8(true); }
  default Object imm$utf8OrElse$1(Object f){ return TextCleaning.encoding(true, TextCleaning.Utf8::new, f); }
  default Object imm$utf16LeOrElse$1(Object f){ return TextCleaning.encoding(true, () -> new TextCleaning.Utf16(true), f); }
  default Object imm$utf16BeOrElse$1(Object f){ return TextCleaning.encoding(true, () -> new TextCleaning.Utf16(false), f); }
  default Object imm$utf32LeOrElse$1(Object f){ return TextCleaning.encoding(true, () -> new TextCleaning.Utf32(true), f); }
  default Object imm$utf32BeOrElse$1(Object f){ return TextCleaning.encoding(true, () -> new TextCleaning.Utf32(false), f); }
  default Object imm$asciiOrElse$1(Object f){ return TextCleaning.encoding(true, TextCleaning.Ascii::new, f); }
  default Object imm$latin1OrElse$1(Object f){ return TextCleaning.encoding(true, TextCleaning.Latin1::new, f); }
  default Object imm$utf8Then$1(Object c){ return TextCleaning.then(true, c); }
  UStrCleaners$5mo$0 instance= new UStrCleaners$5mo$0(){};
}