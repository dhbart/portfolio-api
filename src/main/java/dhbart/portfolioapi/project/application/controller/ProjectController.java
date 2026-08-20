package dhbart.portfolioapi.project.application.controller;

import dhbart.portfolioapi.project.application.dto.ProjectResponse;
import dhbart.portfolioapi.project.application.service.ProjectService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects(@RequestHeader(name = "Accept-Language", required = false) String acceptLanguage) {
        return ResponseEntity.ok(projectService.findAllProjects(acceptLanguage));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable String slug,
                                                      @RequestHeader(name = "Accept-Language", required = false) String acceptLanguage) {
        return ResponseEntity.ok(projectService.findProject(slug, acceptLanguage));
    }
}
