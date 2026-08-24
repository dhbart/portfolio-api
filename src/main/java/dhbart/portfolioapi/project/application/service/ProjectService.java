package dhbart.portfolioapi.project.application.service;

import dhbart.portfolioapi.project.application.dto.ProjectResponse;
import dhbart.portfolioapi.project.application.mapper.ProjectMapper;
import dhbart.portfolioapi.project.domain.model.Project;
import dhbart.portfolioapi.project.domain.repository.ProjectRepository;
import dhbart.portfolioapi.project.domain.repository.ProjectTechnologyRepository;
import dhbart.portfolioapi.technology.application.mapper.TechnologyMapper;
import dhbart.portfolioapi.exception.ResourceNotFoundException;
import dhbart.portfolioapi.localization.application.service.LocaleResolver;
import dhbart.portfolioapi.config.CacheNames;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectTechnologyRepository projectTechnologyRepository;
    private final ProjectMapper projectMapper;
    private final TechnologyMapper technologyMapper;
    private final LocaleResolver localeResolver;

    public ProjectService(ProjectRepository projectRepository,
                          ProjectTechnologyRepository projectTechnologyRepository,
                          ProjectMapper projectMapper,
                          TechnologyMapper technologyMapper,
                          LocaleResolver localeResolver) {
        this.projectRepository = projectRepository;
        this.projectTechnologyRepository = projectTechnologyRepository;
        this.projectMapper = projectMapper;
        this.technologyMapper = technologyMapper;
        this.localeResolver = localeResolver;
    }

    @Cacheable(cacheNames = CacheNames.PROJECTS, key = "#acceptLanguage")
    public List<ProjectResponse> findAllProjects(String acceptLanguage) {
        var projects = localizedProjects(acceptLanguage);
        var technologiesByProjectId = projectTechnologyRepository
                .findAllByProjectIdInOrderByProjectIdAscDisplayOrderAsc(
                        projects.stream().map(Project::getId).toList())
                .stream()
                .collect(Collectors.groupingBy(
                        projectTechnology -> projectTechnology.getProject().getId(),
                        Collectors.mapping(projectTechnology -> technologyMapper.toResponse(
                                projectTechnology.getTechnology()), Collectors.toList())));

        return projects.stream()
                .map(project -> projectMapper.toResponse(
                        project, technologiesByProjectId.getOrDefault(project.getId(), List.of())))
                .toList();
    }

    @Cacheable(cacheNames = CacheNames.PROJECT_DETAILS, key = "{#slug, #acceptLanguage}")
    public ProjectResponse findProject(String slug, String acceptLanguage) {
        for (String locale : localeResolver.resolve(acceptLanguage)) {
            var project = projectRepository.findBySlugAndLocale(slug, locale);
            if (project.isPresent()) return toResponse(project.get());
        }
        throw new ResourceNotFoundException("Project not found");
    }

    private List<Project> localizedProjects(String acceptLanguage) {
        for (String locale : localeResolver.resolve(acceptLanguage)) {
            var projects = projectRepository.findAllByLocaleOrderByDisplayOrderAsc(locale);
            if (!projects.isEmpty()) return projects;
        }
        return List.of();
    }

    private ProjectResponse toResponse(Project project) {
        var technologies = projectTechnologyRepository.findAllByProjectIdOrderByDisplayOrderAsc(project.getId())
                .stream()
                .map(projectTechnology -> technologyMapper.toResponse(projectTechnology.getTechnology()))
                .toList();
        return projectMapper.toResponse(project, technologies);
    }
}
