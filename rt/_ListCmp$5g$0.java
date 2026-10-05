package _base;

import static _base.Util.*;

public interface _ListCmp$5g$0{
  default Object imm$$hash$3(Object by, Object a, Object b){
    var xs= List$o$1Instance.asJava(a);
    var ys= List$o$1Instance.asJava(b);
    for (int i= 0; i < Math.min(xs.size(), ys.size()); i++){
      int c= cmp((OrderBy$5e$2)by, xs.get(i), ys.get(i));
      if (c != 0){ return ord(c); }
    }
    return ord(Integer.compare(xs.size(), ys.size()));
  }
  _ListCmp$5g$0 instance= new _ListCmp$5g$0(){};
}
