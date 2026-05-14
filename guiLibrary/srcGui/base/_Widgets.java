package base;

import javax.swing.JComponent;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import static base.Scopes.*;

class _Button extends AWidget<Button$0,JButton> implements Button$0{
  volatile String text= "";
  _Button(_Frame frame){ super(frame); }
  @Override public Object mut$text$1(Object text){
    this.text = ((Str$0Instance)text).val();
    frame.markLayoutDirty();
    return this;
  }
  @Override public Object read$text$0(){ return new Str$0Instance(text); }
  @Override public Object mut$action$1(Object r){ frame.onEdtAndWait(() ->component.addActionListener(_ -> frame.frame.queue.submit((MF$1)r))); return this; }
  {
    component= new JButton(){
      private static final long serialVersionUID = 1L;
      @Override public void revalidate(){ Awt.revalidateKids(this); super.revalidate(); }
      @Override protected void paintComponent(java.awt.Graphics g){ ButtonAwt.paint(this,g,_Button.this); }
      @Override public Dimension getPreferredSize(){ return Awt.preferred(Awt.textSizeWithInsets(this,_Button.this.text,_Button.this),_Button.this); }
      @Override public Dimension getMinimumSize(){ return getPreferredSize(); }
    };
    component.setOpaque(false);
    component.setContentAreaFilled(false);
    component.setBorderPainted(false);
    component.setFocusPainted(false);
    component.setRolloverEnabled(true);
    component.setBorder(null);
    component.setFocusable(false);
  }
}
class _Label extends AWidget<Label$0,JLabel> implements Label$0{
  volatile String text= "";
  _Label(_Frame frame){ super(frame); }
  @Override public Object mut$text$1(Object text){
    this.text = ((Str$0Instance)text).val();
    frame.markLayoutDirty();
    return this;
  }
  @Override public Object read$text$0(){ return new Str$0Instance(text); }
  {
    component= new JLabel(){
      private static final long serialVersionUID = 1L;
      @Override public void revalidate(){ Awt.revalidateKids(this); super.revalidate(); }
      @Override protected void paintComponent(java.awt.Graphics g){
        Awt.paintBackground(this,g,_Label.this);
        Awt.paintCenteredText(this,g,_Label.this.text,_Label.this);
      }
      @Override public Dimension getPreferredSize(){ return Awt.preferred(Awt.textSizeWithInsets(this,_Label.this.text,_Label.this),_Label.this); }
      @Override public Dimension getMinimumSize(){ return getPreferredSize(); }
    };
    component.setOpaque(false);
    component.setBorder(null);
    component.setFocusable(false);
  }
}
class _Pane extends AContainer<Pane$0,JPanel> implements Pane$0{
  _Pane(_Frame frame){ super(frame); }
  @Override public Pane$0 mut$button$1(Object s){ frame.addTo(component,s,_Button::new); return this; }
  @Override public Pane$0 mut$label$1(Object s){ frame.addTo(component,s,_Label::new); return this; }
  @Override public Pane$0 mut$pane$1(Object s){ frame.addTo(component,s,_Pane::new); return this; }
  @Override public Pane$0 mut$border$1(Object s){ frame.addTo(component,s,_Border::new); return this; }
  {
    component= new JPanel(){
      private static final long serialVersionUID = 1L;
      @Override public Dimension getPreferredSize(){ return Awt.preferred(super.getPreferredSize(), _Pane.this); }
      @Override public Dimension getMinimumSize(){ return getPreferredSize(); }
      @Override public void revalidate(){ Awt.revalidateKids(this); super.revalidate(); }
      @Override protected void paintComponent(java.awt.Graphics g){ _Pane.this.paint(g,this); } 
    };
    component.setOpaque(false);
    component.setBorder(null);
    component.setLayout(new CenteredFlowLayout(_Pane.this));
    component.setFocusable(false);
  }
}
class _Border extends AContainer<Border$0,JPanel> implements Border$0{
  _Border(_Frame frame){ super(frame); }
  @Override public Border$0 mut$north$1(Object s){ frame.addTo(component,BorderLayout.NORTH,s,_Pane::new); return this; }
  @Override public Border$0 mut$south$1(Object s){ frame.addTo(component,BorderLayout.SOUTH,s,_Pane::new); return this; }
  @Override public Border$0 mut$east$1(Object s){ frame.addTo(component,BorderLayout.EAST,s,_Pane::new); return this; }
  @Override public Border$0 mut$west$1(Object s){ frame.addTo(component,BorderLayout.WEST,s,_Pane::new); return this; }
  @Override public Border$0 mut$center$1(Object s){ frame.addTo(component,BorderLayout.CENTER,s,_Pane::new); return this; }
  @Override public Border$0 mut$northB$1(Object s){ frame.addTo(component,BorderLayout.NORTH,s,_Border::new); return this; }
  @Override public Border$0 mut$southB$1(Object s){ frame.addTo(component,BorderLayout.SOUTH,s,_Border::new); return this; }
  @Override public Border$0 mut$eastB$1(Object s){ frame.addTo(component,BorderLayout.EAST,s,_Border::new); return this; }
  @Override public Border$0 mut$westB$1(Object s){ frame.addTo(component,BorderLayout.WEST,s,_Border::new); return this; }
  @Override public Border$0 mut$centerB$1(Object s){ frame.addTo(component,BorderLayout.CENTER,s,_Border::new); return this; }

