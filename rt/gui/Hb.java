package _base;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_CHAR;
import static java.lang.foreign.ValueLayout.JAVA_FLOAT;
import static java.lang.foreign.ValueLayout.JAVA_INT;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.github.humbleui.skija.PathBuilder;

final class Hb{
  record Glyph(int glyph, int cluster, int advance, int dx, int dy){}
  record Layer(io.github.humbleui.skija.Path path, boolean foreground, int color){}
  static final int common= tag("Zyyy");
  static final int inherited= tag("Zinh");
  static final int unknown= tag("Zzzz");
  private static final Arena global= Arena.global();
  private static final Linker linker= Linker.nativeLinker();
  private static final SymbolLookup lib= SymbolLookup.libraryLookup(library(), global);
  private static final MethodHandle blobCreate= fn("hb_blob_create", ADDRESS, ADDRESS, JAVA_INT, JAVA_INT, ADDRESS, ADDRESS);
  private static final MethodHandle faceCreate= fn("hb_face_create", ADDRESS, ADDRESS, JAVA_INT);
  private static final MethodHandle upem= fn("hb_face_get_upem", JAVA_INT, ADDRESS);
  private static final MethodHandle getFace= fn("hb_font_get_face", ADDRESS, ADDRESS);
  private static final MethodHandle fontCreate= fn("hb_font_create", ADDRESS, ADDRESS);
  private static final MethodHandle otFuncs= fn("hb_ot_font_set_funcs", null, ADDRESS);
  private static final MethodHandle nominal= fn("hb_font_get_nominal_glyph", JAVA_INT, ADDRESS, JAVA_INT, ADDRESS);
  private static final MethodHandle unicodeScript= fn("hb_unicode_script", JAVA_INT, ADDRESS, JAVA_INT);
  private static final MethodHandle bufferCreate= fn("hb_buffer_create", ADDRESS);
  private static final MethodHandle bufferDestroy= fn("hb_buffer_destroy", null, ADDRESS);
  private static final MethodHandle addUtf16= fn("hb_buffer_add_utf16", null, ADDRESS, ADDRESS, JAVA_INT, JAVA_INT, JAVA_INT);
  private static final MethodHandle setDirection= fn("hb_buffer_set_direction", null, ADDRESS, JAVA_INT);
  private static final MethodHandle setScript= fn("hb_buffer_set_script", null, ADDRESS, JAVA_INT);
  private static final MethodHandle setLanguage= fn("hb_buffer_set_language", null, ADDRESS, ADDRESS);
  private static final MethodHandle shapeFull= fn("hb_shape_full", JAVA_INT, ADDRESS, ADDRESS, ADDRESS, JAVA_INT, ADDRESS);
  private static final MethodHandle infos= fn("hb_buffer_get_glyph_infos", ADDRESS, ADDRESS, ADDRESS);
  private static final MethodHandle positions= fn("hb_buffer_get_glyph_positions", ADDRESS, ADDRESS, ADDRESS);
  private static final MethodHandle drawGlyph= fn("hb_font_draw_glyph", null, ADDRESS, JAVA_INT, ADDRESS, ADDRESS);
  private static final MethodHandle layers= fn("hb_ot_color_glyph_get_layers", JAVA_INT, ADDRESS, JAVA_INT, JAVA_INT, ADDRESS, ADDRESS);
  private static final MethodHandle colors= fn("hb_ot_color_palette_get_colors", JAVA_INT, ADDRESS, JAVA_INT, JAVA_INT, ADDRESS, ADDRESS);
  private static final MemorySegment unicode= (MemorySegment) call(fn("hb_unicode_funcs_get_default", ADDRESS));
  private static final MemorySegment english= (MemorySegment) call(fn("hb_language_from_string", ADDRESS, ADDRESS, JAVA_INT), global.allocateFrom("en"), -1);
  private static final MemorySegment shapers= shapers();
  private static final MemorySegment draw= draw();
  private static PathBuilder path;

