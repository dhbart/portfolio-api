package dhbart.portfolioapi.assistant.prompt;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class PromptLoader {

    private final ResourceLoader resourceLoader;

    public PromptLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String load(String promptPath) {
        try (var inputStream = resourceLoader
                .getResource("classpath:" + promptPath)
                .getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new UncheckedIOException("Unable to load prompt: " + promptPath, exception);
        }
    }
}
