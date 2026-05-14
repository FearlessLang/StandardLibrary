package base;
public interface _DecidedBlock$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var res$= p0;
    return new base.Block$1(){
    public Object mut$return$1(Object p0){
    var self$= this;
    return res$;
  }

    public Object mut$do$1(Object p0){
    var self$= this;
    return self$;
  }

    public Object mut$let$2(Object p0, Object p1){
    var self$= this;
    return res$;
  }

    public Object mut$openIso$2(Object p0, Object p1){
    var self$= this;
    return res$;
  }

    public Object mut$var$2(Object p0, Object p1){
    var self$= this;
    return res$;
  }

    public Object mut$repr$2(Object p0, Object p1){
    var self$= this;
    return res$;
  }

    public Object mut$assert$1(Object p0){
    var self$= this;
    return self$;
  }

    public Object mut$assert$2(Object p0, Object p1){
    var self$= this;
    return self$;
  }

    public Object mut$loop$1(Object p0){
    var self$= this;
    return self$;
  }

    public Object mut$if$1(Object p0){
    var self$= this;
    return new base.BlockIf$1(){
    public Object mut$return$1(Object p0){
    return self$;
  }

    public Object mut$do$1(Object p0){
    return self$;
  }

    public Object mut$error$1(Object p0){
    return self$;
  }

    public Object mut$done$0(){
    return base._DecidedBlock$0.instance.imm$$hash$1(
base.Void$0.instance);
  }

};
  }

};
  }
  _DecidedBlock$0 instance= new _DecidedBlock$0(){};}