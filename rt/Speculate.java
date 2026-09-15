package base;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static base.Util.*;

@SuppressWarnings("serial")
final class Cancelled extends RuntimeException{ Cancelled(){ super(null, null, false, false); } }
record ErrBox(Throwable t){}

final class Speculate{
  private static final ThreadLocal<Speculate> current= new ThreadLocal<>();
  static final Object skip= new Object();
  private final Speculate parent= current.get();
  private final ArrayDeque<ForkJoinTask<List<Object>>> pending= new ArrayDeque<>();
  private volatile boolean cancelled;
  static void poll(){
    var sp= current.get();
    if (sp != null && sp.cancelled()){ throw new Cancelled(); }
  }
  private boolean cancelled(){ return cancelled || (parent != null && parent.cancelled()); }
  private void cancel(){
    cancelled= true;
    pending.forEach(t -> t.cancel(false));
  }
  static Stream<Object> stream(List<Object> src, UnaryOperator<Stream<Object>> stages){
    var sp= new Speculate();
    var it= sp.ordered(src, stages);
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED), false)
      .map(Speculate::unbox).onClose(sp::cancel);
  }
  private Iterator<Object> ordered(List<Object> src, UnaryOperator<Stream<Object>> stages){
    int n= src.size();
    int parallelism= ForkJoinPool.getCommonPoolParallelism();
    int cap= Math.max(1, n / (4 * parallelism));
    return new Iterator<Object>(){
      int next= 0;
      int size= 1;
      List<Object> buf= List.of();
      int pos= 0;
      { fill(); }
      void fill(){
        while (pending.size() < 4 * parallelism && next < n){
          var sub= src.subList(next, Math.min(n, next + size));
          pending.add(ForkJoinTask.adapt(() -> chunk(sub, stages)).fork());
          next+= sub.size();
          size= Math.min(cap, 2 * size);
        }
      }
      @Override public boolean hasNext(){
        while (pos == buf.size()){
          if (pending.isEmpty()){ return false; }
          poll();
          buf= pending.poll().join();
          pos= 0;
          fill();
        }
        return true;
      }
      @Override public Object next(){ return buf.get(pos++); }
    };
  }
  private List<Object> chunk(List<Object> sub, UnaryOperator<Stream<Object>> stages){
    var prev= current.get();
    current.set(this);
    try{ return prefix(stages.apply(sub.stream()).iterator()); }
    finally{ current.set(prev); }
  }
  static List<Object> prefix(Iterator<Object> it){
    var out= new ArrayList<Object>();
    while (true){
      poll();
      Object e;
      try{ if (!it.hasNext()){ return out; } e= it.next(); }
      catch(Cancelled c){ throw c; }
      catch(Throwable t){ e= new ErrBox(t); }
      out.add(e);
      if (e instanceof ErrBox){ return out; }
    }
  }
  static Object unbox(Object e){
    if (e instanceof ErrBox b){ throw sneaky(b.t()); }
    return e;
  }
  @SuppressWarnings("unchecked") private static <E extends Throwable> RuntimeException sneaky(Throwable t) throws E{ throw (E)t; }
  static Object box(Supplier<Object> f){
    try{ return f.get(); }
    catch(Cancelled c){ throw c; }
    catch(Throwable t){ return new ErrBox(t); }
  }
  static Object map(Object f, Object e){ return e instanceof ErrBox ? e : box(() -> callF$2(f, e)); }
  static Object filter(Object p, Object e){
    if (e instanceof ErrBox){ return e; }
    var r= box(() -> callF$2(p, e));
    if (r instanceof ErrBox){ return r; }
    return isTrue(r) ? e : skip;
  }
  @SuppressWarnings("unchecked") static Stream<Object> flatMap(Object f, Object e){
    if (e instanceof ErrBox){ return Stream.of(e); }
    var r= box(() -> ((Flow$o$1Instance)callF$2(f, e)).s());
    if (r instanceof ErrBox){ return Stream.of(r); }
    try (var inner= (Stream<Object>)r){ return prefix(inner.iterator()).stream(); }
  }
}
