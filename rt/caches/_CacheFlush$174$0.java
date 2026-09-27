package _base;

public interface _CacheFlush$174$0{
  default Object imm$flush$1(Object p0){
    switch(p0){
      case CacheMemo$lk$3 c-> c._cache2().map().clear();
      case CacheMemo$lk$2 c-> c._cache1().map().clear();
      case CacheMemo$lk$1 c-> c._cache0().entry().set(null);
      case CacheF$2p$3 c-> c._cache2().map().clear();
      case CacheF$2p$2 c-> c._cache1().map().clear();
      case CacheF$2p$1 c-> c._cache0().entry().set(null);
      default->{}
    }
    return _base.Void$o$0.instance;
  }
  //repr caches are flushed by repr instead
  //CacheReprF$175$3
  //CacheReprF$175$2
  //TODO: either give dynamic error on those or change common superinterface to restrict passing them in
  _CacheFlush$174$0 instance= new _CacheFlush$174$0(){};}