package dhbart.portfolioapi.assistant.service;

import dhbart.portfolioapi.assistant.config.OpenAiResilienceProperties;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Service
public class OpenAiResilienceService {
    private final ChatClient chatClient;
    private final OpenAiResilienceProperties properties;
    private final Executor executor;
    private final Retry retry;
    private final CircuitBreaker circuitBreaker;
    private final Bulkhead bulkhead;
    private final TimeLimiter timeLimiter;

    public OpenAiResilienceService(ChatClient chatClient, OpenAiResilienceProperties properties,
                                   Executor assistantAiExecutor) {
        this.chatClient = chatClient;
        this.properties = properties;
        this.executor = assistantAiExecutor;
        this.retry = Retry.of("openai-chat", RetryConfig.custom()
                .maxAttempts(properties.retryAttempts())
                .intervalFunction(IntervalFunction.ofExponentialBackoff(properties.initialBackoff().toMillis(), 2.0))
                .retryOnException(OpenAiResilienceService::isTransient)
                .build());
        this.circuitBreaker = CircuitBreaker.of("openai-chat", CircuitBreakerConfig.custom()
                .failureRateThreshold(properties.failureRateThreshold())
                .slidingWindowSize(properties.slidingWindowSize()).build());
        this.bulkhead = Bulkhead.of("openai-chat", BulkheadConfig.custom()
                .maxConcurrentCalls(properties.bulkheadMaxConcurrentCalls()).build());
        this.timeLimiter = TimeLimiter.of("openai-chat", TimeLimiterConfig.custom()
                .timeoutDuration(properties.timeout()).cancelRunningFuture(true).build());
    }

    public String generate(String prompt) {
        Supplier<String> call = Bulkhead.decorateSupplier(bulkhead,
                CircuitBreaker.decorateSupplier(circuitBreaker,
                        Retry.decorateSupplier(retry, () -> chatClient.prompt().user(prompt).call().content())));
        try {
            return timeLimiter.executeFutureSupplier(() -> CompletableFuture.supplyAsync(call, executor));
        } catch (TimeoutException exception) {
            throw new OpenAiCommunicationException("OpenAI request timed out", exception);
        } catch (Exception exception) {
            throw new OpenAiCommunicationException("OpenAI request failed", exception);
        }
    }

    private static boolean isTransient(Throwable exception) {
        String text = exception.getMessage() == null ? "" : exception.getMessage().toLowerCase();
        return !text.contains("400") && !text.contains("401") && !text.contains("403")
                && !text.contains("invalid request") && !text.contains("bad request");
    }

    public static class OpenAiCommunicationException extends RuntimeException {
        public OpenAiCommunicationException(String message, Throwable cause) { super(message, cause); }
    }
}
