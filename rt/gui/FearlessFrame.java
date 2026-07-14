package base;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
final class FearlessFrame extends JFrame{
  // The first ~second of a Swing app is the worst-case EDT: native window
  // creation, first window-manager events, first Skia rasterization, glyph
  // cache warming, first JIT of the paint path. Both timers therefore fire
  // their first event only after this delay (Timer.setInitialDelay), so the
  // first tick runs under the same conditions as every later one. The window
  // is visible and correct in the meantime: start() renders one frame before
  // setVisible, and expose repaints blit that image. Game time zero
  // (elapsed, model tick deadlines, the t= in warnings) is warmup end.
  static final int WarmupMillis = 500;
  // A model timer firing catches up at most this many overdue ticks. Longer
  // stalls (machine sleep, debugger pause) drop the excess with a warning:
  // replaying unbounded ticks after resume would flood the queue and visibly
  // fast-forward the game.
  static final int ModelCatchupMax = 100;

  private final CompletableFuture<Void> done;
  final SerialQueue queue = new SerialQueue(this::high);
  private Timer timer;
  private Timer modelTimer;
  private Throwable failure;
  private boolean high;
  private boolean closing;
  private boolean completed;
  // Number of upcoming WINDOW_CLOSED events to ignore; EDT confined. Used by
  // setDecoration: its dispose() posts a WINDOW_CLOSED that must not be
  // treated as the user quitting.
  private int suppressClosed;
  // Frame-loss / backlog detection state; all EDT confined (all starters and
  // all timer callbacks run on the EDT).
  private long runtimeStartNs = System.nanoTime();// re-based in startRuntime
  private long tickPeriodNs;
  private long tickLast;
  // Model fixed-rate state: tick n is due at modelStartNs + n * modelPeriodNs.
  // Deadlines are absolute, so per-firing lateness never accumulates: the
  // long-run tick rate converges to exactly 1e9/modelPeriodNs per second.
  private long modelPeriodNs;
  private long modelStartNs;
  private long modelTicks;
  private long modelWarnAt = 1_000;

  FearlessFrame(CompletableFuture<Void> done){
    this.done = done;
    GuiEdtBoundary.install();
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    addWindowListener(new WindowAdapter(){
      @Override public void windowClosed(WindowEvent e){ closed(); }
    });
  }

  void startRuntime(int delayMillis, Runnable tick){
    assert SwingUtilities.isEventDispatchThread();
    assert timer == null;
    runtimeStartNs = System.nanoTime() + WarmupMillis * 1_000_000L;// game time zero: warmup end
    tickPeriodNs = delayMillis * 1_000_000L;// period in ns, same unit as nanoTime
    tickLast = runtimeStartNs;
    timer = new Timer(delayMillis, _ -> {
      long now = System.nanoTime();
      warnIfLost("repaint", now, now - tickLast, tickPeriodNs);
      tickLast = now;
      try { tick.run(); }
      catch (Throwable t){ low(t); }
    });
    timer.setInitialDelay(delayMillis + WarmupMillis);
    timer.start();
  }

  // Live fps change, model initiated (_Frame hops to the EDT before calling).
  void setTickDelay(int delayMillis){
    assert SwingUtilities.isEventDispatchThread();
    assert timer != null;// only reachable post start
    if (!active()){ return; }// never (re)start a timer during shutdown: it would keep the EDT alive
    timer.setInitialDelay(delayMillis);
    timer.setDelay(delayMillis);
    timer.restart();
    tickPeriodNs = delayMillis * 1_000_000L;
    tickLast = System.nanoTime();// no spurious loss warning across the change
  }

