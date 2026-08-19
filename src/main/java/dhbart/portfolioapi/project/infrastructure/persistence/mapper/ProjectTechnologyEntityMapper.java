package dhbart.portfolioapi.project.infrastructure.persistence.mapper;

import dhbart.portfolioapi.project.domain.model.ProjectTechnology;
import dhbart.portfolioapi.project.infrastructure.persistence.entity.ProjectTechnologyEntity;
import dhbart.portfolioapi.technology.infrastructure.persistence.mapper.TechnologyEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectTechnologyEntityMapper {

    private final ProjectEntityMapper projectMapper;
    private final TechnologyEntityMapper technologyMapper;

    public ProjectTechnologyEntityMapper(ProjectEntityMapper projectMapper,
                                         TechnologyEntityMapper technologyMapper) {
        this.projectMapper = projectMapper;
        this.technologyMapper = technologyMapper;
    }

    public ProjectTechnology toDomain(ProjectTechnologyEntity entity) {
        return ProjectTechnology.builder()
                .project(projectMapper.toDomain(entity.getProject()))
                .technology(technologyMapper.toDomain(entity.getTechnology()))
                .displayOrder(entity.getDisplayOrder())
                .build();
    }
}
