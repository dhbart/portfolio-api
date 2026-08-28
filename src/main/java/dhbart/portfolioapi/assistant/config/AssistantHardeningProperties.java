package dhbart.portfolioapi.assistant.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "portfolio.ai.hardening")
public record AssistantHardeningProperties(int maxMessageLength, int maxPromptLength,
                                           boolean rejectInjectionAttempts) {
}
