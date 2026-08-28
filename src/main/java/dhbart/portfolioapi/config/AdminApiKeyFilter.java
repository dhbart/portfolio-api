package dhbart.portfolioapi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AdminApiKeyFilter extends OncePerRequestFilter {

    static final String API_KEY_HEADER = "X-API-KEY";
    private static final String ADMIN_PATH_PREFIX = "/api/v1/admin/";
    private static final String ADMIN_PRINCIPAL = "admin-api-key";

    private final AdminSecurityProperties properties;

    public AdminApiKeyFilter(AdminSecurityProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().startsWith(ADMIN_PATH_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String configuredKey = properties.adminApiKey();
        String providedKey = request.getHeader(API_KEY_HEADER);
        if (configuredKey == null || configuredKey.isBlank() || providedKey == null
                || !secureEquals(configuredKey, providedKey)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
            return;
        }

        var authentication = UsernamePasswordAuthenticationToken.authenticated(
                ADMIN_PRINCIPAL, null, java.util.List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private boolean secureEquals(String expected, String actual) {
        return MessageDigest.isEqual(
                expected.getBytes(StandardCharsets.UTF_8), actual.getBytes(StandardCharsets.UTF_8));
    }
}
