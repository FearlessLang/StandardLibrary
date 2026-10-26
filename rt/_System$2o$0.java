package _base;
import java.util.*;

public final class _System$2o$0 implements System$2o$0{
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$try$0(){ return new _CapTry$2s$0(); }
  @Override public Object mut$out$0(){ return new _CapOutput$m8$0(); }
  @Override public Object mut$inputCursor$0(){ return new _CapInputCursor$js0$0(); }
  @Override public Object mut$readLog$1(Object inMemLog){
    return new _CapLogReader$4yo$1((InMemoryLog$2kk$1)inMemLog); }
  @Override public Object mut$assetRead$0(){ return new _CapAssetRead$4y0$0(); }
  @Override public Object mut$download$0(){ return new _CapDownload$2gw$0(); }
  @Override public Object mut$dataRead$1(Object data){ return new _CapDataRead$2h4$0(data); }
  @Override public Object mut$dataEdit$1(Object data){ return new _CapDataEdit$2h4$0(data); }
  @Override public Object mut$manager$0(){ return new _CapManager$18g$0(); }
  @Override public Object mut$process$0(){ return new _CapProcess$18g$0(); }

  @Override public Object mut$gui$0(){ return new _FluentGUI(); }
  @Override public Object mut$revocation$0(){ return new _CapRevocationRoot$4e14$0(); }

}