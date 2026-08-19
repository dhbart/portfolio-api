package dhbart.portfolioapi.project.application.service;

import dhbart.portfolioapi.project.application.dto.ProjectResponse;
import dhbart.portfolioapi.project.application.mapper.ProjectMapper;
import dhbart.portfolioapi.project.domain.repository.ProjectRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public List<ProjectResponse> findAllProjects() {
        return projectRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    public ProjectResponse findProject(String slug) {
        return projectRepository.findBySlug(slug)
                .map(projectMapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Project not found"));
    }
}
