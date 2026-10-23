package _base;

import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

public final class _FluentGUI implements Gui$c$0, Cloneable{
  static final LinkedHashMap<Object,String> preloaded= new LinkedHashMap<>(Map.of(IconsFearless$9kw$0.instance, "base.IconsFearless"));
  String name= "Fearless App";
  Object icon;
  enum Start{ window, maximised, undecorated, fullScreen }
  Start start= Start.window;
  double initialArea= 0.3;
  double minArea= 0.02;
  int opacityFloor= 255;
  String closeKey= "ESCAPE";
  String fullScreenKey= "F11";
  BufferedImage awtIcon;
  String iconName;

  private _FluentGUI set(Consumer<_FluentGUI> r){
    var res= copy();
    r.accept(res);
    return res;
  }
  private _FluentGUI copy(){
    try{ return (_FluentGUI) clone(); }
    catch (CloneNotSupportedException e){ throw new Error(e); }
  }
  private static String key(Object k){ return ((Str$c$0Instance)((KeyStroke$m8$0) k).read$get$0()).val(); }
  static int code(String key){ return _KeyNames$b4$0.codes.get(key); }
  private static double area(Object f, String what){
    double v= ((Float$1c$0Instance) f).val();
    if (v > 0 && v <= 1){ return v; }
    throw Util.detErr(what+"("+v+") is out of range: it is a fraction of the screen area, so it must be more than 0.0 and at most 1.0");
  }
  @Override public Object mut$screenWidth$0(){ return Scopes.w(screen().width); }
  @Override public Object mut$screenHeight$0(){ return Scopes.h(screen().height); }
  private static java.awt.Rectangle screen(){ return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds(); }
  @Override public Object mut$uName$1(Object u){
    var s= Scopes.ustr(u);
    var b= Scopes.lineBreak(s);
    if (b != null){ throw Util.detErr("The new name of this Gui has "+b+": the name of a Gui is what the title bar, the taskbar and the task switcher show, on one line"); }
    if (!s.codePoints().allMatch(_FluentGUI::invisible)){ return set(g -> g.name = s); }
    var shown= ((Str$c$0Instance)((UStr$s$0Instance) u).imm$escape$0()).val();
    throw Util.detErr("The name "+shown+" is blank: the name of a Gui is what the title bar, the taskbar and the task switcher show, so it needs a visible character");
  }
  private static boolean invisible(int c){
    var t= Character.getType(c);
    return Character.isSpaceChar(c) || t == Character.FORMAT || t == Character.CONTROL;
  }
  @Override public Object mut$icon$1(Object i){
    if (preloaded.containsKey(i)){ return set(g -> g.icon = i); }
    var path= AssetBytesRead.str(((ImageFile$lk$0) i).imm$path$0());
    throw Util.detErr("Gui.icon is given the image \""+path+"\", but the main running this Gui preloads only the icons "+String.join(", ", preloaded.values())+".\n"
      +"A window shows only an icon its main preloads: the I of each Shortcut[I], OpenWith[I] and PreLoadIcon[I] of the main, and base.IconsFearless.\n"
      +"Add PreLoadIcon[I] to the main, with I the icon type of \""+path+"\".");
  }
  @Override public Object mut$startAsWindow$1(Object f){ return start(f, Start.window, "Gui.startAsWindow"); }
  @Override public Object mut$startMaximised$0(){ return set(g -> g.start = Start.maximised); }
  @Override public Object mut$startUndecorated$1(Object f){ return start(f, Start.undecorated, "Gui.startUndecorated"); }
  @Override public Object mut$startFullScreen$0(){ return set(g -> g.start = Start.fullScreen); }
  private _FluentGUI start(Object f, Start s, String what){
    double v= area(f, what);
    if (v < minArea){ throw Util.detErr(what+"("+v+") is less than Gui.minArea("+minArea+") of this Gui: the picture always covers at least minArea of the screen area, so the window can not open covering only "+v+" of it. Lower Gui.minArea first"); }
    var res= set(g -> g.start = s);
    res.initialArea = v;
    return res;
  }
  @Override public Object mut$minArea$1(Object f){
    double v= area(f, "Gui.minArea");
    var sized= start == Start.window || start == Start.undecorated;
    if (sized && v > initialArea){ throw Util.detErr("Gui.minArea("+v+") is more than the initial area "+initialArea+" of this Gui: the picture always covers at least minArea of the screen area, so the window could not open covering only "+initialArea+" of it. Raise the initial area with Gui.startAsWindow or Gui.startUndecorated first"); }
    return set(g -> g.minArea = v);
  }
  @Override public Object mut$opacityFloor$1(Object a){ return set(g -> g.opacityFloor = Scopes.alpha(a)); }
  @Override public Object mut$closeKey$1(Object k){
    var v= key(k);
    if (code(v) == code(fullScreenKey)){ throw Util.detErr("Gui.closeKey("+v+") is the full screen key of this Gui: one key press can not both ask to close the window and toggle full screen. Change Gui.fullScreenKey first"); }
    return set(g -> g.closeKey = v);
  }
  @Override public Object mut$fullScreenKey$1(Object k){
    var v= key(k);
    if (code(v) == code(closeKey)){ throw Util.detErr("Gui.fullScreenKey("+v+") is the close key of this Gui: one key press can not both ask to close the window and toggle full screen. Change Gui.closeKey first"); }
    return set(g -> g.fullScreenKey = v);
  }
  @Override public Object mut$run$1(Object o){
    var f= (Consumer$ao$1) o;
    if (SwingUtilities.isEventDispatchThread()){
      throw new IllegalStateException("FluentGUI.run must not be called from the EDT");
    }
    var shown= icon != null ? icon : preloaded.firstEntry().getKey();
    var g= copy();
    g.iconName = preloaded.get(shown);
    try{ g.awtIcon = ImageIO.read(new ByteArrayInputStream(iconBytes(shown))); }
    catch (IOException e){ throw new UncheckedIOException(e); }
    var done= new CompletableFuture<Void>();
    var fb= new _Frame(done, g);
    try{
      f.mut$accept$1(fb);
      fb.onEdtAndWait(fb::start);
    }
    catch (Throwable e){ fb.frame.abortBeforeStart(e); }// Throwable: sneaky throws exist
    try{ done.join(); }
    catch (Throwable e){ throw Util.asFearlessError(e); }
    return Void$o$0.instance;
  }
  public static void preload(Object... namesAndIcons){
    preloaded.clear();
    for (int i= 0; i < namesAndIcons.length; i += 2){ preloaded.put(namesAndIcons[i+1], (String) namesAndIcons[i]); }
  }
  static byte[] iconBytes(Object i){
    var f= (ImageFile$lk$0) i;
    return AssetBytesRead.bytes(AssetBytesRead.str(f.imm$path$0()), AssetBytesRead.str(f.imm$diskPath$0()), AssetBytesRead.str(f.imm$zipSteps$0()), AssetBytesRead.str(f.imm$zipEntry$0()));
  }
}
