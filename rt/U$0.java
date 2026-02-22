package base;
import static base.Util.*;

public interface U$0{
  U$0 instance= new U$0(){};
  default Object imm$$plus$1(Object p0){
    var s=((Str$0Instance)p0).val();
    if (s.isEmpty()){ return UStr$0Instance.instance(""); }
    var sb= new StringBuilder();
    for (var part: s.split(" ",-1)){
      if (part.isEmpty()){ throw Util.err("Bad codepoint list: "+s); } // leading/trailing/double spaces
      try{ sb.appendCodePoint(Integer.parseInt(part,16));}
      catch(NumberFormatException nfe){ throw Util.err("Bad codepoint list: "+s); } // invalid hex -> NumberFormatException
      catch(IllegalArgumentException iae){ throw Util.err("Bad codepoint list: "+s); } // invalid hex -> invalide unicode number
    }
    return UStr$0Instance.instance(sb.toString());
  }
}