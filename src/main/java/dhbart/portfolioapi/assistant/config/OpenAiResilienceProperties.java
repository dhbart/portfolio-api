package dhbart.portfolioapi.assistant.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "portfolio.ai.resilience")
public record OpenAiResilienceProperties(Duration timeout, int retryAttempts, Duration initialBackoff,
                                         float failureRateThreshold, int slidingWindowSize,
                                         int bulkheadMaxConcurrentCalls) {
}
