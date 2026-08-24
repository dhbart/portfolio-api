package dhbart.portfolioapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI portfolioApi() {

        return new OpenAPI()

                .info(new Info()

                        .title("Portfolio API")

                        .version("v1")

                        .description("Public read-only API for Daniel Bartholdy's portfolio.")

                        .contact(new Contact()

                                .name("Daniel Bartholdy")

                                .url("https://github.com/dhbart")))

                .externalDocs(new ExternalDocumentation()

                        .description("GitHub Repository")

                        .url("https://github.com/dhbart/portfolio-api"));
    }

}