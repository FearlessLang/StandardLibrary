package _base;
import java.util.*;

public final class _System$2o$0 implements System$2o$0{
  @Override public Object mut$iso$0(){ return this; }
  @Override public Object mut$close$0(){ return this; }
  @Override public Object mut$try$0(){ return new _CapTry$2s$0(); }
  @Override public Object mut$out$0(){ return new _Output$2o$0(); }
  @Override public Object mut$inputCursor$0(){ return new _InputCursor$2e8$0(); }
  @Override public Object mut$readLog$1(Object inMemLog){
    return new _LogReader$m8$1((InMemoryLog$2kk$1)inMemLog); }
  @Override public Object mut$assetRead$0(){ return new _AssetReadCapability$h1j4$0(); }
  @Override public Object mut$download$0(){ return new _DownloadCapability$8fsw$0(); }
  @Override public Object mut$dataRead$0(){ return new _CapDataRead$2h4$0(); }
  @Override public Object mut$dataReadWrite$0(){ return new _CapDataReadWrite$2780$0(); }
  @Override public Object mut$manager$0(){ return new _CapManager$18g$0(); }
  @Override public Object mut$process$0(){ return new _CapProcess$18g$0(); }

  @Override public Object mut$gui$0(){ return new _FluentGUI(); }
  @Override public Object mut$revocation$0(){ return new _RevocationRoot$iyw$0(); }

}