  {
    component= new JPanel(){
      private static final long serialVersionUID = 1L;
      @Override public Dimension getPreferredSize(){ return Awt.preferred(super.getPreferredSize(), _Border.this); }
      @Override public Dimension getMinimumSize(){ return getPreferredSize(); }
      @Override public void revalidate(){ Awt.revalidateKids(this); super.revalidate(); }
      @Override protected void paintComponent(java.awt.Graphics g){ _Border.this.paint(g,this); }
    };
    component.setOpaque(false);
    component.setBorder(null);
    component.setLayout(new MutableBorderLayout(_Border.this));
    component.setFocusable(false);
  }
}
abstract class AWidget<S,C extends JComponent> implements Widget$1{
  static Nat$0 zero= Nat$0Instance.instance(0);
  static Nat$0 def= Nat$0Instance.instance(6);
  static Nat$0 defText= Nat$0Instance.instance(12);
  volatile WidthNat$0 left= (WidthNat$0) WidthNat$0.instance.read$$hash$1(def);
  volatile HeightNat$0 top= (HeightNat$0) HeightNat$0.instance.read$$hash$1(def);
  volatile WidthNat$0 right= (WidthNat$0) WidthNat$0.instance.read$$hash$1(def);
  volatile HeightNat$0 bottom= (HeightNat$0) HeightNat$0.instance.read$$hash$1(def);
  volatile WidthNat$0 widthGap= (WidthNat$0) WidthNat$0.instance.read$$hash$1(def);
  volatile HeightNat$0 heightGap= (HeightNat$0) HeightNat$0.instance.read$$hash$1(def);
  volatile Nat$0   radius= defText;
  volatile WidthNat$0 preferredWidth= null;//null means 'auto'
  volatile HeightNat$0 preferredHeight= null;
  volatile Color$0 foreground= (Color$0) Color$0.instance;//black
  volatile Color$0 background= (Color$0) Color$0.instance.imm$transparent$0();
  volatile HeightNat$0 textSize= (HeightNat$0) HeightNat$0.instance.read$$hash$1(defText);
  C component;
  final _Frame frame;
  AWidget(_Frame frame){ this.frame= frame; }
  void toZero(){  
    left = WidthNat$0.instance;
    top = HeightNat$0.instance;
    right = WidthNat$0.instance;
    bottom = HeightNat$0.instance;
    widthGap = WidthNat$0.instance;
    heightGap = HeightNat$0.instance;
    radius = zero;
    preferredWidth= null;
    preferredHeight= null;
    foreground= Color$0.instance;
    background= (Color$0) Color$0.instance.imm$transparent$0();
    textSize= (HeightNat$0) HeightNat$0.instance.read$$hash$1(defText);
    frame.markLayoutDirty();
  }
  @Override public Object mut$topInset$1(Object v){
    frame.height((HeightNat$0)v,"top inset");
    top = (HeightNat$0)v;
    frame.markLayoutDirty();
    return mut$self$0();
  }
  @Override public Object mut$bottomInset$1(Object v){
    frame.height((HeightNat$0)v,"bottom inset");
    bottom = (HeightNat$0)v;
    frame.markLayoutDirty();
    return mut$self$0();
  }
  @Override public Object mut$leftInset$1(Object v){
    frame.width((WidthNat$0)v,"left inset");
    left = (WidthNat$0)v;
    frame.markLayoutDirty();
    return mut$self$0();
  }
  @Override public Object mut$rightInset$1(Object v){
    frame.width((WidthNat$0)v,"right inset");
    right = (WidthNat$0)v;
    frame.markLayoutDirty();
    return mut$self$0();
  }
  @Override public Object mut$heightGap$1(Object v){
    frame.height((HeightNat$0)v,"height gap");
    heightGap = (HeightNat$0)v;
    frame.markLayoutDirty();
    return mut$self$0();
  }
  @Override public Object mut$widthGap$1(Object v){
    frame.width((WidthNat$0)v,"width gap");
    widthGap = (WidthNat$0)v;
    frame.markLayoutDirty();
    return mut$self$0();
  }
  @Override public Object mut$width$1(Object w){
    frame.width((WidthNat$0)w,"widget width");
    preferredWidth = (WidthNat$0)w;
    frame.markLayoutDirty();
    return mut$self$0();
  }
  @Override public Object mut$height$1(Object h){
    frame.height((HeightNat$0)h,"widget height");
    preferredHeight = (HeightNat$0)h;
    frame.markLayoutDirty();
    return mut$self$0();
  }
  @Override public Object mut$radius$1(Object r){
    frame.size((Nat$0)r,"radius");
    this.radius = (Nat$0)r;
    frame.markLayoutDirty();
    return mut$self$0();
  }
  public Object mut$textSize$1(Object textSize){//will cross override later for button/label
    frame.height((HeightNat$0)textSize,"text size");
    this.textSize = (HeightNat$0)textSize;
    frame.markLayoutDirty();
    return mut$self$0();
  }
  public Object read$textSize$0(){ return textSize; }

