package base;
import java.util.*;

public final class _System$2o$0 implements System$2o$0{
  @Override public final Object mut$iso$0(){ return this; }
  @Override public final Object mut$close$0(){ return this; }
  @Override public Object mut$try$0(){ return new _CapTry$2s$0(); }
  @Override public Object mut$out$0(){ return new _Output$2o$0(); }
  @Override public Object mut$inputCursor$0(){ return new _InputCursor$2e8$0(); }
  @Override public Object mut$readLog$1(Object inMemLog){
    return new _LogReader$m8$1((InMemoryLog$2kk$1)inMemLog,_InMemoryLogs.of(inMemLog)); }
  @Override public Object mut$assetRead$0(){ return new _AssetReadCapability$h1j4$0(); }
  @Override public Object mut$download$0(){ return new _DownloadCapability$8fsw$0(); }

  @Override public Object mut$gui$0(){ return new _FluentGUI(); }

}