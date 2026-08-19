package dhbart.portfolioapi.project.application.service;

import dhbart.portfolioapi.project.application.dto.ProjectResponse;
import dhbart.portfolioapi.project.application.mapper.ProjectMapper;
import dhbart.portfolioapi.project.domain.model.Project;
import dhbart.portfolioapi.project.domain.repository.ProjectRepository;
import dhbart.portfolioapi.project.domain.repository.ProjectTechnologyRepository;
import dhbart.portfolioapi.technology.application.mapper.TechnologyMapper;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
        return projectRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProjectResponse findProject(String slug) {
        return projectRepository.findBySlug(slug)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Project not found"));
    }

    private ProjectResponse toResponse(Project project) {
        var technologies = projectTechnologyRepository.findAllByProjectIdOrderByDisplayOrderAsc(project.getId())
                .stream()
                .map(projectTechnology -> technologyMapper.toResponse(projectTechnology.getTechnology()))
                .toList();
        return projectMapper.toResponse(project, technologies);
    }
}