  @Override public Object mut$autoWidth$0(){ preferredWidth = null; frame.markLayoutDirty(); return mut$self$0(); }
  @Override public Object mut$autoHeight$0(){ preferredHeight = null; frame.markLayoutDirty(); return mut$self$0(); }
  @Override public Object mut$foreground$1(Object c){ foreground = (Color$0) c; return mut$self$0(); }
  @Override public Object mut$background$1(Object c){ background = (Color$0) c; return mut$self$0(); }

  @Override public Object read$topInset$0(){ return top; }
  @Override public Object read$bottomInset$0(){ return bottom; }
  @Override public Object read$leftInset$0(){ return left; }
  @Override public Object read$rightInset$0(){ return right; }
  @Override public Object read$heightGap$0(){ return heightGap; }
  @Override public Object read$widthGap$0(){ return widthGap; }
  @Override public Object read$width$0(){ return preferredWidth==null?Util.optEmpty():Util.optSome(preferredWidth); }
  @Override public Object read$height$0(){ return preferredHeight==null?Util.optEmpty():Util.optSome(preferredHeight); }
  @Override public Object read$radius$0(){ return radius; }
  @Override public Object read$foreground$0(){ return foreground; }
  @Override public Object read$background$0(){ return background; }

  @Override public Object mut$save$1(Object s){ ((Consumer$1)s).mut$accept$1(mut$self$0()); return mut$self$0(); }
  @Override public Object mut$autoSize$0(){ preferredWidth = null; preferredHeight = null; frame.markLayoutDirty(); return mut$self$0(); }
}
abstract class AContainer<S,C extends JComponent> extends AWidget<S,C>{
  AContainer(_Frame frame){ super(frame); }
  Painter$0 paint = Scopes.idP;
  void toZero(){
    super.toZero();
    clearMouse();
    paint = Scopes.idP;
  }

  public Object mut$mouse$1(Object s){//Cross override later
    clearMouse();
    ((Scope$1)s).mut$run$1(new CMouseBuilder(this));
    return mut$self$0();
  }

