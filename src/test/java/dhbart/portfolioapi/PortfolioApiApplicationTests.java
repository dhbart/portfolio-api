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
}
