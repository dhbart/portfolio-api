package dhbart.portfolioapi.project.application.service;

import dhbart.portfolioapi.exception.ResourceNotFoundException;
import dhbart.portfolioapi.localization.application.service.LocaleResolver;
import dhbart.portfolioapi.project.application.dto.ProjectResponse;
import dhbart.portfolioapi.project.application.mapper.ProjectMapper;
import dhbart.portfolioapi.project.domain.model.Project;
import dhbart.portfolioapi.project.domain.model.ProjectTechnology;
import dhbart.portfolioapi.project.domain.repository.ProjectRepository;
import dhbart.portfolioapi.project.domain.repository.ProjectTechnologyRepository;
import dhbart.portfolioapi.technology.application.mapper.TechnologyMapper;
import dhbart.portfolioapi.technology.domain.model.Technology;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock ProjectRepository projectRepository;
    @Mock ProjectTechnologyRepository relationshipRepository;
    @Mock ProjectMapper projectMapper;
    @Mock TechnologyMapper technologyMapper;

    @Test
    void shouldReturnProjectBySlugUsingLocalizedTechnologyOrdering() {
        var project = project("portfolio-api", 1);
        var first = technology("java", 1);
        var second = technology("postgresql", 2);
        when(projectRepository.findBySlugAndLocale("portfolio-api", "en-US")).thenReturn(Optional.of(project));
        when(relationshipRepository.findAllByProjectIdOrderByDisplayOrderAsc(1L)).thenReturn(List.of(
                ProjectTechnology.builder().project(project).technology(first).displayOrder(1).build(),
                ProjectTechnology.builder().project(project).technology(second).displayOrder(2).build()));
        when(technologyMapper.toResponse(first)).thenReturn(new dhbart.portfolioapi.technology.application.dto.TechnologyResponse(1L, "Java", "java", null, 1));
        when(technologyMapper.toResponse(second)).thenReturn(new dhbart.portfolioapi.technology.application.dto.TechnologyResponse(2L, "PostgreSQL", "postgresql", null, 2));
        var response = new ProjectResponse(1L, "portfolio-api", "Portfolio API", "headline", "description", "challenge", "solution", null, null, null, true, 1,
                List.of(technologyMapper.toResponse(first), technologyMapper.toResponse(second)));
        when(projectMapper.toResponse(project, List.of(technologyMapper.toResponse(first), technologyMapper.toResponse(second)))).thenReturn(response);

        assertThat(new ProjectService(projectRepository, relationshipRepository, projectMapper, technologyMapper, new LocaleResolver())
                .findProject("portfolio-api", "en-US")).isEqualTo(response);
    }

    @Test
    void shouldThrowNotFoundWhenSlugHasNoSupportedTranslation() {
        when(projectRepository.findBySlugAndLocale("missing", "en-US")).thenReturn(Optional.empty());
        when(projectRepository.findBySlugAndLocale("missing", "pt-BR")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> new ProjectService(projectRepository, relationshipRepository, projectMapper, technologyMapper, new LocaleResolver())
                .findProject("missing", "xx-ZZ")).isInstanceOf(ResourceNotFoundException.class);
    }

    private Project project(String slug, int order) { return Project.builder().id(1L).slug(slug).title("Portfolio API").headline("headline").description("description").challenge("challenge").solution("solution").featured(true).displayOrder(order).build(); }
    private Technology technology(String slug, int order) { return Technology.builder().id((long) order).name(slug).slug(slug).displayOrder(order).build(); }
}
