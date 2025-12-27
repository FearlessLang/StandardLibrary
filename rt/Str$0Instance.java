package base;

public record Str$0Instance(String val) implements Str$0{
  public static Str$0 instance(String val){ return new Str$0Instance(val); }
  @Override public Object imm$$lt_eq_gt$1(Object p0){
    return Util.ord(val.compareTo(((Str$0Instance)p0).val));
  }
  String toS(Object o){return ((Str$0Instance)((ToStr$0)o).read$str$0()).val; }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object read$info$0(){ return Info$0.instance; }
  @Override public Object read$str$0(){ return this; }

  @Override public Object imm$$plus$1(Object p0){ return instance(val+toS(p0)); }
  @Override public Object imm$$or$1(Object p0){ return instance(val+"\n"+toS(p0)); }
  @Override public Object imm$$xor$1(Object p0){ return instance(val+"`"+toS(p0)); }
  @Override public Object imm$$xor_xor$1(Object p0){ return instance(val+"\""+toS(p0)); }

  @Override public Object imm$$or$0(){ return instance(val+"\n"); }
  @Override public Object imm$$xor$0(){ return instance(val+"`"); }
  @Override public Object imm$$xor_xor$0(){ return instance(val+"\""); }
  
  @Override public Object imm$isEmpty$0(){ return bool(val.isEmpty()); }
  @Override public Object imm$escape$0(){
    var res= val.replace("`", "`^`").replace("\n", "`|`");
    return instance("`"+res+"`");
  }

}