package dhbart.portfolioapi.project.infrastructure.persistence.repository;

import dhbart.portfolioapi.project.domain.model.ProjectTechnology;
import dhbart.portfolioapi.project.domain.repository.ProjectTechnologyRepository;
import dhbart.portfolioapi.project.infrastructure.persistence.mapper.ProjectTechnologyEntityMapper;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public class ProjectTechnologyRepositoryImpl implements ProjectTechnologyRepository {

    private final ProjectTechnologyJpaRepository repository;
    private final ProjectTechnologyEntityMapper mapper;

    public ProjectTechnologyRepositoryImpl(ProjectTechnologyJpaRepository repository,
                                           ProjectTechnologyEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ProjectTechnology> findAllByProjectIdOrderByDisplayOrderAsc(Long projectId) {
        return repository.findAllByProject_IdOrderByDisplayOrderAsc(projectId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
