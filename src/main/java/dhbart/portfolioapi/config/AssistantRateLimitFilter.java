package dhbart.portfolioapi.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

public class AssistantRateLimitFilter extends OncePerRequestFilter {
    private static final String CHAT_PATH = "/api/v1/assistant/chat";
    private final RateLimitProperties properties;
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    public AssistantRateLimitFilter(RateLimitProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        if (!"POST".equalsIgnoreCase(request.getMethod()) || !CHAT_PATH.equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        Bucket bucket = buckets.computeIfAbsent(request.getRemoteAddr(), ignored -> createBucket());
        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
            return;
        }
        response.setStatus(429);
        response.setHeader("Retry-After", Long.toString(Math.max(1, properties.retryAfter().toSeconds())));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String message = localizedMessage(request.getHeader("Accept-Language"));
        response.getWriter().write("{\"timestamp\":\"" + Instant.now()
                + "\",\"status\":429,\"error\":\"Too Many Requests\",\"message\":\""
                + message + "\",\"path\":\"" + CHAT_PATH + "\"}");
    }

    private Bucket createBucket() {
        Duration window = properties.window();
        Bandwidth limit = Bandwidth.classic(properties.capacity(),
                Refill.greedy(properties.capacity(), window));
        return Bucket.builder().addLimit(limit).build();
    }

    private String localizedMessage(String header) {
        Locale locale = header == null ? Locale.ENGLISH : Locale.forLanguageTag(header.split(",")[0]);
        if ("pt".equalsIgnoreCase(locale.getLanguage())) return "Limite de requisições excedido. Tente novamente mais tarde.";
        if ("es".equalsIgnoreCase(locale.getLanguage())) return "Límite de solicitudes excedido. Inténtalo de nuevo más tarde.";
        return "Request limit exceeded. Try again later.";
    }
}
