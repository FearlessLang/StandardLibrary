package base;
public interface _FormatFailure$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var i$= (base.Info$0)p0;
    return ((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("<failure>").imm$$or$1(
base._FailureExpected$0.instance.imm$$hash$1(
i$))).imm$$or$1(
base._FailureActual$0.instance.imm$$hash$1(
i$))).imm$$or$1(
base._XmlEscape$0.instance.imm$$hash$1(
base._FailureMsg$0.instance.imm$$hash$1(
i$)))).imm$$plus$1(
base._XmlEscape$0.instance.imm$$hash$1(
base._FailureList$0.instance.imm$$hash$1(
i$)))).imm$$or$1(
base.Str$0Instance.instance("</failure>"));
  }
  _FormatFailure$0 instance= new _FormatFailure$0(){};}