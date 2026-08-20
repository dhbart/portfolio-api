package dhbart.portfolioapi.support.icons.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileIconStorage implements IconStorage {
    private final Path directory;

    public FileIconStorage(Path directory) {
        this.directory = directory;
    }

    @Override
    public boolean exists(String slug) {
        return Files.isRegularFile(location(slug));
    }

    @Override
    public void store(String slug, byte[] content, boolean overwrite) throws IOException {
        Files.createDirectories(directory);
        var options = overwrite
                ? new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING}
                : new StandardOpenOption[]{StandardOpenOption.CREATE_NEW};
        Files.write(location(slug), content, options);
    }

    @Override
    public Path location(String slug) {
        if (!slug.matches("[a-z0-9]+(?:-[a-z0-9]+)*")) {
            throw new IllegalArgumentException("Invalid technology slug: " + slug);
        }
        return directory.resolve(slug + ".svg");
    }
}
