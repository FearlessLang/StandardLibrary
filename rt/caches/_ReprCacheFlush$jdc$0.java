package base;
public interface _ReprCacheFlush$jdc$0{
  default Object imm$$hash$1(Object p0){
    {var e= CacheReprF$175$2.myCache.get(p0); if (e != null){ e.map().clear(); }}//TODO: add more lines when more cache arities are added
    {var e= CacheReprF$175$3.myCache.get(p0); if (e != null){ e.map().clear(); }}
    return base.Void$o$0.instance;
  }
  _ReprCacheFlush$jdc$0 instance= new _ReprCacheFlush$jdc$0(){};}