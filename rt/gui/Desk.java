package _base;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.LongStream;

@SuppressWarnings("restricted")
final class Desk{
  static final boolean windows= System.getProperty("os.name").startsWith("Windows");
  static final boolean mac= System.getProperty("os.name").startsWith("Mac");
  static final boolean managed= System.getenv("SWAYSOCK") != null
    || Stream.of(Objects.requireNonNullElse(System.getenv("XDG_CURRENT_DESKTOP"), "").split(":")).map(d -> d.toLowerCase(Locale.ROOT)).anyMatch(List.of("hyprland", "sway", "i3")::contains);
  static final boolean cosmic= Stream.of(Objects.requireNonNullElse(System.getenv("XDG_CURRENT_DESKTOP"), "").split(":")).anyMatch("COSMIC"::equalsIgnoreCase);
  static final boolean resizeUnseen= cosmic || System.getenv("WAYLAND_DISPLAY") != null && Stream.of(Objects.requireNonNullElse(System.getenv("XDG_CURRENT_DESKTOP"), "").split(":")).anyMatch("KDE"::equalsIgnoreCase);
  static Rectangle cosmicArea= cosmicGuess();
  private static Rectangle cosmicGuess(){
    var s= java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds();
    return new Rectangle(s.x, s.y+s.height/30, s.width, s.height-s.height/30-s.height/14);
  }
  static Rectangle bounds(Frame f){
    try (var a= Arena.ofConfined()){
      var g= a.allocate(48);
      call(X11.geometry, X11.display, X11.window(f), g, g.asSlice(8), g.asSlice(12), g.asSlice(16), g.asSlice(20), g.asSlice(24), g.asSlice(28));
      var t= a.allocate(24);
      call(X11.translate, X11.display, X11.window(f), X11.root, 0, 0, t, t.asSlice(4), t.asSlice(8));
      return new Rectangle(t.get(ValueLayout.JAVA_INT, 0), t.get(ValueLayout.JAVA_INT, 4), g.get(ValueLayout.JAVA_INT, 16), g.get(ValueLayout.JAVA_INT, 20));
    }
  }
  static void resize(Frame f, java.awt.Dimension d){
    call(X11.resize, X11.display, X11.window(f), d.width, d.height);
    call(X11.flush, X11.display);
  }
  static void moveResize(Frame f, java.awt.Point at, int direction){ message(f, "_NET_WM_MOVERESIZE", at.x, at.y, direction, 1, 1); }
  static final Field peer= open(reflect(() -> Component.class.getDeclaredField("peer")));

