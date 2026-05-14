package base;
public interface StrParseIeeeFloat$0 extends base.F$2{
  default Object read$$hash$1(Object p0){
    var this$= this;
    var _aimpl$= (base.Tests$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)_aimpl$.imm$test$1(
((base.Bool$0)((base.Opt$1)base.Str$0Instance.instance("").imm$ieeeFloat$0(
)).read$isEmpty$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.Str$0Instance.instance("0.0").imm$ieeeFloat$0(
)).imm$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var f$= (base.Float$0)p0;
    return f$.imm$isPosZero$0(
);
  }

    public Object mut$empty$0(){
    return base.False$0.instance;
  }

})).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.Str$0Instance.instance("-0.0").imm$ieeeFloat$0(
)).imm$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var f$= (base.Float$0)p0;
    return f$.imm$isNegZero$0(
);
  }

    public Object mut$empty$0(){
    return base.False$0.instance;
  }

})).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.Str$0Instance.instance("NaN").imm$ieeeFloat$0(
)).imm$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var f$= (base.Float$0)p0;
    return f$.imm$isNaN$0(
);
  }

    public Object mut$empty$0(){
    return base.False$0.instance;
  }

})).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.Str$0Instance.instance("Infinity").imm$ieeeFloat$0(
)).imm$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var f$= (base.Float$0)p0;
    return f$.imm$isPosInfinity$0(
);
  }

    public Object mut$empty$0(){
    return base.False$0.instance;
  }

})).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.Str$0Instance.instance("-Infinity").imm$ieeeFloat$0(
)).imm$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var f$= (base.Float$0)p0;
    return f$.imm$isNegInfinity$0(
);
  }

    public Object mut$empty$0(){
    return base.False$0.instance;
  }

})).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Opt$1)base.Str$0Instance.instance("1.5").imm$ieeeFloat$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _bimpl$= (base.Float$0)p0;
    return _bimpl$;
  }

},
base.Opts$0.instance.imm$$hash$1(
base.Float$0Instance.instance(1.5d))))).imm$test$1(
((base.Opt$1)base.Str$0Instance.instance("1000.0").imm$ieeeFloat$0(
)).read$assertEq$2(
new base.OrderHashBy$2(){
    public Object imm$$hash$1(Object p0){
    var _cimpl$= (base.Float$0)p0;
    return _cimpl$;
  }

},
base.Opts$0.instance.imm$$hash$1(
base.Float$0Instance.instance(1000.0d))))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.Str$0Instance.instance("0.1").imm$ieeeFloat$0(
)).read$isSome$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.Str$0Instance.instance("-0.1").imm$ieeeFloat$0(
)).read$isSome$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Str$0)((base.Float$0)((base.Opt$1)base.Str$0Instance.instance("0.1").imm$ieeeFloat$0(
)).read$$bang$0(
)).imm$ieeeStr$0(
)).read$assertEq$1(
base.Str$0Instance.instance("0.1")))).imm$test$1(
((base.Str$0)((base.Float$0)((base.Opt$1)base.Str$0Instance.instance("0.100000000000000005").imm$ieeeFloat$0(
)).read$$bang$0(
)).imm$ieeeStr$0(
)).read$assertEq$1(
base.Str$0Instance.instance("0.1")))).imm$test$1(
((base.Bool$0)((base.Opt$1)((base.Str$0)base.Float$0Instance.instance(0.0d).imm$ieeeStr$0(
)).imm$ieeeFloat$0(
)).read$isSome$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)((base.Str$0)((base.Float$0)base.Float$0Instance.instance(0.0d).imm$$slash$1(
base.Float$0Instance.instance(0.0d))).imm$ieeeStr$0(
)).imm$ieeeFloat$0(
)).imm$match$1(
new base.OptMatch$2(){
    public Object mut$some$1(Object p0){
    var f$= (base.Float$0)p0;
    return f$.imm$isNaN$0(
);
  }

    public Object mut$empty$0(){
    return base.False$0.instance;
  }

})).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.Str$0Instance.instance("NaN").imm$floatExact$0(
)).read$isEmpty$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.Str$0Instance.instance("Infinity").imm$floatExact$0(
)).read$isEmpty$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Opt$1)base.Str$0Instance.instance("-Infinity").imm$floatExact$0(
)).read$isEmpty$0(
)).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Num$0)((base.Opt$1)((base.Float$0)((base.Opt$1)base.Str$0Instance.instance("+0.1").imm$ieeeFloat$0(
)).read$$bang$0(
)).imm$numExact$0(
)).read$$bang$0(
)).read$inRange$2(
((base.Opt$1)base.Str$0Instance.instance("0.100000000000000005/1").imm$numExact$0(
)).read$$bang$0(
),
base.Int$0Instance.instance(2L).imm$$slash$1(
base.Nat$0Instance.instance(1L)))).read$assertEq$1(
base.True$0.instance))).imm$test$1(
((base.Bool$0)((base.Num$0)((base.Opt$1)((base.Float$0)((base.Opt$1)base.Str$0Instance.instance("+0.1").imm$ieeeFloat$0(
)).read$$bang$0(
)).imm$numExact$0(
)).read$$bang$0(
)).read$inRange$2(
((base.Opt$1)base.Str$0Instance.instance("+0.1/1").imm$numExact$0(
)).read$$bang$0(
),
((base.Opt$1)base.Str$0Instance.instance("+0.100000000000000005/1").imm$numExact$0(
)).read$$bang$0(
))).read$assertEq$1(
base.False$0.instance));
  }
  StrParseIeeeFloat$0 instance= new StrParseIeeeFloat$0(){};}