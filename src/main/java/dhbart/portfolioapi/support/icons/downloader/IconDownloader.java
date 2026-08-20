package dhbart.portfolioapi.support.icons.downloader;

import java.io.IOException;
import java.net.URI;

public interface IconDownloader {
    byte[] download(URI location) throws IOException, InterruptedException;
}
