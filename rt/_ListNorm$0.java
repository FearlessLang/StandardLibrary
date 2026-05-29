package base;
import static base.Util.*;

import java.util.List;
public interface _ListNorm$0{
  @SuppressWarnings("unchecked")
  default Object imm$$hash$2(Object p0, Object p1){
    var list= (List$1)p0;
    if (!(list instanceof List$1Instance nel)){ return emptyList; }
    List<Object> vals= nel.val();
    vals= vals.stream().map(e->callMF$2(p1, e)).toList();
    return _ListNorm$0.myCache.computeIfAbsent(vals,vs->new Norm(new List$1Instance((List<Object>)vs)));
  }
  Norm emptyList=new Norm(List$1.instance);
  java.util.concurrent.ConcurrentHashMap<Object,Object> myCache= new java.util.concurrent.ConcurrentHashMap<>();
  _ListNorm$0 instance= new _ListNorm$0(){};
}