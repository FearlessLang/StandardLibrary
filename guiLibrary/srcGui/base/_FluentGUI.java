package base;

import java.util.concurrent.CompletableFuture;
import javax.swing.SwingUtilities;

public class _FluentGUI implements Gui$0{
  @Override public Object mut$run$1(Object o) {
    var f=(Consumer$1)o;
    if (SwingUtilities.isEventDispatchThread()){ throw new IllegalStateException("FluentGUI.run must not be called from the EDT"); }
    var done= new CompletableFuture<Void>();
    var fb= new _Frame(done);
    try {
      f.mut$accept$1(fb);
      fb.onEdtAndWait(() -> fb.start());
      }
    catch (Throwable e){ fb.frame.abortBeforeStart(e); }//Needs to be throwable since sneaky throws etc do exists
    try{ done.join(); }
    catch(/*java.util.concurrent.CompletionException*/Throwable e){
      e.printStackTrace();//temporary for debugging 
      throw Util.asFearlessError(e);
    }
    return Void$0.instance;
  }
}