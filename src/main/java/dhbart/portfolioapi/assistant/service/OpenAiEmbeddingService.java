package dhbart.portfolioapi.assistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dhbart.portfolioapi.assistant.config.EmbeddingProperties;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OpenAiEmbeddingService implements EmbeddingService {

    private static final Logger log = LoggerFactory.getLogger(OpenAiEmbeddingService.class);

    private final EmbeddingProperties properties;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public OpenAiEmbeddingService(EmbeddingProperties properties, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder().connectTimeout(properties.timeout()).build();
    }

    @Override
    public float[] generate(String text) {
        if (properties.apiKey() == null || properties.apiKey().isBlank()) {
            throw new IllegalStateException("OPENAI_API_KEY is not configured");
        }

        String payload;
        try {
            payload = objectMapper.writeValueAsString(new EmbeddingRequest(properties.model(), text));
        } catch (IOException exception) {
            throw new EmbeddingProviderException("Could not serialize embedding request", exception);
        }

        long backoffMillis = properties.initialBackoff().toMillis();
        for (int attempt = 1; attempt <= properties.maxAttempts(); attempt++) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(properties.endpoint()))
                        .timeout(properties.timeout())
                        .header("Authorization", "Bearer " + properties.apiKey())
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(payload))
                        .build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() >= 200 && response.statusCode() < 300) {
                    logTokenUsage(response.body());
                    return parseEmbedding(response.body());
                }
                if (!isRetryable(response.statusCode()) || attempt == properties.maxAttempts()) {
                    throw new EmbeddingProviderException("OpenAI embeddings request failed with HTTP "
                            + response.statusCode() + ": " + response.body());
                }
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new EmbeddingProviderException("Embedding request was interrupted", exception);
            } catch (IOException exception) {
                if (attempt == properties.maxAttempts()) {
                    throw new EmbeddingProviderException("OpenAI embeddings request failed", exception);
                }
            }
            sleep(backoffMillis);
            backoffMillis = Math.max(backoffMillis, 1) * 2;
        }
        throw new EmbeddingProviderException("OpenAI embeddings request exhausted retries");
    }

    private float[] parseEmbedding(String body) {
        try {
            JsonNode values = objectMapper.readTree(body).path("data").path(0).path("embedding");
            if (!values.isArray() || values.isEmpty()) {
                throw new EmbeddingProviderException("OpenAI returned an empty embedding");
            }
            List<Float> result = new ArrayList<>(values.size());
            values.forEach(value -> result.add((float) value.asDouble()));
            float[] vector = new float[result.size()];
            for (int i = 0; i < result.size(); i++) vector[i] = result.get(i);
            return vector;
        } catch (IOException exception) {
            throw new EmbeddingProviderException("Could not parse OpenAI embedding response", exception);
        }
    }

    private void logTokenUsage(String body) {
        try {
            JsonNode usage = objectMapper.readTree(body).path("usage");
            if (usage.has("prompt_tokens")) {
                log.info("OpenAI embedding model={} promptTokens={}", properties.model(),
                        usage.path("prompt_tokens").asInt());
            }
        } catch (IOException exception) {
            log.debug("Could not read embedding token usage", exception);
        }
    }

    private boolean isRetryable(int status) {
        return status == 408 || status == 429 || status >= 500;
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new EmbeddingProviderException("Embedding retry was interrupted", exception);
        }
    }

    private record EmbeddingRequest(String model, String input) { }

    public static class EmbeddingProviderException extends RuntimeException {
        public EmbeddingProviderException(String message) { super(message); }
        public EmbeddingProviderException(String message, Throwable cause) { super(message, cause); }
    }
}
