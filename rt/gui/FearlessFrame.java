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
  private final CompletableFuture<Void> done;
  public final SerialQueue queue= new SerialQueue(this::high);
  private Timer timer;
  private Throwable failure;
  private boolean high;
  private boolean closing;
  private boolean completed;

  FearlessFrame(CompletableFuture<Void> done){
    this.done = done;
    GuiEdtBoundary.install();
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    addWindowListener(new WindowAdapter(){
      @Override public void windowClosed(WindowEvent e){ closed(); }
    });
  }

  void startRuntime(int delayMillis,Runnable tick){
    assert timer == null;
    timer = new Timer(delayMillis,_ -> {
      try{ tick.run(); }
      catch(Throwable e){ low(e); }
    });
    timer.start();
  }
  void submit(MF$1 r){
    assert queue != null;
    queue.submit(r);
  }
  // A failure from the user queue has higher priority than an EDT/painting/layout
  // failure. During shutdown after a low-priority Swing failure, we intentionally
  // keep draining/closing the user queue so that a user computation already in
  // progress can report a higher priority failure.
  void high(Throwable e){
    assert !SwingUtilities.isEventDispatchThread();
    Throwable discarded;
    boolean accepted;
    synchronized(this){
      if (completed || high){
        discarded = e;
        accepted = false;
      }
      else{
        discarded = failure;
        failure = e;
        high = true;
        closing = true;
        accepted = true;
      }
    }
    if (discarded != null){ printDiscarded(discarded); }
    if (!accepted){ return; }
    scheduleStopSwing();
    finish();
  }
  void low(Throwable e){
    assert SwingUtilities.isEventDispatchThread();
    boolean accepted;
    synchronized(this){
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
    assert queue != null;
    queue.closeThen(this::finish);
  }
  void closed(){
    assert SwingUtilities.isEventDispatchThread();
    boolean accepted;
    synchronized(this){
      accepted = !completed && !closing;
      if (accepted){ closing = true; }
    }
    if (!accepted){ return; }
    stopTimer();
    assert queue != null;
    queue.closeThen(this::finish);
  }
  void abortBeforeStart(Throwable e){
    boolean accepted;
    synchronized(this){
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
    queue.closeThen(()->{});
    disposeIfNeeded();
    done.completeExceptionally(e);
  }
  private void stopSwingOnEdt(){
    assert SwingUtilities.isEventDispatchThread();
    stopTimer();
    dispose();
  }
  private void scheduleStopSwing(){
    assert !SwingUtilities.isEventDispatchThread();
    SwingUtilities.invokeLater(this::stopSwingOnEdt);
  }
  private void disposeIfNeeded(){//why If? do we know what thread are we in?
    if (SwingUtilities.isEventDispatchThread()){
      dispose();
      return;
    }
    SwingUtilities.invokeLater(this::dispose);
  }
  private void stopTimer(){ if (timer != null){ timer.stop(); } }//can this be null here?

  private void finish(){
    Throwable e;
    synchronized(this){//is some of this running in a controlled thread, so can we avoid some synchronized of those blocks?
      if (completed){ return; }
      completed = true;
      e = failure;
    }
    if (e == null){ done.complete(null); }
    else{ done.completeExceptionally(e); }
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
    if (!installed.compareAndSet(false,true)){ return; }
    java.awt.Toolkit.getDefaultToolkit()
      .getSystemEventQueue()
      .push(new GuiEdtBoundary());
  }

  @Override protected void dispatchEvent(AWTEvent event){
    try{ super.dispatchEvent(event); }
    catch(Throwable e){
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