package base;

public final class Main{
  static{ base.Util.installParentLifeline(); }
  public static void main(String[] args){
    base.Util.topLevel(()->AllTest$0.instance.imm$main$1(new base._System$0()));
    base.Util.topLevel(()->ExtensibleTest$0.instance.imm$main$1(new base._System$0()));
    base.Util.topLevel(()->TankTest$0.instance.imm$main$1(new base._System$0()));
    base.Util.topLevel(()->TestLog$0.instance.imm$main$1(new base._System$0()));
    base.Util.topLevel(()->TestThrowing$0.instance.imm$main$1(new base._System$0()));
    base.Util.topLevel(()->TestThrowingExample$0.instance.imm$main$1(new base._System$0()));
  }
}
