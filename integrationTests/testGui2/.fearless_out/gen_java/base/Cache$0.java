package base;
public interface Cache$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var f$= (base.CacheF$1)p0;
    return f$.imm$_get$0(
);
  }
  default Object imm$$hash$2(Object p0, Object p1){
    var this$= this;
    var a$= (base.Norm$1)p0;
    var f$= (base.CacheF$2)p1;
    return f$.imm$_get$1(
a$);
  }
  default Object imm$$hash$3(Object p0, Object p1, Object p2){
    var this$= this;
    var a$= (base.Norm$1)p0;
    var b$= (base.Norm$1)p1;
    var f$= (base.CacheF$3)p2;
    return f$.imm$_get$2(
a$,
b$);
  }
  default Object imm$norm$1(Object p0){
    var this$= this;
    var f$= (base.CacheMemo$1)p0;
    return f$.imm$_get$0(
);
  }
  default Object imm$norm$2(Object p0, Object p1){
    var this$= this;
    var a$= (base.Norm$1)p0;
    var f$= (base.CacheMemo$2)p1;
    return f$.imm$_get$1(
a$);
  }
  default Object imm$norm$3(Object p0, Object p1, Object p2){
    var this$= this;
    var a$= (base.Norm$1)p0;
    var b$= (base.Norm$1)p1;
    var f$= (base.CacheMemo$3)p2;
    return f$.imm$_get$2(
a$,
b$);
  }
  Cache$0 instance= new Cache$0(){};}