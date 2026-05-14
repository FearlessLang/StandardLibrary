package base;

import static base.Scopes.*;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

public class CenteredFlowLayout extends FlowLayout{
  private static final long serialVersionUID=1L;

  private final AWidget<?,?> gap;

  public CenteredFlowLayout(AWidget<?,?> gap){ this(CENTER,gap); }

  public CenteredFlowLayout(int align,AWidget<?,?> gap){
    super(align,0,0);
    this.gap=gap;
  }

  @Override public java.awt.Dimension preferredLayoutSize(Container target){
    synchronized(target.getTreeLock()){
      return oneRowSize(target,Component::getPreferredSize);
    }
  }

  @Override public java.awt.Dimension minimumLayoutSize(Container target){
    synchronized(target.getTreeLock()){
      return oneRowSize(target,Component::getMinimumSize);
    }
  }

  @Override public void layoutContainer(Container target){
    synchronized(target.getTreeLock()){
      if (getAlignOnBaseline()){ throw new UnsupportedOperationException("baseline alignment"); }
      var area=area(target);
      int y=area.y()+(area.height()-blockHeight(rows(target,area.width())))/2;
      for (var row:rows(target,area.width())){
        layoutRow(area,y,row);
        y+=row.height()+h(gap.heightGap);
      }
    }
  }

  private java.awt.Dimension oneRowSize(Container target,Dim dim){
    int width=0;
    int height=0;
    for (int i=0;i<target.getComponentCount();i++){
      var d=dim.of(target.getComponent(i));
      if (i!=0){ width+=w(gap.widthGap); }
      width+=d.width;
      height=Math.max(height,d.height);
    }
    var in=target.getInsets();
    return new java.awt.Dimension(
      width+in.left+in.right+w(gap.left)+w(gap.right),
      height+in.top+in.bottom+h(gap.top)+h(gap.bottom)
      );
  }

  private List<Row> rows(Container target,int maxWidth){
    var rows=new ArrayList<Row>();
    var cs=new ArrayList<Component>();
    int width=0;
    int height=0;

    for (int i=0;i<target.getComponentCount();i++){
      var c=target.getComponent(i);
      var d=c.getPreferredSize();
      if (!cs.isEmpty() && width+w(gap.widthGap)+d.width>maxWidth){
        rows.add(new Row(cs,width,height));
        cs=new ArrayList<>();
        width=0;
        height=0;
      }
      if (!cs.isEmpty()){ width+=w(gap.widthGap); }
      cs.add(c);
      width+=d.width;
      height=Math.max(height,d.height);
    }

    if (!cs.isEmpty()){ rows.add(new Row(cs,width,height)); }
    return rows;
  }

  private int blockHeight(List<Row> rows){
    return rows.stream().mapToInt(Row::height).sum()
      +h(gap.heightGap)*Math.max(0,rows.size()-1);
  }

  private void layoutRow(Area area,int y,Row row){
    int x=switch(getAlignment()){
      case FlowLayout.LEFT,FlowLayout.LEADING -> 0;
      case FlowLayout.CENTER -> (area.width()-row.width())/2;
      case FlowLayout.RIGHT,FlowLayout.TRAILING -> area.width()-row.width();
      default -> throw new AssertionError(getAlignment());
    };

    for (var c:row.cs()){
      var d=c.getPreferredSize();
      c.setBounds(
        area.x()+x,
        y+(row.height()-d.height)/2,
        d.width,
        d.height
        );
      x+=d.width+w(gap.widthGap);
    }
  }

  private Area area(Container target){
    var in=target.getInsets();
    return new Area(
      in.left+w(gap.left),
      in.top+h(gap.top),
      target.getWidth()-in.left-in.right-w(gap.left)-w(gap.right),
      target.getHeight()-in.top-in.bottom-h(gap.top)-h(gap.bottom)
      );
  }

  private record Row(List<Component> cs,int width,int height){}
  private record Area(int x,int y,int width,int height){}

  private interface Dim{
    java.awt.Dimension of(Component c);
  }
}