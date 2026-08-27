package dhbart.portfolioapi.assistant.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import dhbart.portfolioapi.assistant.config.EmbeddingProperties;
import java.time.Duration;
import org.junit.jupiter.api.Test;

class OpenAiEmbeddingServiceTest {

    @Test
    void rejectsMissingApiKeyWithoutCallingProvider() {
        var service = new OpenAiEmbeddingService(new EmbeddingProperties("", "http://127.0.0.1:1",
                "text-embedding-3-small", Duration.ofMillis(100), 1, Duration.ZERO), new ObjectMapper());

        assertThrows(IllegalStateException.class, () -> service.generate("text"));
    }

    @Test
    void exposesFloatVectorReturnedByMockProvider() throws Exception {
        var server = com.sun.net.httpserver.HttpServer.create(new java.net.InetSocketAddress(0), 0);
        server.createContext("/embeddings", exchange -> {
            exchange.sendResponseHeaders(200, 0);
            try (var output = exchange.getResponseBody()) {
                output.write("{\"data\":[{\"embedding\":[0.1,-0.2]}]}".getBytes());
            }
        });
        server.start();
        try {
            var service = new OpenAiEmbeddingService(new EmbeddingProperties("test-key",
                    "http://127.0.0.1:" + server.getAddress().getPort() + "/embeddings",
                    "text-embedding-3-small", Duration.ofSeconds(2), 1, Duration.ZERO), new ObjectMapper());
            assertArrayEquals(new float[]{0.1f, -0.2f}, service.generate("text"));
        } finally {
            server.stop(0);
        }
    }
}
