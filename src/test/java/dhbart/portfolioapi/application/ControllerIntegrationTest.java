package dhbart.portfolioapi.application;

import dhbart.portfolioapi.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
class ControllerIntegrationTest extends IntegrationTestBase {
    @Autowired MockMvc mockMvc;
    @Autowired CacheManager cacheManager;

    @Test
    void shouldExposeAllPublicPortfolioResourcesInJson() throws Exception {
        mockMvc.perform(get("/api/v1/about")).andExpect(status().isOk()).andExpect(jsonPath("$.title").isNotEmpty());
        mockMvc.perform(get("/api/v1/experiences")).andExpect(status().isOk()).andExpect(jsonPath("$[0].company").isNotEmpty());
        mockMvc.perform(get("/api/v1/projects")).andExpect(status().isOk()).andExpect(jsonPath("$[0].technologies").isArray());
        mockMvc.perform(get("/api/v1/technologies")).andExpect(status().isOk()).andExpect(jsonPath("$[0].slug").isNotEmpty());
        mockMvc.perform(get("/api/v1/certifications")).andExpect(status().isOk()).andExpect(jsonPath("$[0].certificationType").isNotEmpty());
        mockMvc.perform(get("/api/v1/social-links")).andExpect(status().isOk()).andExpect(jsonPath("$[0].url").isNotEmpty());
        mockMvc.perform(get("/api/v1/projects/portfolio").header("Accept-Language", "en-US"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.slug").value("portfolio"));
        mockMvc.perform(get("/api/v1/certifications/1").header("Accept-Language", "en-US"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturnLocalizedContentAndSupportFallback() throws Exception {
        mockMvc.perform(get("/api/v1/hero").header("Accept-Language", "pt-BR"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Desenvolvedor de Software • Tech Lead • Analista de Negócios • Product Owner"));
        mockMvc.perform(get("/api/v1/hero").header("Accept-Language", "es-ES"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title").isNotEmpty());
        mockMvc.perform(get("/api/v1/about").header("Accept-Language", "xx-ZZ"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.title").isNotEmpty());
    }

    @Test
    void shouldReturnStructuredNotFoundErrorForUnknownProject() throws Exception {
        mockMvc.perform(get("/api/v1/projects/does-not-exist"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.path").value("/api/v1/projects/does-not-exist"));
    }

    @Test
    void shouldAllowCorsGetAndOptionsButDenyUnsupportedMethods() throws Exception {
        mockMvc.perform(options("/api/v1/hero").header("Origin", "http://localhost:4200")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk()).andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:4200"));
        mockMvc.perform(get("/actuator/health")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("UP"));
        mockMvc.perform(options("/api/v1/hero").header("Origin", "http://localhost:4200")
                        .header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldPopulateCaffeineCacheForRepeatedProjectReads() throws Exception {
        mockMvc.perform(get("/api/v1/projects").header("Accept-Language", "en-US")).andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/projects").header("Accept-Language", "en-US")).andExpect(status().isOk());
        assertThat(cacheManager.getCache("projects").get("en-US")).isNotNull();
    }
}
