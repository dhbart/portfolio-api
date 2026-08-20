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
    public Optional<About> findByLocale(String locale) {
        return repository.findByLocale(locale).map(mapper::toDomain);
    }

    @Override
    public Optional<About> findByIdAndLocale(Long id, String locale) {
        return repository.findByIdAndLocale(id, locale).map(mapper::toDomain);
    }
}