  public Object mut$paint$1(Object p){//Cross override later
    paint = (Painter$0)p;
    return mut$self$0();
  }
  private void clearMouse(){
    frame.onEdtAndWait(() -> {
      for (var l:component.getMouseListeners()){ component.removeMouseListener(l); }
      for (var l:component.getMouseMotionListeners()){ component.removeMouseMotionListener(l); }
    });
  }
  void paint(java.awt.Graphics g,JComponent c){
    var gg= g.create();
    try{
      Awt.paintBackground(c,gg,this);
      paint.imm$run$1(CGraphicsCtx.of(gg,frame,c));
    }
    finally{ gg.dispose(); }
  }
}
class _Frame implements Frame$0{
  final FearlessFrame frame;
  Time$0 elapsed=time(0);//So that it can not be null if a repaint is scheduled very early
  final int screenW;
  final int screenH;
  final WidthNat$0 screenSizeW;
  final HeightNat$0 screenSizeH;
  private final long startNanos=System.nanoTime();
  private Nat$0 fps                  = n(30);
  private Alpha$0 alpha              =(Alpha$0)Alpha$0.instance.imm$opaque$0();
  private XNat$0 locationX              = null;
  private YNat$0 locationY              = null;
  private Str$0 title                   = new Str$0Instance("");
  private boolean maximized              = false;
  private boolean resizable              = false;
  private boolean undecorated            = false;
  private final AtomicBoolean layoutDirty= new AtomicBoolean();

  private Widget$1 top;

  _Frame(CompletableFuture<Void> done){
    frame = new FearlessFrame(done);
    var b=java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
      .getDefaultScreenDevice()
      .getDefaultConfiguration()
      .getBounds();
    screenW = Math.toIntExact(b.width);
    screenH = Math.toIntExact(b.height);
    screenSizeW = w(screenW);
    screenSizeH = h(screenH);
  }
  <T extends Widget$1> void addTo(JComponent component, Object scope, Function<_Frame,T> make){
    var b= this.onEdtAndWait(() -> {
      var bb= make.apply(this);
      component.add(((AWidget<?,?>)bb).component);
      return bb;
    });
    ((Scope$1)scope).mut$run$1(b);
    this.markLayoutDirty();
  }

  <T extends Widget$1> void addTo(JComponent component, String where, Object scope, Function<_Frame,T> make){
    var b= this.onEdtAndWait(() -> {
      var bb= make.apply(this);
      component.add(((AWidget<?,?>)bb).component,where);
      return bb;
    });
    ((Scope$1)scope).mut$run$1(b);
    this.markLayoutDirty();
  }

  <T> T onEdtAndWait(Supplier<T> s){//TODO: is there a SwingUtilities doing this already?
    if (SwingUtilities.isEventDispatchThread()){ return s.get(); }//Otherwise,still better with CompletableFuture
    var task= new FutureTask<>(s::get);
    SwingUtilities.invokeLater(task);
    try { return task.get(); }
    catch (InterruptedException e){
      Thread.currentThread().interrupt();
      throw new Error(e);
    }
    catch (ExecutionException e){
      var c= e.getCause();
      if (c instanceof RuntimeException re){ throw re; }
      if (c instanceof Error er){ throw er; }
      throw new RuntimeException(c);
    }
  }

  void onEdtAndWait(Runnable r){ onEdtAndWait(() -> { r.run(); return null; }); }

  void markLayoutDirty(){ layoutDirty.set(true); }

