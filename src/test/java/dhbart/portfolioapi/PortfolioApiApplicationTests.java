package dhbart.portfolioapi;

import dhbart.portfolioapi.hero.domain.repository.HeroRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PortfolioApiApplicationTests {

    @TestConfiguration
    static class TestBeans {
        @Bean
        HeroRepository heroRepository() {
            return Mockito.mock(HeroRepository.class);
        }

    }

    @Test
    void contextLoads() {
    }
}
