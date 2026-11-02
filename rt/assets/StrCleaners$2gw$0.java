package _base;

public interface StrCleaners$2gw$0{
  default Object imm$text$0(){ return TextCleaning.clean(false, false); }
  default Object imm$line$0(){ return TextCleaning.clean(false, true); }
  default Object imm$utf8$0(){ return TextCleaning.strictUtf8(false); }
  default Object imm$utf8OrElse$1(Object f){ return TextCleaning.encoding(false, TextCleaning.Utf8::new, f); }
  default Object imm$utf16LeOrElse$1(Object f){ return TextCleaning.encoding(false, () -> new TextCleaning.Utf16(true), f); }
  default Object imm$utf16BeOrElse$1(Object f){ return TextCleaning.encoding(false, () -> new TextCleaning.Utf16(false), f); }
  default Object imm$utf32LeOrElse$1(Object f){ return TextCleaning.encoding(false, () -> new TextCleaning.Utf32(true), f); }
  default Object imm$utf32BeOrElse$1(Object f){ return TextCleaning.encoding(false, () -> new TextCleaning.Utf32(false), f); }
  default Object imm$asciiOrElse$1(Object f){ return TextCleaning.encoding(false, TextCleaning.Ascii::new, f); }
  default Object imm$latin1OrElse$1(Object f){ return TextCleaning.encoding(false, TextCleaning.Latin1::new, f); }
  default Object imm$utf8Then$1(Object c){ return TextCleaning.then(false, c); }
  StrCleaners$2gw$0 instance= new StrCleaners$2gw$0(){};
}