  void tick(Time$0 elapsed) {
    this.elapsed = elapsed;
    var revalidate= layoutDirty.getAndSet(false) && top != null;
    if (revalidate){
      System.err.println("TOP REVALIDATE");
      ((AWidget<?,?>)top).component.revalidate();
    }
    frame.repaint();
  }
  int width(WidthNat$0 v,String what){
    return bounded(nat(v.read$get$0()),screenW,what,"screen width");
  }
  int height(HeightNat$0 v,String what){
    return bounded(nat(v.read$get$0()),screenH,what,"screen height");
  }
  int xPos(XNat$0 v,String what){
    return bounded(nat(v.read$get$0()),screenW-1,what,"screen x");
  }
  int yPos(YNat$0 v,String what){
    return bounded(nat(v.read$get$0()),screenH-1,what,"screen y");
  }
  int size(Nat$0 v,String what){
    return bounded(nat(v),Math.min(screenW,screenH),what,"screen size");
  }
  private long nat(Object n){ return Util.natToInt(n); }
  private int bounded(long v,int max,String what,String bound){
    if (v > max){ throw Util.detErr(what+" must be <= "+max+" ("+bound+")"); }
    return Math.toIntExact(v);
  }  
  private void checkWindowFits(){
    if (frame.getWidth() > screenW || frame.getHeight() > screenH){
      throw Util.detErr(
        "Window must fit on screen: window="
        +frame.getWidth()+"x"+frame.getHeight()
        +", screen="+screenW+"x"+screenH
        );
    }
  }
  private void checkWindowLocationFits(int x,int y){
    if (x + frame.getWidth() > screenW || y + frame.getHeight() > screenH){
      throw Util.detErr(
        "Window location puts window outside screen: location="
        +x+","+y
        +", window="+frame.getWidth()+"x"+frame.getHeight()
        +", screen="+screenW+"x"+screenH
        );
    }
  }
  void start(){
    assert top != null;
    frame.setTitle(((Str$0Instance)title).val());
    top.mut$radius$1(n(0));
    var col= (Color$0)top.read$background$0();
    var zero= Scopes.alpha(col.read$alpha$0()) == 0;
    if (zero){ top.mut$background$1(Color$0.instance.imm$boringGray$0()); }
    else { top.mut$background$1(Color$0.instance.imm$$hash$4(
      col.read$red$0(),
      col.read$green$0(),
      col.read$blue$0(),
      Alpha$0.instance.imm$opaque$0()
    )); }
    frame.setContentPane(((AWidget<?,?>)top).component);
    frame.setUndecorated(undecorated);
    frame.setResizable(resizable);
    frame.pack();
    if (maximized){
      if (locationX != null || locationY != null){ throw Util.detErr("A maximized window cannot also have an explicit location"); }
      maximize(screenSizeW,screenSizeH);
    }
    else{
      checkWindowFits();
      if (locationX != null && locationY != null){
        int xx= xPos(locationX,"window x location");
        int yy= yPos(locationY,"window y location");
        checkWindowLocationFits(xx,yy);
        frame.setLocation(xx,yy);
      }
      else{ frame.setLocationRelativeTo(null); }
  }
  if (undecorated){ frame.setOpacity(Scopes.alpha(alpha)/255f); }
    frame.setFocusable(true);
    frame.setVisible(true);
    frame.startRuntime(
      Math.round(1000.0f / Scopes.nat(fps)),
      () -> tick(timeNanos(System.nanoTime() - startNanos))
    );
  }
  private void maximize(WidthNat$0 w, HeightNat$0 h) {
    frame.setSize(Awt.dimension(w,h));
    frame.setLocation(0, 0);
  }
  @Override public Object mut$maximized$0(){ this.maximized = true; return this; }
  @Override public Object mut$resizable$0(){ this.resizable = true; return this; }
  @Override public Object mut$undecorated$1(Object alpha){ this.undecorated = true; this.alpha = (Alpha$0) alpha; return this; }
  @Override public Object mut$location$2(Object x,Object y){
    xPos((XNat$0)x,"window x location");
    yPos((YNat$0)y,"window y location");
    locationX = (XNat$0)x;
    locationY = (YNat$0)y;
    return this;
  }
  @Override public Object mut$onKey$1(Object scope){
    var keyScope = (Scope$1)scope;
    var keys=new CKeyManager(this);
    keyScope.mut$run$1(keys);
    onEdtAndWait(() -> {
      for (var l:frame.getKeyListeners()){ frame.removeKeyListener(l); }
      frame.addKeyListener(keys);
    });
    return this;
  }
  @Override public Object mut$title$1(Object title){ this.title = (Str$0) title; return this; }
  @Override public Object mut$fps$1(Object fps){
    long nn= Util.natToInt((Nat$0)fps);
    if (nn<1 || nn > 500){ throw Util.detErr("FPS must be between 1 and 500"); }
    this.fps = (Nat$0) fps; return this;
  }

  @Override public Object mut$content$1(Object s){
    var b= onEdtAndWait(() -> new _Pane(this));
    top= b;
    ((Scope$1)s).mut$run$1(b);
    markLayoutDirty();
    return Void$0.instance;
  }

  @Override public Object mut$contentB$1(Object s){
    var b= onEdtAndWait(() -> new _Border(this));
    top= b;
    ((Scope$1)s).mut$run$1(b);
    markLayoutDirty();
    return Void$0.instance;
  }
}