package dhbart.portfolioapi.support.icons.storage;

import java.io.IOException;
import java.nio.file.Path;

public interface IconStorage {
    boolean exists(String slug);
    void store(String slug, byte[] content, boolean overwrite) throws IOException;
    Path location(String slug);
}
