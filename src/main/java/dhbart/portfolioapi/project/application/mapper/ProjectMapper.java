package dhbart.portfolioapi.project.application.mapper;

import dhbart.portfolioapi.project.application.dto.ProjectResponse;
import dhbart.portfolioapi.project.domain.model.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(), project.getSlug(), project.getTitle(), project.getHeadline(),
                project.getDescription(), project.getChallenge(), project.getSolution(),
                project.getImageUrl(), project.getGithubUrl(), project.getDemoUrl(),
                project.getFeatured(), project.getDisplayOrder());
    }
}