  static boolean down(){
    if (windows){ return IntStream.of(1, 2, 4).anyMatch(vk -> (short)call(Win.state, vk) < 0); }
    if (mac){ return IntStream.of(0, 1, 2).anyMatch(b -> (boolean)call(Mac.state, 0, b)); }
    var o= X11.out;
    call(X11.query, X11.display, X11.root, o, o.asSlice(8), o.asSlice(16), o.asSlice(20), o.asSlice(24), o.asSlice(28), o.asSlice(32));
    return (o.get(ValueLayout.JAVA_INT, 32) & 0x700) != 0;
  }
  static boolean maximized(Frame f){
    var s= LongStream.of(X11.atoms(X11.window(f), "_NET_WM_STATE")).boxed().toList();
    return s.contains(X11.atom("_NET_WM_STATE_MAXIMIZED_VERT")) && s.contains(X11.atom("_NET_WM_STATE_MAXIMIZED_HORZ"));
  }
  static boolean fullScreen(Frame f){
    long fs= X11.atom("_NET_WM_STATE_FULLSCREEN");
    return LongStream.of(X11.atoms(X11.window(f), "_NET_WM_STATE")).anyMatch(a -> a == fs);
  }
  static boolean managing(Frame f){ return X11.atoms(X11.window(f), "WM_STATE").length > 0; }
  static void fullScreen(Frame f, boolean on){ message(f, "_NET_WM_STATE", on ? 1 : 0, X11.atom("_NET_WM_STATE_FULLSCREEN"), 0, 1); }
  static void activate(Frame f){ message(f, "_NET_ACTIVE_WINDOW", 2, 0, 0, 0); }
  private static void message(Frame f, String type, long... data){
    var lock= reflect(() -> Class.forName("sun.awt.SunToolkit"));
    reflect(() -> lock.getMethod("awtLock").invoke(null));
    try (var a= Arena.ofConfined()){
      var d= MemorySegment.ofAddress((long)reflect(() -> Class.forName("sun.awt.X11.XToolkit").getMethod("getDisplay").invoke(null)));
      call(X11.ungrab, d, 0L);
      var e= a.allocate(192);
      e.set(ValueLayout.JAVA_INT, 0, 33);
      e.set(ValueLayout.JAVA_LONG, 32, X11.window(f));
      e.set(ValueLayout.JAVA_LONG, 40, X11.atom(type));
      e.set(ValueLayout.JAVA_INT, 48, 32);
      for (int i= 0; i < data.length; i+= 1){ e.set(ValueLayout.JAVA_LONG, 56+8*i, data[i]); }
      call(X11.send, d, X11.root, 0, (1L<<20)|(1L<<19), e);
      call(X11.flush, d);
    }
    finally{ reflect(() -> lock.getMethod("awtUnlock").invoke(null)); }
  }
  static void restoreSize(Frame f, Rectangle r, boolean maximized){
    var gc= f.getGraphicsConfiguration();
    double k= gc.getDefaultTransform().getScaleX();
    var in= Toolkit.getDefaultToolkit().getScreenInsets(gc);
    var s= gc.getBounds();
    int x= (int)Math.round((r.x-s.x-in.left)*k);
    int y= (int)Math.round((r.y-s.y-in.top)*k);
    var p= Win.placement;
    var h= MemorySegment.ofAddress((long)reflect(() -> Win.hwnd.invoke(reflect(() -> peer.get(f)))));
    p.set(ValueLayout.JAVA_INT, 0, 44);
    call(Win.getPlacement, h, p);
    p.set(ValueLayout.JAVA_INT, 8, maximized ? 3 : 1);
    p.set(ValueLayout.JAVA_INT, 28, x);
    p.set(ValueLayout.JAVA_INT, 32, y);
    p.set(ValueLayout.JAVA_INT, 36, x+(int)Math.round(r.width*k));
    p.set(ValueLayout.JAVA_INT, 40, y+(int)Math.round(r.height*k));
    call(Win.setPlacement, h, p);
  }
  private static MethodHandle fn(String lib, String name, FunctionDescriptor d){
    return Linker.nativeLinker().downcallHandle(SymbolLookup.libraryLookup(lib, Arena.global()).find(name).orElseThrow(), d);
  }
  private static Object call(MethodHandle h, Object... args){
    try{ return h.invokeWithArguments(args); }
    catch (Throwable t){ throw new Error(t); }
  }
  private interface Reflective<T>{ T get() throws ReflectiveOperationException; }
  private static <T> T reflect(Reflective<T> r){
    try{ return r.get(); }
    catch (ReflectiveOperationException e){ throw new Error(e); }
  }
  private static <T extends AccessibleObject> T open(T o){
    o.setAccessible(true);
    return o;
  }
  private static final class Win{
    static final MethodHandle state= fn("user32", "GetAsyncKeyState", FunctionDescriptor.of(ValueLayout.JAVA_SHORT, ValueLayout.JAVA_INT));
    static final FunctionDescriptor placementCall= FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS);
    static final MethodHandle getPlacement= fn("user32", "GetWindowPlacement", placementCall);
    static final MethodHandle setPlacement= fn("user32", "SetWindowPlacement", placementCall);
    static final MemorySegment placement= Arena.global().allocate(44);
    static final Method hwnd= open(reflect(() -> Class.forName("sun.awt.windows.WComponentPeer").getMethod("getHWnd")));
  }
  private static final class Mac{
    static final MethodHandle state= fn("/System/Library/Frameworks/CoreGraphics.framework/CoreGraphics", "CGEventSourceButtonState",
      FunctionDescriptor.of(ValueLayout.JAVA_BOOLEAN, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
  }
  private static final class X11{
    static final String lib= "libX11.so.6";
    static final MemorySegment display= (MemorySegment)call(fn(lib, "XOpenDisplay", FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)), MemorySegment.NULL);
    static final long root= (long)call(fn(lib, "XDefaultRootWindow", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)), display);
    static final MethodHandle query= fn(lib, "XQueryPointer", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG,
      ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS));
    static final MemorySegment out= Arena.global().allocate(36);
    static final MethodHandle internAtom= fn(lib, "XInternAtom", FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT));
    static final MethodHandle property= fn(lib, "XGetWindowProperty", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG,
      ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS));
    static final MethodHandle free= fn(lib, "XFree", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS));
    static final MethodHandle send= fn(lib, "XSendEvent", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));
    static final MethodHandle geometry= fn(lib, "XGetGeometry", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG,
      ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS));
    static final MethodHandle translate= fn(lib, "XTranslateCoordinates", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG,
      ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS));
    static final MethodHandle resize= fn(lib, "XResizeWindow", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT));
    static final MethodHandle ungrab= fn(lib, "XUngrabPointer", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG));
    static final MethodHandle flush= fn(lib, "XFlush", FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS));
    static final Method window= reflect(() -> Class.forName("sun.awt.X11.XBaseWindow").getMethod("getWindow"));
    static long window(Frame f){ return (long)reflect(() -> window.invoke(reflect(() -> peer.get(f)))); }
    static long atom(String name){
      try (var a= Arena.ofConfined()){ return (long)call(internAtom, display, a.allocateFrom(name), 0); }
    }
    static long[] atoms(long w, String name){
      try (var a= Arena.ofConfined()){
        var n= a.allocate(8);
        var data= a.allocate(8);
        call(property, display, w, atom(name), 0L, 1024L, 0, 0L, a.allocate(8), a.allocate(4), n, a.allocate(8), data);
        var p= data.get(ValueLayout.ADDRESS, 0);
        var r= p.reinterpret(n.get(ValueLayout.JAVA_LONG, 0)*8).toArray(ValueLayout.JAVA_LONG);
        call(free, p);
        return r;
      }
    }
  }
}
