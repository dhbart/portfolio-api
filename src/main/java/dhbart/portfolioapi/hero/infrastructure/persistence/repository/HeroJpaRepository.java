package dhbart.portfolioapi.hero.infrastructure.persistence.repository;

import dhbart.portfolioapi.hero.infrastructure.persistence.entity.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface HeroJpaRepository extends JpaRepository<HeroEntity, Long> {
}
