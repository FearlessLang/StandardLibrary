package base;
public interface MaxMinTests$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Nat$0)base.Math$0.instance.imm$max$3(
base.Nat$0Instance.instance(1L),
base.Nat$0Instance.instance(2L),
new base.OrderBy$2(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.Nat$0)p0;
    return _bimpl$;
  }

})).read$assertEq$1(
base.Nat$0Instance.instance(2L)))).imm$test$1(
((base.Nat$0)base.Math$0.instance.imm$min$3(
base.Nat$0Instance.instance(2L),
base.Nat$0Instance.instance(1L),
new base.OrderBy$2(){
    public Object imm$$hash$1(Object p0){
    var _cimpl$= (base.Nat$0)p0;
    return _cimpl$;
  }

})).read$assertEq$1(
base.Nat$0Instance.instance(1L)))).imm$test$1(
((base.Str$0)((base._Person$0)base.Math$0.instance.imm$max$3(
base._Persons$0.instance.read$$hash$2(
base.Str$0Instance.instance("Alice"),
base.Nat$0Instance.instance(32L)),
base._Persons$0.instance.read$$hash$2(
base.Str$0Instance.instance("Bob"),
base.Nat$0Instance.instance(8L)),
new base.OrderBy$2(){
    public Object imm$$hash$1(Object p0){
    var _dimpl$= (base._Person$0)p0;
    return _dimpl$.read$age$0(
);
  }

})).read$name$0(
)).read$assertEq$1(
base.Str$0Instance.instance("Alice")))).imm$test$1(
((base.Str$0)((base._Person$0)base.Math$0.instance.imm$min$3(
base._Persons$0.instance.read$$hash$2(
base.Str$0Instance.instance("Alice"),
base.Nat$0Instance.instance(32L)),
base._Persons$0.instance.read$$hash$2(
base.Str$0Instance.instance("Bob"),
base.Nat$0Instance.instance(8L)),
new base.OrderBy$2(){
    public Object imm$$hash$1(Object p0){
    var _eimpl$= (base._Person$0)p0;
    return _eimpl$.read$age$0(
);
  }

})).read$name$0(
)).read$assertEq$1(
base.Str$0Instance.instance("Bob")))).imm$test$1(
((base.Str$0)((base._Person$0)base.Math$0.instance.imm$min$3(
base._Persons$0.instance.read$$hash$2(
base.Str$0Instance.instance("Carol"),
base.Nat$0Instance.instance(32L)),
base._Persons$0.instance.read$$hash$2(
base.Str$0Instance.instance("Daniel"),
base.Nat$0Instance.instance(32L)),
new base.OrderBy$2(){
    public Object imm$$hash$1(Object p0){
    var _fimpl$= (base._Person$0)p0;
    return _fimpl$.read$age$0(
);
  }

})).read$name$0(
)).read$assertEq$1(
base.Str$0Instance.instance("Carol")))).imm$test$1(
((base.Str$0)((base._Person$0)base.Math$0.instance.imm$max$3(
base._Persons$0.instance.read$$hash$2(
base.Str$0Instance.instance("Carol"),
base.Nat$0Instance.instance(32L)),
base._Persons$0.instance.read$$hash$2(
base.Str$0Instance.instance("Daniel"),
base.Nat$0Instance.instance(32L)),
new base.OrderBy$2(){
    public Object imm$$hash$1(Object p0){
    var _gimpl$= (base._Person$0)p0;
    return _gimpl$.read$age$0(
);
  }

})).read$name$0(
)).read$assertEq$1(
base.Str$0Instance.instance("Carol")));
  }
  MaxMinTests$0 instance= new MaxMinTests$0(){};}