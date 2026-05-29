package base;

public interface _CacheFlush$0{
  default Object imm$flush$1(Object p0){
    switch(p0){
      case CacheMemo$3 _-> {var e= CacheMemo$3.myCache.get(p0); if (e != null){ e.map().clear(); }}
      case CacheMemo$2 _-> {var e= CacheMemo$2.myCache.get(p0); if (e != null){ e.map().clear(); }}
      case CacheMemo$1 _-> {var e= CacheMemo$1.myCache.get(p0); if (e != null){ e.entry().set(null); }}
      case CacheF$3 _-> {var e= CacheF$3.myCache.get(p0); if (e != null){ e.map().clear(); }}
      case CacheF$2 _-> {var e= CacheF$2.myCache.get(p0); if (e != null){ e.map().clear(); }}
      case CacheF$1 _-> {var e= CacheF$1.myCache.get(p0); if (e != null){ e.entry().set(null); }}
      default->{}
    }
    return base.Void$0.instance;
  }
  //repr caches are flushed by repr instead
  //CacheReprF$3
  //CacheReprF$2
  //TODO: either give dynamic error on those or change common superinterface to restrict passing them in
  _CacheFlush$0 instance= new _CacheFlush$0(){};}