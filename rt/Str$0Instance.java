package base;

public record Str$0Instance(String val) implements Str$0{
  public static Str$0 instance(String val){ return new Str$0Instance(val); }
  @Override public Object imm$$lt_eq_gt$1(Object p0){
    return Util.ord(val.compareTo(((Str$0Instance)p0).val));
  }
  @Override public Object read$imm$0(){ return this; }
  @Override public Object read$info$0(){ throw new Error("InfoStillTODO"); }
  @Override public Object read$str$0(){ return this; }

  @Override public Object imm$$plus$1(Object p0){ return instance(val+((Str$0Instance)p0).val); }
  @Override public Object imm$$or$1(Object p0){ return instance(val+"\n"+((Str$0Instance)p0).val); }
  @Override public Object imm$$xor$1(Object p0){ return instance(val+"`"+((Str$0Instance)p0).val); }
  @Override public Object imm$$xor_xor$1(Object p0){ return instance(val+"\""+((Str$0Instance)p0).val); }

  @Override public Object imm$$or$0(){ return instance(val+"\n"); }
  @Override public Object imm$$xor$0(){ return instance(val+"`"); }
  @Override public Object imm$$xor_xor$0(){ return instance(val+"\""); }
}