package base;
public interface _XmlEscape$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var s$= (base.Str$0)p0;
    return s$.imm$replaceSimultaneous$1(
base.Lists$0.instance.imm$$hash$6(
base.Str$0Instance.instance("&"),
base.Str$0Instance.instance("&amp;"),
base.Str$0Instance.instance("<"),
base.Str$0Instance.instance("&lt;"),
base.Str$0Instance.instance("\""),
base.Str$0Instance.instance("&quot;")));
  }
  _XmlEscape$0 instance= new _XmlEscape$0(){};}