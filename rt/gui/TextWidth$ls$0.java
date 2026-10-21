package _base;

public interface TextWidth$ls$0{
  default Object imm$$hash$2(Object text, Object textHeight){
    try (var line= Sk.shape(Scopes.ustr(text), Scopes.extent((HeightNat$lg$0) textHeight, "text height"))){ return Scopes.w((long) Math.ceil(line.getWidth())); }
  }
  TextWidth$ls$0 instance= new TextWidth$ls$0(){};
}
