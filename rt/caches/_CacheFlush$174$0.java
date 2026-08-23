package base;

public interface _CacheFlush$174$0{
  default Object imm$flush$1(Object p0){
    switch(p0){
      case CacheMemo$lk$3 _-> {var e= CacheMemo$lk$3.myCache.get(p0); if (e != null){ e.map().clear(); }}
      case CacheMemo$lk$2 _-> {var e= CacheMemo$lk$2.myCache.get(p0); if (e != null){ e.map().clear(); }}
      case CacheMemo$lk$1 _-> {var e= CacheMemo$lk$1.myCache.get(p0); if (e != null){ e.entry().set(null); }}
      case CacheF$2p$3 _-> {var e= CacheF$2p$3.myCache.get(p0); if (e != null){ e.map().clear(); }}
      case CacheF$2p$2 _-> {var e= CacheF$2p$2.myCache.get(p0); if (e != null){ e.map().clear(); }}
      case CacheF$2p$1 _-> {var e= CacheF$2p$1.myCache.get(p0); if (e != null){ e.entry().set(null); }}
      default->{}
    }
    return base.Void$o$0.instance;
  }
  //repr caches are flushed by repr instead
  //CacheReprF$175$3
  //CacheReprF$175$2
  //TODO: either give dynamic error on those or change common superinterface to restrict passing them in
  _CacheFlush$174$0 instance= new _CacheFlush$174$0(){};}