package base;
public interface ThrowRangeSuite$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var try$= (base.CapTry$0)p0;
    return ((base.Block$1)((base.Block$1)((base.Block$1)((base.Block$1)((base.Block$1)((base.Block$1)((base.Block$1)base.Block$0.instance.imm$$hash$0(
)).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Action$1)try$.mut$$hash$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Int$0Instance.instance(3L).read$assertInRange$2(
base.Int$0Instance.instance(5L),
base.Int$0Instance.instance(1L));
  }

})).mut$assertInfo$0(
);
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Action$1)try$.mut$$hash$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Int$0Instance.instance(3L).read$assertInRange$2(
base.Int$0Instance.instance(5L),
base.Int$0Instance.instance(7L));
  }

})).mut$assertInfo$0(
);
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Action$1)try$.mut$$hash$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Int$0Instance.instance(5L).read$assertInRangeOpen$2(
base.Int$0Instance.instance(5L),
base.Int$0Instance.instance(7L));
  }

})).mut$assertInfo$0(
);
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Action$1)try$.mut$$hash$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Str$0Instance.instance("b").read$assertInRange$2(
base.Str$0Instance.instance("c"),
base.Str$0Instance.instance("d"));
  }

})).mut$assertInfo$0(
);
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Action$1)try$.mut$$hash$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Int$0Instance.instance(3L).imm$clamp$2(
base.Int$0Instance.instance(5L),
base.Int$0Instance.instance(1L));
  }

})).mut$assertInfo$0(
);
  }

})).mut$do$1(
new base.MF$1(){
    public Object mut$$hash$0(){
    return ((base.Action$1)try$.mut$$hash$1(
new base.F$1(){
    public Object read$$hash$0(){
    return base.Nat$0Instance.instance(3L).imm$clamp$2(
base.Nat$0Instance.instance(5L),
base.Nat$0Instance.instance(1L));
  }

})).mut$assertInfo$0(
);
  }

})).mut$done$0(
);
  }
  ThrowRangeSuite$0 instance= new ThrowRangeSuite$0(){};}