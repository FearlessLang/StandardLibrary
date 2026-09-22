package base;

public interface _NativeLoop$16w$0 {
  _NativeLoop$16w$0 instance = new _NativeLoop$16w$0(){};

  default Object imm$$hash$2(Object self, Object body){
    var mf= (MF$7$1) body;
    return loop(self, ()->mf.mut$$hash$0());
  }
  default Object imm$$hash$3(Object self, Object a, Object body){
    var mf= (LoopBody$aw$2) body;
    return loop(self, ()->mf.mut$$hash$1(a));
  }
  private static Object loop(Object self, java.util.function.Supplier<Object> step){
    var matcher= new _NativeLoopMatcher(self);
    while (true){
      matcher.result= null;
      ((ControlFlow$2dk$1) step.get()).mut$match$1(matcher);
      if (matcher.result == _NativeLoopMatcher.CONTINUE){ continue; }
      return matcher.result;
    }
  }
}

final class _NativeLoopMatcher implements ControlFlowMatch$2428$2{
  static final Object CONTINUE= new Object();
  final Object self;
  Object result;
  _NativeLoopMatcher(Object self){ this.self= self; }
  @Override public Object mut$continue$0(){ result= CONTINUE; return null; }
  @Override public Object mut$break$0(){ result= self; return null; }
  @Override public Object mut$return$1(Object v){ result= _DecidedBlock$4r4$0.instance.imm$$hash$1(v); return null; }
}
