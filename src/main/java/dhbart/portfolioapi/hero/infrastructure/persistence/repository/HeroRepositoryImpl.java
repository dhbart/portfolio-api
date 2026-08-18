package dhbart.portfolioapi.hero.infrastructure.persistence.repository;

import dhbart.portfolioapi.hero.domain.model.Hero;
import dhbart.portfolioapi.hero.domain.repository.HeroRepository;
import dhbart.portfolioapi.hero.infrastructure.persistence.mapper.HeroEntityMapper;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;

@Repository
@Profile("!test")
public class HeroRepositoryImpl implements HeroRepository {

    private final HeroJpaRepository repository;
    private final HeroEntityMapper mapper;

    public HeroRepositoryImpl(HeroJpaRepository repository, HeroEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Hero> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
