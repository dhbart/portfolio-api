package dhbart.portfolioapi.assistant.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import dhbart.portfolioapi.assistant.config.OpenAiResilienceProperties;
import java.time.Duration;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;

class OpenAiResilienceServiceTest {
    private final Executor executor = Runnable::run;

    @Test
    void retriesTransientProviderFailuresOnlyConfiguredTimes() {
        ChatClient client = mock(ChatClient.class, RETURNS_DEEP_STUBS);
        when(client.prompt().user(anyString()).call().content()).thenThrow(new RuntimeException("503 upstream"));
        var service = new OpenAiResilienceService(client, properties(3, Duration.ofMillis(1), Duration.ofSeconds(1), 100), executor);
        assertThatThrownBy(() -> service.generate("prompt")).isInstanceOf(OpenAiResilienceService.OpenAiCommunicationException.class);
        verify(client, atLeast(3)).prompt();
    }

    @Test
    void opensCircuitAfterConfiguredFailureWindow() {
        ChatClient client = mock(ChatClient.class, RETURNS_DEEP_STUBS);
        when(client.prompt().user(anyString()).call().content()).thenThrow(new RuntimeException("503 upstream"));
        var service = new OpenAiResilienceService(client, properties(1, Duration.ofMillis(1), Duration.ofSeconds(1), 2), executor);
        assertThatThrownBy(() -> service.generate("one")).isInstanceOf(OpenAiResilienceService.OpenAiCommunicationException.class);
        assertThatThrownBy(() -> service.generate("two")).isInstanceOf(OpenAiResilienceService.OpenAiCommunicationException.class);
        clearInvocations(client);
        assertThatThrownBy(() -> service.generate("three")).isInstanceOf(OpenAiResilienceService.OpenAiCommunicationException.class);
        verifyNoInteractions(client);
    }

    @Test
    void timesOutSlowProviderCalls() {
        ChatClient client = mock(ChatClient.class, RETURNS_DEEP_STUBS);
        when(client.prompt().user(anyString()).call().content()).thenAnswer(invocation -> {
            Thread.sleep(100);
            return "answer";
        });
        var executor = java.util.concurrent.Executors.newSingleThreadExecutor();
        try {
            var service = new OpenAiResilienceService(client, properties(1, Duration.ofMillis(1), Duration.ofMillis(10), 10), executor);
            assertThatThrownBy(() -> service.generate("prompt")).isInstanceOf(OpenAiResilienceService.OpenAiCommunicationException.class);
        } finally {
            executor.shutdownNow();
        }
    }

    private OpenAiResilienceProperties properties(int attempts, Duration backoff, Duration timeout, int window) {
        return new OpenAiResilienceProperties(timeout, attempts, backoff, 50, window, 2);
    }
}
