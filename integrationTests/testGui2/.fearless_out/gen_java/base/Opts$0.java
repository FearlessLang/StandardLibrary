package base;
public interface Opts$0{
  default Object imm$$hash$1(Object p0){
    var this$= this;
    var x$= p0;
    return new base.Opt$1(){
    public Object mut$match$1(Object p0){
    var m$= (base.OptMatch$2)p0;
    return m$.mut$some$1(
x$);
  }

    public Object read$match$1(Object p0){
    var m$= (base.OptMatch$2)p0;
    return m$.mut$some$1(
x$);
  }

    public Object imm$match$1(Object p0){
    var m$= (base.OptMatch$2)p0;
    return m$.mut$some$1(
x$);
  }

};
  }
  Opts$0 instance= new Opts$0(){};}