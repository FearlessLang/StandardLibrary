package base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.Locale;

import static base.Util.*;

final class DownloadFetch{
  private DownloadFetch(){ throw new AssertionError(); }

  static final int maxRedirects= 5;
  static final Duration connectTimeout= Duration.ofSeconds(30);
  static final Duration responseTimeout= Duration.ofSeconds(30);

  static final HttpClient client= HttpClient.newBuilder()
    .connectTimeout(connectTimeout)
    .followRedirects(HttpClient.Redirect.NEVER)
    .build();

  static byte[] bytes(String url,long maxBytes){
    String current= url;
    int redirectsLeft= maxRedirects;
    for (;;){
      var uri= validate(current);
      HttpResponse<InputStream> resp= send(current,uri);
      int status= resp.statusCode();
      if (300 <= status && status < 400){
        closeQuietly(resp);
        var locationOpt= resp.headers().firstValue("Location");
        if (locationOpt.isEmpty()){ throw downloadFailed(current,"redirect with no Location header"); }
        var location= locationOpt.get();
        if (redirectsLeft == 0){ throw tooManyRedirects(url); }
        redirectsLeft--;
        current= uri.resolve(location).toString();
        continue;
      }
      if (status < 200 || status >= 300){
        closeQuietly(resp);
        throw httpStatus(url,status);
      }
      checkContentLength(url,resp,maxBytes);
      try(var in= resp.body()){ return readBounded(url,in,maxBytes); }
      catch(IOException e){ throw downloadFailed(url,e.toString()); }
    }
  }

  static HttpResponse<InputStream> send(String url,URI uri){
    var req= HttpRequest.newBuilder(uri).timeout(responseTimeout).GET().build();
    try{ return client.send(req,HttpResponse.BodyHandlers.ofInputStream()); }
    catch(HttpTimeoutException e){ throw downloadTimedOut(url,responseTimeout.toMillis()); }
    catch(IOException e){ throw downloadFailed(url,e.toString()); }
    catch(InterruptedException e){
      Thread.currentThread().interrupt();
      throw downloadFailed(url,e.toString());
    }
  }

  static void closeQuietly(HttpResponse<InputStream> resp){
    try{ resp.body().close(); }
    catch(IOException ignored){}
  }

  static void checkContentLength(String url,HttpResponse<InputStream> resp,long maxBytes){
    long contentLength;
    try{ contentLength= resp.headers().firstValueAsLong("Content-Length").orElse(-1); }
    catch(NumberFormatException e){ return; }
    if (contentLength < 0){ return; }
    if (Long.compareUnsigned(contentLength,maxBytes) <= 0){ return; }
    closeQuietly(resp);
    throw exceedsMaxBytes(url,"contentLength",contentLength,maxBytes);
  }

  static byte[] readBounded(String url,InputStream in,long maxBytes) throws IOException{
    var buf= new ByteArrayOutputStream();
    var chunk= new byte[8192];
    long total= 0;
    int n;
    while ((n= in.read(chunk)) != -1){
      total= Math.addExact(total,n);
      if (Long.compareUnsigned(total,maxBytes) > 0){ throw exceedsMaxBytes(url,"bytesRead",total,maxBytes); }
      buf.write(chunk,0,n);
    }
    return buf.toByteArray();
  }

  static URI validate(String url){
    URI uri;
    try{ uri= new URI(url); }
    catch(URISyntaxException e){ throw invalidUrl(url,e.getReason()); }
    if (!uri.isAbsolute()){ throw invalidUrl(url,"not an absolute URI (missing scheme)"); }
    var scheme= uri.getScheme().toLowerCase(Locale.ROOT);
    if (!scheme.equals("http") && !scheme.equals("https")){ throw badScheme(url,uri.getScheme()); }
    if (uri.getHost() == null){ throw invalidUrl(url,"missing host"); }
    return uri;
  }

  static RuntimeException invalidUrl(String url,String reason){
    throw nonDetErr("Invalid URL descriptor.\n"+reason+"\nurl: "+url);
  }
  static RuntimeException badScheme(String url,String scheme){
    throw nonDetErr("Invalid URL descriptor.\nunsupported scheme (only http/https allowed): "+scheme+" in "+url);
  }
  static RuntimeException downloadFailed(String url,String reason){
    throw nonDetErr("Download failed.\nurl: "+url+"\n"+reason);
  }
  static RuntimeException downloadTimedOut(String url,long timeoutMs){
    throw nonDetErr("Download timed out.\nurl: "+url+"\ntimeoutMs: "+timeoutMs);
  }
  static RuntimeException httpStatus(String url,int status){
    throw nonDetErr("Download failed.\nurl: "+url+"\nHTTP status: "+status);
  }
  static RuntimeException tooManyRedirects(String url){
    throw nonDetErr("Download failed.\nurl: "+url+"\ntoo many redirects");
  }
  static RuntimeException exceedsMaxBytes(String url,String observedLabel,long observed,long maxBytes){
    throw nonDetErr(
      "Download exceeds maxBytes.\nurl: "+url
      +"\n"+observedLabel+": "+Long.toUnsignedString(observed)
      +"\nmaxBytes: "+Long.toUnsignedString(maxBytes)
      );
  }
}
