package dhbart.portfolioapi.hero.infrastructure.persistence.repository;

import dhbart.portfolioapi.hero.infrastructure.persistence.entity.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

interface HeroJpaRepository extends JpaRepository<HeroEntity, Long> {

    Optional<HeroEntity> findByLocale(String locale);

    Optional<HeroEntity> findByIdAndLocale(Long id, String locale);
}
