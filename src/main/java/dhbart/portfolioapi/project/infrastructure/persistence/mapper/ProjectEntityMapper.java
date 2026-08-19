package dhbart.portfolioapi.project.infrastructure.persistence.mapper;

import dhbart.portfolioapi.project.domain.model.Project;
import dhbart.portfolioapi.project.infrastructure.persistence.entity.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectEntityMapper {

    public Project toDomain(ProjectEntity entity) {
        return Project.builder()
                .id(entity.getId())
                .slug(entity.getSlug())
                .title(entity.getTitle())
                .headline(entity.getHeadline())
                .description(entity.getDescription())
                .challenge(entity.getChallenge())
                .solution(entity.getSolution())
                .imageUrl(entity.getImageUrl())
                .githubUrl(entity.getGithubUrl())
                .demoUrl(entity.getDemoUrl())
                .featured(entity.getFeatured())
                .displayOrder(entity.getDisplayOrder())
                .build();
    }

    public ProjectEntity toEntity(Project project) {
        return new ProjectEntity(
                project.getId(), project.getSlug(), project.getTitle(), project.getHeadline(),
                project.getDescription(), project.getChallenge(), project.getSolution(),
                project.getImageUrl(), project.getGithubUrl(), project.getDemoUrl(),
                project.getFeatured(), project.getDisplayOrder(), null, null);
    }
}