  private static Path library(){
    var os= System.getProperty("os.name").toLowerCase(Locale.ROOT);
    var windows= os.startsWith("windows");
    var mac= os.startsWith("mac");
    var dir= (windows ? "windows" : mac ? "macos" : "linux") + "/" + (System.getProperty("os.arch").equals("aarch64") ? "arm64" : "x64");
    var name= windows ? "harfbuzz.dll" : mac ? "libharfbuzz.dylib" : "libharfbuzz.so";
    var res= Path.of(System.getProperty("java.io.tmpdir"), "fearless_harfbuzz_14_3_0_" + dir.replace('/', '_'), name);
    try (var in= Hb.class.getResourceAsStream("/harfbuzz/" + dir + "/" + name)){
      var bytes= in.readAllBytes();
      if (same(res, bytes)){ return res; }
      Files.createDirectories(res.getParent());
      var tmp= Files.createTempFile(res.getParent(), "harfbuzz", ".tmp");
      Files.write(tmp, bytes);
      try{ Files.move(tmp, res, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE); }
      catch (IOException e){ Files.delete(tmp); if (!same(res, bytes)){ throw e; } }
      return res;
    }
    catch (IOException e){ throw new UncheckedIOException(e); }
  }
  private static boolean same(Path p, byte[] bytes) throws IOException{ return Files.exists(p) && Arrays.equals(Files.readAllBytes(p), bytes); }
  private static MethodHandle fn(String name, MemoryLayout res, MemoryLayout... args){
    return linker.downcallHandle(lib.findOrThrow(name), res == null ? FunctionDescriptor.ofVoid(args) : FunctionDescriptor.of(res, args));
  }
  private static Object call(MethodHandle h, Object... args){
    try{ return h.invokeWithArguments(args); }
    catch (Throwable t){ throw new Error(t); }
  }
  static int tag(String s){ return s.charAt(0) << 24 | s.charAt(1) << 16 | s.charAt(2) << 8 | s.charAt(3); }
  private static MemorySegment shapers(){
    var res= global.allocate(ADDRESS, 2);
    res.setAtIndex(ADDRESS, 0, global.allocateFrom("ot"));
    return res;
  }
  private static MemorySegment draw(){
    var res= (MemorySegment) call(fn("hb_draw_funcs_create", ADDRESS));
    on(res, "move_to", "moveTo", JAVA_FLOAT, JAVA_FLOAT);
    on(res, "line_to", "lineTo", JAVA_FLOAT, JAVA_FLOAT);
    on(res, "quadratic_to", "quadTo", JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT);
    on(res, "cubic_to", "cubicTo", JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT, JAVA_FLOAT);
    on(res, "close_path", "close");
    return res;
  }
  private static void on(MemorySegment funcs, String what, String method, MemoryLayout... points){
    var args= new ArrayList<MemoryLayout>(List.of(ADDRESS, ADDRESS, ADDRESS));
    args.addAll(List.of(points));
    args.add(ADDRESS);
    var d= FunctionDescriptor.ofVoid(args.toArray(MemoryLayout[]::new));
    try{
      var h= MethodHandles.lookup().findStatic(Hb.class, method, d.toMethodType());
      call(fn("hb_draw_funcs_set_" + what + "_func", null, ADDRESS, ADDRESS, ADDRESS, ADDRESS), funcs, linker.upcallStub(h, d, global), MemorySegment.NULL, MemorySegment.NULL);
    }
    catch (ReflectiveOperationException e){ throw new Error(e); }
  }
  static void moveTo(MemorySegment f, MemorySegment d, MemorySegment s, float x, float y, MemorySegment u){ path.moveTo(x, -y); }
  static void lineTo(MemorySegment f, MemorySegment d, MemorySegment s, float x, float y, MemorySegment u){ path.lineTo(x, -y); }
  static void quadTo(MemorySegment f, MemorySegment d, MemorySegment s, float cx, float cy, float x, float y, MemorySegment u){ path.quadTo(cx, -cy, x, -y); }
  static void cubicTo(MemorySegment f, MemorySegment d, MemorySegment s, float c1x, float c1y, float c2x, float c2y, float x, float y, MemorySegment u){ path.cubicTo(c1x, -c1y, c2x, -c2y, x, -y); }
  static void close(MemorySegment f, MemorySegment d, MemorySegment s, MemorySegment u){ path.closePath(); }

