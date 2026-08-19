package dhbart.portfolioapi.experience.infrastructure.persistence.repository;

import dhbart.portfolioapi.experience.domain.model.Experience;
import dhbart.portfolioapi.experience.domain.repository.ExperienceRepository;
import dhbart.portfolioapi.experience.infrastructure.persistence.mapper.ExperienceEntityMapper;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public class ExperienceRepositoryImpl implements ExperienceRepository {

    private final ExperienceJpaRepository repository;
    private final ExperienceEntityMapper mapper;

    public ExperienceRepositoryImpl(ExperienceJpaRepository repository, ExperienceEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Experience> findAllByOrderByDisplayOrderDesc() {
        return repository.findAllByOrderByDisplayOrderDesc().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
