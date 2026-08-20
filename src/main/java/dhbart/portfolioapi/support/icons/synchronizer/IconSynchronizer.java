package dhbart.portfolioapi.support.icons.synchronizer;

import dhbart.portfolioapi.support.icons.downloader.IconDownloader;
import dhbart.portfolioapi.support.icons.provider.IconProvider;
import dhbart.portfolioapi.support.icons.storage.IconStorage;
import dhbart.portfolioapi.technology.domain.model.Technology;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IconSynchronizer {
    private final IconProvider provider;
    private final IconDownloader downloader;
    private final IconStorage storage;

    public IconSynchronizer(IconProvider provider, IconDownloader downloader, IconStorage storage) {
        this.provider = provider;
        this.downloader = downloader;
        this.storage = storage;
    }

    public IconSynchronizationReport synchronize(List<Technology> technologies, boolean overwrite) {
        var unsupported = new ArrayList<String>();
        var failures = new ArrayList<String>();
        var downloaded = 0;
        var skipped = 0;
        for (var technology : technologies) {
            var slug = technology.getSlug();
            if (!overwrite && storage.exists(slug)) {
                skipped++;
                continue;
            }
            var location = provider.resolve(technology);
            if (location.isEmpty()) {
                unsupported.add(slug);
                continue;
            }
            try {
                storage.store(slug, downloader.download(location.get()), overwrite);
                downloaded++;
            } catch (IOException | InterruptedException | RuntimeException exception) {
                if (exception instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                failures.add(slug + ": " + exception.getMessage());
            }
        }
        return new IconSynchronizationReport(technologies.size(), downloaded, skipped,
                List.copyOf(unsupported), List.copyOf(failures));
    }
}
