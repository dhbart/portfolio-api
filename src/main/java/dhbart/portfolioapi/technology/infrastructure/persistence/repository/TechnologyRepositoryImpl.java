package dhbart.portfolioapi.technology.infrastructure.persistence.repository;

import dhbart.portfolioapi.technology.domain.model.Technology;
import dhbart.portfolioapi.technology.domain.repository.TechnologyRepository;
import dhbart.portfolioapi.technology.infrastructure.persistence.mapper.TechnologyEntityMapper;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public class TechnologyRepositoryImpl implements TechnologyRepository {

    private final TechnologyJpaRepository repository;
    private final TechnologyEntityMapper mapper;

    public TechnologyRepositoryImpl(TechnologyJpaRepository repository, TechnologyEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Technology> findAllByOrderByDisplayOrderAsc() {
        return repository.findAllByOrderByDisplayOrderAsc().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
