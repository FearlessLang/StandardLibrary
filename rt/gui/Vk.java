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
import java.util.Locale;
import java.util.stream.IntStream;

import io.github.humbleui.skija.DirectContext;

final class Vk{
  static final int version= 1 << 22 | 1 << 12;
  private static final Arena global= Arena.global();
  private static final SymbolLookup lib= SymbolLookup.libraryLookup(System.getProperty("os.name").toLowerCase(Locale.ROOT).startsWith("windows") ? "vulkan-1" : "libvulkan.so.1", global);
  static final DirectContext context= context();

  private static Object call(String name, FunctionDescriptor d, Object... args){
    try{ return Linker.nativeLinker().downcallHandle(lib.findOrThrow(name), d).invokeWithArguments(args); }
    catch (Throwable t){ throw new Error(t); }
  }
  private static void run(String name, MemoryLayout[] layouts, Object... args){ call(name, FunctionDescriptor.ofVoid(layouts), args); }
  private static void ok(String name, MemoryLayout[] layouts, Object... args){
    int res= (int) call(name, FunctionDescriptor.of(JAVA_INT, layouts), args);
    if (res < 0){ throw new Error("The Fearless GUI draws with Vulkan, and " + name + " failed with VkResult " + res); }
  }
  private static MemoryLayout[] addresses(int n){ return IntStream.range(0, n).mapToObj(_ -> ADDRESS).toArray(MemoryLayout[]::new); }
  private static MemorySegment struct(long size, int sType){
    var res= global.allocate(size, 8);
    res.set(JAVA_INT, 0, sType);
    return res;
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
    ok("vkEnumeratePhysicalDevices", addresses(3), vi, n, MemorySegment.NULL);
    if (n.get(JAVA_INT, 0) == 0){ throw new Error("The Fearless GUI draws with Vulkan, and this machine has no Vulkan device"); }
    var all= global.allocate(ADDRESS, n.get(JAVA_INT, 0));
    ok("vkEnumeratePhysicalDevices", addresses(3), vi, n, all);
    var pd= IntStream.range(0, n.get(JAVA_INT, 0)).mapToObj(i -> all.getAtIndex(ADDRESS, i)).min(Comparator.comparingInt(Vk::rank)).orElseThrow();
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
    var device= global.allocate(ADDRESS);
    ok("vkCreateDevice", addresses(4), pd, deviceInfo, MemorySegment.NULL, device);
    var vd= device.get(ADDRESS, 0);
    var queue= global.allocate(ADDRESS);
    run("vkGetDeviceQueue", new MemoryLayout[]{ ADDRESS, JAVA_INT, JAVA_INT, ADDRESS }, vd, family, 0, queue);
    return DirectContext.makeVulkan(vi.address(), pd.address(), vd.address(), queue.get(ADDRESS, 0).address(), family,
      lib.findOrThrow("vkGetInstanceProcAddr").address(), lib.findOrThrow("vkGetDeviceProcAddr").address(), version);
  }
  private static int rank(MemorySegment pd){
    var props= global.allocate(1024, 8);
    run("vkGetPhysicalDeviceProperties", addresses(2), pd, props);
    return switch (props.get(JAVA_INT, 16)){ case 2 -> 0; case 1 -> 1; case 3 -> 2; default -> 3; };
  }
  private static int graphicsFamily(MemorySegment pd){
    var n= global.allocate(JAVA_INT);
    run("vkGetPhysicalDeviceQueueFamilyProperties", addresses(3), pd, n, MemorySegment.NULL);
    var props= global.allocate(24L * n.get(JAVA_INT, 0), 8);
    run("vkGetPhysicalDeviceQueueFamilyProperties", addresses(3), pd, n, props);
    return IntStream.range(0, n.get(JAVA_INT, 0)).filter(i -> (props.get(JAVA_INT, 24L * i) & 1) != 0).findFirst().orElseThrow();
  }
}