  // Swaps window decoration live. AWT forbids setUndecorated on a displayable
  // window, so: dispose, flip, show again with the same bounds — the standard
  // workaround. Runs entirely inside one EDT dispatch, so no repaint tick can
  // interleave while the frame is momentarily non-displayable. The dispose
  // posts one WINDOW_CLOSED event, suppressed via suppressClosed so it is not
  // taken as the user quitting. Expect a brief native flicker: the OS window
  // really is recreated.
  void setDecoration(boolean undecorated, float opacity){
    assert SwingUtilities.isEventDispatchThread();
    if (!active()){ return; }
    if (isUndecorated() == undecorated){
      if (undecorated){ setOpacity(opacity); }// repeated call may still change alpha
      return;
    }
    var b = getBounds();
    if (!undecorated){ setOpacity(1f); }// opacity < 1 is illegal on decorated windows
    suppressClosed++;
    dispose();
    setUndecorated(undecorated);
    if (undecorated){ setOpacity(opacity); }
    setBounds(b);
    setVisible(true);
    toFront();
  }

  // Starts or replaces the model timer, fixed-rate semantics. warmupMillis
  // delays the first firing and the tick deadlines (WarmupMillis at startup,
  // 0 on a live modelFps change). The action list is live and EDT confined:
  // an action added later by the model takes part from the next due tick,
  // exactly like Button.actions. Each firing submits the whole action list
  // once per model tick that became due since the previous firing; the
  // submissions of one firing are contiguous on the queue (single EDT
  // producer), so catch-up ticks cannot interleave with button or key events.
  void restartModelTimer(long periodNs, int warmupMillis, java.util.List<MF$1> actions){
    assert SwingUtilities.isEventDispatchThread();
    if (!active()){ return; }
    if (modelTimer != null){ modelTimer.stop(); }
    modelPeriodNs = periodNs;
    modelStartNs = System.nanoTime() + warmupMillis * 1_000_000L;
    modelTicks = 0;
    modelWarnAt = 1_000;
    int delayMillis = Math.max(1, Math.toIntExact(Math.round(periodNs / 1_000_000.0)));
    modelTimer = new Timer(delayMillis, _ -> {
      long now = System.nanoTime();
      long due = (now - modelStartNs) / modelPeriodNs;// ticks that should have run by now
      long todo = due - modelTicks;
      if (todo > ModelCatchupMax){
        long dropped = todo - ModelCatchupMax;
        System.err.println("Warning [t=" + (now - runtimeStartNs) / 1_000_000 + "ms]: "
          + dropped + " model tick(s) dropped: stall longer than "
          + ModelCatchupMax + " periods (" + ModelCatchupMax * modelPeriodNs / 1_000_000
          + "ms); catch-up capped");
        modelTicks += dropped;
        todo = ModelCatchupMax;
      }
      for (long i = 0; i < todo; i++){
        for (var a : actions){ queue.submit(a); }
        modelTicks++;
      }
      // Backlog: nothing is lost, but the queue thread is slower than the
      // submission rate; latency grows. Escalating threshold: warn at 1000,
      // then 10000, ... and re-arm once the queue fully drains.
      int backlog = queue.q().size();
      if (backlog >= modelWarnAt){
        System.err.println("Warning [t=" + (now - runtimeStartNs) / 1_000_000
          + "ms]: model queue backlog at " + backlog + " tasks");
        modelWarnAt *= 10;
      } else if (backlog == 0){ modelWarnAt = 1_000; }
    });
    modelTimer.setInitialDelay(delayMillis + warmupMillis);
    modelTimer.start();
  }

  // Repaint only: one line per occurrence, no aggregation; the t= timestamp
  // (ms since game time zero) makes the frequency readable. Coarse by
  // design: OS timer granularity (~16ms on Windows) makes gaps of ~1.5x the
  // period normal, so only a gap of 2x the period or more (a whole frame
  // skipped beyond jitter) is reported. The model timer needs no gap
  // warning: a late firing is absorbed by fixed-rate catch-up, and true
  // losses (cap exceeded) are reported above.
  private void warnIfLost(String what, long nowNs, long gapNs, long periodNs){
    long lost = gapNs / periodNs - 1;// integer division: only whole skipped periods count
    if (lost <= 0){ return; }
    System.err.println("Warning [t=" + (nowNs - runtimeStartNs) / 1_000_000 + "ms]: "
      + lost + " " + what + " frame(s) lost ("
      + gapNs / 1_000_000 + "ms gap, period " + periodNs / 1_000_000 + "ms)");
  }

