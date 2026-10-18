package _base;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static _base.Util.*;

public final class _InputCursor$2e8$0 implements InputCursor$2e8$0{
  static final List<Path> files= opened();
  private static List<Path> opened(){
    var res= new CopyOnWriteArrayList<Path>();
    var file= System.getProperty("fearlessInput.file");
    if (file != null){ res.add(Path.of(file)); }
    return res;
  }
  private final int at;
  private _InputCursor$2e8$0(int at){ this.at= at; }
  public _InputCursor$2e8$0(){ this(0); }
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$$hash$0(){ return at < files.size() ? optSome(new InputFile(files.get(at))) : optEmpty(); }
  @Override public Object mut$next$0(){ return new _InputCursor$2e8$0(at + 1); }
}
