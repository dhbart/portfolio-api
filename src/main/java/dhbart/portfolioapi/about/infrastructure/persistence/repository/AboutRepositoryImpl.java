package dhbart.portfolioapi.about.infrastructure.persistence.repository;

import dhbart.portfolioapi.about.domain.model.About;
import dhbart.portfolioapi.about.domain.repository.AboutRepository;
import dhbart.portfolioapi.about.infrastructure.persistence.mapper.AboutEntityMapper;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public class AboutRepositoryImpl implements AboutRepository {

    private final AboutJpaRepository repository;
    private final AboutEntityMapper mapper;

    public AboutRepositoryImpl(AboutJpaRepository repository, AboutEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<About> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}
