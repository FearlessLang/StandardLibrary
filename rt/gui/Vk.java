package _base;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_FLOAT;
import static java.lang.foreign.ValueLayout.JAVA_INT;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.github.humbleui.skija.Bitmap;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.SurfaceOrigin;

final class Vk{
  static final int version= 1 << 22 | 1 << 12;
  private static final Arena global= Arena.global();
  private static final String loader= System.getProperty("os.name").toLowerCase(Locale.ROOT).startsWith("windows") ? "vulkan-1.dll" : "libvulkan.so.1";
  private static final SymbolLookup lib= lib();
  private static String device;
  private static int maxSide;
  private static final DirectContext context= context();

  private static SymbolLookup lib(){
    try{ return SymbolLookup.libraryLookup(loader, global); }
    catch (IllegalArgumentException e){ throw fail("this machine has no Vulkan loader: " + loader + " is not found. The Vulkan loader comes with the graphics driver"); }
  }
  private static Error fail(String what){ return new Error("The Fearless GUI draws with Vulkan, and " + what); }
  private static Object call(String name, FunctionDescriptor d, Object... args){
    try{ return Linker.nativeLinker().downcallHandle(lib.findOrThrow(name), d).invokeWithArguments(args); }
    catch (Throwable t){ throw new Error(t); }
  }
  private static void run(String name, MemoryLayout[] layouts, Object... args){ call(name, FunctionDescriptor.ofVoid(layouts), args); }
  private static int result(String name, MemoryLayout[] layouts, Object... args){ return (int) call(name, FunctionDescriptor.of(JAVA_INT, layouts), args); }
  private static void ok(String name, MemoryLayout[] layouts, Object... args){
    int res= result(name, layouts, args);
    if (res < 0){ throw fail(name + " failed with " + named(res) + ". " + layers()); }
  }
  private static String named(int res){
    return switch (res){
      case 0 -> "VK_SUCCESS";
      case 5 -> "VK_INCOMPLETE";
      case -1 -> "VK_ERROR_OUT_OF_HOST_MEMORY";
      case -2 -> "VK_ERROR_OUT_OF_DEVICE_MEMORY";
      case -3 -> "VK_ERROR_INITIALIZATION_FAILED";
      case -4 -> "VK_ERROR_DEVICE_LOST";
      case -6 -> "VK_ERROR_LAYER_NOT_PRESENT";
      case -7 -> "VK_ERROR_EXTENSION_NOT_PRESENT";
      case -8 -> "VK_ERROR_FEATURE_NOT_PRESENT";
      case -9 -> "VK_ERROR_INCOMPATIBLE_DRIVER";
      default -> "VkResult " + res;
    };
  }
  private static MemoryLayout[] addresses(int n){ return IntStream.range(0, n).mapToObj(_ -> ADDRESS).toArray(MemoryLayout[]::new); }
  private static MemorySegment struct(long size, int sType){
    var res= global.allocate(size, 8);
    res.set(JAVA_INT, 0, sType);
    return res;
  }
  private static String layers(){
    var n= global.allocate(JAVA_INT);
    result("vkEnumerateInstanceLayerProperties", addresses(2), n, MemorySegment.NULL);
    var props= global.allocate(520L * n.get(JAVA_INT, 0), 4);
    result("vkEnumerateInstanceLayerProperties", addresses(2), n, props);
    if (n.get(JAVA_INT, 0) == 0){ return "This machine has no Vulkan layer."; }
    return IntStream.range(0, n.get(JAVA_INT, 0)).mapToObj(i -> props.getString(520L * i) + " (" + props.getString(520L * i + 264) + ")")
      .collect(Collectors.joining(", ", "The Vulkan layers of this machine are ", ". A layer can hide or break the devices; vulkaninfo --summary shows what Vulkan finds on this machine."));
  }
  private static DirectContext context(){
    var app= struct(48, 0);
    app.set(JAVA_INT, 44, version);
    var info= struct(64, 1);
    info.set(ADDRESS, 24, app);
    var instance= global.allocate(ADDRESS);
    ok("vkCreateInstance", addresses(3), info, MemorySegment.NULL, instance);
    var vi= instance.get(ADDRESS, 0);
    var n= global.allocate(JAVA_INT);
    int found= result("vkEnumeratePhysicalDevices", addresses(3), vi, n, MemorySegment.NULL);
    if (found < 0 || n.get(JAVA_INT, 0) == 0){ throw fail("vkEnumeratePhysicalDevices found no Vulkan device, with " + named(found) + ". " + layers()); }
    var all= global.allocate(ADDRESS, n.get(JAVA_INT, 0));
    ok("vkEnumeratePhysicalDevices", addresses(3), vi, n, all);
    var pds= IntStream.range(0, n.get(JAVA_INT, 0)).mapToObj(i -> all.getAtIndex(ADDRESS, i)).toList();
    var pd= pds.stream().filter(Vk::usable).min(Comparator.comparingInt(Vk::rank))
      .orElseThrow(() -> fail("no Vulkan device of this machine has Vulkan 1.1 and a graphics queue: " + describe(pds) + ". " + layers()));
    device = describe(List.of(pd));
    maxSide = properties(pd).get(JAVA_INT, 300);
    int family= graphicsFamily(pd);
    var priority= global.allocate(JAVA_FLOAT);
    priority.set(JAVA_FLOAT, 0, 1);
    var queueInfo= struct(40, 2);
    queueInfo.set(JAVA_INT, 20, family);
    queueInfo.set(JAVA_INT, 24, 1);
    queueInfo.set(ADDRESS, 32, priority);
    var deviceInfo= struct(72, 3);
    deviceInfo.set(JAVA_INT, 20, 1);
    deviceInfo.set(ADDRESS, 24, queueInfo);
    var vd= global.allocate(ADDRESS);
    ok("vkCreateDevice", addresses(4), pd, deviceInfo, MemorySegment.NULL, vd);
    var queue= global.allocate(ADDRESS);
    run("vkGetDeviceQueue", new MemoryLayout[]{ ADDRESS, JAVA_INT, JAVA_INT, ADDRESS }, vd.get(ADDRESS, 0), family, 0, queue);
    long res= DirectContext._nMakeVulkan(vi.address(), pd.address(), vd.get(ADDRESS, 0).address(), queue.get(ADDRESS, 0).address(), family,
      lib.findOrThrow("vkGetInstanceProcAddr").address(), lib.findOrThrow("vkGetDeviceProcAddr").address(), version);
    if (res == 0){ throw fail("Skia cannot draw on " + device); }
    return new DirectContext(res);
  }
  private static MemorySegment properties(MemorySegment pd){
    var res= global.allocate(1024, 8);
    run("vkGetPhysicalDeviceProperties", addresses(2), pd, res);
    return res;
  }
  private static String describe(List<MemorySegment> pds){
    return pds.stream().map(Vk::properties).map(Vk::describe).collect(Collectors.joining(", "));
  }
  private static String describe(MemorySegment p){
    int v= p.get(JAVA_INT, 0);
    var type= List.of("other device", "integrated GPU", "discrete GPU", "virtual GPU", "CPU").get(p.get(JAVA_INT, 16));
    return "\"" + p.getString(20) + "\" (" + type + ", Vulkan " + (v >>> 22) + "." + (v >>> 12 & 0x3FF) + "." + (v & 0xFFF) + ")";
  }
  private static boolean usable(MemorySegment pd){ return Integer.compareUnsigned(properties(pd).get(JAVA_INT, 0), version) >= 0 && graphicsFamily(pd) >= 0; }
  private static int rank(MemorySegment pd){ return List.of(2, 1, 3).indexOf(properties(pd).get(JAVA_INT, 16)) & 3; }
  private static int graphicsFamily(MemorySegment pd){
    var n= global.allocate(JAVA_INT);
    run("vkGetPhysicalDeviceQueueFamilyProperties", addresses(3), pd, n, MemorySegment.NULL);
    var props= global.allocate(24L * n.get(JAVA_INT, 0), 8);
    run("vkGetPhysicalDeviceQueueFamilyProperties", addresses(3), pd, n, props);
    return IntStream.range(0, n.get(JAVA_INT, 0)).filter(i -> (props.get(JAVA_INT, 24L * i) & 1) != 0).findFirst().orElse(-1);
  }
  static Surface surface(ImageInfo info){
    if (info.getWidth() > maxSide || info.getHeight() > maxSide){ throw fail("a window of " + info.getWidth() + " x " + info.getHeight() + " device pixels is larger than " + device + " allows: at most " + maxSide + " on each side"); }
    return Surface.makeRenderTarget(context, false, info, 0, SurfaceOrigin.TOP_LEFT, null, false);
  }
  static void read(Surface s, Bitmap b){
    if (!s.readPixels(b, 0, 0)){ throw fail("reading a frame back from " + device + " failed"); }
  }
}
