package base;

import java.util.ArrayList;

public interface _Throw$0{
  default Object imm$deterministic$1(Object p0){
    var this$= this;
    var info$= (Info$0)p0;
    throw new RuntimeException(info$.read$str$0().toString());
  }
  default Object imm$nonDeterministic$1(Object p0){
    var this$= this;
    var info$= (Info$0)p0;
    throw new Error(info$.read$str$0().toString());
  }
  default Object imm$stackTrace$0(){
    var this$= this;
    var al=new ArrayList<>();
    al.add(new Str$0Instance("AAA"));
    var list= new List$1Instance(al);
    var dtId=new DataTypeBy$3(){ @Override public Object imm$$hash$1(Object p0){ return p0; } };
    return list.read$info$1(dtId);
  }
  _Throw$0 instance= new _Throw$0(){};}