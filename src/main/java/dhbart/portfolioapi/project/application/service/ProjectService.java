package dhbart.portfolioapi.project.application.service;

import dhbart.portfolioapi.project.application.dto.ProjectResponse;
import dhbart.portfolioapi.project.application.mapper.ProjectMapper;
import dhbart.portfolioapi.project.domain.model.Project;
import dhbart.portfolioapi.project.domain.repository.ProjectRepository;
import dhbart.portfolioapi.project.domain.repository.ProjectTechnologyRepository;
import dhbart.portfolioapi.technology.application.mapper.TechnologyMapper;
import dhbart.portfolioapi.exception.ResourceNotFoundException;
import java.util.List;
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

    public ProjectService(ProjectRepository projectRepository,
                          ProjectTechnologyRepository projectTechnologyRepository,
                          ProjectMapper projectMapper,
                          TechnologyMapper technologyMapper) {
        this.projectRepository = projectRepository;
        this.projectTechnologyRepository = projectTechnologyRepository;
        this.projectMapper = projectMapper;
        this.technologyMapper = technologyMapper;
    }

    public List<ProjectResponse> findAllProjects() {
        var projects = projectRepository.findAllByOrderByDisplayOrderAsc();
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

    public ProjectResponse findProject(String slug) {
        return projectRepository.findBySlug(slug)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }

    private ProjectResponse toResponse(Project project) {
        var technologies = projectTechnologyRepository.findAllByProjectIdOrderByDisplayOrderAsc(project.getId())
                .stream()
                .map(projectTechnology -> technologyMapper.toResponse(projectTechnology.getTechnology()))
                .toList();
        return projectMapper.toResponse(project, technologies);
    }
}
