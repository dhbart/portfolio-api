package dhbart.portfolioapi.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import java.time.Duration;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class AssistantRateLimitFilterTest {
    @Test
    void rejectsRequestsAfterConfiguredCapacityWithRetryAfter() throws Exception {
        var filter = new AssistantRateLimitFilter(new RateLimitProperties(1, Duration.ofMinutes(1), Duration.ofSeconds(30)));
        FilterChain chain = mock(FilterChain.class);
        var first = request();
        filter.doFilter(first, new MockHttpServletResponse(), chain);
        var secondResponse = new MockHttpServletResponse();
        filter.doFilter(request(), secondResponse, chain);
        assertThat(secondResponse.getStatus()).isEqualTo(429);
        assertThat(secondResponse.getHeader("Retry-After")).isEqualTo("30");
        verify(chain).doFilter(any(), any());
    }

    private MockHttpServletRequest request() {
        var request = new MockHttpServletRequest("POST", "/api/v1/assistant/chat");
        request.setRemoteAddr("192.0.2.10");
        return request;
    }
}
