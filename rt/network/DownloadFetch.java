package _base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.SSLException;

import static _base.Util.*;

final class DownloadFetch{
  static final Duration idleTimeout= Duration.ofSeconds(30);
  static final Set<Integer> redirects= Set.of(301, 302, 303, 307, 308);
  static final ScheduledThreadPoolExecutor alarms= new ScheduledThreadPoolExecutor(1, Thread.ofVirtual().factory());
  static{ alarms.setRemoveOnCancelPolicy(true); }

  static final HttpClient client= HttpClient.newBuilder()
    .version(HttpClient.Version.HTTP_1_1)
    .followRedirects(HttpClient.Redirect.NEVER)
    .build();

  static byte[] bytes(Object url, long maxBytes, Object maker){
    var follower= (RedirectFollower$23y8$0) ((F$3$1) maker).read$$hash$0();
    for (;;){
      var text= textOf(url);
      var resp= send(text);
      try (var in= resp.body()){
        int status= resp.statusCode();
        if (redirects.contains(status)){
          var locations= resp.headers().allValues("Location");
          if (locations.size() != 1){ throw downloadFailed(text, "HTTP status: "+status+"\na redirect has one Location header, but this redirect has "+locations.size()); }
          url = follow(url, follower, locations.getFirst());
          continue;
        }
        if (status < 200 || status >= 300){ throw downloadFailed(text, "HTTP status: "+status); }
        return readBounded(text, in, maxBytes);
      }
      catch (IOException e){ throw failed(text, e); }
    }
  }

  static String textOf(Object url){ return AssetBytesRead.str(((Url$c$0) url).imm$text$0()); }

  static Object follow(Object url, RedirectFollower$23y8$0 follower, String location){
    var text= textOf(url);
    var bad= location.chars().filter(c -> c < 0x20 || c > 0x7E).findFirst();
    if (bad.isPresent()){ throw downloadFailed(text, String.format(Locale.ROOT, "the Location of the redirect holds the byte %02X, which is not a character of Str; the characters of Str are U+000A and U+0020 to U+007E", bad.getAsInt())); }
    var ls= Str$c$0Instance.instance(location);
    try{ return follower.mut$$hash$2(url, ls); }
    catch (Deterministic d){
      d.i= (Info$o$0) d.i.imm$$plus$1(Infos$1c$0.instance.imm$map$4(Str$c$0Instance.instance("from"), Str$c$0Instance.instance(text), Str$c$0Instance.instance("location"), ls));
      throw d;
    }
  }

  static HttpResponse<InputStream> send(String url){
    var answer= client.sendAsync(HttpRequest.newBuilder(URI.create(url)).GET().build(), HttpResponse.BodyHandlers.ofInputStream());
    try{ return answer.get(idleTimeout.toMillis(), TimeUnit.MILLISECONDS); }
    catch (TimeoutException e){
      answer.cancel(true);
      throw timedOut(url, "the answer did not start within "+idleTimeout.toMillis()+" milliseconds");
    }
    catch (ExecutionException e){ throw failed(url, e.getCause()); }
    catch (InterruptedException e){
      Thread.currentThread().interrupt();
      throw networkFailed(url, "the download was interrupted");
    }
  }

  static Error failed(String url, Throwable t){
    return switch (t){
      case ConnectException _ -> networkFailed(url, "the connection to the host could not be opened");
      case ProtocolException _ -> networkFailed(url, "the host did not answer with a valid HTTP response");
      case SSLException _ -> networkFailed(url, "the TLS handshake with the host failed");
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
        if (idle.get()){ throw timedOut(url, "no byte arrived for "+idleTimeout.toMillis()+" milliseconds"); }
        throw e;
      }
      finally{ alarm.cancel(false); }
      if (n == -1){ return buf.toByteArray(); }
      total += n;
      if (Long.compareUnsigned(total, maxBytes) > 0){ throw detErr("Download exceeds maxBytes.\nurl: "+url+"\nmaxBytes: "+Long.toUnsignedString(maxBytes)); }
      buf.write(chunk, 0, n);
    }
  }

  static Void close(InputStream in, AtomicBoolean idle) throws IOException{
    idle.set(true);
    in.close();
    return null;
  }

  static Error downloadFailed(String url, String reason){ return detErr("Download failed.\nurl: "+url+"\n"+reason); }
  static Error networkFailed(String url, String reason){ return nonDetErr("Download failed.\nurl: "+url+"\n"+reason); }
  static Error timedOut(String url, String reason){ return nonDetErr("Download timed out.\nurl: "+url+"\n"+reason); }
}
