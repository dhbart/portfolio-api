package dhbart.portfolioapi;

import dhbart.portfolioapi.about.domain.repository.AboutRepository;
import dhbart.portfolioapi.certification.domain.repository.CertificationRepository;
import dhbart.portfolioapi.experience.domain.repository.ExperienceRepository;
import dhbart.portfolioapi.hero.domain.repository.HeroRepository;
import dhbart.portfolioapi.project.domain.repository.ProjectRepository;
import dhbart.portfolioapi.sociallink.domain.repository.SocialLinkRepository;
import dhbart.portfolioapi.project.domain.repository.ProjectTechnologyRepository;
import dhbart.portfolioapi.technology.domain.repository.TechnologyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import dhbart.portfolioapi.hero.application.service.HeroService;
import dhbart.portfolioapi.hero.domain.model.Hero;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PortfolioApiApplicationTests {

    @Autowired
    private HeroService heroService;

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private CacheManager cacheManager;

    @TestConfiguration
    static class TestBeans {
        @Bean
        HeroRepository heroRepository() {
            return Mockito.mock(HeroRepository.class);
        }

        @Bean
        AboutRepository aboutRepository() {
            return Mockito.mock(AboutRepository.class);
        }

        @Bean
        ProjectRepository projectRepository() {
            return Mockito.mock(ProjectRepository.class);
        }

        @Bean
        ProjectTechnologyRepository projectTechnologyRepository() {
            return Mockito.mock(ProjectTechnologyRepository.class);
        }

        @Bean
        ExperienceRepository experienceRepository() {
            return Mockito.mock(ExperienceRepository.class);
        }

        @Bean
        SocialLinkRepository socialLinkRepository() {
            return Mockito.mock(SocialLinkRepository.class);
        }

        @Bean
        CertificationRepository certificationRepository() {
            return Mockito.mock(CertificationRepository.class);
        }

        @Bean
        TechnologyRepository technologyRepository() {
            return Mockito.mock(TechnologyRepository.class);
        }

    }

    @Test
    void contextLoads() {
    }

    @Test
    void repeatedHeroReadsUseTheCache() {
        var hero = Hero.builder().id(1L).name("Daniel Henrique Bartholdy").build();
        when(heroRepository.findByLocale("en-US")).thenReturn(java.util.Optional.of(hero));

        heroService.findHero("en-US");
        heroService.findHero("en-US");

        verify(heroRepository, times(1)).findByLocale("en-US");
        assertThat(cacheManager.getCache("hero").get("en-US")).isNotNull();
    }
}
