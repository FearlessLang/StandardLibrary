package _base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.Locale;

import static _base.Util.*;

final class DownloadFetch{
  static final int maxRedirects= 5;
  static final Duration connectTimeout= Duration.ofSeconds(30);
  static final Duration responseTimeout= Duration.ofSeconds(30);

  static final HttpClient client= HttpClient.newBuilder()
    .connectTimeout(connectTimeout)
    .followRedirects(HttpClient.Redirect.NEVER)
    .build();

  static byte[] bytes(String url, long maxBytes){
    int redirectsLeft= maxRedirects;
    for (;;){
      var uri= validate(url);
      HttpResponse<InputStream> resp= send(url, uri);
      int status= resp.statusCode();
      if (300 <= status && status < 400){
        closeQuietly(resp);
        var locationOpt= resp.headers().firstValue("Location");
        if (locationOpt.isEmpty()){ throw downloadFailed(url, "redirect with no Location header"); }
        var location= locationOpt.get();
        if (redirectsLeft == 0){ throw tooManyRedirects(url); }
        redirectsLeft -= 1;
        url = redirect(url, uri, location);
        continue;
      }
      if (status < 200 || status >= 300){
        closeQuietly(resp);
        throw httpStatus(url, status);
      }
      checkContentLength(url, resp, maxBytes);
      try (var in= resp.body()){ return readBounded(url, in, maxBytes); }
      catch (IOException e){ throw networkFailed(url, "the connection ended before the whole response arrived"); }
    }
  }

  static String redirect(String url, URI uri, String location){
    try{ return uri.resolve(new URI(location)).toASCIIString(); }
    catch (URISyntaxException e){ throw downloadFailed(url, "redirect to a malformed Location: "+location); }
  }

  static HttpResponse<InputStream> send(String url, URI uri){
    var req= HttpRequest.newBuilder(uri).timeout(responseTimeout).GET().build();
    try{ return client.send(req, HttpResponse.BodyHandlers.ofInputStream()); }
    catch (HttpTimeoutException e){ throw downloadTimedOut(url, responseTimeout.toMillis()); }
    catch (ConnectException e){ throw networkFailed(url, "the connection to the host could not be opened"); }
    catch (ProtocolException e){ throw networkFailed(url, "the host did not answer with a valid HTTP response"); }
    catch (IOException e){ throw networkFailed(url, "the connection ended before the whole response arrived"); }
    catch (IllegalArgumentException e){ throw networkFailed(url, "the Content-Length of the answer is not a number"); }
    catch (InterruptedException e){
      Thread.currentThread().interrupt();
      throw downloadFailed(url, e.toString());
    }
  }

  static void closeQuietly(HttpResponse<InputStream> resp){
    try{ resp.body().close(); }
    catch (IOException ignored){}
  }

  static void checkContentLength(String url, HttpResponse<InputStream> resp, long maxBytes){
    long contentLength= resp.headers().firstValueAsLong("Content-Length").orElse(-1);
    if (contentLength < 0){ return; }
    if (Long.compareUnsigned(contentLength, maxBytes) <= 0){ return; }
    closeQuietly(resp);
    throw exceedsMaxBytes(url, "contentLength", contentLength, maxBytes);
  }

  static byte[] readBounded(String url, InputStream in, long maxBytes) throws IOException{
    var buf= new ByteArrayOutputStream();
    var chunk= new byte[8192];
    long total= 0;
    int n;
    while ((n = in.read(chunk)) != -1){
      total = Math.addExact(total, n);
      if (Long.compareUnsigned(total, maxBytes) > 0){ throw exceedsMaxBytes(url, "bytesRead", total, maxBytes); }
      buf.write(chunk, 0, n);
    }
    return buf.toByteArray();
  }

  static URI validate(String url){
    URI uri;
    try{ uri= new URI(url); }
    catch (URISyntaxException e){ throw invalidUrl(url, "not a valid URI according to RFC 2396 and RFC 2732"); }
    if (!uri.isAbsolute()){ throw invalidUrl(url, "not an absolute URI (missing scheme)"); }
    var scheme= uri.getScheme().toLowerCase(Locale.ROOT);
    if (!scheme.equals("http") && !scheme.equals("https")){ throw badScheme(url, uri.getScheme()); }
    if (uri.getHost() == null){ throw invalidUrl(url, "missing host"); }
    if (uri.getPort() > 65535){ throw invalidUrl(url, "port out of range: "+uri.getPort()); }
    return uri;
  }

  static RuntimeException invalidUrl(String url, String reason){
    throw detErr("Invalid URL descriptor.\n"+reason+"\nurl: "+url);
  }
  static RuntimeException badScheme(String url, String scheme){
    throw detErr("Invalid URL descriptor.\nunsupported scheme (only http/https allowed): "+scheme+" in "+url);
  }
  static RuntimeException downloadFailed(String url, String reason){
    throw detErr("Download failed.\nurl: "+url+"\n"+reason);
  }
  static RuntimeException networkFailed(String url, String reason){
    throw nonDetErr("Download failed.\nurl: "+url+"\n"+reason);
  }
  static RuntimeException downloadTimedOut(String url, long timeoutMs){
    throw detErr("Download timed out.\nurl: "+url+"\ntimeoutMs: "+timeoutMs);
  }
  static RuntimeException httpStatus(String url, int status){
    throw detErr("Download failed.\nurl: "+url+"\nHTTP status: "+status);
  }
  static RuntimeException tooManyRedirects(String url){
    throw detErr("Download failed.\nurl: "+url+"\ntoo many redirects");
  }
  static RuntimeException exceedsMaxBytes(String url, String observedLabel, long observed, long maxBytes){
    throw detErr(
      "Download exceeds maxBytes.\nurl: "+url
      +"\n"+observedLabel+": "+Long.toUnsignedString(observed)
      +"\nmaxBytes: "+Long.toUnsignedString(maxBytes)
      );
  }
}
