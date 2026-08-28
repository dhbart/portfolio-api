package dhbart.portfolioapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "portfolio.security")
public record AdminSecurityProperties(String adminApiKey) {
}
