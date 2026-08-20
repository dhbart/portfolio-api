package dhbart.portfolioapi.support.icons.downloader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpIconDownloader implements IconDownloader {
    private final HttpClient httpClient;

    public HttpIconDownloader() {
        this(HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15)).build());
    }

    public HttpIconDownloader(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public byte[] download(URI location) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(location).timeout(Duration.ofSeconds(30))
                .header("Accept", "image/svg+xml").GET().build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Devicon returned HTTP " + response.statusCode() + " for " + location);
        }
        var text = new String(response.body(), java.nio.charset.StandardCharsets.UTF_8).stripLeading();
        if (!text.startsWith("<svg") && !text.startsWith("<?xml")) {
            throw new IOException("Downloaded content is not an SVG: " + location);
        }
        return response.body();
    }
}
