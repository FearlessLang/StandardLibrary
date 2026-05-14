package base;
public interface ExtensibleTest$0 extends base.Main$0{
  default Object imm$main$1(Object p0){
    var this$= this;
    var s$= (base.System$0)p0;
    return ((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)((base.Tests$0)base.Tests$0.instance.imm$testSuite$1(
base.ExtensibleBasics$0.instance)).imm$testSuite$1(
base.ExtensibleCloseSemantics$0.instance)).imm$testSuite$1(
base.ExtensibleReenterFlow$0.instance)).imm$testSuite$1(
base.ExtensibleExtensionMethods$0.instance)).imm$testSuite$1(
base.ExtensibleViaDataTypeBy$0.instance)).imm$done$0(
);
  }
  ExtensibleTest$0 instance= new ExtensibleTest$0(){};}