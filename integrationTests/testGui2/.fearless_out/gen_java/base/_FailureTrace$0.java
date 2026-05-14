package base;
public interface _FailureTrace$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var i$= (base.Info$0)p0;
    return ((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base._FrameField$0.instance.imm$$hash$2(
i$,
base.Str$0Instance.instance("fileName"));
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var fn$= (base.Str$0)p0;
    var _aeqS$= (base.Block$1)p1;
    return _aeqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base._FrameField$0.instance.imm$$hash$2(
i$,
base.Str$0Instance.instance("typeName"));
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var tn$= (base.Str$0)p0;
    var _beqS$= (base.Block$1)p1;
    return _beqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base._FrameField$0.instance.imm$$hash$2(
i$,
base.Str$0Instance.instance("methodName"));
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var mn$= (base.Str$0)p0;
    var _ceqS$= (base.Block$1)p1;
    return _ceqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base._FrameField$0.instance.imm$$hash$2(
i$,
base.Str$0Instance.instance("lineNumber"));
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var ln$= (base.Str$0)p0;
    var _deqS$= (base.Block$1)p1;
    return _deqS$.mut$let$2(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Opt$1)mn$.imm$indexOf$1(
base.Str$0Instance.instance(" "))).imm$orLazy$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return base.Error$0.instance.imm$msg$1(
base.Str$0Instance.instance("Malformed stack frame methodName: ").imm$$plus$1(
mn$));
  }

});
  }

},
new base.Continuation$2(){
    public Object mut$$hash$2(Object p0, Object p1){
    var sp$= (base.Nat$0)p0;
    var _eeqS$= (base.Block$1)p1;
    return _eeqS$.mut$return$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)((base.Str$0)mn$.imm$sub$2(
base.Nat$0Instance.instance(0L),
sp$.imm$$plus$1(
base.Nat$0Instance.instance(1L)))).imm$$plus$1(
tn$)).imm$$plus$1(
mn$.imm$sub$2(
sp$.imm$$plus$1(
base.Nat$0Instance.instance(1L)),
mn$.imm$size$0(
)))).imm$$plus$1(
base.Str$0Instance.instance(" error line: "))).imm$$plus$1(
ln$)).imm$$plus$1(
base.Str$0Instance.instance(" in file "))).imm$$plus$1(
fn$);
  }

});
  }

});
  }

});
  }

});
  }

});
  }

});
  }
  _FailureTrace$0 instance= new _FailureTrace$0(){};}