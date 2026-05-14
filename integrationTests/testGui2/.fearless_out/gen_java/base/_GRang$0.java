package base;
public interface _GRang$0 extends base.CacheReprF$2, base.CaptureFree$0{
  default Object imm$$hash$1(Object p0){
    var r$= (base.MinMax$0)p0;
    return ((base.Str$0)((base.Str$0)((base.Str$0)base.Str$0Instance.instance("[").imm$$plus$1(
r$.read$min$0(
))).imm$$plus$1(
base.Str$0Instance.instance("-"))).imm$$plus$1(
r$.read$max$0(
))).imm$$plus$1(
base.Str$0Instance.instance("]"));
  }
  _GRang$0 instance= new _GRang$0(){};}