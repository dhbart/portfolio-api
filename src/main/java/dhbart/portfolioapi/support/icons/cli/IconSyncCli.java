package dhbart.portfolioapi.support.icons.cli;

import dhbart.portfolioapi.support.icons.downloader.HttpIconDownloader;
import dhbart.portfolioapi.support.icons.provider.DeviconProvider;
import dhbart.portfolioapi.support.icons.storage.FileIconStorage;
import dhbart.portfolioapi.support.icons.synchronizer.IconSynchronizer;
import java.nio.file.Path;

public final class IconSyncCli {
    private IconSyncCli() {
    }

    public static void main(String[] args) throws Exception {
        var projectRoot = Path.of("").toAbsolutePath().normalize();
        var seed = projectRoot.resolve(
                "src/main/resources/db/migration/V12__seed_technology_and_project_technology.sql");
        var storagePath = projectRoot.resolve("src/main/resources/static/icons/technologies");
        var overwrite = false;
        for (var arg : args) {
            if (arg.equals("--overwrite")) {
                overwrite = true;
            } else if (arg.startsWith("--seed=")) {
                seed = projectRoot.resolve(arg.substring("--seed=".length())).normalize();
            } else if (arg.startsWith("--storage=")) {
                storagePath = projectRoot.resolve(arg.substring("--storage=".length())).normalize();
            }
        }
        var technologies = new TechnologySeedReader().read(seed);
        var report = new IconSynchronizer(new DeviconProvider(), new HttpIconDownloader(),
                new FileIconStorage(storagePath)).synchronize(technologies, overwrite);
        System.out.printf("Icons: total=%d, downloaded=%d, skipped=%d%n",
                report.total(), report.downloaded(), report.skipped());
        if (!report.unsupported().isEmpty()) {
            System.out.println("Unsupported by Devicon: " + String.join(", ", report.unsupported()));
        }
        if (!report.failures().isEmpty()) {
            System.out.println("Failures:");
            report.failures().forEach(failure -> System.out.println("- " + failure));
            throw new IllegalStateException("Icon synchronization failed for one or more technologies");
        }
    }
}
