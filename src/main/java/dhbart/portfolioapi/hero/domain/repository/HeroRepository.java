package dhbart.portfolioapi.hero.domain.repository;

import dhbart.portfolioapi.hero.domain.model.Hero;
import java.util.Optional;

public interface HeroRepository {

    Optional<Hero> findByLocale(String locale);

    Optional<Hero> findByIdAndLocale(Long id, String locale);

    default Optional<Hero> findById(Long id) {
        return findByIdAndLocale(id, "pt-BR");
    }
}
