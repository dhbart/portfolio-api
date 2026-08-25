package dhbart.portfolioapi.assistant.retrieval.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "portfolio.retrieval")
public record RetrievalProperties(
        boolean enabled,
        String vectorStore,
        int defaultTopK,
        double minimumScore) {
}
