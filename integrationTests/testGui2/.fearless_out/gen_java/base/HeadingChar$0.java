package base;
public interface HeadingChar$0 extends base.DirectionMatch$1{
  default Object mut$north$0(){
    var this$= this;
    return base.Str$0Instance.instance("A");
  }
  default Object mut$east$0(){
    var this$= this;
    return base.Str$0Instance.instance("<");
  }
  default Object mut$south$0(){
    var this$= this;
    return base.Str$0Instance.instance("V");
  }
  default Object mut$west$0(){
    var this$= this;
    return base.Str$0Instance.instance(">");
  }
  HeadingChar$0 instance= new HeadingChar$0(){};}