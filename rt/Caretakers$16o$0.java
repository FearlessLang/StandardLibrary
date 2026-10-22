package _base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public interface Caretakers$16o$0 extends Sealed$2o$0{
  default Object imm$$hash$1(Object p0){
    var gate= (Var$c$1)p0;
    var first= gate.mut$get$0().getClass();
    return Proxy.newProxyInstance(first.getClassLoader(), first.getInterfaces(), (proxy, m, args) -> forward(proxy, gate.mut$get$0(), m, args));
  }
  private static Object forward(Object proxy, Object target, Method m, Object[] args) throws Throwable{
    var name= m.getName();
    if (name.equals("mut$iso$0") || name.equals("mut$close$0")){ return proxy; }
    var method= m.getDeclaringClass() == Object.class ? m : target.getClass().getInterfaces()[0].getMethod(name, m.getParameterTypes());
    try{ return method.invoke(target, args); }
    catch (InvocationTargetException e){ throw e.getCause(); }
  }
  Caretakers$16o$0 instance= new Caretakers$16o$0(){};
}
