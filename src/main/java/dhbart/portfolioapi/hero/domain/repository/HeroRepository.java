package dhbart.portfolioapi.hero.domain.repository;

import dhbart.portfolioapi.hero.domain.model.Hero;
import java.util.Optional;

public interface HeroRepository {

    Optional<Hero> findById(Long id);
}
