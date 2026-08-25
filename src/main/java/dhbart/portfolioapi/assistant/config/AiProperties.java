package dhbart.portfolioapi.assistant.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "portfolio.ai")
public record AiProperties(
        boolean enabled,
        String model,
        double temperature,
        int maxTokens,
        String systemPrompt) {
}
