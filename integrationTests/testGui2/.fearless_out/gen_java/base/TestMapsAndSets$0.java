package base;
public interface TestMapsAndSets$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Nat$0)((base.Map$2)base.Maps$0.instance.imm$$hash$5(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.Person$0)p0;
    return _bimpl$;
  }

},
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(25L),
base.Str$0Instance.instance("Bob"),
base.List$1.instance),
base.Str$0Instance.instance("Toronto 34b Warden St."),
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(34L),
base.Str$0Instance.instance("Alice"),
base.List$1.instance),
base.Str$0Instance.instance("Wellington 134 Kelburn Parade"))).read$size$0(
)).read$assertEq$1(
base.Nat$0Instance.instance(2L)))).imm$test$1(
((base.Bool$0)((base.Map$2)base.Maps$0.instance.imm$$hash$5(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _cimpl$= (base.Person$0)p0;
    return _cimpl$;
  }

},
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(25L),
base.Str$0Instance.instance("Bob"),
base.List$1.instance),
base.Str$0Instance.instance("Toronto 34b Warden St."),
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(34L),
base.Str$0Instance.instance("Alice"),
base.List$1.instance),
base.Str$0Instance.instance("Wellington 134 Kelburn Parade"))).read$isEmpty$0(
)).read$assertEq$1(
base.False$0.instance))).imm$test$1(
((base.Str$0)((base.Map$2)base.Maps$0.instance.imm$$hash$5(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _dimpl$= (base.Person$0)p0;
    return _dimpl$;
  }

},
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(25L),
base.Str$0Instance.instance("Bob"),
base.List$1.instance),
base.Str$0Instance.instance("Toronto 34b Warden St."),
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(34L),
base.Str$0Instance.instance("Alice"),
base.List$1.instance),
base.Str$0Instance.instance("Wellington 134 Kelburn Parade"))).mut$get$1(
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(34L),
base.Str$0Instance.instance("Alice"),
base.List$1.instance))).read$assertEq$1(
base.Str$0Instance.instance("Wellington 134 Kelburn Parade")))).imm$test$1(
((base.Str$0)((base.Opt$1)((base.Map$2)base.Maps$0.instance.imm$$hash$5(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _eimpl$= (base.Person$0)p0;
    return _eimpl$;
  }

},
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(25L),
base.Str$0Instance.instance("Bob"),
base.List$1.instance),
base.Str$0Instance.instance("Toronto 34b Warden St."),
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(34L),
base.Str$0Instance.instance("Alice"),
base.List$1.instance),
base.Str$0Instance.instance("Wellington 134 Kelburn Parade"))).mut$opt$1(
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(38L),
base.Str$0Instance.instance("Neil Armstrong"),
base.List$1.instance))).mut$orValue$1(
base.Str$0Instance.instance("Moon"))).read$assertEq$1(
base.Str$0Instance.instance("Moon")))).imm$test$1(
((base.Str$0)((base.List$1)((base.Flow$1)((base.Flow$1)((base.Map$2)base.Maps$0.instance.imm$$hash$5(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _fimpl$= (base.Person$0)p0;
    return _fimpl$;
  }

},
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(25L),
base.Str$0Instance.instance("Bob"),
base.List$1.instance),
base.Str$0Instance.instance("A"),
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(34L),
base.Str$0Instance.instance("Alice"),
base.List$1.instance),
base.Str$0Instance.instance("B"))).mut$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _gimpl$= (base.KeyElem$2)p0;
    return ((base.Str$0)((base.Person$0)_gimpl$.read$key$0(
)).read$name$0(
)).imm$$plus$1(
_gimpl$.imm$elem$0(
));
  }

})).mut$list$0(
)).mut$get$1(
base.Nat$0Instance.instance(0L))).read$assertEq$1(
base.Str$0Instance.instance("BobA")))).imm$test$1(
((base.Str$0)((base.List$1)((base.Flow$1)((base.Flow$1)((base.Map$2)base.Maps$0.instance.imm$$hash$5(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _himpl$= (base.Person$0)p0;
    return _himpl$;
  }

},
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(25L),
base.Str$0Instance.instance("Bob"),
base.List$1.instance),
base.Str$0Instance.instance("A"),
base.Persons$0.instance.imm$$hash$3(
base.Nat$0Instance.instance(34L),
base.Str$0Instance.instance("Alice"),
base.List$1.instance),
base.Str$0Instance.instance("B"))).mut$flow$0(
)).mut$map$1(
new base.F$2(){
    public Object read$$hash$1(Object p0){
    var _jimpl$= (base.KeyElem$2)p0;
    return ((base.Str$0)((base.Person$0)_jimpl$.read$key$0(
)).read$name$0(
)).imm$$plus$1(
_jimpl$.imm$elem$0(
));
  }

})).mut$list$0(
)).mut$get$1(
base.Nat$0Instance.instance(1L))).read$assertEq$1(
base.Str$0Instance.instance("AliceB")))).imm$test$1(
((base.Nat$0)((base.Map$2)((base.Flow$1)((base.List$1)base.Data$0.instance.imm$ps$0(
)).imm$flow$0(
)).mut$mapping$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _kimpl$= (base.Str$0)p0;
    return _kimpl$;
  }

},
new base.KeyElemMapper$3(){
    public Object imm$key$1(Object p0){
    var p$= (base.Person$0)p0;
    return p$.read$name$0(
);
  }

    public Object imm$elem$1(Object p0){
    var p$= (base.Person$0)p0;
    return p$.read$age$0(
);
  }

})).mut$get$1(
base.Str$0Instance.instance("Dr. Carol"))).read$assertEq$1(
base.Nat$0Instance.instance(40L)));
  }
  TestMapsAndSets$0 instance= new TestMapsAndSets$0(){};}