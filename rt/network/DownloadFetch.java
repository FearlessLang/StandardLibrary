package _base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import static _base.Util.*;

final class DownloadFetch{
  static final int maxRedirects= 5;
  static final Duration connectTimeout= Duration.ofSeconds(30);
  static final Duration idleTimeout= Duration.ofSeconds(30);
  static final ScheduledExecutorService alarms= Executors.newSingleThreadScheduledExecutor(Thread.ofVirtual().factory());

  static final HttpClient client= HttpClient.newBuilder()
    .connectTimeout(connectTimeout)
    .followRedirects(HttpClient.Redirect.NEVER)
    .build();

  static byte[] bytes(String url, long maxBytes){
    int redirectsLeft= maxRedirects;
    for (;;){
      var uri= validate(url);
      var resp= send(url, uri);
      try (var in= resp.body()){
        int status= resp.statusCode();
        if (300 <= status && status < 400){
          var location= resp.headers().firstValue("Location");
          if (location.isEmpty()){ throw downloadFailed(url, "redirect with no Location header"); }
          if (redirectsLeft == 0){ throw downloadFailed(url, "too many redirects"); }
          redirectsLeft -= 1;
          url = redirect(url, uri, location.get());
          continue;
        }
        if (status < 200 || status >= 300){ throw downloadFailed(url, "HTTP status: "+status); }
        return readBounded(url, in, maxBytes);
      }
      catch (IOException e){ throw networkFailed(url, "the connection ended before the whole response arrived"); }
    }
  }

  static String redirect(String url, URI uri, String location){
    URI next;
    try{ next= uri.resolve(new URI(location)); }
    catch (URISyntaxException e){ throw downloadFailed(url, "redirect to a malformed Location: "+location); }
    var toHttp= uri.getScheme().equalsIgnoreCase("https") && "http".equalsIgnoreCase(next.getScheme());
    if (toHttp){ throw downloadFailed(url, "redirect from https to http: "+next.toASCIIString()); }
    return next.toASCIIString();
  }

  static HttpResponse<InputStream> send(String url, URI uri){
    var answer= client.sendAsync(HttpRequest.newBuilder(uri).GET().build(), HttpResponse.BodyHandlers.ofInputStream());
    try{ return answer.get(idleTimeout.toMillis(), TimeUnit.MILLISECONDS); }
    catch (TimeoutException e){
      answer.cancel(true);
      throw timedOut(url);
    }
    catch (ExecutionException e){ throw failed(url, e.getCause()); }
    catch (InterruptedException e){
      Thread.currentThread().interrupt();
      throw downloadFailed(url, "the download was interrupted");
    }
  }

  static Error failed(String url, Throwable t){
    return switch (t){
      case HttpTimeoutException _ -> timedOut(url);
      case ConnectException _ -> networkFailed(url, "the connection to the host could not be opened");
      case ProtocolException _ -> networkFailed(url, "the host did not answer with a valid HTTP response");
      case IOException e when e.getCause() instanceof IllegalArgumentException -> networkFailed(url, "the Content-Length of the answer is not a number");
      case IOException _ -> networkFailed(url, "the connection ended before the whole response arrived");
      default -> throw new Error(t);
    };
  }

  static byte[] readBounded(String url, InputStream in, long maxBytes) throws IOException{
    var buf= new ByteArrayOutputStream();
    var chunk= new byte[8192];
    var idle= new AtomicBoolean();
    long total= 0;
    for (;;){
      var alarm= alarms.schedule(() -> close(in, idle), idleTimeout.toMillis(), TimeUnit.MILLISECONDS);
      int n;
      try{ n= in.read(chunk); }
      catch (IOException e){
        if (idle.get()){ throw timedOut(url); }
        throw e;
      }
      finally{ alarm.cancel(false); }
      if (n == -1){ return buf.toByteArray(); }
      total = Math.addExact(total, n);
      if (Long.compareUnsigned(total, maxBytes) > 0){
        throw detErr("Download exceeds maxBytes.\nurl: "+url+"\nbytesRead: "+Long.toUnsignedString(total)+"\nmaxBytes: "+Long.toUnsignedString(maxBytes));
      }
      buf.write(chunk, 0, n);
    }
  }

  static void close(InputStream in, AtomicBoolean idle){
    idle.set(true);
    try{ in.close(); }
    catch (IOException e){ throw new UncheckedIOException(e); }
  }

  static URI validate(String url){
    URI uri;
    try{ uri= new URI(url); }
    catch (URISyntaxException e){ throw invalidUrl(url, "not a valid URI according to RFC 2396 and RFC 2732"); }
    if (!uri.isAbsolute()){ throw invalidUrl(url, "not an absolute URI (missing scheme)"); }
    var scheme= uri.getScheme().toLowerCase(Locale.ROOT);
    if (!scheme.equals("http") && !scheme.equals("https")){ throw detErr("Invalid URL descriptor.\nunsupported scheme (only http/https allowed): "+uri.getScheme()+" in "+url); }
    if (uri.getHost() == null){ throw invalidUrl(url, "missing host"); }
    if (uri.getPort() > 65535){ throw invalidUrl(url, "port out of range: "+uri.getPort()); }
    return uri;
  }

  static Error invalidUrl(String url, String reason){ return detErr("Invalid URL descriptor.\n"+reason+"\nurl: "+url); }
  static Error downloadFailed(String url, String reason){ return detErr("Download failed.\nurl: "+url+"\n"+reason); }
  static Error networkFailed(String url, String reason){ return nonDetErr("Download failed.\nurl: "+url+"\n"+reason); }
  static Error timedOut(String url){ return detErr("Download timed out.\nurl: "+url+"\nno byte arrived for "+idleTimeout.toMillis()+" milliseconds"); }
}
