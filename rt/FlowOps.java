package base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import static base.Util.*;

@SuppressWarnings("serial")
final class FlowError extends Deterministic{ FlowError(Info$o$0 i){ super(i); } }
@SuppressWarnings("serial")
final class Cancelled extends RuntimeException{ Cancelled(){ super(null, null, false, false); } }

final class FlowOps{
  private FlowOps(){}
  record Err(Object info){}
  interface Call{ Object apply(Object sink, Object state, Object e); }
  interface FlowOp{
    void step(Sink sink);
    void stopUp();
    boolean isRunning();
    default void forAll(Sink sink){
      while (isRunning()){ step(sink); }
      sink.stopDown();
    }
    default FlowOp split(){ return null; }
    default boolean canSplit(){ return false; }
  }
  interface Sink extends _Sink$o$1{
    void accept(Object e);
    void pushError(Object info);
    void stopDown();
    @Override default Object mut$$hash$1(Object p0){ accept(p0); return Void$o$0.instance; }
    @Override default Object mut$pushError$1(Object p0){ pushError(p0); return Void$o$0.instance; }
    @Override default Object mut$stopDown$0(){ stopDown(); return Void$o$0.instance; }
  }
  static final UnaryOperator<Sink> plain= s->s;
  static final Object CONTINUE= new Object();
  static final Object STOP= new Object();
  static final ActorMatch$174$1 match= new ActorMatch$174$1(){
    @Override public Object mut$continue$0(){ return CONTINUE; }
    @Override public Object mut$stop$0(){ return STOP; }
  };
  @SuppressWarnings("unchecked") static <E extends Throwable> RuntimeException sneaky(Throwable t) throws E{ throw (E)t; }
  static abstract class Stage implements FlowOp{
    final UnaryOperator<Sink> deco;
    final FlowOp up;
    Sink sink;
    Stage(UnaryOperator<Sink> deco, FlowOp up){ this.deco= deco; this.up= up; }
    abstract Sink make(Sink down);
    Sink sink(Sink down){
      if (sink == null){ sink= deco.apply(make(down)); }
      return sink;
    }
    @Override public void step(Sink down){ up.step(sink(down)); }
    @Override public void forAll(Sink down){ up.forAll(sink(down)); }
    void stepAll(Sink down){
      while (isRunning()){ step(down); }
      down.stopDown();
    }
    @Override public void stopUp(){ up.stopUp(); }
    @Override public boolean isRunning(){ return up.isRunning(); }
  }
  static abstract class Forward implements Sink{
    final Sink down;
    Forward(Sink down){ this.down= down; }
    @Override public void pushError(Object info){ down.pushError(info); }
    @Override public void stopDown(){ down.stopDown(); }
  }
  static final class ListOp implements FlowOp{
    final List<Object> list;
    int cursor;
    int end;
    ListOp(List<Object> list, int start, int end){ this.list= list; cursor= start; this.end= end; }
    @Override public void step(Sink sink){
      if (cursor >= end){ sink.stopDown(); return; }
      sink.accept(list.get(cursor++));
      if (cursor >= end){ sink.stopDown(); }
    }
    @Override public void forAll(Sink sink){
      while (cursor < end){ sink.accept(list.get(cursor++)); }
      sink.stopDown();
    }
    @Override public void stopUp(){ cursor= end; }
    @Override public boolean isRunning(){ return cursor < end; }
    @Override public FlowOp split(){
      if (!canSplit()){ return null; }
      int mid= cursor + (end - cursor) / 2;
      var right= new ListOp(list, mid, end);
      end= mid;
      return right;
    }
    @Override public boolean canSplit(){ return end - cursor > 1; }
  }
  static final class IteratorOp implements FlowOp{
    final Iterator<Object> it;
    boolean running= true;
    IteratorOp(Iterator<Object> it){ this.it= it; }
    @Override public void step(Sink sink){
      if (!isRunning()){ sink.stopDown(); return; }
      sink.accept(it.next());
      if (!isRunning()){ sink.stopDown(); }
    }
    @Override public void stopUp(){ running= false; }
    @Override public boolean isRunning(){ return running && it.hasNext(); }
  }
  static final class Concat implements FlowOp{
    final FlowOp a;
    final FlowOp b;
    Sink noStop;
    Concat(FlowOp a, FlowOp b){ this.a= a; this.b= b; }
    Sink noStop(Sink sink){
      if (noStop == null){ noStop= new Forward(sink){
        @Override public void accept(Object e){ down.accept(e); }
        @Override public void stopDown(){}
      }; }
      return noStop;
    }
    @Override public void step(Sink sink){
      if (a.isRunning()){ a.step(noStop(sink)); return; }
      b.step(sink);
    }
    @Override public void forAll(Sink sink){ a.forAll(noStop(sink)); b.forAll(sink); }
    @Override public void stopUp(){ a.stopUp(); b.stopUp(); }
    @Override public boolean isRunning(){ return a.isRunning() || b.isRunning(); }
  }
  static final class Map extends Stage{
    final Object f;
    Map(UnaryOperator<Sink> deco, FlowOp up, Object f){ super(deco, up); this.f= f; }
    @Override Sink make(Sink down){ return new Forward(down){ @Override public void accept(Object e){ down.accept(callF$2(f, e)); } }; }
    @Override public FlowOp split(){ var r= up.split(); return r == null ? null : new Map(deco, r, f); }
    @Override public boolean canSplit(){ return up.canSplit(); }
  }
  static final class MapCtx extends Stage{
    final ToIso$1g$1 ctx;
    final Object f;
    MapCtx(UnaryOperator<Sink> deco, FlowOp up, ToIso$1g$1 ctx, Object f){ super(deco, up); this.ctx= ctx; this.f= f; }
    @Override Sink make(Sink down){
      return new Forward(down){
        @Override public void accept(Object e){ down.accept(callF$3(f, ((ToIso$1g$1)ctx.mut$iso$0()).mut$close$0(), e)); }
      };
    }
    @Override public FlowOp split(){ var r= up.split(); return r == null ? null : new MapCtx(deco, r, (ToIso$1g$1)ctx.mut$iso$0(), f); }
    @Override public boolean canSplit(){ return up.canSplit(); }
  }
  static final class Filter extends Stage{
    final Predicate<Object> p;
    Filter(UnaryOperator<Sink> deco, FlowOp up, Predicate<Object> p){ super(deco, up); this.p= p; }
    @Override Sink make(Sink down){ return new Forward(down){ @Override public void accept(Object e){ if (p.test(e)){ down.accept(e); } } }; }
    @Override public FlowOp split(){ var r= up.split(); return r == null ? null : new Filter(deco, r, p); }
    @Override public boolean canSplit(){ return up.canSplit(); }
  }
  static final class FlatMap extends Stage{
    final Object f;
    boolean running= true;
    FlatMap(UnaryOperator<Sink> deco, FlowOp up, Object f){ super(deco, up); this.f= f; }
    @Override Sink make(Sink down){
      return new Forward(down){
        @Override public void accept(Object e){ flatten(((Flow$o$1Instance)callF$2(f, e)).take(), down); }
        @Override public void stopDown(){ running= false; down.stopDown(); }
      };
    }
    void flatten(FlowOp inner, Sink down){
      inner.forAll(deco.apply(new Forward(down){
        @Override public void accept(Object e){ if (running){ down.accept(e); } else { inner.stopUp(); } }
        @Override public void stopDown(){ inner.stopUp(); }
      }));
    }
    @Override public boolean isRunning(){ return running; }
    @Override public FlowOp split(){ var r= up.split(); return r == null ? null : new FlatMap(deco, r, f); }
    @Override public boolean canSplit(){ return up.canSplit(); }
  }
  static final class Limit extends Stage{
    long remaining;
    Limit(UnaryOperator<Sink> deco, FlowOp up, long n){ super(deco, up); remaining= n; }
    @Override Sink make(Sink down){
      return new Forward(down){
        @Override public void accept(Object e){
          if (remaining <= 0){ Limit.this.stopUp(); return; }
          down.accept(e);
          remaining-= 1;
          if (remaining <= 0){ Limit.this.stopUp(); }
        }
        @Override public void pushError(Object info){ if (remaining != 0){ down.pushError(info); } }
      };
    }
    @Override public void step(Sink down){
      if (sink == null && remaining <= 0){ stopUp(); return; }
      up.step(sink(down));
    }
    @Override public void forAll(Sink down){ stepAll(down); }
    @Override public void stopUp(){ remaining= 0; up.stopUp(); }
    @Override public boolean isRunning(){ return remaining > 0 && up.isRunning(); }
  }
  static final class Actor extends Stage{
    final Object state;
    final Call call;
    boolean running= true;
    Actor(UnaryOperator<Sink> deco, FlowOp up, Object state, Call call){ super(deco, up); this.state= state; this.call= call; }
    @Override Sink make(Sink down){
      var actorSink= _ActorSinks$174$0.instance.imm$$hash$1(down);
      return new Forward(down){
        @Override public void accept(Object e){
          if (!running){ Actor.this.stopUp(); return; }
          if (call.apply(actorSink, state, e) == STOP){ down.stopDown(); running= false; }
        }
        @Override public void pushError(Object info){ if (running){ down.pushError(info); } }
        @Override public void stopDown(){ down.stopDown(); Actor.this.stopUp(); }
      };
    }
    @Override public void forAll(Sink down){ stepAll(down); }
    @Override public void stopUp(){
      if (!running){ return; }
      running= false;
      up.stopUp();
    }
    @Override public boolean isRunning(){ return running; }
  }
  static final class Scan extends Stage{
    Object acc;
    final Object f;
    Scan(UnaryOperator<Sink> deco, FlowOp up, Object acc, Object f){ super(deco, up); this.acc= acc; this.f= f; }
    @Override Sink make(Sink down){
      return new Forward(down){ @Override public void accept(Object e){ acc= callF$3(f, acc, e); down.accept(acc); } };
    }
  }
  static final class Best extends Stage{
    final OrderBy$5e$2 by;
    final int sign;
    Object best;
    final ArrayList<Object> ties= new ArrayList<>();
    Best(UnaryOperator<Sink> deco, FlowOp up, OrderBy$5e$2 by, int sign){ super(deco, up); this.by= by; this.sign= sign; }
    @Override Sink make(Sink down){
      return new Forward(down){
        @Override public void accept(Object e){
          if (best == null){ best= e; ties.add(e); return; }
          int c= cmp(by, e, best) * sign;
          if (c > 0){ best= e; ties.clear(); }
          if (c >= 0){ ties.add(e); }
        }
        @Override public void stopDown(){
          ties.forEach(down::accept);
          ties.clear();
          down.stopDown();
        }
      };
    }
  }
  static abstract class Terminal implements Sink{
    final FlowOp op;
    boolean stopped;
    Terminal(FlowOp op){ this.op= op; }
    @Override public void pushError(Object info){
      if (stopped){ return; }
      stopDown();
      throw new FlowError((Info$o$0)info);
    }
    @Override public void stopDown(){ stopped= true; op.stopUp(); }
    void drain(UnaryOperator<Sink> deco){ op.forAll(deco.apply(this)); op.stopUp(); }
  }
  static void run(FlowOp op, UnaryOperator<Sink> deco, Consumer<Object> each){
    new Terminal(op){ @Override public void accept(Object e){ each.accept(e); } }.drain(deco);
  }
  static Object first(FlowOp op, UnaryOperator<Sink> deco){
    var t= new Terminal(op){
      Object res;
      @Override public void accept(Object e){
        if (res == null){ res= e; }
        stopDown();
      }
    };
    t.drain(deco);
    return t.res == null ? optEmpty() : optSome(t.res);
  }
  static List<FlowOp> splitTasks(FlowOp task, int n){
    assert n >= 1;
    var res= new ArrayList<FlowOp>();
    res.add(task);
    for (int round= 0; round < n && res.size() < n; round++){
      var merged= new ArrayList<FlowOp>(res.size() * 2);
      for (var t: res){
        merged.add(t);
        var r= t.split();
        if (r != null){ merged.add(r); }
      }
      if (merged.size() == res.size()){ break; }
      res= merged;
    }
    return List.copyOf(res);
  }
}
