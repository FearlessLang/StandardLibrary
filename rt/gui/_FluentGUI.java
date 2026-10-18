package _base;

import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

public final class _FluentGUI implements Gui$c$0{
  static final LinkedHashMap<Object,String> preloaded= new LinkedHashMap<>(Map.of(IconsFearless$9kw$0.instance, "base.IconsFearless"));
  String name= "Fearless App";
  Object icon;
  double screenArea= 0.3;
  double minArea= 0.02;
  boolean fearlessCorner;
  boolean fullScreen;
  int opacityFloor= 255;
  String closeKey= "ESCAPE";
  String fullScreenKey= "F11";
  BufferedImage awtIcon;
  String iconName;
  private boolean open;

  private _FluentGUI set(Runnable r){
    if (open){ throw Util.detErr("This Gui has an open window, so its settings can not change: the settings of a Gui are fixed for the life of its window. Change them before Gui.run, or on a new Gui from s.gui"); }
    r.run();
    return this;
  }
  private static String key(Object k){ return ((Str$c$0Instance)((KeyStroke$m8$0) k).read$get$0()).val(); }
  static int code(String key){ return _KeyNames$b4$0.codes.get(key); }
  private static double area(Object f, String what){
    double v= ((Float$1c$0Instance) f).val();
    if (v > 0 && v <= 1){ return v; }
    throw Util.detErr("Gui."+what+"("+v+") is out of range: it is a fraction of the screen area, so it must be more than 0.0 and at most 1.0");
  }
  @Override public Object read$screenWidth$0(){ return Scopes.w(screen().width); }
  @Override public Object read$screenHeight$0(){ return Scopes.h(screen().height); }
  private static java.awt.Rectangle screen(){ return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds(); }
  @Override public Object mut$uName$1(Object u){
    var s= Scopes.ustr(u);
    if (s.isBlank()){ throw Util.detErr("The name \""+s+"\" is blank: the name of a Gui is what the title bar, the taskbar and the task switcher show, so it needs a visible character"); }
    return set(() -> name = s);
  }
  @Override public Object mut$icon$1(Object i){
    if (preloaded.containsKey(i)){ return set(() -> icon = i); }
    var path= AssetBytesRead.str(((ImageFile$lk$0) i).imm$path$0());
    throw Util.detErr("Gui.icon is given the image \""+path+"\", but the main running this Gui preloads only the icons "+String.join(", ", preloaded.values())+".\n"
      +"A window shows only an icon its main preloads: the I of each Shortcut[I], OpenWith[I] and PreLoadIcon[I] of the main, and base.IconsFearless.\n"
      +"Add PreLoadIcon[I] to the main, with I the icon type of \""+path+"\".");
  }
  @Override public Object mut$screenArea$1(Object f){
    double v= area(f, "screenArea");
    return set(() -> screenArea = v);
  }
  @Override public Object mut$minArea$1(Object f){
    double v= area(f, "minArea");
    return set(() -> minArea = v);
  }
  @Override public Object mut$fearlessCorner$0(){ return set(() -> fearlessCorner = true); }
  @Override public Object mut$fullScreen$0(){ return set(() -> fullScreen = true); }
  @Override public Object mut$opacityFloor$1(Object a){ return set(() -> opacityFloor = Scopes.alpha(a)); }
  @Override public Object mut$closeKey$1(Object k){ return set(() -> closeKey = key(k)); }
  @Override public Object mut$fullScreenKey$1(Object k){ return set(() -> fullScreenKey = key(k)); }
  @Override public Object mut$run$1(Object o){
    var f= (Consumer$ao$1) o;
    if (SwingUtilities.isEventDispatchThread()){
      throw new IllegalStateException("FluentGUI.run must not be called from the EDT");
    }
    set(() -> {});
    if (minArea > screenArea){ throw Util.detErr("Gui.minArea("+minArea+") is more than Gui.screenArea("+screenArea+"): the picture must always cover at least minArea of the screen area, so the window can not open covering only screenArea"); }
    if (closeKey.equals(fullScreenKey)){ throw Util.detErr("The close key and the full screen key of this Gui are both "+closeKey+": one key press can not both ask to close the window and toggle full screen"); }
    var shown= icon != null ? icon : preloaded.firstEntry().getKey();
    iconName = preloaded.get(shown);
    var bs= iconBytes(shown);
    try{ awtIcon = ImageIO.read(new ByteArrayInputStream(bs)); }
    catch (IOException e){ throw new UncheckedIOException(e); }
    open = true;
    var done= new CompletableFuture<Void>();
    var fb= new _Frame(done, this);
    try{
      f.mut$accept$1(fb);
      fb.onEdtAndWait(fb::start);
    }
    catch (Throwable e){ fb.frame.abortBeforeStart(e); }// Throwable: sneaky throws exist
    try{ done.join(); }
    catch (Throwable e){ throw Util.asFearlessError(e); }
    finally{ open = false; }
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
