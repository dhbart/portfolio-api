package dhbart.portfolioapi.infrastructure;

import dhbart.portfolioapi.about.domain.repository.AboutRepository;
import dhbart.portfolioapi.certification.domain.repository.CertificationRepository;
import dhbart.portfolioapi.experience.domain.repository.ExperienceRepository;
import dhbart.portfolioapi.hero.domain.repository.HeroRepository;
import dhbart.portfolioapi.project.domain.repository.ProjectRepository;
import dhbart.portfolioapi.project.domain.repository.ProjectTechnologyRepository;
import dhbart.portfolioapi.sociallink.domain.repository.SocialLinkRepository;
import dhbart.portfolioapi.support.IntegrationTestBase;
import dhbart.portfolioapi.technology.domain.repository.TechnologyRepository;
import java.util.Comparator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;

class RepositoryIntegrationTest extends IntegrationTestBase {
    @Autowired AboutRepository aboutRepository;
    @Autowired CertificationRepository certificationRepository;
    @Autowired ExperienceRepository experienceRepository;
    @Autowired HeroRepository heroRepository;
    @Autowired ProjectRepository projectRepository;
    @Autowired ProjectTechnologyRepository projectTechnologyRepository;
    @Autowired SocialLinkRepository socialLinkRepository;
    @Autowired TechnologyRepository technologyRepository;

    @Test
    void shouldExecuteFlywayAgainstEmptyPostgresAndLoadSeededAggregates() {
        assertThat(heroRepository.findByLocale("pt-BR")).isPresent();
        assertThat(aboutRepository.findByLocale("en-US")).isPresent();
        assertThat(experienceRepository.findAllByLocaleOrderByDisplayOrderDesc("es-ES")).isNotEmpty();
        assertThat(certificationRepository.findAllByLocaleOrderByDisplayOrderAsc("en-US")).isNotEmpty();
        assertThat(socialLinkRepository.findAllByOrderByDisplayOrderAsc()).hasSize(2);
        assertThat(technologyRepository.findAllByOrderByDisplayOrderAsc()).isNotEmpty();
    }

    @Test
    void shouldReturnProjectsOrderedAndResolveLocalizedSlug() {
        var projects = projectRepository.findAllByLocaleOrderByDisplayOrderAsc("en-US");
        assertThat(projects).isNotEmpty().isSortedAccordingTo(Comparator.comparingInt(p -> p.getDisplayOrder()));
        var project = projectRepository.findBySlugAndLocale(projects.get(0).getSlug(), "en-US");
        assertThat(project).isPresent();
        assertThat(projectTechnologyRepository.findAllByProjectIdOrderByDisplayOrderAsc(project.orElseThrow().getId()))
                .extracting(r -> r.getDisplayOrder()).isSorted();
    }

    @Test
    void shouldKeepLocalizedRowsDistinctAndRespectForeignKeyBackedRelationships() {
        var portuguese = projectRepository.findAllByLocaleOrderByDisplayOrderAsc("pt-BR");
        var english = projectRepository.findAllByLocaleOrderByDisplayOrderAsc("en-US");
        assertThat(portuguese).hasSameSizeAs(english);
        assertThat(portuguese.get(0).getId()).isNotEqualTo(english.get(0).getId());
        var relationships = projectTechnologyRepository.findAllByProjectIdInOrderByProjectIdAscDisplayOrderAsc(
                portuguese.stream().map(p -> p.getId()).toList());
        assertThat(relationships).allSatisfy(r -> assertThat(r.getProject()).isNotNull());
        assertThat(relationships).allSatisfy(r -> assertThat(r.getTechnology()).isNotNull());
    }
}
