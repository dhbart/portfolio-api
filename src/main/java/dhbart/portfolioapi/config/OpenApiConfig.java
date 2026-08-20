package dhbart.portfolioapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Portfolio API",
                version = "v1",
                description = "Public read-only API for the dhbart portfolio"),
        servers = @Server(url = "http://localhost:8080", description = "Local development server"))
public class OpenApiConfig {
}
