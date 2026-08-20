package dhbart.portfolioapi.project.infrastructure.persistence.repository;

import dhbart.portfolioapi.project.domain.model.Project;
import dhbart.portfolioapi.project.domain.repository.ProjectRepository;
import dhbart.portfolioapi.project.infrastructure.persistence.mapper.ProjectEntityMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public class ProjectRepositoryImpl implements ProjectRepository {

    private final ProjectJpaRepository repository;
    private final ProjectEntityMapper mapper;

    public ProjectRepositoryImpl(ProjectJpaRepository repository, ProjectEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Project> findAllByLocaleOrderByDisplayOrderAsc(String locale) {
        return repository.findAllByLocaleOrderByDisplayOrderAsc(locale).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Project> findBySlugAndLocale(String slug, String locale) {
        return repository.findBySlugAndLocale(slug, locale).map(mapper::toDomain);
    }
}
