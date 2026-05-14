package base;
public interface KeyElems$0{
  default Object imm$$hash$2(Object p0, Object p1){
    var this$= this;
    var key$= p0;
    var elem$= p1;
    return new base.KeyElem$2(){
    public Object read$key$0(){
    return key$;
  }

    public Object mut$elem$0(){
    return elem$;
  }

    public Object read$elem$0(){
    return elem$;
  }

    public Object imm$elem$0(){
    return elem$;
  }

};
  }
  KeyElems$0 instance= new KeyElems$0(){};}