  static MemorySegment font(byte[] data){
    var bytes= global.allocate(data.length);
    bytes.copyFrom(MemorySegment.ofArray(data));
    var face= call(faceCreate, call(blobCreate, bytes, data.length, 1, MemorySegment.NULL, MemorySegment.NULL), 0);
    var res= (MemorySegment) call(fontCreate, face);
    call(otFuncs, res);
    return res;
  }
  static int upem(MemorySegment font){ return (int) call(upem, call(getFace, font)); }
  static boolean has(MemorySegment font, int cp){
    try (var a= Arena.ofConfined()){ return (int) call(nominal, font, cp, a.allocate(JAVA_INT)) != 0; }
  }
  static int script(int cp){ return (int) call(unicodeScript, unicode, cp); }
  static List<Glyph> shape(MemorySegment font, String text, int from, int to, boolean rtl, int script){
    var buf= (MemorySegment) call(bufferCreate);
    try (var a= Arena.ofConfined()){
      call(addUtf16, buf, a.allocateFrom(JAVA_CHAR, text.toCharArray()), text.length(), from, to - from);
      call(setDirection, buf, rtl ? 5 : 4);
      call(setScript, buf, script);
      call(setLanguage, buf, english);
      call(shapeFull, font, buf, MemorySegment.NULL, 0, shapers);
      var n= a.allocate(JAVA_INT);
      var is= ((MemorySegment) call(infos, buf, n)).reinterpret(20L * n.get(JAVA_INT, 0));
      var ps= ((MemorySegment) call(positions, buf, n)).reinterpret(20L * n.get(JAVA_INT, 0));
      var res= new ArrayList<Glyph>();
      for (long i= 0; i < is.byteSize(); i += 20){
        res.add(new Glyph(is.get(JAVA_INT, i), is.get(JAVA_INT, i + 8), ps.get(JAVA_INT, i), ps.get(JAVA_INT, i + 8), ps.get(JAVA_INT, i + 12)));
      }
      return res;
    }
    finally{ call(bufferDestroy, buf); }
  }
  private static io.github.humbleui.skija.Path outline(MemorySegment font, int glyph){
    path = new PathBuilder();
    call(drawGlyph, font, glyph, draw, MemorySegment.NULL);
    var res= path.build();
    path = null;
    return res;
  }
  static List<Layer> layers(MemorySegment font, int glyph){
    var face= call(getFace, font);
    try (var a= Arena.ofConfined()){
      var n= a.allocate(JAVA_INT);
      int count= (int) call(layers, face, glyph, 0, MemorySegment.NULL, MemorySegment.NULL);
      if (count == 0){ return List.of(new Layer(outline(font, glyph), true, 0)); }
      var ls= a.allocate(8L * count);
      n.set(JAVA_INT, 0, count);
      call(layers, face, glyph, 0, n, ls);
      int total= (int) call(colors, face, 0, 0, MemorySegment.NULL, MemorySegment.NULL);
      var cs= a.allocate(4L * total);
      n.set(JAVA_INT, 0, total);
      call(colors, face, 0, 0, n, cs);
      var res= new ArrayList<Layer>();
      for (int i= 0; i < count; i += 1){
        int index= ls.get(JAVA_INT, 8L * i + 4);
        res.add(new Layer(outline(font, ls.get(JAVA_INT, 8L * i)), index == 0xFFFF, index == 0xFFFF ? 0 : argb(cs.get(JAVA_INT, 4L * index))));
      }
      return res;
    }
  }
  private static int argb(int bgra){ return bgra << 24 | (bgra >>> 8 & 0xFF) << 16 | (bgra >>> 16 & 0xFF) << 8 | bgra >>> 24; }
}
