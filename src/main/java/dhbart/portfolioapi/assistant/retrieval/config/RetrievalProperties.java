package dhbart.portfolioapi.assistant.retrieval.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "assistant.ai.retrieval")
public record RetrievalProperties(int topK, int maxContextLength) {
}
