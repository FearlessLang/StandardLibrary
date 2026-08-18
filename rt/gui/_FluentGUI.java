package base;

import java.util.concurrent.CompletableFuture;
import javax.swing.SwingUtilities;

public class _FluentGUI implements Gui$c$0{
  @Override public Object mut$run$1(Object o){
    var f = (Consumer$ao$1) o;
    if (SwingUtilities.isEventDispatchThread()){
      throw new IllegalStateException("FluentGUI.run must not be called from the EDT");
    }
    var done = new CompletableFuture<Void>();
    var fb = new _Frame(done);
    try {
      f.mut$accept$1(fb);
      fb.onEdtAndWait(fb::start);
    }
    catch (Throwable e){ fb.frame.abortBeforeStart(e); }// Throwable: sneaky throws exist
    try { done.join(); }
    catch (Throwable e){ throw Util.asFearlessError(e); }
    return Void$o$0.instance;
  }
}