  // A failure from the user queue has higher priority than an EDT/painting/
  // layout failure. During shutdown after a low-priority Swing failure we keep
  // draining/closing the user queue so that a user computation already in
  // progress can still report a higher priority failure.
  void high(Throwable e){
    assert !SwingUtilities.isEventDispatchThread();
    Throwable discarded;
    boolean accepted;
    synchronized (this){
      if (completed || high){
        discarded = e;
        accepted = false;
      } else {
        discarded = failure;
        failure = e;
        high = true;
        closing = true;
        accepted = true;
      }
    }
    if (discarded != null){ printDiscarded(discarded); }
    if (!accepted){ return; }
    SwingUtilities.invokeLater(this::stopSwingOnEdt);
    finish();
  }

  void low(Throwable e){
    assert SwingUtilities.isEventDispatchThread();
    boolean accepted;
    synchronized (this){
      accepted = !completed && !closing && failure == null;
      if (accepted){
        failure = e;
        closing = true;
      }
    }
    if (!accepted){
      printDiscarded(e);
      return;
    }
    stopSwingOnEdt();
    queue.closeThen(this::finish);
  }

  void closed(){
    assert SwingUtilities.isEventDispatchThread();
    if (suppressClosed > 0){// synthetic close from setDecoration, not the user
      suppressClosed--;
      return;
    }
    boolean accepted;
    synchronized (this){
      accepted = !completed && !closing;
      if (accepted){ closing = true; }
    }
    if (!accepted){ return; }
    stopTimers();
    queue.closeThen(this::finish);
  }

  void abortBeforeStart(Throwable e){
    assert timer == null: "unreachable: runtime timer started before abortBeforeStart";
    boolean accepted;
    synchronized (this){
      accepted = !completed && !closing;
      if (accepted){
        closing = true;
        completed = true;
      }
    }
    if (!accepted){
      printDiscarded(e);
      return;
    }
    queue.closeThen(() -> {});
    if (SwingUtilities.isEventDispatchThread()){ dispose(); }
    else { SwingUtilities.invokeLater(this::dispose); }
    done.completeExceptionally(e);
  }

  private synchronized boolean active(){ return !closing && !completed; }

  private void stopSwingOnEdt(){
    assert SwingUtilities.isEventDispatchThread();
    stopTimers();
    dispose();
  }

  private void stopTimers(){
    timer.stop();// always set before stopTimers is reachable
    if (modelTimer != null){ modelTimer.stop(); }
  }

  private void finish(){
    Throwable e;
    synchronized (this){
      if (completed){ return; }
      completed = true;
      e = failure;
    }
    if (e == null){ done.complete(null); }
    else { done.completeExceptionally(e); }
  }

  private static void printDiscarded(Throwable e){
    System.err.println("Discarded GUI error:");
    e.printStackTrace();
  }
}

final class GuiEdtBoundary extends EventQueue{
  private static final AtomicBoolean installed = new AtomicBoolean();

  private GuiEdtBoundary(){}

  static void install(){
    if (!installed.compareAndSet(false, true)){ return; }
    java.awt.Toolkit.getDefaultToolkit().getSystemEventQueue().push(new GuiEdtBoundary());
  }

  @Override protected void dispatchEvent(AWTEvent event){
    try { super.dispatchEvent(event); }
    catch (Throwable e){
      var frame = frameOf(event);
      if (frame == null){
        e.printStackTrace();
        return;
      }
      frame.low(e);
    }
  }

  private static FearlessFrame frameOf(AWTEvent event){
    var src = event.getSource();
    var w = src instanceof Window win
      ? win
      : src instanceof Component c
        ? SwingUtilities.getWindowAncestor(c)
        : null;
    return w instanceof FearlessFrame f ? f : null;
  }
}