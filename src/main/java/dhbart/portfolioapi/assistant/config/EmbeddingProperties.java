package dhbart.portfolioapi.assistant.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "assistant.ai.embedding")
public record EmbeddingProperties(
        String apiKey,
        String endpoint,
        String model,
        Duration timeout,
        int maxAttempts,
        Duration initialBackoff) {
}
