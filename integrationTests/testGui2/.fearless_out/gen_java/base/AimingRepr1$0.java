package base;
public interface AimingRepr1$0 extends base.DirectionMatch$1{
  default Object mut$north$0(){
    var this$= this;
    return base.Str$0Instance.instance(" / | \\ ");
  }
  default Object mut$east$0(){
    var this$= this;
    return base.Str$0Instance.instance(" / - \\ ");
  }
  default Object mut$south$0(){
    var this$= this;
    return base.Str$0Instance.instance(" / - \\ ");
  }
  default Object mut$west$0(){
    var this$= this;
    return base.Str$0Instance.instance(" / - \\ ");
  }
  AimingRepr1$0 instance= new AimingRepr1$0(){};}