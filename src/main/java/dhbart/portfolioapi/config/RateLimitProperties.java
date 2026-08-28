package dhbart.portfolioapi.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "portfolio.rate-limit.assistant")
public record RateLimitProperties(int capacity, Duration window, Duration retryAfter) {
}
